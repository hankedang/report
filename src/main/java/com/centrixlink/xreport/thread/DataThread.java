package com.centrixlink.xreport.thread;

import java.util.concurrent.CountDownLatch;

public class DataThread implements Runnable {

	private CountDownLatch latch;  
	
	@Override
	public void run() {
		latch.countDown();
	}
}
