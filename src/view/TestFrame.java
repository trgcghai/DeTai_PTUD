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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import controller.ComboBoxRenderer;
import controller.Database;
import controller.ExcelHelper;
import controller.FilterImp;
import controller.LabelDateFormatter;
import dao.TaiKhoan_DAO;
import dao.NhanVien_DAO;
import entity.TaiKhoan;
import entity.Customer;
import entity.NhanVien;
import exception.checkBirthday;
import exception.checkDateOfWork;
import exception.checkName;
import exception.checkPhone;
import exception.checkUserName;
import exception.checkUserPass;

public class TestFrame extends JFrame implements ActionListener, MouseListener, FocusListener {
//	Thanh menu
	JMenuBar menuBar;
	JPanel imgMain;
	String userName;
//	Nhân viên
	JMenu menuNhanVien;
//	Tài khoản
	JMenu menuTaiKhoan;
//	Ứng viên
	JMenu menuUngVien;
// 	Hồ sơ
	JMenu menuHoSo;
// 	Nhà tuyển dụng
	JMenu menuNhaTuyenDung;
// 	Tin tuyển dụng
	JMenu menuTinTuyenDung;
// 	Hợp đồng
	JMenu menuHopDong;
//	Tìm việc làm
	JMenu menuTimViecLam;
// 	Thống kê
	JMenu menuThongKe;
//	JMenuItem itemTKNhanVien, itemTKCongTy, itemTKHoSo, itemTKTinTuyenDung;
// 	Hệ thống
	JMenu menuUser;
	JMenuItem itemHome, itemLogout;
	
//	Component danh sách nhân viên
	JPanel leftPanel,menuPanel,
		nhanvienPanel,northPanelNhanVien, centerPanelNhanVien, timkiemPanel,
		danhsachPanel, danhsachNorthPanel, danhsachCenterPanel;
	JLabel userLabel, iconUserLabel,
		timkiemTenLabel, timkiemSDTLabel, titleNhanVien,
		vaitroLeftLabel,
		idNVLabel, nameLabel, dateLabel, addressLabel, phoneLabel, dateWorkLabel, genderLabel, passLabel, vaitroLabel;
	JTextField timkiemTenText, timkiemSDTText,
		idNVText, nameText, addressText, phoneText, userNameText, passText;
	JDatePickerImpl dateText, dateWorkText;
	UtilDateModel modelDate, modelDateWork;
	JComboBox genderText, timkiemText, timkiem;
	JButton btnTimKiem, btnLamLai,
		btnResetEmp, btnThem, btnEditEmp, btnDeleteEmp, btnSaveEmp, btnSearch;
	JTable tableNhanVien;
	DefaultTableModel modelTableNhanVien;
	JScrollPane scrollNhanVien;
	Icon iconBtnAdd;
	
	private NhanVien_DAO empDAO;
	private TaiKhoan_DAO accountDAO;
	
	
	public TestFrame(String userName) {
		setTitle("Nhân viên");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		this.userName=userName;
		
//		Tạo menu bar bên trái
		initMenu();
		initLeft();
		
//		Tạo component bên phải
		initComponent();
		
//		Thêm vào frame
		add(leftPanel, BorderLayout.WEST);
		add(nhanvienPanel, BorderLayout.CENTER);
		
//		addActionListener();
//		addMouseListener();
		addFocusListener();
		
//		Database.getInstance().connect();
//		empDAO=new NhanVien_DAO();
//		accountDAO=new TaiKhoan_DAO();

//		loadData();
//		loadDataTable();
	}
	
