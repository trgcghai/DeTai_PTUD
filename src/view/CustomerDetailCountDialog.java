package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controller.ExcelHelper;
import entity.Bill;

public class CustomerDetailCountDialog extends JDialog implements ActionListener{
	
	JPanel panelNorth;
	JLabel titleLabel;
	JButton btnPrintCount;
	JTable table;
	DefaultTableModel modelTable;
	JScrollPane scroll;
	
	public CustomerDetailCountDialog(Frame parent, boolean modal, ArrayList<Bill> list) {
		super(parent, modal);
		setTitle("Chi tiết");
		setResizable(false);
		setLocation(280, 150);
		setSize(1000,500);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		JPanel panelFrame=new JPanel();
		panelFrame.setLayout(new BorderLayout(10,10));
		panelFrame.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		setContentPane(panelFrame);
		
		initComponent();
		displayBillList(list);
	}
	
	public void initComponent() {
		panelNorth=new JPanel();
		panelNorth.setLayout(new BorderLayout());
		
		titleLabel=new JLabel("KHÁCH HÀNG ĐÃ MUA", SwingConstants.CENTER);
		titleLabel.setFont(new Font("Segoe UI",1,20));
		panelNorth.add(titleLabel, BorderLayout.NORTH);
		
		JPanel panelNorthC=new JPanel(new FlowLayout(FlowLayout.RIGHT,200,30));
		btnPrintCount=new JButton("In Thống Kê");
		btnPrintCount.setFont(new Font("Segoe UI",1,14));
		panelNorthC.add(btnPrintCount);
		panelNorth.add(panelNorthC, BorderLayout.CENTER);
		
		String[] colNames= {"Mã hóa đơn","Tên khách hàng", "Ngày lập hóa đơn","Tổng tiền"};
		modelTable=new DefaultTableModel(colNames,0);
		table=new JTable(modelTable);
		table.getTableHeader().setFont(new Font("Segoe UI",1,14));
		table.setFont(new Font("Segoe UI",1,14));
		scroll=new JScrollPane(table);
		scroll.setBorder(BorderFactory.createLineBorder(new Color(0,191,165)));
		
		add(panelNorth, BorderLayout.NORTH);
		add(scroll, BorderLayout.CENTER);
		btnPrintCount.addActionListener(this);
	}
	
//	Hiển thị danh sách hóa đơn lên bảng
	private void displayBillList(ArrayList<Bill> listBill) {
		DecimalFormat formatter = new DecimalFormat("#,##0 VNĐ");
		DateTimeFormatter format=DateTimeFormatter.ofPattern("dd-MM-yyyy");
        for (Bill i : listBill) {
        	modelTable.addRow(new Object[]{i.getIdBill(), i.getCustomer().getName(), format.format(i.getDateBill()), formatter.format(i.getTotal())});
        }
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		var obj=e.getSource();
		if(obj.equals(btnPrintCount)) {
			ExcelHelper export=new ExcelHelper();
			export.exportDataDialog(this, table);
		}
	}
	
	
}
