package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import org.apache.commons.compress.harmony.unpack200.bytecode.forms.ThisFieldRefForm;

public class Navbar extends JMenuBar{

//	Thanh menu
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
// 	Thống kê
	JMenu menuThongKe;
//	JMenuItem itemTKNhanVien, itemTKCongTy, itemTKHoSo, itemTKTinTuyenDung;
// 	Hệ thống
	JMenu menuUser;
	JMenuItem itemHome, itemLogout;
	JPanel menuPanel;
	
	JFrame parent;
	
	public Navbar(Frame parent) {
		this.parent=(JFrame) parent;
		
		setLayout(new GridLayout(0, 1));
		setBackground(Color.WHITE);
		
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
		
		menuThongKe=new JMenu("Thống kê");
		menuThongKe.setFont(new Font("Segoe UI",0,16));
		menuThongKe.setIcon(new ImageIcon(getClass().getResource("/image/thongke.png")));
		
		add(menuNhanVien);
		add(menuTaiKhoan);
		add(menuUngVien);
		add(menuHoSo);
		add(menuNhaTuyenDung);
		add(menuTinTuyenDung);
		add(menuHopDong);
		add(menuThongKe);
		
		addMouseListener();
	}
	
	private void addMouseListener() {
		menuNhanVien.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				new NhanVienFrame("MinhDat").setVisible(true);
				parent.dispose();
			}
		});

		menuUngVien.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				new UngVienFrame("MinhDat").setVisible(true);
				parent.dispose();
			}
		});

		menuTaiKhoan.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				new TaiKhoanFrame("MinhDat").setVisible(true);
				parent.dispose();
			}
		});

		menuHoSo.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				new HoSoFrame("MinhDat").setVisible(true);
				parent.dispose();
			}
		});

		menuNhaTuyenDung.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				new NhaTuyenDungFrame("MinhDat").setVisible(true);
				parent.dispose();
			}
		});
		
		menuTinTuyenDung.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				new TinTuyenDungFrame("MinhDat").setVisible(true);
				parent.dispose();
			}
		});


	}
}
