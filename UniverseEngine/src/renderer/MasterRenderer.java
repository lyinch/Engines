package renderer;

import entities.Camera;
import entities.Entity;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import shaders.Shader;
import shaders.StaticShader;
import utils.Maths;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

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

        //glEnable(GL_CULL_FACE);
        //glCullFace(GL_BACK);
    }

    /**
     * Creates the mysterious projection matrix 
     * @return 4 Dimensional projection Matrix
     * http://www.songho.ca/opengl/gl_projectionmatrix.html
     * http://www.opengl-tutorial.org/beginners-tutorials/tutorial-3-matrices/
     * https://github.com/JOML-CI/JOML/wiki/JOML-and-modern-OpenGL
     * https://www.youtube.com/watch?v=50Y9u7K0PZo
     */
    public Matrix4f createProjectionMatrix(){
        float aspectRatio = (float) DisplayManager.getWIDTH() / DisplayManager.getHEIGHT();
        return new Matrix4f().perspective(FOV, aspectRatio,
                Z_NEAR, Z_FAR);
    }

    /**
     * The master render loop
     */
    public void render(StaticShader shader,Camera camera){
        prepare();
        shader.start();
        shader.loadViewMatrix(camera);
        renderEntity(shader);
        shader.stop();
        DisplayManager.update();
        //entities.clear();
    }

    /**
     * Renders the entity: Creates and loads the transformation matrix, and then draws the entity
     * @param shader The shader to load the transformation matrix to
     */
    private void renderEntity(StaticShader shader){
        for (Entity entity:entities){
            glBindVertexArray(entity.getModel().getVaoID());
            Matrix4f transformationMatrix= Maths.createTransformationMatrix(entity.getPosition(),entity.getRotation(),entity.getScale());
            shader.loadTransformationMatrix(transformationMatrix);
            
            //glActiveTexture(GL_TEXTURE0);
            //glBindTexture(GL_TEXTURE_2D,entity.getTexture().getTextureID());

            glDrawElements(GL_TRIANGLES, entity.getModel().getCount(), GL11.GL_UNSIGNED_INT, 0);
            entity.addRotation(0f,0,0);
        }
    }

    /**
     * Clears the framebuffer and thus prepares the render process
     */
    private void prepare(){
        glEnable(GL_DEPTH_TEST);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }
}
