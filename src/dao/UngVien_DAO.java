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
import entity.NhanVien;
import entity.UngVien;
import entity.constraint.GioiTinh;

public class UngVien_DAO {
	
	private ArrayList<UngVien> listUngVien;
	
	public UngVien_DAO() {
		listUngVien=new ArrayList<UngVien>();
	}
	
	public ArrayList<UngVien> getListUngVien() {
		return listUngVien;
	}

	public void setListUngVien(ArrayList<UngVien> listUngVien) {
		this.listUngVien = listUngVien;
	}

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
				
				GioiTinh gioitinh=GioiTinh.KHAC;
				for(GioiTinh g: GioiTinh.class.getEnumConstants()) {
					if(g.getValue().equalsIgnoreCase(rs.getString(6))) {
						gioitinh=g;
					}
				}
				
				String soDienThoai = rs.getString(7);
				
				list.add(new UngVien(maUV, tenUV, email, ngaySinh, diaChi, gioitinh, soDienThoai));
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
				GioiTinh gioitinh=GioiTinh.KHAC;
				for(GioiTinh g: GioiTinh.class.getEnumConstants()) {
					if(g.getValue().equalsIgnoreCase(rs.getString(6))) {
						gioitinh=g;
					}
				}
				String soDienThoai = rs.getString(7);
				
				list.add(new UngVien(maUV, tenUV, email, ngaySinh, diaChi, gioitinh, soDienThoai));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list.get(0);
	}
	
	public ArrayList<UngVien> getUngVienBy(String key, int option) {
		ArrayList<UngVien> list = new ArrayList<UngVien>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt =null;
			if(option==1) {
				stmt = con.prepareStatement("Select * from UngVien where TenUV LIKE ?");
				stmt.setString(1, "%"+key+"%");
			}
			else if(option==2) {
				stmt = con.prepareStatement("Select * from UngVien where SoDienThoai LIKE ?");
				stmt.setString(1, "%"+key+"%");
			}
			else if(option==3) {
				String tenUV=key.split("/")[0];
				String sdt=key.split("/")[1];
				
				stmt = con.prepareStatement("Select * from UngVien where TenUV LIKE ? AND SoDienThoai LIKE ?");
				stmt.setString(1, "%"+tenUV+"%");
				stmt.setString(2, "%"+sdt+"%");
			}
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maUV = rs.getString(1);
				String tenUV = rs.getString(2);
				String email = rs.getString(3);
				LocalDate ngaySinh = rs.getDate(4).toLocalDate();
				String diaChi = rs.getString(5);
				GioiTinh gioitinh=GioiTinh.KHAC;
				for(GioiTinh g: GioiTinh.class.getEnumConstants()) {
					if(g.getValue().equalsIgnoreCase(rs.getString(6))) {
						gioitinh=g;
					}
				}
				String soDienThoai = rs.getString(7);
				list.add(new UngVien(maUV, tenUV, email, ngaySinh, diaChi, gioitinh, soDienThoai));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public boolean create(UngVien uv) {
		int n = 0;
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("insert into UngVien values (?, ?, ?, ?, ?, ?, ?)");
			stmt.setString(1, uv.getMaUV());
			stmt.setString(2, uv.getTenUV());
			stmt.setString(3, uv.getEmail());
			stmt.setDate(4, Date.valueOf(uv.getNgaySinh()));
			stmt.setString(5, uv.getDiaChi());
			stmt.setString(6, uv.getGioiTinh().getValue());
			stmt.setString(7, uv.getSoDienThoai());
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n != 0;
	}
	
	public boolean update(UngVien uv) {
		int n = 0;
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("update UngVien set TenUV = ?, Email = ?, NgaySinh = ?, DiaChi = ?, GioiTinh = ?, SoDienThoai = ? where MaUV = ?");
			stmt.setString(1, uv.getTenUV());
			stmt.setString(2, uv.getEmail());
			stmt.setDate(3, Date.valueOf(uv.getNgaySinh()));
			stmt.setString(4, uv.getDiaChi());
			stmt.setString(5, uv.getGioiTinh().getValue());
			stmt.setString(6, uv.getSoDienThoai());
			stmt.setString(7, uv.getMaUV());
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
			PreparedStatement stmt = con.prepareStatement("delete UngVien where MaUV = ?");
			stmt.setString(1, id);
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n != 0;
	}
}
