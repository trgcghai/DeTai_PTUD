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
import controller.actiontable.TableCellEditorViewCreateTinTuyenDung;
import controller.actiontable.TableCellRendererUpdateDelete;
import controller.actiontable.TableCellRendererViewCreateHoSo;
import controller.actiontable.TableCellRendererViewCreateTinTuyenDung;
import dao.TaiKhoan_DAO;
import dao.TinTuyenDung_DAO;
import dao.NhaTuyenDung_DAO;
import dao.NhanVien_DAO;
import entity.TaiKhoan;
import entity.UngVien;
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
import view.dialog.DanhSachTinTuyenDungDialog;
import view.dialog.TaoSuaTinTuyenDungDialog;
import view.dialog.ThemSuaNhaTuyenDungDialog;

public class NhaTuyenDungFrame extends JFrame implements ActionListener, MouseListener, FocusListener {
	
	NhanVien userName;
	NhaTuyenDungFrame parent;
	
//	Component danh sách nhà tuyển dụng
	JPanel leftPanel,menuPanel,
		nhatuyendungPanel,northPanelNhaTuyenDung, centerPanelNhaTuyenDung;
	JLabel userLabel, iconUserLabel,timkiemTenLabel, timkiemSDTLabel, titleNhaTuyenDung,vaitroLeftLabel;
	JTextField timkiemTenText, timkiemSDTText;
	Button btnTimKiem, btnLamLai,btnThem,btnLuu;
	JTable tableNhaTuyenDung;
	DefaultTableModel modelTableNhaTuyenDung;
	JScrollPane scrollNhaTuyenDung;
	Icon iconBtnAdd,iconBtnSave;
	
	GradientRoundPanel timkiemPanel,
	danhsachPanel, danhsachNorthPanel, danhsachCenterPanel;
	
	private NhaTuyenDung_DAO nhatuyendungDAO;
	private TinTuyenDung_DAO tintuyendungDAO;
	
	public NhaTuyenDungFrame(NhanVien userName) {
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
		
		nhatuyendungDAO=new NhaTuyenDung_DAO();
		tintuyendungDAO=new TinTuyenDung_DAO();
		
		loadData();
		loadDataTable();
	}
	
	public void initComponent() {
		nhatuyendungPanel=new JPanel(); 
		nhatuyendungPanel.setLayout(new BorderLayout(5,5));
		
//		Hiển thị tìm kiếm và danh sách nhà tuyển dụng
		centerPanelNhaTuyenDung=new JPanel();
		centerPanelNhaTuyenDung.setLayout(new BorderLayout(10, 10));
		centerPanelNhaTuyenDung.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		centerPanelNhaTuyenDung.setBackground(new Color(89, 145, 144));
//		Tìm kiếm nhà tuyển dụng
		timkiemPanel=new GradientRoundPanel();
		timkiemPanel.setBackground(Color.WHITE);
		timkiemPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 5));
		
		timkiemTenLabel=new JLabel("Tên nhà tuyển dụng:"); 
		timkiemTenLabel.setFont(new Font("Segoe UI",1,16));
		timkiemTenLabel.setForeground(Color.WHITE);
		timkiemTenText=new JTextField(15); 
		timkiemTenText.setFont(new Font("Segoe UI",0,16));
		timkiemTenText.setForeground(Color.WHITE);
		timkiemTenText.setOpaque(false);
		timkiemSDTLabel=new JLabel("Số điện thoại:"); 
		timkiemSDTLabel.setFont(new Font("Segoe UI",1,16));
		timkiemSDTLabel.setForeground(Color.WHITE);
		timkiemSDTText=new JTextField(15); 
		timkiemSDTText.setForeground(Color.WHITE);
		timkiemSDTText.setOpaque(false);
		timkiemSDTText.setFont(new Font("Segoe UI",0,16));
		
		JPanel resBtnSearch=new JPanel();
		resBtnSearch.setOpaque(false);
		resBtnSearch.setPreferredSize(new Dimension(350, 35));
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
		
		timkiemPanel.add(timkiemTenLabel); timkiemPanel.add(timkiemTenText);
		timkiemPanel.add(timkiemSDTLabel); timkiemPanel.add(timkiemSDTText);
		timkiemPanel.add(resBtnSearch);
