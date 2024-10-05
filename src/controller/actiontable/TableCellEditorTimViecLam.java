package controller.actiontable;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;

public class TableCellEditorTimViecLam extends DefaultCellEditor{
	
	private TableActionEvent event;
	
	public TableCellEditorTimViecLam(TableActionEvent event) {
		super(new JCheckBox());
		this.event=event;
	}
	
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		// TODO Auto-generated method stub
		PanelActionTimViecLam action = new PanelActionTimViecLam();
        action.initEvent(event, row);
        
        return action;
	}
	

}
