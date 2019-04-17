import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.QuadCurve2D;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;
import java.awt.Point;
import java.awt.Polygon;

@SuppressWarnings("serial")
public class OrderPanel extends JPanel {
	
	private Controller control;
	private ArrayList<Integer> topologicalOrdering;
	private boolean isValid;

	
	public OrderPanel(Controller control) {	
		
		this.control = control;
				
    }
	

    @Override
	public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    
	    if(isValid) {
		    Graphics2D g2 = (Graphics2D) g;
		    QuadCurve2D quadCurve = new QuadCurve2D.Float();
		    HashMap<Integer, Integer> xPosition = new HashMap<Integer, Integer>();
		    HashMap<Integer, Integer> yPosition = new HashMap<Integer, Integer>();
		    ArrayList<ArrayList<Integer>> list = control.getAdjListInt();
		    AffineTransform tx1;
		    
		    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		    
		    topologicalOrdering = control.getTopolpgicalOrdering();
		    
		    int x , y = 100;
		    
		    // Paint nodes in order
		    for (int i = 1; i <= topologicalOrdering.size(); i++) {
		    	x = i*80;
		    	
		    	g2.setColor(Color.RED);
		    	g2.fillOval(x - 15, y - 15, 30, 30);
		    	g2.setColor(Color.GREEN);
		    	g2.drawString(topologicalOrdering.get(i-1) + "", x , y );
		    	
		    	xPosition.put(topologicalOrdering.get(i-1), x);
		    	yPosition.put(topologicalOrdering.get(i-1), y);
		    }
		    
		    
		    tx1 = g2.getTransform();
		    g2.setColor(Color.BLUE);
		    boolean top = true;			
		    for (int i = 0; i < topologicalOrdering.size(); i++) {
		    	ArrayList<Integer> neighbors = list.get(topologicalOrdering.get(i));
		    	for(int j = 0; j < neighbors.size(); j++) {
		    		Integer x1, y1, xControl, yControl, x2, y2;
		    		Polygon arrowHead = new Polygon();
			        AffineTransform tx = new AffineTransform();
		    		double angle;
		    		
		    		x1 = xPosition.get(topologicalOrdering.get(i));	    		
		    		x2 = xPosition.get(neighbors.get(j));
		    		xControl = (x1 + x2)/2;
		    		g2.setTransform(tx1);
		    		
		    		if(top) {		    			
		    			y1 = yPosition.get(topologicalOrdering.get(i)) - 15;
		    			y2 = yPosition.get(neighbors.get(j)) - 15;
		    			yControl = y1 - 50;
		    			angle = Math.atan2(30, 60);
		    		} else {
		    			y1 = yPosition.get(topologicalOrdering.get(i)) + 15;
		    			y2 = yPosition.get(neighbors.get(j)) + 15;
		    			yControl = y1 + 45;
		    			angle = Math.atan2(-70, 60);
		    		}
		    		
		    		quadCurve.setCurve(x1, y1, xControl, yControl, x2, y2);
		    		g2.draw(quadCurve);
		    		top = !top;
		    		
		    		//Paint arrows
		    		
				    
			        arrowHead.addPoint(0, 5);
			        arrowHead.addPoint(-5, -5);
			        arrowHead.addPoint(5, -5);

			        tx.translate(x2, y2);
			        tx.rotate(angle - Math.PI / 2d);
			        
			        g2.setTransform(tx);
			        g2.fill(arrowHead);      
		    	}
		    }		    
	    }
    }


	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
    
	
	
	
	

}