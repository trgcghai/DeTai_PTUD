package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import component.ComboBoxRenderer;
import component.GradientPanel;
import component.GradientRoundPanel;
import component.RoundPanel;
import controller.Database;
import controller.ExcelHelper;
import controller.FilterImp;
import controller.actiontable.TableActionEvent;
import controller.actiontable.TableCellEditorCreateTaiKhoan;
import controller.actiontable.TableCellEditorUpdateDelete;
import controller.actiontable.TableCellRendererCreateTaiKhoan;
import controller.actiontable.TableCellRendererUpdateDelete;
import dao.TaiKhoan_DAO;
import dao.NhanVien_DAO;
import entity.TaiKhoan;
import entity.constraint.VaiTro;
import entity.NhanVien;
import exception.checkBirthday;
import exception.checkDateOfWork;
import exception.checkName;
import exception.checkPhone;
import exception.checkUserName;
import exception.checkUserPass;

public class NhanVienFrame extends JFrame implements ActionListener, MouseListener, FocusListener {

	NhanVien userName;
	NhanVienFrame parent;
	
//	Component danh sách nhân viên
	JPanel leftPanel,menuPanel,
		nhanvienPanel,northPanelNhanVien, centerPanelNhanVien; 
	JLabel userLabel, iconUserLabel,timkiemTenLabel, timkiemSDTLabel, titleNhanVien,vaitroLeftLabel;
	JTextField timkiemTenText, timkiemSDTText;
	JButton btnTimKiem, btnLamLai,btnThem,btnLuu;
	JTable tableNhanVien;
	DefaultTableModel modelTableNhanVien;
	JScrollPane scrollNhanVien;
	Icon iconBtnAdd, iconBtnSave;
	
	GradientRoundPanel timkiemPanel, danhsachPanel, danhsachNorthPanel, danhsachCenterPanel;
	
	private NhanVien_DAO nhanvienDAO;
	
	public NhanVienFrame(NhanVien userName) {
		this.userName=userName;
		this.parent=this;
		
//		Tạo component bên phải
		initComponent();

//		Thêm update và delete vào table
		addTableActionEvent();
		
//		Thêm sự kiện
		addActionListener();
		addMouseListener();
		addFocusListener();	
		
		Database.getInstance().connect();
		
		nhanvienDAO=new NhanVien_DAO();
				
		loadData();
		loadDataTable();
	}
	
	public void initComponent() {
		nhanvienPanel=new JPanel(); 
		nhanvienPanel.setLayout(new BorderLayout(5,5));
		
//		Hiển thị tìm kiếm và danh sách nhân viên
		centerPanelNhanVien=new JPanel();
		centerPanelNhanVien.setLayout(new BorderLayout(10, 10));
		centerPanelNhanVien.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		centerPanelNhanVien.setBackground(new Color(89, 145, 144));
//		Tìm kiếm nhân viên
		timkiemPanel=new GradientRoundPanel();
		timkiemPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 5));
		
		timkiemTenLabel=new JLabel("Họ tên nhân viên:"); 
		timkiemTenLabel.setFont(new Font("Segoe UI",1,16));
		timkiemTenLabel.setForeground(Color.WHITE);
		timkiemTenText=new JTextField(15); timkiemTenText.setFont(new Font("Segoe UI",0,16));
		timkiemTenText.setOpaque(false);
		timkiemSDTLabel=new JLabel("Số điện thoại:");
		timkiemSDTLabel.setFont(new Font("Segoe UI",1,16));
		timkiemSDTLabel.setForeground(Color.WHITE);
		timkiemSDTText=new JTextField(15); timkiemSDTText.setFont(new Font("Segoe UI",0,16));
		timkiemSDTText.setOpaque(false);
		
		JPanel resBtnSearch=new JPanel();
		resBtnSearch.setPreferredSize(new Dimension(350, 35));
		resBtnSearch.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 5));
		resBtnSearch.setOpaque(false);;
		btnTimKiem=new JButton("Tìm kiếm"); btnTimKiem.setFont(new Font("Segoe UI",0,16));
		btnTimKiem.setPreferredSize(new Dimension(120,25));
		btnTimKiem.setBackground(new Color(0,102,102));
		btnTimKiem.setForeground(Color.WHITE);
		btnLamLai=new JButton("Làm lại"); btnLamLai.setFont(new Font("Segoe UI",0,16));
		btnLamLai.setPreferredSize(new Dimension(120,25));
		btnLamLai.setBackground(Color.RED);
		btnLamLai.setForeground(Color.WHITE);
		resBtnSearch.add(btnTimKiem); resBtnSearch.add(btnLamLai);
		
		timkiemPanel.add(timkiemTenLabel); timkiemPanel.add(timkiemTenText);
		timkiemPanel.add(timkiemSDTLabel); timkiemPanel.add(timkiemSDTText);
		timkiemPanel.add(resBtnSearch);
