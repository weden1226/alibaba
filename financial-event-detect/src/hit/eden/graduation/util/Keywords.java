package hit.eden.graduation.util;

import java.util.LinkedList;
import java.util.List;
import kevin.zhang.NLPTools;
import hit.eden.graduation.common.Term;

public class Keywords {

	public static List<Term> getKeywordsByNLPIR(String rawText, int nMaxLimit){
		
		List<Term> keywords = new LinkedList<Term>();
		
		String[] keyList = NLPTools.getInstance().GetKeyWOrds(rawText, nMaxLimit, true);
		if(keyList.length>0){
			for(String term : keyList){
				if(term.trim().length()>0){
					String[] tmp = term.split("/");
					Term tm = new Term(tmp[0], tmp[1], Double.valueOf(tmp[2]));
					keywords.add(tm);
				}
				
			}
			return keywords;
		}
		else{
			return null;
		}
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
