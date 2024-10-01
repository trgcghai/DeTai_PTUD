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
import entity.Sitting;
import entity.Ticket;

public class Sitting_DAO {
	private ArrayList<Sitting> list;
	
	public Sitting_DAO() {
		list=new ArrayList<Sitting>();
	}

	public ArrayList<Sitting> getList() {
		return list;
	}

	public void setList(ArrayList<Sitting> list) {
		this.list = list;
	}
	
	public ArrayList<Sitting> getAllSitting() {
		ArrayList<Sitting> listSitting = new ArrayList<Sitting>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			String sql = "select * from Sitting";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String idSitting=rs.getString(1);
				String numberSitting=rs.getString(2);
				boolean state=(rs.getInt(3)==1?true:false);
				Room room=new Room(rs.getString(4));
				Ticket ticket=(rs.getString(5)==null?new Ticket():new Ticket(rs.getString(5)));
				
				listSitting.add(new Sitting(idSitting, numberSitting, state, room, ticket));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listSitting;
	}
	
	public ArrayList<Sitting> getAllSittingByScreening(String id){
		ArrayList<Sitting> listSitting=new ArrayList<Sitting>();
		Database.getInstance();
		Connection con=Database.getConnection();
		try {
			PreparedStatement stmt=con.prepareStatement("select idSitting, numberSitting, state, s.idRoom, idTicket "
					+ "from Screening sc join Sitting s on sc.idRoom=s.idRoom \r\n"
					+ "where idScreening=?");
			stmt.setString(1, id);
			ResultSet rs=stmt.executeQuery();
			while(rs.next()) {
				String idSitting=rs.getString(1);
				String numberSitting=rs.getString(2);
				boolean state=(rs.getInt(3)==1?true:false);
				Room room=new Room(rs.getString(4));
				Ticket ticket=(rs.getString(5)==null?new Ticket():new Ticket(rs.getString(5)));
				
				listSitting.add(new Sitting(idSitting, numberSitting, state, room, ticket));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listSitting;
	}
	
	public ArrayList<Sitting> getSittingByTicket(String id){
		ArrayList<Sitting> listSitting=new ArrayList<Sitting>();
		Database.getInstance();
		Connection con=Database.getConnection();
		try {
			PreparedStatement stmt=con.prepareStatement("select idRoom, idSitting, numberSitting, state, t.idTicket from Ticket t\r\n"
					+ "join ScreeningSitting ss on t.idTicket = ss.idTicket\r\n"
					+ "where t.idTicket = ?");
			stmt.setString(1, id);
			ResultSet rs=stmt.executeQuery();
			while(rs.next()) {
				Room room=new Room(rs.getString(1));
				String idSitting=rs.getString(2);
				String numberSitting=rs.getString(3);
				boolean state=(rs.getInt(4) == 1);
				Ticket ticket=(rs.getString(5) == null ? new Ticket() : new Ticket(rs.getString(5)));
				
				listSitting.add(new Sitting(idSitting, numberSitting, state, room, ticket));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listSitting;
	}

	
	public boolean updateTicketAndStateForSitting(Screening screening, Sitting sitting, Ticket ticket) {
		Database.getInstance();
		Connection con=Database.getConnection();
		int n=0;
		try {
			PreparedStatement stmt=con.prepareStatement("update ScreeningSitting\r\n"
					+ "set state=1, idTicket=? \r\n"
					+ "where idScreening=? and numberSitting=?");
			stmt.setString(1, ticket.getIdTicket());
			stmt.setString(2, screening.getIdScreening());
			stmt.setString(3, sitting.getNumberSitting());
			
			n=stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return n>0;
	}
	
	public ArrayList<String> getSittingDone(Screening screening){
		ArrayList<String> sitDone=new ArrayList<String>();
		Database.getInstance();
		Connection con=Database.getConnection();
		try {
			PreparedStatement stmt=con.prepareStatement("select numberSitting\r\n"
					+ "from Screening s join ScreeningSitting ss on s.idScreening=ss.idScreening\r\n"
					+ "where state=1 and s.idScreening=?");
			stmt.setString(1, screening.getIdScreening());
			ResultSet rs=stmt.executeQuery();
			while(rs.next()) {
				sitDone.add(rs.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sitDone;
	}
}
