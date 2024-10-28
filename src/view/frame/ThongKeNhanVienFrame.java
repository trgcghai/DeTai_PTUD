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
import dao.NhanVien_DAO;
import dao.UngVien_DAO;
import entity.HopDong;
import entity.NhanVien;
import entity.UngVien;
import entity.constraint.GioiTinh;
import swing.Button;
import swing.GradientRoundPanel;
import view.dialog.DanhSachHopDongDialog;

public class ThongKeNhanVienFrame  extends JFrame implements ActionListener, MouseListener{
	NhanVien userName;
	ThongKeNhanVienFrame parent;
	
//	Component thống kê ứng viên
	JPanel  NhanVienPanel, centerPanelNhanVien, northPanelThongKe;
	JLabel titleUngVien, ngayBatDauLabel, ngayKetThucLabel, 
		numberLabel, summaryNumberLabel, valueLabel, summaryValueLabel;
	JComboBox<String> gioitinhText, nhanVienText;
	Button btnTimKiem, btnLamLai, btnExcel, btnHuy;
	JTable tableNhanVien;
	DefaultTableModel modelTableNhanVien;
	JScrollPane scrollNhanVien;
	UtilDateModel modelBatDau, modelKetThuc;
	JDatePickerImpl ngayBatDau, ngayKetThuc;
	Icon iconBtnSave;
	
	GradientRoundPanel tongketPanel, menuPanel, timkiemPanel,
		danhsachPanel, danhsachNorthPanel, danhsachCenterPanel;
	
	private NhanVien_DAO nhanvienDAO;
	private HopDong_DAO hopdongDAO;
	
	public ThongKeNhanVienFrame(NhanVien userName) {
		this.userName = userName;
		this.parent = this;

//		Tạo component bên phải
		initComponent();
		
//		Thêm sự kiện
		addActionListener();
		addMousListener();
		addTableListener();
		
		nhanvienDAO=new NhanVien_DAO();
		hopdongDAO=new HopDong_DAO();
		
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
		NhanVienPanel=new JPanel(); 
		NhanVienPanel.setLayout(new BorderLayout(5,5));
		
//		Hiển thị tìm kiếm và thống kê nhân viên 
		centerPanelNhanVien=new JPanel();
		centerPanelNhanVien.setLayout(new BorderLayout(5,5));
		centerPanelNhanVien.setBackground(new Color(89, 145, 144));
		
//		Tìm kiếm nhân viên
		timkiemPanel=new GradientRoundPanel();
		timkiemPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 19, 5));
		
		nhanVienText=new JComboBox<String>(); 
		nhanVienText.setFont(new Font("Segoe UI",0,16));
		nhanVienText.addItem("Chọn nhân viên");
		gioitinhText=new JComboBox<String>(); 
		gioitinhText.setFont(new Font("Segoe UI",0,16));
		gioitinhText.addItem("Chọn giới tính");
		for(GioiTinh g: GioiTinh.class.getEnumConstants()) {
			gioitinhText.addItem(g.getValue());
		}
		
		modelBatDau=new UtilDateModel();
		modelKetThuc=new UtilDateModel();
		Properties p=new Properties();
		p.put("text.day", "Day"); 
		p.put("text.month", "Month"); 
		p.put("text.year", "Year");
		JDatePanelImpl panelDate=new JDatePanelImpl(modelBatDau, p);
		
		ngayBatDauLabel= createLabel("Ngày lập hợp đồng:"); 
		ngayBatDau=new JDatePickerImpl(panelDate, new LabelDateFormatter());
		ngayBatDau.setPreferredSize(new Dimension(150,25));

		panelDate=new JDatePanelImpl(modelKetThuc, p);
		ngayKetThucLabel= createLabel("-"); 
		ngayKetThuc=new JDatePickerImpl(panelDate, new LabelDateFormatter());
		ngayKetThuc.setPreferredSize(new Dimension(150,25));
		
		JPanel resBtnSearch=new JPanel();
		resBtnSearch.setOpaque(false);
		resBtnSearch.setPreferredSize(new Dimension(433, 35));
		resBtnSearch.setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 5));
		btnTimKiem= createButton("Thống kê", new Color(0,102,102), Color.WHITE); 
		btnLamLai= createButton("Hủy", Color.RED, Color.WHITE); 
		resBtnSearch.add(btnTimKiem); 
		resBtnSearch.add(btnLamLai);
		
		timkiemPanel.add(nhanVienText);
		timkiemPanel.add(gioitinhText);
		timkiemPanel.add(ngayBatDauLabel); timkiemPanel.add(ngayBatDau);
		timkiemPanel.add(ngayKetThucLabel); timkiemPanel.add(ngayKetThuc);
		timkiemPanel.add(resBtnSearch);
		
