package view;

import javax.imageio.ImageIO;
import javax.swing.*;

import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class MainFrame extends JFrame implements ActionListener {
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
	
	public MainFrame(String userName) {
		super("Quản lý tìm kiếm việc làm");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.userName=userName;
		
		initComponentNhanVien();
		initComponentUngVien();
		initComponentCongTy();
		initComponentHopDong();
		initComponentTimViecLam();
		initComponentThongKe();
		initComponentUserRight();
		
//		addActionListener();
		
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
	
//	public void addActionListener() {
////		menu nhân viên
//		itemDSNhanVien.addActionListener(this);
//		itemCapNhatNhanVien.addActionListener(this);
//		
////		menu ứng viên
//		itemDSUngVien.addActionListener(this);
//		itemCapNhatUngVien.addActionListener(this);
//		
////		menu hồ sơ
//		itemDSHoSo.addActionListener(this);
//		itemCapNhatHoSo.addActionListener(this);
//		
////		menu công ty
//		itemDSCongTy.addActionListener(this);
//		itemCapNhatCongTy.addActionListener(this);
//		
////		menu tin tuyển dụng
//		itemDSTinTuyenDung.addActionListener(this);
//		itemCapNhatTinTuyenDung.addActionListener(this);
//		
////		menu hợp đồng
//		itemDSHopDong.addActionListener(this);
//		
////		menu tìm việc làm
//		menuTimViecLam.addActionListener(this);
//		
////		menu thống kê
//		itemTKNhanVien.addActionListener(this);
//		itemTKCongTy.addActionListener(this);
//		itemTKHoSo.addActionListener(this);
//		itemTKTinTuyenDung.addActionListener(this);
//		
////		menu tài khoản
//		itemLogout.addActionListener(this);
//		itemHome.addActionListener(this);
//		
//	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
//		var obj=e.getSource();
//		if(obj.equals(itemListEmpoyee)) {
//			new ListEmployeeFrame(userName).setVisible(true);
//			this.dispose();
//		}
//		else if(obj.equals(itemUpdateEmployee)) {
//			new UpdateEmployeeFrame(userName).setVisible(true);
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
	}

}
