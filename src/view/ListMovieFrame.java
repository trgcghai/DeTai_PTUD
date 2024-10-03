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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import controller.Database;
import dao.Director_DAO;
import dao.Movie_DAO;
import entity.Movie;

public class ListMovieFrame extends JFrame implements ActionListener{
//	Thanh menu
	JMenuBar menuBar;
	JPanel imgMovie;
	String userName;
//	Nhân viên
	JMenu menuEmployee;
	JMenuItem itemUpdateEmployee, itemListEmployee;
// Khách hàng
	JMenu menuKhachHang;
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
	JPanel moviePanel, sortPanelMovie, searchPanelMovie,northPanelMovie, southPanelMovie;
	JRadioButton radioSortByNameASCMovie, radioSortByNameDESCMovie, radioSortByIdASCMovie, radioSortByIdDESCMovie,
				radioSearchByNameMovie, radioSearchByIdMovie, radioSearchByTypeMovie, radioSearchByDirector;
	JTextField textSearchByNameMovie, textSearchByIdMovie, textSearchByDirector;
	JComboBox boxSearchByTypeMovie;
	JButton btnSearchMovie, btnResetMovie;
	ButtonGroup btnGroupSortMovie, btnGroupSearchMovie;
	JTable tableMovie;
	DefaultTableModel modelTableMovie;
	JScrollPane scrollMovie;
	
	private Movie_DAO movieDAO;
	private Director_DAO directorDAO;
	
	public ListMovieFrame(String userName) {
		setTitle("Danh sách phim");
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
		
		movieDAO=new Movie_DAO();
		directorDAO=new Director_DAO();
		
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
		menuKhachHang=new JMenu("Khách Hàng");
		menuKhachHang.setFont(new Font("Segoe UI",1,16)); 
		menuKhachHang.setIcon(new ImageIcon(getClass().getResource("/image/customer.png")));
		
		itemListCustomer=new JMenuItem("Quản Lý");
		itemListCustomer.setFont(new Font("Segoe UI",0,14)); 
		itemListCustomer.setIcon(new ImageIcon(getClass().getResource("/image/list.png")));
		itemUpdateCustomer=new JMenuItem("Cập Nhật");
		itemUpdateCustomer.setFont(new Font("Segoe UI",0,14)); 
		itemUpdateCustomer.setIcon(new ImageIcon(getClass().getResource("/image/update.png")));
		
		menuKhachHang.add(itemListCustomer);
		menuKhachHang.add(itemUpdateCustomer);
		
		menuBar.add(menuKhachHang);
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
		moviePanel=new JPanel(); 
		moviePanel.setLayout(new BorderLayout(5,5));
		
//		Panel sắp xếp phim
		sortPanelMovie=new JPanel();
		sortPanelMovie.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(0,191,165)), "Sắp xếp danh sách phim", TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI",1,14)));
		sortPanelMovie.setLayout(new GridLayout(2, 2));
		
		radioSortByNameASCMovie=new JRadioButton("Theo tên tăng dần"); radioSortByNameASCMovie.setFont(new Font("Segoe UI",1,14));
		radioSortByNameDESCMovie=new JRadioButton("Theo tên giảm dần"); radioSortByNameDESCMovie.setFont(new Font("Segoe UI",1,14));
		radioSortByIdASCMovie=new JRadioButton("Theo mã phim tăng dần"); radioSortByIdASCMovie.setFont(new Font("Segoe UI",1,14));
		radioSortByIdDESCMovie=new JRadioButton("Theo mã phim giảm dần"); radioSortByIdDESCMovie.setFont(new Font("Segoe UI",1,14));
		
		sortPanelMovie.add(radioSortByNameASCMovie);  sortPanelMovie.add(radioSortByNameDESCMovie);
		sortPanelMovie.add(radioSortByIdASCMovie);    sortPanelMovie.add(radioSortByIdDESCMovie);
		
