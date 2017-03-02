package models;

/**
 * Created by backes on 24/02/17.
 */
public class Model {
    private int vaoID;
    private int textureVBO;
    private int verticesVBO;
    private int colourVBO;
    private int indicesVBO;
    private int count;
    
    public Model(int[] ids, int count) {
        //ids: [0] = VAO [1] = vertices Buffer [2] = indices buffer [3] = texture coords [4] = colour VBO
        this.vaoID = ids[0];
        this.verticesVBO = ids[1];
        this.indicesVBO = ids[2];
        this.textureVBO = ids[3];
        this.colourVBO = ids[4];
        this.count = count;
    }

    public int getVaoID() {
        return vaoID;
    }

    public int getCount() {
        return count;
    }

    public int getTextureVBO() {
        return textureVBO;
    }

    public int getVerticesVBO() {
        return verticesVBO;
    }

    public int getColourVBO() {
        return colourVBO;
    }

    public int getIndicesVBO() {
        return indicesVBO;
    }
}
