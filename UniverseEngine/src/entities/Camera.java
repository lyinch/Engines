package entities;


import org.joml.Vector3f;
import renderer.DisplayManager;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * Created by backes on 25/02/17.
 */
public class Camera {
    private Vector3f position = new Vector3f(0,0,0);
    private float pitch = 0; //high-low
    private float yaw = 0; //left-right
    private float roll = 0; //tilting 

    private boolean wire = false;
    
    public Camera(Vector3f position) {
        this.position = position;
    }

    /**
     * Moves the camera, gets called every frame
     */
    public void move(){
        float speed = 0.2f;
        if (glfwGetKey(DisplayManager.window,GLFW_KEY_W) == GLFW_TRUE) {
            position.z += -(float)Math.cos(Math.toRadians(yaw)) * speed;
            position.x += (float)Math.sin(Math.toRadians(yaw)) * speed;
        }
        if (glfwGetKey(DisplayManager.window,GLFW_KEY_D) == GLFW_TRUE) {
            position.z += (float)Math.sin(Math.toRadians(yaw)) * speed;
            position.x += (float)Math.cos(Math.toRadians(yaw)) * speed;
        }
        if (glfwGetKey(DisplayManager.window,GLFW_KEY_A) == GLFW_TRUE) {
            position.z -= (float)Math.sin(Math.toRadians(yaw)) * speed;
            position.x -= (float)Math.cos(Math.toRadians(yaw)) * speed;
        } 
        if (glfwGetKey(DisplayManager.window,GLFW_KEY_S) == GLFW_TRUE) {
            position.z -= -(float)Math.cos(Math.toRadians(yaw)) * speed;
            position.x -= (float)Math.sin(Math.toRadians(yaw)) * speed;
        }        
        if (glfwGetKey(DisplayManager.window,GLFW_KEY_E) == GLFW_TRUE) {
            position.y += speed;
        }       
        if (glfwGetKey(DisplayManager.window,GLFW_KEY_Q) == GLFW_TRUE) {
            position.y -= speed;
        }
        if (glfwGetKey(DisplayManager.window,GLFW_KEY_P) == GLFW_TRUE) {
            if (wire) {
                glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
                wire = false;
            }else {
                glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
                wire = true;
            }
        }



    }

    public Vector3f getPosition() {
        return position;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getRoll() {
        return roll;
    }
}
