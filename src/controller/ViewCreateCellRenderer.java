package controller;

import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class ViewCreateCellRenderer extends JPanel implements TableCellRenderer{
	
    public ViewCreateCellRenderer() {
    	setLayout(new FlowLayout(FlowLayout.CENTER,15,5));
    }

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		// TODO Auto-generated method stub
		removeAll();
		Icon viewIcon=new ImageIcon(getClass().getResource("/image/view.png"));
		Icon hosoIcon=new ImageIcon(getClass().getResource("/image/hoso24.png"));
		JLabel updateLabel=new JLabel(viewIcon); 
		JLabel deleteLabel=new JLabel(hosoIcon); 
		
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