	public void initMenu() {
		menuBar=new JMenuBar();
		menuBar.setLayout(new GridLayout(0, 1));
		menuBar.setBackground(Color.WHITE);
		
		menuNhanVien=new JMenu("Nhân viên");  
		menuNhanVien.setFont(new Font("Segoe UI",0,16)); 
		menuNhanVien.setIcon(new ImageIcon(getClass().getResource("/image/nhanvien.png")));
		
		menuTaiKhoan=new JMenu("Tài khoản"); menuTaiKhoan.setFont(new Font("Segoe UI",0,16));
		menuTaiKhoan.setIcon(new ImageIcon(getClass().getResource("/image/taikhoan.png")));
		
		menuUngVien=new JMenu("Ứng viên");
		menuUngVien.setFont(new Font("Segoe UI",0,16)); 
		menuUngVien.setIcon(new ImageIcon(getClass().getResource("/image/ungvien.png")));
		
		menuHoSo=new JMenu("Hồ sơ");
		menuHoSo.setFont(new Font("Segoe UI",0,16)); 
		menuHoSo.setIcon(new ImageIcon(getClass().getResource("/image/hoso.png")));
		
		menuNhaTuyenDung=new JMenu("Nhà tuyển dụng");
		menuNhaTuyenDung.setFont(new Font("Segoe UI",0,16));
		menuNhaTuyenDung.setIcon(new ImageIcon(getClass().getResource("/image/nhatuyendung.png")));
		
		menuTinTuyenDung=new JMenu("Tin tuyển dụng");
		menuTinTuyenDung.setFont(new Font("Segoe UI",0,16)); 
		menuTinTuyenDung.setIcon(new ImageIcon(getClass().getResource("/image/tintuyendung.png")));
		
		menuHopDong=new JMenu("Hợp đồng");
		menuHopDong.setFont(new Font("Segoe UI",0,16));
		menuHopDong.setIcon(new ImageIcon(getClass().getResource("/image/hopdong.png")));
		
		menuTimViecLam=new JMenu("Tìm việc làm");
		menuTimViecLam.setFont(new Font("Segoe UI",0,16));
		menuTimViecLam.setIcon(new ImageIcon(getClass().getResource("/image/timviec.png")));
		
		menuThongKe=new JMenu("Thống kê");
		menuThongKe.setFont(new Font("Segoe UI",0,16));
		menuThongKe.setIcon(new ImageIcon(getClass().getResource("/image/thongke.png")));
		
		menuBar.add(menuNhanVien);
		menuBar.add(menuTaiKhoan);
		menuBar.add(menuUngVien);
		menuBar.add(menuHoSo);
		menuBar.add(menuNhaTuyenDung);
		menuBar.add(menuTinTuyenDung);
		menuBar.add(menuHopDong);
		menuBar.add(menuTimViecLam);
		menuBar.add(menuThongKe);
	}
	
	public void initLeft() {
		leftPanel=new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		leftPanel.setBackground(Color.WHITE);
		
		vaitroLeftLabel=new JLabel("ADMIN", SwingConstants.CENTER);
		vaitroLeftLabel.setFont(new Font("Segoe UI",0,16));
		vaitroLeftLabel.setPreferredSize(new Dimension(getWidth(), 50));
		
		JPanel res= new JPanel();
		res.setPreferredSize(new Dimension(getWidth(),400));
		res.setBackground(Color.WHITE);
		
		menuPanel=new JPanel(); 
		menuPanel.setLayout(new BorderLayout()); menuPanel.setBackground(Color.WHITE);
		menuPanel.add(vaitroLeftLabel, BorderLayout.NORTH);
		menuPanel.add(menuBar, BorderLayout.CENTER);
		menuPanel.add(res, BorderLayout.SOUTH);
		
		leftPanel.add(menuPanel);
	}
	
	public void initComponent() {
		nhanvienPanel=new JPanel(); 
		nhanvienPanel.setLayout(new BorderLayout(5,5));
		nhanvienPanel.setBackground(new Color(220, 220, 220));
		
//		Hiển thị tài khoản
		northPanelNhanVien=new JPanel();
		northPanelNhanVien.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
		northPanelNhanVien.setBackground(new Color(220, 220, 220));
		
		userLabel=new JLabel();
		userLabel.setFont(new Font("Segoe UI",0,16));
		userLabel.setText("Welcome "+userName);
		iconUserLabel=new JLabel();
		iconUserLabel.setIcon(new ImageIcon(getClass().getResource("/image/user.png")));
		
		northPanelNhanVien.add(userLabel); northPanelNhanVien.add(iconUserLabel);
		
//		Hiển thị tìm kiếm và danh sách nhân viên
		centerPanelNhanVien=new JPanel();
		centerPanelNhanVien.setLayout(new BorderLayout(10, 10));
		centerPanelNhanVien.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		centerPanelNhanVien.setBackground(new Color(220, 220, 220));
//		Tìm kiếm nhân viên
		timkiemPanel=new JPanel();
		timkiemPanel.setBackground(Color.WHITE);
		timkiemPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 5));
		
		timkiemTenLabel=new JLabel("Tên:"); timkiemTenLabel.setFont(new Font("Segoe UI",0,16));
		timkiemTenText=new JTextField(15); timkiemTenText.setFont(new Font("Segoe UI",0,16));
		timkiemSDTLabel=new JLabel("Số điện thoại:"); timkiemSDTLabel.setFont(new Font("Segoe UI",0,16));
		timkiemSDTText=new JTextField(15); timkiemSDTText.setFont(new Font("Segoe UI",0,16));
		
		btnTimKiem=new JButton("Tìm kiếm"); btnTimKiem.setFont(new Font("Segoe UI",0,16));
		btnTimKiem.setPreferredSize(new Dimension(120,25));
		btnTimKiem.setBackground(new Color(0,102,102));
		btnTimKiem.setForeground(Color.WHITE);
		btnLamLai=new JButton("Làm lại"); btnLamLai.setFont(new Font("Segoe UI",0,16));
		btnLamLai.setPreferredSize(new Dimension(120,25));
		btnLamLai.setBackground(Color.RED);
		btnLamLai.setForeground(Color.WHITE);
		
		timkiemPanel.add(timkiemTenLabel); timkiemPanel.add(timkiemTenText);
		timkiemPanel.add(timkiemSDTLabel); timkiemPanel.add(timkiemSDTText);
		timkiemPanel.add(btnTimKiem); timkiemPanel.add(btnLamLai);
