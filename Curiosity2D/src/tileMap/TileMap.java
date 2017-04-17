package tileMap;

import core.DisplayManager;
import org.joml.Vector2f;

import java.nio.FloatBuffer;
import java.util.Random;

/**
 * Created by backes on 15/04/17.
 */
public class TileMap {
    private float[] vertices;
    private int[] indices;
    private float[] textureCoords;
    private float[] colour;
    public final int[][] cells;
    private int vboColour;
    private final int WIDTH;
    private final int HEIGHT;

    private FloatBuffer cBuffer;
    
    private int vaoID;
    
    public TileMap(int width, int height) {
        WIDTH = width;
        HEIGHT = height;
        this.textureCoords = new float[(height+1)*(width+1)*2];
        vertices = new float[WIDTH*HEIGHT*6*2];
        indices = new int[WIDTH*HEIGHT];
        this.colour = new float[WIDTH*HEIGHT*6*3];
        cells = new int[WIDTH][HEIGHT];
        Random rand = new Random();
        for (int i = 0; i < WIDTH; i ++){
            for(int j = 0; j < HEIGHT; j++){
                if (j == 0)
                    cells[i][j] = 3;
                else
                    cells[i][j] = rand.nextInt(4);
            }
        }
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
            int cellID = cells[column][row];
            
            switch (cellID) {
                case 0: colR = 0.0f; break;
                case 1: colR = 0.4f; break;
                case 2: colR = 0.7f; break;
                case 3: colR = 0.9f; break;
            }
            
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

    public void load(int[] data) {
        this.vaoID = data[0]; 
        this.vboColour = data[1];
    }
    
    public void createBuffer(FloatBuffer buffer){
        cBuffer = buffer;
    }
    
    public void changeColour(int x, int y){
        this.cells[x][y] = 3;
        //System.out.println(x + ":" + y + " " + this.cells[x][y]);
        int pos = (x+(y*WIDTH))*3*6;
        
        //System.out.println(x + ":" + y + ":" + pos);
        for (int i = 0; i < 6*3; i++) {
            this.colour[pos++] = 1f;
        }

        try {
            cBuffer.put(colour);
            cBuffer.flip();
        }catch (Exception e){
            throw new RuntimeException("TileMap not started!");
        }
    }

    public int getVaoID() {
        return vaoID;
    }
    public int getTileType(int x, int y){
        //System.out.println("Asked: " + x + ":"+y + ":" + cells[x][y]);
        return cells[x][y];
    }

    public int getVboColour() {
        return vboColour;
    }
}
