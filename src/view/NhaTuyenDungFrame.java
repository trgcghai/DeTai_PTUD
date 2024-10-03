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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

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
import entity.Movie;
import exception.checkBirthday;
import exception.checkName;
import exception.checkTimeMovie;

public class NhaTuyenDungFrame extends JFrame implements ActionListener, MouseListener {
//	Thanh menu
	JMenuBar menuBar;
	JPanel imgMain;
	String userName;
//	Nhân viên
	JMenu menuNhanVien;
	JMenuItem itemCapNhatNhanVien, itemDSNhanVien;
//	Tài khoản
	JMenuItem menuTaiKhoan;
//	Ứng viên
	JMenu menuUngVien;
	JMenuItem itemCapNhatUngVien, itemDSUngVien;
// 	Hồ sơ
	JMenuItem menuHoSo;
// 	Nhà tuyển dụng
	JMenu menuNhaTuyenDung;
	JMenuItem itemNhaTuyenDung;
// 	Tin tuyển dụng
	JMenuItem menuTinTuyenDung;
// 	Hợp đồng
	JMenu menuHopDong;
	JMenuItem itemDSHopDong;
//	Tìm việc làm
	JMenu menuTimViecLam;
// 	Thống kê
	JMenu menuThongKe;
	JMenuItem itemTKNhanVien, itemTKCongTy, itemTKHoSo, itemTKTinTuyenDung;
// 	Hệ thống
	JMenu menuUser;
	JMenuItem itemHome, itemLogout;
	
//	Component danh sách nhà tuyển dụng
	JPanel moviePanel, northPanelMovie, southPanelMovie, inforMoviePanel, sortSearchPanel, posterPanel;
	JLabel idMovieLabel, directorLabel, nameMovieLabel, timeMovieLabel, dateMovieLabel, emailLabel,
			searchNameLabel, searchIdLabel, searchEmailLabel, searchPhoneLabel;
	JTextField idMovieText, directorText, nameMovieText, timeMovieText, emailText,
			searchNameText, searchIdText, searchEmailText, searchPhoneText;
	UtilDateModel modelDateMovie;
	JDatePickerImpl dateMovieText;
	JRadioButton radioSortByNameASCNTD, radioSortByNameDESCNTD, radioSortByIdASCNTD, radioSortByIdDESCNTD;
//	JComboBox typeMovieText;
	JFileChooser fileChooser;
	JButton btnResetMovie, btnAddMovie, btnEditMovie, btnDeleteMovie, btnSaveMovie, btnPoster,
			btnReset, btnSearch;
	JTable tableMovie;
	DefaultTableModel modelTableMovie;
	JScrollPane scrollMovie;
	
	private String poster;
	private Movie_DAO movieDAO;
	private Director_DAO directorDAO;
	
	public NhaTuyenDungFrame(String userName) {
		setTitle("Danh sách nhà tuyển dụng");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JPanel panelFrame=new JPanel();
		panelFrame.setLayout(new BorderLayout());
		panelFrame.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		setContentPane(panelFrame);
		
		this.userName=userName;
		
		initComponentNhanVien();
		initComponentUngVien();
		initComponentCongTy();
		initComponentHopDong();
		initComponentTimViecLam();
		initComponentThongKe();
		initComponentUserRight();
		initComponent();
		
//		addActionListener();
//		addMouseListener();
//		
//		Database.getInstance().connect();
//		movieDAO=new Movie_DAO();
//		directorDAO=new Director_DAO();
//		
//		loadData();
//		loadDataTable();
	}
	
	public void initComponentNhanVien() {
		menuBar=new JMenuBar();
		setJMenuBar(menuBar);
		menuNhanVien=new JMenu("Nhân Viên");  
		menuNhanVien.setFont(new Font("Segoe UI",1,16)); 
		menuNhanVien.setIcon(new ImageIcon(getClass().getResource("/image/nhanvien.png")));
		menuBar.add(menuNhanVien);
		
		itemDSNhanVien=new JMenuItem("Quản Lý");  itemDSNhanVien.setFont(new Font("Segoe UI",0,14));
		itemDSNhanVien.setIcon(new ImageIcon(getClass().getResource("/image/danhsach.png")));
		menuNhanVien.add(itemDSNhanVien);
		
		itemCapNhatNhanVien=new JMenuItem("Cập Nhật");  itemCapNhatNhanVien.setFont(new Font("Segoe UI",0,14));
		itemCapNhatNhanVien.setIcon(new ImageIcon(getClass().getResource("/image/capnhat.png")));
		menuNhanVien.add(itemCapNhatNhanVien);
		
		menuTaiKhoan=new JMenuItem("Tài khoản"); menuTaiKhoan.setFont(new Font("Segoe UI",0,14));
		menuTaiKhoan.setIcon(new ImageIcon(getClass().getResource("/image/taikhoan.png")));
		menuNhanVien.add(menuTaiKhoan);
		
		imgMain=new JPanel(); imgMain.setPreferredSize(new Dimension(1100, 700));
		JLabel imgLabel=new JLabel();
		ImageIcon imgIcon=new ImageIcon(getClass().getResource("/image/timvieclam.png"));
		Image img=imgIcon.getImage().getScaledInstance(1600, 800, Image.SCALE_SMOOTH);
		imgLabel.setIcon(new ImageIcon(img));
		imgMain.add(imgLabel);
		
		add(imgMain);
	}
	
