package renderer;

import core.Camera;
import entities.Entity;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import shaders.EntityShader;
import utils.Math;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

/**
 * Created by backes on 15/04/17.
 */
public class PointRenderer {
    public List<Integer> points;
    EntityShader shader;
    Camera camera;
    public PointRenderer(EntityShader shader, Camera camera) {
        points = new ArrayList<>();
        this.shader = shader;
        this.camera = camera;
    }
    
    public void render(){
        shader.start();
        for (int p:points){
            glBindVertexArray(p);
            shader.loadViewMatrix(camera);
            Matrix4f transformation = new Matrix4f();
            shader.loadTransformationMatrix(transformation);
            glDrawArrays(GL_LINE_STRIP,0,2);
        }
        shader.stop();
    }
    
    public void addPoints(int vaoid){
        points.add(vaoid);
    }

    
}
