/**
 * 
 */
package com.pratikabu.pem.client.dash.components;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.pratikabu.pem.client.common.Utility;

/**
 * @author pratsoni
 *
 */
public class AmountTextBox extends BasicValidationBox {
	private boolean negativeAllowed = true;
	private AmountChangeListener amountChangeListener;
	
	public AmountTextBox(boolean negativeAllowed) {
		this.negativeAllowed = negativeAllowed;
		
		this.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				setAmount(getAmount());
				invokeAmountChanged();
			}
		});
		
		this.setAmount(0.0);
	}
	
	public void setAmount(Double amt) {
		if(amt == null) {
			String msg = "This box expects only numbers.";
			if(!negativeAllowed) {
				msg += " Negative values are not allowed.";
			}
			errorOut(msg);
		} else {
			this.setText(Utility.amountFormatter.format(amt));
			makeItNormal();
		}
	}
	
	public Double getAmount() {
		try {
			String txt = this.getText();
			if(txt.trim().isEmpty()) {
				txt = "0";
			}
			double amt = Utility.amountFormatter.parse(txt);
			if(!negativeAllowed && 0 > amt) {
				return null;
			}
			
			return amt;
		} catch(Exception e) {
			return null;
		}
	}

	private void invokeAmountChanged() {
		if(null == amountChangeListener) {
			return;
		}
		
		Double amt = getAmount();
		if(null != amt) {
			amountChangeListener.amountChanged(amt);
		}
	}
	
	public void setAmountChangeListener(AmountChangeListener amountChangeListener) {
		this.amountChangeListener = amountChangeListener;
	}

	public static interface AmountChangeListener {
		void amountChanged(Double newValue);
	}
}
