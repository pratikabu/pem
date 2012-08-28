/**
 * 
 */
package com.pratikabu.pem.client.common;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author pratsoni
 *
 */
public class MessageDialog extends DialogBox {
	private StringBuilder sb;
	
	private HTML htmlContent;
	private Button okButton;
	
	private boolean alertBox = false;
	
	private MessageDialog(boolean alertBox) {
		this.alertBox = alertBox;
		initializeObjects();
		placeObjects();
	}

	private void initializeObjects() {
		this.setModal(true);
		this.setText("Message");
		this.setAnimationEnabled(true);
		this.setGlassEnabled(true);
		int[] coordinates = Utility.getCenterOfWindow(500, 300);
		this.setPopupPosition(coordinates[1], coordinates[0]);
		if(alertBox) {
			this.setStylePrimaryName(Constants.CSS_MESSAGE_ALERT);
			this.setAutoHideEnabled(true);
		}
		
		htmlContent = new HTML();
		htmlContent.setSize("100%", "100%");
		if(alertBox) {
			htmlContent.getElement().getStyle().setPropertyPx("minWidth", 200);
		} else {
			htmlContent.getElement().getStyle().setPropertyPx("minWidth", 300);
			htmlContent.getElement().getStyle().setPropertyPx("minHeight", 200);
		}
		Utility.updateNameAndId(htmlContent, "messageContent");
		
		if(!alertBox) {
			okButton = Utility.getActionButton("Got It!");
			Utility.updateNameAndId(okButton, "okayMessage");
			okButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					hide();
				}
			});
			okButton.addKeyDownHandler(new KeyDownHandler() {
				@Override
				public void onKeyDown(KeyDownEvent event) {
					if(Constants.KEY_ESCAPE == event.getNativeKeyCode()) {
						hide();
					}
				}
			});
		}
	}

	private void placeObjects() {
		if(alertBox) {
			this.setWidget(htmlContent);
		} else {
			VerticalPanel vp = new VerticalPanel();
			vp.add(htmlContent);
			
			HorizontalPanel hp = new HorizontalPanel();
			hp.setWidth("100%");
			hp.getElement().getStyle().setPaddingBottom(5, Unit.PX);
			hp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
			hp.add(okButton);
			
			vp.add(hp);
			this.setWidget(vp);
		}
	}
	
	private StringBuilder getSB() {
		if(null == sb) {
			sb = new StringBuilder();
		}
		return sb;
	}

	@Override
	public void show() {
		// add message to the html pane
		addMessageToPane();
		
		super.show();
		
		if(!alertBox) {
			okButton.setFocus(true);
		}
	}

	private void addMessageToPane() {
		String content = "<p class='readerPTag'";
		if(alertBox) {
			content += " align='center'";
		}
		content += ">";
		content += getSB().toString();
		content += "</p>";
		htmlContent.setHTML(Utility.getSafeHtml(content));
	}
	
	public MessageDialog print(String message) {
		getSB().append(message);
		return this;
	}
	
	public MessageDialog println(String message) {
		getSB().append(message);
		getSB().append("<br/>");
		return this;
	}

	public static MessageDialog get() {
		return new MessageDialog(false);
	}
	
	/**
	 * A small popup will be opened and closed automatically.
	 * @param msg
	 */
	public static void alert(String msg) {
		final MessageDialog md = new MessageDialog(true).print(msg);
		int[] pos = Utility.getCenterOfWindow(80, 200);
		md.setPopupPosition(pos[1], pos[0]);
		md.setGlassEnabled(false);
		md.setModal(false);
		md.setText(null);
		md.show();
		Timer t = new Timer() {
			@Override
			public void run() {
				md.hide();
			}
		};
		t.schedule(3000);
	}
}
