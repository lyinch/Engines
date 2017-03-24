package curiosity;

import core.Camera;
import core.DisplayManager;
import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import renderer.Loader;
import shaders.WorldShader;
import tileMap.TileMap;
import tileMap.TileRenderer;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glValidateProgram;
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

        TileMap tileMap = new TileMap(0.2f,40,40);
        tileMap.generateMap();
        
        //glVertexAttribPointer(1,3, GL_FLOAT,false,0,0);

        WorldShader shader = new WorldShader();
        Camera camera = new Camera();
        shader.loadViewMatrix(camera);
        
        int HEIGHT = (int)(1/0.2f)*2;
        int WIDTH = (int)(1/0.2f)*2;
        System.out.println(WIDTH);
        int[] indices = new int[HEIGHT*WIDTH*2*3];

        int ver_height = 40+1; //how many vertices we have for the height

        //we create the indices quad by quad, filling first the height
        int p = 0;
        int offX = (int)(camera.getPosition().x-1), offY = (int)(camera.getPosition().y-1);

        //noinspection Duplicates
        for (int j = offX; j < WIDTH+offX; j++) {
            for (int i = offY; i < HEIGHT+offY ; i++) {
                indices[p++] = (j*ver_height+i);
                indices[p++] = (j*ver_height+i+1);
                indices[p++] = ((j+1)*ver_height+i+1);
                
                indices[p++] = (j*ver_height+i);
                indices[p++] = ((j+1)*ver_height+i+1);
                indices[p++] = ((j+1)*ver_height+i);
            }
        }




        Loader loader = new Loader();
        int[] d = loader.loadTileMap(tileMap);
        vaoID = d[0];
        while (!glfwWindowShouldClose(DisplayManager.window) ) {
            shader.start();
            glBindVertexArray(vaoID);
            //renderer.render();
            shader.loadViewMatrix(camera);
            camera.addX();
            indices = calcIndices((int)((camera.getPosition().x-1)/0.2),(int)(camera.getPosition().y-1),camera);
            //System.out.println((DisplayManager.WIDTH*(camera.getPosition().x-1))/0.2);
            loader.updateTileMap(indices);
            //System.out.println(camera.getPosition());

            glDrawElements(GL_TRIANGLES,tileMap.getIndices().length, GL11.GL_UNSIGNED_INT, 0);
            //glDrawArrays(GL_TRIANGLE_STRIP,0,4);
            DisplayManager.update();
            shader.stop();
        }
        
        loader.cleanUP();
        
    }
    
    public static int[] calcIndices(int offX, int offY,Camera camera){
        int HEIGHT = (int)(1/0.2f)*2;
        int WIDTH = (int)(1/0.2f)*2;
        int[] indices = new int[HEIGHT*WIDTH*2*3];
        
        int ver_height = 40+1; //how many vertices we have for the height
        //offX= (int)((DisplayManager.WIDTH*(camera.getPosition().x-1))/0.2 - WIDTH);
        //System.out.println(offX);
        //we create the indices quad by quad, filling first the height
        int p = 0;
        //offX+=1;
        //noinspection Duplicates
        for (int j = offX; j < WIDTH+offX; j++) {
            for (int i = offY; i < HEIGHT+offY ; i++) {
                indices[p++] = (j*ver_height+i);
                indices[p++] = (j*ver_height+i+1);
                indices[p++] = ((j+1)*ver_height+i+1);

                indices[p++] = (j*ver_height+i);
                indices[p++] = ((j+1)*ver_height+i+1);
                indices[p++] = ((j+1)*ver_height+i);
            }
        }
        return indices;
    }

}
