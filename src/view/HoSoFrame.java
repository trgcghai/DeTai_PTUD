package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
import javax.swing.JTextArea;
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

public class HoSoFrame extends JFrame implements ActionListener, MouseListener, FocusListener{
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
	
//	Component danh sách hồ sơ
	JPanel hosoPanel, northPanelHoSo, southPanelHoSo, inforHoSoPanel, panelCenter, panelSortSearch;
	JLabel idKHLabel, nameLabel, birthLabel, phoneLabel, genderLabel, emailLabel, diachiLabel,
			idHoSoLabel;
	JTextField idKHText, nameText, phoneText, emailText, diachiText, idHoSoText, timkiemText;
	JTextArea motaText;
	JComboBox genderText, trangThaiText, sapxep, timkiem;
	JButton btnResetHoSo, btnAddHoSo, btnEditHoSo, btnDeleteHoSo, btnSearchUngVien, btnSearch, btnSaveHoSo;
	JTable tableCus;
	DefaultTableModel modelTableCus;
	JScrollPane scrollCus;
	UtilDateModel modelBirth;
	JDatePickerImpl birth;
	
	
	public HoSoFrame(String userName) {
		setTitle("Danh sách hồ sơ");
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
		
		addFocusListener();
		
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

		hosoPanel=new JPanel(); 
		hosoPanel.setLayout(new BorderLayout(5,5));
		
//		Hiển thị thông tin
		northPanelHoSo=new JPanel();  northPanelHoSo.setLayout(new BorderLayout());
		northPanelHoSo.setPreferredSize(new Dimension(getWidth(),200));
		inforHoSoPanel=new JPanel(); inforHoSoPanel.setLayout(new GridBagLayout());
		inforHoSoPanel.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(0,191,165)), "Thông tin hồ sơ",
				TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI",1,14)));
		GridBagConstraints gbc= new GridBagConstraints();
		
		gbc.gridx=0; gbc.gridy=0; gbc.insets=new Insets(5, 3, 5, 3);
		gbc.anchor=GridBagConstraints.EAST;
		nameLabel=new JLabel("Họ tên:"); nameLabel.setFont(new Font("Segoe UI",1,14));
		inforHoSoPanel.add(nameLabel, gbc);
		gbc.gridx=1; gbc.anchor=GridBagConstraints.WEST;
		nameText=new JTextField(15); nameText.setFont(new Font("Segoe UI",1,14));
		nameText.setEditable(false);
		inforHoSoPanel.add(nameText, gbc);
		
		gbc.gridx=2;
		emailLabel=new JLabel("Email:"); emailLabel.setFont(new Font("Segoe UI",1,14));
		inforHoSoPanel.add(emailLabel, gbc);
		gbc.gridx=3; gbc.anchor=GridBagConstraints.WEST; gbc.gridwidth=2;
		emailText=new JTextField(15); emailText.setFont(new Font("Segoe UI",1,14));
		emailText.setEditable(false);
		inforHoSoPanel.add(emailText, gbc);
		
		JPanel panelDiaChi=new JPanel();
		diachiLabel=new JLabel("Địa chỉ:"); diachiLabel.setFont(new Font("Segoe UI",1,14));
		panelDiaChi.add(diachiLabel);
		diachiText=new JTextField(18); diachiText.setFont(new Font("Segoe UI",1,14));
		diachiText.setEditable(false);
		panelDiaChi.add(diachiText);
		gbc.gridx=5; gbc.gridwidth=2;
		inforHoSoPanel.add(panelDiaChi, gbc);
		
