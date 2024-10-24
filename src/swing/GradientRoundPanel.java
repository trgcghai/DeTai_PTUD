package swing;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class GradientRoundPanel extends JPanel{
	
	 public GradientRoundPanel() {
	        setOpaque(false);
	    }
    
    @Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        int w = getWidth(), h = getHeight();
        Color color1 = Color.decode("#ABC8CB");
        Color color2 = Color.decode("#259195");
//	    Color color2 = Color.decode("#D2E0E1");
        
        GradientPaint gp1 = new GradientPaint(0, 0, color1, w/2, 0, color2);
        g2d.setPaint(gp1);
        g2d.fillRoundRect(0, 0, w, h, 15, 15);
        
        GradientPaint gp2 = new GradientPaint(w/2, 0, color2, w, 0, color1);
        g2d.setPaint(gp2);
        g2d.fillRoundRect(w/2, 0, w/2, h, 15, 15);
	}
}
