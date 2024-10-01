package controller;

import java.time.LocalDate;

import exception.*;

public interface Filter {
	public boolean checkUserName(String userName) throws checkUserName;
	public boolean checkUserEmail(String email) throws checkUserEmail;
	public boolean checkUserPass(String pass) throws checkUserPass;
	public boolean checkName(String name) throws checkName;
	public boolean checkBirthday(LocalDate birthday) throws checkBirthday;
	public boolean checkPhone(String phone) throws checkPhone;
	public boolean checkDateOfWork(LocalDate dateOfWork) throws checkDateOfWork;
	public boolean checkTimeMovie(String timeText) throws checkTimeMovie;
	public boolean checkDateOfDebut(LocalDate date) throws checkBirthday;
	public boolean checkDateScreening(LocalDate date) throws checkBirthday;
	public boolean checkTimeScreening(String time) throws checkTimeMovie;
	public boolean checkRoomScreening(String room) throws checkTimeMovie;
	public boolean checkNumber(String number) throws checkNumber;
}