//		Danh sách nhà tuyển dụng
		danhsachPanel=new GradientRoundPanel();
		danhsachPanel.setBackground(Color.WHITE);
		danhsachPanel.setLayout(new BorderLayout(10, 10));
		
		danhsachNorthPanel=new GradientRoundPanel();
		danhsachNorthPanel.setLayout(new BorderLayout(10,10));
		danhsachNorthPanel.setBackground(Color.WHITE);
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
		titleNhaTuyenDung=new JLabel("Danh sách nhà tuyển dụng");
		titleNhaTuyenDung.setForeground(Color.WHITE);
		titleNhaTuyenDung.setFont(new Font("Segoe UI",1,16));
		titleNhaTuyenDung.setBorder(BorderFactory.createEmptyBorder(10,20,0,10));
		danhsachNorthPanel.add(titleNhaTuyenDung, BorderLayout.WEST);
		danhsachNorthPanel.add(resBtnThem, BorderLayout.EAST);
		
		danhsachCenterPanel=new GradientRoundPanel();
		danhsachCenterPanel.setLayout(new BoxLayout(danhsachCenterPanel, BoxLayout.PAGE_AXIS));
		danhsachCenterPanel.setBackground(Color.WHITE);
		String[] colName= {"Mã nhà tuyển dụng","Tên nhà tuyển dụng","Địa chỉ","Email", "Số điện thoại", "Hành động","Tin tuyển dụng"};
		Object[][] data = {
			    {1, "MinhDat", "Nha Trang", "abc@gmail.com", "0123456789", null,null},
			    {2, "ThangDat", "Sài Gòn", "def@gmail.com", "0987654321", null,null}
			};
		modelTableNhaTuyenDung= new DefaultTableModel(data, colName){
			boolean[] canEdit = new boolean [] {
	                false, false, false, false, false, true, true
	            };
			
            @Override
            public boolean isCellEditable(int row, int column) {
               return canEdit[column];
            }
        };
		tableNhaTuyenDung=new JTable(modelTableNhaTuyenDung);
		tableNhaTuyenDung.getTableHeader().setFont(new Font("Segoe UI",1,14));
		tableNhaTuyenDung.setFont(new Font("Segoe UI",0,16));
		tableNhaTuyenDung.setRowHeight(30);
		tableNhaTuyenDung.setDefaultRenderer(Object.class, new TableCellGradient());
		
		tableNhaTuyenDung.setAutoCreateRowSorter(true);
		ArrayList<RowSorter.SortKey> list = new ArrayList<>();
		list.add( new RowSorter.SortKey(0, SortOrder.ASCENDING));
        DefaultRowSorter sorter = ((DefaultRowSorter)tableNhaTuyenDung.getRowSorter());
        sorter.setComparator(0, (o1, o2)->{
       	 String str1 = o1.toString().replaceAll("[^0-9]", "");
            String str2 = o2.toString().replaceAll("[^0-9]", "");
            return Integer.compare(Integer.parseInt(str1), Integer.parseInt(str2));
       });
        sorter.setSortsOnUpdates(true);
        sorter.setSortKeys(list);
        sorter.sort();
        
		scrollNhaTuyenDung=new JScrollPane(tableNhaTuyenDung);
		scrollNhaTuyenDung.setBorder(BorderFactory.createLineBorder(new Color(0,191,165)));
		GradientRoundPanel resScroll=new GradientRoundPanel();
		resScroll.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));
		resScroll.setLayout(new BoxLayout(resScroll, BoxLayout.PAGE_AXIS));
		resScroll.setBackground(Color.WHITE);
		resScroll.add(scrollNhaTuyenDung);
		danhsachCenterPanel.add(resScroll);
		
		danhsachPanel.add(danhsachNorthPanel, BorderLayout.NORTH);
		danhsachPanel.add(danhsachCenterPanel, BorderLayout.CENTER);
		
		centerPanelNhaTuyenDung.add(timkiemPanel, BorderLayout.NORTH);
		centerPanelNhaTuyenDung.add(danhsachPanel, BorderLayout.CENTER);
		
		nhatuyendungPanel.add(centerPanelNhaTuyenDung, BorderLayout.CENTER);
	}
	
	public void addTableActionEvent() {
		TableActionEvent event=new TableActionEvent() {
			@Override
			public void onUpdate(int row) {
				// TODO Auto-generated method stub
				NhaTuyenDung ntd= nhatuyendungDAO.getNhaTuyenDung(tableNhaTuyenDung.getValueAt(row, 0).toString());
				new ThemSuaNhaTuyenDungDialog(parent, rootPaneCheckingEnabled, ntd).setVisible(true);
			}
			
			@Override
			public void onDelete(int row) {
				// TODO Auto-generated method stub
				if(tintuyendungDAO.getTinTuyenDungTheoNTD(tableNhaTuyenDung.getValueAt(row, 0).toString(), 1).size() > 0) {
					JOptionPane.showMessageDialog(rootPane, "Không thể xóa nhà tuyển dụng vì đã tạo tin tuyển dụng");
				}
				else {
					int check=JOptionPane.showConfirmDialog(rootPane, "Có chắc chắn xóa?");
					if(check==JOptionPane.OK_OPTION) {
						nhatuyendungDAO.delete(tableNhaTuyenDung.getValueAt(row, 0).toString());
						JOptionPane.showMessageDialog(rootPane, "Xóa nhà tuyển dụng thành công");
						updateTable();
					}
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
				
			}

			@Override
			public void onViewTinTuyenDung(int row) {
				// TODO Auto-generated method stub
				NhaTuyenDung ntd= nhatuyendungDAO.getNhaTuyenDung(tableNhaTuyenDung.getValueAt(row, 0).toString());
				new DanhSachTinTuyenDungDialog(parent, rootPaneCheckingEnabled, ntd).setVisible(true);
			}

			@Override
			public void onCreateTinTuyenDung(int row) {
				// TODO Auto-generated method stub
				NhaTuyenDung ntd= nhatuyendungDAO.getNhaTuyenDung(tableNhaTuyenDung.getValueAt(row, 0).toString());
				new TaoSuaTinTuyenDungDialog(parent, rootPaneCheckingEnabled, ntd).setVisible(true);
			}

			@Override
			public void onViewDetail(int row) {
				// TODO Auto-generated method stub
				
			}
		};
		
		tableNhaTuyenDung.getColumnModel().getColumn(5).setCellRenderer(new TableCellRendererUpdateDelete());
		tableNhaTuyenDung.getColumnModel().getColumn(5).setCellEditor(new TableCellEditorUpdateDelete(event));
		
		tableNhaTuyenDung.getColumnModel().getColumn(6).setCellRenderer(new TableCellRendererViewCreateTinTuyenDung());
		tableNhaTuyenDung.getColumnModel().getColumn(6).setCellEditor(new TableCellEditorViewCreateTinTuyenDung(event));
	}
	
	public JPanel getPanel() {
		return this.nhatuyendungPanel;
	}
	
//	Lấy dữ liệu từ sql
	public void loadData() {
		nhatuyendungDAO.setListNhatuyenDung(nhatuyendungDAO.getDsNhaTuyenDung());
		tintuyendungDAO.setListTinTuyenDung(tintuyendungDAO.getDsTinTuyenDung());
	}
	
//	Load dữ liệu lên bảng
	public void loadDataTable() {
		modelTableNhaTuyenDung.setRowCount(0);
		for(NhaTuyenDung i: nhatuyendungDAO.getListNhatuyenDung()) {
			Object[] obj=new Object[] {
					i.getMaNTD(), i.getTenNTD(), i.getDiaChi(), 
					i.getEmail(), i.getSoDienThoai(),
					null, null
			};
			modelTableNhaTuyenDung.addRow(obj);
		}

	}
	
//	option tìm kiếm
//	1: tìm kiếm nhà tuyển dụng theo tên
//	2: tìm kiếm nhà tuyển dụng theo số điện thoại
//	3: tìm kiếm nhà tuyển dụng theo tên và số điện thoại
	public void timkiem() {
		if(!timkiemTenText.getText().equals("Nhập dữ liệu") && timkiemSDTText.getText().equals("Nhập dữ liệu")) {
			nhatuyendungDAO.getListNhatuyenDung().clear();
			String key=timkiemTenText.getText().trim();
			nhatuyendungDAO.setListNhatuyenDung(nhatuyendungDAO.getNhaTuyenDungBy(key,1));
			loadDataTable();
			JOptionPane.showMessageDialog(rootPane, "Tìm thấy "+nhatuyendungDAO.getListNhatuyenDung().size()+" nhà tuyển dụng");
		}
		else if(timkiemTenText.getText().equals("Nhập dữ liệu")
				&& !timkiemSDTText.getText().equals("Nhập dữ liệu")) {
			nhatuyendungDAO.getListNhatuyenDung().clear();
			String key=timkiemSDTText.getText().trim();
			nhatuyendungDAO.setListNhatuyenDung(nhatuyendungDAO.getNhaTuyenDungBy(key,2));
			loadDataTable();
			JOptionPane.showMessageDialog(rootPane, "Tìm thấy "+nhatuyendungDAO.getListNhatuyenDung().size()+" nhà tuyển dụng");
		}
		else if(!timkiemTenText.getText().equals("Nhập dữ liệu")
				&& !timkiemSDTText.getText().equals("Nhập dữ liệu")) {
			nhatuyendungDAO.getListNhatuyenDung().clear();
			String key=timkiemTenText.getText().trim()+"/"+timkiemSDTText.getText().trim();
			nhatuyendungDAO.setListNhatuyenDung(nhatuyendungDAO.getNhaTuyenDungBy(key,3));
			loadDataTable();
			JOptionPane.showMessageDialog(rootPane, "Tìm thấy "+nhatuyendungDAO.getListNhatuyenDung().size()+" nhà tuyển dụng");
		}
		else {
			JOptionPane.showMessageDialog(rootPane, "Nhập tên hoặc số điện thoại nhà tuyển dung để tìm kiếm");
		}

		addPlaceHolder(timkiemTenText);
		addPlaceHolder(timkiemSDTText);
	}
	
//	Load lại dữ liệu bảng khi cập nhật ứng viên
	public void updateTable() {
		loadData();
		loadDataTable();
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
		btnTimKiem.addActionListener(this);
		btnLamLai.addActionListener(this);
		btnThem.addActionListener(this);
		btnLuu.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		var obj=e.getSource();
		
		if(obj.equals(btnThem)) {
			new ThemSuaNhaTuyenDungDialog(this, rootPaneCheckingEnabled).setVisible(true);
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
			excel.exportData(this, tableNhaTuyenDung, 2);
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
				timkiemTenText.setBorder(BorderFactory.createLineBorder(new Color(0,102,102), 3));
				
				removePlaceHolder(timkiemTenText);
			}
		}
		else if(obj.equals(timkiemSDTText)) {
			if(timkiemSDTText.getText().equals("Nhập dữ liệu")) {
				timkiemSDTText.setText(null);
				timkiemSDTText.requestFocus();
				timkiemSDTText.setBorder(BorderFactory.createLineBorder(new Color(0,102,102), 3));
				
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
			timkiemTenText.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		}
		else if(obj.equals(timkiemSDTText)) {
			if(timkiemSDTText.getText().length()==0) {
				addPlaceHolder(timkiemSDTText);
				timkiemSDTText.setText("Nhập dữ liệu");
			}
			timkiemSDTText.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		}
	}

}
