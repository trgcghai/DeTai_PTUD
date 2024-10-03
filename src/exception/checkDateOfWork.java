package exception;

import java.time.LocalDate;

public class checkDateOfWork extends Exception {
	private LocalDate dateOfWork;
	
	public checkDateOfWork() {
		
	}
	
	public checkDateOfWork(LocalDate dateOfWork, String msg) {
		super(msg);
		this.dateOfWork=dateOfWork;
	}

	public LocalDate getDateOfWork() {
		return dateOfWork;
	}

	public void setDateOfWork(LocalDate dateOfWork) {
		this.dateOfWork = dateOfWork;
	}
}
