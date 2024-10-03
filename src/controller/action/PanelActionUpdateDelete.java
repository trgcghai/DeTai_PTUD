package controller.action;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PanelActionUpdateDelete extends JPanel {
	
	private ButtonAction update;
	private ButtonAction delete;
	
	public PanelActionUpdateDelete() {
        initComponent();
    }
	
	public void initComponent() {
		update=new ButtonAction();
		update.setIcon(new ImageIcon(getClass().getResource("/image/update.png")));
		delete=new ButtonAction();
		delete.setIcon(new ImageIcon(getClass().getResource("/image/delete.png")));
		
		this.setLayout(new FlowLayout(FlowLayout.CENTER,15,0));
		add(update); add(delete);
	}

	public void initEvent(TableActionEvent event, int row) {
		update.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				event.onUpdate(row);
			}
		});
		
		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				event.onDelete(row);
			}
		});
	}
}
