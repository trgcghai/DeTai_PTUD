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
import entity.HopDong;
import entity.NhanVien;
import entity.TinTuyenDung;
import entity.UngVien;

public class HopDong_DAO {
	
	private ArrayList<HopDong> listHopDong;
	
	public HopDong_DAO() {
		listHopDong=new ArrayList<HopDong>();
	}
	
	public ArrayList<HopDong> getListHopDong() {
		return listHopDong;
	}

	public void setListHopDong(ArrayList<HopDong> listHopDong) {
		this.listHopDong = listHopDong;
	}

	public ArrayList<HopDong> getDSHopDong() {
		ArrayList<HopDong> list = new ArrayList<HopDong>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			String sql = "select * from HopDong";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String maHD = rs.getString(1);
				double phiDichVu = rs.getDouble(2);
				LocalDate thoiGian = rs.getDate(3).toLocalDate();
				TinTuyenDung tinTuyenDung = new TinTuyenDung(rs.getString(4));
				UngVien ungVien = new UngVien(rs.getString(5));
				NhanVien nhanVien  = new NhanVien(rs.getString(6));
				
				list.add(new HopDong(maHD, phiDichVu, thoiGian, tinTuyenDung, ungVien, nhanVien));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	} 
	
	public HopDong getHopDong(String ma) {
		ArrayList<HopDong> list = new ArrayList<HopDong>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("select * from HopDong where maHD = ?");
			stmt.setString(1, ma);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maHD = rs.getString(1);
				double phiDichVu = rs.getDouble(2);
				LocalDate thoiGian = rs.getDate(3).toLocalDate();
				TinTuyenDung tinTuyenDung = new TinTuyenDung(rs.getString(4));
				UngVien ungVien = new UngVien(rs.getString(5));
				NhanVien nhanVien  = new NhanVien(rs.getString(6));
				
				list.add(new HopDong(maHD, phiDichVu, thoiGian, tinTuyenDung, ungVien, nhanVien));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list.get(0);
	}
	
	public boolean create(HopDong hd) {
		int n = 0;
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("insert into HopDong values (?, ?, ?, ?, ?, ?)");
			stmt.setString(1, hd.getMaHD());
			stmt.setDouble(2, hd.getPhiDichVu());
			stmt.setDate(3, Date.valueOf(hd.getThoiGian()));
			stmt.setString(4, hd.getTinTuyenDung().getMaTTD());
			stmt.setString(5, hd.getUngVien().getMaUV());
			stmt.setString(6, hd.getNhanVien().getMaNV());
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n != 0;
	}
	
}
