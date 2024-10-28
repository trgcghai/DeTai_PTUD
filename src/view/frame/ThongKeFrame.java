package view.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import dao.HopDong_DAO;
import dao.NhanVien_DAO;
import dao.TinTuyenDung_DAO;
import entity.ModelChart;
import entity.ModelPieChart;
import entity.NhanVien;
import entity.constraint.NganhNghe;
import entity.constraint.Thang;
import entity.constraint.TrinhDo;
import swing.Button;
import swing.GradientRoundPanel;
import swing.chart.LineChart;
import swing.chart.PolarAreaLabel;
import swing.chart.PolarPieChart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;


public class ThongKeFrame extends JFrame implements ActionListener {

	NhanVien userName;
	ThongKeFrame parent;
	
//	Component thống kê
	JPanel thongKePanel, northPanel, centerPanel, northWestPanel, northEastPanel, northNorthPanel, wrapPanel;
	Button btnThongKeNV, btnThongKeTTD, btnThongKeHD;
	PolarPieChart chartHTLV, chartTTD, chartNTD;
	LineChart lineChart;
	
	private TinTuyenDung_DAO tinTuyenDungDAO;
	private NhanVien_DAO nhanvienDAO;
	private HopDong_DAO hopdongDAO;
	
	public ThongKeFrame(NhanVien userName) {
		this.userName=userName;
		this.parent=this;
		
		tinTuyenDungDAO = new TinTuyenDung_DAO();
		nhanvienDAO=new NhanVien_DAO();
		hopdongDAO=new HopDong_DAO();
		
//		Tạo component bên phải
		initComponent();
		
		addActionListener();
	}
	
	public Button createButton(String title) {
		Button btn=new Button(title); 
		btn.setFont(new Font("Segoe UI",0,16));
		btn.setPreferredSize(new Dimension(240,25));
		btn.setBackground(new Color(0,102,102));
		btn.setForeground(Color.WHITE);
		return btn;
	}
		
	public void addActionListener() {
		btnThongKeHD.addActionListener(this);
		btnThongKeTTD.addActionListener(this);
		btnThongKeNV.addActionListener(this);
	}
	
	public void initComponent() {
		thongKePanel=new JPanel(); 
		thongKePanel.setLayout(new BorderLayout(5,5));
		thongKePanel.setBackground(new Color(89, 145, 144));
		thongKePanel.setBackground(new Color(89, 145, 144));
		
//		Các nút thống kê
		btnThongKeHD = createButton("Thống kê hợp đồng");
		btnThongKeTTD = createButton("Thống kê tin tuyển dụng");
		btnThongKeNV = createButton("Thống kê nhân viên");
		
		northNorthPanel=new GradientRoundPanel();
		northNorthPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 19, 5));
		northNorthPanel.add(btnThongKeHD);
		northNorthPanel.add(btnThongKeTTD);
		northNorthPanel.add(btnThongKeNV);
		
//		Biểu đồ tròn và đường
		northPanel=new JPanel();
		northPanel.setOpaque(false);
		northPanel.setLayout(new BorderLayout(5,5));
		
//		Biểu đồ tròn
		northWestPanel= new GradientRoundPanel();
		northWestPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		
//		thống kê theo hình thức làm việc
		chartHTLV=new PolarPieChart();
		chartHTLV.setChartType(PolarPieChart.PeiChartType.DONUT_CHART);
		Set<Color> tmp = new HashSet<>();
		for (Object[] obj: tinTuyenDungDAO.thongKeHinhThuc()) {
			Color c;
			do{
				c=getRandomColor();
			}while(c.equals(Color.WHITE) || tmp.contains(c));
			chartHTLV.addData(new ModelPieChart(c, obj[0].toString(),
					Integer.parseInt(obj[1].toString())));
			tmp.add(c);
    	}
		JPanel note=new JPanel();
		note.setOpaque(false);
		note.setLayout(new GridLayout(chartHTLV.getList().size(),1));
		for(ModelPieChart i: chartHTLV.getList()) {
			PolarAreaLabel label=new PolarAreaLabel();
			label.setText(i.getName());
			label.setBackground(i.getColor());
			label.setForeground(Color.WHITE);
			label.setFont(new Font("Segoe UI",1,13));
			note.add(label);
		}
		northWestPanel.add(createPanelCircelChart(chartHTLV, "Tỉ lệ hợp đồng theo hình thức làm việc", note));
//		thống kê theo trình độ
		chartTTD=new PolarPieChart();
		chartTTD.setChartType(PolarPieChart.PeiChartType.DONUT_CHART);
		Set<Color> tmp1 = new HashSet<>();
		for (Object[] obj: tinTuyenDungDAO.thongKeTrinhDo()) {
			Color c;
			do{
				c=getRandomColor();
			}while(c.equals(Color.WHITE) || tmp1.contains(c));
			chartTTD.addData(new ModelPieChart(c, obj[0].toString(),
					Integer.parseInt(obj[1].toString())));
			tmp1.add(c);
    	}
		JPanel not=new JPanel();
		not.setOpaque(false);
		not.setLayout(new GridLayout(chartTTD.getList().size(),1));
		for(ModelPieChart i: chartTTD.getList()) {
			PolarAreaLabel label=new PolarAreaLabel();
			label.setText(i.getName());
			label.setBackground(i.getColor());
			label.setForeground(Color.WHITE);
			label.setFont(new Font("Segoe UI",1,13));
			not.add(label);
		}
		northWestPanel.add(createPanelCircelChart(chartTTD, "Tỉ lệ hợp đồng theo trình độ", not));
