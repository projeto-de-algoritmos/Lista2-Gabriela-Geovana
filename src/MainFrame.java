import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {
	
	private static JLabel lblNode;
	
	public MainFrame() {
			
		setTitle("Graphs");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setUndecorated(false);
		
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
			
		//Instruction section
		lblNode = new JLabel("Clique para criar um nó (de 0 até 6)");
		lblNode.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(lblNode);

		//Separating label from section
		contentPane.add(Box.createRigidArea(new Dimension(10,10)));
		
		// Section to draw points
		DrawPanel drawPanel = new DrawPanel();
		drawPanel.setBounds(10, 10, 45, 30);
		contentPane.add(drawPanel);
		
		JPanel topOrdPanel = new JPanel();
		topOrdPanel.setLayout(new GridLayout(1, 2));
		contentPane.add(topOrdPanel);
		
		JPanel adjListPanel = new JPanel();
		adjListPanel.setBackground(Color.MAGENTA);
		adjListPanel.setLayout(new GridLayout(8, 2));
		topOrdPanel.add(adjListPanel);
		
		adjListPanel.add(Box.createRigidArea(new Dimension(10,10)));
		JLabel lblTitleAdj = new JLabel("Lista de Adjacências");
		adjListPanel.add(lblTitleAdj);
		adjListPanel.add(Box.createRigidArea(new Dimension(10,10)));
		adjListPanel.add(Box.createRigidArea(new Dimension(10,10)));
		
		//TODO PEGAR LISTA DE ADJACENCIAS EM DRAWPANEL E MOSTRAR
		for (int node = 0; node < 7; node++) { 
			JLabel lblNode = new JLabel(node + " -> ");
			adjListPanel.add(lblNode);
            
    		for (int neighbor = 0; neighbor < 3; neighbor++) { 
      	    	JLabel lblNeighbor = new JLabel(neighbor + " ");
      	    	adjListPanel.add(lblNeighbor);
            } 
           
        } 
		
		//TODO FAZER A ORDENAÇÃO TOPOLÓGICA
		//TODO FAZER PANEL QUE MOSTRE OS NÓS EM ORDEM TOPOLÓGICA
		//TODO SE FOR UM CICLO MOSTRAR MENSAGEM DIZENDO QUE É UM CICLO
		JPanel nodeOrdPanel = new JPanel();
		nodeOrdPanel.setBackground(Color.BLUE);
		nodeOrdPanel.setLayout(new BoxLayout(nodeOrdPanel, BoxLayout.Y_AXIS));
		topOrdPanel.add(nodeOrdPanel);
		

		
	}
	
	public static void callbackMouseListener() {
		
		lblNode.setText("Clique novamente para gerar setas aleatórias");
				
	}
	


}

