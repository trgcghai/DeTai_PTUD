package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import controller.Database;
import controller.ExcelHelper;
import controller.LabelDateFormatter;
import dao.Bill_DAO;
import dao.Customer_DAO;
import dao.NhanVien_DAO;
import entity.Bill;
import entity.Customer;
import entity.NhanVien;

public class EmployeeCountFrame extends JFrame implements ActionListener{
	JMenuBar menuBar;
	JPanel imgMovie;
	String userName;
//	Nhân viên
	JMenu menuEmployee;
	JMenuItem itemUpdateEmployee, itemListEmpoyee;
// Khách hàng
	JMenu menuCustomer;
	JMenuItem itemListCustomer, itemUpdateCustomer;
// Phim
	JMenu menuMovie;
	JMenuItem itemListMovie, itemDirectorMovie, itemUpdateMovie, itemScreeningMovie;
// Vé
	JMenu menuTicket;
	JMenuItem itemBookTicket;
// Hóa đơn
	JMenu menuBill;
	JMenuItem itemListBill;
// Thống kê
	JMenu menuCount;
	JMenuItem itemTotalCount, itemMovieCount, itemEmployeeCount, itemCustomerCount;
// Hệ thống
	JMenu menuUser;
	JMenuItem itemHome, itemLogout;
	
// Component Thống Kê
	JPanel panelNorth, panelSouth;
	JLabel titleLabel, dateFromLabel, dateToLabel, 
		totalBillLabel, totalTurnoverLabel, totalCustomerLabel, totalBillText, totalTurnoverText, totalCustomerText;
	JDatePickerImpl dateFromText, dateToText;
	UtilDateModel modelDateFrom, modelDateTo;
	JButton btnViewCount, btnDetailCount, btnPrintCount, btnCancel;
	JComboBox box;
	JTable table;
	DefaultTableModel modelTable;
	JScrollPane scroll;
	
	NhanVien_DAO empDAO;
	Bill_DAO billDAO;
	
	public EmployeeCountFrame(String userName) {
		super("Thống kê nhân viên");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JPanel panelFrame=new JPanel();
		panelFrame.setLayout(new BorderLayout(10,10));
		panelFrame.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		setContentPane(panelFrame);
		
		this.userName=userName;
		
		initComponentEmployee();
		initComponentCustomer();
		initComponentMovie();
		initComponentTicket();
		initComponentBill();
		initComponentCount();
		initComponentUserRight();
		initComponent();
		
		Database.getInstance().connect();
		empDAO=new NhanVien_DAO();
		billDAO= new Bill_DAO();
		
		loadData();
		loadDataTable();
		loadInfor();
		
		addActionListener();
	}
	
	public void initComponentEmployee() {
		menuBar=new JMenuBar();
		setJMenuBar(menuBar);
		menuEmployee=new JMenu("Nhân Viên");  
		menuEmployee.setFont(new Font("Segoe UI",1,16)); 
		menuEmployee.setIcon(new ImageIcon(getClass().getResource("/image/employee.png")));
		menuBar.add(menuEmployee);
		
		itemListEmpoyee=new JMenuItem("Quản Lý");  itemListEmpoyee.setFont(new Font("Segoe UI",0,14));
		itemListEmpoyee.setIcon(new ImageIcon(getClass().getResource("/image/list.png")));
		menuEmployee.add(itemListEmpoyee);
		
		itemUpdateEmployee=new JMenuItem("Cập Nhật");  itemUpdateEmployee.setFont(new Font("Segoe UI",0,14));
		itemUpdateEmployee.setIcon(new ImageIcon(getClass().getResource("/image/update.png")));
		menuEmployee.add(itemUpdateEmployee);
		
		imgMovie=new JPanel(); imgMovie.setPreferredSize(new Dimension(1100, 700));
		JLabel imgLabel=new JLabel();
		ImageIcon imgIcon=new ImageIcon(getClass().getResource("/image/cinema.png"));
		Image img=imgIcon.getImage().getScaledInstance(1600, 1000, Image.SCALE_SMOOTH);
		imgLabel.setIcon(new ImageIcon(img));
		imgMovie.add(imgLabel);
		
		add(imgMovie);
	}
	
