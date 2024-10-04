package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainFrame extends JFrame {
	String userName;
	
//	Component
	JPanel leftPanel,menuPanel,
		mainPanel,northPanel, centerPanel, imgPanel;
	JLabel userLabel, iconUserLabel, vaitroLeftLabel;
	
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
		
		Navbar nav=new Navbar(this);
		
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
		centerPanel=new JPanel();
		centerPanel.setLayout(new BorderLayout(10, 10));
		centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		centerPanel.setBackground(new Color(220, 220, 220));
		
		imgPanel=new JPanel(); imgPanel.setPreferredSize(new Dimension(1100, 700));
		JLabel imgLabel=new JLabel();
		ImageIcon imgIcon=new ImageIcon(getClass().getResource("/image/timvieclam.png"));
		Image img=imgIcon.getImage().getScaledInstance(1600, 800, Image.SCALE_SMOOTH);
		imgLabel.setIcon(new ImageIcon(img));
		imgPanel.add(imgLabel);
		
		centerPanel.add(imgPanel);

		mainPanel.add(northPanel, BorderLayout.NORTH);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
	}
	

}
