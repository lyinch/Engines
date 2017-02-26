package models;

/**
 * Created by backes on 24/02/17.
 */
public class ModelData {
    private float[] vertices;
    private int[] indices;
    private int dimension;
    private int count;
    
    public ModelData(float[] vertices, int[] indices, int dimension) {
        this.vertices = vertices;
        this.indices = indices;
        this.dimension = dimension;
        count = indices.length;
    }

    public float[] getVertices() {
        return vertices;
    }

    public int[] getIndices() {
        return indices;
    }
    
    public int getDimension() {
        return dimension;
    }

    public int getCount() {
        return count;
    }
}
