package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;


import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import controller.Database;
import controller.FilterImp;
import controller.LabelDateFormatter;
import dao.Customer_DAO;
import dao.Director_DAO;
import dao.NhanVien_DAO;
import dao.Movie_DAO;
import dao.Room_DAO;
import dao.Screening_DAO;
import dao.Sitting_DAO;
import dao.Ticket_DAO;
import entity.Customer;
import entity.NhanVien;
import entity.Movie;
import entity.Room;
import entity.Screening;
import entity.Sitting;
import entity.Ticket;
import exception.checkName;
import exception.checkNumber;
import exception.checkPhone;

public class BookTicketFrame extends JFrame implements FocusListener, ActionListener, MouseListener, DocumentListener {
//	Thanh menu
	JMenuBar menuBar;
	JPanel imgMovie;
	String userName;
//	Nhân viên
	JMenu menuEmployee;
	JMenuItem itemUpdateEmployee, itemListEmployee;
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
	
//	Component
	JPanel panelWest, panelWNorth,
			panelEast, panelENorth, panelENorthTop, panelENorthBottom, panelECenter, panelESouth, panelESouthTop, panelESouthBottom;
	JLabel movieLabel, typeLabel, directorLabel, 
		ticketTitleLabel, ticketMovieLabel, ticketScreeningLabel, ticketTimeLabel, ticketRoomLabel, ticketSitLabel, ticketTotalMovieLabel, ticketTotalLabel,
		ticketMovieText, ticketScreeningText, ticketTimeText, ticketRoomText, ticketSitText, ticketTotalMovieText, ticketTotalText,
		ticketIDLabel, ticketIDText, paymentTitleLabel, paymentTotalLabel, paymentTotalText,
		paymentCusLabel, payCusLabel, payCusText;
	JTextField movieText, directorText, 
		findTelText, nameText, telText, paymentCusText;
	JComboBox typeText,  genderText;
	JButton btnFindMovie, btnResetMovie, btnFindTel, btnResetPayment, btnPayment;
	JTable tableMovie;
	DefaultTableModel modelMovie;
	JScrollPane scroll;
	
//	Component suất chiếu
	JPanel panelWSouth, panelDoneDate, panelDoneTime, panelAllTime, panelContinue, panelComeback;
	JLabel chooseDateLabel, chooseTimeLabel, dateLabel, doneDateLabel, doneDateText, doneTimeLabel, doneTimeText,
		bookSitLabel, resetLabel;
	JDatePickerImpl date;
	UtilDateModel modelDate;
	JLabel nameMovieLabel;
	JLabel text;
	
	
	private Movie_DAO movieDAO;
	private Director_DAO directorDAO;
	private Screening_DAO screeningDAO;
	private Room_DAO roomDAO;
	private Customer_DAO customerDAO;
	private NhanVien_DAO employeeDAO;
	private Sitting_DAO sittingDAO;
	private Ticket_DAO ticketDAO;
	
	private ArrayList<String> food;
	private ArrayList<String> chairTexts;
	private JLabel totalText;
	private ArrayList<Screening> currentScreeningMovie;
	private Map<Component, String> currentScreeningMovieLabel;
	
	public BookTicketFrame(String userName) {
		setTitle("Đặt vé xem phim");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JPanel panelFrame=new JPanel();
		panelFrame.setLayout(new BorderLayout());
		panelFrame.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		setContentPane(panelFrame);
		
		this.userName=userName;
		food=new ArrayList<String>();
		
		initComponentEmployee();
		initComponentCustomer();
		initComponentMovie();
		initComponentTicket();
		initComponentBill();
		initComponentCount();
		initComponentUserRight();
		initComponent();
		
		addFocusListener();
		addActionListener();
		addMouseListener();
		addDocumentListener();
		
		Database.getInstance().connect();
		movieDAO=new Movie_DAO();
		directorDAO=new Director_DAO();
		screeningDAO=new Screening_DAO();
		roomDAO=new Room_DAO();
		customerDAO=new Customer_DAO();
		employeeDAO=new NhanVien_DAO();
		sittingDAO=new Sitting_DAO();
		ticketDAO=new Ticket_DAO();
		
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
		
		itemListEmployee=new JMenuItem("Quản Lý");  itemListEmployee.setFont(new Font("Segoe UI",0,14));
		itemListEmployee.setIcon(new ImageIcon(getClass().getResource("/image/list.png")));
		menuEmployee.add(itemListEmployee);
		
		itemUpdateEmployee=new JMenuItem("Cập Nhật");  itemUpdateEmployee.setFont(new Font("Segoe UI",0,14));
		itemUpdateEmployee.setIcon(new ImageIcon(getClass().getResource("/image/update.png")));
		menuEmployee.add(itemUpdateEmployee);
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
//		panel West
		panelWest=new JPanel();
		panelWest.setLayout(new BorderLayout(5,5));
		
//		Tìm phim
		panelWNorth=new JPanel();
		panelWNorth.setLayout(new GridBagLayout());
		panelWNorth.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(0,191,165)), "Tìm Phim", TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI",1,14)));
		GridBagConstraints gbc=new GridBagConstraints();
		
		gbc.ipadx=5; gbc.ipady=5;
		gbc.anchor=GridBagConstraints.WEST;
		gbc.insets=new Insets(5, 3, 5, 3);
		gbc.gridx=0; gbc.gridy=0;
		movieLabel=new JLabel("Tên phim:"); movieLabel.setFont(new Font("Segoe UI",1,14));
		panelWNorth.add(movieLabel,gbc);
		gbc.gridx=1;
		movieText=new JTextField(); movieText.setPreferredSize(new Dimension(200,25));
		movieText.setFont(new Font("Segoe UI",0,13));
		panelWNorth.add(movieText,gbc);
		
