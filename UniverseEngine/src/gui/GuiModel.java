package gui;

/**
 * Created by backes on 05/03/17.
 */
public class GuiModel {
    private GuiData data;
    private int vaoID;
    private int count;
    
    public GuiModel(GuiData data, int vaoID, int count) {
        this.data = data;
        this.count = count;
        this.vaoID = vaoID;
    }

    public int getVaoID() {
        return vaoID;
    }

    public GuiData getData() {
        return data;
    }

    public int getCount() {
        return count;
    }
}
