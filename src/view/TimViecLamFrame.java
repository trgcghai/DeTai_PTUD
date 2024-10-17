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

import component.RoundPanel;
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
		timviecPanel, centerPanelTimViec;
	JLabel titleTinTuyenDung, titleHoSo, nhatuyendungLabel, ungvienLabel;
	JTable tableTinTuyenDung, tableHoSo;
	DefaultTableModel modelTableTinTuyenDung, modelTableHoSo;
	JScrollPane scrollTinTuyenDung, scrollHoSo;
	JComboBox nhatuyendungCombo, ungvienCombo;
	
	RoundPanel danhsachTTDPanel, danhsachTTDNorthPanel, danhsachTTDCenterPanel,
				danhsachHoSoPanel, danhsachHoSoNorthPanel, danhsachHoSoCenterPanel;
	
	
	public TimViecLamFrame(String userName) {
		this.userName=userName;
		this.parent=this;
		
//		Tạo component bên phải
		initComponent();
		
//		Thêm update và delete vào table
		addTableTTDActionEvent();
		addTableHoSoActionEvent();
		
//		Thêm sự kiện
		addActionListener();
		addMouseListener();
		addFocusListener();
		
	}
	
	public JLabel createLabel(String title, boolean isBordered) {
		JLabel label = new JLabel(title);
		label.setFont(new Font("Segoe UI",1,16));
		if (isBordered) label.setBorder(BorderFactory.createEmptyBorder(10,20,0,10));
		return label;
	}
	
	public void initComponent() {
		timviecPanel=new JPanel(); 
		timviecPanel.setLayout(new BorderLayout(5,5));
		timviecPanel.setBackground(new Color(220, 220, 220));
		
//		Hiển thị danh sách tin tuyển dụng và danh sách hồ sơ ứng viên
		centerPanelTimViec=new JPanel();
		centerPanelTimViec.setLayout(new BorderLayout(10, 10));
		centerPanelTimViec.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		centerPanelTimViec.setBackground(new Color(220, 220, 220));
		
//		Danh sách tin tuyển dụng
		danhsachTTDPanel=new RoundPanel();
		danhsachTTDPanel.setBackground(Color.WHITE);
		danhsachTTDPanel.setLayout(new BorderLayout(10, 10));
		danhsachTTDPanel.setPreferredSize(new Dimension(670,getHeight()));
		
		danhsachTTDNorthPanel=new RoundPanel();
		danhsachTTDNorthPanel.setLayout(new BorderLayout(10,10));
		danhsachTTDNorthPanel.setBackground(Color.WHITE);
		RoundPanel resNTD=new RoundPanel();
		resNTD.setBorder(BorderFactory.createEmptyBorder(10,10,0,15));
		resNTD.setBackground(Color.WHITE);
		nhatuyendungLabel=new JLabel("Nhà tuyển dụng");
		nhatuyendungLabel.setFont(new Font("Segoe UI",1,16));
		nhatuyendungCombo=new JComboBox();
		nhatuyendungCombo.setFont(new Font("Segoe UI",0,16));
		nhatuyendungCombo.setPreferredSize(new Dimension(200,25));
		nhatuyendungCombo.setRenderer(new ComboBoxRenderer("Chọn nhà tuyển dụng"));
		resNTD.add(nhatuyendungLabel); resNTD.add(nhatuyendungCombo);
		titleTinTuyenDung=new JLabel("Danh sách tin tuyển dụng");
		titleTinTuyenDung.setFont(new Font("Segoe UI",1,16));
		titleTinTuyenDung.setBorder(BorderFactory.createEmptyBorder(10,20,0,10));
		danhsachTTDNorthPanel.add(titleTinTuyenDung, BorderLayout.WEST);
		danhsachTTDNorthPanel.add(resNTD, BorderLayout.EAST);
		
		danhsachTTDCenterPanel=new RoundPanel();
		danhsachTTDCenterPanel.setLayout(new BoxLayout(danhsachTTDCenterPanel, BoxLayout.PAGE_AXIS));
		danhsachTTDCenterPanel.setBackground(Color.WHITE);
		String[] colName= {"Tiêu đề","Trình độ","Lương", "Hành động"};
		Object[][] data = {
			    {"Technical Project Manager","Đại học","1000",null},
			    {"Manual Tester","Cao đẳng", "500",null}
			};
		modelTableTinTuyenDung= new DefaultTableModel(data, colName){
			boolean[] canEdit = new boolean [] {
	                false, false, false, true
	            };
			
            @Override
            public boolean isCellEditable(int row, int column) {
               return canEdit[column];
            }
        };
		tableTinTuyenDung=new JTable(modelTableTinTuyenDung);
		tableTinTuyenDung.getTableHeader().setFont(new Font("Segoe UI",1,14));
		tableTinTuyenDung.setFont(new Font("Segoe UI",0,16));
		tableTinTuyenDung.setRowHeight(30);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		for(int i=0;i<tableTinTuyenDung.getColumnCount()-1;i++) {
			tableTinTuyenDung.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);			
		}
		tableTinTuyenDung.setAutoCreateRowSorter(true);
		ArrayList<RowSorter.SortKey> list = new ArrayList<>();
        DefaultRowSorter sorter = ((DefaultRowSorter)tableTinTuyenDung.getRowSorter());
        sorter.setSortsOnUpdates(true);
        list.add( new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sorter.setSortKeys(list);
        sorter.sort();
		scrollTinTuyenDung=new JScrollPane(tableTinTuyenDung);
		scrollTinTuyenDung.setBorder(BorderFactory.createLineBorder(new Color(0,191,165)));
		RoundPanel resScrollTinTuyenDung=new RoundPanel();
		resScrollTinTuyenDung.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));
		resScrollTinTuyenDung.setLayout(new BoxLayout(resScrollTinTuyenDung, BoxLayout.PAGE_AXIS));
		resScrollTinTuyenDung.setBackground(Color.WHITE);
		resScrollTinTuyenDung.add(scrollTinTuyenDung);
		danhsachTTDCenterPanel.add(resScrollTinTuyenDung);
		
		danhsachTTDPanel.add(danhsachTTDNorthPanel, BorderLayout.NORTH);
		danhsachTTDPanel.add(danhsachTTDCenterPanel, BorderLayout.CENTER);
		
