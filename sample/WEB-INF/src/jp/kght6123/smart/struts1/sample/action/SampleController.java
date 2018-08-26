package jp.kght6123.smart.struts1.sample.action;

import org.apache.struts.action.ActionForward;

import jp.kght6123.smart.struts1.action.Action.Controller;
import jp.kght6123.smart.struts1.sample.form.SampleForm;
import jp.kght6123.smart.struts1.sample.logic.SampleLogic;

public class SampleController extends Controller<SampleForm, SampleLogic> {
	
	@Override
	public ActionForward doGet() throws Exception {
		
		return this.findForward("sample");
	}
	@Override
	public ActionForward doPost() throws Exception {
		
		return this.findForward("sample");
	}
	@Override
	public ActionForward doDelete() throws Exception {
		
		return this.findForward("sample");
	}
	@Override
	public ActionForward doPut() throws Exception {
		
		return this.findForward("sample");
	}
}