package controller;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ComponentInputMap;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class MultiComponentCellRenderer extends JPanel implements TableCellRenderer{
	
    public MultiComponentCellRenderer() {
    	setLayout(new FlowLayout(FlowLayout.CENTER));
    }

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		// TODO Auto-generated method stub
		removeAll();
		Icon updateIcon=new ImageIcon(getClass().getResource("/image/update.png"));
		Icon deleteIcon=new ImageIcon(getClass().getResource("/image/delete.png"));
		JLabel updateLabel=new JLabel(updateIcon); 
		JLabel deleteLabel=new JLabel(deleteIcon); 
		
        add(updateLabel);
        add(deleteLabel);
        
        if (isSelected) {
            setBackground(table.getSelectionBackground());
        } else {
            setBackground(table.getBackground());
        }

        return this;
	}
}