package com.centrixlink.xreport.domain.menu;

import java.io.Serializable;

public class DrillMenu implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;// Menu Name
	private String url;// Menu URL
	private String title;// Menu title

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
