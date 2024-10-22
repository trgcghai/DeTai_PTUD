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
import java.text.DecimalFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import com.spire.doc.Table;

import component.GradientPanel;
import controller.LabelDateFormatter;
import dao.HopDong_DAO;
import dao.NhaTuyenDung_DAO;
import dao.NhanVien_DAO;
import dao.TinTuyenDung_DAO;
import dao.UngVien_DAO;
import entity.HopDong;

public class ChiTietHopDongDialog extends JDialog implements ActionListener{
	
	GradientPanel inforHopDongPanel, btnPanel;
	JLabel idLabel, tenLabel, ngaylapLabel, tieudeLabel, phiLabel,
			luongLabel, nhatuyendungLabel, sdtLabel, emailLabel, nhanvienLabel;
	JTextField idText, tenText, tieudeText,
			luongText, phiText, nhatuyendungText, sdtText, emailText, nhanvienText;
	UtilDateModel modelDateNgayLap;
	JDatePickerImpl ngaylapText;
	JButton btnLuu, btnHuy;
	GridBagConstraints gbc;
	
	private HopDong_DAO hopdongDAO;
	private UngVien_DAO ungvienDAO;
	private NhanVien_DAO nhanvienDAO;
	private TinTuyenDung_DAO tintuyendungDAO;
	private NhaTuyenDung_DAO nhatuyendungDAO;
	
	private HopDong hd;
	
