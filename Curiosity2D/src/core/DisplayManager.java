package core;

import IO.Input;
import org.lwjgl.opengl.GL;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Created by backes on 14/03/17.
 */
public class DisplayManager {
    public static long window;
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    private static ArrayList<Integer> keyList = new ArrayList<>();
    /**
     * The window is initialized
     */
    public static void createDisplay(){
        if ( !glfwInit() )
            throw new RuntimeException("Unable to initialize GLFW!");

        setOpenGLCoreProfile();

        window = glfwCreateWindow(WIDTH, HEIGHT, "Hello World!", NULL, NULL);

        if (window == NULL)
            throw new RuntimeException("Unable to create a new Window!");
        

        
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(window, true);
        });
        



        glfwMakeContextCurrent(window);

        //activates vsync
        glfwSwapInterval(1);

        glfwShowWindow(window);
        GL.createCapabilities();
        glClearColor(0, 0, 0, 0.0f);

    }

    /**
     * The OS (such as OSX) is forced to use the core profile, in order to use an openGL version > 2 to use GLSL < 1.3
     */
    private static void setOpenGLCoreProfile(){
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
    }
    

    /**
     * updates the display, swaps the buffer and pulls the events
     */
    public static void update(){
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT); // clear the framebuffer
        glfwPollEvents();

    }

    public static int getWIDTH() {
        return WIDTH;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }
    

    /**
     * Registers a single key
     * @param keycode
     */
    public static void registerKey(int keycode){
        keyList.add(keycode);
    }

    /**
     * Registers a list of keys to handle the input
     * @param keycodes
     */
    public static void registerKey(ArrayList keycodes){
        keyList.addAll(keycodes);
    }

    /**
     * Creates a callback for every registered key
     */
    public static void startKeyCallback(){
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(window, true);
            for (Integer k:keyList){
                if (key == k && action == GLFW_RELEASE)
                    Input.keyReleased(k);
                if (key == k && action == GLFW_PRESS)
                    Input.keyPressed(k);
            }
        });
    }
}
