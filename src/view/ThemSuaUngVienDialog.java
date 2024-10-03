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

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import controller.LabelDateFormatter;
import entity.constraint.GioiTinh;

public class ThemSuaUngVienDialog extends JDialog {
	
	JPanel inforUngVienPanel, btnPanel;
	JLabel idLabel, tenLabel, sdtLabel, dateLabel, gioitinhLabel, diachiLabel,emailLabel;
	JTextField idText, tenText, sdtText, diachiText, emailText;
	JComboBox gioitinhText;
	UtilDateModel modelDate;
	JDatePickerImpl dateText;
	JButton btnThem, btnHuy;

	public ThemSuaUngVienDialog(Frame parent, boolean modal) {
		super(parent, modal);
		setTitle("Thêm mới ứng viên");
		setResizable(false);
		setSize(550,400);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		
		initComponent();
	}
	
	public void initComponent() {
		inforUngVienPanel=new JPanel(); 
		inforUngVienPanel.setBackground(Color.WHITE);
		inforUngVienPanel.setLayout(new GridBagLayout());
		inforUngVienPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		GridBagConstraints gbc= new GridBagConstraints();
		
//		Thông tin ứng viên
		gbc.gridx=0; gbc.gridy=0; gbc.insets=new Insets(5, 10, 5, 10); gbc.anchor=GridBagConstraints.WEST;
		idLabel=new JLabel("Mã ứng viên"); idLabel.setFont(new Font("Segoe UI",0,16));
		inforUngVienPanel.add(idLabel, gbc);
		gbc.gridx=0; gbc.gridy=1;
		idText=new JTextField(10); idText.setFont(new Font("Segoe UI",0,16));
		idText.setEditable(false);
		inforUngVienPanel.add(idText, gbc);
		
		gbc.gridx=1; gbc.gridy=0;
		tenLabel=new JLabel("Họ tên"); tenLabel.setFont(new Font("Segoe UI",0,16));
		inforUngVienPanel.add(tenLabel, gbc);
		gbc.gridx=1; gbc.gridy=1;
		tenText=new JTextField(20); tenText.setFont(new Font("Segoe UI",0,16));
		inforUngVienPanel.add(tenText, gbc);
		
		gbc.gridx=0; gbc.gridy=2;
		dateLabel=new JLabel("Ngày sinh"); dateLabel.setFont(new Font("Segoe UI",0,16));
		inforUngVienPanel.add(dateLabel, gbc);
		gbc.gridx=0; gbc.gridy=3;
		modelDate=new UtilDateModel();
		Properties p=new Properties();
		p.put("text.day", "Day"); p.put("text.month", "Month"); p.put("text.year", "Year");
		JDatePanelImpl panelDate=new JDatePanelImpl(modelDate, p);
		dateText=new JDatePickerImpl(panelDate, new LabelDateFormatter());
		dateText.setPreferredSize(new Dimension(150,25));
		modelDate.setValue(new Date());
		inforUngVienPanel.add(dateText, gbc);
		
		gbc.gridx=1; gbc.gridy=2;
		sdtLabel=new JLabel("Số điện thoại"); sdtLabel.setFont(new Font("Segoe UI",0,16));
		inforUngVienPanel.add(sdtLabel, gbc);
		gbc.gridx=1; gbc.gridy=3;
		sdtText=new JTextField(20); sdtText.setFont(new Font("Segoe UI",0,16));
		inforUngVienPanel.add(sdtText, gbc);
		
		gbc.gridx=0; gbc.gridy=4;
		gioitinhLabel=new JLabel("Giới tính"); gioitinhLabel.setFont(new Font("Segoe UI",0,16));
		inforUngVienPanel.add(gioitinhLabel, gbc);
		gbc.gridx=0; gbc.gridy=5;
		gioitinhText=new JComboBox(); gioitinhText.setFont(new Font("Segoe UI",0,16));
		GioiTinh[] gioitinhs=GioiTinh.class.getEnumConstants();
		for(GioiTinh g: gioitinhs) {
			gioitinhText.addItem(g.getValue());
		}
		gioitinhText.setPreferredSize(new Dimension(150,25));
		inforUngVienPanel.add(gioitinhText, gbc);
		
		gbc.gridx=1; gbc.gridy=4;
		diachiLabel=new JLabel("Địa chỉ"); diachiLabel.setFont(new Font("Segoe UI",0,16));
		inforUngVienPanel.add(diachiLabel, gbc);
		gbc.gridx=1; gbc.gridy=5;
		diachiText=new JTextField(20); diachiText.setFont(new Font("Segoe UI",0,16));
		inforUngVienPanel.add(diachiText, gbc);
		
		gbc.gridx=1; gbc.gridy=6;
		emailLabel=new JLabel("Email"); emailLabel.setFont(new Font("Segoe UI",0,16));
		inforUngVienPanel.add(emailLabel,gbc);
		gbc.gridx=1; gbc.gridy=7;
		emailText=new JTextField(20); emailText.setFont(new Font("Segoe UI",0,16));
		inforUngVienPanel.add(emailText,gbc);
		
		add(inforUngVienPanel, BorderLayout.CENTER);
		
//		Button
		btnPanel=new JPanel(); 
		btnPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		btnPanel.setBackground(Color.WHITE);
		btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 35));
		
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
