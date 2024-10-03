package view;

import java.awt.BorderLayout;
import java.awt.Color;
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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import controller.Database;
import controller.ExcelHelper;
import controller.FilterImp;
import controller.LabelDateFormatter;
import dao.Movie_DAO;
import dao.Room_DAO;
import dao.Screening_DAO;
import entity.Movie;
import entity.Room;
import entity.Screening;
import exception.checkBirthday;
import exception.checkName;
import exception.checkTimeMovie;

public class ScreeningMovieFrame extends JFrame implements ActionListener, MouseListener, FocusListener{
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
	
//	Component danh sách đạo diễn
	JPanel screeningPanel, sortPanelScreening, searchPanelScreening, inforPanelScreening, northPanelScreening, southPanelDirector;
	JRadioButton radioSortByNameASCScreening, radioSortByNameDESCScreening, radioSortByIdASCScreening, radioSortByIdDESCScreening,
				radioSortByTimeASCScreening, radioSortByTimeDESCScreening, radioSortByRoomASCScreening, radioSortByRoomDESCScreening,
				radioSearchByNameMovie, radioSearchByRoom, radioSearchByTime;
	JTextField nameTextMovie, idTextScreening, timeTextScreening, roomTextScreening 
		,textSearchByNameMovie, textSearchByRoom, textSearchByTimeFrom, textSearchByTimeTo;
	JLabel nameLabelMovie, idLabelScreening, dateLabelScreening, timeLabelScreening,  durationLabelScreening, durationTextScreening,
		typeMovieLabel, typeMovieText, roomLabelScreening,
		labelSearchByTimeFrom, labelSearchByTimeTo;
	JDatePickerImpl dateTextScreening;
	UtilDateModel dateModelScreening;
	JButton btnSearchScreening, btnResetScreening, btnAddScreening, btnEditScreening, btnDeleteScreening, btnSaveScreening;
	ButtonGroup btnGroupSortScreening, btnGroupSearchScreening;
	JTable tableScreening;
	DefaultTableModel modelTableScreening;
	JScrollPane scrollScreening;
	
	private Screening_DAO screeningDAO;
	private Room_DAO roomDAO;
	private Movie_DAO movieDAO;
	
	public ScreeningMovieFrame(String userName) {
		setTitle("Danh sách suất chiếu");
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
		addMouseListener();
		addFocusListener();
		
		Database.getInstance().connect();
		screeningDAO=new Screening_DAO();
		movieDAO=new Movie_DAO();
		roomDAO=new Room_DAO();
		
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
		screeningPanel=new JPanel(); 
		screeningPanel.setLayout(new BorderLayout(5,5));
		
//		Panel sắp xếp suất chiếu
		sortPanelScreening=new JPanel();
		sortPanelScreening.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(0,191,165)), "Sắp xếp danh sách suất chiếu", TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI",1,14)));
		sortPanelScreening.setLayout(new GridLayout(4, 2));
		
		radioSortByNameASCScreening=new JRadioButton("Theo tên phim tăng dần"); radioSortByNameASCScreening.setFont(new Font("Segoe UI",1,14));
		radioSortByNameDESCScreening=new JRadioButton("Theo tên phim giảm dần"); radioSortByNameDESCScreening.setFont(new Font("Segoe UI",1,14));
		radioSortByIdASCScreening=new JRadioButton("Theo mã tăng dần"); radioSortByIdASCScreening.setFont(new Font("Segoe UI",1,14));
		radioSortByIdDESCScreening=new JRadioButton("Theo mã giảm dần"); radioSortByIdDESCScreening.setFont(new Font("Segoe UI",1,14));
		radioSortByTimeASCScreening=new JRadioButton("Theo thời gian tăng dần"); radioSortByTimeASCScreening.setFont(new Font("Segoe UI",1,14));
		radioSortByTimeDESCScreening=new JRadioButton("Theo thời gian giảm dần"); radioSortByTimeDESCScreening.setFont(new Font("Segoe UI",1,14));
		radioSortByRoomASCScreening=new JRadioButton("Theo số phòng tăng dần"); radioSortByRoomASCScreening.setFont(new Font("Segoe UI",1,14));
		radioSortByRoomDESCScreening=new JRadioButton("Theo số phòng giảm dần"); radioSortByRoomDESCScreening.setFont(new Font("Segoe UI",1,14));
		
