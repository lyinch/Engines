package curiosity;

import core.Camera;
import core.DisplayManager;
import org.lwjgl.opengl.GL11;
import renderer.Loader;
import shaders.WorldShader;
import tileMap.TileMap;
import tileMap.TileRenderer;

import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

/**
 * Created by backes on 14/03/17.
 */
public class Main {
    public static void main(String[] args) {
        DisplayManager.createDisplay();
        glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);

        /** ================================================= **/
        

        
        int vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);
        
        /** ================================================= **/


        TileRenderer renderer = new TileRenderer();
        float tileSide = 0.2f;
        int width = 40, height = 40;
        TileMap tileMap = new TileMap(tileSide,width,height);
        tileMap.generateMap(0,0);
        

        WorldShader shader = new WorldShader();
        Camera camera = new Camera();
        shader.loadViewMatrix(camera);
        
        Loader loader = new Loader();
        int[] d = loader.loadTileMap(tileMap);
        vaoID = d[0];
        
        
        tileMap.start(loader,new int[]{});
        while (!glfwWindowShouldClose(DisplayManager.window) ) {
            shader.start();
            glBindVertexArray(vaoID);
            //renderer.render();
            shader.loadViewMatrix(camera);
            camera.addX();
            tileMap.updateIndices(camera);
            //System.out.println((DisplayManager.WIDTH*(camera.getPosition().x-1))/0.2);
            //loader.updateTileMap(indices);
            //System.out.println(camera.getPosition());

            glDrawElements(GL_TRIANGLES,tileMap.getIndices().length, GL11.GL_UNSIGNED_INT, 0);
            //glDrawArrays(GL_TRIANGLE_STRIP,0,4);
            DisplayManager.update();
            shader.stop();
        }
        
        loader.cleanUP();
        
    }
    

}
