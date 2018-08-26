package jp.kght6123.smart.struts1.action;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.RedirectingActionForward;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.ModuleUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.kght6123.smart.struts1.form.Form;
import jp.kght6123.smart.struts1.logic.Logic;
import jp.kght6123.smart.struts1.struct.ServletStructure;

public abstract class Action <F extends Form, L extends Logic<F>> extends org.apache.struts.action.Action {
	
	public ActionForward execute(
		final ActionMapping mapping,
		final ActionForm form,
		final HttpServletRequest request,
		final HttpServletResponse response) throws Exception {
		
		//コンテキストの取得
		//final ServletContext context = getServlet().getServletContext();
		
		// Sessionを取得
		final HttpSession session = request.getSession();
		
		// FIXME クライアントごとにログを出す
		final Logger logger = LoggerFactory.getLogger(Action.class);
		
		// エラーメッセージを保存するオブジェクト
		final ActionErrors errors = new ActionErrors();
		
		ActionUtils.pushNDC(request);
		ActionUtils.outLogResponseMeasurement(request, logger, false);
		ActionUtils.noCache(response);
		ActionUtils.outLogRequestParameter(request,logger);
		
		// 共通フォームの取得
		@SuppressWarnings("unchecked")
		final F f = ((F)form);
		
		// HTTPメソッドの設定
		f.setMethod(request.getMethod());
		
		// FIXME 接続情報をRequestにセット
		//CommonMethod.setConnectInfoToRequest(request, context);
		
		try (final ServletStructure<F> structure = 
					ActionUtils.init(servlet, request, response, session, f, logger, errors);
				) {
			
			// ヘッダーの設定
			ActionUtils.noCache(response);
			
			final ActionForward ret;
			
			switch (structure.getMethod()) {
				case POST:
					ret = controller().init(mapping, structure).doPost();
					break;
				case DELETE:
					ret = controller().init(mapping, structure).doDelete();
					break;
				case PUT:
					ret = controller().init(mapping, structure).doPut();
					break;
				case GET:
				default:
					ret = controller().init(mapping, structure).doGet();
					break;
			}
			// フォワード先にレスポンス種別を指定
			final String path = request.getServletPath();
			
			if(path.endsWith(".json"))
				response.addHeader("_type", "json");
			
			else if(path.endsWith(".xml"))
				response.addHeader("_type", "xml");
			
			else
				response.addHeader("_type", request.getParameter("_type"));
			
			final ModuleConfig mc = 
					ModuleUtils.getInstance().getModuleConfig(request);
			
			if(mc != null)
				response.addHeader("_module", mc.getPrefix());
			
			return ret;
			
		} catch (SQLException se) {
			logger.error("SQLException：" + se.getMessage(), se);
			saveGlobalError(se.getMessage(), errors, request);
			return mapping.findForward("error");
		} catch (Exception e) {
			logger.error("Exception：" + e.getMessage(), e);
			saveGlobalError(e.getMessage(), errors, request);
			return mapping.findForward("error");
		} finally{
			ActionUtils.outLogResponseMeasurement(request, logger, true);
			ActionUtils.removeNDC(request);
		}
	}
	
	private void saveGlobalError(final String message, final ActionMessages errors, final HttpServletRequest request) {
		
		final ActionMessage error = new ActionMessage("errors.systemError", message);
		errors.add(ActionMessages.GLOBAL_MESSAGE, error);
		this.saveErrors(request, errors);
	}
	
	public static class Controller <F extends Form, L extends Logic<F>> {
		
		private ActionMapping mapping;
		private ServletStructure<F> application;
		
		public Controller() {
			super();
		}
		
		public Controller<F,L> init(final ActionMapping mapping, final ServletStructure<F> application) {
			this.mapping = mapping;
			this.application = application;
			return this;
		}
		
		public ActionForward doGet() throws Exception { return findForward("error"); }
		
		public ActionForward doPost() throws Exception { return findForward("error"); }
		
		public ActionForward doDelete() throws Exception { return findForward("error"); }
		
		public ActionForward doPut() throws Exception { return findForward("error"); }
		
		public L logic() {
			@SuppressWarnings("unchecked")
			final L logic = ((L)logic().init(application));
			return logic;
		}
		public RedirectingActionForward redirect(final String path) {
			return new RedirectingActionForward(path);
		}
		
		public Logger getLogger() {
			return application.getLogger();
		}
		public F getForm() {
			return application.getForm();
		}
		public MessageResources getMessageResources() {
			return application.getMessageResources();
		}
		public ActionForward findForward(final String name) {
			return mapping.findForward(name);
		}
		public String[] findForwards() {
			return mapping.findForwards();
		}
	}
	
	abstract public Controller<F,L> controller();
	
	abstract public L logic();
}