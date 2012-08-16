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
import com.pratikabu.pem.client.common.Utility;
import com.pratikabu.pem.client.dash.PaneManager;
import com.pratikabu.pem.client.dash.components.TransactionDatabase;
import com.pratikabu.pem.shared.model.TransactionDTO;

/**
 * @author pratsoni
 *
 */
public class TransactionList extends VerticalPanel {
	private CellList<TransactionDTO> tgList;
	
	
	public TransactionList() {
		TransactionCell tgCell = new TransactionCell();
		tgList = new CellList<TransactionDTO>(tgCell);

		tgList.setPageSize(30);
		tgList.setKeyboardPagingPolicy(KeyboardPagingPolicy.INCREASE_RANGE);
		tgList.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.BOUND_TO_SELECTION);
		tgList.setLoadingIndicator(Utility.getLoadingWidget());
		
		TransactionDatabase.get().addDataDisplay(tgList);
		
		ScrollPanel sp = new ScrollPanel(tgList);
		sp.setHeight("100%");

		// Add a selection model so we can select cells.
		final SingleSelectionModel<TransactionDTO> selectionModel = new SingleSelectionModel<TransactionDTO>(
				TransactionDatabase.KEY_PROVIDER);
		tgList.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				TransactionDTO dto = selectionModel.getSelectedObject();
				PaneManager.renderTransactionDetails(dto.getTransactionId(), dto.getEntryType());
			}
		});
		
		this.setWidth("100%");
		this.setHeight("100%");
		
		this.add(sp);
	}
}
