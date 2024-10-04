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
import java.util.Properties;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
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

public class TaiKhoanTestFrame extends JFrame implements ActionListener, MouseListener {
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
	
//	Component danh sách nhân viên
	JPanel empPanel, northPanelEmp, southPanelEmp, inforEmpPanel;
	JLabel idNVLabel, nameLabel, dateLabel, addressLabel, phoneLabel, dateWorkLabel, genderLabel;
	JTextField idNVText, nameText, addressText, phoneText;
	JDatePickerImpl dateText, dateWorkText;
	UtilDateModel modelDate, modelDateWork;
	JComboBox genderText, nhanvienCombobox;
	JButton btnResetTaiKhoan, btnAddTaiKhoan, btnEditTaiKhoan, btnDeleteTaiKhoan, btnCapTaiKhoan;
	JTable tableEmp;
	DefaultTableModel modelTableEmp;
	JScrollPane scrollEmp;
	
//	Component danh sách tài khoản
	JPanel inforAccountPanel;
	JLabel userNameLabel, passLabel, vaitroLabel, maTKLabel;
	JTextField userNameText, passText, maTKText;
	JComboBox vaitroText;
	
	private NhanVien_DAO empDAO;
	private TaiKhoan_DAO accountDAO;
	
	
	public TaiKhoanTestFrame(String userName) {
		setTitle("Danh sách tài khoản");
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
		
		Database.getInstance().connect();
		empDAO=new NhanVien_DAO();
		accountDAO=new TaiKhoan_DAO();

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
		empPanel=new JPanel(); 
		empPanel.setLayout(new BorderLayout(5,5));
		
//		Hiển thị thông tin
		northPanelEmp=new JPanel();  northPanelEmp.setLayout(new BorderLayout());
		northPanelEmp.setPreferredSize(new Dimension(getWidth(),130));
		inforEmpPanel=new JPanel(); inforEmpPanel.setLayout(new GridBagLayout());
		inforEmpPanel.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(0,191,165)), "Thông tin nhân viên",
				TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI",1,14)));
		GridBagConstraints gbc= new GridBagConstraints();
		
		gbc.gridx=0; gbc.gridy=0; gbc.insets=new Insets(5, 3, 5, 3); 
		gbc.anchor=GridBagConstraints.EAST;
		nameLabel=new JLabel("Họ tên:"); nameLabel.setFont(new Font("Segoe UI",1,14));
		inforEmpPanel.add(nameLabel, gbc);
		gbc.gridx=1; gbc.gridwidth=3; gbc.anchor=GridBagConstraints.WEST;
		nameText=new JTextField(31); nameText.setFont(new Font("Segoe UI",1,14));
		nameText.setEditable(false);
		inforEmpPanel.add(nameText, gbc);
		
		gbc.gridx=4; gbc.gridy=0; gbc.gridwidth=1;
		idNVLabel=new JLabel("Mã nhân viên:"); idNVLabel.setFont(new Font("Segoe UI",1,14));
		inforEmpPanel.add(idNVLabel, gbc);
		gbc.gridx=5; gbc.anchor=GridBagConstraints.WEST;
		idNVText=new JTextField(10); idNVText.setFont(new Font("Segoe UI",1,14));
		idNVText.setEditable(false);
		inforEmpPanel.add(idNVText, gbc);
		
		gbc.gridx=0; gbc.gridy=1; gbc.anchor=GridBagConstraints.EAST;
		dateLabel=new JLabel("Ngày sinh:"); dateLabel.setFont(new Font("Segoe UI",1,14));
		inforEmpPanel.add(dateLabel, gbc);
		gbc.gridx=1; gbc.anchor=GridBagConstraints.WEST;
		modelDate=new UtilDateModel();
		Properties p=new Properties();
		p.put("text.day", "Day"); p.put("text.month", "Month"); p.put("text.year", "Year");
		JDatePanelImpl panelDate=new JDatePanelImpl(modelDate, p);
		dateText=new JDatePickerImpl(panelDate, new LabelDateFormatter());
		dateText.setPreferredSize(new Dimension(150,20));
		modelDate.setValue(new Date());
		inforEmpPanel.add(dateText, gbc);
		
