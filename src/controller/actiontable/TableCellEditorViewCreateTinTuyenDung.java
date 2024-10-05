package controller.actiontable;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;

public class TableCellEditorViewCreateTinTuyenDung extends DefaultCellEditor{
	
	private TableActionEvent event;
	
	public TableCellEditorViewCreateTinTuyenDung(TableActionEvent event) {
		super(new JCheckBox());
		this.event=event;
	}
	
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		// TODO Auto-generated method stub
		PanelActionViewCreateTinTuyenDung action = new PanelActionViewCreateTinTuyenDung();
        action.initEvent(event, row);
        
        return action;
	}
	

}