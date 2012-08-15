/**
 * 
 */
package com.pratikabu.pem.client.dash.components;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.user.client.ui.TextBox;
import com.pratikabu.pem.client.common.Constants;
import com.pratikabu.pem.client.common.Utility;

/**
 * @author pratsoni
 *
 */
public class AmountTextBox extends TextBox {
	private String backgroundColor = "WHITE";
	
	public AmountTextBox() {
		this.setStyleName(Constants.CSS_NORMAL_TEXT);
		backgroundColor = this.getElement().getStyle().getBackgroundColor();
		this.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				setAmount(getAmount());
			}
		});
	}
	
	public void setAmount(Double amt) {
		if(amt == null) {
			this.getElement().getStyle().setBackgroundColor("#FFDBDB");
		} else {
			this.getElement().getStyle().setBackgroundColor(backgroundColor);
			this.setText(Utility.amountFormatter.format(amt));
		}
	}
	
	public Double getAmount() {
		try {
			return Utility.amountFormatter.parse(this.getText());
		} catch(Exception e) {
			return null;
		}
	}
}
