package exception;


public class checkNumber extends Exception {
	private String number;
	
	public checkNumber() {
		
	}
	
	public checkNumber(String number, String msg) {
		super(msg);
		this.number=number;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
}
