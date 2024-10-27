package view.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
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
import dao.TinTuyenDung_DAO;
import entity.NhaTuyenDung;
import entity.NhanVien;
import entity.TinTuyenDung;
import swing.Button;
import swing.GradientRoundPanel;

public class ThongKeNhaTuyenDungFrame  extends JFrame implements ActionListener {
	NhanVien userName;
	ThongKeNhaTuyenDungFrame parent;
	
//	Component thống kê tin tuyển dụng
	JPanel menuPanel, timkiemPanel,nhaTuyenDungPanel,centerPanelNhaTuyenDung, danhsachPanel, danhsachNorthPanel, danhsachCenterPanel, tongketPanel;
	JLabel titleNhaTuyenDung, ngayBatDauLabel, ngayKetThucLabel, timkiemTenLabel, summaryValueLabel, valueLabel;
	Button btnTimKiem, btnLamLai, btnExcel;
	JTable tableNhaTuyenDung;
	DefaultTableModel modeltableNhaTuyenDung;
	JScrollPane scrollNhaTuyenDung;
	UtilDateModel modelBatDau, modelKetThuc;
	JDatePickerImpl ngayBatDau, ngayKetThuc;
	JComboBox<Object> comboBoxNTD;
	Icon iconBtnSave;
	
	private HopDong_DAO hopDong_DAO;
	private NhaTuyenDung_DAO nhaTuyenDung_DAO;
	
	public ThongKeNhaTuyenDungFrame(NhanVien userName) {
		this.userName=userName;
		this.parent = this;
		
//		Tạo component bên phải
		initComponent();
		
//		Thêm sự kiện
		addActionListener();
		
		hopDong_DAO=new HopDong_DAO();
		nhaTuyenDung_DAO=new NhaTuyenDung_DAO();
		
		loadDataTable();
		loadComboBox();
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
		nhaTuyenDungPanel=new JPanel(); 
		nhaTuyenDungPanel.setLayout(new BorderLayout(5,5));
		
//		Hiển thị Thống kê và danh sách tin tuyển dụng
		centerPanelNhaTuyenDung=new JPanel();
		centerPanelNhaTuyenDung.setLayout(new BorderLayout(10, 10));
		centerPanelNhaTuyenDung.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		centerPanelNhaTuyenDung.setBackground(new Color(89, 145, 144));

//		Thống kê tin tuyển dụng
		timkiemPanel=new GradientRoundPanel();
		timkiemPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 5));
		
		comboBoxNTD=new JComboBox<Object>(); 
		comboBoxNTD.setFont(new Font("Segoe UI",0,16));
