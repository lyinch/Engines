package core;

import org.joml.Vector3f;

/**
 * Created by backes on 19/03/17.
 */
public class Camera {
    private Vector3f position = new Vector3f(1,1,0);

    public Vector3f getPosition() {
        return position;
    }
    
    public void addX(){
        position.add(0.05f,0,0);
    }
}