	public void initComponentCustomer() {
		menuCustomer=new JMenu("Khách Hàng");
		menuCustomer.setFont(new Font("Segoe UI",1,16)); 
		menuCustomer.setIcon(new ImageIcon(getClass().getResource("/image/customer.png")));
		
		itemListCustomer=new JMenuItem("Quản Lý");
		itemListCustomer.setFont(new Font("Segoe UI",0,14)); 
		itemListCustomer.setIcon(new ImageIcon(getClass().getResource("/image/list.png")));
		itemUpdateCustomer=new JMenuItem("Cập Nhật");
		itemUpdateCustomer.setFont(new Font("Segoe UI",0,14)); 
		itemUpdateCustomer.setIcon(new ImageIcon(getClass().getResource("/image/update.png")));
		
		menuCustomer.add(itemListCustomer);
		menuCustomer.add(itemUpdateCustomer);
		
		menuBar.add(menuCustomer);
	}

	public void initComponentMovie() {
		menuMovie=new JMenu("Phim");
		menuMovie.setFont(new Font("Segoe UI",1,16));
		menuMovie.setIcon(new ImageIcon(getClass().getResource("/image/movie.png")));
		
		itemListMovie=new JMenuItem("Quản Lý");
		itemListMovie.setFont(new Font("Segoe UI",0,14));
		itemListMovie.setIcon(new ImageIcon(getClass().getResource("/image/list.png")));
		itemDirectorMovie=new JMenuItem("Đạo Diễn");
		itemDirectorMovie.setFont(new Font("Segoe UI",0,14));
		itemDirectorMovie.setIcon(new ImageIcon(getClass().getResource("/image/director.png")));
		itemUpdateMovie=new JMenuItem("Cập Nhật");
		itemUpdateMovie.setFont(new Font("Segoe UI",0,14));
		itemUpdateMovie.setIcon(new ImageIcon(getClass().getResource("/image/update.png")));
		itemScreeningMovie=new JMenuItem("Suất Chiếu");
		itemScreeningMovie.setFont(new Font("Segoe UI",0,14));
		itemScreeningMovie.setIcon(new ImageIcon(getClass().getResource("/image/screening16px.png")));
		
		menuMovie.add(itemListMovie);
		menuMovie.add(itemDirectorMovie);
		menuMovie.add(itemUpdateMovie);
		menuMovie.add(itemScreeningMovie);
		
		menuBar.add(menuMovie);
	}
	
	public void initComponentTicket() {
		menuTicket=new JMenu("Vé");
		menuTicket.setFont(new Font("Segoe UI",1,16));
		menuTicket.setIcon(new ImageIcon(getClass().getResource("/image/ticket.png")));
		
		itemBookTicket=new JMenuItem("Đặt Vé");
		itemBookTicket.setFont(new Font("Segoe UI",0,14));
		itemBookTicket.setIcon(new ImageIcon(getClass().getResource("/image/sell.png")));
		
		menuTicket.add(itemBookTicket);
		
		menuBar.add(menuTicket);
	}
	
	public void initComponentBill() {
		menuBill=new JMenu("Hóa Đơn");
		menuBill.setFont(new Font("Segoe UI",1,16));
		menuBill.setIcon(new ImageIcon(getClass().getResource("/image/bill.png")));
		
		itemListBill=new JMenuItem("Quản Lý");
		itemListBill.setFont(new Font("Segoe UI",0,14));
		itemListBill.setIcon(new ImageIcon(getClass().getResource("/image/list.png")));
		
		menuBill.add(itemListBill);
		
		menuBar.add(menuBill);
	}
	