		sortPanelScreening.add(radioSortByNameASCScreening);  sortPanelScreening.add(radioSortByNameDESCScreening);
		sortPanelScreening.add(radioSortByIdASCScreening);    sortPanelScreening.add(radioSortByIdDESCScreening);
		sortPanelScreening.add(radioSortByTimeASCScreening);  sortPanelScreening.add(radioSortByTimeDESCScreening);
		sortPanelScreening.add(radioSortByRoomASCScreening);  sortPanelScreening.add(radioSortByRoomDESCScreening);
		
		northPanelScreening=new JPanel();  northPanelScreening.setLayout(new BorderLayout());
		northPanelScreening.add(sortPanelScreening, BorderLayout.WEST);
		
//		Panel thông tin suất chiếu
		inforPanelScreening=new JPanel(); inforPanelScreening.setLayout(new GridBagLayout());
		GridBagConstraints gbc=new GridBagConstraints();
		inforPanelScreening.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(0,191,165)), "Thông tin suất chiếu", TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI",1,14)));
		
		gbc.gridx=0; gbc.gridy=0; gbc.insets=new Insets(5, 3, 5, 5); gbc.anchor=GridBagConstraints.WEST;
		idLabelScreening=new JLabel("Mã suất chiếu:"); idLabelScreening.setFont(new Font("Segoe UI",1,14));
		inforPanelScreening.add(idLabelScreening, gbc);
		gbc.gridx=1;
		idTextScreening=new JTextField(19); idTextScreening.setFont(new Font("Segoe UI",0,13));
		idTextScreening.setEditable(false);
		inforPanelScreening.add(idTextScreening, gbc);
		
		gbc.gridx=2;
		dateLabelScreening=new JLabel("Ngày:"); dateLabelScreening.setFont(new Font("Segoe UI",1,14));
		inforPanelScreening.add(dateLabelScreening, gbc);
		gbc.gridx=3;
		dateModelScreening=new UtilDateModel();
		Properties p=new Properties();
		p.put("text.day", "Day"); p.put("text.month", "Month"); p.put("text.year", "Year");
		JDatePanelImpl panelDate=new JDatePanelImpl(dateModelScreening, p);
		dateTextScreening=new JDatePickerImpl(panelDate, new LabelDateFormatter());
		dateTextScreening.setPreferredSize(new Dimension(120,25));
		inforPanelScreening.add(dateTextScreening,gbc);
		
		gbc.gridx=4;
		timeLabelScreening=new JLabel("Thời gian:"); timeLabelScreening.setFont(new Font("Segoe UI",1,14));
		inforPanelScreening.add(timeLabelScreening,gbc);
		gbc.gridx=5;
		timeTextScreening=new JTextField(5); timeTextScreening.setFont(new Font("Segoe UI",0,13));
		inforPanelScreening.add(timeTextScreening,gbc);
		
		gbc.gridx=0; gbc.gridy=1;
		nameLabelMovie=new JLabel("Tên phim:"); nameLabelMovie.setFont(new Font("Segoe UI",1,14));
		inforPanelScreening.add(nameLabelMovie,gbc);
		gbc.gridx=1;
		nameTextMovie=new JTextField(19); nameTextMovie.setFont(new Font("Segoe UI",0,13));
		inforPanelScreening.add(nameTextMovie, gbc);
		
		gbc.gridx=2;
		typeMovieLabel=new JLabel("Loại:"); typeMovieLabel.setFont(new Font("Segoe UI",1,14));
		inforPanelScreening.add(typeMovieLabel,gbc);
		gbc.gridx=3;
		typeMovieText=new JLabel(); typeMovieText.setFont(new Font("Segoe UI",0,13));
		typeMovieText.setPreferredSize(new Dimension(120,25));
		inforPanelScreening.add(typeMovieText,gbc);
		
		gbc.gridx=4;
		durationLabelScreening=new JLabel("Thời lượng:"); durationLabelScreening.setFont(new Font("Segoe UI",1,14));
		inforPanelScreening.add(durationLabelScreening,gbc);
		gbc.gridx=5;
		durationTextScreening=new JLabel(); durationTextScreening.setFont(new Font("Segoe UI",0,13));
		inforPanelScreening.add(durationTextScreening,gbc);
		
		gbc.gridx=0; gbc.gridy=2;
		roomLabelScreening=new JLabel("Phòng chiếu:"); roomLabelScreening.setFont(new Font("Segoe UI",1,14));
		inforPanelScreening.add(roomLabelScreening,gbc);
		gbc.gridx=1;
		roomTextScreening=new JTextField(5); roomTextScreening.setFont(new Font("Segoe UI",0,13));
		inforPanelScreening.add(roomTextScreening,gbc);
		
		northPanelScreening.add(inforPanelScreening, BorderLayout.CENTER);
