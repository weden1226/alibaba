package hit.eden.graduation.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import kevin.zhang.NLPIR;
import kevin.zhang.NLPTools;

import hit.eden.graduation.csv.*;
import hit.eden.graduation.common.Document;
import hit.eden.graduation.common.Term;
import hit.eden.graduation.util.Punctuation;
import hit.eden.graduation.util.Keywords;

public class CSVProcess {

	private static NLPIR nlpir=null;
	
	public CSVProcess(){
		
		nlpir=new NLPIR();
		String argu=".";
		System.out.println("NLPIR Init");
		try{
			if(NLPIR.NLPIR_Init(argu.getBytes("GB2312"), 1)==false){
				System.out.println("Init Failed, Please check!");
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public static void analysisData(String filePath){
		
		HashSet<String> siteIdMap=new HashSet<String>();
		File f=new File(filePath);
		File[] files=f.listFiles();
		for(File file:files){
			if(file.isDirectory()){
				System.out.println("This file is directory! Please check");
			}
			else{
				try {
					CsvReader readerCSV=new CsvReader(file.getAbsolutePath(),',',Charset.forName("UTF-8"));
					
					try {
						while(readerCSV.readRecord()){
							String[] line=readerCSV.getValues();
							if(!siteIdMap.contains(line[5])){
								siteIdMap.add(line[5]);
								System.out.println(line[5]+"\t"+line[6]+"\t"+line[7]+"\t"+line[8]);
							}
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public static void singleProcessFile(String filePath){
		
		try {
			CsvReader readerCSV = new CsvReader(filePath,',',Charset.forName("UTF-8"));
			String cutFlag="。 ？ ? ！ !";
			Punctuation splitSentence=new Punctuation(cutFlag);
			while(readerCSV.readRecord()){
				String[] line=readerCSV.getValues();
				System.out.println(line[1]);
				System.out.println(NLPTools.getInstance().segmentWithPOSTag(line[3]));
//				String[] pa = line[4].split("\t|\r|\n");
//				for(String s:pa){
//					System.out.println("**************");
//					System.out.println(s);
//				}
				Map<Integer,String> sent=splitSentence.split(line[4]);
				if(sent.size()>6){
					System.out.println("第1句：   "+sent.get(0));
					System.out.println("第2句：   "+sent.get(1));
					System.out.println("第3句：   "+sent.get(2));
					System.out.println("第"+(sent.size()-3)+"句：   "+sent.get(sent.size()-3));
					System.out.println("第"+(sent.size()-2)+"句：   "+sent.get(sent.size()-2));
					System.out.println("第"+(sent.size()-1)+"句：   "+sent.get(sent.size()-1));
				}
				else{
					for(int i=0;i<sent.size();i++){
						System.out.println("第"+i+"句：   "+sent.get(i));
					}
				}
				System.out.println(line[7]);
				System.out.println();
				System.out.println();
				System.out.println();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void getNewsBySiteId(String filePath, int id, String fileOut){
		try{
			CsvWriter writer=null;
	 		//writer=new CsvWriter("./FinancialNews/"+fileName+".csv",',',Charset.forName("UTF-8"));
			File f = new File(filePath);
			File[] listF = f.listFiles();
			int count=0;
			for(File single:listF){
				if(single.isDirectory()){
					File[] singleF = single.listFiles();
					for(File file:singleF){
						if(!file.isDirectory()){
							CsvReader csvReader = new CsvReader(file.getAbsolutePath(),',',Charset.forName("UTF-8"));;
							
							String newName="C:/Users/Eric Li/Desktop/wyd/毕设/data/中国证券监督管理委员会/"+single.getName()+"/"+file.getName();
							System.out.println(newName);
							writer = new CsvWriter(newName,',',Charset.forName("UTF-8"));
							
							while(csvReader.readRecord()){
								String[] lineRecord=csvReader.getValues();
								if(Integer.valueOf(lineRecord[5])==id){
									count++;
									writer.writeRecord(lineRecord);
								}
								
							}
							csvReader.close();
							writer.close();
						}
					}
				}
				else{
					
				}
			}
			
			System.out.println("Count: "+count);
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		PrintStream myout = new PrintStream(new FileOutputStream(new File("output.txt")));       
		System.setOut(myout);        
		CSVProcess test = new CSVProcess();
		//analysisData("C:/Users/Eric Li/Desktop/wyd/毕设/data/FinancialNews/2013-11/");
		singleProcessFile("F:/data/东方网/2013-11/2013-11-01.csv");
		//getNewsBySiteId("C:/Users/Eric Li/Desktop/wyd/毕设/data/FinancialNews/",130,"");
	}

}
