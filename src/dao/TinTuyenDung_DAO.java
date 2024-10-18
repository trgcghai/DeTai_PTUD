package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import controller.Database;
import entity.NhaTuyenDung;
import entity.TinTuyenDung;

public class TinTuyenDung_DAO {

	public ArrayList<TinTuyenDung> getDsTinTuyenDung() {
		ArrayList<TinTuyenDung> list = new ArrayList<TinTuyenDung>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			String sql = "select * from TinTuyenDung";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String maTTD = rs.getString(1);
				String tieuDe = rs.getString(2);
				String moTa = rs.getString(3);
				LocalDate ngayDangTin = rs.getDate(4).toLocalDate();
				LocalDate ngayHetHan = rs.getDate(5).toLocalDate();
				String trinhDo = rs.getString(6);
				int soLuong = rs.getInt(7);
				double luong = rs.getDouble(8);
				String nganhNghe = rs.getString(9);
				boolean trangThai = rs.getBoolean(10);
				String hinhThuc = rs.getString(11);
				NhaTuyenDung nhaTuyenDung = new NhaTuyenDung(rs.getString(12));
				
				list.add(new TinTuyenDung(maTTD, tieuDe, moTa, ngayDangTin, ngayHetHan, trinhDo, 
						soLuong, luong, nganhNghe, hinhThuc, trangThai, nhaTuyenDung));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	} 
	
	public TinTuyenDung getTinTuyenDung(String ma) {
		ArrayList<TinTuyenDung> list = new ArrayList<TinTuyenDung>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("select * from TinTuyenDung where maTTD = ?");
			stmt.setString(1, ma);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maTTD = rs.getString(1);
				String tieuDe = rs.getString(2);
				String moTa = rs.getString(3);
				LocalDate ngayDangTin = rs.getDate(4).toLocalDate();
				LocalDate ngayHetHan = rs.getDate(5).toLocalDate();
				String trinhDo = rs.getString(6);
				int soLuong = rs.getInt(7);
				double luong = rs.getDouble(8);
				String nganhNghe = rs.getString(9);
				boolean trangThai = rs.getBoolean(10);
				String hinhThuc = rs.getString(11);
				NhaTuyenDung nhaTuyenDung = new NhaTuyenDung(rs.getString(12));
				
				list.add(new TinTuyenDung(maTTD, tieuDe, moTa, ngayDangTin, ngayHetHan, trinhDo, 
						soLuong, luong, nganhNghe, hinhThuc, trangThai, nhaTuyenDung));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list.get(0);
	}
	
	public ArrayList<TinTuyenDung> getTinTuyenDungTheoNTD(String ma) {
		ArrayList<TinTuyenDung> list = new ArrayList<TinTuyenDung>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("select * from TinTuyenDung where maNTD = ?");
			stmt.setString(1, ma);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maTTD = rs.getString(1);
				String tieuDe = rs.getString(2);
				String moTa = rs.getString(3);
				LocalDate ngayDangTin = rs.getDate(4).toLocalDate();
				LocalDate ngayHetHan = rs.getDate(5).toLocalDate();
				String trinhDo = rs.getString(6);
				int soLuong = rs.getInt(7);
				double luong = rs.getDouble(8);
				String nganhNghe = rs.getString(9);
				boolean trangThai = rs.getBoolean(10);
				String hinhThuc = rs.getString(11);
				NhaTuyenDung nhaTuyenDung = new NhaTuyenDung(rs.getString(12));
				
				list.add(new TinTuyenDung(maTTD, tieuDe, moTa, ngayDangTin, ngayHetHan, trinhDo, 
						soLuong, luong, nganhNghe, hinhThuc, trangThai, nhaTuyenDung));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public TinTuyenDung getTinTuyenDungTheoHoSo(String ma) {
		Database.getInstance();
		Connection con = Database.getConnection();
		TinTuyenDung tinTuyenDung = null;
		try {
			PreparedStatement stmt = con.prepareStatement("select maTTD from HoSo where maHS = ?");
			stmt.setString(1, ma);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				tinTuyenDung = getTinTuyenDung(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tinTuyenDung;
	}
}
