package com.centrixlink.xreport.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.centrixlink.util.DateUtil;
import com.centrixlink.util.StringUtil;
import com.centrixlink.xreport.domain.query.Pagination;
import com.centrixlink.xreport.domain.query.SearchFilter;
import com.centrixlink.xreport.domain.web.DateSpan;

/**
 */
public class ModelQuery implements Serializable {

	private static final long serialVersionUID = 1L;

	private String sort;// 排序字段
	private String order;// 排序类型
	private String table;// 数据表
	private Pagination page;// 分页参数
	private DateSpan dateSpan;// 时间范围
	private List<Column> dimList;// 维度列
	private List<Column> idxList;// 指标字段,可以Sum
	private List<SearchFilter> where;// 查询过滤条件
	private List<SearchFilter> having;// 查询过滤条件

	public ModelQuery() {}

	/*** Query Table **/
	public void setTable (String table) {
		this.table = table;
	}
	
	public String getTable() {
		return this.table;
	}
	
	public String getSort() {
		if (null == sort) {
			Column column = idxList.get(0);
			return column.getName();
		} else {
			return sort;
		}
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		if (StringUtil.isEmpty(order)) {
			return "DESC";
		}
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public Pagination getPage() {
		return page;
	}

	public void setPage(Pagination page) {
		this.page = page;
	}

	public DateSpan getDateSpan() {
		return dateSpan;
	}

	public void setDateSpan(DateSpan dateSpan) {
		this.dateSpan = dateSpan;
	}

	public List<Column> getDimList() {
		return dimList;
	}

	public String getDimField() {
		return dimList.get(0).getName();
	}
	
	public void setDimList(List<Column> dimList) {
		Collections.sort(dimList);
		this.dimList = dimList;
	}
	
	public List<Column> getIdxList() {
		return idxList;
	}

	public void setIdxList(List<Column> idxList) {
		Collections.sort(idxList);
		this.idxList = idxList;
	}

	/*** Where Condition **/
	public void addWhere(SearchFilter filter) {
		if (null == this.where) {
			this.where = new ArrayList<SearchFilter>();
		}
		this.where.add(filter);
	}
	
	public List<SearchFilter> getWhere () {
		return this.where;
	}

	public List<SearchFilter> getHaving() {
		return having;
	}

	public void setHaving(List<SearchFilter> having) {
		this.having = having;
	}
	
	/*** Having Condition **/
	public void addHaving(SearchFilter filter) {
		if (null == this.having) {
			this.having = new ArrayList<SearchFilter>();
		}
		this.having.add(filter);
	}

	public String getGroupSQL() {
		StringBuffer sql = new StringBuffer();
		for (Column dim : dimList) {
			String name = dim.getName();
			String dataType = dim.getDateType();
			if ("sig".equals(dataType)) {
				sql.append(",").append(name);
			} else {
				String para = dim.getParas();
				if (StringUtil.isEmpty(para)) {
					para = name;
				}
				if ("map".equals(dataType)) {
					sql.append(",").append(para);
				} else if ("mul".equals(dataType)) {
					String func = dim.getFormula();
					sql.append(",").append(func).append("(").append(para).append(")");
				}
			}
		}
		return sql.toString().substring(1);
	}
	
	/**
	 * Contact Dim Column To SQL
	 */
	public String getDimSQL () {
		
		StringBuffer sql = new StringBuffer();
		for (Column dim : dimList) {
			String name = dim.getName();
			String func = dim.getFormula();
			// Whether Having Mapping Function
			if (StringUtil.isNotEmpty(func)) {
				String paras = dim.getParas();
				if (StringUtil.isNotEmpty(paras)) {
					sql.append(func).append("(").append(paras).append(") AS ");
				} else {
					sql.append(func).append("(").append(name).append(") AS ");
				}
			}
			sql.append(name).append(",");
		}
		
		return sql.toString();
	}
	
	/**
	 * Contact Indicator Column To SQL
	 */
	public String getIdxSQL() {

		StringBuffer sql = new StringBuffer();
		for (Column column : idxList) {
			sql.append(",");
			String dataType = column.getDateType();
			if ("digit".equals(dataType)) {
				sql.append("ROUND(").append(column.getFormula()).append(",2)");
			} else {
				sql.append(column.getFormula());
			}
			sql.append(" AS ").append(column.getName());
		}

		return sql.toString().substring(1);
	}

	/**
	 * Contact Query Condition To SQL
	 */
	public String getWhereSQL() {
		StringBuffer sql = new StringBuffer();
		// Date Condition
		// If One Day, Search From Table Of Day
		// If Current Month Search From Table Of Month, The Date Condition Can Be Ignored
		if (DateUtil.isOneday(dateSpan)) {
			sql.append("1=1");
		} else if (DateUtil.isCurMonth(dateSpan)) {
			sql.append("1=1");
		} else {
			String start = dateSpan.getStartDate();
			String end = dateSpan.getEndDate();
			sql.append(" rep_date>='").append(start).append("' AND");
			sql.append(" rep_date<='").append(end).append("'");
		}
		// Where Condition
		if (null != where) {
			Collections.sort(where);
			for (SearchFilter filter : where) {
				sql.append(filter.toSQL());
			}
		}
		return sql.toString();
	}

	public String getHavingSQL() {
		StringBuffer sql = new StringBuffer();
		// Having Condition
		if (null != this.having) {
			Collections.sort(having);
			for (SearchFilter filter : having) {
				sql.append(filter.havingSQL());
			}
		}
		return sql.toString();
	}
	/**
	 * Contact Order Column To SQL
	 */
	public String getOrderSql() {
		StringBuffer sql = new StringBuffer();
		
		sql.append(getSort()).append(" ").append(getOrder());
		
		return sql.toString();
	}

	public void addParam(Column column, String sign, String value) {
		if (null == column) {
			return;
		}
		int dim = column.getDim();
		if (dim == 1) {
			addWhere(new SearchFilter(column,sign, value));
		} else {
			addHaving(new SearchFilter(column,sign, value));
		}
	}
}
