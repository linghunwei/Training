
package com.bluetechnology.qulity.batch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bluetechnology.qulity.batch.impl.JeffmaBatchJob;

/**
 * @author jeffma
 */
public class RunBatchJob {
	/** logger */
	private static Log	logger	= LogFactory.getLog(RunBatchJob.class);

	/** default constructors */
	public RunBatchJob() {
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BatchJob batch = null;
		args = new String[] { "com.bluetechnology.qulity.batch.impl.ImportDatabaseBatchJob" };
		if (args != null && args.length >= 1) {
			try {
				Object obj = Class.forName(args[0]).newInstance();
				if (obj instanceof BatchJob) {
					batch = (BatchJob) obj;
				}
				else {
					logger.error("not BatchJob, class:" + args[0]);
					return;
				}
			}
			catch (Exception e) {
				logger.error("args:" + args, e);
				return;
			}
		}
		else {
			// TODO : 請將 JeffmaBatchJob 替換為你自己實作的 BatchJob 程式
			batch = new JeffmaBatchJob();
		}
		batch.run();
	}
}
