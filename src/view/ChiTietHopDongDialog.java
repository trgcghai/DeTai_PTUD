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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import component.ComboBoxRenderer;
import component.GradientPanel;
import controller.LabelDateFormatter;
import entity.constraint.GioiTinh;
import entity.constraint.HinhThucLamViec;
import entity.constraint.NganhNghe;
import entity.constraint.TrangThai;
import entity.constraint.TrinhDo;

public class ChiTietHopDongDialog extends JDialog{
	
	GradientPanel inforHopDongPanel, btnPanel;
	JLabel idLabel, tenLabel, ngaylapLabel, tieudeLabel, phiLabel,
			luongLabel, nhatuyendungLabel, sdtLabel, emailLabel, nhanvienLabel;
	JTextField idText, tenText, tieudeText,
			luongText, phiText, nhatuyendungText, sdtText, emailText, nhanvienText;
	UtilDateModel modelDateNgayLap;
	JDatePickerImpl ngaylapText;
	JButton btnLuu, btnHuy;
	GridBagConstraints gbc;
	
	public ChiTietHopDongDialog(Frame parent, boolean modal) {
		super(parent, modal);
		setTitle("Xem chi tiết hợp đồng");
		setResizable(false);
		setSize(850,450);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		
		initComponent();
	}
	
