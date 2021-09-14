package com.netty.web.nettyhttp;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import javax.swing.text.AbstractDocument;
import static io.netty.handler.codec.http.HttpHeaderNames.*;
import static io.netty.handler.codec.http.HttpHeaderValues.CLOSE;
import static io.netty.handler.codec.http.HttpHeaderValues.TEXT_PLAIN;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;

public class HttpHelloWorldServerHandler extends SimpleChannelInboundHandler<HttpObject> {
	private static final byte[] CONTENT = {'H','e','l','l','o',' ','W','o','r','l','d'};
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, HttpObject httpObject) throws Exception {

		if(httpObject instanceof HttpRequest){
//		强制类型转换
			FullHttpRequest req = (FullHttpRequest) httpObject;
			req.content();

			boolean keepAlived = HttpUtil.isKeepAlive(req);
			FullHttpResponse response = new DefaultFullHttpResponse(req.protocolVersion(),OK,
					Unpooled.wrappedBuffer(CONTENT));
			response.headers()
					.set(CONTENT_TYPE,TEXT_PLAIN)
					.setInt(CONTENT_LENGTH,response.content().readableBytes());
			if (keepAlived){
				if(!req.protocolVersion().isKeepAliveDefault()){
					response.headers().set(CONNECTION,KEEP_ALIVE);
				}
			}else {
				response.headers().set(CONNECTION,CLOSE);
			}
			ChannelFuture f = ctx.writeAndFlush(response);
			if(!keepAlived){
				f.addListener(ChannelFutureListener.CLOSE);
			}
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
