package jp.kght6123.smart.struts1.form;

import jp.kght6123.smart.struts1.Constants.HttpMethod;

import org.apache.struts.action.ActionForm;

public class Form extends ActionForm implements FormImpl
{
	private String method;
	private String _method;
	
	public HttpMethod getMethod() {
		return HttpMethod.valueOf(method);
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String get_method() {
		return _method;
	}
	public void set_method(String _method) {
		this._method = _method;
	}
}