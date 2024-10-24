package view;

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
import java.util.regex.Matcher;
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
import controller.actiontable.TableActionEvent;
import controller.actiontable.TableCellEditorDetail;
import controller.actiontable.TableCellEditorUpdateDelete;
import controller.actiontable.TableCellEditorViewCreateHoSo;
import controller.actiontable.TableCellRendererDetail;
import controller.actiontable.TableCellRendererUpdateDelete;
import controller.actiontable.TableCellRendererViewCreateHoSo;
import dao.TaiKhoan_DAO;
import dao.TinTuyenDung_DAO;
import dao.UngVien_DAO;
import dao.HoSo_DAO;
import dao.HopDong_DAO;
import dao.NhaTuyenDung_DAO;
import dao.NhanVien_DAO;
import entity.TaiKhoan;
import entity.TinTuyenDung;
import entity.UngVien;
import entity.constraint.HinhThucLamViec;
import entity.constraint.NganhNghe;
import entity.constraint.TrangThai;
import entity.constraint.TrinhDo;
import entity.HoSo;
import entity.HopDong;
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
import swing.GradientRoundPanel;
import swing.RoundPanel;

public class HopDongFrame extends JFrame implements ActionListener, MouseListener, FocusListener {
	
	NhanVien userName;
	HopDongFrame parent;
	
//	Component hợp đồng
	JPanel leftPanel,menuPanel,
		timviecPanel, centerPanelTimViec, northPanelTimViec;
	JLabel titleTinTuyenDung, titleHoSo, titleHopDong, nhatuyendungLabel, ungvienLabel,
		ngaybatdauLabel, ngayketthucLabel;
	JTable tableTinTuyenDung, tableHoSo, tableHopDong;
	DefaultTableModel modelTableTinTuyenDung, modelTableHoSo, modelTableHopDong;
	JScrollPane scrollTinTuyenDung, scrollHoSo, scrollHopDong;
	JComboBox nhatuyendungCombo, ungvienCombo;
	UtilDateModel modelDateBatDau, modelDateKetThuc;
	JDatePickerImpl batdauText, ketthucText;
	Button btnHuy, btnTimkiem;
	
	GradientRoundPanel danhsachTTDPanel, danhsachTTDNorthPanel, danhsachTTDCenterPanel,
				danhsachHopDongPanel, danhsachHopDongNorthPanel, danhsachHopDongCenterPanel,
				danhsachHoSoPanel, danhsachHoSoNorthPanel, danhsachHoSoCenterPanel;
	
	private UngVien_DAO ungvienDAO;
	private HoSo_DAO hosoDAO;
	private TinTuyenDung_DAO tintuyendungDAO;
	private NhaTuyenDung_DAO nhatuyendungDAO;
	private HopDong_DAO hopdongDAO;
	
	private ArrayList<UngVien> ungviens;
	private ArrayList<NhaTuyenDung> nhatuyendungs;
	
	public HopDongFrame(NhanVien userName) {
		this.userName=userName;
		this.parent=this;
		
		ungvienDAO=new UngVien_DAO();
		hosoDAO=new HoSo_DAO();
		tintuyendungDAO=new TinTuyenDung_DAO();
		nhatuyendungDAO=new NhaTuyenDung_DAO();
		hopdongDAO=new HopDong_DAO();
		
		ungviens=new ArrayList<UngVien>();
		nhatuyendungs=new ArrayList<NhaTuyenDung>();
		
//		Tạo component bên phải
		initComponent();
		
//		Thêm update và delete vào table
		addTableTTDActionEvent();
		addTableHoSoActionEvent();
		addTableHopDongActionEvent();
		
//		Thêm sự kiện
		addActionListener();
		addMouseListener();
		addFocusListener();
		
		loadData();
		loadDataHoSo();
		loadDataTinTuyenDung();
		loadDataHopDong();
		
		for(UngVien uv: ungvienDAO.getDSUngVien()) {
			ungvienCombo.addItem(uv.getTenUV());
			ungviens.add(uv);
		}
		
		for(NhaTuyenDung ntd: nhatuyendungDAO.getDsNhaTuyenDung()) {
			nhatuyendungCombo.addItem(ntd.getTenNTD());
			nhatuyendungs.add(ntd);
		}
	}
	
