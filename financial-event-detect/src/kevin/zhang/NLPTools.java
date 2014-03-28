package kevin.zhang;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NLPTools {
	
	private static final Logger log = LoggerFactory.getLogger(NLPTools.class);
	private static NLPTools instance=null;
	private static NLPIR nlpir;
	private static final int ICT_POS_MAP_SECOND = 0; // 计算所二级标注集
	
	private NLPTools(){
		nlpir = new NLPIR();
	
		try {
			if(NLPIR.NLPIR_Init(".".getBytes("GB2312"), 1)){
				log.info("NLPIR init success!");
				nlpir.NLPIR_SetPOSmap(ICT_POS_MAP_SECOND);
			}
			else{
				log.info("NLPIR init failed, please read log!");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			log.error("ERROR: "+e);
		}
	}
	
	public static NLPTools getInstance(){
		if(instance == null){
			synchronized(NLPTools.class){
				if(instance == null){
					instance = new NLPTools();
				}
			}
		}
		return instance;
	}
	
	/**
	 * 
	 * @param path
	 *            - path of the dict file in UTF8
	 * @return no. of entries added
	 * @throws UnsupportedEncodingException
	 */
	public int importUserDict(String path) throws UnsupportedEncodingException {
		return nlpir.NLPIR_ImportUserDict(path.getBytes("UTF-8"));
	}
	
	public int importAllUserDicts(String userDictDirPath) {
		// Since ICTCLAS is limited to load only one single user dict, need to write all
		// user dict files to a single temp file first, and then load the temp file to ICTCLAS

		BufferedWriter writer = null;
		File tempDict = null;
		int totalNumEntries = 0;

		try {
			File userDictFolder = new File(userDictDirPath);
			File[] dictFiles = userDictFolder.listFiles();

			try {
				tempDict = File.createTempFile("ICTClasTagger.", ".tempdict");
				writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempDict), "UTF8"));
				BufferedReader reader = null;

				// write all user dict files under the directory to the temp file
				for (File dictFile : dictFiles) {
					if (dictFile.isFile() && dictFile.getName().endsWith(".dict")) {
						try {
							reader = new BufferedReader(new InputStreamReader(new FileInputStream(dictFile), "UTF8"));
							String line = null;
							int numEntries = 0;

							while ((line = reader.readLine()) != null) {
								if (!line.startsWith("#") ) {
									writer.write(line);
									writer.newLine();
									numEntries++;
								}
							}
							writer.flush();
							System.out.println("Read user dict (" + numEntries + " entries) from: " + dictFile.getCanonicalPath());
						} finally {
							if (reader != null) {
								reader.close();
							}
						}
					}
				}
			} finally {
				if (writer != null) {
					writer.close();
				}
			}

			// load the temp dict file to ICTCLAS
			log.info("To load temp dict at: " + tempDict.getCanonicalPath());
			totalNumEntries = importUserDict(tempDict.getCanonicalPath());
			//nlpir.NLPIR_SaveTheUsrDic();
			log.info("enties number: "+totalNumEntries);
			log.error("Finished loading all .dict files under directory: {}, total entries: {}",userDictDirPath,totalNumEntries);
		} catch (Exception e) {
			log.error("Error in import all user dicts", e);
		} finally {
			if (tempDict != null) {
				tempDict.delete();
			}
		}

		return totalNumEntries;
	}
	
	
	/**
	 * @param word
	 * @return status
	 *          -Return 1 if add succeed. Otherwise return 0.
	 */
	public int addUserWord(String word){
		try {
			return nlpir.NLPIR_AddUserWord(word.trim().getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			log.error("AddUserWord Exception: ",e);
			return 0;
		}
	}
	
	/**
	 * @param word
	 * @param partOfSpeech
	 * @return status
	 *        -Return 1 if add succeed. Otherwise return 0.
	 * 
	 */
	public int addUserWord(String word, String partOfSpeech){
		try {
			String wordSpeech=word.trim()+"\t"+" "+partOfSpeech.trim();
			return nlpir.NLPIR_AddUserWord(wordSpeech.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			log.error("AddUserWord Exception: ",e);
			return 0;
		}
	}
	
	/**
	 * 
	 * @return status
	 *         -Return 1 if save succeed. Otherwise return 0
	 *         Notes: this function is only to "addUserWord" to work,
	 *                to "importUserDict" OR "importAllUserDicts" doesn't work.
	 */
	public int saveUserDict(){
		return nlpir.NLPIR_SaveTheUsrDic();
	}
	
	/**
	 * 
	 * @param rawText
	 *            - can be a passage with newline characters
	 * @return
	 */
	public String segmentWithPOSTag(String rawText) {
		try {
			byte nativeBytes1[] = nlpir.NLPIR_ParagraphProcess(rawText.getBytes("UTF8"), 1); //
			return new String(nativeBytes1, 0, nativeBytes1.length-1, "UTF8");
		} catch (UnsupportedEncodingException e) {
			log.error("Error in segmentWithPOSTag", e);
		} catch (Throwable e) {
			log.error("Error in segmentWithPOSTag", e);
		}

		return null;
	}

	public String segmentWithNOPOSTag(String rawText){
		try {
			byte nativeBytes1[] = nlpir.NLPIR_ParagraphProcess(rawText.getBytes("UTF8"), 0); //
			return new String(nativeBytes1, 0, nativeBytes1.length-1, "UTF8");
		} catch (UnsupportedEncodingException e) {
			log.error("Error in segmentWithPOSTag", e);
		} catch (Throwable e) {
			log.error("Error in segmentWithPOSTag", e);
		}

		return null;
	}
	
	public String[] GetNewWOrds(String input, int nMaxLimit, boolean bWeightOut) {
		try {
			byte[] newWord = nlpir.NLPIR_GetNewWords(input.getBytes("UTF-8"), nMaxLimit, bWeightOut);
			String newWords = new String(newWord, 0, newWord.length-1, "UTF-8");
			return newWords.split("  ");
		} catch (UnsupportedEncodingException ue) {
			log.error("Error in segmentWithPosTag ", ue);
			return null;
		}
	}

	
	
	
	public String[] GetKeyWOrds(String input, int nMaxLimit, boolean bWeightOut) {
		try {
			byte[] keyWord = nlpir.NLPIR_GetKeyWords(input.getBytes("UTF-8"), nMaxLimit, bWeightOut);
			String keyWords = new String(keyWord, 0, keyWord.length-1, "UTF-8");
			return keyWords.split("  ");
		} catch (UnsupportedEncodingException ue) {
			log.error("Error in segmentWithPosTag ", ue);
			return null;
		}
	}
	
	public void finalize() {
		NLPIR.NLPIR_Exit();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(NLPTools.getInstance().segmentWithNOPOSTag("我是中国人"));
	}

}
