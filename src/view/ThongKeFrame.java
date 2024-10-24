package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import component.Button;
import component.RoundPanel;
import component.progress.ProgressBar;
import dao.NhanVien_DAO;
import dao.TinTuyenDung_DAO;
import entity.NhanVien;
import entity.constraint.GioiTinh;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.axis.StandardTickUnitSource;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;


public class ThongKeFrame extends JFrame implements ActionListener {

	String userName;
	ThongKeFrame parent;
	
//	Component thống kê
	JPanel thongKePanel, northPanel, centerPanel, northWestPanel, northEastPanel, northNorthPanel, wrapPanel;
	JButton btnThongKeNV, btnThongKeNTD, btnThongKeHD;
	private static TinTuyenDung_DAO tinTuyenDung_DAO;
	
	public ThongKeFrame(String userName) {
		this.userName=userName;
		this.parent=this;
		
		tinTuyenDung_DAO = new TinTuyenDung_DAO();
		
//		Tạo component bên phải
		initComponent();
		
		addActionListener();
	}
	
	public JButton createButton(String title) {
		JButton btn=new JButton(title); 
		btn.setFont(new Font("Segoe UI",0,16));
		btn.setPreferredSize(new Dimension(240,25));
		btn.setBackground(new Color(0,102,102));
		btn.setForeground(Color.WHITE);
		return btn;
	}
		
	public void addActionListener() {
		btnThongKeHD.addActionListener(this);
		btnThongKeNTD.addActionListener(this);
		btnThongKeNV.addActionListener(this);
	}
	
	public void initComponent() {
		thongKePanel=new JPanel(); 
		thongKePanel.setLayout(new BorderLayout(5,5));
		thongKePanel.setBackground(new Color(89, 145, 144));
		
		northPanel= new JPanel();
		northPanel.setLayout(new BorderLayout());
		
		northWestPanel= new JPanel();
		northWestPanel.add(createPieChartPanel("Tỉ lệ tin tuyển dụng theo hình thức làm việc", createHinhThucTinTuyenDungDataset(), tinTuyenDung_DAO.thongKeHinhThuc()));
		northWestPanel.add(createPieChartPanel("Tỉ lệ tin tuyển dụng theo trình độ", createTrinhDoTinTuyenDungDataset(), tinTuyenDung_DAO.thongKeTrinhDo()));
		
		northPanel.add(northWestPanel, BorderLayout.WEST);
		northPanel.add(createLineChartPanel(), BorderLayout.EAST);
		
		centerPanel= new JPanel();
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(createDemoBarChartPanel(), BorderLayout.CENTER);
		
		wrapPanel= new JPanel();
		wrapPanel.setLayout(new BorderLayout());
		wrapPanel.add(northPanel, BorderLayout.NORTH);
		wrapPanel.add(centerPanel, BorderLayout.CENTER);
		

		btnThongKeHD = createButton("Thống kê hợp đồng");
		btnThongKeNTD = createButton("Thống kê nhà tuyển dụng");
		btnThongKeNV = createButton("Thống kê nhân viên");
		
		JPanel panel = new JPanel();
		panel.add(btnThongKeHD);
		panel.add(btnThongKeNTD);
		panel.add(btnThongKeNV);
		
		northNorthPanel=new JPanel();
		northNorthPanel.setLayout(new BorderLayout());
		northNorthPanel.add(panel, BorderLayout.WEST);
		
		thongKePanel.add(northNorthPanel, BorderLayout.NORTH);
		thongKePanel.add(wrapPanel, BorderLayout.CENTER);
		thongKePanel.setBackground(new Color(89, 145, 144));
		
		add(thongKePanel);
	}	
	
