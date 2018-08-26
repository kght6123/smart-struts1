package jp.kght6123.smart.struts1.servlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

@WebServlet(name="thymeleaf-json", urlPatterns={"/templates/json[module]/*"}, loadOnStartup=1,
	initParams={
		@WebInitParam(name="templateMode", value="JAVASCRIPT"),
		@WebInitParam(name="suffix", value=".json"),
		@WebInitParam(name="cacheable", value="false"),
		@WebInitParam(name="templatePath", value="/templates"),
		@WebInitParam(name="contentType", value="application/json"),
		@WebInitParam(name="encoding", value="utf-8")})

public class ThymeleafJsonServlet extends ThymeleafServlet {}