//		Danh sách nhân viên
		danhsachPanel=new JPanel();
		danhsachPanel.setBackground(Color.WHITE);
		danhsachPanel.setLayout(new BorderLayout(10, 10));
		
		danhsachNorthPanel=new JPanel();
		danhsachNorthPanel.setLayout(new BorderLayout(10,10));
		danhsachNorthPanel.setBackground(Color.WHITE);
		iconBtnAdd=new ImageIcon(getClass().getResource("/image/add.png"));
		JPanel resBtnThem=new JPanel();
		resBtnThem.setBorder(BorderFactory.createEmptyBorder(10,10,0,20));
		resBtnThem.setBackground(Color.WHITE);
		btnThem=new JButton("Thêm mới", iconBtnAdd); btnThem.setFont(new Font("Segoe UI",0,16));
		btnThem.setPreferredSize(new Dimension(140,30));
		btnThem.setBackground(new Color(0,102,102));
		btnThem.setForeground(Color.WHITE);
		resBtnThem.add(btnThem);
		titleNhanVien=new JLabel("Danh sách nhân viên");
		titleNhanVien.setFont(new Font("Segoe UI",1,15));
		titleNhanVien.setBorder(BorderFactory.createEmptyBorder(10,20,0,10));
		danhsachNorthPanel.add(titleNhanVien, BorderLayout.WEST);
		danhsachNorthPanel.add(resBtnThem, BorderLayout.EAST);
		
		danhsachCenterPanel=new JPanel();
		danhsachCenterPanel.setLayout(new BoxLayout(danhsachCenterPanel, BoxLayout.PAGE_AXIS));
		danhsachCenterPanel.setBackground(Color.WHITE);
		Icon updateIcon=new ImageIcon(getClass().getResource("/image/update.png"));
		Icon deleteIcon=new ImageIcon(getClass().getResource("/image/delete.png"));
		String[] colName= {"Mã nhân viên","Tên nhân viên","Số điện thoại","Ngày vào làm","Vai trò","Hành động"};
		Object[][] data = {
			    {1, "MinhDat", "01234567", "13/12/2003", "Admin", updateIcon},
			    {2, "ThangDat", "07654321", "13/12/2003", "Nhân viên",deleteIcon}
			};
		modelTableNhanVien= new DefaultTableModel(data, colName){
			
            public Class getColumnClass(int column)
            {
                return getValueAt(0, column).getClass();
            }
            
            @Override
            public boolean isCellEditable(int row, int column) {
               //all cells false
               return false;
            }
        };
		tableNhanVien=new JTable(modelTableNhanVien);
		tableNhanVien.getTableHeader().setFont(new Font("Segoe UI",1,14));
		tableNhanVien.setFont(new Font("Segoe UI",0,16));
		tableNhanVien.setRowHeight(30);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		for(int i=0;i<tableNhanVien.getColumnCount()-1;i++) {
			tableNhanVien.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);			
		}
		tableNhanVien.setAutoCreateRowSorter(true);
		ArrayList<RowSorter.SortKey> list = new ArrayList<>();
        DefaultRowSorter sorter = ((DefaultRowSorter)tableNhanVien.getRowSorter());
        sorter.setSortsOnUpdates(true);
        list.add( new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sorter.setSortKeys(list);
        sorter.sort();
		scrollNhanVien=new JScrollPane(tableNhanVien);
		scrollNhanVien.setBorder(BorderFactory.createLineBorder(new Color(0,191,165)));
		JPanel resScroll=new JPanel();
		resScroll.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));
		resScroll.setLayout(new BoxLayout(resScroll, BoxLayout.PAGE_AXIS));
		resScroll.setBackground(Color.WHITE);
		resScroll.add(scrollNhanVien);
		danhsachCenterPanel.add(resScroll);
		
		danhsachPanel.add(danhsachNorthPanel, BorderLayout.NORTH);
		danhsachPanel.add(danhsachCenterPanel, BorderLayout.CENTER);
		
		centerPanelNhanVien.add(timkiemPanel, BorderLayout.NORTH);
		centerPanelNhanVien.add(danhsachPanel, BorderLayout.CENTER);
		
		
		nhanvienPanel.add(northPanelNhanVien, BorderLayout.NORTH);
		nhanvienPanel.add(centerPanelNhanVien, BorderLayout.CENTER);
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
		font=font.deriveFont(Font.PLAIN);
		text.setFont(font);
		text.setForeground(Color.BLACK);
	}
	
