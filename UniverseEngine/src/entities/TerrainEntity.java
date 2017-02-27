package entities;

import models.Model;
import org.joml.Vector3f;
import textures.Texture;

/**
 * Created by backes on 26/02/17.
 */
public class TerrainEntity extends Entity {
    
    public TerrainEntity(Model model, Vector3f position, Vector3f rotation, float scale) {
        super(model, position, rotation, scale);
    }

    public TerrainEntity(Model model){
        super(model, new Vector3f(0,0,0), new Vector3f(0,0,0), 1f);
    }
}
