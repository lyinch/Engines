package entities;

import IO.Input;
import core.DisplayManager;
import org.joml.*;
import textures.Texture;
import tileMap.TileMap;

import java.lang.Math;

import static curiosity.World.HEIGHT;
import static curiosity.World.PIXELS;
import static curiosity.World.WIDTH;
import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by backes on 15/04/17.
 */
public class Player extends Entity{

    
    private boolean moving = false;
    private int frameDown = 0;
    private int downSpeed = 0;
    private float currentMoveX = 0;
    private float currentMoveY = 0;
    private TileMap tileMap;
    
    private int[] stats = new int[4];
    
    private enum direction  {UP,DOWN,LEFT,RIGHT,NONE};
    private direction currentMovement;


    //states
    private state currentState = state.RESTING;
    private enum state {FALLING,RESTING,MOVING}
    
    //movement
    private int frameCap;
    private int frameCount;
    private float speedX;
    private float speedY;
    private float fallTime;
    private float moveTime;
    
    public Player(Vector2f position, Vector2f rotation, float scale, Texture texture, TileMap tileMap) {
        super(position, rotation, scale, texture);
        this.tileMap = tileMap;
        downSpeed = 20;
        
        
        frameCap = 20;
        frameCount = 0;
        speedX = 0;
        speedY = 0;
        fallTime = 0;
        moveTime = 0;
    }
    
