/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.bluetouch.qulity.batch.impl.JeffmaBatchJob
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bluetechnology.qulity.batch.AbstractBatchJob;

/**
 * @author jeffma
 * 批次作業範例
 */
public class JeffmaBatchJob extends AbstractBatchJob {

	/** serialVersionUID */
	private static final long serialVersionUID = -7553539527635188735L;
	/** logger */
	private Log logger = LogFactory.getLog(getClass());

	/** default constructors */
	public JeffmaBatchJob() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bluetouch.qulity.batch.AbstractBatch#execute()
	 */
	public void execute() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			logger.error("sleep interrupted", e);
		}
	}
}
