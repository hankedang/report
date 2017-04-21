package com.centrixlink.xreport.domain.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryFilter {

	private String id;
	private String text;
	private String iconCls;
	private List<QueryFilter> children;
	private Map<String, Object> attributes;

	public QueryFilter () {}
	
	public QueryFilter (String text) {
		this.text = text;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public List<QueryFilter> getChildren() {
		return children;
	}

	public void addChild(QueryFilter child) {
		if (null == children) {
			children = new ArrayList<>();
		}
		children.add(child);
	}
	
	public void setChildren(List<QueryFilter> children) {
		this.children = children;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}
	
	public void addAttribute (String name, Object value) {
		if (null == this.attributes) {
			this.attributes = new HashMap<String, Object>();
		}
		this.attributes.put(name, value);
	}
	
	public boolean empty () {
		if (null == this.children) {
			return true;
		}
		return this.children.isEmpty();
	}
}
