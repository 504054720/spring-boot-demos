package com.nyfz.dynamicPoxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;


public class MyInvacationHandel implements InvocationHandler{
	private Object target;
	
	public MyInvacationHandel(Object target){
		this.target = target;
	}

	@Override
	public Object invoke(Object arg0, Method method, Object[] arg2)
			throws Throwable {
		System.out.println("invoke method");
		System.out.println("ObjectName:"+arg0.getClass().getName());
		System.out.println("methodName:"+method.getName());
		Object result = method.invoke(target, arg2);
		System.out.println("after");
		return result;
	}

}
