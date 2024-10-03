package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;
import java.util.stream.Collectors;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.jdatepicker.JDatePicker;

import controller.Database;
import controller.LabelDateFormatter;
import dao.Bill_DAO;
import entity.Bill;
import entity.NhanVien;

public class ListBillFrame extends JFrame implements ActionListener{
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
	
//	Component danh sách phim
	JPanel billPanel, sortPanelBill, searchPanelBill, northPanelBill, southPanelBill;
	JRadioButton radioSortByDateASCBill, radioSortByDateDESCBill, radioSortByIdASCBill, radioSortByIdDESCBill, radioSortByTotalPriceASC, radioSortByTotalPriceDESC,
				radioSearchByEmpBill, radioSearchByCusBill, radioSearchByIdBill, radioSearchByDate;
	JTextField textSearchByEmp, textSearchByCus, textSearchByDate, textSearchById;
	JButton btnSearchBill, btnResetBill;
	UtilDateModel modelDate;
	JDatePickerImpl dateBillText;
	ButtonGroup btnGroupSortBill, btnGroupSearchBill;
	JTable tableBill;
	DefaultTableModel modelTableBill;
	JScrollPane scrollBill;
	
	private Bill_DAO bill_dao;
	
	public ListBillFrame(String userName) {
		setTitle("Danh sách hóa đơn");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JPanel panelFrame=new JPanel();
		panelFrame.setLayout(new BorderLayout());
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
		
		addButtonGroup();
		addActionListener();
		
		Database.getInstance().connect();
		bill_dao=new Bill_DAO();
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
		billPanel=new JPanel(); 
		billPanel.setLayout(new BorderLayout(5,5));
		
//		Panel sắp xếp phim
		sortPanelBill=new JPanel();
		sortPanelBill.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(0,191,165)), "Sắp xếp danh sách hóa đơn", TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI",1,14)));
		sortPanelBill.setLayout(new GridLayout(2, 2));
		
		radioSortByDateASCBill=new JRadioButton("Theo ngày lập tăng dần"); radioSortByDateASCBill.setFont(new Font("Segoe UI",1,14));
		radioSortByDateDESCBill=new JRadioButton("Theo ngày lập giảm dần"); radioSortByDateDESCBill.setFont(new Font("Segoe UI",1,14));
		radioSortByIdASCBill=new JRadioButton("Theo mã hóa đơn tăng dần"); radioSortByIdASCBill.setFont(new Font("Segoe UI",1,14));
		radioSortByIdDESCBill=new JRadioButton("Theo mã hóa đơn giảm dần"); radioSortByIdDESCBill.setFont(new Font("Segoe UI",1,14));
		radioSortByTotalPriceASC = new JRadioButton("Theo tổng tiền tăng dần"); radioSortByTotalPriceASC.setFont(new Font("Segoe UI",1,14));
		radioSortByTotalPriceDESC = new JRadioButton("Theo tổng tiền giảm dần"); radioSortByTotalPriceDESC.setFont(new Font("Segoe UI",1,14));
		
		sortPanelBill.add(radioSortByDateASCBill);  sortPanelBill.add(radioSortByDateDESCBill);
		sortPanelBill.add(radioSortByIdASCBill);    sortPanelBill.add(radioSortByIdDESCBill);
		sortPanelBill.add(radioSortByTotalPriceASC);   sortPanelBill.add(radioSortByTotalPriceDESC);
		