		gbc.gridx=2;
		typeLabel=new JLabel("Thể loại:"); typeLabel.setFont(new Font("Segoe UI",1,14));
		panelWNorth.add(typeLabel,gbc);
		gbc.gridx=3;
		typeText=new JComboBox(); typeText.setFont(new Font("Segoe UI",1,13));
		typeText.setPreferredSize(new Dimension(150,25));
		panelWNorth.add(typeText,gbc);
		
		gbc.gridx=4;
		directorLabel=new JLabel("Đạo diễn:"); directorLabel.setFont(new Font("Segoe UI",1,14));
		panelWNorth.add(directorLabel,gbc);
		gbc.gridx=5;
		directorText=new JTextField(); directorText.setFont(new Font("Segoe UI",0,13));
		directorText.setPreferredSize(new Dimension(200,25));
		panelWNorth.add(directorText,gbc);
	
		gbc.gridx=6;
		btnResetMovie=new JButton("Hủy"); btnResetMovie.setFont(new Font("Segoe UI",1,14));
		btnResetMovie.setPreferredSize(new Dimension(80,25));
		btnResetMovie.setBackground(Color.RED);
		btnResetMovie.setForeground(Color.WHITE);
		panelWNorth.add(btnResetMovie,gbc);
		
		gbc.gridx=7;
		btnFindMovie=new JButton("Tìm"); btnFindMovie.setFont(new Font("Segoe UI",1,14));
		btnFindMovie.setPreferredSize(new Dimension(80,25));
		btnFindMovie.setBackground(new Color(0, 102, 102));
		btnFindMovie.setForeground(Color.WHITE);
		panelWNorth.add(btnFindMovie,gbc);
		
			
//		Danh sách phim
		String[] colNames= {"Mã phim","Tên phim","Đạo diễn","Thể loại","Ngày công chiếu","Thời lượng"};
		modelMovie=new DefaultTableModel(colNames,0);
		tableMovie=new JTable(modelMovie);
		tableMovie.getTableHeader().setFont(new Font("Segoe UI",1,14));
		tableMovie.setFont(new Font("Segoe UI",1,13));
		scroll=new JScrollPane(tableMovie); 
		scroll.setPreferredSize(new Dimension(1000,300));
		scroll.setBorder(BorderFactory.createLineBorder(new Color(0,191,165)));

		
//		Danh sách suất chiếu
		panelWSouth=new JPanel();
		panelWSouth.setLayout(null);
		panelWSouth.setPreferredSize(new Dimension(panelWest.getWidth(), 360));
		panelWSouth.setBorder(BorderFactory.createLineBorder(new Color(0,191,165)));
		
		nameMovieLabel=new JLabel();
		nameMovieLabel.setText("");
		nameMovieLabel.setFont(new Font("Segoe UI",1,16));
		nameMovieLabel.setForeground(Color.RED);
		nameMovieLabel.setBounds(5, 0, 200, 30);
		panelWSouth.add(nameMovieLabel);
		
		chooseDateLabel=new JLabel("CHỌN NGÀY XEM:");
		chooseDateLabel.setFont(new Font("Segoe UI",1,16));
		chooseDateLabel.setBounds(5, 20, 150, 30);
		panelWSouth.add(chooseDateLabel);
		
		dateLabel=new JLabel("Ngày");
		dateLabel.setFont(new Font("Segoe UI",1,13));
		dateLabel.setBounds(30, 50, 80, 30);
		panelWSouth.add(dateLabel);
		
		modelDate=new UtilDateModel();
		modelDate.setSelected(true);
		Properties p=new Properties();
		p.put("text.day", "Day"); p.put("text.month", "Month"); p.put("text.year","Year");
		JDatePanelImpl panelDate=new JDatePanelImpl(modelDate, p);
		date=new JDatePickerImpl(panelDate, new LabelDateFormatter());
		date.setBounds(80, 50, 150, 20);
		date.getComponent(1).setEnabled(false);
		panelWSouth.add(date);
		
		panelDoneDate=new JPanel();
		panelDoneDate.setBackground(new Color(33/255f, 33/255f, 33/255f));
		panelDoneDate.setBounds(5, 80, 975, 30);
		panelDoneDate.setLayout(new FlowLayout(FlowLayout.LEADING));
		doneDateLabel=new JLabel("Đã chọn ngày:");
		doneDateLabel.setForeground(Color.WHITE); doneDateLabel.setFont(new Font("Segoe UI",1,13));
		doneDateText=new JLabel();
		doneDateText.setForeground(Color.WHITE); doneDateText.setFont(new Font("Segoe UI",1,13));
		panelDoneDate.add(doneDateLabel); panelDoneDate.add(doneDateText);
		panelWSouth.add(panelDoneDate);
		
		chooseTimeLabel=new JLabel("CHỌN GIỜ:");
		chooseTimeLabel.setFont(new Font("Segoe UI",1,16));
		chooseTimeLabel.setBounds(5, 140, 150, 30);
		panelWSouth.add(chooseTimeLabel);
		
		panelAllTime=new JPanel();
		panelAllTime.setBackground(new Color(223, 223, 223));
		panelAllTime.setBounds(5, 170, 975, 100);
		panelWSouth.add(panelAllTime);
		
		panelDoneTime=new JPanel();
		panelDoneTime.setBackground(new Color(33/255f, 33/255f, 33/255f));
		panelDoneTime.setBounds(5,280,975,30);
		panelDoneTime.setLayout(new FlowLayout(FlowLayout.LEADING));
		doneTimeLabel=new JLabel("Đã chọn giờ:");
		doneTimeLabel.setForeground(Color.WHITE); doneTimeLabel.setFont(new Font("Segoe UI",1,13));
		doneTimeText=new JLabel(); 
		doneTimeText.setForeground(Color.WHITE); doneTimeText.setFont(new Font("Segoe UI",1,13));
		panelDoneTime.add(doneTimeLabel); panelDoneTime.add(doneTimeText);
		panelWSouth.add(panelDoneTime);
		
