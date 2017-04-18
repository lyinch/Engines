package core;

import entities.Player;
import org.joml.Vector2f;
import org.joml.Vector3f;

/**
 * Created by backes on 19/03/17.
 */
public class Camera {
    private Vector2f position = new Vector2f(0,0);

    public Vector2f getPosition() {
        return position;
    }
    
    
    public void move(Player player){
        float x = player.getPosition().x-((float)DisplayManager.getWIDTH())/(float)DisplayManager.getWIDTH();

        float y = player.getPosition().y+((float)DisplayManager.getHEIGHT())/(float)DisplayManager.getHEIGHT();
        if (x < 0)
            x = 0;
        if(y > 0)
            y = 0;
        this.position = new Vector2f(x,y);
        //System.out.println(this.position);
    }
}
