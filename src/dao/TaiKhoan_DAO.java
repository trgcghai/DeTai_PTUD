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
import entity.constraint.VaiTro;
import entity.NhanVien;

public class TaiKhoan_DAO {
	
	private ArrayList<TaiKhoan> listTaiKhoan;
	
	public TaiKhoan_DAO() {
		listTaiKhoan=new ArrayList<TaiKhoan>();
	}
	
	public ArrayList<TaiKhoan> getListTaiKhoan() {
		return listTaiKhoan;
	}

	public void setListTaiKhoan(ArrayList<TaiKhoan> listTaiKhoan) {
		this.listTaiKhoan = listTaiKhoan;
	}

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
				
				VaiTro vaitro=null;
				for(VaiTro v: VaiTro.class.getEnumConstants()) {
					if(v.toString().equalsIgnoreCase(rs.getString(4))) {
						vaitro=v;
						break;
					}
				}
				
				NhanVien nhanVien = new NhanVien(rs.getString(5));
				
				list.add(new TaiKhoan(ma, email, matKhau, vaitro, nhanVien));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	} 
	
	public ArrayList<TaiKhoan> getTaiKhoan(String emailFind) {
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
				VaiTro vaitro=null;
				for(VaiTro v: VaiTro.class.getEnumConstants()) {
					if(v.toString().equalsIgnoreCase(rs.getString(4))) {
						vaitro=v;
						break;
					}
				}
				NhanVien nhanVien = new NhanVien(rs.getString(5));
				
				list.add(new TaiKhoan(ma, email, matKhau, vaitro, nhanVien));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public TaiKhoan getTaiKhoanByID(String id) {
		ArrayList<TaiKhoan> list = new ArrayList<TaiKhoan>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("select * from TaiKhoan where MaTK like ?");
			stmt.setString(1, "%" + id+ "%");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String ma = rs.getString(1);
				String email = rs.getString(2);
				String matKhau = rs.getString(3);
				VaiTro vaitro=null;
				for(VaiTro v: VaiTro.class.getEnumConstants()) {
					if(v.toString().equalsIgnoreCase(rs.getString(4))) {
						vaitro=v;
						break;
					}
				}
				NhanVien nhanVien = new NhanVien(rs.getString(5));
				
				list.add(new TaiKhoan(ma, email, matKhau, vaitro, nhanVien));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list.get(0);
	}
	
	public TaiKhoan getTaiKhoanByNhanVien(String maNV) {
		TaiKhoan tk = null;
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("select * from TaiKhoan where MaNV like ?");
			stmt.setString(1, "%" + maNV+ "%");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String ma = rs.getString(1);
				String email = rs.getString(2);
				String matKhau = rs.getString(3);
				VaiTro vaitro=null;
				for(VaiTro v: VaiTro.class.getEnumConstants()) {
					if(v.toString().equalsIgnoreCase(rs.getString(4))) {
						vaitro=v;
						break;
					}
				}
				NhanVien nhanVien = new NhanVien(rs.getString(5));
				
				tk=new TaiKhoan(ma, email, matKhau, vaitro, nhanVien);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tk;
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
	
	public boolean create(TaiKhoan tk) {
		int n = 0;
		Database.getInstance();
		Connection con = Database.getConnection();
		try {
			PreparedStatement stmt = con.prepareStatement("insert into TaiKhoan values (?, ?, ?, ?, ?)");
			stmt.setString(1, tk.getMaTk());
			stmt.setString(2, tk.getEmail());
			stmt.setString(3, tk.getMatKhau());
			stmt.setString(4, tk.getVaiTro().toString());
			stmt.setString(5, tk.getNhanVien().getMaNV());
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n != 0;
	}
	
	public boolean update(TaiKhoan tk) {
		int n = 0;
		Database.getInstance();
		Connection con = Database.getConnection();
		try {
			PreparedStatement stmt = con.prepareStatement("update TaiKhoan set Email = ?, MatKhau = ?, VaiTro = ? where MaTK = ?");
			stmt.setString(1, tk.getEmail());
			stmt.setString(2, tk.getMatKhau());
			stmt.setString(3, tk.getVaiTro().toString());
			stmt.setString(4, tk.getMaTk());
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n != 0;
	}
	
	public boolean delete(TaiKhoan tk) {
		int n = 0;
		Database.getInstance();
		Connection con = Database.getConnection();
		try {
			PreparedStatement stmt = con.prepareStatement("delete from TaiKhoan where MaTK = ?");
			stmt.setString(1, tk.getMaTk());
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n != 0;
	}

}
