package entity;

import java.time.LocalDate;

public class Bill {
	
	private static int idAuto=1;

	private String idBill;
	private LocalDate dateBill;
	private double total;
	private Customer customer;
	private NhanVien employee;
	private Ticket ticket;

	public Bill(String idBill, LocalDate dateBill, double total, Customer customer, NhanVien employee,
			Ticket ticket) {
		super();
		this.idBill = idBill;
		this.dateBill = dateBill;
		this.total = total;
		this.customer = customer;
		this.employee = employee;
		this.ticket = ticket;
	}

	public static int getIdAuto() {
		return idAuto;
	}


	public static void setIdAuto(int idAuto) {
		Bill.idAuto = idAuto;
	}


	public String getIdBill() {
		return idBill;
	}

	public void setIdBill(String idBill) {
		this.idBill = idBill;
	}

	public LocalDate getDateBill() {
		return dateBill;
	}

	public void setDateBill(LocalDate dateBill) {
		this.dateBill = dateBill;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public NhanVien getEmployee() {
		return employee;
	}

	public void setEmployee(NhanVien employee) {
		this.employee = employee;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idBill == null) ? 0 : idBill.hashCode());
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
		Bill other = (Bill) obj;
		if (idBill == null) {
			if (other.idBill != null)
				return false;
		} else if (!idBill.equals(other.idBill))
			return false;
		return true;
	}
}