		panelComeback=new JPanel();
		panelComeback.setBackground(new Color(33/255f, 33/255f, 33/255f));
		panelComeback.setBounds(390, 320, 80, 30);
		resetLabel=new JLabel("Hủy"); resetLabel.setFont(new Font("Segoe UI",1,13));
		resetLabel.setForeground(Color.WHITE);
		panelComeback.add(resetLabel);
		panelWSouth.add(panelComeback);
		
		panelContinue=new JPanel();
		panelContinue.setBackground(new Color(255, 105, 105));
		panelContinue.setBounds(490, 320, 80, 30);
		bookSitLabel=new JLabel("Đặt chỗ ngồi"); bookSitLabel.setFont(new Font("Segoe UI",1,13));
		bookSitLabel.setForeground(Color.WHITE);
		panelContinue.add(bookSitLabel);
		panelWSouth.add(panelContinue);
		
		panelWest.add(panelWNorth, BorderLayout.NORTH);
		panelWest.add(panelWSouth, BorderLayout.SOUTH);
		panelWest.add(scroll, BorderLayout.CENTER);
		
//		Panel East
		panelEast=new JPanel(); panelEast.setLayout(new BorderLayout());
		
//		Thông tin vé
		panelENorth=new JPanel(); panelENorth.setLayout(new BorderLayout());
		panelENorth.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(0,191,165)), "Thông Tin Vé", TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI",1,14)));
		
		panelENorthTop=new JPanel(); panelENorthTop.setLayout(new FlowLayout(FlowLayout.CENTER));
		ticketTitleLabel=new JLabel("VÉ PHIM"); ticketTitleLabel.setFont(new Font("Segoe UI",1,20));
		panelENorthTop.add(ticketTitleLabel);
		panelENorth.add(panelENorthTop, BorderLayout.NORTH);
		
		panelENorthBottom=new JPanel(); panelENorthBottom.setLayout(new GridBagLayout());
		GridBagConstraints gbcE=new GridBagConstraints();
		
		gbcE.anchor=GridBagConstraints.WEST;
		gbcE.insets= new Insets(5, 3, 5, 20);
		gbcE.gridx=0; gbcE.gridy=0;
		ticketIDLabel=new JLabel("Mã vé: "); ticketIDLabel.setFont(new Font("Segoe UI",1,15));
		panelENorthBottom.add(ticketIDLabel, gbcE);
		gbcE.gridx=1; gbcE.gridwidth=GridBagConstraints.REMAINDER;
		ticketIDText=new JLabel("V001"); ticketIDText.setFont(new Font("Segoe UI",1,15));
		ticketIDText.setForeground(Color.RED);
		panelENorthBottom.add(ticketIDText, gbcE);
		
		gbcE.gridx=0; gbcE.gridy=1;
		ticketMovieLabel=new JLabel("Tên phim: "); ticketMovieLabel.setFont(new Font("Segoe UI",1,15));
		panelENorthBottom.add(ticketMovieLabel, gbcE);
		gbcE.gridx=1; gbcE.gridwidth=GridBagConstraints.REMAINDER;
		ticketMovieText=new JLabel(); ticketMovieText.setFont(new Font("Segoe UI",1,15));
		ticketMovieText.setForeground(Color.RED);
		ticketMovieText.setPreferredSize(new Dimension(300,25));
		panelENorthBottom.add(ticketMovieText, gbcE);
		
		gbcE.gridy=2; gbcE.gridx=0; gbcE.gridwidth=0;
		ticketScreeningLabel=new JLabel("Suất chiếu: "); ticketScreeningLabel.setFont(new Font("Segoe UI",1,15));
		panelENorthBottom.add(ticketScreeningLabel, gbcE);
		gbcE.gridx=1;
		ticketScreeningText=new JLabel(); ticketScreeningText.setFont(new Font("Segoe UI",1,15));
		ticketScreeningText.setPreferredSize(new Dimension(300,25));
		ticketScreeningText.setForeground(Color.RED);
		panelENorthBottom.add(ticketScreeningText, gbcE);
		
		gbcE.gridx=0; gbcE.gridy=3; gbcE.gridwidth=1;
		ticketTimeLabel=new JLabel("Thời gian: "); ticketTimeLabel.setFont(new Font("Segoe UI",1,15));
		panelENorthBottom.add(ticketTimeLabel, gbcE);
		gbcE.gridx=1;
		ticketTimeText=new JLabel(); ticketTimeText.setFont(new Font("Segoe UI",1,15));
		ticketTimeText.setForeground(Color.RED);
		panelENorthBottom.add(ticketTimeText, gbcE);
		
		gbcE.gridy=4; gbcE.gridx=0; gbcE.gridwidth=1;
		ticketRoomLabel=new JLabel("Phòng: "); ticketRoomLabel.setFont(new Font("Segoe UI",1,15));
		panelENorthBottom.add(ticketRoomLabel, gbcE);
		gbcE.gridx=1;
		ticketRoomText=new JLabel(); ticketRoomText.setFont(new Font("Segoe UI",1,15));
		ticketRoomText.setForeground(Color.RED);
		panelENorthBottom.add(ticketRoomText, gbcE);
		
		gbcE.gridx=2;
		ticketSitLabel=new JLabel("Ghế: "); ticketSitLabel.setFont(new Font("Segoe UI",1,15));
		panelENorthBottom.add(ticketSitLabel, gbcE);
		gbcE.gridx=3;
		ticketSitText=new JLabel(); ticketSitText.setFont(new Font("Segoe UI",1,15));
		ticketSitText.setForeground(Color.RED);
		panelENorthBottom.add(ticketSitText, gbcE);
		
		gbcE.gridy=5; gbcE.gridx=0; gbcE.gridwidth=2;
		ticketTotalLabel=new JLabel("Tổng tiền:"); ticketTotalLabel.setFont(new Font("Segoe UI",1,15));
		panelENorthBottom.add(ticketTotalLabel, gbcE);
		gbcE.gridy=5; gbcE.gridx=2; gbcE.gridwidth=GridBagConstraints.REMAINDER;
		ticketTotalText=new JLabel();
		ticketTotalText.setFont(new Font("Segoe UI",1,15));
		ticketTotalText.setForeground(Color.RED);
		panelENorthBottom.add(ticketTotalText, gbcE);
		
		panelENorth.add(panelENorthBottom, BorderLayout.SOUTH);
		
