package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
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

import controller.actiontable.TableActionEvent;
import controller.actiontable.TableCellEditorDetail;
import controller.actiontable.TableCellEditorTimViecLam;
import controller.actiontable.TableCellEditorUpdateDelete;
import controller.actiontable.TableCellRendererDetail;
import controller.actiontable.TableCellRendererTimViecLam;
import controller.actiontable.TableCellRendererUpdateDelete;
import entity.constraint.TrangThai;

public class DanhSachTinTuyenDungDialog extends JDialog implements FocusListener {
	
	JPanel timkiemPanel, danhsachPanel, btnPanel;
	JLabel timkiemTrangThaiLabel, timkiemTieudeLabel;
	JTextField timkiemTieudeText;
	JComboBox timkiemTrangThaiText;
	JButton btnTimKiem, btnLamLai, btnHuy;
	DefaultTableModel modelTableHoSo;
	JTable tableHoSo;
	JScrollPane scrollHoSo;
	
	DanhSachTinTuyenDungDialog son;

	public DanhSachTinTuyenDungDialog(Frame parent, boolean modal) {
		super(parent, modal);
		setTitle("Danh sách tin tuyển dụng");
		setResizable(false);
		setSize(950,450);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		
		this.son=this;
		
		initComponent();
		
		addFocusListener();
		
		addTableActionEvent();
		
	}
	
	public void initComponent() {
//		Tìm kiếm tin tuyển dụng
		timkiemPanel=new JPanel();
		timkiemPanel.setBackground(Color.WHITE);
		timkiemPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 12, 5));
		
		timkiemTieudeLabel=new JLabel("Tiêu đề:"); timkiemTieudeLabel.setFont(new Font("Segoe UI",0,16));
		timkiemTieudeText=new JTextField(20); 
		timkiemTieudeText.setFont(new Font("Segoe UI",0,16));
		
		timkiemTrangThaiLabel=new JLabel("Trạng thái:"); timkiemTrangThaiLabel.setFont(new Font("Segoe UI",0,16));
		String[] trangthais= {"Khả dụng", "Không khả dụng"};
		timkiemTrangThaiText=new JComboBox(trangthais); 
		timkiemTrangThaiText.setFont(new Font("Segoe UI",0,16));
		timkiemTrangThaiText.setPreferredSize(new Dimension(156,26));
		
		JPanel resBtnSearch=new JPanel();
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
		danhsachPanel=new JPanel();
		danhsachPanel.setLayout(new BoxLayout(danhsachPanel, BoxLayout.PAGE_AXIS));
		danhsachPanel.setBackground(Color.WHITE);
		String[] colName= {"Mã tin tuyển dụng","Tiêu đề","Mức lương","Trình độ", "Trạng thái","Hành động"};
		Object[][] data = {
			    {1, "Manual Tester", "10000","Cao đẳng", "Khả dụng",null},
			    {2, "Technical Project Manager", "15000","Đại học", "Không khả dụng",null}
			};
		modelTableHoSo= new DefaultTableModel(data, colName){
			boolean[] canEdit = new boolean [] {
	                false, false, false, false, false, true
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
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		for(int i=0;i<tableHoSo.getColumnCount()-1;i++) {
			tableHoSo.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);			
		}
		tableHoSo.setAutoCreateRowSorter(true);
		ArrayList<RowSorter.SortKey> list = new ArrayList<>();
        DefaultRowSorter sorter = ((DefaultRowSorter)tableHoSo.getRowSorter());
        sorter.setSortsOnUpdates(true);
        list.add( new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sorter.setSortKeys(list);
        sorter.sort();
		scrollHoSo=new JScrollPane(tableHoSo);
		scrollHoSo.setBorder(BorderFactory.createLineBorder(new Color(0,191,165)));
		JPanel resScroll=new JPanel();
		resScroll.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));
		resScroll.setLayout(new BoxLayout(resScroll, BoxLayout.PAGE_AXIS));
		resScroll.setBackground(Color.WHITE);
		resScroll.add(scrollHoSo);
		danhsachPanel.add(resScroll);
		
//		Các nút chức năng
		btnPanel=new JPanel();
		btnPanel.setLayout(new FlowLayout(FlowLayout.RIGHT,20,5));
		btnPanel.setBackground(Color.WHITE);
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
			public void onTimViecLam(int row) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onViewDetail(int row) {
				// TODO Auto-generated method stub
				new ChiTietTinTuyenDungDialog(son, rootPaneCheckingEnabled).setVisible(true);
			}
		};
		
		tableHoSo.getColumnModel().getColumn(5).setCellRenderer(new TableCellRendererDetail());
		tableHoSo.getColumnModel().getColumn(5).setCellEditor(new TableCellEditorDetail(event));
	}
	
//	Trạng thái text chuột không nằm trong ô
	public void addPlaceHolder(JTextField text) {
		Font font=text.getFont();
		font=font.deriveFont(Font.ITALIC);
		text.setFont(font);
		text.setForeground(Color.GRAY);
	}
	
//	Xóa trạng thái text chuột không nằm trong ô
	public void removePlaceHolder(JTextField text) {
		Font font=text.getFont();
		font=font.deriveFont(Font.PLAIN);
		text.setFont(font);
		text.setForeground(Color.BLACK);
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
	

}
