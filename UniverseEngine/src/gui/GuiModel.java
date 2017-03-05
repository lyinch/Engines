package gui;

/**
 * Created by backes on 05/03/17.
 */
public abstract class GuiModel {
    private GuiData data;
    private int vaoID;

    public GuiModel(GuiData data) {
        this.data = data;
    }

    public int getVaoID() {
        return vaoID;
    }

    public GuiData getData() {
        return data;
    }
}
