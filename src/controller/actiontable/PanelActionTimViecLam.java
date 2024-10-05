package controller.actiontable;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PanelActionTimViecLam extends JPanel {
	
	private ButtonAction search;
	
	public PanelActionTimViecLam() {
        initComponent();
    }
	
	public void initComponent() {
		search=new ButtonAction();
		search.setIcon(new ImageIcon(getClass().getResource("/image/timviec.png")));
		
		this.setLayout(new FlowLayout(FlowLayout.CENTER,15,0));
		add(search);
	}

	public void initEvent(TableActionEvent event, int row) {
		search.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				event.onTimViecLam(row);;
			}
		});
	}
}
