package utilz;

import static utilz.Constants.EnemyConstants.CRABBY;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import entities.Crabby;
import main.Game;

public class LoadSave {

    public static final String PLAYER_ATLAS = "player_sprites.png";
    public static final String LEVEL_ATLAS = "outside_sprites.png";
   // public static final String LEVEL_ONE_DATA = "level_one_data.png";
    public static final String LEVEL_ONE_DATA = "level_one_data_long.png";
    //public static final String LEVEL_ONE_DATA = "Level_update.png";
    public static final String MENU_BUTTON = "button_atlas.png";
    public static final String MENU_BACKGROUND = "menu_background.png";
    public static final String ENEMY_ATLAS = "crabby_sprite.png";
    public static final String PAUSE_BACKGROUND = "pause_menu.png";
    public static final String SOUND_BUTTONS = "sound_button.png";
    public static final String URM_BUTTONS = "urm_buttons.png";
    public static final String VOLUME_BUTTONS = "volume_buttons.png";
    public static final String MENU_BACKGROUND_IMG = "background_menu.png";
    public static final String PLAYING_BG_IMG = "playing_bg_img.png";
    public static final String BIG_CLOUDS = "ak.png";
    public static final String SMALL_CLOUDS = "small_clouds.png";
    public static final String CRABBY_SPRITE = "crabby_sprite.png";
    public static final String END_SCREEN= "EndScreen.png";
    public static final String WON_SCREEN = "Won.png";
    public static final String STATUS_BAR = "health_power_bar.png";

    public static BufferedImage GetSpriteAtlas(String fileName){
        BufferedImage img= null;
        InputStream is = LoadSave.class.getResourceAsStream("/"+fileName);
        

        try {
           
           img = ImageIO.read(is); //try catch we be  dont while loading any fiel cause file may not exist

        }
        catch (IOException e) {
            e.printStackTrace();
        }finally{
            try{
                is.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        } 
        return img;
    }

    //same as level data we now need crabs data
    public static ArrayList<Crabby> GetCrabs(){
        BufferedImage img = GetSpriteAtlas(LEVEL_ONE_DATA);
        ArrayList<Crabby> list = new ArrayList<>();
        for(int j =0; j<img.getHeight(); j++){
            for(int i =0; i<img.getWidth();i++){
                Color color = new Color(img.getRGB(i, j));
                int value = color.getGreen();
                if(value == CRABBY ){  //if value of green component is greater than 0 we got a crab position we will add that in list
                    list.add(new Crabby(i* Game.TILES_SIZE, j *Game.TILES_SIZE)); //add the position of crab
                }
            }
        }
        System.out.println(list.size());//number od crabbies in this we are using
        
        return list;
       
        
        

        



    }



    public static int[][] GetLevelData(){ //to get data from the paint file at any particular corrinate
        
        BufferedImage img = GetSpriteAtlas(LEVEL_ONE_DATA);
        int[][] lvlData = new int[img.getHeight()][img.getWidth()];

        for(int j =0; j<img.getHeight(); j++){
            for(int i =0; i<img.getWidth();i++){
                Color color = new Color(img.getRGB(i, j));
                int value = color.getRed();
                if(value >= 48){ //in case value is more than 48 so it will be out of bound
                    value =0;
                }
                lvlData[j][i]  = value;

            }
        }
        return lvlData;
    }
    
}
