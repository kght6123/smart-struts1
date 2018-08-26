package jp.kght6123.smart.struts1.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.kght6123.smart.struts1.form.FormImpl;
import jp.kght6123.smart.struts1.struct.ServletStructure;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.util.MessageResources;
import org.slf4j.Logger;
import org.slf4j.MDC;

/**
 * Actionクラス向けの有用な処理を提供するクラスです。
 */
public class ActionUtils {
	
	/**
	 * インスタンス生成封じ
	 */
	private ActionUtils() {
	}
	
	
	public enum ACTION_INITIALIZE {
		DATASOURCE_ERROR
	}
	
	public static <F extends FormImpl> ServletStructure<F> init
	(
		final ActionServlet servlet,
		final HttpServletRequest request,
		final HttpServletResponse response,
		final HttpSession session,
		final F form,
		final Logger logger,
		final ActionErrors errors
	) throws SQLException {
		
		//コンテキストの取得
		final ServletContext context = servlet.getServletContext();
		
		final MessageResources messageResources = 
				(MessageResources)context.getAttribute("org.apache.struts.action.MESSAGE");
		
		// FIXME データソースの取得
		//final DataSource ds = (DataSource)context.getAttribute("");
		
		final ServletStructure<F> structure =
				new ServletStructure<F>
				(
					form,
					request,
					response,
					context,
					session,
					null,
					logger,
					errors,
					messageResources
				);
		
		// DBコネクションの取得
		//if(ds != null)
		//	structure.setConnection(ds.getConnection());
		
		// FIXME コネクション数の取得
		//ActionUtils.getConnectionInfo(structure, ds);
		
		return structure;
	}
	
	/**
	 * NDCに特定の情報を追加します。
	 * 
	 * @param request
	 */
	public static void pushNDC(final HttpServletRequest request) {
		MDC.put("ip", request.getRemoteAddr());
		MDC.put("sessionId", request.getRequestedSessionId());
		MDC.put("url", request.getRequestURI());
	}
	
	/**
	 * NDCをremoveします。
	 * 
	 * @param request
	 */
	public static void removeNDC(HttpServletRequest request) {
		MDC.remove("ip");
		MDC.remove("sessionId");
		MDC.remove("url");
	}
	
	/**
	 * レスポンス測定用のログを出力します。
	 * 
	 * @param request
	 * @param log
	 * @param end
	 */
	public static void outLogResponseMeasurement(final HttpServletRequest request,
			final Logger log, final boolean end) {
		if (log.isInfoEnabled()) {
			final StringBuffer sb = new StringBuffer();
			sb.append(" -----< レスポンス測定用 >----- [");
			sb.append(request.getRemoteAddr());
			sb.append("] -- [");
			sb.append(request.getRequestURI());
			sb.append("]--[");
			sb.append(new Date());
			sb.append("] ----------(");
			sb.append(end ? "E" : "S");
			sb.append(")----------");
			log.info(sb.toString());
		}
	}
	
	/**
	 * レスポンスにキャッシュを無効にすることを表す情報を埋め込みます。
	 * @param response
	 */
	public static void noCache(final HttpServletResponse response) {
		// cache invalid
		response.setHeader("Expires", getHTTPDate());
		response.setHeader("Pragma","no-cache");
		response.setHeader("Cache-Control","no-cache");
	}
	
	/**
	 * レスポンスのExpiresに設定するキャッシュを無効にするための時刻を取得します。
	 * @return
	 */
	private static String getHTTPDate() {
		final SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy hh:mm:ss zzz",java.util.Locale.US);
		formatter.setTimeZone(java.util.TimeZone.getTimeZone("GMT"));
		return formatter.format(new java.util.Date());
	}

	/**
	 * リクエストパラメータのログを出力します。
	 * @param request
	 * @param log
	 */
	public static void outLogRequestParameter(HttpServletRequest request, Logger log) {
		if (log.isInfoEnabled()) {
			Enumeration<?> parameterNames = request.getParameterNames();
			while (parameterNames.hasMoreElements()) {
				String parameterName = (String) parameterNames.nextElement();
				String[] parameterValues = request.getParameterValues(parameterName);
				log.info("parameterName : " + parameterName);
				if (parameterName != null) {
					for (int x = 0; x < parameterValues.length; x++) {
						final String parameterValue = parameterValues[x];
						log.info("parameterValue : " + parameterValue);
					}
				}
			}
		}
	}
	
	/**
	 * JSONをレスポンスします。
	 * @param response
	 * @param json
	 * @throws IOException
	 */
	public static void ajaxResponse(HttpServletResponse response, String json)
			throws IOException {
		response.setContentType("application/json; charset=UTF-8");
		final PrintWriter pw = response.getWriter();
//		pw.print(JsonUtils.toEncodeForJavaScript(json));
		pw.print(json);
		pw.flush();
		pw.close();
	}
}