		northPanelMovie=new JPanel();  northPanelMovie.setLayout(new GridLayout(1, 2));
		northPanelMovie.add(sortPanelMovie);

//		Panel tìm kiếm phim
		searchPanelMovie=new JPanel();
		searchPanelMovie.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(0,191,165)), "Tìm phim", TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI",1,14)));
		searchPanelMovie.setPreferredSize(new Dimension(450,130));
		searchPanelMovie.setLayout(new GridLayout(3,2));
		
		JPanel res=new JPanel(); res.setLayout(new FlowLayout(FlowLayout.LEFT));
		radioSearchByNameMovie=new JRadioButton("Theo tên:"); 
		radioSearchByNameMovie.setFont(new Font("Segoe UI",1,14));
		textSearchByNameMovie=new JTextField(20);
		textSearchByNameMovie.setFont(new Font("Segoe UI",1,14));
		textSearchByNameMovie.setEditable(false);
		res.add(radioSearchByNameMovie); res.add(textSearchByNameMovie);
		searchPanelMovie.add(res);
		
		res=new JPanel(); res.setLayout(new FlowLayout(FlowLayout.LEFT));
		radioSearchByIdMovie=new JRadioButton("Theo mã:"); 
		radioSearchByIdMovie.setFont(new Font("Segoe UI",1,14));
		textSearchByIdMovie=new JTextField(20);
		textSearchByIdMovie.setFont(new Font("Segoe UI",1,14));
		textSearchByIdMovie.setEditable(false);
		res.add(radioSearchByIdMovie); res.add(textSearchByIdMovie);
		searchPanelMovie.add(res);
		
		res=new JPanel(); res.setLayout(new FlowLayout(FlowLayout.LEFT));
		radioSearchByTypeMovie=new JRadioButton("Theo loại:");
		radioSearchByTypeMovie.setFont(new Font("Segoe UI",1,14));
		boxSearchByTypeMovie=new JComboBox();
		boxSearchByTypeMovie.setFont(new Font("Segoe UI",1,14));
		boxSearchByTypeMovie.setPreferredSize(new Dimension(260,24));
		res.add(radioSearchByTypeMovie); res.add(boxSearchByTypeMovie);
		searchPanelMovie.add(res);
		
		res=new JPanel(); res.setLayout(new FlowLayout(FlowLayout.LEFT));
		radioSearchByDirector=new JRadioButton("Theo đạo diễn:");
		radioSearchByDirector.setFont(new Font("Segoe UI",1,14));
		textSearchByDirector=new JTextField(17);
		textSearchByDirector.setFont(new Font("Segoe UI",1,14));
		textSearchByDirector.setEditable(false);
		res.add(radioSearchByDirector); res.add(textSearchByDirector);
		searchPanelMovie.add(res);
		
		res=new JPanel();
		searchPanelMovie.add(res);
		
		res=new JPanel(); res.setLayout(new FlowLayout(FlowLayout.RIGHT,15,0));
		btnResetMovie=new JButton("Hủy"); btnResetMovie.setFont(new Font("Segoe UI",1,14));
		btnResetMovie.setBackground(Color.RED); btnResetMovie.setForeground(Color.WHITE);
		btnSearchMovie=new JButton("Tìm"); btnSearchMovie.setFont(new Font("Segoe UI",1,14));
		btnSearchMovie.setBackground(new Color(0, 102, 102)); btnSearchMovie.setForeground(Color.WHITE);
		res.add(btnResetMovie);
		res.add(btnSearchMovie);
		searchPanelMovie.add(res);
		
		
		northPanelMovie.add(searchPanelMovie);
		
		moviePanel.add(northPanelMovie,BorderLayout.NORTH);
		
		