	public void initComponent() {
		inforHopDongPanel=new GradientPanel(Color.decode("#ABC8CB"), Color.decode("#7CBDBF")); 
		inforHopDongPanel.setLayout(new GridBagLayout());
		inforHopDongPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		gbc= new GridBagConstraints();
		
//		Thông tin hợp đồng
		gbc.gridx=0; gbc.gridy=0; gbc.insets=new Insets(5, 10, 5, 10); gbc.anchor=GridBagConstraints.WEST;
		idLabel=new JLabel("Mã hợp đồng"); idLabel.setFont(new Font("Segoe UI",0,16));
		inforHopDongPanel.add(idLabel, gbc);
		gbc.gridx=0; gbc.gridy=1;
		idText=new JTextField(12); idText.setFont(new Font("Segoe UI",0,16));
		idText.setEditable(false);
		inforHopDongPanel.add(idText, gbc);
		
		gbc.gridx=1; gbc.gridy=0;
		phiLabel=new JLabel("Phí dịch vụ"); phiLabel.setFont(new Font("Segoe UI",0,16));
		inforHopDongPanel.add(phiLabel, gbc);
		gbc.gridx=1; gbc.gridy=1;
		phiText=new JTextField(12); phiText.setFont(new Font("Segoe UI",0,16));
		phiText.setEditable(false);
		inforHopDongPanel.add(phiText, gbc);
		
		gbc.gridx=2; gbc.gridy=0; gbc.gridwidth=1;
		ngaylapLabel=new JLabel("Ngày lập"); ngaylapLabel.setFont(new Font("Segoe UI",0,16));
		inforHopDongPanel.add(ngaylapLabel, gbc);
		gbc.gridx=2; gbc.gridy=1; gbc.gridwidth=2;
		modelDateNgayLap=new UtilDateModel();
		modelDateNgayLap.setSelected(true);
		Properties p=new Properties();
		p.put("text.day", "Day"); p.put("text.month", "Month"); p.put("text.year","Year");
		JDatePanelImpl panelNgayLap=new JDatePanelImpl(modelDateNgayLap, p);
		ngaylapText=new JDatePickerImpl(panelNgayLap, new LabelDateFormatter());
		ngaylapText.setPreferredSize(new Dimension(370, 24));
		inforHopDongPanel.add(ngaylapText, gbc);
		
		gbc.gridx=0; gbc.gridy=2; gbc.gridwidth=1;
		tieudeLabel=new JLabel("Tiêu đề"); tieudeLabel.setFont(new Font("Segoe UI",0,16));
		inforHopDongPanel.add(tieudeLabel,gbc);
		gbc.gridx=0; gbc.gridy=3; gbc.gridwidth=2;
		tieudeText=new JTextField(26); tieudeText.setFont(new Font("Segoe UI",0,16));
		tieudeText.setEditable(false);
		inforHopDongPanel.add(tieudeText,gbc);
		
		gbc.gridx=2; gbc.gridy=2; gbc.gridwidth=1;
		tenLabel=new JLabel("Tên ứng viên"); tenLabel.setFont(new Font("Segoe UI",0,16));
		inforHopDongPanel.add(tenLabel, gbc);
		gbc.gridx=2; gbc.gridy=3; gbc.gridwidth=2;
		tenText=new JTextField(26); tenText.setFont(new Font("Segoe UI",0,16));
		tenText.setEditable(false);
		inforHopDongPanel.add(tenText, gbc);
		
		gbc.gridx=0; gbc.gridy=4; gbc.gridwidth=1;
		nhatuyendungLabel=new JLabel("Nhà tuyển dụng"); nhatuyendungLabel.setFont(new Font("Segoe UI",0,16));
		inforHopDongPanel.add(nhatuyendungLabel,gbc);
		gbc.gridx=0; gbc.gridy=5;
		nhatuyendungText=new JTextField(12); nhatuyendungText.setFont(new Font("Segoe UI",0,16));
		nhatuyendungText.setEditable(false);
		inforHopDongPanel.add(nhatuyendungText,gbc);
		
		gbc.gridx=1; gbc.gridy=4;
		luongLabel=new JLabel("Lương"); luongLabel.setFont(new Font("Segoe UI",0,16));
		inforHopDongPanel.add(luongLabel, gbc);
		gbc.gridx=1; gbc.gridy=5;
		luongText=new JTextField(12); luongText.setFont(new Font("Segoe UI",0,16));
		luongText.setEditable(false);
		inforHopDongPanel.add(luongText, gbc);
		
		gbc.gridx=2; gbc.gridy=4;
		sdtLabel=new JLabel("Số điện thoại"); sdtLabel.setFont(new Font("Segoe UI",0,16));
		inforHopDongPanel.add(sdtLabel, gbc);
		gbc.gridx=2; gbc.gridy=5;
		sdtText=new JTextField(12); sdtText.setFont(new Font("Segoe UI",0,16));
		sdtText.setEditable(false);
		inforHopDongPanel.add(sdtText, gbc);
		
		gbc.gridx=3; gbc.gridy=4;
		emailLabel=new JLabel("Email"); emailLabel.setFont(new Font("Segoe UI",0,16));
		inforHopDongPanel.add(emailLabel, gbc);
		gbc.gridx=3; gbc.gridy=5;
		emailText=new JTextField(12); emailText.setFont(new Font("Segoe UI",0,16));
		emailText.setEditable(false);
		inforHopDongPanel.add(emailText, gbc);
		
		gbc.gridx=0; gbc.gridy=6;
		nhanvienLabel=new JLabel("Nhân viên phụ trách"); nhanvienLabel.setFont(new Font("Segoe UI",0,16));
		inforHopDongPanel.add(nhanvienLabel, gbc);
		gbc.gridx=0; gbc.gridy=7; gbc.gridwidth=2;
		nhanvienText=new JTextField(26); nhanvienText.setFont(new Font("Segoe UI",0,16));
		nhanvienText.setEditable(false);
		inforHopDongPanel.add(nhanvienText, gbc);

		add(inforHopDongPanel, BorderLayout.CENTER);
		
//		Button
		btnPanel=new GradientPanel(Color.decode("#ABC8CB"), Color.decode("#7CBDBF")); 
		btnPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 45));
		
		btnLuu=new JButton("In hợp đồng"); btnLuu.setFont(new Font("Segoe UI",0,16));
		btnLuu.setPreferredSize(new Dimension(120,25));
		btnLuu.setBackground(new Color(0,102,102));
		btnLuu.setForeground(Color.WHITE);
		
		btnHuy=new JButton("Hủy"); btnHuy.setFont(new Font("Segoe UI",0,16));
		btnHuy.setPreferredSize(new Dimension(120,25));
		btnHuy.setBackground(Color.WHITE);
		btnHuy.setForeground(Color.BLACK);
		
		btnPanel.add(btnLuu); btnPanel.add(btnHuy);
		
		add(btnPanel, BorderLayout.SOUTH);
	}
}
