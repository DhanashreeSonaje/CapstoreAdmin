package com.capstore.app.exception;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
public class CustomHeaderFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("Init filter");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
				
		httpServletResponse.addHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Origin");
		httpServletResponse.addHeader("Access-Control-Allow-Headers", "Content-Type");
		httpServletResponse.addHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Methods");
		httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
		httpServletResponse.setHeader("Content-Type", "*/*");
		httpServletResponse.setHeader("Access-Control-Allow-Methods", "*");
		chain.doFilter(request, response); // continue execution of other filter chain.
	}

	@Override
	public void destroy() {
		System.out.println("destroy filter. release our resources here if any");
	}

}