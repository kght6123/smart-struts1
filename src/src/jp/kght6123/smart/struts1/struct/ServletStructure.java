package jp.kght6123.smart.struts1.struct;

import java.sql.Connection;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.kght6123.smart.struts1.Constants.HttpMethod;
import jp.kght6123.smart.struts1.form.FormImpl;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.util.MessageResources;
import org.slf4j.Logger;

public class ServletStructure <F extends FormImpl> extends Structure<F> 
{
	/** リクエスト */
	private final HttpServletRequest request;
	
	/** レスポンス */
	private final HttpServletResponse response;
	
	/** コンテキスト */
	private final ServletContext context;
	
	/** セッション */
	private HttpSession session;
	
	/** イベント */
	private final HttpMethod method;
	
	/** エラー */
	private final ActionErrors errors;
	
	/** メッセージリソース **/
	private final MessageResources messageResources;
	
	public ServletStructure
	(
		final F form,
		final HttpServletRequest request,
		final HttpServletResponse response,
		final ServletContext context,
		final HttpSession session,
		final Connection connection,
		final Logger logger,
		final ActionErrors errors,
		final MessageResources messageResources
	)
	{
		super
		(
			form,
			connection,
			logger
		);
		this.request = request;
		this.response = response;
		this.context = context;
		this.session = session;
		this.method = form.getMethod();
		this.errors = errors;
		this.messageResources = messageResources;
	}
	
	public ServletContext getContext() {
		return context;
	}
	public ActionErrors getErrors() {
		return errors;
	}
	public HttpMethod getMethod() {
		return method;
	}
	public HttpServletRequest getRequest() {
		return request;
	}
	public HttpServletResponse getResponse() {
		return response;
	}
	public HttpSession getSession() {
		return session;
	}
	public void setSession(HttpSession session)
	{
		this.session = session;
	}
	public MessageResources getMessageResources() {
		return messageResources;
	}
	
	public void newsession(boolean flag) {
		this.session = request.getSession(flag);
	}
}