package SnakeGameAI;


/**
 * @author Joyce
 *The gameboard takes care of drawing and updating our wonderful text based interface
 */

public class GameBoard {



	int MaxGridX = 0;
	int MaxGridY = 0;
	
	char board[][];
	
	enum Grid {EMPTY, WALL, FOOD, OBSTACLE, SNAKE}
	
	boolean firstRender;

	//neighbours list
	Node list[][];
	
	
	public GameBoard(int maxX, int maxY){
		MaxGridX = maxX;
		MaxGridY = maxY;

		board = new char[MaxGridX][MaxGridY];
		list = new Node[MaxGridX][MaxGridY];

		firstRender = true;

		for(int i =0; i < MaxGridX; i++){
			for(int j=0; j < MaxGridY; j++ ){		
				
				board[i][j] = ' ';		
			}
		}
				
		//Initially place the snake in center of board
		createSearchSpace();
		createNeighbours();
		setUpWalls();
		
		
	}
	
	

	public void createNeighbours(){
		for(int i=0; i < list.length; i++){
			//System.out.println();
			for(int j=0; j < list.length; j++){
				int realI = i; int realJ = j;
				
				//Corner position only have 2 neighbours
				if(i==0 && j==0){				

					list[i][j].neighbours.add(list[i][++j]);
					--j;
					list[i][j].neighbours.add(list[++i][j]);

				}
				
				else if( i == list.length-1 && j == list.length-1 )
				{

					list[i][j].neighbours.add(list[i][--j]);
					++j;
					list[i][j].neighbours.add(list[--i][j]);
						
				}
				else if(i == 0 && j == list.length-1){

					list[i][j].neighbours.add(list[i][--j]);
					++j;
					list[i][j].neighbours.add(list[++i][j]);
					
				}
				else if(i == list.length-1 && j == 0){

					list[i][j].neighbours.add(list[i][++j]);
					--j;
					list[i][j].neighbours.add(list[--i][j]);
//list[++i][j].printNeighbours();

		
				}
				
				//wall nodes haves 3 neighbours
				
				//left wall nodes
				else if(i>j && j == 0 && i != list.length-1){
					
					list[i][j].neighbours.add(list[--i][j]);
					++i;
					list[i][j].neighbours.add(list[++i][j]);
					--i;
					list[i][j].neighbours.add(list[i][++j]);
	
				}
				
				//top wall
				else if(j > i && i ==0 && j != list.length-1){
					
					list[i][j].neighbours.add(list[i][--j]);
					++j;
					list[i][j].neighbours.add(list[++i][j]);
					--i;
					list[i][j].neighbours.add(list[i][++j]);
					
				}
				
				//bottom wall
				else if(i == list.length-1 && j != 0 && j != list.length-1)
				{
					list[i][j].neighbours.add(list[i][--j]);
					++j;
					list[i][j].neighbours.add(list[--i][j]);
					++i;
					list[i][j].neighbours.add(list[i][++j]);
				}
				
				//right wall
				else if(j == list.length-1 && i != 0 && i != list.length-1)
				{
					list[i][j].neighbours.add(list[i][--j]);
					++j;
					list[i][j].neighbours.add(list[--i][j]);
					++i;
					list[i][j].neighbours.add(list[++i][j]);
				}
				
				//the rest have 4 neighbours
				else{
					
					list[i][j].neighbours.add(list[i][--j]);
					++j;
					list[i][j].neighbours.add(list[++i][j]);
					--i;
					list[i][j].neighbours.add(list[i][++j]);
					--j;
					list[i][j].neighbours.add(list[--i][j]);
					
				}
				

				//reset i and j to what they were when they entered the loop
				i = realI; j = realJ;

			}//end of inner for loop
			
		}//end of outer for loop
		
	
	}//end of create neighbours
	
	public void setUpWalls(){
		
		if(MaxGridX > MaxGridY){
			for (int i= 0; i <MaxGridY; i++){
				for(int j = 0; j < MaxGridX; j++)
				{
					board[0][i] = '1';
					board[j][0] = '1';
					board[MaxGridX-1][i] = '1';
					board[j][MaxGridY-1] = '1';
				}
			}
		}
		else if(MaxGridX < MaxGridY){
			for (int i= 0; i <MaxGridX; i++){
				for(int j = 0; j < MaxGridY; j++)
				{
					board[i][0] = '1';
					board[0][j] = '1';
					board[MaxGridX-1][j] = '1';
					board[i][MaxGridY-1] = '1';
					
				}
			}
		}
		//same size
		else{
			for (int i= 0; i <MaxGridX; i++){
					board[i][0] = '1';
					board[i][MaxGridX-1] = '1';
					board[0][i] = '1';
					board[MaxGridX-1][i] = '1';
			}
			
		}
		
		
	}
	
