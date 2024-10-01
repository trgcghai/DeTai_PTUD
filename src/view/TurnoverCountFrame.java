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
import java.util.Date;
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
import entity.Bill;

public class TurnoverCountFrame extends JFrame implements ActionListener{
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
	JLabel titleLabel, dateFromLabel, dateToLabel, totalBillLabel, totalTurnoverLabel, totalBillText, totalTurnoverText;
	JDatePickerImpl dateFromText, dateToText;
	UtilDateModel modelDateFrom, modelDateTo;
	JButton btnViewCount, btnDetailCount, btnPrintCount, btnCancel;
	JTable table;
	DefaultTableModel modelTable;
	JScrollPane scroll;
	
// dao
	Bill_DAO bill_dao = new Bill_DAO();

	public TurnoverCountFrame(String userName) {
		super("Thống kê doanh thu");
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
		
		addActionListener();
		
		Database.getInstance().connect();
		
		loadData();
		loadDataTable();
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
		
		titleLabel=new JLabel("THỐNG KÊ DOANH THU", SwingConstants.CENTER);
		titleLabel.setFont(new Font("Segoe UI",1,30));
		panelNorth.add(titleLabel, BorderLayout.NORTH);
		
		JPanel panelNorthC=new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 50));
		panelNorthC.setBorder(BorderFactory.createLineBorder(new Color(0,191,165)));
		dateFromLabel=new JLabel("Từ Ngày:"); dateFromLabel.setFont(new Font("Segoe UI",1,16));
		modelDateFrom=new UtilDateModel();
		Properties p=new Properties();
		p.put("text.day", "Day"); p.put("text.month", "Month"); p.put("text.year", "Year");
		JDatePanelImpl panelDateFrom=new JDatePanelImpl(modelDateFrom, p);
		dateFromText=new JDatePickerImpl(panelDateFrom, new LabelDateFormatter());
		
		dateToLabel=new JLabel("Đến Ngày:"); dateToLabel.setFont(new Font("Segoe UI",1,16));
		modelDateTo=new UtilDateModel();
		JDatePanelImpl panelDateTo=new JDatePanelImpl(modelDateTo, p);
		dateToText=new JDatePickerImpl(panelDateTo, new LabelDateFormatter());
		
		btnViewCount=new JButton("Xem Thống Kê");
		btnViewCount.setFont(new Font("Segoe UI",1,16));
		
		btnDetailCount=new JButton("Xem Chi Tiết");
		btnDetailCount.setFont(new Font("Segoe UI",1,16));
		
		btnPrintCount=new JButton("In Thống Kê");
		btnPrintCount.setFont(new Font("Segoe UI",1,16));
		
		// thêm nút hủy
		btnCancel = new JButton("Hủy");
		btnCancel.setFont(new Font("Segoe UI",1,16));
		
		panelNorthC.add(dateFromLabel); panelNorthC.add(dateFromText);
		panelNorthC.add(dateToLabel); panelNorthC.add(dateToText);
		panelNorthC.add(btnViewCount); panelNorthC.add(btnDetailCount); panelNorthC.add(btnPrintCount); panelNorthC.add(btnCancel);
		panelNorth.add(panelNorthC, BorderLayout.CENTER);
		
		String[] colNames= {"Mã hóa đơn","Tên khách hàng","Tên nhân viên","Ngày lập","Tổng tiền"};
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
		
		panelSouth.add(totalBillLabel); panelSouth.add(totalBillText);
		panelSouth.add(totalTurnoverLabel); panelSouth.add(totalTurnoverText);
		
		add(panelNorth, BorderLayout.NORTH);
		add(scroll, BorderLayout.CENTER);
		add(panelSouth, BorderLayout.SOUTH);
	}
	
// 	Change
//	Gán danh sách từ sql
	public void loadData() {
		bill_dao.setListBill(bill_dao.getAllBill());
	}
	
//	Đưa dữ liệu lên bảng
	public void loadDataTable() {
		modelTable.setRowCount(0);
		modelTable.getDataVector().removeAllElements();
		double totalPrice = 0;
		int numberBill = 0;
		DecimalFormat df = new DecimalFormat("#,###");
		DateTimeFormatter dft = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		for (Bill b : bill_dao.getListBill()) {
			modelTable.addRow(new Object[] {b.getIdBill(), b.getCustomer().getName(), b.getEmployee().getName(), b.getDateBill().format(dft), df.format(b.getTotal()) + " VNĐ"});
			totalPrice += b.getTotal();
			numberBill++;
		}
		totalBillText.setText(df.format(numberBill));
		totalTurnoverText.setText(df.format(totalPrice) + " VNĐ");
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
		btnPrintCount.addActionListener(this);
		btnViewCount.addActionListener(this);
		btnCancel.addActionListener(this);
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
			new UngVienFrame(userName).setVisible(true);
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
		// event 
		else if (obj.equals(btnViewCount)) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			
			if (modelDateFrom.getValue() == null || modelDateTo.getValue() == null) {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày để xem thống kê");
				return;
			}
			
			LocalDate from = LocalDate.parse(format.format(modelDateFrom.getValue()));
			LocalDate to = LocalDate.parse(format.format(modelDateTo.getValue()));
			
			if (from.isAfter(to)) {
				JOptionPane.showMessageDialog(this, "Chọn ngày không hợp lệ");
				return;
			}
			
			if (bill_dao.getAllBillFromDateToDate(from.toString(), to.toString()).size() == 0) {
				JOptionPane.showMessageDialog(this, "Không có hóa đơn trong khoảng thời gian này");
				return;
			}
			
			bill_dao.setListBill(bill_dao.getAllBillFromDateToDate(from.toString(), to.toString()));
			loadDataTable();
			
			modelTable.fireTableDataChanged();
		}
		else if (obj.equals(btnPrintCount)) {
			ExcelHelper excel = new ExcelHelper();
			excel.exportData(this, table);
		}
		else if (obj.equals(btnCancel)) {
			modelDateFrom.setValue(null);
			modelDateTo.setValue(null);
			loadData();
			loadDataTable();
		}
		else if(obj.equals(btnDetailCount)) {
			int row = table.getSelectedRow();
			if (row != -1) {
				
				Bill b = bill_dao.getAllBillByID(table.getValueAt(row, 0).toString()).get(0);
				
				new TurnoverDetailCountDialog(this, rootPaneCheckingEnabled, b).setVisible(true);				
			} else {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn cần xem");
			}
		}
	}
}
