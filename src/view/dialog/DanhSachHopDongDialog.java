package view.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultRowSorter;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import controller.actiontable.TableActionEvent;
import controller.actiontable.TableCellEditorDetail;
import controller.actiontable.TableCellRendererDetail;
import dao.HopDong_DAO;
import dao.NhaTuyenDung_DAO;
import dao.NhanVien_DAO;
import dao.TinTuyenDung_DAO;
import dao.UngVien_DAO;
import entity.HopDong;
import entity.NhanVien;
import swing.Button;
import swing.GradientPanel;
import swing.TableCellGradient;

public class DanhSachHopDongDialog extends JDialog implements ActionListener{
	
	GradientPanel timkiemPanel, danhsachPanel, btnPanel;
	JLabel timkiemTrangThaiLabel, timkiemNTDLabel;
	JComboBox timkiemTrangThaiText, timkiemNTDText;
	Button btnTimKiem, btnLamLai, btnHuy;
	DefaultTableModel modelTableHopDong;
	JTable tableHopDong;
	JScrollPane scrollHopDong;
	
	DanhSachHopDongDialog son;
	
	private NhanVien_DAO nhanvienDAO;
	private HopDong_DAO hopdongDAO;
	private NhaTuyenDung_DAO nhatuyendungDAO;
	private TinTuyenDung_DAO tintuyendungDAO;
	private UngVien_DAO ungvienDAO;
	private NhanVien nv;
	private LocalDate ngaybatdau, ngayketthuc;

	public DanhSachHopDongDialog(Frame parent, boolean modal, NhanVien nv, 
			LocalDate ngaybatdau, LocalDate ngayketthuc) {
		super(parent, modal);
		setTitle("Danh sách hợp đồng");
		setResizable(false);
		setSize(1000,450);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		
		this.son=this;
		this.nv=nv;
		this.ngaybatdau=ngaybatdau;
		this.ngayketthuc=ngayketthuc;
		nhanvienDAO=new NhanVien_DAO();
		hopdongDAO=new HopDong_DAO();
		nhatuyendungDAO=new NhaTuyenDung_DAO();
		tintuyendungDAO=new TinTuyenDung_DAO();
		ungvienDAO=new UngVien_DAO();
		
		initComponent();
		addActionListener();
		
		addTableActionEvent();
		
		loadDataHoSo();
	}
	
	public void initComponent() {
//		Danh sách hợp đồng
		danhsachPanel=new GradientPanel(Color.decode("#ABC8CB"), Color.decode("#7CBDBF"));
		danhsachPanel.setBorder(BorderFactory.createEmptyBorder(13, 0, 0, 0));
		danhsachPanel.setLayout(new BoxLayout(danhsachPanel, BoxLayout.PAGE_AXIS));
		danhsachPanel.setBackground(Color.WHITE);
		String[] colName= {"Mã hợp đồng","Tên nhà tuyển dụng","Tên ứng viên","Phí","Ngày lập","Xem chi tiết"};
		Object[][] data = {
			    {1, "FaceBook","Minh Đạt", "500","13-12-2024",null}
			};
		modelTableHopDong= new DefaultTableModel(data, colName){
			boolean[] canEdit = new boolean [] {
	                false, false, false, false, false, true
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
		list.add( new RowSorter.SortKey(0, SortOrder.ASCENDING));
        DefaultRowSorter sorter = ((DefaultRowSorter)tableHopDong.getRowSorter());
        sorter.setComparator(0, (o1, o2)->{
       	 String str1 = o1.toString().replaceAll("[^0-9]", "");
            String str2 = o2.toString().replaceAll("[^0-9]", "");
            return Integer.compare(Integer.parseInt(str1), Integer.parseInt(str2));
       });
        sorter.setSortsOnUpdates(true);
        sorter.setSortKeys(list);
        sorter.sort();
        
		scrollHopDong=new JScrollPane(tableHopDong);
		scrollHopDong.setBorder(BorderFactory.createLineBorder(new Color(0,191,165)));
		JPanel resScroll=new JPanel();
		resScroll.setOpaque(false);
		resScroll.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));
		resScroll.setLayout(new BoxLayout(resScroll, BoxLayout.PAGE_AXIS));
		resScroll.add(scrollHopDong);
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
				HopDong hd=hopdongDAO.getHopDong(tableHopDong.getValueAt(row, 0).toString());
				new ChiTietHopDongDialog(son, rootPaneCheckingEnabled, hd).setVisible(true);
			}
		};
		
		tableHopDong.getColumnModel().getColumn(5).setCellRenderer(new TableCellRendererDetail());
		tableHopDong.getColumnModel().getColumn(5).setCellEditor(new TableCellEditorDetail(event));
	}

	public void addActionListener() {
		btnHuy.addActionListener(this);
	}
	
	public void loadDataHoSo() {
		DecimalFormat df = new DecimalFormat("#,###");
		DateTimeFormatter format=DateTimeFormatter.ofPattern("dd-MM-yyyy");
		modelTableHopDong.setRowCount(0);
		
		for(HopDong i: hopdongDAO.getHopDongTheoNhanVien(nv.getMaNV(),ngaybatdau,ngayketthuc)) {
			Object[] obj=new Object[] {
					i.getMaHD(),
					nhatuyendungDAO.getNhaTuyenDung(tintuyendungDAO.getTinTuyenDung(i.getTinTuyenDung().getMaTTD())
							.getNhaTuyenDung().getMaNTD()).getTenNTD(),
					ungvienDAO.getUngVien(i.getUngVien().getMaUV()).getTenUV(),
					df.format(i.getPhiDichVu())+" VNĐ", 
					format.format(i.getThoiGian()), null
			};
			modelTableHopDong.addRow(obj);
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
