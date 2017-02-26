package generation;

import org.joml.Math;
import org.joml.Vector3f;
import org.joml.Vector3i;

import java.util.*;

/**
 * Created by backes on 26/02/17.
 */

/**
 * Generates an Icosphere, based on:
 * http://blog.andreaskahler.com/2009/06/creating-icosphere-mesh-in-code.html
 */
public class IcosphereGenerator implements Generator {
    private float[] vertices;
    private int[] indices;
    private float[] textureCoords;
    private float[] colour;
    private List<Vector3i> indicesList = new ArrayList<>();
    private List<Vector3f> verticesList = new ArrayList<>();
    
    private int index;
    private Map<Long,Integer> cache;
    private int detailLevel;

    public IcosphereGenerator(int detailLevel) {
        this.detailLevel = detailLevel;
        index = 0;
        cache = new HashMap<>();
    }

    @Override
    public void generate() {
        index = 0;
        cache.clear();
        /*
        Golden Ratio:
        In a regular icosahedron, all lengths are the same, therefore solving:
            dist( (-1,t,0),(1,t,0)) = dist( (1,t,0),(0,1,t))
        gives us two values for t:
        t1 = (1.0f + (float)Math.sqrt(5))/2.0f -> the golden ration 
        t2 = (1.0f - (float)Math.sqrt(5))/2.0f -> can be used to generate a beautiful great icosahedron
        */
        float t = (1.0f + (float)Math.sqrt(5))/2.0f;
        
        //Vertices are the corners of three orthogonal rectangles
        addVertex(new Vector3f(-1,t,0));
        addVertex(new Vector3f(1,t,0));
        addVertex(new Vector3f(-1,-t,0));
        addVertex(new Vector3f(1,-t,0));

        addVertex(new Vector3f(0,-1,t));
        addVertex(new Vector3f(0,1,t));
        addVertex(new Vector3f(0,-1,-t));
        addVertex(new Vector3f(0,1,-t));

        addVertex(new Vector3f(t,0,-1));
        addVertex(new Vector3f(t,0,1));
        addVertex(new Vector3f(-t,0,-1));
        addVertex(new Vector3f(-t,0,1));
        
        //indicesList are found by looking at an image of an icosphere created by three planes:
        indicesList.add(new Vector3i(0,11,5));
        indicesList.add(new Vector3i(0,5,1));
        indicesList.add(new Vector3i(0,1,7));
        indicesList.add(new Vector3i(0,7,10));
        indicesList.add(new Vector3i(0,10,11));

        indicesList.add(new Vector3i(1,5,9));
        indicesList.add(new Vector3i(5,11,4));
        indicesList.add(new Vector3i(11,10,2));
        indicesList.add(new Vector3i(10,7,6));
        indicesList.add(new Vector3i(7,1,8));

        indicesList.add(new Vector3i(3,9,4));
        indicesList.add(new Vector3i(3,4,2));
        indicesList.add(new Vector3i(3,2,6));
        indicesList.add(new Vector3i(3,6,8));
        indicesList.add(new Vector3i(3,8,9));
        
        indicesList.add(new Vector3i(4,9,5));
        indicesList.add(new Vector3i(2,4,11));
        indicesList.add(new Vector3i(6,2,10));
        indicesList.add(new Vector3i(8,6,7));
        indicesList.add(new Vector3i(9,8,1));
        
        
        refineTriangles(detailLevel);
        
        textureCoords = new float[2*verticesList.size()];
        int pointer = 0;
        for (Vector3f vert:verticesList){
            Vector3f tmp = vert;
            vert.normalize();
            float u = (float) (java.lang.Math.atan2(vert.x,vert.z)/(2f* java.lang.Math.PI))+0.5f;
            float v = vert.y*0.5f+0.5f;
            textureCoords[pointer++]=u;
            textureCoords[pointer++]=v;
        }
        
        listToArray(verticesList,indicesList);
    }

    /**
     * Recursively adds a new vertex between every edge
     * @param details The recursion depth
     */
    private void refineTriangles(int details){
        for (int i = 0; i < details; i++){
            List<Vector3i> indices2 = new ArrayList<>();
            for (Vector3i vec:indicesList) {
                int a = getMiddlePoint(vec.x,vec.y);
                int b = getMiddlePoint(vec.y,vec.z);
                int c = getMiddlePoint(vec.z,vec.x);

                indices2.add(new Vector3i(vec.x,a,c));
                indices2.add(new Vector3i(vec.y,b,a));
                indices2.add(new Vector3i(vec.z,c,b));
                indices2.add(new Vector3i(a,b,c));
            }
            cache.clear();
            indicesList = indices2;
        }
    }

