package IO;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_LAST;

/**
 * Created by backes on 25/04/17.
 */
public class Input {

    
    private static boolean[] keys = new boolean[GLFW_KEY_LAST];
    private static boolean[] keysLast = new boolean[GLFW_KEY_LAST];
    
    
    public static void keyPressed(int keyCode){
        keys[keyCode] = true;
    }

    public static void keyReleased(int keyCode){
        keys[keyCode] = false;
    }
    
    
    
    public static boolean isKeyPressed(int keyCode){
        return (keys[keyCode] && !keysLast[keyCode]);
    }
    
    public static boolean isKeyDown(int keyCode){
        return (keysLast[keyCode] && keys[keyCode]);
    }

    public static boolean isKeyReleased(int keyCode){
        return (keysLast[keyCode] && !keys[keyCode]);
    }
    
    public static void update(){
        keysLast = keys.clone();
    }

}
