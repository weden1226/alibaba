package hit.eden.graduation.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Punctuation {

	
	private Set<String> cutFlag = new HashSet<String>();
	private static final int MIN_LIMIT=1;
	
	
	public Punctuation(){
	}
	
	public Punctuation(Set<String> cutFlag){
		this.setCutFlag(cutFlag);
	}
	
	public Punctuation(String cutFlag){
		this.setCutFlag(cutFlag);
	}
	
	public Map<Integer,String> split(String paragraph){
		if(!paragraph.isEmpty()){
			Map<Integer,String> indexSentence = new HashMap<Integer,String>();
			char[] paragraphCharArr=paragraph.toCharArray();
			int indexStart=0;
			int indexEnd=0;
			int index=0;
			for(int i=0;i<paragraphCharArr.length-1;i++){

				if(i!=paragraphCharArr.length-2){
					if(!this.isCutFlag(String.valueOf(paragraphCharArr[i]))){
						indexEnd++;
					}
					else{
						String sentence=new String(paragraphCharArr,indexStart,indexEnd-indexStart+1);
//						if(index==0){
//							System.out.println("N: "+sentence);
//							String[] senS = sentence.split(" ");
//							
////							for(String sen:senS){//这部分仅针对公司的语料
////								System.out.println("第N"+index+"句:  "+sen);
////								index++;
////							}
//							System.out.println("第"+index+"句:  "+senS[senS.length-1]);
//						}
//						else{
//							System.out.println("第"+index+"句:  "+sentence.trim());
//						}
						//System.out.println("第"+index+"句:  "+sentence.trim());
						if(sentence.trim().length()>MIN_LIMIT){
							indexSentence.put(index++, sentence.trim());
						}
						indexStart=++indexEnd;
					}
				}
				else{//段末不管是否是断句标点，都成一句
					String sentence=new String(paragraphCharArr,indexStart,paragraphCharArr.length-indexStart);
					if(sentence.trim().length()>MIN_LIMIT){
						indexSentence.put(index++, sentence.trim());
					}
				}
				
			}
			return indexSentence;
		}
		else{
			return null;
		}
	}
	
	public boolean isCutFlag(String flag){
		if(this.cutFlag.contains(flag.trim())){
			return true;
		}
		else{
			return false;
		}
	}
	
	public void setCutFlag(Set<String> cutFlag){
		this.cutFlag = cutFlag;
	}
	//以空格隔开
	public void setCutFlag(String cutFlag){
		String[] flagList=cutFlag.split(" ");
		if(flagList.length>0){
			for(String flag:flagList){
				if(flag.trim().length()>0){
					this.cutFlag.add(flag.trim());
				}
			}
		}
	}
	
	public Set<String> getCutFlag(){
		return this.cutFlag;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String paragraph="獲多間券商唱好的國泰（293），集團常務總裁朱國樑出席股東會後表示，今年客運增長審慎樂觀，其中北美長線表現較好，但貨運仍然疲弱。 朱國樑指，復活節期間運載率上升2%，長線表現較佳，國泰由6月起，將分別增加來回香港至倫敦、洛杉磯及紐約的航班。他補充，今年有14架新機投入服務，料下半年運力有改善。 內地爆發H7N9禽流感，朱國樑指，較早前出現輕微預定機位放緩情況，強調現時已無影響。";
		String cutFlag="。 . ？ ? ！ !";
		Punctuation test = new Punctuation(cutFlag);
		test.split(paragraph);
	}

}
