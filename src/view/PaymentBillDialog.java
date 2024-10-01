package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JDialog;

import dao.Bill_DAO;
import dao.Customer_DAO;
import dao.Movie_DAO;
import dao.Room_DAO;
import dao.Sitting_DAO;
import dao.Ticket_DAO;
import entity.Bill;
import entity.Customer;
import entity.NhanVien;
import entity.Screening;
import entity.Sitting;
import entity.Ticket;

import javax.swing.*;

public class PaymentBillDialog extends JDialog implements ActionListener{
	
	JPanel panelNorth, panelCenter, panelCenterC, panelCenterS, panelSouth;
	JLabel label1, label2, label3, label4, title,
		idBillLabel, nameCusLabel, nameEmpLabel, phoneLabel, dateLabel, genderLabel,
		idBillText, nameCusText, nameEmpText, phoneText, dateText, genderText,
		nameMovieLabel, screeningLabel, timeLabel, roomLabel, sitLabel, totalLabel,
		nameMovieText, screeningText, timeText, roomText, sitText, totalText;
	
	JButton btnVerify, btnDispose;
	JLabel ticketIDText;
	
	BookTicketFrame bookTicket;
	Customer customer; 
	NhanVien employee;
	Screening screening; 
	ArrayList<Sitting> chooseSitting;
	
	private static double price=80000;
	private Bill_DAO billDAO;
	private Movie_DAO movieDAO;
	private Room_DAO roomDAO;
	private Customer_DAO customerDAO;
	private Ticket_DAO ticketDAO;
	private Sitting_DAO sittingDAO;

