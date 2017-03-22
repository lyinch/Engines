package curiosity;

import core.Camera;
import core.DisplayManager;
import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
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
        
        float[] vertices = new float[]{
                -0.5f,0.5f,
                -0.5f,-0.5f,
                0.5f,0.5f,
                0.5f,-0.5f,
        };
        float[] colour = new float[]{
                1,0,1,
                0,1,1,
                1,1,1,
                0.5f,0.5f,0,
        };
        
        int vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);
        
//        int vboID = glGenBuffers();
//        glBindBuffer(GL_ARRAY_BUFFER,vboID);
//        FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.length);
//        buffer.put(vertices);
//        buffer.flip();
//        glBufferData(GL_ARRAY_BUFFER,buffer,GL_STATIC_DRAW);
//        glVertexAttribPointer(0,2, GL_FLOAT,false,0,0);
//        glEnableVertexAttribArray(0);
//
//
//        vboID = glGenBuffers();
//        glBindBuffer(GL_ARRAY_BUFFER,vboID);
//        buffer = BufferUtils.createFloatBuffer(colour.length);
//        buffer.put(vertices);
//        buffer.flip();
//        glBufferData(GL_ARRAY_BUFFER,buffer,GL_STATIC_DRAW);
//        glVertexAttribPointer(1,3, GL_FLOAT,false,0,0);
//        glEnableVertexAttribArray(1);



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




        final String VERTEX_FILE = "./src/shaders/vertexShader.vert";
        final String FRAGMENT_FILE = "./src/shaders/fragmentShader.frag";

        int vertexShaderID = loadShader(VERTEX_FILE,GL_VERTEX_SHADER);
        int fragmentShaderID = loadShader(FRAGMENT_FILE,GL_FRAGMENT_SHADER);
        int programID = glCreateProgram();
        glAttachShader(programID,vertexShaderID);
        glAttachShader(programID,fragmentShaderID);
        glBindAttribLocation(programID,0,"vertices");
        glBindAttribLocation(programID,1,"colour");
        glLinkProgram(programID);
        glValidateProgram(programID);

       FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16); //needed to load 4*4 matrices

        int cameraLocation = glGetUniformLocation(programID,"viewMatrix");
        Camera camera = new Camera();
        Matrix4f view = createViewMatrix(camera);
        view.get(matrixBuffer);
        glUniformMatrix4fv(cameraLocation,false,matrixBuffer);

        while (!glfwWindowShouldClose(DisplayManager.window) ) {
            glUseProgram(programID);
            glBindVertexArray(vaoID);
            //renderer.render();
            view = createViewMatrix(camera);
            view.get(matrixBuffer);
            glUniformMatrix4fv(cameraLocation,false,matrixBuffer);
            camera.addX();
            glDrawElements(GL_TRIANGLES,tileMap.getIndices().length, GL11.GL_UNSIGNED_INT, 0);
            //glDrawArrays(GL_TRIANGLE_STRIP,0,4);
            DisplayManager.update();

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


    /**
     * Creates the Camera Matrix. This matrix moves the world in the opposite direction, and thus creating the illusion
     * of a camera, therefore the negative position translation
     * @param camera The camera
     * @return The view Matrix
     */
    public static Matrix4f createViewMatrix(Camera camera){
        Matrix4f viewMatrix = new Matrix4f();
        Vector3f cameraPos = camera.getPosition();
        Vector3f negativeCameraPos = new Vector3f(-cameraPos.x,-cameraPos.y,-cameraPos.z);
        viewMatrix.translate(negativeCameraPos);
        return viewMatrix;
    }


}
