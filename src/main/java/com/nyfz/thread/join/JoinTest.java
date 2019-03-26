package com.nyfz.thread.join;

public class JoinTest {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		Thread threadOne = new Thread(new ThreadOne());
		Thread threadTwo = new Thread(new ThreadOne());
		threadOne.start();
		threadTwo.start();
		
		threadOne.join();
		threadTwo.join();
		
		System.out.println("main ===");
		

	}
	private static class ThreadOne implements Runnable{

		@Override
		public void run() {
			System.out.println(Thread.currentThread().getName());
			
		}}

}
