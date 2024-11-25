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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
import swing.TableCellGradient;
import view.dialog.ChiTietHoSoDialog;
import view.dialog.ChiTietViecLamDialog;

public class TimViecLamFrame extends JFrame implements ActionListener, MouseListener, FocusListener, ItemListener {
	
	NhanVien userName;
	TimViecLamFrame parent;
	
//	Component tìm việc làm
	JPanel leftPanel,menuPanel,
		timviecPanel, centerPanelTimViec, northPanelTimViec;
	JLabel titleTinTuyenDung, titleHoSo, nhatuyendungLabel, ungvienLabel;
	JTable tableTinTuyenDung, tableHoSo;
	DefaultTableModel modelTableTinTuyenDung, modelTableHoSo;
	JScrollPane scrollTinTuyenDung, scrollHoSo;
	JComboBox nhatuyendungCombo, ungvienCombo;
	Button btnHuy;
	
	GradientRoundPanel danhsachTTDPanel, danhsachTTDNorthPanel, danhsachTTDCenterPanel,
				danhsachHoSoPanel, danhsachHoSoNorthPanel, danhsachHoSoCenterPanel;
	
	private UngVien_DAO ungvienDAO;
	private HoSo_DAO hosoDAO;
	private NhaTuyenDung_DAO nhatuyendungDAO;
	private TinTuyenDung_DAO tintuyendungDAO;
	
	private ArrayList<UngVien> ungviens;
	private ArrayList<NhaTuyenDung> nhatuyendungs;
	private HoSo currentHoSo;
	
	public TimViecLamFrame(NhanVien userName) {
		this.userName=userName;
		this.parent=this;
		
		ungvienDAO=new UngVien_DAO();
		hosoDAO=new HoSo_DAO();
		nhatuyendungDAO=new NhaTuyenDung_DAO();
		tintuyendungDAO=new TinTuyenDung_DAO();
		
		ungviens=new ArrayList<UngVien>();
		nhatuyendungs=new ArrayList<NhaTuyenDung>();
		currentHoSo=new HoSo();
		
//		Tạo component bên phải
		initComponent();
		
//		Thêm update và delete vào table
		addTableTTDActionEvent();
		addTableHoSoActionEvent();
		
//		Thêm sự kiện
		addActionListener();
		addMouseListener();
		
		loadData();
		loadDataUngVienHoSo();
		loadDataTinTuyenDungNhaTuyenDung();
		
		for(UngVien uv: ungvienDAO.getListUngVien()) {
			ungvienCombo.addItem(uv.getTenUV());
			ungviens.add(uv);
		}
		
		for(NhaTuyenDung ntd: nhatuyendungDAO.getListNhatuyenDung()) {
			nhatuyendungCombo.addItem(ntd.getTenNTD());
			nhatuyendungs.add(ntd);
		}
	}
	
	public void initComponent() {
		timviecPanel=new JPanel(); 
		timviecPanel.setLayout(new BorderLayout());
		timviecPanel.setBackground(new Color(89, 145, 144));
		
		northPanelTimViec=new JPanel();
		northPanelTimViec.setLayout(new FlowLayout(FlowLayout.RIGHT,10,0));
		northPanelTimViec.setBackground(new Color(89, 145, 144));
		btnHuy=new Button("Hủy"); btnHuy.setFont(new Font("Segoe UI",0,16));
		btnHuy.setPreferredSize(new Dimension(120,25));
		btnHuy.setBackground(Color.RED);
		btnHuy.setForeground(Color.WHITE);
		northPanelTimViec.add(btnHuy);
		
//		Hiển thị danh sách tin tuyển dụng, danh sách hồ sơ ứng viên
		centerPanelTimViec=new JPanel();
		centerPanelTimViec.setLayout(new BorderLayout());
		centerPanelTimViec.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		centerPanelTimViec.setBackground(new Color(89, 145, 144));
		
//		Danh sách tin tuyển dụng
		danhsachTTDPanel=new GradientRoundPanel();
		danhsachTTDPanel.setLayout(new BorderLayout());
		danhsachTTDPanel.setPreferredSize(new Dimension(660,getHeight()));
		
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
	                false, false, false, false, true
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
		
//		Danh sách hồ sơ ứng viên
		danhsachHoSoPanel=new GradientRoundPanel();
		danhsachHoSoPanel.setLayout(new BorderLayout());
		danhsachHoSoPanel.setPreferredSize(new Dimension(660,getHeight()));
		
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
		
		titleHoSo = new JLabel("Danh sách hồ sơ ứng viên");
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
	                false,false, false, false, true
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
		
		centerPanelTimViec.add(danhsachTTDPanel, BorderLayout.EAST);
		centerPanelTimViec.add(danhsachHoSoPanel, BorderLayout.WEST);
		
		timviecPanel.add(northPanelTimViec, BorderLayout.NORTH);
		timviecPanel.add(centerPanelTimViec, BorderLayout.CENTER);
	}
	
