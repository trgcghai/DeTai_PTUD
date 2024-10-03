package exception;

public class checkUserEmail extends Exception {
	private String email;
	
	public checkUserEmail() {
		
	}
	
	public checkUserEmail(String email, String msg) {
		super(msg);
		this.email=email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
