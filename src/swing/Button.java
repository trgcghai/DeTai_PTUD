package swing;

import javax.swing.JButton;

public class Button extends JButton{

	public Button(String text) {
		setText(text);
		setBorderPainted(false);
		setFocusPainted(false);
	}
}
