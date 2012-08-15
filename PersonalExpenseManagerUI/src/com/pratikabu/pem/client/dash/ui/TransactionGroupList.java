/**
 * 
 */
package com.pratikabu.pem.client.dash.ui;

import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.HasKeyboardPagingPolicy.KeyboardPagingPolicy;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.pratikabu.pem.client.dash.PaneManager;
import com.pratikabu.pem.shared.model.TgCellData;

/**
 * @author pratsoni
 *
 */
public class TransactionGroupList extends VerticalPanel {
	private CellList<TgCellData> tgList;
	
	
	public TransactionGroupList() {
		TransactionGroupCell tgCell = new TransactionGroupCell();
		tgList = new CellList<TgCellData>(tgCell);

		tgList.setPageSize(30);
		tgList.setKeyboardPagingPolicy(KeyboardPagingPolicy.INCREASE_RANGE);
		tgList.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.BOUND_TO_SELECTION);
		
		TransactionDatabase.get().addDataDisplay(tgList);
		
		ScrollPanel sp = new ScrollPanel(tgList);
		sp.setHeight("100%");

		// Add a selection model so we can select cells.
		final SingleSelectionModel<TgCellData> selectionModel = new SingleSelectionModel<TgCellData>(
				TransactionDatabase.KEY_PROVIDER);
		tgList.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				PaneManager.renderTransactionDetails(selectionModel.getSelectedObject().getTgId());
			}
		});
		
		this.setWidth("100%");
		this.setHeight("100%");
		
		this.add(sp);
	}
}
