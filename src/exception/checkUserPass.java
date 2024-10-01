package exception;

public class checkUserPass extends Exception {
	private String pass;

	public checkUserPass() {
		
	}
	
	public checkUserPass(String pass, String msg) {
		super(msg);
		this.pass=pass;
	}
	
	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
	
}
