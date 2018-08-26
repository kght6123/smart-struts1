package jp.kght6123.smart.struts1.form;

import java.io.Serializable;

import jp.kght6123.smart.struts1.Constants.HttpMethod;

public interface FormImpl extends Serializable
{
	/**
	 * イベント名を取得する.
	 * 
	 * @return イベント名
	 */
	public HttpMethod getMethod();

	/**
	 * イベント名を設定する.
	 * 
	 * @param event イベント名
	 */
	public void setMethod(String event);
	
}