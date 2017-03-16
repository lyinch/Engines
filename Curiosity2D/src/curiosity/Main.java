package curiosity;

import core.DisplayManager;
import org.lwjgl.BufferUtils;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

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

        /** ================================================= **/
        
        float[] vertices = new float[]{
                0.7f,0.7f,
                0.7f,0.6f,
                0.9f,0.7f,
                0.9f,0.6f,
        };
        int vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);
        int vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER,vboID);
        FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.length);
        buffer.put(vertices);
        buffer.flip();
        glBufferData(GL_ARRAY_BUFFER,buffer,GL_STATIC_DRAW);
        glVertexAttribPointer(0,2, GL_FLOAT,false,0,0);
        glEnableVertexAttribArray(0);



        final String VERTEX_FILE = "./src/shaders/vertexShader.vert";
        final String FRAGMENT_FILE = "./src/shaders/fragmentShader.frag";
        
        int vertexShaderID = loadShader(VERTEX_FILE,GL_VERTEX_SHADER);
        int fragmentShaderID = loadShader(FRAGMENT_FILE,GL_FRAGMENT_SHADER);
        int programID = glCreateProgram();
        glAttachShader(programID,vertexShaderID);
        glAttachShader(programID,fragmentShaderID);
        glBindAttribLocation(programID,0,"vertices");
        glLinkProgram(programID);
        glValidateProgram(programID);
        /** ================================================= **/

        while (!glfwWindowShouldClose(DisplayManager.window) ) {
            glUseProgram(programID);
            DisplayManager.update();
            glBindVertexArray(vaoID);
            glDrawArrays(GL_TRIANGLE_STRIP,0,4);
        }
        
    }

    /**
     * loads the shader file and compiles the shader
     * @param file The path to the shader
     * @param type the shader type: GL_VERTEX_SHADER or GL_FRAGMENT_SHADER
     * @return The compiled shader ID from openGL
     */
    private static int loadShader(String file, int type){
        StringBuilder shaderSource = new StringBuilder();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null){
                shaderSource.append(line).append("\n");
            }
            reader.close();
        }catch (IOException e){
            e.printStackTrace();
            throw new RuntimeException("Shader could not be read!");
        }

        int shaderID = glCreateShader(type);
        glShaderSource(shaderID,shaderSource);
        glCompileShader(shaderID);
        if (glGetShaderi(shaderID,GL_COMPILE_STATUS) == GL_FALSE){
            System.out.println(glGetShaderInfoLog(shaderID));
            throw new RuntimeException("Shader could not be compiled!");
        }
        return shaderID;
    }


}
