package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;

import controller.Database;
import entity.Movie;
import entity.Room;
import entity.Screening;

public class Room_DAO {
	
	private ArrayList<Room> list;
	
	public Room_DAO() {
		list=new ArrayList<Room>();
	}
	
	public ArrayList<Room> getList() {
		return list;
	}
	public void setList(ArrayList<Room> list) {
		this.list = list;
	}

	public ArrayList<Room> getAllRoom() {
		ArrayList<Room> listRoom = new ArrayList<Room>();
		
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			String sql = "select * from Room";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String idRoom = rs.getString(1);
				int numberRoom=rs.getInt(2);
				listRoom.add(new Room(idRoom, numberRoom));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listRoom;
	}
	
	public Room getRoomById(String id) {
		Database.getInstance();
		Connection con = Database.getConnection();
		PreparedStatement stmt;
		try {
			stmt=con.prepareStatement("select * from Room where idRoom=?");
			stmt.setString(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				String idRoom = rs.getString(1);
				int numberRoom=rs.getInt(2);
				return new Room(idRoom, numberRoom);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Room getRoomByNumber(int number) {
		Database.getInstance();
		Connection con = Database.getConnection();
		PreparedStatement stmt;
		try {
			stmt=con.prepareStatement("select * from Room where numberRoom=?");
			stmt.setInt(1, number);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				String idRoom = rs.getString(1);
				int numberRoom=rs.getInt(2);
				return new Room(idRoom, numberRoom);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