	public JLabel createLabel(String title, boolean isBordered) {
		JLabel label = new JLabel(title);
		label.setFont(new Font("Segoe UI",1,16));
		if (isBordered) label.setBorder(BorderFactory.createEmptyBorder(10,20,0,10));
		return label;
	}
	
	public void initComponent() {
		timviecPanel=new JPanel(); 
		timviecPanel.setLayout(new BorderLayout(5,5));
		timviecPanel.setBackground(new Color(89, 145, 144));
		
		northPanelTimViec=new JPanel();
		northPanelTimViec.setLayout(new FlowLayout(FlowLayout.RIGHT,17,0));
		northPanelTimViec.setBackground(new Color(89, 145, 144));
		btnHuy=new Button("Hủy"); btnHuy.setFont(new Font("Segoe UI",0,16));
		btnHuy.setPreferredSize(new Dimension(120,25));
		btnHuy.setBackground(Color.RED);
		btnHuy.setForeground(Color.WHITE);
		northPanelTimViec.add(btnHuy);
		
//		Hiển thị danh sách tin tuyển dụng, danh sách hồ sơ ứng viên và hợp đồng
		centerPanelTimViec=new JPanel();
		centerPanelTimViec.setLayout(new BorderLayout(10, 10));
		centerPanelTimViec.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		centerPanelTimViec.setBackground(new Color(89, 145, 144));
		
		JPanel panelCenter=new JPanel();
		panelCenter.setLayout(new BorderLayout());
		panelCenter.setBackground(new Color(89, 145, 144));
//		Danh sách tin tuyển dụng
		danhsachTTDPanel=new GradientRoundPanel();
		danhsachTTDPanel.setBackground(Color.WHITE);
		danhsachTTDPanel.setLayout(new BorderLayout(10, 10));
		danhsachTTDPanel.setPreferredSize(new Dimension(660,700));
		
		danhsachTTDNorthPanel=new GradientRoundPanel();
		danhsachTTDNorthPanel.setLayout(new BorderLayout(10,10));
		danhsachTTDNorthPanel.setBackground(Color.WHITE);
		JPanel resNTD=new JPanel();
		resNTD.setOpaque(false);
		resNTD.setBorder(BorderFactory.createEmptyBorder(10,10,0,15));
		nhatuyendungLabel=new JLabel("Nhà tuyển dụng");
		nhatuyendungLabel.setFont(new Font("Segoe UI",1,16));
		nhatuyendungLabel.setForeground(Color.WHITE);
		nhatuyendungCombo=new JComboBox();
		nhatuyendungCombo.setFont(new Font("Segoe UI",0,16));
		nhatuyendungCombo.setForeground(Color.WHITE);
		nhatuyendungCombo.setBackground(new Color(89, 145, 144));
		nhatuyendungCombo.setPreferredSize(new Dimension(200,25));
		nhatuyendungCombo.setRenderer(new ComboBoxRenderer("Chọn nhà tuyển dụng"));
		resNTD.add(nhatuyendungLabel); resNTD.add(nhatuyendungCombo);
		titleTinTuyenDung=new JLabel("Danh sách tin tuyển dụng");
		titleTinTuyenDung.setFont(new Font("Segoe UI",1,16));
		titleTinTuyenDung.setForeground(Color.WHITE);
		titleTinTuyenDung.setBorder(BorderFactory.createEmptyBorder(10,20,0,10));
		danhsachTTDNorthPanel.add(titleTinTuyenDung, BorderLayout.WEST);
		danhsachTTDNorthPanel.add(resNTD, BorderLayout.EAST);
		
		danhsachTTDCenterPanel=new GradientRoundPanel();
		danhsachTTDCenterPanel.setLayout(new BoxLayout(danhsachTTDCenterPanel, BoxLayout.PAGE_AXIS));
		danhsachTTDCenterPanel.setBackground(Color.WHITE);
		String[] colName= {"Mã","Tiêu đề","Trình độ","Lương", "Hành động"};
		Object[][] data = {
			    {"1","Technical Project Manager","Đại học","1000",null},
			    {"2","Manual Tester","Cao đẳng", "500",null}
			};
		modelTableTinTuyenDung= new DefaultTableModel(data, colName){
			boolean[] canEdit = new boolean [] {
	                false,false, false, false, true
	            };
			
            @Override
            public boolean isCellEditable(int row, int column) {
               return canEdit[column];
            }
        };
		tableTinTuyenDung=createTable(modelTableTinTuyenDung);
		scrollTinTuyenDung=new JScrollPane(tableTinTuyenDung);
		scrollTinTuyenDung.setBorder(BorderFactory.createLineBorder(new Color(0,191,165)));
		GradientRoundPanel resScrollTinTuyenDung=new GradientRoundPanel();
		resScrollTinTuyenDung.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));
		resScrollTinTuyenDung.setLayout(new BoxLayout(resScrollTinTuyenDung, BoxLayout.PAGE_AXIS));
		resScrollTinTuyenDung.setBackground(Color.WHITE);
		resScrollTinTuyenDung.add(scrollTinTuyenDung);
		danhsachTTDCenterPanel.add(resScrollTinTuyenDung);
		
		danhsachTTDPanel.add(danhsachTTDNorthPanel, BorderLayout.NORTH);
		danhsachTTDPanel.add(danhsachTTDCenterPanel, BorderLayout.CENTER);
		
