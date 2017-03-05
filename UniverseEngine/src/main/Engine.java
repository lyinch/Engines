package main;
//Thank you almighty internet, for making this crap possible
import entities.Camera;
import entities.TerrainEntity;
import generation.TerrainGenerator;
import gui.GuiComponent;
import gui.GuiData;
import gui.GuiModel;
import models.Model;
import models.ModelData;
import org.joml.Vector3f;
import renderer.DisplayManager;
import renderer.Loader;
import renderer.MasterRenderer;
import shaders.StaticShader;
import utils.RayCasting;

import java.util.Arrays;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

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


        TerrainGenerator terrainGenerator = new TerrainGenerator(1,50,50);
        terrainGenerator.generate();
        //terrainGenerator.falseAlgorithm(100);
        ModelData terrainData = new ModelData(terrainGenerator.getVertices(),terrainGenerator.getIndices(),terrainGenerator.getTextureCoords(),terrainGenerator.getColour(),3);
        Model terrainModel = new Model(loader.loadToVAO(terrainData),terrainData.getCount());
        TerrainEntity terrain = new TerrainEntity(terrainModel);
        renderer.addEntity(terrain);
        
//        console.context(DisplayManager.window);
//        console.loadData(terrainGenerator,loader, terrainData, terrain);

        GuiData sidebarData = new GuiData(new float[]{
                0.6f,1f,
                0.6f,-1,
                1,1,
                1,-1,
        },  new float[]{
                1,1,1,
                0.5f,0.5f,0.5f,
                1,0,1,
                0,1,1
        });

        GuiModel sidebarModel = new GuiModel(sidebarData,loader.loadToVao(sidebarData),4);
        GuiComponent sidebar = new GuiComponent(sidebarModel);
        renderer.addGui(sidebar);
        /** ================================================= **/


        /** ================================================= **/
        
        double t = System.nanoTime()/1e9;
        
        RayCasting ray = new RayCasting(camera,renderer);
        
        while (!glfwWindowShouldClose(DisplayManager.window) ) {
            double current = System.nanoTime()/1e9;
            
            if (current-t >=0.1) {

                ray.update();
                terrain.markQuad(ray.getPointOnGround(),1,50,50,loader,Arrays.copyOf(terrainData.getColour(),terrainData.getColour().length));
                terrain.modifyQuad(ray.getPointOnGround(),1,50,50,loader,terrainData.getVertices());

                t = System.nanoTime()/1e9;
            }

            camera.move();
            renderer.render(shader, camera);
            
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
