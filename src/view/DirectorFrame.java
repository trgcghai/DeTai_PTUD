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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.CellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import controller.Database;
import controller.ExcelHelper;
import controller.FilterImp;
import controller.LabelDateFormatter;
import dao.Director_DAO;
import dao.Movie_DAO;
import entity.Director;
import exception.checkBirthday;
import exception.checkName;


public class DirectorFrame extends JFrame implements ActionListener, MouseListener{
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
	JPanel directorPanel, sortPanelDirector, searchPanelDirector, inforPanelDirector, northPanelDirector, southPanelDirector;
	JRadioButton radioSortByNameASCDirector, radioSortByNameDESCDirector, radioSortByIdASCDirector, radioSortByIdDESCDirector,
				radioSearchByNameDirector, radioSearchByIdDirector;
	JTextField textSearchByNameDirector, textSearchByIdDirector, nameTextDirector, idTextDirector;
	JLabel nameLabelDirector, idLabelDirector, dateLabelDirector, movieLabelDirector,  movieTextDirector;
	JDatePickerImpl dateTextDirector;
	UtilDateModel dateModelDirector;
	JButton btnSearchDirector, btnResetDirector, btnAddDirector, btnEditDirector, btnDeleteDirector, btnSaveDirector;
	ButtonGroup btnGroupSortDirector, btnGroupSearchDirector;
	JTable tableDirector;
	DefaultTableModel modelTableDirector;
	JScrollPane scrollDirector;
	
	private Director_DAO directorDAO;
	private Movie_DAO movieDAO;
	
	public DirectorFrame(String userName) {
		setTitle("Danh sách đạo diễn");
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
		
		Database.getInstance().connect();
		
		directorDAO=new Director_DAO();
		movieDAO=new Movie_DAO();
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
		directorPanel=new JPanel(); 
		directorPanel.setLayout(new BorderLayout(5,5));
		
//		Panel sắp xếp đạo diễn
		sortPanelDirector=new JPanel();
		sortPanelDirector.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(0,191,165)), "Sắp xếp danh sách đạo diễn", TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI",1,14)));
		sortPanelDirector.setLayout(new GridLayout(2, 2));
		
		radioSortByNameASCDirector=new JRadioButton("Theo tên tăng dần"); radioSortByNameASCDirector.setFont(new Font("Segoe UI",1,14));
		radioSortByNameDESCDirector=new JRadioButton("Theo tên giảm dần"); radioSortByNameDESCDirector.setFont(new Font("Segoe UI",1,14));
		radioSortByIdASCDirector=new JRadioButton("Theo mã đạo diễn tăng dần"); radioSortByIdASCDirector.setFont(new Font("Segoe UI",1,14));
		radioSortByIdDESCDirector=new JRadioButton("Theo mã đạo diễn giảm dần"); radioSortByIdDESCDirector.setFont(new Font("Segoe UI",1,14));
		
		sortPanelDirector.add(radioSortByNameASCDirector);  sortPanelDirector.add(radioSortByNameDESCDirector);
		sortPanelDirector.add(radioSortByIdASCDirector);    sortPanelDirector.add(radioSortByIdDESCDirector);
		
