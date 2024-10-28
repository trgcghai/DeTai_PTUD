package view.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import controller.actiontable.TableActionEvent;
import controller.actiontable.TableCellEditorDetail;
import controller.actiontable.TableCellRendererDetail;
import dao.HopDong_DAO;
import dao.NhaTuyenDung_DAO;
import dao.TinTuyenDung_DAO;
import entity.NhaTuyenDung;
import entity.NhanVien;
import entity.TinTuyenDung;
import swing.Button;
import swing.GradientRoundPanel;
import view.dialog.ChiTietTinTuyenDungDialog;
import view.dialog.DanhSachHoSoDaNopDialog;

public class ThongKeTinTuyenDungFrame  extends JFrame implements ActionListener, MouseListener {
	NhanVien userName;
	ThongKeTinTuyenDungFrame parent;
	
//	Component thống kê tin tuyển dụng
	JPanel menuPanel, timkiemPanel,tinTuyenDungPanel,centerPanelTinTuyenDung, danhsachPanel, danhsachNorthPanel, danhsachCenterPanel, tongketPanel;
	JLabel titleTinTuyenDung, ngayBatDauLabel, ngayKetThucLabel, timkiemTenLabel, 
	summaryValueLabel, valueLabel,summaryValueHireLabel, valueHireLabel, summaryValueDoneLabel, valueDoneLabel;
	Button btnTimKiem, btnLamLai, btnExcel;
	JTable tableTinTuyenDung;
	DefaultTableModel modeltableTinTuyenDung;
	JScrollPane scrollTinTuyenDung;
	UtilDateModel modelBatDau, modelKetThuc;
	JDatePickerImpl ngayBatDau, ngayKetThuc;
	JComboBox<Object> comboBoxNTD;
	Icon iconBtnSave;
	
	private HopDong_DAO hopDong_DAO;
	private NhaTuyenDung_DAO nhatuyendung_DAO;
	private TinTuyenDung_DAO tintuyendung_DAO;
	
