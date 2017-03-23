package tileMap;

import org.joml.Math;

import java.util.Random;

/**
 * Created by backes on 19/03/17.
 */
public class TileMap {
    private float[] vertices;
    private int[] indices;
    private float[] textureCoords;
    private float[] colour;
    private float size;

    private final int WIDTH;
    private final int HEIGHT;

    public TileMap(float size, int width, int height) {
        WIDTH = width;
        HEIGHT = height;
        this.size = size;
        this.textureCoords = new float[2*10]; //Texture is not implemented, yet
        vertices = new float[(height+1)*(width+1)*3];
        indices = new int[height*width*2*3];
        this.colour = new float[(height+1)*(width+1)*3];
    }
    
    public void generateMap(){
        int ver_height = HEIGHT+1; //how many vertices we have for the height

        //we create the indices quad by quad, filling first the height
        int p = 0;
        for (int j = 0; j < WIDTH; j++) {
            for (int i = 0; i < HEIGHT ; i++) {
                indices[p++] = (j*ver_height+i);
                indices[p++] = (j*ver_height+i+1);
                indices[p++] = ((j+1)*ver_height+i+1);
                indices[p++] = (j*ver_height+i);
                indices[p++] = ((j+1)*ver_height+i+1);
                indices[p++] = ((j+1)*ver_height+i);
            }
        }

        p=0;
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

        
        //fill the texture coords with random value, to not have a null pointer exception. Not needed at the moment
        for (int i = 0; i < textureCoords.length; i++){
            textureCoords[i] = 1f;
        } 
    }

    public float[] getVertices() {
        return vertices;
    }

    public int[] getIndices() {
        return indices;
    }

    public float[] getColour() {
        return colour;
    }
}
