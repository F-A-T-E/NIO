package com.netty.lv.nio.tcp;

public class HandlerContext {

	private Handler handler;
	private MyChannel myChannel;

	HandlerContext prev;
	HandlerContext next;

	public HandlerContext(Handler handler, MyChannel myChannel) {
		this.handler = handler;
		this.myChannel = myChannel;
	}

	public void fireChannelRead(Object msg){
		HandlerContext n = this.next;
		if(n != null){
			n.handler.channelRead(n,msg);
		}
	}
	public void write(Object msg){
		HandlerContext p = this.prev;
		if (p != null){
			p.handler.write(p,msg);
		}
	}

	public void flush(){
		HandlerContext p = this.prev;
		if(p != null){
			p.handler.flush(p);
		}
	}







}
