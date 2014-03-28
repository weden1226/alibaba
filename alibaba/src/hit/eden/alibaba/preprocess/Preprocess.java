package hit.eden.alibaba.preprocess;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import hit.eden.alibaba.csv.*;

public class Preprocess {

	
	
	public List<Record> getDataFromFile(String filePath){
		List<Record> recordList = new ArrayList<Record>();
		
		try {
			CsvReader csvReader = new CsvReader(filePath,',',Charset.forName("UTF-8"));
			
			SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
			
			while(csvReader.readRecord()){
				
				String[] record = csvReader.getValues();
				if(record.length==4){
					Record r = new Record();
					r.setUserId(record[0].trim());
					r.setBrandId(record[1].trim());
					r.setType(Integer.valueOf(record[2].trim()));
					r.setDate(sd.parse("2013-0"+record[3]));
					recordList.add(r);
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return recordList;
	}
	
	public List<Record> getRecordByType(List<Record> recordL, int type){
		List<Record> recordList = new ArrayList<Record>();
		for(Record r: recordL){
			if(r.getType()==type){
				recordList.add(r);
				System.out.println(r);
			}
		}
		
		return recordList;
	}
	
	public List<Record> sortByBrand(List<Record> recordL){
		List<Record> recordList = new ArrayList<Record>();
		
		
		return recordList;
	}
	
	
	public static void main(String[] args){
		
		Preprocess test = new Preprocess();
		List<Record> recordList = test.getDataFromFile("sorted_data.csv");
		List<Record> recordL = test.getRecordByType(recordList, 1);
		
	}
	
}