//		Panel tìm kiếm suất chiếu
		searchPanelScreening=new JPanel();
		searchPanelScreening.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(0,191,165)), "Tìm suất chiếu", TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI",1,14)));
		searchPanelScreening.setPreferredSize(new Dimension(450,130));
		searchPanelScreening.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		radioSearchByNameMovie=new JRadioButton("Theo tên phim: "); radioSearchByNameMovie.setFont(new Font("Segoe UI",1,14));
		textSearchByNameMovie=new JTextField(25);textSearchByNameMovie.setFont(new Font("Segoe UI",0,13));
		textSearchByNameMovie.setEditable(false);
		
		radioSearchByTime=new JRadioButton("Theo thời gian:"); radioSearchByTime.setFont(new Font("Segoe UI",1,14));
		labelSearchByTimeFrom=new JLabel("Từ"); labelSearchByTimeFrom.setFont(new Font("Segoe UI",1,14));
		textSearchByTimeFrom=new JTextField(5); textSearchByTimeFrom.setFont(new Font("Segoe UI",0,13));
		labelSearchByTimeTo=new JLabel("Đến"); labelSearchByTimeTo.setFont(new Font("Segoe UI",1,14));
		textSearchByTimeTo=new JTextField(5); textSearchByTimeTo.setFont(new Font("Segoe UI",0,13));
		textSearchByTimeFrom.setEditable(false);; textSearchByTimeTo.setEditable(false);
		
		radioSearchByRoom=new JRadioButton("Theo số phòng:"); radioSearchByRoom.setFont(new Font("Segoe UI",1,14));
		textSearchByRoom=new JTextField(5);textSearchByRoom.setFont(new Font("Segoe UI",0,13));
		textSearchByRoom.setEditable(false);
		
		JPanel res=new JPanel(); res.setLayout(new FlowLayout(FlowLayout.RIGHT));
		res.setPreferredSize(new Dimension(220,35));
		btnSearchScreening=new JButton("Tìm"); btnSearchScreening.setForeground(Color.WHITE);
		btnSearchScreening.setFont(new Font("Segoe UI",1,14)); btnSearchScreening.setBackground(new Color(0, 102, 102));
		res.add(btnSearchScreening);
		
		searchPanelScreening.add(radioSearchByNameMovie); searchPanelScreening.add(textSearchByNameMovie);
		searchPanelScreening.add(radioSearchByTime);
		searchPanelScreening.add(labelSearchByTimeFrom); searchPanelScreening.add(textSearchByTimeFrom);
		searchPanelScreening.add(labelSearchByTimeTo); searchPanelScreening.add(textSearchByTimeTo);
		searchPanelScreening.add(radioSearchByRoom); searchPanelScreening.add(textSearchByRoom);
		searchPanelScreening.add(res);
		
		northPanelScreening.add(searchPanelScreening, BorderLayout.EAST);
		
		screeningPanel.add(northPanelScreening,BorderLayout.NORTH);
		
		
//		Table suất chiếu
		String[] colNameEmp= {"Mã suất chiếu","Ngày","Thời gian","Tên phim","Thời lượng","Loại phim","Phòng chiếu"};
		modelTableScreening= new DefaultTableModel(colNameEmp,0);
		tableScreening=new JTable(modelTableScreening);
		tableScreening.getTableHeader().setFont(new Font("Segoe UI",1,14));
		tableScreening.setFont(new Font("Segoe UI",1,13));
		scrollScreening=new JScrollPane(tableScreening);
		scrollScreening.setBorder(BorderFactory.createLineBorder(new Color(0,191,165)));
		
		screeningPanel.add(scrollScreening,BorderLayout.CENTER);
		
