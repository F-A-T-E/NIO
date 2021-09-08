package com.netty.lv.nio.tcp;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class MyChannel {

	private SocketChannel channel;
	private EventLoop eventLoop;
	private Queue<ByteBuffer> writeQueue = new ArrayBlockingQueue<>(16);
	private PPLine ppLine;
	public MyChannel(SocketChannel channel,EventLoop eventLoop){
		 this.channel = channel;
		 this.eventLoop = eventLoop;
		 this.ppLine = new PPLine(this,eventLoop);
		 this.ppLine.addLast(new MyHandler1());
		 this.ppLine.addLast(new MyHandler2());
	}
	public void read(SelectionKey key) throws IOException {

		SocketChannel socketChannel = (SocketChannel) key.channel();
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		try{
			int readNum = socketChannel.read(buffer);
			if(readNum == -1){
				System.out.println("读到-1表示结束 关闭socket");
				key.channel();
				socketChannel.close();
				return;
			}
			buffer.flip();
			this.ppLine.headCtx.fireChannelRead(buffer);


		} catch (IOException e) {
			System.out.println("读取时发生异常 关闭socket");
			key.channel();
			socketChannel.close();
		}

	}

	public void write(SelectionKey key) throws IOException {
		ByteBuffer buffer;
		while((buffer = writeQueue.poll()) != null){
			channel.write(buffer);
		}
	/*	SocketChannel socketChannel = (SocketChannel) key.channel();
		byte[] bytes = (byte[]) key.attachment();
		key.attach(null);
		System.out.println("可写事件发生 写入消息" + bytes);

		if(bytes != null){
			channel.write(ByteBuffer.wrap(bytes))
		}*/
		key.interestOps(SelectionKey.OP_READ);
	}
	public void doWrite(Object msg) {
		this.ppLine.tailCtx.write(msg);
	}
	public void addWriteQueue(ByteBuffer buffer){
		writeQueue.add(buffer);
	}

	public void flush(){
		this.ppLine.tailCtx.flush();
	}

}
