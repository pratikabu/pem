/**
 * 
 */
package com.pratikabu.pem.client.dash.components;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.pratikabu.pem.client.common.Utility;
import com.pratikabu.pem.client.dash.PaneManager;

/**
 * @author pratsoni
 *
 */
public class PaginationManager {
	private static PaginationManager pm;
	
	private SimplePager pager;
	
	private int maxResultCount = 10;
	
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
				TransactionDatabase.get().refreshDisplays(null);
			}
		});
		
		pager = new SimplePager(TextLocation.CENTER);
		PaneManager.setInId(pager, "idPagination");
		
		HorizontalPanel hp = new HorizontalPanel();
		hp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		hp.add(Utility.getLabel("Results/Page"));
		hp.add(maxResultBox);
		hp.add(pager);
		
		PaneManager.setInId(hp, "idPagination");
	}

	public SimplePager getPager() {
		return pager;
	}

	public int getMaxResultCount() {
		return maxResultCount;
	}
}