//		Các nút chức năng
		btnResetScreening=new JButton("Làm mới"); btnResetScreening.setFont(new Font("Segoe UI",1,14)); btnResetScreening.setPreferredSize(new Dimension(150, 30));
		btnAddScreening=new JButton("Thêm suất chiếu"); btnAddScreening.setFont(new Font("Segoe UI",1,14)); btnAddScreening.setPreferredSize(new Dimension(150, 30));
		btnEditScreening=new JButton("Sửa suất chiếu"); btnEditScreening.setFont(new Font("Segoe UI",1,14)); btnEditScreening.setPreferredSize(new Dimension(150, 30));
		btnDeleteScreening=new JButton("Xóa suất chiếu"); btnDeleteScreening.setFont(new Font("Segoe UI",1,14)); btnDeleteScreening.setPreferredSize(new Dimension(150, 30));
		btnSaveScreening=new JButton("Xuất danh sách"); btnSaveScreening.setFont(new Font("Segoe UI",1,14)); btnSaveScreening.setPreferredSize(new Dimension(150, 30));
		
		southPanelDirector=new JPanel();
		southPanelDirector.add(btnResetScreening); southPanelDirector.add(btnAddScreening);
		southPanelDirector.add(btnEditScreening); southPanelDirector.add(btnDeleteScreening); southPanelDirector.add(btnSaveScreening);

		screeningPanel.add(southPanelDirector,BorderLayout.SOUTH);
		
		add(screeningPanel);
	}

//	Change
	public void addButtonGroup() {
		btnGroupSortScreening=new ButtonGroup();
		btnGroupSearchScreening=new ButtonGroup();
		
		btnGroupSortScreening.add(radioSortByNameASCScreening);
		btnGroupSortScreening.add(radioSortByNameDESCScreening);
		btnGroupSortScreening.add(radioSortByIdASCScreening);
		btnGroupSortScreening.add(radioSortByIdDESCScreening);
		btnGroupSortScreening.add(radioSortByTimeASCScreening);
		btnGroupSortScreening.add(radioSortByTimeDESCScreening);
		btnGroupSortScreening.add(radioSortByRoomASCScreening);
		btnGroupSortScreening.add(radioSortByRoomDESCScreening);
		
		btnGroupSearchScreening.add(radioSearchByNameMovie);
		btnGroupSearchScreening.add(radioSearchByTime);
		btnGroupSearchScreening.add(radioSearchByRoom);
	}
	
//	Gán danh sách từ sql
	public void loadData() {
		movieDAO.setList(movieDAO.getAllMovie());
		screeningDAO.setList(screeningDAO.getAllScreening());
		roomDAO.setList(roomDAO.getAllRoom());
		
		screeningDAO.sortByIdASC();
		int idAuto=1;
		for(Screening s: screeningDAO.getList()) {
			if(Integer.parseInt(s.getIdScreening().substring(2))==idAuto) {
				idAuto++;
				Screening.setIdAuto(idAuto);
			}
		}
	}
	
//	Đưa dữ liệu lên bảng
	public void loadDataTable() {
		modelTableScreening.setRowCount(0);
		DateTimeFormatter format=DateTimeFormatter.ofPattern("dd-MM-yyyy");
		DateTimeFormatter formatTime=DateTimeFormatter.ofPattern("HH:mm");
		for(Screening i: screeningDAO.getList()) {
			String date=format.format(i.getTimeStart().toLocalDate());
			String time=formatTime.format(i.getTimeStart().toLocalTime());
			Object[] row=new Object[] {
				i.getIdScreening(), date, time, 
				movieDAO.getMovieByID(i.getMovie().getIdMovie()).getName(),
				movieDAO.getMovieByID(i.getMovie().getIdMovie()).getTime()+" phút",
				movieDAO.getMovieByID(i.getMovie().getIdMovie()).getType(),
				roomDAO.getRoomById(i.getRoom().getIdRoom()).getNumberRoom()
			};
			modelTableScreening.addRow(row);
		}
	}
	
