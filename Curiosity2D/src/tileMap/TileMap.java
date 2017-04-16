package tileMap;

import core.DisplayManager;
import org.joml.Vector2f;

import java.util.Random;

/**
 * Created by backes on 15/04/17.
 */
public class TileMap {
    private float[] vertices;
    private int[] indices;
    private float[] textureCoords;
    private float[] colour;

    private final int WIDTH;
    private final int HEIGHT;

    private int vaoID;
    
    public TileMap(int width, int height) {
        WIDTH = width;
        HEIGHT = height;
        this.textureCoords = new float[(height+1)*(width+1)*2];
        vertices = new float[WIDTH*HEIGHT*6*2];
        indices = new int[WIDTH*HEIGHT];
        this.colour = new float[WIDTH*HEIGHT*6*3];
    }

    public void generateMap(){

        int p = 0;
        
        Vector2f[] lookup ={
                new Vector2f(0,0),
                new Vector2f(0,-1f),
                new Vector2f(1f,0),
                new Vector2f(0,-1f),
                new Vector2f(1f,-1f),
                new Vector2f(1f,0) };
        
        int cell = WIDTH*HEIGHT;
        float sizeX = 128/(float)DisplayManager.WIDTH;
        float sizeY = 128/(float)DisplayManager.HEIGHT;
        int c_p = 0;
        Random random = new Random();
        
        for (int i = 0; i < cell; i++){
            int row = i/WIDTH;
            int column = i%WIDTH;
            
            float x = (-1+column*sizeX);
            float y = (1-row*sizeY);
            float colR = random.nextFloat();
            float colG = random.nextFloat();
            float colB = random.nextFloat();
            for (int k = 0; k < 6; k++) {
                vertices[p++] = x + lookup[k].x*sizeX;
                vertices[p++] = y + lookup[k].y*sizeY;
                colour[c_p++] = colR;
                colour[c_p++] = colG;
                colour[c_p++] = colB;
            }
        }
    }

    public float[] getVertices() {
        return vertices;
    }

    public float[] getColour() {
        return colour;
    }

    public void setVaoID(int vaoID) {
        this.vaoID = vaoID;
    }

    public int getVaoID() {
        return vaoID;
    }
}
