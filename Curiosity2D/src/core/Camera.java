package core;

import entities.Player;
import org.joml.Vector3f;

/**
 * Created by backes on 19/03/17.
 */
public class Camera {
    private Vector3f position = new Vector3f(0,0,0);

    public Vector3f getPosition() {
        return position;
    }
    
    public void addX(){
        position.add(0.009f,0,0);
    }
    
    public void move(Player player){
        //this.position = new Vector3f(player.getPosition().x,player.getPosition().y,0.0f);
    }
}
