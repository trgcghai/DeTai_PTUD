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
import java.time.ZoneId;
import java.util.Date;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import controller.FilterImp;
import controller.LabelDateFormatter;
import dao.TaiKhoan_DAO;
import entity.NhanVien;
import entity.TaiKhoan;
import entity.constraint.GioiTinh;
import entity.constraint.VaiTro;
import exception.checkUserEmail;
import exception.checkUserPass;
import swing.Button;
import swing.ComboBoxRenderer;
import swing.GradientPanel;
import view.frame.NhanVienFrame;
import view.frame.TaiKhoanFrame;

public class CapSuaTaiKhoanDialog extends JDialog implements ActionListener{
	
	GradientPanel inforNhanVienPanel, btnPanel;
	JLabel idLabel, tenLabel, sdtLabel, dateLabel, gioitinhLabel, diachiLabel, dateWorkLabel, vaitroLabel,
			tendnLabel, matkhauLabel;
	JTextField idText, tenText, sdtText, diachiText,tendnText, matkhauText;
	JComboBox gioitinhText, vaitroText;
	UtilDateModel modelDate, modelDateWork;
	JDatePickerImpl dateText, dateWorkText;
	Button btnThem, btnHuy;
	GridBagConstraints gbc;
	
	private int idMax=0;
	private NhanVien nv;
	private TaiKhoan tk;
	private TaiKhoan_DAO taikhoanDAO;
	private Frame parent;

	public CapSuaTaiKhoanDialog(Frame parent, boolean modal, NhanVien nv) {
		super(parent, modal);
		setTitle("Cấp tài khoản nhân viên");
		setResizable(false);
		setSize(750,450);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		
		this.nv=nv;
		this.parent=parent;
		taikhoanDAO=new TaiKhoan_DAO();
		for(TaiKhoan tk: taikhoanDAO.getDsTaiKhoan()) {
			int numberID=Integer.parseInt(tk.getMaTk().substring(2, tk.getMaTk().length()));
			if(numberID > idMax) {
				idMax=numberID;
			}
		}
		
		initComponent();
	
		addActionListener();
		
		loadDataNhanVien();
	}
	
	public CapSuaTaiKhoanDialog(Frame parent, boolean modal, NhanVien nv, TaiKhoan tk) {
		this(parent, modal, nv);
		setTitle("Cập nhật tài khoản nhân viên");
		btnThem.setText("Cập nhật");
		
		this.tk=tk;
		
		loadDataTaiKhoan();
	}
	
