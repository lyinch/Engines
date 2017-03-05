package gui;

import org.joml.Vector3f;

/**
 * Created by backes on 05/03/17.
 */
public class GuiComponent {
    private GuiModel guiModel;
    private Vector3f position;
    
    public GuiComponent(GuiModel guiModel) {
        this.guiModel = guiModel;
        position = new Vector3f(0,0,0);
    }

    public GuiModel getGuiModel() {
        return guiModel;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(float x, float y, float z) {
        this.position.sub(x,y,z);
    }
}
