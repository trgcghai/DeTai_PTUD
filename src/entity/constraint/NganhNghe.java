package entity.constraint;

public enum NganhNghe {
	CNTT("Công nghệ thông tin"), KIEMTOAN("Kiểm toán"), HANHCHINH("Hành chính"), BANLE("Bán lẻ"),
	DICHVU("Dịch vụ"), MARKETING("Marketing"), THIETKE("Thiết kế"), KETOAN("Kế toán");
	
	private final String value;
	NganhNghe(String value){
		this.value=value;
	}
	
	public String getValue() {
		return value;
	}
}
