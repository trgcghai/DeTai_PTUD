package view;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultRowSorter;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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


public class ThongKeNhaTuyenDungFrame extends JFrame implements ActionListener, MouseListener, FocusListener{
	
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
		nhaTuyenDungPanel,northPanelNhanVien, centerPanelNhaTuyenDung, thongkePanel,
		danhsachPanel, danhsachNorthPanel, danhsachCenterPanel;
	JLabel userLabel, iconUserLabel, titleNhaTuyenDung, ngayBatDauLabel, ngayKetThucLabel,
		vaitroLeftLabel;
	JDatePickerImpl dateText;
	UtilDateModel modelDate;
	JButton btnXacNhan, btnHuy, btnExcel;
	JTable tableNhaTuyenDung;
	DefaultTableModel modeltableNhaTuyenDung;
	JScrollPane scrollNhaTuyenDung;
	JDatePickerImpl ngayBatDau, ngayKetThuc;
	
	public ThongKeNhaTuyenDungFrame(String userName) {
		setTitle("Thống kê nhà tuyển dụng");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		this.userName=userName;
		
//		Tạo menu bar bên trái
		initMenu();
		initLeft();
		
//		Tạo component bên phải
		initComponent();
		
//		Thêm vào frame
		add(leftPanel, BorderLayout.WEST);
		add(nhaTuyenDungPanel, BorderLayout.CENTER);
		
//		Thêm sự kiện
//		addActionListener();
//		addMouseListener();
//		addFocusListener();
	}
	
	public void initMenu() {
		menuBar=new JMenuBar();
		menuBar.setLayout(new GridLayout(0, 1));
		menuBar.setBackground(Color.WHITE);
		
		menuNhanVien=new JMenu("Nhân viên");  
		menuNhanVien.setFont(new Font("Segoe UI",0,16)); 
		menuNhanVien.setIcon(new ImageIcon(getClass().getResource("/image/nhanvien.png")));
		
		menuTaiKhoan=new JMenu("Tài khoản"); menuTaiKhoan.setFont(new Font("Segoe UI",0,16));
		menuTaiKhoan.setIcon(new ImageIcon(getClass().getResource("/image/taikhoan.png")));
		
		menuUngVien=new JMenu("Ứng viên");
		menuUngVien.setFont(new Font("Segoe UI",0,16)); 
		menuUngVien.setIcon(new ImageIcon(getClass().getResource("/image/ungvien.png")));
		
		menuHoSo=new JMenu("Hồ sơ");
		menuHoSo.setFont(new Font("Segoe UI",0,16)); 
		menuHoSo.setIcon(new ImageIcon(getClass().getResource("/image/hoso.png")));
		
		menuNhaTuyenDung=new JMenu("Nhà tuyển dụng");
		menuNhaTuyenDung.setFont(new Font("Segoe UI",0,16));
		menuNhaTuyenDung.setIcon(new ImageIcon(getClass().getResource("/image/nhatuyendung.png")));
		
		menuTinTuyenDung=new JMenu("Tin tuyển dụng");
		menuTinTuyenDung.setFont(new Font("Segoe UI",0,16)); 
		menuTinTuyenDung.setIcon(new ImageIcon(getClass().getResource("/image/tintuyendung.png")));
		
		menuHopDong=new JMenu("Hợp đồng");
		menuHopDong.setFont(new Font("Segoe UI",0,16));
		menuHopDong.setIcon(new ImageIcon(getClass().getResource("/image/hopdong.png")));
		
		menuTimViecLam=new JMenu("Tìm việc làm");
		menuTimViecLam.setFont(new Font("Segoe UI",0,16));
		menuTimViecLam.setIcon(new ImageIcon(getClass().getResource("/image/timviec.png")));
		
		menuThongKe=new JMenu("Thống kê");
		menuThongKe.setFont(new Font("Segoe UI",0,16));
		menuThongKe.setIcon(new ImageIcon(getClass().getResource("/image/thongke.png")));
		
		menuBar.add(menuNhanVien);
		menuBar.add(menuTaiKhoan);
		menuBar.add(menuUngVien);
		menuBar.add(menuHoSo);
		menuBar.add(menuNhaTuyenDung);
		menuBar.add(menuTinTuyenDung);
		menuBar.add(menuHopDong);
		menuBar.add(menuTimViecLam);
		menuBar.add(menuThongKe);
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
		menuPanel.add(menuBar, BorderLayout.CENTER);
		menuPanel.add(res, BorderLayout.SOUTH);
		
		leftPanel.add(menuPanel);
	}
	
