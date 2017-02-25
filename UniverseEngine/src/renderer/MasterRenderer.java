package renderer;

import entities.Camera;
import entities.Entity;
import org.joml.Matrix4f;
import shaders.Shader;
import shaders.StaticShader;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;

/**
 * Created by backes on 25/02/17.
 */
public class MasterRenderer {
    private final float FOV = 70;
    private final float Z_NEAR = 0.1f;
    private final float Z_FAR = 1000f;

    private List<Entity> entities;
    
    public MasterRenderer(StaticShader shader) {
        shader.start();
        shader.loadProjectionMatrix(createProjectionMatrix());
        shader.stop();
        entities = new ArrayList<>();
    }

    /**
     * Creates the mysterious projection matrix 
     * @return 4 Dimensional projection Matrix
     * http://www.songho.ca/opengl/gl_projectionmatrix.html
     * http://www.opengl-tutorial.org/beginners-tutorials/tutorial-3-matrices/
     * https://github.com/JOML-CI/JOML/wiki/JOML-and-modern-OpenGL
     * https://www.youtube.com/watch?v=50Y9u7K0PZo
     */
    private Matrix4f createProjectionMatrix(){
        float aspectRatio = (float) DisplayManager.getWIDTH() / DisplayManager.getHEIGHT();
        return new Matrix4f().perspective(FOV, aspectRatio,
                Z_NEAR, Z_FAR);
    }

    /**
     * The master render loop
     */
    public void render(Camera camera){
        prepare();
    }

    /**
     * Clears the framebuffer and thus prepares the render process
     */
    private void prepare(){
        glEnable(GL_DEPTH_TEST);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
    }
    
    
}
