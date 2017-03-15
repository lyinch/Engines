package fsm;

/**
 * Created by backes on 10/03/17.
 */
public interface Subject {
    void addObserver(Observer o);
    void removeObserver(Observer o);
}
