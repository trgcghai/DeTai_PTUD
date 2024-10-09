package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import component.RoundPanel;
import component.progress.ProgressBar;

public class ThongKeFrame extends JFrame{

	String userName;
	ThongKeFrame parent;
	
//	Component thống kê
	JPanel thongkePanel, panelCircle1, panelCircle2, panelCircle3;
	JLabel titleLabel, labelCircle1, labelCircle2, labelCircle3;
	RoundPanel centerPanel, 
		circleDashBoardPanel, north, center;
	ProgressBar progressCircle1, progressCircle2, progressCircle3;
	
	public ThongKeFrame(String userName) {
		this.userName=userName;
		this.parent=this;
		
//		Tạo component bên phải
		initComponent();

//		Chạy số liệu
		init();
	}
		
	public void initComponent() {
		thongkePanel=new JPanel(); 
		thongkePanel.setLayout(new BorderLayout(5,5));
		thongkePanel.setBackground(new Color(220, 220, 220));
		
		centerPanel=new RoundPanel();
		centerPanel.setBackground(Color.WHITE);
		
//		Circle dash board
		circleDashBoard();
		
		centerPanel.add(circleDashBoardPanel);
		
		thongkePanel.add(centerPanel, BorderLayout.CENTER);
	}
	
	public void circleDashBoard() {
		circleDashBoardPanel=new RoundPanel();
		circleDashBoardPanel.setLayout(new BorderLayout());
		circleDashBoardPanel.setOpaque(false);
		
		north=new RoundPanel();
		north.setLayout(new FlowLayout(FlowLayout.LEFT));
		titleLabel=new JLabel("Báo cáo tháng");
		titleLabel.setFont(new Font("Segoe UI",1,16));
		titleLabel.setBorder(BorderFactory.createEmptyBorder(1, 10, 1, 1));
		north.add(titleLabel);
		circleDashBoardPanel.add(north, BorderLayout.NORTH);
		
		center= new RoundPanel();
		
		panelCircle1=new JPanel(); panelCircle1.setOpaque(false);
		labelCircle1= new JLabel("Thống kê ứng viên");
		labelCircle1.setFont(new Font("Segoe UI",0,16));
		labelCircle1.setHorizontalAlignment(SwingConstants.CENTER);
		progressCircle1=new ProgressBar();
		progressCircle1.setBackground(new Color(66, 246, 84));
		progressCircle1.setBorder(BorderFactory.createEmptyBorder());
		progressCircle1.setValue(60);
		GroupLayout layout1=new GroupLayout(panelCircle1); panelCircle1.setLayout(layout1);
		layout1.setHorizontalGroup(
				layout1.createParallelGroup(Alignment.LEADING)
					   .addGroup(layout1.createSequentialGroup()
							            .addContainerGap()
							            .addGroup(layout1.createParallelGroup(Alignment.LEADING, false)
							            		         .addComponent(progressCircle1, GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,Short.MAX_VALUE)
							            		         .addComponent(labelCircle1, GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,Short.MAX_VALUE)
							            		 )
							            .addContainerGap()
				        )
		);
		layout1.setVerticalGroup(
				layout1.createParallelGroup(Alignment.LEADING)
				       .addGroup(Alignment.TRAILING, layout1.createSequentialGroup()
				    		   								.addContainerGap()
				    		   								.addComponent(labelCircle1)
				    		   								.addGap(18, 18, 18)
				    		   								.addComponent(progressCircle1,GroupLayout.PREFERRED_SIZE,142,GroupLayout.PREFERRED_SIZE)
				    		   								.addContainerGap()
				       )
		);
		center.add(panelCircle1);
		
		panelCircle2=new JPanel(); panelCircle2.setOpaque(false);
		labelCircle2= new JLabel("Thống kê hồ sơ");
		labelCircle2.setFont(new Font("Segoe UI",0,16));
		labelCircle2.setHorizontalAlignment(SwingConstants.CENTER);
		progressCircle2=new ProgressBar();
		progressCircle2.setBackground(new Color(132, 66, 246));
		progressCircle2.setBorder(BorderFactory.createEmptyBorder());
		progressCircle2.setValue(70);
		GroupLayout layout2=new GroupLayout(panelCircle2); panelCircle2.setLayout(layout2);
		layout2.setHorizontalGroup(
				layout2.createParallelGroup(Alignment.LEADING)
					   .addGroup(layout2.createSequentialGroup()
							            .addContainerGap()
							            .addGroup(layout2.createParallelGroup(Alignment.LEADING, false)
							            		         .addComponent(progressCircle2, GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,Short.MAX_VALUE)
							            		         .addComponent(labelCircle2, GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,Short.MAX_VALUE)
							            		 )
							            .addContainerGap()
				        )
		);
		layout2.setVerticalGroup(
				layout2.createParallelGroup(Alignment.LEADING)
				       .addGroup(Alignment.TRAILING, layout2.createSequentialGroup()
				    		   								.addContainerGap()
				    		   								.addComponent(labelCircle2)
				    		   								.addGap(18, 18, 18)
				    		   								.addComponent(progressCircle2,GroupLayout.PREFERRED_SIZE,142,GroupLayout.PREFERRED_SIZE)
				    		   								.addContainerGap()
				       )
		);
		center.add(panelCircle2);
		
		panelCircle3=new JPanel(); panelCircle3.setOpaque(false);
		labelCircle3= new JLabel("Thống kê hợp đồng");
		labelCircle3.setFont(new Font("Segoe UI",0,16));
		labelCircle3.setHorizontalAlignment(SwingConstants.CENTER);
		progressCircle3=new ProgressBar();
		progressCircle3.setBackground(new Color(66, 193, 246));
		progressCircle3.setBorder(BorderFactory.createEmptyBorder());
		progressCircle3.setValue(80);
		GroupLayout layout3=new GroupLayout(panelCircle3); panelCircle3.setLayout(layout3);
		layout3.setHorizontalGroup(
				layout3.createParallelGroup(Alignment.LEADING)
					   .addGroup(layout3.createSequentialGroup()
							            .addContainerGap()
							            .addGroup(layout3.createParallelGroup(Alignment.LEADING, false)
							            		         .addComponent(progressCircle3, GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,Short.MAX_VALUE)
							            		         .addComponent(labelCircle3, GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,Short.MAX_VALUE)
							            		 )
							            .addContainerGap()
				        )
		);
		layout3.setVerticalGroup(
				layout3.createParallelGroup(Alignment.LEADING)
				       .addGroup(Alignment.TRAILING, layout3.createSequentialGroup()
				    		   								.addContainerGap()
				    		   								.addComponent(labelCircle3)
				    		   								.addGap(18, 18, 18)
				    		   								.addComponent(progressCircle3,GroupLayout.PREFERRED_SIZE,142,GroupLayout.PREFERRED_SIZE)
				    		   								.addContainerGap()
				       )
		);
		center.add(panelCircle3);
		
		circleDashBoardPanel.add(center, BorderLayout.CENTER);
	}
	
	public void init() {
		progressCircle1.start();
		progressCircle2.start();
		progressCircle3.start();
	}
	
	public JPanel getPanel() {
		return this.thongkePanel;
	}
}