//	Tìm kiếm suất chiếu
	public void searchScreening() {
		if(radioSearchByNameMovie.isSelected()) {
			String key=textSearchByNameMovie.getText();
			if(key!=null && !key.equals("")) {
				screeningDAO.getList().clear();
				screeningDAO.setList(screeningDAO.getAllScreeningSearchByName(key));
				loadDataTable();
				btnGroupSearchScreening.clearSelection();
				textSearchByNameMovie.setEditable(false); textSearchByNameMovie.setText("");
				JOptionPane.showMessageDialog(rootPane, "Tìm thấy "+screeningDAO.getList().size()+" suất chiếu");
			}
			else {
				JOptionPane.showMessageDialog(rootPane, "Nhập thông tin để tìm kiếm");
			}
		}
		else if(radioSearchByRoom.isSelected()) {
			String key=textSearchByRoom.getText();
			if(Pattern.compile("^[0-9]+$").matcher(key).matches()) {
				if(key!=null && !key.equals("")) {
					screeningDAO.getList().clear();
					screeningDAO.setList(screeningDAO.getAllScreeningSearchByRoom(key));
					loadDataTable();
					btnGroupSearchScreening.clearSelection();
					textSearchByRoom.setEditable(false); textSearchByRoom.setText("");
					JOptionPane.showMessageDialog(rootPane, "Tìm thấy "+screeningDAO.getList().size()+" suất chiếu");
				}
				else {
					JOptionPane.showMessageDialog(rootPane, "Nhập thông tin để tìm kiếm");
				}
			}
			else {
				JOptionPane.showMessageDialog(rootPane, "Số phòng phải là số");
			}
		}
		else if(radioSearchByTime.isSelected()) {
			String key1=textSearchByTimeFrom.getText();
			String key2=textSearchByTimeTo.getText();
			if(Pattern.compile("^[0-9]+$").matcher(key1).matches() && Pattern.compile("^[0-9]+$").matcher(key2).matches()) {
				if((Integer.parseInt(key1) >=1 && Integer.parseInt(key1)<=24) && (Integer.parseInt(key2) >=1 && Integer.parseInt(key2)<=24)) {
					String dateStart=String.format("%02d:00", Integer.parseInt(key1));
					String dateEnd=String.format("%02d:00", Integer.parseInt(key2));
					screeningDAO.getList().clear();
					screeningDAO.setList(screeningDAO.getAllScreeingSearchByTime(dateStart, dateEnd));
					loadDataTable();
					btnGroupSearchScreening.clearSelection();
					textSearchByTimeFrom.setEditable(false); textSearchByTimeFrom.setText("");
					textSearchByTimeTo.setEditable(false); textSearchByTimeTo.setText("");
					JOptionPane.showMessageDialog(rootPane, "Tìm thấy "+screeningDAO.getList().size()+" suất chiếu");
				}
				else {
					JOptionPane.showMessageDialog(rootPane, "Thời gian tìm kiếm không hợp lệ");
				}
			}
			else {
				JOptionPane.showMessageDialog(rootPane, "Thời gian tìm kiếm không hợp lệ");
			}
		}
		else {
			JOptionPane.showMessageDialog(rootPane, "Chọn tiêu chí để tìm kiếm");
		}
	}
	
//	Reset Screening
	public void resetScreening() {
		btnGroupSortScreening.clearSelection();
		btnGroupSearchScreening.clearSelection();
		idTextScreening.setText("");
		dateModelScreening.setValue(new Date());
		timeTextScreening.setText("");
		nameTextMovie.setText("");
		typeMovieText.setText("");
		durationTextScreening.setText("");
		roomTextScreening.setText("");
		textSearchByNameMovie.setText(""); textSearchByNameMovie.setEditable(false);
		textSearchByRoom.setText(""); textSearchByRoom.setEditable(false);
		textSearchByTimeFrom.setText(""); textSearchByTimeFrom.setEditable(false);
		textSearchByTimeTo.setText(""); textSearchByTimeTo.setEditable(false);
		screeningDAO.setList(screeningDAO.getAllScreening());
		loadDataTable();
	}
	
