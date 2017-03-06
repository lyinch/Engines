package utils;

import renderer.DisplayManager;

import java.util.Observer;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.glfwGetMouseButton;

/**
 * Created by backes on 24/02/17.
 */
public class Input{
    
    public static float mouseX;
    public static float mouseY;
    public static float mouseDX;
    public static float mouseDY;
    
    public static float wheelY;
    public static float wheelDY;
    

    /**
     * The mouse position and the offset since the last position is saved.
     * @param x
     * @param y
     */
    public static void mouseMove(double x, double y){
        mouseDX = mouseX-(float)x;
        mouseDY = mouseY-(float)y;
        mouseX = (float)x;
        mouseY = (float)y;
        float normX = ((2f*Input.mouseX)/ DisplayManager.getWIDTH()-1f);
        float normY = 1f-((2f*Input.mouseY)/ DisplayManager.getHEIGHT());
        if (normX > 0.6f){
            //System.out.println("GUI");
            if (normX >= 0.7f && normX <= 0.9f){
                if (normY >= 0.6 && normY <= 0.7){
                    if (glfwGetMouseButton(DisplayManager.window,GLFW_MOUSE_BUTTON_LEFT) == GLFW_TRUE) {
                        System.out.println("button 1 pressed");
                    }
                }
                
            }
        }
    }

    /**
     * The mouse wheel movement and the offset since the last move is saved. We only register the common Y movement. 
     * @param y
     */
    public static void wheelMove(double y){
        wheelDY = wheelY-(float)y;
        wheelY = (float)y;
    }
    
    public static void keyPressed(int key){
        
    }
    
    public static boolean isPressed(int key){
        return false;
    }
}