		northPanelDirector=new JPanel();  northPanelDirector.setLayout(new BorderLayout());
		northPanelDirector.add(sortPanelDirector, BorderLayout.WEST);
		
//		Panel thông tin đạo diễn
		inforPanelDirector=new JPanel(); inforPanelDirector.setLayout(new FlowLayout(FlowLayout.LEFT,20,5));
		inforPanelDirector.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(0,191,165)), "Thông tin đạo diễn", TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI",1,14)));
		nameLabelDirector=new JLabel("Tên đạo diễn:"); nameLabelDirector.setFont(new Font("Segoe UI",1,14));
		nameTextDirector=new JTextField(30); nameTextDirector.setFont(new Font("Segoe UI",1,14));
		inforPanelDirector.add(nameLabelDirector); inforPanelDirector.add(nameTextDirector);
		
		idLabelDirector=new JLabel("Mã đạo diễn: "); idLabelDirector.setFont(new Font("Segoe UI",1,14));
		idTextDirector=new JTextField(10); idTextDirector.setFont(new Font("Segoe UI",1,14));
		idTextDirector.setEditable(false);
		inforPanelDirector.add(idLabelDirector); inforPanelDirector.add(idTextDirector);
		
		dateLabelDirector=new JLabel("Ngày sinh:"); dateLabelDirector.setFont(new Font("Segoe UI",1,14));
		dateModelDirector=new UtilDateModel();
		Properties p=new Properties();
		p.put("text.day", "Day"); p.put("text.month", "Month"); p.put("text.year", "Year");
		JDatePanelImpl panelDate=new JDatePanelImpl(dateModelDirector, p);
		dateTextDirector=new JDatePickerImpl(panelDate, new LabelDateFormatter());
		dateTextDirector.setPreferredSize(new Dimension(150,20));
		inforPanelDirector.add(dateLabelDirector); inforPanelDirector.add(dateTextDirector);
		
		northPanelDirector.add(inforPanelDirector, BorderLayout.CENTER);
//		Panel tìm kiếm đạo diễn
		searchPanelDirector=new JPanel();
		searchPanelDirector.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(0,191,165)), "Tìm đạo diễn", TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI",1,14)));
		searchPanelDirector.setPreferredSize(new Dimension(450,130));
		searchPanelDirector.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		radioSearchByNameDirector=new JRadioButton("Theo tên:"); 
		textSearchByNameDirector=new JTextField(25); textSearchByNameDirector.setEditable(false);
		textSearchByNameDirector.setFont(new Font("Segoe UI",1,14));
		radioSearchByNameDirector.setFont(new Font("Segoe UI",1,14));
		radioSearchByIdDirector=new JRadioButton("Theo mã: "); 
		textSearchByIdDirector=new JTextField(25); textSearchByIdDirector.setEditable(false);
		textSearchByIdDirector.setFont(new Font("Segoe UI",1,14));
		radioSearchByIdDirector.setFont(new Font("Segoe UI",1,14));
		btnSearchDirector=new JButton("Tìm"); btnSearchDirector.setFont(new Font("Segoe UI",1,14));
		btnSearchDirector.setBackground(new Color(0, 102, 102)); btnSearchDirector.setForeground(Color.WHITE);
		
		searchPanelDirector.add(radioSearchByNameDirector); searchPanelDirector.add(textSearchByNameDirector);
		searchPanelDirector.add(radioSearchByIdDirector); searchPanelDirector.add(textSearchByIdDirector);
		searchPanelDirector.add(btnSearchDirector);
		
		northPanelDirector.add(searchPanelDirector, BorderLayout.EAST);
		
		directorPanel.add(northPanelDirector,BorderLayout.NORTH);
		
		
//		Table đạo diễn
		String[] colNameEmp= {"Mã đạo diễn","Tên đạo diễn","Ngày sinh"};
		modelTableDirector= new DefaultTableModel(colNameEmp,0);
		tableDirector=new JTable(modelTableDirector);
		tableDirector.getTableHeader().setFont(new Font("Segoe UI",1,14));
		tableDirector.setFont(new Font("Segoe UI",1,14));
		scrollDirector=new JScrollPane(tableDirector);
		scrollDirector.setBorder(BorderFactory.createLineBorder(new Color(0,191,165)));
		
		directorPanel.add(scrollDirector,BorderLayout.CENTER);
		
