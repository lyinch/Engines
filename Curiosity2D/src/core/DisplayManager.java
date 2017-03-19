package core;

import org.lwjgl.opengl.GL;

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
        

        //Hardcode the function of the ESCAPE Key with a callback
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
        glfwSwapBuffers(window);
        glfwPollEvents();
        glClear(GL_COLOR_BUFFER_BIT); // clear the framebuffer

    }

    public static int getWIDTH() {
        return WIDTH;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }
}
