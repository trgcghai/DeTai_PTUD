package entity.constraint;

public enum VaiTro {
	CHUACO("Chưa có"), ADMIN("Admin"), NHANVIEN("Nhân viên");
	
	private final String value;
	VaiTro(String value){
		this.value=value;
	}
	
	public String getValue() {
		return value;
	}
}
