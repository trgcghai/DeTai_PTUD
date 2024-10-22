package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.DecimalFormat;
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
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import component.GradientPanel;
import controller.actiontable.TableActionEvent;
import controller.actiontable.TableCellEditorDetail;
import controller.actiontable.TableCellEditorUpdateDelete;
import controller.actiontable.TableCellRendererDetail;
import controller.actiontable.TableCellRendererUpdateDelete;
import dao.TinTuyenDung_DAO;
import entity.NhaTuyenDung;
import entity.TinTuyenDung;
import entity.constraint.TrangThai;

public class DanhSachTinTuyenDungDialog extends JDialog implements FocusListener, ActionListener {
	
	GradientPanel timkiemPanel, danhsachPanel, btnPanel;
	JLabel timkiemTrangThaiLabel, timkiemTieudeLabel;
	JTextField timkiemTieudeText;
	JComboBox timkiemTrangThaiText;
	JButton btnTimKiem, btnLamLai, btnHuy;
	DefaultTableModel modelTableTinTuyenDung;
	JTable tableTinTuyenDung;
	JScrollPane scrollTinTuyenDung;
	
	DanhSachTinTuyenDungDialog son;
	
	private Frame parent;
	private NhaTuyenDung ntd;
	private TinTuyenDung_DAO tintuyendungDAO;

	public DanhSachTinTuyenDungDialog(Frame parent, boolean modal, NhaTuyenDung ntd) {
		super(parent, modal);
		setTitle("Danh sách tin tuyển dụng");
		setResizable(false);
		setSize(950,450);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		
		this.son=this;
		this.parent=parent;
		this.ntd=ntd;
		tintuyendungDAO=new TinTuyenDung_DAO();
		
		initComponent();
		
		addFocusListener();
		addActionListener();
		
		addTableActionEvent();
		
		loadDataTable();
		
	}
	
	public void initComponent() {
//		Tìm kiếm tin tuyển dụng
		timkiemPanel=new GradientPanel(Color.decode("#ABC8CB"), Color.decode("#7CBDBF"));
		timkiemPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 12, 0));
		timkiemPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		
		timkiemTieudeLabel=new JLabel("Tiêu đề:"); 
		timkiemTieudeLabel.setFont(new Font("Segoe UI",1,16));
		timkiemTieudeLabel.setForeground(Color.WHITE);
		timkiemTieudeText=new JTextField(20); 
		timkiemTieudeText.setFont(new Font("Segoe UI",0,16));
		timkiemTieudeText.setForeground(Color.WHITE);
		timkiemTieudeText.setOpaque(false);
		
		timkiemTrangThaiLabel=new JLabel("Trạng thái:"); 
		timkiemTrangThaiLabel.setFont(new Font("Segoe UI",1,16));
		timkiemTrangThaiLabel.setForeground(Color.WHITE);
		String[] trangthais= {"Khả dụng", "Không khả dụng"};
		timkiemTrangThaiText=new JComboBox(trangthais); 
		timkiemTrangThaiText.setFont(new Font("Segoe UI",0,16));
		timkiemTrangThaiText.setForeground(Color.WHITE);
		timkiemTrangThaiText.setBackground(new Color(89, 145, 144));
		timkiemTrangThaiText.setPreferredSize(new Dimension(156,26));
		
		JPanel resBtnSearch=new JPanel();
		resBtnSearch.setOpaque(false);
		resBtnSearch.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 5));
		resBtnSearch.setBackground(Color.WHITE);
		btnTimKiem=new JButton("Tìm kiếm"); btnTimKiem.setFont(new Font("Segoe UI",0,16));
		btnTimKiem.setPreferredSize(new Dimension(120,25));
		btnTimKiem.setBackground(new Color(0,102,102));
		btnTimKiem.setForeground(Color.WHITE);
		btnLamLai=new JButton("Làm lại"); btnLamLai.setFont(new Font("Segoe UI",0,16));
		btnLamLai.setPreferredSize(new Dimension(120,25));
		btnLamLai.setBackground(Color.RED);
		btnLamLai.setForeground(Color.WHITE);
		resBtnSearch.add(btnTimKiem); resBtnSearch.add(btnLamLai);
		
		timkiemPanel.add(timkiemTieudeLabel); timkiemPanel.add(timkiemTieudeText);
		timkiemPanel.add(timkiemTrangThaiLabel); timkiemPanel.add(timkiemTrangThaiText);
		timkiemPanel.add(resBtnSearch);
		
//		Danh sách tin tuyển dụng
		danhsachPanel=new GradientPanel(Color.decode("#ABC8CB"), Color.decode("#7CBDBF"));
		danhsachPanel.setBorder(BorderFactory.createEmptyBorder(13, 0, 0, 0));
		danhsachPanel.setLayout(new BoxLayout(danhsachPanel, BoxLayout.PAGE_AXIS));
		danhsachPanel.setBackground(Color.WHITE);
		String[] colName= {"Mã tin tuyển dụng","Tiêu đề","Mức lương","Trình độ", "Trạng thái","Hành động"};
		Object[][] data = {
			    {1, "Manual Tester", "10000","Cao đẳng", "Khả dụng",null},
			    {2, "Technical Project Manager", "15000","Đại học", "Không khả dụng",null}
			};
		modelTableTinTuyenDung= new DefaultTableModel(data, colName){
			boolean[] canEdit = new boolean [] {
	                false, false, false, false, false, true
	            };
			
            @Override
            public boolean isCellEditable(int row, int column) {
               return canEdit[column];
            }
        };
		tableTinTuyenDung=new JTable(modelTableTinTuyenDung);
		tableTinTuyenDung.getTableHeader().setFont(new Font("Segoe UI",1,14));
		tableTinTuyenDung.setFont(new Font("Segoe UI",0,16));
		tableTinTuyenDung.setRowHeight(30);
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
		JPanel resScroll=new JPanel();
		resScroll.setOpaque(false);
		resScroll.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));
		resScroll.setLayout(new BoxLayout(resScroll, BoxLayout.PAGE_AXIS));
		resScroll.setBackground(Color.WHITE);
		resScroll.add(scrollTinTuyenDung);
		danhsachPanel.add(resScroll);
		
