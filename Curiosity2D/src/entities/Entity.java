package entities;

import org.joml.Vector3f;
import textures.Texture;

/**
 * Created by backes on 15/03/17.
 */
public abstract class Entity {
    private Vector3f position;
    private Vector3f rotation;
    private float scale;
    protected Texture texture;
   
    public Entity(Vector3f position, Vector3f rotation, float scale, Texture texture) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.texture = texture;
    }

    public Vector3f getPosition() {
        return position;
    }
    
    
    public Vector3f getRotation() {
        return rotation;
    }

    public Texture getTexture() {
        return texture;
    }
}
