package com.centrixlink.exception;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping(value = "/exception")
public class ExceptionController {

	@RequestMapping(value = "/session")
	public ModelAndView sessionException(HttpServletRequest request, HttpServletResponse response) {
		String type = request.getHeader("X-Requested-With");
		
		try {
			if ("XMLHttpRequest".equalsIgnoreCase(type)) {// ajax
				this.reply(response, "0");
			} else {
				return new ModelAndView("/sso/logout");
			}
		} catch (Exception e) {}
		return null;
	}
	
	public void queryException (HttpServletRequest request, HttpServletResponse response) {
		
	}
	
	private void reply(HttpServletResponse response, Object datas) throws Exception {
		response.setCharacterEncoding("utf-8");
		Writer writer = response.getWriter();
		if (datas instanceof String) {
			writer.write(String.valueOf(datas));
		} else {
			writer.write(JSON.toJSONString(datas));
		}
	}
}
