package view.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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
import javax.swing.JOptionPane;
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

import controller.ExcelHelper;
import controller.LabelDateFormatter;
import dao.HopDong_DAO;
import dao.NhaTuyenDung_DAO;
import dao.UngVien_DAO;
import entity.HopDong;
import entity.NhaTuyenDung;
import entity.NhanVien;
import entity.TinTuyenDung;
import entity.UngVien;
import swing.Button;
import swing.GradientRoundPanel;
import swing.TableCellGradient;

public class ThongKeHopDongFrame extends JFrame implements ActionListener {
	NhanVien userName;
	ThongKeHopDongFrame parent;
	
//	Component thống kê hợp đồng
	JPanel menuPanel, timkiemPanel, tongketPanel, hopDongPanel,centerPanelHopDong, danhsachPanel, danhsachNorthPanel, danhsachCenterPanel;
	JLabel titleHopDong, ngayBatDauLabel, ngayKetThucLabel, timkiemNTDLabel, timkiemUVLabel, summaryValueLabel, summaryNumberLabel, valueLabel, numberLabel;
	Button btnTimKiem, btnLamLai, btnExcel;
	JTable tableHopDong;
	DefaultTableModel modelTableHopDong;
	JScrollPane scrollHopDong;
	UtilDateModel modelBatDau, modelKetThuc;
	JDatePickerImpl ngayBatDau, ngayKetThuc;
	JComboBox<Object> comboBoxNTD, comboBoxUV;
	Icon iconBtnSave;
	
	private HopDong_DAO hopdong_dao;
	private NhaTuyenDung_DAO nhatuyendungDAO;
	private UngVien_DAO ungVienDao;
	
	public ThongKeHopDongFrame(NhanVien userName) {
		this.userName=userName;
		this.parent = this;

//		Tạo component bên phải
		initComponent();
		
//		Thêm sự kiện
		addActionListener();
		
		hopdong_dao = new HopDong_DAO();
		nhatuyendungDAO = new NhaTuyenDung_DAO();
		ungVienDao = new UngVien_DAO();
		
		loadData();
		loadDataTable();
		loadDataTotal();
	}
	
	public JLabel createLabel(String title) {
		JLabel label = new JLabel(title);
		label.setFont(new Font("Segoe UI",1,16));
		label.setForeground(Color.WHITE);
		return label;
	}
	
