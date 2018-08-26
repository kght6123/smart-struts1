package jp.kght6123.smart.struts1.logic;

import java.sql.Connection;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.kght6123.smart.struts1.Constants.HttpMethod;
import jp.kght6123.smart.struts1.form.FormImpl;
import jp.kght6123.smart.struts1.struct.ServletStructure;
import jp.kght6123.smart.struts1.struct.Structure;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.util.MessageResources;
import org.slf4j.Logger;

public class Logic <F extends FormImpl>
{
	protected F form;
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected ServletContext context;
	protected HttpSession session;
	protected Connection conn;
	protected Logger logger;
	protected HttpMethod method;
	protected ActionErrors errors;
	protected MessageResources messageResources;
	
	/**
	 * コンストラクタ
	 * 
	 * @param structure 構造体
	 */
	public Logic()
	{
		super();
	}
	
	public Logic<F> init(final Structure<F> structure)
	{
		if(structure instanceof ServletStructure)
		{
			this.form = structure.getForm();
			this.request = ((ServletStructure<?>)structure).getRequest();
			this.response = ((ServletStructure<?>)structure).getResponse();
			this.context = ((ServletStructure<?>)structure).getContext();
			this.session = ((ServletStructure<?>)structure).getSession();
			this.conn = structure.getConnection();
			this.logger = structure.getLogger();
			this.method = ((ServletStructure<?>)structure).getMethod();
			this.errors = ((ServletStructure<?>)structure).getErrors();
			this.messageResources = ((ServletStructure<?>)structure).getMessageResources();
		}
		else
		{
			this.form = structure.getForm();
			this.request = null;
			this.response = null;
			this.context = null;
			this.session = null;
			this.conn = structure.getConnection();
			this.logger = structure.getLogger();
			this.method = null;
			this.errors = null;
			this.messageResources = null;
		}
		return this;
	}
}