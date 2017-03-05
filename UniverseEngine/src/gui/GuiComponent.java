package gui;

/**
 * Created by backes on 05/03/17.
 */
public class GuiComponent {
    private GuiModel guiModel;

    public GuiComponent(GuiModel guiModel) {
        this.guiModel = guiModel;
    }

    public GuiModel getGuiModel() {
        return guiModel;
    }
}
