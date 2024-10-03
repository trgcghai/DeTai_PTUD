package controller.action;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;

public class TableCellEditorViewCreateHoSo extends DefaultCellEditor{
	
	private TableActionEvent event;
	
	public TableCellEditorViewCreateHoSo(TableActionEvent event) {
		super(new JCheckBox());
		this.event=event;
	}
	
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		// TODO Auto-generated method stub
		PanelActionViewCreateHoSo action = new PanelActionViewCreateHoSo();
        action.initEvent(event, row);
        
        return action;
	}
	

}