//		Danh sách ứng viên
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
		titleUngVien=new JLabel("Danh sách nhân viên");
		titleUngVien.setForeground(Color.WHITE);
		titleUngVien.setFont(new Font("Segoe UI",1,16));
		titleUngVien.setBorder(BorderFactory.createEmptyBorder(10,20,0,10));
		danhsachNorthPanel.add(titleUngVien, BorderLayout.WEST);
		danhsachNorthPanel.add(resBtnThem, BorderLayout.EAST);
		
		danhsachCenterPanel=new GradientRoundPanel();
		danhsachCenterPanel.setLayout(new BoxLayout(danhsachCenterPanel, BoxLayout.PAGE_AXIS));
		danhsachCenterPanel.setBackground(Color.WHITE);
		String[] colName= {"Mã nhân viên","Tên nhân viên","Số điện thoại","Giới tính", "Ngày sinh", 
				"Số hợp đồng", "Tổng giá trị hợp đồng","Xem hợp đồng"};
		Object[][] data = {
			    {1, "Nguyễn Thắng Minh Đạt", "0123456789", "Nam",
			    	"13-12-2003","10","1000000",null},
			};
		modelTableNhanVien= new DefaultTableModel(data, colName){
		boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, true
            };
		
        @Override
        public boolean isCellEditable(int row, int column) {
           return canEdit[column];
        }};
		tableNhanVien=new JTable(modelTableNhanVien);
		tableNhanVien.getTableHeader().setFont(new Font("Segoe UI",1,14));
		tableNhanVien.setFont(new Font("Segoe UI",0,16));
		tableNhanVien.setRowHeight(30);
		tableNhanVien.getColumnModel().getColumn(1).setPreferredWidth(200);
		tableNhanVien.getColumnModel().getColumn(3).setPreferredWidth(150);
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		for(int i=0;i<tableNhanVien.getColumnCount();i++) {
			tableNhanVien.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);			
		}
		tableNhanVien.setAutoCreateRowSorter(true);
		ArrayList<RowSorter.SortKey> list = new ArrayList<>();
        DefaultRowSorter sorter = ((DefaultRowSorter)tableNhanVien.getRowSorter());
        sorter.setSortsOnUpdates(true);
        list.add( new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sorter.setSortKeys(list);
        sorter.sort();
		scrollNhanVien=new JScrollPane(tableNhanVien);
		scrollNhanVien.setBorder(BorderFactory.createLineBorder(new Color(0,191,165)));
		scrollNhanVien.setPreferredSize(new Dimension(1280, 480));
		GradientRoundPanel resScroll=new GradientRoundPanel();
		resScroll.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));
		resScroll.setLayout(new BoxLayout(resScroll, BoxLayout.PAGE_AXIS));
		resScroll.setBackground(Color.WHITE);
		resScroll.add(scrollNhanVien);
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
		summaryNumberLabel= createLabel("Tổng số lượng nhân viên:"); 
		summaryNumberLabel.setFont(new Font("Segoe UI",1,16));
		numberLabel = createLabel("");
		numberLabel.setFont(new Font("Segoe UI",1,16));
		temp1.add(summaryNumberLabel);
		temp1.add(numberLabel);
		temp1.setOpaque(false);
		resPanelSummary.add(temp1);
		
		tongketPanel.add(resPanelSummary, BorderLayout.WEST);
		
		centerPanelNhanVien.add(timkiemPanel, BorderLayout.NORTH);
		centerPanelNhanVien.add(danhsachPanel, BorderLayout.CENTER);
		centerPanelNhanVien.add(tongketPanel, BorderLayout.SOUTH);
		
		NhanVienPanel.add(centerPanelNhanVien, BorderLayout.CENTER);
	}
	
