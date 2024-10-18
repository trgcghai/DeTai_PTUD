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

import component.GradientRoundPanel;
import component.RoundPanel;
import controller.ComboBoxRenderer;
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
import entity.Customer;
import entity.NhanVien;
import exception.checkBirthday;
import exception.checkDateOfWork;
import exception.checkName;
import exception.checkPhone;
import exception.checkUserName;
import exception.checkUserPass;

public class TaiKhoanFrame extends JFrame implements ActionListener, MouseListener, FocusListener {

	String userName;
	TaiKhoanFrame parent;
	
//	Component danh sách tài khoản
	JPanel leftPanel,menuPanel,
		taikhoanPanel,northPanelTaiKhoan, centerPanelTaiKhoan;
	JLabel userLabel, iconUserLabel,timkiemTenLabel, titleTaiKhoan,vaitroLeftLabel;
	JTextField timkiemTenText;
	JButton btnTimKiem, btnLamLai,btnLuu;
	JTable tableTaiKhoan;
	DefaultTableModel modelTableTaiKhoan;
	JScrollPane scrollTaiKhoan;
	Icon iconBtnSave;
	
	GradientRoundPanel timkiemPanel,
	danhsachPanel, danhsachNorthPanel, danhsachCenterPanel;
	
	public TaiKhoanFrame(String userName) {
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
		taikhoanPanel=new JPanel(); 
		taikhoanPanel.setLayout(new BorderLayout(5,5));
		taikhoanPanel.setBackground(new Color(220, 220, 220));
		
//		Hiển thị tìm kiếm và danh sách tài khoản
		centerPanelTaiKhoan=new JPanel();
		centerPanelTaiKhoan.setLayout(new BorderLayout(10, 10));
		centerPanelTaiKhoan.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		centerPanelTaiKhoan.setBackground(new Color(89, 145, 144));
//		Tìm kiếm tài khoản
		timkiemPanel=new GradientRoundPanel();
		timkiemPanel.setBackground(Color.WHITE);
		timkiemPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 5));
		
		timkiemTenLabel=new JLabel("Tên đăng nhập:"); 
		timkiemTenLabel.setFont(new Font("Segoe UI",1,16));
		timkiemTenLabel.setForeground(Color.WHITE);
		timkiemTenText=new JTextField(15); 
		timkiemTenText.setFont(new Font("Segoe UI",0,16));
		timkiemTenLabel.setForeground(Color.WHITE);
		timkiemTenText.setOpaque(false);
		
		btnTimKiem=new JButton("Tìm kiếm"); btnTimKiem.setFont(new Font("Segoe UI",0,16));
		btnTimKiem.setPreferredSize(new Dimension(120,25));
		btnTimKiem.setBackground(new Color(0,102,102));
		btnTimKiem.setForeground(Color.WHITE);
		btnLamLai=new JButton("Làm lại"); btnLamLai.setFont(new Font("Segoe UI",0,16));
		btnLamLai.setPreferredSize(new Dimension(120,25));
		btnLamLai.setBackground(Color.RED);
		btnLamLai.setForeground(Color.WHITE);
		
		timkiemPanel.add(timkiemTenLabel); timkiemPanel.add(timkiemTenText);
		timkiemPanel.add(btnTimKiem); timkiemPanel.add(btnLamLai);
