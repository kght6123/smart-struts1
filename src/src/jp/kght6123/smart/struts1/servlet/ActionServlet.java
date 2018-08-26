package jp.kght6123.smart.struts1.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ActionServlet extends org.apache.struts.action.ActionServlet {
	
	/**
	 * 削除
	 */
	@Override
	protected void doDelete(
			final HttpServletRequest req,
			final HttpServletResponse resp) throws ServletException, IOException {
		super.doPost(req, resp);// doPostへ
	}
	
	/**
	 * 作成＆更新
	 */
	@Override
	protected void doPut(
			final HttpServletRequest req,
			final HttpServletResponse resp) throws ServletException, IOException {
		super.doPost(req, resp);// doPostへ
	}
	
	/**
	 * 読込
	 */
	@Override
	public void doGet(
			final HttpServletRequest req,
			final HttpServletResponse resp) throws IOException, ServletException {
		super.doGet(req, resp);
	}
	
	/**
	 * 作成
	 */
	@Override
	public void doPost(
			final HttpServletRequest req,
			final HttpServletResponse resp) throws IOException, ServletException {
		super.doPost(req, resp);
	}
	
	@Override
	public void destroy() {
		
		super.destroy();
	}
	
	@Override
	public void init() throws ServletException {
		
		// FIXME コネクション（Struts、JNDI）のモード、デフォルトコネクションの取得
		getServletConfig().getInitParameterNames();
		
		// FIXME ログ（Log4j、Logback）のモード
		getServletConfig().getInitParameter("");
		
		super.init();
	}
}