//		Danh sách hợp đồng
		danhsachHopDongPanel=new GradientRoundPanel();
		danhsachHopDongPanel.setBackground(Color.WHITE);
		danhsachHopDongPanel.setLayout(new BorderLayout(10, 10));
		danhsachHopDongPanel.setPreferredSize(new Dimension(getWidth(),400));
		
		danhsachHopDongNorthPanel=new GradientRoundPanel();
		danhsachHopDongNorthPanel.setLayout(new BorderLayout(10,10));
		danhsachHopDongNorthPanel.setBackground(Color.WHITE);
		
		JPanel resHD=new JPanel();
		resHD.setOpaque(false);
		resHD.setBorder(BorderFactory.createEmptyBorder(10,10,0,15));
		
		ngaybatdauLabel=new JLabel("Bắt đầu từ");
		ngaybatdauLabel.setFont(new Font("Segoe UI",1,16));
		ngaybatdauLabel.setForeground(Color.WHITE);
		modelDateBatDau = new UtilDateModel();
		modelDateBatDau.setSelected(true);
		Properties p=new Properties();
		p.put("text.day", "Day"); p.put("text.month", "Month"); p.put("text.year","Year");
		JDatePanelImpl panelBatDau=new JDatePanelImpl(modelDateBatDau, p);
		batdauText = new JDatePickerImpl(panelBatDau,new LabelDateFormatter());
		batdauText.setPreferredSize(new Dimension(200, 25));
		
		ngayketthucLabel=new JLabel("Kết thúc vào");
		ngayketthucLabel.setFont(new Font("Segoe UI",1,16));
		ngayketthucLabel.setForeground(Color.WHITE);
		modelDateKetThuc = new UtilDateModel();
		modelDateKetThuc.setSelected(true);
		Properties q=new Properties();
		q.put("text.day", "Day"); q.put("text.month", "Month"); q.put("text.year","Year");
		JDatePanelImpl panelKetThuc=new JDatePanelImpl(modelDateKetThuc, q);
		ketthucText = new JDatePickerImpl(panelKetThuc,new LabelDateFormatter());
		ketthucText.setPreferredSize(new Dimension(200, 25));
		
		btnTimkiem=new Button("Tìm kiếm"); btnTimkiem.setFont(new Font("Segoe UI",0,16));
		btnTimkiem.setPreferredSize(new Dimension(120,25));
		btnTimkiem.setBackground(new Color(0,102,102));
		btnTimkiem.setForeground(Color.WHITE);
		
		resHD.add(ngaybatdauLabel); resHD.add(batdauText);
		resHD.add(ngayketthucLabel); resHD.add(ketthucText);
		resHD.add(btnTimkiem);
		
		titleHopDong=new JLabel("Danh sách hợp đồng");
		titleHopDong.setFont(new Font("Segoe UI",1,16));
		titleHopDong.setForeground(Color.WHITE);
		titleHopDong.setBorder(BorderFactory.createEmptyBorder(10,20,0,10));
		danhsachHopDongNorthPanel.add(titleHopDong, BorderLayout.WEST);
		danhsachHopDongNorthPanel.add(resHD, BorderLayout.EAST);
