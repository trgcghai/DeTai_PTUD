package entity.constraint;

public enum TrangThai {
	CHUANOP("Chưa nộp"),
	XEMXET("Xem xét"), CHO("Chờ"), DADUYET("Đã duyệt"), TUCHOI("Từ chối");
	
	private final String value;
	TrangThai(String value){
		this.value=value;
	}
	
	public String getValue() {
		return value;
	}
}
