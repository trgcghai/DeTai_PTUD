package view.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import controller.Database;
import controller.ExcelHelper;
import controller.FilterImp;
import controller.LabelDateFormatter;
import controller.actiontable.ButtonAction;
import controller.actiontable.TableActionEvent;
import controller.actiontable.TableCellEditorUpdateDelete;
import controller.actiontable.TableCellEditorViewCreateHoSo;
import controller.actiontable.TableCellRendererUpdateDelete;
import controller.actiontable.TableCellRendererViewCreateHoSo;
import dao.TaiKhoan_DAO;
import dao.TinTuyenDung_DAO;
import dao.HopDong_DAO;
import dao.NhaTuyenDung_DAO;
import dao.NhanVien_DAO;
import entity.TaiKhoan;
import entity.TinTuyenDung;
import entity.constraint.TrangThai;
import entity.constraint.TrinhDo;
import entity.NhaTuyenDung;
import entity.NhanVien;
import exception.checkBirthday;
import exception.checkDateOfWork;
import exception.checkName;
import exception.checkPhone;
import exception.checkUserName;
import exception.checkUserPass;
import swing.Button;
import swing.ComboBoxRenderer;
import swing.GradientPanel;
import swing.GradientRoundPanel;
import swing.RoundPanel;
import view.dialog.TaoSuaTinTuyenDungDialog;

public class TinTuyenDungFrame extends JFrame implements ActionListener, MouseListener, FocusListener {
	
	NhanVien userName;
	TinTuyenDungFrame parent;
	
//	Component danh sách tin tuyển dụng
	JPanel leftPanel,menuPanel,
		tintuyendungPanel,northPanelTinTuyenDung, centerPanelTinTuyenDung,
		logoPanel;
	JLabel userLabel, iconUserLabel,timkiemTenLabel, timkiemLuongLabel, timkiemNTDLabel, timkiemTrinhDoLabel, 
		titleHoSo,vaitroLeftLabel,
		tieudeLabel, nhatuyendungLabel, luongLabel;
	JTextField timkiemTenText, timkiemLuongText;
	Button btnTimKiem, btnLamLai, btnLuu;
	JComboBox timkiemNTDText, timkiemTrinhDoText;
	JScrollPane scroll;
	Icon iconBtnSave;
	
	GradientPanel danhsachCenterPanel;
	GradientRoundPanel timkiemPanel,
	danhsachPanel, danhsachNorthPanel;
	
	ButtonAction update, delete;
	
	private ArrayList<Component> updates;
	private ArrayList<Component> deletes;
	private ArrayList<RoundPanel> panelTTDs;
	
	private TinTuyenDung_DAO tintuyendungDAO;
	private NhaTuyenDung_DAO nhatuyendungDAO;
	private HopDong_DAO hopdongDAO;
	
	public TinTuyenDungFrame(NhanVien userName) {
		this.userName=userName;
		this.parent=this;
		
		updates=new ArrayList<Component>();
		deletes=new ArrayList<Component>();
		panelTTDs=new ArrayList<RoundPanel>();
		
		tintuyendungDAO=new TinTuyenDung_DAO();
		nhatuyendungDAO=new NhaTuyenDung_DAO();
		hopdongDAO=new HopDong_DAO();
		
//		Tạo component bên phải
		initComponent();
		
//		Thêm update và delete vào table
		
		
//		Thêm sự kiện
		addActionListener();
		addFocusListener();
		
		loadData();
		loadDataTinTuyenDung();
	}
	
