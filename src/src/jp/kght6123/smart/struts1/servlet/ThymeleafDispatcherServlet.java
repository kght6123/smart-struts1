package jp.kght6123.smart.struts1.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import jp.kght6123.smart.struts1.Constants.HttpContentType;

@WebServlet(name="thymeleaf-dispacher", urlPatterns={"/templates/[content-type][module]/*"})
public class ThymeleafDispatcherServlet extends HttpServlet {

	private static final String REPLACE_CHAR_CONTENT_TYPE = "\\[content-type\\]";
	
	private static String findReplacePath(final String fromReplaceStr, final String toReplaceStr, final String replacePath, final String defaultStr) {
		
		// templatePathを対象のContent-Type用に置き換え
		if(StringUtils.isEmpty(toReplaceStr)) {
			// Content-Typeがnullのとき
			return replacePath.replaceAll(fromReplaceStr, defaultStr);
		} else {
			// Content-Typeがnull以外のとき
			for(final HttpContentType type : HttpContentType.values()) {
				if(toReplaceStr.equalsIgnoreCase(type.name())) {
					// Content-Typeが一致したら置き換え
					return replacePath.replaceAll(fromReplaceStr, type.name());
				}
			}
		}
		return null;
	}
	
	@Override
	protected void service(
			final HttpServletRequest req,
			final HttpServletResponse resp) throws ServletException, IOException {
		
		final String templatePath = 
				req.getServletPath() + req.getPathInfo();
		
		final String conentType = resp.getHeader("_type");
		
		// templatePathを対象のContent-Type用に置き換え
		final String templateReplacePath = 
				ThymeleafDispatcherServlet.findReplacePath(REPLACE_CHAR_CONTENT_TYPE, conentType, templatePath, HttpContentType.html.name());
		
		final String module = resp.getHeader("_module");
		resp.addHeader("_module", module);
		
		if(templateReplacePath != null) {
			// templatePathの置き換えができたら、フォワードする
			final RequestDispatcher dispatch = req.getRequestDispatcher(templateReplacePath);
			dispatch.forward(req, resp);
			
		} else if(templatePath.indexOf(REPLACE_CHAR_CONTENT_TYPE.replaceAll("\\", "")) != -1) {
			// 置き換えが不要なとき、フォワードする
			final RequestDispatcher dispatch = req.getRequestDispatcher(templatePath);
			dispatch.forward(req, resp);
			
		} else {
			// 置き換えできていないとき、Exceptionにする
			throw new ServletException("not support RequestParameter _type="+ conentType +".");
		}
	}
}