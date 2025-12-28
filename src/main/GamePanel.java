package main;


import java.awt.Dimension;
import java.awt.Graphics;



import javax.swing.JPanel;

import inputs.KeyboardInputs;
import inputs.MouseInputs;



import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;


public class GamePanel extends JPanel{

private MouseInputs  mouseInputs; //an object of mouseinputs so we can use same object in multiple places
   
private Game game;

    public GamePanel(Game game){
        this.game = game;
        

        mouseInputs = new MouseInputs(this); //intializing the object
        
        

        setPanelSize();


        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
        

    }

    

   

    private void setPanelSize() {
        Dimension size = new  Dimension(GAME_WIDTH,GAME_HEIGHT);
        
        //setMinimumSize(size);
        setPreferredSize(size);
        System.out.println("size: "+GAME_WIDTH+" : "+GAME_HEIGHT);
    }

    



    
   //to make the frame play in a loop to look it like animation
   

    

    public void updateGame(){
        

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g); //thhis is calling the super class method (JComponent is the Parent class) to make us paint in frame
        game.render(g);
        }

    
    
    public Game getGame(){
        return game;
    }
    

    

    
    

    
}

