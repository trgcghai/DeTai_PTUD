package view.dialog;

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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import controller.FilterImp;
import controller.LabelDateFormatter;
import dao.TinTuyenDung_DAO;
import entity.NhaTuyenDung;
import entity.TinTuyenDung;
import entity.constraint.GioiTinh;
import entity.constraint.HinhThucLamViec;
import entity.constraint.NganhNghe;
import entity.constraint.TrangThai;
import entity.constraint.TrinhDo;
import swing.Button;
import swing.ComboBoxRenderer;
import swing.GradientPanel;
import view.frame.TinTuyenDungFrame;

public class TaoSuaTinTuyenDungDialog extends JDialog implements ActionListener{
	
	GradientPanel inforTinTuyenDungPanel, btnPanel;
	JLabel idLabel, tenLabel, hinhthucLabel, startLabel, endLabel, trinhdoLabel, diachiLabel,tieudeLabel,trangthaiLabel, motaLabel,
			soluongLabel, luongLabel, nganhngheLabel;
	JTextField idText, tenText, diachiText, tieudeText,
			soluongText, luongText;
	JComboBox trinhdoText, trangthaiText, hinhthucText, nganhngheBox;
	UtilDateModel modelDateStart, modelDateEnd;
	JDatePickerImpl startText, endText;
	JTextArea motaText;
	JScrollPane scrollMoTa;
	Button btnThem, btnHuy;
	GridBagConstraints gbc;
	
	private Frame parent;
	private NhaTuyenDung ntd;
	private TinTuyenDung ttd;
	private TinTuyenDung_DAO tintuyendungDAO;
	private int idMax;
	
	public TaoSuaTinTuyenDungDialog(Frame parent, boolean modal, NhaTuyenDung ntd) {
		super(parent, modal);
		setTitle("Tạo tin tuyển dụng");
		setResizable(false);
		setSize(800,680);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		
		this.parent=parent;
		this.ntd=ntd;
		tintuyendungDAO=new TinTuyenDung_DAO();
		for(TinTuyenDung ttd: tintuyendungDAO.getDsTinTuyenDung()) {
			int numberID=Integer.parseInt(ttd.getMaTTD().substring(3, ttd.getMaTTD().length()));
			if(numberID > idMax) {
				idMax=numberID;
			}
		}
		
		initComponent();
		
		addActionListener();
		
		idText.setText((idMax+1)<10?("TTD0"+(idMax+1)):("TTD"+(idMax+1)));
		
		loadDataNhaTuyenDung();
	}
	
	public TaoSuaTinTuyenDungDialog(Frame parent, boolean modal, NhaTuyenDung ntd, TinTuyenDung ttd) {
		this(parent, modal, ntd);
		this.ttd=ttd;
		setTitle("Cập nhật tin tuyển dụng");
		
		btnThem.setText("Cập nhật");
		
		loadDataTinTuyenDung();
	}
	
