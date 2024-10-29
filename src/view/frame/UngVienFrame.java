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

import controller.Database;
import controller.ExcelHelper;
import controller.FilterImp;
import controller.LabelDateFormatter;
import controller.actiontable.TableActionEvent;
import controller.actiontable.TableCellEditorUpdateDelete;
import controller.actiontable.TableCellEditorViewCreateHoSo;
import controller.actiontable.TableCellRendererUpdateDelete;
import controller.actiontable.TableCellRendererViewCreateHoSo;
import dao.TaiKhoan_DAO;
import dao.UngVien_DAO;
import dao.NhanVien_DAO;
import entity.TaiKhoan;
import entity.UngVien;
import entity.constraint.GioiTinh;
import entity.constraint.VaiTro;
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
import view.dialog.DanhSachHoSoDialog;
import view.dialog.TaoSuaHoSoDialog;
import view.dialog.ThemSuaUngVienDialog;

public class UngVienFrame extends JFrame implements ActionListener, MouseListener, FocusListener {
	
	NhanVien userName;
	UngVienFrame parent;
	
//	Component danh sách ứng viên
	JPanel leftPanel,menuPanel,
		ungvienPanel,northPanelUngVien, centerPanelUngVien;
	JLabel userLabel, iconUserLabel,timkiemTenLabel, timkiemSDTLabel, titleNhanVien,vaitroLeftLabel;
	JTextField timkiemTenText, timkiemSDTText;
	Button btnTimKiem, btnLamLai,btnThem,btnLuu;
	JTable tableUngVien;
	DefaultTableModel modelTableUngVien;
	JScrollPane scrollUngVien;
	Icon iconBtnAdd,iconBtnSave;
	
	GradientRoundPanel timkiemPanel,
	danhsachPanel, danhsachNorthPanel, danhsachCenterPanel;
	
	private UngVien_DAO ungvienDAO;
	
	
	public UngVienFrame(NhanVien userName) {
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
		
		ungvienDAO=new UngVien_DAO();
		
		loadData();
		loadDataTable();
		
	}
	
	public void initComponent() {
		ungvienPanel=new JPanel(); 
		ungvienPanel.setLayout(new BorderLayout(5,5));
		ungvienPanel.setBackground(new Color(220, 220, 220));
		
//		Hiển thị tìm kiếm và danh sách ứng viên
		centerPanelUngVien=new JPanel();
		centerPanelUngVien.setLayout(new BorderLayout(10, 10));
		centerPanelUngVien.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		centerPanelUngVien.setBackground(new Color(89, 145, 144));
//		Tìm kiếm ứng viên
		timkiemPanel=new GradientRoundPanel();
		timkiemPanel.setBackground(Color.WHITE);
		timkiemPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 5));
		
		timkiemTenLabel=new JLabel("Họ tên ứng viên:");
		timkiemTenLabel.setFont(new Font("Segoe UI",1,16));
		timkiemTenLabel.setForeground(Color.WHITE);
		timkiemTenText=new JTextField(15); 
		timkiemTenText.setFont(new Font("Segoe UI",0,16));
		timkiemTenText.setOpaque(false);
		timkiemTenText.setForeground(Color.WHITE);
		timkiemSDTLabel=new JLabel("Số điện thoại:"); 
		timkiemSDTLabel.setFont(new Font("Segoe UI",1,16));
		timkiemSDTLabel.setForeground(Color.WHITE);
		timkiemSDTText=new JTextField(15); 
		timkiemSDTText.setFont(new Font("Segoe UI",0,16));
		timkiemSDTText.setOpaque(false);
		timkiemSDTText.setForeground(Color.WHITE);
		
		JPanel resBtnSearch=new JPanel();
		resBtnSearch.setPreferredSize(new Dimension(350, 35));
		resBtnSearch.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 5));
		resBtnSearch.setOpaque(false);
		btnTimKiem=new Button("Tìm kiếm"); btnTimKiem.setFont(new Font("Segoe UI",0,16));
		btnTimKiem.setPreferredSize(new Dimension(120,25));
		btnTimKiem.setBackground(new Color(0,102,102));
		btnTimKiem.setForeground(Color.WHITE);
		btnLamLai=new Button("Làm lại"); btnLamLai.setFont(new Font("Segoe UI",0,16));
		btnLamLai.setPreferredSize(new Dimension(120,25));
		btnLamLai.setBackground(Color.RED);
		btnLamLai.setForeground(Color.WHITE);
		resBtnSearch.add(btnTimKiem); resBtnSearch.add(btnLamLai);
		
		timkiemPanel.add(timkiemTenLabel); timkiemPanel.add(timkiemTenText);
		timkiemPanel.add(timkiemSDTLabel); timkiemPanel.add(timkiemSDTText);
		timkiemPanel.add(resBtnSearch);
