package com.netty.web.nettyrpc.client;

import com.netty.web.nettyrpc.pojo.RpcService;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class RcpClient {

	public static void main(String[] args) {
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		try{
			Bootstrap b = new Bootstrap();
			b.group(bossGroup)
					.channel(NioSocketChannel.class)
					.handler(

					);

		}catch (InterruptedException e){
			e.printStackTrace();
		}finally {

		}

	}
}
