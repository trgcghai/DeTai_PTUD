package swing;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JButton;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

public class ButtonMenu extends JButton {
	
	private Animator animator;
    private int targetSize;
    private float animatSize;
    private Point pressedPoint;
    private float alpha;
    private Color effectColor = new Color(173, 173, 173);

    public Color getEffectColor() {
        return effectColor;
    }

    public void setEffectColor(Color effectColor) {
        this.effectColor = effectColor;
    }

    public ButtonMenu(String title) {
    	setText(title);
    	setFocusPainted(false);
        setContentAreaFilled(false);
        setBorder(new EmptyBorder(8, 10, 8, 10));
        setHorizontalAlignment(JButton.LEFT);
        setForeground(new Color(250, 250, 250));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
            	new SwingWorker<Void, Void>() {

					@Override
					protected Void doInBackground() throws Exception {
						// TODO Auto-generated method stub
						targetSize = Math.max(getWidth(), getHeight()) * 2;
		                animatSize = 0;
		                pressedPoint = me.getPoint();
		                alpha = 0.5f;
		                if (animator.isRunning()) {
		                    animator.stop();
		                }
						return null;
					}
					
					@Override
					protected void done() {
						// TODO Auto-generated method stub
						 animator.start();
					}
				}.execute();
            }
        });
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                if (fraction > 0.5f) {
                    alpha = 1 - fraction;
                }
                animatSize = fraction * targetSize;
                revalidate();
                repaint();
            }
        };
        animator = new Animator(700, target);
        animator.setResolution(10);
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        int width = getWidth();
        int height = getHeight();
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        if (pressedPoint != null) {
            Area area = new Area(new RoundRectangle2D.Double(0, 0, width, height, 10, 10));
            g2.setColor(effectColor);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            area.intersect(new Area(new Ellipse2D.Double((pressedPoint.x - animatSize / 2), (pressedPoint.y - animatSize / 2), animatSize, animatSize)));
            g2.fill(area);
        }
        g2.setComposite(AlphaComposite.SrcOver);
        super.paintComponent(grphcs);
    }

    @Override
    public void paint(Graphics grphcs) {
        if (isSelected()) {
            int width = getWidth();
            int height = getHeight();
            Graphics2D g2 = (Graphics2D) grphcs.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(0, 102, 102));
            g2.fillRoundRect(0, 0, width - 1, height - 1, 10, 10);
        }
        super.paint(grphcs);
    }

}