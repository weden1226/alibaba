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
////							for(String sen:senS){//�ⲿ�ֽ���Թ�˾������
////								System.out.println("��N"+index+"��:  "+sen);
////								index++;
////							}
//							System.out.println("��"+index+"��:  "+senS[senS.length-1]);
//						}
//						else{
//							System.out.println("��"+index+"��:  "+sentence.trim());
//						}
						//System.out.println("��"+index+"��:  "+sentence.trim());
						if(sentence.trim().length()>MIN_LIMIT){
							indexSentence.put(index++, sentence.trim());
						}
						indexStart=++indexEnd;
					}
				}
				else{//��ĩ�����Ƿ��ǶϾ��㣬����һ��
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
	//�Կո����
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
		String paragraph="�@���gȯ�̳��õć�̩��293�������F���տ�������ų�ϯ�ɖ|�����ʾ��������\���L�������^�����б����L����F�^�ã���؛�\��Ȼƣ���� �����ָ���ͻ���g�\�d������2%���L����F�^�ѣ���̩��6���𣬌��քe���Ӂ����������ء���ɼ�����~�s�ĺ��ࡣ���a�䣬������14���CͶ����գ����°����\���и��ơ� �ȵر��lH7N9�����У������ָ���^��ǰ���F�p΢�A���Cλ�ž���r�����{�F�r�џoӰ푡�";
		String cutFlag="�� . �� ? �� !";
		Punctuation test = new Punctuation(cutFlag);
		test.split(paragraph);
	}

}
