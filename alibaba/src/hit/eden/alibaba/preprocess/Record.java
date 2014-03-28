package hit.eden.alibaba.preprocess;

import java.util.Date;

public class Record {

	private String userId;
	private String brandId;
	private int type;
	private Date date;
	
	public Record(){
		
	}
	
	public Record(String userId, String brandId, int type, Date date) {
		this.userId = userId;
		this.brandId = brandId;
		this.type = type;
		this.date = date;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getBrandId() {
		return brandId;
	}
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Record [userId=" + userId + ", brandId=" + brandId + ", type="
				+ type + ", date=" + date + "]";
	}
	
	
}
