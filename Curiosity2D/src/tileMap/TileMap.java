package tileMap;

import core.DisplayManager;
import curiosity.World;
import org.joml.Vector2f;

import java.nio.FloatBuffer;
import java.util.Random;

/**
 * Created by backes on 15/04/17.
 */
public class TileMap {
    //Game Data
    private float[] vertices;
    private float[] colour;
    private int[][] tiles;
    
    //GPU Data
    private int[] IDs; //VAO,CBO
    private FloatBuffer persistentColourBuffer;

    //Parameters
    private final int WIDTH;
    private final int HEIGHT;
    private final int pixels;

    
    private Random rand = new Random();

    
    
    public TileMap(int width, int height) {
        IDs = new int[2];
        WIDTH = width;
        HEIGHT = height;
        vertices = new float[WIDTH*HEIGHT*6*2];
        colour = new float[WIDTH*HEIGHT*6*3];
        tiles = new int[WIDTH][HEIGHT];
        this.pixels = World.PIXELS;
        fillCells();
    }

    /**
     * Fills the cell array with random cells
     */
    private void fillCells(){
        for (int i = 0; i < WIDTH; i ++){
            for(int j = 0; j < HEIGHT; j++){
                if (j == 0)
                    tiles[i][j] = 3;
                else
                    tiles[i][j] = rand.nextInt(4);
            }
        }
    }

    /**
     * Generates the map. 6 vertices by quad, going from left to right
     */
    public void generateMap(){

        int p = 0;
        
        Vector2f[] lookup ={
                new Vector2f(0,0),
                new Vector2f(0,-1f),
                new Vector2f(1f,0),
                new Vector2f(0,-1f),
                new Vector2f(1f,-1f),
                new Vector2f(1f,0),
        };
        
        int cell = WIDTH*HEIGHT;
        float sizeX = pixels/(float)DisplayManager.WIDTH;
        float sizeY = pixels/(float)DisplayManager.HEIGHT;
        int c_p = 0;
        
        //For each square
        for (int i = 0; i < cell; i++){
            int row = i/WIDTH;
            int column = i%WIDTH;
            
            float x = (-1+column*sizeX);
            float y = (1-row*sizeY);
            
            int cellID = tiles[column][row];

            //Colour
            float colR = rand.nextFloat();
            float colG = rand.nextFloat();
            float colB = rand.nextFloat();
            
            switch (cellID) {
                case 0: colR = 0.0f; break;
                case 1: colR = 0.4f; break;
                case 2: colR = 0.7f; break;
                case 3: colR = 0.9f; break;
            }
            //For each square, create the vertices and the colour
            for (int k = 0; k < 6; k++) {
                vertices[p++] = x + lookup[k].x*sizeX;
                vertices[p++] = y + lookup[k].y*sizeY;
                colour[c_p++] = colR;
                colour[c_p++] = colR;
                colour[c_p++] = colR;
            }
        }
    }

    public float[] getVertices() {
        return vertices;
    }

    public float[] getColour() {
        return colour;
    }


    /**
     * Loads the tilemap to the VAO and saves the VAO and VBO IDs.
     * @param data the ID array: VAO,CBO
     */
    public void loadToVAO(int[] data) {
        this.IDs[0] = data[0];
        this.IDs[1] = data[1];
    }

    /**
     * Assigns the persistent colour buffer
     * @param buffer The colour buffer
     */
    public void assignPersistentColourBuffer(FloatBuffer buffer){
        persistentColourBuffer = buffer;
    }
    
    
    public void consumeTile(int x, int y){
        //assign NONE state
        this.tiles[x][y] = 3;
        
        int pos = (x+(y*WIDTH))*3*6;
        
        for (int i = 0; i < 6*3; i++) {
            this.colour[pos++] = 1f;
        }
        try {
            persistentColourBuffer.put(colour);
            persistentColourBuffer.flip();
        }catch (Exception e){
            throw new RuntimeException("Could not consume tile!");
        }
    }

    public int getVaoID() {
        return IDs[0];
    }
    
    public int getTileType(int x, int y){
        return tiles[x][y];
    }

    public int getCboID() {
        return IDs[1];
    }
}
