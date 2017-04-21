package com.centrixlink.sso.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.centrixlink.sso.bean.User;

@Repository
public class UserDao {

	@Autowired
	private JdbcTemplate userTemplate;

	/**
	 * 检查用户是否存在
	 */
	public Boolean checkUser(String username) {

		
		return false;

	}

	/**
	 * 获取用户信息
	 */
	public User getUser(String username) {

		StringBuffer sql = new StringBuffer();

		User user = null;

		
		return user;
	}

	
}