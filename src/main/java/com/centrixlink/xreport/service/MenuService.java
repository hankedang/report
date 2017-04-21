package com.centrixlink.xreport.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centrixlink.sso.bean.User;
import com.centrixlink.xreport.dao.MenuDao;
import com.centrixlink.xreport.domain.menu.Menu;

/**
 * 菜单Service
 */
@Service
public class MenuService {

	@Autowired
	private MenuDao menuDao;

	/**
	 * 获取用户的菜单组
	 */
	public List<Menu> getMenuList(User user) {
		return menuDao.getMenuList(user);
	}

	public void addMenu() {
	}

	public List<Menu> getMenus() {
		return null;
	}
}
