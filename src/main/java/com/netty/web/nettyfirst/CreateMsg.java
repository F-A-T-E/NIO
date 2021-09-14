package com.netty.web.nettyfirst;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import java.nio.charset.StandardCharsets;

public class CreateMsg {

	public static void main(String[] args) {
		ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
		buffer.writeShort("HELLO WORLD".getBytes().length);
		buffer.writeBytes("HELLO,WORLD".getBytes());
		System.out.println(ByteBufUtil.hexDump(buffer));
	}
}