    public void move(){
        //System.out.println(getCurrentTile());
        float speedXBase = (PIXELS/(float)DisplayManager.WIDTH);
        float speedYBase = (PIXELS/(float)DisplayManager.HEIGHT);

        float posX = (getPosition().x) / (PIXELS / (float) DisplayManager.WIDTH);
        float posY = (getPosition().y-(1 / (float) DisplayManager.HEIGHT)) / (PIXELS / (float) DisplayManager.HEIGHT);
        
        float pixelMovementX = (4 / (float) DisplayManager.WIDTH);


        if (this.currentState == state.RESTING) {
            
            if (Input.isKeyPressed(GLFW_KEY_D) || Input.isKeyDown(GLFW_KEY_D)) {
                int X = (int) (Math.ceil(posX + pixelMovementX));
                int Y = (int) (Math.floor(Math.abs(posY)));
                if (isValidMove(X,Y)) {
                    if (tileMap.getTileType(X,Y) == 3) {
                        float speed = pixelMovementX + 0.001f*moveTime;
                        speed = utils.Math.clamp(speed,0.0f,pixelMovementX+0.009f);
                        this.addPosition(speed, 0);
                        moveTime++;
                    } else {
                        this.currentState = state.MOVING;
                        setAnimDuration(X, Y);
                        speedX += speedXBase;
                        speedY += 0;
                        doMove();
                        moveTime = 0;
                    }
                }
            }else if (Input.isKeyPressed(GLFW_KEY_A) || Input.isKeyDown(GLFW_KEY_A)) {
                int X = (int) (Math.floor(posX - pixelMovementX));
                int Y = (int) (Math.floor(Math.abs(posY)));
                if (isValidMove(X,Y)) {
                    if (tileMap.getTileType(X,Y) == 3) {
                        float dist = (float) Math.abs((Math.floor(X)*((PIXELS/(float)DisplayManager.WIDTH)))-this.getPosition().x);
                        //System.out.println(dist);
                        this.addPosition(-pixelMovementX, 0);
                        moveTime++;
                    } else {
                        moveTime = 0;
                        this.currentState = state.MOVING;
                        setAnimDuration(X, Y);
                        speedX += -speedXBase;
                        speedY += 0;
                        doMove();
                    }
                }
            }else if (Input.isKeyPressed(GLFW_KEY_S) || Input.isKeyDown(GLFW_KEY_S)) {
                int Y = (int) (Math.ceil(Math.abs(posY)));
                float X = (getPosition().x+((128)/(float)DisplayManager.WIDTH)/2)/(PIXELS/(float)DisplayManager.WIDTH);
                if (isValidMove((int)Math.floor(X), Y)) {
                    this.currentState = state.MOVING;
                    setAnimDuration((int)Math.floor(X), Y);
                    
                    speedX += (Math.floor(X)*((PIXELS/(float)DisplayManager.WIDTH)))-this.getPosition().x;
                    speedY += -speedYBase;
                    doMove();
                }
            }else if(Input.isKeyReleased(GLFW_KEY_D)){
                int X = (int) (Math.ceil(posX + pixelMovementX));
                int Y = (int) (Math.floor(Math.abs(posY)));
                
                if (isValidMove(X,Y)) {
                    if (tileMap.getTileType(X,Y) == 3) {
                        float dist = (float) Math.abs((Math.floor((int) (Math.ceil((getPosition().x) / (PIXELS / (float) DisplayManager.WIDTH) + pixelMovementX))) * ((PIXELS / (float) DisplayManager.WIDTH))) - this.getPosition().x);
                        float epsilon = 0.01f;
                        dist += pixelMovementX * 4 + moveTime *0.0001f;
                        
                        if (Math.abs(dist - 0.05) < epsilon) {
                            System.out.println("Movement corrected Center");
                            this.currentState = state.MOVING;
                            frameCap = 5;
                            speedX += pixelMovementX * 6 + moveTime * 0.0001f;
                            speedY += 0;
                            doMove();
                            //this.addPosition(pixelMovementX*2, 0);
                        }else{
                            System.out.println("Movement corrected");
                            this.currentState = state.MOVING;
                            frameCap = 5;
                            speedX += pixelMovementX * 4 + moveTime * 0.0001f;
                            speedY += 0;
                            doMove();
                        }
                    }
                }
                
  
                moveTime = 0;
            }else if(Input.isKeyReleased(GLFW_KEY_A)){
                moveTime = 0;
            }
//            pullDown();
//            
//            if (glfwGetKey(DisplayManager.window, GLFW_KEY_S) == GLFW_TRUE) {
//                if (isValidMove(getCurrentTile().x, getCurrentTile().y+1)) {
//                    this.currentState = state.MOVING;
//                    setAnimDuration(getCurrentTile().x, getCurrentTile().y+1);
//                    speedX += 0;
//                    speedY += -speedYBase;
//                    doMove();
//                }
//            }else if (glfwGetKey(DisplayManager.window, GLFW_KEY_D) == GLFW_TRUE) {
//                if (isValidMove(getCurrentTile().x+1, getCurrentTile().y)) {
//                    this.currentState = state.MOVING;
//                    setAnimDuration(getCurrentTile().x+1, getCurrentTile().y);
//                    speedX += speedXBase;
//                    speedY += 0;
//                    this.currentState = state.RESTING;
//                    doMove();
//                }
//            }else if (glfwGetKey(DisplayManager.window, GLFW_KEY_A) == GLFW_TRUE) {
//                if (isValidMove(getCurrentTile().x-1, getCurrentTile().y)) {
//                    this.currentState = state.MOVING;
//                    setAnimDuration(getCurrentTile().x-1, getCurrentTile().y);
//                    speedX += -speedXBase;
//                    speedY += 0;
//                    doMove();
//                }
//            }else if (glfwGetKey(DisplayManager.window, GLFW_KEY_W) == GLFW_TRUE) {
//                if (isValidMove(getCurrentTile().x, getCurrentTile().y-1)) {
//                    if (tileMap.getTileType(getCurrentTile().x, getCurrentTile().y-1) == 3) {
//                        this.currentState = state.MOVING;
//                        setAnimDuration(getCurrentTile().x , getCurrentTile().y-1);
//                        speedX += 0;
//                        speedY += speedYBase;
//                        doMove();
//                    }
//                }
//            }
            
        }else if(currentState == state.MOVING){
            doMove();
        }else {
//            pullDown();
//            //noinspection Duplicates
//            if (glfwGetKey(DisplayManager.window, GLFW_KEY_W) == GLFW_TRUE) {
//                if (isValidMove(getCurrentTile().x, getCurrentTile().y - 1)) {
//                    if (tileMap.getTileType(getCurrentTile().x, getCurrentTile().y - 1) == 3) {
//                        this.currentState = state.MOVING;
//                        setAnimDuration(getCurrentTile().x, getCurrentTile().y - 1);
//                        speedX += 0;
//                        speedY += speedYBase;
//                        doMove();
//                    }
//                }
//            }
        }
        
        correctPlayerPosition();
    }
    
