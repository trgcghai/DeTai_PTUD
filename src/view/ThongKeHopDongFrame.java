package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultRowSorter;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import component.GradientRoundPanel;
import controller.ExcelHelper;
import controller.LabelDateFormatter;
import dao.HopDong_DAO;
import dao.NhaTuyenDung_DAO;
import dao.UngVien_DAO;
import entity.HopDong;
import entity.NhaTuyenDung;
import entity.TinTuyenDung;
import entity.UngVien;

public class ThongKeHopDongFrame extends JFrame implements ActionListener, MouseListener, FocusListener {
	String userName;
	ThongKeHopDongFrame parent;
	
//	Component thống kê hợp đồng
	JPanel menuPanel, timkiemPanel, tongketPanel, hopDongPanel,centerPanelHopDong, danhsachPanel, danhsachNorthPanel, danhsachCenterPanel;
	JLabel titleHopDong, ngayBatDauLabel, ngayKetThucLabel, timkiemNTDLabel, timkiemUVLabel, summaryValueLabel, summaryNumberLabel, valueLabel, numberLabel;
	JButton btnTimKiem, btnLamLai, btnExcel;
	JTable tableHopDong;
	DefaultTableModel modelTableHopDong;
	JScrollPane scrollHopDong;
	UtilDateModel modelDate;
	JDatePickerImpl ngayBatDau, ngayKetThuc;
	JComboBox<Object> comboBoxNTD, comboBoxUV;
	Icon iconBtnSave;
	
	private HopDong_DAO hopdong_dao;
	private NhaTuyenDung_DAO nhatuyendungDAO;
	private UngVien_DAO ungVienDao;
	
	public ThongKeHopDongFrame(String userName) {
		this.userName=userName;
		this.parent = this;

//		Tạo component bên phải
		initComponent();
		
//		Thêm sự kiện
		addActionListener();
//		addMouseListener();
//		addFocusListener();
		
		hopdong_dao = new HopDong_DAO();
		nhatuyendungDAO = new NhaTuyenDung_DAO();
		ungVienDao = new UngVien_DAO();
		
		loadData();
		loadDataTable();
	}
	
	public JLabel createLabel(String title) {
		JLabel label = new JLabel(title);
		label.setFont(new Font("Segoe UI",1,16));
		label.setForeground(Color.WHITE);
		return label;
	}
	
	public JButton createButton(String title, Color bgColor, Color fgColor) {
		JButton button = new JButton(title); 
		button.setFont(new Font("Segoe UI",0,16));
		button.setPreferredSize(new Dimension(120,25));
		button.setBackground(bgColor);
		button.setForeground(fgColor);
		return button;
	}
	
