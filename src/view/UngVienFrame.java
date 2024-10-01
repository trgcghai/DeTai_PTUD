package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultRowSorter;
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
import javax.swing.RowSorter;
import javax.swing.SortOrder;
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
import dao.Customer_DAO;
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

public class UngVienFrame extends JFrame implements ActionListener, MouseListener{
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
	
//	Component danh sách ứng viên
	JPanel cusPanel, northPanelCus, southPanelCus, inforCusPanel, inforAccountPanel, panelCenter, panelSearch;
	JLabel idKHLabel, nameLabel, birthLabel, phoneLabel, genderLabel, emailLabel, diachiLabel;
	JTextField idKHText, nameText, phoneText, emailText, diachiText;
	JComboBox genderText,  positionText, timkiemText, timkiem;
	JButton btnResetCus, btnAddCus, btnEditCus, btnDeleteCus, btnSaveCus, btnSearch;
	JTable tableCus;
	DefaultTableModel modelTableCus;
	JScrollPane scrollCus;
	UtilDateModel modelBirth;
	JDatePickerImpl birth;
	
	private Customer_DAO cusDAO;
	
	public UngVienFrame(String userName) {
		setTitle("Danh sách ứng viên");
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
		
//		Database.getInstance().connect();
//		cusDAO=new Customer_DAO();
//	
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

		cusPanel=new JPanel(); 
		cusPanel.setLayout(new BorderLayout(5,5));
		
//		Hiển thị thông tin
		northPanelCus=new JPanel();  northPanelCus.setLayout(new BorderLayout());
		northPanelCus.setPreferredSize(new Dimension(getWidth(),130));
		inforCusPanel=new JPanel(); inforCusPanel.setLayout(new GridBagLayout());
		inforCusPanel.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(0,191,165)), "Thông tin ứng viên",
				TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI",1,14)));
		GridBagConstraints gbc= new GridBagConstraints();
		
		gbc.gridx=0; gbc.gridy=0; gbc.insets=new Insets(5, 3, 5, 3); 
		gbc.anchor=GridBagConstraints.EAST;
		idKHLabel=new JLabel("Mã ứng viên:"); idKHLabel.setFont(new Font("Segoe UI",1,14));
		inforCusPanel.add(idKHLabel, gbc);
		gbc.gridx=1; gbc.anchor=GridBagConstraints.WEST;
		idKHText=new JTextField(7); idKHText.setFont(new Font("Segoe UI",1,14));
		idKHText.setEditable(false);
		inforCusPanel.add(idKHText, gbc);
		
		gbc.gridx=2; gbc.gridy=0;
		nameLabel=new JLabel("Họ tên:"); nameLabel.setFont(new Font("Segoe UI",1,14));
		inforCusPanel.add(nameLabel, gbc);
		gbc.gridx=3; gbc.anchor=GridBagConstraints.WEST;
		nameText=new JTextField(15); nameText.setFont(new Font("Segoe UI",1,14));
		inforCusPanel.add(nameText, gbc);
		
		gbc.gridx=4; gbc.gridy=0; 
		phoneLabel=new JLabel("Số điện thoại:"); phoneLabel.setFont(new Font("Segoe UI",1,14));
		inforCusPanel.add(phoneLabel, gbc);
		gbc.gridx=5; gbc.anchor=GridBagConstraints.WEST;
		phoneText=new JTextField(15); phoneText.setFont(new Font("Segoe UI",1,14));
		inforCusPanel.add(phoneText, gbc);
		
		gbc.gridx=0; gbc.gridy=1;
		genderLabel=new JLabel("Giới tính:"); genderLabel.setFont(new Font("Segoe UI",1,14));
		inforCusPanel.add(genderLabel, gbc);
		gbc.gridx=1; gbc.anchor=GridBagConstraints.WEST;
		String[] genders= {"Nam", "Nữ"};
		genderText=new JComboBox(genders); genderText.setFont(new Font("Segoe UI",1,14));
		genderText.setPreferredSize(new Dimension(95,25));
		inforCusPanel.add(genderText, gbc);
		
