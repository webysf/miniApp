package com.miniapp.service.back;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miniapp.common.redisCon.RedisUtil;
import com.miniapp.dao.back.BackUserDao;
import com.miniapp.entity.back.User;

import reactor.core.publisher.Flux;


@Service
public class BackUserService {
	
	@Autowired
	private BackUserDao dao;
	
	@Autowired
	private RedisUtil redisUtil;
	
	// 添加后台用户
	public int addUser(User user) {
		
		int result = dao.insertUser(user);
		if (1 == result) {
			//redisUtil.Put("back_user." + user.getEmployId(),user);
			redisUtil.hashPut("back_user", user.getEmployId(), user);
		}
		return result;
	}
	
	// 查询所有用户
	public Flux<User> findAll() {
		
		List<User> listCache = redisUtil.hashGetAll("back_user");
		if (listCache != null) {
			return Flux.fromIterable(listCache);
		}
		return Flux.fromIterable(dao.selectAll());
	}
	
	// 修改用户密码
	public int modifyPassword(User user) {
		return dao.updatePassword(user);
	}
}	