	public void initComponent() {
		tintuyendungPanel=new JPanel(); 
		tintuyendungPanel.setLayout(new BorderLayout(5,5));
		
//		Hiển thị tìm kiếm và danh sách tin tuyển dụng
		centerPanelTinTuyenDung=new JPanel();
		centerPanelTinTuyenDung.setLayout(new BorderLayout(10, 10));
		centerPanelTinTuyenDung.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		centerPanelTinTuyenDung.setBackground(new Color(89, 145, 144));
//		Tìm kiếm tin tuyển dụng
		timkiemPanel=new GradientRoundPanel();
		timkiemPanel.setLayout(new BorderLayout(5,5));
		
		JPanel resSearch=new JPanel(); 
		resSearch.setOpaque(false);
		resSearch.setLayout(new GridBagLayout());
		resSearch.setBackground(Color.WHITE);
		GridBagConstraints gbc= new GridBagConstraints();
		gbc.gridx=0; gbc.gridy=0; gbc.insets=new Insets(5, 10, 5, 10); gbc.anchor=GridBagConstraints.EAST;
		timkiemTenLabel=new JLabel("Tiêu đề tin tuyển dụng:"); 
		timkiemTenLabel.setFont(new Font("Segoe UI",1,16));
		timkiemTenLabel.setForeground(Color.WHITE);
		resSearch.add(timkiemTenLabel, gbc);
		gbc.gridx=1; gbc.gridy=0; gbc.anchor=GridBagConstraints.WEST;
		timkiemTenText=new JTextField(15); 
		timkiemTenText.setFont(new Font("Segoe UI",0,16));
		timkiemTenText.setOpaque(false);
		timkiemTenText.setOpaque(false);
		resSearch.add(timkiemTenText, gbc);
		
		gbc.gridx=2; gbc.gridy=0; gbc.anchor=GridBagConstraints.EAST;
		timkiemLuongLabel=new JLabel("Mức lương:"); 
		timkiemLuongLabel.setFont(new Font("Segoe UI",1,16));
		timkiemLuongLabel.setForeground(Color.WHITE);
		resSearch.add(timkiemLuongLabel, gbc);
		gbc.gridx=3; gbc.gridy=0; gbc.anchor=GridBagConstraints.WEST;
		timkiemLuongText=new JTextField(15); 
		timkiemLuongText.setFont(new Font("Segoe UI",0,16));
		timkiemLuongText.setOpaque(false);
		timkiemLuongText.setForeground(Color.WHITE);
		resSearch.add(timkiemLuongText, gbc);
		
		gbc.gridx=0; gbc.gridy=1; gbc.anchor=GridBagConstraints.EAST;
		timkiemNTDLabel=new JLabel("Nhà tuyển dụng:"); 
		timkiemNTDLabel.setFont(new Font("Segoe UI",1,16));
		timkiemNTDLabel.setForeground(Color.WHITE);
		resSearch.add(timkiemNTDLabel, gbc);
		gbc.gridx=1; gbc.gridy=1; gbc.anchor=GridBagConstraints.WEST;
		timkiemNTDText=new JComboBox(); 
		timkiemNTDText.setFont(new Font("Segoe UI",0,16));
		timkiemNTDText.setForeground(Color.WHITE);
		timkiemNTDText.setBackground(new Color(89, 145, 144));
		timkiemNTDText.setPreferredSize(new Dimension(213,26));
		resSearch.add(timkiemNTDText, gbc);
		
		gbc.gridx=2; gbc.gridy=1; gbc.anchor=GridBagConstraints.EAST;
		timkiemTrinhDoLabel=new JLabel("Trình độ:"); 
		timkiemTrinhDoLabel.setFont(new Font("Segoe UI",1,16));
		timkiemTrinhDoLabel.setForeground(Color.WHITE);
		resSearch.add(timkiemTrinhDoLabel, gbc);
		gbc.gridx=3; gbc.gridy=1; gbc.anchor=GridBagConstraints.WEST;
		timkiemTrinhDoText=new JComboBox(); 
		timkiemTrinhDoText.setFont(new Font("Segoe UI",0,16));
		timkiemTrinhDoText.setBackground(new Color(89, 145, 144));
		timkiemTrinhDoText.setForeground(Color.WHITE);
		timkiemTrinhDoText.addItem("Chọn trình độ");
		TrinhDo[] trinhdos=TrinhDo.class.getEnumConstants();
		for(TrinhDo t: trinhdos) {
			timkiemTrinhDoText.addItem(t.getValue());
		}
		timkiemTrinhDoText.setPreferredSize(new Dimension(213,26));
		resSearch.add(timkiemTrinhDoText, gbc);
		
		JPanel resBtnSearch=new JPanel(); 
		resBtnSearch.setOpaque(false);
		resBtnSearch.setLayout(new BorderLayout(0,5));
		resBtnSearch.setBorder(BorderFactory.createEmptyBorder(10,10,10,23));
		btnTimKiem=new Button("Tìm kiếm"); btnTimKiem.setFont(new Font("Segoe UI",0,16));
		btnTimKiem.setPreferredSize(new Dimension(120,25));
		btnTimKiem.setBackground(new Color(0,102,102));
		btnTimKiem.setForeground(Color.WHITE);
		btnLamLai=new Button("Làm lại"); btnLamLai.setFont(new Font("Segoe UI",0,16));
		btnLamLai.setPreferredSize(new Dimension(120,25));
		btnLamLai.setBackground(Color.RED);
		btnLamLai.setForeground(Color.WHITE);
		resBtnSearch.add(btnTimKiem, BorderLayout.NORTH); resBtnSearch.add(btnLamLai, BorderLayout.SOUTH);
		
		timkiemPanel.add(resSearch, BorderLayout.CENTER);
		timkiemPanel.add(resBtnSearch, BorderLayout.EAST);
		
//		Danh sách tin tuyển dụng
		danhsachPanel=new GradientRoundPanel();
		danhsachPanel.setLayout(new BorderLayout(10, 10));
		
		danhsachNorthPanel=new GradientRoundPanel();
		danhsachNorthPanel.setLayout(new BorderLayout(10,10));
		danhsachNorthPanel.setBackground(Color.WHITE);
		iconBtnSave=new ImageIcon(getClass().getResource("/image/save.png"));
		JPanel resBtn=new JPanel();
		resBtn.setOpaque(false);
		resBtn.setBorder(BorderFactory.createEmptyBorder(10,10,0,20));
		resBtn.setBackground(Color.WHITE);
		btnLuu=new Button("Xuất Excel"); 
		btnLuu.setIcon(iconBtnSave);
		btnLuu.setFont(new Font("Segoe UI",0,16));
		btnLuu.setPreferredSize(new Dimension(140,30));
		btnLuu.setBackground(new Color(51,51,255));
		btnLuu.setForeground(Color.WHITE);
//		resBtn.add(btnLuu);
		titleHoSo=new JLabel("Danh sách tin tuyển dụng");
		titleHoSo.setFont(new Font("Segoe UI",1,16));
		titleHoSo.setForeground(Color.WHITE);
		titleHoSo.setBorder(BorderFactory.createEmptyBorder(10,20,0,10));
		danhsachNorthPanel.add(titleHoSo, BorderLayout.WEST);
		danhsachNorthPanel.add(resBtn, BorderLayout.EAST);
		
		danhsachCenterPanel=new GradientPanel(Color.decode("#ABC8CB"), Color.decode("#259195"));
		danhsachCenterPanel.setLayout(new FlowLayout(FlowLayout.LEFT,20,20));
		danhsachCenterPanel.setPreferredSize(new Dimension(getWidth(), 30*50));
		danhsachCenterPanel.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 0));
		
		scroll=new JScrollPane(danhsachCenterPanel);
		
		danhsachPanel.add(danhsachNorthPanel, BorderLayout.NORTH);
		danhsachPanel.add(scroll, BorderLayout.CENTER);
		
		centerPanelTinTuyenDung.add(timkiemPanel, BorderLayout.NORTH);
		centerPanelTinTuyenDung.add(danhsachPanel, BorderLayout.CENTER);
		
		tintuyendungPanel.add(centerPanelTinTuyenDung, BorderLayout.CENTER);
	}
	
	public RoundPanel panelTinTuyenDung(String tieude, String nhatuyendung, double luong, String logo, String maTTD) {
		DecimalFormat df = new DecimalFormat("#,###");
		RoundPanel panel=new RoundPanel();
		panel.setPreferredSize(new Dimension(400, 100));
		panel.setBackground(new Color(89, 145, 144));
		panel.setLayout(new BorderLayout(10,0));
		panel.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 10));
		
		logoPanel=new JPanel();
		logoPanel.setOpaque(false);
		logoPanel.setPreferredSize(new Dimension(100,100));
		if(getClass().getResource("/image/imageNTD/"+logo)!=null) {
			if(logoPanel.getComponents()!=null) {
				logoPanel.removeAll();
				logoPanel.revalidate();
				logoPanel.repaint();	
			}
			
			ImageIcon imageIcon=new ImageIcon(getClass().getResource("/image/imageNTD/"+logo));
			Image image=imageIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
			JLabel logoLabel=new JLabel(); logoLabel.setIcon(new ImageIcon(image));
			logoPanel.add(logoLabel);
			logoPanel.revalidate();
			logoPanel.repaint();				
		}
		else {
			logoPanel.removeAll();
			logoPanel.revalidate();
			logoPanel.repaint();	
		}
		
		JPanel centerPanel=new JPanel();
		centerPanel.setOpaque(false);
		centerPanel.setLayout(new GridLayout(3,1));
		tieudeLabel=new JLabel(tieude);
		tieudeLabel.setFont(new Font("Segoe UI",0,16));
		tieudeLabel.setForeground(Color.WHITE);
		nhatuyendungLabel=new JLabel(nhatuyendung);
		nhatuyendungLabel.setFont(new Font("Segoe UI",0,16));
		nhatuyendungLabel.setForeground(Color.WHITE);
		luongLabel=new JLabel(df.format(luong)+" VNĐ");
		luongLabel.setFont(new Font("Segoe UI",0,16));
		luongLabel.setForeground(Color.WHITE);
		centerPanel.add(tieudeLabel);
		centerPanel.add(nhatuyendungLabel);
		centerPanel.add(luongLabel);
		
		JPanel eastPanel=new JPanel();
		eastPanel.setBackground(new Color(89, 145, 144));
		eastPanel.setLayout(new BorderLayout());
		JPanel res=new JPanel();
		res.setOpaque(false);
		ButtonAction update=new ButtonAction();
		update.setIcon(new ImageIcon(getClass().getResource("/image/update.png")));
		update.setName(maTTD);
		ButtonAction delete=new ButtonAction();
		delete.setIcon(new ImageIcon(getClass().getResource("/image/delete.png")));
		delete.setName(maTTD);
		res.add(update); res.add(delete);
		eastPanel.add(res, BorderLayout.SOUTH);
		
		panel.add(logoPanel, BorderLayout.WEST);
		panel.add(centerPanel, BorderLayout.CENTER);
		panel.add(eastPanel, BorderLayout.EAST);
		
		update.addMouseListener(this);
		delete.addMouseListener(this);
		
		updates.add(update);
		deletes.add(delete);
		
		return panel;
	}
	
	public JPanel getPanel() {
		return this.tintuyendungPanel;
	}
	
	public void loadData() {
		tintuyendungDAO.setListTinTuyenDung(tintuyendungDAO.getDsTinTuyenDung());
		nhatuyendungDAO.setListNhatuyenDung(nhatuyendungDAO.getDsNhaTuyenDung());
		hopdongDAO.setListHopDong(hopdongDAO.getDSHopDong());
		
		timkiemNTDText.addItem("Chọn nhà tuyển dụng");
		for(NhaTuyenDung i: nhatuyendungDAO.getListNhatuyenDung()) {
			timkiemNTDText.addItem(i.getTenNTD());
		}
	}
	
	public void loadDataTinTuyenDung() {
		updates.clear();
		deletes.clear();
		panelTTDs.clear();
		
		danhsachCenterPanel.setPreferredSize(
				new Dimension(danhsachCenterPanel.getWidth(), tintuyendungDAO.getListTinTuyenDung().size()*50));
		danhsachCenterPanel.removeAll();
		danhsachCenterPanel.revalidate();
		danhsachCenterPanel.repaint();
		
		for(TinTuyenDung i: tintuyendungDAO.getListTinTuyenDung()) {
			NhaTuyenDung nhatuyendung=nhatuyendungDAO.getNhaTuyenDung(i.getNhaTuyenDung().getMaNTD());
			String tieude=i.getTieuDe();
			String tenNTD=nhatuyendung.getTenNTD();
			double luong=i.getLuong();
			String logo=nhatuyendung.getLogo();

			RoundPanel panel=panelTinTuyenDung(tieude, tenNTD, luong, logo, i.getMaTTD());
			panelTTDs.add(panel);
			danhsachCenterPanel.add(panel);
		}
	}
	
	public void updateData() {
		loadData();
		loadDataTinTuyenDung();
	}
	
	public void displayChiTietTinTuyenDung(String maTTD) {
		TinTuyenDung ttd=tintuyendungDAO.getTinTuyenDung(maTTD);
		NhaTuyenDung ntd=nhatuyendungDAO.getNhaTuyenDung(ttd.getNhaTuyenDung().getMaNTD());
		
		new TaoSuaTinTuyenDungDialog(parent, rootPaneCheckingEnabled, ntd, ttd).setVisible(true);;
	}
	
