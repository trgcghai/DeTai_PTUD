package controller.action;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PanelActionViewCreateHoSo extends JPanel {
	
	private ButtonAction view;
	private ButtonAction create;
	
	public PanelActionViewCreateHoSo() {
        initComponent();
    }
	
	public void initComponent() {
		view=new ButtonAction();
		view.setIcon(new ImageIcon(getClass().getResource("/image/view.png")));
		create=new ButtonAction();
		create.setIcon(new ImageIcon(getClass().getResource("/image/hoso24.png")));
		
		this.setLayout(new FlowLayout(FlowLayout.CENTER,15,0));
		add(view); add(create);
	}

	public void initEvent(TableActionEvent event, int row) {
		view.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				event.onViewHoSo(row);;
			}
		});
		
		create.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				event.onCreateHoSo(row);
			}
		});
	}
}