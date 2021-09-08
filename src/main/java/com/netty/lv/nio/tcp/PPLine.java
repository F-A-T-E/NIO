package com.netty.lv.nio.tcp;

import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;

@Slf4j
public class PPLine {

	private MyChannel myChannel;
	private EventLoop eventLoop;

	public HandlerContext headCtx;
	public HandlerContext tailCtx;

	public PPLine(MyChannel myChannel, EventLoop eventLoop) {
		this.myChannel = myChannel;
		this.eventLoop = eventLoop;
		PPHandler handler = new PPHandler();
		this.headCtx = new HandlerContext(handler,myChannel);
		this.tailCtx = new HandlerContext(handler,myChannel);
//		构建链表
		this.headCtx.next = this.tailCtx;
		this.tailCtx.prev = this.headCtx;
	}

	public void addLast(Handler handler){
		HandlerContext context = new HandlerContext(handler,myChannel);
		HandlerContext p = this.tailCtx.prev;
		p.next = context;
		context.prev = p;
		context.next = tailCtx;
		tailCtx.prev = context;
	}


	public class PPHandler implements Handler{

		@Override
		public void channelRead(HandlerContext ctx, Object msg) {
//			log.debug(msg.toString());
			System.out.println("最后的handler Tail释放资源" + msg);
		}

		@Override
		public void write(HandlerContext ctx, Object msg) {
			log.debug(msg.toString());
			if(!(msg instanceof ByteBuffer)){
				throw new RuntimeException("类型错误" + msg.getClass());
			}

//			PPLine.this.myChannel
		}

		@Override
		public void flush(HandlerContext ctx) {
			log.debug("flush");
		}
	}

}
