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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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

import component.GradientPanel;
import controller.FilterImp;
import controller.LabelDateFormatter;
import dao.NhanVien_DAO;
import entity.NhanVien;
import entity.constraint.GioiTinh;
import entity.constraint.VaiTro;
import exception.checkBirthday;
import exception.checkDateOfWork;
import exception.checkName;
import exception.checkPhone;

public class ThemSuaNhanVienDialog extends JDialog implements ActionListener{
	
	GradientPanel inforNhanVienPanel, btnPanel;
	JLabel idLabel, tenLabel, sdtLabel, dateLabel, gioitinhLabel, diachiLabel, dateWorkLabel, vaitroLabel;
	JTextField idText, tenText, sdtText, diachiText;
	JComboBox gioitinhText, vaitroText;
	UtilDateModel modelDate, modelDateWork;
	JDatePickerImpl dateText, dateWorkText;
	JButton btnThem, btnHuy;
	GridBagConstraints gbc;
	
	private int idMax=0;
	private NhanVien nv;
	private NhanVienFrame parent;
	private NhanVien_DAO nhanvienDAO;

	public ThemSuaNhanVienDialog(Frame parent, boolean modal) {
		super(parent, modal);
		setTitle("Thêm mới nhân viên");
		setResizable(false);
		setSize(550,400);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		
		this.parent=(NhanVienFrame) parent;
		nhanvienDAO=new NhanVien_DAO();
		for(NhanVien nv: nhanvienDAO.getDSNhanVien()) {
			int numberID=Integer.parseInt(nv.getMaNV().substring(2, nv.getMaNV().length()));
			if(numberID > idMax) {
				idMax=numberID;
			}
		}
		
		initComponentThem();
		
		addActionListener();
		
		idText.setText((idMax+1)<10?("NV0"+(idMax+1)):("NV"+(idMax+1)));
	}
	
	public ThemSuaNhanVienDialog(Frame parent, boolean modal, NhanVien nv) {
		this(parent, modal);
		this.nv=nv;
		setTitle("Cập nhật nhân viên");
		
		initComponentSua();
		loadDataNhanVien();
	}
	
