/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.bluetouch.qulity.batch.AbstractBatchJob
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
package com.bluetechnology.qulity.batch;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author jeffma
 * 
 */
public abstract class AbstractBatchJob implements BatchJob, Serializable {
	/** logger */
	private Log					logger		= LogFactory.getLog(getClass());
	/** actor */
	private String				actor;
	// for Oracle
	// protected static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
	// protected static final String JDBC_URL = "jdbc:oracle:thin:@localhost:1521:quality";
	// protected static final String USERNAME = "bt";
	// protected static final String PASSWORD = "bt123456";
	// for MySQL
	protected static String		JDBC_DRIVER	= "com.mysql.jdbc.Driver";
	protected static String		JDBC_URL	= "jdbc:mysql://localhost:3306/bt_uniec";
	protected static String		USERNAME	= "root";
	protected static String		PASSWORD	= "jeff1234";
	// for postgresql
	// protected static final String JDBC_DRIVER = "org.postgresql.Driver";
	// protected static final String JDBC_URL = "jdbc:postgresql://localhost/bt";
	// protected static final String USERNAME = "bt";
	// protected static final String PASSWORD = "bt123456";
	protected final Properties	props		= new Properties();

	/**
	 * default constructors
	 * 
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public AbstractBatchJob() {
		this.actor = getClass().getSimpleName();
		try {
			props.load(AbstractBatchJob.class.getResourceAsStream("/quality.properties"));
			JDBC_DRIVER = props.getProperty("quality.jdbc.driver");
			JDBC_URL = props.getProperty("quality.jdbc.url");
			USERNAME = props.getProperty("quality.jdbc.username");
			PASSWORD = props.getProperty("quality.jdbc.password");
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * constructors
	 * 
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public AbstractBatchJob(String actor) {
		this.actor = actor;
		try {
			props.load(AbstractBatchJob.class.getResourceAsStream("/quality.properties"));
			JDBC_DRIVER = props.getProperty("quality.jdbc.driver");
			JDBC_URL = props.getProperty("quality.jdbc.url");
			USERNAME = props.getProperty("quality.jdbc.username");
			PASSWORD = props.getProperty("quality.jdbc.password");
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return
	 */
	public Properties getProps() {
		return props;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bluetouch.qulity.batch.BatchJob#getActor()
	 */
	public String getActor() {
		if (actor == null) {
			logger.warn("actor is null");
			return getClass().getSimpleName();
		}
		return actor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bluetouch.qulity.batch.BatchJob#run()
	 */
	public final void run() {
		logger.debug("start");
		Date start = new Date();
		execute();
		Date end = new Date();
		logger.debug("end");
		updateResult(start, end);
	}

	/**
	 * @param actor
	 * @param start
	 * @param end
	 */
	private void updateResult(Date start, Date end) {
		Connection conn = null;
		long time = (end.getTime() - start.getTime());
		logger.info("time:" + time + ",start:" + start);
		try {
			int key = 0;
			// 設定 JDBC
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
			PreparedStatement pstmt = null;
			// 取得 PK
			pstmt = conn.prepareStatement("select max(job_oid)+1 from BATCH_LOG");
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				key = rs.getInt(1);
			}
			rs.close();
			logger.debug("get key:" + key);
			// 紀錄 Log
			pstmt = conn.prepareStatement("insert into BATCH_LOG (job_oid, actor_id, job_start, job_end, job_time) values (?, ?, ?, ?, ?)");
			pstmt.setInt(1, key);
			pstmt.setString(2, getActor());
			pstmt.setTimestamp(3, new Timestamp(start.getTime()));
			pstmt.setTimestamp(4, new Timestamp(end.getTime()));
			pstmt.setLong(5, time);
			pstmt.executeUpdate();
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
