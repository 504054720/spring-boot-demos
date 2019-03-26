package com.nyfz.singleton;

public class SingletonTest {
	private static SingletonTest singletonTest = null;
	private SingletonTest(){}
	
	private synchronized static SingletonTest getInstance(){
		if(singletonTest == null){
			singletonTest = new SingletonTest();
		}
		return singletonTest;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
