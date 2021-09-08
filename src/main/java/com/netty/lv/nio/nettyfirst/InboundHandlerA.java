package com.netty.lv.nio.nettyfirst;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InboundHandlerA extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		log.debug("channelRead->" + msg.toString());
		super.channelRead(ctx, msg);
		ctx.channel().writeAndFlush(ctx.alloc().buffer(16).writeBytes("hello".getBytes()));
	}
}