//		
		danhsachHopDongCenterPanel=new GradientRoundPanel();
		danhsachHopDongCenterPanel.setLayout(new BoxLayout(danhsachHopDongCenterPanel, BoxLayout.PAGE_AXIS));
		String[] column= {"Mã","Tiêu đề","Lương", "Tên ứng viên", "Ngày lập", "Hành động"};
		Object[][] dt = {
			    {"1","Technical Project Manager","1000", "Minh Đạt", "13/12/2022", null},
			    {"2","Manual Tester", "500", "Thắng Đạt", "13/01/2024", null}
			};
		modelTableHopDong= new DefaultTableModel(dt, column){
			boolean[] canEdit = new boolean [] {
	                false, false, false, false, false, true
	            };
			
            @Override
            public boolean isCellEditable(int row, int column) {
               return canEdit[column];
            }
        };
		tableHopDong=createTable(modelTableHopDong);
		scrollHopDong=new JScrollPane(tableHopDong);
		scrollHopDong.setBorder(BorderFactory.createLineBorder(new Color(0,191,165)));
		GradientRoundPanel resScrollHopDong=new GradientRoundPanel();
		resScrollHopDong.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));
		resScrollHopDong.setLayout(new BoxLayout(resScrollHopDong, BoxLayout.PAGE_AXIS));
		resScrollHopDong.setBackground(Color.WHITE);
		resScrollHopDong.add(scrollHopDong);
		danhsachHopDongCenterPanel.add(resScrollHopDong);
		
		danhsachHopDongPanel.add(danhsachHopDongNorthPanel, BorderLayout.NORTH);
		danhsachHopDongPanel.add(danhsachHopDongCenterPanel, BorderLayout.CENTER);
		
