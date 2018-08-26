package jp.kght6123.smart.struts1.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import jp.kght6123.smart.struts1.form.Form;

public class ThymeleafServlet extends HttpServlet {

	private final TemplateEngine engine = new TemplateEngine();
	private String templatePath;
	private String contentType;
	private String encoding;
	
	@Override
	public void init() throws ServletException {
		
		final String templateMode = getServletConfig().getInitParameter("templateMode");
		
		final String prefix = getServletConfig().getInitParameter("templatePath");
		final String suffix = getServletConfig().getInitParameter("suffix");
		final String cacheable = getServletConfig().getInitParameter("cacheable");
		
		final ServletContextTemplateResolver resolver = 
				new ServletContextTemplateResolver(getServletContext());
		
		if(prefix != null) resolver.setPrefix(prefix);
		if(suffix != null) resolver.setSuffix(suffix);
		if(templateMode != null) resolver.setTemplateMode(TemplateMode.valueOf(templateMode));
		if(cacheable != null) resolver.setCacheable(Boolean.parseBoolean(cacheable));
		
		this.engine.setTemplateResolver(resolver);
		
		this.templatePath = prefix;
		this.contentType = getServletConfig().getInitParameter("contentType");
		this.encoding = getServletConfig().getInitParameter("encoding");
	}
	
	@Override
	public void destroy() {
		this.engine.clearTemplateCache();
	}
	
	@Override
	protected void service(
			final HttpServletRequest req,
			final HttpServletResponse resp) throws ServletException, IOException {
		
		final String today = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss.SSS").format(Calendar.getInstance().getTime());
		
		final WebContext ctx = new WebContext(req, resp, getServletContext(), req.getLocale());
		ctx.setVariable("today", today);
		
		for (final String attrName : Collections.list(req.getAttributeNames())) {
			final Object obj = req.getAttribute(attrName);
			if(obj instanceof Form)
				ctx.setVariable(attrName, req.getAttribute(attrName));
		}
		
		final String templatePath = 
				req.getServletPath() + req.getPathInfo();
		
		final String module = resp.getHeader("_module");//resp.getContentType();
		
		// templatePathを対象のModule用に置き換え
		final String templateReplacePath = 
				findReplacePath(REPLACE_CHAR_MODULE, module, templatePath, "");
		
		if(this.contentType != null) resp.setContentType(this.contentType);
		if(this.encoding != null) resp.setCharacterEncoding(this.encoding);
		
		this.engine.process(templateReplacePath.replaceFirst(this.templatePath, ""), ctx, resp.getWriter());
	}
	
	public static final String REPLACE_CHAR_MODULE = "\\[module\\]";
	
	public static String findReplacePath(final String fromReplaceStr, final String toReplaceStr, final String replacePath, final String defaultStr) {
		
		// templatePathを対象のModule用に置き換え
		if(StringUtils.isEmpty(toReplaceStr)) {
			// Moduleがnullのとき
			return replacePath.replaceAll(fromReplaceStr, defaultStr);
		} else {
			// Moduleがnull以外のとき
			return replacePath.replaceAll(fromReplaceStr, toReplaceStr);
		}
	}
}