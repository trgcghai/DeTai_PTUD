package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import controller.Database;
import entity.TaiKhoan;
import entity.constraint.VaiTro;

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
	
	public ArrayList<TaiKhoan> getAllTaiKhoan() {
		Database.getInstance();
		Connection con = Database.getConnection();
		ArrayList<TaiKhoan> list=new ArrayList<TaiKhoan>();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from TaiKhoan");
			while(rs.next()) {
				VaiTro vaitro=rs.getString(4).equalsIgnoreCase("ADMIN")?VaiTro.ADMIN:VaiTro.NHANVIEN;
				list.add(new TaiKhoan(rs.getInt(1), rs.getString(2), rs.getString(3), vaitro));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public TaiKhoan getTaiKhoanByEmail(String email) {
		Database.getInstance();
		Connection con = Database.getConnection();
		try {
			PreparedStatement stmt = con.prepareStatement("select * from  where email = ?");
			stmt.setString(1, email);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				VaiTro vaitro=rs.getString(4).equalsIgnoreCase("ADMIN")?VaiTro.ADMIN:VaiTro.NHANVIEN;
				return new TaiKhoan(rs.getInt(1), rs.getString(2), rs.getString(3), vaitro);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean create(TaiKhoan taikhoan) {
		int n = 0;
		Database.getInstance();
		Connection con = Database.getConnection();
		try {
			PreparedStatement stmt = con.prepareStatement("insert into TaiKhoan values (?, ?, ?, ?)");
			stmt.setInt(1, taikhoan.getMaTK());
			stmt.setString(2, taikhoan.getEmail());
			stmt.setString(3, taikhoan.getMatkhau());
			stmt.setString(4, taikhoan.getVaitro().toString());
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n != 0;
	}
	
	public boolean update(TaiKhoan taikhoan) {
		int n = 0;
		Database.getInstance();
		Connection con = Database.getConnection();
		try {
			PreparedStatement stmt = con.prepareStatement("update TaiKhoan set email = ?, matkhau = ?, vaitro = ? where maTK = ?");
			stmt.setString(1, taikhoan.getEmail());
			stmt.setString(2, taikhoan.getMatkhau());
			stmt.setString(3, taikhoan.getVaitro().toString());
			stmt.setInt(4, taikhoan.getMaTK());
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n != 0;
	}
	
	public boolean delete(TaiKhoan taikhoan) {
		int n = 0;
		Database.getInstance();
		Connection con = Database.getConnection();
		try {
			PreparedStatement stmt = con.prepareStatement("delete from TaiKhoan where maTK = ?");
			stmt.setInt(1, taikhoan.getMaTK());
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n != 0;
	}
}
