package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.*;

import dao.Sitting_DAO;
import entity.Screening;

public class BookSitDialog extends JDialog implements ActionListener, MouseListener {
	
	JPanel screenMain, allSitPanel, paymentPanel, sitEmptyPanel, sitChoosePanel, sitDonePanel;
	JLabel screenTitle, sitLabel, text, sitEmptyText, sitChooseText, sitDoneText,
			priceLabel, chairLabel, chairText, priceText, qtyLabel, qtyText, totalLabel, totalText;
	JButton btnVerify, btnComeback;
	
	private BookTicketFrame home;
	private ArrayList<Component> sitLabels;
	private ArrayList<String> chairTexts;
	private Screening screening;
	private Sitting_DAO sittingDAO;
	
	
	public BookSitDialog(Frame parent, boolean modal) {
		super(parent, modal);
		setTitle("Đặt chỗ ngồi");
		setResizable(false);
		setLocation(280, 150);
		setSize(1000,500);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		home=(BookTicketFrame) parent;
		sitLabels=new ArrayList<Component>();
		chairTexts=new ArrayList<String>();
		
		initComponent();
		
		addListener();
		
		sittingDAO=new Sitting_DAO();
	}
	
	public BookSitDialog(Frame parent, boolean modal, Screening screening) {
		this(parent, modal);
		this.screening=screening;
		displaySitDone();
	}
	
