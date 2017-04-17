package entities;

import core.DisplayManager;
import org.joml.Vector2f;
import org.joml.Vector3f;
import textures.Texture;

/**
 * Created by backes on 15/03/17.
 */
public abstract class Entity {
    private Vector2f position;
    private Vector2f rotation;
    private float scale;
    protected Texture texture;
    private int vaoID;
    private float[] vertices;
    private  int[] indices;
    
    public Entity(Vector2f position, Vector2f rotation, float scale, Texture texture) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.texture = texture;
        initAsset();
    }

    public Vector2f getPosition() {
        return position;
    }
    
    
    public Vector2f getRotation() {
        return rotation;
    }

    public Texture getTexture() {
        return texture;
    }

    public int getVaoID() {
        return vaoID;
    }

    public void setVaoID(int vaoID) {
        this.vaoID = vaoID;
    }
    
    private void initAsset(){
        float pixel = 128;
        float width = 1;
        float height = 1;
        float sX = width*(pixel/(float)DisplayManager.WIDTH);
        float sY = height*(pixel/(float)DisplayManager.HEIGHT);
//        vertices = new float[]{
//                -sX, sY, 
//                -sX, -sY,
//                sX, sY,
//                sX, -sY
//        };

        //anchor: top left
        vertices = new float[]{
                -1, 1,
                -1, 1-sY,
                -1+sX, 1,
                -1+sX, 1-sY
        };
        

        indices = new int[]{
                0,1,2,
                1,2,3
        };
    }

    public float[] getVertices() {
        return vertices;
    }

    public int[] getIndices() {
        return indices;
    }
    
    public void addPosition(float x, float y){
        this.position.add(x,y);
    }
}