	public void initComponentUngVien() {
		menuUngVien=new JMenu("Ứng Viên");
		menuUngVien.setFont(new Font("Segoe UI",1,16)); 
		menuUngVien.setIcon(new ImageIcon(getClass().getResource("/image/ungvien.png")));
		
		itemDSUngVien=new JMenuItem("Quản Lý");
		itemDSUngVien.setFont(new Font("Segoe UI",0,14)); 
		itemDSUngVien.setIcon(new ImageIcon(getClass().getResource("/image/danhsach.png")));
		itemCapNhatUngVien=new JMenuItem("Cập Nhật");
		itemCapNhatUngVien.setFont(new Font("Segoe UI",0,14)); 
		itemCapNhatUngVien.setIcon(new ImageIcon(getClass().getResource("/image/capnhat.png")));
		
		menuHoSo=new JMenuItem("Hồ Sơ");
		menuHoSo.setFont(new Font("Segoe UI",0,14)); 
		menuHoSo.setIcon(new ImageIcon(getClass().getResource("/image/hoso.png")));
		
		menuUngVien.add(itemDSUngVien);
		menuUngVien.add(itemCapNhatUngVien);
		menuUngVien.add(menuHoSo);
		
		menuBar.add(menuUngVien);
	}
	
	public void initComponentCongTy() {
		menuNhaTuyenDung=new JMenu("Nhà Tuyển Dụng");
		menuNhaTuyenDung.setFont(new Font("Segoe UI",1,16));
		menuNhaTuyenDung.setIcon(new ImageIcon(getClass().getResource("/image/nhatuyendung.png")));
		
		itemNhaTuyenDung=new JMenuItem("Nhà Tuyển Dụng");
		itemNhaTuyenDung.setFont(new Font("Segoe UI",0,14));
		itemNhaTuyenDung.setIcon(new ImageIcon(getClass().getResource("/image/danhsach.png")));
		
		menuTinTuyenDung=new JMenuItem("Tin Tuyển Dụng");
		menuTinTuyenDung.setFont(new Font("Segoe UI",0,14)); 
		menuTinTuyenDung.setIcon(new ImageIcon(getClass().getResource("/image/tintuyendung.png")));
		
		menuNhaTuyenDung.add(itemNhaTuyenDung);
		menuNhaTuyenDung.add(menuTinTuyenDung);
		
		menuBar.add(menuNhaTuyenDung);
	}
	
	public void initComponentHopDong() {
		menuHopDong=new JMenu("Hợp Đồng");
		menuHopDong.setFont(new Font("Segoe UI",1,16));
		menuHopDong.setIcon(new ImageIcon(getClass().getResource("/image/hopdong.png")));
		
		itemDSHopDong=new JMenuItem("Quản Lý");
		itemDSHopDong.setFont(new Font("Segoe UI",0,14));
		itemDSHopDong.setIcon(new ImageIcon(getClass().getResource("/image/danhsach.png")));
		
		menuHopDong.add(itemDSHopDong);
		
		menuBar.add(menuHopDong);
	}
	
	public void initComponentTimViecLam() {
		menuTimViecLam=new JMenu("Tìm Việc Làm");
		menuTimViecLam.setFont(new Font("Segoe UI",1,16));
		menuTimViecLam.setIcon(new ImageIcon(getClass().getResource("/image/timviec.png")));
		
		menuBar.add(menuTimViecLam);
	}
	
