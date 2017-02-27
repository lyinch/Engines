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
        this.colour = new float[3*10];
        this.textureCoords = new float[2*10];
    }

    @Override
    public void generate() {
//        vertices = new float[]{
//                0,0,0,
//                0,0,10,
//                0,0,20,
//                10,0,0,
//                10,0,10,
//                10,0,20
//                
//        };
//        
//        indices = new int[]{
//                0,1,4,
//                0,4,3,
//                1,2,5,
//                1,5,4
//        };
        
        int height = 4;
        int width = 2;
        vertices = new float[height*(width+1)*3];
        indices = new int[height*(width+1)*3];

        int size = 10;
        int p = 0;
        //indices
        for (int j = 0; j < width; j++) {
            for (int i = j * height; i < (j + 1) * height - 1; i++) {
                indices[p++] = i;
                indices[p++] = (i+1);
                indices[p++] = (i+1+height);
                indices[p++] = i;
                indices[p++] = (i+1+height);
                indices[p++] = (i+height);
                //System.out.println("[" + i + "," + (i + 1) + "," + (i + 1 + height) + "]");
                //System.out.println("[" + i + "," + (i + 1 + height) + "," + (i + height) + "]");
            }
        }
        
        p = 0;
        for (int i = 0; i < width+1; i++){
            for (int j = 0; j < height; j++) {
                vertices[p++] = (i * size);
                vertices[p++] = 0;
                vertices[p++] = j*size;
                //System.out.println((i * size) + " : " + 0 + " : " + (j * size));
                //System.out.println(i + ":" + j);
            }
        }
        


        for (int i = 0; i < colour.length; i++){
            colour[i] = 0.5f;
        }

        for (int i = 0; i < textureCoords.length; i++){
            textureCoords[i] = 0.5f;
        }
    }

    @Override
    public int[] getIndices() {
        return indices;
    }

    @Override
    public float[] getVertices() {
        return vertices;
    }

    public float[] getTextureCoords() {
        return textureCoords;
    }

    public float[] getColour() {
        return colour;
    }
    
    
}
