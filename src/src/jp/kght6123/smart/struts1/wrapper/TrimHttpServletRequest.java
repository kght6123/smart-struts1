package jp.kght6123.smart.struts1.wrapper;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class TrimHttpServletRequest extends HttpServletRequestWrapper
{
	public TrimHttpServletRequest(final HttpServletRequest request)
	{
		super(request);
	}

	@Override
	public String getParameter(final String name)
	{
		final String value = super.getParameter(name);
		if(value != null)
			return value.trim();
		else
			return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map getParameterMap()
	{
		final Map paramMap = super.getParameterMap();
		
		final Set entrySet = paramMap.entrySet();
		for(final Object obj : entrySet)
		{
			final Map.Entry entry = (Map.Entry)obj;
			final Object valObj = entry.getValue();
			
			if(valObj instanceof String)
				entry.setValue(((String)valObj).trim());
			else
				entry.setValue((valObj.toString()).trim());
		}
		return paramMap;
	}

	@Override
	public String[] getParameterValues(final String name) {
		final String[] values = super.getParameterValues(name);
		if(values == null
				|| values.length == 0)
			return values;
		else
		{
			for(int i = 0; i < values.length; i++)
			{
				final String value = values[i];
				if(value != null)
					values[i] = value.trim();
			}
			return values;
		}
	}
}