		gbc.gridx=2; gbc.gridy=1;
		birthLabel=new JLabel("Ngày sinh:"); birthLabel.setFont(new Font("Segoe UI",1,14));
		inforCusPanel.add(birthLabel, gbc);
		gbc.gridx=3;
		modelBirth=new UtilDateModel();
		modelBirth.setSelected(true);
		Properties p=new Properties();
		p.put("text.day", "Day"); p.put("text.month", "Month"); p.put("text.year","Year");
		JDatePanelImpl panelBirth=new JDatePanelImpl(modelBirth, p);
		birth=new JDatePickerImpl(panelBirth, new LabelDateFormatter());
		birth.setPreferredSize(new Dimension(200, 24));
		inforCusPanel.add(birth, gbc);
		
		gbc.gridx=4;
		emailLabel=new JLabel("Email:"); emailLabel.setFont(new Font("Segoe UI",1,14));
		inforCusPanel.add(emailLabel, gbc);
		gbc.gridx=5; gbc.anchor=GridBagConstraints.WEST;
		emailText=new JTextField(15); emailText.setFont(new Font("Segoe UI",1,14));
		inforCusPanel.add(emailText, gbc);
		
		gbc.gridx=6;
		diachiLabel=new JLabel("Địa chỉ:"); diachiLabel.setFont(new Font("Segoe UI",1,14));
		inforCusPanel.add(diachiLabel, gbc);
		gbc.gridx=7; gbc.anchor=GridBagConstraints.WEST;
		diachiText=new JTextField(15); diachiText.setFont(new Font("Segoe UI",1,14));
		inforCusPanel.add(diachiText, gbc);

		northPanelCus.add(inforCusPanel, BorderLayout.WEST);
		cusPanel.add(northPanelCus,BorderLayout.NORTH);
		
//		Tìm kiếm
		panelCenter=new JPanel(); panelCenter.setLayout(new BorderLayout(5,5));
		panelSearch=new JPanel();
		
		timkiemText=new JComboBox(); timkiemText.setFont(new Font("Segoe UI",1,14));
		String[] timkiems= {"Tìm kiếm theo tên", "Tìm kiếm theo mã ứng viên",
				"Tìm kiếm theo số điện thoại", "Tìm kiếm theo email"};
		timkiem=new JComboBox(timkiems); timkiem.setFont(new Font("Segoe UI",1,14));
		timkiem.setPreferredSize(new Dimension(300,25));
		timkiem.setRenderer(new ComboBoxRenderer("Tìm kiếm ứng viên"));
		timkiem.setSelectedIndex(-1);
		btnSearch=new JButton("Tìm"); btnSearch.setFont(new Font("Segoe UI",1,14));
		btnSearch.setPreferredSize(new Dimension(80,25));
		btnSearch.setBackground(new Color(0,102,102));
		btnSearch.setForeground(Color.WHITE);
		
