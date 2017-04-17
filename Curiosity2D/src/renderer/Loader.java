package renderer;

import entities.Entity;
import org.lwjgl.BufferUtils;
import textures.Texture;
import tileMap.TileMap;
import tileMap.TileMap_OLD;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load;

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
    
    public int attributeLess(int[] data){
        int vaoID = createVAO();
        int iboID = bindIndicesBuffer(data);
        return vaoID;
    }

    /**
     * Loads the tilemap
     *
     * @return Array of VAO/VBOs
     */
    public int[] loadTileMap(TileMap_OLD tileMapOLD) {
        int vaoID = createVAO();
        int vboVertices = storeDataInAttributeList(0,3, tileMapOLD.getVertices());
        int vboColour = storeDataInAttributeList(1,3, tileMapOLD.getColour());
        int vioID = bindIndicesBuffer(tileMapOLD.getIndices());
        int vboTex = storeDataInAttributeList(2,2, tileMapOLD.getTextureCoords());
        System.out.println(vioID);
        //b = glMapBufferRange(GL_ELEMENT_ARRAY_BUFFER, 0, 600 * 4, GL_MAP_WRITE_BIT).asIntBuffer();

        return new int[]{vaoID};
    }
    
    public int loadEmpty(TileMap tileMap){
        int vaoID = createVAO();
        int vboVertices = storeDataInAttributeList(0,2,tileMap.getVertices());
        int vboColour = storeDataInAttributeList(1,3,tileMap.getColour());
        return vaoID;
    }
    
    public int loadPoints(float[] data){
        int vaoID = createVAO();
        int vboVertices = storeDataInAttributeList(0,2,data);
        return vaoID;
    }

    public int loadEntity(Entity entity){
        int vaoID = createVAO();
        int vboVertices = storeDataInAttributeList(0,2,entity.getVertices());
        int vboIndices = bindIndicesBuffer(entity.getIndices());
        return vaoID;
    }

    public void updateTileMap(int[] data) {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 3);
        //IntBuffer b = glMapBufferRange(GL_ELEMENT_ARRAY_BUFFER,0, data.length*4,GL_MAP_WRITE_BIT).asIntBuffer();
        //System.out.println(data.length);
        b.put(data);
        b.flip();
        //System.out.println("worked");
    }
    
    public IntBuffer createPersistentIntBuffer(int vboID,int length){
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboID);
        return glMapBufferRange(GL_ELEMENT_ARRAY_BUFFER, 0, length * 4, GL_MAP_WRITE_BIT).asIntBuffer();
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
     * Loads a .png file as a texture with stbi, and binds the textureID. Returns the texture object
     * @param filename the filename of the texture, without a path or file ending
     * @return the Texture object
     */
    public Texture loadTexture(String filename){
        int textureID;
        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer components = BufferUtils.createIntBuffer(1);
        ByteBuffer data = stbi_load("./res/"+filename+".png",width,height,components,4);

        if (data == null)
            throw new RuntimeException("Could not load texture!");

        textureID = glGenTextures();

        int w = width.get();
        int h = height.get();

        glBindTexture(GL_TEXTURE_2D,textureID);

        glTexParameterf(GL_TEXTURE_2D,GL_TEXTURE_MIN_FILTER,GL_NEAREST);
        glTexParameterf(GL_TEXTURE_2D,GL_TEXTURE_MAG_FILTER,GL_NEAREST);

//        glTexParameterf(GL_TEXTURE_2D,GL_TEXTURE_WRAP_S,GL_CLAMP);
//        glTexParameterf(GL_TEXTURE_2D,GL_TEXTURE_WRAP_T,GL_MIRRORED_REPEAT);

        glTexImage2D(GL_TEXTURE_2D,0,GL_RGBA,w,h,0,GL_RGBA,GL_UNSIGNED_BYTE,data);

        stbi_image_free(data);
        //textures.add(textureID);

        return new Texture(textureID,w,h);
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
