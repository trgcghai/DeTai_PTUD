package entity;

import java.time.LocalDate;

public class Director {
	
	private static int idAuto=1;

	private String idDirector;
	private String name;
	private LocalDate birthday;
	
	public static int getIdAuto() {
		return idAuto;
	}

	public static void setIdAuto(int idAuto) {
		Director.idAuto = idAuto;
	}

	public String getIdDirector() {
		return idDirector;
	}
	
	public void setIdDirector(String idDirector) {
		this.idDirector = idDirector;
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
	
	public Director(String idDirector, String name, LocalDate birthday) {
		super();
		this.idDirector = idDirector;
		this.name = name;
		this.birthday = birthday;
	}

	public Director(String idDirector) {
		super();
		this.idDirector = idDirector;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idDirector == null) ? 0 : idDirector.hashCode());
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
		Director other = (Director) obj;
		if (idDirector == null) {
			if (other.idDirector != null)
				return false;
		} else if (!idDirector.equals(other.idDirector))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Director [idDirector=" + idDirector + ", name=" + name + ", birthday=" + birthday + "]";
	}
}