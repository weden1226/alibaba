package hit.eden.graduation.common;

import java.util.Date;

public class Document {

	private String id;
	private String uName;
	private String title;
	private String content;
	private Date pDate;
	private String link;
	private int sType;
	
	public Document(){
		
	}
	
	public Document(String id,String uName,String title,String content,
			Date pDate,String link,int sType){
		this.setId(id);
		this.setUserName(uName);
		this.setTitle(title);
		this.setContent(content);
		this.setPublishDate(pDate);
		this.setLink(link);
		this.setSourceType(sType);
	}
	
	public void setId(String id){
		this.id = id;
	}
	
	public void setUserName(String uName){
		this.uName = uName;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public void setContent(String content){
		this.content = content;
	}
	
	public void setPublishDate(Date pDate){
		this.pDate = pDate;
	}
	
	public void setLink(String link){
		this.link = link;
	}
	
	public void setSourceType(int sType){
		this.sType = sType;
	}
	
	public String getId(){
		return this.id;
	}
	
	public String getUserName(){
		return this.uName;
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public String getContent(){
		return this.content;
	}
	
	public Date getPublishDate(){
		return this.pDate;
	}
	
	public String getLink(){
		return this.link;
	}
	
	public int getSourceType(){
		return this.sType;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
