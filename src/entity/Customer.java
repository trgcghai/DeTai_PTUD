package entity;

import java.time.LocalDate;

public class Customer {
	
	private static int idAuto=1;
	

	private String idCustomer;
	private String name;
	private LocalDate birthday;
	private String phone;
	private int gender;
	
	public static int getIdAuto() {
		return idAuto;
	}

	public static void setIdAuto(int idAuto) {
		Customer.idAuto = idAuto;
	}

	public String getIdCustomer() {
		return idCustomer;
	}
	
	public void setIdCustomer(String idCustomer) {
		this.idCustomer = idCustomer;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public LocalDate getBirthday() {
		return birthday;
	}
	
	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}
	
	public int getAge() {
		return LocalDate.now().getYear() - this.birthday.getYear();
	}

	public Customer(String idCustomer, String name, LocalDate birthday, String phone, int gender) {
		super();
		this.idCustomer = idCustomer;
		this.name = name;
		this.birthday = birthday;
		this.phone = phone;
		this.gender = gender;
	}

	public Customer(String idCustomer) {
		super();
		this.idCustomer = idCustomer;
	}
	
	public Customer(String idCustomer, String name) {
		super();
		this.idCustomer = idCustomer;
		this.name=name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idCustomer == null) ? 0 : idCustomer.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		if (idCustomer != other.idCustomer)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Customer [idCustomer=" + idCustomer + ", name=" + name + ", birthday=" + birthday + ", phone=" + phone
				+ ", gender=" + (gender == 1 ? "Nam" : "Nữ") + "]";
	}
	
	
}