//	Load dữ liệu lên bảng
	public void loadDataTable() {
		DecimalFormat df = new DecimalFormat("#,###");
		double totalValue = 0;
		int countTotal = 0;
		modelTableNhanVien.setRowCount(0);
		for(Object[] i: hopdongDAO.thongKeHopDongTheoNhanVien()) {
			modelTableNhanVien.addRow(new Object[] {
					i[0], i[1], i[2], i[3], i[4], 
					i[5], df.format(i[6])+" VNĐ",null
			});

			countTotal++;
			totalValue += Double.valueOf(i[6].toString());
		}

		valueLabel.setText(df.format(totalValue)+" VNĐ");
		numberLabel.setText(String.valueOf(countTotal));
	}
	
	public void loadComboBox() {
		nhanvienDAO.setListNhanVien(nhanvienDAO.getDSNhanVien());
		for(NhanVien nv : nhanvienDAO.getListNhanVien()) {
			nhanVienText.addItem(nv.getTenNV());
		}
	}
	
	public void addActionListener() {
		btnExcel.addActionListener(this);
		btnTimKiem.addActionListener(this);
		btnLamLai.addActionListener(this);
	}
	
	public void addMousListener() {
		tableNhanVien.addMouseListener(this);
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
				NhanVien nv=nhanvienDAO.getNhanVien(tableNhanVien.getValueAt(row, 0).toString());
				SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
				
				new DanhSachHopDongDialog(parent, rootPaneCheckingEnabled, nv, 
					modelBatDau.getValue()!=null?LocalDate.parse(format.format(modelBatDau.getValue())):null, 
					modelKetThuc.getValue()!=null?LocalDate.parse(format.format(modelKetThuc.getValue())):null).setVisible(true);;
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
		tableNhanVien.getColumnModel().getColumn(7).setCellRenderer(new TableCellRendererDetail());
		tableNhanVien.getColumnModel().getColumn(7).setCellEditor(new TableCellEditorDetail(event));
	}
	
	public int getFetchType() {
		Object ngayBD = ngayBatDau.getModel().getValue();
		Object ngayKT = ngayKetThuc.getModel().getValue();
		String gioiTinh = gioitinhText.getSelectedItem().toString();
		String nhanVien = nhanVienText.getSelectedItem().toString();
		
		if (!nhanVien.equalsIgnoreCase("Chọn nhân viên") && !gioiTinh.equalsIgnoreCase("Chọn giới tính") && (ngayBD != null && ngayKT != null)) {
			return 7;
		}
		
		if (!nhanVien.equalsIgnoreCase("Chọn nhân viên") && !gioiTinh.equalsIgnoreCase("Chọn giới tính")) {
			return 6;
		}
		
		if (!nhanVien.equalsIgnoreCase("Chọn nhân viên") && (ngayBD != null && ngayKT != null)) {
			return 5;
		}
		
		if (!gioiTinh.equalsIgnoreCase("Chọn giới tính") && (ngayBD != null && ngayKT != null)) {
			return 4;
		}

		if (ngayBD != null && ngayKT != null) {
			return 3;
		}
		
		if (!gioiTinh.equalsIgnoreCase("Chọn giới tính")) {
			return 2;
		}
		
		if (!gioiTinh.equalsIgnoreCase("Chọn nhân viên")) {
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
			excel.exportData(this, tableNhanVien, 0);
		}
		else if(obj.equals(btnTimKiem)) {
			DecimalFormat df = new DecimalFormat("#,###");
			Object ngayBD = ngayBatDau.getModel().getValue();
			Object ngayKT = ngayKetThuc.getModel().getValue();
			String gioiTinh = gioitinhText.getSelectedItem().toString();
			String nhanVien = nhanVienText.getSelectedItem().toString();
			
			if (gioiTinh.equalsIgnoreCase("Chọn giới tính") && nhanVien.equalsIgnoreCase("Chọn nhân viên") && (ngayBD == null && ngayKT == null )) {
				return;
			}
			
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

			double totalValue = 0;
			int countTotal = 0;
			modelTableNhanVien.setRowCount(0);
			
			// 7 thống kê theo nhân viên, giới tính và thời gian
			// 6 thống kê theo nhân viên và giới tính
			// 5 thống kê theo nhân viên và thời gian
			// 4 thống kê theo giới tính và thời gian
			// 3 thống kê theo thời gian
			// 2 thống kê theo giới tính
			// 1 thống kê theo nhân viên
			switch(getFetchType()) {
			case 7: 
				for(Object[] i: hopdongDAO.thongKeHopDongTheoNhanVien(nhanVien, gioiTinh, ngayBatDau, ngayKetThuc)) {
					modelTableNhanVien.addRow(new Object[] {
							i[0], i[1], i[2], i[3], i[4], 
							i[5], df.format(i[6])+" VNĐ"
					});
					countTotal++;
					totalValue += Double.valueOf(i[6].toString());
				}
				break;
			case 6:
				for(Object[] i: hopdongDAO.thongKeHopDongTheoNhanVien(nhanVien, gioiTinh)) {
					modelTableNhanVien.addRow(new Object[] {
							i[0], i[1], i[2], i[3], i[4], 
							i[5], df.format(i[6])+" VNĐ"
					});
					countTotal++;
					totalValue += Double.valueOf(i[6].toString());
				}
				break;
			case 5:
				for(Object[] i: hopdongDAO.thongKeHopDongTheoTenNhanVien(nhanVien, ngayBatDau, ngayKetThuc)) {
					modelTableNhanVien.addRow(new Object[] {
							i[0], i[1], i[2], i[3], i[4], 
							i[5], df.format(i[6])+" VNĐ"
					});
					countTotal++;
					totalValue += Double.valueOf(i[6].toString());
				}
				break;
			case 4:
				for(Object[] i: hopdongDAO.thongKeHopDongTheoNhanVien(gioiTinh, ngayBatDau, ngayKetThuc)) {
					modelTableNhanVien.addRow(new Object[] {
							i[0], i[1], i[2], i[3], i[4], 
							i[5], df.format(i[6])+" VNĐ"
					});
					countTotal++;
					totalValue += Double.valueOf(i[6].toString());
				}
				break;
			case 3:
				for(Object[] i: hopdongDAO.thongKeHopDongTheoNhanVien(ngayBatDau, ngayKetThuc)) {
					modelTableNhanVien.addRow(new Object[] {
							i[0], i[1], i[2], i[3], i[4], 
							i[5], df.format(i[6])+" VNĐ"
					});
					countTotal++;
					totalValue += Double.valueOf(i[6].toString());
				}
				break;
			case 2:
				for(Object[] i: hopdongDAO.thongKeHopDongTheoNhanVien(gioiTinh)) {
					modelTableNhanVien.addRow(new Object[] {
							i[0], i[1], i[2], i[3], i[4], 
							i[5], df.format(i[6])+" VNĐ"
					});
					countTotal++;
					totalValue += Double.valueOf(i[6].toString());
				}
				break;
			case 1:
				for(Object[] i: hopdongDAO.thongKeHopDongTheoTenNhanVien(nhanVien)) {
					modelTableNhanVien.addRow(new Object[] {
							i[0], i[1], i[2], i[3], i[4], 
							i[5], df.format(i[6])+" VNĐ"
					});
					countTotal++;
					totalValue += Double.valueOf(i[6].toString());
				}
				break;
			default:
				return;
			}
			valueLabel.setText(df.format(totalValue)+" VNĐ");
			numberLabel.setText(String.valueOf(countTotal));
			
		}
		else if(obj.equals(btnLamLai)) {
			nhanvienDAO.setListNhanVien((nhanvienDAO.getDSNhanVien()));
			modelBatDau.setValue(null);
			modelKetThuc.setValue(null);
			gioitinhText.setSelectedIndex(0);
			nhanVienText.setSelectedIndex(0);
			loadDataTable();
		}
	}

	public JPanel getPanel() {
		return this.NhanVienPanel;
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