//		Các nút chức năng
		btnPanel=new GradientPanel(Color.decode("#ABC8CB"), Color.decode("#7CBDBF"));
		btnPanel.setLayout(new FlowLayout(FlowLayout.RIGHT,20,0));
		btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		btnHuy=new JButton("Hủy"); btnHuy.setFont(new Font("Segoe UI",0,16));
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
				TinTuyenDung tintuyendung=tintuyendungDAO.getTinTuyenDung(tableTinTuyenDung.getValueAt(row, 0).toString());
				new ChiTietTinTuyenDungDialog(son, rootPaneCheckingEnabled, tintuyendung, ntd).setVisible(true);
			}
		};
		
		tableTinTuyenDung.getColumnModel().getColumn(5).setCellRenderer(new TableCellRendererDetail());
		tableTinTuyenDung.getColumnModel().getColumn(5).setCellEditor(new TableCellEditorDetail(event));
	}
	
//	getTinTuyenDungTheoNTD với option
//	1: theo mã nhà tuyển dụng
//	2: theo trạng thái tin tuyển dụng
//	3: theo tiêu đề và trạng thái tin tuyển dụng
	public void loadDataTable() {
		modelTableTinTuyenDung.setRowCount(0);
		DecimalFormat df = new DecimalFormat("#,###");
		for(TinTuyenDung i: tintuyendungDAO.getTinTuyenDungTheoNTD(ntd.getMaNTD(),1)) {
			Object[] obj=new Object[] {
					i.getMaTTD(), i.getTieuDe(), df.format(i.getLuong())+" VNĐ", 
					i.getTrinhDo().getValue(), i.isTrangThai()?"Khả dụng":"Không khả dụng",
					null
			};
			modelTableTinTuyenDung.addRow(obj);
		}
	}
	
	public void timkiem() {
		modelTableTinTuyenDung.setRowCount(0);
		String tieude=timkiemTieudeText.getText();
		boolean trangthai=timkiemTrangThaiText.getSelectedItem().toString().equalsIgnoreCase("Khả dụng")?true:false;
		
		if(tieude.equals("Nhập dữ liệu") || tieude.equals("")) {
			for(TinTuyenDung i: tintuyendungDAO.getTinTuyenDungTheoNTD(ntd.getMaNTD()+"/"+String.valueOf(trangthai?1:0), 2)) {
				Object[] obj=new Object[] {
						i.getMaTTD(), i.getTieuDe(), i.getLuong(), 
						i.getTrinhDo().getValue(), i.isTrangThai()?"Khả dụng":"Không khả dụng",
						null
				};
				modelTableTinTuyenDung.addRow(obj);
			}
		}
		else {
			for(TinTuyenDung i: tintuyendungDAO.
					getTinTuyenDungTheoNTD(ntd.getMaNTD()+"/"+String.valueOf(trangthai?1:0)+"/"+tieude, 3)) {
				Object[] obj=new Object[] {
						i.getMaTTD(), i.getTieuDe(), i.getLuong(), 
						i.getTrinhDo().getValue(), i.isTrangThai()?"Khả dụng":"Không khả dụng",
						null
				};
				modelTableTinTuyenDung.addRow(obj);
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
	
	public void addActionListener() {
		btnTimKiem.addActionListener(this);
		btnLamLai.addActionListener(this);
		btnHuy.addActionListener(this);
	}
	
	public void addFocusListener() {
		timkiemTieudeText.addFocusListener(this);
		
		addPlaceHolder(timkiemTieudeText);
	}

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		var obj=e.getSource();
		if(obj.equals(timkiemTieudeText)) {
			if(timkiemTieudeText.getText().equals("Nhập dữ liệu")) {
				timkiemTieudeText.setText(null);
				timkiemTieudeText.requestFocus();
				
				removePlaceHolder(timkiemTieudeText);
			}
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		var obj=e.getSource();
		if(obj.equals(timkiemTieudeText)) {
			if(timkiemTieudeText.getText().length()==0) {
				addPlaceHolder(timkiemTieudeText);
				timkiemTieudeText.setText("Nhập dữ liệu");
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		var obj=e.getSource();
		if(obj.equals(btnTimKiem)) {
			timkiem();
		}
		else if(obj.equals(btnLamLai)) {
			addPlaceHolder(timkiemTieudeText);
			timkiemTrangThaiText.setSelectedIndex(0);
			
			loadDataTable();
		}
		else if(obj.equals(btnHuy)) {
			this.dispose();
		}
	}
	

}