//		Table phim
		String[] colNameEmp= {"Mã phim","Tên phim","Đạo diễn","Thời lượng","Ngày công chiếu","Loại phim"};
		modelTableMovie= new DefaultTableModel(colNameEmp,0);
		tableMovie=new JTable(modelTableMovie);
		tableMovie.getTableHeader().setFont(new Font("Segoe UI",1,14));
		tableMovie.setFont(new Font("Segoe UI",1,14));
		scrollMovie=new JScrollPane(tableMovie);
		scrollMovie.setBorder(BorderFactory.createLineBorder(new Color(0,191,165)));
		
		moviePanel.add(scrollMovie,BorderLayout.CENTER);
		
		add(moviePanel);
	}

//	Change 
	public void addButtonGroup() {
		btnGroupSortMovie=new ButtonGroup();
		btnGroupSortMovie.add(radioSortByNameASCMovie);
		btnGroupSortMovie.add(radioSortByNameDESCMovie);
		btnGroupSortMovie.add(radioSortByIdASCMovie);
		btnGroupSortMovie.add(radioSortByIdDESCMovie);
		
		btnGroupSearchMovie=new ButtonGroup();
		btnGroupSearchMovie.add(radioSearchByNameMovie);
		btnGroupSearchMovie.add(radioSearchByIdMovie);
		btnGroupSearchMovie.add(radioSearchByTypeMovie);
		btnGroupSearchMovie.add(radioSearchByDirector);
	}
	
//	Gán danh sách từ sql
	public void loadData() {
		movieDAO.setList(movieDAO.getAllMovie());
		
		Set<String> typeSet=new HashSet<String>();
		for(Movie i:movieDAO.getAllMovie()) {
			typeSet.add(i.getType());
		}
		for(String i:typeSet) {
			boxSearchByTypeMovie.addItem(i);
		}
	}
	
//	Đưa dữ liệu lên bảng
	public void loadDataTable() {
		DateTimeFormatter format=DateTimeFormatter.ofPattern("dd-MM-yyyy");
		modelTableMovie.setRowCount(0);
		for(Movie i: movieDAO.getList()) {
			Object[] obj=new Object[] {
				i.getIdMovie(), i.getName(), directorDAO.getDirectorById(i.getDirector().getIdDirector()).getName(),
				i.getTime()+" phút", format.format(i.getDateOfDebut()), i.getType()
			};
			modelTableMovie.addRow(obj);
		}
	}
	
//	Tìm kiếm phim
	public void searchMovie() {
		if(radioSearchByNameMovie.isSelected()) {
			String key=textSearchByNameMovie.getText();
			if(!key.isEmpty() || !key.equals("")) {
				movieDAO.getList().clear();
				movieDAO.setList(movieDAO.getAllMovieByName(key));
				loadDataTable();
				JOptionPane.showMessageDialog(rootPane, "Tìm thấy: "+movieDAO.getList().size()+" phim");
			}
			else {
				JOptionPane.showMessageDialog(rootPane, "Nhập thông tin để tìm kiếm");
			}
		}
		else if(radioSearchByIdMovie.isSelected()) {
			String key=textSearchByIdMovie.getText();
			if(!key.isEmpty() || !key.equals("")) {
				movieDAO.getList().clear();
				movieDAO.setList(movieDAO.getAllMovieById(key));
				loadDataTable();
				JOptionPane.showMessageDialog(rootPane, "Tìm thấy: "+movieDAO.getList().size()+" phim");
			}
			else {
				JOptionPane.showMessageDialog(rootPane, "Nhập thông tin để tìm kiếm");
			}
		}
		else if(radioSearchByTypeMovie.isSelected()) {
			String key=boxSearchByTypeMovie.getSelectedItem().toString();
			movieDAO.getList().clear();
			movieDAO.setList(movieDAO.getAllMovieByType(key));
			loadDataTable();
			JOptionPane.showMessageDialog(rootPane, "Tìm thấy: "+movieDAO.getList().size()+" phim");
		}
		else if(radioSearchByDirector.isSelected()) {
			String key=textSearchByDirector.getText();
			if(!key.isEmpty() || !key.equals("")) {
				movieDAO.getList().clear();
				movieDAO.setList(movieDAO.getAllMovieByDirector(key));
				loadDataTable();
				JOptionPane.showMessageDialog(rootPane, "Tìm thấy: "+movieDAO.getList().size()+" phim");
			}
			else {
				JOptionPane.showMessageDialog(rootPane, "Nhập thông tin để tìm kiếm");
			}
		}
		else {
			JOptionPane.showMessageDialog(rootPane, "Chọn tiêu chí để tìm kiếm");
		}
	}
	
