package view.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultRowSorter;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import controller.actiontable.TableActionEvent;
import controller.actiontable.TableCellEditorDetail;
import controller.actiontable.TableCellEditorUpdateDelete;
import controller.actiontable.TableCellRendererDetail;
import controller.actiontable.TableCellRendererUpdateDelete;
import dao.HoSo_DAO;
import dao.NhaTuyenDung_DAO;
import dao.TinTuyenDung_DAO;
import entity.HoSo;
import entity.NhaTuyenDung;
import entity.TinTuyenDung;
import entity.UngVien;
import entity.constraint.TrangThai;
import swing.Button;
import swing.GradientPanel;
import swing.TableCellGradient;

public class DanhSachHoSoDialog extends JDialog implements ActionListener{
	
	GradientPanel timkiemPanel, danhsachPanel, btnPanel;
	JLabel timkiemTrangThaiLabel, timkiemNTDLabel;
	JComboBox timkiemTrangThaiText, timkiemNTDText;
	Button btnTimKiem, btnLamLai, btnHuy;
	DefaultTableModel modelTableHoSo;
	JTable tableHoSo;
	JScrollPane scrollHoSo;
	
	DanhSachHoSoDialog son;
	
	private UngVien uv;
	private HoSo_DAO hosoDAO;
	private NhaTuyenDung_DAO nhatuyendungDAO;
	private TinTuyenDung_DAO tintuyendungDAO;

	public DanhSachHoSoDialog(Frame parent, boolean modal, UngVien uv) {
		super(parent, modal);
		setTitle("Danh sách hồ sơ");
		setResizable(false);
		setSize(1000,450);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		
		this.son=this;
		this.uv=uv;
		hosoDAO=new HoSo_DAO();
		nhatuyendungDAO=new NhaTuyenDung_DAO();
		tintuyendungDAO=new TinTuyenDung_DAO();
		
		initComponent();
		addActionListener();
		
		addTableActionEvent();
		
		loadDataNhaTuyenDung();
		loadDataHoSo();
	}
	
	public void initComponent() {
//		Tìm kiếm hồ sơ
		timkiemPanel=new GradientPanel(Color.decode("#ABC8CB"), Color.decode("#7CBDBF"));
		timkiemPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 12, 0));
		timkiemPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		
		timkiemTrangThaiLabel=new JLabel("Trạng thái:"); timkiemTrangThaiLabel.setFont(new Font("Segoe UI",0,16));
		timkiemTrangThaiText=new JComboBox(); 
		TrangThai[] trangthais=TrangThai.class.getEnumConstants();
		for(TrangThai t: trangthais) {
			timkiemTrangThaiText.addItem(t.getValue());
		}
		timkiemTrangThaiText.setFont(new Font("Segoe UI",0,16));
		timkiemTrangThaiText.setPreferredSize(new Dimension(156,26));
		
		timkiemNTDLabel=new JLabel("Nhà tuyển dụng:"); timkiemNTDLabel.setFont(new Font("Segoe UI",0,16));
		timkiemNTDText=new JComboBox(); 
		timkiemNTDText.setFont(new Font("Segoe UI",0,16));
		timkiemNTDText.setPreferredSize(new Dimension(300,26));
		
		JPanel resBtnSearch=new JPanel();
		resBtnSearch.setOpaque(false);
		resBtnSearch.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 5));
		resBtnSearch.setBackground(Color.WHITE);
		btnTimKiem=new Button("Tìm kiếm"); btnTimKiem.setFont(new Font("Segoe UI",0,16));
		btnTimKiem.setPreferredSize(new Dimension(120,25));
		btnTimKiem.setBackground(new Color(0,102,102));
		btnTimKiem.setForeground(Color.WHITE);
		btnLamLai=new Button("Làm lại"); btnLamLai.setFont(new Font("Segoe UI",0,16));
		btnLamLai.setPreferredSize(new Dimension(120,25));
		btnLamLai.setBackground(Color.RED);
		btnLamLai.setForeground(Color.WHITE);
		resBtnSearch.add(btnTimKiem); resBtnSearch.add(btnLamLai);
		
		timkiemPanel.add(timkiemTrangThaiLabel); timkiemPanel.add(timkiemTrangThaiText);
		timkiemPanel.add(timkiemNTDLabel); timkiemPanel.add(timkiemNTDText);
		timkiemPanel.add(resBtnSearch);
		
//		Danh sách hồ sơ
		danhsachPanel=new GradientPanel(Color.decode("#ABC8CB"), Color.decode("#7CBDBF"));
		danhsachPanel.setBorder(BorderFactory.createEmptyBorder(13, 0, 0, 0));
		danhsachPanel.setLayout(new BoxLayout(danhsachPanel, BoxLayout.PAGE_AXIS));
		danhsachPanel.setBackground(Color.WHITE);
		String[] colName= {"Mã hồ sơ","Trạng thái","Nhà tuyển dụng","Tin tuyển dụng","Hành động"};
		Object[][] data = {
			    {1, "Chờ","Amazon", "abc",null},
			    {2, "Chờ","Facebook", "xyz",null}
			};
		modelTableHoSo= new DefaultTableModel(data, colName){
			boolean[] canEdit = new boolean [] {
	                false, false, false, false, true
	            };
			
            @Override
            public boolean isCellEditable(int row, int column) {
               return canEdit[column];
            }
        };
		tableHoSo=new JTable(modelTableHoSo);
		tableHoSo.getTableHeader().setFont(new Font("Segoe UI",1,14));
		tableHoSo.setFont(new Font("Segoe UI",0,16));
		tableHoSo.setRowHeight(30);
		tableHoSo.setDefaultRenderer(Object.class, new TableCellGradient());
		
		tableHoSo.setAutoCreateRowSorter(true);
		ArrayList<RowSorter.SortKey> list = new ArrayList<>();
		list.add( new RowSorter.SortKey(0, SortOrder.ASCENDING));
        DefaultRowSorter sorter = ((DefaultRowSorter)tableHoSo.getRowSorter());
        sorter.setComparator(0, (o1, o2)->{
       	 String str1 = o1.toString().replaceAll("[^0-9]", "");
            String str2 = o2.toString().replaceAll("[^0-9]", "");
            return Integer.compare(Integer.parseInt(str1), Integer.parseInt(str2));
       });
        sorter.setSortsOnUpdates(true);
        sorter.setSortKeys(list);
        sorter.sort();
        
		scrollHoSo=new JScrollPane(tableHoSo);
		scrollHoSo.setBorder(BorderFactory.createLineBorder(new Color(0,191,165)));
		JPanel resScroll=new JPanel();
		resScroll.setOpaque(false);
		resScroll.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));
		resScroll.setLayout(new BoxLayout(resScroll, BoxLayout.PAGE_AXIS));
		resScroll.add(scrollHoSo);
		danhsachPanel.add(resScroll);
		
