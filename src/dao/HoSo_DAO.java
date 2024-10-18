package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import controller.Database;
import entity.HoSo;
import entity.NhanVien;
import entity.TinTuyenDung;
import entity.UngVien;

public class HoSo_DAO {
	
	public ArrayList<HoSo> getDSHoSo() {
		ArrayList<HoSo> list = new ArrayList<HoSo>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			String sql = "select * from HoSo";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String maHS = rs.getString(1);
				String moTa = rs.getString(2);
				String trangThai = rs.getNString(3);
				TinTuyenDung tinTuyenDung = new TinTuyenDung(rs.getString(4));
				UngVien ungVien = new UngVien(rs.getString(5));
				NhanVien nhanVien = new NhanVien(rs.getString(6));
				
				list.add(new HoSo(maHS, moTa, trangThai, ungVien, tinTuyenDung, nhanVien));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	} 
	
	public HoSo getHoSo(String ma) {
		ArrayList<HoSo> list = new ArrayList<HoSo>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("select * from HoSo where maHS = ?");
			stmt.setString(1, ma);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maHS = rs.getString(1);
				String moTa = rs.getString(2);
				String trangThai = rs.getNString(3);
				TinTuyenDung tinTuyenDung = new TinTuyenDung(rs.getString(4));
				UngVien ungVien = new UngVien(rs.getString(5));
				NhanVien nhanVien = new NhanVien(rs.getString(6));
				
				list.add(new HoSo(maHS, moTa, trangThai, ungVien, tinTuyenDung, nhanVien));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list.get(0);
	}
	
	public ArrayList<HoSo> getHoSoTheoTinTD(String ma) {
		ArrayList<HoSo> list = new ArrayList<HoSo>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("select * from HoSo where maTTD = ?");
			stmt.setString(1, ma);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maHS = rs.getString(1);
				String moTa = rs.getString(2);
				String trangThai = rs.getNString(3);
				TinTuyenDung tinTuyenDung = new TinTuyenDung(rs.getString(4));
				UngVien ungVien = new UngVien(rs.getString(5));
				NhanVien nhanVien = new NhanVien(rs.getString(6));
				
				list.add(new HoSo(maHS, moTa, trangThai, ungVien, tinTuyenDung, nhanVien));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<HoSo> getHoSoTheoUngVien(String ma) {
		ArrayList<HoSo> list = new ArrayList<HoSo>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("select * from HoSo where maUV = ?");
			stmt.setString(1, ma);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maHS = rs.getString(1);
				String moTa = rs.getString(2);
				String trangThai = rs.getNString(3);
				TinTuyenDung tinTuyenDung = new TinTuyenDung(rs.getString(4));
				UngVien ungVien = new UngVien(rs.getString(5));
				NhanVien nhanVien = new NhanVien(rs.getString(6));
				
				list.add(new HoSo(maHS, moTa, trangThai, ungVien, tinTuyenDung, nhanVien));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public int tinhTongHoSoTheoNhanVien(String ma) {
		int res = 0;
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("select maNV, count(maHS) from HoSo where maNV = ? groupby maNV");
			stmt.setString(1, ma);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				res = rs.getInt(2);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public String getTrangThai(String ma) {
		String res = "";
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("select trangThai from HoSo where maHS = ?");
			stmt.setString(1, ma);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				res = rs.getNString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
}