	public void initComponent() {
		hopDongPanel=new JPanel(); 
		hopDongPanel.setLayout(new BorderLayout(5,5));
		
//		Hiển thị Thống kê và danh sách tin tuyển dụng
		centerPanelHopDong=new JPanel();
		centerPanelHopDong.setLayout(new BorderLayout(10, 10));
		centerPanelHopDong.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		centerPanelHopDong.setBackground(new Color(89, 145, 144));
//		Thống kê tin tuyển dụng
		timkiemPanel=new GradientRoundPanel();
		timkiemPanel.setBackground(Color.WHITE);
		timkiemPanel.setLayout(new BorderLayout());
		
		JPanel resFormSearch = new JPanel();
		resFormSearch.setOpaque(false);
		resFormSearch.setBackground(Color.WHITE);
		
		timkiemNTDLabel= createLabel("Nhà tuyển dụng:"); 
		comboBoxNTD=new JComboBox<Object>(); 
		comboBoxNTD.setFont(new Font("Segoe UI",0,16));
		comboBoxNTD.setOpaque(false);
		resFormSearch.add(timkiemNTDLabel);
		resFormSearch.add(comboBoxNTD);
		
		timkiemUVLabel= createLabel("Ứng viên:"); 
		comboBoxUV=new JComboBox<Object>(); 
		comboBoxUV.setFont(new Font("Segoe UI",0,16));
		comboBoxUV.setOpaque(false);
		resFormSearch.add(timkiemUVLabel);
		resFormSearch.add(comboBoxUV);
		
		modelDate=new UtilDateModel();
		Properties p=new Properties();
		p.put("text.day", "Day"); 
		p.put("text.month", "Month"); 
		p.put("text.year", "Year");
		JDatePanelImpl panelDate=new JDatePanelImpl(modelDate, p);
		
		ngayBatDauLabel= createLabel("Ngày bắt đầu:"); 
		ngayBatDau=new JDatePickerImpl(panelDate, new LabelDateFormatter());
		ngayBatDau.setPreferredSize(new Dimension(150,25));
		resFormSearch.add(ngayBatDauLabel);
		resFormSearch.add(ngayBatDau);
		
		ngayKetThucLabel= createLabel("Ngày kết thúc:"); 
		ngayKetThuc=new JDatePickerImpl(panelDate, new LabelDateFormatter());
		ngayKetThuc.setPreferredSize(new Dimension(150,25));
		resFormSearch.add(ngayKetThucLabel);
		resFormSearch.add(ngayKetThuc);
		
		JPanel resBtnSearch=new JPanel();
		resBtnSearch.setOpaque(false);
		resBtnSearch.setPreferredSize(new Dimension(350, 35));
		resBtnSearch.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 5));
		resBtnSearch.setBackground(Color.WHITE);
		
		btnTimKiem= createButton("Thống kê", new Color(0,102,102), Color.WHITE); 
		btnLamLai= createButton("Hủy", Color.RED, Color.WHITE); 
		resBtnSearch.add(btnTimKiem); 
		resBtnSearch.add(btnLamLai);
		
		timkiemPanel.add(resFormSearch, BorderLayout.WEST);
		timkiemPanel.add(resBtnSearch, BorderLayout.EAST);
		
