package utils;

import entities.TerrainEntity;
import generation.TerrainGenerator;
import models.ModelData;
import renderer.Loader;

import java.util.Scanner;

import static org.lwjgl.glfw.GLFW.GLFW_NOT_INITIALIZED;
import static org.lwjgl.glfw.GLFW.glfwGetCurrentContext;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;

/**
 * Created by backes on 02/03/17.
 */
public class Console implements Runnable {
    private TerrainGenerator terrainGenerator;
    private Loader loader;
    private boolean loaded;
    private ModelData terrainData;
    private TerrainEntity terrain;
    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (!Thread.currentThread().isInterrupted()){
            String in = scanner.next();
            if (loaded) {
                if (glfwGetCurrentContext() == GLFW_NOT_INITIALIZED)
                    System.out.println("null");
                if (in.equals("generate")) {
                    System.out.println("Generating...");
                    terrainGenerator.randomHeight();
                    terrainGenerator.generate();
                    terrainGenerator.falseAlgorithm(200);
                    loader.updateVBO(terrainData.getVertices(), terrain.getModel().getVerticesVBO());
//                    loader.updateVBO(terrainData.getColour(),  terrain.getModel().getColourVBO());
                    System.out.println("Done!");
                }
            }else {
                System.out.println("Console has not been initialized, yet!");
            }
        }
        scanner.close();
        
    }
    
    public void context(long window){
        glfwMakeContextCurrent(window);
    }
    
    public void loadData(TerrainGenerator terrainGenerator, Loader loader, ModelData terrainData, TerrainEntity terrain){
        this.terrainGenerator = terrainGenerator;
        this.loader = loader;
        this.loaded = true;
        this.terrainData = terrainData;
        this.terrain = terrain;
    }
}
