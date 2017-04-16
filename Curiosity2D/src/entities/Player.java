package entities;

import core.DisplayManager;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.glfw.GLFW;
import textures.Texture;
import utils.Math;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by backes on 15/04/17.
 */
public class Player extends Entity{
    private boolean moving = false;
    private int frameDown = 0;
    public Player(Vector2f position, Vector2f rotation, float scale, Texture texture) {
        super(position, rotation, scale, texture);
    }
    
    public void move(){
        getCell();
        float speed = 0.01f;
        if (!moving) {
            if (glfwGetKey(DisplayManager.window, GLFW_KEY_D) == GLFW_TRUE) {
                this.addPosition(speed, 0);
            }
            if (glfwGetKey(DisplayManager.window, GLFW_KEY_A) == GLFW_TRUE) {
                this.addPosition(-speed, 0);
            }
            if (glfwGetKey(DisplayManager.window, GLFW_KEY_S) == GLFW_TRUE) {
                moveDown();
            }
            if (glfwGetKey(DisplayManager.window, GLFW_KEY_W) == GLFW_TRUE) {
                this.addPosition(0, speed);
            }
        }else
            moveDown();

    }
    
    private void moveDown(){
        moving = true;
        frameDown++;
        this.addPosition(0.01f,-0.01f);
        if (frameDown == 1) {
            moving = false;
            frameDown = 0;
        }
    }
    
    private void getCell(){
        float x = (2f*getPosition().x)/DisplayManager.WIDTH-1f;
        float y = 1-(2f*getPosition().y)/DisplayManager.getHEIGHT();
        Vector2f normalizedCoords = new Vector2f(x,y);
        Vector4f clipCoords = new Vector4f(normalizedCoords.x,normalizedCoords.y,-1f,1);
        //Vector4f eyeSpcae = toEyeSpace(clipCoords);

        System.out.println(clipCoords.x + " :" +  clipCoords.y);
        System.out.println(this.getPosition());
        Matrix4f transform = Math.createTransformationMatrix(this.getPosition(),this.getRotation(),1);
        transform.invert();
        Vector4f postmp = new Vector4f(getPosition().x,getPosition().y,1.0f,1.0f);
        postmp.mul(transform);
        //System.out.println(postmp);
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