//		Danh sách tin tuyển dụng
		danhsachPanel=new GradientRoundPanel();
		danhsachPanel.setBackground(Color.WHITE);
		danhsachPanel.setLayout(new BorderLayout(10, 10));
		
		danhsachNorthPanel=new GradientRoundPanel();
		danhsachNorthPanel.setLayout(new BorderLayout(10,10));
		danhsachNorthPanel.setBackground(Color.WHITE);
		iconBtnSave=new ImageIcon(getClass().getResource("/image/save.png"));
		JPanel resBtnThem=new JPanel();
		resBtnThem.setOpaque(false);
		resBtnThem.setBorder(BorderFactory.createEmptyBorder(10,10,0,20));
		resBtnThem.setBackground(Color.WHITE);
		
		btnExcel=new JButton("Xuất Excel", iconBtnSave); 
		btnExcel.setFont(new Font("Segoe UI",0,16));
		btnExcel.setPreferredSize(new Dimension(140,30));
		btnExcel.setBackground(new Color(51,51,255));
		btnExcel.setForeground(Color.WHITE);
		resBtnThem.add(btnExcel);
		titleHopDong=new JLabel("Danh sách hợp đồng");
		titleHopDong.setForeground(Color.WHITE);
		titleHopDong.setFont(new Font("Segoe UI",1,16));
		titleHopDong.setBorder(BorderFactory.createEmptyBorder(10,20,0,10));
		danhsachNorthPanel.add(titleHopDong, BorderLayout.WEST);
		danhsachNorthPanel.add(resBtnThem, BorderLayout.EAST);
		
		danhsachCenterPanel=new GradientRoundPanel();
		danhsachCenterPanel.setLayout(new BoxLayout(danhsachCenterPanel, BoxLayout.PAGE_AXIS));
		danhsachCenterPanel.setBackground(Color.WHITE);
		String[] colName= {"Mã hợp đồng","Tên ứng viên","Số điện thoại","Nhà tuyển dụng", "Phí dịch vụ", "Ngày lập"};
		Object[][] data = {
			    {1, "Nguyễn Thắng Minh Đạt", "0123456789", "Facebook", "50,000 VNĐ", LocalDate.now().toString()},
			    {2, "Nguyễn Thắng Minh Đạt", "0987654321", "Amazon", "50,000 VNĐ", LocalDate.now().toString()}
			};
		modelTableHopDong= new DefaultTableModel(data, colName){
			boolean[] canEdit = new boolean [] {
	                false, false, false, false, false, true, true
	            };
			
            @Override
            public boolean isCellEditable(int row, int column) {
               return canEdit[column];
            }
        };
		tableHopDong=new JTable(modelTableHopDong);
		tableHopDong.getTableHeader().setFont(new Font("Segoe UI",1,14));
		tableHopDong.setFont(new Font("Segoe UI",0,16));
		tableHopDong.setRowHeight(30);
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		for(int i=0;i<tableHopDong.getColumnCount();i++) {
			tableHopDong.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);			
		}
		tableHopDong.setAutoCreateRowSorter(true);
		ArrayList<RowSorter.SortKey> list = new ArrayList<>();
        DefaultRowSorter sorter = ((DefaultRowSorter)tableHopDong.getRowSorter());
        sorter.setSortsOnUpdates(true);
        list.add( new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sorter.setSortKeys(list);
        sorter.sort();
		scrollHopDong=new JScrollPane(tableHopDong);
		scrollHopDong.setBorder(BorderFactory.createLineBorder(new Color(0,191,165)));
		scrollHopDong.setPreferredSize(new Dimension(1280, 570));
		GradientRoundPanel resScroll=new GradientRoundPanel();
		resScroll.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));
		resScroll.setLayout(new BoxLayout(resScroll, BoxLayout.PAGE_AXIS));
		resScroll.setBackground(Color.WHITE);
		resScroll.add(scrollHopDong);
		danhsachCenterPanel.add(resScroll);
		
		tongketPanel=new GradientRoundPanel();
		tongketPanel.setBackground(Color.WHITE);
		tongketPanel.setLayout(new BorderLayout());
		
		JPanel resPanelSummary = new JPanel();
		resPanelSummary.setOpaque(false);
		resPanelSummary.setBackground(Color.WHITE);
		resPanelSummary.setLayout(new BorderLayout());
		
		JPanel temp = new JPanel();
		summaryValueLabel= createLabel("Tổng giá trị hợp đồng:"); 
		valueLabel = createLabel("");
		temp.add(summaryValueLabel);
		temp.add(valueLabel);
		temp.setOpaque(false);
		temp.setBackground(Color.WHITE);
		resPanelSummary.add(temp, BorderLayout.NORTH);
		
		JPanel temp1 = new JPanel();
		summaryNumberLabel= createLabel("Tổng số lượng hợp đồng:"); 
		numberLabel = createLabel("");
		temp1.add(summaryNumberLabel);
		temp1.add(numberLabel);
		temp1.setOpaque(false);
		temp1.setBackground(Color.WHITE);
		resPanelSummary.add(temp1, BorderLayout.CENTER);
		tongketPanel.add(resPanelSummary, BorderLayout.WEST);
		
		danhsachPanel.add(danhsachNorthPanel, BorderLayout.NORTH);
		danhsachPanel.add(danhsachCenterPanel, BorderLayout.CENTER);
		
		centerPanelHopDong.add(timkiemPanel, BorderLayout.NORTH);
		centerPanelHopDong.add(danhsachPanel, BorderLayout.CENTER);
		centerPanelHopDong.add(tongketPanel, BorderLayout.SOUTH);
		
		hopDongPanel.add(centerPanelHopDong, BorderLayout.CENTER);
	}
	
//	Xóa trạng thái text chuột không nằm trong ô
	public void removePlaceHolder(JTextField text) {
		Font font=text.getFont();
		font=font.deriveFont(Font.PLAIN);
		text.setFont(font);
		text.setForeground(Color.BLACK);
	}
	
