package entity.constraint;

public enum TrinhDo {
	CAODANG("Cao đẳng"), DAIHOC("Đại học"), KYSU("Kỹ sư");
	
	private final String value;
	TrinhDo(String value){
		this.value=value;
	}
	
	public String getValue() {
		return value;
	}
}