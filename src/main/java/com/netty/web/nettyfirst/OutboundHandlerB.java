package com.netty.web.nettyfirst;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OutboundHandlerB extends ChannelOutboundHandlerAdapter {

	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		log.debug("write->" + msg.toString());
		super.write(ctx, msg, promise);

		ctx.writeAndFlush(ctx.alloc().buffer(16).writeBytes("hello".getBytes()));

	}
}