//		thống kê theo ngành nghề
		chartNTD=new PolarPieChart();
		chartNTD.setChartType(PolarPieChart.PeiChartType.DONUT_CHART);
		Set<Color> tmp2 = new HashSet<>();
		for (Object[] obj: tinTuyenDungDAO.thongKeNganhNghe()) {
			Color c;
			do{
				c=getRandomColor();
			}while(c.equals(Color.WHITE) || tmp2.contains(c));
			chartNTD.addData(new ModelPieChart(c, obj[0].toString(),
					Integer.parseInt(obj[1].toString())));
			tmp2.add(c);
    	}
		JPanel nt=new JPanel();
		nt.setOpaque(false);
		nt.setLayout(new GridLayout(chartNTD.getList().size(),1));
		for(ModelPieChart i: chartNTD.getList()) {
			PolarAreaLabel label=new PolarAreaLabel();
			label.setText(i.getName());
			label.setBackground(i.getColor());
			label.setForeground(Color.WHITE);
			label.setFont(new Font("Segoe UI",1,13));
			nt.add(label);
		}
		northWestPanel.add(createPanelCircelChart(chartNTD, "Tỉ lệ hợp đồng theo ngành nghề", nt));
		
		northPanel.add(northWestPanel, BorderLayout.CENTER);
		
//		Biểu đồ đường
		centerPanel=new GradientRoundPanel();
		centerPanel.setLayout(new BorderLayout());
		
		JLabel titleLabel=new JLabel("Tổng doanh thu hợp đồng theo tháng của nhân viên", SwingConstants.CENTER);
		titleLabel.setFont(new Font("Segoe UI",1,18));
		titleLabel.setForeground(Color.WHITE);
		
		lineChart=new LineChart();
		lineChart.setOpaque(false);
		Set<Color> tmp3 = new HashSet<>();
		for(NhanVien n: nhanvienDAO.getDSNhanVien()) {
			Color c;
			do{
				c=getRandomColor();
			}while(c.equals(Color.WHITE) || tmp3.contains(c));
			lineChart.addLegend(n.getTenNV(), c, c.brighter());
			tmp3.add(c);
		}
		for(Thang t: Thang.class.getEnumConstants()) {
			double[] soluong=new double[100];
			int count=0;
			for(NhanVien n: nhanvienDAO.getDSNhanVien()) {
					soluong[count++]=hopdongDAO.thongKeHopDongTheoNhanVien(n.getMaNV(), Integer.parseInt(t.getValue().split(" ")[1]));
			}
			lineChart.addData(new ModelChart(t.getValue(),
					soluong));
		}
        lineChart.start();
        
        centerPanel.add(titleLabel, BorderLayout.NORTH);
		centerPanel.add(lineChart, BorderLayout.CENTER);
		
		wrapPanel= new JPanel();
		wrapPanel.setOpaque(false);
		wrapPanel.setLayout(new BorderLayout(5,5));
		wrapPanel.add(northPanel, BorderLayout.NORTH);
		wrapPanel.add(centerPanel, BorderLayout.CENTER);
		
		thongKePanel.add(northNorthPanel, BorderLayout.NORTH);
		thongKePanel.add(wrapPanel, BorderLayout.CENTER);
		
		
		add(thongKePanel);
	}	
	
	private JPanel createPanelCircelChart(PolarPieChart chart, String title, JPanel note) {
		JPanel panel=new JPanel();
		panel.setOpaque(false);
		panel.setLayout(new BorderLayout());
		
		JLabel titleLabel=new JLabel(title, SwingConstants.CENTER);
		titleLabel.setFont(new Font("Segoe UI",1,16));
		titleLabel.setForeground(Color.WHITE);
		
		JPanel res=new JPanel();
		res.setOpaque(false);
		res.add(chart);
		res.add(note);
		
		panel.add(titleLabel, BorderLayout.NORTH);
		panel.add(res, BorderLayout.CENTER);
		
		return panel;
	}
	
	public Color getRandomColor() {
	    Random random = new Random();
	    int r = random.nextInt(256);
	    int g = random.nextInt(256); 
	    int b = random.nextInt(256); 
	    return new Color(r, g, b);
	}
	
	public JPanel getPanel() {
		return this.thongKePanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		var obj=e.getSource();
		if(obj.equals(btnThongKeTTD)) {
			wrapPanel.removeAll();
			wrapPanel.add(new ThongKeTinTuyenDungFrame(userName).getPanel());
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
