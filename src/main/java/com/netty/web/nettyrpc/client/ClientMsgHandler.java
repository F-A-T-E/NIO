package com.netty.web.nettyrpc.client;


import com.netty.web.nettyrpc.pojo.User;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class ClientMsgHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		log.debug(msg.toString());
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		User user = new User();
		user.setAge(18);
		user.setBirthday(new Date());
		user.setName("Jack");
		ctx.channel().writeAndFlush(user);
	}
}
