/**
 * 
 */
package com.pratikabu.pem.client.dash.components;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.pratikabu.pem.client.common.Utility;
import com.pratikabu.pem.client.dash.PaneManager;
import com.pratikabu.pem.client.dash.ui.PaginationPanel;
import com.pratikabu.pem.client.dash.ui.PaginationPanel.PaginationHandler;

/**
 * @author pratsoni
 *
 */
public class PaginationManager {
	private static PaginationManager pm;
	
	private PaginationPanel pager;
	
	private int maxResultCount = 10;
	private int endPos;
	
	public static PaginationManager get() {
		if(null == pm) {
			pm = new PaginationManager();
		}
		
		return pm;
	}
	
	private PaginationManager() {
		
	}
	
	public void placePaginationComponents() {
		final ListBox maxResultBox = Utility.getListBox(false);
		
		for(int size = 10; size <= 50; size += 10) {
			maxResultBox.addItem(size + "");
		}
		
		maxResultBox.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				maxResultCount = Integer.parseInt(maxResultBox.getValue(maxResultBox.getSelectedIndex()));
				pager.setElementsPerPageCount(maxResultCount);
			}
		});
		
		pager = new PaginationPanel(new PaginationHandler() {
			@Override
			public void renderPaginatedObjects(int start, int count) {
				start--;
				if(-1 == start) {
					return;
				}
				TransactionDatabase.get().fetchTransactionsAndShow(start, count);
				pager.refreshLinks();
			}
		});
		PaneManager.setInId(pager, "idPagination");
		
		HorizontalPanel hp = new HorizontalPanel();
		hp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		hp.add(Utility.getLabel("Results/Page"));
		hp.add(maxResultBox);
		hp.add(pager);
		
		PaneManager.setInId(hp, "idPagination");
	}

	public PaginationPanel getPager() {
		return pager;
	}

	public int getMaxResultCount() {
		return maxResultCount;
	}

	public int getEndPos() {
		return endPos;
	}

	public void setEndPos(int endPos) {
		this.endPos = endPos;
	}
}
