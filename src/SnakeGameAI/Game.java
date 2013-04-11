package SnakeGameAI;


/**
 * @author Joyce
 *Sets up and creates the environment for the snake.
 *Initializes the game board and the environment based off of the users command collected from the Main.
 *
 *This is where the game is being "played" and where the board gets notified of changes.
 *
 */

public class Game {

	GameBoard board;
	Snake snake;
	Food  food;
	
	int numObstacles;
	Game game;

	
	public Game(int maxGridX, int maxGridY, int snakeSize, int numObstacles, int searchType, int heuristics){
		board = new GameBoard(maxGridX, maxGridY);

		//place snake in the middle of the board
		snake = new Snake(snakeSize, maxGridX/2, maxGridY/2, board, searchType, heuristics ); 
		
		this.numObstacles = numObstacles;
		
		for(int i=0; i< numObstacles; i++){
			addRandBlock();
		}

		for(int i=0; i < 2; i++)
			randomFood();

		
	}
	
	//everything has been placed, now draw the snake
	public void start(boolean firstStart){

		startSnake(firstStart);

	}

	public void addFood(int x, int y){
		Food food = new Food(x,y);
		snake.addFoodToEat(food);
		board.placeFood(food);
		board.update(snake, false);

	}
	
	
	public int getSnake(int i){
		return snake.getSnake(i);
	}

	public void startSnake(boolean firstStart){

	
		Node list[][] = board.list;
		long start = System.currentTimeMillis();
		long end = start + 1000; 
	
			if(firstStart)
				board.update(snake, true);
			else
				board.update(snake, false);
			
			snake.searchForFood(list);
	
			if(System.currentTimeMillis() < end){
				randomFood();
				start = System.currentTimeMillis();
				
			}
	
	}
	
	public void randomFood(){
		int randx = 0;
		int randy = 0;
		boolean isgood = false;
		
		while(!isgood){
			randx = (int) (Math.random()*board.MaxGridX);
			randy = (int) (Math.random()*board.MaxGridY);
			
			isgood = isProper(randx, randy);
		}
		
		addFood(randx, randy);
		
	}
	
	public void addRandBlock(){
		int randx = 0;
		int randy = 0;
		boolean isgood = false;
		
		while(!isgood){
			randx = (int) (Math.random()*board.MaxGridX);
			randy = (int) (Math.random()*board.MaxGridY);
			
			isgood = isProper(randx, randy);
		}
		board.placeObstacle(randx, randy);

	}
	
	public boolean isProper(int x, int y){
			if(board.getBoardStatus(x, y) == GameBoard.Grid.EMPTY){
				
				return true;
			}
			return false;
	}
	

}
