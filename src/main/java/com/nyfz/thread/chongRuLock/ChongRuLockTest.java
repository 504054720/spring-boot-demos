package com.nyfz.thread.chongRuLock;

public class ChongRuLockTest {

	public static void main(String[] args) {
		new LockObject().test2();

	}
	private static class LockObject{
		public synchronized void test1(){
			System.out.println("this is test1");
		}
		public synchronized void test2(){
			// 1、进入该方法获取到this对象的锁
			System.out.println("begin test2");
			// 2、调用test1()需要再次获得this对象的锁，如果没有可重入机制，
			// this锁没有被释放时再次请求锁，则会产生死锁。
			test1();
			System.out.println("end test2");
		}
	}

}