//	Add Screening
	public void addScreening() {
		if(!idTextScreening.getText().equals("")) {
			JOptionPane.showMessageDialog(rootPane, "Suất chiếu đã tồn tại");
			return;
		}
		if(timeTextScreening.getText().isEmpty() && nameTextMovie.getText().isEmpty()) {
			JOptionPane.showMessageDialog(rootPane, "Nhập đủ thông tin");
		}
		else {
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
			LocalDate date=LocalDate.parse(format.format(dateModelScreening.getValue()));
			String time=timeTextScreening.getText();
			String nameMovie=nameTextMovie.getText();
			String room=roomTextScreening.getText();
			
			if(!time.isEmpty() && !nameMovie.isEmpty() && !room.isEmpty() && !typeMovieText.getText().isEmpty()) {
				var check=new FilterImp();
				try {
					if(check.checkDateScreening(date) && check.checkName(nameMovie) && check.checkTimeScreening(time) && check.checkRoomScreening(room)) {
						String id="SC"+Screening.getIdAuto(); Screening.setIdAuto(Screening.getIdAuto()+1);
						LocalTime localTime=LocalTime.parse(time);
						LocalDateTime dateTime=LocalDateTime.of(date, localTime);
						Movie movie=movieDAO.getMovieByName(nameMovie);
						Room roomScreening=roomDAO.getRoomByNumber(Integer.parseInt(room));
						
						if(movie.getDateOfDebut().isAfter(dateTime.toLocalDate())) {
							Screening.setIdAuto(Screening.getIdAuto()-1);
							JOptionPane.showMessageDialog(rootPane, "Phim công chiếu vào ngày: "+
									DateTimeFormatter.ofPattern("dd-MM-yyyy").format(movie.getDateOfDebut()));
							return;
						}
						if(roomScreening!=null) {
							Screening res=new Screening(id, dateTime, roomScreening, movie);
							ArrayList<Screening> listScreening=screeningDAO.getScreeningByRoom(res.getRoom().getNumberRoom());
							int excute=0;
							for(Screening i: listScreening) {
								if(i.getTimeStart().equals(res.getTimeStart())) {
									excute=1;
									JOptionPane.showMessageDialog(rootPane, "Suất chiếu này đã có phim");
									Screening.setIdAuto(Screening.getIdAuto()-1);
									break;
								}
							}
							if(excute==0) {
								int excute2=0;
								for(Screening j: listScreening) {
									if(res.getTimeStart().toLocalDate().compareTo(j.getTimeStart().toLocalDate())==0) {
										int timeOfMovie=movieDAO.getMovieByID(j.getMovie().getIdMovie()).getTime();
										if(res.getTimeStart().compareTo(j.getTimeStart())>0 && res.getTimeStart().compareTo(j.getTimeStart().plusMinutes(timeOfMovie))<0){
											excute2=1;
											JOptionPane.showMessageDialog(rootPane, 
													"Thời gian diễn ra phim "+j.getIdScreening()+" (trùng với suất chiếu)");
											Screening.setIdAuto(Screening.getIdAuto()-1);
											break;
										}
										else if(res.getTimeStart().compareTo(j.getTimeStart())<0 && res.getTimeEnd().compareTo(j.getTimeStart())>0) {
											excute2=1;
											JOptionPane.showMessageDialog(rootPane, 
													"Thời gian kết thúc phim: "+res.getTimeEnd().toLocalTime()+" (trùng với suất chiếu sau "+j.getIdScreening()+" )");
											Screening.setIdAuto(Screening.getIdAuto()-1);
											break;
										}
									}
								}
								if(excute2==0) {
									for(Screening s: screeningDAO.getAllScreening()) {
										if(s.getIdScreening().equals(res.getIdScreening())) {
											res.setIdScreening("SC"+Screening.getIdAuto());
											Screening.setIdAuto(Screening.getIdAuto()+1);
										}
									}
									screeningDAO.create(res);
									resetScreening();
									JOptionPane.showMessageDialog(rootPane, "Thêm suất chiếu thành công");
								}
							}
						}
						else {
							Screening.setIdAuto(Screening.getIdAuto()-1);
							JOptionPane.showMessageDialog(rootPane, "Phòng chiếu không tồn tại");
						}
						
					}
				} catch (checkBirthday | checkName | checkTimeMovie e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(rootPane, e.getMessage());
				}
			}
			else {
				JOptionPane.showMessageDialog(rootPane, "Nhập đủ thông tin");
			}
		}
	}

