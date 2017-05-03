package entities;

import IO.Input;
import core.DisplayManager;
import org.joml.*;
import textures.Texture;
import tileMap.TileMap;

import java.lang.Math;
import java.math.BigInteger;

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
    private boolean consumeTile;
    
    //Physics 2D
    Vector2f acceleration;
    Vector2f velocity;
    Vector2f speedInit;

    boolean moved = false;


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
        consumeTile = false;
        
        //
        acceleration = new Vector2f(0,0);
        velocity = new Vector2f(0,0);
        speedInit = new Vector2f(0.001f,0.001f);
    }

    public void move(){
        float speedXBase = (PIXELS/(float)DisplayManager.WIDTH);
        float speedYBase = (PIXELS/(float)DisplayManager.HEIGHT);

        float posX = (getPosition().x) / (PIXELS / (float) DisplayManager.WIDTH);
        float posY = (getPosition().y-(1 / (float) DisplayManager.HEIGHT)) / (PIXELS / (float) DisplayManager.HEIGHT);

        float pixelMovementX = (4 / (float) DisplayManager.WIDTH);
        
        if (Input.isKeyPressed(GLFW_KEY_D)) {
            int X = (int) (Math.ceil(posX + pixelMovementX));
            int Y = (int) (Math.floor(Math.abs(posY)));
            if (isValidMove(X,Y)) {
                if (tileMap.getTileType(X,Y) == 3) {
                    System.out.println("D Pressed");
                    this.acceleration.x += 0.001f;
                    moved = true;
                }
                
            }
        }else if (Input.isKeyPressed(GLFW_KEY_A)) {
            int X = (int) (Math.floor(posX - pixelMovementX));
            int Y = (int) (Math.floor(Math.abs(posY)));
            if (isValidMove(X, Y)) {
                if (tileMap.getTileType(X, Y) == 3) {
                    System.out.println("A Pressed");
                    this.acceleration.x -= 0.001f;
                    moved = true;
                }
            }
        }else if (Input.isKeyReleased(GLFW_KEY_D)){
            if (moved) {
                System.out.println("D Released");
                this.acceleration.x -= 0.001f;
                moved = false;
            }
        }else if (Input.isKeyReleased(GLFW_KEY_A)){
            if (moved) {
                System.out.println("A Released");
                this.acceleration.x += 0.001f;
                moved = false;
            }
        }
        
        this.velocity.add(acceleration);

        
        //acceleration.x = utils.Math.clampDown(acceleration.x,0f);

        //add friction
        if (velocity.x < 0) {
            velocity.x = org.joml.Math.min(0,velocity.x+0.0007f);
        }
        else if (velocity.x > 0) {
            velocity.x = org.joml.Math.max(0,velocity.x-0.0007f);
        }
        
        //velocity.mul(0.93f);
        //velocity.x = utils.Math.clamp(velocity.x,-0.02f,0.02f);
//        float epsilon = 0.0008f;
//        if (Math.abs(velocity.x) <= epsilon)
//            velocity.x = 0;

        //System.out.println("Acceleration : " + acceleration + " Velocity: "  + velocity);
        this.addPosition(velocity.x,0);
            
        correctPlayerPosition();
            
            
    }
    
    
    /**
     * Input handling of the player. 
     */
    public void move_OLD(){
        //System.out.println(getCurrentTile());
        float speedXBase = (PIXELS/(float)DisplayManager.WIDTH);
        float speedYBase = (PIXELS/(float)DisplayManager.HEIGHT);

        float posX = (getPosition().x) / (PIXELS / (float) DisplayManager.WIDTH);
        float posY = (getPosition().y-(1 / (float) DisplayManager.HEIGHT)) / (PIXELS / (float) DisplayManager.HEIGHT);
        
        float pixelMovementX = (4 / (float) DisplayManager.WIDTH);


        if (this.currentState == state.RESTING) {
            
            //Normal movement behaviour
            if (Input.isKeyPressed(GLFW_KEY_D) || Input.isKeyDown(GLFW_KEY_D)) {
                int X = (int) (Math.ceil(posX + pixelMovementX));
                int Y = (int) (Math.floor(Math.abs(posY)));
                if (isValidMove(X,Y)) {
                    if (tileMap.getTileType(X,Y) == 3) {
                        float speed = pixelMovementX + 0.0002f*moveTime+(float)Math.pow(0.00009f*moveTime,2);
                        //rSystem.out.print("Speed: " + velocity);
                        speed = utils.Math.clamp(speed,0.0f,0.029f);
                        //System.out.println(" Speed Corrected: " + velocity);
                        this.addPosition(speed, 0);
                        moveTime++;
                    } else {
                        this.currentState = state.MOVING;
                        setAnimDuration(X, Y);
                        speedX += speedXBase;
                        speedY += 0;
                        consumeTile = true;
                        doMove();
                        moveTime = 0;
                    }
                }
            }else if (Input.isKeyPressed(GLFW_KEY_A) || Input.isKeyDown(GLFW_KEY_A)) {
                int X = (int) (Math.floor(posX - pixelMovementX));
                int Y = (int) (Math.floor(Math.abs(posY)));
                if (isValidMove(X,Y)) {
                    if (tileMap.getTileType(X,Y) == 3) {
                        float speed = pixelMovementX + 0.0002f*moveTime+(float)Math.pow(0.00009f*moveTime,2);
                        speed = utils.Math.clamp(speed,0.0f,0.029f);

                        float dist = (float) Math.abs((Math.floor(X)*((PIXELS/(float)DisplayManager.WIDTH)))-this.getPosition().x);
                        //System.out.println(dist);
                        this.addPosition(-speed, 0);
                        moveTime++;
                    } else {
                        moveTime = 0;
                        this.currentState = state.MOVING;
                        setAnimDuration(X, Y);
                        speedX += -speedXBase;
                        speedY += 0;
                        consumeTile = true;
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
                    consumeTile = true;
                    doMove();
                }
                //Key released behaviour, "rolls out" the player, corrects movement to not stop between two tiles
            }else if(Input.isKeyReleased(GLFW_KEY_D)){
//                int X = (int) (Math.ceil(posX + pixelMovementX));
//                int Y = (int) (Math.floor(Math.abs(posY)));
//                
//                if (isValidMove(X,Y)) {
//                    if (tileMap.getTileType(X,Y) == 3) {
//                        float dist = (float) Math.abs((Math.floor((int) (Math.ceil((getPosition().x) / (PIXELS / (float) DisplayManager.WIDTH) + pixelMovementX))) * ((PIXELS / (float) DisplayManager.WIDTH))) - this.getPosition().x);
//                        float epsilon = 0.01f;
//                        dist += pixelMovementX * 4 + moveTime *0.001f;
//                        System.out.println(dist);
//                        dist = utils.Math.clamp(dist,0.0f,pixelMovementX * 4 + 0.009f);
//                        System.out.println(dist);
//                        if (Math.abs(dist - 0.05) < epsilon) {
//                            System.out.println("Movement corrected Center");
//                            this.currentState = state.MOVING;
//                            frameCap = 5;
//                            speedX += pixelMovementX * 6 + moveTime * 0.001f;
//                            speedX = 0;
//
//                            speedY += 0;
//                            consumeTile = false;
//                            doMove();
//                            //this.addPosition(pixelMovementX*2, 0);
//                        }else{
//                            System.out.println("Movement corrected");
//                            this.currentState = state.MOVING;
//                            frameCap = 5;
//                            speedX += pixelMovementX * 4 + moveTime * 0.001f;
//                            speedX = 0;
//                            speedY += 0;
//                            consumeTile = false;
//                            doMove();
//                        }
//                    }
//                }
                
                moveTime = 0;
            }else if(Input.isKeyReleased(GLFW_KEY_A)){
                moveTime = 0;
            }
           pullDown();
//            
            
        }else if(currentState == state.MOVING){
            doMove();
        }else {
            pullDown();
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

    /**
     * Corrects the players X and Y coordinates, i.e. clamps them to 0 and max tile size
     */
    private void correctPlayerPosition(){
        float x = this.getPosition().x;
        float y = this.getPosition().y;
        
        float xMax = (WIDTH*(PIXELS/(float)DisplayManager.getWIDTH())) - (PIXELS/(float)DisplayManager.WIDTH);
        float yMax = -(HEIGHT*(PIXELS/(float)DisplayManager.getHEIGHT()))+(PIXELS/(float)DisplayManager.HEIGHT);

        if (x < 0f)
            velocity.x = 0;

        x = utils.Math.clamp(x,0.0f,xMax);
        y = utils.Math.clamp(y,yMax,0.0f);
        


        this.setPosition(x,y);

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

    /**
     * Move animation of the player, static boolean 'consumeTile' defines whether the tile colour and type are updated
     */
    private void doMove(){
        frameCount++;
        this.addPosition(speedX/frameCap,speedY/frameCap);
        if (frameCap == frameCount){
            this.frameCap = 1; //set framecap to 1 for gravity
            this.currentState = state.RESTING;
            this.frameCount = 0;
            speedX = 0;
            speedY = 0;
            if (consumeTile)
                tileMap.consumeTile(getCurrentTile().x, getCurrentTile().y);
        }
    }

    /**
     * Sets the duration of the animated move based on tile (x,y coords of player). High framecap is slow animation
     * @param x The x coordinate of the player
     * @param y he y coordinate of the player
     */
    private void setAnimDuration(int x, int y){
        int type = tileMap.getTileType(x,y);
        switch (type){
            case 0: frameCap = 80; break;
            case 1: frameCap = 55; break;
            case 2: frameCap = 40; break;
            case 3: frameCap = 20; break;
        }
    }

    /**
     * Checks if the given x,y tile coordinates are valid
     * @param x The x tile coordinate
     * @param y The y tile coordinate
     * @return boolean
     */
    private boolean isValidMove(float x, float y){
        //System.out.println(x);
        return !(x < 0 || x > WIDTH-1 || y < 0 || y > HEIGHT-1);
    }
    
    @Deprecated
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
