package jp.kght6123.smart.struts1.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import jp.kght6123.smart.struts1.wrapper.HttpMethodOverrideServletRequest;

@WebFilter(filterName="HttpMethodOverrideFilter", urlPatterns="/*", servletNames="action")
public class HttpMethodOverrideFilter
	implements Filter
{
	public HttpMethodOverrideFilter(){}
	
	public void init(final FilterConfig config) throws ServletException {}
	public void destroy(){}

	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException
	{
		if(request instanceof HttpServletRequest)
			chain.doFilter(new HttpMethodOverrideServletRequest((HttpServletRequest)request), response);
		else
			chain.doFilter(request, response);
	}
}