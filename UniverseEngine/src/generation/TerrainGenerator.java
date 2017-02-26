package generation;

/**
 * Created by backes on 26/02/17.
 */
public class TerrainGenerator implements Generator{
    private float[] vertices;
    private int[] indices;
    private float[] textureCoords;
    private float[] colour;
    private int size;
    private int tileSize = 40;
    
    public TerrainGenerator(int size) {
        this.size = size;
        this.indices = new int[3*size*size];
        this.vertices = new float[3*size*size];
        this.colour = new float[3*size*size];
        this.textureCoords = new float[2*size*size];
    }

    @Override
    public void generate() {
        int pointer = 0;
        for (int i = 0; i < size; i++){
            for (int j = 0; j <  size; j++){
                vertices[pointer*3] = tileSize*i;
                vertices[pointer*3+1] = 0;
                vertices[pointer*3+2] = tileSize*j;
                vertices[pointer*2] = 0;
                vertices[pointer*2+1] = 1;
            }
        }

        pointer = 0;
        for (int i = 1; i < size+1; i++){
            for (int j = 1; j <  size+1; j++){
                indices[pointer++] = i;
                indices[pointer++] = j;
                indices[pointer++] = 0;
            }
        }
        
    }

    @Override
    public int[] getIndices() {
        return new int[0];
    }

    @Override
    public float[] getVertices() {
        return new float[0];
    }

    public float[] getTextureCoords() {
        return textureCoords;
    }

    public float[] getColour() {
        return colour;
    }
    
    
}
