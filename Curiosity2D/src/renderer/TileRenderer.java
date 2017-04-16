package renderer;

import core.Camera;
import shaders.Shader;
import shaders.WorldShader;
import tileMap.TileMap;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

/**
 * Created by backes on 18/03/17.
 */
public class TileRenderer {
    List <TileMap> maps;
    WorldShader shader;
    Camera camera;
    
    public TileRenderer(WorldShader shader, Camera camera) {
        maps = new ArrayList<>();
        this.shader = shader;
        this.camera = camera;
        
    }
    
    public void addMap(TileMap map){
        maps.add(map);
    }

    public void render(){
        shader.start();
        for (TileMap map : maps){
            glBindVertexArray(map.getVaoID());
            shader.loadViewMatrix(camera);
            glDrawArrays(GL_TRIANGLES,0, map.getVertices().length);
        }
        shader.stop();
    }
}
