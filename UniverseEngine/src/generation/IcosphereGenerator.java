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
    float[] vertices;
    int[] indices;
    List<Vector3i> indicesList = new ArrayList<>();
    List<Vector3i> indicesListFinal = new ArrayList<>();
    List<Vector3f> verticesList = new ArrayList<>();
    private int index;
    private Map<Long,Integer> cache;
    
    @Override
    public void generate() {
        index = 0;
        cache = new HashMap<Long,Integer>();
        
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

        for (int i = 0; i < 6; i++){
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

        for (Vector3i vec:indicesList) {
            indicesListFinal.add(vec);
        }
        
        
        this.indices = new int[indicesListFinal.size()*3];
        int pointer = 0;
        for (Vector3i indi:indicesListFinal){
            this.indices[pointer++] = indi.x;
            this.indices[pointer++] = indi.y;
            this.indices[pointer++] = indi.z;
        }

        this.vertices = new float[verticesList.size()*3];
        pointer = 0;
        for (Vector3f coord:verticesList){
            this.vertices[pointer++] = coord.x;
            this.vertices[pointer++] = coord.y;
            this.vertices[pointer++] = coord.z;
        }
    }

    /**
     * Adds the vertex in the center of an edge to the mesh
     * @param p1
     * @param p2
     * @return
     */
    private int getMiddlePoint(int p1, int p2){
        boolean firstIsSmaller = p1<p2;
        long smallerIndex = firstIsSmaller ? p1:p2;
        long greaterIndex = firstIsSmaller ? p2:p1;
        long key = (smallerIndex << 32) + greaterIndex;
        
        if (this.cache.containsKey(key)){
            return cache.get(key);
        }
        
        
        Vector3f point1 = verticesList.get(p1);
        Vector3f point2 = verticesList.get(p2);
        Vector3f middle = new Vector3f(
                (point1.x+point2.x) / 2.0f,
                (point1.y+point2.y) / 2.0f,
                (point1.z+point2.z) / 2.0f);
        
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
        float length = p.length();
        verticesList.add(new Vector3f(p.x/length,p.y/length,p.z/length));
        //index++;
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
}
