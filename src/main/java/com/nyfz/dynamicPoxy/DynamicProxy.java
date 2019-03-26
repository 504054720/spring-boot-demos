package com.nyfz.dynamicPoxy;

import java.lang.reflect.Proxy;

public class DynamicProxy {

	public static void main(String[] args) {

		HelloImpl hello = new HelloImpl();
		IHello iHello = (IHello) Proxy.newProxyInstance(HelloImpl.class.getClassLoader(), HelloImpl.class.getInterfaces(), new MyInvacationHandel(hello));
		iHello.sayHello();
	}

}
