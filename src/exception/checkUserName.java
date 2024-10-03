package exception;

public class checkUserName extends Exception {
	private String userName;

	public checkUserName() {
		
	}
	
	public checkUserName(String userName, String msg) {
		super(msg);
		this.userName=userName;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
