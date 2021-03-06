package com.netty.web.nettyrpc.client;

import com.netty.web.nettyrpc.pojo.RpcService;
import com.netty.web.nettyrpc.pojo.RpcServiceHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.lang.reflect.Proxy;

public class RpcClientFactory {

	private ClientMsgHandler handler = new ClientMsgHandler();


	public static void main(String[] args) {
		RpcService rpcService = getRpcService();
		rpcService.rpcLogin("admin","123456");

	}
	public  RpcClientFactory(String ip, int port) throws InterruptedException {
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
			Bootstrap b = new Bootstrap();
			b.group(bossGroup)
					.channel(NioSocketChannel.class)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) {
							ch.pipeline().addLast("ObjectEncoder", new ObjectEncoder());
							ch.pipeline().addLast("ObjectEncoder", new ObjectDecoder(Integer.MAX_VALUE,
									ClassResolvers.weakCachingResolver(null)));
							ch.pipeline().addLast("handler", new ClientMsgHandler());
						}
					});
			b.connect(ip, port).sync();
	}

	public static RpcService getRpcService(){
		return (RpcService) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
				new Class[]{RpcService.class},new RpcServiceHandler());
	}

}
