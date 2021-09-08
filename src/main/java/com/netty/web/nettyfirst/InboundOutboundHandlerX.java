package com.netty.web.nettyfirst;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InboundOutboundHandlerX extends ChannelDuplexHandler {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		log.debug(msg.toString());
		super.channelRead(ctx, msg);
		ctx.channel().writeAndFlush( ctx.alloc().buffer(16).writeBytes("hello".getBytes()));
	}

	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		log.debug("write->" + msg.toString());
		super.write(ctx, msg, promise);
	}
}
