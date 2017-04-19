package core;

import entities.Player;
import org.joml.Vector2f;
import org.joml.Vector3f;

import static curiosity.World.HEIGHT;
import static curiosity.World.PIXELS;
import static curiosity.World.WIDTH;

/**
 * Created by backes on 19/03/17.
 */
public class Camera {
    private Vector2f position = new Vector2f(0,0);

    public Vector2f getPosition() {
        return position;
    }
    
    
    public void move(Player player){
        float x = player.getPosition().x-1;

        float y = player.getPosition().y+1;

        float xMax = (WIDTH*(PIXELS/(float)DisplayManager.getWIDTH()))-2;
        float yMax = -(HEIGHT*(PIXELS/(float)DisplayManager.getHEIGHT()))+2;
        if (x < 0)
            x = 0;
        if(y > 0)
            y = 0;
        if (x > xMax)
            x = xMax;
        if(y < yMax)
            y = yMax;
        this.position = new Vector2f(x,y);
    }
}