	public void initComponentThongKe() {
		menuThongKe=new JMenu("Thống Kê");
		menuThongKe.setFont(new Font("Segoe UI",1,16));
		menuThongKe.setIcon(new ImageIcon(getClass().getResource("/image/thongke.png")));
		
		itemTKNhanVien=new JMenuItem("Nhân Viên");
		itemTKNhanVien.setFont(new Font("Segoe UI",0,14));
		itemTKNhanVien.setIcon(new ImageIcon(getClass().getResource("/image/nhanvien.png")));
		itemTKCongTy=new JMenuItem("Công Ty");
		itemTKCongTy.setFont(new Font("Segoe UI",0,14));
		itemTKCongTy.setIcon(new ImageIcon(getClass().getResource("/image/nhatuyendung.png")));
		itemTKHoSo=new JMenuItem("Hồ Sơ");
		itemTKHoSo.setFont(new Font("Segoe UI",0,14));
		itemTKHoSo.setIcon(new ImageIcon(getClass().getResource("/image/hoso.png")));
		itemTKTinTuyenDung=new JMenuItem("Tin Tuyển Dụng");
		itemTKTinTuyenDung.setFont(new Font("Segoe UI",0,14));
		itemTKTinTuyenDung.setIcon(new ImageIcon(getClass().getResource("/image/tintuyendung.png")));
		
		menuThongKe.add(itemTKNhanVien);
		menuThongKe.add(itemTKCongTy);
		menuThongKe.add(itemTKHoSo);
		menuThongKe.add(itemTKTinTuyenDung);
		
		menuBar.add(menuThongKe);
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
		
//		Hiển thị thông tin
		northPanelMovie=new JPanel();  northPanelMovie.setLayout(new BorderLayout(100,0));
		northPanelMovie.setPreferredSize(new Dimension(getWidth(),200));
		inforMoviePanel=new JPanel(); inforMoviePanel.setLayout(new GridBagLayout());
		inforMoviePanel.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(0,191,165)), "Thông tin nhà tuyển dụng",
				TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI",1,14)));
		GridBagConstraints gbc= new GridBagConstraints();
		
		gbc.gridx=0; gbc.gridy=0; gbc.insets=new Insets(5, 3, 5, 3); 
		gbc.anchor=GridBagConstraints.EAST;
		nameMovieLabel=new JLabel("Tên nhà tuyển dụng:"); nameMovieLabel.setFont(new Font("Segoe UI",1,14));
		inforMoviePanel.add(nameMovieLabel, gbc);
		gbc.gridx=1; gbc.anchor=GridBagConstraints.WEST;
		nameMovieText=new JTextField(15); nameMovieText.setFont(new Font("Segoe UI",1,14));
		inforMoviePanel.add(nameMovieText, gbc);
		
		gbc.gridx=2; gbc.gridy=0; gbc.gridwidth=1; gbc.anchor=GridBagConstraints.EAST;
		timeMovieLabel=new JLabel("Số điện thoại:"); timeMovieLabel.setFont(new Font("Segoe UI",1,14));
		inforMoviePanel.add(timeMovieLabel, gbc);
		gbc.gridx=3; gbc.anchor=GridBagConstraints.WEST;
		timeMovieText=new JTextField(15); timeMovieText.setFont(new Font("Segoe UI",1,14));
		inforMoviePanel.add(timeMovieText, gbc);
		
		gbc.gridx=4; gbc.gridheight=3;
		posterPanel=new JPanel();
		posterPanel.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(0,191,165)), "Logo",
				TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI",1,12)));
		posterPanel.setPreferredSize(new Dimension(100, 100));
		inforMoviePanel.add(posterPanel, gbc);
		
		gbc.gridx=0; gbc.gridy=1; gbc.gridwidth=1; gbc.gridheight=1; gbc.anchor=GridBagConstraints.EAST;
		directorLabel=new JLabel("Địa chỉ:"); directorLabel.setFont(new Font("Segoe UI",1,14));
		inforMoviePanel.add(directorLabel, gbc);
		gbc.gridx=1; gbc.anchor=GridBagConstraints.WEST;
		directorText=new JTextField(15); directorText.setFont(new Font("Segoe UI",1,14));
		inforMoviePanel.add(directorText, gbc);
		
		gbc.gridx=2; gbc.anchor=GridBagConstraints.EAST;
		emailLabel=new JLabel("Email:"); emailLabel.setFont(new Font("Segoe UI",1,14));
		inforMoviePanel.add(emailLabel,gbc);
		gbc.gridx=3; gbc.anchor=GridBagConstraints.WEST;
		emailText=new JTextField(15); emailText.setFont(new Font("Segoe UI",0,14));
		inforMoviePanel.add(emailText, gbc);
		
		gbc.gridx=0; gbc.gridy=2; gbc.anchor=GridBagConstraints.EAST;
		idMovieLabel=new JLabel("Mã nhà tuyển dụng:"); idMovieLabel.setFont(new Font("Segoe UI",1,14));
		inforMoviePanel.add(idMovieLabel, gbc);
		gbc.gridx=1; gbc.anchor=GridBagConstraints.WEST;
		idMovieText=new JTextField(15); idMovieText.setFont(new Font("Segoe UI",1,14));
		idMovieText.setEditable(false);
		inforMoviePanel.add(idMovieText, gbc);
		
		gbc.gridx=4; gbc.gridy=3;
		btnPoster=new JButton("Chọn logo");
		btnPoster.setFont(new Font("Segoe UI",1,13));
		inforMoviePanel.add(btnPoster, gbc);
		
		northPanelMovie.add(inforMoviePanel, BorderLayout.WEST);
		