//		Các nút chức năng
		btnResetDirector=new JButton("Làm mới"); btnResetDirector.setFont(new Font("Segoe UI",1,14)); btnResetDirector.setPreferredSize(new Dimension(145, 30));
		btnAddDirector=new JButton("Thêm đạo diễn"); btnAddDirector.setFont(new Font("Segoe UI",1,14)); btnAddDirector.setPreferredSize(new Dimension(145, 30));
		btnEditDirector=new JButton("Sửa đạo diễn"); btnEditDirector.setFont(new Font("Segoe UI",1,14)); btnEditDirector.setPreferredSize(new Dimension(145, 30));
		btnDeleteDirector=new JButton("Xóa đạo diễn"); btnDeleteDirector.setFont(new Font("Segoe UI",1,14)); btnDeleteDirector.setPreferredSize(new Dimension(145, 30));
		btnSaveDirector=new JButton("Xuất danh sách"); btnSaveDirector.setFont(new Font("Segoe UI",1,14)); btnSaveDirector.setPreferredSize(new Dimension(145, 30));
		
		southPanelDirector=new JPanel();
		southPanelDirector.add(btnResetDirector); southPanelDirector.add(btnAddDirector);
		southPanelDirector.add(btnEditDirector); southPanelDirector.add(btnDeleteDirector); southPanelDirector.add(btnSaveDirector);

		directorPanel.add(southPanelDirector,BorderLayout.SOUTH);
		
		add(directorPanel);
	}
	
//	Change
	public void addButtonGroup() {
		btnGroupSortDirector=new ButtonGroup();
		btnGroupSearchDirector=new ButtonGroup();
		
		btnGroupSortDirector.add(radioSortByNameASCDirector);
		btnGroupSortDirector.add(radioSortByNameDESCDirector);
		btnGroupSortDirector.add(radioSortByIdASCDirector);
		btnGroupSortDirector.add(radioSortByIdDESCDirector);
		
		btnGroupSearchDirector.add(radioSearchByNameDirector);
		btnGroupSearchDirector.add(radioSearchByIdDirector);
	}
	
//	Gán danh sách từ sql
	public void loadData() {
		directorDAO.setList(directorDAO.getAllDirector());
		movieDAO.setList(movieDAO.getAllMovie());
		
		directorDAO.sortByIdDirectorASC();
		int idAutoDirector=1;
		for(Director j: directorDAO.getList()) {
			if(idAutoDirector==Integer.parseInt(j.getIdDirector().substring(2))) {
				idAutoDirector++;
				Director.setIdAuto(idAutoDirector);
			}
		}
	}
	
//	Đưa dữ liệu lên bảng
	public void loadDataTable() {
		modelTableDirector.setRowCount(0);
		DateTimeFormatter format=DateTimeFormatter.ofPattern("dd-MM-yyyy");
		for(Director i: directorDAO.getList()) {
			Object[] row=new Object[] {
				i.getIdDirector(), i.getName(), (i.getBirthday()!=null?format.format(i.getBirthday()):"")
			};
			modelTableDirector.addRow(row);
		}
	}
	
//	Tìm kiếm đạo diễn
	public void searchDirector() {
		if(radioSearchByNameDirector.isSelected()) {
			String key=textSearchByNameDirector.getText();
			if(key!=null && !key.equals("")) {
				directorDAO.getList().clear();
				directorDAO.setList(directorDAO.getAllDirectorByName(key));
				loadDataTable();
				
				JOptionPane.showMessageDialog(rootPane, "Tìm thấy "+directorDAO.getList().size()+" kết quả");
				
				btnGroupSearchDirector.clearSelection();
				textSearchByNameDirector.setText("");
				textSearchByNameDirector.setEditable(false);
			}
			else {
				JOptionPane.showMessageDialog(rootPane, "Nhập thông tin để tìm kiếm");
			}
		}
		else if(radioSearchByIdDirector.isSelected()) {
			String key=textSearchByIdDirector.getText();
			if(key!=null && !key.equals("")) {
				directorDAO.getList().clear();
				directorDAO.setList(directorDAO.getAllDirectorById(key));
				loadDataTable();
				
				JOptionPane.showMessageDialog(rootPane, "Tìm thấy "+directorDAO.getList().size()+" kết quả");
				
				btnGroupSearchDirector.clearSelection();
				textSearchByIdDirector.setText("");
				textSearchByIdDirector.setEditable(false);
			}
			else {
				JOptionPane.showMessageDialog(rootPane, "Nhập thông tin để tìm kiếm");
			}
		}
		else {
			JOptionPane.showMessageDialog(rootPane, "Chọn tiêu chí để tìm kiếm");
		}
	}
	
