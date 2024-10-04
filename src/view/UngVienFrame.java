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

import controller.ComboBoxRenderer;
import controller.Database;
import controller.ExcelHelper;
import controller.FilterImp;
import controller.LabelDateFormatter;
import controller.action.TableActionEvent;
import controller.action.TableCellEditorUpdateDelete;
import controller.action.TableCellEditorViewCreateHoSo;
import controller.action.TableCellRendererUpdateDelete;
import controller.action.TableCellRendererViewCreateHoSo;
import dao.TaiKhoan_DAO;
import dao.NhanVien_DAO;
import entity.TaiKhoan;
import entity.Customer;
import entity.NhanVien;
import exception.checkBirthday;
import exception.checkDateOfWork;
import exception.checkName;
import exception.checkPhone;
import exception.checkUserName;
import exception.checkUserPass;

public class UngVienFrame extends JFrame implements ActionListener, MouseListener, FocusListener {
	
	String userName;
	UngVienFrame parent;
	
//	Component danh sách ứng viên
	JPanel leftPanel,menuPanel,
		ungvienPanel,northPanelUngVien, centerPanelUngVien, timkiemPanel,
		danhsachPanel, danhsachNorthPanel, danhsachCenterPanel;
	JLabel userLabel, iconUserLabel,timkiemTenLabel, timkiemSDTLabel, titleNhanVien,vaitroLeftLabel;
	JTextField timkiemTenText, timkiemSDTText;
	JButton btnTimKiem, btnLamLai,btnThem,btnLuu;
	JTable tableUngVien;
	DefaultTableModel modelTableUngVien;
	JScrollPane scrollUngVien;
	Icon iconBtnAdd,iconBtnSave;
	
	
	public UngVienFrame(String userName) {
		setTitle("Ứng viên");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		this.userName=userName;
		this.parent=this;
		
//		Tạo menu bar bên trái
		initLeft();
		
//		Tạo component bên phải
		initComponent();
		
//		Thêm update và delete vào table
		addTableActionEvent();
		
//		Thêm vào frame
		add(leftPanel, BorderLayout.WEST);
		add(ungvienPanel, BorderLayout.CENTER);
		
//		Thêm sự kiện
		addActionListener();
		addMouseListener();
		addFocusListener();
		
	}
	
	public void initLeft() {
		leftPanel=new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		leftPanel.setBackground(Color.WHITE);
		
		vaitroLeftLabel=new JLabel("ADMIN", SwingConstants.CENTER);
		vaitroLeftLabel.setFont(new Font("Segoe UI",0,16));
		vaitroLeftLabel.setPreferredSize(new Dimension(getWidth(), 50));
		
		JPanel res= new JPanel();
		res.setPreferredSize(new Dimension(getWidth(),400));
		res.setBackground(Color.WHITE);
		
		Navbar nav=new Navbar(this);
		
		menuPanel=new JPanel(); 
		menuPanel.setLayout(new BorderLayout()); menuPanel.setBackground(Color.WHITE);
		menuPanel.add(vaitroLeftLabel, BorderLayout.NORTH);
		menuPanel.add(nav, BorderLayout.CENTER);
		menuPanel.add(res, BorderLayout.SOUTH);
		
		leftPanel.add(menuPanel);
	}
	
	public void initComponent() {
		ungvienPanel=new JPanel(); 
		ungvienPanel.setLayout(new BorderLayout(5,5));
		ungvienPanel.setBackground(new Color(220, 220, 220));
		
//		Hiển thị tài khoản
		northPanelUngVien=new JPanel();
		northPanelUngVien.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
		northPanelUngVien.setBackground(new Color(220, 220, 220));
		
		userLabel=new JLabel();
		userLabel.setFont(new Font("Segoe UI",0,16));
		userLabel.setText("Welcome "+userName);
		iconUserLabel=new JLabel();
		iconUserLabel.setIcon(new ImageIcon(getClass().getResource("/image/user.png")));
		
		northPanelUngVien.add(userLabel); northPanelUngVien.add(iconUserLabel);
		
//		Hiển thị tìm kiếm và danh sách ứng viên
		centerPanelUngVien=new JPanel();
		centerPanelUngVien.setLayout(new BorderLayout(10, 10));
		centerPanelUngVien.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		centerPanelUngVien.setBackground(new Color(220, 220, 220));
//		Tìm kiếm ứng viên
		timkiemPanel=new JPanel();
		timkiemPanel.setBackground(Color.WHITE);
		timkiemPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 5));
		
		timkiemTenLabel=new JLabel("Tên:"); timkiemTenLabel.setFont(new Font("Segoe UI",0,16));
		timkiemTenText=new JTextField(15); timkiemTenText.setFont(new Font("Segoe UI",0,16));
		timkiemSDTLabel=new JLabel("Số điện thoại:"); timkiemSDTLabel.setFont(new Font("Segoe UI",0,16));
		timkiemSDTText=new JTextField(15); timkiemSDTText.setFont(new Font("Segoe UI",0,16));
		
		btnTimKiem=new JButton("Tìm kiếm"); btnTimKiem.setFont(new Font("Segoe UI",0,16));
		btnTimKiem.setPreferredSize(new Dimension(120,25));
		btnTimKiem.setBackground(new Color(0,102,102));
		btnTimKiem.setForeground(Color.WHITE);
		btnLamLai=new JButton("Làm lại"); btnLamLai.setFont(new Font("Segoe UI",0,16));
		btnLamLai.setPreferredSize(new Dimension(120,25));
		btnLamLai.setBackground(Color.RED);
		btnLamLai.setForeground(Color.WHITE);
		
		timkiemPanel.add(timkiemTenLabel); timkiemPanel.add(timkiemTenText);
		timkiemPanel.add(timkiemSDTLabel); timkiemPanel.add(timkiemSDTText);
		timkiemPanel.add(btnTimKiem); timkiemPanel.add(btnLamLai);
