package renderer;

import core.Camera;
import shaders.EntityShader;
import shaders.WorldShader;

/**
 * Created by backes on 16/03/17.
 */
public class MasterRenderer {
    WorldShader worldShader;
    EntityShader entityShader;
    Camera camera;
    EntityRenderer entityRenderer;
    TileRenderer tileRenderer;

    public MasterRenderer(WorldShader worldShader, EntityShader entityShader, Camera camera, EntityRenderer entityRenderer, TileRenderer tileRenderer) {
        this.worldShader = worldShader;
        this.entityShader = entityShader;
        this.camera = camera;
        this.entityRenderer = entityRenderer;
        this.tileRenderer = tileRenderer;
    }
    
    public void render(){
        
    }
}
