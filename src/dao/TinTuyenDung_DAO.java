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
import entity.NhaTuyenDung;
import entity.TinTuyenDung;
import entity.constraint.HinhThucLamViec;
import entity.constraint.NganhNghe;
import entity.constraint.TrinhDo;

public class TinTuyenDung_DAO {
	
	private ArrayList<TinTuyenDung> listTinTuyenDung;
	
	public TinTuyenDung_DAO() {
		listTinTuyenDung=new ArrayList<TinTuyenDung>();
	}

	public ArrayList<TinTuyenDung> getListTinTuyenDung() {
		return listTinTuyenDung;
	}

	public void setListTinTuyenDung(ArrayList<TinTuyenDung> listTinTuyenDung) {
		this.listTinTuyenDung = listTinTuyenDung;
	}

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
				TrinhDo trinhdo=null;
				for(TrinhDo t: TrinhDo.class.getEnumConstants()) {
					if(t.getValue().equalsIgnoreCase(rs.getString(6))) {
						trinhdo=t;
						break;
					}
				}
				int soLuong = rs.getInt(7);
				double luong = rs.getDouble(8);
				NganhNghe nganhnghe=null;
				for(NganhNghe n: NganhNghe.class.getEnumConstants()) {
					if(n.getValue().equalsIgnoreCase(rs.getString(9))) {
						nganhnghe=n;
						break;
					}
				}
				boolean trangThai = rs.getBoolean(10);
				HinhThucLamViec hinhthuc=null;
				for(HinhThucLamViec h: HinhThucLamViec.class.getEnumConstants()) {
					if(h.getValue().equalsIgnoreCase(rs.getString(11))) {
						hinhthuc=h;
						break;
					}
				}
				NhaTuyenDung nhaTuyenDung = new NhaTuyenDung(rs.getString(12));
				
				list.add(new TinTuyenDung(maTTD, tieuDe, moTa, ngayDangTin, ngayHetHan, trinhdo, 
						soLuong, luong, nganhnghe, hinhthuc, trangThai, nhaTuyenDung));
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
			PreparedStatement stmt = con.prepareStatement("select * from TinTuyenDung where MaTTD = ?");
			stmt.setString(1, ma);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maTTD = rs.getString(1);
				String tieuDe = rs.getString(2);
				String moTa = rs.getString(3);
				LocalDate ngayDangTin = rs.getDate(4).toLocalDate();
				LocalDate ngayHetHan = rs.getDate(5).toLocalDate();
				TrinhDo trinhdo=null;
				for(TrinhDo t: TrinhDo.class.getEnumConstants()) {
					if(t.getValue().equalsIgnoreCase(rs.getString(6))) {
						trinhdo=t;
						break;
					}
				}
				int soLuong = rs.getInt(7);
				double luong = rs.getDouble(8);
				NganhNghe nganhnghe=null;
				for(NganhNghe n: NganhNghe.class.getEnumConstants()) {
					if(n.getValue().equalsIgnoreCase(rs.getString(9))) {
						nganhnghe=n;
						break;
					}
				}
				boolean trangThai = rs.getBoolean(10);
				HinhThucLamViec hinhthuc=null;
				for(HinhThucLamViec h: HinhThucLamViec.class.getEnumConstants()) {
					if(h.getValue().equalsIgnoreCase(rs.getString(11))) {
						hinhthuc=h;
						break;
					}
				}
				NhaTuyenDung nhaTuyenDung = new NhaTuyenDung(rs.getString(12));
				
