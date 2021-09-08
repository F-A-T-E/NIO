package com.netty.lv.nio.tcp;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyHandler1 implements Handler{
	@Override
	public void channelRead(HandlerContext ctx, Object msg) {
		log.debug(msg.toString());
	}

	@Override
	public void write(HandlerContext ctx, Object msg) {
		log.debug(msg.toString());
	}

	@Override
	public void flush(HandlerContext ctx) {
		log.debug("flush");
	}
}
