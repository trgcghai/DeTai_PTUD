package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import entity.*;
import controller.Database;

public class Bill_DAO {
	private ArrayList<Bill> listBill;

	public Bill_DAO() {
		listBill=new ArrayList<Bill>();
	}
	
	public ArrayList<Bill> getListBill() {
		return listBill;
	}
	public void setListBill(ArrayList<Bill> listBill) {
		this.listBill = listBill;
	}
	
	public ArrayList<Bill> getAllBill() {
		ArrayList<Bill> listBill = new ArrayList<Bill>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			String sql = "select idBill, dateBill, total, e.idEmployee, e.name, c.idCustomer, c.name, idTicket "
					+ "from Bill b join Customer c on b.idCustomer = c.idCustomer join Employee e on b.idEmployee = e.idEmployee";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String idBill = rs.getString(1);
				LocalDate dateBill = rs.getDate(2).toLocalDate();
				double total = rs.getDouble(3);
				NhanVien emp = new NhanVien(rs.getString(4), rs.getString(5));
				Customer cus = new Customer(rs.getString(6), rs.getString(7));
				Ticket ticket = new Ticket(rs.getString(8));
				listBill.add(new Bill(idBill, dateBill, total, cus, emp, ticket));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listBill;
	}
	
	public void sortByDateASC() {
		Collections.sort(listBill, new Comparator<Bill>() {
			@Override
			public int compare(Bill o1, Bill o2) {
				// TODO Auto-generated method stub
				return o1.getDateBill().compareTo(o2.getDateBill());
			}
		});
	}
	
	public void sortByDateDESC() {
		Collections.sort(listBill, new Comparator<Bill>() {
			@Override
			public int compare(Bill o1, Bill o2) {
				// TODO Auto-generated method stub
				return o2.getDateBill().compareTo(o1.getDateBill());
			}
		});
	}
	
	public void sortByIdBillASC() {
		Collections.sort(listBill, new Comparator<Bill>() {
			@Override
			public int compare(Bill o1, Bill o2) {
				// TODO Auto-generated method stub
				String b1=o1.getIdBill().substring(1);
				String b2=o2.getIdBill().substring(1);
				return Integer.parseInt(b1) - Integer.parseInt(b2);
			}
		});
	}
	
	public void sortByIdBillDESC() {
		Collections.sort(listBill, new Comparator<Bill>() {
			@Override
			public int compare(Bill o1, Bill o2) {
				// TODO Auto-generated method stub
				String b1=o1.getIdBill().substring(1);
				String b2=o2.getIdBill().substring(1);
				return Integer.parseInt(b2) - Integer.parseInt(b1);
			}
		});
	}
	
	public void sortByTotalPriceASC() {
		Collections.sort(listBill, new Comparator<Bill>() {
			@Override
			public int compare(Bill o1, Bill o2) {
				// TODO Auto-generated method stub
				return (int) (o1.getTotal() - o2.getTotal());
			}
		});
	}
	
	public void sortByTotalPriceDESC() {
		Collections.sort(listBill, new Comparator<Bill>() {
			@Override
			public int compare(Bill o1, Bill o2) {
				// TODO Auto-generated method stub
				return (int) (o2.getTotal() - o1.getTotal());
			}
		});
	}
	
	public ArrayList<Bill> getAllBillByID(String id) {
		ArrayList<Bill> listBill = new ArrayList<Bill>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("select idBill, dateBill, total, e.idEmployee, e.name, c.idCustomer, c.name, idTicket "
					+ "from Bill b join Customer c on b.idCustomer = c.idCustomer join Employee e on b.idEmployee = e.idEmployee where idBill like ?");
			stmt.setString(1, "%"+id+"%");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String idBill = rs.getString(1);
				LocalDate dateBill = rs.getDate(2).toLocalDate();
				double total = rs.getDouble(3);
				NhanVien emp = new NhanVien(rs.getString(4), rs.getString(5));
				Customer cus = new Customer(rs.getString(6), rs.getString(7));
				Ticket ticket = new Ticket(rs.getString(8));
				listBill.add(new Bill(idBill, dateBill, total, cus, emp, ticket));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listBill;
	}
	
	public ArrayList<Bill> getAllBillByEmp(String name) {
		ArrayList<Bill> listBill = new ArrayList<Bill>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("select idBill, dateBill, total, e.idEmployee, e.name, c.idCustomer, c.name, idTicket "
					+ "from Bill b join Customer c on b.idCustomer = c.idCustomer join Employee e on b.idEmployee = e.idEmployee where e.name like ?");
			stmt.setString(1, "%" + name + "%");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String idBill = rs.getString(1);
				LocalDate dateBill = rs.getDate(2).toLocalDate();
				double total = rs.getDouble(3);
				NhanVien emp = new NhanVien(rs.getString(4), rs.getString(5));
				Customer cus = new Customer(rs.getString(6), rs.getString(7));
				Ticket ticket = new Ticket(rs.getString(8));
				listBill.add(new Bill(idBill, dateBill, total, cus, emp, ticket));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listBill;
	}
	
