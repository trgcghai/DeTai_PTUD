package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.util.ArrayList;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import component.GradientRoundPanel;
import controller.ExcelHelper;
import controller.LabelDateFormatter;
import dao.UngVien_DAO;
import entity.UngVien;

public class ThongKeUngVienFrame extends JFrame implements ActionListener, MouseListener, FocusListener  {
	String userName;
	ThongKeUngVienFrame parent;
	
//	Component thống kê ứng viên
	JPanel menuPanel, timkiemPanel, UngVienPanel, centerPanelUngVien, danhsachPanel, danhsachNorthPanel, danhsachCenterPanel;
	JLabel titleUngVien, ngayBatDauLabel, ngayKetThucLabel;
	JButton btnTimKiem, btnLamLai, btnExcel;
	JTable tableUngVien;
	DefaultTableModel modelTableUngVien;
	JScrollPane scrollUngVien;
	UtilDateModel modelDate;
	JDatePickerImpl ngayBatDau, ngayKetThuc;
	JComboBox<Object> comboBoxNTD, comboBoxUV;
	Icon iconBtnSave;
	
	private UngVien_DAO ungvienDAO;
	
	public ThongKeUngVienFrame(String userName) {
		this.userName = userName;
		this.parent = this;

//		Tạo component bên phải
		initComponent();
		
//		Thêm sự kiện
		addActionListener();
//		addMouseListener();
//		addFocusListener();
		
		ungvienDAO=new UngVien_DAO();
		
		loadData();
		loadDataTable();
	}
	
	public JLabel createLabel(String title) {
		JLabel label = new JLabel(title);
		label.setFont(new Font("Segoe UI",1,16));
		label.setForeground(Color.WHITE);
		return label;
	}
	
	public JButton createButton(String title, Color bgColor, Color fgColor) {
		JButton button = new JButton(title); 
		button.setFont(new Font("Segoe UI",0,16));
		button.setPreferredSize(new Dimension(120,25));
		button.setBackground(bgColor);
		button.setForeground(fgColor);
		return button;
	}
	
	public void initComponent() {
		UngVienPanel=new JPanel(); 
		UngVienPanel.setLayout(new BorderLayout(5,5));
		
//		Hiển thị thống kê và danh sách ứng viên
		centerPanelUngVien=new JPanel();
		centerPanelUngVien.setLayout(new BorderLayout(10, 10));
		centerPanelUngVien.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		centerPanelUngVien.setBackground(new Color(89, 145, 144));
//		Thống kê ứng viên
		timkiemPanel=new GradientRoundPanel();
		timkiemPanel.setBackground(Color.WHITE);
		timkiemPanel.setLayout(new BorderLayout());
		
		JPanel resFormSearch = new JPanel();
		resFormSearch.setOpaque(false);
		resFormSearch.setBackground(Color.WHITE);
		
		modelDate=new UtilDateModel();
		Properties p=new Properties();
		p.put("text.day", "Day"); 
		p.put("text.month", "Month"); 
		p.put("text.year", "Year");
		JDatePanelImpl panelDate=new JDatePanelImpl(modelDate, p);
		
		ngayBatDauLabel= createLabel("Ngày bắt đầu:"); 
		ngayBatDau=new JDatePickerImpl(panelDate, new LabelDateFormatter());
		ngayBatDau.setPreferredSize(new Dimension(150,25));
		resFormSearch.add(ngayBatDauLabel);
		resFormSearch.add(ngayBatDau);
		
		ngayKetThucLabel= createLabel("Ngày kết thúc:"); 
		ngayKetThuc=new JDatePickerImpl(panelDate, new LabelDateFormatter());
		ngayKetThuc.setPreferredSize(new Dimension(150,25));
		resFormSearch.add(ngayKetThucLabel);
		resFormSearch.add(ngayKetThuc);
		
		JPanel resBtnSearch=new JPanel();
		resBtnSearch.setOpaque(false);
		resBtnSearch.setPreferredSize(new Dimension(350, 35));
		resBtnSearch.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 5));
		resBtnSearch.setBackground(Color.WHITE);
		
		btnTimKiem= createButton("Thống kê", new Color(0,102,102), Color.WHITE); 
		btnLamLai= createButton("Hủy", Color.RED, Color.WHITE); 
		resBtnSearch.add(btnTimKiem); 
		resBtnSearch.add(btnLamLai);
		
		timkiemPanel.add(resFormSearch, BorderLayout.WEST);
		timkiemPanel.add(resBtnSearch, BorderLayout.EAST);
		