    private void correctPlayerPosition(){
        if (this.getPosition().x < 0)
            this.setPosition(0.0f,this.getPosition().y);

        float xMax = (WIDTH*(PIXELS/(float)DisplayManager.getWIDTH())) - (PIXELS/(float)DisplayManager.WIDTH);
        //System.out.println(this.getPosition().x + " : " + xMax);
        if (this.getPosition().x > xMax)
            this.setPosition(xMax,this.getPosition().y);
        
        
        float yMax = -(HEIGHT*(PIXELS/(float)DisplayManager.getHEIGHT()))+(PIXELS/(float)DisplayManager.HEIGHT);
        //System.out.println(yMax);
        if (this.getPosition().y < yMax)
            this.setPosition(this.getPosition().x,yMax);

    }
    
    private void pullDown(){
        float speedYBase = (128/(float)DisplayManager.HEIGHT);
        if (isValidMove(getCurrentTile().x, getCurrentTile().y+1)){
            if (tileMap.getTileType(getCurrentTile().x, getCurrentTile().y+1) == 3) {
                speedX += 0;
                speedY += -speedYBase/100 - fallTime*0.0001f;
                doMove();
                this.currentState = state.FALLING; 
                fallTime++;
                //System.out.println("pulling");

            }else {
                this.currentState = state.RESTING;
                if (fallTime != 0)
                    System.out.println("Falltime:" + fallTime/60.0f);
                this.fallTime = 0;
            }
        }else{
            this.currentState = state.RESTING;
            if (fallTime != 0)
                System.out.println("Falltime:" + fallTime/60.0f);
            this.fallTime = 0;
        }
    }
    
    private void doMove(){
        frameCount++;
        this.addPosition(speedX/frameCap,speedY/frameCap);
        if (frameCap == frameCount){
            this.frameCap = 1; //set framecap to 1 for gravity
            this.currentState = state.RESTING;
            this.frameCount = 0;
            speedX = 0;
            speedY = 0;
            tileMap.consumeTile(getCurrentTile().x, getCurrentTile().y);
        }
    }

    private void setAnimDuration(int x, int y){
        int type = tileMap.getTileType(x,y);
        switch (type){
            case 0: frameCap = 80; break;
            case 1: frameCap = 55; break;
            case 2: frameCap = 40; break;
            case 3: frameCap = 20; break;
        }
    }
    
    private boolean isValidMove(float x, float y){
        //System.out.println(x);
        return !(x < 0 || x > WIDTH-1 || y < 0 || y > HEIGHT-1);
    }
    
    private void animMove(){
        moving = true;
        frameDown++;
        this.addPosition(currentMoveX/downSpeed,currentMoveY/downSpeed);
        if (frameDown == downSpeed){
            moving = false;
            currentMoveY = 0;
            currentMoveX = 0;
            frameDown = 0;
            downSpeed = 20;
            if (currentMovement == direction.DOWN) {
                stats[tileMap.getTileType(getCurrentTile().x, getCurrentTile().y)]++;
                tileMap.consumeTile(getCurrentTile().x, getCurrentTile().y);
                String str = "";
                for (int i = 0; i < stats.length; i++){
                    str+= "("+i+")" + ": " + stats[i] + " ";
                }
                System.out.print("\r" + str);
            }
            currentMovement = direction.NONE;
        }
    }
    
    private Vector2i getCurrentTile(){
        float posX = (getPosition().x+((128)/(float)DisplayManager.WIDTH)/2)/(PIXELS/(float)DisplayManager.WIDTH);
        //System.out.println(posX + ":" + ((int)(Math.ceil(posX))-1) + ":"+ getPosition().x);
        float posY = 1-(getPosition().y+((1)/(float)DisplayManager.HEIGHT))/(PIXELS/(float)DisplayManager.HEIGHT);
        //System.out.println("Current Tile: [" + (int)org.joml.Math.floor(posX) + " : " + (int)org.joml.Math.floor(posY) + "]");
        
        return new Vector2i((int)(Math.floor(posX)),(int)org.joml.Math.floor(posY));
    }
    
    
}
