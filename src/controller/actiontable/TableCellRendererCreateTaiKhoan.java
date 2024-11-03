package controller.actiontable;

import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class TableCellRendererCreateTaiKhoan extends DefaultTableCellRenderer{
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		// TODO Auto-generated method stub
		Component com = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        PanelActionCreateTaiKhoan action = new PanelActionCreateTaiKhoan();
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
