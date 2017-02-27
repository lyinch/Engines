package generation;

import org.joml.Math;
import org.joml.Vector3f;

import java.util.Random;

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
        this.textureCoords = new float[2*10];
    }

    @Override
    public void generate() {
        
        //nbr. of squares with the given size
        int width = 200;
        int height = 200;
        int size = 1;

        vertices = new float[(height+1)*(width+1)*3];
        indices = new int[height*width*2*3];
        this.colour = new float[(height+1)*(width+1)*3];

        int ver_height = height+1; //how many vertices we have for the height
        
        int p = 0;
        for (int j = 0; j < width; j++) {
            for (int i = 0; i < height ; i++) {
                indices[p++] = (j*ver_height+i);
                indices[p++] = (j*ver_height+i+1);
                indices[p++] = ((j+1)*ver_height+i+1);
                indices[p++] = (j*ver_height+i);
                indices[p++] = ((j+1)*ver_height+i+1);
                indices[p++] = ((j+1)*ver_height+i);
//                p+=6;
//                System.out.println(p);
//
//                System.out.println("[" + (j*ver_height+i) + "," + (j*ver_height+i+1) + "," + ((j+1)*ver_height+i+1) + "]");
//                System.out.println("[" + (j*ver_height+i) + "," + ((j+1)*ver_height+i+1) + "," + ((j+1)*ver_height+i) + "]");
            }
        }
        
        p=0;
        int max = 0;
        for (int i = 0; i < width+1; i++){
            for (int j = 0; j < height+1; j++) {
                vertices[p++] = (i * size);
                int h = new Random().nextInt(5);
                max = java.lang.Math.max(max,h);
                vertices[p++] = h;
                vertices[p++] = j*size;
                //System.out.println((i * size) + " : " + 0 + " : " + (j * size));
                //System.out.println(i + ":" + j);
            }
        }



       
        for (int i = 0; i < colour.length-3; i+=3){
//            colour[i]=1/5f*vertices[i+1];
//            colour[i+1]=1/5f*vertices[i+1];
//            colour[i+2]=1/5f*vertices[i+1]; 
            colour[i]=1/5f*vertices[i+1];
            colour[i+1]=1/5f*vertices[i+1];
            colour[i+2]=1/5f*vertices[i+1];
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
