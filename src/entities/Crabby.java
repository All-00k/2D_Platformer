package entities;

import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.*;
import static utilz.Constants.Direction.*;

import java.awt.Color;
import java.awt.Graphics;

import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;

import main.Game;



public class Crabby extends Enemy {

    // AttackBox
	private Rectangle2D.Float attackBox;
	private int attackBoxOffsetX;


    public Crabby(float x, float y) {
        super(x, y, CRABBY_WIDTH, CRABBY_HEIGHT, CRABBY);
        initHitbox(x, y,(int) (22 *Game.SCALE) ,(int)(19*Game.SCALE)); //actual size of crabby 22x19

        initAttackBox();
        
    
    }

    private void initAttackBox() {
       attackBox = new Rectangle2D.Float(x, y, (int) (82 /*30(left claw) +22 (crab)+30(right claw)*/ * Game.SCALE), (int) (19 * Game.SCALE)); //attack box will be biiger from left of crabby to right  of crabby
       attackBoxOffsetX = (int) (Game.SCALE*30);
    }

    public void update(int[][] lvlData, Player player){
        updateBehaviour(lvlData, player);
        updateAnimationTick();
        updateAttackBox();
        


    }

    private void updateAttackBox() {
		attackBox.x = hitbox.x - attackBoxOffsetX;
		attackBox.y = hitbox.y;

	}

    //this is for how enemy will patrol and its hitboxes checks and now also the attack /comabat mappign will be updated
    public void updateBehaviour(int[][] lvlData, Player player){
        if(firstUpdate)
            firstUpdateCheck(lvlData);


        if(inAir)//falling{}
           updateInAir(lvlData);
        

        else{//not in  Air
            switch (enemyState) {
                case IDLE:
                    newState(RUNNING);
                    break;
                case RUNNING:
                    if(canSeePlayer(lvlData, player))
                        turnTowards(player);
                    if(isPlayerCloseForAttack(player))
                        newState(ATTACK);

                    move(lvlData);
                
                    break;
                case ATTACK:
                    if(aniIndex ==0)
                        attackChecked = false;

                    if(aniIndex == 3 && !attackChecked) //so we will check attack once per animation index
                        checkEnemyHit(attackBox,player);
                        
                    break;
                case HIT:
                    break;

            }


        }

        

    }



    public void drawAttackBox(Graphics g, int xLvlOffset) {
		g.setColor(Color.red);
		g.drawRect((int) (attackBox.x - xLvlOffset), (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);
	}

    public int flipX(){
        if(walkDir == RIGHT)
            return width;
        else
            return 0;

    }

    public int flipW(){
        if(walkDir == RIGHT)
            return -1;
        else    
            return 1;

    }

    

    
    
    
}