//		Danh sách hồ sơ ứng viên
		danhsachHoSoPanel=new RoundPanel();
		danhsachHoSoPanel.setBackground(Color.WHITE);
		danhsachHoSoPanel.setLayout(new BorderLayout(10, 10));
		danhsachHoSoPanel.setPreferredSize(new Dimension(670,getHeight()));
		
		danhsachHoSoNorthPanel=new RoundPanel();
		danhsachHoSoNorthPanel.setLayout(new BorderLayout(10,10));
		danhsachHoSoNorthPanel.setBackground(Color.WHITE);
		RoundPanel resUngVien=new RoundPanel();
		resUngVien.setBorder(BorderFactory.createEmptyBorder(10,10,0,15));
		resUngVien.setBackground(Color.WHITE);
		
//		ungvienLabel = createLabel("Ứng viên", false);
		ungvienLabel=new JLabel("Ứng viên");
		ungvienLabel.setFont(new Font("Segoe UI",1,16));
		
		ungvienCombo=new JComboBox();
		ungvienCombo.setFont(new Font("Segoe UI",0,16));
		ungvienCombo.setPreferredSize(new Dimension(200,25));
		ungvienCombo.setRenderer(new ComboBoxRenderer("Chọn ứng viên"));
		resUngVien.add(ungvienLabel); resUngVien.add(ungvienCombo);
		