		panelSearch.add(timkiemText); panelSearch.add(timkiem); panelSearch.add(btnSearch);
		panelCenter.add(panelSearch, BorderLayout.NORTH);
		
//		Table ứng viên
		String[] colNameCus= {"Mã ứng viên","Tên ứng viên","Số điện thoại", "Giới tính", "Ngày sinh","Email","Địa chỉ"};
		modelTableCus= new DefaultTableModel(colNameCus,0);
		tableCus=new JTable(modelTableCus);
		tableCus.getTableHeader().setFont(new Font("Segoe UI",1,14));
		tableCus.setFont(new Font("Segoe UI",1,14));
		tableCus.setAutoCreateRowSorter(true);
		ArrayList<RowSorter.SortKey> list = new ArrayList<>();
        DefaultRowSorter sorter = ((DefaultRowSorter)tableCus.getRowSorter());
        sorter.setSortsOnUpdates(true);
        list.add( new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sorter.setSortKeys(list);
        sorter.sort();
		scrollCus=new JScrollPane(tableCus);
		scrollCus.setBorder(BorderFactory.createLineBorder(new Color(0,191,165)));
		
		panelCenter.add(scrollCus, BorderLayout.CENTER);
		
		cusPanel.add(panelCenter,BorderLayout.CENTER);
		
//		Các nút chức năng trong tab ứng viên
		btnResetCus=new JButton("Làm mới"); btnResetCus.setFont(new Font("Segoe UI",1,14)); btnResetCus.setPreferredSize(new Dimension(160, 30));
		btnAddCus=new JButton("Thêm ứng viên"); btnAddCus.setFont(new Font("Segoe UI",1,14)); btnAddCus.setPreferredSize(new Dimension(160, 30));
		btnEditCus=new JButton("Sửa ứng viên"); btnEditCus.setFont(new Font("Segoe UI",1,14)); btnEditCus.setPreferredSize(new Dimension(160, 30));
		btnDeleteCus=new JButton("Xóa ứng viên"); btnDeleteCus.setFont(new Font("Segoe UI",1,14)); btnDeleteCus.setPreferredSize(new Dimension(160, 30));
		btnSaveCus=new JButton("Xuất danh sách"); btnSaveCus.setFont(new Font("Segoe UI",1,14)); btnSaveCus.setPreferredSize(new Dimension(160, 30));
		
		southPanelCus=new JPanel();
		southPanelCus.add(btnResetCus); southPanelCus.add(btnAddCus);
		southPanelCus.add(btnEditCus); southPanelCus.add(btnDeleteCus); southPanelCus.add(btnSaveCus);

		cusPanel.add(southPanelCus,BorderLayout.SOUTH);
		
		add(cusPanel);

	}
	
//	Change
//	Gán danh sách từ sql
//	public void loadData() {
//		cusDAO.setList(cusDAO.getAllCustomer());
//		
//		cusDAO.sortByIdCusASC();
//		int idAuto=1;
//		for(Customer i: cusDAO.getList()) {
//			int id=Integer.parseInt(i.getIdCustomer().substring(2));
//			if(idAuto==id) {
//				idAuto++;
//				Customer.setIdAuto(idAuto);
//			}
//		}
//	}
	
//	Đưa dữ liệu lên bảng
//	public void loadDataTable() {
//		modelTableCus.setRowCount(0);
//		DateTimeFormatter format=DateTimeFormatter.ofPattern("dd-MM-yyyy");
//		for(Customer i: cusDAO.getList()) {
//			var birthday=(i.getBirthday()!=null?format.format(i.getBirthday()):"");
//			Object[] obj=new Object[] {
//					i.getIdCustomer(), i.getName(), (i.getGender()==1?"Nam":"Nữ"), i.getPhone(), birthday
//			};
//			modelTableCus.addRow(obj);
//		}
//	}
//	
//	Reset Customer
//	public void resetCus() {
//		nameText.setText("");
//		idKHText.setText("");
//		modelBirth.setValue(new Date());
//		genderText.setSelectedIndex(0);
//		phoneText.setText("");
//		
//		cusDAO.setList(cusDAO.getAllCustomer());
//		loadDataTable();
//	}
	
//	Add Customer
//	public void addCus() {
//		if(!idKHText.getText().equals("")) {
//			JOptionPane.showMessageDialog(rootPane, "Khách hàng đã tồn tại");
//			resetCus();
//			return;
//		}
//		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
//		
//		var name=nameText.getText();
//		var birthday=format.format(modelBirth.getValue());
//		var gender=genderText.getSelectedItem().toString();
//		var phone=phoneText.getText();
//		
//		if(!name.equals("") && !phone.equals("")) {
//			var check = new FilterImp();
//			try {
//				if(check.checkName(name) && check.checkBirthday(LocalDate.parse(birthday)) && check.checkPhone(phone)) {
//					Customer cus  = new Customer("KH"+Customer.getIdAuto(), name, LocalDate.parse(birthday),
//							phone, (gender=="Nam"?1:0));
//					Customer.setIdAuto(Customer.getIdAuto()+1);
//					for(Customer c: cusDAO.getAllCustomer()) {
//						if(c.getIdCustomer().equalsIgnoreCase(cus.getIdCustomer())) {
//							cus.setIdCustomer("KH"+Customer.getIdAuto());
//							Customer.setIdAuto(Customer.getIdAuto()+1);
//						}
//					}
//					cusDAO.create(cus);
//					cusDAO.setList(cusDAO.getAllCustomer());
//					loadDataTable();
//					JOptionPane.showMessageDialog(rootPane, "Thêm khách hàng thành công");
//					resetCus();
//				}
//			} catch (checkName | checkBirthday | checkPhone e) {
//				// TODO Auto-generated catch block
//				JOptionPane.showMessageDialog(rootPane, e.getMessage());
//			}
//		}
//		else {
//			JOptionPane.showMessageDialog(rootPane, "Nhập đủ thông tin khách hàng");
//		}
//	}
//	
////	Edit Employee
//	public void editCus() {
//		int index = tableCus.getSelectedRow();
//		if(index!=-1) {
//			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
//			
//			var name=nameText.getText();
//			var id=idKHText.getText();
//			var birthday=format.format(modelBirth.getValue());
//			var gender=genderText.getSelectedItem().toString();
//			var phone=phoneText.getText();
//			
//			if(!name.equals("") && !phone.equals("")) {
//				var check=new FilterImp();
//				try {
//					if(check.checkName(name) && check.checkBirthday(LocalDate.parse(birthday)) && check.checkPhone(phone)) {
//						Customer cus=new Customer(id, name, LocalDate.parse(birthday), phone, (gender=="Nam"?1:0));
//						cusDAO.update(cus);
//						cusDAO.setList(cusDAO.getAllCustomer());
//						loadDataTable();
//						JOptionPane.showMessageDialog(rootPane, "Sửa khách hàng thành công");
//						resetCus();
//					}
//				} catch (checkName | checkBirthday | checkPhone e) {
//					// TODO Auto-generated catch block
//					JOptionPane.showMessageDialog(rootPane, e.getMessage());
//				}
//			}
//			else {
//				JOptionPane.showMessageDialog(rootPane, "Nhập đủ thông tin khách hàng");
//			}
//		}
//		else {
//			JOptionPane.showMessageDialog(rootPane, "Chọn khách hàng để sửa");
//		}
//	}
//	
////	Delete Customner
//	public void deleteCus() {
//		int index = tableCus.getSelectedRow();
//		if(index!=-1) {
//			var id = idKHText.getText();
//			var check=JOptionPane.showConfirmDialog(rootPane, "Chắc chắn xóa khách hàng");
//			if(check==JOptionPane.OK_OPTION) {
//				cusDAO.delete(id);
//				cusDAO.setList(cusDAO.getAllCustomer());
//				int idAutoCurrent=1;
//				for(Customer i: cusDAO.getList()) {
//					int res=Integer.parseInt(i.getIdCustomer().substring(2));
//					if(idAutoCurrent==res) {
//						idAutoCurrent++;
//						Customer.setIdAuto(idAutoCurrent);
//					}
//				}
//				loadDataTable();
//				resetCus();
//				JOptionPane.showMessageDialog(rootPane, "Xóa khách hàng thành công");
//			}
//		}
//		else {
//			JOptionPane.showMessageDialog(rootPane, "Chọn khách hàng để xóa");
//		}
//	}
//	
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
////		Table click
//		tableCus.addMouseListener(this);
//		
////		other
//		btnResetCus.addActionListener(this);
//		btnAddCus.addActionListener(this);
//		btnEditCus.addActionListener(this);
//		btnDeleteCus.addActionListener(this);
//		btnSaveCus.addActionListener(this);
//	}

