package generation;

import org.joml.Math;
import java.util.Random;

import static utils.Maths.lerp;

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
    
    private int WIDTH;
    private int HEIGHT;
    
    public TerrainGenerator(int size) {
        this.size = size;
        this.textureCoords = new float[2*10];
    }

    @Override
    public void generate() {
        
        //nbr. of squares with the given size
        int width = 40;
        int height = 40;
        int size = 5;
        WIDTH = width;
        HEIGHT = height;

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
        float max = 0;
        float min = 0;
        for (int i = 0; i < width+1; i++){
            for (int j = 0; j < height+1; j++) {
                vertices[p++] = (i * size);
                int h = new Random().nextInt(5);
                max = java.lang.Math.max(max,h);
                min = Math.min(min,h);
                h=0;
                vertices[p++] = h;
                vertices[p++] = j*size;
                //System.out.println((i * size) + " : " + 0 + " : " + (j * size));
                //System.out.println(i + ":" + j);
            }
        }






        //System.out.println(max + " : " + min);
        float a = 0;
        float b = 1;
        float f = 12;

        //System.out.println((a * (1.0f - f)) + (b * f));


        for (int i = 0; i < colour.length-3; i+=3){
//            colour[i]=1/5f*vertices[i+1];
//            colour[i+1]=1/5f*vertices[i+1];
//            colour[i+2]=1/5f*vertices[i+1]; 
            //System.out.println(vertices[i+1]);
            float h = lerp(min,0,max,1,vertices[i+1]);
            //System.out.println(h);
            if(h >= 0.45f) {
                //white (0xffffff to brown 0x7e5151)
                colour[i]=lerp(0.75f,256f/256f,0.45f,(0x7e/256f),h);
                colour[i+1]=lerp(0.75f,256f/256f,0.45f,(0x51/256f),h);
                colour[i+2]=lerp(0.75f,256f/256f,0.45f,(0x51/256f),h);
            }else if(h >=0.40f ) {
                //brown 0x7e5151 to green 0x2da552
                colour[i] = lerp(0.45f,0x7e/256f,0.40f,0x2d/256f,h);
                colour[i + 1] = lerp(0.45f,0x51/256f,0.40f,0xa5/256f,h);
                colour[i + 2] = lerp(0.45f,0x51/256f,0.40f,0x52/256f,h);
            }else{
                //green 0x2da552 to blue 0x6087e8
                colour[i] = 0x60/255f;
                colour[i + 1] =  0x87/255f;
                colour[i + 2] =  0xe8/255f;
            }

//            colour[i] = 1 / 5f * vertices[i + 1];
//            colour[i + 1] = 1 / 5f * vertices[i + 1];
//            colour[i + 2] = 1 / 5f * vertices[i + 1];
        }
        
        

        for (int i = 0; i < textureCoords.length; i++){
            textureCoords[i] = 0.5f;
        }
    }


    /**
     * 
     * http://www.lighthouse3d.com/opengl/terrain/index.php?impdetails
     * @param iterations
     */
    public void falseAlgorithm(int iterations){
        float disp = 0.3f;
        
        float min = 0;
        float max = 0;
        for (int k = 0; k < 400; k++){
            int rand1 = new Random().nextInt(vertices.length/3)*3;
            int rand2 = new Random().nextInt(vertices.length/3)*3;
            while(rand2 == rand1){
                System.out.println("collision");
                rand2 = new Random().nextInt(vertices.length/3)*3;
            }
            if(rand2 %3 != 0 || rand1 %3 != 0)
                System.out.println("not mult of 3");
//            float a = (vertices[rand2+2]-vertices[rand1+2]);
//            float b = -(vertices[rand2]-vertices[rand1]);
//            float c = -vertices[rand1]*(a)-vertices[rand1+2]*b;

            float a = (vertices[rand2+2]-vertices[rand1+2]);
            float b = -(vertices[rand2]-vertices[rand1]);
            //float c = -vertices[rand1]*(a)-vertices[rand1+2]*b;


            for (int i = 0; i < vertices.length-3; i+=3){
                if( ((vertices[i]-vertices[rand1])*a+b*(vertices[i+2]-vertices[rand1+2])) > 0)
                    vertices[i+1] += disp;
                else
                    vertices[i+1]-=disp;
                max = Math.max(max,vertices[i+1]);
                min = Math.min(min,vertices[i+1]);
            }
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
    
    public void randomHeight(){
        int p = 0;
        for (int i = 0; i < WIDTH+1; i++){
            for (int j = 0; j < HEIGHT+1; j++) {
                p++;
                int h = new Random().nextInt(5);
                int sign = new Random().nextInt(2);
                vertices[p++] = sign ==  0 ? (+h)  :  (-h);
                //vertices[p++] += h;
                p++;
                //System.out.println((i * size) + " : " + 0 + " : " + (j * size));
                //System.out.println(i + ":" + j);
            }
        }
    }
    
}