	public JTable createTable(DefaultTableModel model) {
		JTable table=new JTable(model);
		table.getTableHeader().setFont(new Font("Segoe UI",1,14));
		table.setFont(new Font("Segoe UI",0,16));
		table.setRowHeight(30);
		table.setDefaultRenderer(Object.class, new TableCellGradient());
		table.setAutoCreateRowSorter(true);
		ArrayList<RowSorter.SortKey> lists = new ArrayList<>();
		lists.add( new RowSorter.SortKey(0, SortOrder.ASCENDING));
        DefaultRowSorter sorters = ((DefaultRowSorter)table.getRowSorter());
        sorters.setComparator(0, (o1, o2)->{
       	 String str1 = o1.toString().replaceAll("[^0-9]", "");
            String str2 = o2.toString().replaceAll("[^0-9]", "");
            return Integer.compare(Integer.parseInt(str1), Integer.parseInt(str2));
       });
        sorters.setSortsOnUpdates(true);
        sorters.setSortKeys(lists);
        sorters.sort();
        
		return table;
	}
	
	public void loadData() {
		ungvienDAO.setListUngVien(ungvienDAO.getDSUngVien());
		hosoDAO.setListHoSo(hosoDAO.getDSHoSo());
		tintuyendungDAO.setListTinTuyenDung(tintuyendungDAO.getDsTinTuyenDung());
		nhatuyendungDAO.setListNhatuyenDung(nhatuyendungDAO.getDsNhaTuyenDung());
	}
	
	public void loadDataUngVienHoSo() {
		modelTableHoSo.setRowCount(0);
		for(HoSo i: hosoDAO.getListHoSo()) {
			if(i.getTrangThai().getValue().equalsIgnoreCase("Chưa nộp")) {
				Object[] obj=new Object[] {
						i.getMaHS(),
						i.getTrangThai().getValue(), 
						ungvienDAO.getUngVien(i.getUngVien().getMaUV()).getTenUV(), 
						extracInfor(i.getMoTa(), "Trình độ"), null
				};
				modelTableHoSo.addRow(obj);
			}
		}
	}
	
	public void loadDataTinTuyenDungNhaTuyenDung() {
		DecimalFormat df = new DecimalFormat("#,###");
		modelTableTinTuyenDung.setRowCount(0);
		for(TinTuyenDung i: tintuyendungDAO.getListTinTuyenDung()) {
			if(i.getSoLuong() > 0) {
				if(i.getNgayHetHan().compareTo(LocalDate.now())>0) {
					if(i.getNgayDangTin().compareTo(LocalDate.now())<=0 && i.isTrangThai()) {
						Object[] obj=new Object[] {
								i.getMaTTD(),
								i.getTieuDe(), i.getTrinhDo().getValue(), df.format(i.getLuong())+" VNĐ", null
						};
						modelTableTinTuyenDung.addRow(obj);
					}
				}
			}
		}
	}
	
