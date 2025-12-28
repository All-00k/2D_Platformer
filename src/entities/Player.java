package entities;

import static utilz.Constants.PlayerConstants.*;
import static utilz.HelpMethods.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import gamestates.Playing;
import main.Game;
import utilz.LoadSave;

public class Player extends Entitiy{
    private Playing playing;
    private int aniTick, aniIndex, aniSpeed=15;
    private int playerAction = IDLE; 
    private boolean moving = false,attacking =false;
    private boolean left,up,down,right,jump;
    private float playerSpeed =1.0f * Game.SCALE;
    private int[][] lvlData;
    private float xDrawOffset =21 * Game.SCALE;
    private float yDrawOffset = 4 * Game.SCALE;

    //jumping    and     gravity 
    private float airSpeed =0f;
    private float gravity = 0.04f * Game.SCALE;
    private float jumpSpeed = -2.25f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f *Game.SCALE;
    private boolean inAir = false;
    private BufferedImage[][] animations;


    // StatusBarUI
	private BufferedImage statusBarImg;
    
	private int statusBarWidth = (int) (192 * Game.SCALE);
	private int statusBarHeight = (int) (58 * Game.SCALE);
	private int statusBarX = (int) (10 * Game.SCALE);
	private int statusBarY = (int) (10 * Game.SCALE);

	private int healthBarWidth = (int) (150 * Game.SCALE);
	private int healthBarHeight = (int) (4 * Game.SCALE);
	private int healthBarXStart = (int) (34 * Game.SCALE);
	private int healthBarYStart = (int) (14 * Game.SCALE);


    //to fill aur decrease health bar
    private int maxHealth = 100;
	private int currentHealth = maxHealth;
	private int healthWidth = healthBarWidth;

    // AttackBox
	private Rectangle2D.Float attackBox; //this is the tile(the box) if enemy is present in this the enemy will be effected


    //to flip the sprite
	private int flipX = 0;
	private int flipW = 1;


    private boolean attackChecked;

  