	public void initComponentCount() {
		menuCount=new JMenu("Thống Kê");
		menuCount.setFont(new Font("Segoe UI",1,16));
		menuCount.setIcon(new ImageIcon(getClass().getResource("/image/count.png")));
		
		itemTotalCount=new JMenuItem("Doanh Thu");
		itemTotalCount.setFont(new Font("Segoe UI",0,14));
		itemTotalCount.setIcon(new ImageIcon(getClass().getResource("/image/total.png")));
		itemMovieCount=new JMenuItem("Phim");
		itemMovieCount.setFont(new Font("Segoe UI",0,14));
		itemMovieCount.setIcon(new ImageIcon(getClass().getResource("/image/movie.png")));
		itemCustomerCount=new JMenuItem("Khách Hàng");
		itemCustomerCount.setFont(new Font("Segoe UI",0,14));
		itemCustomerCount.setIcon(new ImageIcon(getClass().getResource("/image/customer.png")));
		itemEmployeeCount=new JMenuItem("Nhân Viên");
		itemEmployeeCount.setFont(new Font("Segoe UI",0,14));
		itemEmployeeCount.setIcon(new ImageIcon(getClass().getResource("/image/employee.png")));
		
		menuCount.add(itemTotalCount);
		menuCount.add(itemMovieCount);
		menuCount.add(itemCustomerCount);
		menuCount.add(itemEmployeeCount);
		
		menuBar.add(menuCount);
	}
	
	public void initComponentUserRight() {
		menuBar.add(Box.createHorizontalGlue());
		menuUser=new JMenu();
		menuUser.setText(userName);
		menuUser.setFont(new Font("Segoe UI",1,16));
		menuUser.setIcon(new ImageIcon(getClass().getResource("/image/user.png")));
		menuBar.add(menuUser);
		menuUser.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		
		itemHome=new JMenuItem("Trang Chủ");
		itemHome.setFont(new Font("Segoe UI",0,14));
		itemHome.setIcon(new ImageIcon(getClass().getResource("/image/home.png")));
		itemLogout=new JMenuItem("Đăng Xuất"); itemLogout.setFont(new Font("Segoe UI",0,14));
		itemLogout.setIcon(new ImageIcon(getClass().getResource("/image/exit.png")));
		
		menuUser.add(itemHome);
		menuUser.add(itemLogout);
	}
	
