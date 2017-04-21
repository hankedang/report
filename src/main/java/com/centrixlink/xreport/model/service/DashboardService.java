package com.centrixlink.xreport.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centrixlink.cache.CacheService;
import com.centrixlink.xreport.domain.model.Dashboard;
import com.centrixlink.xreport.domain.model.DataModel;
import com.centrixlink.xreport.model.dao.DashboardDao;

/**
 * 分析模型Service
 */
@Service
public class DashboardService {

	@Autowired
	private DashboardDao dashboardDao;

	@Autowired
	private CacheService modelCache;
	
	@Autowired
	private DataModelService dataModelService;

	/**
	 * Get Dashboard Specified By Id
	 */
	public Dashboard getBoard(String id) {
		Dashboard board = null ; //modelCache.getBoard(id);
		if (board == null) {
			board = dashboardDao.getBoard(id);
			// Add Data Model
			DataModel dataModel = dataModelService.getDataModel(id);
			board.setDataModel(dataModel);
			modelCache.putData(id, board);
		}
		return board;
	}
}