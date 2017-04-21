package com.centrixlink.xreport.domain.menu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单类
 * 
 * @author jiaqiang 2015.01.08
 */
public class Menu implements Serializable {

	private static final long serialVersionUID = 6401042065040003502L;

	// 菜单名单
	private String name;
	// 菜单请求action url
	private String url;
	// 子菜单集
	private List<Menu> subMenuList = new ArrayList<Menu>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<Menu> getSubMenuList() {
		return subMenuList;
	}

	public void addSubMenu(Menu menu) {
		subMenuList.add(menu);
	}

	public String toString() {
		StringBuffer info = new StringBuffer();
		
		info.append("[");
		info.append("Name=").append(name).append(";");
		info.append("URL=").append(url).append(";");
		info.append("]");
		
		return info.toString();
	}
}
