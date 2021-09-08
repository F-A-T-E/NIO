package com.netty.lv.nio.tcp;

public class PPLine {

	private MyChannel myChannel;
	private EventLoop eventLoop;

	private HandlerContext headCtx;
	private HandlerContext tailCtx;

	public PPLine(MyChannel myChannel, EventLoop eventLoop) {
		this.myChannel = myChannel;
		this.eventLoop = eventLoop;
		PPHandler handler = new PPHandler();
		this.headCtx = new HandlerContext(handler,myChannel);
		this.tailCtx = new HandlerContext(handler,myChannel);
	}
	public class PPHandler implements Handler{

		@Override
		public void channelRead(HandlerContext ctx, Object msg) {
			System.out.println("PPHandler:channelRead");
		}

		@Override
		public void write(HandlerContext ctx, Object msg) {

		}

		@Override
		public void flush(HandlerContext ctx) {

		}
	}

}
