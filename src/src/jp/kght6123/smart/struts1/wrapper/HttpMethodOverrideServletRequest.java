package jp.kght6123.smart.struts1.wrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang3.StringUtils;

public class HttpMethodOverrideServletRequest extends HttpServletRequestWrapper
{
	private final static String _METHOD = "_method";
	private final static String X_METHOD_OVERRIDE = "X-Method-Override";
	private final static String X_HTTP_METHOD_OVERRIDE = "X-HTTP-Method-Override";
	
	public HttpMethodOverrideServletRequest(final HttpServletRequest request)
	{
		super(request);
	}
	@Override
	public String getMethod() {
		
		final String[] methodOverrideValues = 
				new String[]{
					getParameter(_METHOD),
					getParameter(_METHOD.toUpperCase()),
					getParameter(X_METHOD_OVERRIDE.toLowerCase()),
					getParameter(X_METHOD_OVERRIDE.toUpperCase()),
					getParameter(X_HTTP_METHOD_OVERRIDE.toLowerCase()),
					getParameter(X_HTTP_METHOD_OVERRIDE.toUpperCase()),
					getHeader(X_METHOD_OVERRIDE),
					getHeader(X_METHOD_OVERRIDE.toUpperCase()),
					getHeader(X_HTTP_METHOD_OVERRIDE),
					getHeader(X_HTTP_METHOD_OVERRIDE.toUpperCase()),
		};
		
		for(final String methodOverrideValue : methodOverrideValues) {
			if (StringUtils.isNotEmpty(methodOverrideValue))
				return methodOverrideValue;
		}
		return super.getMethod();
	}
}