//	
//	public void initComponent() {
//		nhanvienPanel=new JPanel(); 
//		nhanvienPanel.setLayout(new BorderLayout(5,5));
//		
////		Hiển thị thông tin
//		northPanelEmp=new JPanel();  northPanelEmp.setLayout(new BorderLayout());
//		northPanelEmp.setPreferredSize(new Dimension(getWidth(),130));
//		inforEmpPanel=new JPanel(); inforEmpPanel.setLayout(new GridBagLayout());
//		inforEmpPanel.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(0,191,165)), "Thông tin nhân viên",
//				TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI",1,14)));
//		GridBagConstraints gbc= new GridBagConstraints();
//		
//		gbc.gridx=0; gbc.gridy=0; gbc.insets=new Insets(5, 3, 5, 3); 
//		gbc.anchor=GridBagConstraints.EAST;
//		nameLabel=new JLabel("Họ tên:"); nameLabel.setFont(new Font("Segoe UI",1,14));
//		inforEmpPanel.add(nameLabel, gbc);
//		gbc.gridx=1; gbc.gridwidth=3; gbc.anchor=GridBagConstraints.WEST;
//		nameText=new JTextField(31); nameText.setFont(new Font("Segoe UI",1,14));
//		inforEmpPanel.add(nameText, gbc);
//		
//		gbc.gridx=4; gbc.gridy=0; gbc.gridwidth=1;
//		idNVLabel=new JLabel("Mã nhân viên:"); idNVLabel.setFont(new Font("Segoe UI",1,14));
//		inforEmpPanel.add(idNVLabel, gbc);
//		gbc.gridx=5; gbc.anchor=GridBagConstraints.WEST;
//		idNVText=new JTextField(10); idNVText.setFont(new Font("Segoe UI",1,14));
//		idNVText.setEditable(false);
//		inforEmpPanel.add(idNVText, gbc);
//		
//		gbc.gridx=0; gbc.gridy=1; gbc.anchor=GridBagConstraints.EAST;
//		dateLabel=new JLabel("Ngày sinh:"); dateLabel.setFont(new Font("Segoe UI",1,14));
//		inforEmpPanel.add(dateLabel, gbc);
//		gbc.gridx=1; gbc.anchor=GridBagConstraints.WEST;
//		modelDate=new UtilDateModel();
//		Properties p=new Properties();
//		p.put("text.day", "Day"); p.put("text.month", "Month"); p.put("text.year", "Year");
//		JDatePanelImpl panelDate=new JDatePanelImpl(modelDate, p);
//		dateText=new JDatePickerImpl(panelDate, new LabelDateFormatter());
//		dateText.setPreferredSize(new Dimension(150,20));
//		modelDate.setValue(new Date());
//		inforEmpPanel.add(dateText, gbc);
//		
//		gbc.gridx=2;
//		genderLabel=new JLabel("Giới tính:"); genderLabel.setFont(new Font("Segoe UI",1,14));
//		inforEmpPanel.add(genderLabel, gbc);
//		gbc.gridx=3; gbc.anchor=GridBagConstraints.WEST;
//		String[] genders= {"Nam", "Nữ"};
//		genderText=new JComboBox(genders); genderText.setFont(new Font("Segoe UI",1,14));
//		genderText.setPreferredSize(new Dimension(147,20));
//		inforEmpPanel.add(genderText, gbc);
//		
//		gbc.gridx=4;
//		addressLabel=new JLabel("Địa chỉ:"); addressLabel.setFont(new Font("Segoe UI",1,14));
//		inforEmpPanel.add(addressLabel, gbc);
//		gbc.gridx=5;
//		addressText=new JTextField(10); addressText.setFont(new Font("Segoe UI",1,14));
//		inforEmpPanel.add(addressText, gbc);
//		
//		gbc.gridx=0; gbc.gridy=2;
//		phoneLabel=new JLabel("Số điện thoại:"); phoneLabel.setFont(new Font("Segoe UI",1,14));
//		inforEmpPanel.add(phoneLabel, gbc);
//		gbc.gridx=1; gbc.anchor=GridBagConstraints.WEST;
//		phoneText=new JTextField(11); phoneText.setFont(new Font("Segoe UI",1,14));
//		inforEmpPanel.add(phoneText, gbc);
//		
//		gbc.gridx=2;
//		dateWorkLabel=new JLabel("Ngày vào làm:"); dateWorkLabel.setFont(new Font("Segoe UI",1,14));
//		inforEmpPanel.add(dateWorkLabel,gbc);
//		gbc.gridx=3;
//		modelDateWork=new UtilDateModel();
//		JDatePanelImpl panelDateWork=new JDatePanelImpl(modelDateWork, p);
//		dateWorkText=new JDatePickerImpl(panelDateWork, new LabelDateFormatter());
//		dateWorkText.setPreferredSize(new Dimension(150,20));
//		modelDateWork.setValue(new Date());
//		inforEmpPanel.add(dateWorkText,gbc);
//		
//		northPanelEmp.add(inforEmpPanel, BorderLayout.WEST);
//		
////		Tài khoản nhân viên
//		inforAccountPanel=new JPanel();
//		inforAccountPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
//		inforAccountPanel.setPreferredSize(new Dimension(270,100));
//		inforAccountPanel.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(0,191,165)), "Tài khoản nhân viên",
//				TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI",1,14)));
//		
//		userNameLabel=new JLabel("Tên đăng nhập:"); userNameLabel.setFont(new Font("Segoe UI",1,13));
//		userNameLabel.setPreferredSize(new Dimension(150,20));
//		vaitroLabel=new JLabel("Admin"); 
//		vaitroLabel.setFont(new Font("Segoe UI",1,15)); vaitroLabel.setForeground(Color.RED);
//		userNameText=new JTextField(20); userNameText.setFont(new Font("Segoe UI",1,13)); userNameText.setEditable(false);
//		inforAccountPanel.add(userNameLabel);
//		inforAccountPanel.add(vaitroLabel);
//		inforAccountPanel.add(userNameText);
//		
//		passLabel=new JLabel("Mật khẩu:"); passLabel.setFont(new Font("Segoe UI",1,13));
//		passText=new JTextField(20); passText.setFont(new Font("Segoe UI",1,13)); passText.setEditable(false);
//		inforAccountPanel.add(passLabel);
//		inforAccountPanel.add(passText);
//		
//		northPanelEmp.add(inforAccountPanel, BorderLayout.EAST);
//		
//		nhanvienPanel.add(northPanelEmp,BorderLayout.NORTH);
//		
////		Tìm kiếm
//		panelCenter=new JPanel(); panelCenter.setLayout(new BorderLayout(5,5));
//		panelSearch=new JPanel();
//		
//		timkiemText=new JComboBox(); timkiemText.setFont(new Font("Segoe UI",1,14));
//		String[] timkiems= {"Tìm kiếm theo tên", "Tìm kiếm theo mã nhân viên",
//				"Tìm kiếm theo số điện thoại"};
//		timkiem=new JComboBox(timkiems); timkiem.setFont(new Font("Segoe UI",1,14));
//		timkiem.setPreferredSize(new Dimension(300,25));
//		timkiem.setRenderer(new ComboBoxRenderer("Tìm kiếm nhân viên"));
//		timkiem.setSelectedIndex(-1);
//		btnSearch=new JButton("Tìm"); btnSearch.setFont(new Font("Segoe UI",1,14));
//		btnSearch.setPreferredSize(new Dimension(80,25));
//		btnSearch.setBackground(new Color(0,102,102));
//		btnSearch.setForeground(Color.WHITE);
//		
//		panelSearch.add(timkiemText); panelSearch.add(timkiem); panelSearch.add(btnSearch);
//		panelCenter.add(panelSearch, BorderLayout.NORTH);
//		
////		Table nhân viên
//		String[] colNameEmp= {"Mã nhân viên","Tên nhân viên","Giới tính","Ngày sinh","Địa chỉ",
//				"Số điện thoại","Ngày vào làm"};
//		modelTableEmp= new DefaultTableModel(colNameEmp,0);
//		tableEmp=new JTable(modelTableEmp);
//		tableEmp.getTableHeader().setFont(new Font("Segoe UI",1,14));
//		tableEmp.setFont(new Font("Segoe UI",1,14));
//		tableEmp.setAutoCreateRowSorter(true);
//		ArrayList<RowSorter.SortKey> list = new ArrayList<>();
//        DefaultRowSorter sorter = ((DefaultRowSorter)tableEmp.getRowSorter());
//        sorter.setSortsOnUpdates(true);
//        list.add( new RowSorter.SortKey(0, SortOrder.ASCENDING));
//        sorter.setSortKeys(list);
//        sorter.sort();
//		scrollEmp=new JScrollPane(tableEmp);
//		scrollEmp.setBorder(BorderFactory.createLineBorder(new Color(0,191,165)));
//		
//		panelCenter.add(scrollEmp, BorderLayout.CENTER);
//		
//		nhanvienPanel.add(panelCenter,BorderLayout.CENTER);
//		
////		Các nút chức năng trong tab nhân viên
//		btnResetEmp=new JButton("Làm mới"); btnResetEmp.setFont(new Font("Segoe UI",1,14)); btnResetEmp.setPreferredSize(new Dimension(145, 30));
//		btnAddEmp=new JButton("Thêm nhân viên"); btnAddEmp.setFont(new Font("Segoe UI",1,14)); btnAddEmp.setPreferredSize(new Dimension(145, 30));
//		btnEditEmp=new JButton("Sửa nhân viên"); btnEditEmp.setFont(new Font("Segoe UI",1,14)); btnEditEmp.setPreferredSize(new Dimension(145, 30));
//		btnDeleteEmp=new JButton("Xóa nhân viên"); btnDeleteEmp.setFont(new Font("Segoe UI",1,14)); btnDeleteEmp.setPreferredSize(new Dimension(145, 30));
//		btnSaveEmp=new JButton("Xuất danh sách"); btnSaveEmp.setFont(new Font("Segoe UI",1,14)); btnSaveEmp.setPreferredSize(new Dimension(145, 30));
//		
//		southPanelEmp=new JPanel();
//		southPanelEmp.add(btnResetEmp); southPanelEmp.add(btnAddEmp);
//		southPanelEmp.add(btnEditEmp); southPanelEmp.add(btnDeleteEmp); southPanelEmp.add(btnSaveEmp);
//
//		nhanvienPanel.add(southPanelEmp,BorderLayout.SOUTH);
//	}

