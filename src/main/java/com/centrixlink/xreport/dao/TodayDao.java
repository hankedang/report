package com.centrixlink.xreport.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.centrixlink.cache.CacheService;
import com.centrixlink.encypt.MD5Encrypt;
import com.centrixlink.log.LogService;
import com.centrixlink.util.ModelUtil;
import com.centrixlink.xreport.domain.model.ModelQuery;
import com.centrixlink.xreport.domain.query.JTableData;

/**
 * 数据模型DAO
 * 
 * @author franplk 2016.07.11
 */
@Repository
public class TodayDao {

	@Autowired
	private JdbcTemplate dataTemplate;
	
	@Autowired
	private CacheService modelCache;
	
	/**
	 * Organize The Table Data
	 */
	public JTableData getTableData(ModelQuery query) throws Exception {
		JTableData tableData = new JTableData();
		
		// Total Records And Summary
		Map<String, Object> sumAndCountData = getSumAndCount(query);
		long records = (long) sumAndCountData.get("records");
		tableData.setTotal(records);
		tableData.addSum(sumAndCountData);
		
		// DataList
		List<Map<String, Object>> datas = getRowList(query);
		tableData.setRows(datas);
		
		return tableData;
	}

	/**
	 * Get Sum And Count
	 */
	public Map<String, Object> getSumAndCount(ModelQuery query) throws Exception {
		String sql = ModelUtil.getSumAndCountSQL(query);
		LogService.debug("Sum And Count SQL:" + sql, LogService.TYPE_QUERY);
		
		String sqlKey = MD5Encrypt.encrypt(sql);
		Map<String, Object> data = modelCache.getDataMap(sqlKey);
		if (null == data) {
			data = dataTemplate.queryForMap(sql);
			modelCache.putData(sqlKey, data);
		}
		return data;
	}

	/**
	 * Get Data
	 */
	public List<Map<String, Object>> getRowList(ModelQuery query) throws Exception {
		String sql = ModelUtil.getListSql(query);
		LogService.debug("List SQL:" + sql, LogService.TYPE_QUERY);
		String sqlKey = MD5Encrypt.encrypt(sql);
		List<Map<String, Object>> data = modelCache.getDataList(sqlKey);
		if (null == data) {
			data = dataTemplate.queryForList(sql);
			modelCache.putData(sqlKey, data);
		}
		return data;
	}
}
