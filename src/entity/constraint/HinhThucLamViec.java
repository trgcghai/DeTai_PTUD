package entity.constraint;

public enum HinhThucLamViec {
	ONLINE("Online"), OFFLINE("Offline"), FULLTIME("Full time"), PARTTIME("Part time");
	
	private final String value;
	HinhThucLamViec(String value){
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
