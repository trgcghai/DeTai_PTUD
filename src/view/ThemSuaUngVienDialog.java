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
import dao.UngVien_DAO;
import entity.NhanVien;
import entity.UngVien;
import entity.constraint.GioiTinh;
import exception.checkBirthday;
import exception.checkName;
import exception.checkPhone;
import exception.checkUserEmail;

public class ThemSuaUngVienDialog extends JDialog implements ActionListener{
	
	GradientPanel inforUngVienPanel, btnPanel;
	JLabel idLabel, tenLabel, sdtLabel, dateLabel, gioitinhLabel, diachiLabel,emailLabel;
	JTextField idText, tenText, sdtText, diachiText, emailText;
	JComboBox gioitinhText;
	UtilDateModel modelDate;
	JDatePickerImpl dateText;
	JButton btnThem, btnHuy;
	
	private int idMax=0;
	private UngVien uv;
	private UngVienFrame parent;
	private UngVien_DAO ungvienDAO;

	public ThemSuaUngVienDialog(Frame parent, boolean modal) {
		super(parent, modal);
		setTitle("Thêm mới ứng viên");
		setResizable(false);
		setSize(550,400);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		
		this.parent=(UngVienFrame) parent;
		ungvienDAO=new UngVien_DAO();
		for(UngVien uv: ungvienDAO.getDSUngVien()) {
			int numberID=Integer.parseInt(uv.getMaUV().substring(2, uv.getMaUV().length()));
			if(numberID > idMax) {
				idMax=numberID;
			}
		}
		
		initComponent();
		
		addActionListener();
		
		idText.setText((idMax+1)<10?("UV0"+(idMax+1)):("UV"+(idMax+1)));
	}
	
	public ThemSuaUngVienDialog(Frame parent, boolean modal, UngVien ungvien) {
		this(parent, modal);
		this.uv=ungvien;
		setTitle("Cập nhật ứng viên");
		btnThem.setText("Cập nhật");
		
		loadDataUngVien();
	}
	
	public void initComponent() {
		inforUngVienPanel=new GradientPanel(Color.decode("#ABC8CB"), Color.decode("#7CBDBF")); 
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
		btnPanel=new GradientPanel(Color.decode("#ABC8CB"), Color.decode("#7CBDBF")); 
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
		String email=emailText.getText();
		
		if(!ten.equals("") && !sdt.equals("") && !diachi.equals("") && !email.equals("")) {
			var check=new FilterImp();
			try {
				if(check.checkName(ten) && check.checkPhone(sdt) && check.checkUserEmail(email)
					&& check.checkBirthday(LocalDate.parse(ngaysinh))) {
					GioiTinh g=null;
					for(GioiTinh i: GioiTinh.class.getEnumConstants()) {
						if(i.getValue().equalsIgnoreCase(gioitinh)) {
							g=i;
							break;
						}
					}
					
					UngVien uv=new UngVien(id, ten, email, LocalDate.parse(ngaysinh), diachi, g, sdt);
					if(btnThem.getText().equalsIgnoreCase("Thêm mới")) {
						ungvienDAO.create(uv);
						JOptionPane.showMessageDialog(rootPane, "Thêm ứng viên thành công");						
					}
					else {
						ungvienDAO.update(uv);
						JOptionPane.showMessageDialog(rootPane, "Cập nhật ứng viên thành công");
					}
					this.dispose();
					parent.updateTable();
				}
			} catch (checkName | checkPhone | checkUserEmail | checkBirthday e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(rootPane, e.getMessage());
			}
		}
		else {
			JOptionPane.showMessageDialog(rootPane, "Nhập đủ thông tin ứng viên");
		}
	}
	
	public void huy() {
		tenText.setText("");
		sdtText.setText("");
		diachiText.setText("");
		gioitinhText.setSelectedIndex(0);
		emailText.setText("");
		modelDate.setValue(new Date());
	}
	
	public void loadDataUngVien() {
		idText.setText(uv.getMaUV());
		tenText.setText(uv.getTenUV());
		sdtText.setText(uv.getSoDienThoai());
		diachiText.setText(uv.getDiaChi());
		emailText.setText(uv.getEmail());
		modelDate.setValue(Date.from(uv.getNgaySinh().atStartOfDay(ZoneId.systemDefault()).toInstant()));
		
		for(int i=0;i< gioitinhText.getItemCount();i++) {
			if(gioitinhText.getItemAt(i).toString().equals(uv.getGioiTinh().getValue())) {
				gioitinhText.setSelectedIndex(i);
				break;
			}
		}
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
