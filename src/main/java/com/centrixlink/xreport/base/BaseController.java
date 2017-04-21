package com.centrixlink.xreport.base;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.centrixlink.exception.ParameterException;
import com.centrixlink.util.DateUtil;
import com.centrixlink.util.StringUtil;
import com.centrixlink.xreport.domain.query.Pagination;
import com.centrixlink.xreport.domain.web.DateSpan;
import com.centrixlink.xreport.domain.web.QueryBean;

public abstract class BaseController {

	protected void reply(HttpServletResponse response, Object datas) throws IOException {
		response.setCharacterEncoding("utf-8");
		Writer writer = response.getWriter();
		if (datas instanceof String) {
			writer.write(String.valueOf(datas));
		} else {
			writer.write(JSON.toJSONString(datas));
		}
	}

	/**
	 * 获取查询条件和分页参数
	 */
	protected QueryBean getQueryParams(HttpServletRequest request) throws ParameterException {
		QueryBean qParam = new QueryBean();

		// Date Span
		DateSpan dateSpan = getDateSpan(request, DateUtil.SPAN_BEFORE);
		qParam.setDateSpan(dateSpan);

		// Order
		qParam.setOrderBy(request.getParameter("orderBy"));
		qParam.setOrderType(request.getParameter("orderType"));

		// idxList
		String dimStr = request.getParameter("queryDim");
		if (StringUtil.isNotEmpty(dimStr)) {
			List<String> dimList = StringUtil.split2List(dimStr, ";");
			qParam.setDimList(dimList);
		}
		
		// idxList
		String idxStr = request.getParameter("queryIdx");
		if (StringUtil.isNotEmpty(idxStr)) {
			List<String> idxList = StringUtil.split2List(idxStr, ";");
			qParam.setIdxList(idxList);
		}

		// Query Condition
		String queryStr = request.getParameter("queryStr");
		Map<String, Map<String,String>> paramMap = getParamMap(queryStr, ";");
		qParam.setParamMap(paramMap);

		// Pagination
		String type = request.getParameter("downType");
		if (!"all".equals(type)) {
			Pagination page = getPagination(request);
			qParam.setPage(page);
		}
		return qParam;
	}
	
	protected DateSpan getDateSpan (HttpServletRequest request, String type) {
		DateSpan dateSpan = new DateSpan();
		String start = request.getParameter("startDate");
		if (StringUtil.isEmpty(start)) {
			dateSpan = DateUtil.getDateSpan(type);
		} else {
			dateSpan.setStartDate(start);
			String end = request.getParameter("endDate");
			if (StringUtil.isEmpty(end)) {
				dateSpan.setEndDate(start);
			} else {
				dateSpan.setEndDate(end);
			}
		}
		return dateSpan;
	}
	
	/**
	 * Get Query Parameters
	 */
	private Map<String, Map<String,String>> getParamMap(String queryStr, String split) {
		
		if (StringUtil.isEmpty(queryStr)) {
			return null;
		}
		
		Map<String, Map<String,String>> paramMap = new HashMap<>();
		
		String[] queryArr = queryStr.split(split);
		for (String paramItem : queryArr) {
			String[] paramArr = paramItem.split(":");
			if (paramArr.length != 3) {
				continue;
			}
			String key = paramArr[0];
			String sign = paramArr[1];
			String value = paramArr[2];
			
			if (paramMap.containsKey(key)) {
				Map<String,String> filter = paramMap.get(key);
				if (filter.containsKey(sign)) {
					if (sign.matches("(eq|in)")) {
						String con = filter.get(sign);
						filter.put(sign, con + "," + value);
					}
				} else {
					filter.put(sign, value);
				}
			} else {
				Map<String,String> filter = new HashMap<>();
				filter.put(sign, value);
				paramMap.put(key,filter);
			}
		}
		
		return paramMap;
	}

	/**
	 * Get Pagination Parameters
	 */
	private Pagination getPagination(HttpServletRequest request) throws ParameterException {
		Pagination page = new Pagination();
		String pageNumber = request.getParameter("pageNumber");
		String pageSzie = request.getParameter("pageSize");
		try {
			if (pageNumber != null) {
				page.setCurrPage(Integer.parseInt(pageNumber));
			}
			if (pageSzie != null) {
				page.setPageSize(Integer.parseInt(pageSzie));
			}
		} catch (Exception e) {
			throw new ParameterException("Page Param Is Not Invalid");
		}
		return page;
	}
}