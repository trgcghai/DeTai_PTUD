package view.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import controller.FilterImp;
import controller.LabelDateFormatter;
import dao.NhaTuyenDung_DAO;
import entity.NhaTuyenDung;
import entity.constraint.GioiTinh;
import exception.checkPhone;
import exception.checkUserEmail;
import swing.Button;
import swing.GradientPanel;
import view.frame.NhaTuyenDungFrame;

public class ThemSuaNhaTuyenDungDialog extends JDialog implements ActionListener{
	
	JPanel logoPanel;
	GradientPanel inforNhaTuyenDungPanel, btnPanel;
	JLabel idLabel, tenLabel, sdtLabel, dateLabel, gioitinhLabel, diachiLabel,emailLabel;
	JTextField idText, tenText, sdtText, diachiText, emailText;
	JComboBox gioitinhText;
	UtilDateModel modelDate;
	JDatePickerImpl dateText;
	Button btnThem, btnHuy, btnLogo;
	JFileChooser fileChooser;
	String logo;
	
	private Frame parent;
	private NhaTuyenDung ntd;
	private int idMax=0;
	private NhaTuyenDung_DAO nhatuyendungDAO;

	public ThemSuaNhaTuyenDungDialog(Frame parent, boolean modal) {
		super(parent, modal);
		setTitle("Thêm mới nhà tuyển dụng");
		setResizable(false);
		setSize(650,450);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		
		this.parent=parent;
		nhatuyendungDAO=new NhaTuyenDung_DAO();
		for(NhaTuyenDung ntd: nhatuyendungDAO.getDsNhaTuyenDung()) {
			int numberID=Integer.parseInt(ntd.getMaNTD().substring(3, ntd.getMaNTD().length()));
			if(numberID > idMax) {
				idMax=numberID;
			}
		}
		
		initComponent();
		
		addActionListener();
		
		idText.setText((idMax+1)<10?("NTD0"+(idMax+1)):("NTD"+(idMax+1)));
	}
	
	public ThemSuaNhaTuyenDungDialog(Frame parent, boolean modal, NhaTuyenDung ntd) {
		this(parent, modal);
		this.ntd=ntd;
		setTitle("Cập nhật nhà tuyển dụng");
		btnThem.setText("Cập nhật");
		
		loadDataNhaTuyenDung();
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
		logoPanel.setLayout(new BorderLayout());
		logoPanel.setOpaque(false);
		logoPanel.setBorder(BorderFactory.createLineBorder(new Color(0,102,102)));
		logoPanel.setPreferredSize(new Dimension(100, 100));
		logoPanel.setBackground(Color.WHITE);
		inforNhaTuyenDungPanel.add(logoPanel, gbc);
		
		gbc.gridx=2; gbc.gridy=1; gbc.gridheight=1;
		btnLogo=new Button("Chọn logo");
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
		
		btnThem=new Button("Thêm mới"); btnThem.setFont(new Font("Segoe UI",0,16));
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

	public void openFile() {
//		fileChooser=new JFileChooser("D:\\DeadlineIUH\\BaitapPTUD\\Code\\DeTai_PTUD\\src\\image\\imageNTD");
		JFileChooser fileChooser = new JFileChooser(new File("src/image/imageNTD").getAbsolutePath());
		int actionResult=fileChooser.showOpenDialog(this);
		if(actionResult==fileChooser.APPROVE_OPTION) {
			String path=fileChooser.getSelectedFile().getAbsolutePath();
			var res=path.split("\\\\");
			String extension = res[res.length-1].split("\\.")[1];
			logo=res[res.length-1].split("\\.")[0]+"."+extension;
			Pattern pattern=Pattern.compile("(png|jpg|gif)$",Pattern.CASE_INSENSITIVE);
			if(pattern.matcher(extension).matches()) {
				if(logoPanel.getComponents()!=null) {
					logoPanel.removeAll();
					logoPanel.revalidate();
					logoPanel.repaint();	
				}
				
				ImageIcon imageIcon=new ImageIcon(path);
				Image image=imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
				JLabel logo=new JLabel(); logo.setIcon(new ImageIcon(image));
				logoPanel.add(logo, BorderLayout.CENTER);
				logoPanel.revalidate();
				logoPanel.repaint();			
			}
			else {
				JOptionPane.showMessageDialog(rootPane, "Không phải file ảnh");
			}
		}
	}

	public void loadDataNhaTuyenDung() {
		idText.setText(ntd.getMaNTD());
		tenText.setText(ntd.getTenNTD());
		diachiText.setText(ntd.getDiaChi());
		sdtText.setText(ntd.getSoDienThoai());
		emailText.setText(ntd.getEmail());
		logo=ntd.getLogo();
		
		if(getClass().getResource("/image/imageNTD/"+ntd.getLogo())!=null) {
			if(logoPanel.getComponents()!=null) {
				logoPanel.removeAll();
				logoPanel.revalidate();
				logoPanel.repaint();	
			}
			
			ImageIcon imageIcon=new ImageIcon(getClass().getResource("/image/imageNTD/"+ntd.getLogo()));
			Image image=imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
			JLabel logo=new JLabel(); logo.setIcon(new ImageIcon(image));
			logoPanel.add(logo);
			logoPanel.revalidate();
			logoPanel.repaint();				
		}
		else {
			logoPanel.removeAll();
			logoPanel.revalidate();
			logoPanel.repaint();	
		}

	}
	
	public void them() {
		String id=idText.getText();
		String ten=tenText.getText();
		String sdt=sdtText.getText();
		String diachi=diachiText.getText();
		String email=emailText.getText();
		
		if(!ten.equals("") && !sdt.equals("") && !diachi.equals("") && !email.equals("")) {
			if(!logo.equals("")) {
				var check=new FilterImp();
				try {
					if(check.checkPhone(sdt) && check.checkUserEmail(email)) {
						NhaTuyenDung ntd=new NhaTuyenDung(id, ten, email, logo, sdt, diachi);
						
						if(btnThem.getText().equals("Thêm mới")) {
							nhatuyendungDAO.create(ntd);
							JOptionPane.showMessageDialog(rootPane, "Thêm nhà tuyển dụng thành công");
						}
						else {
							nhatuyendungDAO.update(ntd);
							JOptionPane.showMessageDialog(rootPane, "Cập nhật nhà tuyển dụng thành công");
						}
						this.dispose();
						((NhaTuyenDungFrame)parent).updateTable();
					}
				} catch (checkPhone | checkUserEmail e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(rootPane, e.getMessage());
				}
			}
			else {
				JOptionPane.showMessageDialog(rootPane, "Chọn logo nhà tuyển dụng");
			}
		}
		else {
			JOptionPane.showMessageDialog(rootPane, "Nhập đủ thông tin nhà tuyển dụng");
		}
	}
	
	public void huy() {
		tenText.setText("");
		sdtText.setText("");
		diachiText.setText("");
		emailText.setText("");
		
		logoPanel.removeAll();
		logoPanel.revalidate();
		logoPanel.repaint();
		
		logo="";

	}
	
	public void addActionListener() {
		btnThem.addActionListener(this);
		btnHuy.addActionListener(this);
		btnLogo.addActionListener(this);
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
		else if(obj.equals(btnLogo)) {
			openFile();
		}
	}
}
