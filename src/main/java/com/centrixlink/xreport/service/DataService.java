package com.centrixlink.xreport.service;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centrixlink.log.LogService;
import com.centrixlink.util.ExcelUtil;
import com.centrixlink.xreport.base.BaseService;
import com.centrixlink.xreport.dao.DataDao;
import com.centrixlink.xreport.domain.model.Column;
import com.centrixlink.xreport.domain.model.DataModel;
import com.centrixlink.xreport.domain.model.ModelQuery;
import com.centrixlink.xreport.domain.query.JTableData;
import com.centrixlink.xreport.domain.web.ExcelHeader;
import com.centrixlink.xreport.domain.web.ModelMap;
import com.centrixlink.xreport.domain.web.QueryBean;

/**
 * @service Data Service
 * @function Get Table Data, Chart Data; Download Data
 */
@Service
public class DataService extends BaseService {

	@Autowired
	private DataDao dataDao;

	/**
	 * Get Pagination Data For Specified Data Model
	 */
	public ModelMap getData(DataModel dataModel, QueryBean queryBean) {
		ModelMap modelMap = null;
		if (isToday(queryBean)) {
			modelMap = getTodayData(dataModel, queryBean);
		} else {
			modelMap = getHistoryData(dataModel, queryBean);
		}
		return modelMap;
	}

	/**
	 * Download The Data To Excel
	 */
	public void downLoad(HttpServletResponse response, DataModel dataModel, QueryBean queryBean) {
		
		ModelMap modelMap = getData(dataModel, queryBean);

		// Failed Deploy
		if (modelMap.getMsg() != null) {
			System.out.println(modelMap.getMsg());
			return;
		}

		// Deploy The DownLoad
		JTableData tableData = modelMap.getTableData();
		List<Map<String, Object>> dataList = getExportData(tableData);
		try {
			response.setContentType("application/octet-stream;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename='report.xls'");
			OutputStream out = response.getOutputStream();
			List<ExcelHeader> headers = getHeaders(modelMap);
			ExcelUtil.exportComExcel(headers, dataList, out, "report");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private ModelMap getTodayData(DataModel dataModel, QueryBean queryBean) {
		return getHistoryData(dataModel, queryBean);
	}
	
	private ModelMap getHistoryData(DataModel dataModel, QueryBean queryBean) {
		ModelMap modelMap = new ModelMap();

		/********* Get Query Model ********/
		ModelQuery modelQuery = getModelQuery(dataModel, queryBean);

		/********* Start Query ********/
		JTableData tableData = null;
		try {
			tableData = dataDao.getTableData(modelQuery);
			/**** Here, Do Some Data Filter Later ****/
		} catch (Exception e) {
			modelMap.setMsg("查询失败");
			LogService.error(e.getMessage(), LogService.TYPE_QUERY);
		}
		// Data Query Successfully
		if (null == modelMap.getMsg()) {
			modelMap.setTableData(tableData);
			// Columns
			List<Column> columns = new ArrayList();
			columns.addAll(modelQuery.getDimList());
			columns.addAll(modelQuery.getIdxList());
			modelMap.setColumns(columns);
		}
		return modelMap;
	}
	
	private List<Map<String, Object>> getExportData(JTableData tableData) {
		List<Map<String, Object>> dataList = new ArrayList<>();
		dataList.addAll(tableData.getRows());
		dataList.addAll(tableData.getFooter());
		return dataList;
	}
}
