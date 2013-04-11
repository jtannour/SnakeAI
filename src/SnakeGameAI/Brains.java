package SnakeGameAI;


import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


/**
 * @author Joyce
 *The brain class contains the snake's "brain". This is how it will maneuver through the board and figure out
 *the best path to take to get food.
 *
 *Snake can use either BFS or DFS or A* search.
 *
 *A* uses two different heuristics Pythagorean Theorem and Euclidean distance.
 *It also uses third heuristic that takes the average of the two.
 */
public class Brains {

	int searchType;
	int aStarType;
	long start;
	long end;

	GameBoard board;
	
	public Brains(int type, int aStarType, GameBoard board){
		
		searchType = type;
		this.aStarType = aStarType;
		this.board = board;
	}
	
	public List<Node> startSearch(Node startNode, Node goal){
		
		start = System.currentTimeMillis();
		
		switch(searchType){
		case(1):
			System.out.print("Using BFS");
			return BFS(startNode, goal);
		case(2):
			System.out.print("Using DFS");
			return DFS(startNode, goal);
		case(3):
			System.out.print("Using A*");
			return aStarSearch(aStarType, startNode, goal);
		default:
			return null;
		}
		
		
	}
	
	public int heuristicMethods(int hType, Node start, Node goal){
		switch(hType){
		case(1):
			return h1Search(start, goal);
		case(2):
			return h2Search(start, goal);
		case(3):
			return (h1Search(start,goal) + h2Search(start, goal))/2;
		default:
			return -1;
		
		}
		
	}

	public List<Node> BFS(Node startNode, Node goal){
		
		//list of non-visited nodes
		Queue<Node> openNodes = new LinkedList<Node>();
       
		//list of visited nodes
		Queue<Node> closedNodes = new LinkedList<Node>();
	    
		
		openNodes.add(startNode);
		startNode.parent = null;
		
		while(!openNodes.isEmpty()){
			Node node = openNodes.poll();
			
			if(node == goal){
				//we did it!
				end = System.currentTimeMillis();
				return node.constructPath(goal);
			}
			else{
				closedNodes.add(node);
				
				//add neighbors to the open list
				Iterator<Node> i = node.neighbours.iterator();
				
				while(i.hasNext()){
					Node neighborNode = i.next();
					if(!closedNodes.contains(neighborNode) && !openNodes.contains(neighborNode) && isProper(neighborNode)){
						neighborNode.parent = node;
						openNodes.add(neighborNode);
					}
				}
			}
		
		}
		
		//couldn't find anything
		System.out.println("Could not find path");
		return null;
        
	}
	
	public List<Node> DFS(Node startNode, Node goal){
		
		LinkedList<Node> openNodes = new LinkedList<Node>();
		LinkedList<Node> closedNodes = new LinkedList<Node>();
		
		List<Node> potentialValidList;
		
		openNodes.push(startNode);
		startNode.parent = null;
		
		while(!openNodes.isEmpty()){
			Node node = openNodes.pop();
			
			if(node == goal){
				//we did it?
				potentialValidList = node.constructPath(goal);
				end = System.currentTimeMillis();
				return potentialValidList;

			}
			else{
				closedNodes.add(node);
				
				//add neighbors to the open list
				Iterator<Node> i = node.neighbours.iterator();
				
				while(i.hasNext()){
					Node neighborNode = i.next();
					if(!closedNodes.contains(neighborNode) && !openNodes.contains(neighborNode) && isProper(neighborNode)){
						neighborNode.parent = node;
						openNodes.push(neighborNode);
					}
				}
			}
		
		}


		//no solution found
		System.out.println("No solution found");
		return null;
	}
	
	public List<Node> aStarSearch(int type, Node startNode, Node goal){
		Node.PriorityList openList = new Node.PriorityList();
		LinkedList<Node> closedList = new LinkedList<Node>();


		startNode.costFromStart = 0;
		startNode.estimatedCostToGoal = heuristicMethods(type, startNode, goal);

		startNode.parent = null;
		openList.add(startNode);
		
		
		
		while(!openList.isEmpty()){
			
			//sort the openList by order of weights			
			Node aNode = (Node)openList.removeFirst();

			
			if(aNode == goal){
				end = System.currentTimeMillis();
				return aNode.constructPath(goal);
			}
			
			List<Node> neighboursList = aNode.getNeighbours();

			for(int i=0; i < neighboursList.size(); i++){
				Node neighborNode = neighboursList.get(i);
				
				boolean isOpen = openList.contains(neighborNode);
				boolean isClosed = closedList.contains(neighborNode);
				
				float costFromStart = aNode.costFromStart + heuristicMethods(type, aNode, neighborNode);
			
				//have you been visited? or is there a shorter path?				
				if((!isOpen && !isClosed) || costFromStart < neighborNode.costFromStart){

					neighborNode.parent = aNode;
					neighborNode.costFromStart = costFromStart;
					neighborNode.estimatedCostToGoal = heuristicMethods(type, neighborNode, goal);
					
					
					if(isClosed){
						closedList.remove(neighborNode);
					}
					if(!isOpen && isProper(neighborNode)){
						openList.add(neighborNode);
					}
				}

			}
			closedList.add(aNode);
		}
		
		//no path found
		System.out.println("No solution found");
		return null;
	}
	
	
	//Getting the distance with the Pythagorean Theorem
	public int h1Search(Node start, Node goal){
		double ePath = Math.sqrt(Math.pow(start.getNodeX()-goal.getNodeX(),2)  + Math.pow(start.getNodeY()-goal.getNodeY(),2));
		return (int) ePath;

	}
	
	//and the Euclidean distance
	public int h2Search(Node start, Node goal){
		return Math.abs(start.getNodeX()-goal.getNodeX()) + Math.abs(start.getNodeY()-goal.getNodeY());
	}
	
	public boolean isProper(Node n){

		if(board.getBoardStatus(n.getNodeX(), n.getNodeY()) == GameBoard.Grid.EMPTY  
			|| board.getBoardStatus(n.getNodeX(), n.getNodeY()) == GameBoard.Grid.FOOD)
		{
			
			return true;
		}
		else
		{
			return false;
		}
	}
	
	
	public long getTime(){
		return end - start;
	}

}
