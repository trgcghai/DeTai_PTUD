package controller.actiontable;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PanelActionUpdateDelete extends JPanel {
	
	private ButtonAction update;
	private ButtonAction delete;
	private int row;
	
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
		this.row=row;
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
