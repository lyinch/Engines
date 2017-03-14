package curiosity;

import core.DisplayManager;

import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

/**
 * Created by backes on 14/03/17.
 */
public class Main {
    public static void main(String[] args) {
        DisplayManager.createDisplay();
        
        while (!glfwWindowShouldClose(DisplayManager.window) ) {
            DisplayManager.update();
        }
        
    }
}
