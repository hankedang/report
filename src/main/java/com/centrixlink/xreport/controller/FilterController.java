package com.centrixlink.xreport.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.centrixlink.xreport.base.BaseController;
import com.centrixlink.xreport.domain.query.QueryFilter;
import com.centrixlink.xreport.service.FilterService;

/**
 * 分析模型Controller
 */
@Controller
@RequestMapping(value = "/filter")
public class FilterController extends BaseController {

	@Autowired
	private FilterService conService;

	/**
	 * Query The Data Of The DashBoard
	 */
	@RequestMapping(value = "/con/{boardId}")
	public void getConTree(HttpServletRequest request, HttpServletResponse response, @PathVariable String boardId)
			throws Exception {

		List<QueryFilter> conTree = conService.getConTree(boardId);

		this.reply(response, conTree);
	}

	/**
	 * Query The Data Of The DashBoard
	 */
	@RequestMapping(value = "/type/{id}")
	public void getConType(HttpServletRequest request, HttpServletResponse response, @PathVariable String id)
			throws Exception {

		List<Map<String, Object>> conType = conService.getConType(id);

		this.reply(response, conType);
	}

	/**
	 * Query The Data Of The DashBoard
	 */
	@RequestMapping(value = "/value/{conId}")
	public void getConValue(HttpServletRequest request, HttpServletResponse response, @PathVariable String conId)
			throws Exception {

		List<Map<String, Object>> conValue = conService.getConValue(conId);

		this.reply(response, conValue);
	}
}
