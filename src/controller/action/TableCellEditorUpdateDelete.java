package controller.action;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;

public class TableCellEditorUpdateDelete extends DefaultCellEditor{
	
	private TableActionEvent event;
	
	public TableCellEditorUpdateDelete(TableActionEvent event) {
		super(new JCheckBox());
		this.event=event;
	}
	
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		// TODO Auto-generated method stub
		PanelActionUpdateDelete action= new PanelActionUpdateDelete();
        action.initEvent(event, row);
        
        return action;
	}
	

}
