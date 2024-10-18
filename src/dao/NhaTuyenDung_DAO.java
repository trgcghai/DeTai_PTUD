package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import controller.Database;
import entity.NhanVien;
import entity.NhaTuyenDung;

public class NhaTuyenDung_DAO {
	
	private ArrayList<NhaTuyenDung> listNhatuyenDung;
	
	public NhaTuyenDung_DAO() {
		listNhatuyenDung=new ArrayList<NhaTuyenDung>();
	}

	public ArrayList<NhaTuyenDung> getListNhatuyenDung() {
		return listNhatuyenDung;
	}

	public void setListNhatuyenDung(ArrayList<NhaTuyenDung> listNhatuyenDung) {
		this.listNhatuyenDung = listNhatuyenDung;
	}

	public ArrayList<NhaTuyenDung> getDsNhaTuyenDung() {
		ArrayList<NhaTuyenDung> list = new ArrayList<NhaTuyenDung>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			String sql = "select * from NhaTuyenDung";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String maNTD = rs.getString(1);
				String tenNTD = rs.getString(2);
				String email = rs.getString(3);
				String logo = rs.getString(4);
				String diaChi = rs.getString(5);
				String soDienThoai = rs.getString(6);
				
				list.add(new NhaTuyenDung(maNTD, tenNTD, email, logo, soDienThoai, diaChi));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	} 
	
	public NhaTuyenDung getNhaTuyenDung(String ma) {
		ArrayList<NhaTuyenDung> list = new ArrayList<NhaTuyenDung>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("select * from NhaTuyenDung where maNTD = ?");
			stmt.setString(1, ma);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maNTD = rs.getString(1);
				String tenNTD = rs.getString(2);
				String email = rs.getString(3);
				String logo = rs.getString(4);
				String diaChi = rs.getString(5);
				String soDienThoai = rs.getString(6);
				
				list.add(new NhaTuyenDung(maNTD, tenNTD, email, logo, soDienThoai, diaChi));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list.get(0);
	}
	
	public boolean create(NhaTuyenDung ntd) {
		int n = 0;
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("insert into NhaTuyenDung values (?, ?, ?, ?, ?, ?)");
			stmt.setString(1, ntd.getMaNTD());
			stmt.setString(2, ntd.getTenNTD());
			stmt.setString(3, ntd.getEmail());
			stmt.setString(4, ntd.getLogo());
			stmt.setString(5, ntd.getDiaChi());
			stmt.setString(6, ntd.getSoDienThoai());
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n != 0;
	}
	
	public boolean update(NhaTuyenDung ntd) {
		int n = 0;
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("update NhaTuyenDung set TenNTD = ?, Email = ?, Logo = ?, DiaChi = ?, SoDienThoai = ? where MaNTD = ?");
			stmt.setString(1, ntd.getTenNTD());
			stmt.setString(2, ntd.getEmail());
			stmt.setString(3, ntd.getLogo());
			stmt.setString(4, ntd.getDiaChi());
			stmt.setString(5, ntd.getSoDienThoai());
			stmt.setString(6, ntd.getMaNTD());
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
			PreparedStatement stmt = con.prepareStatement("delete NhaTuyenDung where MaNTD = ?");
			stmt.setString(1, id);
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n != 0;
	}
}
