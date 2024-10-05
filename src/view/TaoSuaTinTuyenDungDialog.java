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

import controller.ComboBoxRenderer;
import controller.LabelDateFormatter;
import entity.constraint.GioiTinh;
import entity.constraint.HinhThucLamViec;
import entity.constraint.NganhNghe;
import entity.constraint.TrangThai;
import entity.constraint.TrinhDo;

public class TaoSuaTinTuyenDungDialog extends JDialog{
	
	JPanel inforTinTuyenDungPanel, btnPanel;
	JLabel idLabel, tenLabel, hinhthucLabel, startLabel, endLabel, trinhdoLabel, diachiLabel,tieudeLabel,trangthaiLabel, motaLabel,
			soluongLabel, luongLabel;
	JTextField idText, tenText, diachiText, tieudeText,
			soluongText, luongText, nganhngheText;
	JComboBox trinhdoText, trangthaiText, hinhthucText, nganhngheBox;
	UtilDateModel modelDateStart, modelDateEnd;
	JDatePickerImpl startText, endText;
	JTextArea motaText;
	JScrollPane scrollMoTa;
	JButton btnThem, btnHuy;
	GridBagConstraints gbc;
	
	public TaoSuaTinTuyenDungDialog(Frame parent, boolean modal) {
		super(parent, modal);
		setTitle("Tạo tin tuyển dụng");
		setResizable(false);
		setSize(800,680);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		
		initComponent();
	}
	
	public TaoSuaTinTuyenDungDialog(Frame parent, boolean modal, boolean check) {
		this(parent, modal);
		setTitle("Cập nhật tin tuyển dụng");
		
		btnThem.setText("Cập nhật");
	}
	
