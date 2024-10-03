package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultRowSorter;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import controller.LabelDateFormatter;
import controller.UpdateDeleteCellRenderer;

public class ThongKeNhanVienFrame extends JFrame implements ActionListener, MouseListener, FocusListener{
	
//	Thanh menu
	JMenuBar menuBar;
	JPanel imgMain;
	String userName;
//	Nhân viên
	JMenu menuNhanVien;
//	Tài khoản
	JMenu menuTaiKhoan;
//	Ứng viên
	JMenu menuUngVien;
// 	Hồ sơ
	JMenu menuHoSo;
// 	Nhà tuyển dụng
	JMenu menuNhaTuyenDung;
// 	Tin tuyển dụng
	JMenu menuTinTuyenDung;
// 	Hợp đồng
	JMenu menuHopDong;
//	Tìm việc làm
	JMenu menuTimViecLam;
// 	Thống kê
	JMenu menuThongKe;
//	JMenuItem itemTKNhanVien, itemTKCongTy, itemTKHoSo, itemTKTinTuyenDung;
// 	Hệ thống
	JMenu menuUser;
	JMenuItem itemHome, itemLogout;
	
//	Component danh sách nhân viên
	JPanel leftPanel,menuPanel,
		nhanvienPanel,northPanelNhanVien, centerPanelNhanVien, thongkePanel,
		danhsachPanel, danhsachNorthPanel, danhsachCenterPanel;
	JLabel userLabel, iconUserLabel,
		timkiemTenLabel, timkiemSDTLabel, titleNhanVien, ngayBatDauLabel, ngayKetThucLabel,
		vaitroLeftLabel,
		idNVLabel, nameLabel, dateLabel, addressLabel, phoneLabel, dateWorkLabel, genderLabel, passLabel, vaitroLabel;
	JTextField timkiemTenText, timkiemSDTText,
		idNVText, nameText, addressText, phoneText, userNameText, passText;
	JDatePickerImpl dateText, dateWorkText;
	UtilDateModel modelDate, modelDateWork;
	JComboBox genderText, timkiemText, timkiem;
	JButton btnXacNhan, btnHuy, btnExcel;
	JTable tableNhanVien;
	DefaultTableModel modelTableNhanVien;
	JScrollPane scrollNhanVien;
	Icon iconBtnAdd;
	JDatePickerImpl ngayBatDau, ngayKetThuc;
	
	public ThongKeNhanVienFrame(String userName) {
		setTitle("Thống kê nhân viên");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		this.userName=userName;
		
//		Tạo menu bar bên trái
		initLeft();
		
//		Tạo component bên phải
		initComponent();
		
//		Thêm vào frame
		add(leftPanel, BorderLayout.WEST);
		add(nhanvienPanel, BorderLayout.CENTER);
		
//		Thêm sự kiện
//		addActionListener();
//		addMouseListener();
//		addFocusListener();
		
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
		
		menuPanel=new JPanel(); 
		menuPanel.setLayout(new BorderLayout()); menuPanel.setBackground(Color.WHITE);
		menuPanel.add(vaitroLeftLabel, BorderLayout.NORTH);
		menuPanel.add(new Navbar(), BorderLayout.CENTER);
		menuPanel.add(res, BorderLayout.SOUTH);
		
		leftPanel.add(menuPanel);
	}
	
	public void initComponent() {
		nhanvienPanel=new JPanel(); 
		nhanvienPanel.setLayout(new BorderLayout(5,5));
		nhanvienPanel.setBackground(new Color(220, 220, 220));
		
//		Hiển thị tài khoản
		northPanelNhanVien=new JPanel();
		northPanelNhanVien.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
		northPanelNhanVien.setBackground(new Color(220, 220, 220));
		
		userLabel=new JLabel();
		userLabel.setFont(new Font("Segoe UI",0,16));
		userLabel.setText("Welcome "+userName);
		iconUserLabel=new JLabel();
		iconUserLabel.setIcon(new ImageIcon(getClass().getResource("/image/user.png")));
		
		northPanelNhanVien.add(userLabel); northPanelNhanVien.add(iconUserLabel);
		
//		Hiển thị tìm kiếm và danh sách nhân viên
		centerPanelNhanVien=new JPanel();
		centerPanelNhanVien.setLayout(new BorderLayout(10, 10));
		centerPanelNhanVien.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		centerPanelNhanVien.setBackground(new Color(220, 220, 220));
//		Tìm kiếm nhân viên
		thongkePanel=new JPanel();
		thongkePanel.setBackground(Color.WHITE);
		thongkePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));
		
		modelDate=new UtilDateModel();
		
		Properties properties = new Properties();
		
		ngayBatDauLabel =new JLabel("Ngày bắt đầu:"); 
		ngayBatDauLabel.setFont(new Font("Segoe UI",1,14));
		properties.put("text.today", "Today");
        properties.put("text.month", "Month");
        properties.put("text.year", "Year");
        JDatePanelImpl panelNgayBatDau = new JDatePanelImpl(modelDate, properties);
        ngayBatDau = new JDatePickerImpl(panelNgayBatDau, new LabelDateFormatter());
