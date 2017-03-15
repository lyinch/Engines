package fsm;

/**
 * Created by backes on 10/03/17.
 */
public class FSM implements Observer{
    private State currentState;

    public FSM(State currentState) {
        this.currentState = currentState;
    }

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    @Override
    public void onNotify(Events e) {
        handleInput(e);
    }
    
    public void update(){
        currentState.update();
    }
    
    public void handleInput(Events e){
        State tmp = currentState.handleInput(e);
        if (tmp != null){
            currentState = tmp;
            currentState.enter();
        }
    }
}
