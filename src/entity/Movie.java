package entity;

import java.time.LocalDate;

public class Movie {
	
	private static int idAuto=1;

	private String idMovie;
	private String name;
	private int time;
	private LocalDate dateOfDebut;
	private String type;
	private Director director;
	private String poster;
	
	public static int getIdAuto() {
		return idAuto;
	}

	public static void setIdAuto(int idAuto) {
		Movie.idAuto = idAuto;
	}

	public String getIdMovie() {
		return idMovie;
	}
	
	public void setIdMovie(String idMovie) {
		this.idMovie = idMovie;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getTime() {
		return time;
	}
	
	public void setTime(int time) {
		this.time = time;
	}
	
	public LocalDate getDateOfDebut() {
		return dateOfDebut;
	}
	
	public void setDateOfDebut(LocalDate dateOfDebut) {
		this.dateOfDebut = dateOfDebut;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}

	public Director getDirector() {
		return director;
	}

	public void setDirector(Director director) {
		this.director = director;
	}
	
	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public Movie(String idMovie, String name, int time, LocalDate dateOfDebut, String type,
			Director director, String poster) {
		super();
		this.idMovie = idMovie;
		this.name = name;
		this.time = time;
		this.dateOfDebut = dateOfDebut;
		this.type = type;
		this.director = director;
		this.poster = poster;
	}

	public Movie(String idMovie) {
		super();
		this.idMovie = idMovie;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idMovie == null) ? 0 : idMovie.hashCode());
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
		Movie other = (Movie) obj;
		if (idMovie == null) {
			if (other.idMovie != null)
				return false;
		} else if (!idMovie.equals(other.idMovie))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Movie [idMovie=" + idMovie + ", name=" + name + ", time=" + time + ", dateOfDebut="
				+ dateOfDebut + ", type=" + type + ", director=" + director + ", poster=" + poster + "]";
	}	
}