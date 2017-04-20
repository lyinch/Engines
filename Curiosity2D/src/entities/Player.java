package entities;

import core.DisplayManager;
import org.joml.*;
import textures.Texture;
import tileMap.TileMap;

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
    
    public Player(Vector2f position, Vector2f rotation, float scale, Texture texture, TileMap tileMap) {
        super(position, rotation, scale, texture);
        this.tileMap = tileMap;
        downSpeed = 20;
        
        
        frameCap = 20;
        frameCount = 0;
        speedX = 0;
        speedY = 0;
        fallTime = 0;
    }
    
    public void move(){
        //System.out.println(getCurrentTile());
        float speedXBase = (128/(float)DisplayManager.WIDTH);
        float speedYBase = (128/(float)DisplayManager.HEIGHT);
        
        
        if (this.currentState == state.RESTING){
            pullDown();
            
            if (glfwGetKey(DisplayManager.window, GLFW_KEY_S) == GLFW_TRUE) {
                if (isValidMove(getCurrentTile().x, getCurrentTile().y+1)) {
                    this.currentState = state.MOVING;
                    setAnimDuration(getCurrentTile().x, getCurrentTile().y+1);
                    speedX += 0;
                    speedY += -speedYBase;
                    doMove();
                }
            }else if (glfwGetKey(DisplayManager.window, GLFW_KEY_D) == GLFW_TRUE) {
                if (isValidMove(getCurrentTile().x+1, getCurrentTile().y)) {
                    this.currentState = state.MOVING;
                    setAnimDuration(getCurrentTile().x+1, getCurrentTile().y);
                    speedX += speedXBase;
                    speedY += 0;
                    doMove();
                }
            }
            else if (glfwGetKey(DisplayManager.window, GLFW_KEY_W) == GLFW_TRUE) {
                if (isValidMove(getCurrentTile().x, getCurrentTile().y-1)) {
                    if (tileMap.getTileType(getCurrentTile().x, getCurrentTile().y-1) == 3) {
                        this.currentState = state.MOVING;
                        setAnimDuration(getCurrentTile().x , getCurrentTile().y-1);
                        speedX += 0;
                        speedY += speedYBase;
                        doMove();
                    }
                }
            }
        }else if(currentState == state.MOVING){
            doMove();
        }else{
            pullDown();
        }
        
        correctPlayerPosition();
        System.out.println(this.getPosition());
        
        
//        if (!moving) {
//            //grav();
//            if (glfwGetKey(DisplayManager.window, GLFW_KEY_D) == GLFW_TRUE) {
//                if (isValidMove(getCurrentTile().x+1,getCurrentTile().y)) {
//                    currentMoveX += speedX;
//                    currentMoveY += 0;
//                    animMove();
//                }
//                //this.addPosition(speedX, 0);
//            }
//            else if (glfwGetKey(DisplayManager.window, GLFW_KEY_A) == GLFW_TRUE) {
//                if (isValidMove(getCurrentTile().x-1,getCurrentTile().y)) {
//                    currentMoveX += -speedX;
//                    currentMoveY += 0;
//                    animMove();
//                }
//            }
//            else if (glfwGetKey(DisplayManager.window, GLFW_KEY_S) == GLFW_TRUE) {
//                if (isValidMove(getCurrentTile().x,getCurrentTile().y+1)) {
//                    setAnimDuration(getCurrentTile().x,getCurrentTile().y+1);
//                    currentMoveX += 0;
//                    currentMoveY += -speedY;
//                    currentMovement = direction.DOWN;
//                    animMove();
//                    //System.out.println("Down: " + getCurrentTile().x + ":" + (getCurrentTile().y+1));
//                }
//            }
//            else if (glfwGetKey(DisplayManager.window, GLFW_KEY_W) == GLFW_TRUE) {
//                if (isValidMove(getCurrentTile().x,getCurrentTile().y-1)) {
//                    currentMoveX += 0;
//                    currentMoveY += speedY;
//                    animMove();
//                    //tileMap.changeColour(getCurrentTile().x,getCurrentTile().y-1);
//                }
//            }
//        }else
//            animMove();
    }
    
    private void correctPlayerPosition(){
        if (this.getPosition().x < 0)
            this.setPosition(0.0f,this.getPosition().y);
        
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
            }else {
                this.currentState = state.RESTING;
                this.fallTime = 0;
            }
        }else{
            this.currentState = state.RESTING;
            this.fallTime = 0;
        }
    }
    
    private void doMove(){
        frameCount++;
        this.addPosition(speedX/frameCap,speedY/frameCap);
        if (frameCap == frameCount){
            this.frameCap = 1;
            this.currentState = state.RESTING;
            this.frameCount = 0;
            speedX = 0;
            speedY = 0;
            tileMap.consumeTile(getCurrentTile().x, getCurrentTile().y);
        }
    }
    
    private void grav(){
        if (isValidMove(getCurrentTile().x, getCurrentTile().y+1)){
            if (tileMap.getTileType(getCurrentTile().x, getCurrentTile().y+1) == 3){
                this.addPosition(0,-0.01f);
                
            }
        }
    }
    
    private void setAnimDuration(int x, int y){
        int type = tileMap.getTileType(x,y);
        switch (type){
            case 0: frameCap = 80; break;
            case 1: frameCap = 55; break;
            case 2: frameCap = 40; break;
            case 3: frameCap = 10; break;
        }
    }
    
    private boolean isValidMove(int x, int y){
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
        float posX = (getPosition().x+(PIXELS/(float)DisplayManager.WIDTH)/2)/(PIXELS/(float)DisplayManager.WIDTH);
        float posY = 1-(getPosition().y+((PIXELS-1)/(float)DisplayManager.HEIGHT))/(PIXELS/(float)DisplayManager.HEIGHT);
        //System.out.println("Current Tile: [" + (int)org.joml.Math.floor(posX) + " : " + (int)org.joml.Math.floor(posY) + "]");
        return new Vector2i((int)org.joml.Math.floor(posX),(int)org.joml.Math.floor(posY));
    }
    
    
}
