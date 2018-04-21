package com.miniapp.common.filters;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet Filter implementation class PrepositionParameterHandlerFilter
 */
public class PrepositionParameterHandlerFilter implements Filter {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	/**
	 * Default constructor.
	 */
	public PrepositionParameterHandlerFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		logger.debug("进入前置参数处理过滤器");
		// place your code here
		@SuppressWarnings("rawtypes")
		ConcurrentHashMap paramMap = new ConcurrentHashMap();

		Enumeration en = request.getParameterNames();
		while (en.hasMoreElements()) {
			String nms = en.nextElement().toString();
			paramMap.put(nms, request.getParameter(nms).trim());
		}
		request.setAttribute("paramMap", paramMap);
		
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
