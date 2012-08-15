/**
 * 
 */
package com.pratikabu.pem.client.dash.ui;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.pratikabu.pem.client.common.Utility;
import com.pratikabu.pem.shared.OneTimeData;
import com.pratikabu.pem.shared.model.TgCellData;

/**
 * @author pratsoni
 * 
 */
public class TransactionGroupCell extends AbstractCell<TgCellData> {

	@Override
	public void render(com.google.gwt.cell.client.Cell.Context context,
			TgCellData value, SafeHtmlBuilder sb) {
		// Value can be null, so do a null check..
		if (value == null) {
			return;
		}

		String currencySymbol = OneTimeData.getCurrecnySymbol();
		String amountStr = Utility.get2DecimalAmount(value.getAmount());
		String formattedDate = Utility.getDateFormatted(value.getDate());
		String title = "You spent " + currencySymbol + " " + amountStr + " on " + formattedDate;
		String tgCellIconStyle = "tgCellIconOut";
		String heading = value.getName();
		String tgTableStyle = "tgCellTableOut";
		char entrySymbol = '►';
		
		if(TgCellData.ET_INWARD_TG == value.getEntryType()) {
			tgCellIconStyle = "tgCellIconIn";
			tgTableStyle = "tgCellTableIn";
			entrySymbol = '◄';
			title = "You recieved " + currencySymbol + " " + amountStr + " on " + formattedDate;
		} else if(TgCellData.ET_TRIP == value.getEntryType()) {
			tgCellIconStyle = "tgCellIconTrip";
			tgTableStyle = "tgCellTableTrip";
			entrySymbol = '♦';
			
			title = "You created a trip on " + formattedDate;
		}
		
		sb.appendHtmlConstant("<table align='center' class='" + tgTableStyle + "' cellspacing='0px' title='" + title + "'><tr>");
		sb.appendHtmlConstant("<td align='left' class='tgCellTDStyle'><div class='" + tgCellIconStyle + "'>" + entrySymbol + "</div></td>");
		sb.appendHtmlConstant("<td align='left' class='tgCellTDStyle' width='250px'><div class='tgCellNormalLabel'>" +
				heading + "</div></td>");
		sb.appendHtmlConstant("<td align='center' class='tgCellTDStyle' width='100px'>");
		sb.appendHtmlConstant("<table align='center' cellspacing='0px' width='100%'><tr><td align='right'>");
		if(TgCellData.ET_TRIP != value.getEntryType()) {
			sb.appendHtmlConstant("<span class='normalLabel'>" + currencySymbol + " <span style='font-weight: bold;" +
					" padding-right: 5px;'>" + amountStr + "</span></span>");
			sb.appendHtmlConstant("</td></tr><tr><td align='right'>");
		}
		sb.appendHtmlConstant("<span class='normalLabel' style='font-size: 10px; padding-right: 5px;'>" + formattedDate + "</span>");
		sb.appendHtmlConstant("</td></tr></table>");
		sb.appendHtmlConstant("</td></tr></table>");
	}

}