		gbc.gridx=2;
		genderLabel=new JLabel("Giới tính:"); genderLabel.setFont(new Font("Segoe UI",1,14));
		inforEmpPanel.add(genderLabel, gbc);
		gbc.gridx=3; gbc.anchor=GridBagConstraints.WEST;
		String[] genders= {"Nam", "Nữ"};
		genderText=new JComboBox(genders); genderText.setFont(new Font("Segoe UI",1,14));
		genderText.setPreferredSize(new Dimension(147,20));
		inforEmpPanel.add(genderText, gbc);
		
		gbc.gridx=4;
		addressLabel=new JLabel("Địa chỉ:"); addressLabel.setFont(new Font("Segoe UI",1,14));
		inforEmpPanel.add(addressLabel, gbc);
		gbc.gridx=5;
		addressText=new JTextField(10); addressText.setFont(new Font("Segoe UI",1,14));
		addressText.setEditable(false);
		inforEmpPanel.add(addressText, gbc);
		
		gbc.gridx=0; gbc.gridy=2;
		phoneLabel=new JLabel("Số điện thoại:"); phoneLabel.setFont(new Font("Segoe UI",1,14));
		inforEmpPanel.add(phoneLabel, gbc);
		gbc.gridx=1; gbc.anchor=GridBagConstraints.WEST;
		phoneText=new JTextField(11); phoneText.setFont(new Font("Segoe UI",1,14));
		phoneText.setEditable(false);
		inforEmpPanel.add(phoneText, gbc);
		
		gbc.gridx=2;
		dateWorkLabel=new JLabel("Ngày vào làm:"); dateWorkLabel.setFont(new Font("Segoe UI",1,14));
		inforEmpPanel.add(dateWorkLabel,gbc);
		gbc.gridx=3;
		modelDateWork=new UtilDateModel();
		JDatePanelImpl panelDateWork=new JDatePanelImpl(modelDateWork, p);
		dateWorkText=new JDatePickerImpl(panelDateWork, new LabelDateFormatter());
		dateWorkText.setPreferredSize(new Dimension(150,20));
		modelDateWork.setValue(new Date());
		inforEmpPanel.add(dateWorkText,gbc);
		
		gbc.gridx=5;
		nhanvienCombobox=new JComboBox();
		nhanvienCombobox.setRenderer(new ComboBoxRenderer("Chọn nhân viên"));
		nhanvienCombobox.setSelectedIndex(-1);
		nhanvienCombobox.setFont(new Font("Segoe UI",1,14));
		nhanvienCombobox.setPreferredSize(new Dimension(133,22));
		inforEmpPanel.add(nhanvienCombobox,gbc);
		
		
		northPanelEmp.add(inforEmpPanel, BorderLayout.WEST);
		
//		Tài khoản nhân viên
		inforAccountPanel=new JPanel();
		inforAccountPanel.setLayout(new GridBagLayout());
		inforAccountPanel.setPreferredSize(new Dimension(500,100));
		inforAccountPanel.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(0,191,165)), "Tài khoản nhân viên",
				TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI",1,14)));
		
		GridBagConstraints gbca= new GridBagConstraints();
		gbca.gridx=0; gbca.gridy=0; gbca.insets=new Insets(5, 3, 5, 3); 
		gbca.anchor=GridBagConstraints.EAST;
		maTKLabel=new JLabel("Mã tài khoản:"); maTKLabel.setFont(new Font("Segoe UI",1,14));
		inforAccountPanel.add(maTKLabel, gbca);
		
		gbca.gridx=1; gbca.anchor=GridBagConstraints.WEST;
		maTKText=new JTextField(10); maTKText.setFont(new Font("Segoe UI",1,14));
		maTKText.setEditable(false);
		inforAccountPanel.add(maTKText, gbca);
		
		gbca.gridx=2;
		gbca.anchor=GridBagConstraints.EAST;
		vaitroLabel=new JLabel("Vai trò:"); vaitroLabel.setFont(new Font("Segoe UI",1,14));
		inforAccountPanel.add(vaitroLabel, gbca);
		
		gbca.gridx=3; gbca.anchor=GridBagConstraints.WEST;
		vaitroText=new JComboBox(); vaitroText.setFont(new Font("Segoe UI",1,14));
		vaitroText.setPreferredSize(new Dimension(147,20));
		inforAccountPanel.add(vaitroText, gbca);
		
		gbca.gridx=0; gbca.gridy=1;
		gbca.anchor=GridBagConstraints.EAST;
		userNameLabel=new JLabel("Tên đăng nhập:"); userNameLabel.setFont(new Font("Segoe UI",1,14));
		inforAccountPanel.add(userNameLabel, gbca);
		
		gbca.gridx=1; gbca.anchor=GridBagConstraints.WEST; gbca.gridwidth=3;
		userNameText=new JTextField(26); userNameText.setFont(new Font("Segoe UI",1,14));
		inforAccountPanel.add(userNameText, gbca);
		
		gbca.gridx=0; gbca.gridy=2;
		gbca.anchor=GridBagConstraints.EAST; gbca.gridwidth=1;
		passLabel=new JLabel("Mật khẩu:"); passLabel.setFont(new Font("Segoe UI",1,14));
		inforAccountPanel.add(passLabel, gbca);
		
		gbca.gridx=1; gbca.anchor=GridBagConstraints.WEST; gbca.gridwidth=3;
		passText=new JTextField(26); passText.setFont(new Font("Segoe UI",1,14));
		inforAccountPanel.add(passText, gbca);
		
		northPanelEmp.add(inforAccountPanel, BorderLayout.EAST);
		
		empPanel.add(northPanelEmp,BorderLayout.NORTH);
		