//	Trạng thái text chuột không nằm trong ô
	public void addPlaceHolder(JTextField text) {
		Font font=text.getFont();
		font=font.deriveFont(Font.ITALIC);
		text.setFont(font);
		text.setForeground(Color.GRAY);
	}
	
	public void addActionListener() {
		btnExcel.addActionListener(this);
		btnTimKiem.addActionListener(this);
		btnLamLai.addActionListener(this);	
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
				var obj=e.getSource();
				if(obj.equals(btnExcel)) {
					ExcelHelper excel = new ExcelHelper();
					excel.exportData(this, tableHopDong, 0);
				}
				else if(obj.equals(btnTimKiem)) {
					String tenNtd = comboBoxNTD.getSelectedItem().toString();
					String tenUV = comboBoxUV.getSelectedItem().toString();
					
					if (tenNtd.equalsIgnoreCase("Chọn nhà tuyển dụng") && tenUV.equalsIgnoreCase("Chọn ứng viên")) return;
					
					if (nhatuyendungDAO.getNhaTuyenDungBy(tenNtd, 1).size() != 0 && ungVienDao.getUngVienBy(tenUV, 1).size() == 0) {
						NhaTuyenDung ntd = nhatuyendungDAO.getNhaTuyenDungBy(tenNtd, 1).get(0);
						hopdong_dao.setListHopDong(hopdong_dao.getHopDongTheoNhaTuyenDung(ntd.getMaNTD()));
						loadDataTable();
					} else if (nhatuyendungDAO.getNhaTuyenDungBy(tenNtd, 1).size() == 0 && ungVienDao.getUngVienBy(tenUV, 1).size() != 0 ) {
						UngVien uv = ungVienDao.getUngVienBy(tenUV, 1).get(0);
						hopdong_dao.setListHopDong(hopdong_dao.getHopDongTheoUngVien(uv.getMaUV()));
						loadDataTable();
					} else if (nhatuyendungDAO.getNhaTuyenDungBy(tenNtd, 1).size() != 0 && ungVienDao.getUngVienBy(tenUV, 1).size() != 0) {
						NhaTuyenDung ntd = nhatuyendungDAO.getNhaTuyenDungBy(tenNtd, 1).get(0);
						UngVien uv = ungVienDao.getUngVienBy(tenUV, 1).get(0);
						hopdong_dao.setListHopDong(hopdong_dao.getHopDongTheoUngVienVaNhaTuyenDung(uv.getMaUV(), ntd.getMaNTD()));
						loadDataTable();
					}
					
				}
				else if(obj.equals(btnLamLai)) {
					comboBoxNTD.setSelectedIndex(0);
					comboBoxUV.setSelectedIndex(0);
					hopdong_dao.setListHopDong(hopdong_dao.getDSHopDong());
					loadDataTable();
				}
	}
	
	public void loadData() {
		hopdong_dao.setListHopDong(hopdong_dao.getDSHopDong());
		nhatuyendungDAO.setListNhatuyenDung(nhatuyendungDAO.getDsNhaTuyenDung());
		ungVienDao.setListUngVien(ungVienDao.getDSUngVien());
		
		comboBoxNTD.addItem("Chọn nhà tuyển dụng");
		for(NhaTuyenDung i: nhatuyendungDAO.getListNhatuyenDung()) {
			comboBoxNTD.addItem(i.getTenNTD());
		}
		
		comboBoxUV.addItem("Chọn ứng viên");
		for(UngVien i: ungVienDao.getListUngVien()) {
			comboBoxUV.addItem(i.getTenUV());
		}
		
		numberLabel.setText(String.valueOf(hopdong_dao.getSoLuongHopDong()));
		valueLabel.setText(String.valueOf(hopdong_dao.getTongGiaTriHopDong()));
	}
	
	public void loadDataTable() {
		modelTableHopDong.setRowCount(0);
		for(HopDong i: hopdong_dao.getListHopDong()) {
			UngVien uv = ungVienDao.getUngVien(i.getUngVien().getMaUV());
			NhaTuyenDung ntd = nhatuyendungDAO.getNhaTuyenDungTheoMaTTD(i.getTinTuyenDung().getMaTTD());
			DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			Object[] obj=new Object[] {
					i.getMaHD(), uv.getTenUV(), uv.getSoDienThoai(), ntd.getTenNTD(), i.getPhiDichVu(), i.getThoiGian().format(formatters)
			};
			modelTableHopDong.addRow(obj);
		}
	}

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
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

	public JPanel getPanel() {
		return this.hopDongPanel;
	}
}
