package com.centrixlink.xreport.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centrixlink.xreport.domain.model.DataModel;
import com.centrixlink.xreport.model.dao.DataModelDao;
/**
 * 数据模型服务
 * 2015.02.05
 */
@Service
public class DataModelService {

	@Autowired
	private DataModelDao dataModelDao;
	
	public DataModel getDataModel(String dashId) {
		return dataModelDao.getModel(dashId);
	}
}
