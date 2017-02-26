package generation;

/**
 * Created by backes on 24/02/17.
 */
public interface Generator {
    //The generator function for the vertices
    void generate();
    int[] getIndices();
    float[] getVertices();
}
