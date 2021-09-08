package com.netty.web.nettyrpc.pojo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

public class RpcServiceHandler implements InvocationHandler {
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("invoke"+method.getName() + Arrays.toString(args));
		return new User();
	}
}
