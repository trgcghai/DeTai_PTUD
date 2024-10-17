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
	
	GradientRoundPanel danhsachTTDPanel, danhsachTTDNorthPanel, danhsachTTDCenterPanel,
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
	
	public void initComponent() {
		timviecPanel=new JPanel(); 
		timviecPanel.setLayout(new BorderLayout());
		timviecPanel.setBackground(new Color(89, 145, 144));
		
//		Hiển thị danh sách tin tuyển dụng, danh sách hồ sơ ứng viên
		centerPanelTimViec=new JPanel();
		centerPanelTimViec.setLayout(new BorderLayout());
		centerPanelTimViec.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		centerPanelTimViec.setBackground(new Color(89, 145, 144));
		
//		Danh sách tin tuyển dụng
		danhsachTTDPanel=new GradientRoundPanel();
		danhsachTTDPanel.setLayout(new BorderLayout());
		danhsachTTDPanel.setPreferredSize(new Dimension(660,getHeight()));
		
		danhsachTTDNorthPanel=new GradientRoundPanel();
		danhsachTTDNorthPanel.setLayout(new BorderLayout(10,10));
		danhsachTTDNorthPanel.setBackground(Color.WHITE);
		JPanel resNTD=new JPanel();
		resNTD.setOpaque(false);
		resNTD.setBorder(BorderFactory.createEmptyBorder(10,10,0,15));
		nhatuyendungLabel=new JLabel("Nhà tuyển dụng");
		nhatuyendungLabel.setFont(new Font("Segoe UI",1,16));
		nhatuyendungLabel.setForeground(Color.WHITE);
		nhatuyendungCombo=new JComboBox();
		nhatuyendungCombo.setFont(new Font("Segoe UI",0,16));
		nhatuyendungCombo.setForeground(Color.WHITE);
		nhatuyendungCombo.setBackground(new Color(89, 145, 144));
		nhatuyendungCombo.setPreferredSize(new Dimension(200,25));
		nhatuyendungCombo.setRenderer(new ComboBoxRenderer("Chọn nhà tuyển dụng"));
		resNTD.add(nhatuyendungLabel); resNTD.add(nhatuyendungCombo);
		titleTinTuyenDung=new JLabel("Danh sách tin tuyển dụng");
		titleTinTuyenDung.setFont(new Font("Segoe UI",1,16));
		titleTinTuyenDung.setForeground(Color.WHITE);
		titleTinTuyenDung.setBorder(BorderFactory.createEmptyBorder(10,20,0,10));
		danhsachTTDNorthPanel.add(titleTinTuyenDung, BorderLayout.WEST);
		danhsachTTDNorthPanel.add(resNTD, BorderLayout.EAST);
		
		danhsachTTDCenterPanel=new GradientRoundPanel();
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
		tableTinTuyenDung=createTable(modelTableTinTuyenDung);
		scrollTinTuyenDung=new JScrollPane(tableTinTuyenDung);
		scrollTinTuyenDung.setBorder(BorderFactory.createLineBorder(new Color(0,191,165)));
		GradientRoundPanel resScrollTinTuyenDung=new GradientRoundPanel();
		resScrollTinTuyenDung.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));
		resScrollTinTuyenDung.setLayout(new BoxLayout(resScrollTinTuyenDung, BoxLayout.PAGE_AXIS));
		resScrollTinTuyenDung.setBackground(Color.WHITE);
		resScrollTinTuyenDung.add(scrollTinTuyenDung);
		danhsachTTDCenterPanel.add(resScrollTinTuyenDung);
		
		danhsachTTDPanel.add(danhsachTTDNorthPanel, BorderLayout.NORTH);
		danhsachTTDPanel.add(danhsachTTDCenterPanel, BorderLayout.CENTER);
		
//		Danh sách hồ sơ ứng viên
		danhsachHoSoPanel=new GradientRoundPanel();
		danhsachHoSoPanel.setLayout(new BorderLayout());
		danhsachHoSoPanel.setPreferredSize(new Dimension(660,getHeight()));
		
		danhsachHoSoNorthPanel=new GradientRoundPanel();
		danhsachHoSoNorthPanel.setLayout(new BorderLayout(10,10));
		danhsachHoSoNorthPanel.setBackground(Color.WHITE);
		JPanel resUngVien=new JPanel();
		resUngVien.setOpaque(false);
		resUngVien.setBorder(BorderFactory.createEmptyBorder(10,10,0,15));
		resUngVien.setBackground(Color.WHITE);
		
		ungvienLabel=new JLabel("Ứng viên");
		ungvienLabel.setFont(new Font("Segoe UI",1,16));
		ungvienLabel.setForeground(Color.WHITE);
		
		ungvienCombo=new JComboBox();
		ungvienCombo.setForeground(Color.WHITE);
		ungvienCombo.setBackground(new Color(89, 145, 144));
		ungvienCombo.setFont(new Font("Segoe UI",0,16));
		ungvienCombo.setPreferredSize(new Dimension(200,25));
		ungvienCombo.setRenderer(new ComboBoxRenderer("Chọn ứng viên"));
		resUngVien.add(ungvienLabel); resUngVien.add(ungvienCombo);
		
		titleHoSo = new JLabel("Danh sách hồ sơ ứng viên");
		titleHoSo.setFont(new Font("Segoe UI",1,16));
		titleHoSo.setForeground(Color.WHITE);
		titleHoSo.setBorder(BorderFactory.createEmptyBorder(10,20,0,10));
		danhsachHoSoNorthPanel.add(titleHoSo, BorderLayout.WEST);
		danhsachHoSoNorthPanel.add(resUngVien, BorderLayout.EAST);
		
		danhsachHoSoCenterPanel=new GradientRoundPanel();
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
		tableHoSo=createTable(modelTableHoSo);
		scrollHoSo=new JScrollPane(tableHoSo);
		scrollHoSo.setBorder(BorderFactory.createLineBorder(new Color(0,191,165)));
		GradientRoundPanel resScrollHoSo=new GradientRoundPanel();
		resScrollHoSo.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));
		resScrollHoSo.setLayout(new BoxLayout(resScrollHoSo, BoxLayout.PAGE_AXIS));
		resScrollHoSo.add(scrollHoSo);
		danhsachHoSoCenterPanel.add(resScrollHoSo);
		
		danhsachHoSoPanel.add(danhsachHoSoNorthPanel, BorderLayout.NORTH);
		danhsachHoSoPanel.add(danhsachHoSoCenterPanel, BorderLayout.CENTER);
		
		centerPanelTimViec.add(danhsachTTDPanel, BorderLayout.EAST);
		centerPanelTimViec.add(danhsachHoSoPanel, BorderLayout.WEST);
		
		timviecPanel.add(centerPanelTimViec, BorderLayout.CENTER);
	}
	
	public JTable createTable(DefaultTableModel model) {
		JTable table=new JTable(model);
		table.getTableHeader().setFont(new Font("Segoe UI",1,14));
		table.setFont(new Font("Segoe UI",0,16));
		table.setRowHeight(30);
		DefaultTableCellRenderer centerRenderers = new DefaultTableCellRenderer();
		centerRenderers.setHorizontalAlignment( JLabel.CENTER );
		for(int i=0;i<table.getColumnCount()-1;i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(centerRenderers);			
		}
		table.setAutoCreateRowSorter(true);
		ArrayList<RowSorter.SortKey> lists = new ArrayList<>();
        DefaultRowSorter sorters = ((DefaultRowSorter)table.getRowSorter());
        sorters.setSortsOnUpdates(true);
        lists.add( new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sorters.setSortKeys(lists);
        sorters.sort();
        
		return table;
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