//		Danh sách ứng viên
		danhsachPanel=new GradientRoundPanel();
		danhsachPanel.setLayout(new BorderLayout(10, 10));
		
		danhsachNorthPanel=new GradientRoundPanel();
		danhsachNorthPanel.setLayout(new BorderLayout(10,10));
		iconBtnAdd=new ImageIcon(getClass().getResource("/image/add.png"));
		iconBtnSave=new ImageIcon(getClass().getResource("/image/save.png"));
		JPanel resBtnThem=new JPanel();
		resBtnThem.setOpaque(false);
		resBtnThem.setBorder(BorderFactory.createEmptyBorder(10,10,0,20));
		resBtnThem.setBackground(Color.WHITE);
		btnThem=new Button("Thêm mới"); 
		btnThem.setIcon(iconBtnAdd);
		btnThem.setFont(new Font("Segoe UI",0,16));
		btnThem.setPreferredSize(new Dimension(140,30));
		btnThem.setBackground(new Color(0,102,102));
		btnThem.setForeground(Color.WHITE);
		btnLuu=new Button("Xuất Excel"); 
		btnLuu.setIcon(iconBtnSave);
		btnLuu.setFont(new Font("Segoe UI",0,16));
		btnLuu.setPreferredSize(new Dimension(140,30));
		btnLuu.setBackground(new Color(51,51,255));
		btnLuu.setForeground(Color.WHITE);
		resBtnThem.add(btnThem); resBtnThem.add(btnLuu);
		titleNhanVien=new JLabel("Danh sách ứng viên");
		titleNhanVien.setForeground(Color.WHITE);
		titleNhanVien.setFont(new Font("Segoe UI",1,16));
		titleNhanVien.setBorder(BorderFactory.createEmptyBorder(10,20,0,10));
		danhsachNorthPanel.add(titleNhanVien, BorderLayout.WEST);
		danhsachNorthPanel.add(resBtnThem, BorderLayout.EAST);
		
		danhsachCenterPanel=new GradientRoundPanel();
		danhsachCenterPanel.setLayout(new BoxLayout(danhsachCenterPanel, BoxLayout.PAGE_AXIS));
		danhsachCenterPanel.setBackground(Color.WHITE);
		String[] colName= {"Mã ứng viên","Tên ứng viên","Số điện thoại","Email","Hành động","Hồ sơ"};
		Object[][] data = {
			    {1, "MinhDat", "01234567", "abc@gmail.com",null,null},
			    {2, "ThangDat", "07654321", "def@gmail.com",null,null}
			};
		modelTableUngVien= new DefaultTableModel(data, colName){
			boolean[] canEdit = new boolean [] {
	                false, false, false, false, true, true
	            };
			
            @Override
            public boolean isCellEditable(int row, int column) {
               return canEdit[column];
            }
        };
		tableUngVien=new JTable(modelTableUngVien);
		tableUngVien.getTableHeader().setFont(new Font("Segoe UI",1,14));
		tableUngVien.setFont(new Font("Segoe UI",0,16));
		tableUngVien.setRowHeight(30);
		tableUngVien.setDefaultRenderer(Object.class, new TableCellGradient());
		tableUngVien.setAutoCreateRowSorter(true);
		ArrayList<RowSorter.SortKey> list = new ArrayList<>();
        DefaultRowSorter sorter = ((DefaultRowSorter)tableUngVien.getRowSorter());
        sorter.setSortsOnUpdates(true);
        list.add( new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sorter.setSortKeys(list);
        sorter.sort();
		scrollUngVien=new JScrollPane(tableUngVien);
		scrollUngVien.setBorder(BorderFactory.createLineBorder(new Color(0,191,165)));
		GradientRoundPanel resScroll=new GradientRoundPanel();
		resScroll.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));
		resScroll.setLayout(new BoxLayout(resScroll, BoxLayout.PAGE_AXIS));
		resScroll.setBackground(Color.WHITE);
		resScroll.add(scrollUngVien);
		danhsachCenterPanel.add(resScroll);
		
		danhsachPanel.add(danhsachNorthPanel, BorderLayout.NORTH);
		danhsachPanel.add(danhsachCenterPanel, BorderLayout.CENTER);
		
		centerPanelUngVien.add(timkiemPanel, BorderLayout.NORTH);
		centerPanelUngVien.add(danhsachPanel, BorderLayout.CENTER);
		
		ungvienPanel.add(centerPanelUngVien, BorderLayout.CENTER);
	}
	
	public void addTableActionEvent() {
		TableActionEvent event=new TableActionEvent() {
			@Override
			public void onUpdate(int row) {
				// TODO Auto-generated method stub
				UngVien ungvien=ungvienDAO.getUngVien(tableUngVien.getValueAt(row, 0).toString());
				new ThemSuaUngVienDialog(parent, rootPaneCheckingEnabled, ungvien).setVisible(true);
			}
			
			@Override
			public void onDelete(int row) {
				// TODO Auto-generated method stub
				UngVien uv=ungvienDAO.getUngVien(tableUngVien.getValueAt(row, 0).toString());
				int check=JOptionPane.showConfirmDialog(rootPane, "Có chắc chắn xóa?");
				if(check==JOptionPane.OK_OPTION) {
					ungvienDAO.delete(uv.getMaUV());
					JOptionPane.showMessageDialog(rootPane, "Xóa ứng viên thành công");
					updateTable();
				}
			}

			@Override
			public void onViewHoSo(int row) {
				// TODO Auto-generated method stub
				UngVien uv=ungvienDAO.getUngVien(tableUngVien.getValueAt(row, 0).toString());
				new DanhSachHoSoDialog(parent, rootPaneCheckingEnabled, uv).setVisible(true);
			}

			@Override
			public void onCreateHoSo(int row) {
				// TODO Auto-generated method stub
				UngVien uv=ungvienDAO.getUngVien(tableUngVien.getValueAt(row, 0).toString());
				new TaoSuaHoSoDialog(parent, rootPaneCheckingEnabled, uv, userName).setVisible(true);
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
				
			}
		};
		
		tableUngVien.getColumnModel().getColumn(4).setCellRenderer(new TableCellRendererUpdateDelete());
		tableUngVien.getColumnModel().getColumn(4).setCellEditor(new TableCellEditorUpdateDelete(event));
		
		tableUngVien.getColumnModel().getColumn(5).setCellRenderer(new TableCellRendererViewCreateHoSo());
		tableUngVien.getColumnModel().getColumn(5).setCellEditor(new TableCellEditorViewCreateHoSo(event));
	}
	
	public JPanel getPanel() {
		return this.ungvienPanel;
	}
	
