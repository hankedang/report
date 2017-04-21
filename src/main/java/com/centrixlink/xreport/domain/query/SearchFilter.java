package com.centrixlink.xreport.domain.query;

import com.centrixlink.xreport.domain.model.Column;

public class SearchFilter implements Comparable<SearchFilter> {

	private Column column;
	private String sign;
	private String value;

	public SearchFilter() {}
	
	public SearchFilter(Column column, String value){
		this(column, "eq", value);
	}
	
	public SearchFilter (Column column, String sign, String value) {
		this.column = column;
		this.sign = sign;
		this.value = value;
	}
	
	public Column getColumn() {
		return column;
	}

	public void setColumn(Column column) {
		this.column = column;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String toSQL () {
		StringBuffer sql = new StringBuffer();
		sql.append(" AND ").append(column.getName());
		
		if (value.contains(",")) {
			// The String Value need round with ''
			sql.append(" IN ('").append(value.replaceAll(",","','")).append("')");
		} else {
			if ("inc".equals(sign)) {// 包含
				sql.append(" LIKE '%").append(value).append("%'");
			} else if ("neq".equals(sign)) {
				sql.append("<>'").append(value).append("'");
			} else if ("lte".equals(sign)) {
				sql.append("<='").append(value).append("'");
			} else if ("lt".equals(sign)) {
				sql.append("<'").append(value).append("'");
			} else if ("gte".equals(sign)) {
				sql.append(">='").append(value).append("'");
			} else if ("gt".equals(sign)) {
				sql.append(">'").append(value).append("'");
			} else {
				sql.append("='").append(value).append("'");
			}
		}
		
		return sql.toString();
	}
	
	public String havingSQL () {
		StringBuffer sql = new StringBuffer();
		sql.append(" AND ").append(column.getFormula());
		if ("eq".equals(sign)) {//
			sql.append("='").append(value).append("'");
		} else if ("lte".equals(sign)) {
			sql.append("<='").append(value).append("'");
		} else if ("lt".equals(sign)) {
			sql.append("<'").append(value).append("'");
		} else if ("gte".equals(sign)) {
			sql.append(">='").append(value).append("'");
		} else if ("gt".equals(sign)) {
			sql.append(">'").append(value).append("'");
		}
		
		return sql.toString();
	}

	@Override
	public int compareTo(SearchFilter filter) {
		String sign = filter.getSign();
		if (this.sign.equals(sign)) {
			return 0;
		} else {
			if (sign.equals("inc") || this.sign.equals("eq")) {
				return -1;
			} else if (sign.equals("eq") || this.sign.equals("inc")) {
				return 1;
			}
		}
		return 0;
	}
}
