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
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.controlsfx.control.CheckComboBox;
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
import entity.constraint.HinhThucLamViec;
import entity.constraint.NganhNghe;
import entity.constraint.TrinhDo;
import entity.Customer;
import entity.NhanVien;
import exception.checkBirthday;
import exception.checkDateOfWork;
import exception.checkName;
import exception.checkPhone;
import exception.checkUserName;
import exception.checkUserPass;


public class TinTuyenDungTestFrame extends JFrame implements ActionListener, MouseListener{
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
	
//	Component danh sách tin tuyển dụng
	JPanel hosoPanel, northPanelHoSo, southPanelHoSo, inforHoSoPanel, panelCenter, panelSortSearch;
	JLabel idTTDLabel, nameLabel, phoneLabel, genderLabel, trinhdoLabel, soluongLabel,
			idHoSoLabel, startLabel, endLabel, luongLabel, hinhthucLabel,
			nhatuyendungLabel, trangthaiLabel, nganhngheLabel;
	JTextField idTTDText, nameText, phoneText, soluongText, idHoSoText, luongText, nganhngheText;
	JTextArea motaText;
	JComboBox genderText, trangThaiText, sapxep, timkiemText, timkiem, hinhthucText, trinhdoText, nhatuyendungText,
			trangthaiText, nganhngheBox;
	JButton btnResetTTD, btnAddTTD, btnEditTTD, btnDeleteTTD, btnSearchUngVien, btnSearch, btnSaveTTD;
	JTable tableCus;
	DefaultTableModel modelTableCus;
	JScrollPane scrollCus;
	UtilDateModel modelDateStart, modelDateEnd;
	JDatePickerImpl start, end;
	
	
	public TinTuyenDungTestFrame(String userName) {
		setTitle("Danh sách tin tuyển dụng");
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
		inforHoSoPanel.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(0,191,165)), "Thông tin tin tuyển dụng",
				TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI",1,14)));
		GridBagConstraints gbc= new GridBagConstraints();
		
		gbc.gridx=0; gbc.gridy=0; gbc.insets=new Insets(5, 3, 5, 3);
		gbc.anchor=GridBagConstraints.EAST;
		idTTDLabel=new JLabel("Mã tin:"); idTTDLabel.setFont(new Font("Segoe UI",1,14));
		inforHoSoPanel.add(idTTDLabel, gbc);
		gbc.gridx=1; gbc.anchor=GridBagConstraints.WEST;
		idTTDText=new JTextField(5); idTTDText.setFont(new Font("Segoe UI",1,14));
		idTTDText.setEditable(false);
		inforHoSoPanel.add(idTTDText, gbc);
		
		gbc.gridx=4; gbc.anchor=GridBagConstraints.EAST; gbc.gridwidth=1;
		nhatuyendungLabel=new JLabel("Nhà tuyển dụng:"); nhatuyendungLabel.setFont(new Font("Segoe UI",1,14));
		inforHoSoPanel.add(nhatuyendungLabel, gbc);
		gbc.gridx=5; gbc.anchor=GridBagConstraints.WEST;
		nhatuyendungText=new JComboBox(); 
		nhatuyendungText.setFont(new Font("Segoe UI",1,14));
		nhatuyendungText.setPreferredSize(new Dimension(150,25));
		inforHoSoPanel.add(nhatuyendungText, gbc);
		
