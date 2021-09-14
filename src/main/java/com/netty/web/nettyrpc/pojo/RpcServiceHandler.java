package com.netty.web.nettyrpc.pojo;

import com.netty.web.nettyrpc.client.ClientMsgHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

public class RpcServiceHandler implements InvocationHandler {

	private ClientMsgHandler handler;

	public RpcServiceHandler() {
		this.handler = handler;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("invoke"+method.getName() + Arrays.toString(args));
		return new User();
	}













}
