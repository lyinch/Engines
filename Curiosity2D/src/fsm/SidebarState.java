package fsm;

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
        return null;
    }
}
