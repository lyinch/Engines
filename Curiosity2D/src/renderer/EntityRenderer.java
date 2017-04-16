package renderer;

import core.Camera;
import entities.Entity;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import shaders.EntityShader;
import shaders.WorldShader;
import utils.Math;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

/**
 * Created by backes on 15/04/17.
 */
public class EntityRenderer {
    public List<Entity> entities;
    EntityShader shader;
    Camera camera;
    public EntityRenderer(EntityShader shader, Camera camera) {
        entities = new ArrayList<>();
        this.shader = shader;
        this.camera = camera;
    }
    
    public void render(){
        shader.start();
        for (Entity entity:entities){
            shader.loadViewMatrix(camera);
            glBindVertexArray(entity.getVaoID());
            Matrix4f transformation = Math.createTransformationMatrix(entity.getPosition(),entity.getRotation(),1);
            
            shader.loadTransformationMatrix(transformation);
            glDrawElements(GL_TRIANGLES, 6, GL11.GL_UNSIGNED_INT, 0);
        }
        shader.stop();
    }
    
    public void addEntity(Entity entity){
        entities.add(entity);
    }

    
}