		gbc.gridx=6; gbc.anchor=GridBagConstraints.EAST;
		nganhngheBox = new JComboBox<String>();
		nganhngheBox.setFont(new Font("Segoe UI",1,14));
		nganhngheBox.setPreferredSize(new Dimension(150,25));
		nganhngheBox.setRenderer(new ComboBoxRenderer("Chọn ngành nghề"));
		NganhNghe[] nganhnghes=NganhNghe.class.getEnumConstants();
		for(NganhNghe n: nganhnghes) {
			nganhngheBox.addItem(n.getValue());
		}
		nganhngheBox.setSelectedIndex(-1);
		nganhngheBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	nganhngheBox.setSelectedIndex(-1);
            }
        });
		inforHoSoPanel.add(nganhngheBox, gbc);
		gbc.gridx=7; gbc.anchor=GridBagConstraints.WEST;
		nganhngheText=new JTextField(12); nganhngheText.setFont(new Font("Segoe UI",1,14));
		inforHoSoPanel.add(nganhngheText, gbc);
		
		gbc.gridx=8; gbc.gridheight=4;
		motaText=new JTextArea(5, 45);
		motaText.setBorder(new TitledBorder(BorderFactory.createLineBorder(Color.WHITE), "Mô tả công việc:",
				TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI",1,14)));
		motaText.setFont(new Font("Segoe UI",0,14));
		motaText.setLineWrap(true);
		JScrollPane scrollMoTa=new JScrollPane(motaText);
		inforHoSoPanel.add(scrollMoTa, gbc);
		
		gbc.gridx=0; gbc.gridy=1; gbc.gridheight=1;
		gbc.anchor=GridBagConstraints.EAST;
		nameLabel=new JLabel("Tiêu đề:"); nameLabel.setFont(new Font("Segoe UI",1,14));
		inforHoSoPanel.add(nameLabel, gbc);
		gbc.gridx=1; gbc.anchor=GridBagConstraints.WEST; gbc.gridwidth=3;
		nameText=new JTextField(15); nameText.setFont(new Font("Segoe UI",1,14));
		inforHoSoPanel.add(nameText, gbc);
		
		gbc.gridx=4; gbc.anchor=GridBagConstraints.WEST; gbc.gridwidth=1;
		hinhthucLabel=new JLabel("Hình thức làm việc:"); hinhthucLabel.setFont(new Font("Segoe UI",1,14));
		inforHoSoPanel.add(hinhthucLabel, gbc);
		gbc.gridx=5; gbc.anchor=GridBagConstraints.WEST;
		hinhthucText=new JComboBox<String>(); 
		hinhthucText.setFont(new Font("Segoe UI",1,14));
		hinhthucText.setPreferredSize(new Dimension(150,25));
		HinhThucLamViec[] res=HinhThucLamViec.class.getEnumConstants();
		for(HinhThucLamViec h: res) {
			hinhthucText.addItem(h.getValue());
		}
		inforHoSoPanel.add(hinhthucText, gbc);
		
		gbc.gridx=6; gbc.anchor=GridBagConstraints.EAST;
		trinhdoLabel=new JLabel("Trình độ:"); trinhdoLabel.setFont(new Font("Segoe UI",1,14));
		inforHoSoPanel.add(trinhdoLabel, gbc);
		gbc.gridx=7; gbc.anchor=GridBagConstraints.WEST;
		trinhdoText=new JComboBox<String>(); trinhdoText.setFont(new Font("Segoe UI",1,14));
		trinhdoText.setPreferredSize(new Dimension(159,25));
		TrinhDo[] tmp=TrinhDo.class.getEnumConstants();
		for(TrinhDo h: tmp) {
			trinhdoText.addItem(h.getValue());
		}
		inforHoSoPanel.add(trinhdoText, gbc);
		
		gbc.gridx=0; gbc.gridy=2; gbc.anchor=GridBagConstraints.EAST;
		soluongLabel=new JLabel("Số lượng:"); soluongLabel.setFont(new Font("Segoe UI",1,14));
		inforHoSoPanel.add(soluongLabel, gbc);
		gbc.gridx=1; gbc.anchor=GridBagConstraints.WEST;
		soluongText=new JTextField(5); soluongText.setFont(new Font("Segoe UI",1,14));
		inforHoSoPanel.add(soluongText, gbc);
		
		gbc.gridx=2; gbc.anchor=GridBagConstraints.EAST;
		luongLabel=new JLabel("Lương:"); luongLabel.setFont(new Font("Segoe UI",1,14));
		inforHoSoPanel.add(luongLabel, gbc);
		gbc.gridx=3; gbc.anchor=GridBagConstraints.WEST;
		luongText=new JTextField(5); luongText.setFont(new Font("Segoe UI",1,14));
		inforHoSoPanel.add(luongText, gbc);
		
		gbc.gridx=4; gbc.anchor=GridBagConstraints.EAST;
		startLabel=new JLabel("Ngày đăng tin:"); startLabel.setFont(new Font("Segoe UI",1,14));
		inforHoSoPanel.add(startLabel, gbc);
		gbc.gridx=5; gbc.anchor=GridBagConstraints.WEST;
		modelDateStart=new UtilDateModel();
		modelDateStart.setSelected(true);
		Properties p=new Properties();
		p.put("text.day", "Day"); p.put("text.month", "Month"); p.put("text.year","Year");
		JDatePanelImpl panelStart=new JDatePanelImpl(modelDateStart, p);
		start=new JDatePickerImpl(panelStart, new LabelDateFormatter());
		start.setPreferredSize(new Dimension(150, 24));
		inforHoSoPanel.add(start, gbc);
		
		gbc.gridx=6; gbc.anchor=GridBagConstraints.EAST;
		endLabel=new JLabel("Ngày hết hạn:"); endLabel.setFont(new Font("Segoe UI",1,14));
		inforHoSoPanel.add(endLabel, gbc);
		gbc.gridx=7; gbc.anchor=GridBagConstraints.WEST;
		modelDateEnd=new UtilDateModel();
		modelDateEnd.setSelected(true);
		Properties q=new Properties();
		q.put("text.day", "Day"); q.put("text.month", "Month"); q.put("text.year","Year");
		JDatePanelImpl panelEnd=new JDatePanelImpl(modelDateEnd, p);
		end=new JDatePickerImpl(panelEnd, new LabelDateFormatter());
		end.setPreferredSize(new Dimension(159, 24));
		inforHoSoPanel.add(end, gbc);
		
		gbc.gridx=0; gbc.gridy=3; gbc.anchor=GridBagConstraints.EAST;
		trangthaiLabel=new JLabel("Trạng thái:"); trangthaiLabel.setFont(new Font("Segoe UI",1,14));
		inforHoSoPanel.add(trangthaiLabel, gbc);
		gbc.gridx=1; gbc.anchor=GridBagConstraints.WEST; gbc.gridwidth=3;
		String[] trangthais= {"Khả dụng", "Không khả dụng"};
		trangthaiText=new JComboBox<String>(trangthais); trangthaiText.setFont(new Font("Segoe UI",1,14));
		trangthaiText.setPreferredSize(new Dimension(200,25));
		inforHoSoPanel.add(trangthaiText, gbc);

		northPanelHoSo.add(inforHoSoPanel, BorderLayout.WEST);
		hosoPanel.add(northPanelHoSo,BorderLayout.NORTH);
		
//		Tìm kiếm
		panelCenter=new JPanel(); panelCenter.setLayout(new BorderLayout(5,5));
		panelSortSearch=new JPanel();
		
		timkiemText=new JComboBox(); timkiemText.setFont(new Font("Segoe UI",1,14));
		String[] timkiems= {"Tìm kiếm theo tiêu đề", "Tìm kiếm theo mã tin tuyển dụng",
				"Tìm kiếm theo nhà tuyển dụng", "Tìm kiếm theo hình thức làm việc", "Tìm kiếm theo trình độ",
				"Tìm kiếm theo mức lương"};
		timkiem=new JComboBox(timkiems); timkiem.setFont(new Font("Segoe UI",1,14));
		timkiem.setPreferredSize(new Dimension(300,25));
		timkiem.setRenderer(new ComboBoxRenderer("Tìm kiếm tin tuyển dụng"));
		timkiem.setSelectedIndex(-1);
		btnSearch=new JButton("Tìm"); btnSearch.setFont(new Font("Segoe UI",1,14));
		btnSearch.setPreferredSize(new Dimension(80,25));
		btnSearch.setBackground(new Color(0,102,102));
		btnSearch.setForeground(Color.WHITE);
		
		panelSortSearch.add(timkiemText); panelSortSearch.add(timkiem); panelSortSearch.add(btnSearch);
		panelCenter.add(panelSortSearch, BorderLayout.NORTH);
		
//		Table tin tuyển dụng
		String[] colNameCus= {"Mã tin tuyển dụng","Tiêu đề","Ngành nghề","Hình thức làm việc","Trình độ","Số lượng","Lương",
				"Ngày đăng tin","Ngày hết hạn","Nhà tuyển dụng","Trạng thái"};
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
		
//		Các nút chức năng trong tab tin tuyển dụng
		btnResetTTD=new JButton("Làm mới"); btnResetTTD.setFont(new Font("Segoe UI",1,14)); btnResetTTD.setPreferredSize(new Dimension(185, 30));
		btnAddTTD=new JButton("Thêm tin tuyển dụng"); btnAddTTD.setFont(new Font("Segoe UI",1,14)); btnAddTTD.setPreferredSize(new Dimension(185, 30));
		btnEditTTD=new JButton("Sửa tin tuyển dụng"); btnEditTTD.setFont(new Font("Segoe UI",1,14)); btnEditTTD.setPreferredSize(new Dimension(185, 30));
		btnDeleteTTD=new JButton("Xóa tin tuyển dụng"); btnDeleteTTD.setFont(new Font("Segoe UI",1,14)); btnDeleteTTD.setPreferredSize(new Dimension(185, 30));
		btnSaveTTD=new JButton("Xuất danh sách"); btnSaveTTD.setFont(new Font("Segoe UI",1,14)); btnSaveTTD.setPreferredSize(new Dimension(185, 30));
		
		southPanelHoSo=new JPanel();
		southPanelHoSo.add(btnResetTTD); southPanelHoSo.add(btnAddTTD);
		southPanelHoSo.add(btnEditTTD); southPanelHoSo.add(btnDeleteTTD); southPanelHoSo.add(btnSaveTTD);

		hosoPanel.add(southPanelHoSo,BorderLayout.SOUTH);
		
		add(hosoPanel);

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
	

}
