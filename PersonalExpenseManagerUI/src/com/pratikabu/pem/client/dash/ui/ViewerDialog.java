/**
 * 
 */
package com.pratikabu.pem.client.dash.ui;

import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author pratsoni
 *
 */
public class ViewerDialog extends DialogBox {
	private static ViewerDialog vd;
	
	public static void showWidget(Widget w, String title) {
		get();
		vd.setText(title);
		vd.clear();
		vd.setWidget(w);
		if(!vd.isShowing()) {
			vd.show();
		}
		vd.center();
	}
	
	public static ViewerDialog get() {
		if(null == vd) {
			vd = new ViewerDialog();
		}
		return vd;
	}

	private ViewerDialog() {
		this.setAnimationEnabled(true);
		this.setAutoHideEnabled(true);
		this.setGlassEnabled(true);
		this.getGlassElement().getStyle().setBackgroundColor("#B4B4B4");
		
		this.addCloseHandler(new CloseHandler<PopupPanel>() {
			@Override
			public void onClose(CloseEvent<PopupPanel> event) {
				if(event.isAutoClosed()) {
				}
			}
		});
	}
	
	public void close() {
		hide();
	}
}
