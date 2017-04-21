package com.centrixlink.xreport.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.centrixlink.util.DateUtil;
import com.centrixlink.util.ModelUtil;
import com.centrixlink.util.StringUtil;
import com.centrixlink.xreport.domain.model.Column;
import com.centrixlink.xreport.domain.model.DataModel;
import com.centrixlink.xreport.domain.model.ModelQuery;
import com.centrixlink.xreport.domain.web.DateSpan;
import com.centrixlink.xreport.domain.web.ExcelHeader;
import com.centrixlink.xreport.domain.web.ModelMap;
import com.centrixlink.xreport.domain.web.QueryBean;

public abstract class BaseService {

	protected List<ExcelHeader> getHeaders(ModelMap modelMap) {
		List<ExcelHeader> headers = new ArrayList<ExcelHeader>();

		// Add Columns
		List<Column> columnList = modelMap.getColumns();
		for (Column column : columnList) {
			headers.add(new ExcelHeader(column.getName(), column.getTitle()));
		}

		return headers;
	}

	protected ModelQuery getModelQuery(DataModel dataModel, QueryBean queryBean) {

		ModelQuery modelQuery = new ModelQuery();

		/** Add Data Span **/
		modelQuery.setDateSpan(queryBean.getDateSpan());

		/** Add Order **/
		String sortName = queryBean.getOrderBy();
		if (StringUtil.isNotEmpty(sortName)) {
			modelQuery.setSort(sortName);
		} else {
			modelQuery.setSort(dataModel.getOrderName());
		}
		modelQuery.setOrder(queryBean.getOrderType());

		/** Add Dim Key Exclude **/
		String exValue = dataModel.getExDimValue();
		if (exValue != null) {
			Column dimCol = dataModel.getColumn(dataModel.getDimName());
			modelQuery.addParam(dimCol, "neq", exValue);
		}
		/** Add Filter Conditions **/
		Map<String, Map<String, String>> paramMap = queryBean.getParamMap();
		if (null != paramMap) {
			for (Entry<String, Map<String, String>> param : paramMap.entrySet()) {
				String key = param.getKey();
				Column column = dataModel.getColumn(key);
				if (null == column) {
					continue;
				}
				for (Entry<String, String> filter : param.getValue().entrySet()) {
					modelQuery.addParam(column, filter.getKey(), filter.getValue());
				}
			}
		}

		/** Add dimension **/
		List<String> dimList = queryBean.getDimList();
		List<Column> dimColumns = dataModel.getCustomDims(dimList);
		modelQuery.setDimList(dimColumns);

		/** Add Indicator **/
		List<String> idxList = queryBean.getIdxList();
		List<Column> idxColumns = dataModel.getCustomIdxes(idxList);
		modelQuery.setIdxList(idxColumns);

		/** Add Query Pagination **/
		modelQuery.setPage(queryBean.getPage());

		/*** Set Query Table */
//		String tableName = ModelUtil.getTableName(modelQuery, dataModel.getTheme());
		String tableName = ModelUtil.getTableName(modelQuery, dataModel.getModelName(), dataModel.getModelType());
		modelQuery.setTable(tableName);

		return modelQuery;
	}

	protected boolean isToday(QueryBean queryBean) {
		DateSpan dateSpan = queryBean.getDateSpan();
		return DateUtil.isToday(dateSpan);
	}
}
