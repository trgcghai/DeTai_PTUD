package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;

import javax.swing.JDialog;

import dao.Customer_DAO;
import dao.Movie_DAO;
import dao.Room_DAO;
import dao.Screening_DAO;
import dao.Sitting_DAO;
import dao.Ticket_DAO;
import entity.Bill;
import entity.Customer;
import entity.Movie;
import entity.Room;
import entity.Screening;
import entity.Sitting;
import entity.Ticket;
import controller.PDFHelper;

import javax.swing.*;

public class TurnoverDetailCountDialog extends JDialog implements ActionListener {
	
	JPanel panelNorth, panelCenter, panelCenterC, panelCenterS, panelSouth;
	JLabel label1, label2, label3, label4, title,
		idBillLabel, nameCusLabel, nameEmpLabel, phoneLabel, dateLabel, addressLabel,
		idBillText, nameCusText, nameEmpText, phoneText, dateText, addressText,
		nameMovieLabel, screeningLabel, timeLabel, roomLabel, sitLabel, totalLabel,
		nameMovieText, screeningText, timeText, roomText, sitText, totalText;
	JButton btnPrintCount;
	Bill curBill;
	Customer cus;
	Movie movie;
	Ticket ticket;
	Screening screening;
	Room room;
	
	public TurnoverDetailCountDialog(Frame parent, boolean modal, Bill bill) {
		super(parent, modal);
		setTitle("Chi tiết");
		setResizable(false);
		setSize(580,600);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		
		JPanel panel=new JPanel(); panel.setLayout(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
		setContentPane(panel);
		
		curBill = bill;
		
		Customer_DAO cus_dao = new Customer_DAO();
		Ticket_DAO ticket_dao = new Ticket_DAO();
		Movie_DAO movie_dao = new Movie_DAO();
		Screening_DAO screening_dao = new Screening_DAO();
		Room_DAO room_dao = new Room_DAO();
		
		ticket = ticket_dao.getAllTicketById(bill.getTicket().getIdTicket()).get(0);
		cus = cus_dao.getAllCustomerById(bill.getCustomer().getIdCustomer()).get(0);
		screening = screening_dao.getScreeningById(ticket.getScreening().getIdScreening()).get(0);
		movie = movie_dao.getMovieByID(screening.getMovie().getIdMovie());
		room = room_dao.getRoomById(screening.getRoom().getIdRoom());
		
		initComponent();
		
		btnPrintCount.addActionListener(this);
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
		idBillText=new JLabel(curBill.getIdBill()); idBillText.setFont(new Font("Segoe UI",0,15));
		panelCenterC.add(idBillText,gbc);
		gbc.gridx=2;  gbc.insets=new Insets(10, 5, 10, 10);
		nameEmpLabel=new JLabel("Tên Nhân Viên:"); nameEmpLabel.setFont(new Font("Segoe UI",0,15));
		panelCenterC.add(nameEmpLabel,gbc);
		gbc.gridx=3; gbc.insets=new Insets(10, 0, 10, 5);
		String[] nameEmployee=curBill.getEmployee().getName().split(" ");
		nameEmpText=new JLabel(nameEmployee[nameEmployee.length-2]+nameEmployee[nameEmployee.length-1]); 
		nameEmpText.setFont(new Font("Segoe UI",0,15));
		panelCenterC.add(nameEmpText,gbc);
		
		gbc.gridx=0; gbc.gridy=1; gbc.insets=new Insets(10, 5, 10, 10); gbc.anchor=GridBagConstraints.WEST;
		nameCusLabel=new JLabel("Tên Khách Hàng:"); nameCusLabel.setFont(new Font("Segoe UI",0,15));
		panelCenterC.add(nameCusLabel,gbc);
		gbc.gridx=1; gbc.insets=new Insets(10, 0, 10, 30);
		nameCusText=new JLabel(curBill.getCustomer().getName()); nameCusText.setFont(new Font("Segoe UI",0,15));
		panelCenterC.add(nameCusText,gbc);
		gbc.gridx=2;  gbc.insets=new Insets(10, 5, 10, 10);
		phoneLabel=new JLabel("Số Điện Thoại:"); phoneLabel.setFont(new Font("Segoe UI",0,15));
		panelCenterC.add(phoneLabel,gbc);
		gbc.gridx=3; gbc.insets=new Insets(10, 0, 10, 5);
		phoneText=new JLabel(cus.getPhone()); phoneText.setFont(new Font("Segoe UI",0,15));
		panelCenterC.add(phoneText,gbc);
		
		DateTimeFormatter dft = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		gbc.gridx=0; gbc.gridy=2; gbc.insets=new Insets(10, 5, 10, 10); gbc.anchor=GridBagConstraints.WEST;
		dateLabel=new JLabel("Ngày Mua:"); dateLabel.setFont(new Font("Segoe UI",0,15));
		panelCenterC.add(dateLabel,gbc);
		gbc.gridx=1; gbc.insets=new Insets(10, 0, 10, 30);
		dateText=new JLabel(curBill.getDateBill().format(dft)); dateText.setFont(new Font("Segoe UI",0,15));
		panelCenterC.add(dateText,gbc);
		gbc.gridx=2; gbc.insets=new Insets(10, 5, 10, 10); gbc.anchor=GridBagConstraints.WEST;
		dateLabel=new JLabel("Giới tính:"); dateLabel.setFont(new Font("Segoe UI",0,15));
		panelCenterC.add(dateLabel,gbc);
		gbc.gridx=3; gbc.insets=new Insets(10, 0, 10, 30);
		dateText=new JLabel(cus.getGender() == 0 ? "Nữ" : "Nam"); dateText.setFont(new Font("Segoe UI",0,15));
		panelCenterC.add(dateText,gbc);
		
		
		panelCenterS=new JPanel();
		panelCenterS.setPreferredSize(new Dimension(getWidth(),200));
		panelCenterS.setLayout(new GridBagLayout());
		
		gbc.gridx=0; gbc.gridy=0; gbc.anchor=GridBagConstraints.WEST; gbc.insets=new Insets(10, 5, 10, 10);
		nameMovieLabel=new JLabel("Tên Phim:"); nameMovieLabel.setFont(new Font("Segoe UI",0,15));
		panelCenterS.add(nameMovieLabel,gbc);
		gbc.gridx=1; gbc.insets=new Insets(10, 0, 10, 30);
		nameMovieText=new JLabel(movie.getName()); nameMovieText.setFont(new Font("Segoe UI",0,15));
		panelCenterS.add(nameMovieText,gbc);
		gbc.gridx=2; gbc.insets=new Insets(10, 5, 10, 10);
		screeningLabel=new JLabel("Suất Chiếu:"); screeningLabel.setFont(new Font("Segoe UI",0,15));
		panelCenterS.add(screeningLabel,gbc);
		gbc.gridx=3; gbc.insets=new Insets(10, 0, 10, 30);
		screeningText=new JLabel(screening.getTimeStart().toLocalDate().format(dft)); screeningText.setFont(new Font("Segoe UI",0,15));
		panelCenterS.add(screeningText,gbc);
		
		gbc.gridx=0; gbc.gridy=1; gbc.insets=new Insets(10, 5, 10, 10);
		timeLabel=new JLabel("Thời Gian:"); timeLabel.setFont(new Font("Segoe UI",0,15));
		panelCenterS.add(timeLabel,gbc);
		gbc.gridx=1; gbc.insets=new Insets(10, 0, 10, 10);
		timeText=new JLabel(screening.getTimeStart().toLocalTime().toString()); timeText.setFont(new Font("Segoe UI",0,15));
		panelCenterS.add(timeText,gbc);
		gbc.gridx=2; gbc.insets=new Insets(10, 5, 10, 10); gbc.anchor=GridBagConstraints.WEST;
		roomLabel=new JLabel("Phòng:"); roomLabel.setFont(new Font("Segoe UI",0,15));
		panelCenterS.add(roomLabel,gbc);
		gbc.gridx=3; gbc.insets=new Insets(10, 0, 10, 30); gbc.anchor=GridBagConstraints.WEST;
		roomText=new JLabel(String.valueOf(room.getNumberRoom())); roomText.setFont(new Font("Segoe UI",0,15));
		panelCenterS.add(roomText,gbc);
		
		gbc.gridx=0; gbc.gridy=2; gbc.insets=new Insets(10, 5, 10, 10); gbc.anchor=GridBagConstraints.WEST;
		sitLabel=new JLabel("Ghế:"); sitLabel.setFont(new Font("Segoe UI",0,15));
		panelCenterS.add(sitLabel,gbc);
		gbc.gridx=1; gbc.insets=new Insets(10, 0, 10, 5); gbc.gridwidth=GridBagConstraints.REMAINDER;
		String seats = "";
		Sitting_DAO sitting_dao = new Sitting_DAO();
		for (Sitting s : sitting_dao.getSittingByTicket(ticket.getIdTicket())) {
			seats += s.getNumberSitting();
			seats += ";";
		}
		if (seats.charAt(seats.length() - 1) == ';') {
			seats = seats.substring(0, seats.length() - 1);
		}
		sitText=new JLabel(seats); sitText.setFont(new Font("Segoe UI",0,15));
		panelCenterS.add(sitText,gbc);
		
		gbc.gridx=2; gbc.gridy=3; gbc.insets=new Insets(10, 5, 10, 10); gbc.gridwidth=1; gbc.anchor=GridBagConstraints.WEST;
		totalLabel=new JLabel("Tổng tiền:"); totalLabel.setFont(new Font("Segoe UI",0,15));
		panelCenterS.add(totalLabel,gbc);
		gbc.gridx=3; gbc.insets=new Insets(10, 0, 10, 5); gbc.anchor=GridBagConstraints.WEST;
		DecimalFormat df = new DecimalFormat("#,###");
		totalText=new JLabel(df.format(curBill.getTotal())+" VNĐ"); totalText.setFont(new Font("Segoe UI",0,15));
		panelCenterS.add(totalText,gbc);
		
		panelSouth=new JPanel(); panelSouth.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
		btnPrintCount=new JButton("In Hóa Đơn"); btnPrintCount.setFont(new Font("Segoe UI",1,14));
		panelSouth.add(btnPrintCount);
		
		panelCenter.add(panelCenterC, BorderLayout.CENTER);
		panelCenter.add(panelCenterS, BorderLayout.SOUTH);
		
		add(panelNorth, BorderLayout.NORTH);
		add(panelCenter, BorderLayout.CENTER);
		add(panelSouth, BorderLayout.SOUTH);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if (o.equals(btnPrintCount)) {
			panelSouth.remove(btnPrintCount);
			PDFHelper.exportToPdf(this);
			panelSouth.add(btnPrintCount);
		}
	}
}