//	Change
//	Gán danh sách từ sql
//	public void loadData() {
//		empDAO.setListEmp(empDAO.getAllEmp());
//		accountDAO.setListAccount(accountDAO.getAllAccount());
//		
//		empDAO.sortByIdEmployeeASC();
//		int idAuto=1;
//		for(NhanVien i: empDAO.getListEmp()) {
//			int id=Integer.parseInt(i.getIdEmployee().substring(2));
//			if(idAuto==id) {
//				idAuto++;
//				NhanVien.setIdAuto(idAuto);
//			}
//		}
//	}
	
//	Đưa dữ liệu lên bảng
//	public void loadDataTable() {
//		modelTableEmp.setRowCount(0);
//		DateTimeFormatter format=DateTimeFormatter.ofPattern("dd-MM-yyyy");
//		for(NhanVien i: empDAO.getAllEmp()) {
//			var birthday=format.format(i.getBirthday());
//			var dateOfWork=format.format(i.getDateOfWork());
//			Object[] obj=new Object[] {
//					i.getIdEmployee(), i.getName(), (i.getGender()==1?"Nam":"Nữ"),
//					birthday, i.getAddress(), i.getPhone(), dateOfWork
//			};
//			modelTableEmp.addRow(obj);
//		}
//	}
	
//	Reset Employee
//	public void resetEmp() {
//		nameText.setText("");
//		idNVText.setText("");
//		modelDate.setValue(new Date());
//		genderText.setSelectedIndex(0);
//		addressText.setText("");
//		phoneText.setText("");
//		modelDateWork.setValue(new Date());
//		userNameText.setText("");
//		passText.setText("");
//		empDAO.setListEmp(empDAO.getAllEmp());
//		loadDataTable();
//	}
	