	public Button createButton(String title, Color bgColor, Color fgColor) {
		Button button = new Button(title); 
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
		centerPanelHopDong.setLayout(new BorderLayout(5,5));
		centerPanelHopDong.setBackground(new Color(89, 145, 144));
//		Thống kê tin tuyển dụng
		timkiemPanel=new GradientRoundPanel();
		timkiemPanel.setLayout(new FlowLayout(FlowLayout.LEFT,19,5));
		
		comboBoxNTD=new JComboBox<Object>(); 
		comboBoxNTD.setFont(new Font("Segoe UI",0,16));
		comboBoxNTD.setOpaque(false);
		comboBoxNTD.setPreferredSize(new Dimension(270,30));
		comboBoxUV=new JComboBox<Object>(); 
		comboBoxUV.setFont(new Font("Segoe UI",0,16));
		comboBoxUV.setOpaque(false);
		
		modelBatDau=new UtilDateModel();
		modelKetThuc=new UtilDateModel();
		Properties p=new Properties();
		p.put("text.day", "Day"); 
		p.put("text.month", "Month"); 
		p.put("text.year", "Year");
		JDatePanelImpl panelDateBatDau=new JDatePanelImpl(modelBatDau, p);
		JDatePanelImpl panelDateKetThuc=new JDatePanelImpl(modelKetThuc, p);
		
		ngayBatDauLabel= createLabel("Ngày bắt đầu:"); 
		ngayBatDau=new JDatePickerImpl(panelDateBatDau, new LabelDateFormatter());
		ngayBatDau.setPreferredSize(new Dimension(130,25));
		
		ngayKetThucLabel= createLabel("Ngày kết thúc:"); 
		ngayKetThuc=new JDatePickerImpl(panelDateKetThuc, new LabelDateFormatter());
		ngayKetThuc.setPreferredSize(new Dimension(130,25));
		
		JPanel resBtnSearch=new JPanel();
		resBtnSearch.setOpaque(false);
		resBtnSearch.setLayout(new FlowLayout(FlowLayout.RIGHT, 7, 5));
		resBtnSearch.setBackground(Color.WHITE);
		
		btnTimKiem= createButton("Thống kê", new Color(0,102,102), Color.WHITE); 
		btnLamLai= createButton("Hủy", Color.RED, Color.WHITE); 
		resBtnSearch.add(btnTimKiem); 
		resBtnSearch.add(btnLamLai);
		
		timkiemPanel.add(comboBoxNTD); timkiemPanel.add(comboBoxUV);
		timkiemPanel.add(ngayBatDauLabel); timkiemPanel.add(ngayBatDau);
		timkiemPanel.add(ngayKetThucLabel); timkiemPanel.add(ngayKetThuc);
		timkiemPanel.add(resBtnSearch);
		
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
		resBtnThem.setBorder(BorderFactory.createEmptyBorder(10,10,0,15));
		resBtnThem.setBackground(Color.WHITE);
		
		btnExcel=new Button("Xuất Excel");
		btnExcel.setIcon(iconBtnSave);
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
		
		tableHopDong.setDefaultRenderer(Object.class, new TableCellGradient());
		tableHopDong.setAutoCreateRowSorter(true);
		ArrayList<RowSorter.SortKey> list = new ArrayList<>();
        DefaultRowSorter sorter = ((DefaultRowSorter)tableHopDong.getRowSorter());
        sorter.setSortsOnUpdates(true);
        list.add( new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sorter.setSortKeys(list);
        sorter.sort();
		scrollHopDong=new JScrollPane(tableHopDong);
		scrollHopDong.setBorder(BorderFactory.createLineBorder(new Color(0,191,165)));
		scrollHopDong.setPreferredSize(new Dimension(1280, 480));
		GradientRoundPanel resScroll=new GradientRoundPanel();
		resScroll.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));
		resScroll.setLayout(new BoxLayout(resScroll, BoxLayout.PAGE_AXIS));
		resScroll.setBackground(Color.WHITE);
		resScroll.add(scrollHopDong);
		danhsachCenterPanel.add(resScroll);
		
		danhsachPanel.add(danhsachNorthPanel, BorderLayout.NORTH);
		danhsachPanel.add(danhsachCenterPanel, BorderLayout.CENTER);
		
//		Tổng số và giá trị hợp đồng
		tongketPanel=new GradientRoundPanel();
		tongketPanel.setLayout(new BorderLayout());
		tongketPanel.setBorder(BorderFactory.createEmptyBorder(0, 17, 0, 0));
		
		JPanel resPanelSummary = new JPanel();
		resPanelSummary.setOpaque(false);
		resPanelSummary.setLayout(new GridLayout(2, 1));
		
		JPanel temp = new JPanel();
		temp.setLayout(new FlowLayout(FlowLayout.RIGHT));
		summaryValueLabel= createLabel("Tổng giá trị hợp đồng:"); 
		summaryValueLabel.setFont(new Font("Segoe UI",1,16));
		valueLabel = createLabel("");
		valueLabel.setFont(new Font("Segoe UI",1,16));
		temp.add(summaryValueLabel);
		temp.add(valueLabel);
		temp.setOpaque(false);
		resPanelSummary.add(temp);
		
		JPanel temp1 = new JPanel();
		temp1.setLayout(new FlowLayout(FlowLayout.LEFT));
		summaryNumberLabel= createLabel("Tổng số lượng hợp đồng:"); 
		summaryNumberLabel.setFont(new Font("Segoe UI",1,16));
		numberLabel = createLabel("");
		numberLabel.setFont(new Font("Segoe UI",1,16));
		temp1.add(summaryNumberLabel);
		temp1.add(numberLabel);
		temp1.setOpaque(false);
		resPanelSummary.add(temp1);
		
		tongketPanel.add(resPanelSummary, BorderLayout.WEST);
		
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
	
