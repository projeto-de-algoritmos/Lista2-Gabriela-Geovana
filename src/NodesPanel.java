import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
	    	g2.fillOval(control.getNodes().get(i).x, control.getNodes().get(i).y, 30, 30);
	    	g2.setColor(Color.GREEN);
	    	g2.drawString(i + "", control.getNodes().get(i).x + 10, control.getNodes().get(i).y + 15);
	    } 
	    
	    //Paint arrows
	    if (isArrow) {
	    	
	    	control.setArrowDirections();
	    	ArrayList<ArrayList<Point>> adjList = control.getAdjList();
	    	mainFrame.setAdjReady();
	    	mainFrame.updateNodeOrderPanel(control.getAdjListInt());
	    	control.topologicalSort();
	    	
	    	g2.setColor(Color.BLACK);
	    	
	    	for (int node = 0; node < adjList.size(); node++) { 
	    		
	    		Point from = control.getNodes().get(node);
	            
	    		for (int neighbor = 0; neighbor < adjList.get(node).size(); neighbor++) { 
	      	    	Point to = adjList.get(node).get(neighbor);
	      	    	
	      	    	g2.drawLine(from.x + 10, from.y + 15, to.x + 10, to.y + 15);//linha
	      	    	g2.fillOval(to.x , to.y + 5 , 10, 10);//ponta da linha
	            }	          
	        } 
	    	
	    }
    }
    
	
	
	
	

}
