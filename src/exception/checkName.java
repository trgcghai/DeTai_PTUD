package exception;

public class checkName extends Exception {
	private String name;
	
	public checkName() {
		
	}
	
	public checkName(String name, String msg) {
		super(msg);
		this.name=name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