		northPanelBill=new JPanel();  northPanelBill.setLayout(new GridLayout(1, 2));
		northPanelBill.add(sortPanelBill);

//		Panel tìm kiếm phim
		searchPanelBill=new JPanel();
		searchPanelBill.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(0,191,165)), "Tìm hóa đơn", TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI",1,14)));
		searchPanelBill.setPreferredSize(new Dimension(450,130));
		searchPanelBill.setLayout(new GridLayout(3,2));
		
		JPanel res=new JPanel(); res.setLayout(new FlowLayout(FlowLayout.LEFT));
		radioSearchByIdBill=new JRadioButton("Theo mã hóa đơn:   ");
		radioSearchByIdBill.setFont(new Font("Segoe UI",1,14));
		textSearchById= new JTextField(15);
		textSearchById.setFont(new Font("Segoe UI",0,14));
		textSearchById.setEditable(false);
		res.add(radioSearchByIdBill); res.add(textSearchById);
		searchPanelBill.add(res);
		
		res=new JPanel(); res.setLayout(new FlowLayout(FlowLayout.LEFT));
		radioSearchByDate=new JRadioButton("Theo ngày lập:           ");
		radioSearchByDate.setFont(new Font("Segoe UI",1,14));
		
		modelDate = new UtilDateModel();
		Properties properties = new Properties();
        properties.put("text.today", "Today");
        properties.put("text.month", "Month");
        properties.put("text.year", "Year");
        JDatePanelImpl panelDateBill = new JDatePanelImpl(modelDate, properties);
        modelDate.setValue(new Date());
        dateBillText = new JDatePickerImpl(panelDateBill, new LabelDateFormatter());
        dateBillText.setPreferredSize(new Dimension(190,20));
		res.add(radioSearchByDate); res.add(dateBillText);
		searchPanelBill.add(res);
		
		res=new JPanel(); res.setLayout(new FlowLayout(FlowLayout.LEFT));
		radioSearchByEmpBill=new JRadioButton("Theo tên nhân viên:"); 
		radioSearchByEmpBill.setFont(new Font("Segoe UI",1,14));
		textSearchByEmp=new JTextField(15);
		textSearchByEmp.setFont(new Font("Segoe UI",0,14));
		textSearchByEmp.setEditable(false);
		res.add(radioSearchByEmpBill); res.add(textSearchByEmp);
		searchPanelBill.add(res);
		
		res=new JPanel(); res.setLayout(new FlowLayout(FlowLayout.LEFT));
		radioSearchByCusBill=new JRadioButton("Theo tên khách hàng:"); 
		radioSearchByCusBill.setFont(new Font("Segoe UI",1,14));
		textSearchByCus = new JTextField(14);
		textSearchByCus.setFont(new Font("Segoe UI",0,14));
		textSearchByCus.setEditable(false);
		res.add(radioSearchByCusBill); res.add(textSearchByCus);
		searchPanelBill.add(res);
		
		res=new JPanel(); searchPanelBill.add(res);
		
		res=new JPanel(); res.setLayout(new FlowLayout(FlowLayout.RIGHT, 7, 0));
		btnResetBill=new JButton("Hủy"); btnResetBill.setFont(new Font("Segoe UI",1,14));
		btnResetBill.setBackground(Color.RED); btnResetBill.setForeground(Color.WHITE);
		btnSearchBill=new JButton("Tìm"); btnSearchBill.setFont(new Font("Segoe UI",1,14));
		btnSearchBill.setBackground(new Color(0, 102, 102)); btnSearchBill.setForeground(Color.WHITE);
		res.add(btnResetBill);
		res.add(btnSearchBill);
		searchPanelBill.add(res);
		
		northPanelBill.add(searchPanelBill);
		
		billPanel.add(northPanelBill,BorderLayout.NORTH);
		
		
//		Table phim
		String[] colNameEmp= {"Mã hóa đơn","Tên khách hàng","Tên nhân viên","Ngày lập","Tổng tiền"};
		modelTableBill= new DefaultTableModel(colNameEmp,0);
		tableBill=new JTable(modelTableBill);
		tableBill.getTableHeader().setFont(new Font("Segoe UI",1,14));
		tableBill.setFont(new Font("Segoe UI",1,13));
		scrollBill=new JScrollPane(tableBill);
		scrollBill.setBorder(BorderFactory.createLineBorder(new Color(0,191,165)));
		
		billPanel.add(scrollBill,BorderLayout.CENTER);
		
		add(billPanel);
	}

//	Change
	public void addButtonGroup() {
		btnGroupSortBill=new ButtonGroup();
		btnGroupSortBill.add(radioSortByDateASCBill);
		btnGroupSortBill.add(radioSortByDateDESCBill);
		btnGroupSortBill.add(radioSortByIdASCBill);
		btnGroupSortBill.add(radioSortByIdDESCBill);
		btnGroupSortBill.add(radioSortByTotalPriceASC);
		btnGroupSortBill.add(radioSortByTotalPriceDESC);
		
		btnGroupSearchBill=new ButtonGroup();
		btnGroupSearchBill.add(radioSearchByEmpBill);
		btnGroupSearchBill.add(radioSearchByCusBill);
		btnGroupSearchBill.add(radioSearchByIdBill);
		btnGroupSearchBill.add(radioSearchByDate);
	}
	
//	Gán danh sách từ sql
	public void loadData() {
		bill_dao.setListBill(bill_dao.getAllBill());
	}
	
//	Đưa dữ liệu lên bảng
	public void loadDataTable() {
		modelTableBill.getDataVector().removeAllElements();
		DateTimeFormatter format=DateTimeFormatter.ofPattern("dd-MM-yyyy");
		DecimalFormat decimalFormat=new DecimalFormat("#,##0"+" VNĐ");
		for (Bill b : bill_dao.getListBill()) {
			Object[] row=new Object[] {
				b.getIdBill(), b.getCustomer().getName(), b.getEmployee().getName(), 
				format.format(b.getDateBill()), decimalFormat.format(b.getTotal())
			};
			modelTableBill.addRow(row);
		}
	}
	
//	Reset Bill
	public void resetBill() {
		btnGroupSortBill.clearSelection();
		btnGroupSearchBill.clearSelection();
		textSearchByCus.setText(""); textSearchByCus.setEditable(false);
		textSearchByEmp.setText(""); textSearchByEmp.setEditable(false);
		textSearchById.setText(""); textSearchById.setEditable(false);
		modelDate.setValue(new Date());
		
		bill_dao.setListBill(bill_dao.getAllBill());
		loadDataTable();
	}
	
