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
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultRowSorter;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.w3c.dom.events.MouseEvent;

public class HopDongFrame extends JFrame implements MouseListener, ActionListener {
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
	JPanel panel, northPanel, centerPanel, sortSearchPanel;
	JLabel searchLabel;
	JTextField searchNameText, searchPhoneText, searchDateText, searchFeeText, searchTitleText, searchEmailText;
//	JComboBox typeMovieText;
	JFileChooser fileChooser;
	JButton btnReset, btnSeeDetail, btnSearch;
	JTable tableContract;
	DefaultTableModel modeltableContract;
	JScrollPane scrollPanel;
	
	public HopDongFrame(String userName) {
		setTitle("Danh sách hợp đồng");
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
		
		addEventListener();
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
		panel=new JPanel(); 
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.gridx = 0;
		
		northPanel=new JPanel();
		northPanel.setPreferredSize(new Dimension(this.getWidth(), 1000));
		sortSearchPanel=new JPanel(); 
		sortSearchPanel.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(0,191,165)), 
				"Tìm kiếm nhà tuyển dụng",
				TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI",1,14)));
		
		JPanel searchPanel=new JPanel(); 
		searchPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbca= new GridBagConstraints();
		gbca.gridx=0; 
		gbca.gridy=0; 
		gbca.insets=new Insets(10, 5, 10, 10);
		
		gbca.anchor=GridBagConstraints.EAST;
		searchLabel=new JLabel("Họ tên ứng viên:"); 
		searchLabel.setFont(new Font("Segoe UI",1,14));
		searchPanel.add(searchLabel, gbca);
		
		gbca.gridx=1; 
		gbca.anchor=GridBagConstraints.WEST;
		searchNameText=new JTextField(15); 
		searchNameText.setFont(new Font("Segoe UI",1,14));
		searchPanel.add(searchNameText, gbca);
		
		gbca.gridx=2;
		gbca.anchor=GridBagConstraints.EAST;
		searchLabel=new JLabel("Email:"); 
		searchLabel.setFont(new Font("Segoe UI",1,14));
		searchPanel.add(searchLabel, gbca);
		
		gbca.gridx=3; 
		gbca.anchor=GridBagConstraints.WEST;
		searchEmailText=new JTextField(15); 
		searchEmailText.setFont(new Font("Segoe UI",1,14));
		searchPanel.add(searchEmailText, gbca);
		
		gbca.gridx=4;
		gbca.anchor=GridBagConstraints.EAST;
		searchLabel=new JLabel("Số điện thoại:"); 
		searchLabel.setFont(new Font("Segoe UI",1,14));
		searchPanel.add(searchLabel, gbca);
		
		gbca.gridx=5; 
		gbca.anchor=GridBagConstraints.WEST;
		searchPhoneText=new JTextField(15); 
		searchPhoneText.setFont(new Font("Segoe UI",1,14));
		searchPanel.add(searchPhoneText, gbca);
		
		gbca.gridx=0; 
		gbca.gridy=1;
		gbca.anchor=GridBagConstraints.EAST;
		searchLabel=new JLabel("Tiêu đề tin tuyển dụng:"); 
		searchLabel.setFont(new Font("Segoe UI",1,14));
		searchPanel.add(searchLabel, gbca);
		
		gbca.gridx=1;
		gbca.anchor=GridBagConstraints.WEST;
		searchTitleText=new JTextField(15); 
		searchTitleText.setFont(new Font("Segoe UI",1,14));
		searchPanel.add(searchTitleText, gbca);
		
		gbca.gridx=2;
		gbca.anchor=GridBagConstraints.EAST;
		searchLabel=new JLabel("Phí dịch vụ:"); 
		searchLabel.setFont(new Font("Segoe UI",1,14));
		searchPanel.add(searchLabel, gbca);
		
		gbca.gridx=3; 
		gbca.anchor=GridBagConstraints.WEST;
		searchFeeText=new JTextField(15); 
		searchFeeText.setFont(new Font("Segoe UI",1,14));
		searchPanel.add(searchFeeText, gbca);
		
		gbca.gridx=4;
		gbca.anchor=GridBagConstraints.EAST;
		searchLabel=new JLabel("Thời gian:"); 
		searchLabel.setFont(new Font("Segoe UI",1,14));
		searchPanel.add(searchLabel, gbca);
		
		gbca.gridx=5; 
		gbca.anchor=GridBagConstraints.WEST;
		searchDateText=new JTextField(15); 
		searchDateText.setFont(new Font("Segoe UI",1,14));
		searchPanel.add(searchDateText, gbca);
		
		sortSearchPanel.add(searchPanel);
		northPanel.add(sortSearchPanel);
		
//		Các nút chức năng 
		btnReset=new JButton("Xóa trắng"); 
		btnReset.setFont(new Font("Segoe UI",1,14)); 
		btnReset.setPreferredSize(new Dimension(185, 30));
		
		btnSeeDetail=new JButton("Xem chi tiết hợp đồng"); 
		btnSeeDetail.setFont(new Font("Segoe UI",1,14)); 
		btnSeeDetail.setPreferredSize(new Dimension(185, 30));
		
		btnSearch=new JButton("Tìm hợp đồng"); 
		btnSearch.setFont(new Font("Segoe UI",1,14)); 
		btnSearch.setPreferredSize(new Dimension(185, 30));
		
		centerPanel=new JPanel();
		centerPanel.add(btnReset); 
		centerPanel.add(btnSeeDetail);
		centerPanel.add(btnSearch); 
		
		
//		Table nhà tuyển dụng
		String[] colNameEmp= {"Mã hợp đồng","Tên ứng viên","Số điện thoại","Email","Tin tuyển dụng","Phí dịch vụ", "Ngày lập"};
		modeltableContract= new DefaultTableModel(colNameEmp,0);
		tableContract=new JTable(modeltableContract);
		tableContract.getTableHeader().setFont(new Font("Segoe UI",1,14));
		tableContract.setFont(new Font("Segoe UI",1,14));
		tableContract.setAutoCreateRowSorter(true);
		ArrayList<RowSorter.SortKey> list = new ArrayList<>();
        DefaultRowSorter sorter = ((DefaultRowSorter)tableContract.getRowSorter());
        sorter.setSortsOnUpdates(true);
        list.add( new RowSorter.SortKey(6, SortOrder.DESCENDING));
        sorter.setSortKeys(list);
        sorter.sort();
        
		scrollPanel=new JScrollPane(tableContract);
		scrollPanel.setBorder(BorderFactory.createLineBorder(new Color(0,191,165)));
		
		gbc.gridy = 0;
        gbc.weighty = 1.0;
		panel.add(northPanel, gbc);
		
		gbc.gridy = 1;
        gbc.weighty = 1.0;
		panel.add(centerPanel, gbc);
		
		gbc.gridy = 2;
        gbc.weighty = 10.0;
		panel.add(scrollPanel, gbc);
		
		add(panel);
	}

	public void addEventListener() {
		btnReset.addActionListener(this);
		btnSearch.addActionListener(this);
		btnSeeDetail.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if (o.equals(btnReset)) {

			searchNameText.setText("");
			searchPhoneText.setText("");
			searchDateText.setText("");
			searchFeeText.setText("");
			searchTitleText.setText("");
			searchEmailText.setText("");
			
			searchNameText.requestFocus();
			
		} else if (o.equals(btnSearch)) {
			System.out.println("tim kiem");
		} else if (o.equals(btnSeeDetail)) {
			System.out.println("xem chi tiet");
		}
	}

	@Override
	public void mouseClicked(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
