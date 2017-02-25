package entities;

import models.Model;
import org.joml.Vector3f;

/**
 * Created by backes on 25/02/17.
 */
public class CubeEntity extends Entity {

    public CubeEntity(Model model, Vector3f position, Vector3f rotation, float scale) {
        super(model, position, rotation, scale);
    }
    
    public CubeEntity(Model model){
        super(model, new Vector3f(0,0,-1), new Vector3f(0,0,0), 0.5f);
    }
}
