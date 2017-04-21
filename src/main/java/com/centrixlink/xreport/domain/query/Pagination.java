package com.centrixlink.xreport.domain.query;

import java.io.Serializable;

/**
 * 页面类
 * 
 * @author jiaqiang 2015.01.29
 */
public class Pagination implements Serializable {

	private static final long serialVersionUID = 1L;

	private int pageSize;// 页面行数
	private int currPage;// 当面页面
	private int order_id;// 排序字段
	private String orderType; // 排序类型

	public Pagination() {
		this(1, 20);
	}

	public Pagination(int currPage, int pageSize) {
		this.currPage = currPage;
		this.pageSize = pageSize;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getPageSql() {
		if (pageSize <= 0) {
			pageSize = 20;
		}
		if (currPage <= 0) {
			currPage = 1;
		}
		int from = (currPage - 1) * pageSize;
		StringBuffer sql = new StringBuffer(" LIMIT ");
		sql.append(from).append(",").append(pageSize);
		return sql.toString();
	}

	@Override
	public String toString() {
		StringBuffer info = new StringBuffer();
		info.append("[");
		info.append("Page=").append(this.currPage).append(";");
		info.append("Rows=").append(this.pageSize).append(";");
		info.append("]");
		return info.toString();
	}
}
