package entity;

public class Sitting {

	private String idSitting;
	private String numberSitting;
	private boolean state;
	private Room room;
	private Ticket ticket;

	public String getIdSitting() {
		return idSitting;
	}

	public void setIdSitting(String idSitting) {
		this.idSitting = idSitting;
	}

	public String getNumberSitting() {
		return numberSitting;
	}

	public void setNumberSitting(String numberSitting) {
		this.numberSitting = numberSitting;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}
	
	public Sitting(String idSitting, String numberSitting, boolean state, Room room, Ticket ticket) {
		super();
		this.idSitting = idSitting;
		this.numberSitting = numberSitting;
		this.state = state;
		this.room = room;
		this.ticket = ticket;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idSitting == null) ? 0 : idSitting.hashCode());
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
		Sitting other = (Sitting) obj;
		if (idSitting == null) {
			if (other.idSitting != null)
				return false;
		} else if (!idSitting.equals(other.idSitting))
			return false;
		return true;
	}
}