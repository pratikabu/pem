/**
 * 
 */
package com.pratikabu.pem.client.dash.components;

import com.google.gwt.user.client.ui.TextBox;
import com.pratikabu.pem.client.common.Constants;

/**
 * @author pratsoni
 *
 */
public abstract class BasicValidationBox extends TextBox {
	private String backgroundColor = "WHITE";
	private static final String ERROR_COLOR = "#FFDBDB";
	
	private boolean isValid = true;
	private String errorMessage;
	
	public BasicValidationBox() {
		this.setStyleName(Constants.CSS_NORMAL_TEXT);
		backgroundColor = this.getElement().getStyle().getBackgroundColor();
		makeItNormal();
	}
	
	/**
	 * This method will show error in this TextBox
	 * @param msg
	 */
	public void errorOut(String msg) {
		setCss(msg);
	}
	
	public void makeItNormal() {
		setCss("");
	}

	private void setCss(String msg) {
		String background = ERROR_COLOR;
		isValid = false;
		if(msg != null && msg.isEmpty()) {
			background = backgroundColor;
			isValid = true;
		}
		
		this.getElement().getStyle().setBackgroundColor(background);
		this.setTitle(msg);
		this.errorMessage = msg;
	}
	
	public boolean isValid() {
		return isValid;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}
