package renderer;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL30.GL_MAP_WRITE_BIT;
import static org.lwjgl.opengl.GL30.glMapBufferRange;
import static org.lwjgl.opengl.GL44.GL_MAP_PERSISTENT_BIT;

/**
 * Created by backes on 16/03/17.
 */
public class Loader {

    IntBuffer b = glMapBufferRange(GL_ELEMENT_ARRAY_BUFFER,0, 600*4,GL_MAP_WRITE_BIT).asIntBuffer();

    
    
    
    /**
     * Loads the tilemap
     * @param vertices
     * @param indices
     * @param colours
     * @return Array of VAO/VBOs
     */
    public int[] loadTileMap(float[] vertices, float[] indices, float[] colours){
        
        return new int[]{};
    }
    
    public void updateTileMap(int[] data){
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,3);
        //IntBuffer b = glMapBufferRange(GL_ELEMENT_ARRAY_BUFFER,0, data.length*4,GL_MAP_WRITE_BIT).asIntBuffer();
        //System.out.println(data.length);
        b.put(data);
        b.flip();
        //System.out.println("worked");
    }
}
