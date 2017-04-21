package com.centrixlink.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import org.springframework.web.servlet.HandlerExceptionResolver;

public class CustomHandler implements HandlerExceptionResolver {

	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object object, Exception exception) {
		// 判断是否ajax请求
		if (isAjax(request)) {
			try {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("success", false);
				// 为安全起见，只有业务异常我们对前端可见，否则统一归为系统异常
				if (exception instanceof AuthException) {
					map.put("errorMsg", exception.getMessage());
				} else {
					map.put("errorMsg", "系统异常！");
				}
				response.setContentType("application/json;charset=UTF-8");
				PrintWriter writer = response.getWriter();
				writer.write(JSON.toJSONString(map));
				writer.flush();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			// 为安全起见，只有业务异常我们对前端可见，否则否则统一归为系统异常
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("success", false);
			if (exception instanceof AuthException) {
				map.put("errorMsg", exception.getMessage());
			} else {
				map.put("errorMsg", "系统异常！");
			}
			// 这里需要手动将异常打印出来，由于没有配置log，实际生产环境应该打印到log里面
			exception.printStackTrace();
			// 对于非ajax请求，我们都统一跳转到error.jsp页面
			return new ModelAndView("/error", map);
		}
		return null;
	}

	private boolean isAjax(HttpServletRequest request) {
		String acceptType = request.getHeader("accept");
		if (acceptType.indexOf("application/json") > -1) {
			return true;
		}
		String x_request = request.getHeader("X-Requested-With");
		if (x_request != null && x_request.indexOf("XMLHttpRequest") > -1) {
			return true;
		}
		return false;
	}
}