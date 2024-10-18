package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import controller.Database;
import entity.NhanVien;

public class NhanVien_DAO {
	
	public ArrayList<NhanVien> getDSNhanVien() {
		ArrayList<NhanVien> list = new ArrayList<NhanVien>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			String sql = "select * from NhanVien";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String ma = rs.getString(1);
				String ten = rs.getString(2);
				Date dob = rs.getDate(3);
				String diaChi = rs.getString(4);
				String gioiTinh = rs.getString(5);
				String sdt = rs.getString(6);
				Date ngayVaoLam = rs.getDate(7);
				
				list.add(new NhanVien(ma, ten, dob.toLocalDate(), diaChi, gioiTinh, ten, ngayVaoLam.toLocalDate()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	} 
	
	public NhanVien getNhanVien(String ma) {
		ArrayList<NhanVien> list = new ArrayList<NhanVien>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("select * from NhanVien where maNV = ?");
			stmt.setString(1, ma);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String id = rs.getString(1);
				String ten = rs.getString(2);
				Date dob = rs.getDate(3);
				String diaChi = rs.getString(4);
				String gioiTinh = rs.getString(5);
				String sdt = rs.getString(6);
				Date ngayVaoLam = rs.getDate(7);
				
				list.add(new NhanVien(ma, ten, dob.toLocalDate(), diaChi, gioiTinh, ten, ngayVaoLam.toLocalDate()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list.get(0);
	}
}
