package jp.kght6123.smart.struts1.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

@WebFilter(filterName="EncodingRequestFilter", urlPatterns="*.do", servletNames="action", 
	initParams={@WebInitParam(name="encoding", value="UTF-8")})
public class EncodingRequestFilter implements Filter {
	
	private String encoding;
	
	public EncodingRequestFilter() {}
	
	public void destroy() {}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
		try {
			request.setCharacterEncoding(this.encoding);
			
		} catch (final UnsupportedEncodingException ex) {
			throw new ServletException(ex);
		}
		chain.doFilter(request, response);
	}
	
	public void init(FilterConfig config) throws ServletException {
		this.encoding = config.getInitParameter("encoding");
	}
}