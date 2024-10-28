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
import dao.UngVien_DAO;
import entity.HoSo;
import entity.NhaTuyenDung;
import entity.TinTuyenDung;
import entity.UngVien;
import entity.constraint.TrangThai;
import swing.Button;
import swing.GradientPanel;

public class DanhSachHoSoDaNopDialog extends JDialog implements ActionListener{
	
	GradientPanel timkiemPanel, danhsachPanel, btnPanel;
	JLabel timkiemTrangThaiLabel, timkiemNTDLabel;
	JComboBox timkiemTrangThaiText, timkiemNTDText;
	Button btnTimKiem, btnLamLai, btnHuy;
	DefaultTableModel modelTableHoSo;
	JTable tableHoSo;
	JScrollPane scrollHoSo;
	
	DanhSachHoSoDaNopDialog son;
	
	private TinTuyenDung ttd;
	private HoSo_DAO hosoDAO;
	private UngVien_DAO ungvienDAO;
	private TinTuyenDung_DAO tintuyendungDAO;

	public DanhSachHoSoDaNopDialog(Frame parent, boolean modal, TinTuyenDung ttd) {
		super(parent, modal);
		setTitle("Danh sách hồ sơ");
		setResizable(false);
		setSize(1000,450);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		
		this.son=this;
		this.ttd=ttd;
		hosoDAO=new HoSo_DAO();
		ungvienDAO=new UngVien_DAO();
		tintuyendungDAO=new TinTuyenDung_DAO();
		
		initComponent();
		addActionListener();
		
		addTableActionEvent();
		
		loadDataHoSo();
	}
	
	public void initComponent() {
//		Danh sách hồ sơ
		danhsachPanel=new GradientPanel(Color.decode("#ABC8CB"), Color.decode("#7CBDBF"));
		danhsachPanel.setBorder(BorderFactory.createEmptyBorder(13, 0, 0, 0));
		danhsachPanel.setLayout(new BoxLayout(danhsachPanel, BoxLayout.PAGE_AXIS));
		danhsachPanel.setBackground(Color.WHITE);
		String[] colName= {"Mã hồ sơ","Trạng thái","Tên ứng viên","Hành động"};
		Object[][] data = {
			    {1, "Chờ","Minh Đạt", null}
			};
		modelTableHoSo= new DefaultTableModel(data, colName){
			boolean[] canEdit = new boolean [] {
	                false, false, false, true
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
				UngVien uv=ungvienDAO.getUngVien(hs.getUngVien().getMaUV());
				new ChiTietHoSoDialog(son, rootPaneCheckingEnabled, hs, uv).setVisible(true);
			}
		};
		
		tableHoSo.getColumnModel().getColumn(3).setCellRenderer(new TableCellRendererDetail());
		tableHoSo.getColumnModel().getColumn(3).setCellEditor(new TableCellEditorDetail(event));
	}

	public void addActionListener() {
		btnHuy.addActionListener(this);
	}
	
	public void loadDataHoSo() {
		modelTableHoSo.setRowCount(0);
		for(HoSo i: hosoDAO.getHoSoTheoTinTD(ttd.getMaTTD())) {
			Object[] obj=new Object[] {
					i.getMaHS(), i.getTrangThai().getValue(), 
					ungvienDAO.getUngVien(i.getUngVien().getMaUV()).getTenUV(), 
					null
			};
			modelTableHoSo.addRow(obj);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		var obj=e.getSource();
		
		if(obj.equals(btnHuy)) {
			this.dispose();
		}
	}
	

}
