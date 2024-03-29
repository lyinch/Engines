package renderer;

import entities.Camera;
import entities.Entity;
import gui.GuiComponent;
import gui.GuiShader;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import shaders.StaticShader;
import utils.Maths;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

/**
 * Created by backes on 25/02/17.
 */
public class MasterRenderer {
    private final float FOV = 70;
    private final float Z_NEAR = 0.1f;
    private final float Z_FAR = 1000f;

    private List<Entity> entities;
    private Stack<GuiComponent> guiComponents;
    
    
  
    public MasterRenderer(StaticShader shader) {
        shader.start();
        shader.loadProjectionMatrix(createProjectionMatrix());
        shader.stop();
        entities = new ArrayList<>();
        guiComponents = new Stack<>();
//        VAO_ID = glGenVertexArrays();
//        glBindVertexArray(VAO_ID);
//        int vboID = glGenBuffers();
//        glBindBuffer(GL_ARRAY_BUFFER,vboID);
//        float[] data = new float[]{
//                0f,0f,0,
//                0,0,1,
//                -1,0,1,
//                -1,0,0
//                };
//        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
//        buffer.put(data);
//        buffer.flip();
//        glBufferData(GL_ARRAY_BUFFER,buffer,GL_STATIC_DRAW);
//        glVertexAttribPointer(0,3, GL_FLOAT,false,0,0);
//
//
//
//        vboID = glGenBuffers();
//        glBindBuffer(GL_ARRAY_BUFFER,vboID);
//        data = new float[]{
//                1,1,1,
//                0.5f,0.5f,0.5f,
//                1,0,1,
//                0,1,1
//        };
//        buffer = BufferUtils.createFloatBuffer(data.length);
//        buffer.put(data);
//        buffer.flip();
//        glBufferData(GL_ARRAY_BUFFER,buffer,GL_STATIC_DRAW);
//        glVertexAttribPointer(1,3, GL_FLOAT,false,0,0);
//
//        glEnableVertexAttribArray(0);
//        glEnableVertexAttribArray(1);

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
        
//        glBindVertexArray(VAO_ID);
//        Matrix4f transformationMatrix= Maths.createTransformationMatrix(new Vector3f(0,0,0),new Vector3f(0,0,0),1);
//        shader.loadTransformationMatrix(transformationMatrix);
//
//        glDrawArrays(GL_LINE_STRIP,0,4);
        shader.stop();
        
        GuiShader guiS = new GuiShader();
        guiS.start();
        renderGui(shader);
        guiS.stop();
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
    
    private void renderLine(StaticShader shader){
        
    }
    
    private void renderGui(StaticShader shader){
        while (!guiComponents.isEmpty()){
            GuiComponent gui = guiComponents.pop();
            glBindVertexArray(gui.getGuiModel().getVaoID());
            Matrix4f transformationMatrix= Maths.createTransformationMatrix(gui.getPosition(),new Vector3f(0,0,0),1f);
            shader.loadTransformationMatrix(transformationMatrix);
            glDrawArrays(GL_TRIANGLE_STRIP,0,gui.getGuiModel().getCount());

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
    
    public void addGui(GuiComponent gui) {
        guiComponents.add(gui);
    }
}
