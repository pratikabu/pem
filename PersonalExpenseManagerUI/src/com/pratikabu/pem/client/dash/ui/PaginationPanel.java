/**
 * 
 */
package com.pratikabu.pem.client.dash.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.pratikabu.pem.client.common.Utility;

/**
 * @author pratsoni
 *
 */
public class PaginationPanel extends HorizontalPanel {
	private PaginationComputer pc;
	
	private LinkButton firstButton, prevButton, nextButton, lastButton;
	
	private HTML currentPageLabel;
	
	private PaginationHandler handler;
	
	public PaginationPanel(PaginationHandler handler) {
		this.handler = handler;
		
		initializeObjects();
		placeObjects();
	}

	private void initializeObjects() {
		this.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		
		pc = new PaginationComputer(handler);
		
		firstButton = new LinkButton("<<");
		firstButton.setStyleName("paginationLinks");
		firstButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if(pc.isFirstPage()) {
					return;
				}
				pc.goToFirstPage();
			}
		});
		
		prevButton = new LinkButton("<");
		prevButton.setStyleName("paginationLinks");
		prevButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				pc.goToPreviousPage();
			}
		});
		
		nextButton = new LinkButton(">");
		nextButton.setStyleName("paginationLinks");
		nextButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				pc.goToNextPage();
			}
		});
		
		lastButton = new LinkButton(">>");
		lastButton.setStyleName("paginationLinks");
		lastButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if(pc.isLastPage()) {
					return;
				}
				pc.goToLastPage();
			}
		});
		
		currentPageLabel = new HTML("0 of 0");
	}

	public void refreshLinks() {
		firstButton.setEnabled(!pc.isFirstPage());
		prevButton.setEnabled(pc.hasPreviousPage());
		nextButton.setEnabled(pc.hasNextPage());
		lastButton.setEnabled(!pc.isLastPage());
		
		currentPageLabel.setHTML(Utility.getSafeHtml("<span class='normalLabel' style='font-size: 12px;'>" +
				pc.getCurrentPage() + " of " + pc.getTotalPages() + "</span>"));
	}

	private void placeObjects() {
		this.add(firstButton);
		this.add(prevButton);
		this.add(currentPageLabel);
		this.add(nextButton);
		this.add(lastButton);
	}

	private class LinkButton extends Anchor {
		public LinkButton(String text) {
			super(Utility.getSafeHtml("<b>" + text + "</b>"));
		}
	}
	
	public void setTotalPageCount(int totalCount, int elementsPerPage) {
		pc.initiatePP(totalCount, elementsPerPage);
	}
	
	public void setElementsPerPageCount(int elementsPerPage) {
		pc.initiatePP(pc.getTotalCount(), elementsPerPage);
	}
	
	/**
	 * This class is responsible to compute the paging calculations
	 * @author pratsoni
	 *
	 */
	public static class PaginationComputer {
		private int totalCount, currentPage, elementsPerPage;
		private PaginationHandler handler;
		private int totalPages, lastPageCount;

		public PaginationComputer(PaginationHandler handler) {
			this.handler = handler;
		}

		public void initiatePP(int totalCount, int elementsPerPage) {
			this.totalCount = totalCount;
			this.elementsPerPage = elementsPerPage;
			calculateCounts();
			if (totalPages != 0) {
				goToFirstPage();
			} else {
				goToPage(0);
			}
		}

		private void calculateCounts() {
			// get the total number of pages
			if (elementsPerPage != 0)
				totalPages = totalCount / elementsPerPage;
			else {
				totalPages = 0;
				return;
			}

			// get the total number of objects for last page
			int remaining = totalCount % elementsPerPage;
			if (remaining != 0) {
				lastPageCount = remaining;
				totalPages++;
			} else {
				lastPageCount = elementsPerPage;
			}
		}

		public int getTotalCount() {
			return totalCount;
		}

		public int getCurrentPage() {
			return currentPage;
		}

		public int getElementsPerPage() {
			return elementsPerPage;
		}

		public int getTotalPages() {
			return totalPages;
		}

		/**********************************************
		 * The functional methods will start form here**
		 **********************************************/

		/**
		 * This method will increase the count by one for the pagination panel
		 */
		public void increaseCount() {
			totalCount++;
			calculateCounts();
		}

		/**
		 * This method will decrease the count by one for the pagination panel
		 */
		public void decreaseCount() {
			totalCount--;
			calculateCounts();
		}

		/**
		 * This method is responsible for driving the pages. You can go to any
		 * specified page if it exists
		 * 
		 * @param pageNumber
		 */
		public void goToPage(int pageNumber) {
			if (pageNumber > 0 && pageNumber <= totalPages) {
				currentPage = pageNumber;
				int count = elementsPerPage;
				if (pageNumber == totalPages) {
					count = lastPageCount;
				}

				handler.renderPaginatedObjects((pageNumber - 1) * elementsPerPage
						+ 1, count);
			} else if (pageNumber == 0) {
				handler.renderPaginatedObjects(0, 0);
			}
		}

		public void goToNextPage() {
			int pageNumber = currentPage + 1;
			goToPage(pageNumber);
		}

		public void goToPreviousPage() {
			int pageNumber = currentPage - 1;
			goToPage(pageNumber);
		}

		public void goToFirstPage() {
			int pageNumber = 1;
			goToPage(pageNumber);
		}

		public void goToLastPage() {
			int pageNumber = totalPages;
			goToPage(pageNumber);
		}

		public boolean hasNextPage() {
			return currentPage < totalPages;
		}

		public boolean hasPreviousPage() {
			return currentPage > 1;
		}
		
		public boolean isFirstPage() {
			return currentPage == 1;
		}
		
		public boolean isLastPage() {
			return currentPage == totalPages;
		}
	} 
	
	/**
	 * Handler when the page is changed
	 * @author pratsoni
	 *
	 */
	public static interface PaginationHandler {

		void renderPaginatedObjects(int start, int count);

	}
}