//	Edit Screening
	public void editScreening() {
		int index=tableScreening.getSelectedRow();
		
		if(index!=-1) {
			if(timeTextScreening.getText().isEmpty() && nameTextMovie.getText().isEmpty()) {
				JOptionPane.showMessageDialog(rootPane, "Nhập đủ thông tin");
			}
			else {
				SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
				LocalDate date=LocalDate.parse(format.format(dateModelScreening.getValue()));
				String time=timeTextScreening.getText();
				String nameMovie=nameTextMovie.getText();
				String room=roomTextScreening.getText();
				
				if(!time.isEmpty() && !nameMovie.isEmpty() && !room.isEmpty() && !typeMovieText.getText().isEmpty()) {
					var check=new FilterImp();
					try {
						if(check.checkDateScreening(date) && check.checkName(nameMovie) && check.checkTimeScreening(time) && check.checkRoomScreening(room)) {
							String id=idTextScreening.getText();
							LocalTime localTime=LocalTime.parse(time);
							LocalDateTime dateTime=LocalDateTime.of(date, localTime);
							Movie movie=movieDAO.getMovieByName(nameMovie);
							Room roomScreening=roomDAO.getRoomByNumber(Integer.parseInt(room));
							
							if(roomScreening!=null) {
								Screening res=new Screening(id, dateTime, roomScreening, movie);
								ArrayList<Screening> listScreening=screeningDAO.getScreeningByRoom(res.getRoom().getNumberRoom());
								int excute=0;
								
								for(Screening i: listScreening) {
									if(i.getTimeStart().equals(res.getTimeStart()) && !i.getIdScreening().equals(id)) {
										excute=1;
										JOptionPane.showMessageDialog(rootPane, "Suất chiếu này đã có phim");
										break;
									}
								}
								if(excute==0) {
									int excute2=0;
									for(Screening j: listScreening) {
										if(!j.getIdScreening().equals(id)) {
											int timeOfMovie=movieDAO.getMovieByID(j.getMovie().getIdMovie()).getTime();
											if(res.getTimeStart().toLocalDate().compareTo(j.getTimeStart().toLocalDate())==0) {
												if(res.getTimeStart().compareTo(j.getTimeStart())>0 && res.getTimeStart().compareTo(j.getTimeStart().plusMinutes(timeOfMovie))<0){
													excute2=1;
													JOptionPane.showMessageDialog(rootPane, 
															"Thời gian diễn ra phim "+j.getIdScreening()+" (trùng với suất chiếu)");
													break;
												}
												else if(res.getTimeStart().compareTo(j.getTimeStart())<0 && res.getTimeEnd().compareTo(j.getTimeStart())>0) {
													excute2=1;
													JOptionPane.showMessageDialog(rootPane, 
															"Thời gian kết thúc phim: "+res.getTimeEnd().toLocalTime()+" (trùng với suất chiếu sau "+j.getIdScreening()+" )");
													break;
												}
											}
										}
									}
									if(excute2==0) {
										screeningDAO.update(res);
										resetScreening();
										JOptionPane.showMessageDialog(rootPane, "Sửa suất chiếu thành công");
									}
								}
							}
							else {
								JOptionPane.showMessageDialog(rootPane, "Phòng chiếu không tồn tại");
							}
							
						}
					} catch (checkBirthday | checkName | checkTimeMovie e) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(rootPane, e.getMessage());
					}
				}
				else {
					JOptionPane.showMessageDialog(rootPane, "Nhập đủ thông tin");
				}
			}
		}
		else {
			JOptionPane.showMessageDialog(rootPane, "Chọn suất chiếu để sửa");
		}
	}
	
//	Delete Screening
	public void deleteScreening() {
		int index=tableScreening.getSelectedRow();
		
		if(index!=-1) {
			String id=idTextScreening.getText();
			var check=JOptionPane.showConfirmDialog(rootPane, "Chắc chắn xóa suất chiếu");
			if(check==JOptionPane.OK_OPTION) {
				screeningDAO.delete(id);
				resetScreening();
				int idAutoCurrent=1;
				for(Screening s: screeningDAO.getList()) {
					if(Integer.parseInt(s.getIdScreening().substring(2))==idAutoCurrent) {
						idAutoCurrent++;
						Screening.setIdAuto(idAutoCurrent);
					}
				}
				JOptionPane.showMessageDialog(rootPane, "Xóa suất chiếu thành công");
			}
		}
		else {
			JOptionPane.showMessageDialog(rootPane, "Chọn suất chiếu để xóa");
		}
	}
	
