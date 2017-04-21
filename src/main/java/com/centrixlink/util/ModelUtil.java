package com.centrixlink.util;

import java.util.List;

import com.centrixlink.xreport.domain.model.Column;
import com.centrixlink.xreport.domain.model.ModelQuery;
import com.centrixlink.xreport.domain.query.Pagination;
import com.centrixlink.xreport.domain.query.SearchFilter;
import com.centrixlink.xreport.domain.web.DateSpan;

public class ModelUtil {
	
	private ModelUtil () {}
	
	/**
	 * 表名由系统前缀 + 表名 + 表类型 组成， 例如： xxx_platform
	 * @param modelQuery
	 * @param modelName
	 * @param modelType 0: 单表， 1：年表， 2：月表， 3：天表
	 * @return
	 */
	
	public static String getTableName(ModelQuery modelQuery, String modelName, int modelType) {
		
//		StringBuffer tableName = new StringBuffer();
//		
//		tableName.append("xxxx").append("_").append("rep").append("_").append(modelName);
//		DateSpan dateSpan = modelQuery.getDateSpan();
//		String dateStr = dateSpan.getDateStr();
//		
//		switch(modelType) {
//			case 0: break;
//			case 1: tableName.append("_").append(dateStr.substring(0, 4)); break;
//			case 2: tableName.append("_").append(dateStr.substring(0, 6)); break;
//			case 3: tableName.append("_").append(dateStr.substring(0, 8)); break;
//			default : break;
//		}
//		return tableName.toString();
		return "ad_rep_campaign";
	}
	
	/**
	 * Get Table Name
	 */
//	public static String getTableName (ModelQuery modelQuery, String prefix) {
//		
//		StringBuffer tableName = new StringBuffer();
//		
//		// Prefix : system + theme
//		tableName.append("xxxx_").append(prefix);
//
//		// level suffix
//		boolean isTotal = false; int level = 0;
//		
//		// Dim
//		List<Column> dimList = modelQuery.getDimList();
//		for (Column column : dimList) {
//			int dimCode = column.getLevel();
//			if (level == 0) {
//				level = dimCode;
//				continue;
//			}
//			if (level != dimCode) {
//				isTotal = true;
//			}
//			level = Math.max(level, dimCode);
//		}
//		
//		// Condition
//		List<SearchFilter> filters = modelQuery.getWhere();
//		if (null != filters) {
//			for (SearchFilter filter : filters) {
//				int code = filter.getColumn().getLevel();
//				if (level != code) {
//					isTotal = true;
//				}
//				level = Math.max(level, code);
//			}
//		}
//		tableName.append("_l").append(level);
//
//		// total or not
//		if (isTotal) {
//			tableName.append("_1");
//		} else {
//			tableName.append("_0");
//		}
//
//		// Date Suffix
//		DateSpan dateSpan = modelQuery.getDateSpan();
//		tableName.append("_").append(dateSpan.getDateStr());
//		
//		return tableName.toString();
//	}

	/**
	 * Get Sum And Count
	 */
	public static String getSumAndCountSQL (ModelQuery query) {
		
		String table = query.getTable();
		String idxSql = query.getIdxSQL();
		String groupSql = query.getGroupSQL();
		String whereSQL = query.getWhereSQL();
		
		StringBuffer sql = new StringBuffer();
		// Count
		sql.append("SELECT COUNT(DISTINCT ").append(groupSql).append(") AS records,");
		// Total
		sql.append("'TOTAL' AS ").append(query.getDimField()).append(",").append(idxSql);
		sql.append(" FROM ");
		
		String havingSQL = query.getHavingSQL();
		// 子查询
		if (StringUtil.isNotEmpty(havingSQL)) {
			sql.append("(SELECT * FROM ").append(table);
			// Condition
			sql.append(" WHERE ").append(whereSQL);
			// Group By
			sql.append(" GROUP BY ").append(groupSql);
			// Having
			sql.append(" HAVING ").append(havingSQL.substring(4));
			sql.append(") T");
		} else {
			sql.append(table);
			// Condition
			sql.append(" WHERE ").append(whereSQL);
		}
		
		return sql.toString();
	}
	
	/**
	 * Get List SQL, Page Or Not
	 */
	public static String getListSql(ModelQuery query) {
		
		String table = query.getTable();
		String dimSql = query.getDimSQL();
		String idxSql = query.getIdxSQL();
		String groupSql = query.getGroupSQL();
		String whereSQL = query.getWhereSQL();
		String havingSQL = query.getHavingSQL();
		Pagination page = query.getPage();
		
		StringBuffer sql = new StringBuffer();
		// Query Columns
		sql.append("SELECT ").append(dimSql).append(idxSql);
		sql.append(" FROM ").append(table);
		// Condition
		sql.append(" WHERE ").append(whereSQL);
		// Group By
		sql.append(" GROUP BY ").append(groupSql);
		// Having
		if (StringUtil.isNotEmpty(havingSQL)) {
			sql.append(" HAVING ").append(havingSQL.substring(4));
		}
		// Order By
		sql.append(" ORDER BY ").append(query.getOrderSql());
		// Pagination
		if (page != null) {
			sql.append(page.getPageSql());
		}

		return sql.toString();
	}
}
