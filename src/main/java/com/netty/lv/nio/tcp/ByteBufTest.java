package com.netty.lv.nio.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;

public class ByteBufTest {

	public static void main(String[] args) {
		ByteBuf buf = PooledByteBufAllocator.DEFAULT.heapBuffer(10);
		buf.writeByte(11);
		buf.writeByte(22);
		buf.writeByte(33);
		buf.writeByte(44);
		buf.writeByte(55);
		System.out.println(buf.readerIndex() + "|" + buf.writerIndex());
		System.out.println(buf.readByte());
		System.out.println(buf.readByte());
		System.out.println(buf.readByte());
		System.out.println(buf.readByte());
		System.out.println(buf.readerIndex() + "|" + buf.writerIndex());
		buf.writeByte(66);
		ByteBuf newbuf = buf.duplicate();
		System.out.println(newbuf.readerIndex() + "|" + newbuf.writerIndex());
		newbuf.writeByte(77);
		System.out.println(buf.readByte());
		System.out.println(buf.readByte());
		System.out.println(buf.readByte());
	}
}
