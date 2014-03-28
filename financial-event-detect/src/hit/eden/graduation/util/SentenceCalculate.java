package hit.eden.graduation.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import kevin.zhang.NLPTools;

public class SentenceCalculate {

	
	private static final double COEF_TF = 0.1;
	private static final double COEF_LOC = 0.4;
	private static final double COEF_LEN = 0.1;
	private static final double COEF_NE = 0.2;
	private static final double COEF_OVER = 0.2;
	
	private int locThreshold = 3;
	private int lenThreshold = 16;
	private int sentenceCount = 0;
	
	private Map<Integer, Double> scoreTF;
	private Map<Integer, Double> scoreLoc;
	private Map<Integer, Double> scoreLen;
	private Map<Integer, Double> scoreNE;
	private Map<Integer, Double> scoreOver;
	
	
	public SentenceCalculate(){
		
		scoreTF = new LinkedHashMap<Integer, Double>();
		scoreLoc = new LinkedHashMap<Integer, Double>();
		scoreLen = new LinkedHashMap<Integer, Double>();
		scoreNE = new LinkedHashMap<Integer, Double>();
		scoreOver = new LinkedHashMap<Integer, Double>();
	}
	
	public void doCalculate(Map<Integer, String> indexSentence, String title){
		
		Map<String, Double> wordMap = new HashMap<String, Double>();
		Map<Integer, Set<String>> indexWord = new HashMap<Integer, Set<String>>();
		String[] titleWord = NLPTools.getInstance().segmentWithNOPOSTag(title).split(" ");
		Set<String> titleSet = new HashSet<String>();
		for(String s:titleWord){
			titleSet.add(s.trim());
		}
		int count = 0;
		for(int index:indexSentence.keySet()){
			Set<String> singleSenSet = new HashSet<String>();
			String sentence = indexSentence.get(index);
			String[] temp = NLPTools.getInstance().segmentWithNOPOSTag(sentence).split(" ");
			for(String s:temp){
				if(s.trim().length()>1){
					count++;
					String w = s.trim();
					singleSenSet.add(w);
					if(wordMap.containsKey(w)){
						wordMap.put(w, 1.0);
					}
					else{
						wordMap.put(w, wordMap.get(w)+1.0);
					}
				}
			}
			indexWord.put(index, singleSenSet);
			if(sentence.length()>this.lenThreshold){
				scoreLen.put(index, 1.0);
			}
			else{
				scoreLen.put(index, 0.0);
			}
		}
		
		for(String s:wordMap.keySet()){
			double tf = (double)wordMap.get(s)/(double)count;
			wordMap.put(s, tf);
		}
		
		this.sentenceCount = indexSentence.size();
		for(int index:indexWord.keySet()){
			double tfCount = 0.0;
			int difCount = 0;
			int titleWordSize = titleSet.size();
			for(String s:indexWord.get(index)){
				tfCount = tfCount + wordMap.get(s);
				if(titleSet.contains(s)){
					difCount++;
				}
			}
			scoreTF.put(index, tfCount);
			scoreLoc.put(index, getScoreLoc(index,this.sentenceCount));
			scoreOver.put(index, (double)difCount/(double)(titleWordSize+indexWord.get(indexWord).size()-difCount));
		}

	}
	
	public Map<Integer, Double> getScoreSentence(){
		
		Map<Integer, Double> scoreMap = new HashMap<Integer, Double>();
		
		for(int i = 1; i<this.sentenceCount+1; i++){
			double score = COEF_TF*scoreTF.get(i) + COEF_LOC*scoreLoc.get(i) + COEF_LEN*scoreLen.get(i)
					       + COEF_OVER*scoreOver.get(i);
			scoreMap.put(i, score);
		}
		
		return scoreMap;
	}
	
	public void setLocThreshold(int locThreshold){
		this.locThreshold = locThreshold;
	}
	
	public void setLenThreshold(int lenThreshold){
		this.lenThreshold = lenThreshold;
	}
	
	private double getScoreLoc(int i, int n){//i<=n
		
		if(i<=n&&i>0){
			if(i<=this.locThreshold){
				return 1.0;
			}
			else{
				return(1.0-(double)java.lang.Math.log(i)/java.lang.Math.log(n));
			}
		}
		else{
			return 0.0;
		}
	}
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
