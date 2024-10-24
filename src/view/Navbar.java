package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import swing.ButtonMenu;

public class Navbar extends JPanel{

//	Thanh menu
//	Nhân viên
	ButtonMenu menuNhanVien;
//	Tài khoản
	ButtonMenu menuTaiKhoan;
//	Ứng viên
	ButtonMenu menuUngVien;
// 	Hồ sơ
	ButtonMenu menuHoSo;
// 	Nhà tuyển dụng
	ButtonMenu menuNhaTuyenDung;
// 	Tin tuyển dụng
	ButtonMenu menuTinTuyenDung;
// 	Hợp đồng
	ButtonMenu menuHopDong;
//	Tìm việc làm
	ButtonMenu menuTimViec;
// 	Thống kê
	ButtonMenu menuThongKe;
//	JMenuItem itemTKNhanVien, itemTKCongTy, itemTKHoSo, itemTKTinTuyenDung;
// 	Hệ thống
	ButtonMenu menuUser;
	ButtonMenu menuHome, menuLogout;
	JPanel menuPanel;
	
	MainFrame parent;
	
	public Navbar(Frame parent) {
		this.parent=(MainFrame) parent;
		
		setLayout(new GridLayout(0, 1));
		setForeground(Color.WHITE);
		
		menuNhanVien=createMenu("Nhân viên", "nhanvien");  
		menuTaiKhoan=createMenu("Tài khoản", "taikhoan");
		menuUngVien=createMenu("Ứng viên", "ungvien");
		menuHoSo=createMenu("Hồ sơ", "hoso");
		menuNhaTuyenDung=createMenu("Nhà tuyển dụng", "nhatuyendung");
		menuTinTuyenDung=createMenu("Tin tuyển dụng", "tintuyendung");
		menuHopDong=createMenu("Hợp đồng", "hopdong");
		menuTimViec=createMenu("Tìm việc làm", "timviec16");
		menuThongKe=createMenu("Thống kê", "thongke");
		menuHome=createMenu("Trang chủ", "home");
		menuLogout=createMenu("Đăng xuất", "exit");
		
		if(this.parent.getVaiTro().equalsIgnoreCase("Admin")) {
			add(menuNhanVien);
			add(menuTaiKhoan);
		}
		
		add(menuUngVien);
		add(menuHoSo);
		add(menuNhaTuyenDung);
		add(menuTinTuyenDung);
		add(menuHopDong);
		add(menuTimViec);
		
		if(this.parent.getVaiTro().equalsIgnoreCase("Admin")) {
			add(menuThongKe);
		}
		
		add(new JLabel(),"push");
		add(new JLabel(),"push");
		
		add(menuHome);
		add(menuLogout);
		
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
	    g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	    int w = getWidth(), h = getHeight();
	    Color color1 = Color.decode("#259195");
	    Color color2 = Color.decode("#ABC8CB");
	    GradientPaint gp = new GradientPaint(0, 0, color1, w, h, color2);
	    g2d.setPaint(gp);
	    g2d.fillRect(0, 0, w, h);
	}
	
	private ButtonMenu createMenu(String title, String nameImg) {
		ButtonMenu menu=new ButtonMenu(title);
		menu.setFont(new Font("Segoe UI",1,16));
		menu.setForeground(Color.WHITE);
		menu.setIcon(new ImageIcon(getClass().getResource("/image/"+nameImg+".png")));
		
		return menu;
	}
	
}
