/**
 * 
 */
package com.pratikabu.pem.client.common;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasName;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author pratsoni
 *
 */
public class Utility {
	
	/**
	 * Set the id of the widget to the specified one.
	 * Also if the widget has the name property then it will set it too.
	 * @param w
	 * @param name
	 */
	public static void updateNameAndId(Widget w, String name) {
		updateNameAndId(w, name, name);
	}
	
	/**
	 * If we want to add specific ids and name
	 * @param w
	 * @param id
	 * @param name
	 */
	public static void updateNameAndId(Widget w, String id, String name) {
		w.getElement().setId(id);
		if(w instanceof HasName) {
			((HasName)w).setName(name);
		}
	}
	
	/**
	 * Get the error label to show errors.
	 * @param text
	 * @return
	 */
	public static Label getErrorLabel(String text) {
		return getLabel(text, "errLabel");
	}
	
	/**
	 * Get you a label with default label style.
	 * @param text
	 * @return
	 */
	public static Label getLabel(String text) {
		return getLabel(text, Constants.CSS_NORMAL_LABEL);
	}
	
	/**
	 * Get you a label with the specified looks.
	 * @param text
	 * @param styleName
	 * @return
	 */
	public static Label getLabel(String text, String styleName) {
		Label label = new Label(text);
		
		if (null != styleName) {
			label.setStyleName(styleName);
		}
		return label;
	}
	
	/**
	 * This method will add widgets in horizontal fashion and return the widget back.
	 * @param spacing
	 * @param widgets
	 * @return
	 */
	public static Widget addHorizontally(int spacing, Widget... widgets) {
		HorizontalPanel hp = new HorizontalPanel();
		
		if (-1 != spacing) {
			hp.setSpacing(spacing);
		}
		
		if(null != widgets) {
			for (Widget w : widgets) {
				hp.add(w);
			}
		}
		
		return hp;
	}

	public static void setAutoCompleteOff(Element element) {
		element.setAttribute("autocomplete", "off");
	}
	
	public static boolean isEmptyValidation(TextBoxBase... boxes) {
		if(null != boxes) {
			for(TextBoxBase t : boxes) {
				if(null == t.getText() || t.getText().isEmpty()) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean isValidEmail(String email) {
		email = email.trim();
		int atIndex = email.indexOf('@');
		int dotIndex = email.indexOf('.');
		
		return -1 != atIndex && atIndex > 0 && -1 != dotIndex &&
				dotIndex > (atIndex + 1) && dotIndex != (email.length() - 1);
	}
	
	public static Button getActionButton(String text) {
		return getButton(text, "actionButton");
	}
	
	public static Button getNormalButton(String text) {
		return getButton(text, "normalButton");
	}
	
	public static Button getButton(String text, String styleName) {
		Button b = new Button(text);
		if(null != styleName) {
			b.setStyleName(styleName);
		}
		updateNameAndId(b, text.replaceAll(" ", ""));
		return b;
	}
	
	public static TextBox getTextBox(String defaultText) {
		TextBox t = new TextBox();
		t.setStyleName(Constants.CSS_NORMAL_TEXT);
		t.setText(defaultText);
		
		return t;
	}
	
	public static ListBox getListBox(boolean isMultipleSelect) {
		ListBox lb = new ListBox(isMultipleSelect);
		return lb;
	}

	public static void navigateRelative(String results) {
		// TODO Auto-generated method stub
		System.out.println(results);
	}
}
