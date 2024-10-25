package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import dao.TinTuyenDung_DAO;
import entity.ModelChart;
import entity.ModelPieChart;
import entity.NhanVien;
import entity.constraint.NganhNghe;
import entity.constraint.TrinhDo;
import swing.Chart;
import swing.GradientRoundPanel;
import swing.PolarAreaLabel;
import swing.PolarPieChart;

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
	JButton btnThongKeNV, btnThongKeNTD, btnThongKeHD;
	PolarPieChart chartHTLV, chartTTD, chartNTD;
	Chart lineChart;
	private static TinTuyenDung_DAO tinTuyenDung_DAO;
	
	public ThongKeFrame(NhanVien userName) {
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
		thongKePanel.setBackground(new Color(89, 145, 144));
		
//		Các nút thống kê
		btnThongKeHD = createButton("Thống kê hợp đồng");
		btnThongKeNTD = createButton("Thống kê nhà tuyển dụng");
		btnThongKeNV = createButton("Thống kê nhân viên");
		
		northNorthPanel=new GradientRoundPanel();
		northNorthPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 5));
		northNorthPanel.add(btnThongKeHD);
		northNorthPanel.add(btnThongKeNTD);
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
		for (Object[] obj: tinTuyenDung_DAO.thongKeHinhThuc()) {
			chartHTLV.addData(new ModelPieChart(getRandomColor(), obj[0].toString(),
					Integer.parseInt(obj[1].toString())));
    	}
		JPanel note=new JPanel();
		note.setOpaque(false);
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
		for (Object[] obj: tinTuyenDung_DAO.thongKeTrinhDo()) {
			chartTTD.addData(new ModelPieChart(getRandomColor(), obj[0].toString(),
					Integer.parseInt(obj[1].toString())));
    	}
		JPanel not=new JPanel();
		not.setOpaque(false);
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
		for (Object[] obj: tinTuyenDung_DAO.thongKeNganhNghe()) {
			chartNTD.addData(new ModelPieChart(getRandomColor(), obj[0].toString(),
					Integer.parseInt(obj[1].toString())));
    	}
		JPanel nt=new JPanel();
		nt.setOpaque(false);
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
		
		JLabel titleLabel=new JLabel("Tổng số tin tuyển dụng theo ngành nghề và trình độ", SwingConstants.CENTER);
		titleLabel.setFont(new Font("Segoe UI",1,18));
		titleLabel.setForeground(Color.WHITE);
		
		lineChart=new Chart();
		lineChart.setOpaque(false);
		for(TrinhDo t: TrinhDo.class.getEnumConstants()) {
			Color c=getRandomColor();
			lineChart.addLegend(t.getValue(), c, c.brighter());
		}
		for(NganhNghe n: NganhNghe.class.getEnumConstants()) {
			double[] soluong=new double[100];
			int count=0;
			for(TrinhDo t: TrinhDo.class.getEnumConstants()) {
				for (Object[] obj: tinTuyenDung_DAO.thongKeNganhNgheTrinhDo(n.getValue(), t.getValue())) {
					soluong[count]=Double.parseDouble(obj[2].toString());
					count++;
		    	}
			}
			lineChart.addData(new ModelChart(n.getValue(),
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
		
		panel.add(titleLabel, BorderLayout.NORTH);
		panel.add(chart, BorderLayout.CENTER);
		panel.add(note, BorderLayout.SOUTH);
		
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