	@Override
	public void mouseClicked(MouseEvent e) {
//		int index = tableCus.getSelectedRow();
//		if (index!=-1) {
//			idKHText.setText(modelTableCus.getValueAt(index, 0).toString());
//			nameText.setText(modelTableCus.getValueAt(index, 1).toString());
//			for(int i=0;i<genderText.getItemCount();i++) {
//				var gender=modelTableCus.getValueAt(index, 2).toString();
//				if(genderText.getItemAt(i).toString().equalsIgnoreCase(gender)) {
//					genderText.setSelectedIndex(i);
//					break;
//				}
//			}
//			phoneText.setText(modelTableCus.getValueAt(index, 3).toString());
//			SimpleDateFormat format=new SimpleDateFormat("dd-MM-yyyy");
//			try {
//				if(modelTableCus.getValueAt(index, 4)!=null && !modelTableCus.getValueAt(index, 4).toString().equals("")) {
//					Date date=format.parse(modelTableCus.getValueAt(index, 4).toString());
//					modelBirth.setValue(date);
//				}
//				else {
//					modelBirth.setValue(null);
//				}
//			} catch (ParseException e1) {
//				e1.printStackTrace();
//			}
//		}
	}

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
//		} else if (obj.equals(btnResetCus)) {
//			resetCus();
//		} else if (obj.equals(btnAddCus)) {
//			addCus();
//		} else if (obj.equals(btnDeleteCus)) {
//			deleteCus();
//		} else if (obj.equals(btnEditCus)) {
//			editCus();
//		} else if (obj.equals(btnSaveCus)) {
//			ExcelHelper excel = new ExcelHelper();
//			excel.exportData(this, tableCus);
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
