package swing;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class GradientPanel extends JPanel{
	
	private Color color1, color2;
	public GradientPanel(Color color1, Color color2) {
		this.color1=color1;
		this.color2=color2;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        int w = getWidth(), h = getHeight();
//        Color color1 = Color.decode("#ABC8CB");
//        Color color2 = Color.decode("#259195");
//	    Color color2 = Color.decode("#D2E0E1");
        
        GradientPaint gp1 = new GradientPaint(0, 0, color1, w/2, 0, color2);
        g2d.setPaint(gp1);
        g2d.fillRect(0, 0, w/2, h);
        
        GradientPaint gp2 = new GradientPaint(w/2, 0, color2, w, 0, color1);
        g2d.setPaint(gp2);
        g2d.fillRect(w/2, 0, w/2, h);
	}
}
