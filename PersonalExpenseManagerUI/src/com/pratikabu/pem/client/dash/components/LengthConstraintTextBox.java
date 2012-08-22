/**
 * 
 */
package com.pratikabu.pem.client.dash.components;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;

/**
 * @author pratsoni
 *
 */
public class LengthConstraintTextBox extends BasicValidationBox {
	private int maxLengthAllowed = -1;
	
	public LengthConstraintTextBox(int maxLengthAllowed) {
		this.maxLengthAllowed = maxLengthAllowed;
		
		this.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				if(LengthConstraintTextBox.this.maxLengthAllowed == -1 ||
						getText().length() <= LengthConstraintTextBox.this.maxLengthAllowed) {
					makeItNormal();
				} else {
					String msg = "Only " + LengthConstraintTextBox.this.maxLengthAllowed + " characters are allowed.";
					errorOut(msg);
				}
			}
		});
	}
}
