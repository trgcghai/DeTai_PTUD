package controller.actiontable;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;

public class TableCellEditorCreateTaiKhoan extends DefaultCellEditor{
	
	private TableActionEvent event;
	
	public TableCellEditorCreateTaiKhoan(TableActionEvent event) {
		super(new JCheckBox());
		this.event=event;
	}
	
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		// TODO Auto-generated method stub
		PanelActionCreateTaiKhoan action= new PanelActionCreateTaiKhoan();
        action.initEvent(event, row);
        
        return action;
	}
	

}
