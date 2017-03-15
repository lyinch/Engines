package fsm;


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
        return null;
    }
}
