package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import controller.Database;
import entity.Bill;
import entity.Customer;
import entity.NhanVien;

public class Customer_DAO {
	private ArrayList<Customer> list;
	
	public Customer_DAO() {
		list=new ArrayList<Customer>();
	}
	
	public ArrayList<Customer> getList() {
		return list;
	}

	public void setList(ArrayList<Customer> list) {
		this.list = list;
	}

	public ArrayList<Customer> getAllCustomer() {
		ArrayList<Customer> listCustomer = new ArrayList<Customer>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			String sql = "select * from Customer";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String idCustomer = rs.getString(1);
				String name = rs.getString(2);
				LocalDate birthday;
				if(rs.getDate(3)!=null) {
					birthday = rs.getDate(3).toLocalDate();					
				}
				else {
					birthday = null;
				}
				String phone = rs.getString(4);
				int gender = rs.getInt(5);
				listCustomer.add(new Customer(idCustomer, name, birthday, phone, gender));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listCustomer;
	}
	
	public ArrayList<Customer> getAllCustomerByName(String name) {
		ArrayList<Customer> listCustomer = new ArrayList<Customer>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("Select * from Customer where name like ?");
			stmt.setString(1, "%"+name+"%");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String idCustomer = rs.getString(1);
				String nameCus = rs.getString(2);
				LocalDate birthday;
				if(rs.getDate(3)!=null) {
					birthday = rs.getDate(3).toLocalDate();
				}
				else {
					birthday=null;
				}
				String phone = rs.getString(4);
				int gender = rs.getInt(5);
				listCustomer.add(new Customer(idCustomer, nameCus, birthday, phone, gender));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listCustomer;
	}
	
	public ArrayList<Customer> getAllCustomerById(String id) {
		ArrayList<Customer> listCustomer = new ArrayList<Customer>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("Select * from Customer where idCustomer like ?");
			stmt.setString(1, "%"+id+"%");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String idCustomer = rs.getString(1);
				String nameCus = rs.getString(2);
				LocalDate birthday;
				if(rs.getDate(3)!=null) {
					birthday = rs.getDate(3).toLocalDate();
				}
				else {
					birthday=null;
				}
				String phone = rs.getString(4);
				int gender = rs.getInt(5);
				listCustomer.add(new Customer(idCustomer, nameCus, birthday, phone, gender));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listCustomer;
	}
	
	public ArrayList<Customer> getCustomerByGender(int gen) {
		ArrayList<Customer> listCustomer = new ArrayList<Customer>();
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("Select * from Customer where gender = ?");
			stmt.setInt(1, gen);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String idCustomer = rs.getString(1);
				String name = rs.getString(2);
				LocalDate birthday;
				if(rs.getDate(3)!=null) {
					birthday = rs.getDate(3).toLocalDate();
				}
				else {
					birthday=null;
				}
				String phone = rs.getString(4);
				int gender = rs.getInt(5);
				listCustomer.add(new Customer(idCustomer, name, birthday, phone, gender));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listCustomer;
	}
	
