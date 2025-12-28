package entities;

import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.*;

import java.awt.geom.Rectangle2D;


import static utilz.Constants.Direction.*;
import gamestates.State;

import main.Game;
public abstract class Enemy extends Entitiy {

    protected int aniIndex, enemyState, enemyType;
    protected int aniTick, aniSpeed=25;
    protected boolean firstUpdate =true;
    protected boolean inAir;
    protected float fallSpeed;
    protected float gravity = 0.04f * Game.SCALE;
    protected float walkSpeed = 0.35f * Game.SCALE;
    protected int walkDir = LEFT;
    protected int tileY;
    protected float attackDistance = Game.TILES_SIZE;
    protected int maxHealth;
    protected int currentHealth;
    protected boolean active = true;
    protected boolean attackChecked;



    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;
        initHitbox(x, y, width, height);
        maxHealth = GetMaxHealth(enemyType);
        currentHealth = maxHealth;
        
    }


    protected void firstUpdateCheck(int[][] lvlData) {
		if (!IsEntityOnFloor(hitbox, lvlData))
			inAir = true;
		firstUpdate = false;
	}

    protected void updateInAir(int[][] lvlData) {
         if(CanMoveHere(hitbox.x, hitbox.y +fallSpeed, hitbox.width, hitbox.height, lvlData)){
                hitbox.y +=fallSpeed;
                fallSpeed += gravity;
            }
            else{ //else will trigger when we are at enough height from ground and we have to avoid to fall inside the ground
                inAir = false;
                hitbox.y = GetEntityYPosUnderRoofOrAboveTheFloor(hitbox, fallSpeed);
                tileY = (int) (hitbox.y / Game.TILES_SIZE);//this is the level at which the enemy will always be
            }
    
    }

    protected void move(int[][] lvlData){
         float xSpeed =0;
                    if(walkDir == LEFT)
                        xSpeed = -walkSpeed;
                    
                    else
                        xSpeed = walkSpeed;
                    

                    if(CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)){ //hitbox.x+xSpeed because we are checking wheather  we are able to move beyond hitbox.x
                        if(IsFloor(hitbox, xSpeed,lvlData)){
                            hitbox.x += xSpeed;
                            return;
                        }
                    }
                    changeWalkDir();

    }

    //change enemy moving direction so that it can approch the player
    protected void turnTowards(Player player ){
        if(player.hitbox.x > hitbox.x){
            walkDir =RIGHT;
            
        }
        else   
            walkDir =LEFT;
    }



    protected boolean canSeePlayer(int[][] lvlData, Player player){
        int playerTileY = (int) (player.getHitbox().y /Game.TILES_SIZE); //this variable holds verticle tile of the enemy hitbox
        if(playerTileY== tileY)
            if(isPlayerInRange(player)){
                if(IsSightClear(lvlData,hitbox,player.hitbox, tileY))
                    return true;

            }
        return false;

    }

    protected boolean isPlayerInRange(Player player) {
        int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);
        return absValue <= attackDistance*5;
    }


    protected boolean isPlayerCloseForAttack(Player player){
        int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);
        return absValue <= attackDistance;
    }

    


    protected void newState(int enemyState) {
		this.enemyState = enemyState;
		aniTick = 0;
		aniIndex = 0;
	}


    public void hurt(int amount){
        currentHealth -= amount;
        if(currentHealth <=0)
            newState(DEAD);
        else 
            newState(HIT);
        
    }


    //enemy attacked the player 
    protected void checkEnemyHit(Rectangle2D.Float attackBox,Player player) {
        if(attackBox.intersects(player.hitbox))
            player.changeHealth(-GetEnemyDamage(enemyType)); //players heatlth is changed
        attackChecked = true;

        


        
    }
    
   protected void updateAnimationTick() {
		aniTick++;
		if (aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= GetSpriteAmount(enemyType, enemyState)) {
				aniIndex = 0;

                switch(enemyState){
                    case ATTACK,HIT -> enemyState=IDLE;
                    case DEAD -> active = false;
                }

   	
			}
		}
	}
    
    
    

    protected void changeWalkDir() {
        if(walkDir == LEFT)
            walkDir = RIGHT;

        
        else
            walkDir = LEFT;

        
    }

    public void resetEnemy(){
        hitbox.x = x;
        hitbox.y =y;
        firstUpdate = true;
        currentHealth =maxHealth;
        newState(IDLE);
        active = true;
        fallSpeed = 0;

    }

    public int getAniIndex(){
        return aniIndex;

    }

    public int getEnemyState(){
        return enemyState;
    }


    public boolean isActive(){
        return active;
    }
}
