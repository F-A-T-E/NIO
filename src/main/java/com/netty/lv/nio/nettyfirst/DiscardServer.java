package com.netty.lv.nio.nettyfirst;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.sctp.nio.NioSctpServerChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class DiscardServer {

	private int port;

	public DiscardServer(int port){
		this.port = port;
	}
	public DiscardServer(){
		this.port = port;
	}

	public void run(int port) throws Exception{
//		BOSS 是专门处理连接的，一个端口只用一个就够了（ServerSocketChannel）
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
//		Work线程是专门处理IO事件的，一般设置为256-512之间（SocketChannel）
		EventLoopGroup workerGroup = new NioEventLoopGroup(16);

		try{
			//启动的引导类，可以让程序员配置信息
			ServerBootstrap b = new ServerBootstrap();
//			把线程组设置进去
			b.group(bossGroup,workerGroup)
//					设置IO模型，使用的是java的NIO模型
					.channel(NioServerSocketChannel.class)
//					配置channel-PPline当中的handler（编解码器）
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
//							ch.pipeline().addLast(new DiscardServerHandler());
//							ch.pipeline().addLast("aa",new SecondHandler());
						ch.pipeline().addLast("1",new InboundHandlerA());
						ch.pipeline().addLast("2",new InboundHandlerB());
						ch.pipeline().addLast("3",new OutboundHandlerA());
						ch.pipeline().addLast("4",new OutboundHandlerB());
						ch.pipeline().addLast("5",new InboundOutboundHandlerX());
						}
					});
/*
					.option(ChannelOption.SO_BACKLOG,128)
					.childOption(ChannelOption.SO_KEEPALIVE,true);
*/
//			绑定端口
			ChannelFuture f = b.bind(port).sync();
//			阻塞当前线程直到Server端关闭
			f.channel().closeFuture().sync();
		}finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws Exception {
		new DiscardServer().run(8080);
	}
}