//	Listener
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
		radioSortByNameASCScreening.addActionListener(this);
		radioSortByNameDESCScreening.addActionListener(this);
		radioSortByIdASCScreening.addActionListener(this);
		radioSortByIdDESCScreening.addActionListener(this);
		radioSortByTimeASCScreening.addActionListener(this);
		radioSortByTimeDESCScreening.addActionListener(this);
		radioSortByRoomASCScreening.addActionListener(this);
		radioSortByRoomDESCScreening.addActionListener(this);
		
		radioSearchByNameMovie.addActionListener(this);
		radioSearchByTime.addActionListener(this);
		radioSearchByRoom.addActionListener(this);
		
		btnSearchScreening.addActionListener(this);
		btnResetScreening.addActionListener(this);
		btnAddScreening.addActionListener(this);
		btnEditScreening.addActionListener(this);
		btnDeleteScreening.addActionListener(this);
		btnSaveScreening.addActionListener(this);
	}

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
		else if(obj.equals(radioSortByNameASCScreening)) {
			screeningDAO.sortByNameASC();
			loadDataTable();
		}
		else if(obj.equals(radioSortByNameDESCScreening)) {
			screeningDAO.sortByNameDESC();
			loadDataTable();
		}
		else if(obj.equals(radioSortByIdASCScreening)) {
			screeningDAO.sortByIdASC();
			loadDataTable();
		}
		else if(obj.equals(radioSortByIdDESCScreening)) {
			screeningDAO.sortByIdDESC();
			loadDataTable();
		}
		else if(obj.equals(radioSortByTimeASCScreening)) {
			screeningDAO.sortByTimeASC();
			loadDataTable();
		}
		else if(obj.equals(radioSortByTimeDESCScreening)) {
			screeningDAO.sortByTimeDESC();
			loadDataTable();
		}
		else if(obj.equals(radioSortByRoomASCScreening)) {
			screeningDAO.sortByRoomASC();
			loadDataTable();
		}
		else if(obj.equals(radioSortByRoomDESCScreening)) {
			screeningDAO.sortByRoomDESC();
			loadDataTable();
		}
		else if(obj.equals(radioSearchByNameMovie)) {
			if(!radioSearchByRoom.isSelected()) {
				textSearchByRoom.setText("");
				textSearchByRoom.setEditable(false);
			}
			if(!radioSearchByTime.isSelected()) {
				textSearchByTimeFrom.setText(""); textSearchByTimeFrom.setEditable(false);
				textSearchByTimeTo.setText(""); textSearchByTimeTo.setEditable(false);
			}
			textSearchByNameMovie.setEditable(true);
		}
		else if(obj.equals(radioSearchByRoom)) {
			if(!radioSearchByNameMovie.isSelected()) {
				textSearchByNameMovie.setText(""); textSearchByNameMovie.setEditable(false);
			}
			if(!radioSearchByTime.isSelected()) {
				textSearchByTimeFrom.setText(""); textSearchByTimeFrom.setEditable(false);
				textSearchByTimeTo.setText(""); textSearchByTimeTo.setEditable(false);
			}
			textSearchByRoom.setEditable(true);
		}
		else if(obj.equals(radioSearchByTime)) {
			if(!radioSearchByRoom.isSelected()) {
				textSearchByRoom.setText(""); textSearchByRoom.setEditable(false);
			}
			if(!radioSearchByNameMovie.isSelected()) {
				textSearchByNameMovie.setText(""); textSearchByNameMovie.setEditable(false);
			}
			textSearchByTimeFrom.setEditable(true);
			textSearchByTimeTo.setEditable(true);
		}
		else if(obj.equals(btnSearchScreening)) {
			searchScreening();
		}
		else if(obj.equals(btnResetScreening)) {
			resetScreening();
		}
		else if(obj.equals(btnAddScreening)){
			addScreening();
		}
		else if(obj.equals(btnEditScreening)) {
			editScreening();
		}
		else if(obj.equals(btnDeleteScreening)) {
			deleteScreening();
		}
		else if(obj.equals(btnSaveScreening)) {
			ExcelHelper export=new ExcelHelper();
			export.exportData(this, tableScreening);
		}
	}
	
	public void addMouseListener() {
		tableScreening.addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int index=tableScreening.getSelectedRow();
		SimpleDateFormat format=new SimpleDateFormat("dd-MM-yyyy");
		
		if(index!=-1) {
			idTextScreening.setText(modelTableScreening.getValueAt(index, 0).toString());
			try {
				dateModelScreening.setValue(format.parse(modelTableScreening.getValueAt(index, 1).toString()));
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			timeTextScreening.setText(modelTableScreening.getValueAt(index, 2).toString());
			nameTextMovie.setText(modelTableScreening.getValueAt(index, 3).toString());
			durationTextScreening.setText(modelTableScreening.getValueAt(index, 4).toString());
			typeMovieText.setText(modelTableScreening.getValueAt(index, 5).toString());
			roomTextScreening.setText(modelTableScreening.getValueAt(index, 6).toString());
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

	public void addFocusListener() {
		nameTextMovie.addFocusListener(this);
	}
	
	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		String nameMovie=nameTextMovie.getText();
		if(nameMovie!=null && !nameMovie.equals("")) {
			Movie movie=movieDAO.getMovieByName(nameMovie);
			if(movie!=null) {
				typeMovieText.setText(movie.getType());
				durationTextScreening.setText(movie.getTime()+" phút");
			}
			else {
				JOptionPane.showMessageDialog(rootPane, "Phim chưa được thêm");
			}
		}
	}
	
	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}


}