	public void initComponent() {
		screenMain=new JPanel();
		screenMain.setLayout(new FlowLayout(FlowLayout.CENTER));
		screenMain.setBackground(new Color(33/255f, 33/255f, 33/255f));
		
		screenTitle=new JLabel("MÀN HÌNH CHÍNH");
		screenTitle.setFont(new Font("Segoe UI",1,16));
		screenTitle.setForeground(Color.WHITE);
		screenMain.add(screenTitle);
		
		
		allSitPanel=new JPanel();
		allSitPanel.setBackground(new Color(33/255f, 33/255f, 33/255f));
		JPanel panelSit=new JPanel();
		panelSit.setPreferredSize(new Dimension(540,260));
		panelSit.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelSit.setBackground(new Color(33/255f, 33/255f, 33/255f));
		
		String[] alphabets= {"A","B","C","D","E"};
		for(int i=0;i<5;i++) {
			for(int j=1;j<=10;j++) {
				sitLabel=new JLabel(new ImageIcon(getClass().getResource("/image/sitEmpty.png")));
				sitLabel.setLayout(null);
				text=new JLabel(alphabets[i]+j);
				text.setBounds(16, 10, 50, 20);
				text.setForeground(Color.WHITE);
				sitLabel.add(text);
				sitLabels.add(sitLabel);
				panelSit.add(sitLabel);				
			}
		}
		allSitPanel.add(panelSit);
		
		
		paymentPanel=new JPanel();
		paymentPanel.setLayout(new BorderLayout());
		paymentPanel.setBackground(new Color(33/255f, 33/255f, 33/255f));
		
		JPanel panelBtn=new JPanel();
		panelBtn.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 5));
		panelBtn.setBackground(new Color(33/255f, 33/255f, 33/255f));
		btnVerify=new JButton("Xác nhận");
		btnVerify.setFont(new Font("Segoe UI",1,13));
		btnVerify.setBackground(new Color(255, 105, 105));
		btnVerify.setForeground(Color.WHITE);
		btnVerify.setPreferredSize(new Dimension(100,30));
		btnComeback=new JButton("Quay lại");
		btnComeback.setFont(new Font("Segoe UI",1,13));
		btnComeback.setBackground(Color.GRAY);
		btnComeback.setForeground(Color.WHITE);
		btnComeback.setPreferredSize(new Dimension(100,30));
		panelBtn.add(btnComeback);
		panelBtn.add(btnVerify);
		paymentPanel.add(panelBtn, BorderLayout.SOUTH);
		
		JPanel panelTotal=new JPanel();
		panelTotal.setLayout(new GridLayout(4, 2));
		panelTotal.setBackground(new Color(33/255f, 33/255f, 33/255f));
		panelTotal.setBorder(BorderFactory.createEmptyBorder(5, 200, 0, 10));
		
		JPanel res=new JPanel();
		res.setLayout(new FlowLayout(FlowLayout.LEADING));
		res.setBackground(new Color(33/255f, 33/255f, 33/255f));
		sitEmptyPanel=new JPanel();
		sitEmptyPanel.setBackground(Color.GREEN);
		sitEmptyPanel.setPreferredSize(new Dimension(100,20));
		sitEmptyText=new JLabel("Ghế đang đặt");
		sitEmptyText.setFont(new Font("Segoe UI",1,13));
		sitEmptyText.setForeground(Color.WHITE);
		res.add(sitEmptyPanel); res.add(sitEmptyText);
		panelTotal.add(res);
		
		res=new JPanel();
		res.setLayout(new FlowLayout(FlowLayout.LEADING));
		res.setBackground(new Color(33/255f, 33/255f, 33/255f));
		priceLabel=new JLabel("Giá: ");
		priceLabel.setForeground(Color.WHITE);
		priceLabel.setFont(new Font("Segoe UI",1,13));
		priceText=new JLabel("80.000 VNĐ");
		priceText.setForeground(Color.WHITE);
		priceText.setFont(new Font("Segoe UI",1,13));
		res.add(priceLabel); res.add(priceText);
		panelTotal.add(res);
		
		res=new JPanel();
		res.setLayout(new FlowLayout(FlowLayout.LEADING));
		res.setBackground(new Color(33/255f, 33/255f, 33/255f));
		sitChoosePanel=new JPanel();
		sitChoosePanel.setBackground(Color.RED);
		sitChoosePanel.setPreferredSize(new Dimension(100,20));
		sitChooseText=new JLabel("Ghế trống");
		sitChooseText.setFont(new Font("Segoe UI",1,13));
		sitChooseText.setForeground(Color.WHITE);
		res.add(sitChoosePanel); res.add(sitChooseText);
		panelTotal.add(res);
		
		res=new JPanel();
		res.setLayout(new FlowLayout(FlowLayout.LEADING));
		res.setBackground(new Color(33/255f, 33/255f, 33/255f));
		chairLabel=new JLabel("Ghế: ");
		chairLabel.setForeground(Color.WHITE);
		chairLabel.setFont(new Font("Segoe UI",1,13));
		chairText=new JLabel();
		chairText.setForeground(Color.WHITE);
		chairText.setFont(new Font("Segoe UI",1,13));
		res.add(chairLabel); res.add(chairText);
		panelTotal.add(res);
		
		res=new JPanel();
		res.setLayout(new FlowLayout(FlowLayout.LEADING));
		res.setBackground(new Color(33/255f, 33/255f, 33/255f));
		sitDonePanel=new JPanel();
		sitDonePanel.setBackground(Color.GRAY);
		sitDonePanel.setPreferredSize(new Dimension(100,20));
		sitDoneText=new JLabel("Ghế đã đặt");
		sitDoneText.setFont(new Font("Segoe UI",1,13));
		sitDoneText.setForeground(Color.WHITE);
		res.add(sitDonePanel); res.add(sitDoneText);
		panelTotal.add(res);
		
		res=new JPanel();
		res.setLayout(new FlowLayout(FlowLayout.LEADING));
		res.setBackground(new Color(33/255f, 33/255f, 33/255f));
		qtyLabel=new JLabel("Số lượng: ");
		qtyLabel.setForeground(Color.WHITE);
		qtyLabel.setFont(new Font("Segoe UI",1,13));
		qtyText=new JLabel();
		qtyText.setText("0");
		qtyText.setForeground(Color.WHITE);
		qtyText.setFont(new Font("Segoe UI",1,13));
		res.add(qtyLabel); res.add(qtyText);
		panelTotal.add(res);
		
		JPanel tmp=new JPanel(); tmp.setBackground(new Color(33/255f, 33/255f, 33/255f));
		panelTotal.add(tmp);
		
		res=new JPanel();
		res.setLayout(new FlowLayout(FlowLayout.LEADING));
		res.setBackground(new Color(33/255f, 33/255f, 33/255f));
		totalLabel=new JLabel("Tổng giá: ");
		totalLabel.setForeground(Color.WHITE);
		totalLabel.setFont(new Font("Segoe UI",1,13));
		totalText=new JLabel("0 VNĐ");
		totalText.setForeground(Color.WHITE);
		totalText.setFont(new Font("Segoe UI",1,13));
		res.add(totalLabel); res.add(totalText);
		panelTotal.add(res);
		
		
		
		paymentPanel.add(panelTotal, BorderLayout.CENTER);
		
		
		add(screenMain, BorderLayout.NORTH);
		add(allSitPanel, BorderLayout.CENTER);
		add(paymentPanel, BorderLayout.SOUTH);
	}
	