//		Danh sách nhân viên
		danhsachPanel=new GradientRoundPanel();
		danhsachPanel.setLayout(new BorderLayout(10, 10));
		
		danhsachNorthPanel=new GradientRoundPanel();
		danhsachNorthPanel.setLayout(new BorderLayout(10,10));
		iconBtnAdd=new ImageIcon(getClass().getResource("/image/add.png"));
		iconBtnSave=new ImageIcon(getClass().getResource("/image/save.png"));
		JPanel resBtnThem=new JPanel();
		resBtnThem.setOpaque(false);
		resBtnThem.setBorder(BorderFactory.createEmptyBorder(10,10,0,20));
		btnThem=new JButton("Thêm mới", iconBtnAdd); btnThem.setFont(new Font("Segoe UI",0,16));
		btnThem.setPreferredSize(new Dimension(140,30));
		btnThem.setBackground(new Color(0,102,102));
		btnThem.setForeground(Color.WHITE);
		btnLuu=new JButton("Xuất Excel", iconBtnSave); btnLuu.setFont(new Font("Segoe UI",0,16));
		btnLuu.setPreferredSize(new Dimension(140,30));
		btnLuu.setBackground(new Color(51,51,255));
		btnLuu.setForeground(Color.WHITE);
		resBtnThem.add(btnThem); resBtnThem.add(btnLuu);
		titleNhanVien=new JLabel("Danh sách nhân viên");
		titleNhanVien.setFont(new Font("Segoe UI",1,16));
		titleNhanVien.setForeground(Color.WHITE);
		titleNhanVien.setBorder(BorderFactory.createEmptyBorder(10,20,0,10));
		danhsachNorthPanel.add(titleNhanVien, BorderLayout.WEST);
		danhsachNorthPanel.add(resBtnThem, BorderLayout.EAST);
		
		danhsachCenterPanel=new GradientRoundPanel();
		danhsachCenterPanel.setLayout(new BoxLayout(danhsachCenterPanel, BoxLayout.PAGE_AXIS));
		String[] colName= {"Mã nhân viên","Tên nhân viên","Số điện thoại","Ngày vào làm","Vai trò","Hành động","Tài khoản"};
		Object[][] data = {
			    {1, "MinhDat", "01234567", "13/12/2003", "Admin", null,null},
			    {2, "ThangDat", "07654321", "13/12/2003", "Nhân viên",null,null}
			};
		modelTableNhanVien= new DefaultTableModel(data, colName){
			 boolean[] canEdit = new boolean [] {
		                false, false, false, false, false, true, true
		            };
			
            @Override
            public boolean isCellEditable(int row, int column) {
               return canEdit[column];
            }
        };
		tableNhanVien=new JTable(modelTableNhanVien);
		tableNhanVien.getTableHeader().setFont(new Font("Segoe UI",1,14));
		tableNhanVien.setFont(new Font("Segoe UI",0,16));
		tableNhanVien.setRowHeight(30);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		for(int i=0;i<tableNhanVien.getColumnCount()-1;i++) {
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
		GradientRoundPanel resScroll=new GradientRoundPanel();
		resScroll.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));
		resScroll.setLayout(new BoxLayout(resScroll, BoxLayout.PAGE_AXIS));
		resScroll.setBackground(Color.WHITE);
		resScroll.add(scrollNhanVien);
		danhsachCenterPanel.add(resScroll);
		
		danhsachPanel.add(danhsachNorthPanel, BorderLayout.NORTH);
		danhsachPanel.add(danhsachCenterPanel, BorderLayout.CENTER);
		
		centerPanelNhanVien.add(timkiemPanel, BorderLayout.NORTH);
		centerPanelNhanVien.add(danhsachPanel, BorderLayout.CENTER);
		
		nhanvienPanel.add(centerPanelNhanVien, BorderLayout.CENTER);
	}
	
	public void addTableActionEvent() {
		TableActionEvent event=new TableActionEvent() {
			@Override
			public void onUpdate(int row) {
				// TODO Auto-generated method stub
				NhanVien nv=nhanvienDAO.getNhanVien(tableNhanVien.getValueAt(row, 0).toString());
				new ThemSuaNhanVienDialog(parent, rootPaneCheckingEnabled, nv).setVisible(true);
			}
			
			@Override
			public void onDelete(int row) {
				// TODO Auto-generated method stub
				NhanVien nv=nhanvienDAO.getNhanVien(tableNhanVien.getValueAt(row, 0).toString());
				int check=JOptionPane.showConfirmDialog(rootPane, "Có chắc chắn xóa?");
				if(check==JOptionPane.OK_OPTION) {
					nhanvienDAO.delete(nv.getMaNV());
					JOptionPane.showMessageDialog(rootPane, "Xóa nhân viên thành công");
					updateTable();
				}
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
				NhanVien nv=nhanvienDAO.getNhanVien(tableNhanVien.getValueAt(row, 0).toString());
				if(nhanvienDAO.getVaiTro(nv.getMaNV()).equalsIgnoreCase("ChuaCo")) {
					new CapSuaTaiKhoanDialog(parent, rootPaneCheckingEnabled, nv).setVisible(true);					
				}
				else {
					JOptionPane.showMessageDialog(rootPane, "Nhân viên đã có tài khoản");
				}
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
				
			}
		};
		
		tableNhanVien.getColumnModel().getColumn(5).setCellRenderer(new TableCellRendererUpdateDelete());
		tableNhanVien.getColumnModel().getColumn(5).setCellEditor(new TableCellEditorUpdateDelete(event));
		
		tableNhanVien.getColumnModel().getColumn(6).setCellRenderer(new TableCellRendererCreateTaiKhoan());
		tableNhanVien.getColumnModel().getColumn(6).setCellEditor(new TableCellEditorCreateTaiKhoan(event));
	}
	
	public JPanel getPanel() {
		return this.nhanvienPanel;
	}
	