	public void updateData() {
		loadData();
		loadDataUngVienHoSo();
		loadDataTinTuyenDungNhaTuyenDung();
	}
	
	
//	Lấy thông tin trình độ và chuyên ngành trong mô tả hồ sơ
	public String extracInfor(String text, String key) {
		Matcher matcher=Pattern.compile(key + ":\\s*(.*)", Pattern.MULTILINE).matcher(text);
		if(matcher.find()) {
			return matcher.group(1).trim();
		}
		return "";
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
				new ChiTietHoSoDialog(parent, rootPaneCheckingEnabled,hoso).setVisible(true);
			}
			
		};
		
		tableHoSo.getColumnModel().getColumn(4).setCellRenderer(new TableCellRendererDetail());
		tableHoSo.getColumnModel().getColumn(4).setCellEditor(new TableCellEditorDetail(event));
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
				new ChiTietViecLamDialog(parent, rootPaneCheckingEnabled, ttd, currentHoSo, userName).setVisible(true);
			}
			
		};
		
		tableTinTuyenDung.getColumnModel().getColumn(4).setCellRenderer(new TableCellRendererDetail());
		tableTinTuyenDung.getColumnModel().getColumn(4).setCellEditor(new TableCellEditorDetail(event));
	}
	
	public JPanel getPanel() {
		return this.timviecPanel;
	}
	
//	Tìm kiếm việc làm phù hợp với hồ sơ
	public void displayTinTuyenDung(HoSo hoso) {
		String trinhdo=extracInfor(hoso.getMoTa(), "Trình độ");
		String nganhnghe=extracInfor(hoso.getMoTa(), "Chuyên ngành");
		
		tintuyendungDAO.setListTinTuyenDung(tintuyendungDAO.getTinTuyenDungTheoTDNN(trinhdo, nganhnghe));
		loadDataTinTuyenDungNhaTuyenDung();
	}
	
//	Tìm hồ sơ phù hợp với việc làm
	public void displayHoSo(TinTuyenDung tintuyendung) {
		String trinhdo=tintuyendung.getTrinhDo().getValue();
		String nganhnghe=tintuyendung.getNganhNghe().getValue();
		
		ArrayList<HoSo> hs=new ArrayList<HoSo>();
		for(HoSo i: hosoDAO.getDSHoSo()) {
			String hsTD=extracInfor(i.getMoTa(), "Trình độ");
			String hsNN=extracInfor(i.getMoTa(), "Chuyên ngành");
			if(trinhdo.equalsIgnoreCase(hsTD) && nganhnghe.equalsIgnoreCase(hsNN)) {
				hs.add(i);
			}
		}
		hosoDAO.setListHoSo(hs);
		loadDataUngVienHoSo();
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
	public void addActionListener() {
		btnHuy.addActionListener(this);
		ungvienCombo.addActionListener(this);
		nhatuyendungCombo.addActionListener(this);
	}

	private boolean flag=true;
	private boolean check=true;
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		var obj=e.getSource();
		if(obj.equals(btnHuy)) {
			ungvienCombo.setSelectedIndex(0);
			nhatuyendungCombo.setSelectedIndex(0);
			
			currentHoSo=null;
			loadData();
			loadDataUngVienHoSo();
			loadDataTinTuyenDungNhaTuyenDung();
		}
		else if(obj.equals(ungvienCombo)) {
			if(!flag) {
				UngVien uv=ungviens.get(ungvienCombo.getSelectedIndex());
				hosoDAO.setListHoSo(hosoDAO.getHoSoTheoUngVien(uv.getMaUV()));
				loadDataUngVienHoSo();
			}
			else {
				flag=false;
			}
		}
		else if(obj.equals(nhatuyendungCombo)) {
			if(!check) {
				NhaTuyenDung ntd=nhatuyendungs.get(nhatuyendungCombo.getSelectedIndex());
				tintuyendungDAO.setListTinTuyenDung(tintuyendungDAO.getTinTuyenDungTheoNTD(ntd.getMaNTD(), 1));
				loadDataTinTuyenDungNhaTuyenDung();
			}
			else {
				check=false;
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
				HoSo hoso=hosoDAO.getHoSo(tableHoSo.getValueAt(index, 0).toString());
				currentHoSo=hoso;
				displayTinTuyenDung(hoso);
			}
		}
		else if(e.getSource().equals(tableTinTuyenDung)) {
			int idx=tableTinTuyenDung.getSelectedRow();
			TinTuyenDung tintuyendung=tintuyendungDAO.getTinTuyenDung(tableTinTuyenDung.getValueAt(idx, 0).toString());
			currentHoSo=null;
			displayHoSo(tintuyendung);
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

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
	}

}
