import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class Controller {
	
	private MainFrame mainFrame;
	private int numNodes = 6;
	private ArrayList<Point> nodes;
	private ArrayList<ArrayList<Point>> adjList; 
	private ArrayList<ArrayList<Integer>> adjListInt; 
	
	public Controller(MainFrame mainFrame) {
		nodes = new ArrayList<Point>();
		this.mainFrame = mainFrame;
	}
	
	public ArrayList<Point> getNodes() {
		return this.nodes;
	}
	
	public int getNumNodes() {
		return this.numNodes;
	}
	
	public void addNode(Point node) {
		nodes.add(node);
	}
	
	public ArrayList<ArrayList<Point>> getAdjList(){
		return adjList;
	}
	
	public ArrayList<ArrayList<Integer>> getAdjListInt(){
		return adjListInt;
	}
	
	public void setArrowDirections() {		
		adjList = new ArrayList<ArrayList<Point>>();
		adjListInt = new ArrayList<ArrayList<Integer>>();
		ArrayList<Point> nodeNeighbors;
		ArrayList<Integer> nodeNeighborsInt;
		Random rand = new Random();
		int nodesTo; 
		int to;
		int j;		
		
		for (int from = 0; from<nodes.size(); from++) {
			nodesTo = rand.nextInt(numNodes/3); //para quantos n�s nodes[i] aponta	
			nodeNeighbors = new ArrayList<>();
			nodeNeighborsInt = new ArrayList<Integer>();
			
			j = 0;
			while(j < nodesTo) { //definir quais n�s nodes[i] aponta				
				to = rand.nextInt(numNodes);
				
				if(!nodeNeighbors.contains(nodes.get(to)) && from != to) {
					nodeNeighbors.add(nodes.get(to));
					nodeNeighborsInt.add(to);
					System.out.println("De" + from + "Para" + to);
					j++;
				}
			}
			adjList.add(nodeNeighbors);
			adjListInt.add(nodeNeighborsInt);
		}	
		System.out.println("\n");
		
	}
		 
    // The function to do Topological Sort. It uses 
    // recursive topologicalSortUtil() 
    public void topologicalSort() {
    	int[] incomingEdgeCount = new int[adjListInt.size()];
		for(int v = 0; v<adjListInt.size(); v++){
			for(int w : adjListInt.get(v)){
				incomingEdgeCount[w]++;
			}
		}
		
		LinkedList<Integer> nodesWithNoIncomingEdges = new LinkedList<Integer>();
		for(int v = 0; v<incomingEdgeCount.length; v++){
			if(incomingEdgeCount[v]==0){
				nodesWithNoIncomingEdges.add(v);
			}
		}
		
		if(nodesWithNoIncomingEdges.isEmpty()){		//Not a DAG, terminate early
			System.out.println("Failed to find node with no incoming edges. \nMust contain a cycle and therefore no Topological Ordering exists.");
			return;
		}
		
		
		ArrayList<Integer> topolpgicalOrdering = new ArrayList<Integer>();
		while(!nodesWithNoIncomingEdges.isEmpty()){
			int v = nodesWithNoIncomingEdges.remove();
			topolpgicalOrdering.add(v);
			for(int w : adjListInt.get(v)){
				incomingEdgeCount[w]--;
				if(incomingEdgeCount[w]==0){
					nodesWithNoIncomingEdges.add(w);
				}
			}			
		}
		
		if(topolpgicalOrdering.size() == adjListInt.size()){
			System.out.println("Topological Ordering: "+topolpgicalOrdering);
		}else{
			System.out.println("Failed to reach all nodes. Must contain a cycle and therefore no Topological Ordering exists.");
		}
    } 
}
