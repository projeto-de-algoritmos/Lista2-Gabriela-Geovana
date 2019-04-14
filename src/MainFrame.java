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

@SuppressWarnings("serial")
public class MainFrame extends JFrame {
	
	private Controller control = new Controller(this);
	private JPanel contentPane;
	private JLabel lblNode;
	private NodesPanel nodesPanel;
	private JPanel topOrdPanel;
	private JPanel adjListPanel;
	private JPanel nodeOrdPanel;
	private JTable tableItem;
	private JLabel isCycle;

	public MainFrame() {

		setTitle("Graphs");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(false);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

		// Instruction section
		lblNode = new JLabel("Clique para criar um n� (de 0 at� 6)");
		lblNode.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(lblNode);

		// Separating label from section
		contentPane.add(Box.createRigidArea(new Dimension(10, 10)));

		// Section to draw points
		nodesPanel = new NodesPanel(this, control);
		contentPane.add(nodesPanel);

		// Panel of adjacency list + topological order
		topOrdPanel = new JPanel();
		topOrdPanel.setLayout(new GridLayout(1, 2));
		topOrdPanel.setMaximumSize(new Dimension(2000, 10000));
		contentPane.add(topOrdPanel);

		// Panel of adjacency list
		adjListPanel = new JPanel();
		adjListPanel.setMaximumSize(new Dimension(2000, 10000));
		isCycle = new JLabel("");
		adjListPanel.add(isCycle);
		topOrdPanel.add(adjListPanel);


		// Topological order
		nodeOrdPanel = new JPanel();
		nodeOrdPanel.setLayout(new BoxLayout(nodeOrdPanel, BoxLayout.Y_AXIS));
		topOrdPanel.add(nodeOrdPanel);
	}

	public void setTextArrows() {
		lblNode.setText("Clique novamente para gerar setas aleat�rias");
	}
	
	public void setTextCycle() {
		isCycle.setText("Tem ciclo: ");
	}
	
	public void setAdjReady(){
		adjListPanel.removeAll();
		isCycle.setText("");
		tableItem = new JTable(control.getAdjListInt().size(), 4);
		tableItem.setBackground(Color.MAGENTA);
		tableItem.setSize(new Dimension(10, 4));
		for (int node = 0; node < control.getAdjListInt().size(); node++) {
			tableItem.setValueAt(node + " ->", node, 0);
			for (int neighbor = 0; neighbor < control.getAdjListInt().get(node).size(); neighbor++) {
				tableItem.setValueAt(control.getAdjListInt().get(node).get(neighbor), node, neighbor + 1);
			}
		}
		adjListPanel.add(tableItem);
		adjListPanel.revalidate();
		adjListPanel.repaint();
	}
	
	public void updateNodeOrderPanel(ArrayList<ArrayList<Integer>> adjList) {
		nodeOrdPanel.removeAll();
		JLabel ciclicGraphLbl;

		if (!control.isAcyclic(adjList)) {
			ciclicGraphLbl = new JLabel("N�o � poss�vel fazer a ordena��o, pois o grafo resultante � c�clico");
		} else {
			ciclicGraphLbl = new JLabel("� poss�vel ordenar");
		}

		nodeOrdPanel.add(ciclicGraphLbl);
		nodeOrdPanel.revalidate();
		nodeOrdPanel.repaint();
	}
	


}
