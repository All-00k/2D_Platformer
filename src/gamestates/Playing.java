package gamestates;

import static utilz.Constants.Environment.BIG_CLOUD_HEIGHT;
import static utilz.Constants.Environment.BIG_CLOUD_WIDTH;
import static utilz.Constants.Environment.SMALL_CLOUD_HEIGHT;
import static utilz.Constants.Environment.SMALL_CLOUD_WIDTH;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import entities.EnemyManager;
import entities.Player;
import levels.LevelManager;
import main.Game;
import ui.GameOverOverlay;
import ui.PauseOverlay;
import utilz.LoadSave;
import utilz.Constants.Environment.*;



public class Playing extends State  implements Statemethods{
  

    private Player player;
    //one more here
    
	private LevelManager levelManager;
    private EnemyManager enemyManager;
    private boolean paused = false;
    private PauseOverlay pauseOverlay;

    private GameOverOverlay gameOverOverlay;
    private boolean gameOver;

    private int xLvlOffset;
    private int leftBorder = (int) (0.2 * Game.GAME_WIDTH); //setting variables so that we can scale out scenes for our game
    private int rightBorder = (int) (0.8 * Game.GAME_WIDTH);

    //we need a max value for offset what if we reach end of our leveldata so we have to make sure that our player dont do beyond or mayebe a new finised window should appear
    private int LvlTilesWide =  LoadSave.GetLevelData()[0].length; // here we getting length/width of enitre image (number of tiles in x direction whole map)
    // we need a variable so that we can specify how much tiles are left to be shown
    private int maxTilesOffset = LvlTilesWide - Game.TILES_IN_WIDTH;
    private int maxLvlOffsetX = maxTilesOffset * Game.TILES_SIZE; //changing maxTilesOffset into pixels

    private BufferedImage backgroundImg, bigCloud, smallCloud;
    private int[] smallCloudsPos ;
    private Random rnd = new Random();
    
    public Playing(Game game) {
        super(game);
        initClasses();

        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG);
        bigCloud = LoadSave.GetSpriteAtlas(LoadSave.BIG_CLOUDS);
        smallCloud = LoadSave.GetSpriteAtlas(LoadSave.SMALL_CLOUDS);
        smallCloudsPos = new int[8];
        for(int i=0;i< smallCloudsPos.length;i++){
           smallCloudsPos[i] = (int) (90 * Game.SCALE) + rnd.nextInt((int) (100 * Game.SCALE));
        }
       
    }

    private void initClasses() {
		levelManager = new LevelManager(game);
        enemyManager = new EnemyManager(this); //this because we need a Playing class Object as a n argument 
		player = new Player(200, 200, (int) (64* Game.SCALE), (int) (40* Game.SCALE),this);

  
       
		player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
        pauseOverlay = new PauseOverlay(this);
        System.out.println("size: "+ LoadSave.GetLevelData()[0].length);
        gameOverOverlay = new GameOverOverlay(this);

              
        
        
	}
   
    @Override
    public void update() {
        if(!paused && !gameOver){
            levelManager.update();
            player.update();
            enemyManager.update(levelManager.getCurrentLevel().getLevelData(),player);
            checkCloseToBorder();

        }
        else{
           
            pauseOverlay.update();
        }
   
    }

    private void checkCloseToBorder() {
        int playerX = (int) player.getHitbox().x;
        int diff = playerX - xLvlOffset; //we check whether diff is more than right or less than left border to make changes
        int playerY = (int) player.getHitbox().y;
        // System.out.println("test y: "+playerY );
       
        if(diff > rightBorder)
            xLvlOffset += diff -rightBorder;

        else if(diff < leftBorder)
            xLvlOffset += diff - leftBorder;

        if(xLvlOffset>maxLvlOffsetX)
            xLvlOffset = maxLvlOffsetX;
        else if(xLvlOffset<0)
            xLvlOffset=0;


//    // WAS TRYING TO QUIT GAME WHEN IT REACHES END OF THE LEVEL AND IT WORKED WOORKED
        if(playerX   >= 3150 )
        {
            
            Gamestate.state = Gamestate.WON;
            resetAll();
        }

          
         if(playerY   >= 830 )
        {
            
           
            resetAll();
        }



    
        
    }

    @Override
    public void draw(Graphics g) {

        g.drawImage(backgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);

        drawClouds(g);
        levelManager.draw(g,xLvlOffset);
        player.render(g,xLvlOffset);
        enemyManager.draw(g, xLvlOffset);

        if(paused){//to maek background somewhat blur(blacked out)
            g.setColor(new Color(0,0,0,200));
            g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
            pauseOverlay.draw(g);
        }
        else if(gameOver)
            gameOverOverlay.draw(g);

        
        

        
    }

    private void drawClouds(Graphics g) {
        for (int i = 0; i < 4; i++)
			g.drawImage(bigCloud,  i*BIG_CLOUD_WIDTH/2 - (int) (xLvlOffset *0.3) ,  (int)(189* Game.SCALE) ,BIG_CLOUD_WIDTH,BIG_CLOUD_HEIGHT*2,null);
        
        for(int i =0; i<smallCloudsPos.length; i++)
            g.drawImage(smallCloud, SMALL_CLOUD_WIDTH * 4*i - (int) (xLvlOffset *0.7), smallCloudsPos[i], SMALL_CLOUD_WIDTH, SMALL_CLOUD_HEIGHT, null);

       
    }

    public void resetAll(){
        //TODO: Reset playing, enemy, lcl etc.
        gameOver = false;
        paused = false;
        player.resetAll();
        enemyManager.resetAllEnemies();

    }


    public void setGameOver(boolean gameOver){
        this.gameOver = gameOver;
    }

    public void checkEnemyHit(Rectangle2D.Float attackbox){
        enemyManager.checkEnemyHit(attackbox);
    }



    public void mouseDragged(MouseEvent e){
        if(!gameOver)
            if(paused){
                pauseOverlay.mouseDragged(e);
            }

    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if(!gameOver)
            if(e.getButton() == MouseEvent.BUTTON1)
                player.setAttacking(true);
            
       
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(!gameOver)
            if(paused)
                pauseOverlay.mousePressed(e);
            
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(!gameOver)
            if(paused)
                pauseOverlay.mouseReleased(e);
            
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(!gameOver)
            if(paused)
                pauseOverlay.mouseMoved(e);
    
    }

    

    @Override
    public void keyPressed(KeyEvent e) {
        if(gameOver)
            gameOverOverlay.KeyPressed(e);
        else
            switch (e.getKeyCode()) {
            
                case KeyEvent.VK_A: 
                    player.setLeft(true);
                    break;
            
                case KeyEvent.VK_D:
                    player.setRight(true);
                    break;

                case KeyEvent.VK_SPACE:
                    player.setjump(true);
                    break;

                case KeyEvent.VK_ESCAPE:
                paused = !paused;
                break;

            
            
        }
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(!gameOver)
           switch (e.getKeyCode()) {
			case KeyEvent.VK_A:
				player.setLeft(false);
				break;
			case KeyEvent.VK_D:
				player.setRight(false);
				break;
			case KeyEvent.VK_SPACE:
				player.setjump(false);
				break;
        }
        
    }

    public void unpauseGame(){
        paused = false;
    }
    
    public void windowFocusLost(){
		player.resetDirBooleans();
	}

	public Player getPlayer(){
		return  player;
	}

    
}
