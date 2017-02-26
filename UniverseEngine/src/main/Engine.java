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


import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by backes on 24/02/17.
 */
public class Engine {
    public static void main(String[] args){
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

        
        IcosphereGenerator icosphereGenerator = new IcosphereGenerator(4);
        icosphereGenerator.generate();
        ModelData icoData = new ModelData(icosphereGenerator.getVertices(),icosphereGenerator.getIndices(),icosphereGenerator.getTextureCoords(),icosphereGenerator.getColour(),3);
        Model icoModel = new Model(loader.loadToVAO(icoData),icoData.getCount());
        IconosphereEntity ico = new IconosphereEntity(icoModel,loader.loadTexture("moon1k"));
        ico.addPosition(0,0,-3);
//        renderer.addEntity(cube);
        renderer.addEntity(ico);

//        TerrainGenerator terrainGenerator = new TerrainGenerator(40);
//        terrainGenerator.generate();
//        ModelData terrainData = new ModelData(terrainGenerator.getVertices(),terrainGenerator.getIndices(),terrainGenerator.getTextureCoords(),terrainGenerator.getColour(),3);
//        Model terrainModel = new Model(loader.loadToVAO(terrainData),terrainData.getCount());
//        TerrainEntity terrain = new TerrainEntity(terrainModel);
//        renderer.addEntity(terrain);
        
        
        /** ================================================= **/
        

        /** ================================================= **/
        
        while (!glfwWindowShouldClose(DisplayManager.window) ) {
            renderer.render(shader, camera);
            

            //glBindVertexArray(cube.getModel().getVaoID());
            
            //System.out.println(cube.getModel().getVaoID());
            //Matrix4f transformationMatrix= Maths.createTransformationMatrix(cube.getPosition(),cube.getRotation(),cube.getScale());
            //shader.loadTransformationMatrix(transformationMatrix);
            //glDrawArrays(GL_TRIANGLES, 0, 12*3); 

        }
        shader.cleanUp();
        loader.cleanUP();
    }
    
    
}
