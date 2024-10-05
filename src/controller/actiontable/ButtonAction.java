package controller.actiontable;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

public class ButtonAction extends JButton{
	
	private boolean mousePress;

    public ButtonAction() {
        setContentAreaFilled(false);
        setBorder(new EmptyBorder(3, 3, 3, 3));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                mousePress = true;
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                mousePress = false;
            }
        });
    }

}