//	Add Employee
//	public void addEmp() {
//		if(!idNVText.getText().equals("")) {
//			JOptionPane.showMessageDialog(rootPane, "Nhân viên đã tồn tại");
//			resetEmp();
//			return;
//		}
//		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
//		
//		var name=nameText.getText();
//		var birthday=format.format(modelDate.getValue());
//		var gender=genderText.getSelectedItem().toString();
//		var address=addressText.getText();
//		var phone=phoneText.getText();
//		var dateOfWork=format.format(modelDateWork.getValue());
//		var username=userNameText.getText();
//		var pass=passText.getText();
//		
//		if(!name.equals("") && !address.equals("") && !phone.equals("") && !username.equals("") && !pass.equals("")) {
//			var check=new FilterImp();
//			try {
//				if(check.checkName(name) && check.checkBirthday(LocalDate.parse(birthday)) && check.checkPhone(phone) 
//					&& check.checkDateOfWork(LocalDate.parse(dateOfWork)) && check.checkUserName(username) && check.checkUserPass(pass)) {
//					TaiKhoan account=new TaiKhoan(NhanVien.getIdAuto(), username, pass);
//					if(!accountDAO.getListAccount().contains(account)) {
//						NhanVien emp=new NhanVien("NV"+NhanVien.getIdAuto(), name, LocalDate.parse(birthday),
//								address, LocalDate.parse(dateOfWork), (gender=="Nam"?1:0), phone, account);
//						NhanVien.setIdAuto(NhanVien.getIdAuto()+1);
//						for(NhanVien e: empDAO.getAllEmp()) {
//							if(e.getIdEmployee().equalsIgnoreCase(emp.getIdEmployee())) {
//								emp.setIdEmployee("NV"+NhanVien.getIdAuto());
//								emp.getAccount().setIdAccount(NhanVien.getIdAuto());
//								NhanVien.setIdAuto(NhanVien.getIdAuto()+1);
//							}
//						}
//						accountDAO.create(emp.getAccount());
//						empDAO.create(emp);
//						accountDAO.setListAccount(accountDAO.getAllAccount());
//						empDAO.setListEmp(empDAO.getAllEmp());
//						loadDataTable();
//						JOptionPane.showMessageDialog(rootPane, "Thêm nhân viên thành công");
//						resetEmp();
//					}
//					else {
//						JOptionPane.showMessageDialog(rootPane, "Tên đăng nhập đã tồn tại");
//					}
//				}
//			} catch (checkName | checkBirthday | checkPhone | checkUserName | checkUserPass |  checkDateOfWork e) {
//				// TODO Auto-generated catch block
//				JOptionPane.showMessageDialog(rootPane, e.getMessage());
//			}
//		}
//		else {
//			JOptionPane.showMessageDialog(rootPane, "Nhập đủ thông tin nhân viên");
//		}
//	}
	
