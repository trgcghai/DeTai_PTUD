package entity.constraint;

public enum TrangThai {
	CHUANOP("Chưa nộp"), XEMXET("Xem xét"), CHO("Chờ"), CHAPNHAN("Chấp nhận"), TUCHOI("Từ chối");
	
	private final String value;
	TrangThai(String value){
		this.value=value;
	}
	
	public String getValue() {
		return value;
	}
}
