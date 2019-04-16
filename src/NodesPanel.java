import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class NodesPanel extends JPanel {
	
	private Controller control;
	private MainFrame mainFrame;
	private boolean isArrow;
	
	public NodesPanel(MainFrame mainFrame, Controller control) {	
		
		setBackground(Color.GRAY);
		
		this.control = control;
		this.mainFrame = mainFrame;
		isArrow = false;
				
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
            	
                if (control.getNodes().size() > control.getNumNodes()) {
                	isArrow = true;
                	repaint();
                } else {
            	    control.addNode(new Point(e.getX(), e.getY()));
            		repaint();
            		if(control.getNodes().size() > control.getNumNodes()) 
            			mainFrame.setTextArrows();
            	}
            }
        });
    }
	

    @Override
	public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    
	    
	    Graphics2D g2 = (Graphics2D) g;
	    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    
	    // Paint nodes
	    for (int i = 0; i<control.getNodes().size(); i++) {
	    	g2.setColor(Color.RED);
	    	g2.fillOval(control.getNodes().get(i).x - 15, control.getNodes().get(i).y - 15, 30, 30);
	    	g2.setColor(Color.GREEN);
	    	g2.drawString(i + "", control.getNodes().get(i).x - 5 , control.getNodes().get(i).y );
	    } 
	    
	    
	    
	    //Paint arrows
	    if (isArrow) {
	    	
	    	control.setArrowDirections();
	    	ArrayList<ArrayList<Point>> adjList = control.getAdjList();
	    	mainFrame.setAdjReady();
	    	mainFrame.setTextCycle(control.topologicalSort());
	    	
	    	g2.setColor(Color.BLACK);
	    	
	    	for (int node = 0; node < adjList.size(); node++) { 
	    		
	    		Point from = control.getNodes().get(node);
	            
	    		for (int neighbor = 0; neighbor < adjList.get(node).size(); neighbor++) { 
	      	    	Point to = adjList.get(node).get(neighbor);
	      	    	
	      	    	//Line
	                g2.drawLine(from.x, from.y, to.x, to.y);
	                
	                //Arrow
	                int dx = to.x - from.x, dy = to.y - from.y;
	                double D = Math.sqrt(dx*dx + dy*dy);
	                double xm = D - 6, xn = xm, ym = 6, yn = -6, x;
	                double sin = dy / D, cos = dx / D;

	                x = xm*cos - ym*sin + from.x;
	                ym = xm*sin + ym*cos + from.y;
	                xm = x;

	                x = xn*cos - yn*sin + from.x;
	                yn = xn*sin + yn*cos + from.y;
	                xn = x;

	                int[] xpoints = {to.x, (int) xm, (int) xn};
	                int[] ypoints = {to.y, (int) ym, (int) yn};
	                
	                g.fillPolygon(xpoints, ypoints, 3);
	     
	            }	          
	        } 
	    	
	    }
    }
    
	
	
	
	

}