	public void initComponent() {
		nhaTuyenDungPanel=new JPanel(); 
		nhaTuyenDungPanel.setLayout(new BorderLayout(5,5));
		nhaTuyenDungPanel.setBackground(new Color(220, 220, 220));
		
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
		
//		Hiển thị thống kê và danh sách nhà tuyển dụng
		centerPanelNhaTuyenDung=new JPanel();
		centerPanelNhaTuyenDung.setLayout(new BorderLayout(10, 10));
		centerPanelNhaTuyenDung.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		centerPanelNhaTuyenDung.setBackground(new Color(220, 220, 220));
//		Thống kê nhà tuyển dụng
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
		
		
//		Danh sách nhà tuyển dụng
		danhsachPanel=new JPanel();
		danhsachPanel.setBackground(Color.WHITE);
		danhsachPanel.setLayout(new BorderLayout(10, 10));
		
		danhsachNorthPanel=new JPanel();
		danhsachNorthPanel.setLayout(new BorderLayout(10,10));
		danhsachNorthPanel.setBackground(Color.WHITE);
		titleNhaTuyenDung=new JLabel("Danh sách nhà tuyển dụng");
		titleNhaTuyenDung.setFont(new Font("Segoe UI",1,16));
		titleNhaTuyenDung.setBorder(BorderFactory.createEmptyBorder(10,20,0,10));
		danhsachNorthPanel.add(titleNhaTuyenDung, BorderLayout.WEST);
		
		danhsachCenterPanel=new JPanel();
		danhsachCenterPanel.setLayout(new BoxLayout(danhsachCenterPanel, BoxLayout.PAGE_AXIS));
		danhsachCenterPanel.setBackground(Color.WHITE);
		String[] colName= {"Mã nhà tuyển dụng","Tên nhà tuyển dụng","Email","Số điện thoại","Địa chỉ","Số lượng tin tuyển dụng"};
		Object[][] data = {
			    {1, "MinhDat","a1@gmail.com", "01234567","HCM","100"},
			    {2, "ThangDat","b1@gmail.com", "01234567","HCM","100"},
			};
		modeltableNhaTuyenDung= new DefaultTableModel(data, colName){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
		tableNhaTuyenDung=new JTable(modeltableNhaTuyenDung);
		tableNhaTuyenDung.getTableHeader().setFont(new Font("Segoe UI",1,14));
		tableNhaTuyenDung.setFont(new Font("Segoe UI",0,16));
		tableNhaTuyenDung.setRowHeight(30);
//		tableNhaTuyenDung.getColumnModel().getColumn(5).setCellRenderer(new MultiComponentCellRenderer());
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		for(int i=0;i<tableNhaTuyenDung.getColumnCount();i++) {
			tableNhaTuyenDung.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);			
		}
		tableNhaTuyenDung.setAutoCreateRowSorter(true);
		ArrayList<RowSorter.SortKey> list = new ArrayList<>();
        DefaultRowSorter sorter = ((DefaultRowSorter)tableNhaTuyenDung.getRowSorter());
        sorter.setSortsOnUpdates(true);
        list.add( new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sorter.setSortKeys(list);
        sorter.sort();
		scrollNhaTuyenDung=new JScrollPane(tableNhaTuyenDung);
		scrollNhaTuyenDung.setBorder(BorderFactory.createLineBorder(new Color(0,191,165)));
		JPanel resScroll=new JPanel();
		resScroll.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));
		resScroll.setLayout(new BoxLayout(resScroll, BoxLayout.PAGE_AXIS));
		resScroll.setBackground(Color.WHITE);
		resScroll.add(scrollNhaTuyenDung);
		danhsachCenterPanel.add(resScroll);
		
		danhsachPanel.add(danhsachNorthPanel, BorderLayout.NORTH);
		danhsachPanel.add(danhsachCenterPanel, BorderLayout.CENTER);
		
		centerPanelNhaTuyenDung.add(thongkePanel, BorderLayout.NORTH);
		centerPanelNhaTuyenDung.add(danhsachPanel, BorderLayout.CENTER);
		
		
		nhaTuyenDungPanel.add(northPanelNhanVien, BorderLayout.NORTH);
		nhaTuyenDungPanel.add(centerPanelNhaTuyenDung, BorderLayout.CENTER);
	}
	
//	Xóa trạng thái text chuột không nằm trong ô
	public void removePlaceHolder(JTextField text) {
		Font font=text.getFont();
		font=font.deriveFont(Font.PLAIN);
		text.setFont(font);
		text.setForeground(Color.BLACK);
	}
	
//	Trạng thái text chuột không nằm trong ô
	public void addPlaceHolder(JTextField text) {
		Font font=text.getFont();
		font=font.deriveFont(Font.ITALIC);
		text.setFont(font);
		text.setForeground(Color.GRAY);
	}

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		
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