	private static JPanel createPieChartPanel(String title, DefaultPieDataset dataset, ArrayList<Object[]> data) {
        JFreeChart chart = createPieChart(title, dataset, data);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(400, 300));
        return chartPanel;
    }

	private static JPanel createLineChartPanel() {
        JFreeChart chart = createLineChart("Số lượng các tin tuyển dụng theo ngành nghề theo tháng trong năm 2024", 
        		"Tháng", "Số lượng tin", createNganhNgheTTDTheoThoiGianDataset());
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 300));
        return chartPanel;
    }
	
	private static JPanel createDemoBarChartPanel() {
        JFreeChart chart = createBarChart("Tổng số lượng tin tuyển dụng theo ngành nghề", "Ngành nghề", "Số lượng tin", createNganhNgheTrinhDoDataset());
        ChartPanel chartPanel = new ChartPanel(chart);
        return chartPanel;
    }
	
    private static DefaultPieDataset createHinhThucTinTuyenDungDataset() {
    	DefaultPieDataset dataset = new DefaultPieDataset();
    	for (Object[] obj: tinTuyenDung_DAO.thongKeHinhThuc()) {
    		dataset.setValue(obj[0].toString(), (Number) obj[1]);
    	}
    	return dataset;
    }
    
    private static DefaultPieDataset createTrinhDoTinTuyenDungDataset() {
    	DefaultPieDataset dataset = new DefaultPieDataset();
    	for (Object[] obj: tinTuyenDung_DAO.thongKeTrinhDo()) {
    		dataset.setValue(obj[0].toString(), (Number) obj[1]);
    	}
    	return dataset;
    }
    
    private static DefaultCategoryDataset createNganhNgheTTDTheoThoiGianDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Object[] obj: tinTuyenDung_DAO.thongKeNganhNgheTheoThoiGian()) {
    		dataset.addValue(Double.valueOf(obj[2].toString()) , obj[0].toString(), (Comparable) obj[1]);
    	}
        return dataset;
    }
    
    private static DefaultCategoryDataset createNganhNgheTrinhDoDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Object[] obj: tinTuyenDung_DAO.thongKeNganhNgheTrinhDo()) {
    		dataset.addValue(Double.valueOf(obj[2].toString()) , obj[1].toString(), obj[0].toString());
    	}
        return dataset;
    }
    
    private static JFreeChart createBarChart(String title, String horizontalTitle, String verticalTitle, DefaultCategoryDataset dataset) {
    	JFreeChart chart = ChartFactory.createBarChart(title, horizontalTitle, verticalTitle, dataset, PlotOrientation.VERTICAL, true, true, false);
    	CategoryPlot plot = (CategoryPlot) chart.getPlot();
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
    	
        return chart;
    }

    private static JFreeChart createPieChart(String title, DefaultPieDataset dataset, ArrayList<Object[]> data) {
        JFreeChart chart = ChartFactory.createPieChart(title, dataset, true, true, false);
        PiePlot plot = (PiePlot) chart.getPlot();
        ArrayList<Color> colors = new ArrayList<Color>();
        colors.add(new Color(200, 255, 200));
        colors.add(new Color(255, 200, 200));
        colors.add(new Color(200, 200, 255));
        colors.add(new Color(200, 255, 255));
        
        int length = data.size();
        for (int i = 0; i < length; i++) {
        	plot.setSectionPaint(data.get(i)[0].toString(), colors.get(i));
        }   
        return chart;
    }
    
    private static JFreeChart createLineChart(String title, String horizontalTitle, String verticalTitle, DefaultCategoryDataset dataset) {
        return ChartFactory.createLineChart(title, horizontalTitle, verticalTitle, dataset, PlotOrientation.VERTICAL, true, true, false);
    }
	
	public JLabel createLabel(String title) {
		JLabel label = new JLabel(title);
		label.setFont(new Font("Segoe UI",0,16));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		
		return label;
	}
	
	public JPanel getPanel() {
		return this.thongKePanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		var obj=e.getSource();
		if(obj.equals(btnThongKeNTD)) {
			wrapPanel.removeAll();
			wrapPanel.add(new ThongKeNhaTuyenDungFrame(userName).getPanel());
		}
		else if(obj.equals(btnThongKeHD)) {
			wrapPanel.removeAll();
			wrapPanel.add(new ThongKeHopDongFrame(userName).getPanel());
		}
		else if(obj.equals(btnThongKeNV)) {
			wrapPanel.removeAll();
			wrapPanel.add(new ThongKeNhanVienFrame(userName).getPanel());
		}
		wrapPanel.revalidate();
		wrapPanel.repaint();
	}
}
