package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import controller.Database;
import entity.NhanVien;
import entity.Movie;
import entity.Room;
import entity.Screening;
import entity.TaiKhoan;
import entity.constraint.GioiTinh;
import entity.Bill;

public class NhanVien_DAO {
	private ArrayList<NhanVien> listNhanVien;

	public NhanVien_DAO() {
		listNhanVien=new ArrayList<NhanVien>();
	}
	
	public ArrayList<NhanVien> getListEmp() {
		return listNhanVien;
	}

	public void setListEmp(ArrayList<NhanVien> listNhanVien) {
		this.listNhanVien = listNhanVien;
	}

	public ArrayList<NhanVien> getAllNhanVien() {
		ArrayList<NhanVien> listNhanVien = new ArrayList<NhanVien>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			String sql = "select * from NhanVien";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int maNV = rs.getInt(1);
				String tenNV = rs.getString(2);
				LocalDate ngaysinh = rs.getDate(3).toLocalDate();
				String diachi = rs.getString(4);
				
				GioiTinh gioitinh = null;
				if(rs.getString(5).equalsIgnoreCase("Nam")) {
					gioitinh=GioiTinh.NAM;
				}
				else if(rs.getString(5).equalsIgnoreCase("Nu")) {
					gioitinh=GioiTinh.NU;
				}
				else {
					gioitinh=GioiTinh.KHAC;
				}
				
				String sdt=rs.getString(6);
				TaiKhoan taikhoan = new TaiKhoan(rs.getInt(7));
				listNhanVien.add(new NhanVien(maNV, tenNV, ngaysinh, diachi, gioitinh, sdt, taikhoan));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listNhanVien;
	}
	
	public ArrayList<NhanVien> getNhanVienByTen(String ten) {
		ArrayList<NhanVien> listNhanVien = new ArrayList<NhanVien>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("Select * from NhanVien where tenNV = ?");
			stmt.setString(1, ten);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int maNV = rs.getInt(1);
				String tenNV = rs.getString(2);
				LocalDate ngaysinh = rs.getDate(3).toLocalDate();
				String diachi = rs.getString(4);
				
				GioiTinh gioitinh = null;
				if(rs.getString(5).equalsIgnoreCase("Nam")) {
					gioitinh=GioiTinh.NAM;
				}
				else if(rs.getString(5).equalsIgnoreCase("Nu")) {
					gioitinh=GioiTinh.NU;
				}
				else {
					gioitinh=GioiTinh.KHAC;
				}
				
				String sdt=rs.getString(6);
				TaiKhoan taikhoan = new TaiKhoan(rs.getInt(7));
				listNhanVien.add(new NhanVien(maNV, tenNV, ngaysinh, diachi, gioitinh, sdt, taikhoan));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listNhanVien;
	}
	
	public ArrayList<NhanVien> getNhanVienByGioiTinh(String gt) {
		ArrayList<NhanVien> listNhanVien = new ArrayList<NhanVien>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("Select * from NhanVien where gioitinh = ?");
			stmt.setString(1, gt);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int maNV = rs.getInt(1);
				String tenNV = rs.getString(2);
				LocalDate ngaysinh = rs.getDate(3).toLocalDate();
				String diachi = rs.getString(4);
				
				GioiTinh gioitinh = null;
				if(rs.getString(5).equalsIgnoreCase("Nam")) {
					gioitinh=GioiTinh.NAM;
				}
				else if(rs.getString(5).equalsIgnoreCase("Nu")) {
					gioitinh=GioiTinh.NU;
				}
				else {
					gioitinh=GioiTinh.KHAC;
				}
				
				String sdt=rs.getString(6);
				TaiKhoan taikhoan = new TaiKhoan(rs.getInt(7));
				listNhanVien.add(new NhanVien(maNV, tenNV, ngaysinh, diachi, gioitinh, sdt, taikhoan));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listNhanVien;
	}
	
