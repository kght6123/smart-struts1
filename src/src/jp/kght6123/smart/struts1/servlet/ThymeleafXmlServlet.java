package jp.kght6123.smart.struts1.servlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

@WebServlet(name="thymeleaf-xml", urlPatterns={"/templates/xml[module]/*"}, loadOnStartup=1,
	initParams={
		@WebInitParam(name="templateMode", value="XML"),
		@WebInitParam(name="suffix", value=".xml"),
		@WebInitParam(name="cacheable", value="false"),
		@WebInitParam(name="templatePath", value="/templates"),
		@WebInitParam(name="contentType", value="application/xml"),
		@WebInitParam(name="encoding", value="utf-8")})

public class ThymeleafXmlServlet extends ThymeleafServlet {}