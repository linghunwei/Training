/* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   Module Name          : com.bluetouch.qulity.batch.BatchJob
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

/**
 * @author jeffma
 * 
 */
public interface BatchJob {
	/**
	 * @return 執行ID
	 */
	public String getActor();

	/**
	 * 批次作業實作
	 */
	public void execute();

	/**
	 * 啟動批次作業方法
	 * 
	 * @param actor
	 * @param start
	 * @param end
	 */
	public void run();
}