//		titleHoSo = createLabel("Danh sách hồ sơ ứng viên", true);
		titleHoSo=new JLabel("Danh sách hồ sơ ứng viên");
		titleHoSo.setFont(new Font("Segoe UI",1,16));
		titleHoSo.setBorder(BorderFactory.createEmptyBorder(10,20,0,10));
		danhsachHoSoNorthPanel.add(titleHoSo, BorderLayout.WEST);
		danhsachHoSoNorthPanel.add(resUngVien, BorderLayout.EAST);
		
		danhsachHoSoCenterPanel=new RoundPanel();
		danhsachHoSoCenterPanel.setLayout(new BoxLayout(danhsachHoSoCenterPanel, BoxLayout.PAGE_AXIS));
		danhsachHoSoCenterPanel.setBackground(Color.WHITE);
		String[] col= {"Trạng thái","Tên ứng viên","Trình độ", "Hành động"};
		Object[][] datas = {
			    {"Chưa nộp","Minh Đạt", "Đại học", null},
			    {"Chưa nộp","Thắng Đạt", "Cao đẳng", null}
			};
		modelTableHoSo= new DefaultTableModel(datas, col){
			boolean[] canEdit = new boolean [] {
	                false, false, false, true
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
		DefaultTableCellRenderer centerRenderers = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		for(int i=0;i<tableHoSo.getColumnCount()-1;i++) {
			tableHoSo.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);			
		}
		tableHoSo.setAutoCreateRowSorter(true);
		ArrayList<RowSorter.SortKey> lists = new ArrayList<>();
        DefaultRowSorter sorters = ((DefaultRowSorter)tableHoSo.getRowSorter());
        sorters.setSortsOnUpdates(true);
        lists.add( new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sorters.setSortKeys(lists);
        sorters.sort();
		scrollHoSo=new JScrollPane(tableHoSo);
		scrollHoSo.setBorder(BorderFactory.createLineBorder(new Color(0,191,165)));
		RoundPanel resScrollHoSo=new RoundPanel();
		resScrollHoSo.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));
		resScrollHoSo.setLayout(new BoxLayout(resScrollHoSo, BoxLayout.PAGE_AXIS));
		resScrollHoSo.setBackground(Color.WHITE);
		resScrollHoSo.add(scrollHoSo);
		danhsachHoSoCenterPanel.add(resScrollHoSo);
		
		danhsachHoSoPanel.add(danhsachHoSoNorthPanel, BorderLayout.NORTH);
		danhsachHoSoPanel.add(danhsachHoSoCenterPanel, BorderLayout.CENTER);
		
		centerPanelTimViec.add(danhsachTTDPanel, BorderLayout.EAST);
		centerPanelTimViec.add(danhsachHoSoPanel, BorderLayout.WEST);
		
		timviecPanel.add(centerPanelTimViec, BorderLayout.CENTER);
	}
	
	public void addTableTTDActionEvent() {
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
				new ChiTietViecLamDialog(parent, rootPaneCheckingEnabled).setVisible(true);
			}
			
		};
		
		tableTinTuyenDung.getColumnModel().getColumn(3).setCellRenderer(new TableCellRendererDetail());
		tableTinTuyenDung.getColumnModel().getColumn(3).setCellEditor(new TableCellEditorDetail(event));
	}
	
	public void addTableHoSoActionEvent() {
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
				new ChiTietHoSoDialog(parent, rootPaneCheckingEnabled).setVisible(true);
			}
			
		};
		
		tableHoSo.getColumnModel().getColumn(3).setCellRenderer(new TableCellRendererDetail());
		tableHoSo.getColumnModel().getColumn(3).setCellEditor(new TableCellEditorDetail(event));
	}
	
	
	public JPanel getPanel() {
		return this.timviecPanel;
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

	}
	
	public void addActionListener() {
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		var obj=e.getSource();
		
	}
	
	public void addMouseListener() {
		tableHoSo.addMouseListener(this);
		tableTinTuyenDung.addMouseListener(this);
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
	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
	}

}
