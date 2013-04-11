package SnakeGameAI;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

//comparable so that we may use a priority queue and compare the nodes weights for A*.
//which in this case weights would be the estimated costs
public class Node implements Comparable{

	List<Node> neighbours;
	Node parent;
	int id;
	int x;
	int y;
	float costFromStart;
	float estimatedCostToGoal;
	
	
	public Node(int id, int x, int y){
		this.id = id;
		this.x = x;
		this.y = y;
		neighbours = new LinkedList<Node>();		
	}
	
	public int getNodeX(){
		return x;
	}
	
	public int getNodeY(){
		return y;
	}
	
	public String toString(){
		return "Node" + this.id;
	}
	
	
	public List<Node> constructPath(Node node){
		LinkedList<Node> path = new LinkedList<Node>();
		
		while(node.parent !=null){
			path.addFirst(node);
			node = node.parent;
		}
		
		System.out.print(" this is my path ");
		for(Node n: path){
			System.out.print("{ " + n + " }");
		}
			System.out.println("\nEating food in " + path.size() + " steps");
			
			return path;
	}
		
	public List<Node> getNeighbours(){

//	System.out.println("\nHere are my neighbours : ");
		Iterator<Node> it = this.neighbours.iterator();
		
		if(this.neighbours.isEmpty()){
			System.out.println("sorry I don't seem to have any neighbours :(");
		}
		
		return neighbours;
	}
	
	public float getCost(){
		return costFromStart + estimatedCostToGoal;
	}
	
	@Override
	public int compareTo(Object object) {
		float thisValue = this.getCost();
		float otherValue = ((Node) object).getCost();
		
		float value = thisValue - otherValue;
		
		return (value>0)?1:(value<0)? -1: 0;
	}
	
	public int compareTo(Node object) {
		float thisValue = this.getCost();
		float otherValue = object.getCost();
		
		float value = thisValue - otherValue;
		
		return (value>0)?1:(value<0)? -1: 0;
	}
	
	public float estimatedCostToGoal(Node node){
		
		return node.estimatedCostToGoal;

	}
	
	public float getEstimatedCost(Node node){
		
		while(node.parent !=null){
			node.estimatedCostToGoal++;
			node = node.parent;
		}
		
		return node.estimatedCostToGoal;
		
	}
	
	
	public static class PriorityList extends LinkedList{
		public void add(Comparable<Node> object){
			for(int i=0; i < size(); i++){
			if(object.compareTo((Node) get(i)) <=0)
			{
				add(i, object);
				return;
			}
			}
			addLast(object);
		}
	}


	
	
	


}

