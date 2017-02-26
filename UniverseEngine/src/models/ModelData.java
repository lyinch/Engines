package models;

/**
 * Created by backes on 24/02/17.
 */
public class ModelData {
    private float[] vertices;
    private float[] textureCoords;
    private float[] colour;
    private int[] indices;
    private int dimension;
    private int count;
    
    public ModelData(float[] vertices, int[] indices, int dimension) {
        this.vertices = vertices;
        this.indices = indices;
        this.dimension = dimension;
        count = indices.length;
    }

    public ModelData(float[] vertices, int[] indices, float[] textureCoords, float[] colour, int dimension) {
        this.vertices = vertices;
        this.indices = indices;
        this.dimension = dimension;
        this.textureCoords = textureCoords;
        this.colour = colour;
        count = indices.length;
    }

    public float[] getVertices() {
        return vertices;
    }

    public int[] getIndices() {
        return indices;
    }

    public float[] getTextureCoords() {
        return textureCoords;
    }

    public int getDimension() {
        return dimension;
    }

    public int getCount() {
        return count;
    }

    public float[] getColour() {
        return colour;
    }
}
