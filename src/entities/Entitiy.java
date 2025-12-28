package entities;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;



public abstract class Entitiy {
     protected float x,y;
     protected int width, height;
     protected Rectangle2D.Float hitbox;


    public Entitiy(float x, float y,int width, int height){
        this.x = x;
        this. y =y;
        this.width = width;
        this.height = height;
        
    }

    protected void drawHitbox(Graphics g , int xLvlOffset){
        //for debugging the hitbox
        g.setColor(Color.PINK);
        g.drawRect((int)hitbox.x-xLvlOffset, (int)hitbox.y, (int)hitbox.width, (int)hitbox.height);

    }



    protected void initHitbox(float x, float y, int width, int height){
        hitbox = new Rectangle2D.Float(x,y,width,height);
    }

    // protected void updateHitbox(){ //protected so that only childs of Enitiy can update it like player or enimies
    //     hitbox.x = (int) x; 
    //     hitbox.y = (int) y;

    // }

    public Rectangle2D.Float getHitbox() {
        return hitbox;

    }    
}
