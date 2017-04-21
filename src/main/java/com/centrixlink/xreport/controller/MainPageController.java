package com.centrixlink.xreport.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.centrixlink.sso.bean.User;
import com.centrixlink.xreport.base.BaseController;
import com.centrixlink.xreport.domain.menu.Menu;
import com.centrixlink.xreport.domain.user.EntryPage;
import com.centrixlink.xreport.service.MenuService;
import com.centrixlink.xreport.service.PageService;

@Controller
@RequestMapping(value = "/main")
public class MainPageController extends BaseController {

	@Autowired
	private PageService pageService;
	
	@Autowired
	private MenuService menuService;

	@RequestMapping(value = "/page")
	public ModelAndView mainPage(HttpServletRequest request) throws Exception {
		
		User tmpUser = new User();
		tmpUser.setUserId(1L);
		tmpUser.setUserType("admin");
		tmpUser.setLoginName("lalala");
		tmpUser.setDisplayName("韩克党");
		
		
		// Get Session User
//		HttpSession session = request.getSession();
		User user = tmpUser; // (User) session.getAttribute(Constant.AUTH_SESS_USER);
		
		// Query Menu List And The Entry Page For The Different User
		List<Menu> menuList = menuService.getMenuList(user);
		EntryPage entryPage = pageService.getEntrypage(user);
		
		Map<String, Object> modelMap = new HashMap<>();
		modelMap.put("entryPage", entryPage);
		modelMap.put("menuList", menuList);
		
		return new ModelAndView("/index", modelMap);
	}
}