//		Các nút chức năng
		btnPanel=new GradientPanel(Color.decode("#ABC8CB"), Color.decode("#7CBDBF"));
		btnPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 0));
		btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		btnHuy=new Button("Hủy"); btnHuy.setFont(new Font("Segoe UI",0,16));
		btnHuy.setPreferredSize(new Dimension(100,30));
		btnHuy.setBackground(Color.RED);
		btnHuy.setForeground(Color.WHITE);
		btnPanel.add(btnHuy);
		
		add(timkiemPanel, BorderLayout.NORTH);
		add(danhsachPanel, BorderLayout.CENTER);
		add(btnPanel, BorderLayout.SOUTH);
		
	}
	
	public void addTableActionEvent() {
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
				HoSo hs=hosoDAO.getHoSo(tableHoSo.getValueAt(row, 0).toString());
				new ChiTietHoSoDialog(son, rootPaneCheckingEnabled, hs, uv).setVisible(true);
			}
		};
		
		tableHoSo.getColumnModel().getColumn(4).setCellRenderer(new TableCellRendererDetail());
		tableHoSo.getColumnModel().getColumn(4).setCellEditor(new TableCellEditorDetail(event));
	}

	public void addActionListener() {
		btnTimKiem.addActionListener(this);
		btnLamLai.addActionListener(this);
		btnHuy.addActionListener(this);
	}
	
	public void loadDataNhaTuyenDung() {
		for(NhaTuyenDung ntd: nhatuyendungDAO.getDsNhaTuyenDung()) {
			timkiemNTDText.addItem(ntd.getTenNTD());
		}
	}
	
	public void loadDataHoSo() {
		modelTableHoSo.setRowCount(0);
		for(HoSo i: hosoDAO.getHoSoTheoUngVien(uv.getMaUV())) {
			NhaTuyenDung ntd=null;
			TinTuyenDung ttd=null;
			if(i.getTinTuyenDung()!=null) {
				ttd=tintuyendungDAO.getTinTuyenDung(i.getTinTuyenDung().getMaTTD());
				
				ntd=nhatuyendungDAO.getNhaTuyenDung(ttd.getNhaTuyenDung().getMaNTD());
			}
			Object[] obj=new Object[] {
					i.getMaHS(), i.getTrangThai().getValue(), 
					ntd!=null?ntd.getTenNTD():"", 
					ttd!=null?ttd.getTieuDe():"",
					null
			};
			modelTableHoSo.addRow(obj);
		}
	}
	
	public void timkiemHoSo() {
		modelTableHoSo.setRowCount(0);
		String trangthai=timkiemTrangThaiText.getSelectedItem().toString();
		String nhatuyendung=timkiemNTDText.getSelectedItem().toString();
		
		for(HoSo i: hosoDAO.getHoSoTheoUngVien(uv.getMaUV())) {
			NhaTuyenDung ntd=null;
			TinTuyenDung ttd=null;
			if(i.getTinTuyenDung()!=null) {
				ttd=tintuyendungDAO.getTinTuyenDung(i.getTinTuyenDung().getMaTTD());
				
				ntd=nhatuyendungDAO.getNhaTuyenDung(ttd.getNhaTuyenDung().getMaNTD());
			}
			
			if(i.getTrangThai().getValue().equalsIgnoreCase(trangthai) && ttd!=null) {
				if(ntd.getTenNTD().equalsIgnoreCase(nhatuyendung)) {
					Object[] obj=new Object[] {
							i.getMaHS(), i.getTrangThai().getValue(), 
							ntd!=null?ntd.getTenNTD():"", 
							ttd!=null?ttd.getTieuDe():"",
							null
					};
					modelTableHoSo.addRow(obj);
				}
			}
			else if(i.getTrangThai().getValue().equalsIgnoreCase(trangthai) && ttd==null) {
				Object[] obj=new Object[] {
						i.getMaHS(), i.getTrangThai().getValue(), 
						ntd!=null?ntd.getTenNTD():"", 
						ttd!=null?ttd.getTieuDe():"",
						null
				};
				modelTableHoSo.addRow(obj);
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		var obj=e.getSource();
		if(obj.equals(btnTimKiem)) {
			timkiemHoSo();
		}
		else if(obj.equals(btnLamLai)) {
			timkiemTrangThaiText.setSelectedIndex(0);
			timkiemNTDText.setSelectedIndex(0);
			
			loadDataHoSo();
		}
		else if(obj.equals(btnHuy)) {
			this.dispose();
		}
	}
	

}
