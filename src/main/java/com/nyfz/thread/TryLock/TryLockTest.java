package com.nyfz.thread.TryLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TryLockTest {
	Lock lock = new ReentrantLock();

	public static void main(String[] args) {
		TryLockTest tt = new TryLockTest();
		
		new Thread(){
			public void run(){
				tt.threadRun(Thread.currentThread());
			}
		}.start();
		
		new Thread(){
			public void run(){
				tt.threadRun(Thread.currentThread());
			}
		}.start();
	}
	public void threadRun(Thread t){
		try {
			if(lock.tryLock(3, TimeUnit.SECONDS)){
				try {
					System.out.println(t.getName()+"获取到了锁！");
					Thread.sleep(4000);
				} catch (Exception e) {
					System.out.println("execption!");
				}finally{
					System.out.println(t.getName()+"finally unlock!");
					lock.unlock();
				}
			}else{
				System.out.println(t.getName()+"没有获取到了锁！");
			}
		} catch (InterruptedException e) {
			System.out.println("execption");
		}
		
	}


}