//		Danh sách hồ sơ ứng viên
		danhsachHoSoPanel=new GradientRoundPanel();
		danhsachHoSoPanel.setLayout(new BorderLayout(10, 10));
		danhsachHoSoPanel.setPreferredSize(new Dimension(660, 700));
		
		danhsachHoSoNorthPanel=new GradientRoundPanel();
		danhsachHoSoNorthPanel.setLayout(new BorderLayout(10,10));
		danhsachHoSoNorthPanel.setBackground(Color.WHITE);
		JPanel resUngVien=new JPanel();
		resUngVien.setOpaque(false);
		resUngVien.setBorder(BorderFactory.createEmptyBorder(10,10,0,15));
		resUngVien.setBackground(Color.WHITE);
		
		ungvienLabel=new JLabel("Ứng viên");
		ungvienLabel.setFont(new Font("Segoe UI",1,16));
		ungvienLabel.setForeground(Color.WHITE);
		
		ungvienCombo=new JComboBox();
		ungvienCombo.setForeground(Color.WHITE);
		ungvienCombo.setBackground(new Color(89, 145, 144));
		ungvienCombo.setFont(new Font("Segoe UI",0,16));
		ungvienCombo.setPreferredSize(new Dimension(200,25));
		ungvienCombo.setRenderer(new ComboBoxRenderer("Chọn ứng viên"));
		resUngVien.add(ungvienLabel); resUngVien.add(ungvienCombo);
		
		titleHoSo = createLabel("Danh sách hồ sơ ứng viên", true);
		titleHoSo.setFont(new Font("Segoe UI",1,16));
		titleHoSo.setForeground(Color.WHITE);
		titleHoSo.setBorder(BorderFactory.createEmptyBorder(10,20,0,10));
		danhsachHoSoNorthPanel.add(titleHoSo, BorderLayout.WEST);
		danhsachHoSoNorthPanel.add(resUngVien, BorderLayout.EAST);
		
		danhsachHoSoCenterPanel=new GradientRoundPanel();
		danhsachHoSoCenterPanel.setLayout(new BoxLayout(danhsachHoSoCenterPanel, BoxLayout.PAGE_AXIS));
		danhsachHoSoCenterPanel.setBackground(Color.WHITE);
		String[] col= {"Mã","Trạng thái","Tên ứng viên","Trình độ", "Hành động"};
		Object[][] datas = {
			    {"1","Chưa nộp","Minh Đạt", "Đại học", null},
			    {"2","Chưa nộp","Thắng Đạt", "Cao đẳng", null}
			};
		modelTableHoSo= new DefaultTableModel(datas, col){
			boolean[] canEdit = new boolean [] {
	                false, false, false, false, true
	            };
			
            @Override
            public boolean isCellEditable(int row, int column) {
               return canEdit[column];
            }
        };
		tableHoSo=createTable(modelTableHoSo);
		scrollHoSo=new JScrollPane(tableHoSo);
		scrollHoSo.setBorder(BorderFactory.createLineBorder(new Color(0,191,165)));
		GradientRoundPanel resScrollHoSo=new GradientRoundPanel();
		resScrollHoSo.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));
		resScrollHoSo.setLayout(new BoxLayout(resScrollHoSo, BoxLayout.PAGE_AXIS));
		resScrollHoSo.add(scrollHoSo);
		danhsachHoSoCenterPanel.add(resScrollHoSo);
		
		danhsachHoSoPanel.add(danhsachHoSoNorthPanel, BorderLayout.NORTH);
		danhsachHoSoPanel.add(danhsachHoSoCenterPanel, BorderLayout.CENTER);
		
		
		panelCenter.add(danhsachHoSoPanel, BorderLayout.WEST);
		panelCenter.add(danhsachTTDPanel, BorderLayout.EAST);
		
		centerPanelTimViec.add(northPanelTimViec, BorderLayout.NORTH);
		centerPanelTimViec.add(panelCenter, BorderLayout.CENTER);
		centerPanelTimViec.add(danhsachHopDongPanel, BorderLayout.SOUTH);
		
		timviecPanel.add(centerPanelTimViec, BorderLayout.CENTER);
	}
	
	public JTable createTable(DefaultTableModel model) {
		JTable table=new JTable(model);
		table.getTableHeader().setFont(new Font("Segoe UI",1,14));
		table.setFont(new Font("Segoe UI",0,16));
		table.setRowHeight(30);
		DefaultTableCellRenderer centerRenderers = new DefaultTableCellRenderer();
		centerRenderers.setHorizontalAlignment( JLabel.CENTER );
		for(int i=0;i<table.getColumnCount()-1;i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(centerRenderers);			
		}
		table.setAutoCreateRowSorter(true);
		ArrayList<RowSorter.SortKey> lists = new ArrayList<>();
        DefaultRowSorter sorters = ((DefaultRowSorter)table.getRowSorter());
        sorters.setSortsOnUpdates(true);
        lists.add( new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sorters.setSortKeys(lists);
        sorters.sort();
        
		return table;
	}
	
	public void addTableTTDActionEvent() {
		TableActionEvent event=new TableActionEvent() {
			@Override
			public void onUpdate(int row) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onDelete(int row) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onViewHoSo(int row) {
				// TODO Auto-generated method stub
			
			}

			@Override
			public void onCreateHoSo(int row) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onCreateTaiKhoan(int row) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onViewTinTuyenDung(int row) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onCreateTinTuyenDung(int row) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onViewDetail(int row) {
				// TODO Auto-generated method stub
				TinTuyenDung ttd=tintuyendungDAO.getTinTuyenDung(tableTinTuyenDung.getValueAt(row, 0).toString());
				new ChiTietViecLamDialog(parent, rootPaneCheckingEnabled, ttd, null, null, true).setVisible(true);
			}
			
		};
		
		tableTinTuyenDung.getColumnModel().getColumn(4).setCellRenderer(new TableCellRendererDetail());
		tableTinTuyenDung.getColumnModel().getColumn(4).setCellEditor(new TableCellEditorDetail(event));
	}
	
	public void addTableHoSoActionEvent() {
		TableActionEvent event=new TableActionEvent() {
			@Override
			public void onUpdate(int row) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onDelete(int row) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onViewHoSo(int row) {
				// TODO Auto-generated method stub
			
			}

			@Override
			public void onCreateHoSo(int row) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onCreateTaiKhoan(int row) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onViewTinTuyenDung(int row) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onCreateTinTuyenDung(int row) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onViewDetail(int row) {
				// TODO Auto-generated method stub
				HoSo hoso=hosoDAO.getHoSo(tableHoSo.getValueAt(row, 0).toString());
				new ChiTietHoSoDialog(parent, rootPaneCheckingEnabled, hoso).setVisible(true);
			}
			
		};
		
		tableHoSo.getColumnModel().getColumn(4).setCellRenderer(new TableCellRendererDetail());
		tableHoSo.getColumnModel().getColumn(4).setCellEditor(new TableCellEditorDetail(event));
	}
	
	public void addTableHopDongActionEvent() {
		TableActionEvent event=new TableActionEvent() {
			@Override
			public void onUpdate(int row) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onDelete(int row) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onViewHoSo(int row) {
				// TODO Auto-generated method stub
			
			}

			@Override
			public void onCreateHoSo(int row) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onCreateTaiKhoan(int row) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onViewTinTuyenDung(int row) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onCreateTinTuyenDung(int row) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onViewDetail(int row) {
				// TODO Auto-generated method stub
				HopDong hd=hopdongDAO.getHopDong(tableHopDong.getValueAt(row, 0).toString());
				new ChiTietHopDongDialog(parent, rootPaneCheckingEnabled, hd).setVisible(true);
			}
			
		};
		
		tableHopDong.getColumnModel().getColumn(5).setCellRenderer(new TableCellRendererDetail());
		tableHopDong.getColumnModel().getColumn(5).setCellEditor(new TableCellEditorDetail(event));
	}
	
	
	public JPanel getPanel() {
		return this.timviecPanel;
	}
	
//	Lấy thông tin trình độ và chuyên ngành trong mô tả hồ sơ
	public String extracInfor(String text, String key) {
		Matcher matcher=Pattern.compile(key + ":\\s*(.*)", Pattern.MULTILINE).matcher(text);
		if(matcher.find()) {
			return matcher.group(1).trim();
		}
		return "";
	}
	
	public void loadData() {
		hosoDAO.setListHoSo(hosoDAO.getDSHoSo());
		ungvienDAO.setListUngVien(ungvienDAO.getDSUngVien());
		nhatuyendungDAO.setListNhatuyenDung(nhatuyendungDAO.getDsNhaTuyenDung());
		tintuyendungDAO.setListTinTuyenDung(tintuyendungDAO.getDsTinTuyenDung());
		hopdongDAO.setListHopDong(hopdongDAO.getDSHopDong());
	}
	
	public void loadDataHoSo() {
		modelTableHoSo.setRowCount(0);
		for(HoSo i: hosoDAO.getListHoSo()) {
			if(!i.getTrangThai().getValue().equalsIgnoreCase("Chưa nộp")) {
				Object[] obj=new Object[] {
						i.getMaHS(),
						i.getTrangThai().getValue(), 
						ungvienDAO.getUngVien(i.getUngVien().getMaUV()).getTenUV(), 
						extracInfor(i.getMoTa(),"Trình độ"), null
				};
				modelTableHoSo.addRow(obj);
			}
		}
	}
	
	public void loadDataTinTuyenDung() {
		modelTableTinTuyenDung.setRowCount(0);
		DecimalFormat df = new DecimalFormat("#,###");
		for(TinTuyenDung i: tintuyendungDAO.getListTinTuyenDung()) {
			Object[] obj=new Object[] {
					i.getMaTTD(), i.getTieuDe(),
					i.getTrinhDo().getValue(), 
					df.format(i.getLuong())+" VNĐ",null
			};
			modelTableTinTuyenDung.addRow(obj);
		}
	}
	
	public void loadDataHopDong() {
		DecimalFormat df = new DecimalFormat("#,###");
		DateTimeFormatter format=DateTimeFormatter.ofPattern("dd-MM-yyyy");
		modelTableHopDong.setRowCount(0);
		for(HopDong i: hopdongDAO.getListHopDong()) {
			Object[] obj=new Object[] {
					i.getMaHD(),
					tintuyendungDAO.getTinTuyenDung(i.getTinTuyenDung().getMaTTD()).getTieuDe(),
					df.format(tintuyendungDAO.getTinTuyenDung(i.getTinTuyenDung().getMaTTD()).getLuong())+" VNĐ", 
					ungvienDAO.getUngVien(i.getUngVien().getMaUV()).getTenUV(),
					format.format(i.getThoiGian()), null
			};
			modelTableHopDong.addRow(obj);
		}
	}
	
//	Trạng thái text chuột không nằm trong ô
	public void addPlaceHolder(JTextField text) {
		Font font=text.getFont();
		font=font.deriveFont(Font.ITALIC);
		text.setFont(font);
		text.setForeground(Color.WHITE);
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

	}
	
	public void addActionListener() {
		ungvienCombo.addActionListener(this);
		nhatuyendungCombo.addActionListener(this);
		
		btnHuy.addActionListener(this);
		btnTimkiem.addActionListener(this);
	}

	private boolean flag=true;
	private boolean check=true;
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		var obj=e.getSource();
		if(obj.equals(ungvienCombo)) {
			if(!flag) {
				UngVien uv=ungviens.get(ungvienCombo.getSelectedIndex());
				hosoDAO.setListHoSo(hosoDAO.getHoSoTheoUngVien(uv.getMaUV()));
				loadDataHoSo();
			}
			else {
				flag=false;
			}
		}
		else if(obj.equals(nhatuyendungCombo)) {
			if(!check) {
				NhaTuyenDung ntd=nhatuyendungs.get(nhatuyendungCombo.getSelectedIndex());
				tintuyendungDAO.setListTinTuyenDung(tintuyendungDAO.getTinTuyenDungTheoNTD(ntd.getMaNTD(), 1));
				loadDataTinTuyenDung();
			}
			else {
				check=false;
			}
		}
		else if(obj.equals(btnHuy)) {
			ungvienCombo.setSelectedIndex(0);
			nhatuyendungCombo.setSelectedIndex(0);
			modelDateBatDau.setValue(new Date());
			modelDateKetThuc.setValue(new Date());
			loadData();
			loadDataHoSo();
			loadDataTinTuyenDung();
			loadDataHopDong();
		}
		else if(obj.equals(btnTimkiem)) {
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
			if(LocalDate.parse(format.format(modelDateBatDau.getValue()))
					.compareTo(LocalDate.parse(format.format(modelDateKetThuc.getValue())))<=0) {
				ArrayList<HopDong> listHD=new ArrayList<HopDong>();
				for(HopDong i: hopdongDAO.getDSHopDong()) {
					if(LocalDate.parse(format.format(modelDateBatDau.getValue())).compareTo(i.getThoiGian())<=0
					&& LocalDate.parse(format.format(modelDateKetThuc.getValue())).compareTo(i.getThoiGian())>=0) {
						listHD.add(i);
					}
				}
				hopdongDAO.setListHopDong(listHD);
				loadDataHopDong();
			}
			else {
				JOptionPane.showMessageDialog(rootPane, "Khoảng thời gian tìm kiếm không hợp lệ");
			}
		}
	}
	
	public void addMouseListener() {
		tableHoSo.addMouseListener(this);
		tableTinTuyenDung.addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(tableHoSo)) {
			int index=tableHoSo.getSelectedRow();
			if(index!=-1) {
				DateTimeFormatter format=DateTimeFormatter.ofPattern("dd-MM-yyyy");
				HoSo hoso=hosoDAO.getHoSo(tableHoSo.getValueAt(index, 0).toString());
				HopDong hd=hopdongDAO.getHopDongTheoHoSo(hoso.getMaHS());
				modelTableHopDong.setRowCount(0);
				Object[] obj=new Object[] {
						hd.getMaHD(),
						tintuyendungDAO.getTinTuyenDung(hd.getTinTuyenDung().getMaTTD()).getTieuDe(),
						tintuyendungDAO.getTinTuyenDung(hd.getTinTuyenDung().getMaTTD()).getLuong(), 
						ungvienDAO.getUngVien(hd.getUngVien().getMaUV()).getTenUV(),
						format.format(hd.getThoiGian()), null
				};
				modelTableHopDong.addRow(obj);
			}
		}
		else if(e.getSource().equals(tableTinTuyenDung)) {
			int idx=tableTinTuyenDung.getSelectedRow();
			TinTuyenDung tintuyendung=tintuyendungDAO.getTinTuyenDung(tableTinTuyenDung.getValueAt(idx, 0).toString());
			hopdongDAO.setListHopDong(hopdongDAO.getHopDongTheoTinTuyenDung(tintuyendung.getMaTTD()));
			loadDataHopDong();
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
	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
	}

}