	public void initComponent() {
		panelNorth=new JPanel();
		panelNorth.setLayout(new BorderLayout());
		
		titleLabel=new JLabel("THỐNG KÊ NHÂN VIÊN", SwingConstants.CENTER);
		titleLabel.setFont(new Font("Segoe UI",1,30));
		panelNorth.add(titleLabel, BorderLayout.NORTH);
		
		JPanel panelNorthC=new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 50));
		panelNorthC.setBorder(BorderFactory.createLineBorder(new Color(0,191,165)));
		dateFromLabel=new JLabel("Từ Ngày:"); dateFromLabel.setFont(new Font("Segoe UI",1,15));
		modelDateFrom=new UtilDateModel();
		Properties p=new Properties();
		p.put("text.day", "Day"); p.put("text.month", "Month"); p.put("text.year", "Year");
		JDatePanelImpl panelDateFrom=new JDatePanelImpl(modelDateFrom, p);
		dateFromText=new JDatePickerImpl(panelDateFrom, new LabelDateFormatter());
		
		dateToLabel=new JLabel("Đến Ngày:"); dateToLabel.setFont(new Font("Segoe UI",1,15));
		modelDateTo=new UtilDateModel();
		JDatePanelImpl panelDateTo=new JDatePanelImpl(modelDateTo, p);
		dateToText=new JDatePickerImpl(panelDateTo, new LabelDateFormatter());
		
		String[] options= {"====Chọn loại thống kê====","Top 5 nhân viên","Nhân viên nam","Nhân viên nữ","Nhân viên có đơn nhiều nhất"};
		box=new JComboBox(options);
		box.setFont(new Font("Segoe UI",1,15));
		
		btnViewCount=new JButton("Xem Thống Kê");
		btnViewCount.setFont(new Font("Segoe UI",1,15));
		
		btnDetailCount=new JButton("Xem Chi Tiết");
		btnDetailCount.setFont(new Font("Segoe UI",1,15));
		
		btnPrintCount=new JButton("In Thống Kê");
		btnPrintCount.setFont(new Font("Segoe UI",1,15));
		
		btnCancel=new JButton("Hủy");
		btnCancel.setFont(new Font("Segoe UI",1,15));
		
		panelNorthC.add(dateFromLabel); panelNorthC.add(dateFromText);
		panelNorthC.add(dateToLabel); panelNorthC.add(dateToText);
		panelNorthC.add(box);
		panelNorthC.add(btnViewCount); panelNorthC.add(btnDetailCount); panelNorthC.add(btnPrintCount); panelNorthC.add(btnCancel);
		panelNorth.add(panelNorthC, BorderLayout.CENTER);
		
		String[] colNames= {"Mã nhân viên","Tên nhân viên","Số điện thoại","Giới tính","Ngày sinh","Số lượng đơn","Tổng tiền"};
		modelTable=new DefaultTableModel(colNames,0);
		table=new JTable(modelTable);
		table.getTableHeader().setFont(new Font("Segoe UI",1,16));
		table.setFont(new Font("Segoe UI",1,14));
		scroll=new JScrollPane(table);
		scroll.setBorder(BorderFactory.createLineBorder(new Color(0,191,165)));
		
		panelSouth=new JPanel();
		panelSouth.setLayout(new FlowLayout(FlowLayout.RIGHT,30,50));
		panelSouth.setBorder(BorderFactory.createLineBorder(new Color(0,191,165)));
		
		totalBillLabel=new JLabel("Tổng đơn hàng:"); totalBillLabel.setFont(new Font("Segoe UI",1,20));
		totalBillText=new JLabel("20"); 
		totalBillText.setFont(new Font("Segoe UI",1,20)); totalBillText.setForeground(Color.RED);
		
		totalTurnoverLabel=new JLabel("Tổng doanh thu:"); totalTurnoverLabel.setFont(new Font("Segoe UI",1,20));
		totalTurnoverText=new JLabel("1.000.000 VNĐ"); 
		totalTurnoverText.setFont(new Font("Segoe UI",1,20)); totalTurnoverText.setForeground(Color.RED);
		
		totalCustomerLabel=new JLabel("Tổng nhân viên:"); totalCustomerLabel.setFont(new Font("Segoe UI",1,20));
		totalCustomerText=new JLabel("20"); 
		totalCustomerText.setFont(new Font("Segoe UI",1,20)); totalCustomerText.setForeground(Color.RED);
		
		panelSouth.add(totalCustomerLabel); panelSouth.add(totalCustomerText);
		panelSouth.add(totalBillLabel); panelSouth.add(totalBillText);
		panelSouth.add(totalTurnoverLabel); panelSouth.add(totalTurnoverText);
		
		add(panelNorth, BorderLayout.NORTH);
		add(scroll, BorderLayout.CENTER);
		add(panelSouth, BorderLayout.SOUTH);
	}
	
//	Gán danh sách từ sql
	public void loadData() {
		empDAO.setListEmp(empDAO.getAllEmp());
		billDAO.setListBill(billDAO.getAllBill());
	}
	
//	Đưa dữ liệu lên bảng
	private void loadDataTable() {
		writeDataFromListNoTime(empDAO.getListEmp());
		loadInfor();
	}
	
// 	Xóa hết dữ liệu trên bảng
	private void deleteAll() {
		DefaultTableModel dModel = (DefaultTableModel) table.getModel();
		dModel.getDataVector().removeAllElements();
		dModel.fireTableDataChanged();
	}
	
//	Lấy dữ liệu từ list không xét thời gian lên bảng
	private void writeDataFromListNoTime(List<NhanVien> listEmp) {
		modelTable.setRowCount(0);
		DecimalFormat formatter = new DecimalFormat("#,##0 VNĐ");
		DateTimeFormatter format=DateTimeFormatter.ofPattern("dd-MM-yyyy");
		for(NhanVien i: listEmp) {
	        var birthday=format.format(i.getBirthday());
	        int numBill = billDAO.getAllBillByEmpId(i.getIdEmployee()).size();
	        if (numBill!=0) {
	        	double total = billDAO.getAllBillByEmpId(i.getIdEmployee()).stream().mapToDouble(Bill::getTotal).sum();
		        Object[] objects=new Object[] {
		                i.getIdEmployee(), i.getName(), i.getPhone(), (i.getGender()==1?"Nam":"Nữ"),
		                birthday, numBill, formatter.format(total)
		        };
		        modelTable.addRow(objects);
	        }
	    }
	}
	
