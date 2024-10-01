package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import controller.Database;
import entity.*;

public class Director_DAO {
	private ArrayList<Director> list;
	
	public Director_DAO() {
		list=new ArrayList<Director>();
	}
	
	public ArrayList<Director> getList() {
		return list;
	}
	public void setList(ArrayList<Director> list) {
		this.list = list;
	}

	public ArrayList<Director> getAllDirector() {
		ArrayList<Director> listDirector = new ArrayList<Director>(); 
		Database.getInstance();
		
		Connection con = Database.getConnection();
		
		Statement stmt;
		try {
			String sql = "Select * from Director";
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String id = rs.getString(1);
				String name = rs.getString(2);
				LocalDate birthday;
				if(rs.getDate(3)!=null) {
					birthday = rs.getDate(3).toLocalDate();					
				}
				else {
					birthday = null;
				}
				
				listDirector.add(new Director(id, name, (LocalDate) birthday));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listDirector;
	}
	
	public Director getDirectorById(String id) {
		Database.getInstance();
		
		Connection con=Database.getConnection();
		PreparedStatement stmt;
		
		try {
			stmt=con.prepareStatement("Select * from Director where idDirector = ?");
			stmt.setString(1, id);
			ResultSet rs=stmt.executeQuery();
			if(rs.next()) {
				String idDirector=rs.getString(1);
				String name=rs.getString(2);
				LocalDate birthday;
				if(rs.getDate(3)!=null) {
					birthday=rs.getDate(3).toLocalDate();
				}
				else {
					birthday=null;
				}
				
				return new Director(idDirector, name, birthday);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Director getDirectorByName(String name) {
		Database.getInstance();
		Connection con = Database.getConnection();
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement("Select * from Director where name = ?");
			stmt.setString(1, name);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				String id = rs.getString(1);
				String nameDirector = rs.getString(2);
				LocalDate birthday;
				if(rs.getDate(3)!=null) {
					birthday = rs.getDate(3).toLocalDate();					
				}
				else {
					birthday = null;			
				}
				
				return new Director(id, nameDirector, (LocalDate) birthday);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean create(Director director) {
		int n = 0;
		Connection con = Database.getConnection();
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement("insert into Director values (?, ?, ?)");
			stmt.setString(1, director.getIdDirector());
			stmt.setString(2, director.getName());
			if(director.getBirthday()!=null) {
				stmt.setDate(3, Date.valueOf(director.getBirthday()));				
			}
			else {
				stmt.setDate(3, null);	
			}
			
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return n != 0;
	}
	
	public boolean delete(String id) {
		int n = 0;
		Connection con = Database.getConnection();
		PreparedStatement stmt;
		
		try {
			stmt = con.prepareStatement("delete \r\n"
					+ "from ScreeningSitting\r\n"
					+ "where idMovie= (\r\n"
					+ "	select idMovie from Movie m join Director d on m.idDirector=d.idDirector\r\n"
					+ "	where d.idDirector=?\r\n"
					+ ")"
					+ "delete from Director where idDirector = ?");
			stmt.setString(1, id);
			stmt.setString(2, id);
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return n != 0;
	}
	
	public boolean update(Director director) {
		int n = 0;
		Connection con = Database.getConnection();
		PreparedStatement stmt;
		
		try {
			stmt = con.prepareStatement("update Director set name = ?, birthday = ? where idDirector = ?");
			stmt.setString(1, director.getName());
			stmt.setDate(2, Date.valueOf(director.getBirthday()));
			stmt.setString(3, director.getIdDirector());
			
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return n != 0;
	}
	
	public ArrayList<Director> getAllDirectorByName(String name) {
		ArrayList<Director> list=new ArrayList<Director>();
		Database.getInstance();
		Connection con = Database.getConnection();
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement("Select * from Director where name like ?");
			stmt.setString(1, "%"+name+"%");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String id = rs.getString(1);
				String nameDirector = rs.getString(2);
				LocalDate birthday;
				if(rs.getDate(3)!=null) {
					birthday = rs.getDate(3).toLocalDate();					
				}
				else {
					birthday = null;			
				}
				
				list.add(new Director(id, nameDirector, (LocalDate) birthday));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<Director> getAllDirectorById(String idDirector) {
		ArrayList<Director> list=new ArrayList<Director>();
		Database.getInstance();
		Connection con = Database.getConnection();
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement("Select * from Director where idDirector like ?");
			stmt.setString(1, "%"+idDirector+"%");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String id = rs.getString(1);
				String nameDirector = rs.getString(2);
				LocalDate birthday;
				if(rs.getDate(3)!=null) {
					birthday = rs.getDate(3).toLocalDate();					
				}
				else {
					birthday = null;			
				}
				
				list.add(new Director(id, nameDirector, (LocalDate) birthday));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public void sortByNameDirectorASC() {
		Collections.sort(list, new Comparator<Director>() {
			@Override
			public int compare(Director o1, Director o2) {
				// TODO Auto-generated method stub
				return o1.getName().compareTo(o2.getName());
			}
			
		});
	}
	
	public void sortByNameDirectorDESC() {
		Collections.sort(list, new Comparator<Director>() {
			@Override
			public int compare(Director o1, Director o2) {
				// TODO Auto-generated method stub
				return o2.getName().compareTo(o1.getName());
			}
			
		});
	}
	
	public void sortByIdDirectorASC() {
		Collections.sort(list, new Comparator<Director>() {
			@Override
			public int compare(Director o1, Director o2) {
				// TODO Auto-generated method stub
				String d1=o1.getIdDirector().substring(2);
				String d2=o2.getIdDirector().substring(2);
				return Integer.parseInt(d1) - Integer.parseInt(d2);
			}
			
		});
	}
	
	public void sortByIdDirectorDESC() {
		Collections.sort(list, new Comparator<Director>() {
			@Override
			public int compare(Director o1, Director o2) {
				// TODO Auto-generated method stub
				String d1=o1.getIdDirector().substring(2);
				String d2=o2.getIdDirector().substring(2);
				return Integer.parseInt(d2) - Integer.parseInt(d1);
			}
			
		});
	}
}