//	Edit Employee
//	public void editEmp() {
//		int index=tableEmp.getSelectedRow();
//		if(index!=-1) {
//			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
//			
//			var name=nameText.getText();
//			var id=idNVText.getText();
//			var birthday=format.format(modelDate.getValue());
//			var gender=genderText.getSelectedItem().toString();
//			var address=addressText.getText();
//			var phone=phoneText.getText();
//			var dateOfWork=format.format(modelDateWork.getValue());
//			
//			if(!name.equals("") && !address.equals("") && !phone.equals("")) {
//				var check=new FilterImp();
//				try {
//					if(check.checkName(name) && check.checkBirthday(LocalDate.parse(birthday)) && check.checkPhone(phone) 
//						&& check.checkDateOfWork(LocalDate.parse(dateOfWork))) {
//						NhanVien emp=new NhanVien(id, name, LocalDate.parse(birthday),
//								address, LocalDate.parse(dateOfWork), (gender=="Nam"?1:0), phone);
//						empDAO.update(emp);
//						empDAO.setListEmp(empDAO.getAllEmp());
//						loadDataTable();
//						JOptionPane.showMessageDialog(rootPane, "Sửa nhân viên thành công");
//						resetEmp();
//					}
//				} catch (checkName | checkBirthday | checkPhone | checkDateOfWork e) {
//					// TODO Auto-generated catch block
//					JOptionPane.showMessageDialog(rootPane, e.getMessage());
//				}
//			}
//			else {
//				JOptionPane.showMessageDialog(rootPane, "Nhập đủ thông tin nhân viên");
//			}
//		}
//		else {
//			JOptionPane.showMessageDialog(rootPane, "Chọn nhân viên để sửa");
//		}
//	}
	
//	Delete Employee
//	public void deleteEmp() {
//		int index=tableEmp.getSelectedRow();
//		if(index!=-1) {
//			var id=idNVText.getText();
//			var check=JOptionPane.showConfirmDialog(rootPane, "Chắc chắn xóa nhân viên");
//			if(check==JOptionPane.OK_OPTION) {
//				NhanVien res=new NhanVien(id);
//				int idx=empDAO.getListEmp().indexOf(res);
//				NhanVien emp=empDAO.getListEmp().get(idx);
//				accountDAO.delete(emp.getAccount());
//				accountDAO.setListAccount(accountDAO.getAllAccount());
//				empDAO.setListEmp(empDAO.getAllEmp());
//				int idAutoCurrent=1;
//				for(NhanVien i: empDAO.getListEmp()) {
//					int tmp=Integer.parseInt(i.getIdEmployee().substring(2));
//					if(idAutoCurrent==tmp) {
//						idAutoCurrent++;
//						NhanVien.setIdAuto(idAutoCurrent);
//					}
//				}
//				loadDataTable();
//				resetEmp();
//				JOptionPane.showMessageDialog(rootPane, "Xóa nhân viên thành công");
//			}
//		}
//		else {
//			JOptionPane.showMessageDialog(rootPane, "Chọn nhân viên để xóa");
//		}
//	}
	
