package com.netty.web.nettypool;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;

public class PoolTest1 {

	public static void main(String[] args) {
		PooledByteBufAllocator allocator = PooledByteBufAllocator.DEFAULT;
		ByteBuf byteBuf = allocator.heapBuffer(8192);
		byteBuf.release();

	}
}
