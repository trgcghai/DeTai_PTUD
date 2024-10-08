package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import component.RoundPanel;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainFrame extends JFrame implements MouseListener{
	String userName;
	Navbar nav;
	
//	Component
	JPanel leftPanel,menuPanel,
		mainPanel,northPanel;
	JLabel userLabel, iconUserLabel, vaitroLeftLabel;
	RoundPanel centerPanel, imgPanel;
	
	public MainFrame(String userName) {
		setTitle("Dịch vụ tìm việc làm");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		this.userName=userName;

//		Tạo menu bar bên trái
		initLeft();
		
//		Tạo component bên phải
		initComponent();
		
		addMenuListener(nav);
		
//		Thêm vào frame
		add(leftPanel, BorderLayout.WEST);
		add(mainPanel, BorderLayout.CENTER);
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
		
		nav=new Navbar(this);
		
		menuPanel=new JPanel(); 
		menuPanel.setLayout(new BorderLayout()); menuPanel.setBackground(Color.WHITE);
		menuPanel.add(vaitroLeftLabel, BorderLayout.NORTH);
		menuPanel.add(nav, BorderLayout.CENTER);
		menuPanel.add(res, BorderLayout.SOUTH);
		
		leftPanel.add(menuPanel);
	}
	
	public void initComponent() {
		mainPanel=new JPanel(); 
		mainPanel.setLayout(new BorderLayout(5,5));
		mainPanel.setBackground(new Color(220, 220, 220));
		
//		Hiển thị tài khoản
		northPanel=new JPanel();
		northPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
		northPanel.setBackground(new Color(220, 220, 220));
		
		userLabel=new JLabel();
		userLabel.setFont(new Font("Segoe UI",0,16));
		userLabel.setText("Welcome "+userName);
		iconUserLabel=new JLabel();
		iconUserLabel.setIcon(new ImageIcon(getClass().getResource("/image/user.png")));
		
		northPanel.add(userLabel); northPanel.add(iconUserLabel);
		
//		Hiển thị hình ảnh
		centerPanel=new RoundPanel();
		centerPanel.setLayout(new BorderLayout(10, 10));
		centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		centerPanel.setBackground(new Color(220, 220, 220));
		
		imgPanel=new RoundPanel(); imgPanel.setPreferredSize(new Dimension(1100, 700));
		JLabel imgLabel=new JLabel();
		ImageIcon imgIcon=new ImageIcon(getClass().getResource("/image/timvieclam.png"));
		Image img=imgIcon.getImage().getScaledInstance(1600, 800, Image.SCALE_SMOOTH);
		imgLabel.setIcon(new ImageIcon(img));
		imgPanel.add(imgLabel);
		
		centerPanel.add(imgPanel);

		mainPanel.add(northPanel, BorderLayout.NORTH);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
	}
	
	public void addMenuListener(Navbar nav) {
		for(Component c: nav.getComponents()) {
			if(c.getClass().equals(JMenu.class)) {
				((JMenu)c).addMouseListener(this);
			}
		}
	}
	
//	1 Nhân viên     5 Nhà tuyển dụng    9 Thống kê
//	2 Tài khoản     6 Tin tuyển dụng
//	3 Ứng viên      7 Hợp đồng
//	4 Hồ sơ         8 Tìm việc làm

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		var obj=e.getSource();
		if(obj.getClass().equals(JMenu.class)) {
			if(((JMenu)obj).getText().equals("Nhân viên")) {
				centerPanel.removeAll();
				centerPanel.add(new NhanVienFrame("MinhDat").getPanel());
				
				this.setTitle("Nhân viên");
			}
			else if(((JMenu)obj).getText().equals("Tài khoản")) {
				centerPanel.removeAll();
				centerPanel.add(new TaiKhoanFrame("MinhDat").getPanel());
				
				this.setTitle("Tài khoản");
			}
			else if(((JMenu)obj).getText().equals("Ứng viên")) {
				centerPanel.removeAll();
				centerPanel.add(new UngVienFrame("MinhDat").getPanel());
				
				this.setTitle("Ứng viên");
			}
			else if(((JMenu)obj).getText().equals("Hồ sơ")) {
				centerPanel.removeAll();
				centerPanel.add(new HoSoFrame("MinhDat").getPanel());
				
				this.setTitle("Hồ sơ");
			}
			else if(((JMenu)obj).getText().equals("Nhà tuyển dụng")) {
				centerPanel.removeAll();
				centerPanel.add(new NhaTuyenDungFrame("MinhDat").getPanel());
				
				this.setTitle("Nhà tuyển dụng");
			}
			else if(((JMenu)obj).getText().equals("Tin tuyển dụng")) {
				centerPanel.removeAll();
				centerPanel.add(new TinTuyenDungFrame("MinhDat").getPanel());
				
				this.setTitle("Tin tuyển dụng");
			}
			else if(((JMenu)obj).getText().equals("Hợp đồng")) {
				centerPanel.removeAll();
				centerPanel.add(new HopDongFrame("MinhDat").getPanel());
				
				this.setTitle("Hợp đồng");
			}
			else if(((JMenu)obj).getText().equals("Tìm việc làm")) {
				centerPanel.removeAll();
				centerPanel.add(new TimViecLamFrame("MinhDat").getPanel());
				
				this.setTitle("Tìm việc làm");
			}
			else if(((JMenu)obj).getText().equals("Thống kê")) {
				centerPanel.removeAll();
				centerPanel.add(new ThongKeFrame("MinhDat").getPanel());
				
				this.setTitle("Thống kê");
			}
			centerPanel.revalidate();
			centerPanel.repaint();
		}
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