//		Sắp xếp và tìm kiếm
		sortSearchPanel=new JPanel(); sortSearchPanel.setLayout(new BorderLayout());
		sortSearchPanel.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(0,191,165)), 
				"Tìm kiếm nhà tuyển dụng",
				TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI",1,14)));
		
		JPanel searchPanel=new JPanel(); 
		searchPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbca= new GridBagConstraints();
		
		gbca.gridx=0; gbca.gridy=0; gbca.insets=new Insets(5, 3, 5, 3);
		gbca.anchor=GridBagConstraints.EAST;
		searchNameLabel=new JLabel("Tìm kiếm tên:"); searchNameLabel.setFont(new Font("Segoe UI",1,14));
		searchPanel.add(searchNameLabel, gbca);
		gbca.gridx=1; gbca.anchor=GridBagConstraints.WEST;
		searchNameText=new JTextField(15); searchNameText.setFont(new Font("Segoe UI",1,14));
		searchPanel.add(searchNameText, gbca);
		
		gbca.gridx=2;
		gbca.anchor=GridBagConstraints.EAST;
		searchIdLabel=new JLabel("Tìm kiếm mã:"); searchIdLabel.setFont(new Font("Segoe UI",1,14));
		searchPanel.add(searchIdLabel, gbca);
		gbca.gridx=3; gbca.anchor=GridBagConstraints.WEST;
		searchIdText=new JTextField(15); searchIdText.setFont(new Font("Segoe UI",1,14));
		searchPanel.add(searchIdText, gbca);
		
		gbca.gridx=0; gbca.gridy=1;
		gbca.anchor=GridBagConstraints.EAST;
		searchEmailLabel=new JLabel("Tìm kiếm email:"); searchEmailLabel.setFont(new Font("Segoe UI",1,14));
		searchPanel.add(searchEmailLabel, gbca);
		gbca.gridx=1; gbca.anchor=GridBagConstraints.WEST;
		searchEmailText=new JTextField(15); searchEmailText.setFont(new Font("Segoe UI",1,14));
		searchPanel.add(searchEmailText, gbca);
		
		gbca.gridx=2;
		gbca.anchor=GridBagConstraints.EAST;
		searchPhoneLabel=new JLabel("Tìm kiếm SĐT:"); searchPhoneLabel.setFont(new Font("Segoe UI",1,14));
		searchPanel.add(searchPhoneLabel, gbca);
		gbca.gridx=3; gbca.anchor=GridBagConstraints.WEST;
		searchPhoneText=new JTextField(15); searchPhoneText.setFont(new Font("Segoe UI",1,14));
		searchPanel.add(searchPhoneText, gbca);
		
		JPanel res=new JPanel(); res.setLayout(new FlowLayout(FlowLayout.RIGHT,15,5));
		btnReset=new JButton("Hủy"); btnReset.setFont(new Font("Segoe UI",1,14));
		btnReset.setBackground(Color.RED); btnReset.setForeground(Color.WHITE);
		btnSearch=new JButton("Tìm"); btnSearch.setFont(new Font("Segoe UI",1,14));
		btnSearch.setBackground(new Color(0, 102, 102)); btnSearch.setForeground(Color.WHITE);
		res.add(btnReset); res.add(btnSearch);
		
		sortSearchPanel.add(searchPanel, BorderLayout.CENTER);
		sortSearchPanel.add(res, BorderLayout.SOUTH);
		
		northPanelMovie.add(sortSearchPanel, BorderLayout.EAST);
		
		moviePanel.add(northPanelMovie,BorderLayout.NORTH);
		
