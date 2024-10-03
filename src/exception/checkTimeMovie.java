package exception;

public class checkTimeMovie extends Exception{
	private String timeText;
	
	public checkTimeMovie() {
		
	}
	
	public checkTimeMovie(String timeText, String msg) {
		super(msg);
		this.timeText=timeText;
	}

	public String getTimeText() {
		return timeText;
	}

	public void setTimeText(String timeText) {
		this.timeText = timeText;
	}
}