//	option
//	1: tìm kiếm theo tiêu đề
//	2: tìm kiếm theo lương
//	3: tìm kiếm theo tiêu đề và lương
//	4: tìm kiếm theo tiêu đề và nhà tuyển dụng
//	5: tìm kiếm theo tiêu đề và trình độ
//	6: tìm kiếm theo lương và nhà tuyển dụng
//	7: tìm kiếm theo lương và trình độ
//	8: tìm kiếm theo tiêu đề, lương và nhà tuyển dụng
//	9: tìm kiếm theo tiêu đề, lương và trình độ
//	10: tìm kiếm theo tất cả tiêu chí
	public void timkiem() {
		String tieude=timkiemTenText.getText();
		String luong=timkiemLuongText.getText();
		String ntd=timkiemNTDText.getSelectedItem().toString();
		String trinhdo=timkiemTrinhDoText.getSelectedItem().toString();
		
		if(tieude.equals("Nhập dữ liệu") && luong.equals("Nhập dữ liệu")
			&& ntd.equalsIgnoreCase("Chọn nhà tuyển dụng") && trinhdo.equalsIgnoreCase("Chọn trình độ")) {
			JOptionPane.showMessageDialog(rootPane, "Nhập thông tin để tìm kiếm");
			return;
		}
		
		if(!tieude.equals("Nhập dữ liệu") && luong.equals("Nhập dữ liệu")
				&& ntd.equalsIgnoreCase("Chọn nhà tuyển dụng") && trinhdo.equalsIgnoreCase("Chọn trình độ")) {
			tintuyendungDAO.getListTinTuyenDung().clear();
			tintuyendungDAO.setListTinTuyenDung(tintuyendungDAO.getTinTuyenDungTheo(tieude,1));
			loadDataTinTuyenDung();
			JOptionPane.showMessageDialog(rootPane, "Tìm thấy "+tintuyendungDAO.getListTinTuyenDung().size()+" tin tuyển dụng");
		}
		else if(!luong.equals("Nhập dữ liệu") && tieude.equals("Nhập dữ liệu")
				&& ntd.equalsIgnoreCase("Chọn nhà tuyển dụng") && trinhdo.equalsIgnoreCase("Chọn trình độ")) {
			if(Pattern.compile("^[0-9]+$").matcher(luong).matches()) {
				tintuyendungDAO.getListTinTuyenDung().clear();
				tintuyendungDAO.setListTinTuyenDung(tintuyendungDAO.getTinTuyenDungTheo(luong,2));
				loadDataTinTuyenDung();
				JOptionPane.showMessageDialog(rootPane, "Tìm thấy "+tintuyendungDAO.getListTinTuyenDung().size()+" tin tuyển dụng");
			}
			else {
				JOptionPane.showMessageDialog(rootPane, "Lương phải là số lớn hơn 0");
			}
		}
		else if(!ntd.equalsIgnoreCase("Chọn nhà tuyển dụng") && tieude.equals("Nhập dữ liệu") 
				&& luong.equals("Nhập dữ liệu") && trinhdo.equalsIgnoreCase("Chọn trình độ")) {
			tintuyendungDAO.getListTinTuyenDung().clear();
			tintuyendungDAO.setListTinTuyenDung(tintuyendungDAO.getTinTuyenDungTheo(ntd,11));
			loadDataTinTuyenDung();
			JOptionPane.showMessageDialog(rootPane, "Tìm thấy "+tintuyendungDAO.getListTinTuyenDung().size()+" tin tuyển dụng");
		}
		else if(!trinhdo.equalsIgnoreCase("Chọn trình độ") && ntd.equalsIgnoreCase("Chọn nhà tuyển dụng") 
				&& tieude.equals("Nhập dữ liệu") && luong.equals("Nhập dữ liệu")) {
			tintuyendungDAO.getListTinTuyenDung().clear();
			tintuyendungDAO.setListTinTuyenDung(tintuyendungDAO.getTinTuyenDungTheo(trinhdo,12));
			loadDataTinTuyenDung();
			JOptionPane.showMessageDialog(rootPane, "Tìm thấy "+tintuyendungDAO.getListTinTuyenDung().size()+" tin tuyển dụng");
		}
		else if(!ntd.equalsIgnoreCase("Chọn nhà tuyển dụng") && !trinhdo.equalsIgnoreCase("Chọn trình độ")
				&& tieude.equals("Nhập dữ liệu") && luong.equals("Nhập dữ liệu")) {
			tintuyendungDAO.getListTinTuyenDung().clear();
			String key=ntd+"/"+trinhdo;
			tintuyendungDAO.setListTinTuyenDung(tintuyendungDAO.getTinTuyenDungTheo(key,13));
			loadDataTinTuyenDung();
			JOptionPane.showMessageDialog(rootPane, "Tìm thấy "+tintuyendungDAO.getListTinTuyenDung().size()+" tin tuyển dụng");
		}
		else if(!tieude.equals("Nhập dữ liệu") && !luong.equals("Nhập dữ liệu")
				&& ntd.equalsIgnoreCase("Chọn nhà tuyển dụng") && trinhdo.equalsIgnoreCase("Chọn trình độ")) {
			if(Pattern.compile("^[0-9]+$").matcher(luong).matches()) {
				tintuyendungDAO.getListTinTuyenDung().clear();
				String key=tieude+"/"+luong;
				tintuyendungDAO.setListTinTuyenDung(tintuyendungDAO.getTinTuyenDungTheo(key,3));
				loadDataTinTuyenDung();
				JOptionPane.showMessageDialog(rootPane, "Tìm thấy "+tintuyendungDAO.getListTinTuyenDung().size()+" tin tuyển dụng");
			}
			else {
				JOptionPane.showMessageDialog(rootPane, "Lương phải là số lớn hơn 0");
			}
		}
		else if(!tieude.equals("Nhập dữ liệu") && !ntd.equalsIgnoreCase("Chọn nhà tuyển dụng")
				&& luong.equals("Nhập dữ liệu") && trinhdo.equalsIgnoreCase("Chọn trình độ")) {
			tintuyendungDAO.getListTinTuyenDung().clear();
			String key=tieude+"/"+ntd;
			tintuyendungDAO.setListTinTuyenDung(tintuyendungDAO.getTinTuyenDungTheo(key,4));
			loadDataTinTuyenDung();
			JOptionPane.showMessageDialog(rootPane, "Tìm thấy "+tintuyendungDAO.getListTinTuyenDung().size()+" tin tuyển dụng");
		}
		else if(!tieude.equals("Nhập dữ liệu") && !trinhdo.equalsIgnoreCase("Chọn trình độ")
				&& luong.equals("Nhập dữ liệu") && ntd.equalsIgnoreCase("Chọn nhà tuyển dụng")) {
			tintuyendungDAO.getListTinTuyenDung().clear();
			String key=tieude+"/"+trinhdo;
			tintuyendungDAO.setListTinTuyenDung(tintuyendungDAO.getTinTuyenDungTheo(key,5));
			loadDataTinTuyenDung();
			JOptionPane.showMessageDialog(rootPane, "Tìm thấy "+tintuyendungDAO.getListTinTuyenDung().size()+" tin tuyển dụng");
		}
		else if(!luong.equals("Nhập dữ liệu") && !ntd.equalsIgnoreCase("Chọn nhà tuyển dụng")
				&& tieude.equals("Nhập dữ liệu") && trinhdo.equalsIgnoreCase("Chọn trình độ")) {
			if(Pattern.compile("^[0-9]+$").matcher(luong).matches()) {
				tintuyendungDAO.getListTinTuyenDung().clear();
				String key=luong+"/"+ntd;
				tintuyendungDAO.setListTinTuyenDung(tintuyendungDAO.getTinTuyenDungTheo(key,6));
				loadDataTinTuyenDung();
				JOptionPane.showMessageDialog(rootPane, "Tìm thấy "+tintuyendungDAO.getListTinTuyenDung().size()+" tin tuyển dụng");
			}
			else {
				JOptionPane.showMessageDialog(rootPane, "Lương phải là số lớn hơn 0");
			}
		}
		else if(!luong.equals("Nhập dữ liệu") && !trinhdo.equalsIgnoreCase("Chọn trình độ")
				&& tieude.equals("Nhập dữ liệu") && ntd.equalsIgnoreCase("Chọn nhà tuyển dụng")) {
			if(Pattern.compile("^[0-9]+$").matcher(luong).matches()) {
				tintuyendungDAO.getListTinTuyenDung().clear();
				String key=luong+"/"+trinhdo;
				tintuyendungDAO.setListTinTuyenDung(tintuyendungDAO.getTinTuyenDungTheo(key,7));
				loadDataTinTuyenDung();
				JOptionPane.showMessageDialog(rootPane, "Tìm thấy "+tintuyendungDAO.getListTinTuyenDung().size()+" tin tuyển dụng");
			}
			else {
				JOptionPane.showMessageDialog(rootPane, "Lương phải là số lớn hơn 0");
			}
		}
		else if(!tieude.equals("Nhập dữ liệu") && !ntd.equalsIgnoreCase("Chọn nhà tuyển dụng")
				&& !trinhdo.equalsIgnoreCase("Chọn trình độ") && luong.equals("Nhập dữ liệu")) {
			tintuyendungDAO.getListTinTuyenDung().clear();
			String key=tieude+"/"+ntd+"/"+trinhdo;
			tintuyendungDAO.setListTinTuyenDung(tintuyendungDAO.getTinTuyenDungTheo(key,14));
			loadDataTinTuyenDung();
			JOptionPane.showMessageDialog(rootPane, "Tìm thấy "+tintuyendungDAO.getListTinTuyenDung().size()+" tin tuyển dụng");
		}
		else if(!luong.equals("Nhập dữ liệu") && !ntd.equalsIgnoreCase("Chọn nhà tuyển dụng")
				&& !trinhdo.equalsIgnoreCase("Chọn trình độ") && tieude.equals("Nhập dữ liệu")) {
			if(Pattern.compile("^[0-9]+$").matcher(luong).matches()) {
				tintuyendungDAO.getListTinTuyenDung().clear();
				String key=luong+"/"+ntd+"/"+trinhdo;
				tintuyendungDAO.setListTinTuyenDung(tintuyendungDAO.getTinTuyenDungTheo(key,15));
				loadDataTinTuyenDung();
				JOptionPane.showMessageDialog(rootPane, "Tìm thấy "+tintuyendungDAO.getListTinTuyenDung().size()+" tin tuyển dụng");
			}
			else {
				JOptionPane.showMessageDialog(rootPane, "Lương phải là số lớn hơn 0");
			}
		}
		else if(!tieude.equals("Nhập dữ liệu") && !luong.equals("Nhập dữ liệu") 
				&& !ntd.equalsIgnoreCase("Chọn nhà tuyển dụng") && trinhdo.equalsIgnoreCase("Chọn trình độ")) {
			if(Pattern.compile("^[0-9]+$").matcher(luong).matches()) {
				tintuyendungDAO.getListTinTuyenDung().clear();
				String key=tieude+"/"+luong+"/"+ntd;
				tintuyendungDAO.setListTinTuyenDung(tintuyendungDAO.getTinTuyenDungTheo(key,8));
				loadDataTinTuyenDung();
				JOptionPane.showMessageDialog(rootPane, "Tìm thấy "+tintuyendungDAO.getListTinTuyenDung().size()+" tin tuyển dụng");
			}
			else {
				JOptionPane.showMessageDialog(rootPane, "Lương phải là số lớn hơn 0");
			}
		}
		else if(!tieude.equals("Nhập dữ liệu") && !luong.equals("Nhập dữ liệu") 
				&& !trinhdo.equalsIgnoreCase("Chọn trình độ") && ntd.equalsIgnoreCase("Chọn nhà tuyển dụng")) {
			if(Pattern.compile("^[0-9]+$").matcher(luong).matches()) {
				tintuyendungDAO.getListTinTuyenDung().clear();
				String key=tieude+"/"+luong+"/"+trinhdo;
				tintuyendungDAO.setListTinTuyenDung(tintuyendungDAO.getTinTuyenDungTheo(key,9));
				loadDataTinTuyenDung();
				JOptionPane.showMessageDialog(rootPane, "Tìm thấy "+tintuyendungDAO.getListTinTuyenDung().size()+" tin tuyển dụng");
			}
			else {
				JOptionPane.showMessageDialog(rootPane, "Lương phải là số lớn hơn 0");
			}
		}
		else if(!tieude.equals("Nhập dữ liệu") && !luong.equals("Nhập dữ liệu") 
				&& !ntd.equalsIgnoreCase("Chọn nhà tuyển dụng") && !trinhdo.equalsIgnoreCase("Chọn trình độ")) {
			if(Pattern.compile("^[0-9]+$").matcher(luong).matches()) {
				tintuyendungDAO.getListTinTuyenDung().clear();
				String key=tieude+"/"+luong+"/"+ntd+"/"+trinhdo;
				tintuyendungDAO.setListTinTuyenDung(tintuyendungDAO.getTinTuyenDungTheo(key,10));
				loadDataTinTuyenDung();
				JOptionPane.showMessageDialog(rootPane, "Tìm thấy "+tintuyendungDAO.getListTinTuyenDung().size()+" tin tuyển dụng");
			}
			else {
				JOptionPane.showMessageDialog(rootPane, "Lương phải là số lớn hơn 0");
			}
		}
	}
	
