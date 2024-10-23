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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.time.ZoneId;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
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

import component.Button;
import component.GradientPanel;
import controller.LabelDateFormatter;
import dao.HoSo_DAO;
import dao.NhaTuyenDung_DAO;
import dao.TinTuyenDung_DAO;
import entity.HoSo;
import entity.NhaTuyenDung;
import entity.NhanVien;
import entity.TinTuyenDung;
import entity.UngVien;
import entity.constraint.GioiTinh;
import entity.constraint.TrangThai;

public class TaoSuaHoSoDialog extends JDialog implements ActionListener, FocusListener{
	
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
	Button btnThem, btnHuy;
	GridBagConstraints gbc;
	
	private String motaDefault="Hồ sơ phải có thông tin trình độ và ngành nghề của ứng viên";
	private int idMax;
	private Frame parent;
	private NhanVien nv;
	private UngVien uv;
	private HoSo hoso;
	private HoSo_DAO hosoDAO;
	private TinTuyenDung_DAO tintuyendungDAO;
	private NhaTuyenDung_DAO nhatuyendungDAO;
	
	public TaoSuaHoSoDialog(Frame parent, boolean modal, UngVien uv, NhanVien user) {
		super(parent, modal);
		setTitle("Tạo hồ sơ");
		setResizable(false);
		setSize(800,680);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		
		this.parent=parent;
		this.nv=user;
		this.uv=uv;
		hosoDAO=new HoSo_DAO();
		
		for(HoSo hs: hosoDAO.getDSHoSo()) {
			int numberID=Integer.parseInt(hs.getMaHS().substring(2, hs.getMaHS().length()));
			if(numberID > idMax) {
				idMax=numberID;
			}
		}
		
		initComponent();
		addActionListener();
		addFocusListener();
		
		idText.setText((idMax+1)<10?("HS0"+(idMax+1)):("HS"+(idMax+1)));
		
		loadDataUngVien();
	}