    /**
     * Copies the data from the ArrayLists to the output Arrays
     * @param vertices
     * @param indices
     */
    private void listToArray(List<Vector3f> vertices, List<Vector3i> indices){
        
        this.indices = new int[indices.size()*3];
        int pointer = 0;
        for (Vector3i indi:indices){
            this.indices[pointer++] = indi.x;
            this.indices[pointer++] = indi.y;
            this.indices[pointer++] = indi.z;
        }
        
        
        this.vertices = new float[vertices.size()*3];
        this.colour = new float[vertices.size()*3];
        pointer = 0;
        float max = 0;
        for (Vector3f coord:vertices) {

            /** ================================================= **/
            float j = new Random().nextInt(2);
            float k = new Random().nextInt(2);
            float l = new Random().nextInt(2);

            if (j == 0)
                j = new Random().nextFloat();
            else
                j = -new Random().nextFloat();


            if (k == 0)
                k = new Random().nextFloat();
            else
                k = -new Random().nextFloat();


            if (l == 0)
                l = new Random().nextFloat();
            else
                l = -new Random().nextFloat();
            coord.add(j / 10, k / 10, l / 10);

            //System.out.println(middle + " " + j + " " + k + " " + l);
            max = Math.max(max,coord.length());
            /** ================================================= **/
            //System.out.println(coord.lengthSquared());
        }
        
        pointer = 0;
        for (Vector3f coord:vertices){
            colour[pointer] = coord.length()/max;
            this.vertices[pointer++] = coord.x;
            colour[pointer] = coord.length()/max;

            this.vertices[pointer++] = coord.y;
            colour[pointer] = coord.length()/max;
            this.vertices[pointer++] = coord.z;
        } 
    }

    /**
     * Adds a new vertex in the center of an edge to the mesh
     * @param p1 The first point
     * @param p2 The second point
     * @return The index for the indices list
     */
    private int getMiddlePoint(int p1, int p2){
        
        boolean firstIsSmaller = p1<p2;
        long smallerIndex = firstIsSmaller ? p1:p2;
        long greaterIndex = firstIsSmaller ? p2:p1;
        long key = (smallerIndex << 32) + greaterIndex;
        
        Integer val = cache.get(key);
        if (val != null)
            return val;
        
        
        Vector3f point1 = verticesList.get(p1);
        Vector3f point2 = verticesList.get(p2);
        Vector3f middle = new Vector3f(
                (point1.x+point2.x) / 2.0f,
                (point1.y+point2.y) / 2.0f,
                (point1.z+point2.z) / 2.0f);

        /** ================================================= **/
        float j = new Random().nextInt(2);
        float k = new Random().nextInt(2);
        float l = new Random().nextInt(2);
        
        if (j == 0)
            j = new Random().nextFloat();
        else 
            j = - new Random().nextFloat();


        if (k == 0)
            k = new Random().nextFloat();
        else
            k = - new Random().nextFloat();


        if (l == 0)
            l = new Random().nextFloat();
        else
            l = - new Random().nextFloat();
        //middle.add(j,k,l);

        //System.out.println(middle + " " + j + " " + k + " " + l);

        /** ================================================= **/
        int i = addVertex(middle);
        cache.put(key,i);
        return i;
    }

    /**
     * Normalizes the vector, and adds it to the mesh
     * @param p the vector which is added to the mesh
     * @return the index of the vertex in the list
     */
    private int addVertex(Vector3f p){
        p.normalize();
        verticesList.add(p);
        return index++;
    }
    
    
    @Override
    public int[] getIndices() {
        return indices;
    }

    @Override
    public float[] getVertices() {
        return vertices;
    }

    public int getDetailLevel() {
        return detailLevel;
    }

    public void setDetailLevel(int detailLevel) {
        this.detailLevel = detailLevel;
    }

    public float[] getTextureCoords() {
        return textureCoords;
    }

    public float[] getColour() {
        return colour;
    }
}
