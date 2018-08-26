package jp.kght6123.smart.struts1.form;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.kght6123.smart.struts1.action.ActionUtils;
import jp.kght6123.smart.struts1.struct.ServletStructure;

public abstract class ValidateForm <F extends Form> extends Form
{
	private static final long serialVersionUID = 1L;
	
	private boolean validate = false;
	
	@Override
	public ActionErrors validate(
			final ActionMapping mapping,
			final HttpServletRequest request) {
		
		if(!isValidate())
			return super.validate(mapping, request);
		
		//コンテキストの取得
		//final ServletContext context = getServlet().getServletContext();
		
		// Sessionを取得
		final HttpSession session = request.getSession();
		
		// クライアントごとにログを出す
		final Logger logger = LoggerFactory.getLogger(ValidateForm.class);//Log4jLoggerUtil.getRequestContextLogger(context, request);
		
		// エラーメッセージを保存するオブジェクト
		final ActionErrors errors = new ActionErrors();
		
		ActionUtils.pushNDC(request);
		ActionUtils.outLogResponseMeasurement(request, logger, false);
		ActionUtils.outLogRequestParameter(request,logger);
		
		@SuppressWarnings("unchecked")
		final F f = ((F)this);
		
		// HTTPメソッドの設定
		f.setMethod((String)request.getAttribute("method"));
		
		try (final ServletStructure<F> structure = 
				ActionUtils.init(servlet, request, null, session, f, logger, errors);
			) {
			
			return doValidate(mapping, structure, errors);
			
		} catch (SQLException se) {
			logger.error("SQLException：" + se.getMessage(), se);
			addError(errors, "errors.systemError", se.getMessage());
			return errors;
			
		} catch (Exception e) {
			logger.error("Exception：" + e.getMessage(), e);
			addError(errors, "errors.systemError", e.getMessage());
			return errors;
			
		} finally{
			ActionUtils.outLogResponseMeasurement(request, logger, true);
			ActionUtils.removeNDC(request);
		}
	}
	
	protected void addError(final ActionMessages errors, final String messageResoueceKey, final Object... args)
	{
		final ActionMessage error = new ActionMessage(messageResoueceKey, args);
		errors.add(getMessageKey(), error);
	}
	
	protected abstract ActionErrors doValidate(final ActionMapping mapping, final ServletStructure<F> structure, final ActionErrors errors) throws Exception;
	
	public boolean isValidate() {
		return validate;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
	}
	
	protected abstract String getMessageKey();
	
}