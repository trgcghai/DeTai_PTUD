package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import controller.Database;
import entity.*;

public class Screening_DAO {
	private ArrayList<Screening> list;
	private Movie_DAO movieDAO;
	
	public Screening_DAO () {
		list=new ArrayList<Screening>();
		movieDAO=new Movie_DAO();
	}
	
	public ArrayList<Screening> getList() {
		return list;
	}
	public void setList(ArrayList<Screening> list) {
		this.list = list;
	}
	
	public ArrayList<Screening> getAllScreening() {
		ArrayList<Screening> listScreening = new ArrayList<Screening>();
		
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			String sql = "select * from Screening";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String idScreening = rs.getString(1);
				LocalDateTime dateStart = rs.getTimestamp(2).toLocalDateTime();
				Movie movie = new Movie(rs.getString(3));
				Room room = new Room(rs.getString(4));
				listScreening.add(new Screening(idScreening, dateStart, room, movie));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listScreening;
	}
	
	public Screening getScreening(String nameMovie, int numberRoom, LocalDateTime dateTime) {
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("select idScreening, dateStart, sc.idMovie, sc.idRoom from Screening sc \r\n"
					+ "join Movie m on sc.idMovie=m.idMovie\r\n"
					+ "join Room r on r.idRoom=sc.idRoom\r\n"
					+ "where m.name =? and r.numberRoom=? and dateStart=?");
			stmt.setString(1, nameMovie);
			stmt.setInt(2, numberRoom);
			stmt.setTimestamp(3, Timestamp.valueOf(dateTime));
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				String idScreening = rs.getString(1);
				LocalDateTime dateStart = rs.getTimestamp(2).toLocalDateTime();
				Movie movie = new Movie(rs.getString(3));
				Room room = new Room(rs.getString(4));
				return new Screening(idScreening, dateStart, room, movie);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<Screening> getScreeningById(String id) {
		ArrayList<Screening> listScreening = new ArrayList<Screening>();
		
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("select * from Screening sc join Movie m on sc.idMovie = m.idMovie"
					+ " where idScreening = ?");
			stmt.setString(1, id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String idScreening = rs.getString(1);
				LocalDateTime dateStart = rs.getTimestamp(2).toLocalDateTime();
				Movie movie = new Movie(rs.getString(3));
				Room room = new Room(rs.getString(4));
				listScreening.add(new Screening(idScreening, dateStart, room, movie));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listScreening;
	}
	
	public ArrayList<Screening> getScreeningByMovie(String name) {
		ArrayList<Screening> listScreening = new ArrayList<Screening>();
		
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("select * from Screening sc join Movie m on sc.idMovie = m.idMovie"
					+ "where name = ?");
			stmt.setString(1, name);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String idScreening = rs.getString(1);
				LocalDateTime dateStart = rs.getTimestamp(2).toLocalDateTime();
				Movie movie = new Movie(rs.getString(3));
				Room room = new Room(rs.getString(4));
				listScreening.add(new Screening(idScreening, dateStart, room, movie));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listScreening;
	}
	
	public ArrayList<Screening> getScreeningByRoom(int numberRoom) {
		ArrayList<Screening> listScreening = new ArrayList<Screening>();
		
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("select * from Screening sc join Room r on sc.idRoom = r.idRoom "
					+ "where numberRoom = ?");
			stmt.setInt(1, numberRoom);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String idScreening = rs.getString(1);
				LocalDateTime dateStart = rs.getTimestamp(2).toLocalDateTime();
				Movie movie = new Movie(rs.getString(3));
				Room room = new Room(rs.getString(4));
				listScreening.add(new Screening(idScreening, dateStart, room, movie));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listScreening;
	}
	
	public String getIdRoomByDate(LocalDateTime dateTime, Movie movieRes) {
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("select idRoom from Screening where dateStart=? and idMovie=?");
			stmt.setTimestamp(1, Timestamp.valueOf(dateTime));
			stmt.setString(2, movieRes.getIdMovie());
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean create(Screening sc) {
		int n = 0;
		Database.getInstance();
		Connection con = Database.getConnection();
		try {
			PreparedStatement stmt = con.prepareStatement("insert into Screening values (?, ?, ?, ?)\r\n"
					+"insert into ScreeningSitting \r\n"
					+ "select idScreening, dateStart, idMovie, sc.idRoom, idSitting, numberSitting, state, idTicket\r\n"
					+ "from Screening sc join Sitting s on sc.idRoom=s.idRoom \r\n"
					+ "where idScreening=?");
			stmt.setString(1, sc.getIdScreening());
			stmt.setTimestamp(2, Timestamp.valueOf(sc.getTimeStart()));
			stmt.setString(3, sc.getMovie().getIdMovie());
			stmt.setString(4, sc.getRoom().getIdRoom());
			stmt.setString(5, sc.getIdScreening());
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return n != 0;
	}
	
	public boolean update(Screening sc) {
		int n = 0;
		Database.getInstance();
		Connection con = Database.getConnection();
		try {
			PreparedStatement stmt = con.prepareStatement("update Screening set dateStart = ?, idMovie = ?, idRoom = ? where idScreening = ?\r\n"
					+ "update ScreeningSitting set dateStart = ?, idMovie = ?, idRoom = ? where idScreening = ?");
			stmt.setTimestamp(1, Timestamp.valueOf(sc.getTimeStart()));
			stmt.setString(2, sc.getMovie().getIdMovie());
			stmt.setString(3, sc.getRoom().getIdRoom());
			stmt.setString(4, sc.getIdScreening());
			stmt.setTimestamp(5, Timestamp.valueOf(sc.getTimeStart()));
			stmt.setString(6, sc.getMovie().getIdMovie());
			stmt.setString(7, sc.getRoom().getIdRoom());
			stmt.setString(8, sc.getIdScreening());
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n != 0;
	}
	
	public boolean delete(String id) {
		int n = 0;
		Database.getInstance();
		Connection con = Database.getConnection();
		try {
			PreparedStatement stmt = con.prepareStatement("delete from ScreeningSitting where idScreening=?\r\n"
					+ "delete from Screening where idScreening=?");
			stmt.setString(1, id);
			stmt.setString(2, id);
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n != 0;
	}
	
	public void sortByNameASC(){
		Collections.sort(list, new Comparator<Screening>() {

			@Override
			public int compare(Screening o1, Screening o2) {
				// TODO Auto-generated method stub
				Movie movie1=movieDAO.getMovieByID(o1.getMovie().getIdMovie());
				Movie movie2=movieDAO.getMovieByID(o2.getMovie().getIdMovie());
				return movie1.getName().compareTo(movie2.getName());
			}
			
		});
	}
	
	public void sortByNameDESC(){
		Collections.sort(list, new Comparator<Screening>() {

			@Override
			public int compare(Screening o1, Screening o2) {
				// TODO Auto-generated method stub
				Movie movie1=movieDAO.getMovieByID(o1.getMovie().getIdMovie());
				Movie movie2=movieDAO.getMovieByID(o2.getMovie().getIdMovie());
				return movie2.getName().compareTo(movie1.getName());
			}
			
		});
	}
	
	public void sortByIdASC(){
		Collections.sort(list, new Comparator<Screening>() {

			@Override
			public int compare(Screening o1, Screening o2) {
				// TODO Auto-generated method stub
				return Integer.parseInt(o1.getIdScreening().substring(2))
						- Integer.parseInt(o2.getIdScreening().substring(2));
			}
			
		});
	}
	
	public void sortByIdDESC(){
		Collections.sort(list, new Comparator<Screening>() {

			@Override
			public int compare(Screening o1, Screening o2) {
				// TODO Auto-generated method stub
				return Integer.parseInt(o2.getIdScreening().substring(2))
						- Integer.parseInt(o1.getIdScreening().substring(2));
			}
			
		});
	}
	
	public void sortByTimeASC(){
		Collections.sort(list, new Comparator<Screening>() {

			@Override
			public int compare(Screening o1, Screening o2) {
				// TODO Auto-generated method stub
				return o1.getTimeStart().compareTo(o2.getTimeStart());
			}
			
		});
	}
	
	public void sortByTimeDESC(){
		Collections.sort(list, new Comparator<Screening>() {

			@Override
			public int compare(Screening o1, Screening o2) {
				// TODO Auto-generated method stub
				return o2.getTimeStart().compareTo(o1.getTimeStart());
			}
			
		});
	}
	
	public void sortByRoomASC(){
		Collections.sort(list, new Comparator<Screening>() {

			@Override
			public int compare(Screening o1, Screening o2) {
				// TODO Auto-generated method stub
				return o1.getRoom().getIdRoom().compareTo(o2.getRoom().getIdRoom());
			}
			
		});
	}
	
	public void sortByRoomDESC(){
		Collections.sort(list, new Comparator<Screening>() {

			@Override
			public int compare(Screening o1, Screening o2) {
				// TODO Auto-generated method stub
				return o2.getRoom().getIdRoom().compareTo(o1.getRoom().getIdRoom());
			}
			
		});
	}
	
	public ArrayList<Screening> getAllScreeningSearchByName(String key) {
		ArrayList<Screening> listScreening = new ArrayList<Screening>();
		
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("SELECT idScreening, dateStart, sc.idMovie, idRoom "
					+ "FROM Screening sc JOIN Movie m ON sc.idMovie=m.idMovie "
					+ "WHERE name LIKE ?");
			stmt.setString(1, "%"+key+"%");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String idScreening = rs.getString(1);
				LocalDateTime dateStart = rs.getTimestamp(2).toLocalDateTime();
				Movie movie = new Movie(rs.getString(3));
				Room room = new Room(rs.getString(4));
				listScreening.add(new Screening(idScreening, dateStart, room, movie));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listScreening;
	}
	
	public ArrayList<Screening> getAllScreeningSearchByRoom(String key) {
		ArrayList<Screening> listScreening = new ArrayList<Screening>();
		
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("SELECT idScreening, dateStart, sc.idMovie, sc.idRoom"
					+ " FROM Screening sc JOIN Room r ON sc.idRoom=r.idRoom "
					+ "WHERE numberRoom=?");
			stmt.setInt(1, Integer.parseInt(key));
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String idScreening = rs.getString(1);
				LocalDateTime dateStart = rs.getTimestamp(2).toLocalDateTime();
				Movie movie = new Movie(rs.getString(3));
				Room room = new Room(rs.getString(4));
				listScreening.add(new Screening(idScreening, dateStart, room, movie));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listScreening;
	} 
	
	public ArrayList<Screening> getAllScreeingSearchByTime(String dateStart, String dateEnd) {
		ArrayList<Screening> listScreening = new ArrayList<Screening>();
		Database.getInstance();
		Connection con = Database.getConnection();
		try {
			PreparedStatement stmt = con.prepareStatement("select * from Screening sc join Room r on sc.idRoom = r.idRoom "
					+ "where CONVERT(time, dateStart) between ? and ?");
			stmt.setString(1, dateStart);
			stmt.setString(2, dateEnd);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String idScreening = rs.getString(1);
				LocalDateTime dateStartScreening = rs.getTimestamp(2).toLocalDateTime();
				Movie movie = new Movie(rs.getString(3));
				Room room = new Room(rs.getString(4));
				listScreening.add(new Screening(idScreening, dateStartScreening, room, movie));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listScreening;
	}
}
