package view;

import javax.swing.*;

import component.ButtonMenu;
import component.RoundPanel;
import entity.NhanVien;
import entity.constraint.VaiTro;

import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame implements ActionListener{
	NhanVien userName;
	String vaitro;
	Navbar nav;
	
//	Component
	JPanel leftPanel, menuPanel, mainPanel,northPanel,imgPanel;
	JLabel userLabel, iconUserLabel, vaitroLeftLabel;
	RoundPanel centerPanel;
//	GradientPanel menuPanel;
	
	public MainFrame(NhanVien userName, String vaitro) {
		setTitle("Dịch vụ tìm việc làm");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		this.userName=userName;
		this.vaitro=vaitro;

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
		leftPanel.setBackground(new Color(89, 145, 144));
		
		vaitroLeftLabel=new JLabel("", SwingConstants.CENTER);
		vaitroLeftLabel.setFont(new Font("Segoe UI",1,16));
		vaitroLeftLabel.setForeground(Color.WHITE);
		vaitroLeftLabel.setPreferredSize(new Dimension(getWidth(), 50));
		VaiTro[] vaitros=VaiTro.class.getEnumConstants();
		for(VaiTro v: vaitros) {
			if(v.toString().equalsIgnoreCase(vaitro)) {
				vaitroLeftLabel.setText(v.getValue());
				break;
			}
		}
		
		nav=new Navbar(this);
		
		menuPanel=new JPanel(); 
		menuPanel.setLayout(new BorderLayout());
		menuPanel.setBackground(Color.decode("#259195"));
		menuPanel.add(vaitroLeftLabel, BorderLayout.NORTH);
		menuPanel.add(nav, BorderLayout.CENTER);
		
		leftPanel.add(menuPanel);
	}
	
	public void initComponent() {
		mainPanel=new JPanel(); 
		mainPanel.setLayout(new BorderLayout(5,5));
		mainPanel.setBackground(new Color(89, 145, 144));
		
//		Hiển thị tài khoản
		northPanel=new JPanel();
		northPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
		northPanel.setBackground(new Color(89, 145, 144));
		
		userLabel=new JLabel();
		userLabel.setFont(new Font("Segoe UI",1,16));
		userLabel.setText("Welcome "+userName.getTenNV());
		userLabel.setForeground(Color.WHITE);
		iconUserLabel=new JLabel();
		iconUserLabel.setIcon(new ImageIcon(getClass().getResource("/image/user.png")));
		
		northPanel.add(userLabel); northPanel.add(iconUserLabel);
		
//		Hiển thị hình ảnh
		centerPanel=new RoundPanel();
		centerPanel.setLayout(new BorderLayout(10, 10));
		centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		centerPanel.setBackground(new Color(89, 145, 144));
		
		imgPanel=new JPanel(); imgPanel.setPreferredSize(new Dimension(1100, 800));
		imgPanel.setBackground(Color.WHITE);
		JLabel imgLabel=new JLabel();
		ImageIcon imgIcon=new ImageIcon(getClass().getResource("/image/timvieclam.png"));
		Image img=imgIcon.getImage().getScaledInstance(1600, 800, Image.SCALE_SMOOTH);
		imgLabel.setIcon(new ImageIcon(img));
		imgPanel.add(imgLabel);
		
		centerPanel.add(imgPanel);

		mainPanel.add(northPanel, BorderLayout.NORTH);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
	}
	
	public JPanel getPanel() {
		return this.centerPanel;
	}
	
	public String getVaiTro() {
		return vaitro;
	}
	
	public void addMenuListener(Navbar nav) {
		for(Component c: nav.getComponents()) {
			if(c.getClass().equals(ButtonMenu.class)) {
				((ButtonMenu)c).addActionListener(this);;
			}
		}
	}
	
	private void setSelected(ButtonMenu menu) {
        for (Component com : nav.getComponents()) {
            if (com instanceof ButtonMenu) {
                ButtonMenu b = (ButtonMenu) com;
                b.setSelected(false);
            }
        }
        menu.setSelected(true);
    }
	
//	1 Nhân viên     5 Nhà tuyển dụng    9 Thống kê
//	2 Tài khoản     6 Tin tuyển dụng
//	3 Ứng viên      7 Hợp đồng
//	4 Hồ sơ         8 Tìm việc làm
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		var obj=e.getSource();
		if(obj.getClass().equals(ButtonMenu.class)) {
			if(((ButtonMenu)obj).getText().equals("Nhân viên")) {
				setSelected(((ButtonMenu)obj));
				centerPanel.removeAll();
				centerPanel.add(new NhanVienFrame(userName).getPanel());
				this.setTitle("Nhân viên");
			}
			else if(((ButtonMenu)obj).getText().equals("Tài khoản")) {
				setSelected(((ButtonMenu)obj));
				centerPanel.removeAll();
				centerPanel.add(new TaiKhoanFrame(userName).getPanel());
				this.setTitle("Tài khoản");
			}
			else if(((ButtonMenu)obj).getText().equals("Ứng viên")) {
				setSelected(((ButtonMenu)obj));
				centerPanel.removeAll();
				centerPanel.add(new UngVienFrame(userName).getPanel());
				this.setTitle("Ứng viên");
			}
			else if(((ButtonMenu)obj).getText().equals("Hồ sơ")) {
				setSelected(((ButtonMenu)obj));
				centerPanel.removeAll();
				centerPanel.add(new HoSoFrame(userName).getPanel());
				this.setTitle("Hồ sơ");
			}
			else if(((ButtonMenu)obj).getText().equals("Nhà tuyển dụng")) {
				setSelected(((ButtonMenu)obj));
				centerPanel.removeAll();
				centerPanel.add(new NhaTuyenDungFrame(userName).getPanel());
				this.setTitle("Nhà tuyển dụng");
			}
			else if(((ButtonMenu)obj).getText().equals("Tin tuyển dụng")) {
				setSelected(((ButtonMenu)obj));
				centerPanel.removeAll();
				centerPanel.add(new TinTuyenDungFrame(userName).getPanel());
				this.setTitle("Tin tuyển dụng");
			}
			else if(((ButtonMenu)obj).getText().equals("Hợp đồng")) {
				setSelected(((ButtonMenu)obj));
				centerPanel.removeAll();
				centerPanel.add(new HopDongFrame(userName).getPanel());
				this.setTitle("Hợp đồng");
			}
			else if(((ButtonMenu)obj).getText().equals("Tìm việc làm")) {
				setSelected(((ButtonMenu)obj));
				centerPanel.removeAll();
				centerPanel.add(new TimViecLamFrame(userName).getPanel());
				this.setTitle("Tìm việc làm");
			}
			else if(((ButtonMenu)obj).getText().equals("Thống kê")) {
				setSelected(((ButtonMenu)obj));
				centerPanel.removeAll();
				centerPanel.add(new ThongKeFrame(userName).getPanel());
				this.setTitle("Thống kê");
			}
			else if(((ButtonMenu)obj).getText().equals("Trang chủ")) {
				setSelected(((ButtonMenu)obj));
				centerPanel.removeAll();
				centerPanel.add(new MainFrame(userName, vaitro).getPanel());
				this.setTitle("Dịch vụ tìm việc làm");
			}
			else if(((ButtonMenu)obj).getText().equals("Đăng xuất")) {
				setSelected(((ButtonMenu)obj));
				this.dispose();
				new LoginFrame().setVisible(true);
			}
			centerPanel.revalidate();
			centerPanel.repaint();
		}
	}

}