//		Poster
		panelECenter=new JPanel();
		panelECenter.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(0,191,165)), "Poster", TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI",1,14)));
		panelECenter.setPreferredSize(new Dimension(getWidth(),200));
		
//		Thanh toán
		panelESouth=new JPanel(); panelESouth.setLayout(new BorderLayout());
		panelESouth.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(0,191,165)), "Thanh Toán", TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI",1,14)));
		
		panelESouthTop=new JPanel(); panelESouthTop.setLayout(new FlowLayout(FlowLayout.CENTER));
		paymentTitleLabel=new JLabel("THANH TOÁN"); paymentTitleLabel.setFont(new Font("Segoe UI",1,20));
		panelESouthTop.add(paymentTitleLabel);
		panelESouth.add(panelESouthTop,BorderLayout.NORTH);
		
		panelESouthBottom=new JPanel(); panelESouthBottom.setLayout(new GridBagLayout());
		GridBagConstraints gbcS=new GridBagConstraints();
		
		gbcS.insets=new Insets(5, 3, 5, 0);
		gbcS.anchor=GridBagConstraints.WEST;
		gbcS.gridx=0; gbcS.gridy=0;
		findTelText=new JTextField();
		findTelText.setText("Nhập sđt khách hàng");
		findTelText.setPreferredSize(new Dimension(150,25)); findTelText.setFont(new Font("Segoe UI",1,13));
		panelESouthBottom.add(findTelText,gbcS);
		
		gbcS.gridx=1; gbcS.gridwidth=GridBagConstraints.REMAINDER;
		btnFindTel=new JButton("Tìm"); btnFindTel.setFont(new Font("Segoe UI",1,15));
		btnFindTel.setPreferredSize(new Dimension(80,25));
		btnFindTel.setBackground(new Color(0,102,102));
		btnFindTel.setForeground(Color.WHITE);
		panelESouthBottom.add(btnFindTel,gbcS);
		
		gbcS.gridx=0; gbcS.gridy=1; gbcS.gridwidth=1;
		nameText=new JTextField();
		nameText.setPreferredSize(new Dimension(150,25)); nameText.setFont(new Font("Segoe UI",1,13));
		nameText.setText("Tên khách hàng");
		panelESouthBottom.add(nameText,gbcS);
		
		gbcS.gridx=1; gbcS.gridy=1;
		telText=new JTextField();
		telText.setPreferredSize(new Dimension(150,25)); telText.setFont(new Font("Segoe UI",1,13));
		telText.setText("SĐT khách hàng");
		panelESouthBottom.add(telText,gbcS);
		
		gbcS.gridx=2; gbcS.gridy=1;
		String[] genders= {"Nam", "Nữ"};
		genderText=new JComboBox(genders);
		genderText.setPreferredSize(new Dimension(150,25)); genderText.setFont(new Font("Segoe UI",1,13));
		panelESouthBottom.add(genderText,gbcS);
		
		gbcS.gridy=2; gbcS.gridx=0;
		paymentTotalLabel=new JLabel("Tổng tiền:"); paymentTotalLabel.setFont(new Font("Segoe UI",1,15));
		panelESouthBottom.add(paymentTotalLabel,gbcS);
		gbcS.gridx=1;
		paymentTotalText=new JLabel(); paymentTotalText.setFont(new Font("Segoe UI",1,15));
		paymentTotalText.setForeground(Color.RED);
		panelESouthBottom.add(paymentTotalText,gbcS);
		
		gbcS.gridy=3; gbcS.gridx=0;
		paymentCusLabel=new JLabel("Tiền khách đưa: "); paymentCusLabel.setFont(new Font("Segoe UI",1,15));
		panelESouthBottom.add(paymentCusLabel,gbcS);
		gbcS.gridy=3; gbcS.gridx=1;
		paymentCusText=new JTextField(); paymentCusText.setFont(new Font("Segoe UI",1,13));
		paymentCusText.setPreferredSize(new Dimension(150,25));
		panelESouthBottom.add(paymentCusText,gbcS);
		
		gbcS.gridy=4; gbcS.gridx=0;
		payCusLabel=new JLabel("Tiền thừa: "); payCusLabel.setFont(new Font("Segoe UI",1,15));
		panelESouthBottom.add(payCusLabel,gbcS);
		gbcS.gridx=1;
		payCusText=new JLabel(); payCusText.setFont(new Font("Segoe UI",1,15));
		payCusText.setForeground(Color.RED);
		panelESouthBottom.add(payCusText,gbcS);
		
		gbcS.gridy=5; gbcS.gridx=0;
		btnResetPayment=new JButton("Hủy"); btnResetPayment.setFont(new Font("Segoe UI",1,15));
		btnResetPayment.setBackground(Color.RED);
		btnResetPayment.setForeground(Color.WHITE);
		panelESouthBottom.add(btnResetPayment,gbcS);
		gbcS.gridx=2;
		btnPayment=new JButton("Thanh toán"); btnPayment.setFont(new Font("Segoe UI",1,15));
		btnPayment.setBackground(Color.GREEN);
		btnPayment.setForeground(Color.WHITE);
		panelESouthBottom.add(btnPayment,gbcS);
	
		panelESouth.add(panelESouthBottom, BorderLayout.SOUTH);
		
		panelEast.add(panelECenter, BorderLayout.NORTH);
		panelEast.add(panelENorth, BorderLayout.CENTER);
		panelEast.add(panelESouth, BorderLayout.SOUTH);
		
		add(panelWest, BorderLayout.WEST);
		add(panelEast, BorderLayout.EAST);
	}
	
