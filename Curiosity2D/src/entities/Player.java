package entities;

import core.DisplayManager;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.glfw.GLFW;
import textures.Texture;
import utils.Math;

import java.util.Random;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by backes on 15/04/17.
 */
public class Player extends Entity{
    private boolean moving = false;
    private int frameDown = 0;
    
    private float currentMoveX = 0;
    private float currentMoveY = 0;
    
    public Player(Vector2f position, Vector2f rotation, float scale, Texture texture) {
        super(position, rotation, scale, texture);
    }
    
    public void move(){
        getCell();
        float speedX = (128/(float)DisplayManager.WIDTH);
        float speedY = (128/(float)DisplayManager.HEIGHT);

        if (!moving) {
            if (glfwGetKey(DisplayManager.window, GLFW_KEY_D) == GLFW_TRUE) {
                currentMoveX += speedX;
                currentMoveY += 0;
                animMove();
                //this.addPosition(speedX, 0);
            }
            else if (glfwGetKey(DisplayManager.window, GLFW_KEY_A) == GLFW_TRUE) {
                currentMoveX += -speedX;
                currentMoveY += 0;
                animMove();
            }
            else if (glfwGetKey(DisplayManager.window, GLFW_KEY_S) == GLFW_TRUE) {
                currentMoveX += 0;
                currentMoveY += -speedY;
                animMove();
            }
            else if (glfwGetKey(DisplayManager.window, GLFW_KEY_W) == GLFW_TRUE) {
                currentMoveX += 0;
                currentMoveY += speedY;
                animMove();
            }
        }else
            animMove();

    }
    
    private void animMove(){
        int speed = 20;
        moving = true;
        frameDown++;
        this.addPosition(currentMoveX/speed,currentMoveY/speed);
        if (frameDown == speed){
            moving = false;
            currentMoveY = 0;
            currentMoveX = 0;
            frameDown = 0;
        }
    }
    
    private void moveDown(){
        moving = true;
        frameDown++;
        this.addPosition(0,-0.002f);
        if (frameDown == 1) {
            moving = false;
            frameDown = 0;
        }
    }
    
    public void moveSpecial(){
        Random random = new Random();
        int num = random.nextInt(4);
        float speedX = (128/(float)DisplayManager.WIDTH);
        float speedY = (128/(float)DisplayManager.HEIGHT);

        switch (num){
            case 0: this.addPosition(0, speedY); break;
            case 1: this.addPosition(0, -speedY); break;
            case 2: this.addPosition(speedX, 0); break;
            case 3: this.addPosition(-speedX, 0); break;

        }
    }
    
    private void getCell(){
        
        float posX = (getPosition().x+(128/(float)DisplayManager.WIDTH)/2)/(128/(float)DisplayManager.WIDTH);
        float posY = 1-(getPosition().y+(128/(float)DisplayManager.HEIGHT)/2)/(128/(float)DisplayManager.HEIGHT);
        System.out.println("Current Tile: [" + (int)org.joml.Math.floor(posX) + " : " + (int)org.joml.Math.floor(posY) + "]");

    }

    /**
     * We invert the view Matrix, i.e. the Camera
     * @param eyeCoords
     * @return
     */
    private Vector3f toWorldSpace(Vector4f eyeCoords){
        Matrix4f invertedView = new Matrix4f();
        //viewMatrix.invert(invertedView);
        Vector4f rayWorld = invertedView.transform(eyeCoords);
        return new Vector3f(rayWorld.x,rayWorld.y,rayWorld.z).normalize();
    }
    
}
