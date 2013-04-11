package SnakeGameAI;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Joyce
 *Our snake class moves around and tries to find food. 
 *
 *It keeps track of all the food that's on the board and searches for it based on the heuristic chosen
 */
public class Snake {

	int size; //size of snake including head and tail
	
	char snake[];
	int snakePosX[];
	int snakePosY[];
	int headposx;
	int headposy;
	int tailPosX;
	int tailPosY;
	
	
	int newX;
	int newY;
	
	Brains brain;
	GameBoard board;
	
	//snakes keeps track of the food he sees on the board
	ArrayList<Food> foodOnBoard = new ArrayList<Food>();
	List<Node> movePath;
	
	public Snake(int newSize, int posx, int posy, GameBoard board, int searchType, int heuristics){
		size = newSize;
		headposx = newX = posx ;
		headposy = newY = posy;
		snake = new char[newSize];
		snakePosX = new int[newSize];
		snakePosY = new int[newSize];
		this.board = board;
		createSnake();

		brain = new Brains(searchType, heuristics , board);
	}
	
	//initiate the snakes body
	public void createSnake(){
		
		snake[0] = '6';
		for(int i=1; i< size-1; i++){
			snake[i] = '8';
		}
		snake[size-1] = '7';
	}
	
	public int getSnakeLength(){return size;}
	
	public char getSnake(int i){return snake[i];}
	
	public void setSnakePosX(int i, int x){snakePosX[i] = x;}	
	public void setSnakePosY(int i, int y){snakePosY[i] = y;}
	
	public int getSnakeX(int i){return snakePosX[i];}
	public int getSnakeY(int i){return snakePosY[i];}
	
	public void setHeadX(int x){headposx = x;}	
	public int getHeadX(){return headposx;}
	
	public void setHeadY(int y){headposy = y;}
	public int getHeadY(){return headposy;}
	
	public void setTailX(int posx){tailPosX = posx;}
	
	public void setTailY(int posy){tailPosY = posy;}
	
	public int getTailX(){return tailPosX;}
	
	public int getTailY(){return tailPosY;}
	
	public int getNewX(){return  newX;}
	
	public int getNewY(){return newY;}
	
	//returns the coordinates of where to move snake
	public void move(){

		Node n = null;
		
		//move the snake along the path to the food
		while(!movePath.isEmpty()){
			n = movePath.remove(0);
					
			newX = n.getNodeX();
			newY =n.getNodeY();
			board.update(this, true);
			setHeadX(newX); 
			setHeadY(newY);
		}

							
	}
	
	//Update the snake that there's more food
	public void addFoodToEat(Food food){
		foodOnBoard.add(food);
	}
	

	//snake now searches the board for whatever food comes at him first
	public void searchForFood(Node list[][]){

		Food food =  null;
		
		Iterator<Food> iter = foodOnBoard.iterator();

		while(iter.hasNext()){
			food = iter.next();
			board.printObstacles();
			System.out.println("\n I was at position " + getHeadX() + " , " + getHeadY() + 
					" and trying to eat " + food.getFoodX() +" , " + food.getFoodY());
			
			
			movePath = brain.startSearch(list[getHeadX()][getHeadY()], list[food.getFoodX()][food.getFoodY()]);

			if(movePath != null){				 
				move();			
				System.out.println("\nTIME = " + brain.getTime() + " ms");
			}
			
		}
		
		foodOnBoard.clear();

	}


}
