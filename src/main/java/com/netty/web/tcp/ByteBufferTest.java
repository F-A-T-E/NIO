package com.netty.web.tcp;

import java.nio.ByteBuffer;

/**
 *
 * HeapBuffer 堆内内存 内部使用byte[]存取数据
 * DirectBuffer 堆外内存 内部使用Unsafe工具类存取数据
 *  底层实现原理： position -- 写入地址   、
 *              capacity  --
 *              flip方法   将 （1）limit设置为当前position   （2）将position设置为0
 *
 */
public class ByteBufferTest {
	public static void main(String[] args) {
		ByteBuffer buffer = ByteBuffer.allocate(16);

		buffer.put((byte) 23);
		buffer.putInt(888477777);
		buffer.flip();
		System.out.println(buffer.get());
		System.out.println(buffer.getInt());

		ByteBuffer directBuffer = ByteBuffer.allocateDirect(16);
		directBuffer.put((byte) 23);
		directBuffer.get();
	}
}
