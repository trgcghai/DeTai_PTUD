package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import controller.Database;
import entity.UngVien;

public class UngVien_DAO {
	
	public ArrayList<UngVien> getDSUngVien() {
		ArrayList<UngVien> list = new ArrayList<UngVien>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			String sql = "select * from UngVien";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String maUV = rs.getString(1);
				String tenUV = rs.getString(2);
				String email = rs.getString(3);
				LocalDate ngaySinh = rs.getDate(4).toLocalDate();
				String diaChi = rs.getString(5);
				String gioiTinh = rs.getString(6);
				String soDienThoai = rs.getString(7);
				
				list.add(new UngVien(maUV, tenUV, email, ngaySinh, diaChi, gioiTinh, soDienThoai));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	} 
	
	public UngVien getUngVien(String ma) {
		ArrayList<UngVien> list = new ArrayList<UngVien>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("select * from UngVien where maUV = ?");
			stmt.setString(1, ma);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maUV = rs.getString(1);
				String tenUV = rs.getString(2);
				String email = rs.getString(3);
				LocalDate ngaySinh = rs.getDate(4).toLocalDate();
				String diaChi = rs.getString(5);
				String gioiTinh = rs.getString(6);
				String soDienThoai = rs.getString(7);
				
				list.add(new UngVien(maUV, tenUV, email, ngaySinh, diaChi, gioiTinh, soDienThoai));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list.get(0);
	}
}
