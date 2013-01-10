/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.bluetouch.qulity.batch.impl.ImportDatabaseBatchJob
   Module Description   :

   Date Created      : 2007/8/29
   Original Author   : jeffma
   Team              : Bluetouch
   ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   MODIFICATION HISTORY
   ------------------------------------------------------------------------------
   Date Modified       Modified by       Comments
   ------------------------------------------------------------------------------
   ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
package com.bluetechnology.qulity.batch.impl;

import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bluetechnology.qulity.batch.AbstractBatchJob;
import com.csvreader.CsvReader;

/**
 * @author jeffma 批次作業範例
 */
public class ImportDatabaseBatchJob extends AbstractBatchJob {
	/** serialVersionUID */
	private static final long	serialVersionUID	= -7553539527635188735L;
	/** logger */
	public final short			TYPE_NULL			= 0;
	public final short			TYPE_DOUBLE			= 1;
	public final short			TYPE_STRING			= 2;
	public final short			TYPE_TIMESTAMP		= 3;
	public final short			TYPE_TIMESTAMP_NOW	= 9;
	private Log					logger				= LogFactory.getLog(getClass());
	private short[]				columnTypes			= new short[] { 1, 2, 2, 3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 9, 2, 9 };
	private int					batchSize			= 200;
	private String				importFile			= "C:\\FLOWNHIS.txt";

	/** default constructors */
	public ImportDatabaseBatchJob() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bluetouch.qulity.batch.AbstractBatch#execute()
	 */
	public void execute() {
		Connection conn = null;
		try {
			// 設定 JDBC
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
			conn.setAutoCommit(false);
			PreparedStatement pstmt = null;
			// 準備 PrepareStatement
			pstmt = conn.prepareStatement("insert into v_flownhis values (" + "?,?,?,?,?,?,?,?,?,?," + "?,?,?,?,?,?,?,?,?,?," + "?,?,?,?,?,?,?,?,?,?," + "?,?,?,?,?,?,?,?,?,?," + "?,?,?,?,?,?,?,?,?,?)");
			// 讀取檔案
			CsvReader reader = new CsvReader(importFile, ',', Charset.forName("BIG5"));
			// 忽略檔頭部份
			reader.skipLine();
			for (int i = 0; reader.readRecord(); i++) {
				for (int j = 0; j < columnTypes.length; j++) {
					// 根據設定 columnTypes 將 csv 資料轉型以符合資料庫型別
					switch (columnTypes[j]) {
					case TYPE_DOUBLE:
						if (StringUtils.isNotEmpty(reader.get(j)))
							pstmt.setDouble(j + 1, Double.parseDouble(reader.get(j)));
						else
							pstmt.setString(j + 1, null);
						break;
					case TYPE_STRING:
						pstmt.setString(j + 1, reader.get(j));
						break;
					case TYPE_TIMESTAMP:
						pstmt.setTimestamp(j + 1, Timestamp.valueOf(reader.get(j)));
						break;
					case TYPE_TIMESTAMP_NOW:
						pstmt.setTimestamp(j + 1, new Timestamp(new Date().getTime()));
						break;
					default:
						pstmt.setString(j + 1, null);
						break;
					}
				}
				pstmt.addBatch();
				// 為效能, 以批次 commit 資料
				if (i % batchSize == 0 && i != 0) {
					pstmt.executeBatch();
					conn.commit();
					logger.debug("insert " + batchSize + " records");
				}
			}
			// 批次餘數 commit
			int[] count = pstmt.executeBatch();
			conn.commit();
			logger.debug("insert records, size:" + count.length);
			pstmt.close();
		}
		catch (Exception e) {
			logger.error("update execure log fail", e);
		}
		finally {
			try {
				// 關閉 JDBC Connection
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
