package com.centrixlink.xreport.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.centrixlink.exception.ModelException;
import com.centrixlink.log.LogService;
import com.centrixlink.util.StringUtil;
import com.centrixlink.xreport.base.BaseController;
import com.centrixlink.xreport.domain.model.Column;
import com.centrixlink.xreport.domain.model.Dashboard;
import com.centrixlink.xreport.domain.model.DataModel;
import com.centrixlink.xreport.domain.web.DateSpan;
import com.centrixlink.xreport.domain.web.ModelMap;
import com.centrixlink.xreport.domain.web.QueryBean;
import com.centrixlink.xreport.model.service.DashboardService;
import com.centrixlink.xreport.service.DataService;

/**
 * 分析模型Controller
 */
@Controller
@RequestMapping(value = "/board")
public class PageController extends BaseController {

	@Autowired
	private DashboardService boardService;

	@Autowired
	private DataService dataService;

	/**
	 * Get The Dash board Defined By The Menu; Firstly, Display The Page
	 * Skeleton
	 */
	@RequestMapping(value = "/{boardId}")
	public ModelAndView getPage(HttpServletRequest request, @PathVariable String boardId) throws Exception {

		Dashboard board = boardService.getBoard(boardId);
		if (board == null) {
			LogService.fatal("没有对应的DASHBOARD[ID=" + boardId + "]", LogService.TYPE_ACCESS);
			throw new ModelException();
		}

		// Default Display The Data Yesterday
		DataModel dataModel = board.getDataModel();
		List<Column> dimList = dataModel.getColumnList(1);
		List<Column> idxList = dataModel.getColumnList(0);
		
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("boardId", boardId);
		modelMap.put("dimName", dataModel.getDimName());
		modelMap.put("dirllMenus", board.getDrillMenus());
		modelMap.put("dimList", dimList);
		modelMap.put("idxList", idxList);
		
		// DateSpan
		DateSpan dateSpan = getDateSpan(request, board.getDateType());
		modelMap.put("dateSpan", dateSpan);
		// Drill Condition
		String drillCon = request.getParameter("drillCon");
		if (StringUtil.isNotEmpty(drillCon)) {
			modelMap.put("drillCon", drillCon);
		}
		
		String boardUrl = board.getModelURL();
		if (StringUtil.isNotEmpty(boardUrl)) {
			return new ModelAndView("/frame/model/" + boardUrl, modelMap);
		} else {
			return new ModelAndView("/frame/model/default", modelMap);
		}
	}

	/**
	 * Query The Data Of The DashBoard
	 */
	@RequestMapping(value = "/search/{boardId}")
	public void search(HttpServletRequest request, HttpServletResponse response, @PathVariable String boardId)
			throws Exception {
		// Get Dash board
		Dashboard dashboard = boardService.getBoard(boardId);
		DataModel dataModel = dashboard.getDataModel();
		dataModel.setModelType(dashboard.getTableType());
		dataModel.setModelName(dashboard.getName());
		
		QueryBean queryBean = getQueryParams(request);
		LogService.debug("QueryParams=" + queryBean, LogService.TYPE_QUERY);
		
		ModelMap modelMap = dataService.getData(dataModel, queryBean);
		this.reply(response, modelMap);
	}

	/**
	 * Download Data
	 */
	@RequestMapping(value = "/download/{boardId}")
	public void downLoad(HttpServletRequest request, HttpServletResponse response, @PathVariable String boardId)
			throws Exception {
		// Get Dash board
		Dashboard dashboard = boardService.getBoard(boardId);
		DataModel dataModel = dashboard.getDataModel();
		QueryBean queryBean = getQueryParams(request);
		
		dataService.downLoad(response, dataModel, queryBean);
	}
}
