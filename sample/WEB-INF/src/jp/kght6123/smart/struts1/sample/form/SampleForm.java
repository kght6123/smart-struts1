package jp.kght6123.smart.struts1.sample.form;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import jp.kght6123.smart.struts1.form.ValidateForm;
import jp.kght6123.smart.struts1.struct.ServletStructure;

public class SampleForm extends ValidateForm<SampleForm> {

	@Override
	protected ActionErrors doValidate(
			final ActionMapping mapping,
			final ServletStructure<SampleForm> structure,
			final ActionErrors errors) throws Exception {
		// FIXME Actionと同じ方式にして、mapping・structure・errorsを見えない用にする！
		// FIXME json化のときもセキュリティの為に使う。最悪、トレース情報からjackson呼び出しを検出して、nullを返すオーバーライドをするか？
		return errors;
	}
	@Override
	protected String getMessageKey() {
		return "sample.error";
	}
	
	private String text;
	private boolean check;

	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public boolean isCheck() {
		return check;
	}
	public void setCheck(boolean check) {
		this.check = check;
	}
}