//FIX LỖI MẤT VIỀN
        ngayBatDau.setPreferredSize(new Dimension(190, 27)); 
        ngayBatDau.setBorder(BorderFactory.createLineBorder(Color.GRAY)); 
        panelNgayBatDau.revalidate();
        panelNgayBatDau.repaint();
        
        ngayKetThucLabel =new JLabel("Ngày kết thúc:"); 
        ngayKetThucLabel.setFont(new Font("Segoe UI",1,14));
		properties.put("text.today", "Today");
        properties.put("text.month", "Month");
        properties.put("text.year", "Year");
        JDatePanelImpl panelNgayKetThuc = new JDatePanelImpl(modelDate, properties);
        ngayKetThuc = new JDatePickerImpl(panelNgayKetThuc, new LabelDateFormatter());
//FIX LỖI MẤT VIỀN
        ngayKetThuc.setPreferredSize(new Dimension(190, 27)); 
        ngayKetThuc.setBorder(BorderFactory.createLineBorder(Color.GRAY)); 
        panelNgayKetThuc.revalidate();
        panelNgayKetThuc.repaint();
		
		btnXacNhan=new JButton("Xác nhận"); btnXacNhan.setFont(new Font("Segoe UI",0,16));
		btnXacNhan.setPreferredSize(new Dimension(120,25));
		btnXacNhan.setBackground(new Color(0,102,102));
		btnXacNhan.setForeground(Color.WHITE);
		btnHuy=new JButton("Hủy"); btnHuy.setFont(new Font("Segoe UI",0,16));
		btnHuy.setPreferredSize(new Dimension(120,25));
		btnHuy.setBackground(Color.RED);
		btnHuy.setForeground(Color.WHITE);
		btnExcel=new JButton("Xuất Excel"); btnExcel.setFont(new Font("Segoe UI",0,16));
		btnExcel.setPreferredSize(new Dimension(120,25));
		btnExcel.setBackground(new Color(0,102,102));
		btnExcel.setForeground(Color.WHITE);
		
		thongkePanel.add(ngayBatDauLabel); thongkePanel.add(ngayBatDau);
		thongkePanel.add(ngayKetThucLabel); thongkePanel.add(ngayKetThuc);
		thongkePanel.add(btnXacNhan); thongkePanel.add(btnHuy); thongkePanel.add(btnExcel);
		
		
//		Danh sách nhân viên
		danhsachPanel=new JPanel();
		danhsachPanel.setBackground(Color.WHITE);
		danhsachPanel.setLayout(new BorderLayout(10, 10));
		
		danhsachNorthPanel=new JPanel();
		danhsachNorthPanel.setLayout(new BorderLayout(10,10));
		danhsachNorthPanel.setBackground(Color.WHITE);
		iconBtnAdd=new ImageIcon(getClass().getResource("/image/add.png"));
		titleNhanVien=new JLabel("Danh sách nhân viên");
		titleNhanVien.setFont(new Font("Segoe UI",1,16));
		titleNhanVien.setBorder(BorderFactory.createEmptyBorder(10,20,0,10));
		danhsachNorthPanel.add(titleNhanVien, BorderLayout.WEST);
		
		danhsachCenterPanel=new JPanel();
		danhsachCenterPanel.setLayout(new BoxLayout(danhsachCenterPanel, BoxLayout.PAGE_AXIS));
		danhsachCenterPanel.setBackground(Color.WHITE);
		String[] colName= {"Mã nhân viên","Tên nhân viên","Email","Số điện thoại","Địa chỉ","Giới tính","Ngày sinh","Số lượng hồ sơ","Số lượng hợp đồng"};
		Object[][] data = {
			    {1, "MinhDat","a1@gmail.com", "01234567","HCM","Nam","13/12/2003","100","100"},
			    {2, "ThangDat","b1@gmail.com", "01234567","HCM","Nam","13/12/2003","100","100"},
			};
		modelTableNhanVien= new DefaultTableModel(data, colName){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
		tableNhanVien=new JTable(modelTableNhanVien);
		tableNhanVien.getTableHeader().setFont(new Font("Segoe UI",1,14));
		tableNhanVien.setFont(new Font("Segoe UI",0,16));
		tableNhanVien.setRowHeight(30);
		tableNhanVien.getColumnModel().getColumn(5).setCellRenderer(new UpdateDeleteCellRenderer());
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		for(int i=0;i<tableNhanVien.getColumnCount();i++) {
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
		JPanel resScroll=new JPanel();
		resScroll.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));
		resScroll.setLayout(new BoxLayout(resScroll, BoxLayout.PAGE_AXIS));
		resScroll.setBackground(Color.WHITE);
		resScroll.add(scrollNhanVien);
		danhsachCenterPanel.add(resScroll);
		
		danhsachPanel.add(danhsachNorthPanel, BorderLayout.NORTH);
		danhsachPanel.add(danhsachCenterPanel, BorderLayout.CENTER);
		
		centerPanelNhanVien.add(thongkePanel, BorderLayout.NORTH);
		centerPanelNhanVien.add(danhsachPanel, BorderLayout.CENTER);
		
		
		nhanvienPanel.add(northPanelNhanVien, BorderLayout.NORTH);
		nhanvienPanel.add(centerPanelNhanVien, BorderLayout.CENTER);
	}
	
//	Xóa trạng thái text chuột không nằm trong ô
	public void removePlaceHolder(JTextField text) {
		Font font=text.getFont();
		font=font.deriveFont(Font.PLAIN);
		text.setFont(font);
		text.setForeground(Color.BLACK);
	}

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
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
	
//	Trạng thái text chuột không nằm trong ô
	public void addPlaceHolder(JTextField text) {
		Font font=text.getFont();
		font=font.deriveFont(Font.ITALIC);
		text.setFont(font);
		text.setForeground(Color.GRAY);
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
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
