package door;

public class DataBox {
	private int id;
	private int flag;
	private String stFlag;
	private String entryDate;
	public int getId() {
		return id;
	}
	private String entryTime;


	public void setId(int id) {
		this.id = id;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public String getStFlag() {
		return stFlag;
	}
	public void setStFlag(String stFlag) {
		this.stFlag = stFlag;
	}
	public String getEntryDate() {
		return entryDate;
	}
	public void setEntryDate(String entryDate) {
		this.entryDate = entryDate;
	}
	public String getEntryTime() {
		return entryTime;
	}
	public void setEntryTime(String entryTime) {
		this.entryTime = entryTime;
	}
}