		gbc.gridx=7; gbc.gridheight=3; gbc.gridwidth=1;
		motaText=new JTextArea(4, 45);
		motaText.setBorder(new TitledBorder(BorderFactory.createLineBorder(Color.WHITE), "Mô tả:",
				TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI",1,14)));
		motaText.setFont(new Font("Segoe UI",0,14));
		motaText.setLineWrap(true);
		JScrollPane scrollMoTa=new JScrollPane(motaText);
		inforHoSoPanel.add(scrollMoTa, gbc);
		
		gbc.gridx=0; gbc.gridy=1; gbc.gridheight=1;
		birthLabel=new JLabel("Ngày sinh:"); birthLabel.setFont(new Font("Segoe UI",1,14));
		inforHoSoPanel.add(birthLabel, gbc);
		gbc.gridx=1;
		modelBirth=new UtilDateModel();
		modelBirth.setSelected(true);
		Properties p=new Properties();
		p.put("text.day", "Day"); p.put("text.month", "Month"); p.put("text.year","Year");
		JDatePanelImpl panelBirth=new JDatePanelImpl(modelBirth, p);
		birth=new JDatePickerImpl(panelBirth, new LabelDateFormatter());
		birth.setPreferredSize(new Dimension(200, 24));
		inforHoSoPanel.add(birth, gbc);
		
		gbc.gridx=2;
		genderLabel=new JLabel("Giới tính:"); genderLabel.setFont(new Font("Segoe UI",1,14));
		inforHoSoPanel.add(genderLabel, gbc);
		gbc.gridx=3; gbc.anchor=GridBagConstraints.WEST;
		String[] genders= {"Nam", "Nữ"};
		genderText=new JComboBox(genders); genderText.setFont(new Font("Segoe UI",1,14));
		genderText.setPreferredSize(new Dimension(95,25));
		inforHoSoPanel.add(genderText, gbc);
		
		gbc.gridx=4; 
		phoneLabel=new JLabel("Số điện thoại:"); phoneLabel.setFont(new Font("Segoe UI",1,14));
		inforHoSoPanel.add(phoneLabel, gbc);
		gbc.gridx=5; gbc.anchor=GridBagConstraints.WEST;
		phoneText=new JTextField(17); phoneText.setFont(new Font("Segoe UI",1,14));
		phoneText.setText("Nhập sđt ứng viên để tìm kiếm");
		inforHoSoPanel.add(phoneText, gbc);
		
		gbc.gridx=6;
		btnSearchUngVien=new JButton("Tìm"); btnSearchUngVien.setFont(new Font("Segoe UI",1,14));
		btnSearchUngVien.setPreferredSize(new Dimension(80,25));
		btnSearchUngVien.setBackground(new Color(0,102,102));
		btnSearchUngVien.setForeground(Color.WHITE);
		inforHoSoPanel.add(btnSearchUngVien, gbc);
		
		gbc.gridx=0; gbc.gridy=2;
		idHoSoLabel=new JLabel("Mã hồ sơ:"); idHoSoLabel.setFont(new Font("Segoe UI",1,14));
		inforHoSoPanel.add(idHoSoLabel, gbc);
		gbc.gridx=1; gbc.anchor=GridBagConstraints.WEST;
		idHoSoText=new JTextField(15); idHoSoText.setFont(new Font("Segoe UI",1,14));
		idHoSoText.setEditable(false);
		inforHoSoPanel.add(idHoSoText, gbc);
		
		gbc.gridx=2;
		String[] trangthai= {"Chưa nộp", "Xem xét", "Chờ", "Chấp nhận", "Từ chối"};
		trangThaiText=new JComboBox(trangthai); trangThaiText.setFont(new Font("Segoe UI",1,14));
		trangThaiText.setPreferredSize(new Dimension(100,25));
		trangThaiText.setRenderer(new ComboBoxRenderer("Trạng thái"));
		trangThaiText.setSelectedIndex(-1);
		inforHoSoPanel.add(trangThaiText, gbc);

		northPanelHoSo.add(inforHoSoPanel, BorderLayout.WEST);
		hosoPanel.add(northPanelHoSo,BorderLayout.NORTH);
		
//		Tìm kiếm
		panelCenter=new JPanel(); panelCenter.setLayout(new BorderLayout(5,5));
		panelSortSearch=new JPanel();
		
		timkiemText=new JTextField(15); timkiemText.setFont(new Font("Segoe UI",1,14));
		String[] timkiems= {"Tìm kiếm theo tên ứng viên", "Tìm kiếm theo email ứng viên",
				"Tìm kiếm theo mã hồ sơ","Tìm kiếm theo trạng thái hồ sơ"};
		timkiem=new JComboBox(timkiems); timkiem.setFont(new Font("Segoe UI",1,14));
		timkiem.setPreferredSize(new Dimension(300,25));
		timkiem.setRenderer(new ComboBoxRenderer("Tìm kiếm hồ sơ"));
		timkiem.setSelectedIndex(-1);
		btnSearch=new JButton("Tìm"); btnSearch.setFont(new Font("Segoe UI",1,14));
		btnSearch.setPreferredSize(new Dimension(80,25));
		btnSearch.setBackground(new Color(0,102,102));
		btnSearch.setForeground(Color.WHITE);
		
		panelSortSearch.add(timkiemText); panelSortSearch.add(timkiem); panelSortSearch.add(btnSearch);
		
		panelCenter.add(panelSortSearch, BorderLayout.NORTH);
		
//		Table ứng viên
		String[] colNameCus= {"Mã hồ sơ","Tên ứng viên","Công ty đã nộp","Trạng thái"};
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
		
		hosoPanel.add(panelCenter,BorderLayout.CENTER);
		
//		Các nút chức năng trong tab hồ sơ
		btnResetHoSo=new JButton("Làm mới"); btnResetHoSo.setFont(new Font("Segoe UI",1,14)); btnResetHoSo.setPreferredSize(new Dimension(160, 30));
		btnAddHoSo=new JButton("Thêm hồ sơ"); btnAddHoSo.setFont(new Font("Segoe UI",1,14)); btnAddHoSo.setPreferredSize(new Dimension(160, 30));
		btnEditHoSo=new JButton("Sửa hồ sơ"); btnEditHoSo.setFont(new Font("Segoe UI",1,14)); btnEditHoSo.setPreferredSize(new Dimension(160, 30));
		btnDeleteHoSo=new JButton("Xóa hồ sơ"); btnDeleteHoSo.setFont(new Font("Segoe UI",1,14)); btnDeleteHoSo.setPreferredSize(new Dimension(160, 30));
		btnSaveHoSo=new JButton("Xuất danh sách"); btnSaveHoSo.setFont(new Font("Segoe UI",1,14)); btnSaveHoSo.setPreferredSize(new Dimension(160, 30));
		
		southPanelHoSo=new JPanel();
		southPanelHoSo.add(btnResetHoSo); southPanelHoSo.add(btnAddHoSo);
		southPanelHoSo.add(btnEditHoSo); southPanelHoSo.add(btnDeleteHoSo); southPanelHoSo.add(btnSaveHoSo);

		hosoPanel.add(southPanelHoSo,BorderLayout.SOUTH);
		
		add(hosoPanel);

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
	
	public void addFocusListener() {
		phoneText.addFocusListener(this);
		
		addPlaceHolder(phoneText);
	}
	

	@Override
	public void mouseClicked(MouseEvent e) {

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
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		var obj=e.getSource();
		if(obj.equals(phoneText)) {
			if(phoneText.getText().equals("Nhập sđt ứng viên để tim kiếm")) {
				phoneText.setText(null);
				phoneText.requestFocus();
				
				removePlaceHolder(phoneText);
			}
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		var obj=e.getSource();
		if(obj.equals(phoneText)) {
			if(phoneText.getText().length()==0) {
				addPlaceHolder(phoneText);
				phoneText.setText("Nhập sđt ứng viên để tim kiếm");
			}
			
		}
	}

}
