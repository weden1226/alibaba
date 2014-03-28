package kevin.zhang;


//import kevin.zhang.NLPIR;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

/**
* �Էִʽ�������˷�װ.�������ݿ�ÿ���������˵��
* @author: Eden 
* @date:2012.10.12
*/
public class NLPResult {
	private int start; // start position,��������������еĿ�ʼλ��
	private int length; // length,����ĳ���
	private char sPOS[];// ����
	private int posId;// word type������IDֵ�����Կ��ٵĻ�ȡ���Ա�
	private int wordId; // �����δ��¼�ʣ����0����-1
	private int word_type; // add by qp 2008.10.29 �����û��ʵ�;1�����û��ʵ��еĴʣ�0�����û��ʵ��еĴ�
	private double weight;// add by qp 2008.11.17 word weight
	public NLPResult(){
		start=0;
		length=0;
		posId=0;
		wordId=0;
		word_type=0;
		weight=0.0;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public char[] getsPOS() {
		return sPOS;
	}
	public void setsPOS(char[] sPOS) {
		this.sPOS = sPOS;
	}
	public int getPosId() {
		return posId;
	}
	public void setPosId(int posId) {
		this.posId = posId;
	}
	public int getWordId() {
		return wordId;
	}
	public void setWordId(int wordId) {
		this.wordId = wordId;
	}
	public int getWord_type() {
		return word_type;
	}
	public void setWord_type(int word_type) {
		this.word_type = word_type;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public static NLPResult[] getResults(NLPIR myICTCLAS2011,
		byte[] nativeBytes) throws IOException {
		int nativeElementSize = myICTCLAS2011.NLPIR_GetElemLength(0);// size
		int nElement = nativeBytes.length / nativeElementSize;
		//System.out.println(nativeBytes.length);
		NLPResult[] resultArr = new NLPResult[nElement];
		DataInputStream dis = new DataInputStream(new ByteArrayInputStream(
				nativeBytes));
		int iSkipNum;
		//System.out.println("----------" + nElement);
		for (int i = 0; i < nElement; i++) {
			
			resultArr[i] = new NLPResult();
			resultArr[i].start = Integer.reverseBytes(dis.readInt());
			//System.out.println("start:"+resultArr[i].start);
			iSkipNum = myICTCLAS2011.NLPIR_GetElemLength(1) - 4;
			
			if (iSkipNum > 0) {
				dis.skipBytes(iSkipNum);
			}

			resultArr[i].length = Integer.reverseBytes(dis.readInt());
			//System.out.println("length:"+resultArr[i].length);
			iSkipNum = myICTCLAS2011.NLPIR_GetElemLength(2) - 4;
			
			
			if (iSkipNum > 0) {
				dis.skipBytes(iSkipNum);
			}

			dis.skipBytes(myICTCLAS2011.NLPIR_GetElemLength(3));
			resultArr[i].posId = Integer.reverseBytes(dis.readInt());
			//System.out.println("posId:"+resultArr[i].posId);
			iSkipNum = myICTCLAS2011.NLPIR_GetElemLength(4) - 4;
			
			if (iSkipNum > 0) {
				dis.skipBytes(iSkipNum);
			}

			resultArr[i].wordId = Integer.reverseBytes(dis.readInt());
			//System.out.println("wordId:"+resultArr[i].wordId);
			iSkipNum = myICTCLAS2011.NLPIR_GetElemLength(5) - 4;
			if (iSkipNum > 0) {
				dis.skipBytes(iSkipNum);
			}

			resultArr[i].word_type = Integer.reverseBytes(dis.readInt());
			//System.out.println("word_type:"+resultArr[i].word_type);
			iSkipNum = myICTCLAS2011.NLPIR_GetElemLength(6) - 8;
			if (iSkipNum > 0) {
				dis.skipBytes(iSkipNum);
			}
			//resultArr[i].weight = Integer.reverseBytes(dis.readDouble());
			//resultArr[i].weight=dis.readDouble();
			dis.skipBytes(12);
			/*iSkipNum = myICTCLAS2011.NLPIR_GetElemLength(7) - 4;
			//iSkipNum=myICTCLAS2011.NLPIR_GetElemLength(0)-;
			if (iSkipNum > 0) {
				dis.skipBytes(iSkipNum);
			}*/

		}

		dis.close();
		return resultArr;
	}

	public String toString() {
		return "start=" + this.start + ",length=" + this.length + "posID="
				+ this.posId + "wordID=" + this.wordId + "word_type="+this.word_type+"  weight="
				+ this.weight;
	}

};


