package main;

import java.awt.Graphics;


import gamestates.Won;
import gamestates.Gamestate;
import gamestates.Menu;
import gamestates.Playing;
import utilz.LoadSave;

public class Game implements Runnable{
	
	private GameWindow gamewindow;
	private GamePanel gamePanel;
	private Thread gameThread;
	private final int FPS_SET =120;
	private final int UPS_SET =200;

	private Playing playing;
	private Menu menu;

	private Won won;
	

	public final static int TILES_DEFAULT_SIZE =32;
	public final static float SCALE =2f;
	public final static int TILES_IN_WIDTH = 26; //number of tiles in x direction in a single frame //visible width
	public final static int TILES_IN_HEIGHT =14; //number of tiles on y position // visible height
	public final static int TILES_SIZE =(int) (TILES_DEFAULT_SIZE * SCALE);
	public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
	public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;

	public Game() {
		initClasses(); //to intialize all class of game state
		gamePanel = new GamePanel(this);
		gamewindow = new GameWindow(gamePanel); //created a object of GameWindow as the constructor will be called the attributes of class of GameWindow() will work.. first main function will be called then Game() constructor then gamewindow object will use attributesits class
		
		gamePanel.requestFocus();

		
		
		startGameLoop(); //game loop should be last thingh toput in Game constructr
		
	


		
	}

	private void initClasses() {
		menu = new Menu(this);

		playing = new Playing(this);

		
		won = new Won(this);
		
		
		
	}

	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	public void update() {
		
		switch(Gamestate.state) {
			case MENU:
				menu.update();
				break;
			case PLAYNG:
				playing.update();
				
				break;
			

			case WON:
				won.update();
				break;
			case OPTIONS:
			case QUIT:
			default:
				System.exit(0);
				break;
			
		}
		
	
	}

	public void render(Graphics g){
		
			switch(Gamestate.state) {
			case MENU:
				menu.draw(g);
				
				break;
			case PLAYNG:
				playing.draw(g);

				break;
			
			case WON:
				won.draw(g);
				break;
			default:
				break;
			
		}
	}

	@Override
	public void run() {

		double timePerFrame = 1000000000.0 / FPS_SET; //how many nanosec each frames lasts (for rendering)

		
		double timePerUpdate = 1000000000.0 / UPS_SET; //frequency of updateW (for game logic)


		

		long previousTime = System.nanoTime();



		int frames = 0;
		int updates = 0;
		long lastCheck = System.currentTimeMillis();

		double deltaU =0;
		double deltaF =0;


		while (true) {

			
			long currentTime = System.nanoTime();

			//using deltaU so that the time which is not used might not get lost	
			deltaU += (currentTime - previousTime) / timePerUpdate; //deltaU will be 1.0 or more WHEN the duration since last update is equal or more than timePerUpdate

			deltaF += (currentTime - previousTime) / timePerFrame;

			previousTime = currentTime;
			if(deltaU >=1){
				update();
				updates++;
				deltaU--;
			}

			if(deltaF>=1){
				gamePanel.repaint();
				frames++;
				deltaF--;
			}

			

			if (System.currentTimeMillis() - lastCheck >= 1000) {
				lastCheck = System.currentTimeMillis();
				System.out.println("FPS: " + frames+"| "+"UPS: "+updates);
				frames = 0;
				updates =0;
			}
		}

		
	}

	public void windowFocusLost(){
		if(Gamestate.state == Gamestate.PLAYNG){
			playing.getPlayer().resetDirBooleans();
		}
		
	}

	public Menu getMenu(){
		return menu;

	}

	public Playing getPlaying(){
		return playing;
	}


}
