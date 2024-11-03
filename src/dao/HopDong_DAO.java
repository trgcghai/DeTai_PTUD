package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import controller.Database;
import entity.HopDong;
import entity.NhaTuyenDung;
import entity.NhanVien;
import entity.TinTuyenDung;
import entity.UngVien;
import entity.constraint.GioiTinh;

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
	
	public int getSoLuongHopDongTheoTTD(String maTTD) {
		int res = 0;
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("\r\n"
					+ "select COUNT(*) as soluong \r\n"
					+ "from TinTuyenDung ttd join HopDong hd\r\n"
					+ "on ttd.MaTTD=hd.MaTTD\r\n"
					+ "where ttd.MaTTD=?\r\n"
					+ "group by ttd.MaTTD");
			stmt.setString(1, maTTD);
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
	
	public ArrayList<Object[]> thongKeHopDongTheoNhanVien() {
		ArrayList<Object[]> result = new ArrayList<Object[]>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement(""
					+ "select nv.Manv, TenNV, SoDienThoai, GioiTinh, NgaySinh, count(*), sum(PhiDichVu) from HopDong hd \r\n"
					+ "join NhanVien nv on hd.MaNV = nv.MaNV\r\n"
					+ "group by nv.Manv, TenNV, SoDienThoai, GioiTinh, NgaySinh");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maNV = rs.getString(1);
				String tenNV = rs.getString(2);
				String dienThoai = rs.getString(3);
				GioiTinh gioitinh=GioiTinh.KHAC;
				for(GioiTinh g: GioiTinh.class.getEnumConstants()) {
					if(g.getValue().equalsIgnoreCase(rs.getString(4))) {
						gioitinh=g;
					}
				}
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
				LocalDate ngaySinh = rs.getDate(5).toLocalDate();
				int tongHopDong = rs.getInt(6);
				double tongGiaTriHD = rs.getDouble(7);
				
				result.add(new Object[] {maNV, tenNV, dienThoai, gioitinh.getValue(), ngaySinh.format(formatter), 
						tongHopDong, tongGiaTriHD});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public ArrayList<Object[]> thongKeHopDongTheoNhanVien(String gioiTinh) {
		ArrayList<Object[]> result = new ArrayList<Object[]>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement(""
					+ "select nv.Manv, TenNV, SoDienThoai, GioiTinh, NgaySinh, count(*), sum(PhiDichVu) from HopDong hd \r\n"
					+ "join NhanVien nv on hd.MaNV = nv.MaNV\r\n where GioiTinh = ? \r\n"
					+ "group by nv.Manv, TenNV, SoDienThoai, GioiTinh, NgaySinh");
			stmt.setString(1, gioiTinh);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maNV = rs.getString(1);
				String tenNV = rs.getString(2);
				String dienThoai = rs.getString(3);
				GioiTinh gioitinh=GioiTinh.KHAC;
				for(GioiTinh g: GioiTinh.class.getEnumConstants()) {
					if(g.getValue().equalsIgnoreCase(rs.getString(4))) {
						gioitinh=g;
					}
				}
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
				LocalDate ngaySinh = rs.getDate(5).toLocalDate();
				int tongHopDong = rs.getInt(6);
				double tongGiaTriHD = rs.getDouble(7);
				
				result.add(new Object[] {maNV, tenNV, dienThoai, gioitinh.getValue(), ngaySinh.format(formatter), tongHopDong, tongGiaTriHD});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public ArrayList<Object[]> thongKeHopDongTheoTenNhanVien(String tenNhanVien) {
		ArrayList<Object[]> result = new ArrayList<Object[]>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement(""
					+ "select nv.Manv, TenNV, SoDienThoai, GioiTinh, NgaySinh, count(*), sum(PhiDichVu) from HopDong hd \r\n"
					+ "join NhanVien nv on hd.MaNV = nv.MaNV\r\n where TenNV = ? \r\n"
					+ "group by nv.Manv, TenNV, SoDienThoai, GioiTinh, NgaySinh");
			stmt.setString(1, tenNhanVien);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maNV = rs.getString(1);
				String tenNV = rs.getString(2);
				String dienThoai = rs.getString(3);
				GioiTinh gioitinh=GioiTinh.KHAC;
				for(GioiTinh g: GioiTinh.class.getEnumConstants()) {
					if(g.getValue().equalsIgnoreCase(rs.getString(4))) {
						gioitinh=g;
					}
				}
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
				LocalDate ngaySinh = rs.getDate(5).toLocalDate();
				int tongHopDong = rs.getInt(6);
				double tongGiaTriHD = rs.getDouble(7);
				
				result.add(new Object[] {maNV, tenNV, dienThoai, gioitinh.getValue(), ngaySinh.format(formatter), tongHopDong, tongGiaTriHD});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public ArrayList<Object[]> thongKeHopDongTheoNhanVien(LocalDate ngayBatDau, LocalDate ngayKetThuc) {
		ArrayList<Object[]> result = new ArrayList<Object[]>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			PreparedStatement stmt = con.prepareStatement(""
					+ "select nv.Manv, TenNV, SoDienThoai, GioiTinh, NgaySinh, count(*), sum(PhiDichVu) from HopDong hd \r\n"
					+ "join NhanVien nv on hd.MaNV = nv.MaNV\r\n where ThoiGian between ? and ? \r\n"
					+ "group by nv.Manv, TenNV, SoDienThoai, GioiTinh, NgaySinh");
			stmt.setString(1, formater.format(ngayBatDau));
			stmt.setString(2, formater.format(ngayKetThuc));
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maNV = rs.getString(1);
				String tenNV = rs.getString(2);
				String dienThoai = rs.getString(3);
				GioiTinh gioitinh=GioiTinh.KHAC;
				for(GioiTinh g: GioiTinh.class.getEnumConstants()) {
					if(g.getValue().equalsIgnoreCase(rs.getString(4))) {
						gioitinh=g;
					}
				}
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
				LocalDate ngaySinh = rs.getDate(5).toLocalDate();
				int tongHopDong = rs.getInt(6);
				double tongGiaTriHD = rs.getDouble(7);
				
				result.add(new Object[] {maNV, tenNV, dienThoai, gioitinh.getValue(), ngaySinh.format(formatter), tongHopDong, tongGiaTriHD});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public ArrayList<Object[]> thongKeHopDongTheoNhanVien(String gioiTinh, LocalDate ngayBatDau, LocalDate ngayKetThuc) {
		ArrayList<Object[]> result = new ArrayList<Object[]>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			PreparedStatement stmt = con.prepareStatement(""
					+ "select nv.Manv, TenNV, SoDienThoai, GioiTinh, NgaySinh, count(*), sum(PhiDichVu) from HopDong hd \r\n"
					+ "join NhanVien nv on hd.MaNV = nv.MaNV\r\n where GioiTinh = ? and ThoiGian between ? and ? \r\n"
					+ "group by nv.Manv, TenNV, SoDienThoai, GioiTinh, NgaySinh");
			stmt.setString(1, gioiTinh);
			stmt.setString(2, formater.format(ngayBatDau));
			stmt.setString(3, formater.format(ngayKetThuc));
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maNV = rs.getString(1);
				String tenNV = rs.getString(2);
				String dienThoai = rs.getString(3);
				GioiTinh gioitinh=GioiTinh.KHAC;
				for(GioiTinh g: GioiTinh.class.getEnumConstants()) {
					if(g.getValue().equalsIgnoreCase(rs.getString(4))) {
						gioitinh=g;
					}
				}
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
				LocalDate ngaySinh = rs.getDate(5).toLocalDate();
				int tongHopDong = rs.getInt(6);
				double tongGiaTriHD = rs.getDouble(7);
				
				result.add(new Object[] {maNV, tenNV, dienThoai, gioitinh.getValue(), ngaySinh.format(formatter), tongHopDong, tongGiaTriHD});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public ArrayList<Object[]> thongKeHopDongTheoTenNhanVien(String tenNhanVien, LocalDate ngayBatDau, LocalDate ngayKetThuc) {
		ArrayList<Object[]> result = new ArrayList<Object[]>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			PreparedStatement stmt = con.prepareStatement(""
					+ "select nv.Manv, TenNV, SoDienThoai, GioiTinh, NgaySinh, count(*), sum(PhiDichVu) from HopDong hd \r\n"
					+ "join NhanVien nv on hd.MaNV = nv.MaNV\r\n where TenNV = ? and ThoiGian between ? and ? \r\n"
					+ "group by nv.Manv, TenNV, SoDienThoai, GioiTinh, NgaySinh");
			stmt.setString(1, tenNhanVien);
			stmt.setString(2, formater.format(ngayBatDau));
			stmt.setString(3, formater.format(ngayKetThuc));
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maNV = rs.getString(1);
				String tenNV = rs.getString(2);
				String dienThoai = rs.getString(3);
				GioiTinh gioitinh=GioiTinh.KHAC;
				for(GioiTinh g: GioiTinh.class.getEnumConstants()) {
					if(g.getValue().equalsIgnoreCase(rs.getString(4))) {
						gioitinh=g;
					}
				}
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
				LocalDate ngaySinh = rs.getDate(5).toLocalDate();
				int tongHopDong = rs.getInt(6);
				double tongGiaTriHD = rs.getDouble(7);
				
				result.add(new Object[] {maNV, tenNV, dienThoai, gioitinh.getValue(), ngaySinh.format(formatter), tongHopDong, tongGiaTriHD});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public ArrayList<Object[]> thongKeHopDongTheoNhanVien(String tenNhanVien, String gioiTinh) {
		ArrayList<Object[]> result = new ArrayList<Object[]>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement(""
					+ "select nv.Manv, TenNV, SoDienThoai, GioiTinh, NgaySinh, count(*), sum(PhiDichVu) from HopDong hd \r\n"
					+ "join NhanVien nv on hd.MaNV = nv.MaNV\r\n where TenNV = ? and GioiTinh = ? \r\n"
					+ "group by nv.Manv, TenNV, SoDienThoai, GioiTinh, NgaySinh");
			stmt.setString(1, tenNhanVien);
			stmt.setString(2, gioiTinh);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maNV = rs.getString(1);
				String tenNV = rs.getString(2);
				String dienThoai = rs.getString(3);
				GioiTinh gioitinh=GioiTinh.KHAC;
				for(GioiTinh g: GioiTinh.class.getEnumConstants()) {
					if(g.getValue().equalsIgnoreCase(rs.getString(4))) {
						gioitinh=g;
					}
				}
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
				LocalDate ngaySinh = rs.getDate(5).toLocalDate();
				int tongHopDong = rs.getInt(6);
				double tongGiaTriHD = rs.getDouble(7);
				
				result.add(new Object[] {maNV, tenNV, dienThoai, gioitinh.getValue(), ngaySinh.format(formatter), tongHopDong, tongGiaTriHD});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public ArrayList<Object[]> thongKeHopDongTheoNhanVien(String tenNhanVien, String gioiTinh, LocalDate ngayBatDau, LocalDate ngayKetThuc) {
		ArrayList<Object[]> result = new ArrayList<Object[]>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			PreparedStatement stmt = con.prepareStatement(""
					+ "select nv.Manv, TenNV, SoDienThoai, GioiTinh, NgaySinh, count(*), sum(PhiDichVu) from HopDong hd \r\n"
					+ "join NhanVien nv on hd.MaNV = nv.MaNV\r\n where TenNV = ? and GioiTinh = ? and ThoiGian between ? and ? \r\n"
					+ "group by nv.Manv, TenNV, SoDienThoai, GioiTinh, NgaySinh");
			stmt.setString(1, tenNhanVien);
			stmt.setString(2, gioiTinh);
			stmt.setString(3, formater.format(ngayBatDau));
			stmt.setString(4, formater.format(ngayKetThuc));
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maNV = rs.getString(1);
				String tenNV = rs.getString(2);
				String dienThoai = rs.getString(3);
				GioiTinh gioitinh=GioiTinh.KHAC;
				for(GioiTinh g: GioiTinh.class.getEnumConstants()) {
					if(g.getValue().equalsIgnoreCase(rs.getString(4))) {
						gioitinh=g;
					}
				}
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
				LocalDate ngaySinh = rs.getDate(5).toLocalDate();
				int tongHopDong = rs.getInt(6);
				double tongGiaTriHD = rs.getDouble(7);
				
				result.add(new Object[] {maNV, tenNV, dienThoai, gioitinh.getValue(), ngaySinh.format(formatter), tongHopDong, tongGiaTriHD});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public ArrayList<Object[]> thongKeHopDongTheoNTD() {
		ArrayList<Object[]> result = new ArrayList<Object[]>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("select ntd.MaNTD, TenNTD, Email, SoDienThoai, count(MaTTD) from NhaTuyenDung ntd "
					+ "join TinTuyenDung ttd on ntd.MaNTD = ttd.MaNTD\r\n"
					+ "group by ntd.MaNTD, TenNTD, Email, SoDienThoai");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maNtd = rs.getString(1);
				String tenNtd = rs.getString(2);
				String email = rs.getString(3);
				String dienThoai = rs.getString(4);
				int soLuongTtd = rs.getInt(5);
				
				result.add(new Object[] {maNtd, tenNtd, email, dienThoai, soLuongTtd});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public ArrayList<Object[]> thongKeHopDongTheoNTD(LocalDate ngayBatDau, LocalDate ngayKetThuc) {
		ArrayList<Object[]> result = new ArrayList<Object[]>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			PreparedStatement stmt = con.prepareStatement("select ntd.MaNTD, TenNTD, Email, SoDienThoai, count(MaTTD) from NhaTuyenDung ntd \r\n"
					+ "join TinTuyenDung ttd on ntd.MaNTD = ttd.MaNTD\r\n"
					+ "where NgayDangTin between ? and ?\r\n"
					+ "group by ntd.MaNTD, TenNTD, Email, SoDienThoai");
			stmt.setString(1, formater.format(ngayBatDau));
			stmt.setString(2, formater.format(ngayKetThuc));
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maNtd = rs.getString(1);
				String tenNtd = rs.getString(2);
				String email = rs.getString(3);
				String dienThoai = rs.getString(4);
				int soLuongTtd = rs.getInt(5);
				
				result.add(new Object[] {maNtd, tenNtd, email, dienThoai, soLuongTtd});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public ArrayList<Object[]> thongKeHopDongTheoNTD(String tenNTD) {
		ArrayList<Object[]> result = new ArrayList<Object[]>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("select ntd.MaNTD, TenNTD, Email, SoDienThoai, count(MaTTD) from NhaTuyenDung ntd \r\n"
					+ "join TinTuyenDung ttd on ntd.MaNTD = ttd.MaNTD\r\n"
					+ "where TenNTD = ?\r\n"
					+ "group by ntd.MaNTD, TenNTD, Email, SoDienThoai");
			stmt.setString(1, tenNTD);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maNtd = rs.getString(1);
				String tenNtd = rs.getString(2);
				String email = rs.getString(3);
				String dienThoai = rs.getString(4);
				int soLuongTtd = rs.getInt(5);
				
				result.add(new Object[] {maNtd, tenNtd, email, dienThoai, soLuongTtd});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public ArrayList<Object[]> thongKeHopDongTheoNTD(String tenNTD, LocalDate ngayBatDau, LocalDate ngayKetThuc) {
		ArrayList<Object[]> result = new ArrayList<Object[]>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			PreparedStatement stmt = con.prepareStatement("select ntd.MaNTD, TenNTD, Email, SoDienThoai, count(MaTTD) from NhaTuyenDung ntd \r\n"
					+ "join TinTuyenDung ttd on ntd.MaNTD = ttd.MaNTD\r\n"
					+ "where TenNTD = ? and NgayDangTin between ? and ?\r\n"
					+ "group by ntd.MaNTD, TenNTD, Email, SoDienThoai");
			stmt.setString(1, tenNTD);
			stmt.setString(2, formater.format(ngayBatDau));
			stmt.setString(3, formater.format(ngayKetThuc));
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maNtd = rs.getString(1);
				String tenNtd = rs.getString(2);
				String email = rs.getString(3);
				String dienThoai = rs.getString(4);
				int soLuongTtd = rs.getInt(5);
				
				result.add(new Object[] {maNtd, tenNtd, email, dienThoai, soLuongTtd});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public ArrayList<HopDong> getHopDongTheoNhanVien(String maNV, LocalDate ngaybatdau, LocalDate ngayketthuc) {
		ArrayList<HopDong> list = new ArrayList<HopDong>();
		DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt=null;
			if(ngaybatdau!=null && ngayketthuc!=null) {
				stmt = con.prepareStatement("select * from HopDong \r\n"
						+ "where MaNV = ? and ThoiGian Between ? and ?");
				stmt.setString(1, maNV);
				stmt.setString(2, formater.format(ngaybatdau));
				stmt.setString(3, formater.format(ngayketthuc));
			}
			else {
				stmt = con.prepareStatement("select * from HopDong where MaNV = ?");
				stmt.setString(1, maNV);
			}
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
	
	public double thongKeHopDongTheoNhanVien(String maNV, int thang) {
		double result = 0;
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("select sum(PhiDichVu) \r\n"
					+ "from HopDong hd join NhanVien nv on hd.MaNV = nv.MaNV\r\n"
					+ "WHERE nv.MaNV= ? AND MONTH(ThoiGian)= ? \r\n"
					+ "group by nv.Manv, TenNV, SoDienThoai, GioiTinh, NgaySinh, MONTH(ThoiGian)");
			stmt.setString(1, maNV);
			stmt.setInt(2, thang);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				result = rs.getDouble(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
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
