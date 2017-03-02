package renderer;

import models.ModelData;
import org.lwjgl.BufferUtils;
import textures.Texture;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load;

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

    /**
     * 
     * @param modelData
     * @return array of vao and vbo ids. [0] = VAO [1] = vertices Buffer [2] = indices buffer [3] = texture coords [4] =
     * colour VBO
     */
    public int[] loadToVAO(ModelData modelData){
        int[] ids = new int[5];
        ids[0] = createVAO();
        ids[1] = storeDataInAttributeList(0,modelData.getDimension(),modelData.getVertices());
        ids[2] = bindIndicesBuffer(modelData.getIndices());
        ids[3] = storeDataInAttributeList(1,2,modelData.getTextureCoords());
        ids[4] = storeDataInAttributeList(1,3,modelData.getColour());

        return ids;
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
        System.out.println(vboID);
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
        glBufferData(GL_ELEMENT_ARRAY_BUFFER,buffer,GL_STATIC_DRAW);
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

        glTexImage2D(GL_TEXTURE_2D,0,GL_RGBA,w,h,0,GL_RGBA,GL_UNSIGNED_BYTE,data);

        stbi_image_free(data);
        textures.add(textureID);

        return new Texture(textureID,w,h);
    }
    
    public synchronized void updateVBO(float[] data, int VBOid){
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        glBindBuffer(GL_ARRAY_BUFFER,VBOid);
        glBufferSubData(GL_ARRAY_BUFFER,0,buffer);

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
