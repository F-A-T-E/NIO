package com.netty.web.nettyrpc.nettyws;

import com.netty.web.nettyhttp.HttpHelloWorldServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolConfig;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

public class NettyWsServer {

	public void run(int port) throws Exception{
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup(16);
		try{
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup,workerGroup)
					.channel(NioServerSocketChannel.class)
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch) {
							ch.pipeline().addLast("http-decoder",new HttpRequestDecoder());
							ch.pipeline().addLast("http-encoder",new HttpResponseEncoder());
							ch.pipeline().addLast("aggregator",new HttpObjectAggregator(655360));

//							处理websocket的编解码器
							WebSocketServerProtocolConfig wsConfig = WebSocketServerProtocolConfig.newBuilder()
									.websocketPath("/websocket")
									.maxFramePayloadLength(Integer.MAX_VALUE)
									.checkStartsWith(true).build();
							ch.pipeline().addLast("webSocketHandler",new WebSocketServerProtocolHandler(wsConfig));
							ch.pipeline().addLast("WsTextHandler",new WsTextHandler());
						}
					});
			ChannelFuture f = b.bind(port).sync();
			f.channel().closeFuture().sync();
		}finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws Exception {
		new NettyWsServer().run(8080);
	}

}
