package utilz;

import java.awt.geom.Rectangle2D;



import main.Game;

public class HelpMethods {

    public static boolean CanMoveHere(float x, float y, float width , float height, int[][] lvlData){
        if (!IsSoild(x, y, lvlData)){ //checking for whole top left coroner
            if(!IsSoild(x+width, y+height, lvlData)){ //for bottom right corner
                if(!IsSoild(x+width, y, lvlData)){
                    if(!IsSoild(x, y+height, lvlData)){
                        return true;
                    }
                }

            }

        }
        return false;

    }

    private static boolean IsSoild(float x, float y, int[][] lvlData){
        int maxWidth = lvlData[0].length * Game.TILES_SIZE; // to get the extreme end of the level data or endpoint
        if(x<0 || x >= maxWidth) //we need to set farest variable to actuall where our level is ending not on the visble end
            return true;
        if(y<0 || y >= Game.GAME_HEIGHT)
            return true;

        float xIndex = x / Game.TILES_SIZE; //xIndex gives at which tile you are my taking input as coordinates of x
        float yIndex = y /Game.TILES_SIZE;

        return IsTileSolid((int) xIndex, (int) yIndex, lvlData);
    }

    public static boolean IsTileSolid(int xTile , int yTile, int[][] lvlData){ //this method we made to use in IsSightClear so that  enemy can approach the player if no solid boxes are in between
             int value = lvlData[(int) (yTile)][(int) (xTile)];

             if(value>=48 || value <0 || value !=11) { //index 11 have no sprites means no void space(non solid space) and checks for >=48 and <0 is just for out of bound checkss
                return true; //checks the array of level sprites is out of bound or not 

            }
            return false;
    }


    public static float  GetEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed){
        int currentTile = (int)(hitbox.x/Game.TILES_SIZE);
        if(xSpeed>0){
            //right side collision
            int tileXPos = currentTile * Game.TILES_SIZE;
            int xOffset = (int) (Game.TILES_SIZE - hitbox.width);
            return tileXPos+xOffset -1;

        }
        else{
            //left side collision
            return currentTile *Game.TILES_SIZE;
        }
    }

    public static float GetEntityYPosUnderRoofOrAboveTheFloor(Rectangle2D.Float hitbox,Float airSpeed){
        int currentTile = (int)(hitbox.y/Game.TILES_SIZE);
        if(airSpeed>0){//we going down(falling)
            int tileYPos = currentTile * Game.TILES_SIZE; //tilepos in pixels verticallyy
            int yOffset = (int) (Game.TILES_SIZE - hitbox.height);
            return tileYPos+yOffset -1;

        }
        else{
            //jumping (going up)
            return currentTile* Game.TILES_SIZE;
        }
    }

    public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int[][] lvlData){
        //check the pixel below bottom left and bottom right
        if(!IsSoild(hitbox.x, hitbox.y+ hitbox.height+1, lvlData)) //if in air it goes to nested if
            if(!IsSoild(hitbox.x +hitbox.width, hitbox.y+ hitbox.height+1, lvlData))
                return false;
            
        return true;
        

    }

    public static boolean IsFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] lvlData){
        return IsSoild(hitbox.x +xSpeed, hitbox.y + hitbox.height +1, lvlData);
    }


    
    public static boolean IsAllTilesWalkable(int xStart, int xEnd, int y,int[][] lvlData){
        for (int i = 0; i < xEnd - xStart; i++) {
			if (IsTileSolid(xStart + i, y, lvlData))
				return false;
			if (!IsTileSolid(xStart + i, y + 1, lvlData))
				return false;
		}

		return true;

    }

    public static boolean IsSightClear(int[][] lvlData, Rectangle2D.Float firstHitbox , Rectangle2D.Float secondHitox, int yTile){
        int firstXTile = (int) (firstHitbox.x / Game.TILES_SIZE);
        int secondXTile = (int) (secondHitox.x / Game.TILES_SIZE);

        if(firstXTile > secondXTile){
            return IsAllTilesWalkable(secondXTile,firstXTile,  yTile, lvlData);

            
        }
        else{
             return IsAllTilesWalkable(firstXTile, secondXTile, yTile, lvlData);
            
        }


    }


    
}
