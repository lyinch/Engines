package entities;

import core.DisplayManager;
import curiosity.World;
import org.joml.*;
import org.lwjgl.glfw.GLFW;
import textures.Texture;
import tileMap.TileMap;
import utils.Math;

import java.util.Random;

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
    public Player(Vector2f position, Vector2f rotation, float scale, Texture texture, TileMap tileMap) {
        super(position, rotation, scale, texture);
        this.tileMap = tileMap;
        downSpeed = 20;
    }
    
    public void move(){
        //System.out.println(getCurrentCell());
        float speedX = (128/(float)DisplayManager.WIDTH);
        float speedY = (128/(float)DisplayManager.HEIGHT);

        if (!moving) {
            //grav();
            if (glfwGetKey(DisplayManager.window, GLFW_KEY_D) == GLFW_TRUE) {
                if (isValidMove(getCurrentCell().x+1,getCurrentCell().y)) {
                    currentMoveX += speedX;
                    currentMoveY += 0;
                    animMove();
                }
                //this.addPosition(speedX, 0);
            }
            else if (glfwGetKey(DisplayManager.window, GLFW_KEY_A) == GLFW_TRUE) {
                if (isValidMove(getCurrentCell().x-1,getCurrentCell().y)) {
                    currentMoveX += -speedX;
                    currentMoveY += 0;
                    animMove();
                }
            }
            else if (glfwGetKey(DisplayManager.window, GLFW_KEY_S) == GLFW_TRUE) {
                if (isValidMove(getCurrentCell().x,getCurrentCell().y+1)) {
                    setCellSpeed(getCurrentCell().x,getCurrentCell().y+1);
                    currentMoveX += 0;
                    currentMoveY += -speedY;
                    currentMovement = direction.DOWN;
                    animMove();
                    //System.out.println("Down: " + getCurrentCell().x + ":" + (getCurrentCell().y+1));
                }
            }
            else if (glfwGetKey(DisplayManager.window, GLFW_KEY_W) == GLFW_TRUE) {
                if (isValidMove(getCurrentCell().x,getCurrentCell().y-1)) {
                    currentMoveX += 0;
                    currentMoveY += speedY;
                    animMove();
                    //tileMap.changeColour(getCurrentCell().x,getCurrentCell().y-1);
                }
            }
        }else
            animMove();
    }
    
    private void grav(){
        if (isValidMove(getCurrentCell().x,getCurrentCell().y+1)){
            if (tileMap.getTileType(getCurrentCell().x,getCurrentCell().y+1) == 3){
                this.addPosition(0,-0.01f);
                
            }
        }
    }
    
    private void setCellSpeed(int x, int y){
        int type = tileMap.getTileType(x,y);
        switch (type){
            case 0: downSpeed = 80; break;
            case 1: downSpeed = 55; break;
            case 2: downSpeed = 40; break;
            case 3: downSpeed = 10; break;
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
                stats[tileMap.getTileType(getCurrentCell().x, getCurrentCell().y)]++;
                tileMap.consumeTile(getCurrentCell().x, getCurrentCell().y);
                String str = "";
                for (int i = 0; i < stats.length; i++){
                    str+= "("+i+")" + ": " + stats[i] + " ";
                }
                System.out.print("\r" + str);
            }
            currentMovement = direction.NONE;
        }
    }
    
    private Vector2i getCurrentCell(){
        float posX = (getPosition().x+(PIXELS/(float)DisplayManager.WIDTH)/2)/(PIXELS/(float)DisplayManager.WIDTH);
        float posY = 1-(getPosition().y+(PIXELS/(float)DisplayManager.HEIGHT)/2)/(PIXELS/(float)DisplayManager.HEIGHT);
        //System.out.println("Current Tile: [" + (int)org.joml.Math.floor(posX) + " : " + (int)org.joml.Math.floor(posY) + "]");
        return new Vector2i((int)org.joml.Math.floor(posX),(int)org.joml.Math.floor(posY));
    }
    
    
}
