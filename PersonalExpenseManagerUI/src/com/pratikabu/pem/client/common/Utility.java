/**
 * 
 */
package com.pratikabu.pem.client.common;

import java.util.Date;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasName;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

/**
 * @author pratsoni
 *
 */
public class Utility {
	public static final NumberFormat amountFormatter = NumberFormat.getFormat("#,##0.00");
	
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
				t.setText(t.getText().trim());
				if(null == t.getText() || t.getText().isEmpty()) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean isValidEmail(String email) {
		return email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}$");
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
		b.setTitle(text);
		updateNameAndId(b, text.replaceAll(" ", ""));
		return b;
	}
	
	public static TextBox getTextBox(String defaultText) {
		TextBox t = new TextBox();
		t.setStyleName(Constants.CSS_NORMAL_TEXT);
		t.setText(defaultText);
		
		return t;
	}
	
	public static TextArea getTextArea() {
		TextArea t = new TextArea();
		t.setStyleName(Constants.CSS_NORMAL_TEXT);
		t.getElement().getStyle().setProperty("resize", "none");
		return t;
	}
	
	public static ListBox getListBox(boolean isMultipleSelect) {
		ListBox lb = new ListBox(isMultipleSelect);
		lb.setStyleName(Constants.CSS_NORMAL_TEXT);
		return lb;
	}

	/**
	 * redirect the browser to the given url
	 * @param results
	 */
	public static native void navigateRelative(String url)/*-{
		$wnd.location = url;
	}-*/;

	public static String get2DecimalAmount(double amount) {
		return amountFormatter.format(amount);
	}
	
	public static String getPrintableAmountWithSign(String symbol, double amount) {
		return symbol + " " + get2DecimalAmount(amount);
	}
	
	public static String getDateFormatted(Date date) {
		return formatDate(date, "dd MMM, yyyy");
	}
	
	public static String formatDate(Date date, String formattingString) {
		if(null == date) {
			return "null";
		}
		DateTimeFormat formatter = DateTimeFormat.getFormat(formattingString);
		return formatter.format(date);
	}
	
	public static SafeHtml getSafeHtml(String readerContent) {
		return SafeHtmlUtils.fromSafeConstant(readerContent);
	}
	
	public static Widget getLoadingWidget() {
		return getLabel("Loading...");
	}
	
	public static DateBox getDateBox() {
		DateTimeFormat formatter = DateTimeFormat.getFormat("dd/MMM/yyyy");
		DateBox db = new DateBox();
		db.setFormat(new DateBox.DefaultFormat(formatter));
		db.setStyleName(Constants.CSS_NORMAL_TEXT);
		db.getElement().getStyle().setMargin(0, Unit.PX);
		
		return db;
	}
	
	public static int[] getCenterOfWindow(int reqHeight, int reqWidth) {
		int browserHeight = Window.getClientHeight();
		int browserWidth = Window.getClientWidth();
		
		int top = 0, left = 0;
		if (browserHeight > reqHeight) {
			top = (browserHeight - reqHeight) / 2;
		}
		
		if(browserWidth > reqWidth) {
			left = (browserWidth - reqWidth) / 2;
		}
		
		return new int[] {top, left};
	}

	public static void alert(String text) {
		MessageDialog.alert(text);
	}

	/**
	 * This method will populate genders in the passed list.
	 * @param genderList
	 */
	public static void populateGender(ListBox genderList) {
		genderList.addItem("Male", "m");
		genderList.addItem("Female", "f");
	}
}
