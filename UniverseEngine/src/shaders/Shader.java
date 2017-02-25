package shaders;


import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;

/**
 * Created by backes on 24/02/17.
 */
public abstract class Shader {

    private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16); //needed to load 4*4 matrices


    private int vertexShaderID;
    private int fragmentShaderID;
    private int programID;


    /**
     * creates the program from the vertex and fragment shader, and binds the attributes
     * @param vertexShader The path to the vertex shader
     * @param fragmentShader The path to the fragment shader
     */
    public Shader(String vertexShader, String fragmentShader) {
        vertexShaderID = loadShader(vertexShader,GL_VERTEX_SHADER);
        fragmentShaderID = loadShader(fragmentShader,GL_FRAGMENT_SHADER);
        programID = glCreateProgram();
        glAttachShader(programID,vertexShaderID);
        glAttachShader(programID,fragmentShaderID);
        bindAttributes();
        glLinkProgram(programID);
        glValidateProgram(programID);
        getAllUniformLocations();
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
     * Gets the location/id of all the uniform variables
     */
    protected abstract void getAllUniformLocations();

    /**
     * Gets the location/id of the given uniform variable
     * @param uniformName The uniform variable name
     * @return The id of the uniform variable in the shader
     */
    protected int getUniformLocation(String uniformName){
        return glGetUniformLocation(programID,uniformName);
    }


    /**
     * Binds a given attribute number to an IN variable in the shader
     * @param attribute The attribute ID in the VAO
     * @param varName the IN variable name in the shader
     */
    protected void bindAttribute(int attribute, String varName){
        glBindAttribLocation(programID,attribute,varName);
    }

    /**
     * Initializes all the attributes of the shader
     */
    public abstract void bindAttributes();


    /**
     * Loads 4 Dimensional matrices to the shader
     * @param location The shader uniform variable
     * @param matrix The 4 Dimensional matrix
     */
    protected void loadMatrix(int location, Matrix4f matrix){
        matrix.get(matrixBuffer);
        glUniformMatrix4fv(location,false,matrixBuffer);
    }
    
    public void start(){
        glUseProgram(programID);
    }

    public void stop(){
        glUseProgram(0);
    }

    public void cleanUp(){
        stop();
        glDetachShader(programID,vertexShaderID);
        glDetachShader(programID,fragmentShaderID);
        glDeleteShader(fragmentShaderID);
        glDeleteShader(vertexShaderID);
        glDeleteProgram(programID);
    }
    
}