	public ArrayList<NhanVien> getNhanVienByDiaChi(String dc) {
		ArrayList<NhanVien> listNhanVien = new ArrayList<NhanVien>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("Select * from NhanVien where diachi = ?");
			stmt.setString(1, dc);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int maNV = rs.getInt(1);
				String tenNV = rs.getString(2);
				LocalDate ngaysinh = rs.getDate(3).toLocalDate();
				String diachi = rs.getString(4);
				
				GioiTinh gioitinh = null;
				if(rs.getString(5).equalsIgnoreCase("Nam")) {
					gioitinh=GioiTinh.NAM;
				}
				else if(rs.getString(5).equalsIgnoreCase("Nu")) {
					gioitinh=GioiTinh.NU;
				}
				else {
					gioitinh=GioiTinh.KHAC;
				}
				
				String sdt=rs.getString(6);
				TaiKhoan taikhoan = new TaiKhoan(rs.getInt(7));
				listNhanVien.add(new NhanVien(maNV, tenNV, ngaysinh, diachi, gioitinh, sdt, taikhoan));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listNhanVien;
	}
	
	public NhanVien getNhanVienByEmail(String email) {
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("select maNV, tenNV, ngaysinh, diachi, gioitinh, sodienthoai, e.maTK\r\n"
					+ "from [dbo].[NhanVien] e join [dbo].[TaiKhoan] a on e.maTK=a.maTK\r\n"
					+ "where email=?");
			stmt.setString(1, email);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				String id = rs.getString(1);
				String nameEmp = rs.getString(2);
				LocalDate birthday = rs.getDate(3).toLocalDate();
				String addressEmp = rs.getString(4);
				LocalDate dateOfWork = rs.getDate(5).toLocalDate();
				TaiKhoan account = new TaiKhoan(rs.getInt(6));
				int gender = rs.getInt(7);
				String phone = rs.getString(8);
				return new NhanVien(id, nameEmp, birthday, addressEmp, dateOfWork, gender, phone, account);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean create(NhanVien emp) {
		int n = 0;
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("insert into Employee values (?, ?, ?, ?, ?, ?, ?, ?)");
			stmt.setString(1, emp.getIdEmployee());
			stmt.setString(2, emp.getName());
			stmt.setDate(3, Date.valueOf(emp.getBirthday()));
			stmt.setString(4, emp.getAddress());
			stmt.setDate(5, Date.valueOf(emp.getDateOfWork()));
			stmt.setInt(6, emp.getAccount().getIdAccount());
			stmt.setInt(7, emp.getGender());
			stmt.setString(8, emp.getPhone());
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n != 0;
	}
	
	public boolean update(NhanVien emp) {
		int n = 0;
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("update Employee set name = ?, birthday = ?, address = ?, dateOfWork = ?, gender = ?, phone = ? where idEmployee = ?");
			stmt.setString(1, emp.getName());
			stmt.setDate(2, Date.valueOf(emp.getBirthday()));
			stmt.setString(3, emp.getAddress());
			stmt.setDate(4, Date.valueOf(emp.getDateOfWork()));
			stmt.setInt(5, emp.getGender());
			stmt.setString(6, emp.getPhone());
			stmt.setString(7, emp.getIdEmployee());
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
			PreparedStatement stmt = con.prepareStatement("delete Employee where idEmployee = ?");
			stmt.setString(1, id);
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n != 0;
	}
	
	public ArrayList<NhanVien> searchByNameEmp(String key) {
		ArrayList<NhanVien> listEmp = new ArrayList<NhanVien>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM [dbo].[Employee] "
					+ "WHERE name LIKE ?");
			stmt.setString(1, "%"+key+"%");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String id = rs.getString(1);
				String name = rs.getString(2);
				LocalDate birthday = rs.getDate(3).toLocalDate();
				String address = rs.getString(4);
				LocalDate dateOfWork = rs.getDate(5).toLocalDate();
				TaiKhoan account = new TaiKhoan(rs.getInt(6));
				int gender = rs.getInt(7);
				String phone = rs.getString(8);
				listEmp.add(new NhanVien(id, name, birthday, address, dateOfWork, gender, phone, account));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listEmp;
	}
	
