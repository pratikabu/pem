/**
 * 
 */
package com.pratikabu.pem.client.dash.components;

import java.util.ArrayList;
import java.util.List;

import com.pratikabu.pem.shared.model.AccountDTO;
import com.pratikabu.pem.shared.model.TransactionDTO;
import com.pratikabu.pem.shared.model.TransactionGroupDTO;

/**
 * @author pratsoni
 *
 */
public class CentralEventHandler {
	public static final int ACTION_CREATED = 1, ACTION_EDITED = 2, ACTION_DELETED = 3;
	private static List<CListener> listeners = new ArrayList<CentralEventHandler.CListener>();
	
	public static void addListener(CListener listener) {
		listeners.add(listener);
	}
	
	public static void transactionUpdated(TransactionDTO dto, int action) {
		for(CListener l : listeners) {
			if(l instanceof TransactionUpdateListener) {
				((TransactionUpdateListener) l).transactionUpdatedEvent(dto, action);
			}
		}
	}
	
	public static void transactionGroupUpdated(TransactionGroupDTO dto, int action) {
		for(CListener l : listeners) {
			if(l instanceof TransactionGroupUpdateListener) {
				((TransactionGroupUpdateListener) l).transactionGroupUpdatedEvent(dto, action);
			}
		}
	}
	
	public static void accountUpdated(AccountDTO dto, int action) {
		for(CListener l : listeners) {
			if(l instanceof AccountUpdateListener) {
				((AccountUpdateListener) l).accountUpdatedEvent(dto, action);
			}
		}
	}
	
	public static interface CListener {}
	
	public static interface TransactionUpdateListener extends CListener {
		/**
		 * method will be invoked whenever the record is updated.
		 * @param dto
		 * @param action see <code>CentralEventHandler.ACTION_</code>
		 */
		void transactionUpdatedEvent(TransactionDTO dto, int action);
	}
	
	public static interface TransactionGroupUpdateListener extends CListener {
		/**
		 * method will be invoked whenever the record is updated.
		 * @param dto
		 * @param action see <code>CentralEventHandler.ACTION_</code>
		 */
		void transactionGroupUpdatedEvent(TransactionGroupDTO dto, int action);
	}
	
	public static interface AccountUpdateListener extends CListener {
		/**
		 * method will be invoked whenever the record is updated.
		 * @param dto
		 * @param action see <code>CentralEventHandler.ACTION_</code>
		 */
		void accountUpdatedEvent(AccountDTO dto, int action);
	}
	
	public static int getActionForCreateUpdate(Long pk) {
		return pk > 0 ? CentralEventHandler.ACTION_EDITED
				: CentralEventHandler.ACTION_CREATED;
	}
}