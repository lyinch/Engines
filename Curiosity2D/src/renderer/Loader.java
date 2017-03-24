package renderer;

import org.lwjgl.BufferUtils;
import tileMap.TileMap;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;

/**
 * Created by backes on 16/03/17.
 */
public class Loader {

    IntBuffer b;
    private List<Integer> vaos;
    private List<Integer> vbos;


    public Loader() {
        vaos = new ArrayList<>();
        vbos = new ArrayList<>();
    }

    /**
     * Loads the tilemap
     *
     * @return Array of VAO/VBOs
     */
    public int[] loadTileMap(TileMap tileMap) {
        int vaoID = createVAO();
        int vboVertices = storeDataInAttributeList(0,3,tileMap.getVertices());
        int vboColour = storeDataInAttributeList(1,3,tileMap.getColour());
        int vioID = bindIndicesBuffer(tileMap.getIndices());
        b = glMapBufferRange(GL_ELEMENT_ARRAY_BUFFER, 0, 600 * 4, GL_MAP_WRITE_BIT).asIntBuffer();

        return new int[]{vaoID};
    }

    public void updateTileMap(int[] data) {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 3);
        //IntBuffer b = glMapBufferRange(GL_ELEMENT_ARRAY_BUFFER,0, data.length*4,GL_MAP_WRITE_BIT).asIntBuffer();
        //System.out.println(data.length);
        b.put(data);
        b.flip();
        //System.out.println("worked");
    }

    /**
     * Creates and binds a new VAO
     *
     * @return the VAO ID
     */
    private int createVAO() {
        int vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);
        vaos.add(vaoID);
        return vaoID;
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
     * @return VBO ID
     */
    private int bindIndicesBuffer(int[] indices){
        int vboIndicesID = glGenBuffers();
        vbos.add(vboIndicesID);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,vboIndicesID);
        IntBuffer buffer = storeDataInIntBuffer(indices);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER,buffer,GL_DYNAMIC_DRAW);
        return vboIndicesID;
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
     * Deletes all the VAOs VBOs and Textures
     */
    public void cleanUP(){
        for (int vao: this.vaos)
            glDeleteVertexArrays(vao);

        for (int vbo: this.vbos)
            glDeleteBuffers(vbo);
//
//        for (int tex : this.textures)
//            glDeleteTextures(tex);
    }
    
}
