package main;

import entities.Camera;
import entities.CubeEntity;
import entities.Entity;
import generation.CubeGenerator;
import models.Model;
import models.ModelData;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import renderer.DisplayManager;
import renderer.Loader;
import renderer.MasterRenderer;
import shaders.StaticShader;
import utils.Maths;


import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;

import static org.lwjgl.opengl.GL30.glBindVertexArray;

/**
 * Created by backes on 24/02/17.
 */
public class Engine {
    public static void main(String[] args){
        DisplayManager.createDisplay();

        Loader loader = new Loader();
        
        CubeGenerator cubeGenerator = new CubeGenerator();
        ModelData cubeData = new ModelData(cubeGenerator.generate());
        Model cubeModel = new Model(loader.loadToVAO(cubeData.getVertices()));
        CubeEntity cube = new CubeEntity(cubeModel);
        StaticShader shader = new StaticShader();
        MasterRenderer renderer = new MasterRenderer(shader);
        Camera camera = new Camera(new Vector3f(0,0,0));
        /** ================================================= **/

        /** ================================================= **/
        
        while (!glfwWindowShouldClose(DisplayManager.window) ) {
            cube.addPosition(0,0,-0.001f);
            cube.addRotation(0,1f,1f);

            glEnable(GL_DEPTH_TEST);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
            camera.move();
            shader.start();
            shader.loadViewMatrix(camera);
            glBindVertexArray(cube.getModel().getVaoID());
            
            //System.out.println(cube.getModel().getVaoID());
            Matrix4f transformationMatrix= Maths.createTransformationMatrix(cube.getPosition(),cube.getRotation(),cube.getScale());
            shader.loadTransformationMatrix(transformationMatrix);
            glDrawArrays(GL_TRIANGLES, 0, 12*3); 

            glfwSwapBuffers(DisplayManager.window);
            shader.stop();
            glfwPollEvents();
        }
        shader.cleanUp();
    }
    
    
}