//		Danh sách ứng viên
		danhsachPanel=new JPanel();
		danhsachPanel.setBackground(Color.WHITE);
		danhsachPanel.setLayout(new BorderLayout(10, 10));
		
		danhsachNorthPanel=new JPanel();
		danhsachNorthPanel.setLayout(new BorderLayout(10,10));
		danhsachNorthPanel.setBackground(Color.WHITE);
		iconBtnAdd=new ImageIcon(getClass().getResource("/image/add.png"));
		iconBtnSave=new ImageIcon(getClass().getResource("/image/save.png"));
		JPanel resBtnThem=new JPanel();
		resBtnThem.setBorder(BorderFactory.createEmptyBorder(10,10,0,20));
		resBtnThem.setBackground(Color.WHITE);
		btnThem=new JButton("Thêm mới", iconBtnAdd); btnThem.setFont(new Font("Segoe UI",0,16));
		btnThem.setPreferredSize(new Dimension(140,30));
		btnThem.setBackground(new Color(0,102,102));
		btnThem.setForeground(Color.WHITE);
		btnLuu=new JButton("Xuất Excel", iconBtnSave); btnLuu.setFont(new Font("Segoe UI",0,16));
		btnLuu.setPreferredSize(new Dimension(140,30));
		btnLuu.setBackground(new Color(51,51,255));
		btnLuu.setForeground(Color.WHITE);
		resBtnThem.add(btnThem); resBtnThem.add(btnLuu);
		titleNhanVien=new JLabel("Danh sách ứng viên");
		titleNhanVien.setFont(new Font("Segoe UI",1,16));
		titleNhanVien.setBorder(BorderFactory.createEmptyBorder(10,20,0,10));
		danhsachNorthPanel.add(titleNhanVien, BorderLayout.WEST);
		danhsachNorthPanel.add(resBtnThem, BorderLayout.EAST);
		
		danhsachCenterPanel=new JPanel();
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
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		for(int i=0;i<tableUngVien.getColumnCount()-2;i++) {
			tableUngVien.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);			
		}
		tableUngVien.setAutoCreateRowSorter(true);
		ArrayList<RowSorter.SortKey> list = new ArrayList<>();
        DefaultRowSorter sorter = ((DefaultRowSorter)tableUngVien.getRowSorter());
        sorter.setSortsOnUpdates(true);
        list.add( new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sorter.setSortKeys(list);
        sorter.sort();
		scrollUngVien=new JScrollPane(tableUngVien);
		scrollUngVien.setBorder(BorderFactory.createLineBorder(new Color(0,191,165)));
		JPanel resScroll=new JPanel();
		resScroll.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));
		resScroll.setLayout(new BoxLayout(resScroll, BoxLayout.PAGE_AXIS));
		resScroll.setBackground(Color.WHITE);
		resScroll.add(scrollUngVien);
		danhsachCenterPanel.add(resScroll);
		
		danhsachPanel.add(danhsachNorthPanel, BorderLayout.NORTH);
		danhsachPanel.add(danhsachCenterPanel, BorderLayout.CENTER);
		
		centerPanelUngVien.add(timkiemPanel, BorderLayout.NORTH);
		centerPanelUngVien.add(danhsachPanel, BorderLayout.CENTER);
		
		
		ungvienPanel.add(northPanelUngVien, BorderLayout.NORTH);
		ungvienPanel.add(centerPanelUngVien, BorderLayout.CENTER);
	}
	
	public void addTableActionEvent() {
		TableActionEvent event=new TableActionEvent() {
			@Override
			public void onUpdate(int row) {
				// TODO Auto-generated method stub
				new ThemSuaUngVienDialog(parent, rootPaneCheckingEnabled, true).setVisible(true);
			}
			
			@Override
			public void onDelete(int row) {
				// TODO Auto-generated method stub
				JOptionPane.showMessageDialog(rootPane, "Chức năng xóa ứng viên đang hoàn thiện");
			}

			@Override
			public void onViewHoSo(int row) {
				// TODO Auto-generated method stub
				JOptionPane.showMessageDialog(rootPane, "Chức năng xem hồ sơ ứng viên đang hoàn thiện");
			}

			@Override
			public void onCreateHoSo(int row) {
				// TODO Auto-generated method stub
				new TaoSuaHoSoDialog(parent, rootPaneCheckingEnabled).setVisible(true);
			}

			@Override
			public void onCreateTaiKhoan(int row) {
				// TODO Auto-generated method stub
				
			}
		};
		
		tableUngVien.getColumnModel().getColumn(4).setCellRenderer(new TableCellRendererUpdateDelete());
		tableUngVien.getColumnModel().getColumn(4).setCellEditor(new TableCellEditorUpdateDelete(event));
		
		tableUngVien.getColumnModel().getColumn(5).setCellRenderer(new TableCellRendererViewCreateHoSo());
		tableUngVien.getColumnModel().getColumn(5).setCellEditor(new TableCellEditorViewCreateHoSo(event));
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
	
//	Listener
	public void addFocusListener() {
		timkiemTenText.addFocusListener(this);
		timkiemSDTText.addFocusListener(this);
		
		addPlaceHolder(timkiemTenText);
		addPlaceHolder(timkiemSDTText);
	}
	
	public void addActionListener() {
		btnThem.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		var obj=e.getSource();
		
		if(obj.equals(btnThem)) {
			new ThemSuaUngVienDialog(this, rootPaneCheckingEnabled).setVisible(true);
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
