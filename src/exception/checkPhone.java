package exception;

public class checkPhone extends Exception {
	private String phone;
	
	public checkPhone() {
		
	}
	
	public checkPhone(String phone, String msg) {
		super(msg);
		this.phone=phone;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
