import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.Stack;

import javax.swing.JLabel;
import javax.swing.JTable;

public class Controller {
	
	private MainFrame mainFrame;
	private int numNodes = 6;
	private ArrayList<Point> nodes;
	private ArrayList<ArrayList<Point>> adjList; 
	private ArrayList<ArrayList<Integer>> adjListInt; 
	private ArrayList<ArrayList<Integer>> adjListReverse;
	
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
			nodesTo = rand.nextInt(numNodes-2); //para quantos n�s nodes[i] aponta	
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
		
	
	  // A recursive function used by topologicalSort 
    public void topologicalSortUtil(int node, boolean visited[], 
                             Stack<Integer> stack) 
    { 
        // Mark the current node as visited. 
        visited[node] = true; 
        Integer i; 
  
        // Recur for all the vertices adjacent to this 
        // vertex 
        
    		           
		for (int neighbor = 0; neighbor < adjListInt.get(node).size(); neighbor++) { 
			i = adjListInt.get(node).get(neighbor);
			System.out.println("De" + node + "Para" + neighbor);
			
			if (!visited[i]) 
                topologicalSortUtil(i, visited, stack); 
        }	          
  
        
  
        // Push current vertex to stack which stores result 
        stack.push(new Integer(node)); 
    } 
  
    // The function to do Topological Sort. It uses 
    // recursive topologicalSortUtil() 
    public void topologicalSort() {
    	//Referencia https://www.geeksforgeeks.org/java-program-for-topological-sorting/
        Stack<Integer> stack = new Stack<Integer>(); 
  
        // Mark all the vertices as not visited 
        boolean visited[] = new boolean[numNodes + 1]; 
        for (int i = 0; i <= numNodes; i++) 
            visited[i] = false; 
  
        // Call the recursive helper function to store 
        // Topological Sort starting from all vertices 
        // one by one 
        for (int i = 0; i <= numNodes; i++) 
            if (visited[i] == false) 
                topologicalSortUtil(i, visited, stack); 
        
        System.out.println("\nOrdem");
        // Print contents of stack 
        while (stack.empty()==false) 
            System.out.print(stack.pop() + " "); 
    } 
	
	
	
	public boolean dfs(ArrayList<ArrayList<Integer>> adjList, Integer node, LinkedList<Integer> visited, LinkedList<Integer> queue){
		//Refer�ncia:
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

	public boolean isAcyclic(ArrayList<ArrayList<Integer>> adjList) {
		//Refer�ncia:
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




}
