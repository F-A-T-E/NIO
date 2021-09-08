package com.netty.web.tcp;

public interface Handler {

	 void channelRead(HandlerContext ctx, Object msg);

	 void write(HandlerContext ctx, Object msg);

	 void flush(HandlerContext ctx);
}