//		Table nhà tuyển dụng
		String[] colNameEmp= {"Mã nhà tuyển dụng","Tên nhà tuyển dụng","Số điện thoại","Địa chỉ","Email","Logo"};
		modelTableMovie= new DefaultTableModel(colNameEmp,0);
		tableMovie=new JTable(modelTableMovie);
		tableMovie.getTableHeader().setFont(new Font("Segoe UI",1,14));
		tableMovie.setFont(new Font("Segoe UI",1,14));
		tableMovie.setAutoCreateRowSorter(true);
		ArrayList<RowSorter.SortKey> list = new ArrayList<>();
        DefaultRowSorter sorter = ((DefaultRowSorter)tableMovie.getRowSorter());
        sorter.setSortsOnUpdates(true);
        list.add( new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sorter.setSortKeys(list);
        sorter.sort();
		scrollMovie=new JScrollPane(tableMovie);
		scrollMovie.setBorder(BorderFactory.createLineBorder(new Color(0,191,165)));
		
		moviePanel.add(scrollMovie,BorderLayout.CENTER);
		
//		Các nút chức năng 
		btnResetMovie=new JButton("Làm mới"); btnResetMovie.setFont(new Font("Segoe UI",1,14)); btnResetMovie.setPreferredSize(new Dimension(185, 30));
		btnAddMovie=new JButton("Thêm nhà tuyển dụng"); btnAddMovie.setFont(new Font("Segoe UI",1,14)); btnAddMovie.setPreferredSize(new Dimension(185, 30));
		btnEditMovie=new JButton("Sửa nhà tuyển dụng"); btnEditMovie.setFont(new Font("Segoe UI",1,14)); btnEditMovie.setPreferredSize(new Dimension(185, 30));
		btnDeleteMovie=new JButton("Xóa nhà tuyển dụng"); btnDeleteMovie.setFont(new Font("Segoe UI",1,14)); btnDeleteMovie.setPreferredSize(new Dimension(185, 30));
		btnSaveMovie=new JButton("Xuất danh sách"); btnSaveMovie.setFont(new Font("Segoe UI",1,14)); btnSaveMovie.setPreferredSize(new Dimension(185, 30));
		
		southPanelMovie=new JPanel();
		southPanelMovie.add(btnResetMovie); southPanelMovie.add(btnAddMovie);
		southPanelMovie.add(btnEditMovie); southPanelMovie.add(btnDeleteMovie); southPanelMovie.add(btnSaveMovie);

		moviePanel.add(southPanelMovie,BorderLayout.SOUTH);
		
		add(moviePanel);
	}
	
//	change

//	Gán danh sách từ sql
//	public void loadData() {
//		movieDAO.setList(movieDAO.getAllMovie());
//		directorDAO.setList(directorDAO.getAllDirector());
//		
//		movieDAO.sortByIdMovieASC();
//		directorDAO.sortByIdDirectorASC();
//		
//		int idAutoMovie=1, idAutoDirector=1;
//		for(Movie i: movieDAO.getList()) {
//			if(idAutoMovie==Integer.parseInt(i.getIdMovie().substring(1))) {
//				idAutoMovie++;
//				Movie.setIdAuto(idAutoMovie);
//			}
//		}
//		for(Director j: directorDAO.getList()) {
//			if(idAutoDirector==Integer.parseInt(j.getIdDirector().substring(2))) {
//				idAutoDirector++;
//				Director.setIdAuto(idAutoDirector);
//			}
//		}
//	}	
//	Đưa dữ liệu lên bảng
//	public void loadDataTable() {
//		DateTimeFormatter format=DateTimeFormatter.ofPattern("dd-MM-yyyy");
//		modelTableMovie.setRowCount(0);
//		for(Movie i: movieDAO.getList()) {
//			Object[] obj=new Object[] {
//				i.getIdMovie(), i.getName(), directorDAO.getDirectorById(i.getDirector().getIdDirector()).getName(),
//				i.getTime()+" phút", format.format(i.getDateOfDebut()), i.getType()
//			};
//			modelTableMovie.addRow(obj);
//		}
//	}
	
//	Choose File
//	public void openFile() {
//		fileChooser=new JFileChooser("D:\\DeadlineIUH\\BaiTapNhom\\BaiTapNhom_OOPE\\Movies\\src\\image\\poster");
//		int actionResult=fileChooser.showOpenDialog(this);
//		if(actionResult==fileChooser.APPROVE_OPTION) {
//			String path=fileChooser.getSelectedFile().getAbsolutePath();
//			var res=path.split("\\\\");
//			poster=res[res.length-1].split("\\.")[0];
//			String extension = res[res.length-1].split("\\.")[1];
//			Pattern pattern=Pattern.compile("(png|jpg|gif)$",Pattern.CASE_INSENSITIVE);
//			if(pattern.matcher(extension).matches()) {
//				if(posterPanel.getComponents()!=null) {
//					posterPanel.removeAll();
//					posterPanel.revalidate();
//					posterPanel.repaint();	
//				}
//				
//				ImageIcon imageIcon=new ImageIcon(path);
//				Image image=imageIcon.getImage().getScaledInstance(340, 95, Image.SCALE_SMOOTH);
//				JLabel poster=new JLabel(); poster.setIcon(new ImageIcon(image));
//				posterPanel.add(poster);
//				posterPanel.revalidate();
//				posterPanel.repaint();			
//			}
//			else {
//				JOptionPane.showMessageDialog(rootPane, "Không phải file ảnh");
//			}
//		}
//	}
	
//	Reset Movie
//	public void resetMovie() {
//		nameMovieText.setText("");
//		idMovieText.setText("");
//		directorText.setText("");
//		typeMovieText.setSelectedIndex(0);
//		timeMovieText.setText("");
//		modelDateMovie.setValue(new Date());
//		
//		posterPanel.removeAll();
//		posterPanel.revalidate();
//		posterPanel.repaint();
//		
//		poster="";
//		
//		movieDAO.setList(movieDAO.getAllMovie());
//		loadDataTable();
//	}
	
