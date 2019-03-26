package com.nyfz.thread;

/**
 * 第一种：继承Thread 类，重写run方法；每次创建线程时都会NEW一个对象，所以不会共享i实例这个变量，
 * 通过继承Thread类创建线程时，多个线程之间不共享实例变量
 * @author Administrator
 */
public class FirstThread extends Thread{
	private int i;
	public void run(){
		for(i = 0; i<100; i++){
		System.out.println(getName()+"__"+i);	
		}
	}
	public static void main(String[] args) {
		for(int i=0; i<100;i++){
			//获得当前线程：主线程
			System.out.println("主线程："+Thread.currentThread().getName());
			if(i == 20){
				new FirstThread().start();// 启动第一个线程
				new FirstThread().start();// 启动第二个线程
			}
		}
	}
}