	public ChiTietHopDongDialog(Frame parent, boolean modal, HopDong hd) {
		super(parent, modal);
		setTitle("Xem chi tiết hợp đồng");
		setResizable(false);
		setSize(850,450);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		
		this.hd=hd;
		hopdongDAO=new HopDong_DAO();
		ungvienDAO=new UngVien_DAO();
		nhanvienDAO=new NhanVien_DAO();
		tintuyendungDAO=new TinTuyenDung_DAO();
		nhatuyendungDAO=new NhaTuyenDung_DAO();
		
		initComponent();
		addActionListener();
		loadData();
		loadDataHopDong();
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

	public void loadData() {
		hopdongDAO.setListHopDong(hopdongDAO.getDSHopDong());
		ungvienDAO.setListUngVien(ungvienDAO.getDSUngVien());
		nhanvienDAO.setListNhanVien(nhanvienDAO.getDSNhanVien());
		tintuyendungDAO.setListTinTuyenDung(tintuyendungDAO.getDsTinTuyenDung());
		nhatuyendungDAO.setListNhatuyenDung(nhatuyendungDAO.getDsNhaTuyenDung());
	}
	
	public void loadDataHopDong() {
		idText.setText(hd.getMaHD());
		phiText.setText(String.valueOf(hd.getPhiDichVu()));
		modelDateNgayLap.setValue(Date.from(hd.getThoiGian().atStartOfDay(ZoneId.systemDefault()).toInstant()));
		tieudeText.setText(tintuyendungDAO.getTinTuyenDung(hd.getTinTuyenDung().getMaTTD()).getTieuDe());
		nhatuyendungText.setText(nhatuyendungDAO
				.getNhaTuyenDung(tintuyendungDAO.getTinTuyenDung(hd.getTinTuyenDung().getMaTTD()).getNhaTuyenDung().getMaNTD())
				.getTenNTD());
		luongText.setText(String.valueOf(tintuyendungDAO.getTinTuyenDung(hd.getTinTuyenDung().getMaTTD()).getLuong()));
		tenText.setText(ungvienDAO.getUngVien(hd.getUngVien().getMaUV()).getTenUV());
		sdtText.setText(ungvienDAO.getUngVien(hd.getUngVien().getMaUV()).getSoDienThoai());
		emailText.setText(ungvienDAO.getUngVien(hd.getUngVien().getMaUV()).getEmail());
		nhanvienText.setText(nhanvienDAO.getNhanVien(hd.getNhanVien().getMaNV()).getTenNV());
	}
	
	private void fillTableWithData(Table table, String[][] data) {
	    for (int r = 0; r < data.length; r++) {
	        for (int c = 0; c < data[r].length; c++) {
	            table.getRows().get(r + 1).getCells().get(c).getParagraphs().get(0).setText(data[r][c]);
	        }
	    }
	}
	
	private void writeDataToDocument(Document doc, String[][] purchaseData) {
	    Table table = doc.getSections().get(0).getTables().get(2);
	    fillTableWithData(table, purchaseData);
	}
	
	public void addActionListener() {
		btnHuy.addActionListener(this);
		btnLuu.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		var obj=e.getSource();
		if(obj.equals(btnHuy)) {
			this.dispose();
		}
		else if(obj.equals(btnLuu)) {
			DateTimeFormatter dft = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			DecimalFormat df = new DecimalFormat("#,###");
			Document doc = new Document();
			 
	        doc.loadFromFile("src/form/form.docx");
	 
	        doc.replace("#MaHD", hd.getMaHD(), true, true);
	        doc.replace("#Date", hd.getThoiGian().format(dft), true, true);
	        doc.replace("#nhanvien", nhanvienDAO.getNhanVien(hd.getNhanVien().getMaNV()).getTenNV(), true, true);
	        
	        doc.replace("#tenNTD", nhatuyendungDAO
					.getNhaTuyenDung(tintuyendungDAO.getTinTuyenDung(hd.getTinTuyenDung().getMaTTD()).getNhaTuyenDung().getMaNTD())
					.getTenNTD(), true, true);
	        doc.replace("#tieude", tintuyendungDAO.getTinTuyenDung(hd.getTinTuyenDung().getMaTTD()).getTieuDe(), true, true);
	        doc.replace("#emailNTD", nhatuyendungDAO
					.getNhaTuyenDung(tintuyendungDAO.getTinTuyenDung(hd.getTinTuyenDung().getMaTTD()).getNhaTuyenDung().getMaNTD())
					.getEmail(), true, true);
	        doc.replace("#diachiNTD", nhatuyendungDAO
					.getNhaTuyenDung(tintuyendungDAO.getTinTuyenDung(hd.getTinTuyenDung().getMaTTD()).getNhaTuyenDung().getMaNTD())
					.getDiaChi(), true, true);
	        doc.replace("#sodienthoaiNTD", nhatuyendungDAO
					.getNhaTuyenDung(tintuyendungDAO.getTinTuyenDung(hd.getTinTuyenDung().getMaTTD()).getNhaTuyenDung().getMaNTD())
					.getSoDienThoai(), true, true);
	        
	        doc.replace("#tenUV", ungvienDAO.getUngVien(hd.getUngVien().getMaUV()).getTenUV(), true, true);
	        doc.replace("#ngaysinh", ungvienDAO.getUngVien(hd.getUngVien().getMaUV()).getNgaySinh().format(dft), true, true);
	        doc.replace("#emailUV", ungvienDAO.getUngVien(hd.getUngVien().getMaUV()).getEmail(), true, true);
	        doc.replace("#diachiUV", ungvienDAO.getUngVien(hd.getUngVien().getMaUV()).getDiaChi(), true, true);
	        doc.replace("#sodienthoaiUV", ungvienDAO.getUngVien(hd.getUngVien().getMaUV()).getSoDienThoai(), true, true);
	        
	        String[][] purchaseData = {
	                new String[]{tintuyendungDAO.getTinTuyenDung(hd.getTinTuyenDung().getMaTTD()).getTieuDe(), 
	                	df.format(tintuyendungDAO.getTinTuyenDung(hd.getTinTuyenDung().getMaTTD()).getLuong())+" VNĐ"}
	        };
	        
	        writeDataToDocument(doc, purchaseData);
	        
	        doc.replace("#luong", df.format(tintuyendungDAO.getTinTuyenDung(hd.getTinTuyenDung().getMaTTD()).getLuong())+" VNĐ", true, true);
	        int tax=(int)(hd.getPhiDichVu()*100/tintuyendungDAO.getTinTuyenDung(hd.getTinTuyenDung().getMaTTD()).getLuong());
	        doc.replace("#number", String.valueOf(tax), true, true);
	        doc.replace("#thanhtien", df.format(hd.getPhiDichVu())+" VNĐ", true, true);
	 
	        doc.isUpdateFields(true);
	 
	        int number=new Random().nextInt();
	        doc.saveToFile("Invoice_"+number+".pdf", FileFormat.PDF);
	        
	        JOptionPane.showMessageDialog(rootPane, "In hợp đồng thành công");
	        this.dispose();
		}
	}
}