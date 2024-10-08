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

public class HopDongFrame extends JFrame implements ActionListener, MouseListener, FocusListener {
	
	String userName;
	HopDongFrame parent;
	
//	Component danh sách hợp đồng
	JPanel leftPanel,menuPanel,
		hopdongPanel,northPanelHopDong, centerPanelHopDong, timkiemPanel,
		danhsachPanel, danhsachNorthPanel, danhsachCenterPanel, ngaylapPanel;
	JLabel userLabel, iconUserLabel,timkiemTenLabel, timkiemNTDLabel,
		titleHopDong,vaitroLeftLabel, ngaylapLabel, startLabel, endLabel;
	JTextField timkiemTenText;
	JButton btnTimKiem, btnLamLai, btnLuu;
	JTable tableHopDong;
	DefaultTableModel modelTableHopDong;
	JScrollPane scrollHopDong;
	JComboBox timkiemNTDText, timkiemTrinhDoText;
	Icon iconBtnSave;
	UtilDateModel modelDateStart, modelDateEnd;
	JDatePanelImpl panelStart, panelEnd;
	JDatePickerImpl startText, endText;
	
	
	public HopDongFrame(String userName) {
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
		
	}
	
	public void initComponent() {
		hopdongPanel=new JPanel(); 
		hopdongPanel.setLayout(new BorderLayout(5,5));
		hopdongPanel.setBackground(new Color(220, 220, 220));
		
//		Hiển thị tìm kiếm và danh sách tin tuyển dụng
		centerPanelHopDong=new JPanel();
		centerPanelHopDong.setLayout(new BorderLayout(10, 10));
		centerPanelHopDong.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		centerPanelHopDong.setBackground(new Color(220, 220, 220));
//		Tìm kiếm tin tuyển dụng
		timkiemPanel=new JPanel();
		timkiemPanel.setBackground(Color.WHITE);
		timkiemPanel.setLayout(new BorderLayout(5,5));
		
		JPanel resSearch=new JPanel(); resSearch.setLayout(new GridBagLayout());
		resSearch.setBackground(Color.WHITE);
		GridBagConstraints gbc= new GridBagConstraints();
		gbc.gridx=0; gbc.gridy=0; gbc.insets=new Insets(5, 10, 5, 10); gbc.anchor=GridBagConstraints.EAST;
		timkiemTenLabel=new JLabel("Tên ứng viên:"); timkiemTenLabel.setFont(new Font("Segoe UI",0,16));
		resSearch.add(timkiemTenLabel, gbc);
		gbc.gridx=1; gbc.gridy=0; gbc.anchor=GridBagConstraints.WEST; gbc.gridwidth=2;
		timkiemTenText=new JTextField(15); timkiemTenText.setFont(new Font("Segoe UI",0,16));
		resSearch.add(timkiemTenText, gbc);
		
		gbc.gridx=3; gbc.gridy=0; gbc.anchor=GridBagConstraints.EAST;  gbc.gridwidth=1;
		timkiemNTDLabel=new JLabel("Nhà tuyển dụng:"); timkiemNTDLabel.setFont(new Font("Segoe UI",0,16));
		resSearch.add(timkiemNTDLabel, gbc);
		gbc.gridx=4; gbc.gridy=0; gbc.anchor=GridBagConstraints.WEST;
		timkiemNTDText=new JComboBox(); timkiemNTDText.setFont(new Font("Segoe UI",0,16));
		timkiemNTDText.setPreferredSize(new Dimension(213,26));
		resSearch.add(timkiemNTDText, gbc);
		
		gbc.gridx=0; gbc.gridy=1; gbc.anchor=GridBagConstraints.EAST;
		ngaylapLabel=new JLabel("Ngày lập:"); ngaylapLabel.setFont(new Font("Segoe UI",0,16));
		resSearch.add(ngaylapLabel, gbc);
		
		gbc.gridx=1; gbc.gridy=1; gbc.anchor=GridBagConstraints.EAST;
		startLabel=new JLabel("Bắt đầu"); startLabel.setFont(new Font("Segoe UI",0,16));
		resSearch.add(startLabel, gbc);
		gbc.gridx=2; gbc.gridy=1; gbc.anchor=GridBagConstraints.WEST;
		modelDateStart=new UtilDateModel();
		modelDateStart.setSelected(true);
		Properties p=new Properties();
		p.put("text.day", "Day"); p.put("text.month", "Month"); p.put("text.year","Year");
		panelStart=new JDatePanelImpl(modelDateStart, p);
		startText=new JDatePickerImpl(panelStart, new LabelDateFormatter());
		startText.setPreferredSize(new Dimension(140, 24));
		resSearch.add(startText, gbc);
		
		gbc.gridx=3; gbc.gridy=1; gbc.anchor=GridBagConstraints.EAST;
		endLabel=new JLabel("Kết thúc"); endLabel.setFont(new Font("Segoe UI",0,16));
		resSearch.add(endLabel, gbc);
		gbc.gridx=4; gbc.gridy=1; gbc.anchor=GridBagConstraints.WEST;
		modelDateEnd=new UtilDateModel();
		modelDateEnd.setSelected(true);
		Properties q=new Properties();
		q.put("text.day", "Day"); q.put("text.month", "Month"); q.put("text.year","Year");
		panelEnd=new JDatePanelImpl(modelDateEnd, q);
		endText=new JDatePickerImpl(panelEnd, new LabelDateFormatter());
		endText.setPreferredSize(new Dimension(140, 24));
		resSearch.add(endText, gbc);
		
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
		
//		Danh sách hợp đồng
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
		titleHopDong=new JLabel("Danh sách hợp đồng");
		titleHopDong.setFont(new Font("Segoe UI",1,16));
		titleHopDong.setBorder(BorderFactory.createEmptyBorder(10,20,0,10));
		danhsachNorthPanel.add(titleHopDong, BorderLayout.WEST);
		danhsachNorthPanel.add(resBtn, BorderLayout.EAST);
		
		danhsachCenterPanel=new JPanel();
		danhsachCenterPanel.setLayout(new BoxLayout(danhsachCenterPanel, BoxLayout.PAGE_AXIS));
		danhsachCenterPanel.setBackground(Color.WHITE);
		String[] colName= {"Mã hợp đồng","Tên ứng viên","Số điện thoại","Nhà tuyển dụng","Phí dịch vụ","Ngày lập","Hành động"};
		Object[][] data = {
			    {1, "Minh Đạt", "0123456789", "Facebook", "1000", "15/12/2023",null},
			    {2, "Thắng Đạt", "0987654321", "Amazon", "1000", "15/12/2024",null}
			};
		modelTableHopDong= new DefaultTableModel(data, colName){
			boolean[] canEdit = new boolean [] {
	                false, false, false, false, false, false, true
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
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		for(int i=0;i<tableHopDong.getColumnCount()-1;i++) {
			tableHopDong.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);			
		}
		tableHopDong.setAutoCreateRowSorter(true);
		ArrayList<RowSorter.SortKey> list = new ArrayList<>();
        DefaultRowSorter sorter = ((DefaultRowSorter)tableHopDong.getRowSorter());
        sorter.setSortsOnUpdates(true);
        list.add( new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sorter.setSortKeys(list);
        sorter.sort();
		scrollHopDong=new JScrollPane(tableHopDong);
		scrollHopDong.setBorder(BorderFactory.createLineBorder(new Color(0,191,165)));
		JPanel resScroll=new JPanel();
		resScroll.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));
		resScroll.setLayout(new BoxLayout(resScroll, BoxLayout.PAGE_AXIS));
		resScroll.setBackground(Color.WHITE);
		resScroll.add(scrollHopDong);
		danhsachCenterPanel.add(resScroll);
		
		danhsachPanel.add(danhsachNorthPanel, BorderLayout.NORTH);
		danhsachPanel.add(danhsachCenterPanel, BorderLayout.CENTER);
		
		centerPanelHopDong.add(timkiemPanel, BorderLayout.NORTH);
		centerPanelHopDong.add(danhsachPanel, BorderLayout.CENTER);
		
		hopdongPanel.add(centerPanelHopDong, BorderLayout.CENTER);
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
				new ChiTietHopDongDialog(parent, rootPaneCheckingEnabled).setVisible(true);
			}
		};
		
		tableHopDong.getColumnModel().getColumn(6).setCellRenderer(new TableCellRendererDetail());
		tableHopDong.getColumnModel().getColumn(6).setCellEditor(new TableCellEditorDetail(event));
	}
	
	public JPanel getPanel() {
		return this.hopdongPanel;
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
