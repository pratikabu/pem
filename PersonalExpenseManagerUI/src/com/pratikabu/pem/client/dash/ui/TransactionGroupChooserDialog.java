/**
 * 
 */
package com.pratikabu.pem.client.dash.ui;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.HasKeyboardPagingPolicy.KeyboardPagingPolicy;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.pratikabu.pem.client.common.Constants;
import com.pratikabu.pem.client.common.Utility;
import com.pratikabu.pem.client.dash.components.TransactionGroupDatabase;
import com.pratikabu.pem.shared.model.TransactionGroupDTO;

/**
 * @author pratsoni
 *
 */
public class TransactionGroupChooserDialog extends DialogBox {
	
	private static TransactionGroupChooserDialog ac;
	
	private CellList<TransactionGroupDTO> tgList;
	
	private Button selectButton, closeButton;
	
	private TransactionGroupDTO selectedTGDTO;
	private TransactionGroupSelectionListener listener;
	
	public static void chooseSingleAccount(TransactionGroupSelectionListener listener) {
		get().listener = listener;
		get().show();
	}
	
	public static TransactionGroupChooserDialog get() {
		if(null == ac) {
			ac = new TransactionGroupChooserDialog();
		}
		
		return ac;
	}
	
	private TransactionGroupChooserDialog() {
		initializeObjects();
		placeObjects();
	}
	
	private void initializeObjects() {
		this.setModal(true);
		this.setText("Select a Transaction Group");
		this.setAnimationEnabled(true);
		this.setGlassEnabled(true);
		int[] coordinates = Utility.getCenterOfWindow(500, 300);
		this.setPopupPosition(coordinates[1], coordinates[0]);
		
		tgList = new CellList<TransactionGroupDTO>(new TGCell(), TransactionGroupDatabase.KEY_PROVIDER);
		tgList.setPageSize(30);
		
		tgList.setKeyboardPagingPolicy(KeyboardPagingPolicy.INCREASE_RANGE);
		tgList.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.BOUND_TO_SELECTION);
		tgList.setLoadingIndicator(Utility.getLoadingWidget());
		
		TransactionGroupDatabase.get().addDataDisplay(tgList);
		
		// Add a selection model so we can select cells.
		final SingleSelectionModel<TransactionGroupDTO> selectionModel = new SingleSelectionModel<TransactionGroupDTO>(
				TransactionGroupDatabase.KEY_PROVIDER);
		tgList.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				selectedTGDTO = selectionModel.getSelectedObject();
			}
		});
		
		selectButton = Utility.getActionButton("Select & Close");
		selectButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if(null == selectedTGDTO) {
					Utility.alert("Please select any account.");
					return;
				}
				
				if(null != listener) {
					listener.transactionGroupSelectedEvent(selectedTGDTO);
				}
				
				get().hide();
			}
		});
		
		closeButton = Utility.getNormalButton("Close");
		closeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				get().hide();
			}
		});
	}
	
	private void placeObjects() {
		VerticalPanel vp = new VerticalPanel();
		vp.setSpacing(5);
		
		ScrollPanel sp = new ScrollPanel(tgList);
		sp.setStyleName(Constants.CSS_NORMAL_TEXT);
		sp.setWidth("300px");
		sp.setHeight("300px");
		vp.add(sp);
		
		vp.add(Utility.addHorizontally(5, selectButton, closeButton));
		
		this.setWidget(vp);
	}
	
	private class TGCell extends AbstractCell<TransactionGroupDTO> {

		@Override
		public void render(Context context,
				TransactionGroupDTO value, SafeHtmlBuilder sb) {
			// Value can be null, so do a null check..
			if (value == null) {
				return;
			}

			sb.appendHtmlConstant("<span class='normalLabel' style='font-size: 16px'>");
			if(-1 == value.getId()) {
				sb.appendHtmlConstant(value.getTgName());
			} else {
				sb.appendHtmlConstant(value.getTgNameWithCount());
			}
			sb.appendHtmlConstant("</span>");
		}

	}
	
	public static interface TransactionGroupSelectionListener {
		void transactionGroupSelectedEvent(TransactionGroupDTO dto);
	}
}
