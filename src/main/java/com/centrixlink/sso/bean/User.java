package com.centrixlink.sso.bean;

import java.io.Serializable;

/**
 * 用户对象
 */
public class User implements Serializable {

	private static final long serialVersionUID = -5320257834723733881L;

	private Long userId;
	private String userType;
	private String loginName;
	private String displayName;

	public User() {
		super();
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
}
