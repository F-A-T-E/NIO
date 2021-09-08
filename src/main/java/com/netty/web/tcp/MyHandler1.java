package com.netty.web.tcp;

import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;

@Slf4j
public class MyHandler1 implements Handler{
	@Override
	public void channelRead(HandlerContext ctx, Object msg) {
		log.debug(msg.toString());
//		解码
		ByteBuffer buffer = (ByteBuffer) msg;
		int l = buffer.limit();
		byte[] content = new byte[1];
		buffer.get(content);
		String str = new String(content);
		ctx.fireChannelRead(str);

//		释放逻辑
		buffer.clear();
//
	}

	@Override
	public void write(HandlerContext ctx, Object msg) {
		log.debug(msg.toString());
		ctx.write(msg);
	}

	@Override
	public void flush(HandlerContext ctx) {
		log.debug("flush");
	}
}
