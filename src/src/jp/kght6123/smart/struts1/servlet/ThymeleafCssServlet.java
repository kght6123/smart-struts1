package jp.kght6123.smart.struts1.servlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

@WebServlet(name="thymeleaf-css", urlPatterns={"/templates/css[module]/*"}, loadOnStartup=1,
	initParams={
		@WebInitParam(name="templateMode", value="CSS"),
		@WebInitParam(name="suffix", value=".css"),
		@WebInitParam(name="cacheable", value="false"),
		@WebInitParam(name="templatePath", value="/templates"),
		@WebInitParam(name="contentType", value="text/css"),
		@WebInitParam(name="encoding", value="utf-8")})

public class ThymeleafCssServlet extends ThymeleafServlet {}