	public PaymentBillDialog(Frame parent, boolean modal) {
		super(parent, modal);
		setTitle("Hóa đơn");
		setResizable(false);
		setSize(650,600);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		
		JPanel panel=new JPanel(); panel.setLayout(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
		setContentPane(panel);
		
		initComponent();
		addActionListener();
		
		bookTicket=(BookTicketFrame) parent;
		billDAO=new Bill_DAO();
		movieDAO=new Movie_DAO();
		roomDAO=new Room_DAO();
		customerDAO=new Customer_DAO();
		ticketDAO=new Ticket_DAO();
		sittingDAO=new Sitting_DAO();
		
	}
	
	public PaymentBillDialog(Frame parent, boolean modal, Customer customer, NhanVien employee, 
			Screening screening, ArrayList<Sitting> chooseSitting, JLabel ticketIDText) {
		this(parent,modal);
		
		this.customer=customer;
		this.employee=employee;
		this.screening=screening;
		this.chooseSitting=chooseSitting;
		this.ticketIDText=ticketIDText;
		
		loadData();
		fillInforBill();
	}
	
	public void initComponent() {
		panelNorth=new JPanel(); panelNorth.setLayout(new GridLayout(4, 1, 10, 5));
		label1=new JLabel("-HDT-RẠP CHIẾU PHIM", SwingConstants.CENTER); 
		label1.setFont(new Font("Segoe UI",0,13));
		label2=new JLabel("-Địa chỉ: 12 Nguyễn Văn Bảo, Phường 4, Quận Gò Vấp, Thành phố Hồ Chí Minh", SwingConstants.CENTER);
		label2.setFont(new Font("Segoe UI",0,13));
		label3=new JLabel("-Website: HDT.com.vn | Fanpage: fb.com/HDT", SwingConstants.CENTER); 
		label3.setFont(new Font("Segoe UI",0,13));
		label4=new JLabel("-Hotline: 0862880673", SwingConstants.CENTER); 
		label4.setFont(new Font("Segoe UI",0,13));
		panelNorth.add(label1); panelNorth.add(label2);
		panelNorth.add(label3); panelNorth.add(label4);
		
		panelCenter=new JPanel(); panelCenter.setLayout(new BorderLayout());
		title=new JLabel("HÓA ĐƠN PHIM", SwingConstants.CENTER); title.setFont(new Font("Segoe UI",1,20));
		panelCenter.add(title, BorderLayout.NORTH);
		
		panelCenterC=new JPanel(); 
		panelCenterC.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
		panelCenterC.setLayout(new GridBagLayout());
		GridBagConstraints gbc=new GridBagConstraints();
		
		gbc.gridx=0; gbc.gridy=0; gbc.insets=new Insets(10, 5, 10, 10); gbc.anchor=GridBagConstraints.WEST;
		idBillLabel=new JLabel("Mã Hóa Đơn:"); idBillLabel.setFont(new Font("Segoe UI",0,15));
		panelCenterC.add(idBillLabel,gbc);
		gbc.gridx=1; gbc.insets=new Insets(10, 0, 10, 30);
		idBillText=new JLabel(); idBillText.setFont(new Font("Segoe UI",0,15));
		panelCenterC.add(idBillText,gbc);
		gbc.gridx=2;  gbc.insets=new Insets(10, 5, 10, 10);
		nameEmpLabel=new JLabel("Tên Nhân Viên:"); nameEmpLabel.setFont(new Font("Segoe UI",0,15));
		panelCenterC.add(nameEmpLabel,gbc);
		gbc.gridx=3; gbc.insets=new Insets(10, 0, 10, 5);
		nameEmpText=new JLabel(); nameEmpText.setFont(new Font("Segoe UI",0,15));
		panelCenterC.add(nameEmpText,gbc);
		
		gbc.gridx=0; gbc.gridy=1; gbc.insets=new Insets(10, 5, 10, 10); gbc.anchor=GridBagConstraints.WEST;
		nameCusLabel=new JLabel("Tên Khách Hàng:"); nameCusLabel.setFont(new Font("Segoe UI",0,15));
		panelCenterC.add(nameCusLabel,gbc);
		gbc.gridx=1; gbc.insets=new Insets(10, 0, 10, 30);
		nameCusText=new JLabel(); nameCusText.setFont(new Font("Segoe UI",0,15));
		panelCenterC.add(nameCusText,gbc);
		gbc.gridx=2;  gbc.insets=new Insets(10, 5, 10, 10);
		phoneLabel=new JLabel("Số Điện Thoại:"); phoneLabel.setFont(new Font("Segoe UI",0,15));
		panelCenterC.add(phoneLabel,gbc);
		gbc.gridx=3; gbc.insets=new Insets(10, 0, 10, 5);
		phoneText=new JLabel(); phoneText.setFont(new Font("Segoe UI",0,15));
		panelCenterC.add(phoneText,gbc);
		
		gbc.gridx=0; gbc.gridy=2; gbc.insets=new Insets(10, 5, 10, 10); gbc.anchor=GridBagConstraints.WEST;
		dateLabel=new JLabel("Ngày Mua:"); dateLabel.setFont(new Font("Segoe UI",0,15));
		panelCenterC.add(dateLabel,gbc);
		gbc.gridx=1; gbc.insets=new Insets(10, 0, 10, 30);
		dateText=new JLabel(); dateText.setFont(new Font("Segoe UI",0,15));
		panelCenterC.add(dateText,gbc);
		gbc.gridx=2;  gbc.insets=new Insets(10, 5, 10, 10);
		genderLabel=new JLabel("Giới tính:"); genderLabel.setFont(new Font("Segoe UI",0,15));
		panelCenterC.add(genderLabel,gbc);
		gbc.gridx=3; gbc.insets=new Insets(10, 0, 10, 5);
		genderText=new JLabel(); genderText.setFont(new Font("Segoe UI",0,15));
		panelCenterC.add(genderText,gbc);
		
		
		panelCenterS=new JPanel();
		panelCenterS.setPreferredSize(new Dimension(getWidth(),200));
		panelCenterS.setLayout(new GridBagLayout());
		
		gbc.gridx=0; gbc.gridy=0; gbc.anchor=GridBagConstraints.WEST; gbc.insets=new Insets(10, 5, 10, 10);
		nameMovieLabel=new JLabel("Tên Phim:"); nameMovieLabel.setFont(new Font("Segoe UI",0,15));
		panelCenterS.add(nameMovieLabel,gbc);
		gbc.gridx=1;
		nameMovieText=new JLabel("King Kong"); nameMovieText.setFont(new Font("Segoe UI",0,15));
		panelCenterS.add(nameMovieText,gbc);
		
		gbc.gridx=0; gbc.gridy=1;
		screeningLabel=new JLabel("Suất Chiếu:"); screeningLabel.setFont(new Font("Segoe UI",0,15));
		panelCenterS.add(screeningLabel,gbc);
		gbc.gridx=1; 
		screeningText=new JLabel("23/04/2024"); screeningText.setFont(new Font("Segoe UI",0,15));
		panelCenterS.add(screeningText,gbc);
		gbc.gridx=2; 
		timeLabel=new JLabel("Thời Gian:"); timeLabel.setFont(new Font("Segoe UI",0,15));
		panelCenterS.add(timeLabel,gbc);
		gbc.gridx=3;
		timeText=new JLabel("09:00"); timeText.setFont(new Font("Segoe UI",0,15));
		panelCenterS.add(timeText,gbc);
		
		gbc.gridx=0; gbc.gridy=2; gbc.insets=new Insets(10, 5, 10, 10); gbc.anchor=GridBagConstraints.WEST;
		roomLabel=new JLabel("Phòng:"); roomLabel.setFont(new Font("Segoe UI",0,15));
		panelCenterS.add(roomLabel,gbc);
		gbc.gridx=1;  gbc.anchor=GridBagConstraints.WEST;
		roomText=new JLabel("R1"); roomText.setFont(new Font("Segoe UI",0,15));
		panelCenterS.add(roomText,gbc);
		gbc.gridx=2;  gbc.anchor=GridBagConstraints.WEST;
		sitLabel=new JLabel("Ghế:"); sitLabel.setFont(new Font("Segoe UI",0,15));
		panelCenterS.add(sitLabel,gbc);
		gbc.gridx=3;  gbc.gridwidth=GridBagConstraints.REMAINDER;
		sitText=new JLabel("D1;A1;B1"); sitText.setFont(new Font("Segoe UI",0,15));
		panelCenterS.add(sitText,gbc);
		
		gbc.gridx=2; gbc.gridy=3;  gbc.gridwidth=1; gbc.anchor=GridBagConstraints.WEST;
		totalLabel=new JLabel("Tổng tiền:"); totalLabel.setFont(new Font("Segoe UI",0,15));
		panelCenterS.add(totalLabel,gbc);
		gbc.gridx=3;  gbc.anchor=GridBagConstraints.WEST;
		totalText=new JLabel("160,000 VNĐ"); totalText.setFont(new Font("Segoe UI",0,15));
		panelCenterS.add(totalText,gbc);
		
		panelSouth=new JPanel(); panelSouth.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
		btnDispose=new JButton("Quay lại"); btnDispose.setFont(new Font("Segoe UI",1,14));
		btnDispose.setForeground(Color.WHITE); btnDispose.setBackground(Color.RED);
		btnVerify=new JButton("Xác nhận"); btnVerify.setFont(new Font("Segoe UI",1,14));
		btnVerify.setForeground(Color.WHITE); btnVerify.setBackground(Color.GREEN);
		
		panelSouth.add(btnDispose); panelSouth.add(btnVerify);
		
		panelCenter.add(panelCenterC, BorderLayout.CENTER);
		panelCenter.add(panelCenterS, BorderLayout.SOUTH);
		
		add(panelNorth, BorderLayout.NORTH);
		add(panelCenter, BorderLayout.CENTER);
		add(panelSouth, BorderLayout.SOUTH);
	}
	
//	Lập mã hóa đơn
	public void loadData() {
		billDAO.setListBill(billDAO.getAllBill());
		Collections.sort(billDAO.getListBill(), new Comparator<Bill>() {
			@Override
			public int compare(Bill o1, Bill o2) {
				// TODO Auto-generated method stub
				return Integer.parseInt(o1.getIdBill().substring(1)) - Integer.parseInt(o2.getIdBill().substring(1));
			}
		});
		int idAuto=1;
		for(Bill b: billDAO.getListBill()) {
			if( Integer.parseInt(b.getIdBill().substring(1))==idAuto) {
				idAuto++;
				Bill.setIdAuto(idAuto);
			}
		}
	}
	
//	Load thông tin vào hóa đơn
	public void fillInforBill() {
		idBillText.setText("B"+Bill.getIdAuto());
		nameEmpText.setText(employee.getName().split(" ")[employee.getName().split(" ").length-2]+" "+
							employee.getName().split(" ")[employee.getName().split(" ").length-1]);
		nameCusText.setText(customer.getName());
		phoneText.setText(customer.getPhone());
		DateTimeFormatter format=DateTimeFormatter.ofPattern("dd/MM/yyyy, HH:mm:ss");
		dateText.setText(format.format(LocalDateTime.now()));
		genderText.setText(customer.getGender()==1?"Nam":"Nữ");
		
		nameMovieText.setText(movieDAO.getMovieByID(screening.getMovie().getIdMovie()).getName());
		screeningText.setText(DateTimeFormatter.ofPattern("dd/MM/yyyy").format(screening.getTimeStart().toLocalDate()));
		timeText.setText(DateTimeFormatter.ofPattern("HH:mm").format(screening.getTimeStart().toLocalTime()));
		roomText.setText(String.valueOf(roomDAO.getRoomById(screening.getRoom().getIdRoom()).getNumberRoom()));
		var row="";
		for(int i=0;i<chooseSitting.size();i++) {
			if(i!=chooseSitting.size()-1) {
				row+=chooseSitting.get(i).getNumberSitting()+",";
			}
			else {
				row+=chooseSitting.get(i).getNumberSitting();
			}
		}
		sitText.setText(row);
		DecimalFormat numberFormat=new DecimalFormat("#,##0");
		totalText.setText(numberFormat.format(chooseSitting.size()*price)+" VNĐ");
	}

//	Xác nhận thanh toán hóa đơn
	public void verify() {
		if(customerDAO.getCustomerByPhone(customer.getPhone())==null) {
			customerDAO.create(customer);
		}
		
		Ticket ticket=new Ticket(ticketIDText.getText(), LocalDateTime.now(),
				chooseSitting.size()*price, screening);
		ticketDAO.create(ticket);
		
		Bill bill=new Bill(idBillText.getText(),LocalDate.now(),chooseSitting.size()*price,
				customer,employee,ticket);
		billDAO.create(bill);
		
		for(Sitting i: chooseSitting) {
			sittingDAO.updateTicketAndStateForSitting(screening, i, ticket);
		}
		
		JOptionPane.showMessageDialog(rootPane, "Thanh toán thành công");
		Ticket.setIdAuto(Ticket.getIdAuto()+1);
		bookTicket.resetInfor();
		this.dispose();
	}
	
//	Listener
	public void addActionListener() {
		btnDispose.addActionListener(this);
		btnVerify.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		var obj=e.getSource();
		if(obj.equals(btnDispose)) {
			this.dispose();
		}
		else if(obj.equals(btnVerify)) {
			verify();
		}
	}
}
