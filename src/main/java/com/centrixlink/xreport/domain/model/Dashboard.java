package com.centrixlink.xreport.domain.model;

import java.io.Serializable;
import java.util.List;

import com.centrixlink.xreport.domain.menu.DrillMenu;

/**
 * 仪表盘，一个仪表盘包括包括数据模型和Chart模型,查询组件
 * 
 */
public class Dashboard implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	private String dimName;
	private String exValue;
	private String dateType;
	private String modelURL;
	private DataModel dataModel;
	private ChartModel chartModel;
	private List<DrillMenu> drillMenus;
	
	//
	private int tableType;
	
	public Dashboard() {}

	public Dashboard(String id) {
		this();
		this.id = id;
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

	public String getExValue() {
		return exValue;
	}

	public void setExValue(String exValue) {
		this.exValue = exValue;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public String getModelURL() {
		return modelURL;
	}

	public void setModelURL(String modelURL) {
		this.modelURL = modelURL;
	}

	/**
	 * 添加数据模型
	 * @param dm
	 */
	public void setDataModel(DataModel dataModel) {
		dataModel.setDimName(dimName);
		dataModel.setExDimValue(exValue);
		this.dataModel = dataModel;
	}

	public DataModel getDataModel () {
		return this.dataModel;
	}

	public ChartModel getChartModel() {
		return chartModel;
	}

	public void setChartModel(ChartModel chartModel) {
		this.chartModel = chartModel;
	}
	
	public String getChartModelId () {
		return this.chartModel.getId();
	}
	
	public String getDataModelId () {
		return this.dataModel.getId();
	}
	
	public List<DrillMenu> getDrillMenus() {
		return drillMenus;
	}

	public void setDrillMenus(List<DrillMenu> drillMenus) {
		this.drillMenus = drillMenus;
	}
	
	public int getTableType() {
		return tableType;
	}
	
	public void setTableType(int tableType) {
		this.tableType = tableType;
	}

	@Override
	public String toString() {
		StringBuffer info = new StringBuffer();
		info.append("仪表盘:[");
		info.append("Name=").append(name).append(";");
		info.append("DataModel=").append(dataModel).append(";");
		info.append("ChartModel=").append(chartModel).append(";");
		info.append("]");
		return info.toString();
	}
}