//	Lấy dữ liệu từ sql
	public void loadData() {
		ungvienDAO.setListUngVien(ungvienDAO.getDSUngVien());
	}
	
//	Load dữ liệu lên bảng
	public void loadDataTable() {
		modelTableUngVien.setRowCount(0);
		for(UngVien i: ungvienDAO.getListUngVien()) {
			Object[] obj=new Object[] {
					i.getMaUV(), i.getTenUV(), i.getSoDienThoai(), i.getEmail(),
					null, null
			};
			modelTableUngVien.addRow(obj);
		}

	}
	
//	Load lại dữ liệu bảng khi cập nhật ứng viên
	public void updateTable() {
		loadData();
		loadDataTable();
	}
	
//	option tìm kiếm
//	1: tìm kiếm ứng viên theo tên
//	2: tìm kiếm ứng viên theo số điện thoại
//	3: tìm kiếm ứng viên theo tên và số điện thoại
//	Tìm kiếm nhân viên
	public void timkiem() {
		if(!timkiemTenText.getText().equals("Nhập dữ liệu") && timkiemSDTText.getText().equals("Nhập dữ liệu")) {
			ungvienDAO.getListUngVien().clear();
			String key=timkiemTenText.getText().trim();
			ungvienDAO.setListUngVien(ungvienDAO.getUngVienBy(key,1));
			loadDataTable();
			JOptionPane.showMessageDialog(rootPane, "Tìm thấy "+ungvienDAO.getListUngVien().size()+" ứng viên");
		}
		else if(timkiemTenText.getText().equals("Nhập dữ liệu")
				&& !timkiemSDTText.getText().equals("Nhập dữ liệu")) {
			ungvienDAO.getListUngVien().clear();
			String key=timkiemSDTText.getText().trim();
			ungvienDAO.setListUngVien(ungvienDAO.getUngVienBy(key,2));
			loadDataTable();
			JOptionPane.showMessageDialog(rootPane, "Tìm thấy "+ungvienDAO.getListUngVien().size()+" ứng viên");
		}
		else if(!timkiemTenText.getText().equals("Nhập dữ liệu")
				&& !timkiemSDTText.getText().equals("Nhập dữ liệu")) {
			ungvienDAO.getListUngVien().clear();
			String key=timkiemTenText.getText().trim()+"/"+timkiemSDTText.getText().trim();
			ungvienDAO.setListUngVien(ungvienDAO.getUngVienBy(key,3));
			loadDataTable();
			JOptionPane.showMessageDialog(rootPane, "Tìm thấy "+ungvienDAO.getListUngVien().size()+" ứng viên");
		}
		else {
			JOptionPane.showMessageDialog(rootPane, "Nhập tên hoặc số điện thoại ứng viên để tìm kiếm");
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
		btnTimKiem.addActionListener(this);
		btnLamLai.addActionListener(this);
		btnLuu.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		var obj=e.getSource();
		
		if(obj.equals(btnThem)) {
			new ThemSuaUngVienDialog(this, rootPaneCheckingEnabled).setVisible(true);
		}
		else if(obj.equals(btnTimKiem)) {
			timkiem();
		}
		else if(obj.equals(btnLamLai)) {
			addPlaceHolder(timkiemTenText);
			addPlaceHolder(timkiemSDTText);
			
			loadData();
			loadDataTable();
		}
		else if(obj.equals(btnLuu)) {
			ExcelHelper excel=new ExcelHelper();
			excel.exportData(this, tableUngVien, 2);
		}
	}
	
	public void addMouseListener() {
		
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
