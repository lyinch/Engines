package curiosity;

import core.Camera;
import core.DisplayManager;
import entities.Entity;
import entities.Player;
import org.joml.Vector2f;
import org.joml.Vector3f;
import renderer.EntityRenderer;
import renderer.Loader;
import shaders.EntityShader;
import shaders.WorldShader;
import tileMap.TileMap;
import renderer.TileRenderer;

import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_FRONT_AND_BACK;
import static org.lwjgl.opengl.GL11.GL_LINE;
import static org.lwjgl.opengl.GL11.glPolygonMode;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

/**
 * Created by backes on 14/03/17.
 */
public class Main {
    
    public final static int pixels = 128;
    
    public static void main(String[] args) {
        DisplayManager.createDisplay();
        //glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);

        /** ================================================= **/
        

        

        
        /** ================================================= **/



        WorldShader worldShader = new WorldShader();
        EntityShader entityShader = new EntityShader();
        Camera camera = new Camera();
        worldShader.loadViewMatrix(camera);
        entityShader.loadViewMatrix(camera);
        TileRenderer tileRenderer = new TileRenderer(worldShader,camera);

        Loader loader = new Loader();

        double last = System.nanoTime()/1e9;
        int frames = 0;
        
        TileMap tileMap = new TileMap(40,40);
        tileMap.generateMap();
        tileMap.setVaoID(loader.loadEmpty(tileMap));
        tileRenderer.addMap(tileMap);
        
        //worldShader.start();

        Player player = new Player(new Vector2f(0,0), new Vector2f(0,0),1,loader.loadTexture("character"));
        player.setVaoID(loader.loadEntity(player));

        EntityRenderer entityRenderer = new EntityRenderer(entityShader,camera);
        entityRenderer.addEntity(player);
        while (!glfwWindowShouldClose(DisplayManager.window) ) {
            double current = System.nanoTime()/1e9;
            DisplayManager.update();
            tileRenderer.render();
            entityRenderer.render();
            player.move();
            camera.move(player);
            frames++;
            if (current-last>=1){
                System.out.println(frames);
                frames = 0;
                last = current;
            }
            glfwSwapBuffers(DisplayManager.window);

        }
        
        loader.cleanUP();
        
    }
    

}