	public Customer getCustomerByPhone(String tel) {
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("Select * from Customer where phone = ?");
			stmt.setString(1, tel);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				String idCustomer = rs.getString(1);
				String name = rs.getString(2);
				LocalDate birthday;
				if(rs.getDate(3)!=null) {
					birthday = rs.getDate(3).toLocalDate();
				}
				else {
					birthday=null;
				}
				String phone = rs.getString(4);
				int gender = rs.getInt(5);
				return new Customer(idCustomer, name, birthday, phone, gender);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean create(Customer cus) {
		int n = 0;
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("insert into Customer values (?, ?, ?, ?, ?)");
			stmt.setString(1, cus.getIdCustomer());
			stmt.setString(2, cus.getName());
			if(cus.getBirthday()!=null) {
				stmt.setDate(3, Date.valueOf(cus.getBirthday()));				
			}
			else {
				stmt.setDate(3, null);
			}
			stmt.setString(4, cus.getPhone());
			stmt.setInt(5, cus.getGender());
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
			PreparedStatement stmt = con.prepareStatement("delete from Customer where idCustomer = ?");
			stmt.setString(1, id);
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n != 0;
	}
	
	public boolean update(Customer cus) {
		int n = 0;
		Database.getInstance();
		Connection con = Database.getConnection();
		
		try {
			PreparedStatement stmt = con.prepareStatement("update Customer set name = ?, birthday = ?, phone = ?, gender = ? where idCustomer = ?");
			stmt.setString(1, cus.getName());
			stmt.setDate(2, Date.valueOf(cus.getBirthday()));
			stmt.setString(3, cus.getPhone());
			stmt.setInt(4, cus.getGender());
			stmt.setString(5, cus.getIdCustomer());
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n != 0;
	}
	
	public void sortByNameCusASC() {
		Collections.sort(list, new Comparator<Customer>() {
			@Override
			public int compare(Customer o1, Customer o2) {
				// TODO Auto-generated method stub
				String firstName1=o1.getName().split("\\s+")[o1.getName().split("\\s+").length-1];
		 		String firstName2=o2.getName().split("\\s+")[o2.getName().split("\\s+").length-1];
		        return firstName1.compareTo(firstName2);
			}
			
		});
	}
	
	public void sortByNameCusDESC() {
		Collections.sort(list, new Comparator<Customer>() {
			@Override
			public int compare(Customer o1, Customer o2) {
				// TODO Auto-generated method stub
				String firstName1=o1.getName().split("\\s+")[o1.getName().split("\\s+").length-1];
		 		String firstName2=o2.getName().split("\\s+")[o2.getName().split("\\s+").length-1];
		        return firstName2.compareTo(firstName1);
			}
			
		});
	}
	
	public void sortByIdCusASC() {
		Collections.sort(list, new Comparator<Customer>() {
			@Override
			public int compare(Customer o1, Customer o2) {
				// TODO Auto-generated method stub
				String c1=o1.getIdCustomer().substring(2);
				String c2=o2.getIdCustomer().substring(2);
				return Integer.parseInt(c1) - Integer.parseInt(c2);
			}
			
		});
	}
	
	public void sortByIdCusDESC() {
		Collections.sort(list, new Comparator<Customer>() {
			@Override
			public int compare(Customer o1, Customer o2) {
				// TODO Auto-generated method stub
				String c1=o1.getIdCustomer().substring(2);
				String c2=o2.getIdCustomer().substring(2);
				return Integer.parseInt(c2) - Integer.parseInt(c1);
			}
			
		});
	}
	
	public List<Customer> getTopCustomers(int limit, Bill_DAO billDAO) {
        return list.stream()
                .sorted((c1, c2) -> {
                    double total1 = billDAO.getAllBillByCusId(c1.getIdCustomer()).stream().mapToDouble(Bill::getTotal).sum();
                    double total2 = billDAO.getAllBillByCusId(c2.getIdCustomer()).stream().mapToDouble(Bill::getTotal).sum();
                    return Double.compare(total2, total1);
                })
                .limit(limit)
                .collect(Collectors.toList());
    }
	
	public List<Customer> getGenderCustomers(int gender) {
	    return list.stream()
	            .filter(customer -> customer.getGender() == gender)
	            .collect(Collectors.toList());
	}
	
	public List<Customer> getCustomersWithMostBillsHasTime(Bill_DAO billDAO, LocalDate dateFrom, LocalDate dateTo) {
	    int maxNumBills = list.stream()
	            .mapToInt(customer -> billDAO.getAllBillByCusIdAndDateRange(customer.getIdCustomer(), dateFrom, dateTo).size())
	            .max()
	            .orElse(0);
	    return list.stream()
	            .filter(customer -> billDAO.getAllBillByCusIdAndDateRange(customer.getIdCustomer(), dateFrom, dateTo).size() == maxNumBills)
	            .collect(Collectors.toList());
	}

	public List<Customer> getCustomersWithMostBills(Bill_DAO billDAO) {
	    int maxNumBills = list.stream()
	            .mapToInt(customer -> billDAO.getAllBillByCusId(customer.getIdCustomer()).size())
	            .max()
	            .orElse(0);
	    return list.stream()
	            .filter(customer -> billDAO.getAllBillByCusId(customer.getIdCustomer()).size() == maxNumBills)
	            .collect(Collectors.toList());
	}

}