//	Lấy dữ liệu từ sql
	public void loadData() {
		nhanvienDAO.setListNhanVien(nhanvienDAO.getDSNhanVien());
	}
	
//	Load dữ liệu lên bảng
	public void loadDataTable() {
		modelTableNhanVien.setRowCount(0);
		DateTimeFormatter format=DateTimeFormatter.ofPattern("dd-MM-yyyy");
		for(NhanVien i: nhanvienDAO.getListNhanVien()) {
			String vaitro=null;
			VaiTro[] vaitros=VaiTro.class.getEnumConstants();
			for(VaiTro v: vaitros) {
				if(v.toString().equalsIgnoreCase(nhanvienDAO.getVaiTro(i.getMaNV()))) {
					vaitro=v.getValue();
					break;
				}
			}
			Object[] obj=new Object[] {
					i.getMaNV(), i.getTenNV(), i.getSoDienThoai(),
					format.format(i.getNgayVaoLam()), vaitro, null, null
			};
			modelTableNhanVien.addRow(obj);
		}

	}
	
//	Load lại dữ liệu bảng khi cập nhật nhân viên
	public void updateTable() {
		loadData();
		loadDataTable();
	}
	
//	option tìm kiếm
//	1: tìm kiếm nhân viên theo tên
//	2: tìm kiếm nhân viên theo số điện thoại
//	3: tìm kiếm nhân viên theo tên và số điện thoại
//	Tìm kiếm nhân viên
	public void timkiemNhanVien() {
		if(!timkiemTenText.getText().equals("Nhập dữ liệu")
			&& timkiemSDTText.getText().equals("Nhập dữ liệu")) {
			nhanvienDAO.getListNhanVien().clear();
			String key=timkiemTenText.getText().trim();
			nhanvienDAO.setListNhanVien(nhanvienDAO.getNhanVienBy(key,1));
			loadDataTable();
			JOptionPane.showMessageDialog(rootPane, "Tìm thấy "+nhanvienDAO.getListNhanVien().size()+" nhân viên");
		}
		else if(timkiemTenText.getText().equals("Nhập dữ liệu")
				&& !timkiemSDTText.getText().equals("Nhập dữ liệu")) {
			nhanvienDAO.getListNhanVien().clear();
			String key=timkiemSDTText.getText().trim();
			nhanvienDAO.setListNhanVien(nhanvienDAO.getNhanVienBy(key,2));
			loadDataTable();
			JOptionPane.showMessageDialog(rootPane, "Tìm thấy "+nhanvienDAO.getListNhanVien().size()+" nhân viên");
		}
		else if(!timkiemTenText.getText().equals("Nhập dữ liệu")
				&& !timkiemSDTText.getText().equals("Nhập dữ liệu")) {
			nhanvienDAO.getListNhanVien().clear();
			String key=timkiemTenText.getText().trim()+"/"+timkiemSDTText.getText().trim();
			nhanvienDAO.setListNhanVien(nhanvienDAO.getNhanVienBy(key,3));
			loadDataTable();
			JOptionPane.showMessageDialog(rootPane, "Tìm thấy "+nhanvienDAO.getListNhanVien().size()+" nhân viên");
		}
		else {
			JOptionPane.showMessageDialog(rootPane, "Nhập tên hoặc số điện thoại nhân viên để tìm kiếm");
		}

		addPlaceHolder(timkiemTenText);
		addPlaceHolder(timkiemSDTText);
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
	
//	Listener
	public void addFocusListener() {
		timkiemTenText.addFocusListener(this);
		timkiemSDTText.addFocusListener(this);
		
		addPlaceHolder(timkiemTenText);
		addPlaceHolder(timkiemSDTText);
	}
	
	public void addActionListener() {
		btnThem.addActionListener(this);
		btnLuu.addActionListener(this);
		btnTimKiem.addActionListener(this);
		btnLamLai.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		var obj=e.getSource();
		
		if(obj.equals(btnThem)) {
			new ThemSuaNhanVienDialog(this, rootPaneCheckingEnabled).setVisible(true);
		}
		else if(obj.equals(btnLuu)) {
			ExcelHelper excel = new ExcelHelper();
			excel.exportData(this, tableNhanVien, 2);
		}
		else if(obj.equals(btnTimKiem)) {
			timkiemNhanVien();
		}
		else if(obj.equals(btnLamLai)) {
			addPlaceHolder(timkiemTenText);
			addPlaceHolder(timkiemSDTText);
			
			nhanvienDAO.setListNhanVien(nhanvienDAO.getDSNhanVien());
			loadDataTable();
		}
	}
	
	public void addMouseListener() {
		tableNhanVien.addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int row=tableNhanVien.getSelectedRow();
		if(row > -1) {
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
		var obj=e.getSource();
		if(obj.equals(timkiemTenText)) {
			if(timkiemTenText.getText().equals("Nhập dữ liệu")) {
				timkiemTenText.setText(null);
				timkiemTenText.requestFocus();
				
				removePlaceHolder(timkiemTenText);
			}
		}
		else if(obj.equals(timkiemSDTText)) {
			if(timkiemSDTText.getText().equals("Nhập dữ liệu")) {
				timkiemSDTText.setText(null);
				timkiemSDTText.requestFocus();
				
				removePlaceHolder(timkiemSDTText);
			}
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		var obj=e.getSource();
		if(obj.equals(timkiemTenText)) {
			if(timkiemTenText.getText().length()==0) {
				addPlaceHolder(timkiemTenText);
				timkiemTenText.setText("Nhập dữ liệu");
			}
		}
		else if(obj.equals(timkiemSDTText)) {
			if(timkiemSDTText.getText().length()==0) {
				addPlaceHolder(timkiemSDTText);
				timkiemSDTText.setText("Nhập dữ liệu");
			}
		}
	}

}
