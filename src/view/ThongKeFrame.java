package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import component.RoundPanel;
import component.progress.ProgressBar;
import entity.NhanVien;

public class ThongKeFrame extends JFrame implements MouseListener{

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
		thongkePanel.setBackground(new Color(89, 145, 144));
		
		centerPanel=new RoundPanel();
		centerPanel.setBackground(new Color(89, 145, 144));
		
//		Circle dash board
		circleDashBoard();
		
		centerPanel.add(circleDashBoardPanel);
		
		thongkePanel.add(centerPanel, BorderLayout.CENTER);
	}
	
	public JLabel createLabel(String title) {
		JLabel label = new JLabel(title);
		label.setFont(new Font("Segoe UI",0,16));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		
		return label;
	}
	
	public ProgressBar createProgressBar(int value, Color color) {
		ProgressBar progressCircle = new ProgressBar();
		progressCircle.setBackground(color);
		progressCircle.setBorder(BorderFactory.createEmptyBorder());
		progressCircle.setValue(value);
		return progressCircle;
	}
	
	public void preparePanelCircle(JPanel panel, ProgressBar progressBar, JLabel label) {
		GroupLayout layout=new GroupLayout(panel); 
		panel.setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(Alignment.LEADING)
					   .addGroup(layout.createSequentialGroup()
							            .addContainerGap()
							            .addGroup(layout.createParallelGroup(Alignment.LEADING, false)
							            		         .addComponent(progressBar, GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,Short.MAX_VALUE)
							            		         .addComponent(label, GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,Short.MAX_VALUE)
							            		 )
							            .addContainerGap()
				        )
		);
		layout.setVerticalGroup(
				layout.createParallelGroup(Alignment.LEADING)
				       .addGroup(Alignment.TRAILING, layout.createSequentialGroup()
				    		   								.addContainerGap()
				    		   								.addComponent(label)
				    		   								.addGap(18, 18, 18)
				    		   								.addComponent(progressBar,GroupLayout.PREFERRED_SIZE,142,GroupLayout.PREFERRED_SIZE)
				    		   								.addContainerGap()
				       )
		);
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
		
		panelCircle1=new JPanel(); 
		panelCircle1.setOpaque(false);
		labelCircle1= createLabel("Thống kê nhân viên");
		progressCircle1= createProgressBar(60, new Color(66, 246, 84));
		preparePanelCircle(panelCircle1, progressCircle1, labelCircle1);
		
		panelCircle2=new JPanel(); 
		panelCircle2.setOpaque(false);
		labelCircle2= createLabel("Thống kê nhà tuyển dụng");
		progressCircle2= createProgressBar(70, new Color(132, 66, 246));
		preparePanelCircle(panelCircle2, progressCircle2, labelCircle2);
		
		panelCircle3=new JPanel(); panelCircle3.setOpaque(false);
		labelCircle3= createLabel("Thống kê hợp đồng");
		progressCircle3= createProgressBar(80, new Color(66, 193, 246));
		preparePanelCircle(panelCircle3, progressCircle3, labelCircle3);

		center.add(panelCircle1);
		center.add(panelCircle2);
		center.add(panelCircle3);
		
		circleDashBoardPanel.add(center, BorderLayout.CENTER);
	}
	
	public void init() {
		progressCircle1.start();
		progressCircle2.start();
		progressCircle3.start();
		
		labelCircle1.addMouseListener(this);
		labelCircle2.addMouseListener(this);
		labelCircle3.addMouseListener(this);
	}
	
	public JPanel getPanel() {
		return this.thongkePanel;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		var obj=e.getSource();
		if(obj.getClass().equals(JLabel.class)) {
			if(((JLabel)obj).getText().equals("Thống kê nhà tuyển dụng")) {
				centerPanel.removeAll();
				centerPanel.add(new ThongKeNhaTuyenDungFrame(userName).getPanel());
			}
			else if(((JLabel)obj).getText().equals("Thống kê hợp đồng")) {
				centerPanel.removeAll();
				centerPanel.add(new ThongKeHopDongFrame(userName).getPanel());
			}
			else if(((JLabel)obj).getText().equals("Thống kê nhân viên")) {
				centerPanel.removeAll();
				centerPanel.add(new ThongKeNhanVienFrame(userName).getPanel());
			}
			centerPanel.revalidate();
			centerPanel.repaint();
		}
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
}