//	Trạng thái text chuột không nằm trong ô
	public void addPlaceHolder(JTextField text) {
		Font font=text.getFont();
		font=font.deriveFont(Font.ITALIC);
		text.setFont(font);
		text.setForeground(Color.WHITE);
		text.setText("Nhập dữ liệu");
	}
	
//	Xóa trạng thái text chuột không nằm trong ô
	public void removePlaceHolder(JTextField text) {
		Font font=text.getFont();
		font=font.deriveFont(Font.PLAIN);
		text.setFont(font);
		text.setForeground(Color.WHITE);
	}
	
//	Listener
	public void addFocusListener() {
		timkiemTenText.addFocusListener(this);
		timkiemLuongText.addFocusListener(this);
		
		addPlaceHolder(timkiemTenText);
		addPlaceHolder(timkiemLuongText);
	}
	
	public void addActionListener() {
		btnTimKiem.addActionListener(this);
		btnLamLai.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		var obj=e.getSource();
		if(obj.equals(btnTimKiem)) {
			timkiem();
		}
		else if(obj.equals(btnLamLai)) {
			addPlaceHolder(timkiemTenText);
			addPlaceHolder(timkiemLuongText);
			timkiemNTDText.setSelectedIndex(0);
			timkiemTrinhDoText.setSelectedIndex(0);
			
			updateData();
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		var obj=e.getSource();
		for(int i=0; i<updates.size();i++) {
			if(obj.equals(updates.get(i))) {
				displayChiTietTinTuyenDung(updates.get(i).getName());
				break;
			}
		}
		for(int i=0; i<deletes.size();i++) {
			if(obj.equals(deletes.get(i))) {
				if(hopdongDAO.getHopDongTheoTinTuyenDung(deletes.get(i).getName()).size() > 0) {
					JOptionPane.showMessageDialog(rootPane, "Không thể xóa tin tuyển dụng vì đã có hồ sơ ứng tuyển");
				}
				else {
					int check=JOptionPane.showConfirmDialog(parent, "Có chắc chắn xóa?");
					if(check==JOptionPane.OK_OPTION) {
						tintuyendungDAO.delete(deletes.get(i).getName());
						JOptionPane.showMessageDialog(rootPane, "Xóa tin tuyển dụng thành công");
						updateData();
					}
				}
				break;
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		var obj=e.getSource();
		if(obj.equals(timkiemTenText)) {
			if(timkiemTenText.getText().equals("Nhập dữ liệu")) {
				timkiemTenText.setText(null);
				timkiemTenText.requestFocus();
				timkiemTenText.setBorder(BorderFactory.createLineBorder(new Color(0,102,102), 3));
				
				removePlaceHolder(timkiemTenText);
			}
		}
		else if(obj.equals(timkiemLuongText)) {
			if(timkiemLuongText.getText().equals("Nhập dữ liệu")) {
				timkiemLuongText.setText(null);
				timkiemLuongText.requestFocus();
				timkiemLuongText.setBorder(BorderFactory.createLineBorder(new Color(0,102,102), 3));
				
				removePlaceHolder(timkiemLuongText);
			}
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		var obj=e.getSource();
		if(obj.equals(timkiemTenText)) {
			if(timkiemTenText.getText().length()==0) {
				addPlaceHolder(timkiemTenText);
				timkiemTenText.setText("Nhập dữ liệu");
			}
			timkiemTenText.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		}
		else if(obj.equals(timkiemLuongText)) {
			if(timkiemLuongText.getText().length()==0) {
				addPlaceHolder(timkiemLuongText);
				timkiemLuongText.setText("Nhập dữ liệu");
			}
			timkiemLuongText.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		}
	}

}