	public void initComponent() {
		inforTinTuyenDungPanel=new JPanel(); 
		inforTinTuyenDungPanel.setBackground(Color.WHITE);
		inforTinTuyenDungPanel.setLayout(new GridBagLayout());
		inforTinTuyenDungPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		gbc= new GridBagConstraints();
		
//		Thông tin tin tuyển dụng
		gbc.gridx=0; gbc.gridy=0; gbc.insets=new Insets(5, 10, 5, 10); gbc.anchor=GridBagConstraints.WEST;
		idLabel=new JLabel("Mã tin tuyển dụng"); idLabel.setFont(new Font("Segoe UI",0,16));
		inforTinTuyenDungPanel.add(idLabel, gbc);
		gbc.gridx=0; gbc.gridy=1;
		idText=new JTextField(10); idText.setFont(new Font("Segoe UI",0,16));
		idText.setEditable(false);
		inforTinTuyenDungPanel.add(idText, gbc);
		
		gbc.gridx=1; gbc.gridy=0;
		trangthaiLabel=new JLabel("Trạng thái"); trangthaiLabel.setFont(new Font("Segoe UI",0,16));
		inforTinTuyenDungPanel.add(trangthaiLabel, gbc);
		gbc.gridx=1; gbc.gridy=1;
		String[] trangthais= {"Khả dụng", "Không khả dụng"};
		trangthaiText=new JComboBox(trangthais); trangthaiText.setFont(new Font("Segoe UI",0,16));
		trangthaiText.setPreferredSize(new Dimension(160,26));
		inforTinTuyenDungPanel.add(trangthaiText, gbc);
		
		gbc.gridx=2; gbc.gridy=0;
		tenLabel=new JLabel("Nhà tuyển dụng"); tenLabel.setFont(new Font("Segoe UI",0,16));
		inforTinTuyenDungPanel.add(tenLabel, gbc);
		gbc.gridx=2; gbc.gridy=1; gbc.gridwidth=2;
		tenText=new JTextField(23); tenText.setFont(new Font("Segoe UI",0,16));
		tenText.setEditable(false);
		inforTinTuyenDungPanel.add(tenText, gbc);
		
		gbc.gridx=0; gbc.gridy=2; gbc.gridwidth=1;
		tieudeLabel=new JLabel("Tiêu đề"); tieudeLabel.setFont(new Font("Segoe UI",0,16));
		inforTinTuyenDungPanel.add(tieudeLabel,gbc);
		gbc.gridx=0; gbc.gridy=3; gbc.gridwidth=2;
		tieudeText=new JTextField(23); tieudeText.setFont(new Font("Segoe UI",0,16));
		inforTinTuyenDungPanel.add(tieudeText,gbc);
		
		gbc.gridx=2; gbc.gridy=2; gbc.gridwidth=1;
		hinhthucLabel=new JLabel("Hình thức làm việc"); hinhthucLabel.setFont(new Font("Segoe UI",0,16));
		inforTinTuyenDungPanel.add(hinhthucLabel, gbc);
		gbc.gridx=2; gbc.gridy=3;
		hinhthucText=new JComboBox(); hinhthucText.setFont(new Font("Segoe UI",0,16));
		HinhThucLamViec[] hinhthucs=HinhThucLamViec.class.getEnumConstants();
		for(HinhThucLamViec h: hinhthucs) {
			hinhthucText.addItem(h.getValue());
		}
		hinhthucText.setPreferredSize(new Dimension(156,25));
		inforTinTuyenDungPanel.add(hinhthucText,gbc);
		
		gbc.gridx=3; gbc.gridy=2;
		trinhdoLabel=new JLabel("Trình độ"); trinhdoLabel.setFont(new Font("Segoe UI",0,16));
		inforTinTuyenDungPanel.add(trinhdoLabel, gbc);
		gbc.gridx=3; gbc.gridy=3;
		trinhdoText=new JComboBox(); trinhdoText.setFont(new Font("Segoe UI",0,16));
		TrinhDo[] trinhdos=TrinhDo.class.getEnumConstants();
		for(TrinhDo t: trinhdos) {
			trinhdoText.addItem(t.getValue());
		}
		trinhdoText.setPreferredSize(new Dimension(150,25));
		inforTinTuyenDungPanel.add(trinhdoText, gbc);
		
		gbc.gridx=0; gbc.gridy=4;
		soluongLabel=new JLabel("Số lượng"); soluongLabel.setFont(new Font("Segoe UI",0,16));
		inforTinTuyenDungPanel.add(soluongLabel, gbc);
		gbc.gridx=0; gbc.gridy=5;
		soluongText=new JTextField(10); soluongText.setFont(new Font("Segoe UI",0,16));
		inforTinTuyenDungPanel.add(soluongText, gbc);
		
		gbc.gridx=1; gbc.gridy=4;
		luongLabel=new JLabel("Lương"); luongLabel.setFont(new Font("Segoe UI",0,16));
		inforTinTuyenDungPanel.add(luongLabel, gbc);
		gbc.gridx=1; gbc.gridy=5;
		luongText=new JTextField(11); luongText.setFont(new Font("Segoe UI",0,16));
		inforTinTuyenDungPanel.add(luongText, gbc);
		
		gbc.gridx=2; gbc.gridy=4;
		nganhngheBox = new JComboBox<String>();
		nganhngheBox.setFont(new Font("Segoe UI",0,16));
		nganhngheBox.setPreferredSize(new Dimension(156,25));
		nganhngheBox.setRenderer(new ComboBoxRenderer("Chọn ngành nghề"));
		NganhNghe[] nganhnghes=NganhNghe.class.getEnumConstants();
		for(NganhNghe n: nganhnghes) {
			nganhngheBox.addItem(n.getValue());
		}
		nganhngheBox.setSelectedIndex(-1);
		nganhngheBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	nganhngheBox.setSelectedIndex(-1);
            }
        });
		inforTinTuyenDungPanel.add(nganhngheBox, gbc);
		gbc.gridx=2; gbc.gridy=5; gbc.gridwidth=2;
		nganhngheText=new JTextField(23); nganhngheText.setFont(new Font("Segoe UI",0,16));
		inforTinTuyenDungPanel.add(nganhngheText, gbc);
		
		gbc.gridx=0; gbc.gridy=6; gbc.gridwidth=1;
		startLabel=new JLabel("Ngày đăng tin"); startLabel.setFont(new Font("Segoe UI",0,16));
		inforTinTuyenDungPanel.add(startLabel, gbc);
		gbc.gridx=0; gbc.gridy=7;
		modelDateStart=new UtilDateModel();
		modelDateStart.setSelected(true);
		Properties p=new Properties();
		p.put("text.day", "Day"); p.put("text.month", "Month"); p.put("text.year","Year");
		JDatePanelImpl panelStart=new JDatePanelImpl(modelDateStart, p);
		startText=new JDatePickerImpl(panelStart, new LabelDateFormatter());
		startText.setPreferredSize(new Dimension(140, 24));
		inforTinTuyenDungPanel.add(startText, gbc);
		
		gbc.gridx=1; gbc.gridy=6;
		endLabel=new JLabel("Ngày hết hạn"); endLabel.setFont(new Font("Segoe UI",0,16));
		inforTinTuyenDungPanel.add(endLabel, gbc);
		gbc.gridx=1; gbc.gridy=7;
		modelDateEnd=new UtilDateModel();
		modelDateEnd.setSelected(true);
		Properties q=new Properties();
		q.put("text.day", "Day"); q.put("text.month", "Month"); q.put("text.year","Year");
		JDatePanelImpl panelEnd=new JDatePanelImpl(modelDateEnd, q);
		endText=new JDatePickerImpl(panelEnd, new LabelDateFormatter());
		endText.setPreferredSize(new Dimension(157, 24));
		inforTinTuyenDungPanel.add(endText, gbc);
		
		gbc.gridx=0; gbc.gridy=8;
		motaLabel=new JLabel("Mô tả công việc"); motaLabel.setFont(new Font("Segoe UI",0,16));
		inforTinTuyenDungPanel.add(motaLabel, gbc);
		gbc.gridx=0; gbc.gridy=9; gbc.gridwidth=4;
		motaText=new JTextArea(10, 48); motaText.setFont(new Font("Segoe UI",0,16));
		motaText.setBorder(BorderFactory.createLineBorder(new Color(0,191,165)));
		motaText.setLineWrap(true);
		scrollMoTa=new JScrollPane(motaText);
		inforTinTuyenDungPanel.add(scrollMoTa, gbc);
		
		add(inforTinTuyenDungPanel, BorderLayout.CENTER);
		
//		Button
		btnPanel=new JPanel(); 
		btnPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		btnPanel.setBackground(Color.WHITE);
		btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 50));
		
		btnThem=new JButton("Tạo mới"); btnThem.setFont(new Font("Segoe UI",0,16));
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