	public void initComponent() {
		inforTinTuyenDungPanel=new GradientPanel(Color.decode("#ABC8CB"), Color.decode("#7CBDBF")); 
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
		nganhngheLabel=new JLabel("Ngành nghề"); nganhngheLabel.setFont(new Font("Segoe UI",0,16));
		inforTinTuyenDungPanel.add(nganhngheLabel, gbc);
		gbc.gridx=2; gbc.gridy=5; gbc.gridwidth=2;
		nganhngheBox = new JComboBox<String>();
		nganhngheBox.setFont(new Font("Segoe UI",0,16));
		nganhngheBox.setPreferredSize(new Dimension(328,25));
		nganhngheBox.setRenderer(new ComboBoxRenderer("Chọn ngành nghề"));
		NganhNghe[] nganhnghes=NganhNghe.class.getEnumConstants();
		for(NganhNghe n: nganhnghes) {
			nganhngheBox.addItem(n.getValue());
		}
		nganhngheBox.setSelectedIndex(-1);
		inforTinTuyenDungPanel.add(nganhngheBox, gbc);
		
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
		motaText.setWrapStyleWord(true);
		scrollMoTa=new JScrollPane(motaText);
		inforTinTuyenDungPanel.add(scrollMoTa, gbc);
		
		add(inforTinTuyenDungPanel, BorderLayout.CENTER);
		
//		Button
		btnPanel=new GradientPanel(Color.decode("#ABC8CB"), Color.decode("#7CBDBF")); 
		btnPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		btnPanel.setBackground(Color.WHITE);
		btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 50));
		
		btnThem=new Button("Tạo mới"); btnThem.setFont(new Font("Segoe UI",0,16));
		btnThem.setPreferredSize(new Dimension(120,25));
		btnThem.setBackground(new Color(0,102,102));
		btnThem.setForeground(Color.WHITE);
		
		btnHuy=new Button("Hủy"); btnHuy.setFont(new Font("Segoe UI",0,16));
		btnHuy.setPreferredSize(new Dimension(120,25));
		btnHuy.setBackground(Color.WHITE);
		btnHuy.setForeground(Color.BLACK);
		
		btnPanel.add(btnThem); btnPanel.add(btnHuy);
		
		add(btnPanel, BorderLayout.SOUTH);
	}

	public void loadDataNhaTuyenDung() {
		tenText.setText(ntd.getTenNTD());
	}
	
	public void loadDataTinTuyenDung() {
		idText.setText(ttd.getMaTTD());
		trangthaiText.setSelectedIndex(ttd.isTrangThai()?0:1);
		tieudeText.setText(ttd.getTieuDe());
		for(int i=0;i<hinhthucText.getItemCount();i++) {
			if(hinhthucText.getItemAt(i).toString().equalsIgnoreCase(ttd.getHinhThuc().getValue())) {
				hinhthucText.setSelectedIndex(i);
				break;
			}
		}
		for(int i=0;i<trinhdoText.getItemCount();i++) {
			if(trinhdoText.getItemAt(i).toString().equalsIgnoreCase(ttd.getTrinhDo().getValue())) {
				trinhdoText.setSelectedIndex(i);
				break;
			}
		}
		for(int i=0;i<nganhngheBox.getItemCount();i++) {
			if(nganhngheBox.getItemAt(i).toString().equalsIgnoreCase(ttd.getNganhNghe().getValue())) {
				nganhngheBox.setSelectedIndex(i);
				break;
			}
		}
		soluongText.setText(String.valueOf(ttd.getSoLuong()));
		luongText.setText(String.valueOf((int)ttd.getLuong()));
		modelDateStart.setValue(Date.from(ttd.getNgayDangTin().atStartOfDay(ZoneId.systemDefault()).toInstant()));
		modelDateEnd.setValue(Date.from(ttd.getNgayHetHan().atStartOfDay(ZoneId.systemDefault()).toInstant()));
		motaText.setText(ttd.getMoTa());
	}
	
	public void them() {
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		
		String id=idText.getText();
		boolean trangthai=trangthaiText.getSelectedItem().toString().equalsIgnoreCase("Khả dụng")?true:false;
		String tieude=tieudeText.getText();
		HinhThucLamViec hinhthuc=null;
		for(HinhThucLamViec h: HinhThucLamViec.class.getEnumConstants()) {
			if(h.getValue().equalsIgnoreCase(hinhthucText.getSelectedItem().toString())) {
				hinhthuc=h;
				break;
			}
		}
		TrinhDo trinhdo=null;
		for(TrinhDo h: TrinhDo.class.getEnumConstants()) {
			if(h.getValue().equalsIgnoreCase(trinhdoText.getSelectedItem().toString())) {
				trinhdo=h;
				break;
			}
		}
		String soluong=soluongText.getText();
		String luong=luongText.getText();
		NganhNghe nganhnghe=null;
		if(nganhngheBox.getSelectedIndex()!=-1) {
			for(NganhNghe h: NganhNghe.class.getEnumConstants()) {
				if(h.getValue().equalsIgnoreCase(nganhngheBox.getSelectedItem().toString())) {
					nganhnghe=h;
					break;
				}
			}
		}
		String ngaydangtin=format.format(modelDateStart.getValue());
		String ngayhethan=format.format(modelDateEnd.getValue());
		String mota=motaText.getText();
		
		if(!tieude.equals("") && !soluong.equals("") && !luong.equals("") && !mota.equals("")) {
			if(Pattern.compile("^[0-9]+$").matcher(soluong).matches() && Integer.parseInt(soluong) > 0
				&& Pattern.compile("^[0-9]+$").matcher(luong).matches() && Integer.parseInt(luong) > 0) {
				if(nganhngheBox.getSelectedIndex()!=-1) {
					if(btnThem.getText().equalsIgnoreCase("Tạo mới")) {
						if(LocalDate.parse(ngaydangtin).compareTo(LocalDate.now())>=0) {
							if(LocalDate.parse(ngayhethan).compareTo(LocalDate.now())>0
									&& LocalDate.parse(ngayhethan).compareTo(LocalDate.parse(ngaydangtin))>0) {
								
								TinTuyenDung tintuyendung=new TinTuyenDung(id, tieude, mota,
										LocalDate.parse(ngaydangtin), LocalDate.parse(ngayhethan), 
										trinhdo, Integer.parseInt(soluong), Double.parseDouble(luong), 
										nganhnghe, hinhthuc, trangthai, ntd);
								
								tintuyendungDAO.create(tintuyendung);
								JOptionPane.showMessageDialog(rootPane, "Thêm tin tuyển dụng thành công");								
								this.dispose();
							}
							else {
								JOptionPane.showMessageDialog(rootPane, 
										"Ngày hết hạn phải sau ngày hiện tại và ngày đăng tin");
							}
						}
						else {
							JOptionPane.showMessageDialog(rootPane, "Ngày đăng tin phải sau hoặc bằng ngày hiện tại");
						}
					}
					else if(btnThem.getText().equalsIgnoreCase("Cập nhật")) {
						if(LocalDate.parse(ngayhethan).compareTo(LocalDate.now())>0) {
							TinTuyenDung tintuyendung=new TinTuyenDung(id, tieude, mota,
									LocalDate.parse(ngaydangtin), LocalDate.parse(ngayhethan), 
									trinhdo, Integer.parseInt(soluong), Double.parseDouble(luong), 
									nganhnghe, hinhthuc, trangthai, ntd);
							tintuyendungDAO.update(tintuyendung);
							JOptionPane.showMessageDialog(rootPane, "Cập nhật tin tuyển dụng thành công");	
							((TinTuyenDungFrame)parent).updateData();
							this.dispose();
						}
						else {
							JOptionPane.showMessageDialog(rootPane, "Ngày đăng tin và ngày hết hạn không hợp lệ (Tin tuyển dụng đã hết hạn)");
						}
					}
				}
				else {
					JOptionPane.showMessageDialog(rootPane, "Chọn ngành nghề cho tin tuyển dụng");
				}
			}
			else {
				JOptionPane.showMessageDialog(rootPane, "Số lượng và lương phải là số lớn hơn 0");
			}
		}
		else {
			JOptionPane.showMessageDialog(rootPane, "Nhập đủ thông tin tin tuyển dụng");
		}
	}
	
	public void huy() {
		trangthaiText.setSelectedIndex(0);
		tieudeText.setText("");
		hinhthucText.setSelectedIndex(0);
		trinhdoText.setSelectedIndex(0);
		soluongText.setText("");
		luongText.setText("");
		nganhngheBox.setSelectedIndex(-1);
		modelDateStart.setValue(new Date());
		modelDateEnd.setValue(new Date());
		motaText.setText("");
	}
	
	public void addActionListener() {
		btnThem.addActionListener(this);
		btnHuy.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		var obj=e.getSource();
		if(obj.equals(btnThem)) {
			them();
		}
		else if(obj.equals(btnHuy)) {
			huy();
		}
	}
}
