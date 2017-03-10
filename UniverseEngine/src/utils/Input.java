package utils;

import fsm.Events;
import fsm.Observer;
import fsm.Subject;
import renderer.DisplayManager;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.glfwGetMouseButton;

/**
 * Created by backes on 24/02/17.
 */
public class Input implements Subject{
    
    static List<Observer> observers = new ArrayList<>();
    
    public static float mouseX;
    public static float mouseY;
    public static float mouseDX;
    public static float mouseDY;
    
    public static float wheelY;
    public static float wheelDY;


    public Input() {
        
    }

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
        for (Observer o: observers){
            o.onNotify(Events.MOUSE_MOVED);
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

    @Override
    public void addObserver(Observer o) {
        this.observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        this.observers.remove(o);
    }
}