//		comboBoxNTD.setPreferredSize(new Dimension(250,30));
		comboBoxNTD.setOpaque(false);
		comboBoxNTD.addItem("Chọn nhà tuyển dụng");
		
		modelBatDau=new UtilDateModel();
		modelKetThuc=new UtilDateModel();
		Properties p=new Properties();
		p.put("text.day", "Day"); 
		p.put("text.month", "Month"); 
		p.put("text.year", "Year");
		JDatePanelImpl panelDate=new JDatePanelImpl(modelBatDau, p);
		
		ngayBatDauLabel= createLabel("Ngày bắt đầu:"); 
		ngayBatDau=new JDatePickerImpl(panelDate, new LabelDateFormatter());
		ngayBatDau.setPreferredSize(new Dimension(130,25));
		
		panelDate=new JDatePanelImpl(modelKetThuc, p);
		ngayKetThucLabel= createLabel("Ngày kết thúc:"); 
		ngayKetThuc=new JDatePickerImpl(panelDate, new LabelDateFormatter());
		ngayKetThuc.setPreferredSize(new Dimension(130,25));
		
		JPanel resBtnSearch=new JPanel();
		resBtnSearch.setOpaque(false);
		resBtnSearch.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 5));
		resBtnSearch.setBackground(Color.WHITE);
		
		btnTimKiem= createButton("Thống kê", new Color(0,102,102), Color.WHITE); 
		btnLamLai= createButton("Hủy", Color.RED, Color.WHITE); 
		resBtnSearch.add(btnTimKiem); 
		resBtnSearch.add(btnLamLai);
		
		timkiemPanel.add(comboBoxNTD);
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
		resBtnThem.setBorder(BorderFactory.createEmptyBorder(10,10,0,20));
		resBtnThem.setBackground(Color.WHITE);
		
		btnExcel=new Button("Xuất Excel");
		btnExcel.setIcon(iconBtnSave);
		btnExcel.setFont(new Font("Segoe UI",0,16));
		btnExcel.setPreferredSize(new Dimension(140,30));
		btnExcel.setBackground(new Color(51,51,255));
		btnExcel.setForeground(Color.WHITE);
		resBtnThem.add(btnExcel);
		titleNhaTuyenDung=new JLabel("Danh sách nhà tuyển dụng");
		titleNhaTuyenDung.setForeground(Color.WHITE);
		titleNhaTuyenDung.setFont(new Font("Segoe UI",1,16));
		titleNhaTuyenDung.setBorder(BorderFactory.createEmptyBorder(10,20,0,10));
		danhsachNorthPanel.add(titleNhaTuyenDung, BorderLayout.WEST);
		danhsachNorthPanel.add(resBtnThem, BorderLayout.EAST);
		
		danhsachCenterPanel=new GradientRoundPanel();
		danhsachCenterPanel.setLayout(new BoxLayout(danhsachCenterPanel, BoxLayout.PAGE_AXIS));
		danhsachCenterPanel.setBackground(Color.WHITE);
		String[] colName= {"Mã nhà tuyển dụng","Tên nhà tuyển dụng","Email","Số điện thoại", "Số lượng tin tuyển dụng"};
		Object[][] data = {
			    {1, "Manual Tester", "Amazon", "5,000,000 VNĐ", "Cao đẳng", "Khả dụng"},
		};
		modeltableNhaTuyenDung= new DefaultTableModel(data, colName){
			boolean[] canEdit = new boolean [] {
	                false, false, false, false, false,
	            };
			
            @Override
            public boolean isCellEditable(int row, int column) {
               return canEdit[column];
            }
        };
		tableNhaTuyenDung=new JTable(modeltableNhaTuyenDung);
		tableNhaTuyenDung.getTableHeader().setFont(new Font("Segoe UI",1,14));
		tableNhaTuyenDung.setFont(new Font("Segoe UI",0,16));
		tableNhaTuyenDung.setRowHeight(30);
		tableNhaTuyenDung.getColumnModel().getColumn(1).setPreferredWidth(200);
		tableNhaTuyenDung.getColumnModel().getColumn(2).setPreferredWidth(150);
		tableNhaTuyenDung.getColumnModel().getColumn(3).setPreferredWidth(150);
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		for(int i=0;i<tableNhaTuyenDung.getColumnCount();i++) {
			tableNhaTuyenDung.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);			
		}
		tableNhaTuyenDung.setAutoCreateRowSorter(true);
		ArrayList<RowSorter.SortKey> list = new ArrayList<>();
        DefaultRowSorter sorter = ((DefaultRowSorter)tableNhaTuyenDung.getRowSorter());
        sorter.setSortsOnUpdates(true);
        list.add( new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sorter.setSortKeys(list);
        sorter.sort();
		scrollNhaTuyenDung=new JScrollPane(tableNhaTuyenDung);
		scrollNhaTuyenDung.setBorder(BorderFactory.createLineBorder(new Color(0,191,165)));
		scrollNhaTuyenDung.setPreferredSize(new Dimension(1280, 480));
		GradientRoundPanel resScroll=new GradientRoundPanel();
		resScroll.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));
		resScroll.setLayout(new BoxLayout(resScroll, BoxLayout.PAGE_AXIS));
		resScroll.setBackground(Color.WHITE);
		resScroll.add(scrollNhaTuyenDung);
		danhsachCenterPanel.add(resScroll);
		
		// tổng số lượng tin tuyển dụng
		tongketPanel=new GradientRoundPanel();
		tongketPanel.setLayout(new BorderLayout());
		tongketPanel.setBorder(BorderFactory.createEmptyBorder(0, 17, 0, 0));
		
		JPanel resPanelSummary = new JPanel();
		resPanelSummary.setOpaque(false);
		resPanelSummary.setLayout(new GridLayout(2, 1));
		
		JPanel temp = new JPanel();
		temp.setLayout(new FlowLayout(FlowLayout.RIGHT));
		summaryValueLabel= createLabel("Tổng số lượng tin tuyển dụng:"); 
		summaryValueLabel.setFont(new Font("Segoe UI",1,16));
		valueLabel = createLabel("");
		valueLabel.setFont(new Font("Segoe UI",1,16));
		temp.add(summaryValueLabel);
		temp.add(valueLabel);
		temp.setOpaque(false);
		resPanelSummary.add(temp);

		tongketPanel.add(resPanelSummary, BorderLayout.WEST);
		
		danhsachPanel.add(danhsachNorthPanel, BorderLayout.NORTH);
		danhsachPanel.add(danhsachCenterPanel, BorderLayout.CENTER);
		
		centerPanelNhaTuyenDung.add(timkiemPanel, BorderLayout.NORTH);
		centerPanelNhaTuyenDung.add(danhsachPanel, BorderLayout.CENTER);
		centerPanelNhaTuyenDung.add(tongketPanel, BorderLayout.SOUTH);
		
		nhaTuyenDungPanel.add(centerPanelNhaTuyenDung, BorderLayout.CENTER);
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
	
	public void loadDataTable() {
		modeltableNhaTuyenDung.setRowCount(0);
		int totalNumber = 0;
		for(Object[] i: hopDong_DAO.thongKeHopDongTheoNTD()) {
			modeltableNhaTuyenDung.addRow(i);
			totalNumber += Integer.valueOf(i[4].toString());
		}
		valueLabel.setText(String.valueOf(totalNumber));
	}
	
	public void loadComboBox() {
		nhaTuyenDung_DAO.setListNhatuyenDung(nhaTuyenDung_DAO.getDsNhaTuyenDung());
		for (NhaTuyenDung ntd: nhaTuyenDung_DAO.getListNhatuyenDung()) {
			comboBoxNTD.addItem(ntd.getTenNTD());			
		}
	}

	public void addActionListener() {
		btnExcel.addActionListener(this);
		btnTimKiem.addActionListener(this);
		btnLamLai.addActionListener(this);
	}
	
	public int getFetchType() {
		String ntd = comboBoxNTD.getSelectedItem().toString();
		Object ngayBD = ngayBatDau.getModel().getValue();
		Object ngayKT = ngayKetThuc.getModel().getValue();
		
		if (!ntd.equalsIgnoreCase("Chọn nhà tuyển dụng") && ngayBD != null && ngayKT != null) {
			return 3;
		}
		
		if (!ntd.equalsIgnoreCase("Chọn nhà tuyển dụng")) {
			return 2;
		}
		
		if (ngayBD != null && ngayKT != null) {
			return 1;
		}
		
		return 0;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		var obj=e.getSource();
		if(obj.equals(btnExcel)) {
			ExcelHelper excel = new ExcelHelper();
			excel.exportData(this, tableNhaTuyenDung, 0);
		}
		else if(obj.equals(btnTimKiem)) {
			String tenNtd = comboBoxNTD.getSelectedItem().toString();
			Object ngayBD = ngayBatDau.getModel().getValue();
			Object ngayKT = ngayKetThuc.getModel().getValue();
			
			if (tenNtd.equalsIgnoreCase("Chọn nhà tuyển dụng") && ngayBD == null && ngayKT == null) return;
			
			if ((ngayBD == null && ngayKT != null) ||
				(ngayBD != null && ngayKT == null)) {
				JOptionPane.showMessageDialog(this, "Phải chọn cả ngày bắt đầu và ngày kết thúc");
				return;
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
			LocalDate ngayBatDau = null;
			LocalDate ngayKetThuc = null;
			
			if (ngayBD != null && ngayKT != null) {
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
			}
			
			// 3 thống kê theo nhà tuyển dụng và thời gian
			// 2 thống kê theo nhà tuyển dụng 
			// 1 thống kê theo thời gian
			int totalNumber = 0;
			modeltableNhaTuyenDung.setRowCount(0);
			switch(getFetchType()) {
			case 3:
				for (Object[] i : hopDong_DAO.thongKeHopDongTheoNTD(tenNtd, ngayBatDau, ngayKetThuc)) {
					modeltableNhaTuyenDung.addRow(i);
					totalNumber += Integer.valueOf(i[4].toString());
				}
				break;
			case 2:
				for (Object[] i : hopDong_DAO.thongKeHopDongTheoNTD(tenNtd)) {
					modeltableNhaTuyenDung.addRow(i);
					totalNumber += Integer.valueOf(i[4].toString());
				}
				break;
			case 1:
				for (Object[] i : hopDong_DAO.thongKeHopDongTheoNTD(ngayBatDau, ngayKetThuc)) {
					modeltableNhaTuyenDung.addRow(i);
					totalNumber += Integer.valueOf(i[4].toString());
				}
				break;
			default:
				break;
			}
			valueLabel.setText(String.valueOf(totalNumber));
			
		}
		else if(obj.equals(btnLamLai)) {
			comboBoxNTD.setSelectedIndex(0);
			modelBatDau.setValue(new Date());
			modelKetThuc.setValue(new Date());
			loadDataTable();
		}
	}

	public JPanel getPanel() {
		return this.nhaTuyenDungPanel;
	}
}
