import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.Box;
import javax.swing.BoxLayout;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.LinkedList;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	private static JLabel lblNode;
	private JPanel adjListPanel = new JPanel();
	private JPanel nodeOrdPanel;

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

		// Instruction section
		lblNode = new JLabel("Clique para criar um nó (de 0 até 6)");
		lblNode.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(lblNode);

		// Separating label from section
		contentPane.add(Box.createRigidArea(new Dimension(10, 10)));

		// Section to draw points
		DrawPanel drawPanel = new DrawPanel(this);
		drawPanel.setBounds(10, 10, 45, 30);
		contentPane.add(drawPanel);

		JPanel topOrdPanel = new JPanel();
		topOrdPanel.setLayout(new GridLayout(1, 2));
		contentPane.add(topOrdPanel);

		adjListPanel.setBackground(Color.MAGENTA);
		adjListPanel.setLayout(new GridLayout(1, 1));
		topOrdPanel.add(adjListPanel);

		adjListPanel.add(Box.createRigidArea(new Dimension(10, 10)));
		JLabel lblTitleAdj = new JLabel("Lista de Adjacências");
		adjListPanel.add(lblTitleAdj);
		adjListPanel.add(Box.createRigidArea(new Dimension(10, 10)));
		adjListPanel.add(Box.createRigidArea(new Dimension(10, 10)));

		// TODO FAZER A ORDENAÇÃO TOPOLÓGICA
		// TODO FAZER PANEL QUE MOSTRE OS NÓS EM ORDEM TOPOLÓGICA
		nodeOrdPanel = new JPanel();
		nodeOrdPanel.setBackground(Color.BLUE);
		nodeOrdPanel.setLayout(new BoxLayout(nodeOrdPanel, BoxLayout.Y_AXIS));
		topOrdPanel.add(nodeOrdPanel);
	}

	public static void callbackMouseListener() {
		lblNode.setText("Clique novamente para gerar setas aleatórias");
	}

	private boolean dfs(ArrayList<ArrayList<Integer>> adjList, Integer node, LinkedList<Integer> visited, LinkedList<Integer> queue){
		//Referência:
		//https://www.ime.usp.br/~pf/analise_de_algoritmos/aulas/cycles-and-dags.html
		visited.add(node);
		if(queue.contains(node)) {
			queue.remove(node);
		}		
		ArrayList<Integer> neighbors = adjList.get(node);
		while (!neighbors.isEmpty()) {
			Integer neighbor = neighbors.get(neighbors.size() - 1);
			neighbors.remove(neighbor);
			if (visited.contains(neighbor)) {
				return false;
			}
			else {
				boolean x = dfs(adjList, neighbor, visited, queue);
				if(!x) {
					return false;
				}				
			}
		}
		return true;
	}

	private boolean isAcyclic(ArrayList<ArrayList<Integer>> adjList) {
		//Referência:
		//https://www.ime.usp.br/~pf/analise_de_algoritmos/aulas/cycles-and-dags.html
		
		LinkedList<Integer> queue = new LinkedList<Integer>();
		
		for(int node = 0; node < adjList.size() -1; node++) {
			queue.add(node);
		}
		
		boolean x = dfs(adjList, 0, new LinkedList<Integer>(), queue);
		
		if(!x) {
			return false;
		}
		else if(x && queue.isEmpty()) {
			return true;
		}
		else {
			return dfs(adjList, queue.get(queue.size()-1), new LinkedList<Integer>(), queue);
		
		}
	}	 


	public void updateAdjList(ArrayList<ArrayList<Integer>> adjList) {
		adjListPanel.removeAll();

		JTable tableItem = new JTable(adjList.size(), 4);
		tableItem.setBackground(Color.MAGENTA);
		tableItem.setSize(new Dimension(10, 4));
		for (int node = 0; node < adjList.size(); node++) {
			tableItem.setValueAt(node, node, 0);
			for (int neighbor = 0; neighbor < adjList.get(node).size(); neighbor++) {
				tableItem.setValueAt("->  " + adjList.get(node).get(neighbor), node, neighbor + 1);
			}
		}
		adjListPanel.add(tableItem);
		adjListPanel.revalidate();
		adjListPanel.repaint();
	}

	public void updateNodeOrderPanel(ArrayList<ArrayList<Integer>> adjList) {
		nodeOrdPanel.removeAll();
		JLabel ciclicGraphLbl;

		if (!isAcyclic(adjList)) {
			ciclicGraphLbl = new JLabel("Não é possível fazer a ordenação, pois o grafo resultante é cíclico");
		} else {
			ciclicGraphLbl = new JLabel("É possível ordenar");
		}

		nodeOrdPanel.add(ciclicGraphLbl);
		nodeOrdPanel.revalidate();
		nodeOrdPanel.repaint();
	}

}
