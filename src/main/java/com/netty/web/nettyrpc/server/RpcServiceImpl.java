package com.netty.web.nettyrpc.server;

import com.netty.web.nettyrpc.pojo.RpcService;
import com.netty.web.nettyrpc.pojo.User;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class RpcServiceImpl implements RpcService {
	@Override
	public User rpcLogin(String username, String password) {
		log.debug(username + "---" + password);
		User user = new User();
		user.setAge(18);
		user.setBirthday(new Date());
		user.setName("Jack");
		return user;
	}
}
