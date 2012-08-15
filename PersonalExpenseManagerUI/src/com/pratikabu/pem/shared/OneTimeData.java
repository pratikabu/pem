/**
 * 
 */
package com.pratikabu.pem.shared;

import java.io.Serializable;

/**
 * @author pratsoni
 *
 */
@SuppressWarnings("serial")
public class OneTimeData implements Serializable {
	private static String currecnySymbol = "$";

	public static String getCurrecnySymbol() {
		return currecnySymbol;
	}

	public static void setCurrecnySymbol(String currecnySymbol) {
		OneTimeData.currecnySymbol = currecnySymbol;
	}
}
