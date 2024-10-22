package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import controller.Database;
import entity.HopDong;
import entity.NhaTuyenDung;
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
	
	public HopDong getHopDongTheoHoSo(String maHS) {
		ArrayList<HopDong> list = new ArrayList<HopDong>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("select hd.MaHD, hd.PhiDichVu, hd.ThoiGian, hd.MaTTD, hd.MaUV, hd.MaNV\r\n"
					+ "from HoSo hs join TinTuyenDung ttd on hs.MaTTD=ttd.MaTTD\r\n"
					+ "join HopDong hd on ttd.MaTTD=hd.MaTTD\r\n"
					+ "where MaHS = ?");
			stmt.setString(1, maHS);
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
	
	public ArrayList<HopDong> getHopDongTheoTinTuyenDung(String maTTD) {
		ArrayList<HopDong> list = new ArrayList<HopDong>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("select * from HopDong where MaTTD = ?");
			stmt.setString(1, maTTD);
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
		return list;
	}
	
	public ArrayList<HopDong> getHopDongTheoThoiGian(LocalDate ngayBatDau, LocalDate ngayKetThuc) {
		ArrayList<HopDong> list = new ArrayList<HopDong>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			PreparedStatement stmt = con.prepareStatement("select * from HopDong where ThoiGian between ? and ?");
			stmt.setString(1, formater.format(ngayBatDau));
			stmt.setString(2, formater.format(ngayKetThuc));
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
		return list;
	}
	
	public ArrayList<HopDong> getHopDongTheoUngVien(String maUV) {
		ArrayList<HopDong> list = new ArrayList<HopDong>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("select * from HopDong where MaUV = ?");
			stmt.setString(1, maUV);
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
		return list;
	}
	
	public ArrayList<HopDong> getHopDongTheoUngVien(String maUV, LocalDate ngayBatDau, LocalDate ngayKetThuc) {
		ArrayList<HopDong> list = new ArrayList<HopDong>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			PreparedStatement stmt = con.prepareStatement("select * from HopDong where MaUV = ? and ThoiGian between ? and ?");
			stmt.setString(1, maUV);
			stmt.setString(2, formater.format(ngayBatDau));
			stmt.setString(3, formater.format(ngayKetThuc));
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
		return list;
	}
	
	public ArrayList<HopDong> getHopDongTheoNhaTuyenDung(String maNTD) {
		ArrayList<HopDong> list = new ArrayList<HopDong>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement(""
					+ "select MaHD, PhiDichVu, ThoiGian, ttd.MaTTD, MaUV, MaNV from HopDong hd join TinTuyenDung ttd on hd.MaTTD = ttd.MaTTD join NhaTuyenDung ntd on ttd.MaNTD = ntd.MaNTD\r\n"
					+ "where ntd.MaNTD = ?");
			stmt.setString(1, maNTD);
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
		return list;
	}
	
	public ArrayList<HopDong> getHopDongTheoNhaTuyenDung(String maNTD, LocalDate ngayBatDau, LocalDate ngayKetThuc) {
		ArrayList<HopDong> list = new ArrayList<HopDong>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			PreparedStatement stmt = con.prepareStatement(""
					+ "select MaHD, PhiDichVu, ThoiGian, ttd.MaTTD, MaUV, MaNV from HopDong hd join TinTuyenDung ttd on hd.MaTTD = ttd.MaTTD join NhaTuyenDung ntd on ttd.MaNTD = ntd.MaNTD\r\n"
					+ "where ntd.MaNTD = ? and ThoiGian between ? and ? ");
			stmt.setString(1, maNTD);
			stmt.setString(2, formater.format(ngayBatDau));
			stmt.setString(3, formater.format(ngayKetThuc));
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
		return list;
	}
	
	public ArrayList<HopDong> getHopDongTheoUngVienVaNhaTuyenDung(String maUV, String maNTD) {
		ArrayList<HopDong> list = new ArrayList<HopDong>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement(""
					+ "select MaHD, PhiDichVu, ThoiGian, ttd.MaTTD, MaUV, MaNV from HopDong hd join TinTuyenDung ttd on hd.MaTTD = ttd.MaTTD join NhaTuyenDung ntd on ttd.MaNTD = ntd.MaNTD\r\n"
					+ "where ntd.MaNTD = ? and MaUV = ?");
			stmt.setString(1, maNTD);
			stmt.setString(2, maUV);
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
		return list;
	}
	
	public ArrayList<HopDong> getHopDongTheoUngVienVaNhaTuyenDung(String maUV, String maNTD, LocalDate ngayBatDau, LocalDate ngayKetThuc) {
		ArrayList<HopDong> list = new ArrayList<HopDong>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			PreparedStatement stmt = con.prepareStatement(""
					+ "select MaHD, PhiDichVu, ThoiGian, ttd.MaTTD, MaUV, MaNV from HopDong hd join TinTuyenDung ttd on hd.MaTTD = ttd.MaTTD join NhaTuyenDung ntd on ttd.MaNTD = ntd.MaNTD\r\n"
					+ "where ntd.MaNTD = ? and MaUV = ? and ThoiGian between ? and ?");
			stmt.setString(1, maNTD);
			stmt.setString(2, maUV);
			stmt.setString(3, formater.format(ngayBatDau));
			stmt.setString(4, formater.format(ngayKetThuc));
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
		return list;
	}
	
	public int getSoLuongHopDong() {
		int res = 0;
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("select count(*) from HopDong");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				res = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public double getTongGiaTriHopDong() {
		double res = 0;
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("select sum(PhiDichVu) from HopDong");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				res = rs.getDouble(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
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