//	Tìm kiếm hóa đơn
	public void searchBill() {
		if (radioSearchByIdBill.isSelected()) {
			String id = textSearchById.getText().trim();
			if (id.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Vui lòng nhập mã hóa đơn để tìm");
				return;
			}
			bill_dao.getListBill().clear();
			bill_dao.setListBill(bill_dao.getAllBillByID(id));
			loadDataTable();
			JOptionPane.showMessageDialog(this, "Tìm thấy "+bill_dao.getListBill().size()+" kết quả");
		} else if (radioSearchByCusBill.isSelected()) {
			String cus = textSearchByCus.getText().trim();
			if (cus.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Vui lòng nhập tên khách hàng để tìm");
				return;
			}
			bill_dao.getListBill().clear();
			bill_dao.setListBill(bill_dao.getAllBillByCus(cus));
			loadDataTable();
			JOptionPane.showMessageDialog(this, "Tìm thấy "+bill_dao.getListBill().size()+" kết quả");
		} else if (radioSearchByEmpBill.isSelected()) {
			String emp = textSearchByEmp.getText().trim();
			if (emp.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Vui lòng nhập tên nhân viên để tìm");
				return;
			}
			bill_dao.getListBill().clear();
			bill_dao.setListBill(bill_dao.getAllBillByEmp(emp));
			loadDataTable();
			JOptionPane.showMessageDialog(this, "Tìm thấy "+bill_dao.getListBill().size()+" kết quả");
		} else if (radioSearchByDate.isSelected()) {
			Date date = modelDate.getValue();
			if (date == null) {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày lập để tìm");
				return;
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String dateString = sdf.format(date);
			bill_dao.getListBill().clear();
			bill_dao.setListBill(bill_dao.getAllBillByDate(dateString));
			loadDataTable();
			JOptionPane.showMessageDialog(this, "Tìm thấy "+bill_dao.getListBill().size()+" kết quả");
		} else {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn tiêu chí để tìm kiếm");
		}
		
		btnGroupSortBill.clearSelection();
		btnGroupSearchBill.clearSelection();
		textSearchByCus.setText(""); textSearchByCus.setEditable(false);
		textSearchByEmp.setText(""); textSearchByEmp.setEditable(false);
		textSearchById.setText(""); textSearchById.setEditable(false);
		modelDate.setValue(new Date());
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
		radioSortByDateASCBill.addActionListener(this);
		radioSortByDateDESCBill.addActionListener(this);
		radioSortByIdASCBill.addActionListener(this);
		radioSortByIdDESCBill.addActionListener(this);
		radioSortByTotalPriceASC.addActionListener(this);
		radioSortByTotalPriceDESC.addActionListener(this);
		
		
		radioSearchByEmpBill.addActionListener(this);
		radioSearchByCusBill.addActionListener(this);
		radioSearchByIdBill.addActionListener(this);
		radioSearchByDate.addActionListener(this);
		
		btnSearchBill.addActionListener(this);
		btnResetBill.addActionListener(this);
	}
	
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
		else if(obj.equals(radioSearchByIdBill)) {
			if(!radioSearchByEmpBill.isSelected() && !radioSearchByCusBill.isSelected()) {
				textSearchByEmp.setText(""); textSearchByEmp.setEditable(false);
				textSearchByCus.setText(""); textSearchByCus.setEditable(false);
				modelDate.setValue(new Date());
			}
			textSearchById.setEditable(true);
			
		}
		else if(obj.equals(radioSearchByEmpBill)) {
			if(!radioSearchByIdBill.isSelected() && !radioSearchByCusBill.isSelected()) {
				textSearchById.setText(""); textSearchById.setEditable(false);
				textSearchByCus.setText(""); textSearchByCus.setEditable(false);
				modelDate.setValue(new Date());
			}
			textSearchByEmp.setEditable(true);
		}
		else if(obj.equals(radioSearchByCusBill)) {
			if(!radioSearchByIdBill.isSelected() && !radioSearchByEmpBill.isSelected()) {
				textSearchById.setText(""); textSearchById.setEditable(false);
				textSearchByEmp.setText(""); textSearchByEmp.setEditable(false);
				modelDate.setValue(new Date());
			}
			textSearchByCus.setEditable(true);
		}
		else if (obj.equals(btnSearchBill)) {
			searchBill();
		}
		else if (obj.equals(btnResetBill)) {
			resetBill();
		}
		else if (obj.equals(radioSortByDateASCBill)) {
			bill_dao.sortByDateASC();
			loadDataTable();
		} 
		else if (obj.equals(radioSortByDateDESCBill)) {
			bill_dao.sortByDateDESC();
			loadDataTable();
		} 
		else if (obj.equals(radioSortByIdASCBill)) {
			bill_dao.sortByIdBillASC();
			loadDataTable();
		}
		else if (obj.equals(radioSortByIdDESCBill)) {
			bill_dao.sortByIdBillDESC();
			loadDataTable();
		}
		else if (obj.equals(radioSortByTotalPriceASC)) {
			bill_dao.sortByTotalPriceASC();
			loadDataTable();
		} 
		else if (obj.equals(radioSortByTotalPriceDESC)) {
			bill_dao.sortByTotalPriceDESC();
			loadDataTable();
		}
	}
}