	public void initComponent() {
		inforNhanVienPanel=new GradientPanel(Color.decode("#ABC8CB"), Color.decode("#7CBDBF")); 
		inforNhanVienPanel.setBackground(Color.WHITE);
		inforNhanVienPanel.setLayout(new GridBagLayout());
		inforNhanVienPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		gbc= new GridBagConstraints();
		
//		Thông tin nhân viên
		gbc.gridx=0; gbc.gridy=0; gbc.insets=new Insets(5, 10, 5, 10); gbc.anchor=GridBagConstraints.WEST;
		idLabel=new JLabel("Mã tài khoản"); idLabel.setFont(new Font("Segoe UI",0,16));
		inforNhanVienPanel.add(idLabel, gbc);
		gbc.gridx=0; gbc.gridy=1;
		idText=new JTextField(10); idText.setFont(new Font("Segoe UI",0,16));
		idText.setEditable(false);
		inforNhanVienPanel.add(idText, gbc);
		
		gbc.gridx=1; gbc.gridy=0;
		vaitroLabel=new JLabel("Vai trò"); vaitroLabel.setFont(new Font("Segoe UI",0,16));
		inforNhanVienPanel.add(vaitroLabel, gbc);
		gbc.gridx=1; gbc.gridy=1;
		vaitroText=new JComboBox(); vaitroText.setFont(new Font("Segoe UI",0,16));
		VaiTro[] vaitros=VaiTro.class.getEnumConstants();
		for(VaiTro v: vaitros) {
			vaitroText.addItem(v.getValue());
		}
		vaitroText.setPreferredSize(new Dimension(150,25));
		inforNhanVienPanel.add(vaitroText, gbc);
		
		gbc.gridx=2; gbc.gridy=0;
		tenLabel=new JLabel("Họ tên nhân viên"); tenLabel.setFont(new Font("Segoe UI",0,16));
		inforNhanVienPanel.add(tenLabel, gbc);
		gbc.gridx=2; gbc.gridy=1;
		tenText=new JTextField(20); tenText.setFont(new Font("Segoe UI",0,16));
		tenText.setEditable(false);
		inforNhanVienPanel.add(tenText, gbc);
		
		gbc.gridx=0; gbc.gridy=2;
		dateLabel=new JLabel("Ngày sinh"); dateLabel.setFont(new Font("Segoe UI",0,16));
		inforNhanVienPanel.add(dateLabel, gbc);
		gbc.gridx=0; gbc.gridy=3;
		modelDate=new UtilDateModel();
		Properties p=new Properties();
		p.put("text.day", "Day"); p.put("text.month", "Month"); p.put("text.year", "Year");
		JDatePanelImpl panelDate=new JDatePanelImpl(modelDate, p);
		dateText=new JDatePickerImpl(panelDate, new LabelDateFormatter());
		dateText.setPreferredSize(new Dimension(150,25));
		dateText.getComponent(1).setEnabled(false);
		modelDate.setValue(new Date());
		inforNhanVienPanel.add(dateText, gbc);
		
		gbc.gridx=1; gbc.gridy=2;
		gioitinhLabel=new JLabel("Giới tính"); gioitinhLabel.setFont(new Font("Segoe UI",0,16));
		inforNhanVienPanel.add(gioitinhLabel, gbc);
		gbc.gridx=1; gbc.gridy=3;
		gioitinhText=new JComboBox(); gioitinhText.setFont(new Font("Segoe UI",0,16));
		GioiTinh[] gioitinhs=GioiTinh.class.getEnumConstants();
		for(GioiTinh g: gioitinhs) {
			gioitinhText.addItem(g.getValue());
		}
		gioitinhText.setPreferredSize(new Dimension(150,25));
		inforNhanVienPanel.add(gioitinhText, gbc);
		
		gbc.gridx=2; gbc.gridy=2;
		sdtLabel=new JLabel("Số điện thoại"); sdtLabel.setFont(new Font("Segoe UI",0,16));
		inforNhanVienPanel.add(sdtLabel, gbc);
		gbc.gridx=2; gbc.gridy=3;
		sdtText=new JTextField(20); sdtText.setFont(new Font("Segoe UI",0,16));
		sdtText.setEditable(false);
		inforNhanVienPanel.add(sdtText, gbc);
		
		gbc.gridx=0; gbc.gridy=4;
		diachiLabel=new JLabel("Địa chỉ"); diachiLabel.setFont(new Font("Segoe UI",0,16));
		inforNhanVienPanel.add(diachiLabel, gbc);
		gbc.gridx=0; gbc.gridy=5; gbc.gridwidth=2;
		diachiText=new JTextField(23); diachiText.setFont(new Font("Segoe UI",0,16));
		diachiText.setEditable(false);
		inforNhanVienPanel.add(diachiText, gbc);
		
		gbc.gridx=2; gbc.gridy=4; gbc.gridwidth=1;
		dateWorkLabel=new JLabel("Ngày vào làm"); dateWorkLabel.setFont(new Font("Segoe UI",0,16));
		inforNhanVienPanel.add(dateWorkLabel,gbc);
		gbc.gridx=2; gbc.gridy=5;
		modelDateWork=new UtilDateModel();
		JDatePanelImpl panelDateWork=new JDatePanelImpl(modelDateWork, p);
		dateWorkText=new JDatePickerImpl(panelDateWork, new LabelDateFormatter());
		dateWorkText.setPreferredSize(new Dimension(150,25));
		dateWorkText.getComponent(1).setEnabled(false);
		modelDateWork.setValue(new Date());
		inforNhanVienPanel.add(dateWorkText,gbc);
		
		gbc.gridx=0; gbc.gridy=6;
		tendnLabel=new JLabel("Tên đăng nhập"); tendnLabel.setFont(new Font("Segoe UI",0,16));
		inforNhanVienPanel.add(tendnLabel, gbc);
		gbc.gridx=0; gbc.gridy=7; gbc.gridwidth=2;
		tendnText=new JTextField(23); tendnText.setFont(new Font("Segoe UI",0,16));
		inforNhanVienPanel.add(tendnText, gbc);
		
		gbc.gridx=2; gbc.gridy=6; gbc.gridwidth=1;
		matkhauLabel=new JLabel("Mật khẩu"); matkhauLabel.setFont(new Font("Segoe UI",0,16));
		inforNhanVienPanel.add(matkhauLabel, gbc);
		gbc.gridx=2; gbc.gridy=7;
		matkhauText=new JTextField(20); matkhauText.setFont(new Font("Segoe UI",0,16));
		inforNhanVienPanel.add(matkhauText, gbc);
		
		add(inforNhanVienPanel, BorderLayout.CENTER);
		
//		Button
		btnPanel=new GradientPanel(Color.decode("#ABC8CB"), Color.decode("#7CBDBF")); 
		btnPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		btnPanel.setBackground(Color.WHITE);
		btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 47));
		
		btnThem=new Button("Cấp tài khoản"); btnThem.setFont(new Font("Segoe UI",0,16));
		btnThem.setPreferredSize(new Dimension(135,25));
		btnThem.setBackground(new Color(0,102,102));
		btnThem.setForeground(Color.WHITE);
		
		btnHuy=new Button("Hủy"); btnHuy.setFont(new Font("Segoe UI",0,16));
		btnHuy.setPreferredSize(new Dimension(135,25));
		btnHuy.setBackground(Color.WHITE);
		btnHuy.setForeground(Color.BLACK);
		
		btnPanel.add(btnThem); btnPanel.add(btnHuy);
		
		add(btnPanel, BorderLayout.SOUTH);
	}

	public void loadDataNhanVien() {
		idText.setText((idMax+1)<10?("TK0"+(idMax+1)):("TK"+(idMax+1)));
		tenText.setText(nv.getTenNV());
		modelDate.setValue(Date.from(nv.getNgaySinh().atStartOfDay(ZoneId.systemDefault()).toInstant()));
		modelDateWork.setValue(Date.from(nv.getNgayVaoLam().atStartOfDay(ZoneId.systemDefault()).toInstant()));
		
		for(int i=0;i< gioitinhText.getItemCount();i++) {
			if(gioitinhText.getItemAt(i).toString().equals(nv.getGioiTinh().getValue())) {
				gioitinhText.setSelectedIndex(i);
				break;
			}
		}
		
		sdtText.setText(nv.getSoDienThoai());
		diachiText.setText(nv.getDiaChi());
	}
	
	public void loadDataTaiKhoan() {
		idText.setText(tk.getMaTk());
		tendnText.setText(tk.getEmail());
		matkhauText.setText(tk.getMatKhau());
		
		for(int i=0;i<vaitroText.getItemCount();i++) {
			if(vaitroText.getItemAt(i).toString().equalsIgnoreCase(tk.getVaiTro().getValue())) {
				vaitroText.setSelectedIndex(i);
				break;
			}
		}
	}
	
	public void them() {
		String id=idText.getText();
		String tendn=tendnText.getText();
		String matkhau=matkhauText.getText();
		String vaitro=vaitroText.getSelectedItem().toString();
		NhanVien nhanvien=nv;
		
		if(!tendn.equals("") && !matkhau.equals("")) {
			var check=new FilterImp();
			try {
				if(check.checkUserEmail(tendn) && check.checkUserPass(matkhau)) {
					VaiTro vt=null;
					for(VaiTro v: VaiTro.class.getEnumConstants()) {
						if(v.getValue().equalsIgnoreCase(vaitro)) {
							vt=v;
							break;
						}
					}
					
					if(vt==VaiTro.CHUACO) {
						JOptionPane.showMessageDialog(rootPane, "Chọn vai trò cho nhân viên");
					}
					else {
						TaiKhoan tk=new TaiKhoan(id, tendn, matkhau, vt, nhanvien);
						if(btnThem.getText().equalsIgnoreCase("Cấp tài khoản")) {
							taikhoanDAO.create(tk);
							JOptionPane.showMessageDialog(rootPane, "Cấp tài khoản thành công");
							((NhanVienFrame)parent).updateTable();
						}
						else {
							taikhoanDAO.update(tk);
							JOptionPane.showMessageDialog(rootPane, "Cập nhật tài khoản thành công");
							((TaiKhoanFrame)parent).updateTable();
						}
						this.dispose();
					}
					
				}
			} catch (checkUserEmail | checkUserPass e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(rootPane, e.getMessage());
			}
		}
		else {
			JOptionPane.showMessageDialog(rootPane, "Nhập đủ thông tin");
		}
	}
	
	public void huy() {
		vaitroText.setSelectedIndex(0);
		tendnText.setText("");
		matkhauText.setText("");
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
