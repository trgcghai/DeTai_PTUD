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
import entity.constraint.TrangThai;

public class HoSo_DAO {
	
	private ArrayList<HoSo> listHoSo;
	
	public HoSo_DAO() {
		listHoSo=new ArrayList<HoSo>();
	}
	
	public ArrayList<HoSo> getListHoSo() {
		return listHoSo;
	}

	public void setListHoSo(ArrayList<HoSo> listHoSo) {
		this.listHoSo = listHoSo;
	}

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
				
				TrangThai trangthai=null;
				for(TrangThai t: TrangThai.class.getEnumConstants()) {
					if(t.getValue().equalsIgnoreCase(rs.getString(3))) {
						trangthai=t;
					}
				}
				TinTuyenDung tinTuyenDung = new TinTuyenDung(rs.getString(4));
				UngVien ungVien = new UngVien(rs.getString(5));
				NhanVien nhanVien = new NhanVien(rs.getString(6));
				
				list.add(new HoSo(maHS, moTa, trangthai, ungVien, tinTuyenDung, nhanVien));
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
			PreparedStatement stmt = con.prepareStatement("select * from HoSo where MaHS = ?");
			stmt.setString(1, ma);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maHS = rs.getString(1);
				String moTa = rs.getString(2);
				TrangThai trangthai=null;
				for(TrangThai t: TrangThai.class.getEnumConstants()) {
					if(t.getValue().equalsIgnoreCase(rs.getString(3))) {
						trangthai=t;
					}
				}
				TinTuyenDung tinTuyenDung = rs.getString(4)!=null?new TinTuyenDung(rs.getString(4)):null;;
				UngVien ungVien = new UngVien(rs.getString(5));
				NhanVien nhanVien = new NhanVien(rs.getString(6));
				
				list.add(new HoSo(maHS, moTa, trangthai, ungVien, tinTuyenDung, nhanVien));
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
				TrangThai trangthai=null;
				for(TrangThai t: TrangThai.class.getEnumConstants()) {
					if(t.getValue().equalsIgnoreCase(rs.getString(3))) {
						trangthai=t;
					}
				}
				TinTuyenDung tinTuyenDung = new TinTuyenDung(rs.getString(4));
				UngVien ungVien = new UngVien(rs.getString(5));
				NhanVien nhanVien = new NhanVien(rs.getString(6));
				
				list.add(new HoSo(maHS, moTa, trangthai, ungVien, tinTuyenDung, nhanVien));
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
			PreparedStatement stmt = con.prepareStatement("select * from HoSo where MaUV = ?");
			stmt.setString(1, ma);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maHS = rs.getString(1);
				String moTa = rs.getString(2);
				TrangThai trangthai=null;
				for(TrangThai t: TrangThai.class.getEnumConstants()) {
					if(t.getValue().equalsIgnoreCase(rs.getString(3))) {
						trangthai=t;
					}
				}
				TinTuyenDung tinTuyenDung = rs.getString(4)!=null?new TinTuyenDung(rs.getString(4)):null;
				UngVien ungVien = new UngVien(rs.getString(5));
				NhanVien nhanVien = new NhanVien(rs.getString(6));
				
				list.add(new HoSo(maHS, moTa, trangthai, ungVien, tinTuyenDung, nhanVien));
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
			PreparedStatement stmt = con.prepareStatement("select TrangThai from HoSo where MaHS = ?");
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
	
	public boolean create(HoSo hs) {
		int n = 0;
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("insert into HoSo values (?, ?, ?, ?, ?, ?)");
			stmt.setString(1, hs.getMaHS());
			stmt.setString(2, hs.getMoTa());
			stmt.setString(3, hs.getTrangThai().getValue());
			stmt.setString(4, hs.getTinTuyenDung()==null?null:hs.getTinTuyenDung().getMaTTD());
			stmt.setString(5, hs.getUngVien()==null?null:hs.getUngVien().getMaUV());
			stmt.setString(6, hs.getNhanVien()==null?null:hs.getNhanVien().getMaNV());
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n != 0;
	}
	
	public boolean update(HoSo hs) {
		int n = 0;
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("update HoSo set TrangThai = ?, MoTa = ? where MaHS = ?");
			stmt.setString(1, hs.getMoTa());
			stmt.setString(2, hs.getTrangThai().getValue());
			stmt.setString(3, hs.getMaHS());
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
			PreparedStatement stmt = con.prepareStatement("delete HoSo where MaHS = ?");
			stmt.setString(1, id);
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n != 0;
	}
}