//	Add Movie
//	public void addMovie() {
//		if(!idMovieText.getText().isEmpty()) {
//			JOptionPane.showMessageDialog(rootPane, "Phim đã tồn tại");
//			return;
//		}
//		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
//		
//		String nameMovie=nameMovieText.getText();
//		String idMovie="P"+Movie.getIdAuto(); Movie.setIdAuto(Movie.getIdAuto()+1);
//		String director=directorText.getText();
//		String typeMovie=typeMovieText.getSelectedItem().toString();
//		String time=timeMovieText.getText();
//		LocalDate dateofDebut=LocalDate.parse(format.format(modelDateMovie.getValue()));
//		String posterMovie=poster;
//		
//		if(!nameMovie.isEmpty() && !director.isEmpty() && !time.isEmpty()) {
//			var check=new FilterImp();
//			try {
//				if(check.checkName(nameMovie) && check.checkName(director) && check.checkTimeMovie(time) && check.checkDateOfDebut(dateofDebut)) {
//					if(posterMovie!=null && !posterMovie.equals("")) {
//						Director res=directorDAO.getDirectorByName(director);
//						Movie movie;
//						if(res!=null) {
//							movie=new Movie(idMovie, nameMovie, Integer.parseInt(time), dateofDebut, typeMovie, res, posterMovie);
//						}
//						else {
//							Director tmp=new Director("DD"+Director.getIdAuto(),director,null);
//							Director.setIdAuto(Director.getIdAuto()+1);
//							for(Director d: directorDAO.getAllDirector()) {
//								if(d.getIdDirector().equals(tmp.getIdDirector())) {
//									tmp.setIdDirector("DD"+Director.getIdAuto());
//									Director.setIdAuto(Director.getIdAuto()+1);
//								}
//							}
//							directorDAO.create(tmp);
//							directorDAO.setList(directorDAO.getAllDirector());
//							movie=new Movie(idMovie, nameMovie, Integer.parseInt(time), dateofDebut, typeMovie, tmp, posterMovie);
//						}
//						if(movieDAO.getMovieByName(movie.getName())!=null) {
//							JOptionPane.showMessageDialog(rootPane, "Tên phim đã tồn tại");
//							Movie.setIdAuto(Movie.getIdAuto()-1);
//						}
//						else {
//							for(Movie m: movieDAO.getAllMovie()) {
//								if(m.getIdMovie().equals(movie.getIdMovie())) {
//									movie.setIdMovie("P"+Movie.getIdAuto());
//									Movie.setIdAuto(Movie.getIdAuto()+1);
//								}
//							}
//							movieDAO.create(movie);
//							movieDAO.setList(movieDAO.getAllMovie());
//							loadDataTable();
//							resetMovie();
//							JOptionPane.showMessageDialog(rootPane, "Thêm phim thành công");
//						}
//					}
//					else {
//						JOptionPane.showMessageDialog(rootPane, "Chọn poster cho phim");
//						Movie.setIdAuto(Movie.getIdAuto()-1);
//					}
//				}
//			} catch (checkName | checkTimeMovie | checkBirthday e) {
//				// TODO Auto-generated catch block
//				JOptionPane.showMessageDialog(rootPane, e.getMessage());
//				Movie.setIdAuto(Movie.getIdAuto()-1);
//			}
//		}
//		else {
//			JOptionPane.showMessageDialog(rootPane, "Nhập đủ thông tin phim");
//			Movie.setIdAuto(Movie.getIdAuto()-1);
//		}
//	}
//	
//	Edit Movie
//	public void editMovie() {
//		int index=tableMovie.getSelectedRow();
//		if(index!=-1) {
//			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
//			
//			String nameMovie=nameMovieText.getText();
//			String idMovie=idMovieText.getText();
//			String director=directorText.getText();
//			String typeMovie=typeMovieText.getSelectedItem().toString();
//			String time=timeMovieText.getText();
//			LocalDate dateofDebut=LocalDate.parse(format.format(modelDateMovie.getValue()));
//			String posterMovie=poster;
//			
//			if(!nameMovie.isEmpty() && !director.isEmpty() && !time.isEmpty()) {
//				var check=new FilterImp();
//				try {
//					if(check.checkName(nameMovie) && check.checkName(director) && check.checkTimeMovie(time) && check.checkDateOfDebut(dateofDebut)) {
//						if(posterMovie!=null && !posterMovie.equals("")) {
//							Director res=directorDAO.getDirectorByName(director);
//							Movie movie;
//							if(res!=null) {
//								movie=new Movie(idMovie, nameMovie, Integer.parseInt(time), dateofDebut, typeMovie, res, posterMovie);
//							}
//							else {
//								Director tmp=new Director("DD"+(directorDAO.getAllDirector().size()+1),director,null);
//								directorDAO.create(tmp);
//								directorDAO.setList(directorDAO.getAllDirector());
//								movie=new Movie(idMovie, nameMovie, Integer.parseInt(time), dateofDebut, typeMovie, tmp, posterMovie);
//							}
//							movieDAO.update(movie);
//							movieDAO.setList(movieDAO.getAllMovie());
//							loadDataTable();
//							resetMovie();
//						}
//						else {
//							String tmpPoster=movieDAO.getMovieByID(idMovie).getPoster();
//							Director res=directorDAO.getDirectorByName(director);
//							Movie movie;
//							if(res!=null) {
//								movie=new Movie(idMovie, nameMovie, Integer.parseInt(time), dateofDebut, typeMovie, res, tmpPoster);
//							}
//							else {
//								Director tmp=new Director("DD"+(directorDAO.getAllDirector().size()+1),director,null);
//								directorDAO.create(tmp);
//								directorDAO.setList(directorDAO.getAllDirector());
//								movie=new Movie(idMovie, nameMovie, Integer.parseInt(time), dateofDebut, typeMovie, tmp, tmpPoster);
//							}
//							movieDAO.update(movie);
//							movieDAO.setList(movieDAO.getAllMovie());
//							loadDataTable();
//							resetMovie();
//						}
//						JOptionPane.showMessageDialog(rootPane, "Sửa phim thành công");
//					}
//				} catch (checkName | checkTimeMovie | checkBirthday e) {
//					// TODO Auto-generated catch block
//					JOptionPane.showMessageDialog(rootPane, e.getMessage());
//				}
//			}
//			else {
//				JOptionPane.showMessageDialog(rootPane, "Nhập đủ thông tin phim");
//			}
//			
//		}
//		else {
//			JOptionPane.showMessageDialog(rootPane, "Chọn phim để sửa");
//		}
//	}
//	
//	Delete Movie
//	public void deleteMovie() {
//		int index=tableMovie.getSelectedRow();
//		if(index!=-1) {
//			var check=JOptionPane.showConfirmDialog(rootPane, "Có chắc chắn xóa");
//			if(check==JOptionPane.OK_OPTION) {
//				movieDAO.delete(modelTableMovie.getValueAt(index, 0).toString());
//				movieDAO.setList(movieDAO.getAllMovie());
//				int idAutoCurrent=1;
//				for(Movie m: movieDAO.getList()) {
//					if(Integer.parseInt(m.getIdMovie().substring(1))==idAutoCurrent) {
//						idAutoCurrent++;
//						Movie.setIdAuto(idAutoCurrent);
//					}
//				}
//				loadDataTable();
//				resetMovie();
//				JOptionPane.showMessageDialog(rootPane, "Xóa phim thành công");
//			}
//		}
//		else {
//			JOptionPane.showMessageDialog(rootPane, "Chọn phim để xóa");
//		}
//	}
	
