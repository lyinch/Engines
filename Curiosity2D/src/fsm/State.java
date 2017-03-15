package fsm;

/**
 * Created by backes on 10/03/17.
 */
public interface State {
    void enter();
    void update();
    State handleInput(Events e);
}
