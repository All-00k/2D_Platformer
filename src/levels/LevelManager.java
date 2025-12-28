package levels;



import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import utilz.LoadSave;

public class LevelManager {

    public static Object getCurrentLevel;
    private Game game;
    private BufferedImage[] levelSprite; //single dimesnison array to store the sprites
    private Level levelOne;

    public static final String PLAYER_ATLAS = "player_sprites.png";

    
    public LevelManager(Game game) {
        this.game = game;
       
        importOutsideSprites();
        levelOne = new Level(LoadSave.GetLevelData());
       
    }

    private void importOutsideSprites() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
        levelSprite = new BufferedImage[48];
        for(int j =0 ; j<4;j++){//since our sprites sheet is 4 sprites high
            for(int i=0;i<12;i++){ //sprite sheet is 12 sprites wide
                int index = j*12 + i;
                levelSprite[index] = img.getSubimage(i*32, j * 32, 32, 32);
                //in upper line we assigned the sprites to a particular index of levelSpites array


            }
        }

    }

    public void draw(Graphics g, int lvlOffset){
        

        for(int j=0; j< Game.TILES_IN_HEIGHT; j++){
            for(int i=0; i< levelOne.getLevelData()[0].length; i++){
                int index = levelOne.getSpriteIndex(i, j);//levelOne is the object od Level class
                g.drawImage(levelSprite[index], Game.TILES_SIZE*i - lvlOffset, Game.TILES_SIZE*j, Game.TILES_SIZE,Game.TILES_SIZE,null);

            }
        }

    }

    

    public void update(){
        
    }
    
    public Level getCurrentLevel(){
        return levelOne;
    }
}
