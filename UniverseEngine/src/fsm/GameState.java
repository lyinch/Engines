package fsm;

import renderer.DisplayManager;
import utils.Input;

/**
 * Created by backes on 10/03/17.
 */
public class GameState implements State {
    @Override
    public void enter() {
        System.out.println("GameState Enter");
    }

    @Override
    public void update() {

    }

    @Override
    public State handleInput(Events e) {
        System.out.println("Game State Input");
        float normX = ((2f*Input.mouseX)/ DisplayManager.getWIDTH()-1f);
        if (normX >= 0.6)
            return StaticStates.sidebarState;
        return null;
    }
}
