package com.app.scheduleThread;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.app.controller.UserController;

/**
 * creating the thread by implementing {@Runable} interface
 * 
 * @author Shiva Narune
 * @version 1.0
 * @since 23-10-2018
 */

@Component
@Scope("prototype")
public class MyThread implements Runnable {

	final static Logger logger = Logger.getLogger(MyThread.class);
	@Autowired
	UserController uc;

	/**
	 * Overrided run() method from runnable interface
	 */
	@Override
	public void run() {
		logger.info("Called from thread");

		for (int i = 1; i < 3; i++) {

			/**
			 * handled interrupted exception
			 */
			try {

				logger.info("thread id ***********************@@@@@@@@@@@@@@@ {}" + Thread.currentThread().getId());
			
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				logger.error("InterruptedException " + e);
			}
			logger.info("Hey, i am sleeping for 5 sec");
		}

	}

}