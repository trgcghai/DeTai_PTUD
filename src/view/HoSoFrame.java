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
import controller.actiontable.TableActionEvent;
import controller.actiontable.TableCellEditorTimViecLam;
import controller.actiontable.TableCellEditorUpdateDelete;
import controller.actiontable.TableCellEditorViewCreateHoSo;
import controller.actiontable.TableCellRendererTimViecLam;
import controller.actiontable.TableCellRendererUpdateDelete;
import controller.actiontable.TableCellRendererViewCreateHoSo;
import dao.TaiKhoan_DAO;
import dao.NhanVien_DAO;
import entity.TaiKhoan;
import entity.constraint.TrangThai;
import entity.Customer;
import entity.NhanVien;
import exception.checkBirthday;
import exception.checkDateOfWork;
import exception.checkName;
import exception.checkPhone;
import exception.checkUserName;
import exception.checkUserPass;

public class HoSoFrame extends JFrame implements ActionListener, MouseListener, FocusListener {
	
	String userName;
	HoSoFrame parent;
	
//	Component danh sách hồ sơ
	JPanel leftPanel,menuPanel,
		hosoPanel,northPanelHoSo, centerPanelHoSo, timkiemPanel,
		danhsachPanel, danhsachNorthPanel, danhsachCenterPanel;
	JLabel userLabel, iconUserLabel,timkiemTenLabel, timkiemTrangThaiLabel, titleHoSo,vaitroLeftLabel;
	JTextField timkiemTenText;
	JButton btnTimKiem, btnLamLai, btnLuu;
	JTable tableHoSo;
	DefaultTableModel modelTableHoSo;
	JScrollPane scrollHoSo;
	JComboBox timkiemTrangThaiText;
	Icon iconBtnSave;
	
	
	public HoSoFrame(String userName) {
		setTitle("Hồ sơ");
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
		add(hosoPanel, BorderLayout.CENTER);
		
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
		hosoPanel=new JPanel(); 
		hosoPanel.setLayout(new BorderLayout(5,5));
		hosoPanel.setBackground(new Color(220, 220, 220));
		
//		Hiển thị tài khoản
		northPanelHoSo=new JPanel();
		northPanelHoSo.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
		northPanelHoSo.setBackground(new Color(220, 220, 220));
		
		userLabel=new JLabel();
		userLabel.setFont(new Font("Segoe UI",0,16));
		userLabel.setText("Welcome "+userName);
		iconUserLabel=new JLabel();
		iconUserLabel.setIcon(new ImageIcon(getClass().getResource("/image/user.png")));
		
		northPanelHoSo.add(userLabel); northPanelHoSo.add(iconUserLabel);
		
//		Hiển thị tìm kiếm và danh sách hồ sơ
		centerPanelHoSo=new JPanel();
		centerPanelHoSo.setLayout(new BorderLayout(10, 10));
		centerPanelHoSo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		centerPanelHoSo.setBackground(new Color(220, 220, 220));
//		Tìm kiếm hồ sơ
		timkiemPanel=new JPanel();
		timkiemPanel.setBackground(Color.WHITE);
		timkiemPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 5));
		
		timkiemTenLabel=new JLabel("Tên:"); timkiemTenLabel.setFont(new Font("Segoe UI",0,16));
		timkiemTenText=new JTextField(15); timkiemTenText.setFont(new Font("Segoe UI",0,16));
		timkiemTrangThaiLabel=new JLabel("Trạng thái:"); timkiemTrangThaiLabel.setFont(new Font("Segoe UI",0,16));
		timkiemTrangThaiText=new JComboBox(); 
		TrangThai[] trangthais=TrangThai.class.getEnumConstants();
		for(TrangThai t: trangthais) {
			timkiemTrangThaiText.addItem(t.getValue());
		}
		timkiemTrangThaiText.setFont(new Font("Segoe UI",0,16));
		timkiemTrangThaiText.setPreferredSize(new Dimension(156,26));
		
		btnTimKiem=new JButton("Tìm kiếm"); btnTimKiem.setFont(new Font("Segoe UI",0,16));
		btnTimKiem.setPreferredSize(new Dimension(120,25));
		btnTimKiem.setBackground(new Color(0,102,102));
		btnTimKiem.setForeground(Color.WHITE);
		btnLamLai=new JButton("Làm lại"); btnLamLai.setFont(new Font("Segoe UI",0,16));
		btnLamLai.setPreferredSize(new Dimension(120,25));
		btnLamLai.setBackground(Color.RED);
		btnLamLai.setForeground(Color.WHITE);
		
		timkiemPanel.add(timkiemTenLabel); timkiemPanel.add(timkiemTenText);
		timkiemPanel.add(timkiemTrangThaiLabel); timkiemPanel.add(timkiemTrangThaiText);
		timkiemPanel.add(btnTimKiem); timkiemPanel.add(btnLamLai);
