package entity;

public class Room {
	private String idRoom;
	private int numberRoom;
	
	public String getIdRoom() {
		return idRoom;
	}
	public void setIdRoom(String idRoom) {
		this.idRoom = idRoom;
	}
	public int getNumberRoom() {
		return numberRoom;
	}
	public void setNumberRoom(int numberRoom) {
		this.numberRoom = numberRoom;
	}
	
	public Room(String idRoom, int numberRoom) {
		super();
		this.idRoom = idRoom;
		this.numberRoom = numberRoom;
	}
	
	public Room(String idRoom) {
		super();
		this.idRoom = idRoom;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idRoom == null) ? 0 : idRoom.hashCode());
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
		Room other = (Room) obj;
		if (idRoom == null) {
			if (other.idRoom != null)
				return false;
		} else if (!idRoom.equals(other.idRoom))
			return false;
		return true;
	}
}
