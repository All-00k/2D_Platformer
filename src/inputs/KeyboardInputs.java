package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import gamestates.Gamestate;
import main.GamePanel;
import static utilz.Constants.Direction.*;



public class KeyboardInputs implements KeyListener {

    private GamePanel gamePanel;
    public KeyboardInputs(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }


    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        switch (Gamestate.state) {
            case MENU:
                gamePanel.getGame().getMenu().keyPressed(e);
                break;
            case PLAYNG:
                gamePanel.getGame().getPlaying().keyPressed(e);
                break;
            default:
                break;
           
        }
      
       
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        switch (Gamestate.state) {
            case MENU:
                gamePanel.getGame().getMenu().keyReleased(e);
                break;
            case PLAYNG:
                gamePanel.getGame().getPlaying().keyReleased(e);
                break;
            default:
                break;
           
        }
       
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        
        
    }
    
}
