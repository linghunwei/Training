/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.bluetouch.qulity.batch.impl.ResetDatabaseBatchJob
   Module Description   :

   Date Created      : 2007/7/26
   Original Author   : jeffma
   Team              : Bluetouch
   ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   MODIFICATION HISTORY
   ------------------------------------------------------------------------------
   Date Modified       Modified by       Comments
   ------------------------------------------------------------------------------
   ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
package com.bluetechnology.qulity.batch.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bluetechnology.qulity.batch.AbstractBatchJob;

/**
 * @author jeffma
 * 批次作業範例
 */
public class ResetDatabaseBatchJob extends AbstractBatchJob {

	/** serialVersionUID */
	private static final long serialVersionUID = -7553539527635188735L;
	/** logger */
	private Log logger = LogFactory.getLog(getClass());

	/** default constructors */
	public ResetDatabaseBatchJob() {
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
			PreparedStatement pstmt = null;

			// 清除不需要資料
			pstmt = conn.prepareStatement("delete from BATCH_LOG_TMP");
			int count = pstmt.executeUpdate();
			logger.debug("delete record size:" + count);

			// 重倒資料
			pstmt = conn.prepareStatement("insert into BATCH_LOG_TMP select * from BATCH_LOG");
			count = pstmt.executeUpdate();
			logger.debug("update record size:" + count);

			pstmt.close();
		} catch (Exception e) {
			logger.error("update execure log fail", e);
		} finally {
			try {
				// 關閉 JDBC Connection
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
