package renderer;

import models.ModelData;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

/**
 * Created by backes on 24/02/17.
 */
public class Loader {
    //Keeping track of existing VAOs, VBOs and Textures to cleanup
    private List<Integer> vaos = new ArrayList<>();
    private List<Integer> vbos = new ArrayList<>();
    private List<Integer> textures = new ArrayList<>();


    /**
     * Loads the vertices and indices into the VAO and returns the ID
     * @param vertices the vertices array
     * @param indices the indices array
     * @param dimension The dimension of the coordinates (i.e. vertices: 3 , indices : 2)
     * @return the VAO id
     */
    public int loadToVAO(float[] vertices, int[] indices, int dimension){
        int vaoID = createVAO();
        storeDataInAttributeList(0,dimension,vertices);
        bindIndicesBuffer(indices);
        return vaoID;
    }

    public int loadToVAO(ModelData modelData){
        int vaoID = createVAO();
        storeDataInAttributeList(0,modelData.getDimension(),modelData.getVertices());
        bindIndicesBuffer(modelData.getIndices());
        return vaoID;
    }

    public void loadToVAO(float[] vertices, float[] normals, float[] indices){

    }
    
    
    public void loadToVAO(float[] vertices,float[] textureCoords, float[] normals, float[] indices){

    }


    /**
     * Creates a new VBO and stores the data in it
     * @param attributeNumber The attribute where the VBO is stored
     * @param coordSize The dimension of the coordinate vector
     * @param data the coordinates as a 1D float array
     * @return the VBO ID
     */
    private int storeDataInAttributeList(int attributeNumber, int coordSize, float[] data){
        int vboID = glGenBuffers();
        vbos.add(vboID);
        glBindBuffer(GL_ARRAY_BUFFER,vboID);
        FloatBuffer buffer = storeDataInFloatBuffer(data);
        glBufferData(GL_ARRAY_BUFFER,buffer,GL_STATIC_DRAW);
        glVertexAttribPointer(attributeNumber,coordSize, GL_FLOAT,false,0,0);
        glEnableVertexAttribArray(attributeNumber);
        return vboID;
    }

    /**
     * Binds the indices buffer to the VAO
     * @param indices the indices array
     */
    private void bindIndicesBuffer(int[] indices){
        int vboIndicesID = glGenBuffers();
        vbos.add(vboIndicesID);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,vboIndicesID);
        IntBuffer buffer = storeDataInIntBuffer(indices);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER,buffer,GL_STATIC_DRAW);
    }

    /**
     * Creates a FloatBuffer and stores the data in the buffer
     * @param data the data to be stored in the buffer
     * @return the float buffer
     */
    private FloatBuffer storeDataInFloatBuffer(float[] data){
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    /**
     * Creates an IntBuffer and stores the data in the buffer
     * @param data the data to be stored in the buffer
     * @return the int buffer
     */
    private IntBuffer storeDataInIntBuffer(int[] data){
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }


    /**
     * Creates and binds a new VAO
     * @return the VAO ID
     */
    private int createVAO(){
        int vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);
        vaos.add(vaoID);
        return vaoID;
    }
    
    /**
     * Deletes all the VAOs VBOs and Textures
     */
    public void cleanUP(){
        for (int vao: this.vaos)
            glDeleteVertexArrays(vao);

        for (int vbo: this.vbos)
            glDeleteBuffers(vbo);
        
        for (int tex : this.textures)
            glDeleteTextures(tex);
    }
}