//		Danh sách tài khoản
		danhsachPanel=new GradientRoundPanel();
		danhsachPanel.setBackground(Color.WHITE);
		danhsachPanel.setLayout(new BorderLayout(10, 10));
		
		danhsachNorthPanel=new GradientRoundPanel();
		danhsachNorthPanel.setLayout(new BorderLayout(10,10));
		danhsachNorthPanel.setBackground(Color.WHITE);
		iconBtnSave=new ImageIcon(getClass().getResource("/image/save.png"));
		JPanel resBtn=new JPanel();
		resBtn.setOpaque(false);
		resBtn.setBorder(BorderFactory.createEmptyBorder(10,10,0,20));
		resBtn.setBackground(Color.WHITE);
		btnLuu=new JButton("Xuất Excel", iconBtnSave); btnLuu.setFont(new Font("Segoe UI",0,16));
		btnLuu.setPreferredSize(new Dimension(140,30));
		btnLuu.setBackground(new Color(51,51,255));
		btnLuu.setForeground(Color.WHITE);
		resBtn.add(btnLuu);
		titleTaiKhoan=new JLabel("Danh sách tài khoản nhân viên");
		titleTaiKhoan.setFont(new Font("Segoe UI",1,16));
		titleTaiKhoan.setForeground(Color.WHITE);
		titleTaiKhoan.setBorder(BorderFactory.createEmptyBorder(10,20,0,10));
		danhsachNorthPanel.add(titleTaiKhoan, BorderLayout.WEST);
		danhsachNorthPanel.add(resBtn, BorderLayout.EAST);
		
		danhsachCenterPanel=new GradientRoundPanel();
		danhsachCenterPanel.setLayout(new BoxLayout(danhsachCenterPanel, BoxLayout.PAGE_AXIS));
		danhsachCenterPanel.setBackground(Color.WHITE);
		String[] colName= {"Mã tài khoản","Tên đăng nhập", "Tên nhân viên", "Vai trò","Hành động"};
		Object[][] data = {
			    {1, "MinhDat", "Minh Đạt", "Admin", null},
			    {2, "ThangDat", "Thắng Đạt", "Nhân viên", null}
			};
		modelTableTaiKhoan= new DefaultTableModel(data, colName){
			 boolean[] canEdit = new boolean [] {
		                false, false, false, false, true
		            };
			
            @Override
            public boolean isCellEditable(int row, int column) {
               return canEdit[column];
            }
        };
		tableTaiKhoan=new JTable(modelTableTaiKhoan);
		tableTaiKhoan.getTableHeader().setFont(new Font("Segoe UI",1,14));
		tableTaiKhoan.setFont(new Font("Segoe UI",0,16));
		tableTaiKhoan.setRowHeight(30);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		for(int i=0;i<tableTaiKhoan.getColumnCount()-1;i++) {
			tableTaiKhoan.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);			
		}
		tableTaiKhoan.setAutoCreateRowSorter(true);
		ArrayList<RowSorter.SortKey> list = new ArrayList<>();
        DefaultRowSorter sorter = ((DefaultRowSorter)tableTaiKhoan.getRowSorter());
        sorter.setSortsOnUpdates(true);
        list.add( new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sorter.setSortKeys(list);
        sorter.sort();
		scrollTaiKhoan=new JScrollPane(tableTaiKhoan);
		scrollTaiKhoan.setBorder(BorderFactory.createLineBorder(new Color(0,191,165)));
		GradientRoundPanel resScroll=new GradientRoundPanel();
		resScroll.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));
		resScroll.setLayout(new BoxLayout(resScroll, BoxLayout.PAGE_AXIS));
		resScroll.setBackground(Color.WHITE);
		resScroll.add(scrollTaiKhoan);
		danhsachCenterPanel.add(resScroll);
		
		danhsachPanel.add(danhsachNorthPanel, BorderLayout.NORTH);
		danhsachPanel.add(danhsachCenterPanel, BorderLayout.CENTER);
		
		centerPanelTaiKhoan.add(timkiemPanel, BorderLayout.NORTH);
		centerPanelTaiKhoan.add(danhsachPanel, BorderLayout.CENTER);
		
		taikhoanPanel.add(centerPanelTaiKhoan, BorderLayout.CENTER);
	}
	
	public void addTableActionEvent() {
		TableActionEvent event=new TableActionEvent() {
			@Override
			public void onUpdate(int row) {
				// TODO Auto-generated method stub
				new CapSuaTaiKhoanDialog(parent, rootPaneCheckingEnabled, true).setVisible(true);;
			}
			
			@Override
			public void onDelete(int row) {
				// TODO Auto-generated method stub
				JOptionPane.showMessageDialog(rootPane, "Chức năng xóa tài khoản đang hoàn thiện");
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
				
			}
		};
		
		tableTaiKhoan.getColumnModel().getColumn(4).setCellRenderer(new TableCellRendererUpdateDelete());
		tableTaiKhoan.getColumnModel().getColumn(4).setCellEditor(new TableCellEditorUpdateDelete(event));
	}
	
	public JPanel getPanel() {
		return this.taikhoanPanel;
	}
	
//	Trạng thái text chuột không nằm trong ô
	public void addPlaceHolder(JTextField text) {
		Font font=text.getFont();
		font=font.deriveFont(Font.ITALIC);
		text.setFont(font);
		text.setForeground(Color.WHITE);
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
		
		addPlaceHolder(timkiemTenText);
	}
	
	public void addActionListener() {
		
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}
	
	public void addMouseListener() {
		tableTaiKhoan.addMouseListener(this);
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
