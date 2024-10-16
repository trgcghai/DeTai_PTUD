package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import component.GradientPanel;
import controller.LabelDateFormatter;
import entity.constraint.GioiTinh;
import entity.constraint.TrangThai;

public class ChiTietHoSoDialog extends JDialog{
	
	GradientPanel inforUngVienPanel, btnPanel;
	JLabel idLabel, tenLabel, sdtLabel, dateLabel, gioitinhLabel, diachiLabel,emailLabel,trangthaiLabel, motaLabel,
			nhatuyendungLabel, tintuyendungLabel;
	JTextField idText, tenText, sdtText, diachiText, emailText,
			nhatuyendungText, tintuyendungText;
	JComboBox gioitinhText, trangthaiText;
	UtilDateModel modelDate;
	JDatePickerImpl dateText;
	JTextArea motaText;
	JScrollPane scrollMoTa;
	JButton btnThem, btnHuy;
	GridBagConstraints gbc;
	
	public ChiTietHoSoDialog(Dialog parent, boolean modal) {
		super(parent, modal);
		setTitle("Xem chi tiết hồ sơ");
		setResizable(false);
		setSize(800,680);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		
		initComponent();
	}
	
	public ChiTietHoSoDialog(Frame parent, boolean modal) {
		super(parent, modal);
		setTitle("Xem chi tiết hồ sơ");
		setResizable(false);
		setSize(800,680);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		
		initComponent();
	}
	
