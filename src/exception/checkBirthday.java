package exception;

import java.time.LocalDate;

public class checkBirthday extends Exception {
	private LocalDate birthday;
	
	public checkBirthday() {
		
	}
	
	public checkBirthday(LocalDate birthday, String msg) {
		super(msg);
		this.birthday=birthday;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}
}
