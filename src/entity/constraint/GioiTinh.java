package entity.constraint;

public enum GioiTinh {
	NAM("Nam"), NU("Nữ"), KHAC("Khác");
	
	private final String value;
	GioiTinh(String value) {
		this.value=value;
	}
	
	public String getValue() {
		return value;
	}
}
