package com.netty.web.tcp;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingDeque;

public class EventLoop implements Runnable{

	private Selector selector;
	private Thread thread;
	private Queue<Runnable> taskQueue = new LinkedBlockingDeque<>(32);

	public EventLoop() throws IOException {
		this.selector = SelectorProvider.provider().openSelector();
		this.thread = new Thread(this);
		this.thread.start();
	}

	/**
	 * 将channel注册到事件查询器上
	 * @param channel
	 * @param keyOps
	 */
	public void register(SocketChannel channel,int keyOps) throws ClosedChannelException {
		//把注册的逻辑封装成一个任务
		taskQueue.add(()->{
			try {
				MyChannel myChannel = new MyChannel(channel,this);
				SelectionKey selectionKey = channel.register(selector,keyOps);
				selectionKey.attach(myChannel);
			} catch (ClosedChannelException e) {
				e.printStackTrace();
			}
		});
		//唤起selector上的阻塞的线程
		selector.wakeup();
	}

	@Override
	public void run() {
		while(!Thread.interrupted()){
			try {
				System.out.println(thread + "开始查询IO事件...");
				int eventNum = selector.select();
				System.out.println("系统发生IO事件 数量->" + eventNum);
				Set<SelectionKey> keySet = selector.selectedKeys();
				Iterator<SelectionKey> iterable = keySet.iterator();
				while(iterable.hasNext()){
					SelectionKey key = iterable.next();
					iterable.remove();
//					可读事件
					MyChannel myChannel =  (MyChannel) key.attachment();
					if(key.isReadable()){
						myChannel.read(key);
					}
//					可写事件
					if (key.isWritable()) {
						myChannel.write(key);
					}
				}
				taskQueue.poll();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
