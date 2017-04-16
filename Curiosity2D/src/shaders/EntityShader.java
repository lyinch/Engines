package shaders;


import core.Camera;
import org.joml.Matrix4f;
import utils.Math;

/**
 * Created by backes on 22/03/17.
 */
public class EntityShader extends Shader {

    private static final String VERTEX_FILE = "./src/shaders/entityVertexShader.vert";
    private static final String FRAGMENT_FILE = "./src/shaders/entityFragmentShader.frag";

    
    private int location_viewMatrix;
    private int location_projectionMatrix;
    private int location_transformationMatrix;

    /**
     * creates the program from the vertex and fragment shader, and binds the attributes
     */
    public EntityShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }
    
    public void loadViewMatrix(Camera camera){
        super.loadMatrix(location_viewMatrix, Math.createViewMatrix(camera));
        super.loadMatrix(location_projectionMatrix, Math.createProjectionMatrix(1/3f));
    }

    /**
     * loads the transformation matrix to the vertex shader
     * @param matrix 4 Dimensional transformation matrix
     */
    public void loadTransformationMatrix(Matrix4f matrix){
        super.loadMatrix(location_transformationMatrix,matrix);
    }


    @Override
    protected void getAllUniformLocations() {
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
    }
    
    

    @Override
    public void bindAttributes() {
        super.bindAttribute(0,"vertices");
        super.bindAttribute(1,"colour");
        super.bindAttribute(2,"t");
    }
}