//		Danh sách hồ sơ
		danhsachPanel=new JPanel();
		danhsachPanel.setBackground(Color.WHITE);
		danhsachPanel.setLayout(new BorderLayout(10, 10));
		
		danhsachNorthPanel=new JPanel();
		danhsachNorthPanel.setLayout(new BorderLayout(10,10));
		danhsachNorthPanel.setBackground(Color.WHITE);
		iconBtnSave=new ImageIcon(getClass().getResource("/image/save.png"));
		JPanel resBtn=new JPanel();
		resBtn.setBorder(BorderFactory.createEmptyBorder(10,10,0,20));
		resBtn.setBackground(Color.WHITE);
		btnLuu=new JButton("Xuất Excel", iconBtnSave); btnLuu.setFont(new Font("Segoe UI",0,16));
		btnLuu.setPreferredSize(new Dimension(140,30));
		btnLuu.setBackground(new Color(51,51,255));
		btnLuu.setForeground(Color.WHITE);
		resBtn.add(btnLuu);
		titleHoSo=new JLabel("Danh sách hồ sơ");
		titleHoSo.setFont(new Font("Segoe UI",1,16));
		titleHoSo.setBorder(BorderFactory.createEmptyBorder(10,20,0,10));
		danhsachNorthPanel.add(titleHoSo, BorderLayout.WEST);
		danhsachNorthPanel.add(resBtn, BorderLayout.EAST);
		
		danhsachCenterPanel=new JPanel();
		danhsachCenterPanel.setLayout(new BoxLayout(danhsachCenterPanel, BoxLayout.PAGE_AXIS));
		danhsachCenterPanel.setBackground(Color.WHITE);
		String[] colName= {"Mã hồ sơ","Trạng thái","Tên ứng viên","Nhà tuyển dụng","Tin tuyển dụng","Hành động","Tìm việc làm"};
		Object[][] data = {
			    {1, "Chờ", "Minh Đạt", "Amazon", "abc",null, null},
			    {2, "Chờ", "Thắng Đạt", "Facebook", "xyz",null, null}
			};
		modelTableHoSo= new DefaultTableModel(data, colName){
			boolean[] canEdit = new boolean [] {
	                false, false, false, false, false, true, true
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
		for(int i=0;i<tableHoSo.getColumnCount()-2;i++) {
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
		danhsachCenterPanel.add(resScroll);
		
		danhsachPanel.add(danhsachNorthPanel, BorderLayout.NORTH);
		danhsachPanel.add(danhsachCenterPanel, BorderLayout.CENTER);
		
		centerPanelHoSo.add(timkiemPanel, BorderLayout.NORTH);
		centerPanelHoSo.add(danhsachPanel, BorderLayout.CENTER);
		
		
		hosoPanel.add(northPanelHoSo, BorderLayout.NORTH);
		hosoPanel.add(centerPanelHoSo, BorderLayout.CENTER);
	}
	
	public void addTableActionEvent() {
		TableActionEvent event=new TableActionEvent() {
			@Override
			public void onUpdate(int row) {
				// TODO Auto-generated method stub
				new TaoSuaHoSoDialog(parent, rootPaneCheckingEnabled, true).setVisible(true);
			}
			
			@Override
			public void onDelete(int row) {
				// TODO Auto-generated method stub
				JOptionPane.showMessageDialog(rootPane, "Chức năng xóa hồ sơ đang hoàn thiện");
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
				JOptionPane.showMessageDialog(rootPane, "Chức năng tìm việc làm đang hoàn thiện");
			}
		};
		
		tableHoSo.getColumnModel().getColumn(5).setCellRenderer(new TableCellRendererUpdateDelete());
		tableHoSo.getColumnModel().getColumn(5).setCellEditor(new TableCellEditorUpdateDelete(event));
		
		tableHoSo.getColumnModel().getColumn(6).setCellRenderer(new TableCellRendererTimViecLam());
		tableHoSo.getColumnModel().getColumn(6).setCellEditor(new TableCellEditorTimViecLam(event));
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
		
		addPlaceHolder(timkiemTenText);
	}
	
	public void addActionListener() {
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		var obj=e.getSource();
		
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
	}

}