	public ThongKeTinTuyenDungFrame(NhanVien userName) {
		this.userName=userName;
		this.parent = this;
		
		hopDong_DAO=new HopDong_DAO();
		nhatuyendung_DAO=new NhaTuyenDung_DAO();
		tintuyendung_DAO=new TinTuyenDung_DAO();
		
//		Tạo component bên phải
		initComponent();
		
//		Thêm sự kiện
		addTableListener();
		addActionListener();
		addMouseListener();
		
		loadData();
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
		tinTuyenDungPanel=new JPanel(); 
		tinTuyenDungPanel.setLayout(new BorderLayout(5,5));
		
//		Hiển thị Thống kê và danh sách tin tuyển dụng
		centerPanelTinTuyenDung=new JPanel();
		centerPanelTinTuyenDung.setLayout(new BorderLayout(5, 5));
		centerPanelTinTuyenDung.setBackground(new Color(89, 145, 144));

//		Thống kê tin tuyển dụng
		timkiemPanel=new GradientRoundPanel();
		timkiemPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 19, 5));
		
		comboBoxNTD=new JComboBox<Object>(); 
		comboBoxNTD.setFont(new Font("Segoe UI",0,16));
		comboBoxNTD.setOpaque(false);
		comboBoxNTD.addItem("Chọn nhà tuyển dụng");
		
		JPanel res=new JPanel();
		res.setOpaque(false);
		modelBatDau=new UtilDateModel();
		modelKetThuc=new UtilDateModel();
		Properties p=new Properties();
		p.put("text.day", "Day"); 
		p.put("text.month", "Month"); 
		p.put("text.year", "Year");
		JDatePanelImpl panelDate=new JDatePanelImpl(modelBatDau, p);
		ngayBatDauLabel= createLabel("Ngày đăng tin:"); 
		ngayBatDau=new JDatePickerImpl(panelDate, new LabelDateFormatter());
		ngayBatDau.setPreferredSize(new Dimension(130,25));
		panelDate=new JDatePanelImpl(modelKetThuc, p);
		ngayKetThucLabel= createLabel("-"); 
		ngayKetThuc=new JDatePickerImpl(panelDate, new LabelDateFormatter());
		ngayKetThuc.setPreferredSize(new Dimension(130,25));
		
		res.add(ngayBatDauLabel); res.add(ngayBatDau);
		res.add(ngayKetThucLabel); res.add(ngayKetThuc);
		
		JPanel resBtnSearch=new JPanel();
		resBtnSearch.setOpaque(false);
		resBtnSearch.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 5));
		resBtnSearch.setPreferredSize(new Dimension(400, 30));
		resBtnSearch.setBackground(Color.WHITE);
		
		btnTimKiem= createButton("Thống kê", new Color(0,102,102), Color.WHITE); 
		btnLamLai= createButton("Hủy", Color.RED, Color.WHITE); 
		resBtnSearch.add(btnTimKiem); 
		resBtnSearch.add(btnLamLai);
		
		timkiemPanel.add(comboBoxNTD, BorderLayout.WEST);
		timkiemPanel.add(res, BorderLayout.CENTER);
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
		resBtnThem.setBorder(BorderFactory.createEmptyBorder(10,10,0,15));
		resBtnThem.setBackground(Color.WHITE);
		
		btnExcel=new Button("Xuất Excel");
		btnExcel.setIcon(iconBtnSave);
		btnExcel.setFont(new Font("Segoe UI",0,16));
		btnExcel.setPreferredSize(new Dimension(140,30));
		btnExcel.setBackground(new Color(51,51,255));
		btnExcel.setForeground(Color.WHITE);
		resBtnThem.add(btnExcel);
		titleTinTuyenDung=new JLabel("Danh sách tin tuyển dụng");
		titleTinTuyenDung.setForeground(Color.WHITE);
		titleTinTuyenDung.setFont(new Font("Segoe UI",1,16));
		titleTinTuyenDung.setBorder(BorderFactory.createEmptyBorder(10,20,0,10));
		danhsachNorthPanel.add(titleTinTuyenDung, BorderLayout.WEST);
		danhsachNorthPanel.add(resBtnThem, BorderLayout.EAST);
		
		danhsachCenterPanel=new GradientRoundPanel();
		danhsachCenterPanel.setLayout(new BoxLayout(danhsachCenterPanel, BoxLayout.PAGE_AXIS));
		danhsachCenterPanel.setBackground(Color.WHITE);
		String[] colName= {"Mã", "Tiêu đề", "Nhà tuyển dụng","Hình thức làm việc", 
				"Trình độ","Ngày đăng tin","Số lượng tuyển","Số lượng đã tuyển","Xem hồ sơ"};
		Object[][] data = {
			    {1, "Manual Tester", "Amazon", "Full-time", "Đại học",
			    	"13-12-2024",10,5,null},
		};
		modeltableTinTuyenDung= new DefaultTableModel(data, colName){
			boolean[] canEdit = new boolean [] {
	                false, false, false, false, 
	                false, false, false, false, true
	            };
			
            @Override
            public boolean isCellEditable(int row, int column) {
               return canEdit[column];
            }
        };
		tableTinTuyenDung=new JTable(modeltableTinTuyenDung);
		tableTinTuyenDung.getTableHeader().setFont(new Font("Segoe UI",1,14));
		tableTinTuyenDung.setFont(new Font("Segoe UI",0,16));
		tableTinTuyenDung.setRowHeight(30);
		tableTinTuyenDung.getColumnModel().getColumn(1).setPreferredWidth(200);
		tableTinTuyenDung.getColumnModel().getColumn(2).setPreferredWidth(150);
		tableTinTuyenDung.getColumnModel().getColumn(3).setPreferredWidth(150);
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		for(int i=0;i<tableTinTuyenDung.getColumnCount()-1;i++) {
			tableTinTuyenDung.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);			
		}
		tableTinTuyenDung.setAutoCreateRowSorter(true);
		ArrayList<RowSorter.SortKey> list = new ArrayList<>();
        DefaultRowSorter sorter = ((DefaultRowSorter)tableTinTuyenDung.getRowSorter());
        sorter.setSortsOnUpdates(true);
        list.add( new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sorter.setSortKeys(list);
        sorter.sort();
		scrollTinTuyenDung=new JScrollPane(tableTinTuyenDung);
		scrollTinTuyenDung.setBorder(BorderFactory.createLineBorder(new Color(0,191,165)));
		scrollTinTuyenDung.setPreferredSize(new Dimension(1280, 480));
		GradientRoundPanel resScroll=new GradientRoundPanel();
		resScroll.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));
		resScroll.setLayout(new BoxLayout(resScroll, BoxLayout.PAGE_AXIS));
		resScroll.setBackground(Color.WHITE);
		resScroll.add(scrollTinTuyenDung);
		danhsachCenterPanel.add(resScroll);
		
		// tổng số lượng 
		tongketPanel=new GradientRoundPanel();
		tongketPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		tongketPanel.setBorder(BorderFactory.createEmptyBorder(0, 17, 0, 0));
		
		JPanel resPanelSummary = new JPanel();
		resPanelSummary.setOpaque(false);
		resPanelSummary.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 0));
		
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
		
		JPanel temp1 = new JPanel();
		temp1.setLayout(new FlowLayout(FlowLayout.RIGHT));
		summaryValueHireLabel= createLabel("Tổng số lượng cần tuyển:"); 
		summaryValueHireLabel.setFont(new Font("Segoe UI",1,16));
		valueHireLabel = createLabel("");
		valueHireLabel.setFont(new Font("Segoe UI",1,16));
		temp1.add(summaryValueHireLabel);
		temp1.add(valueHireLabel);
		temp1.setOpaque(false);
		resPanelSummary.add(temp1);
		
		JPanel temp2 = new JPanel();
		temp2.setLayout(new FlowLayout(FlowLayout.RIGHT));
		summaryValueDoneLabel= createLabel("Tổng số lượng đã tuyển:"); 
		summaryValueDoneLabel.setFont(new Font("Segoe UI",1,16));
		valueDoneLabel = createLabel("");
		valueDoneLabel.setFont(new Font("Segoe UI",1,16));
		temp2.add(summaryValueDoneLabel);
		temp2.add(valueDoneLabel);
		temp2.setOpaque(false);
		resPanelSummary.add(temp2);

		tongketPanel.add(resPanelSummary, BorderLayout.WEST);
		
		danhsachPanel.add(danhsachNorthPanel, BorderLayout.NORTH);
		danhsachPanel.add(danhsachCenterPanel, BorderLayout.CENTER);
		
		centerPanelTinTuyenDung.add(timkiemPanel, BorderLayout.NORTH);
		centerPanelTinTuyenDung.add(danhsachPanel, BorderLayout.CENTER);
		centerPanelTinTuyenDung.add(tongketPanel, BorderLayout.SOUTH);
		
		tinTuyenDungPanel.add(centerPanelTinTuyenDung, BorderLayout.CENTER);
	}
	
	public void loadData() {
		tintuyendung_DAO.setListTinTuyenDung(tintuyendung_DAO.getDsTinTuyenDung());
		nhatuyendung_DAO.setListNhatuyenDung(nhatuyendung_DAO.getDsNhaTuyenDung());
		hopDong_DAO.setListHopDong(hopDong_DAO.getDSHopDong());
	}
	
	public void loadDataTable() {
		modeltableTinTuyenDung.setRowCount(0);
		DateTimeFormatter format=DateTimeFormatter.ofPattern("dd-MM-yyyy");
		int totalHire=0;
		int totalDone=0;
		
		for(TinTuyenDung i: tintuyendung_DAO.getListTinTuyenDung()) {
			totalHire+=i.getSoLuong();
			totalDone+=hopDong_DAO.getSoLuongHopDongTheoTTD(i.getMaTTD());
			
			Object[] row=new Object[] {
				i.getMaTTD(), i.getTieuDe(), nhatuyendung_DAO.getNhaTuyenDung(i.getNhaTuyenDung().getMaNTD()).getTenNTD(),
				i.getHinhThuc().getValue(), i.getTrinhDo().getValue(), format.format(i.getNgayDangTin()),
				i.getSoLuong()+hopDong_DAO.getSoLuongHopDongTheoTTD(i.getMaTTD()),
				hopDong_DAO.getSoLuongHopDongTheoTTD(i.getMaTTD()), null
			};
			modeltableTinTuyenDung.addRow(row);
		}
		
		valueLabel.setText(String.valueOf(tintuyendung_DAO.getListTinTuyenDung().size()));
		valueHireLabel.setText(String.valueOf(totalHire));
		valueDoneLabel.setText(String.valueOf(totalDone));
	}
	
	public void loadComboBox() {
		nhatuyendung_DAO.setListNhatuyenDung(nhatuyendung_DAO.getDsNhaTuyenDung());
		for (NhaTuyenDung ntd: nhatuyendung_DAO.getListNhatuyenDung()) {
			comboBoxNTD.addItem(ntd.getTenNTD());			
		}
	}

	public void addActionListener() {
		btnExcel.addActionListener(this);
		btnTimKiem.addActionListener(this);
		btnLamLai.addActionListener(this);
	}
	
	public void addMouseListener() {
		tableTinTuyenDung.addMouseListener(this);
	}
	
	public void addTableListener() {
		TableActionEvent event=new TableActionEvent() {
			
			@Override
			public void onViewTinTuyenDung(int row) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onViewHoSo(int row) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onViewDetail(int row) {
				// TODO Auto-generated method stub
				String maTTD=tableTinTuyenDung.getValueAt(row, 0).toString();
				TinTuyenDung ttd=tintuyendung_DAO.getTinTuyenDung(maTTD);
				
				new DanhSachHoSoDaNopDialog(parent, rootPaneCheckingEnabled, ttd).setVisible(true);;
			}
			
			@Override
			public void onUpdate(int row) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onDelete(int row) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onCreateTinTuyenDung(int row) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onCreateTaiKhoan(int row) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onCreateHoSo(int row) {
				// TODO Auto-generated method stub
				
			}
		};
		
		tableTinTuyenDung.getColumnModel().getColumn(8).setCellRenderer(new TableCellRendererDetail());
		tableTinTuyenDung.getColumnModel().getColumn(8).setCellEditor(new TableCellEditorDetail(event));
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
			excel.exportData(this, tableTinTuyenDung, 1);
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
			modeltableTinTuyenDung.setRowCount(0);
			switch(getFetchType()) {
			case 3:
				tintuyendung_DAO.setListTinTuyenDung(tintuyendung_DAO.thongKeTheoNTD(tenNtd, ngayBatDau, ngayKetThuc));
				loadDataTable();
				break;
			case 2:
				tintuyendung_DAO.setListTinTuyenDung(tintuyendung_DAO.thongKeTheoNTD(tenNtd));
				loadDataTable();
				break;
			case 1:
				tintuyendung_DAO.setListTinTuyenDung(tintuyendung_DAO.thongKeTheoNTD(ngayBatDau, ngayKetThuc));
				loadDataTable();
				break;
			default:
				break;
			}
			
		}
		else if(obj.equals(btnLamLai)) {
			comboBoxNTD.setSelectedIndex(0);
			modelBatDau.setValue(null);
			modelKetThuc.setValue(null);
			loadData();
			loadDataTable();
		}
	}

	public JPanel getPanel() {
		return this.tinTuyenDungPanel;
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
}
