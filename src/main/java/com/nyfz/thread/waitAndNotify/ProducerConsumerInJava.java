package com.nyfz.thread.waitAndNotify;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class ProducerConsumerInJava {

	public static void main(String[] args) {
		Queue<Integer> queue = new LinkedList<Integer>();
		int maxSize = 10;
		Thread producer = new Producer(queue, maxSize, "producer");
		Thread consumer = new Consumer(queue, maxSize, "consumer");
		producer.start();
		consumer.start();
		

	}
	private static class Producer extends Thread{
		private Queue<Integer> queue;
		private int maxSize;
		public Producer(Queue<Integer> queue, int maxSize,String name) {
			super(name);
			this.queue = queue;
			this.maxSize = maxSize;
		}
		
		@Override
		public void run() {
			while(true){
				synchronized(queue){
					System.out.println("producer:"+queue.size());
					while(queue.size() == maxSize){
						try {
							System.out.println("producer:wait..."+queue.size());
							queue.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					Random random = new Random();
					System.out.println("producer:add"+queue.size());
					queue.add(random.nextInt());
					System.out.println("producer:notifyAll"+queue.size());
					queue.notifyAll();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	static class Consumer extends Thread{
		private Queue<Integer> queue;
		private int maxSize;
		
		public Consumer(Queue<Integer> queue, int maxSize,String name) {
			super(name);
			this.queue = queue;
			this.maxSize = maxSize;
		}

		@Override
		public void run() {
			while(true){
				synchronized(queue){
					System.out.println("consumer:"+queue.size());
					while(queue.isEmpty()){
						try {
							System.out.println("consumer:wait..."+queue.size());
							queue.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					System.out.println("consumer:remove"+queue.size());
					queue.remove();
					System.out.println("consumer:notify"+queue.size());
					queue.notifyAll();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		}
	}
}
