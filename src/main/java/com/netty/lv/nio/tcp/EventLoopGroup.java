package com.netty.lv.nio.tcp;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SocketChannel;
import java.util.concurrent.atomic.AtomicInteger;

public class EventLoopGroup {

	private EventLoop[] eventLoops = new EventLoop[16];

	private final AtomicInteger idx = new AtomicInteger(0);

	public EventLoopGroup() throws IOException {
		for (int i = 0; i < eventLoops.length; i++) {
			eventLoops[i] = new EventLoop();
		}
	}

	public EventLoop next(){
		//轮询算法
		return eventLoops[idx.getAndIncrement() & eventLoops.length - 1];
	}

	public void register(SocketChannel channel, int keyOps) throws ClosedChannelException {
		next().register(channel, keyOps);


	}
}
