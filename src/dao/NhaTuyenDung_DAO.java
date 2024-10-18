package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import controller.Database;
import entity.NhanVien;
import entity.NhaTuyenDung;

public class NhaTuyenDung_DAO {

	public ArrayList<NhaTuyenDung> getDsNhaTuyenDung() {
		ArrayList<NhaTuyenDung> list = new ArrayList<NhaTuyenDung>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			String sql = "select * from NhaTuyenDung";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String maNTD = rs.getString(1);
				String tenNTD = rs.getString(2);
				String email = rs.getString(3);
				String logo = rs.getString(4);
				String soDienThoai = rs.getString(5);
				String diaChi = rs.getString(6);
				
				list.add(new NhaTuyenDung(maNTD, tenNTD, email, logo, soDienThoai, diaChi));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	} 
	
	public NhaTuyenDung getNhaTuyenDung(String ma) {
		ArrayList<NhaTuyenDung> list = new ArrayList<NhaTuyenDung>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("select * from NhaTuyenDung where maNTD = ?");
			stmt.setString(1, ma);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maNTD = rs.getString(1);
				String tenNTD = rs.getString(2);
				String email = rs.getString(3);
				String logo = rs.getString(4);
				String soDienThoai = rs.getString(5);
				String diaChi = rs.getString(6);
				
				list.add(new NhaTuyenDung(maNTD, tenNTD, email, logo, soDienThoai, diaChi));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list.get(0);
	}
	
}
