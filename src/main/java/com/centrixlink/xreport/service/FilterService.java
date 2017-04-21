package com.centrixlink.xreport.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centrixlink.xreport.dao.FilterDAO;
import com.centrixlink.xreport.domain.query.QueryFilter;

@Service
public class FilterService {

	@Autowired
	private FilterDAO conDao;
	
	public List<QueryFilter> getConTree(String boardId) {
		return conDao.getConTree(boardId);
	}

	public List<Map<String, Object>> getConType(String id) {
		return conDao.getConType(id);
	}

	public List<Map<String, Object>> getConValue(String conId) {
		return conDao.getConValue(conId);
	}

}
