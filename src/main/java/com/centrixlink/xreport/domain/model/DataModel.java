package com.centrixlink.xreport.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据模型,封装事实表数据
 * 
 */
public class DataModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String dimName;
	private String exDimValue;
	private String orderName;
	private String orderType;
	private Map<String, Column> columnMap;
	
	// 模型存储表的类型
	private int modelType;
	// 模型名称
	private String modelName; 
	
	
	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public int getModelType() {
		return modelType;
	}

	public void setModelType(int modelType) {
		this.modelType = modelType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public DataModel() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDimName() {
		return dimName;
	}

	public void setDimName(String dimName) {
		this.dimName = dimName;
	}

	public String getExDimValue() {
		return exDimValue;
	}

	public void setExDimValue(String exDimValue) {
		this.exDimValue = exDimValue;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public Map<String, Column> getColumnMap() {
		return columnMap;
	}

	public void setColumnMap(Map<String, Column> columnMap) {
		this.columnMap = columnMap;
	}

	public Column getColumn(String name) {
		if (name == null) {
			return columnMap.get(dimName);
		}
		return columnMap.get(name);
	}

	public void addColumn(Column column) {
		if (columnMap == null) {
			columnMap = new LinkedHashMap<>();
		}
		String key = column.getName();
		columnMap.put(key, column);
	}

	public List<Column> getColumnList(int type) {
		List<Column> columnList = new ArrayList<>();
		for (Column column : columnMap.values()) {
			if (type == column.getDim()) {
				if (dimName.equals(column.getName())) {
					column.setFlag(true);
				}
				columnList.add(column);
			}
		}
		Collections.sort(columnList);
		return columnList;
	}

	public List<Column> getCustomDims(List<String> dimList) {
		List<Column> columns = new ArrayList<Column>();
		if (dimList == null || dimList.isEmpty()) {
			columns.add(columnMap.get(dimName));
		} else {
			for (String name : dimList) {
				columns.add(columnMap.get(name));
			}
		}
		return columns;
	}

	public List<Column> getCustomIdxes(List<String> idxList) {
		if (idxList == null || idxList.isEmpty()) {
			return getVisibleColumns();
		}
		List<Column> columns = new ArrayList<Column>();
		for (String name : idxList) {
			columns.add(columnMap.get(name));
		}
		return columns;
	}

	/**
	 * Get Default Indicator
	 */
	public List<Column> getVisibleColumns() {
		List<Column> columns = new ArrayList<>();
		for (Column column : columnMap.values()) {
			if (column.isFlag() && 0 == column.getDim()) {
				columns.add(column);
			}
		}
		return columns;
	}
}
