package SnakeGameAI;

public class Food {

	int foodX;
	int foodY;

	public Food(int posX, int posY){
		foodX = posX;
		foodY = posY;
		
	}
		
	public int getFoodX(){
		return foodX;
	}
	
	public int getFoodY(){
		return foodY;
	}

}