//	Reset Director
	public void resetDirector() {
		btnGroupSearchDirector.clearSelection();
		btnGroupSortDirector.clearSelection();
		textSearchByNameDirector.setText(""); textSearchByNameDirector.setEditable(false);
		textSearchByIdDirector.setText(""); textSearchByIdDirector.setEditable(false);
		nameTextDirector.setText("");
		idTextDirector.setText("");
		dateModelDirector.setValue(new Date());
		directorDAO.setList(directorDAO.getAllDirector());
		loadDataTable();
	}
	
//	Add Director
	public void addDirector() {
		if(!idTextDirector.getText().equals("")) {
			JOptionPane.showMessageDialog(rootPane, "Đạo diễn đã tồn tại");
			return;
		}
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		String nameDirector=nameTextDirector.getText();
		LocalDate birthday=LocalDate.parse(format.format(dateModelDirector.getValue()));
		
		if(!nameDirector.isEmpty() || !nameDirector.equals("")) {
			var check=new FilterImp();
			try {
				if(check.checkName(nameDirector) && check.checkBirthday(birthday)) {
					Director director=directorDAO.getDirectorByName(nameDirector);
					if(director!=null) {
						directorDAO.update(new Director(director.getIdDirector(), nameDirector, birthday));
					}
					else {
						String idDirector="DD"+Director.getIdAuto();
						Director.setIdAuto(Director.getIdAuto()+1);
						for(Director d: directorDAO.getAllDirector()) {
							if(d.getIdDirector().equals(idDirector)) {
								idDirector="DD"+Director.getIdAuto();
								Director.setIdAuto(Director.getIdAuto()+1);
							}
						}
						directorDAO.create(new Director(idDirector, nameDirector, birthday));
					}
					directorDAO.setList(directorDAO.getAllDirector());
					loadDataTable();
					resetDirector();
					JOptionPane.showMessageDialog(rootPane, "Thêm đạo diễn thành công");
				}
			} catch (checkName | checkBirthday e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(rootPane, e.getMessage());
			}
		}
		else {
			JOptionPane.showMessageDialog(rootPane, "Nhập đủ thông tin");
		}
	}
	
//	Edit Director
	public void editDirector() {
		int index=tableDirector.getSelectedRow();
		
		if(index!=-1) {
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
			String nameDirector=nameTextDirector.getText();
			String id=idTextDirector.getText();
			LocalDate birthday=LocalDate.parse(format.format(dateModelDirector.getValue()));
			
			if(!nameDirector.isEmpty() || !nameDirector.equals("")) {
				var check=new FilterImp();
				try {
					if(check.checkName(nameDirector) && check.checkBirthday(birthday)) {
						directorDAO.update(new Director(id, nameDirector, birthday));
						directorDAO.setList(directorDAO.getAllDirector());
						loadDataTable();
						resetDirector();
						JOptionPane.showMessageDialog(rootPane, "Sửa đạo diễn thành công");
					}
				} catch (checkName | checkBirthday e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(rootPane, e.getMessage());
				}
			}
			else {
				JOptionPane.showMessageDialog(rootPane, "Nhập đủ thông tin");
			}
		}
		else {
			JOptionPane.showMessageDialog(rootPane, "Chọn đạo diễn để sửa");
		}
	}

