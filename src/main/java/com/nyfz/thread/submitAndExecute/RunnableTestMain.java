package com.nyfz.thread.submitAndExecute;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class RunnableTestMain {

	public static void main(String[] args) {
		ExecutorService pool = Executors.newFixedThreadPool(2);
		
		pool.execute(new RunnableTest());
		
		Future future = pool.submit(new RunnableTest());
		try {
			
			if(future.get() == null){
				System.out.println("execute success!");
			}
		} catch (Exception e) {
			System.out.println("execption:"+e.getCause().getMessage());
		}
		try{}finally{}

	}
	private static class RunnableTest implements Runnable{

		@Override
		public void run() {
			System.out.println("run=====");
			throw new RuntimeException("runtimeException!");
		}
		
	}

}