//		Table tài khoản
		String[] colNameEmp= {"Mã tài khoản","Tên đăng nhập","Mật khẩu","Vai trò"};
		modelTableEmp= new DefaultTableModel(colNameEmp,0);
		tableEmp=new JTable(modelTableEmp);
		tableEmp.getTableHeader().setFont(new Font("Segoe UI",1,14));
		tableEmp.setFont(new Font("Segoe UI",1,14));
		tableEmp.setAutoCreateRowSorter(true);
		ArrayList<RowSorter.SortKey> list = new ArrayList<>();
        DefaultRowSorter sorter = ((DefaultRowSorter)tableEmp.getRowSorter());
        sorter.setSortsOnUpdates(true);
        list.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sorter.setSortKeys(list);
        sorter.sort();
		scrollEmp=new JScrollPane(tableEmp);
		scrollEmp.setBorder(BorderFactory.createLineBorder(new Color(0,191,165)));
		
		empPanel.add(scrollEmp,BorderLayout.CENTER);
		
//		Các nút chức năng trong tab tài khoản
		btnResetTaiKhoan=new JButton("Làm mới"); btnResetTaiKhoan.setFont(new Font("Segoe UI",1,14)); btnResetTaiKhoan.setPreferredSize(new Dimension(145, 30));
		btnAddTaiKhoan=new JButton("Thêm tài khoản"); btnAddTaiKhoan.setFont(new Font("Segoe UI",1,14)); btnAddTaiKhoan.setPreferredSize(new Dimension(145, 30));
		btnEditTaiKhoan=new JButton("Sửa tài khoản"); btnEditTaiKhoan.setFont(new Font("Segoe UI",1,14)); btnEditTaiKhoan.setPreferredSize(new Dimension(145, 30));
		btnDeleteTaiKhoan=new JButton("Xóa tài khoản"); btnDeleteTaiKhoan.setFont(new Font("Segoe UI",1,14)); btnDeleteTaiKhoan.setPreferredSize(new Dimension(145, 30));
		btnCapTaiKhoan=new JButton("Cấp tài khoản"); btnCapTaiKhoan.setFont(new Font("Segoe UI",1,14)); btnCapTaiKhoan.setPreferredSize(new Dimension(145, 30));
		
		southPanelEmp=new JPanel();
		southPanelEmp.add(btnResetTaiKhoan); southPanelEmp.add(btnAddTaiKhoan);
		southPanelEmp.add(btnEditTaiKhoan); southPanelEmp.add(btnDeleteTaiKhoan);
		southPanelEmp.add(btnCapTaiKhoan);

		empPanel.add(southPanelEmp,BorderLayout.SOUTH);
		
		add(empPanel);

	}

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

}
