package curiosity;

import core.Camera;
import core.DisplayManager;
import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
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

        TileMap tileMap = new TileMap(1,40,40);
        tileMap.generateMap();

        int vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER,vboID);
        FloatBuffer buffer = BufferUtils.createFloatBuffer(tileMap.getVertices().length);
        buffer.put(tileMap.getVertices());
        buffer.flip();
        glBufferData(GL_ARRAY_BUFFER,buffer,GL_STATIC_DRAW);
        glVertexAttribPointer(0,3, GL_FLOAT,false,0,0);
        glEnableVertexAttribArray(0);


        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER,vboID);
        buffer = BufferUtils.createFloatBuffer(tileMap.getColour().length);
        buffer.put(tileMap.getColour());
        buffer.flip();
        glBufferData(GL_ARRAY_BUFFER,buffer,GL_STATIC_DRAW);
        glVertexAttribPointer(1,3, GL_FLOAT,false,0,0);
        glEnableVertexAttribArray(1);
        

        vboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,vboID);
        IntBuffer buffer2 = BufferUtils.createIntBuffer(tileMap.getIndices().length);
        buffer2.put(tileMap.getIndices());
        buffer2.flip();
        glBufferData(GL_ELEMENT_ARRAY_BUFFER,buffer2,GL_STATIC_DRAW);
        //glVertexAttribPointer(1,3, GL_FLOAT,false,0,0);

        WorldShader shader = new WorldShader();
        Camera camera = new Camera();
        shader.loadViewMatrix(camera);
        while (!glfwWindowShouldClose(DisplayManager.window) ) {
            shader.start();
            glBindVertexArray(vaoID);
            //renderer.render();
            shader.loadViewMatrix(camera);
            camera.addX();
            glDrawElements(GL_TRIANGLES,tileMap.getIndices().length, GL11.GL_UNSIGNED_INT, 0);
            //glDrawArrays(GL_TRIANGLE_STRIP,0,4);
            DisplayManager.update();
            shader.stop();
        }
        
    }

}
