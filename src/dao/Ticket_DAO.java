package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import controller.Database;
import entity.Screening;
import entity.Ticket;

public class Ticket_DAO {
	private ArrayList<Ticket> list;
	
	public Ticket_DAO() {
		list=new ArrayList<Ticket>();
	}
	
	public ArrayList<Ticket> getList() {
		return list;
	}
	public void setList(ArrayList<Ticket> list) {
		this.list = list;
	}
	
	public ArrayList<Ticket> getAllTicket(){
		ArrayList<Ticket> listTicket=new ArrayList<Ticket>();
		Database.getInstance();
		Connection con=Database.getConnection();
		try {
			Statement stm=con.createStatement();
			ResultSet rs=stm.executeQuery("Select * from Ticket");
			while(rs.next()) {
				String idTicket=rs.getString(1);
				LocalDateTime dateBook=rs.getTimestamp(2).toLocalDateTime();
				double price=rs.getFloat(3);
				Screening sc=new Screening(rs.getString(4));
				
				listTicket.add(new Ticket(idTicket, dateBook, price, sc));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listTicket;
	}
	
	public ArrayList<Ticket> getAllTicketById(String id){
		ArrayList<Ticket> listTicket=new ArrayList<Ticket>();
		Database.getInstance();
		Connection con=Database.getConnection();
		try {
			PreparedStatement stmt=con.prepareStatement("Select * from Ticket where idTicket = ?");
			stmt.setString(1, id);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				String idTicket=rs.getString(1);
				LocalDateTime dateBook=rs.getTimestamp(2).toLocalDateTime();
				double price=rs.getFloat(3);
				Screening sc=new Screening(rs.getString(4));
				
				listTicket.add(new Ticket(idTicket, dateBook, price, sc));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listTicket;
	}

	public boolean create(Ticket ticket) {
		int n = 0;
		Database.getInstance();
		Connection con = Database.getConnection();
		try {
			PreparedStatement stmt = con.prepareStatement("insert into Ticket values (?, ?, ?, ?)");
			stmt.setString(1, ticket.getIdTicket());
			stmt.setTimestamp(2, Timestamp.valueOf(ticket.getDateBook()));
			stmt.setDouble(3, ticket.getPrice());
			stmt.setString(4, ticket.getScreening().getIdScreening());
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n != 0;
	}
}