//	Listener
	public void addFocusListener() {
		timkiemTenText.addFocusListener(this);
		timkiemSDTText.addFocusListener(this);
		
		addPlaceHolder(timkiemTenText);
		addPlaceHolder(timkiemSDTText);
	}
	
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
//		btnResetEmp.addActionListener(this);
//		btnAddEmp.addActionListener(this);
//		btnEditEmp.addActionListener(this);
//		btnDeleteEmp.addActionListener(this);
//		btnSaveEmp.addActionListener(this);
//	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
//		var obj=e.getSource();
//		if(obj.equals(itemListEmployee)) {
//			new DSNhanVienFrame(userName).setVisible(true);
//			this.dispose();
//		}
//		else if(obj.equals(itemUpdateEmployee)) {
//			new CapNhatNhanVien(userName).setVisible(true);
//			this.dispose();
//		}
//		else if(obj.equals(itemListCustomer)) {
//			new ListCustomerFrame(userName).setVisible(true);
//			this.dispose();
//		}
//		else if(obj.equals(itemUpdateCustomer)) {
//			new UpdateCustomerFrame(userName).setVisible(true);
//			this.dispose();
//		}
//		else if(obj.equals(itemListMovie)) {
//			new ListMovieFrame(userName).setVisible(true);
//			this.dispose();
//		}
//		else if(obj.equals(itemUpdateMovie)) {
//			new UpdateMovieFrame(userName).setVisible(true);
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
//		else if(obj.equals(btnResetEmp)) {
//			resetEmp();
//		}
//		else if(obj.equals(btnAddEmp)) {
//			addEmp();
//		}
//		else if(obj.equals(btnEditEmp)) {
//			editEmp();
//		}
//		else if(obj.equals(btnDeleteEmp)) {
//			deleteEmp();
//		}
//		else if(obj.equals(btnSaveEmp)) {
//			ExcelHelper excel = new ExcelHelper();
//			excel.exportData(this, tableEmp);
//		}
		
	}
	
//	public void addMouseListener() {
//		tableEmp.addMouseListener(this);
//	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
//		int index=tableEmp.getSelectedRow();
//		if(index!=-1) {
//			idNVText.setText(modelTableEmp.getValueAt(index, 0).toString());
//			nameText.setText(modelTableEmp.getValueAt(index, 1).toString());
//			for(int i=0;i<genderText.getItemCount();i++) {
//				var gender=modelTableEmp.getValueAt(index, 2).toString();
//				if(genderText.getItemAt(i).toString().equalsIgnoreCase(gender)) {
//					genderText.setSelectedIndex(i);
//					break;
//				}
//			}
//			SimpleDateFormat format=new SimpleDateFormat("dd-MM-yyyy");
//			try {
//				Date date=format.parse(modelTableEmp.getValueAt(index, 3).toString());
//				modelDate.setValue(date);
//			} catch (ParseException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			addressText.setText(modelTableEmp.getValueAt(index, 4).toString());
//			phoneText.setText(modelTableEmp.getValueAt(index, 5).toString());
//			try {
//				Date date=format.parse(modelTableEmp.getValueAt(index, 6).toString());
//				modelDateWork.setValue(date);
//			} catch (ParseException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
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

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		var obj=e.getSource();
		if(obj.equals(timkiemTenText)) {
			if(timkiemTenText.getText().equals("Nhập dữ liệu")) {
				timkiemTenText.setText(null);
				timkiemTenText.requestFocus();
				
				removePlaceHolder(timkiemTenText);
			}
		}
		else if(obj.equals(timkiemSDTText)) {
			if(timkiemSDTText.getText().equals("Nhập dữ liệu")) {
				timkiemSDTText.setText(null);
				timkiemSDTText.requestFocus();
				
				removePlaceHolder(timkiemSDTText);
			}
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		var obj=e.getSource();
		if(obj.equals(timkiemTenText)) {
			if(timkiemTenText.getText().length()==0) {
				addPlaceHolder(timkiemTenText);
				timkiemTenText.setText("Nhập dữ liệu");
			}
		}
		else if(obj.equals(timkiemSDTText)) {
			if(timkiemSDTText.getText().length()==0) {
				addPlaceHolder(timkiemSDTText);
				timkiemSDTText.setText("Nhập dữ liệu");
			}
		}
	}

}
