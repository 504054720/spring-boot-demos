package com.nyfz.thread;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
/**
 * Callable多线程方式，不共享实例变量
 */
public class CallableFuture implements Callable{
	private int i;
	@Override
	public Object call() throws Exception {
		for(; i<10; i++){
			System.out.println(Thread.currentThread().getName()+"__"+i);
		}
		return "callMethodResult";
	}
	public static void main(String[] args) throws Exception, ExecutionException {
		CallableFuture fu2 = new CallableFuture();
		CallableFuture fu3 = new CallableFuture();
		CallableFuture fu4 = new CallableFuture();
		ExecutorService executorService = Executors.newFixedThreadPool(3);
		Future<Object> future2 = executorService.submit(fu2);
		Future<Object> future3 = executorService.submit(fu3);
		Future<Object> future4 = executorService.submit(fu4);
		System.out.println(Thread.currentThread().getName()+"result:"+future4.get());
		executorService.shutdownNow();
	}



}
