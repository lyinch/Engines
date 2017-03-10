package fsm;

import renderer.DisplayManager;
import utils.Input;

/**
 * Created by backes on 10/03/17.
 */
public class SidebarState implements State {
    @Override
    public void enter() {
        System.out.println("Sidebar Enter");
    }

    @Override
    public void update() {

    }

    @Override
    public State handleInput(Events e) {
        System.out.println("Sidebar State Input");
        float normX = ((2f*Input.mouseX)/ DisplayManager.getWIDTH()-1f);
        if (normX < 0.6)
            return StaticStates.gameState;
        return null;
    }
}
