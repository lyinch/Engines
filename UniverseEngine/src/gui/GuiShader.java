package gui;

import shaders.Shader;

/**
 * Created by backes on 04/03/17.
 */
public class GuiShader extends Shader {

    private static final String VERTEX_FILE = "./src/gui/GuiVertexShader.vert";
    private static final String FRAGMENT_FILE = "./src/gui/GuiFragmentShader.frag";

    /**
     * creates the program from the vertex and fragment shader, and binds the attributes
     */
    public GuiShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void getAllUniformLocations() {
        
    }

    @Override
    public void bindAttributes() {
        super.bindAttribute(0,"vertices");
        super.bindAttribute(1,"colour");
    }
}
