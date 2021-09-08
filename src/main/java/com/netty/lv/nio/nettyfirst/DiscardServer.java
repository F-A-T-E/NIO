package com.netty.lv.nio.nettyfirst;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.sctp.nio.NioSctpServerChannel;
import io.netty.channel.socket.SocketChannel;

public class DiscardServer {

	private int port;

	public DiscardServer(int port){
		this.port = port;
	}
	public DiscardServer(){
		this.port = port;
	}

	public void run(int port) throws Exception{
//		BOSS 是专门处理连接的，一个端口只用一个就够了
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
//		Work线程是专门处理IO事件的，一般设置为256-512之间
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try{
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup,workerGroup)
					.channel(NioSctpServerChannel.class)
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast(new DiscardServerHandler());
						}
					});
/*
					.option(ChannelOption.SO_BACKLOG,128)
					.childOption(ChannelOption.SO_KEEPALIVE,true);
*/
			ChannelFuture f = b.bind(port).sync();
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
