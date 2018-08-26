package jp.kght6123.smart.struts1.sample.action;

import jp.kght6123.smart.struts1.action.Action;
import jp.kght6123.smart.struts1.sample.form.SampleForm;
import jp.kght6123.smart.struts1.sample.logic.SampleLogic;

public class SampleAction extends Action<SampleForm, SampleLogic> {
	
	@Override
	public Controller<SampleForm, SampleLogic> controller() {
		return new SampleController();
	}
	
	@Override
	public SampleLogic logic() {
		return new SampleLogic();
	}
}