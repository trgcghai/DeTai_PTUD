package entity.constraint;

public enum NganhNghe {
	CNTT("Công nghệ thông tin"), KIEMTOAN("Kiểm toán");
	
	private final String value;
	NganhNghe(String value){
		this.value=value;
	}
	
	public String getValue() {
		return value;
	}
}