//	Change
//	Gán danh sách từ sql
	public void loadData() {
		movieDAO.setList(movieDAO.getAllMovie());
		directorDAO.setList(directorDAO.getAllDirector());
		screeningDAO.setList(screeningDAO.getAllScreening());
		roomDAO.setList(roomDAO.getAllRoom());
		customerDAO.setList(customerDAO.getAllCustomer());
		employeeDAO.setListEmp(employeeDAO.getAllEmp());
		ticketDAO.setList(ticketDAO.getAllTicket());
		
		Set<String> typeSet=new HashSet<String>();
		for(Movie i:movieDAO.getAllMovie()) {
			typeSet.add(i.getType());
		}
		for(String i:typeSet) {
			typeText.addItem(i);
		}
		
		int idAuto=1;
		Collections.sort(ticketDAO.getList(),new Comparator<Ticket>() {
			@Override
			public int compare(Ticket o1, Ticket o2) {
				// TODO Auto-generated method stub
				return Integer.parseInt(o1.getIdTicket().substring(1)) - Integer.parseInt(o2.getIdTicket().substring(1));
			}
			
		});
		for(Ticket t: ticketDAO.getList()) {
			if(Integer.parseInt(t.getIdTicket().substring(1))==idAuto) {
				idAuto++;
				Ticket.setIdAuto(idAuto);
			}
		}
		ticketIDText.setText("V"+Ticket.getIdAuto());
		
		int idAutoCus=1;
		customerDAO.sortByIdCusASC();
		for(Customer c: customerDAO.getList()) {
			if(Integer.parseInt(c.getIdCustomer().substring(2))==idAutoCus) {
				idAutoCus++;
				Customer.setIdAuto(idAutoCus);
			}
		}
	}
	
//	Đưa dữ liệu lên bảng
	public void loadDataTable() {
		modelMovie.setRowCount(0);
		DateTimeFormatter format=DateTimeFormatter.ofPattern("dd-MM-yyyy");
		for(Movie i: movieDAO.getList()) {
			Object[] row=new Object[] {
				i.getIdMovie(), i.getName(), directorDAO.getDirectorById(i.getDirector().getIdDirector()).getName(),
				i.getType(), format.format(i.getDateOfDebut()), i.getTime()+" phút"
			};
			modelMovie.addRow(row);
		}
	}
	
//	Hủy lựa chọn tìm kiếm phim
	public void resetMovie() {
		movieText.setText("");
		typeText.setSelectedIndex(0);
		directorText.setText("");
		movieDAO.setList(movieDAO.getAllMovie());
		loadDataTable();
		date.getComponent(1).setEnabled(false);
		ticketMovieText.setText("");
		panelECenter.removeAll();
		panelECenter.revalidate();
		panelECenter.repaint();
	}
	
//	Tìm kiếm phim
	public void searchMovie() {
		String nameMovie=movieText.getText();
		String typeMovie=typeText.getSelectedItem().toString();
		String directorMovie=directorText.getText();
		
		if(!nameMovie.isEmpty() && directorMovie.isEmpty()) {
			movieDAO.getList().clear();
			movieDAO.setList(movieDAO.getAllMovieByNameAndType(nameMovie, typeMovie));
			loadDataTable();
			JOptionPane.showMessageDialog(rootPane, "Tìm thấy "+movieDAO.getList().size()+" kết quả");
		}
		else if(nameMovie.isEmpty() && !directorMovie.isEmpty()) {
			movieDAO.getList().clear();
			movieDAO.setList(movieDAO.getAllMovieByDirectorAndType(directorMovie, typeMovie));
			loadDataTable();
			JOptionPane.showMessageDialog(rootPane, "Tìm thấy "+movieDAO.getList().size()+" kết quả");
		}
		else if(!nameMovie.isEmpty() && !directorMovie.isEmpty()) {
			movieDAO.getList().clear();
			movieDAO.setList(movieDAO.getAllMovieByNameAndDirectorAndType(nameMovie, directorMovie, typeMovie));
			loadDataTable();
			JOptionPane.showMessageDialog(rootPane, "Tìm thấy "+movieDAO.getList().size()+" kết quả");
		}
		else {
			movieDAO.getList().clear();
			movieDAO.setList(movieDAO.getAllMovieByType(typeMovie));
			loadDataTable();
			JOptionPane.showMessageDialog(rootPane, "Tìm thấy "+movieDAO.getList().size()+" kết quả");
		}
		movieText.setText("");
		typeText.setSelectedIndex(0);
		directorText.setText("");
	}
	