//	Delete Director
	public void deleteDirector() {
		int index=tableDirector.getSelectedRow();
		
		if(index!=-1) {
			String id=modelTableDirector.getValueAt(index, 0).toString();
			var check=JOptionPane.showConfirmDialog(rootPane, "Xóa đạo diễn sẽ xóa các bộ phim của đạo diễn");
			if(check==JOptionPane.OK_OPTION) {
				directorDAO.delete(id);
				directorDAO.setList(directorDAO.getAllDirector());
				loadDataTable();
				resetDirector();
				int idAutoCurrent=1;
				for(Director d: directorDAO.getList()) {
					if(Integer.parseInt(d.getIdDirector().substring(2))==idAutoCurrent) {
						idAutoCurrent++;
						Director.setIdAuto(idAutoCurrent);
					}
				}
				JOptionPane.showMessageDialog(rootPane, "Xóa đạo diễn thành công");
			}
		}
		else {
			JOptionPane.showMessageDialog(rootPane, "Chọn đạo diễn để xóa");
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
		radioSortByNameASCDirector.addActionListener(this);
		radioSortByNameDESCDirector.addActionListener(this);
		radioSortByIdASCDirector.addActionListener(this);
		radioSortByIdDESCDirector.addActionListener(this);
		
		radioSearchByNameDirector.addActionListener(this);
		radioSearchByIdDirector.addActionListener(this);
		btnSearchDirector.addActionListener(this);
		
		btnResetDirector.addActionListener(this);
		btnAddDirector.addActionListener(this);
		btnEditDirector.addActionListener(this);
		btnDeleteDirector.addActionListener(this);
		btnSaveDirector.addActionListener(this);
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
		else if(obj.equals(radioSortByNameASCDirector)) {
			directorDAO.sortByNameDirectorASC();
			loadDataTable();
		}
		else if(obj.equals(radioSortByNameDESCDirector)) {
			directorDAO.sortByNameDirectorDESC();
			loadDataTable();
		}
		else if(obj.equals(radioSortByIdASCDirector)) {
			directorDAO.sortByIdDirectorASC();
			loadDataTable();
		}
		else if(obj.equals(radioSortByIdDESCDirector)) {
			directorDAO.sortByIdDirectorDESC();
			loadDataTable();
		}
		else if(obj.equals(radioSearchByNameDirector)) {
			if(!radioSearchByIdDirector.isSelected()) {
				textSearchByIdDirector.setEditable(false);
				textSearchByIdDirector.setText("");
			}
			textSearchByNameDirector.setEditable(true);
		}
		else if(obj.equals(radioSearchByIdDirector)) {
			if(!radioSearchByNameDirector.isSelected()) {
				textSearchByNameDirector.setEditable(false);
				textSearchByNameDirector.setText("");
			}
			textSearchByIdDirector.setEditable(true);
		}
		else if(obj.equals(btnSearchDirector)) {
			searchDirector();
		}
		else if(obj.equals(btnResetDirector)) {
			resetDirector();
		}
		else if(obj.equals(btnAddDirector)) {
			addDirector();
		}
		else if(obj.equals(btnEditDirector)) {
			editDirector();
		}
		else if(obj.equals(btnDeleteDirector)) {
			deleteDirector();
		}
		else if(obj.equals(btnSaveDirector)) {
			ExcelHelper export=new ExcelHelper();
			export.exportData(this, tableDirector);
		}
	}
	
	public void addMouseListener() {
		tableDirector.addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int index=tableDirector.getSelectedRow();
		if(index!=-1) {
			idTextDirector.setText(modelTableDirector.getValueAt(index, 0).toString());
			nameTextDirector.setText(modelTableDirector.getValueAt(index, 1).toString());
			
			if(!modelTableDirector.getValueAt(index, 2).toString().equals("")) {
				SimpleDateFormat format=new SimpleDateFormat("dd-MM-yyyy");
				try {
					Date birthday=format.parse(modelTableDirector.getValueAt(index, 2).toString());
					dateModelDirector.setValue(birthday);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else {
				dateModelDirector.setValue(new Date());
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

}
