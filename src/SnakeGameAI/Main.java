package SnakeGameAI;

public class Main {


	public static void main(String[] args) {

		int maxGridX = 10;
		int maxGridY = 10;
		int snakeSize = 4;
		int numObstacles = 10;
		int searchType = 1;
		int heuristics = 0;
		int simTime = 2;
	
		if(args.length <12 ){
			System.out.println("Arguments Not accepted.\nUsing default of -Gx "
	        		+maxGridX+" -Gy " + maxGridY+ "-o" + numObstacles + "-Ss" + snakeSize + " -st " + searchType +" -h "+heuristics+ "-s " + simTime); }
	    else{                        
	            try{
	                    for (int i=0; i<args.length;++i) {
	                            if(args[i].equals("-Gx")){
	                                   ++i;
	                                   maxGridX = Integer.parseInt(args[i]);
	                                  
	                            }
	                            else if(args[i].equals("-Gy")){
	                            	++i;
	                            	maxGridY = Integer.parseInt(args[i]);
	                            }
	                            else if(args[i].equals("-Ss")){
	                            	++i;
	                            	snakeSize = Integer.parseInt(args[i]);
	                            }
	                            else if(args[i].equals("-o")){
	                            	++i;
	                            	numObstacles = Integer.parseInt(args[i]);
	                            }
	                            else if(args[i].equals("-s")){
	                            	++i;
	                            	simTime = Integer.parseInt(args[i]);
	                            }
	                            else if(args[i].equals("-st")){
	                            	++i;
	                            	searchType = Integer.parseInt(args[i]);
	                            }
	                            else if(args[i].equals("-h")){
	                            	++i;
	                            	heuristics = Integer.parseInt(args[i]);
	                            }
	                            
	                    } 
	                    
	                    System.out.println("Arguments accepted.\n");  
	            }catch(Exception e){
	            	System.out.println("Arguments Not accepted.\nUsing default of -Gx "
	    	        		+maxGridX+" -Gy " + maxGridY+ " -st " + searchType +" -h "+heuristics+ "-s " + simTime); }
	                
	            }
	            
		
		long start = System.currentTimeMillis();
		long end = start + simTime*1000; 
		
		Game game = new Game(maxGridX, maxGridY, snakeSize, numObstacles, searchType, heuristics);
		
		boolean firstStart = true;
		while (System.currentTimeMillis() < end){
			game.start(firstStart);

			long pauseStart = System.currentTimeMillis();
			long pauseEnd = pauseStart + 1000;
			while (System.currentTimeMillis() < pauseEnd){
				
			}
			firstStart = false;
			
		}

		
		System.out.println("\nSimulation has Ended");


	}

}