//	Hiển thị thời gian suất chiếu
	public void displayScreeningTime() {
		if(panelAllTime.getComponents()!=null) {
			panelAllTime.removeAll();
			panelAllTime.revalidate(); panelAllTime.repaint();
		}
		currentScreeningMovieLabel=new HashMap<Component, String>();
		JPanel panel=new JPanel();
		panel.setPreferredSize(new Dimension(420,90));
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 3));
		panel.setBackground(new Color(223, 223, 223));
		
		DateTimeFormatter format=DateTimeFormatter.ofPattern("HH:mm");
		var dmy=doneDateText.getText().split("/");
		int day=Integer.parseInt(dmy[0]);
		int month=Integer.parseInt(dmy[1]);
		int year=Integer.parseInt(dmy[2]);
		
		int index=1;
		
		for(Screening i: currentScreeningMovie) {
			LocalDate dateOfScreening=i.getTimeStart().toLocalDate();
			LocalDate dateChoose=LocalDate.of(year, month, day);
			var time=format.format(i.getTimeStart().toLocalTime());
			if(dateOfScreening.compareTo(dateChoose)==0) {
				JLabel label=new JLabel(new ImageIcon(getClass().getResource("/image/screening.png")));
				label.setLayout(null);
				JLabel text=new JLabel(); text.setText(time); text.setBounds(10, 15, 50, 20);
				label.add(text);  label.setName(String.valueOf(index++));
				currentScreeningMovieLabel.put(label,i.getRoom().getIdRoom());
				panel.add(label);
			}
		}
		
		panelAllTime.add(panel);
		panelAllTime.revalidate(); panelAllTime.repaint();
		
		for(Map.Entry<Component, String> c: currentScreeningMovieLabel.entrySet()) {
			((JLabel)c.getKey()).addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					changeColorTicket(((JLabel)c.getKey()),currentScreeningMovieLabel);
				}
			});
		}
	}
	
//	Thay đổi trạng thái suất chiếu
	public void changeColorTicket(JLabel label, Map<Component, String> components) {
		for(Map.Entry<Component, String> c: components.entrySet()) {
			if(c.getKey().getName().equalsIgnoreCase(label.getName())) {
				((JLabel) c.getKey()).setIcon(new ImageIcon(getClass().getResource("/image/screeningRed.png")));
				Component component=((JLabel)c.getKey()).getComponents()[0];
				String labelInner=((JLabel)component).getText();
				doneTimeText.setText(labelInner);
				
//				Movie movie=movieDAO.getMovieByName(ticketMovieText.getText());
//				var dmy=doneDateText.getText().split("/");
//				int day=Integer.parseInt(dmy[0]);
//				int month=Integer.parseInt(dmy[1]);
//				int year=Integer.parseInt(dmy[2]);
//				LocalDate date=LocalDate.of(year, month, day);
//				LocalTime time=LocalTime.parse(doneTimeText.getText(), DateTimeFormatter.ofPattern("HH:mm"));
				
				ticketRoomText.setText(String.valueOf(roomDAO.getRoomById(c.getValue()).getNumberRoom()));
			}
			else {
				((JLabel) c.getKey()).setIcon(new ImageIcon(getClass().getResource("/image/screening.png")));
			}
		}
	}
	
//	Hủy chọn thời gian
	public void resetBookTicket() {
		doneDateText.setText(""); doneTimeText.setText("");
		ticketRoomText.setText("");
		
		if(currentScreeningMovieLabel!=null && currentScreeningMovieLabel.size() > 0) {
			panelAllTime.removeAll();
			panelAllTime.revalidate(); panelAllTime.repaint();
		}
	}
	
//	Trạng thái text chuột không nằm trong ô
	public void addPlaceHolder(JTextField text) {
		Font font=text.getFont();
		font=font.deriveFont(Font.ITALIC);
		text.setFont(font);
		text.setForeground(Color.GRAY);
	}
	
//	Xóa trạng thái text chuột không nằm trong ô
	public void removePlaceHolder(JTextField text) {
		Font font=text.getFont();
		font=font.deriveFont(Font.BOLD);
		text.setFont(font);
		text.setForeground(Color.BLACK);
	}
	
//	Lấy danh sách ghế và tổng tiền
	public void callBackSitInfor(ArrayList<String> chairTexts, JLabel totalText) {
		this.chairTexts=chairTexts;
		this.totalText=totalText;
	}
	
//	Tìm khách hàng qua số điện thoại
	public void searchTel() {
		String phone=findTelText.getText();
		if(!phone.equals("Nhập sđt khách hàng")) {
			var check=new FilterImp();
			try {
				if(check.checkPhone(phone)) {
					Customer customer=customerDAO.getCustomerByPhone(phone);
					if(customer!=null) {
						removePlaceHolder(nameText); removePlaceHolder(telText);
						nameText.setText(customer.getName());
						telText.setText(customer.getPhone());
						String gender=(customer.getGender()==1?"Nam":"Nữ");
						for(int i=0;i<genderText.getItemCount();i++) {
							if(genderText.getItemAt(i).equals(gender)) {
								genderText.setSelectedIndex(i);
								break;
							}
						}
					}
					else {
						JOptionPane.showMessageDialog(rootPane, "Khách hàng lần đầu mua vé");
						addPlaceHolder(nameText); nameText.setText("Tên khách hàng");
						addPlaceHolder(telText); telText.setText("SĐT khách hàng");
					}
					addPlaceHolder(findTelText);
					findTelText.setText("Nhập sđt khách hàng");
				}
			} catch (checkPhone e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(rootPane, e.getMessage());
			}
		}
		else {
			JOptionPane.showMessageDialog(rootPane, "Nhập số điện thoại để tìm kiếm");
		}
	}
	