	public void initComponentThem() {
		inforNhanVienPanel=new GradientPanel(Color.decode("#ABC8CB"), Color.decode("#7CBDBF")); 
		inforNhanVienPanel.setLayout(new GridBagLayout());
		inforNhanVienPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		gbc= new GridBagConstraints();
		
//		Thông tin nhân viên
		gbc.gridx=0; gbc.gridy=0; gbc.insets=new Insets(5, 10, 5, 10); gbc.anchor=GridBagConstraints.WEST;
		idLabel=new JLabel("Mã nhân viên"); idLabel.setFont(new Font("Segoe UI",0,16));
		inforNhanVienPanel.add(idLabel, gbc);
		gbc.gridx=0; gbc.gridy=1;
		idText=new JTextField(10); idText.setFont(new Font("Segoe UI",0,16));
		idText.setEditable(false);
		inforNhanVienPanel.add(idText, gbc);
		
		gbc.gridx=1; gbc.gridy=0;
		tenLabel=new JLabel("Họ tên"); tenLabel.setFont(new Font("Segoe UI",0,16));
		inforNhanVienPanel.add(tenLabel, gbc);
		gbc.gridx=1; gbc.gridy=1;
		tenText=new JTextField(20); tenText.setFont(new Font("Segoe UI",0,16));
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
		modelDate.setValue(new Date());
		inforNhanVienPanel.add(dateText, gbc);
		
		gbc.gridx=1; gbc.gridy=2;
		sdtLabel=new JLabel("Số điện thoại"); sdtLabel.setFont(new Font("Segoe UI",0,16));
		inforNhanVienPanel.add(sdtLabel, gbc);
		gbc.gridx=1; gbc.gridy=3;
		sdtText=new JTextField(20); sdtText.setFont(new Font("Segoe UI",0,16));
		inforNhanVienPanel.add(sdtText, gbc);
		
		gbc.gridx=0; gbc.gridy=4;
		gioitinhLabel=new JLabel("Giới tính"); gioitinhLabel.setFont(new Font("Segoe UI",0,16));
		inforNhanVienPanel.add(gioitinhLabel, gbc);
		gbc.gridx=0; gbc.gridy=5;
		gioitinhText=new JComboBox(); gioitinhText.setFont(new Font("Segoe UI",0,16));
		GioiTinh[] gioitinhs=GioiTinh.class.getEnumConstants();
		for(GioiTinh g: gioitinhs) {
			gioitinhText.addItem(g.getValue());
		}
		gioitinhText.setPreferredSize(new Dimension(150,25));
		inforNhanVienPanel.add(gioitinhText, gbc);
		
		gbc.gridx=1; gbc.gridy=4;
		diachiLabel=new JLabel("Địa chỉ"); diachiLabel.setFont(new Font("Segoe UI",0,16));
		inforNhanVienPanel.add(diachiLabel, gbc);
		gbc.gridx=1; gbc.gridy=5;
		diachiText=new JTextField(20); diachiText.setFont(new Font("Segoe UI",0,16));
		inforNhanVienPanel.add(diachiText, gbc);
		
		gbc.gridx=0; gbc.gridy=6;
		dateWorkLabel=new JLabel("Ngày vào làm"); dateWorkLabel.setFont(new Font("Segoe UI",0,16));
		inforNhanVienPanel.add(dateWorkLabel,gbc);
		gbc.gridx=0; gbc.gridy=7;
		modelDateWork=new UtilDateModel();
		JDatePanelImpl panelDateWork=new JDatePanelImpl(modelDateWork, p);
		dateWorkText=new JDatePickerImpl(panelDateWork, new LabelDateFormatter());
		dateWorkText.setPreferredSize(new Dimension(150,25));
		modelDateWork.setValue(new Date());
		inforNhanVienPanel.add(dateWorkText,gbc);
		
		add(inforNhanVienPanel, BorderLayout.CENTER);
		
//		Button
		btnPanel=new GradientPanel(Color.decode("#ABC8CB"), Color.decode("#7CBDBF"));  
		btnPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
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

	public void initComponentSua() {
		gbc.gridx=1; gbc.gridy=6;
		vaitroLabel=new JLabel("Vai trò"); vaitroLabel.setFont(new Font("Segoe UI",0,16));
		inforNhanVienPanel.add(vaitroLabel, gbc);
		gbc.gridx=1; gbc.gridy=7;
		vaitroText=new JComboBox(); vaitroText.setFont(new Font("Segoe UI",0,16));
		VaiTro[] vaitros=VaiTro.class.getEnumConstants();
		for(VaiTro v: vaitros) {
			vaitroText.addItem(v.getValue());
		}
		vaitroText.setPreferredSize(new Dimension(150,25));
		inforNhanVienPanel.add(vaitroText, gbc);
		
		btnThem.setText("Cập nhật");
	}
	
	public void addActionListener() {
		btnThem.addActionListener(this);
		btnHuy.addActionListener(this);
	}
	
	public void them() {
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		
		String id=idText.getText();
		String ten=tenText.getText();
		String sdt=sdtText.getText();
		String diachi=diachiText.getText();
		String ngaysinh = format.format(modelDate.getValue());
		String gioitinh=gioitinhText.getSelectedItem().toString();
		String ngayvaolam=format.format(modelDateWork.getValue());

		if(!ten.equals("") && !sdt.equals("") && !diachi.equals("")) {
			var check=new FilterImp();
			try {
				if(check.checkName(ten) && check.checkPhone(sdt) 
						&& check.checkBirthday(LocalDate.parse(ngaysinh)) && check.checkDateOfWork(LocalDate.parse(ngayvaolam))) {
					GioiTinh g=null;
					for(GioiTinh i: GioiTinh.class.getEnumConstants()) {
						if(i.getValue().equalsIgnoreCase(gioitinh)) {
							g=i;
							break;
						}
					}
					
					NhanVien nv=new NhanVien(id, ten, LocalDate.parse(ngaysinh), diachi, g, 
							sdt,LocalDate.parse(ngayvaolam));
					if(btnThem.getText().equalsIgnoreCase("Thêm mới")) {
						nhanvienDAO.create(nv);
						JOptionPane.showMessageDialog(rootPane, "Thêm nhân viên thành công");						
					}
					else {
						nhanvienDAO.update(nv);
						JOptionPane.showMessageDialog(rootPane, "Cập nhật nhân viên thành công");
					}
					this.dispose();
					parent.updateTable();
				}
			} catch (checkName | checkPhone | checkBirthday | checkDateOfWork e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(rootPane, e.getMessage());
			}
		}
		else {
			JOptionPane.showMessageDialog(rootPane, "Nhập đủ thông tin nhân viên");
		}
	}
	
	public void huy() {
		tenText.setText("");
		sdtText.setText("");
		diachiText.setText("");
		gioitinhText.setSelectedIndex(0);
		modelDate.setValue(new Date());
		modelDateWork.setValue(new Date());
	}
	
	public void loadDataNhanVien() {
		idText.setText(nv.getMaNV());
		tenText.setText(nv.getTenNV());
		sdtText.setText(nv.getSoDienThoai());
		diachiText.setText(nv.getDiaChi());
		
		for(int i=0;i< gioitinhText.getItemCount();i++) {
			if(gioitinhText.getItemAt(i).toString().equals(nv.getGioiTinh().getValue())) {
				gioitinhText.setSelectedIndex(i);
				break;
			}
		}
		
		modelDate.setValue(Date.from(nv.getNgaySinh().atStartOfDay(ZoneId.systemDefault()).toInstant()));
		modelDateWork.setValue(Date.from(nv.getNgayVaoLam().atStartOfDay(ZoneId.systemDefault()).toInstant()));

		VaiTro vaitro=null;
		for(VaiTro v: VaiTro.class.getEnumConstants()) {
			if(v.toString().equalsIgnoreCase(nhanvienDAO.getVaiTro(nv.getMaNV()))) {
				vaitro=v;
			}
		}
		for(int i=0;i<vaitroText.getItemCount();i++) {
			if(vaitroText.getItemAt(i).toString().equalsIgnoreCase(vaitro.getValue())) {
				vaitroText.setSelectedIndex(i);
				break;
			}
		}
		vaitroText.setEnabled(false);
		vaitroText.setFont(new Font("Segoe UI",1,16));
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