//	Lấy dữ liệu từ list có xét thời gian lên bảng
	private void writeDataFromListHasTime(List<NhanVien> listEmp, LocalDate fromDate, LocalDate toDate) {
		modelTable.setRowCount(0);
		DateTimeFormatter formatLocal = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		DecimalFormat formatter = new DecimalFormat("#,##0 VNĐ");
		for (NhanVien i : listEmp) {
			var birthday = (i.getBirthday() != null ? formatLocal.format(i.getBirthday()) : null);
			ArrayList<Bill> bills = billDAO.getAllBillByEmpIdAndDateRange(i.getIdEmployee(), fromDate, toDate);
			long numBill = bills.stream()
						.filter(bill -> bill.getDateBill().compareTo(fromDate) >= 0 && bill.getDateBill().compareTo(toDate) <= 0)
                        .count();
			if (numBill != 0) {
				double total = bills.stream().mapToDouble(Bill::getTotal).sum();
				Object[] objects = new Object[] {
						i.getIdEmployee(), i.getName(), i.getPhone(), (i.getGender()==1?"Nam":"Nữ"),
						birthday, numBill, formatter.format(total)
						};
            modelTable.addRow(objects);
			}	
		}
	}

	
//  Đưa dữ liệu thống kê lên JFrame
	private void loadInfor() {
		DecimalFormat formatter=new DecimalFormat("#,##0 VNĐ");
		String formatTotal;
		int countCus = table.getRowCount();
		int countBill = 0;
		double total = 0;
		for(int i=0; i<table.getRowCount(); i++) {
			countBill = countBill + Integer.parseInt(modelTable.getValueAt(i, 5).toString());
			formatTotal = modelTable.getValueAt(i, 6).toString();
			formatTotal = formatTotal.replaceAll("[^0-9.]", "");
			total = total + Double.parseDouble(formatTotal);
		}
		totalCustomerText.setText(String.valueOf(countCus));
		totalBillText.setText(String.valueOf(countBill));
		totalTurnoverText.setText(formatter.format(total));
	}
	
