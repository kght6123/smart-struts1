package jp.kght6123.smart.struts1.servlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

@WebServlet(name="thymeleaf-js", urlPatterns={"/templates/js[module]/*"}, loadOnStartup=1,
	initParams={
		@WebInitParam(name="templateMode", value="JAVASCRIPT"),
		@WebInitParam(name="suffix", value=".js"),
		@WebInitParam(name="cacheable", value="false"),
		@WebInitParam(name="templatePath", value="/templates"),
		@WebInitParam(name="contentType", value="text/javascript"),
		@WebInitParam(name="encoding", value="utf-8")})

public class ThymeleafJsServlet extends ThymeleafServlet {}