//	Reset Movie
	public void resetMovie() {
		btnGroupSearchMovie.clearSelection();
		btnGroupSortMovie.clearSelection();
		textSearchByNameMovie.setEditable(false); textSearchByNameMovie.setText("");
		textSearchByIdMovie.setEditable(false); textSearchByIdMovie.setText("");
		textSearchByDirector.setEditable(false); textSearchByDirector.setText("");
		boxSearchByTypeMovie.setSelectedIndex(0);
		movieDAO.setList(movieDAO.getAllMovie());
		loadDataTable();
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
		radioSortByNameASCMovie.addActionListener(this);
		radioSortByNameDESCMovie.addActionListener(this);
		radioSortByIdASCMovie.addActionListener(this);
		radioSortByIdDESCMovie.addActionListener(this);
		
		radioSearchByNameMovie.addActionListener(this);
		radioSearchByIdMovie.addActionListener(this);
		radioSearchByTypeMovie.addActionListener(this);
		radioSearchByDirector.addActionListener(this);
		
		btnSearchMovie.addActionListener(this);
		btnResetMovie.addActionListener(this);
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
		else if(obj.equals(radioSortByNameASCMovie)) {
			movieDAO.sortByNameMovieASC();
			loadDataTable();
		}
		else if(obj.equals(radioSortByNameDESCMovie)) {
			movieDAO.sortByNameMovieDESC();
			loadDataTable();
		}
		else if(obj.equals(radioSortByIdASCMovie)) {
			movieDAO.sortByIdMovieASC();
			loadDataTable();
		}
		else if(obj.equals(radioSortByIdDESCMovie)) {
			movieDAO.sortByIdMovieDESC();
			loadDataTable();
		}
		else if(obj.equals(radioSearchByNameMovie)) {
			if(!radioSearchByDirector.isSelected() && !radioSearchByIdMovie.isSelected()) {
				textSearchByIdMovie.setEditable(false); textSearchByIdMovie.setText("");
				textSearchByDirector.setEditable(false); textSearchByDirector.setText("");
			}
			textSearchByNameMovie.setEditable(true);;
		}
		else if(obj.equals(radioSearchByIdMovie)) {
			if(!radioSearchByDirector.isSelected() && !radioSearchByNameMovie.isSelected()) {
				textSearchByNameMovie.setEditable(false); textSearchByNameMovie.setText("");
				textSearchByDirector.setEditable(false); textSearchByDirector.setText("");
			}
			textSearchByIdMovie.setEditable(true);;
		}
		else if(obj.equals(radioSearchByDirector)) {
			if(!radioSearchByIdMovie.isSelected() && !radioSearchByNameMovie.isSelected()) {
				textSearchByNameMovie.setEditable(false); textSearchByNameMovie.setText("");
				textSearchByIdMovie.setEditable(false); textSearchByIdMovie.setText("");
			}
			textSearchByDirector.setEditable(true);;
		}
		else if(obj.equals(radioSearchByTypeMovie)) {
			if(!radioSearchByIdMovie.isSelected() && !radioSearchByNameMovie.isSelected() && !radioSearchByDirector.isSelected()) {
				textSearchByNameMovie.setEditable(false); textSearchByNameMovie.setText("");
				textSearchByIdMovie.setEditable(false); textSearchByIdMovie.setText("");
				textSearchByDirector.setEditable(false); textSearchByDirector.setText("");
			}
		}
		else if(obj.equals(btnSearchMovie)) {
			searchMovie();
		}
		else if(obj.equals(btnResetMovie)) {
			resetMovie();
		}
	}
	
	
	
	

}
