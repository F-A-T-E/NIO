package com.netty.lv.nio.tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Set;

public class NioServer {

	public static void main(String[] args) throws IOException {
		//创建一个ServerSocket
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.bind(new InetSocketAddress(8080));
		//设置为非阻塞模式
		serverSocketChannel.configureBlocking(false);

		//创建一个时间查询器
		Selector selector = SelectorProvider.provider().openSelector();;

		//把ServerSocketChannel注册到事件查询器上，并且关注OP_ACCEPT事件
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		//创建一组事件查询器
		EventLoopGroup eventLoopGroup = new EventLoopGroup();

		while (true){
			//阻塞方法，等待系统有IO事件发生
			int eventNum = selector.select();
			System.out.println("系统发生了io事件  数量 -》" + eventNum);

			Set<SelectionKey> keySet = selector.selectedKeys();
			Iterator<SelectionKey> iterable=  keySet.iterator();

			while(iterable.hasNext()){
				SelectionKey key = iterable.next();
				iterable.remove();

				//连接事件
				if (key.isAcceptable()) {
					//接受客户端的连接，一个socketChannel代表了一个tcp的连接
					ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
					SocketChannel socketChannel = ssc.accept();
					//把SocketChannel设置为异步模式
					socketChannel.configureBlocking(false);
					System.out.println("服务器接收了一个新的连接" + socketChannel.getRemoteAddress());
					//把socketChannel注册到事件查询器上，并且关注OP_READ事件
//					socketChannel.register(selector, SelectionKey.OP_READ);
					eventLoopGroup.register(socketChannel,SelectionKey.OP_READ);
				}
				//可读事件
				if(key.isReadable()){
					SocketChannel socketChannel =  (SocketChannel) key.channel();
					ByteBuffer buffer = ByteBuffer.allocate(1024);
					try {
						int readNum = socketChannel.read(buffer);
						if(readNum == -1){
							System.out.println("读取到-1时表示结束  关闭socket");
							key.channel();
							socketChannel.close();
							break;
						}
						buffer.flip();
						byte[] bytes = new byte[readNum];
						System.out.println(buffer.get(bytes,0,readNum));
						System.out.println(new String(bytes));

						//把key关注的事件切换为 byte[] response = "client hello".getBytes();
						//把key关注的事件切换为OP_WRITE
						key.interestOps(SelectionKey.OP_WRITE);
						byte[] response = "client hello".getBytes();
						buffer.clear();
						buffer.put(response);
						buffer.flip();
						socketChannel.write(buffer);
					} catch (IOException e) {
						System.out.println("读取时发生异常  关闭socket");
						key.channel();
						socketChannel.close();
					}
				}
				//可写事件
				if(key.isWritable()){
					SocketChannel socketChannel = (SocketChannel) key.channel();
					byte[] bytes = (byte[]) key.attachment();
					key.attach(null);
					System.out.println("可写事件发生 写入消息" + bytes);
					if(bytes != null){
						socketChannel.write(ByteBuffer.wrap(bytes));
					}
					//把key关注的事件切换为OP_READ
					key.interestOps(SelectionKey.OP_READ);
				}
			}
		}
	}
}
