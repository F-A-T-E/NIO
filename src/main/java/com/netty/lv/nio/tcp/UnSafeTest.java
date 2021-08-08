package com.netty.lv.nio.tcp;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnSafeTest {
	public static void main(String[] args) {
		//拿到堆外内存管理工具
		Unsafe unsafe = getUnsafe();
		//分配10个字节的内存，返回值为内存基础地址
		long addess = unsafe.allocateMemory(10);

		//传入基础地址，长度为10，byte-0，初始化堆外内存
		unsafe.setMemory(addess,10L,(byte) 0);

		//传入内存地址位置设置为byte值\
		unsafe.putByte(addess,(byte)1);
		unsafe.putByte(addess +1 ,(byte)2);
		unsafe.putByte(addess + 2,(byte)3);

		//根据内存地址获取byte值
		System.out.println(unsafe.getByte(addess));
		System.out.println(unsafe.getByte(addess+1));
		System.out.println(unsafe.getByte(addess+2));
		System.out.println(unsafe.getByte(addess+3));


		//释放内存
		unsafe.freeMemory(addess);
	}

	//反射拿到Unsafe对象
	private static Unsafe getUnsafe(){
		try{
			Field field = Unsafe.class.getDeclaredField("theUnsafe");
			field.setAccessible(true);
			return (Unsafe) field.get(null);
		}catch (Exception e){
			return null;
		}
	}
}
