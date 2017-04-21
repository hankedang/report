package com.centrixlink.xreport.domain.model;

import java.io.Serializable;

/**
 * 封装域
 * 
 */
public class Column implements Serializable, Comparable<Column> {

	private static final long serialVersionUID = 1L;

	private String name;// 列名称
	private String title;// 列业务名
	private int dim;
	private boolean flag;// 列显示标识
	private String dateType;// 列数据类型
	private String formula;// 列计算公式
	private String paras;// 列公式包含的参数
	private boolean sortable;// 是否可排序
	private int code;// 列顺序
	private int level;// 列等级

	public Column () {}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getDim() {
		return dim;
	}

	public void setDim(int dim) {
		this.dim = dim;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}
	
	public String getParas() {
		return paras;
	}

	public void setParas(String paras) {
		this.paras = paras;
	}

	public boolean isSortable() {
		return sortable;
	}

	public void setSortable(boolean sortable) {
		this.sortable = sortable;
	}

	public boolean equals (Column column) {
		if (name.equals(column.getName())) {
			return true;
		}
		return false;
	}

	/**
	 * Have The Same Level But Not itself
	 */
	public boolean eqLevel(Column column) {
		if (equals (column)) {
			return false;
		} else if (column.getLevel() == level) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int compareTo(Column column) {
		int level = column.getLevel();
		if (this.level == level) {
			int code = column.getCode();
			return this.code - code;
		} else {
			return this.level - level;
		}
	}
}