//	Thanh toán 
	public void payment() {
		String idTicket=ticketIDText.getText();
		String nameMovie=ticketMovieText.getText();
		String screeningMovie=ticketScreeningText.getText();
		String timeMovie=ticketTimeText.getText();
		String numberRoom=ticketRoomText.getText();
		String[] sittings=ticketSitText.getText().split(", ");
		String total=ticketTotalText.getText();
		
		if(idTicket.isEmpty() || nameMovie.isEmpty() || screeningMovie.isEmpty() || timeMovie.isEmpty()
			|| numberRoom.isEmpty() || ticketSitText.getText().isEmpty() || total.isEmpty()) {
			JOptionPane.showMessageDialog(rootPane, "Vui lòng tạo vé phim để thanh toán");
		}
		else {
			DateTimeFormatter format=DateTimeFormatter.ofPattern("HH:mm");
			var dmy=ticketScreeningText.getText().split("/");
			int day=Integer.parseInt(dmy[0]);
			int month=Integer.parseInt(dmy[1]);
			int year=Integer.parseInt(dmy[2]);
			
			String nameCus=nameText.getText();
			String telCus=telText.getText();
			String gender=genderText.getSelectedItem().toString();
			String paymentCus=paymentCusText.getText();
			
			if(nameCus.equals("Tên khách hàng") || telCus.equals("SĐT khách hàng") || paymentCus.isEmpty()) {
				JOptionPane.showMessageDialog(rootPane, "Nhập đầy đủ thông tin để thanh toán");
			}
			else {
				var check=new FilterImp();
				try {
					if(check.checkName(nameCus) && check.checkPhone(telCus) && check.checkNumber(paymentCus)) {
						Customer res= customerDAO.getCustomerByPhone(telCus);
						Customer cus=null;
						if(res!=null) {
							cus=res;
						}
						else {
							cus=new Customer("KH"+Customer.getIdAuto(), nameCus, null, telCus, gender=="Nam"?1:0);
							Customer.setIdAuto(Customer.getIdAuto()+1);
							for(Customer c: customerDAO.getList()) {
								if(c.getIdCustomer().equalsIgnoreCase(cus.getIdCustomer())) {
									cus.setIdCustomer("KH"+Customer.getIdAuto());
									Customer.setIdAuto(Customer.getIdAuto()+1);
								}
							}
						}
						NhanVien emp=employeeDAO.getEmpByuserName(userName);
						Screening screening=screeningDAO.getScreening(nameMovie, Integer.parseInt(numberRoom), 
								LocalDateTime.of(LocalDate.of(year, month, day), LocalTime.parse( timeMovie, format)));
						
						ArrayList<Sitting> listSitting=sittingDAO.getAllSittingByScreening(screening.getIdScreening());
						ArrayList<Sitting> chooseSitting=new ArrayList<Sitting>();
						for(Sitting i: listSitting) {
							for(String j: sittings) {
								if(i.getNumberSitting().equals(j)) {
									chooseSitting.add(i);
								}
							}
						}
						
						new PaymentBillDialog(this, rootPaneCheckingEnabled, cus, emp, screening, chooseSitting, ticketIDText).setVisible(true);
					}
				} catch (checkName | checkPhone | checkNumber e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(rootPane, e.getMessage());
				}
			}
		}
	}
	
//	Trở lại ban đầu khi thanh toán thành công
	public void resetInfor() {
		resetPayment();
		paymentTotalText.setText("");
		ticketIDText.setText("V"+Ticket.getIdAuto());
		ticketMovieText.setText(""); 
		ticketScreeningText.setText(""); ticketTimeText.setText("");
		ticketRoomText.setText(""); ticketSitText.setText("");
		ticketTotalText.setText("");
	}
	
//	Hủy thông tin khách hàng
	public void resetPayment() {
		addPlaceHolder(findTelText); findTelText.setText("Nhập sđt khách hàng");
		addPlaceHolder(nameText); nameText.setText("Tên khách hàng");
		addPlaceHolder(telText); telText.setText("SĐT khách hàng");
		genderText.setSelectedIndex(0);
		paymentCusText.setText("");
		payCusText.setText("");
	}
	
//	Thay đổi tiền thừa
	public void changePayCusText() {
		DecimalFormat format=new DecimalFormat("#,##0"+" VNĐ");
		var check=new FilterImp();
		try {
			if(check.checkNumber(paymentCusText.getText())) {
				if(paymentTotalText!=null && !paymentTotalText.getText().isEmpty()) {
					var paymentTotal=paymentTotalText.getText().split(" VNĐ")[0].split(",")[0] + paymentTotalText.getText().split(" VNĐ")[0].split(",")[1];
					var result=Double.parseDouble(paymentCusText.getText()) - Double.parseDouble(paymentTotal);
					payCusText.setText(String.valueOf(format.format(result)));
				}
				else {
					payCusText.setText(String.valueOf(format.format(Double.parseDouble(paymentCusText.getText()))));
				}			
			}
		} catch (checkNumber e) {
			// TODO Auto-generated catch block
			payCusText.setText("Vui lòng nhập số tiền");
		}
	}
	
