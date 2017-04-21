package com.centrixlink.xreport.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.centrixlink.xreport.base.BaseController;
import com.centrixlink.xreport.domain.menu.Menu;
import com.centrixlink.xreport.service.MenuService;

/**
 * @author franplk
 *
 */
@Controller
@RequestMapping("/menu")
public class MenuController extends BaseController {

	@Autowired
	private MenuService menuService;
	
	@RequestMapping("/page/add")
	public ModelAndView addPage () {
		return new ModelAndView("/sys/addUser");
	}
	
	@RequestMapping("/page/list")
	public ModelAndView page () {
		return new ModelAndView("/sys/user");
	}
	
	@RequestMapping("/data/add")
	public void addMenu () {
		menuService.addMenu();
	}
	
	@RequestMapping("data/list")
	public void listMenu (HttpServletResponse response) throws Exception {
		List<Menu> users = menuService.getMenus();
		this.reply(response, users);
	}
	
}
