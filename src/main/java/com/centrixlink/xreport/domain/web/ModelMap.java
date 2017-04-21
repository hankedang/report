package com.centrixlink.xreport.domain.web;

import java.util.List;

import com.centrixlink.xreport.domain.model.Column;
import com.centrixlink.xreport.domain.query.ChartData;
import com.centrixlink.xreport.domain.query.JTableData;

/**
 * @domain WebPage Data Model
 * @function
 */
public class ModelMap {

	private String msg;// Query Result. OK Or ERROR
	private List<Column> columns;// DataModel Columns
	private JTableData tableData;// Used For EasyUI Table
	private ChartData chartData;// Used For HighCharts Data

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<Column> getColumns() {
		return columns;
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}

	public JTableData getTableData() {
		return tableData;
	}

	public void setTableData(JTableData tableData) {
		this.tableData = tableData;
	}

	public ChartData getChartData() {
		return chartData;
	}

	public void setChartData(ChartData chartData) {
		this.chartData = chartData;
	}
}
