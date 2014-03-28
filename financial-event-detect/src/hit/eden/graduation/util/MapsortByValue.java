package hit.eden.graduation.util;

import java.awt.List;
import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class MapsortByValue {

	 private class ValueComparator implements Comparator<Map.Entry<String, Integer>>  
	 {  
	     public int compare(Map.Entry<String, Integer> mp1, Map.Entry<String, Integer> mp2)   
	     {  
	         return mp1.getValue() - mp2.getValue();  
	     }  
	 }  
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