// 	Listener
//	public void addActionListener() {
////		menu nhân viên
//		itemListEmployee.addActionListener(this);
//		itemUpdateEmployee.addActionListener(this);
//		
////		menu khách hàng
//		itemListCustomer.addActionListener(this);
//		itemUpdateCustomer.addActionListener(this);
//		
////		menu phim
//		itemListMovie.addActionListener(this);
//		itemUpdateMovie.addActionListener(this);
//		itemDirectorMovie.addActionListener(this);
//		itemScreeningMovie.addActionListener(this);
//		
////		menu vé
//		itemBookTicket.addActionListener(this);
//		
////		menu hóa đơn
//		itemListBill.addActionListener(this);
//		
////		menu thống kê
//		itemCustomerCount.addActionListener(this);
//		itemEmployeeCount.addActionListener(this);
//		itemMovieCount.addActionListener(this);
//		itemTotalCount.addActionListener(this);
//		
////		menu tài khoản
//		itemLogout.addActionListener(this);
//		itemHome.addActionListener(this);
//		
////		other
//		btnPoster.addActionListener(this);
//		btnResetMovie.addActionListener(this);
//		btnAddMovie.addActionListener(this);
//		btnEditMovie.addActionListener(this);
//		btnDeleteMovie.addActionListener(this);
//		btnSaveMovie.addActionListener(this);
//		
//	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
//		var obj=e.getSource();
//		if(obj.equals(itemListEmployee)) {
//			new DSNhanVienFrame(userName).setVisible(true);
//			this.dispose();
//		}
//		else if(obj.equals(itemUpdateEmployee)) {
//			new CapNhatNhanVienFrame(userName).setVisible(true);
//			this.dispose();
//		}
//		else if(obj.equals(itemListCustomer)) {
//			new DSUngVienFrame(userName).setVisible(true);
//			this.dispose();
//		}
//		else if(obj.equals(itemUpdateCustomer)) {
//			new CapNhatUngVienFrame(userName).setVisible(true);
//			this.dispose();
//		}
//		else if(obj.equals(itemListMovie)) {
//			new ListMovieFrame(userName).setVisible(true);
//			this.dispose();
//		}
//		else if(obj.equals(itemUpdateMovie)) {
//			new NhaTuyenDungFrame(userName).setVisible(true);
//			this.dispose();
//		}
//		else if(obj.equals(itemDirectorMovie)) {
//			new DirectorFrame(userName).setVisible(true);
//			this.dispose();
//		}
//		else if(obj.equals(itemScreeningMovie)) {
//			new ScreeningMovieFrame(userName).setVisible(true);
//			this.dispose();
//		}
//		else if(obj.equals(itemBookTicket)) {
//			new BookTicketFrame(userName).setVisible(true);
//			this.dispose();
//		}
//		else if(obj.equals(itemListBill)) {
//			new ListBillFrame(userName).setVisible(true);
//			this.dispose();
//		}
//		else if(obj.equals(itemCustomerCount)) {
//			new CustomerCountFrame(userName).setVisible(true);
//			this.dispose();
//		}
//		else if(obj.equals(itemEmployeeCount)) {
//			new EmployeeCountFrame(userName).setVisible(true);
//			this.dispose();
//		}
//		else if(obj.equals(itemMovieCount)) {
//			new MovieCountFrame(userName).setVisible(true);
//			this.dispose();
//		}
//		else if(obj.equals(itemTotalCount)) {
//			new TurnoverCountFrame(userName).setVisible(true);
//			this.dispose();
//		}
//		else if(obj.equals(itemHome)) {
//			new MainFrame(userName).setVisible(true);
//			this.dispose();
//		}
//		else if(obj.equals(itemLogout)) {
//			new LoginFrame().setVisible(true);
//			this.dispose();
//		}
//		else if(obj.equals(btnPoster)) {
//			openFile();
//		}
//		else if(obj.equals(btnResetMovie)) {
//			resetMovie();
//		}
//		else if(obj.equals(btnAddMovie)) {
//			addMovie();
//		}
//		else if(obj.equals(btnEditMovie)) {
//			editMovie();
//		}
//		else if(obj.equals(btnDeleteMovie)) {
//			deleteMovie();
//		}
//		else if(obj.equals(btnSaveMovie)) {
//			ExcelHelper export=new ExcelHelper();
//			export.exportData(this, tableMovie);
//		}
	}
	
