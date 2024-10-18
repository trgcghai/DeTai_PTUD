package controller;
import java.time.LocalDate;
import java.util.regex.*;
import exception.*;

public class FilterImp implements Filter{

	@Override
	public boolean checkUserName(String userName) throws exception.checkUserName {
		// TODO Auto-generated method stub
		if(userName!="" || userName!=null) {
			String regex="^([a-zA-ZẮẰẲẴẶĂẤẦẨẪẬÂÁÀÃẢẠĐẾỀỂỄỆÊÉÈẺẼẸÍÌỈĨỊỐỒỔỖỘÔỚỜỞỠ"
	                + "ỢƠÓÒÕỎỌỨỪỬỮỰƯÚÙỦŨỤÝỲỶỸỴ]+\\s?){1,40}$";
			Pattern pattern= Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
			Matcher matcher=pattern.matcher(userName);
			if(matcher.matches()) {
				return true;
			}
			else {
				throw new checkUserName(userName, "Tên đăng nhập chỉ gồm chữ cái");
			}
		}
		else {
			throw new checkUserName(userName, "Tên đăng nhập không để trống");
		}
	}

	@Override
	public boolean checkUserEmail(String email) throws exception.checkUserEmail {
		// TODO Auto-generated method stub
		if(email!="" || email!=null) {
			String regex="^[a-z]+[a-z0-9._]*@gmail.com$";
			Pattern pattern= Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
			Matcher matcher=pattern.matcher(email);
			if(matcher.matches()) {
				return true;
			}
			else {
				throw new checkUserEmail(email, "Email không hợp lệ");
			}
		}
		else {
			throw new checkUserEmail(email, "Email không để trống");
		}
	}

	@Override
	public boolean checkUserPass(String pass) throws exception.checkUserPass {
		// TODO Auto-generated method stub
		if(pass!="" || pass!=null) {
			String regex="^[a-zA-Z0-9]{3,}$";
			Pattern pattern= Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
			Matcher matcher=pattern.matcher(pass);
			if(matcher.matches()) {
				return true;
			}
			else {
				throw new checkUserPass(pass, "Mật khẩu ít nhất 3 ký tự");
			}
		}
		else {
			throw new checkUserPass(pass, "Mật khẩu không để trống");
		}
	}

	@Override
	public boolean checkName(String name) throws exception.checkName {
		if(name!="" || name!=null) {
			String regex="^([a-zA-ZắẮằẰẳẲẵẴặẶăĂấẤầẦẩẨẫẪậẬâÂáÁàÀãÃảẢạẠđĐếẾềỀểỂễỄệỆêÊéÉèÈẻẺẽẼẹẸíÍìÌỉỈĩĨịỊốỐồỒổỔỗỖộỘôÔớỚờỜởỞỡỠ"
	                + "ợỢơƠóÓòÒõÕỏỎọỌứỨừỪửỬữỮựỰưƯúÚùÙủỦũŨụỤýÝỳỲỷỶỹỸỵỴ]+:?\\s?){1,40}$";
			Pattern pattern= Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
			Matcher matcher=pattern.matcher(name);
			if(matcher.matches()) {
				return true;
			}
			else {
				throw new checkName(name, "Họ tên chỉ gồm chữ cái");
			}
		}
		else {
			throw new checkName(name, "Họ tên không để trống");
		}
	}

	@Override
	public boolean checkBirthday(LocalDate birthday) throws exception.checkBirthday {
		// TODO Auto-generated method stub
		LocalDate currentDate=LocalDate.now();
		if(currentDate.getYear() - birthday.getYear() >= 18) {
			return true;
		}
		else {
			throw new exception.checkBirthday(birthday, "Chưa đủ 18 tuổi");
		}
	}

	@Override
	public boolean checkPhone(String phone) throws exception.checkPhone {
		if(phone!="" || phone!=null) {
			String regex="^(0){1}[0-9]{9}$";
			Pattern pattern= Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
			Matcher matcher=pattern.matcher(phone);
			if(matcher.matches()) {
				return true;
			}
			else {
				throw new checkPhone(phone, "SĐT phải có 10 số và bắt đầu bằng số 0");
			}
		}
		else {
			throw new checkPhone(phone, "SĐT không để trống");
		}
	}

	@Override
	public boolean checkDateOfWork(LocalDate dateOfWork) throws exception.checkDateOfWork {
		LocalDate currentDate=LocalDate.now();
		if(dateOfWork.compareTo(currentDate) < 0) {
			return true;
		}
		else {
			throw new exception.checkDateOfWork(dateOfWork, "Ngày vào làm không hợp lệ");
		}
	}

	@Override
	public boolean checkTimeMovie(String timeText) throws exception.checkTimeMovie {
		// TODO Auto-generated method stub
		Pattern pattern=Pattern.compile("^[0-9]+$");
		if(pattern.matcher(timeText).matches()) {
			return true;
		}
		else {
			throw new exception.checkTimeMovie(timeText, "Thời lượng phải là số (tính bằng phút)");
		}
	}

	@Override
	public boolean checkDateOfDebut(LocalDate date) throws exception.checkBirthday {
		LocalDate currentDate=LocalDate.now();
		if(date.compareTo(currentDate) > 0) {
			return true;
		}
		else {
			throw new exception.checkBirthday(date, "Ngày công chiếu phải trước ngày hiện tại");
		}
	}

	@Override
	public boolean checkDateScreening(LocalDate date) throws exception.checkBirthday {
		// TODO Auto-generated method stub
		LocalDate currentDate=LocalDate.now();
		if(date.compareTo(currentDate) > 0) {
			return true;
		}
		else {
			throw new exception.checkBirthday(date, "Suất chiếu phải sau ngày hiện tại");
		}
	}

	@Override
	public boolean checkTimeScreening(String time) throws exception.checkTimeMovie {
		// TODO Auto-generated method stub
		if(Pattern.compile("^[0-9]{2}:[0-9]{2}$").matcher(time).matches()) {
			return true;
		}
		else {
			throw new exception.checkTimeMovie(time, "Thời gian theo định dạng: HH:mm");
		}
	}

	@Override
	public boolean checkRoomScreening(String room) throws exception.checkTimeMovie {
		// TODO Auto-generated method stub
		Pattern pattern=Pattern.compile("^[0-9]+$");
		if(pattern.matcher(room).matches()) {
			return true;
		}
		else {
			throw new exception.checkTimeMovie(room, "Số phòng là số");
		}
	}

	@Override
	public boolean checkNumber(String number) throws exception.checkNumber {
		// TODO Auto-generated method stub
		Pattern pattern=Pattern.compile("^[0-9]+$");
		if(pattern.matcher(number).matches()) {
			return true;
		}
		else {
			throw new exception.checkNumber(number, "Giá phải là số");
		}
	}
	
}
