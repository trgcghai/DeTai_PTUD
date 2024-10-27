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
import entity.constraint.GioiTinh;

public class NhanVien_DAO {
	
	private ArrayList<NhanVien> listNhanVien;
	
	public NhanVien_DAO() {
		listNhanVien=new ArrayList<NhanVien>();
	}
	
	public ArrayList<NhanVien> getListNhanVien() {
		return listNhanVien;
	}

	public void setListNhanVien(ArrayList<NhanVien> listNhanVien) {
		this.listNhanVien = listNhanVien;
	}

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
				
				GioiTinh gioitinh=null;
				GioiTinh[] gioitinhs=GioiTinh.class.getEnumConstants();
				for(GioiTinh g: gioitinhs) {
					if(g.getValue().equalsIgnoreCase(rs.getString(5))) {
						gioitinh=g;
						break;
					}
				}
				if(gioitinh==null) {
					gioitinh=GioiTinh.KHAC;
				}
				
				String sdt = rs.getString(6);
				Date ngayVaoLam = rs.getDate(7);
				list.add(new NhanVien(ma, ten, dob.toLocalDate(), diaChi, gioitinh, sdt, ngayVaoLam.toLocalDate()));
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
				GioiTinh gioitinh=null;
				GioiTinh[] gioitinhs=GioiTinh.class.getEnumConstants();
				for(GioiTinh g: gioitinhs) {
					if(g.getValue().equalsIgnoreCase(rs.getString(5))) {
						gioitinh=g;
						break;
					}
				}
				if(gioitinh==null) {
					gioitinh=GioiTinh.KHAC;
				}
				String sdt = rs.getString(6);
				Date ngayVaoLam = rs.getDate(7);
				
				list.add(new NhanVien(id, ten, dob.toLocalDate(), diaChi, gioitinh, sdt, ngayVaoLam.toLocalDate()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list.get(0);
	}
	
	public NhanVien getNhanVienByTaiKhoan(String maTK) {
		ArrayList<NhanVien> list = new ArrayList<NhanVien>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("Select * from \r\n"
					+ "NhanVien n join TaiKhoan t on n.MaNV=t.MaNV \r\n"
					+ "where MaTK= ?");
			stmt.setString(1, maTK);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String id = rs.getString(1);
				String ten = rs.getString(2);
				Date dob = rs.getDate(3);
				String diaChi = rs.getString(4);
				GioiTinh gioitinh=null;
				GioiTinh[] gioitinhs=GioiTinh.class.getEnumConstants();
				for(GioiTinh g: gioitinhs) {
					if(g.getValue().equalsIgnoreCase(rs.getString(5))) {
						gioitinh=g;
						break;
					}
				}
				if(gioitinh==null) {
					gioitinh=GioiTinh.KHAC;
				}
				String sdt = rs.getString(6);
				Date ngayVaoLam = rs.getDate(7);
				
				list.add(new NhanVien(id, ten, dob.toLocalDate(), diaChi, gioitinh, sdt, ngayVaoLam.toLocalDate()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list.get(0);
	}
	
	public String getVaiTro(String maNV) {
		ArrayList<NhanVien> list = new ArrayList<NhanVien>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("Select VaiTro from TaiKhoan where MaNV = ?");
			stmt.setString(1, maNV);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				return rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "ChuaCo";
	}
	
	public ArrayList<NhanVien> getNhanVienBy(String key, int option) {
		ArrayList<NhanVien> list = new ArrayList<NhanVien>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt =null;
			if(option==1) {
				stmt = con.prepareStatement("Select * from NhanVien where TenNV LIKE ?");
				stmt.setString(1, "%"+key+"%");
			}
			else if(option==2) {
				stmt = con.prepareStatement("Select * from NhanVien where SoDienThoai LIKE ?");
				stmt.setString(1, "%"+key+"%");
			}
			else if(option==3) {
				String tenNV=key.split("/")[0];
				String sdt=key.split("/")[1];
				
				stmt = con.prepareStatement("Select * from NhanVien where TenNV LIKE ? AND SoDienThoai LIKE ?");
				stmt.setString(1, "%"+tenNV+"%");
				stmt.setString(2, "%"+sdt+"%");
			}
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String id = rs.getString(1);
				String ten = rs.getString(2);
				Date dob = rs.getDate(3);
				String diaChi = rs.getString(4);
				GioiTinh gioitinh=null;
				GioiTinh[] gioitinhs=GioiTinh.class.getEnumConstants();
				for(GioiTinh g: gioitinhs) {
					if(g.getValue().equalsIgnoreCase(rs.getString(5))) {
						gioitinh=g;
						break;
					}
				}
				if(gioitinh==null) {
					gioitinh=GioiTinh.KHAC;
				}
				String sdt = rs.getString(6);
				Date ngayVaoLam = rs.getDate(7);
				list.add(new NhanVien(id, ten, dob.toLocalDate(), diaChi, gioitinh, sdt, ngayVaoLam.toLocalDate()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public boolean create(NhanVien nv) {
		int n = 0;
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("insert into NhanVien values (?, ?, ?, ?, ?, ?, ?)");
			stmt.setString(1, nv.getMaNV());
			stmt.setString(2, nv.getTenNV());
			stmt.setDate(3, Date.valueOf(nv.getNgaySinh()));
			stmt.setString(4, nv.getDiaChi());
			stmt.setString(5, nv.getGioiTinh().getValue());
			stmt.setString(6, nv.getSoDienThoai());
			stmt.setDate(7, Date.valueOf(nv.getNgayVaoLam()));
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n != 0;
	}
	
	public boolean update(NhanVien nv) {
		int n = 0;
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("update NhanVien set TenNV = ?, NgaySinh = ?, DiaChi = ?, GioiTinh = ?, SoDienThoai = ?, NgayVaoLam = ? where MaNV = ?");
			stmt.setString(1, nv.getTenNV());
			stmt.setDate(2, Date.valueOf(nv.getNgaySinh()));
			stmt.setString(3, nv.getDiaChi());
			stmt.setString(4, nv.getGioiTinh().getValue());
			stmt.setString(5, nv.getSoDienThoai());
			stmt.setDate(6, Date.valueOf(nv.getNgayVaoLam()));
			stmt.setString(7, nv.getMaNV());
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
			PreparedStatement stmt = con.prepareStatement("delete NhanVien where MaNV = ?");
			stmt.setString(1, id);
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n != 0;
	}
	
	public ArrayList<Object[]> thongKeGioiTinh() {
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			String sql = "select GioiTinh, count(*) from NhanVien group by GioiTinh";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String gioiTinh = rs.getString(1);
				int soLuong = rs.getInt(2);
				
				list.add(new Object[] {gioiTinh, soLuong});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
