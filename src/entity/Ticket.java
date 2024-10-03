package entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Ticket {
	private static int idAuto=1;

	private String idTicket;
	private LocalDateTime dateBook;
	private double price;
	private Screening screening;
	
	public Ticket() {
		
	}
	
	public Ticket(String idTicket, LocalDateTime dateBook, double price, Screening screening) {
		super();
		this.idTicket = idTicket;
		this.dateBook = dateBook;
		this.price = price;
		this.screening = screening;
	}

	public Ticket(String idTicket) {
		super();
		this.idTicket = idTicket;
	}
	
	public static int getIdAuto() {
		return idAuto;
	}

	public static void setIdAuto(int idAuto) {
		Ticket.idAuto = idAuto;
	}

	public String getIdTicket() {
		return idTicket;
	}

	public void setIdTicket(String idTicket) {
		this.idTicket = idTicket;
	}

	public LocalDateTime getDateBook() {
		return dateBook;
	}

	public void setDateBook(LocalDateTime dateBook) {
		this.dateBook = dateBook;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Screening getScreening() {
		return screening;
	}

	public void setScreening(Screening screening) {
		this.screening = screening;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idTicket == null) ? 0 : idTicket.hashCode());
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
		Ticket other = (Ticket) obj;
		if (idTicket == null) {
			if (other.idTicket != null)
				return false;
		} else if (!idTicket.equals(other.idTicket))
			return false;
		return true;
	}

	
}