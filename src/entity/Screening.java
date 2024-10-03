package entity;

import java.time.LocalDateTime;

public class Screening {
	
	private static int idAuto=1;

	private String idScreening;
	private LocalDateTime timeStart;
	private Room room;
	private Movie movie;
	
	public static int getIdAuto() {
		return idAuto;
	}

	public static void setIdAuto(int idAuto) {
		Screening.idAuto = idAuto;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public String getIdScreening() {
		return idScreening;
	}

	public void setIdScreening(String idScreening) {
		this.idScreening = idScreening;
	}

	public LocalDateTime getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(LocalDateTime timeStart) {
		this.timeStart = timeStart;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public LocalDateTime getTimeEnd() {
		LocalDateTime timeEnd = timeStart.plusMinutes(this.movie.getTime());
		return timeEnd;
	}
	
	public Screening(String id) {
		this.idScreening=id;
	}
	
	public Screening(String idScreening, LocalDateTime timeStart, Room room, Movie movie) {
		super();
		this.idScreening = idScreening;
		this.timeStart = timeStart;
		this.room = room;
		this.movie = movie;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idScreening == null) ? 0 : idScreening.hashCode());
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
		Screening other = (Screening) obj;
		if (idScreening == null) {
			if (other.idScreening != null)
				return false;
		} else if (!idScreening.equals(other.idScreening))
			return false;
		return true;
	}

	
}