				list.add(new TinTuyenDung(maTTD, tieuDe, moTa, ngayDangTin, ngayHetHan, trinhdo, 
						soLuong, luong, nganhnghe, hinhthuc, trangThai, nhaTuyenDung));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list.get(0);
	}
	
	public ArrayList<TinTuyenDung> getTinTuyenDungTheoNTD(String key, int option) {
		ArrayList<TinTuyenDung> list = new ArrayList<TinTuyenDung>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt=null;
			if(option==1) {
				stmt = con.prepareStatement("select * from TinTuyenDung where maNTD = ?");
				stmt.setString(1, key);				
			}
			else if(option==2) {
				String mantd=key.split("/")[0];
				int trangthai=Integer.parseInt(key.split("/")[1]);
				stmt = con.prepareStatement("select * from TinTuyenDung \r\n"
						+ "where maNTD = ? AND TrangThai = ?");
				stmt.setString(1, mantd);
				stmt.setInt(2, trangthai);
			}
			else if(option==3) {
				String mantd=key.split("/")[0];
				int trangthai=Integer.parseInt(key.split("/")[1]);
				String tieude=key.split("/")[2];
				stmt = con.prepareStatement("select * from TinTuyenDung \r\n"
						+ "where maNTD = ? AND TrangThai = ? AND TieuDe LIKE ?");
				stmt.setString(1, mantd);
				stmt.setInt(2, trangthai);
				stmt.setString(3, "%"+tieude+"%");
			}
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maTTD = rs.getString(1);
				String tieuDe = rs.getString(2);
				String moTa = rs.getString(3);
				LocalDate ngayDangTin = rs.getDate(4).toLocalDate();
				LocalDate ngayHetHan = rs.getDate(5).toLocalDate();
				TrinhDo trinhdo=null;
				for(TrinhDo t: TrinhDo.class.getEnumConstants()) {
					if(t.getValue().equalsIgnoreCase(rs.getString(6))) {
						trinhdo=t;
						break;
					}
				}
				int soLuong = rs.getInt(7);
				double luong = rs.getDouble(8);
				NganhNghe nganhnghe=null;
				for(NganhNghe n: NganhNghe.class.getEnumConstants()) {
					if(n.getValue().equalsIgnoreCase(rs.getString(9))) {
						nganhnghe=n;
						break;
					}
				}
				boolean trangThai = rs.getBoolean(10);
				HinhThucLamViec hinhthuc=null;
				for(HinhThucLamViec h: HinhThucLamViec.class.getEnumConstants()) {
					if(h.getValue().equalsIgnoreCase(rs.getString(11))) {
						hinhthuc=h;
						break;
					}
				}
				NhaTuyenDung nhaTuyenDung = new NhaTuyenDung(rs.getString(12));
				
				list.add(new TinTuyenDung(maTTD, tieuDe, moTa, ngayDangTin, ngayHetHan, trinhdo, 
						soLuong, luong, nganhnghe, hinhthuc, trangThai, nhaTuyenDung));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TinTuyenDung> getTinTuyenDungTheo(String key, int option) {
		ArrayList<TinTuyenDung> list = new ArrayList<TinTuyenDung>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt=null;
			if(option==1) {
				stmt = con.prepareStatement("select * from TinTuyenDung where TieuDe LIKE ?");
				stmt.setString(1, "%"+key+"%");				
			}
			else if(option==2) {
				stmt = con.prepareStatement("select * from TinTuyenDung where Luong = ?");
				stmt.setDouble(1, Double.parseDouble(key));
			}
			else if(option==3) {
				String tieude=key.split("/")[0];
				double luong=Double.parseDouble(key.split("/")[1]);
				stmt = con.prepareStatement("select * from TinTuyenDung \r\n"
						+ "where TieuDe LIKE ? AND Luong = ?");
				stmt.setString(1, "%"+tieude+"%");
				stmt.setDouble(2, luong);
			}
			else if(option==4) {
				String tieude=key.split("/")[0];
				String ntd=key.split("/")[1];
				stmt = con.prepareStatement("select * from \r\n"
						+ "TinTuyenDung t join NhaTuyenDung n on t.MaNTD=n.MaNTD\r\n"
						+ "where TieuDe LIKE ? AND TenNTD LIKE ?");
				stmt.setString(1, "%"+tieude+"%");
				stmt.setString(2, "%"+ntd+"%");
			}
			else if(option==5) {
				String tieude=key.split("/")[0];
				String trinhdo=key.split("/")[1];
				stmt = con.prepareStatement("select * from TinTuyenDung \r\n"
						+ "where TieuDe LIKE ? AND TrinhDo LIKE ?");
				stmt.setString(1, "%"+tieude+"%");
				stmt.setString(2, "%"+trinhdo+"%");
			}
			else if(option==6) {
				double luong=Double.parseDouble(key.split("/")[0]);
				String ntd=key.split("/")[1];
				stmt = con.prepareStatement("select * from \r\n"
						+ "TinTuyenDung t join NhaTuyenDung n on t.MaNTD=n.MaNTD\r\n"
						+ "where Luong = ? AND TenNTD LIKE ?");
				stmt.setDouble(1, luong);
				stmt.setString(2, "%"+ntd+"%");
			}
			else if(option==7) {
				double luong=Double.parseDouble(key.split("/")[0]);
				String trinhdo=key.split("/")[1];
				stmt = con.prepareStatement("select * from \r\n"
						+ "TinTuyenDung t join NhaTuyenDung n on t.MaNTD=n.MaNTD\r\n"
						+ "where Luong = ? AND TrinhDo LIKE ?");
				stmt.setDouble(1, luong);
				stmt.setString(2, "%"+trinhdo+"%");
			}
			else if(option==8) {
				String tieude=key.split("/")[0];
				double luong=Double.parseDouble(key.split("/")[1]);
				String ntd=key.split("/")[2];
				stmt = con.prepareStatement("select * from \r\n"
						+ "TinTuyenDung t join NhaTuyenDung n on t.MaNTD=n.MaNTD\r\n"
						+ "where TieuDe LIKE ? AND Luong = ? AND TenNTD LIKE ?");
				stmt.setString(1, "%"+tieude+"%");
				stmt.setDouble(2, luong);
				stmt.setString(3, "%"+ntd+"%");
			}
			else if(option==9) {
				String tieude=key.split("/")[0];
				double luong=Double.parseDouble(key.split("/")[1]);
				String trinhdo=key.split("/")[2];
				stmt = con.prepareStatement("select * from TinTuyenDung \r\n"
						+ "where TieuDe LIKE ? AND Luong = ? AND TrinhDo LIKE ?");
				stmt.setString(1, "%"+tieude+"%");
				stmt.setDouble(2, luong);
				stmt.setString(3, "%"+trinhdo+"%");
			}
			else if(option==10) {
				String tieude=key.split("/")[0];
				double luong=Double.parseDouble(key.split("/")[1]);
				String ntd=key.split("/")[2];
				String trinhdo=key.split("/")[3];
				stmt = con.prepareStatement("select * from \r\n"
						+ "TinTuyenDung t join NhaTuyenDung n on t.MaNTD=n.MaNTD\r\n"
						+ "where TieuDe LIKE ? AND Luong = ? AND TenNTD LIKE ? AND TrinhDo LIKE ?");
				stmt.setString(1, "%"+tieude+"%");
				stmt.setDouble(2, luong);
				stmt.setString(3, "%"+ntd+"%");
				stmt.setString(4, "%"+trinhdo+"%");
			}
			else if(option==11) {
				stmt = con.prepareStatement("select * from \r\n"
						+ "TinTuyenDung t join NhaTuyenDung n on t.MaNTD=n.MaNTD\r\n"
						+ "where TenNTD LIKE ?");
				stmt.setString(1, "%"+key+"%");
			}
			else if(option==12) {
				stmt = con.prepareStatement("select * from \r\n"
						+ "TinTuyenDung t join NhaTuyenDung n on t.MaNTD=n.MaNTD\r\n"
						+ "where TrinhDo LIKE ?");
				stmt.setString(1, "%"+key+"%");
			}
			else if(option==13) {
				String ntd=key.split("/")[0];
				String trinhdo=key.split("/")[1];
				stmt = con.prepareStatement("select * from \r\n"
						+ "TinTuyenDung t join NhaTuyenDung n on t.MaNTD=n.MaNTD\r\n"
						+ "where TenNTD LIKE ? AND TrinhDo LIKE ?");
				stmt.setString(1, "%"+ntd+"%");
				stmt.setString(2, "%"+trinhdo+"%");
			}
			else if(option==14) {
				String tieude=key.split("/")[0];
				String ntd=key.split("/")[1];
				String trinhdo=key.split("/")[2];
				stmt = con.prepareStatement("select * from \r\n"
						+ "TinTuyenDung t join NhaTuyenDung n on t.MaNTD=n.MaNTD\r\n"
						+ "where TieuDe LIKE ? AND TenNTD LIKE ? AND TrinhDo LIKE ?");
				stmt.setString(1, "%"+tieude+"%");
				stmt.setString(2, "%"+ntd+"%");
				stmt.setString(3, "%"+trinhdo+"%");
			}
			else if(option==15) {
				double luong=Double.parseDouble(key.split("/")[0]);
				String ntd=key.split("/")[1];
				String trinhdo=key.split("/")[2];
				stmt = con.prepareStatement("select * from \r\n"
						+ "TinTuyenDung t join NhaTuyenDung n on t.MaNTD=n.MaNTD\r\n"
						+ "where Luong = ? AND TenNTD LIKE ? AND TrinhDo LIKE ?");
				stmt.setDouble(1, luong);
				stmt.setString(2, "%"+ntd+"%");
				stmt.setString(3, "%"+trinhdo+"%");
			}
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maTTD = rs.getString(1);
				String tieuDe = rs.getString(2);
				String moTa = rs.getString(3);
				LocalDate ngayDangTin = rs.getDate(4).toLocalDate();
				LocalDate ngayHetHan = rs.getDate(5).toLocalDate();
				TrinhDo trinhdo=null;
				for(TrinhDo t: TrinhDo.class.getEnumConstants()) {
					if(t.getValue().equalsIgnoreCase(rs.getString(6))) {
						trinhdo=t;
						break;
					}
				}
				int soLuong = rs.getInt(7);
				double luong = rs.getDouble(8);
				NganhNghe nganhnghe=null;
				for(NganhNghe n: NganhNghe.class.getEnumConstants()) {
					if(n.getValue().equalsIgnoreCase(rs.getString(9))) {
						nganhnghe=n;
						break;
					}
				}
				boolean trangThai = rs.getBoolean(10);
				HinhThucLamViec hinhthuc=null;
				for(HinhThucLamViec h: HinhThucLamViec.class.getEnumConstants()) {
					if(h.getValue().equalsIgnoreCase(rs.getString(11))) {
						hinhthuc=h;
						break;
					}
				}
				NhaTuyenDung nhaTuyenDung = new NhaTuyenDung(rs.getString(12));
				
				list.add(new TinTuyenDung(maTTD, tieuDe, moTa, ngayDangTin, ngayHetHan, trinhdo, 
						soLuong, luong, nganhnghe, hinhthuc, trangThai, nhaTuyenDung));
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
	
	public boolean create(TinTuyenDung ttd) {
		int n = 0;
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("insert into TinTuyenDung values (?, ?, ?, \r\n"
					+ "?, ?, ?, \r\n"
					+ "?, ?, ?,\r\n"
					+ "?, ?, ?)");
			stmt.setString(1, ttd.getMaTTD());
			stmt.setString(2, ttd.getTieuDe());
			stmt.setString(3, ttd.getMoTa());
			stmt.setDate(4, Date.valueOf(ttd.getNgayDangTin()));
			stmt.setDate(5, Date.valueOf(ttd.getNgayHetHan()));
			stmt.setString(6, ttd.getTrinhDo().getValue());
			stmt.setInt(7, ttd.getSoLuong());
			stmt.setDouble(8, ttd.getLuong());
			stmt.setString(9, ttd.getNganhNghe().getValue());
			stmt.setBoolean(10, ttd.isTrangThai());
			stmt.setString(11, ttd.getHinhThuc().getValue());
			stmt.setString(12, ttd.getNhaTuyenDung().getMaNTD());
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n != 0;
	}
	
	public boolean update(TinTuyenDung ttd) {
		int n = 0;
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("update TinTuyenDung set TieuDe = ?, MoTa = ?, NgayDangTin = ?, \r\n"
					+ "NgayHetHan = ?, TrinhDo = ?, SoLuong = ?,\r\n"
					+ "Luong = ?, NganhNghe = ?, TrangThai = ?,\r\n"
					+ "HinhThuc = ?, MaNTD = ?\r\n"
					+ "where MaTTD = ?");
			stmt.setString(1, ttd.getTieuDe());
			stmt.setString(2, ttd.getMoTa());
			stmt.setDate(3, Date.valueOf(ttd.getNgayDangTin()));
			stmt.setDate(4, Date.valueOf(ttd.getNgayHetHan()));
			stmt.setString(5, ttd.getTrinhDo().getValue());
			stmt.setInt(6, ttd.getSoLuong());
			stmt.setDouble(7, ttd.getLuong());
			stmt.setString(8, ttd.getNganhNghe().getValue());
			stmt.setBoolean(9, ttd.isTrangThai());
			stmt.setString(10, ttd.getHinhThuc().getValue());
			stmt.setString(11, ttd.getNhaTuyenDung().getMaNTD());
			stmt.setString(12, ttd.getMaTTD());
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
			PreparedStatement stmt = con.prepareStatement("delete TinTuyenDung where MaTTD = ?");
			stmt.setString(1, id);
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n != 0;
	}
}