//	Listener
	public void addFocusListener() {
		findTelText.addFocusListener(this);
		nameText.addFocusListener(this);
		telText.addFocusListener(this);
		
		addPlaceHolder(findTelText);
		addPlaceHolder(nameText);
		addPlaceHolder(telText);
	}
	
	@Override
	public void focusGained(FocusEvent e) {
		var obj=e.getSource();
		if(obj.equals(findTelText)) {
			if(findTelText.getText().equals("Nhập sđt khách hàng")) {
				findTelText.setText(null);
				findTelText.requestFocus();
				
				removePlaceHolder(findTelText);
			}
		}
		else if(obj.equals(nameText)) {
			if(nameText.getText().equals("Tên khách hàng")) {
				nameText.setText(null);
				nameText.requestFocus();
				
				removePlaceHolder(nameText);
			}
		}
		else if(obj.equals(telText)) {
			if(telText.getText().equals("SĐT khách hàng")) {
				telText.setText(null);
				telText.requestFocus();
				
				removePlaceHolder(telText);
			}
		}
	}
	
	@Override
	public void focusLost(FocusEvent e) {
		var obj=e.getSource();
		if(obj.equals(findTelText)) {
			if(findTelText.getText().length()==0) {
				addPlaceHolder(findTelText);
				findTelText.setText("Nhập sđt khách hàng");
			}
			
		}
		else if(obj.equals(nameText)){
			if(nameText.getText().length()==0) {
				addPlaceHolder(nameText);
				nameText.setText("Tên khách hàng");
			}
		}
		else if(obj.equals(telText)) {
			if(telText.getText().length()==0) {
				addPlaceHolder(telText);
				telText.setText("SĐT khách hàng");
			}
		}
	}
	
	public void addActionListener() {
//		menu nhân viên
		itemListEmployee.addActionListener(this);
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
		btnResetPayment.addActionListener(this);
		btnPayment.addActionListener(this);
		btnResetMovie.addActionListener(this);
		btnFindMovie.addActionListener(this);
		btnFindTel.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		var obj=e.getSource();
		if(obj.equals(itemListEmployee)) {
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
		else if(obj.equals(btnFindMovie)) {
			searchMovie();
		}
		else if(obj.equals(btnResetMovie)) {
			resetMovie();
		}
		else if(obj.equals(btnFindTel)) {
			searchTel();
		}
		else if(obj.equals(btnPayment)) {
			payment();
		}
		else if(obj.equals(btnResetPayment)) {
			resetPayment();
		}
	}

	private boolean checkListener=true;
	public void addMouseListener() {
		SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy");
		modelDate.addChangeListener(e->{
			if(checkListener) {
				String dateSelected=formatter.format(date.getModel().getValue());
				doneDateText.setText(dateSelected);
				displayScreeningTime();
				checkListener=false;
			}
			else {
				checkListener=true;
			}
		});
		
		resetLabel.addMouseListener(this);
		bookSitLabel.addMouseListener(this);
		tableMovie.addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		SimpleDateFormat format=new SimpleDateFormat("dd-MM-yyyy");
		int index=tableMovie.getSelectedRow();
		if(index!=-1) {
			date.getComponent(1).setEnabled(true);
			String idMovie=modelMovie.getValueAt(index, 0).toString();
			String poster=movieDAO.getMovieByID(idMovie).getPoster();
			if(getClass().getResource("/image/poster/"+poster+".png")!=null) {
				if(panelECenter.getComponents()!=null) {
					panelECenter.removeAll();
					panelECenter.revalidate();
					panelECenter.repaint();
				}
				ImageIcon imageIcon=new ImageIcon(getClass().getResource("/image/poster/"+poster+".png"));
				Image image=imageIcon.getImage().getScaledInstance(440, 160, Image.SCALE_SMOOTH);
				JLabel posterLabel=new JLabel(); posterLabel.setIcon(new ImageIcon(image));
				panelECenter.add(posterLabel);
				panelECenter.revalidate();
				panelECenter.repaint();	
			}
			else {
				panelECenter.removeAll();
				panelECenter.revalidate();
				panelECenter.repaint();
			}
			ticketMovieText.setText(modelMovie.getValueAt(index, 1).toString());
			currentScreeningMovie= screeningDAO.getAllScreeningSearchByName(modelMovie.getValueAt(index, 1).toString());
		}
		
//		other
		var obj=e.getSource();
		if(obj.equals(resetLabel)) {
			resetBookTicket();
		}
		else if(obj.equals(bookSitLabel)) {
			if(index!=-1) {
				if(doneDateText.getText().equals("") || doneTimeText.getText().equals("")) {
					JOptionPane.showMessageDialog(rootPane, "Vui lòng chọn thời gian");	
				}
				else {
					var dmy=doneDateText.getText().split("/");
					int day=Integer.parseInt(dmy[0]);
					int month=Integer.parseInt(dmy[1]);
					int year=Integer.parseInt(dmy[2]);
					LocalDateTime chooseDateTime=LocalDateTime.of(LocalDate.of(year, month, day), 
							LocalTime.parse(doneTimeText.getText(), DateTimeFormatter.ofPattern("HH:mm")));
					Screening currentScreening=screeningDAO.getScreening(ticketMovieText.getText(), 
							Integer.parseInt(ticketRoomText.getText()), chooseDateTime);
					
					new BookSitDialog(this, rootPaneCheckingEnabled, currentScreening).setVisible(true);
					if(totalText!=null && chairTexts!=null) {
						var row="";
						for(int i=0;i<chairTexts.size();i++) {
							if(i==chairTexts.size()-1) {
								row+=chairTexts.get(i)+"";							
							}
							else {
								row+=chairTexts.get(i)+", ";
							}
						}
						ticketSitText.setText(row);
						ticketTotalText.setText(totalText.getText());
						ticketScreeningText.setText(doneDateText.getText());
						ticketTimeText.setText(doneTimeText.getText());
						paymentTotalText.setText(totalText.getText());
					}
					doneDateText.setText(""); doneTimeText.setText("");
					if(currentScreeningMovieLabel!=null && currentScreeningMovieLabel.size() > 0) {
						panelAllTime.removeAll();
						panelAllTime.revalidate(); panelAllTime.repaint();
					}
					movieText.setText("");
					typeText.setSelectedIndex(0);
					directorText.setText("");
					movieDAO.setList(movieDAO.getAllMovie());
					loadDataTable();
					date.getComponent(1).setEnabled(false);
					panelECenter.removeAll();
					panelECenter.revalidate();
					panelECenter.repaint();	
				}
			}
			else {
				JOptionPane.showMessageDialog(rootPane, "Vui lòng chọn phim");
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	public void addDocumentListener() {
		paymentCusText.getDocument().addDocumentListener(this);
	}
	
	@Override
	public void insertUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		changePayCusText();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		changePayCusText();
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		changePayCusText();
	}

	
}
