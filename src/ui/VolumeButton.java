package ui;



import java.awt.Graphics;
import java.awt.image.BufferedImage;

import utilz.LoadSave;
import static utilz.Constants.UI.VolumeButtons.*;

public class VolumeButton extends PauseButton{

    private BufferedImage[] imgs;
    private BufferedImage slider;
    private boolean mouseOver, mousePressed;
    private int index =0;
    private int buttonX, minX, maxX;

    //(int x, int y, int width, int height) these parameters are actually taking the who volume image as bounds but we need only slider so we need to edit this
    public VolumeButton(int x, int y, int width, int height) {
        super(x + width/2, y, VOLUME_WIDTH, height); //this is goind to be hitbox of our button
        bounds.x -= VOLUME_WIDTH / 2;
        buttonX = x + width/2;
        this.x =x;
        this.width=width;
        this.minX=x + VOLUME_WIDTH /2;
        this.maxX= x + width -VOLUME_WIDTH /2;
        loadImgs();
       
    }
    
    private void loadImgs(){
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.VOLUME_BUTTONS);
        imgs = new BufferedImage[3];
        for(int i=0 ; i<imgs.length; i++){
            imgs[i] = temp.getSubimage(i* VOLUME_DEFAULT_WIDTH, 0, VOLUME_DEFAULT_WIDTH, VOLUME_DEFAULT_HEIGHT);
        }

        slider = temp.getSubimage(3*VOLUME_DEFAULT_WIDTH, 0, SLIDER_DEFAULT_WIDTH, VOLUME_DEFAULT_WIDTH);
    }
    public void update(){
        index = 0;
		if (mouseOver)
			index = 1;
		if (mousePressed)
			index = 2;

	}
        

    

    public void draw(Graphics g){
        g.drawImage(slider, x, y, width, height, null);
        g.drawImage(imgs[index], buttonX - VOLUME_WIDTH/2, y, VOLUME_WIDTH,height, null);
        
        
    }


    public void changeX(int x) {
		if (x < minX) //if we try to move slider to the left of its mininmun it will set it till min
			buttonX = minX;
		else if (x > maxX) //this is for max
			buttonX = maxX;
		else
			buttonX = x;

		bounds.x = buttonX - VOLUME_WIDTH / 2;

	}
    
    public void resetBools(){
        mouseOver =false;
        mousePressed=false;
    }



//getters and setters
    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }



}
