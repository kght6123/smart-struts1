package jp.kght6123.smart.struts1.servlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

@WebServlet(name="thymeleaf-html", urlPatterns={"/templates/html[module]/*"}, loadOnStartup=1,
	initParams={
		@WebInitParam(name="templateMode", value="HTML"),
		@WebInitParam(name="suffix", value=".html"),
		@WebInitParam(name="cacheable", value="false"),
		@WebInitParam(name="templatePath", value="/templates"),
		@WebInitParam(name="contentType", value="text/html"),
		@WebInitParam(name="encoding", value="utf-8")})

public class ThymeleafHtmlServlet extends ThymeleafServlet {}