	public ArrayList<NhanVien> searchByIdEmp(String key) {
		ArrayList<NhanVien> listEmp = new ArrayList<NhanVien>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM [dbo].[Employee] "
					+ "WHERE idEmployee LIKE ?");
			stmt.setString(1, "%"+key+"%");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String id = rs.getString(1);
				String name = rs.getString(2);
				LocalDate birthday = rs.getDate(3).toLocalDate();
				String address = rs.getString(4);
				LocalDate dateOfWork = rs.getDate(5).toLocalDate();
				TaiKhoan account = new TaiKhoan(rs.getInt(6));
				int gender = rs.getInt(7);
				String phone = rs.getString(8);
				listEmp.add(new NhanVien(id, name, birthday, address, dateOfWork, gender, phone, account));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listEmp;
	}
	
	public void sortByNameEmpASC() {
		Collections.sort(listEmp, new Comparator<NhanVien>() {
			@Override
			public int compare(NhanVien o1, NhanVien o2) {
				// TODO Auto-generated method stub
				String firstName1=o1.getName().split("\\s+")[o1.getName().split("\\s+").length-1];
		 		String firstName2=o2.getName().split("\\s+")[o2.getName().split("\\s+").length-1];
		        return firstName1.compareTo(firstName2);
			}
			
		});
	}
	
	public void sortByNameEmpDESC() {
		Collections.sort(listEmp, new Comparator<NhanVien>() {
			@Override
			public int compare(NhanVien o1, NhanVien o2) {
				// TODO Auto-generated method stub
				String firstName1=o1.getName().split("\\s+")[o1.getName().split("\\s+").length-1];
		 		String firstName2=o2.getName().split("\\s+")[o2.getName().split("\\s+").length-1];
		        return firstName2.compareTo(firstName1);
			}
			
		});
	}
	
	public void sortByIdEmployeeASC() {
		Collections.sort(listEmp, new Comparator<NhanVien>() {
			@Override
			public int compare(NhanVien o1, NhanVien o2) {
				// TODO Auto-generated method stub
				String e1=o1.getIdEmployee().substring(2);
				String e2=o2.getIdEmployee().substring(2);
				return Integer.parseInt(e1) - Integer.parseInt(e2);
			}
			
		});
	}
	
	public void sortByIdEmployeeDESC() {
		Collections.sort(listEmp, new Comparator<NhanVien>() {
			@Override
			public int compare(NhanVien o1, NhanVien o2) {
				// TODO Auto-generated method stub
				String e1=o1.getIdEmployee().substring(2);
				String e2=o2.getIdEmployee().substring(2);
				return Integer.parseInt(e2) - Integer.parseInt(e1);
			}
			
		});
	}
	
	public List<NhanVien> getTopEmployees(int limit, Bill_DAO billDAO) {
        return listEmp.stream()
                .sorted((c1, c2) -> {
                    double total1 = billDAO.getAllBillByEmpId(c1.getIdEmployee()).stream().mapToDouble(Bill::getTotal).sum();
                    double total2 = billDAO.getAllBillByEmpId(c2.getIdEmployee()).stream().mapToDouble(Bill::getTotal).sum();
                    return Double.compare(total2, total1);
                })
                .limit(limit)
                .collect(Collectors.toList());
    }
	
	public List<NhanVien> getGenderEmployees(int gender) {
	    return listEmp.stream()
	            .filter(emp -> emp.getGender() == gender)
	            .collect(Collectors.toList());
	}

	public List<NhanVien> getEmployeesWithMostBillsHasTime(Bill_DAO billDAO, LocalDate dateFrom, LocalDate dateTo) {
	    int maxNumBills = listEmp.stream()
	            .mapToInt(emp -> billDAO.getAllBillByEmpIdAndDateRange(emp.getIdEmployee(), dateFrom, dateTo).size())
	            .max()
	            .orElse(0);
	    return listEmp.stream()
	            .filter(emp -> billDAO.getAllBillByEmpIdAndDateRange(emp.getIdEmployee(), dateFrom, dateTo).size() == maxNumBills)
	            .collect(Collectors.toList());
	}

	public List<NhanVien> getEmployeesWithMostBills(Bill_DAO billDAO) {
	    int maxNumBills = listEmp.stream()
	            .mapToInt(emp -> billDAO.getAllBillByEmpId(emp.getIdEmployee()).size())
	            .max()
	            .orElse(0);

	    return listEmp.stream()
	            .filter(emp -> billDAO.getAllBillByEmpId(emp.getIdEmployee()).size() == maxNumBills)
	            .collect(Collectors.toList());
	}



}