//	public void addMouseListener() {
//		tableMovie.addMouseListener(this);
//	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
//		var index=tableMovie.getSelectedRow();
//		if(index!=-1) {
//			idMovieText.setText(modelTableMovie.getValueAt(index, 0).toString());
//			nameMovieText.setText(modelTableMovie.getValueAt(index, 1).toString());
//			directorText.setText(modelTableMovie.getValueAt(index, 2).toString());
//			timeMovieText.setText(modelTableMovie.getValueAt(index, 3).toString());
//			SimpleDateFormat format=new SimpleDateFormat("dd-MM-yyyy");
//			try {
//				Date date=format.parse(modelTableMovie.getValueAt(index, 4).toString());
//				modelDateMovie.setValue(date);
//			} catch (ParseException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			for(int i=0;i<typeMovieText.getItemCount();i++) {
//				String res=modelTableMovie.getValueAt(index, 5).toString();
//				if(typeMovieText.getItemAt(i).toString().equalsIgnoreCase(res)) {
//					typeMovieText.setSelectedIndex(i);
//					break;
//				}
//			}
//			String namePoster=movieDAO.getMovieByID(modelTableMovie.getValueAt(index, 0).toString()).getPoster();
//			if(getClass().getResource("/image/poster/"+namePoster+".png")!=null) {
//				if(posterPanel.getComponents()!=null) {
//					posterPanel.removeAll();
//					posterPanel.revalidate();
//					posterPanel.repaint();	
//				}
//				
//				ImageIcon imageIcon=new ImageIcon(getClass().getResource("/image/poster/"+namePoster+".png"));
//				Image image=imageIcon.getImage().getScaledInstance(340, 95, Image.SCALE_SMOOTH);
//				JLabel poster=new JLabel(); poster.setIcon(new ImageIcon(image));
//				posterPanel.add(poster);
//				posterPanel.revalidate();
//				posterPanel.repaint();				
//			}
//			else {
//				posterPanel.removeAll();
//				posterPanel.revalidate();
//				posterPanel.repaint();	
//			}
//		}
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