	public void createSearchSpace(){
		int nodeID = 0;

		for(int i =0; i < MaxGridX; i++){
			for(int j=0; j < MaxGridY; j++ ){
				
				
				if(getBoardStatus(i, j) == Grid.OBSTACLE || getBoardStatus(i, j) == Grid.WALL)
					continue;
				//create the searchspace
				list[i][j] = new Node(nodeID++, i, j);
					
			}
		}
	}
	
	
	
	public void printBoard(){
		setUpWalls();

		
		for(int i =0; i < MaxGridX; i++){
			
				System.out.println();
			for(int j=0; j < MaxGridY; j++ )
				System.out.print(board[i][j]);
		}

		
	}
	
	public void updateSnake(Snake snake){
		//get the head position
		
		
			int headX = snake.getHeadX();
			int headY = snake.getHeadY();
			
			int newX = snake.getNewX();
			int newY = snake.getNewY();


			int i = snake.getSnakeLength()-1;
			

	
			//place the head at the new location
			board[newX][newY] = snake.getSnake(0);
			
		
			
			for(; i > 0 ; i--){
				
				if(firstRender){

					if(i == snake.getSnakeLength()-1){
						//slant if he's passing the grid
						if(headX-i == 0){
							board[headX-i+1][headY-1] = snake.getSnake(i);
							snake.setSnakePosX(i,(headX-i+1));
							snake.setSnakePosY(i, headY-1);
						}
						else if(headX-i < 0){
							board[headX-i+2][headY-2] = snake.getSnake(i);
							snake.setSnakePosX(i,(headX-i+2));
							snake.setSnakePosY(i, headY-2);
						}
						else{
							board[headX-i][headY] = snake.getSnake(i);

							snake.setSnakePosX(i,(headX-i));
							snake.setSnakePosY(i, headY);
						}
						
						continue;
					}
				
					if(headX-i == 0){
						board[headX-i+1][headY-1] = snake.getSnake(i);
						snake.setSnakePosX(i, headX-i+1);
						snake.setSnakePosY(i, headY-1);
					}
					else{
						board[headX-i][headY] = snake.getSnake(i);
						snake.setSnakePosX(i, headX-i);
						snake.setSnakePosY(i, headY);
					}
					
					if(i == 1){
						snake.setSnakePosX(i, headX-i);
						snake.setSnakePosY(i, headY);
						firstRender = false;
						break;
					}			
				
				
				
				
				continue;

			}
				
				board[snake.getSnakeX(i-1)][snake.getSnakeY(i-1)] = snake.getSnake(i);
				snake.setSnakePosX(i, snake.getSnakeX(i-1));
				snake.setSnakePosY(i, snake.getSnakeY(i-1));
	
			}	
		
			snake.setSnakePosX(0, newX);
			snake.setSnakePosY(0, newY);
		

	}
	
	public void printObstacles(){
		System.out.print("\nObstacles at { ");
		for(int i =0; i < MaxGridX; i++){
			for(int j=0; j < MaxGridY; j++ ){
				if(getBoardStatus(i,j) == Grid.OBSTACLE)
					System.out.print("("+i + " , " + j+")");
			}
		}
		System.out.println(" }");
	}
	
	public Grid getBoardStatus(int i, int j){
		if(board[i][j] == ' ')
		{
			return Grid.EMPTY;
		}
		else if (board[i][j] == '1')
		{
			return Grid.WALL;
		}
		else if (board[i][j] == '2')
		{
			return Grid.FOOD;
		}
		else if (board[i][j] == '3')
		{
			return Grid.OBSTACLE;
		}
		else
			return Grid.SNAKE;
	}
	
	public void placeFood(Food food){
		int x = food.getFoodX();
		int y = food.getFoodY();
		
		board[x][y] = '2';

	}
	
	public void placeObstacle(int x, int y){
		board[x][y] = '3';
	}
	
	public void update(Snake snake, boolean drawSnake){
		
		for(int i =1; i < MaxGridX; i++){
			for(int j=1; j < MaxGridY; j++ ){
				if(getBoardStatus(i,j) != Grid.FOOD && getBoardStatus(i,j) !=Grid.OBSTACLE)
					board[i][j] = ' ';

			}
			
			
		}
		
		setUpWalls();
		if(drawSnake){
			updateSnake(snake);
			printBoard();
		}
	}
	
	
}
