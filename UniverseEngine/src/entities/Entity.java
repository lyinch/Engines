package entities;

import models.Model;
import org.joml.Vector3f;
import textures.Texture;

/**
 * Created by backes on 24/02/17.
 */
public abstract class Entity {
    private Model model;
    
    private Vector3f position;
    private Vector3f rotation;
    private float scale;
    protected Texture texture;

    public Entity(Model model, Vector3f position, Vector3f rotation, float scale) {
        this.model = model;
        this.position = position;
        this.rotation = rotation; 
        this.scale = scale;
    }
    
    
    public void addPosition(Vector3f positionOffset){
        this.position.add(positionOffset);
    }

    public void addPosition(float x, float y, float z){
        this.position.add(x,y,z);
    }


    public void addRotation(Vector3f rotationOffset){
        this.rotation.add(rotationOffset);
    }

    public void addRotation(float rx, float ry, float rz){
        this.rotation.add(rx,ry,rz);
    }


    public void scale(float scale){
       this.scale += scale;
    }
    
    public void setPosition(Vector3f position){
        this.position = position;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public float getScale() {
        return scale;
    }

    public Model getModel() {
        return model;
    }

    public Texture getTexture() {
        return texture;
    }
}
