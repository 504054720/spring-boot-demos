package com.nyfz.thread;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
/**
 * 第三种：实现Callable，把实例交给FutureTask，再创建线程时把FutureTask交给Thread,只能创建单线程
 */
public class ThridThread implements Callable<Object>{
	private int i;
	@Override
	public Object call() throws Exception {
		for(;i<100; i++){
			System.out.println(Thread.currentThread().getName()+"__"+i);
		}
		return "call_method";
	}
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ThridThread tt = new ThridThread();
		FutureTask<Object> futureTask = new FutureTask<Object>(tt);
		System.out.println(Thread.currentThread().getName());
		new Thread(futureTask, "newThread:").start();
		String result = (String) futureTask.get();
		System.out.println("result:"+result);
	}
}