	public int getFetchType() {
		String tenNtd = comboBoxNTD.getSelectedItem().toString();
		String tenUV = comboBoxUV.getSelectedItem().toString();
		Object ngayBD = ngayBatDau.getModel().getValue();
		Object ngayKT = ngayKetThuc.getModel().getValue();
		
		if (!tenNtd.equalsIgnoreCase("Chọn nhà tuyển dụng") && !tenUV.equalsIgnoreCase("Chọn ứng viên") && (ngayBD != null && ngayKT != null)) {
			return 7;
		} 
		
		if (!tenNtd.equalsIgnoreCase("Chọn nhà tuyển dụng") && (ngayBD != null && ngayKT != null)) {
			return 6;
		}
		
		if (!tenUV.equalsIgnoreCase("Chọn ứng viên") && (ngayBD != null && ngayKT != null)) {
			return 5;
		}
		
		if (!tenNtd.equalsIgnoreCase("Chọn nhà tuyển dụng") && !tenUV.equalsIgnoreCase("Chọn ứng viên")) {
			return 4;
		}
		
		if (ngayBD != null && ngayKT != null) {
			return 3;
		}
		
		if (!tenNtd.equalsIgnoreCase("Chọn nhà tuyển dụng")) {
			return 2;
		}
		
		if (!tenUV.equalsIgnoreCase("Chọn ứng viên") ) {
			return 1;
		}
		
		return 0;
	}
	
	public void fetchHopDong(String tenNtd, String tenUV) {
			ArrayList<NhaTuyenDung> listNtd = nhatuyendungDAO.getNhaTuyenDungBy(tenNtd, 1);
			ArrayList<UngVien> listUv = ungVienDao.getUngVienBy(tenUV, 1);
		
		if (listNtd.size() != 0 && listUv.size() == 0) {
			NhaTuyenDung ntd = listNtd.get(0);
			hopdong_dao.setListHopDong(hopdong_dao.getHopDongTheoNhaTuyenDung(ntd.getMaNTD()));
			return;
		}
		if (nhatuyendungDAO.getNhaTuyenDungBy(tenNtd, 1).size() == 0 && ungVienDao.getUngVienBy(tenUV, 1).size() != 0 ) {
			UngVien uv = listUv.get(0);
			hopdong_dao.setListHopDong(hopdong_dao.getHopDongTheoUngVien(uv.getMaUV()));
			return;
		}
		if (nhatuyendungDAO.getNhaTuyenDungBy(tenNtd, 1).size() != 0 && ungVienDao.getUngVienBy(tenUV, 1).size() != 0) {
			NhaTuyenDung ntd = listNtd.get(0);
			UngVien uv = listUv.get(0);
			hopdong_dao.setListHopDong(hopdong_dao.getHopDongTheoUngVienVaNhaTuyenDung(uv.getMaUV(), ntd.getMaNTD()));
			return;
		}
	}
	
