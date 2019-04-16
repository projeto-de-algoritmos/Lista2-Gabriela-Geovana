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
			nodesTo = rand.nextInt(numNodes/3); //how many nodes one points to
			nodeNeighbors = new ArrayList<>();
			nodeNeighborsInt = new ArrayList<Integer>();
			
			j = 0;
			while(j < nodesTo) { //what nodes one points to			
				to = rand.nextInt(numNodes);
				
				if(!nodeNeighbors.contains(nodes.get(to)) && from != to) {
					// if edge does not exists...
					// and does not point to itself ...
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
		 
    public String topologicalSort() {
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
			return "Ordenação Topológica: "+ topolpgicalOrdering;
		}else{
			return "Falha ao fazer a ordenação topológica."
					+ " Deve existir um ciclo.";
		}
    } 
}
