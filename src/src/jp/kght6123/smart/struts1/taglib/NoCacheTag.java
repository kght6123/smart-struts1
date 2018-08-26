package jp.kght6123.smart.struts1.taglib;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * キャッシュ無効タグ
 */
public class NoCacheTag extends TagSupport
{
	private static final long serialVersionUID = 1L;
	
	public NoCacheTag() {}
	
	public int doStartTag() throws JspException {
		return SKIP_BODY;
	}
	public int doEndTag() throws JspException {
		final ServletResponse r = super.pageContext.getResponse();
		
		if(r instanceof HttpServletResponse)
		{
			final HttpServletResponse res = (HttpServletResponse)r;
			res.setHeader("Cache-Control", "no-cache");
			res.setHeader("Cache-Control", "no-store");
			res.setHeader("Cache-Control", "must-revalidate");
			res.setHeader("Cache-Control", "pre-check=0");
			res.setHeader("Cache-Control", "post-check=0");
			res.setHeader("Pragma", "no-cache");
			res.setHeader("Expires", "-1");
		}
		return EVAL_PAGE;
	}
	public void release() {
		super.release();
	}
}