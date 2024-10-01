package dao;

import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import controller.Database;
import entity.Movie;
import entity.Director;

public class Movie_DAO {
	
	private ArrayList<Movie> list;
	
	public Movie_DAO() {
		list=new ArrayList<Movie>();
	}
	
	public ArrayList<Movie> getList() {
		return list;
	}
	public void setList(ArrayList<Movie> list) {
		this.list = list;
	}

	public ArrayList<Movie> getAllMovie() {
		ArrayList<Movie> listMovie = new ArrayList<Movie>();
		Database.getInstance();
		Connection con = Database.getConnection();
		try {
			String sql = "select * from Movie";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String idMovie = rs.getString(1);
				String nameMovie = rs.getString(2);
				int time = rs.getInt(3);
				LocalDate dateOfDebut = rs.getDate(4).toLocalDate();
				String type = rs.getString(5);
				String poster = rs.getString(6);
				Director director = new Director(rs.getString(7));
				
				listMovie.add(new Movie(idMovie, nameMovie, time, dateOfDebut, type, director, poster));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listMovie;
	}
	
	public ArrayList<Movie> getAllMovieByDirector(String name) {
		ArrayList<Movie> listMovie = new ArrayList<Movie>(); 
		Database.getInstance();
		
		Connection con = Database.getConnection();
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement("Select * from Movie m join Director d on m.idDirector = d.idDirector where d.name like ?");
			stmt.setString(1, "%"+name+"%");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String idMovie = rs.getString(1);
				String nameMovie = rs.getString(2);
				int time = rs.getInt(3);
				LocalDate dateOfDebut = rs.getDate(4).toLocalDate();
				String type = rs.getString(5);
				String poster = rs.getString(6);
				Director director = new Director(rs.getString(7));
				
				listMovie.add(new Movie(idMovie, nameMovie, time, dateOfDebut, type, director, poster));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listMovie;
	}
	
	public ArrayList<Movie> getAllMovieByType(String type) {
		ArrayList<Movie> listMovie = new ArrayList<Movie>(); 
		Database.getInstance();
		
		Connection con = Database.getConnection();
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement("Select * from Movie where type = ?");
			stmt.setString(1, type);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String idMovie = rs.getString(1);
				String nameMovie = rs.getString(2);
				int time = rs.getInt(3);
				LocalDate dateOfDebut = rs.getDate(4).toLocalDate();
				String typeMovie = rs.getString(5);
				String poster = rs.getString(6);
				Director director = new Director(rs.getString(7));
				
				listMovie.add(new Movie(idMovie, nameMovie, time, dateOfDebut, typeMovie, director, poster));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listMovie;
	}
	
	public ArrayList<Movie> getAllMovieByName(String name) {
		ArrayList<Movie> listMovie = new ArrayList<Movie>(); 
		Database.getInstance();
		
		Connection con = Database.getConnection();
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement("Select * from Movie where name like ?");
			stmt.setString(1, "%"+name+"%");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String idMovie = rs.getString(1);
				String nameMovie = rs.getString(2);
				int time = rs.getInt(3);
				LocalDate dateOfDebut = rs.getDate(4).toLocalDate();
				String type = rs.getString(5);
				String poster = rs.getString(6);
				Director director = new Director(rs.getString(7));
				
				listMovie.add(new Movie(idMovie, nameMovie, time, dateOfDebut, type, director, poster));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listMovie;
	}
	
	public ArrayList<Movie> getAllMovieByNameAndType(String name, String typeMovie) {
		ArrayList<Movie> listMovie = new ArrayList<Movie>(); 
		Database.getInstance();
		
		Connection con = Database.getConnection();
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement("select * from movie where name like ? and type=?");
			stmt.setString(1, "%"+name+"%");
			stmt.setString(2, typeMovie);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String idMovie = rs.getString(1);
				String nameMovie = rs.getString(2);
				int time = rs.getInt(3);
				LocalDate dateOfDebut = rs.getDate(4).toLocalDate();
				String type = rs.getString(5);
				String poster = rs.getString(6);
				Director director = new Director(rs.getString(7));
				
				listMovie.add(new Movie(idMovie, nameMovie, time, dateOfDebut, type, director, poster));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listMovie;
	}
	
	public ArrayList<Movie> getAllMovieByDirectorAndType(String name, String typeMovie) {
		ArrayList<Movie> listMovie = new ArrayList<Movie>(); 
		Database.getInstance();
		
		Connection con = Database.getConnection();
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement("select idMovie, m.name, time, dateOfDebut, type, poster, d.idDirector from movie m join director d on m.idDirector=d.idDirector\r\n"
					+ "where d.name like ? and type=?");
			stmt.setString(1, "%"+name+"%");
			stmt.setString(2, typeMovie);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String idMovie = rs.getString(1);
				String nameMovie = rs.getString(2);
				int time = rs.getInt(3);
				LocalDate dateOfDebut = rs.getDate(4).toLocalDate();
				String type = rs.getString(5);
				String poster = rs.getString(6);
				Director director = new Director(rs.getString(7));
				
				listMovie.add(new Movie(idMovie, nameMovie, time, dateOfDebut, type, director, poster));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listMovie;
	}
	
	public ArrayList<Movie> getAllMovieByNameAndDirectorAndType(String name, String directorMovie, String typeMovie) {
		ArrayList<Movie> listMovie = new ArrayList<Movie>(); 
		Database.getInstance();
		
		Connection con = Database.getConnection();
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement("select idMovie, m.name, time, dateOfDebut, type, poster, d.idDirector from movie m join director d on m.idDirector=d.idDirector\r\n"
					+ "where m.name like ? and d.name like ? and type=?");
			stmt.setString(1, "%"+name+"%");
			stmt.setString(2, "%"+directorMovie+"%");
			stmt.setString(3, typeMovie);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String idMovie = rs.getString(1);
				String nameMovie = rs.getString(2);
				int time = rs.getInt(3);
				LocalDate dateOfDebut = rs.getDate(4).toLocalDate();
				String type = rs.getString(5);
				String poster = rs.getString(6);
				Director director = new Director(rs.getString(7));
				
				listMovie.add(new Movie(idMovie, nameMovie, time, dateOfDebut, type, director, poster));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listMovie;
	}
	
	public ArrayList<Movie> getAllMovieById(String id) {
		ArrayList<Movie> listMovie = new ArrayList<Movie>(); 
		Database.getInstance();
		
		Connection con = Database.getConnection();
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement("Select * from Movie where idMovie like ?");
			stmt.setString(1, "%"+id+"%");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String idMovie = rs.getString(1);
				String nameMovie = rs.getString(2);
				int time = rs.getInt(3);
				LocalDate dateOfDebut = rs.getDate(4).toLocalDate();
				String type = rs.getString(5);
				String poster = rs.getString(6);
				Director director = new Director(rs.getString(7));
				
				listMovie.add(new Movie(idMovie, nameMovie, time, dateOfDebut, type, director, poster));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listMovie;
	}
	
	public Movie getMovieByName(String name) {
		Database.getInstance();
		Connection con = Database.getConnection();
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement("Select * from Movie where name = ?");
			stmt.setString(1, name);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				String idMovie = rs.getString(1);
				String nameMovie = rs.getString(2);
				int time = rs.getInt(3);
				LocalDate dateOfDebut = rs.getDate(4).toLocalDate();
				String type = rs.getString(5);
				String poster = rs.getString(6);
				Director director = new Director(rs.getString(7));
				
				return new Movie(idMovie, nameMovie, time, dateOfDebut, type, director, poster);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Movie getMovieByID(String id) {
		Database.getInstance();
		Connection con = Database.getConnection();
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement("Select * from Movie where idMovie = ?");
			stmt.setString(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				String idMovie = rs.getString(1);
				String nameMovie = rs.getString(2);
				int time = rs.getInt(3);
				LocalDate dateOfDebut = rs.getDate(4).toLocalDate();
				String type = rs.getString(5);
				String poster = rs.getString(6);
				Director director = new Director(rs.getString(7));
				
				return new Movie(idMovie, nameMovie, time, dateOfDebut, type, director, poster);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<Object[]> getAllCountMovie() {
		ArrayList<Object[]> listMovie = new ArrayList<Object[]>();
		Database.getInstance();
		Connection con = Database.getConnection();
		try {
			PreparedStatement stmt = con.prepareStatement("select m.idMovie, name, type, time, count(idTicket) as soLuongDon, sum(price) as tongTien from Ticket t \r\n"
					+ "join Screening sc on t.idScreening = sc.idScreening\r\n"
					+ "join Movie m on sc.idMovie = m.idMovie\r\n"
					+ "group by m.idMovie, name, type, time");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String idMovie = rs.getString(1);
				String name = rs.getString(2);
				String type = rs.getString(3);
				int time = rs.getInt(4);
				int numberBill = rs.getInt(5);
				double totalPrice = rs.getDouble(6);
				
				listMovie.add(new Object[] {idMovie, name, type, time, numberBill, totalPrice});
						
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listMovie;
	}

	public ArrayList<Object[]> getAllCountMovieByDate(String from, String to) {
		ArrayList<Object[]> listMovie = new ArrayList<Object[]>();
		Database.getInstance();
		Connection con = Database.getConnection();
		try {
			PreparedStatement stmt = con.prepareStatement("select m.idMovie, name, type, time, count(t.idTicket) as soLuongDon, sum(price) as tongTien from Ticket t \r\n"
					+ "join Screening sc on t.idScreening = sc.idScreening\r\n"
					+ "join Movie m on sc.idMovie = m.idMovie\r\n"
					+ "join Bill b on t.idTicket = b.idTicket\r\n"
					+ "where dateBill between ? and ?\r\n"
					+ "group by m.idMovie, name, type, time");
			stmt.setDate(1, Date.valueOf(from));
			stmt.setDate(2, Date.valueOf(to));
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String idMovie = rs.getString(1);
				String name = rs.getString(2);
				String type = rs.getString(3);
				int time = rs.getInt(4);
				int numberBill = rs.getInt(5);
				double totalPrice = rs.getDouble(6);
				
				listMovie.add(new Object[] {idMovie, name, type, time, numberBill, totalPrice});
						
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listMovie;
	}

	
	public boolean create(Movie movie) {
		int n = 0;
		Database.getInstance();
		
		Connection con = Database.getConnection();
		try {
			PreparedStatement stmt = con.prepareStatement("insert into Movie values (?, ?, ?, ?, ?, ?, ?)");
			stmt.setString(1, movie.getIdMovie());
			stmt.setString(2, movie.getName());
			stmt.setInt(3, movie.getTime());
			stmt.setDate(4, Date.valueOf (movie.getDateOfDebut()));
			stmt.setString(5, movie.getType());
			stmt.setString(6, movie.getPoster());
			stmt.setString(7, movie.getDirector().getIdDirector());
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return n != 0;
	}
	
	public boolean update(Movie movie) {
		int n = 0;
		Database.getInstance();
		
		Connection con = Database.getConnection();
		try {
			PreparedStatement stmt = con.prepareStatement("update Movie set name = ?, time = ?, dateOfDebut = ?, type = ?, poster = ?, idDirector = ? where idMovie = ?");
			stmt.setString(1, movie.getName());
			stmt.setInt(2, movie.getTime());
			stmt.setDate(3, Date.valueOf(movie.getDateOfDebut()));
			stmt.setString(4, movie.getType());
			stmt.setString(5, movie.getPoster());
			stmt.setString(6, movie.getDirector().getIdDirector());
			stmt.setString(7, movie.getIdMovie());
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return n != 0;
	}
	
	public boolean delete(String id) {
		int n = 0;
		Database.getInstance();
		
		Connection con = Database.getConnection();
		try {
			PreparedStatement stmt = con.prepareStatement("delete from ScreeningSitting where idMovie= ?\r\n"
					+ "delete from Movie where idMovie = ?");
			stmt.setString(1, id);
			stmt.setString(2, id);
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return n != 0;
	}
	
	public void sortByNameMovieASC() {
		Collections.sort(list, new Comparator<Movie>() {
			@Override
			public int compare(Movie o1, Movie o2) {
				// TODO Auto-generated method stub
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
		});
	}
	
	public void sortByNameMovieDESC() {
		Collections.sort(list, new Comparator<Movie>() {
			@Override
			public int compare(Movie o1, Movie o2) {
				// TODO Auto-generated method stub
				return o2.getName().compareToIgnoreCase(o1.getName());
			}
		});
	}
	
	public void sortByIdMovieASC() {
		Collections.sort(list, new Comparator<Movie>() {
			@Override
			public int compare(Movie o1, Movie o2) {
				// TODO Auto-generated method stub
				String m1=o1.getIdMovie().substring(1);
				String m2=o2.getIdMovie().substring(1);
				return Integer.parseInt(m1) - Integer.parseInt(m2);
			}
		});
	}
	
	public void sortByIdMovieDESC() {
		Collections.sort(list, new Comparator<Movie>() {
			@Override
			public int compare(Movie o1, Movie o2) {
				// TODO Auto-generated method stub
				String m1=o1.getIdMovie().substring(1);
				String m2=o2.getIdMovie().substring(1);
				return Integer.parseInt(m2) - Integer.parseInt(m1);
			}
		});
	}
}
