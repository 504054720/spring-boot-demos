package com.nyfz.thread.waitAndSleep;

public class ObjectLock {
	public static void main(String[] args) throws InterruptedException {
		new Thread(new Thread1(), "thread1").start();
		
		// Thread.sleep(3000);
		
		new Thread(new Thread2()).start();
	}
	private static class Thread1 implements Runnable{

		@Override
		public void run() {
			synchronized (ObjectLock.class) {
				System.out.println("this is thread1");
				System.out.println("thread1 is waiting");
				try {
					ObjectLock.class.wait();
					// Thread.sleep(3000);
					System.out.println("thread1 after wait");
					System.out.println("thread1 is going on");
					System.out.println("thread1 is over");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
	}
	private static class Thread2 implements Runnable{

		@Override
		public void run() {
			synchronized (ObjectLock.class) {
				System.out.println("this is thread2");
				System.out.println("notify thread1...");
				ObjectLock.class.notify();
				
				try {
					System.out.println("thread2 into sleep ...");
					Thread.sleep(3000);
					System.out.println("thread2 is going on");
					System.out.println("thread2 is over");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
	}

}
