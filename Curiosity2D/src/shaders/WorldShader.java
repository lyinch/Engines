package shaders;


import core.Camera;
import utils.Math;

/**
 * Created by backes on 22/03/17.
 */
public class WorldShader extends Shader {

    private static final String VERTEX_FILE = "./src/shaders/vertexShader.vert";
    private static final String FRAGMENT_FILE = "./src/shaders/fragmentShader.frag";

    
    private int location_viewMatrix;
    private int location_projectionMatrix;

    /**
     * creates the program from the vertex and fragment shader, and binds the attributes
     */
    public WorldShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }
    
    public void loadViewMatrix(Camera camera){
        super.loadMatrix(location_viewMatrix, Math.createViewMatrix(camera));
        super.loadMatrix(location_projectionMatrix, Math.createProjectionMatrix(1/3f));
    }

    @Override
    protected void getAllUniformLocations() {
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
    }

    @Override
    public void bindAttributes() {
        super.bindAttribute(0,"vertices");
        super.bindAttribute(1,"colour");
        super.bindAttribute(2,"t");
    }
}