//	Hiển thị dữ liệu theo yêu cầu thống kê lên bảng
	private void loadDataWithStatistics() {
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		LocalDate fromDate = null;
	    LocalDate toDate = null;
	    if (modelDateFrom.getValue()==null && modelDateTo.getValue()!=null) {
	    	JOptionPane.showMessageDialog(this, "Hãy chọn ngày bắt đầu muốn thống kê", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
	        return;
		}else if (modelDateFrom.getValue()!=null && modelDateTo.getValue()==null) {
	        return;
		} else if (modelDateFrom.getValue()!=null && modelDateTo.getValue()!=null) {
	    	fromDate = LocalDate.parse(format.format(modelDateFrom.getValue()));
	        toDate = LocalDate.parse(format.format(modelDateTo.getValue()));
	        if (fromDate.isAfter(toDate)) {
	        	JOptionPane.showMessageDialog(this, "Ngày kết thúc phải sau ngày bắt đầu", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
	        	return;
			} else {
				deleteAll();
				String selectedOption = box.getSelectedItem().toString();
				if ("Top 5 nhân viên".equals(selectedOption)) {
					List<NhanVien> listEmp = empDAO.getTopEmployees(5, billDAO);
				    writeDataFromListHasTime(listEmp, fromDate, toDate);
				} else if ("Nhân viên nam".equals(selectedOption)) {
					List<NhanVien> listEmp = empDAO.getGenderEmployees(1);
					writeDataFromListHasTime(listEmp, fromDate, toDate);
				} else if ("Nhân viên nữ".equals(selectedOption)) {
					List<NhanVien> listEmp = empDAO.getGenderEmployees(0);
					writeDataFromListHasTime(listEmp, fromDate, toDate);
				} else if ("Nhân viên có đơn nhiều nhất".equals(selectedOption)) {
					writeDataFromListHasTime(empDAO.getEmployeesWithMostBillsHasTime(billDAO, fromDate, toDate), fromDate, toDate);
				} else if ("====Chọn loại thống kê====".equals(selectedOption)) {
					writeDataFromListHasTime(empDAO.getListEmp(), fromDate, toDate);
				}
			}
	        if (table.getRowCount()==0) {
	        	JOptionPane.showMessageDialog(this, "Không tìm thấy dữ liệu hóa đơn theo yêu cầu", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
	        	loadDataTable();
	        	modelDateFrom.setValue(null);
				modelDateTo.setValue(null);
				box.setSelectedIndex(0);
	        	loadDataTable();
	        	return;
			}
	    } else {
	    	deleteAll();
	    	String selectedOption = box.getSelectedItem().toString();
			if ("Top 5 nhân viên".equals(selectedOption)) {
			    List<NhanVien> listEmp = empDAO.getTopEmployees(5, billDAO);
			    writeDataFromListNoTime(listEmp);
			} else if ("Nhân viên nam".equals(selectedOption)) {
				List<NhanVien> listEmp = empDAO.getGenderEmployees(1);
				writeDataFromListNoTime(listEmp);
			} else if ("Nhân viên nữ".equals(selectedOption)) {
				List<NhanVien> listEmp = empDAO.getGenderEmployees(0);
				writeDataFromListNoTime(listEmp);
			} else if ("Nhân viên có đơn nhiều nhất".equals(selectedOption)) {
				List<NhanVien> listEmp = empDAO.getEmployeesWithMostBills(billDAO);
				writeDataFromListNoTime(listEmp);
			} else if ("====Chọn loại thống kê====".equals(selectedOption)) {
				loadDataTable();
			}
			if (table.getRowCount()==0) {
	        	JOptionPane.showMessageDialog(this, "Không tìm thấy dữ liệu hóa đơn theo yêu cầu", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
	        	loadDataTable();
	        	modelDateFrom.setValue(null);
				modelDateTo.setValue(null);
				box.setSelectedIndex(0);
	        	loadDataTable();
	        	return;
			}
	    }
		loadInfor();
	}
	
// 	Hiển thị các hóa dơn của khách hàng
	private void displayListBillEmp() {
		int row = table.getSelectedRow();
	    if (row == -1) {
	        JOptionPane.showMessageDialog(this, "Hãy chọn nhân viên cần xem các hóa đơn", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
	        return;
	    } else {
	        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
			LocalDate fromDate = null;
		    LocalDate toDate = null;
		    if (modelDateFrom.getValue()==null && modelDateTo.getValue()!=null) {
		    	JOptionPane.showMessageDialog(this, "Hãy chọn ngày bắt đầu muốn thống kê", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
		        return;
			}else if (modelDateFrom.getValue()!=null && modelDateTo.getValue()==null) {
		    	JOptionPane.showMessageDialog(this, "Hãy chọn ngày kết thúc", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
		        return;
			} else if (modelDateFrom.getValue()!=null && modelDateTo.getValue()!=null) {
				fromDate = LocalDate.parse(format.format(modelDateFrom.getValue()));
		        toDate = LocalDate.parse(format.format(modelDateTo.getValue()));
		    	ArrayList<Bill> list = billDAO.getAllBillByEmpIdAndDateRange(modelTable.getValueAt(row, 0).toString(), fromDate, toDate);
		    	new EmployeeDetailCountDialog(this, rootPaneCheckingEnabled, list).setVisible(true);
		    } else {
		    	ArrayList<Bill> list = billDAO.getAllBillByEmpId(modelTable.getValueAt(row, 0).toString());
		        new EmployeeDetailCountDialog(this, rootPaneCheckingEnabled, list).setVisible(true);
		    }
	    }
	}
	
	
//	Listener
	public void addActionListener() {
//		menu nhân viên
		itemListEmpoyee.addActionListener(this);
		itemUpdateEmployee.addActionListener(this);
		
//		menu khách hàng
		itemListCustomer.addActionListener(this);
		itemUpdateCustomer.addActionListener(this);
		
//		menu phim
		itemListMovie.addActionListener(this);
		itemUpdateMovie.addActionListener(this);
		itemDirectorMovie.addActionListener(this);
		itemScreeningMovie.addActionListener(this);
		
//		menu vé
		itemBookTicket.addActionListener(this);
		
//		menu hóa đơn
		itemListBill.addActionListener(this);
		
//		menu thống kê
		itemCustomerCount.addActionListener(this);
		itemEmployeeCount.addActionListener(this);
		itemMovieCount.addActionListener(this);
		itemTotalCount.addActionListener(this);
		
//		menu tài khoản
		itemLogout.addActionListener(this);
		itemHome.addActionListener(this);
		
//		other
		btnDetailCount.addActionListener(this);
		btnCancel.addActionListener(this);
		btnPrintCount.addActionListener(this);
		btnViewCount.addActionListener(this);	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		var obj=e.getSource();
		if(obj.equals(itemListEmpoyee)) {
			new DSNhanVienFrame(userName).setVisible(true);
			this.dispose();
		}
		else if(obj.equals(itemUpdateEmployee)) {
			new NhanVienFrame(userName).setVisible(true);
			this.dispose();
		}
		else if(obj.equals(itemListCustomer)) {
			new DSUngVienFrame(userName).setVisible(true);
			this.dispose();
		}
		else if(obj.equals(itemUpdateCustomer)) {
			new UngVienTestFrame(userName).setVisible(true);
			this.dispose();
		}
		else if(obj.equals(itemListMovie)) {
			new ListMovieFrame(userName).setVisible(true);
			this.dispose();
		}
		else if(obj.equals(itemUpdateMovie)) {
			new NhaTuyenDungFrame(userName).setVisible(true);
			this.dispose();
		}
		else if(obj.equals(itemDirectorMovie)) {
			new DirectorFrame(userName).setVisible(true);
			this.dispose();
		}
		else if(obj.equals(itemScreeningMovie)) {
			new ScreeningMovieFrame(userName).setVisible(true);
			this.dispose();
		}
		else if(obj.equals(itemBookTicket)) {
			new BookTicketFrame(userName).setVisible(true);
			this.dispose();
		}
		else if(obj.equals(itemListBill)) {
			new ListBillFrame(userName).setVisible(true);
			this.dispose();
		}
		else if(obj.equals(itemCustomerCount)) {
			new CustomerCountFrame(userName).setVisible(true);
			this.dispose();
		}
		else if(obj.equals(itemEmployeeCount)) {
			new EmployeeCountFrame(userName).setVisible(true);
			this.dispose();
		}
		else if(obj.equals(itemMovieCount)) {
			new MovieCountFrame(userName).setVisible(true);
			this.dispose();
		}
		else if(obj.equals(itemTotalCount)) {
			new TurnoverCountFrame(userName).setVisible(true);
			this.dispose();
		}
		else if(obj.equals(itemHome)) {
			new MainFrame(userName).setVisible(true);
			this.dispose();
		}
		else if(obj.equals(itemLogout)) {
			new LoginFrame().setVisible(true);
			this.dispose();
		}
		else if(obj.equals(btnDetailCount)) {
			displayListBillEmp();
		}
		else if (obj.equals(btnViewCount)) {
			loadDataWithStatistics();;
		}
		else if (obj.equals(btnPrintCount)) {
			ExcelHelper export=new ExcelHelper();
			export.exportData(this, table);
		}
		else if (obj.equals(btnCancel)) {
			modelDateFrom.setValue(null);
			modelDateTo.setValue(null);
			box.setSelectedIndex(0);
			loadDataTable();
		}
	}
}