    public Player(float x, float y, int width, int height, Playing playing) {
        super(x, y,width, height);
        this.playing = playing;
        loadAnimation();
        initHitbox(x,y, (int) (20* Game.SCALE), (int) (28*Game.SCALE)); //siize of actual hitbox 20 and 28
        initAttackBox();
       


        
    }
    

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x,y,(int) (20*Game.SCALE), (int) (20*Game.SCALE));
    }


    public void update(){
        updateHealthBar();

        if(currentHealth <=0){
            playing.setGameOver(true);
            return;
        }

        
        updateAttackBox();

        updatePos();
        if(attacking)
            checkAttack();

        updateAnimationTick(); //this is updating tick everytime paint componeent is called means every frame

        setAnimation();
        
        

    }

    private void checkAttack() {
       if(attackChecked || aniIndex !=1) //1 is the index where we are actually doing the attack animation
            return;
       attackChecked =true;
       playing.checkEnemyHit(attackBox);
        
    }


    private void updateAttackBox() {
        if (right)
			attackBox.x = hitbox.x + hitbox.width + (int) (Game.SCALE * 10);
		else if (left)
			attackBox.x = hitbox.x - hitbox.width - (int) (Game.SCALE * 10);

		attackBox.y = hitbox.y + (Game.SCALE * 10);
        
    }


    private void updateHealthBar() {
        healthWidth = (int) ((currentHealth/(float) maxHealth) *healthBarWidth); //this shows what amount of fraction od health bar would be filled if cuurent is 40 and max is 100 the 0.4 times the healthbarWidth is filled
    }


    public void render(Graphics g, int lvlOffset ){
        g.drawImage(animations[playerAction][aniIndex], (int) (hitbox.x -xDrawOffset) - lvlOffset + flipX, (int) (hitbox.y- yDrawOffset),width * flipW ,height, null);
       //drawHitbox(g,lvlOffset); //lets not draw the hitbox only its behaviour is shown
      // drawAttackBox(g,lvlOffset);
        drawUI(g);

    }


     

    
    

    
    private void drawAttackBox(Graphics g, int lvlOffsetX) {
        g.setColor(Color.red);
        g.drawRect((int) attackBox.x - lvlOffsetX,(int)attackBox.y,(int)attackBox.width, (int)attackBox.height);
       

    }


    private void drawUI(Graphics g) {
       g.drawImage(statusBarImg,statusBarX,statusBarY,statusBarWidth,statusBarHeight,null);
       g.setColor(Color.red);
       g.fillRect(healthBarXStart +statusBarX, healthBarYStart +statusBarY, healthWidth,healthBarHeight); 
       

    }


    private void updateAnimationTick() {
        aniTick++;
        if(aniTick >= aniSpeed){
            aniTick=0;
            aniIndex++;
            if(aniIndex>=GetSpriteAmount(playerAction)){ //if animation index get greater than the length og idle animation array it will set it to zero and hence forth the loop we be going till the paintComponent is called
                aniIndex=0;
                attacking=false;
                attackChecked = false;
            }
       }
       
       
    }

    private void setAnimation() {
        int startAni = playerAction;

        if(moving) //we passed moving true so if will start RUNNING
            playerAction = RUNNING; //RUNNING return playerAction=6(from CONSTANTS) and that playerAction goes to updateAniamtionTick methods's loop
        else
            playerAction = IDLE; //FALSE SO IT WILL BE IDLE

        if(inAir){
            if(airSpeed <0)
                playerAction = JUMP;
            else
                playerAction = FALLING;

            

        }
        
        if(attacking){
            playerAction = ATTACK; //attack  animation start
            if(startAni != ATTACK){ //as soon we entered attack if it is not attacking we will have a dealy so changing attack index to actual attack sprite will help to remove delay
                aniIndex =1; 
                aniTick =0;
                return;
            }
        }

        if(startAni != playerAction){
            resetAnitick();
        }

            
        }
        
        private void resetAnitick() {
            aniTick =0;
            aniIndex=0;
    }

        private void updatePos() {
            moving = false;

            if(jump){
                jump();
            }

            // if(!left && !right && !inAir){
            //     return;
            // }

            // the upper commented code was causing a bug (when A and D was pressed together the run animation was playing buy player was not moving so we need to stop the animation)

            if(!inAir){
                if((!left && !right) || (left && right)){
                    return;
                }
            }


            float xSpeed =0;

            if(left){
                xSpeed -= playerSpeed;
                flipX = width; //only doinf flipX = width while left so that our sprite be at same spot and should just change the directio
                flipW = -1;
                   
            }
            if(right){
                xSpeed += playerSpeed;   
                flipX = 0; 
                flipW =1;
            }

            if(!inAir){
                if(!IsEntityOnFloor(hitbox, lvlData)){
                    inAir = true;

                }
            }


            if(inAir){
                if(CanMoveHere(hitbox.x, hitbox.y + airSpeed,hitbox.width, hitbox.height,lvlData)) {
                    hitbox.y += airSpeed;
                    airSpeed += gravity;
                    updateXPos(xSpeed);
                }
                else{ //if we are hitting the floor or the roof
                    hitbox.y = GetEntityYPosUnderRoofOrAboveTheFloor(hitbox, airSpeed);
                    if(airSpeed>0){//we are going down and we hit smthing
                     resetInAir();

                    }
                    else{  // we hit the room (so we go down with gravity)
                    airSpeed =fallSpeedAfterCollision;
                    }
                    updateXPos(xSpeed);
                }

            }
            else
                updateXPos(xSpeed);    
            
            moving = true;

        }

    private void jump() {
            if(inAir)
                return;
            
            
            inAir = true;
            airSpeed = jumpSpeed;
            

        }

    private void resetInAir(){
        inAir = false;
        airSpeed=0;
    }


    //method to change the heath bar on certain conditions
    public void changeHealth(int value){
        currentHealth += value;
        System.out.println("HEalth : "+ currentHealth);

        if (currentHealth <= 0)
			currentHealth = 0;
		else if (currentHealth >= maxHealth)
			currentHealth = maxHealth;
    }

    private void updateXPos(float xSpeed) {
                  
            if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
			    hitbox.x += xSpeed;
            }
            else{
                hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
                
            }
            
        }
    //to make player animations
    public void loadAnimation(){
        
          BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
        
          animations= new BufferedImage[7][8];  
          for(int j =0 ; j<animations.length; j++){ //we have 7 rows
            for(int i =0 ; i<animations[j].length ;i++){ //we have 8 colums at to the max for our player sprites
                animations[j][i] = img.getSubimage(i*64, j*40, 64, 40); //64 size in x and 40 size in y of the sprite which we are cutting out of sprite sheet

            }
        }

        statusBarImg = LoadSave.GetSpriteAtlas(LoadSave.STATUS_BAR);

    }  

    public void loadLvlData(int[][] lvlData){
        this.lvlData = lvlData;
        if(!IsEntityOnFloor(hitbox, lvlData))
            inAir = true;


    }
    

    public void resetDirBooleans(){
        left = false;
        right = false;
        up = false;
        down = false;
}

    public void setAttacking(boolean attacking){
        this.attacking =attacking;
    }




    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setjump(boolean jump) {
        this.jump = jump;
    }

    public void resetAll(){
        resetDirBooleans();
		inAir = false;
		attacking = false;
		moving = false;
		playerAction = IDLE;
		currentHealth = maxHealth;

		hitbox.x = x;
		hitbox.y = y;

		if (!IsEntityOnFloor(hitbox, lvlData))
			inAir = true;

    }
    


}
