package tileMap;

import core.Camera;
import renderer.Loader;
import utils.Math;

import java.nio.IntBuffer;
import java.util.Random;

/**
 * Created by backes on 19/03/17.
 */
public class TileMap_OLD {
    private float[] vertices;
    private int[] indices;
    private float[] textureCoords;
    private float[] colour;
    private float size;
        
    private IntBuffer persistentIndicesBuffer;
    
    private final int WIDTH;
    private final int HEIGHT;

    public TileMap_OLD(float size, int width, int height) {
        WIDTH = width;
        HEIGHT = height;
        this.size = size;
        this.textureCoords = new float[(height+1)*(width+1)*2];
        vertices = new float[(height+1)*(width+1)*3];
        indices = new int[(int)(1/size+1)*2*(int)(1/size+1)*2*2*3];
        this.colour = new float[(height+1)*(width+1)*3];
    }
    
    public void generateMap(int offX, int offY){
        calcIndices(offX,offY);

        
        
        int p=0;
        for (int i = 0; i < WIDTH+1; i++){
            for (int j = 0; j < HEIGHT+1; j++) {
                vertices[p++] = (i * size);
                vertices[p++] = j*size;
                vertices[p++] = 0;
            }
        }

        Random random = new Random();
        for (int i = 0; i <= colour.length-3; i+=3) {
            colour[i]   = random.nextFloat();
            colour[i+1] = random.nextFloat();
            colour[i+2] = random.nextFloat();
        }

        
        float x = 736/32;
        float y = 672/32;
//        for (int i = 0; i <= textureCoords.length-8; i+=8){
//            textureCoords[i] = 0f;
//            textureCoords[i+1] = 1f;
//            textureCoords[i+2] = 0f;
//            textureCoords[i+3] = 0f;
//            textureCoords[i+4] = 1f;
//            textureCoords[i+5] = 0f;
//            textureCoords[i+6] = 1f;
//            textureCoords[i+7] = 1f;
//        }
        
        for (int i = 0; i < textureCoords.length; i++)
            textureCoords[i] = 0;
        
        textureCoords[0] = 0;
        textureCoords[1] = 0;
        
        textureCoords[2] = 0;
        textureCoords[3] = 1;
        
        textureCoords[4] = 0;
        textureCoords[5] = 0;
        
        textureCoords[6] = 0;
        textureCoords[7] = 1;
        // --------------- //
        textureCoords[8] = 1;
        textureCoords[9] = 0;
        
        textureCoords[10] = 1;
        textureCoords[11] = 1;

        textureCoords[12] = 1;
        textureCoords[13] = 0;

        textureCoords[14] = 1;
        textureCoords[15] = 1;
        // --------------- //
        textureCoords[16] = 0;
        textureCoords[17] = 0;

        textureCoords[18] = 0;
        textureCoords[19] = 1;

        textureCoords[20] = 0;
        textureCoords[21] = 0;

        textureCoords[22] = 0;
        textureCoords[23] = 1;
        
        colour[0] = 1;
        colour[1] = 1;
        colour[2] = 1;
        
        colour[3] = 1;
        colour[4] = 1;
        colour[5] = 1;

        colour[6] = 1;
        colour[7] = 1;
        colour[8] = 1;

        colour[9] = 1;
        colour[10] = 1;
        colour[11] = 1;

        colour[12] = 1;
        colour[13] = 1;
        colour[14] = 1;

        
//        
//        int first = 0;
//        for (int i = 0; i <= textureCoords.length-4; i+=4){
//            if (first == 0) {
//                textureCoords[i + 0] = 0f;
//                textureCoords[i + 1] = 0f;
//                textureCoords[i + 2] = 1f;
//                textureCoords[i + 3] = 0f;
//                if (i%HEIGHT == 0)
//                    first = 1;
//            }else{
//                textureCoords[i + 0] = 1f;
//                textureCoords[i + 1] = 1f;
//                textureCoords[i + 2] = 0f;
//                textureCoords[i + 3] = 1f;
//                if (i%HEIGHT == 0)
//                    first = 0;
//            }
//        }
    }
    
    public void calcIndices(int offX, int offY){
        int ver_height = HEIGHT+1; //how many vertices we have for the height
        //we create the indices quad by quad, filling first the height
        int p = 0;
        int HEIGHT = java.lang.Math.round(1/size)*2;
        int WIDTH = java.lang.Math.round(1/size)*2;
        int offXHigh = offX+1;
        int offYHigh = offY+1;
        offXHigh = Math.clamp(offXHigh,0,this.WIDTH);
        offYHigh = Math.clamp(offYHigh,0,this.HEIGHT);
        offX = Math.clamp(offX,0,this.WIDTH);
        offY = Math.clamp(offY,0,this.HEIGHT);
        for (int j = offX; j < WIDTH+offXHigh; j++) {
            for (int i = offY; i < HEIGHT+offYHigh ; i++) {
                indices[p++] = (j*ver_height+i);
                indices[p++] = (j*ver_height+i+1);
                indices[p++] = ((j+1)*ver_height+i+1);
                indices[p++] = (j*ver_height+i);
                indices[p++] = ((j+1)*ver_height+i+1);
                indices[p++] = ((j+1)*ver_height+i);
            }
        }
    }
    
    public void updateIndices(Camera camera){
        int offX = (int)((camera.getPosition().x-1)/size), offY = (int)((camera.getPosition().y-1)/size);
        calcIndices(offX,offY);
        try {
            persistentIndicesBuffer.put(indices);
            persistentIndicesBuffer.flip();
        }catch (Exception e){
            throw new RuntimeException("TileMap not started!");
        }
    }
    
    public void start(Loader loader, int[] ids){
        persistentIndicesBuffer = loader.createPersistentIntBuffer(3,indices.length);
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

    public float[] getColour() {
        return colour;
    }


}
