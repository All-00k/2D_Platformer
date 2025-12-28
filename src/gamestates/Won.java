package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;


import main.Game;
import utilz.LoadSave;

public class Won extends State implements Statemethods {
    private BufferedImage wonScreen;

    public Won(Game game) {
        super(game);
        wonScreen = LoadSave.GetSpriteAtlas(LoadSave.WON_SCREEN);
        
    }


 

    @Override
    public void update() {
        
    }

    @Override
    public void draw(Graphics g) {

        g.drawImage(wonScreen, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT,null);
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) { 

    }
        

    @Override
    public void mouseMoved(MouseEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }
        

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    
}