	public TaoSuaHoSoDialog(Frame parent, boolean modal, UngVien uv, NhanVien user, HoSo hoso) {
		this(parent, modal, uv, user);
		setTitle("Cập nhật hồ sơ");
		
		this.hoso=hoso;
		tintuyendungDAO=new TinTuyenDung_DAO();
		nhatuyendungDAO=new NhaTuyenDung_DAO();
		
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
		
		btnThem.setText("Cập nhật");
		
		removePlaceHolder(motaText);
		
		loadDataHoSo();
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
		dateText.getComponent(1).setEnabled(false);
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
		motaText.setWrapStyleWord(true);
		scrollMoTa=new JScrollPane(motaText);
		inforUngVienPanel.add(scrollMoTa, gbc);
		
		add(inforUngVienPanel, BorderLayout.CENTER);
		
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

	public void addActionListener() {
		btnThem.addActionListener(this);
		btnHuy.addActionListener(this);
	}
	
	public void loadDataUngVien() {
		tenText.setText(uv.getTenUV());
		emailText.setText(uv.getEmail());
		sdtText.setText(uv.getSoDienThoai());
		diachiText.setText(uv.getDiaChi());
		modelDate.setValue(Date.from(uv.getNgaySinh().atStartOfDay(ZoneId.systemDefault()).toInstant()));
		
		for(int i=0;i< gioitinhText.getItemCount();i++) {
			if(gioitinhText.getItemAt(i).toString().equals(uv.getGioiTinh().getValue())) {
				gioitinhText.setSelectedIndex(i);
				break;
			}
		}
	}
	
	public void loadDataHoSo() {
		idText.setText(hoso.getMaHS());
		for(int i=0;i< trangthaiText.getItemCount();i++) {
			if(trangthaiText.getItemAt(i).toString().equalsIgnoreCase(hoso.getTrangThai().getValue())) {
				trangthaiText.setSelectedIndex(i);
				break;
			}
		}
		motaText.setText(hoso.getMoTa());
		
		TinTuyenDung ttd=null;
		NhaTuyenDung ntd=null;
		if(hoso.getTinTuyenDung()!=null) {
			ttd=tintuyendungDAO.getTinTuyenDung(hoso.getTinTuyenDung().getMaTTD());
			ntd=nhatuyendungDAO.getNhaTuyenDung(ttd.getNhaTuyenDung().getMaNTD());
			motaText.setEditable(false);
		}
		tintuyendungText.setText(ttd!=null?ttd.getTieuDe():"");
		nhatuyendungText.setText(ntd!=null?ntd.getTenNTD():"");
	}
	
//	Lấy thông tin trình độ và chuyên ngành trong mô tả hồ sơ
	public String extracInfor(String text, String key) {
		Matcher matcher=Pattern.compile(key + ":\\s*(.*)", Pattern.MULTILINE).matcher(text);
		if(matcher.find()) {
			return matcher.group(1).trim();
		}
		return "";
	}
	
	public void them() {
		String id=idText.getText();
		String mota=motaText.getText();
		String trangthai=trangthaiText.getSelectedItem().toString();
		
		if(!mota.equals(motaDefault)) {
			if(extracInfor(mota, "Trình độ").equals("") || extracInfor(mota, "Chuyên ngành").equals("")) {
				JOptionPane.showMessageDialog(rootPane, "Mô tả hồ sơ thiếu thông tin trình độ hoặc chuyên ngành");
			}
			else {
				TrangThai t=null;
				for(TrangThai i: TrangThai.class.getEnumConstants()) {
					if(i.getValue().equalsIgnoreCase(trangthai)) {
						t=i;
					}
				}
				
				HoSo hs=new HoSo(id, mota, t, uv, null, nv);
				if(btnThem.getText().equals("Tạo mới")) {
					if(!trangthaiText.getSelectedItem().toString().equalsIgnoreCase("Chưa nộp")) {
						JOptionPane.showMessageDialog(rootPane, "Trạng thái hồ sơ không hợp lệ");
					}
					else {
						hosoDAO.create(hs);
						JOptionPane.showMessageDialog(rootPane, "Tạo hồ sơ thành công");
						((UngVienFrame)parent).updateTable();
						this.dispose();					
					}
				}
				else {
					if(hoso.getTinTuyenDung()!=null) {
						if(trangthaiText.getSelectedItem().toString().equalsIgnoreCase("Chưa nộp")) {
							JOptionPane.showMessageDialog(rootPane, "Trạng thái hồ sơ không hợp lệ");
						}
						else {
							TinTuyenDung ttd=tintuyendungDAO.getTinTuyenDung(hoso.getTinTuyenDung().getMaTTD());
							hs.setTinTuyenDung(ttd);
							hosoDAO.update(hs);
							JOptionPane.showMessageDialog(rootPane, "Cập nhật hồ sơ thành công");
							((HoSoFrame)parent).updateTable();
							this.dispose();
						}
					}
					else {
						if(!trangthaiText.getSelectedItem().toString().equalsIgnoreCase("Chưa nộp")) {
							JOptionPane.showMessageDialog(rootPane, "Trạng thái hồ sơ không hợp lệ");
						}
						else {
							hosoDAO.update(hs);
							JOptionPane.showMessageDialog(rootPane, "Cập nhật hồ sơ thành công");
							((HoSoFrame)parent).updateTable();
							this.dispose();
						}
					}
				}
			}
		}
		else {
			JOptionPane.showMessageDialog(rootPane, "Nhập đủ thông tin hồ sơ");
		}
	}
	
	public void huy() {
		trangthaiText.setSelectedIndex(0);
		
		if(hoso==null) {
			motaText.setText("");	
		}
		else {
			if(hoso.getTinTuyenDung()==null) {
				motaText.setText("");			
			}
		}
		
	}
	
//	Trạng thái text chuột không nằm trong ô
	public void addPlaceHolder(JTextArea text) {
		Font font=text.getFont();
		font=font.deriveFont(Font.ITALIC);
		text.setFont(font);
		text.setForeground(Color.decode("#259195"));
		text.setText(motaDefault);
	}
	
//	Xóa trạng thái text chuột không nằm trong ô
	public void removePlaceHolder(JTextArea text) {
		Font font=text.getFont();
		font=font.deriveFont(Font.PLAIN);
		text.setFont(font);
		text.setForeground(Color.decode("#259195"));
	}
	
	public void addFocusListener() {
		motaText.addFocusListener(this);
		
		addPlaceHolder(motaText);
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

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		var obj=e.getSource();
		if(obj.equals(motaText)) {
			if(motaText.getText().equals(motaDefault)) {
				motaText.setText(null);
				motaText.requestFocus();
				
				removePlaceHolder(motaText);
			}
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		var obj=e.getSource();
		if(obj.equals(motaText)) {
			if(motaText.getText().length()==0) {
				addPlaceHolder(motaText);
				motaText.setText(motaDefault);
			}
		}
	}
}
