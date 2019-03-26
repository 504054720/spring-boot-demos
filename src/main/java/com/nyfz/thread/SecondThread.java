package com.nyfz.thread;

/**
 * 第二种：通过实现Runnable接口 实现run方法，因为是new的一个对象，所以此实例中的变量在多个线程中共享
 */
public class SecondThread implements Runnable{
	private int i;
	@Override
	public void run() {
		for(;i<10; i++){
			System.out.println(Thread.currentThread().getName()+"__"+i);
		}
	}
	public static void main(String[] args) {
		SecondThread st = new SecondThread();
		//SecondThread st2 = new SecondThread();
		new Thread(st, "线程1").start();
		new Thread(st, "线程2").start();
	}
}
