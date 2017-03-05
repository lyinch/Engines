package gui;

/**
 * Created by backes on 05/03/17.
 */
public class GuiData {
    private float vertices[];
    private float colour[];

    public GuiData(float[] vertices, float[] colour) {
        this.vertices = vertices;
        this.colour = colour;
    }

    public float[] getVertices() {
        return vertices;
    }

    public float[] getColour() {
        return colour;
    }
}
