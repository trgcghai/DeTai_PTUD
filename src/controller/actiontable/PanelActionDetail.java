package controller.actiontable;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PanelActionDetail extends JPanel {
	
	private ButtonAction view;
	
	public PanelActionDetail() {
        initComponent();
    }
	
	public void initComponent() {
		view=new ButtonAction();
		view.setIcon(new ImageIcon(getClass().getResource("/image/eye.png")));
		
		this.setLayout(new FlowLayout(FlowLayout.CENTER,15,0));
		add(view);
	}

	public void initEvent(TableActionEvent event, int row) {
		view.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				event.onViewDetail(row);
			}
		});
	}
}