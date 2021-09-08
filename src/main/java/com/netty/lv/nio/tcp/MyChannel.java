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
	public MyChannel(SocketChannel channel,EventLoop eventLoop){
		 this.channel = channel;
		 this.eventLoop = eventLoop;
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
			byte[] bytes = new byte[readNum];
			buffer.get(bytes,0,readNum);
			String clientData = new String(bytes);
			System.out.println(clientData);
//          加入写缓冲区
			writeQueue.add(ByteBuffer.wrap("hello".getBytes()));

//			key.attach("hello client".getBytes());
			if(clientData.equals("flush")){
//				把Key关注的事件切换为OP_WRITE
				key.interestOps(SelectionKey.OP_WRITE);
			}

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
}