	public ArrayList<Bill> getAllBillByCus(String name) {
		ArrayList<Bill> listBill = new ArrayList<Bill>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("select idBill, dateBill, total, e.idEmployee, e.name, c.idCustomer, c.name, idTicket "
					+ "from Bill b join Customer c on b.idCustomer = c.idCustomer join Employee e on b.idEmployee = e.idEmployee where c.name like ?");
			stmt.setString(1, "%" + name + "%");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String idBill = rs.getString(1);
				LocalDate dateBill = rs.getDate(2).toLocalDate();
				double total = rs.getDouble(3);
				NhanVien emp = new NhanVien(rs.getString(4), rs.getString(5));
				Customer cus = new Customer(rs.getString(6), rs.getString(7));
				Ticket ticket = new Ticket(rs.getString(8));
				listBill.add(new Bill(idBill, dateBill, total, cus, emp, ticket));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listBill;
	}
	
	public ArrayList<Bill> getAllBillByDate(String date) {
		ArrayList<Bill> listBill = new ArrayList<Bill>();
		Database.getInstance();
		Connection con = Database.getConnection();
		try {
			PreparedStatement stmt = con.prepareStatement("select idBill, dateBill, total, e.idEmployee, e.name, c.idCustomer, c.name, idTicket "
					+ "from Bill b join Customer c on b.idCustomer = c.idCustomer join Employee e on b.idEmployee = e.idEmployee where dateBill = ?");
			stmt.setDate(1, Date.valueOf(date));
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String idBill = rs.getString(1);
				LocalDate dateBill = rs.getDate(2).toLocalDate();
				double total = rs.getDouble(3);
				NhanVien emp = new NhanVien(rs.getString(4), rs.getString(5));
				Customer cus = new Customer(rs.getString(6), rs.getString(7));
				Ticket ticket = new Ticket(rs.getString(8));
				listBill.add(new Bill(idBill, dateBill, total, cus, emp, ticket));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listBill;
	}
	
	public ArrayList<Bill> getAllBillFromDateToDate(String from, String to) {
		ArrayList<Bill> listBill = new ArrayList<Bill>();
		Database.getInstance();
		Connection con = Database.getConnection();
		try {
			PreparedStatement stmt = con.prepareStatement("select idBill, dateBill, total, e.idEmployee, e.name, c.idCustomer, c.name, idTicket "
					+ "from Bill b join Customer c on b.idCustomer = c.idCustomer join Employee e on b.idEmployee = e.idEmployee where dateBill between ? and ?");
			stmt.setDate(1, Date.valueOf(from));
			stmt.setDate(2, Date.valueOf(to));
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String idBill = rs.getString(1);
				LocalDate dateBill = rs.getDate(2).toLocalDate();
				double total = rs.getDouble(3);
				NhanVien emp = new NhanVien(rs.getString(4), rs.getString(5));
				Customer cus = new Customer(rs.getString(6), rs.getString(7));
				Ticket ticket = new Ticket(rs.getString(8));
				listBill.add(new Bill(idBill, dateBill, total, cus, emp, ticket));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listBill;
	}
	
	public boolean create(Bill bill) {
		int n = 0;
		Database.getInstance();
		Connection con = Database.getConnection();
		try {
			PreparedStatement stmt = con.prepareStatement("insert into Bill values (?, ?, ?, ?, ?, ?)");
			stmt.setString(1, bill.getIdBill());
			stmt.setDate(2, Date.valueOf (bill.getDateBill()));
			stmt.setDouble(3, bill.getTotal());
			stmt.setString(4, bill.getEmployee().getIdEmployee());
			stmt.setString(5, bill.getCustomer().getIdCustomer());
			stmt.setString(6, bill.getTicket().getIdTicket());
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return n != 0;
	}
	
	public boolean update(Bill bill) {
		int n = 0;
		Database.getInstance();
		Connection con = Database.getConnection();
		try {
			PreparedStatement stmt = con.prepareStatement("update Bill set date = ?, total = ?, idEmployee = ?, idCustomer = ?, idTicket = ? where idBill = ?");
			stmt.setDate(1, Date.valueOf(bill.getDateBill()));
			stmt.setDouble(2, bill.getTotal());
			stmt.setString(3, bill.getEmployee().getIdEmployee());
			stmt.setString(4, bill.getCustomer().getIdCustomer());
			stmt.setString(5, bill.getTicket().getIdTicket());
			stmt.setString(6, bill.getIdBill());
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return n != 0;
	}
	
	public boolean delete(String id) {
		int n = 0;
		Database.getInstance();
		Connection con = Database.getConnection();
		try {
			PreparedStatement stmt = con.prepareStatement("delete from Bill where idBill = ?");
			stmt.setString(1, id);
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return n != 0;
	}
	
	public ArrayList<Bill> getAllBillByCusId(String id) {
		ArrayList<Bill> listBill = new ArrayList<Bill>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("select idBill, dateBill, total, e.idEmployee, e.name, c.idCustomer, c.name, idTicket "
					+ "from Bill b join Customer c on b.idCustomer = c.idCustomer join Employee e on b.idEmployee = e.idEmployee where c.idCustomer like ?");
			stmt.setString(1, "%" + id + "%");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String idBill = rs.getString(1);
				LocalDate dateBill = rs.getDate(2).toLocalDate();
				double total = rs.getDouble(3);
				NhanVien emp = new NhanVien(rs.getString(4), rs.getString(5));
				Customer cus = new Customer(rs.getString(6), rs.getString(7));
				Ticket ticket = new Ticket(rs.getString(8));
				listBill.add(new Bill(idBill, dateBill, total, cus, emp, ticket));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listBill;
	}
	
	public ArrayList<Bill> getAllBillByCusIdAndDateRange(String id, LocalDate dateFrom, LocalDate dateTo) {
	    ArrayList<Bill> listBill = new ArrayList<Bill>();
	    Database.getInstance();
	    Connection con = Database.getConnection();
	    
	    try {
	        PreparedStatement stmt = con.prepareStatement("SELECT idBill, dateBill, total, e.idEmployee, e.name, c.idCustomer, c.name, idTicket "
	                + "FROM Bill b JOIN Customer c ON b.idCustomer = c.idCustomer "
	                + "JOIN Employee e ON b.idEmployee = e.idEmployee "
	                + "WHERE c.idCustomer = ? AND dateBill BETWEEN ? AND ?");
	        stmt.setString(1, id);
	        stmt.setDate(2, Date.valueOf(dateFrom));
	        stmt.setDate(3, Date.valueOf(dateTo));
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            String idBill = rs.getString(1);
	            LocalDate dateBill = rs.getDate(2).toLocalDate();
	            double total = rs.getDouble(3);
	            NhanVien emp = new NhanVien(rs.getString(4), rs.getString(5));
	            Customer cus = new Customer(rs.getString(6), rs.getString(7));
	            Ticket ticket = new Ticket(rs.getString(8));
	            listBill.add(new Bill(idBill, dateBill, total, cus, emp, ticket));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return listBill;
	}

	public ArrayList<Bill> getAllBillByEmpId(String name) {
		ArrayList<Bill> listBill = new ArrayList<Bill>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("select idBill, dateBill, total, e.idEmployee, e.name, c.idCustomer, c.name, idTicket "
					+ "from Bill b join Customer c on b.idCustomer = c.idCustomer join Employee e on b.idEmployee = e.idEmployee where e.idEmployee like ?");
			stmt.setString(1, "%" + name + "%");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String idBill = rs.getString(1);
				LocalDate dateBill = rs.getDate(2).toLocalDate();
				double total = rs.getDouble(3);
				NhanVien emp = new NhanVien(rs.getString(4), rs.getString(5));
				Customer cus = new Customer(rs.getString(6), rs.getString(7));
				Ticket ticket = new Ticket(rs.getString(8));
				listBill.add(new Bill(idBill, dateBill, total, cus, emp, ticket));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listBill;
	}

	public ArrayList<Bill> getAllBillByEmpIdAndDateRange(String id, LocalDate dateFrom, LocalDate dateTo) {
	    ArrayList<Bill> listBill = new ArrayList<Bill>();
	    Database.getInstance();
	    Connection con = Database.getConnection();
	    
	    try {
	        PreparedStatement stmt = con.prepareStatement("SELECT idBill, dateBill, total, e.idEmployee, e.name, c.idCustomer, c.name, idTicket "
	                + "FROM Bill b JOIN Customer c ON b.idCustomer = c.idCustomer "
	                + "JOIN Employee e ON b.idEmployee = e.idEmployee "
	                + "WHERE e.idEmployee = ? AND dateBill BETWEEN ? AND ?");
	        stmt.setString(1, id);
	        stmt.setDate(2, Date.valueOf(dateFrom));
	        stmt.setDate(3, Date.valueOf(dateTo));
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            String idBill = rs.getString(1);
	            LocalDate dateBill = rs.getDate(2).toLocalDate();
	            double total = rs.getDouble(3);
	            NhanVien emp = new NhanVien(rs.getString(4), rs.getString(5));
	            Customer cus = new Customer(rs.getString(6), rs.getString(7));
	            Ticket ticket = new Ticket(rs.getString(8));
	            listBill.add(new Bill(idBill, dateBill, total, cus, emp, ticket));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return listBill;
	}


}
