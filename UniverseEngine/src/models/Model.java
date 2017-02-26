package models;

/**
 * Created by backes on 24/02/17.
 */
public class Model {
    private int vaoID;
    private int count;
    
    public Model(int vaoID, int count) {
        this.vaoID = vaoID;
        this.count = count;
    }

    public int getVaoID() {
        return vaoID;
    }

    public int getCount() {
        return count;
    }
}
