package swing.progress;

import javax.swing.JProgressBar;

public class ProgressBar extends JProgressBar {

    private final ProgressCircleUI ui;

    public ProgressBar() {
        setOpaque(false);
        setStringPainted(true);
        ui = new ProgressCircleUI();
        setUI(ui);
    }

    @Override
    public String getString() {
        return ((int) (getValue() * ui.getAnimate())) + "%";
    }

    public void start() {
        ui.start();
    }
}
