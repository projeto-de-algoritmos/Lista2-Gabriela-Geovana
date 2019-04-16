import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import javax.swing.JPanel;

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
		    } 
	    } 
 
	    
    }


	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
    
	
	
	
	

}