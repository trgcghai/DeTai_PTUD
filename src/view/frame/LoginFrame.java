package view.frame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.*;

import controller.Database;
import controller.FilterImp;
import dao.NhanVien_DAO;
import dao.TaiKhoan_DAO;
import exception.checkUserEmail;
import exception.checkUserName;
import exception.checkUserPass;
import entity.NhanVien;
import entity.TaiKhoan;

public class LoginFrame extends JFrame implements ActionListener {
	
	JPanel panelRight, panelLeft;
	JLabel imgLabel, loginTitle,
		nameUserLabel, passLabel,
		msgLoginLabel;
	JTextField nameUserText;
	JPasswordField passText;
	JButton btnLogin, btnSignup;
	
	private TaiKhoan_DAO listTaiKhoan;
	private NhanVien_DAO listNhanVien;
	
	public LoginFrame() {
		setTitle("Đăng nhập");
		setSize(800,450);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		
		initComponent();
		addActionListener();
		
		listTaiKhoan=new TaiKhoan_DAO();
		listNhanVien=new NhanVien_DAO();
		
		Database.getInstance().connect();
		loadUser();
		
	}
	
	public void initComponent() {
//		Phần login bên trái
		panelLeft= new JPanel();
		panelLeft.setPreferredSize(new Dimension(400,500));
		panelLeft.setBackground(new Color(0, 102, 102));
		panelLeft.setLayout(new GridBagLayout());
		GridBagConstraints gbcL=new GridBagConstraints();
		gbcL.anchor=GridBagConstraints.CENTER;
		
		imgLabel=new JLabel();
		ImageIcon imgIcon=new ImageIcon(getClass().getResource("/image/Logo2.png"));
		Image image=imgIcon.getImage().getScaledInstance(140, 160, Image.SCALE_SMOOTH);
		imgLabel.setIcon(new ImageIcon(image));
		panelLeft.add(imgLabel,gbcL);
		
		add(panelLeft, BorderLayout.WEST);
		
//		Phần login bên phải
		panelRight= new JPanel();
		panelRight.setPreferredSize(new Dimension(400,500));
		panelRight.setBackground(new Color(255, 255, 255));
		panelRight.setLayout(new GridBagLayout());
		GridBagConstraints gbcR=new GridBagConstraints();
		
		loginTitle=new JLabel("ĐĂNG NHẬP");
		loginTitle.setFont(new Font("Segoe UI",1,30));
		loginTitle.setForeground(new Color(0,102,102));
		gbcR.anchor=GridBagConstraints.CENTER;
		gbcR.insets= new Insets(0, 5, 20, 5);
		gbcR.gridwidth=GridBagConstraints.REMAINDER;
		panelRight.add(loginTitle,gbcR);
		
		nameUserLabel=new JLabel("Tên đăng nhập");
		nameUserLabel.setFont(new Font("Segoe UI",0,13));
		nameUserLabel.setBackground(new Color(102, 102, 102));
		gbcR.anchor=GridBagConstraints.WEST;
		gbcR.gridy=1;
		gbcR.insets= new Insets(5, 5, 5, 5);
		panelRight.add(nameUserLabel,gbcR);
		
		nameUserText=new JTextField(30);
		nameUserText.setPreferredSize(new Dimension(200,30));
		nameUserText.setFont(new Font("Segoe UI",0,13));
		gbcR.gridy=2;
		panelRight.add(nameUserText,gbcR);
		
		passLabel=new JLabel("Mật khẩu");
		passLabel.setFont(new Font("Segoe UI",0,13));
		passLabel.setBackground(new Color(102, 102, 102));
		gbcR.gridy=3;
		panelRight.add(passLabel,gbcR);
		
		passText=new JPasswordField(30);
		passText.setPreferredSize(new Dimension(200,30));
		passText.setFont(new Font("Segoe UI",0,13));
		gbcR.gridy=4;
		panelRight.add(passText,gbcR);
		
		btnLogin=new JButton("Đăng nhập");
		btnLogin.setFont(new Font("Segoe UI",0,13));
		btnLogin.setForeground(new Color(255, 255, 255));
		btnLogin.setBackground(new Color(0, 102, 102));
		btnLogin.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "Enter pressed");
		btnLogin.getActionMap().put("Enter pressed",new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				login();
			}
		});
		gbcR.gridy=5;
		panelRight.add(btnLogin,gbcR);
		
		add(panelRight, BorderLayout.EAST);
	}
	
	public void addActionListener() {
		btnLogin.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		var obj=e.getSource();
		if(obj.equals(btnLogin)) {
			login();
		}
	}
	
	public void login() {
		String userName=nameUserText.getText();
		String pass=passText.getText();
		
		var check=new FilterImp();
		try {
			if(check.checkUserEmail(userName) && check.checkUserPass(pass)) {
				TaiKhoan user=new TaiKhoan(userName,pass);
				if(listTaiKhoan.getListTaiKhoan().contains(user)) {
					int index=listTaiKhoan.getListTaiKhoan().indexOf(user);
					String vaitro=listTaiKhoan.getListTaiKhoan().get(index).getVaiTro().getValue();
					NhanVien nv=listNhanVien
							.getNhanVien(listTaiKhoan.getListTaiKhoan().get(index).getNhanVien().getMaNV());
					new MainFrame(nv, vaitro).setVisible(true);
					this.dispose();
				}
				else {
					JOptionPane.showMessageDialog(rootPane, "Tài khoản không tồn tại");
				}
			}
		} 
		catch (checkUserEmail e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(rootPane, e.getMessage());
		}
		catch (checkUserPass e) {
			JOptionPane.showMessageDialog(rootPane, e.getMessage());
		}
	}
	
	public void loadUser() {
		listTaiKhoan.setListTaiKhoan(listTaiKhoan.getDsTaiKhoan());
	}
}
