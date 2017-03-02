package main;

import entities.Camera;
import entities.CubeEntity;
import entities.IconosphereEntity;
import entities.TerrainEntity;
import generation.CubeGenerator;
import generation.IcosphereGenerator;
import generation.TerrainGenerator;
import models.Model;
import models.ModelData;
import org.joml.Vector3f;
import renderer.DisplayManager;
import renderer.Loader;
import renderer.MasterRenderer;
import shaders.StaticShader;
import utils.Console;


import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.Scanner;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL44.glBufferStorage;
import static utils.RayCasting.ray;

/**
 * Created by backes on 24/02/17.
 */
public class Engine {
    public static void main(String[] args){

//        Console console = new Console();
//        
//        Thread consoleThread = new Thread(console);
//        
//        consoleThread.start();

 
        
        DisplayManager.createDisplay();

        Loader loader = new Loader();

        StaticShader shader = new StaticShader();
        MasterRenderer renderer = new MasterRenderer(shader);
        Camera camera = new Camera(new Vector3f(0,0,0));
        
//        CubeGenerator cubeGenerator = new CubeGenerator();
//        cubeGenerator.generate();
//        ModelData cubeData = new ModelData(cubeGenerator.getVertices(),cubeGenerator.getIndices(),cubeGenerator.getTextureCoords(),3);
//        Model cubeModel = new Model(loader.loadToVAO(cubeData),cubeData.getCount());
//        CubeEntity cube = new CubeEntity(cubeModel,loader.loadTexture("white"));

        
//        IcosphereGenerator icosphereGenerator = new IcosphereGenerator(5);
//        icosphereGenerator.generate();
//        ModelData icoData = new ModelData(icosphereGenerator.getVertices(),icosphereGenerator.getIndices(),icosphereGenerator.getTextureCoords(),icosphereGenerator.getColour(),3);
//        Model icoModel = new Model(loader.loadToVAO(icoData),icoData.getCount());
//        IconosphereEntity ico = new IconosphereEntity(icoModel,loader.loadTexture("moon1k"));
//        ico.addPosition(0,0,-30);
//        ico.setScale(3);
////        renderer.addEntity(cube);
//        renderer.addEntity(ico);


        TerrainGenerator terrainGenerator = new TerrainGenerator(5,40,40);
        terrainGenerator.generate();
        //terrainGenerator.falseAlgorithm(100);
        ModelData terrainData = new ModelData(terrainGenerator.getVertices(),terrainGenerator.getIndices(),terrainGenerator.getTextureCoords(),terrainGenerator.getColour(),3);
        Model terrainModel = new Model(loader.loadToVAO(terrainData),terrainData.getCount());
        TerrainEntity terrain = new TerrainEntity(terrainModel);
        renderer.addEntity(terrain);
        
//        console.context(DisplayManager.window);
//        console.loadData(terrainGenerator,loader, terrainData, terrain);
        /** ================================================= **/


        /** ================================================= **/
        boolean once = false;
        double t = System.nanoTime()/1e9;
        while (!glfwWindowShouldClose(DisplayManager.window) ) {
            double current = System.nanoTime()/1e9;
            
            if (current-t >=1) {
                if (once){
                    FloatBuffer buffer = glMapBuffer(GL_ARRAY_BUFFER, GL_READ_WRITE).asFloatBuffer();
                    buffer.put(0.1f);
                    once = false;
                }
                terrainGenerator.randomHeight();
                terrainGenerator.generate();
                terrainGenerator.falseAlgorithm(200);
                
                loader.updateVBO(terrainData.getVertices(), terrain.getModel().getVerticesVBO());
                loader.updateVBO(terrainData.getColour(),  terrain.getModel().getColourVBO());

                t = System.nanoTime()/1e9;
            }

            camera.move();
            renderer.render(shader, camera);
            ray(camera,renderer);
            
            //glBindVertexArray(cube.getModel().getVaoID());
            
            //System.out.println(cube.getModel().getVaoID());
            //Matrix4f transformationMatrix= Maths.createTransformationMatrix(cube.getPosition(),cube.getRotation(),cube.getScale());
            //shader.loadTransformationMatrix(transformationMatrix);
            //glDrawArrays(GL_TRIANGLES, 0, 12*3); 
            
        }
//        
//        consoleThread.interrupt();
//        
//        try {
//            consoleThread.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        
        
        shader.cleanUp();
        loader.cleanUP();
    }
    
    
}
