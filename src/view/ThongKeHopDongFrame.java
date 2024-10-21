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
import controller.LabelDateFormatter;

public class ThongKeHopDongFrame extends JFrame implements ActionListener, MouseListener, FocusListener {
	String userName;
	ThongKeHopDongFrame parent;
	
//	Component thống kê hợp đồng
	JPanel menuPanel, timkiemPanel, hopDongPanel,centerPanelHopDong, danhsachPanel, danhsachNorthPanel, danhsachCenterPanel;
	JLabel titleHopDong, ngayBatDauLabel, ngayKetThucLabel, timkiemNTDLabel, timkiemUVLabel;
	JButton btnTimKiem, btnLamLai, btnExcel;
	JTable tableHopDong;
	DefaultTableModel modelTableHopDong;
	JScrollPane scrollHopDong;
	UtilDateModel modelDate;
	JDatePickerImpl ngayBatDau, ngayKetThuc;
	JComboBox<Object> comboBoxNTD, comboBoxUV;
	Icon iconBtnSave;
	
	public ThongKeHopDongFrame(String userName) {
		this.userName=userName;
		this.parent = this;

//		Tạo component bên phải
		initComponent();
		
//		Thêm sự kiện
//		addActionListener();
//		addMouseListener();
//		addFocusListener();
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
		hopDongPanel=new JPanel(); 
		hopDongPanel.setLayout(new BorderLayout(5,5));
		
//		Hiển thị Thống kê và danh sách tin tuyển dụng
		centerPanelHopDong=new JPanel();
		centerPanelHopDong.setLayout(new BorderLayout(10, 10));
		centerPanelHopDong.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		centerPanelHopDong.setBackground(new Color(89, 145, 144));
//		Thống kê tin tuyển dụng
		timkiemPanel=new GradientRoundPanel();
		timkiemPanel.setBackground(Color.WHITE);
		timkiemPanel.setLayout(new BorderLayout());
		
		JPanel resFormSearch = new JPanel();
		resFormSearch.setOpaque(false);
		resFormSearch.setBackground(Color.WHITE);
		
		timkiemNTDLabel= createLabel("Nhà tuyển dụng:"); 
		comboBoxNTD=new JComboBox<Object>(); 
		comboBoxNTD.setFont(new Font("Segoe UI",0,16));
		comboBoxNTD.setOpaque(false);
		comboBoxNTD.addItem("Nhà tuyển dụng 1");
		comboBoxNTD.addItem("Nhà tuyển dụng 2");
		comboBoxNTD.addItem("Nhà tuyển dụng 3");
		resFormSearch.add(timkiemNTDLabel);
		resFormSearch.add(comboBoxNTD);
		
		timkiemUVLabel= createLabel("Ứng viên:"); 
		comboBoxUV=new JComboBox<Object>(); 
		comboBoxUV.setFont(new Font("Segoe UI",0,16));
		comboBoxUV.setOpaque(false);
		comboBoxUV.addItem("Ứng viên 1");
		comboBoxUV.addItem("Ứng viên 2");
		comboBoxUV.addItem("Ứng viên 3");
		resFormSearch.add(timkiemUVLabel);
		resFormSearch.add(comboBoxUV);
		
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
		
//		Danh sách tin tuyển dụng
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
		titleHopDong=new JLabel("Danh sách hợp đồng");
		titleHopDong.setForeground(Color.WHITE);
		titleHopDong.setFont(new Font("Segoe UI",1,16));
		titleHopDong.setBorder(BorderFactory.createEmptyBorder(10,20,0,10));
		danhsachNorthPanel.add(titleHopDong, BorderLayout.WEST);
		danhsachNorthPanel.add(resBtnThem, BorderLayout.EAST);
		
		danhsachCenterPanel=new GradientRoundPanel();
		danhsachCenterPanel.setLayout(new BoxLayout(danhsachCenterPanel, BoxLayout.PAGE_AXIS));
		danhsachCenterPanel.setBackground(Color.WHITE);
		String[] colName= {"Mã hợp đồng","Tên ứng viên","Số điện thoại","Nhà tuyển dụng", "Phí dịch vụ", "Ngày lập"};
		Object[][] data = {
			    {1, "Nguyễn Thắng Minh Đạt", "0123456789", "Facebook", "50,000 VNĐ", LocalDate.now().toString()},
			    {2, "Nguyễn Thắng Minh Đạt", "0987654321", "Amazon", "50,000 VNĐ", LocalDate.now().toString()}
			};
		modelTableHopDong= new DefaultTableModel(data, colName){
			boolean[] canEdit = new boolean [] {
	                false, false, false, false, false, true, true
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
		tableHopDong.getColumnModel().getColumn(1).setPreferredWidth(200);
		tableHopDong.getColumnModel().getColumn(2).setPreferredWidth(150);
		tableHopDong.getColumnModel().getColumn(3).setPreferredWidth(150);
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		for(int i=0;i<tableHopDong.getColumnCount();i++) {
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
		scrollHopDong.setPreferredSize(new Dimension(1680, 800));
		GradientRoundPanel resScroll=new GradientRoundPanel();
		resScroll.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));
		resScroll.setLayout(new BoxLayout(resScroll, BoxLayout.PAGE_AXIS));
		resScroll.setBackground(Color.WHITE);
		resScroll.add(scrollHopDong);
		danhsachCenterPanel.add(resScroll);
		
		danhsachPanel.add(danhsachNorthPanel, BorderLayout.NORTH);
		danhsachPanel.add(danhsachCenterPanel, BorderLayout.CENTER);
		
		centerPanelHopDong.add(timkiemPanel, BorderLayout.NORTH);
		centerPanelHopDong.add(danhsachPanel, BorderLayout.CENTER);
		
		hopDongPanel.add(centerPanelHopDong, BorderLayout.CENTER);
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

	public JPanel getPanel() {
		return this.hopDongPanel;
	}
}