	public void initComponent() {
		inforUngVienPanel=new GradientPanel(Color.decode("#ABC8CB"), Color.decode("#7CBDBF")); 
		inforUngVienPanel.setBackground(Color.WHITE);
		inforUngVienPanel.setLayout(new GridBagLayout());
		inforUngVienPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		gbc= new GridBagConstraints();
		
//		Thông tin hồ sơ
		gbc.gridx=0; gbc.gridy=0; gbc.insets=new Insets(5, 10, 5, 10); gbc.anchor=GridBagConstraints.WEST;
		idLabel=new JLabel("Mã hồ sơ"); idLabel.setFont(new Font("Segoe UI",0,16));
		inforUngVienPanel.add(idLabel, gbc);
		gbc.gridx=0; gbc.gridy=1;
		idText=new JTextField(10); idText.setFont(new Font("Segoe UI",0,16));
		idText.setEditable(false);
		inforUngVienPanel.add(idText, gbc);
		
		gbc.gridx=1; gbc.gridy=0;
		trangthaiLabel=new JLabel("Trạng thái"); trangthaiLabel.setFont(new Font("Segoe UI",0,16));
		inforUngVienPanel.add(trangthaiLabel, gbc);
		gbc.gridx=1; gbc.gridy=1;
		trangthaiText=new JComboBox(); trangthaiText.setFont(new Font("Segoe UI",0,16));
		TrangThai[] trangthais=TrangThai.class.getEnumConstants();
		for(TrangThai t: trangthais) {
			trangthaiText.addItem(t.getValue());
		}
		trangthaiText.setPreferredSize(new Dimension(156,26));
		inforUngVienPanel.add(trangthaiText, gbc);
		
		gbc.gridx=2; gbc.gridy=0;
		tenLabel=new JLabel("Họ tên ứng viên"); tenLabel.setFont(new Font("Segoe UI",0,16));
		inforUngVienPanel.add(tenLabel, gbc);
		gbc.gridx=2; gbc.gridy=1; gbc.gridwidth=2;
		tenText=new JTextField(23); tenText.setFont(new Font("Segoe UI",0,16));
		tenText.setEditable(false);
		inforUngVienPanel.add(tenText, gbc);
		
		gbc.gridx=0; gbc.gridy=2; gbc.gridwidth=1;
		emailLabel=new JLabel("Email"); emailLabel.setFont(new Font("Segoe UI",0,16));
		inforUngVienPanel.add(emailLabel,gbc);
		gbc.gridx=0; gbc.gridy=3; gbc.gridwidth=2;
		emailText=new JTextField(23); emailText.setFont(new Font("Segoe UI",0,16));
		emailText.setEditable(false);
		inforUngVienPanel.add(emailText,gbc);
		
		gbc.gridx=2; gbc.gridy=2; gbc.gridwidth=1;
		sdtLabel=new JLabel("Số điện thoại"); sdtLabel.setFont(new Font("Segoe UI",0,16));
		inforUngVienPanel.add(sdtLabel, gbc);
		gbc.gridx=2; gbc.gridy=3; gbc.gridwidth=2;
		sdtText=new JTextField(23); sdtText.setFont(new Font("Segoe UI",0,16));
		sdtText.setEditable(false);
		inforUngVienPanel.add(sdtText, gbc);
		
		gbc.gridx=0; gbc.gridy=4; gbc.gridwidth=1;
		dateLabel=new JLabel("Ngày sinh"); dateLabel.setFont(new Font("Segoe UI",0,16));
		inforUngVienPanel.add(dateLabel, gbc);
		gbc.gridx=0; gbc.gridy=5;
		modelDate=new UtilDateModel();
		Properties p=new Properties();
		p.put("text.day", "Day"); p.put("text.month", "Month"); p.put("text.year", "Year");
		JDatePanelImpl panelDate=new JDatePanelImpl(modelDate, p);
		dateText=new JDatePickerImpl(panelDate, new LabelDateFormatter());
		dateText.setPreferredSize(new Dimension(150,25));
		modelDate.setValue(new Date());
		inforUngVienPanel.add(dateText, gbc);
		
		gbc.gridx=1; gbc.gridy=4;
		gioitinhLabel=new JLabel("Giới tính"); gioitinhLabel.setFont(new Font("Segoe UI",0,16));
		inforUngVienPanel.add(gioitinhLabel, gbc);
		gbc.gridx=1; gbc.gridy=5;
		gioitinhText=new JComboBox(); gioitinhText.setFont(new Font("Segoe UI",0,16));
		GioiTinh[] gioitinhs=GioiTinh.class.getEnumConstants();
		for(GioiTinh g: gioitinhs) {
			gioitinhText.addItem(g.getValue());
		}
		gioitinhText.setPreferredSize(new Dimension(156,25));
		inforUngVienPanel.add(gioitinhText, gbc);
		
		gbc.gridx=2; gbc.gridy=4;
		diachiLabel=new JLabel("Địa chỉ"); diachiLabel.setFont(new Font("Segoe UI",0,16));
		inforUngVienPanel.add(diachiLabel, gbc);
		gbc.gridx=2; gbc.gridy=5; gbc.gridwidth=2;
		diachiText=new JTextField(23); diachiText.setFont(new Font("Segoe UI",0,16));
		diachiText.setEditable(false);
		inforUngVienPanel.add(diachiText, gbc);
		
		gbc.gridx=0; gbc.gridy=8;
		motaLabel=new JLabel("Mô tả hồ sơ"); motaLabel.setFont(new Font("Segoe UI",0,16));
		inforUngVienPanel.add(motaLabel, gbc);
		gbc.gridx=0; gbc.gridy=9; gbc.gridwidth=4;
		motaText=new JTextArea(10, 48); motaText.setFont(new Font("Segoe UI",0,16));
		motaText.setBorder(BorderFactory.createLineBorder(new Color(0,191,165)));
		motaText.setLineWrap(true);
		motaText.setEditable(false);
		scrollMoTa=new JScrollPane(motaText);
		inforUngVienPanel.add(scrollMoTa, gbc);
		
		gbc.gridx=0; gbc.gridy=6; gbc.gridwidth=1;
		nhatuyendungLabel=new JLabel("Nhà tuyển dụng"); nhatuyendungLabel.setFont(new Font("Segoe UI",0,16));
		inforUngVienPanel.add(nhatuyendungLabel,gbc);
		gbc.gridx=0; gbc.gridy=7; gbc.gridwidth=2;
		nhatuyendungText=new JTextField(23); nhatuyendungText.setFont(new Font("Segoe UI",0,16));
		nhatuyendungText.setEditable(false);
		inforUngVienPanel.add(nhatuyendungText,gbc);
		
		gbc.gridx=2; gbc.gridy=6; gbc.gridwidth=1;
		tintuyendungLabel=new JLabel("Tin tuyển dụng"); tintuyendungLabel.setFont(new Font("Segoe UI",0,16));
		inforUngVienPanel.add(tintuyendungLabel,gbc);
		gbc.gridx=2; gbc.gridy=7; gbc.gridwidth=2;
		tintuyendungText=new JTextField(23); tintuyendungText.setFont(new Font("Segoe UI",0,16));
		tintuyendungText.setEditable(false);
		inforUngVienPanel.add(tintuyendungText,gbc);
		
		add(inforUngVienPanel, BorderLayout.CENTER);
		
//		Button
		btnPanel=new GradientPanel(Color.decode("#ABC8CB"), Color.decode("#7CBDBF")); 
		btnPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		btnPanel.setBackground(Color.WHITE);
		btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 50));
		
		btnHuy=new JButton("Hủy"); btnHuy.setFont(new Font("Segoe UI",0,16));
		btnHuy.setPreferredSize(new Dimension(120,25));
		btnHuy.setBackground(Color.RED);
		btnHuy.setForeground(Color.WHITE);
		
		btnPanel.add(btnHuy);
		
		add(btnPanel, BorderLayout.SOUTH);
	}
}
