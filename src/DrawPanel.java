import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DrawPanel extends JPanel {
	
	private int numNodes = 6;
	private ArrayList<Point> nodes;
	private boolean isArrow = false;
	private ArrayList<ArrayList<Point>> adjList; 
	
	public DrawPanel() {
		
		
		setBackground(Color.GRAY);
		
        nodes = new ArrayList<Point>();
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
            	
                if (nodes.size() > numNodes) {
                	isArrow = true;
                	repaint();
                }
            	else {
            	    nodes.add(new Point(e.getX(), e.getY()));
            		repaint();
            		if(nodes.size() > numNodes) MainFrame.callbackMouseListener();
     	
            	}
            	
                
            }
        });
    }
	

    @Override
	public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    
	    Graphics2D g2 = (Graphics2D) g;
	    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    
	    for (int i = 0; i<nodes.size(); i++) {
	    	g2.setColor(Color.RED);
	    	g2.fillOval(nodes.get(i).x, nodes.get(i).y, 30, 30);
	    	g2.setColor(Color.GREEN);
	    	g2.drawString(i + "", nodes.get(i).x + 10, nodes.get(i).y + 15);
	    } 
	    
	    if (isArrow) {
	    	this.setArrowDirections();
	    	g2.setColor(Color.BLACK);
	    	
	    	for (int node = 0; node < adjList.size(); node++) { 
	    		
	    		Point from = nodes.get(node);
	            
	    		for (int neighbor = 0; neighbor < adjList.get(node).size(); neighbor++) { 
	      	    	Point to = adjList.get(node).get(neighbor);
	      	    	
	      	    	g2.drawLine(from.x + 10, from.y + 15, to.x + 10, to.y + 15);//linha
	      	    	g2.fillOval(to.x , to.y + 5 , 10, 10);//ponta da linha
	            } 
	           
	        } 
	    	
	    } 
        
    }
    
	
	private void setArrowDirections() {
		
		adjList = new ArrayList<ArrayList<Point>>();
		ArrayList<Point> nodeNeighbors;
		Random rand = new Random();
		int nodesTo; 
		int to;
		int j;
		
		
		for (int from = 0; from<nodes.size(); from++) {

			nodesTo = rand.nextInt(numNodes/3) + 1; //para quantos nós nodes[i] aponta	
			nodeNeighbors = new ArrayList<>();
			
			j = 0;
			while(j < nodesTo) { //definir quais nós nodes[i] aponta
				
				to = rand.nextInt(numNodes);
				System.out.println("De" + from + "para" + to);
				
				if(nodeNeighbors.contains(nodes.get(to))) j--;
				else if (from == to) j--;
				else nodeNeighbors.add(nodes.get(to));
				
				j++;
			}
			
			adjList.add(nodeNeighbors);
			
		} 
		
				
	}
	
	
    
}
