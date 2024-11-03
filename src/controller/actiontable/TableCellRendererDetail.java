package controller.actiontable;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class TableCellRendererDetail extends DefaultTableCellRenderer{
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		// TODO Auto-generated method stub
		Component com = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        PanelActionDetail action = new PanelActionDetail();
        if (isSelected == false && row % 2 == 0) {
            action.setBackground(Color.decode("#5faeb1"));
        } 
        else if(isSelected){
        	action.setBackground(Color.decode("#33FF33"));
        }
        else {
        	action.setBackground(com.getBackground());
        }
        return action;
	}

}
