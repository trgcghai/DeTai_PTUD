package component;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class ComboBoxRenderer extends JLabel implements ListCellRenderer {
    private String title;

    public ComboBoxRenderer(String title){
        this.title = title;
    }
    
    @Override
    public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean hasFocus){
        if (index == -1 && value == null) {
        	setText (title);
        }
        else {
        	setText(value.toString());
        }
        return this;
    }
}