	public void fetchHopDong(String tenNtd, String tenUV, LocalDate ngayBatDau, LocalDate ngayKetThuc) {
		ArrayList<NhaTuyenDung> listNtd = nhatuyendungDAO.getNhaTuyenDungBy(tenNtd, 1);
		ArrayList<UngVien> listUv = ungVienDao.getUngVienBy(tenUV, 1);
	
		if (listNtd.size() == 0 && listUv.size() == 0) {
			hopdong_dao.setListHopDong(hopdong_dao.getHopDongTheoThoiGian(ngayBatDau, ngayKetThuc));
			return;
		}
		if (listNtd.size() != 0 && listUv.size() == 0) {
			NhaTuyenDung ntd = listNtd.get(0);
			hopdong_dao.setListHopDong(hopdong_dao.getHopDongTheoNhaTuyenDung(ntd.getMaNTD(), ngayBatDau, ngayKetThuc));
			return;
		}
		if (listNtd.size() == 0 && listUv.size() != 0) {
			UngVien uv = listUv.get(0);
			hopdong_dao.setListHopDong(hopdong_dao.getHopDongTheoUngVien(uv.getMaUV(), ngayBatDau, ngayKetThuc));
			return;
		}
		if (listNtd.size() != 0 && listUv.size() != 0) {
			NhaTuyenDung ntd = listNtd.get(0);
			UngVien uv = listUv.get(0);
			hopdong_dao.setListHopDong(hopdong_dao.getHopDongTheoUngVienVaNhaTuyenDung(uv.getMaUV(), ntd.getMaNTD(), ngayBatDau, ngayKetThuc));
			return;
		}
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
					Object ngayBD = ngayBatDau.getModel().getValue();
					Object ngayKT = ngayKetThuc.getModel().getValue();
					
					if (tenNtd.equalsIgnoreCase("Chọn nhà tuyển dụng") && tenUV.equalsIgnoreCase("Chọn ứng viên") && (ngayBD == null && ngayKT == null )) {
						return;
					}
					
					if ((ngayBD == null && ngayKT != null) ||
						(ngayBD != null && ngayKT == null)) {
						JOptionPane.showMessageDialog(this, "Phải chọn cả ngày bắt đầu và ngày kết thúc");
						return;
					}
					
					// 7 fetch data theo nhà tuyển dụng, ứng viên và thời gian
					// 6 fetch data theo nhà tuyển dụng và thời gian
					// 5 fetch data theo ứng viên và thời gian
					// 4 fetch data theo nhà tuyển dụng và ứng viên
					// 3 fetch data theo thời gian
					// 2 fetch data theo nhà tuyển dụng
					// 1 fetch data theo ứng viên
					switch(getFetchType()) {
					case 7:
					case 6:
					case 5:
					case 3:
						SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
						LocalDate ngayBatDau = null;
						LocalDate ngayKetThuc = null;
						try {
							ngayKetThuc = sdf.parse(ngayKT.toString()).toInstant()
							 .atZone(ZoneId.systemDefault())
							 .toLocalDate();
							ngayBatDau = sdf.parse(ngayBD.toString()).toInstant()
	                        .atZone(ZoneId.systemDefault())
	                        .toLocalDate();
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						if (ngayBatDau.isAfter(ngayKetThuc)) {
							JOptionPane.showMessageDialog(this, "Ngày bắt đầu phải trước ngày kết thúc");
							return;
						}
						
						fetchHopDong(tenNtd, tenUV, ngayBatDau, ngayKetThuc);
						break;
					case 4:
					case 2:
					case 1:
						fetchHopDong(tenNtd, tenUV);
						break;
					default:
					}
					
					loadDataTable();
					loadDataTotal();
				}
				else if(obj.equals(btnLamLai)) {
					comboBoxNTD.setSelectedIndex(0);
					comboBoxUV.setSelectedIndex(0);
					hopdong_dao.setListHopDong(hopdong_dao.getDSHopDong());
					modelBatDau.setValue(null);
					modelKetThuc.setValue(null);
					
					loadDataTable();
					loadDataTotal();
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
	}
	
	public void loadDataTotal() {
		DecimalFormat format = new DecimalFormat("#,### VNĐ");
		double totalHopDong = 0;
		for (HopDong hd : hopdong_dao.getListHopDong()) {
			totalHopDong += hd.getPhiDichVu();
		}
		valueLabel.setText(format.format(totalHopDong));
		numberLabel.setText(String.valueOf(hopdong_dao.getListHopDong().size()));
	}
	
	public void loadDataTable() {
		modelTableHopDong.setRowCount(0);
		for(HopDong i: hopdong_dao.getListHopDong()) {
			UngVien uv = ungVienDao.getUngVien(i.getUngVien().getMaUV());
			NhaTuyenDung ntd = nhatuyendungDAO.getNhaTuyenDungTheoMaTTD(i.getTinTuyenDung().getMaTTD());
			DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			DecimalFormat formatLuong = new DecimalFormat("#,### VNĐ");
			Object[] obj=new Object[] {
					i.getMaHD(), uv.getTenUV(), uv.getSoDienThoai(), ntd.getTenNTD(), formatLuong.format(i.getPhiDichVu()), i.getThoiGian().format(formatters)
			};
			modelTableHopDong.addRow(obj);
		}
	}

	public JPanel getPanel() {
		return this.hopDongPanel;
	}
}