//		Danh sách ứng viên
		danhsachPanel=new GradientRoundPanel();
		danhsachPanel.setBackground(Color.WHITE);
		danhsachPanel.setLayout(new BorderLayout(10, 10));
		
		danhsachNorthPanel=new GradientRoundPanel();
		danhsachNorthPanel.setLayout(new BorderLayout(10,10));
		danhsachNorthPanel.setBackground(Color.WHITE);
		iconBtnSave=new ImageIcon(getClass().getResource("/image/save.png"));
		JPanel resBtnThem=new JPanel();
		resBtnThem.setOpaque(false);
		resBtnThem.setBorder(BorderFactory.createEmptyBorder(10,10,0,20));
		resBtnThem.setBackground(Color.WHITE);
		
		btnExcel=new JButton("Xuất Excel", iconBtnSave); 
		btnExcel.setFont(new Font("Segoe UI",0,16));
		btnExcel.setPreferredSize(new Dimension(140,30));
		btnExcel.setBackground(new Color(51,51,255));
		btnExcel.setForeground(Color.WHITE);
		resBtnThem.add(btnExcel);
		titleUngVien=new JLabel("Danh sách ứng viên");
		titleUngVien.setForeground(Color.WHITE);
		titleUngVien.setFont(new Font("Segoe UI",1,16));
		titleUngVien.setBorder(BorderFactory.createEmptyBorder(10,20,0,10));
		danhsachNorthPanel.add(titleUngVien, BorderLayout.WEST);
		danhsachNorthPanel.add(resBtnThem, BorderLayout.EAST);
		
		danhsachCenterPanel=new GradientRoundPanel();
		danhsachCenterPanel.setLayout(new BoxLayout(danhsachCenterPanel, BoxLayout.PAGE_AXIS));
		danhsachCenterPanel.setBackground(Color.WHITE);
		String[] colName= {"Mã ứng viên","Tên ứng viên","Số điện thoại","Email"};
		Object[][] data = {
			    {1, "Nguyễn Thắng Minh Đạt", "0123456789", "email@gmail.com"},
			    {2, "Nguyễn Thắng Minh Đạt", "0987654321", "email@gmail.com"}
			};
		modelTableUngVien= new DefaultTableModel(data, colName){
			boolean[] canEdit = new boolean [] {
	                false, false, false, false, false
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
		tableUngVien.getColumnModel().getColumn(1).setPreferredWidth(200);
		tableUngVien.getColumnModel().getColumn(3).setPreferredWidth(150);
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		for(int i=0;i<tableUngVien.getColumnCount();i++) {
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
		scrollUngVien.setPreferredSize(new Dimension(1680, 800));
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
		
		UngVienPanel.add(centerPanelUngVien, BorderLayout.CENTER);
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
	
	public void addActionListener() {
		btnExcel.addActionListener(this);
		btnTimKiem.addActionListener(this);
		btnLamLai.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		var obj=e.getSource();
		if(obj.equals(btnExcel)) {
			ExcelHelper excel = new ExcelHelper();
			excel.exportData(this, tableUngVien, 0);
		}
		else if(obj.equals(btnTimKiem)) {
			
		}
		else if(obj.equals(btnLamLai)) {
			ungvienDAO.setListUngVien(ungvienDAO.getDSUngVien());
			loadDataTable();
		}
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

	public JPanel getPanel() {
		return this.UngVienPanel;
	}
}
