package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Date;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import component.GradientPanel;
import controller.LabelDateFormatter;
import entity.constraint.GioiTinh;

public class ThemSuaNhaTuyenDungDialog extends JDialog {
	
	JPanel logoPanel;
	GradientPanel inforNhaTuyenDungPanel, btnPanel;
	JLabel idLabel, tenLabel, sdtLabel, dateLabel, gioitinhLabel, diachiLabel,emailLabel;
	JTextField idText, tenText, sdtText, diachiText, emailText;
	JComboBox gioitinhText;
	UtilDateModel modelDate;
	JDatePickerImpl dateText;
	JButton btnThem, btnHuy, btnLogo;

	public ThemSuaNhaTuyenDungDialog(Frame parent, boolean modal) {
		super(parent, modal);
		setTitle("Thêm mới nhà tuyển dụng");
		setResizable(false);
		setSize(650,450);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		
		initComponent();
	}
	
	public ThemSuaNhaTuyenDungDialog(Frame parent, boolean modal, boolean check) {
		this(parent, modal);
		setTitle("Cập nhật nhà tuyển dụng");
		btnThem.setText("Cập nhật");
	}
	
	public void initComponent() {
		inforNhaTuyenDungPanel=new GradientPanel(Color.decode("#ABC8CB"), Color.decode("#7CBDBF")); 
		inforNhaTuyenDungPanel.setBackground(Color.WHITE);
		inforNhaTuyenDungPanel.setLayout(new GridBagLayout());
		inforNhaTuyenDungPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		GridBagConstraints gbc= new GridBagConstraints();
		
//		Thông tin nhà tuyển dụng
		JPanel resId=new JPanel();
		resId.setOpaque(false);
		resId.setLayout(new BorderLayout(0,10)); resId.setBackground(Color.WHITE);
		gbc.gridx=0; gbc.gridy=0; gbc.insets=new Insets(5, 10, 5, 10); gbc.anchor=GridBagConstraints.WEST;
		idLabel=new JLabel("Mã nhà tuyển dụng"); idLabel.setFont(new Font("Segoe UI",0,16));
		resId.add(idLabel, BorderLayout.NORTH);
		idText=new JTextField(10); idText.setFont(new Font("Segoe UI",0,16));
		idText.setEditable(false);
		resId.add(idText, BorderLayout.CENTER);
		inforNhaTuyenDungPanel.add(resId, gbc);
		
		gbc.gridx=1; gbc.gridy=0; gbc.gridheight=2;
		logoPanel=new JPanel();
		logoPanel.setOpaque(false);
		logoPanel.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(0,191,165)), "Logo",
				TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI",0,15)));
		logoPanel.setPreferredSize(new Dimension(100, 100));
		logoPanel.setBackground(Color.WHITE);
		inforNhaTuyenDungPanel.add(logoPanel, gbc);
		
		gbc.gridx=2; gbc.gridy=1; gbc.gridheight=1;
		btnLogo=new JButton("Chọn logo");
		btnLogo.setFont(new Font("Segoe UI",0,16));
		btnLogo.setBackground(new Color(0,102,102));
		btnLogo.setForeground(Color.WHITE);
		btnLogo.setPreferredSize(new Dimension(110,25));
		inforNhaTuyenDungPanel.add(btnLogo, gbc);
		
		gbc.gridx=0; gbc.gridy=2;
		tenLabel=new JLabel("Tên nhà tuyển dụng"); tenLabel.setFont(new Font("Segoe UI",0,16));
		inforNhaTuyenDungPanel.add(tenLabel, gbc);
		gbc.gridx=0; gbc.gridy=3;
		tenText=new JTextField(20); tenText.setFont(new Font("Segoe UI",0,16));
		inforNhaTuyenDungPanel.add(tenText, gbc);
		
		gbc.gridx=1; gbc.gridy=2;
		sdtLabel=new JLabel("Số điện thoại"); sdtLabel.setFont(new Font("Segoe UI",0,16));
		inforNhaTuyenDungPanel.add(sdtLabel, gbc);
		gbc.gridx=1; gbc.gridy=3; gbc.gridwidth=2;
		sdtText=new JTextField(20); sdtText.setFont(new Font("Segoe UI",0,16));
		inforNhaTuyenDungPanel.add(sdtText, gbc);
		
		gbc.gridx=0; gbc.gridy=4; gbc.gridwidth=1;
		diachiLabel=new JLabel("Địa chỉ"); diachiLabel.setFont(new Font("Segoe UI",0,16));
		inforNhaTuyenDungPanel.add(diachiLabel, gbc);
		gbc.gridx=0; gbc.gridy=5;
		diachiText=new JTextField(20); diachiText.setFont(new Font("Segoe UI",0,16));
		inforNhaTuyenDungPanel.add(diachiText, gbc);
		
		gbc.gridx=1; gbc.gridy=4;
		emailLabel=new JLabel("Email"); emailLabel.setFont(new Font("Segoe UI",0,16));
		inforNhaTuyenDungPanel.add(emailLabel,gbc);
		gbc.gridx=1; gbc.gridy=5; gbc.gridwidth=2;
		emailText=new JTextField(20); emailText.setFont(new Font("Segoe UI",0,16));
		inforNhaTuyenDungPanel.add(emailText,gbc);
		
		add(inforNhaTuyenDungPanel, BorderLayout.CENTER);
		
//		Button
		btnPanel=new GradientPanel(Color.decode("#ABC8CB"), Color.decode("#7CBDBF")); 
		btnPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		btnPanel.setBackground(Color.WHITE);
		btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20));
		
		btnThem=new JButton("Thêm mới"); btnThem.setFont(new Font("Segoe UI",0,16));
		btnThem.setPreferredSize(new Dimension(120,25));
		btnThem.setBackground(new Color(0,102,102));
		btnThem.setForeground(Color.WHITE);
		
		btnHuy=new JButton("Hủy"); btnHuy.setFont(new Font("Segoe UI",0,16));
		btnHuy.setPreferredSize(new Dimension(120,25));
		btnHuy.setBackground(Color.WHITE);
		btnHuy.setForeground(Color.BLACK);
		
		btnPanel.add(btnThem); btnPanel.add(btnHuy);
		
		add(btnPanel, BorderLayout.SOUTH);
	}
}
