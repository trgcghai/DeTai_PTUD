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
import controller.actiontable.TableCellEditorDetail;
import controller.actiontable.TableCellEditorUpdateDelete;
import controller.actiontable.TableCellEditorViewCreateHoSo;
import controller.actiontable.TableCellRendererDetail;
import controller.actiontable.TableCellRendererUpdateDelete;
import controller.actiontable.TableCellRendererViewCreateHoSo;
import dao.TaiKhoan_DAO;
import dao.NhanVien_DAO;
import entity.TaiKhoan;
import entity.constraint.HinhThucLamViec;
import entity.constraint.NganhNghe;
import entity.constraint.TrangThai;
import entity.constraint.TrinhDo;
import entity.Customer;
import entity.NhanVien;
import exception.checkBirthday;
import exception.checkDateOfWork;
import exception.checkName;
import exception.checkPhone;
import exception.checkUserName;
import exception.checkUserPass;

public class TimViecLamFrame extends JFrame implements ActionListener, MouseListener, FocusListener {
	
	String userName;
	TimViecLamFrame parent;
	
//	Component tìm việc làm
	JPanel leftPanel,menuPanel,
		timviecPanel,northPanelTimViec, centerPanelTimViec, timkiemPanel,
		danhsachPanel, danhsachNorthPanel, danhsachCenterPanel, ngaylapPanel;
	JLabel trinhdoLabel, luongLabel, hinhthucLabel,
		userLabel, iconUserLabel,timkiemTenLabel, timkiemNTDLabel,
		titleHopDong,vaitroLeftLabel, ngaylapLabel, startLabel, endLabel;
	JTextField timkiemTenText, luongText, ungvienText;
	JButton btnTimKiem, btnLamLai, btnLuu;
	JTable tableTimViec;
	DefaultTableModel modelTableTimViec;
	JScrollPane scrollTimViec;
	JComboBox ungvienComBo, trinhdoText, hinhthucText;
	Icon iconBtnSave;
	UtilDateModel modelDateStart, modelDateEnd;
	JDatePanelImpl panelStart, panelEnd;
	JDatePickerImpl startText, endText;
	
	
	public TimViecLamFrame(String userName) {
		setTitle("Tìm việc làm");
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
		add(timviecPanel, BorderLayout.CENTER);
		
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
		timviecPanel=new JPanel(); 
		timviecPanel.setLayout(new BorderLayout(5,5));
		timviecPanel.setBackground(new Color(220, 220, 220));
		
//		Hiển thị tài khoản
		northPanelTimViec=new JPanel();
		northPanelTimViec.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
		northPanelTimViec.setBackground(new Color(220, 220, 220));
		
		userLabel=new JLabel();
		userLabel.setFont(new Font("Segoe UI",0,16));
		userLabel.setText("Welcome "+userName);
		iconUserLabel=new JLabel();
		iconUserLabel.setIcon(new ImageIcon(getClass().getResource("/image/user.png")));
		
		northPanelTimViec.add(userLabel); northPanelTimViec.add(iconUserLabel);
		
//		Hiển thị tìm kiếm và danh sách việc làm
		centerPanelTimViec=new JPanel();
		centerPanelTimViec.setLayout(new BorderLayout(10, 10));
		centerPanelTimViec.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		centerPanelTimViec.setBackground(new Color(220, 220, 220));
//		Tìm kiếm việc làm
		timkiemPanel=new JPanel();
		timkiemPanel.setBackground(Color.WHITE);
		timkiemPanel.setLayout(new BorderLayout(5,5));
		
		JPanel resSearch=new JPanel(); resSearch.setLayout(new GridBagLayout());
		resSearch.setBackground(Color.WHITE);
		
		GridBagConstraints gbc= new GridBagConstraints();
		gbc.gridx=0; gbc.gridy=0; gbc.insets=new Insets(5, 10, 5, 10); gbc.anchor=GridBagConstraints.EAST;
		ungvienComBo = new JComboBox<String>();
		ungvienComBo.setFont(new Font("Segoe UI",0,16));
		ungvienComBo.setPreferredSize(new Dimension(150,25));
		ungvienComBo.setRenderer(new ComboBoxRenderer("Chọn ứng viên"));
		ungvienComBo.setSelectedIndex(-1);
		resSearch.add(ungvienComBo, gbc);
		
		gbc.gridx=1;
		ungvienText=new JTextField(15); ungvienText.setFont(new Font("Segoe UI",0,16));
		ungvienText.setEditable(false);
		resSearch.add(ungvienText, gbc);
		
		gbc.gridx=2;
		trinhdoLabel=new JLabel("Trình độ:"); trinhdoLabel.setFont(new Font("Segoe UI",0,16));
		resSearch.add(trinhdoLabel, gbc);
		gbc.gridx=3;
		trinhdoText=new JComboBox(); trinhdoText.setFont(new Font("Segoe UI",0,16));
		TrinhDo[] trinhdos=TrinhDo.class.getEnumConstants();
		for(TrinhDo t: trinhdos) {
			trinhdoText.addItem(t.getValue());
		}
		trinhdoText.setPreferredSize(new Dimension(156,25));
		resSearch.add(trinhdoText, gbc);
		
		gbc.gridx=0; gbc.gridy=1;
		luongLabel=new JLabel("Lương:"); luongLabel.setFont(new Font("Segoe UI",0,16));
		resSearch.add(luongLabel, gbc);
		gbc.gridx=1;
		luongText=new JTextField(15); luongText.setFont(new Font("Segoe UI",0,16));
		resSearch.add(luongText, gbc);
		
		gbc.gridx=2;
		hinhthucLabel=new JLabel("Hình thức làm việc:"); hinhthucLabel.setFont(new Font("Segoe UI",0,16));
		resSearch.add(hinhthucLabel, gbc);
		gbc.gridx=3;
		hinhthucText=new JComboBox(); hinhthucText.setFont(new Font("Segoe UI",0,16));
		HinhThucLamViec[] hinhthucs=HinhThucLamViec.class.getEnumConstants();
		for(HinhThucLamViec h: hinhthucs) {
			hinhthucText.addItem(h.getValue());
		}
		hinhthucText.setPreferredSize(new Dimension(156,25));
		resSearch.add(hinhthucText,gbc);
		
		JPanel resBtnSearch=new JPanel(); resBtnSearch.setLayout(new BorderLayout(0,5));
		resBtnSearch.setBorder(BorderFactory.createEmptyBorder(10,10,10,23));
		resBtnSearch.setBackground(Color.WHITE);
		btnTimKiem=new JButton("Tìm kiếm"); btnTimKiem.setFont(new Font("Segoe UI",0,16));
		btnTimKiem.setPreferredSize(new Dimension(120,25));
		btnTimKiem.setBackground(new Color(0,102,102));
		btnTimKiem.setForeground(Color.WHITE);
		btnLamLai=new JButton("Làm lại"); btnLamLai.setFont(new Font("Segoe UI",0,16));
		btnLamLai.setPreferredSize(new Dimension(120,25));
		btnLamLai.setBackground(Color.RED);
		btnLamLai.setForeground(Color.WHITE);
		resBtnSearch.add(btnTimKiem, BorderLayout.NORTH); resBtnSearch.add(btnLamLai, BorderLayout.SOUTH);
		
		timkiemPanel.add(resSearch, BorderLayout.CENTER);
		timkiemPanel.add(resBtnSearch, BorderLayout.EAST);
		
//		Danh sách việc làm
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
		titleHopDong=new JLabel("Danh sách việc làm thích hợp");
		titleHopDong.setFont(new Font("Segoe UI",1,16));
		titleHopDong.setBorder(BorderFactory.createEmptyBorder(10,20,0,10));
		danhsachNorthPanel.add(titleHopDong, BorderLayout.WEST);
		danhsachNorthPanel.add(resBtn, BorderLayout.EAST);
		
		danhsachCenterPanel=new JPanel();
		danhsachCenterPanel.setLayout(new BoxLayout(danhsachCenterPanel, BoxLayout.PAGE_AXIS));
		danhsachCenterPanel.setBackground(Color.WHITE);
		String[] colName= {"Mã tin tuyển dụng","Tiêu đề","Nhà tuyển dụng","Trình độ","Lương","Hình thức làm việc","Hành động"};
		Object[][] data = {
			    {1, "Technical Project Manager","Facebook", "Đại học","1000","Part time",null},
			    {2, "Manual Tester","Amazon", "Cao đẳng", "500","Full time",null}
			};
		modelTableTimViec= new DefaultTableModel(data, colName){
			boolean[] canEdit = new boolean [] {
	                false, false, false, false, false, false, true
	            };
			
            @Override
            public boolean isCellEditable(int row, int column) {
               return canEdit[column];
            }
        };
		tableTimViec=new JTable(modelTableTimViec);
		tableTimViec.getTableHeader().setFont(new Font("Segoe UI",1,14));
		tableTimViec.setFont(new Font("Segoe UI",0,16));
		tableTimViec.setRowHeight(30);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		for(int i=0;i<tableTimViec.getColumnCount()-1;i++) {
			tableTimViec.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);			
		}
		tableTimViec.setAutoCreateRowSorter(true);
		ArrayList<RowSorter.SortKey> list = new ArrayList<>();
        DefaultRowSorter sorter = ((DefaultRowSorter)tableTimViec.getRowSorter());
        sorter.setSortsOnUpdates(true);
        list.add( new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sorter.setSortKeys(list);
        sorter.sort();
		scrollTimViec=new JScrollPane(tableTimViec);
		scrollTimViec.setBorder(BorderFactory.createLineBorder(new Color(0,191,165)));
		JPanel resScroll=new JPanel();
		resScroll.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));
		resScroll.setLayout(new BoxLayout(resScroll, BoxLayout.PAGE_AXIS));
		resScroll.setBackground(Color.WHITE);
		resScroll.add(scrollTimViec);
		danhsachCenterPanel.add(resScroll);
		
		danhsachPanel.add(danhsachNorthPanel, BorderLayout.NORTH);
		danhsachPanel.add(danhsachCenterPanel, BorderLayout.CENTER);
		
		centerPanelTimViec.add(timkiemPanel, BorderLayout.NORTH);
		centerPanelTimViec.add(danhsachPanel, BorderLayout.CENTER);
		
		
		timviecPanel.add(northPanelTimViec, BorderLayout.NORTH);
		timviecPanel.add(centerPanelTimViec, BorderLayout.CENTER);
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
				new ChiTietViecLamDialog(parent, rootPaneCheckingEnabled).setVisible(true);
			}
		};
		
		tableTimViec.getColumnModel().getColumn(6).setCellRenderer(new TableCellRendererDetail());
		tableTimViec.getColumnModel().getColumn(6).setCellEditor(new TableCellEditorDetail(event));
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
		ungvienText.addFocusListener(this);
		luongText.addFocusListener(this);
		
		addPlaceHolder(ungvienText);
		addPlaceHolder(luongText);
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
		if(obj.equals(ungvienText)) {
			if(ungvienText.getText().equals("Hiển thị email ứng viên")) {
				ungvienText.setText(null);
				ungvienText.requestFocus();
				
				removePlaceHolder(ungvienText);
			}
		}
		else if(obj.equals(luongText)) {
			if(luongText.getText().equals("Nhập dữ liệu")) {
				luongText.setText(null);
				luongText.requestFocus();
				
				removePlaceHolder(luongText);
			}
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		var obj=e.getSource();
		if(obj.equals(ungvienText)) {
			if(ungvienText.getText().length()==0) {
				addPlaceHolder(ungvienText);
				ungvienText.setText("Hiển thị email ứng viên");
			}
		}
		else if(obj.equals(luongText)) {
			if(luongText.getText().length()==0) {
				addPlaceHolder(luongText);
				luongText.setText("Nhập dữ liệu");
			}
		}
	}

}
