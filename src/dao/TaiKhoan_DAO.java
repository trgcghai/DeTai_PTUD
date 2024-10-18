package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import controller.Database;
import entity.TaiKhoan;
import entity.NhanVien;

public class TaiKhoan_DAO {
	
	public ArrayList<TaiKhoan> getDsTaiKhoan() {
		ArrayList<TaiKhoan> list = new ArrayList<TaiKhoan>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			String sql = "select * from TaiKhoan";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String ma = rs.getString(1);
				String email = rs.getString(2);
				String matKhau = rs.getString(3);
				String vaiTro = rs.getString(4);
				NhanVien nhanVien = new NhanVien(rs.getString(5));
				
				list.add(new TaiKhoan(ma, email, matKhau, vaiTro, nhanVien));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	} 
	
	public TaiKhoan getTaiKhoan(String emailFind) {
		ArrayList<TaiKhoan> list = new ArrayList<TaiKhoan>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("select * from TaiKhoan where email like ?");
			stmt.setString(1, "%" + emailFind + "%");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String ma = rs.getString(1);
				String email = rs.getString(2);
				String matKhau = rs.getString(3);
				String vaiTro = rs.getString(4);
				NhanVien nhanVien = new NhanVien(rs.getString(5));
				
				list.add(new TaiKhoan(ma, email, matKhau, vaiTro, nhanVien));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list.get(0);
	}
	
	public String getVaiTro(String email) {
		Database.getInstance();
		Connection con = Database.getConnection();
		String result = "";
		try {
			PreparedStatement stmt = con.prepareStatement("select vaiTro from TaiKhoan where email like ?");
			stmt.setString(1, "%" + email + "%");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				result = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