//	Thay đổi màu sắc ghế khi chọn
	public void changeSitColorChoose(Component c) {
		JLabel labelOutside=(JLabel)c;
		JLabel resChoose=new JLabel(new ImageIcon(getClass().getResource("/image/sitChoose.png")));
		JLabel resEmpty=new JLabel(new ImageIcon(getClass().getResource("/image/sitEmpty.png")));
		
		if(labelOutside.getIcon().toString().equalsIgnoreCase(resChoose.getIcon().toString())) {
			labelOutside.setIcon(new ImageIcon(getClass().getResource("/image/sitEmpty.png")));
			JLabel labelInside=(JLabel)((JLabel)c).getComponents()[0];
			chairTexts.remove(labelInside.getText());
			String row="";
			if(chairTexts.size()==0) {
				chairText.setText("");
			}
			else {
				for(String tmp: chairTexts) {
					row+=tmp+", ";
				}
				chairText.setText(row);				
			}
		}
		else if(labelOutside.getIcon().toString().equalsIgnoreCase(resEmpty.getIcon().toString())){
			labelOutside.setIcon(new ImageIcon(getClass().getResource("/image/sitChoose.png")));
			JLabel labelInside=(JLabel)((JLabel)c).getComponents()[0];
			chairTexts.add(labelInside.getText());
			String row="";
			for(String tmp: chairTexts) {
				row+=tmp+", ";
			}
			chairText.setText(row);
		}
		qtyText.setText(String.valueOf(chairTexts.size()));
		
		DecimalFormat formatter=new DecimalFormat("#,##0");
		double totalPrice=Integer.parseInt(qtyText.getText()) * 80000;
		totalText.setText(formatter.format(totalPrice)+" VNĐ");
	}
	
//	Hiển thị những ghế đã đặt
	public void displaySitDone() {
		ArrayList<String> sitDone=sittingDAO.getSittingDone(screening);
		for(Component c: sitLabels) {
			JLabel labelOutside=(JLabel)c;
			JLabel labelInside=(JLabel)labelOutside.getComponents()[0];
			String text=labelInside.getText();
			for(String s: sitDone) {
				if(text.equalsIgnoreCase(s)) {
					labelOutside.setIcon(new ImageIcon(getClass().getResource("/image/sitDone.png")));
				}
			}
		}
	}
	
//	Listener
	public void addListener() {
		btnComeback.addActionListener(this);
		btnVerify.addActionListener(this);
		
		for(Component c: sitLabels) {
			c.addMouseListener(this);
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		var obj=e.getSource();
		if(obj.equals(btnComeback)) {
			this.dispose();
		}
		else if(obj.equals(btnVerify)) {
			if(chairTexts.size()==0) {
				JOptionPane.showMessageDialog(rootPane, "Vui lòng chọn ghế ngồi");				
			}
			else {
				home.callBackSitInfor(chairTexts, totalText);
				this.dispose();
			}
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		var obj=e.getSource();
		for(Component c:sitLabels) {
			if(obj.equals(c)) {
				changeSitColorChoose(c);
				break;
			}
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
