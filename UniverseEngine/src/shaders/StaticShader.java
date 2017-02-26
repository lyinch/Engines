package shaders;


import entities.Camera;
import org.joml.Matrix4f;
import utils.Maths;

/**
 * Created by backes on 24/02/17.
 */
public class StaticShader extends Shader{
    private static final String VERTEX_FILE = "./src/shaders/VertexShader.vert";
    private static final String FRAGMENT_FILE = "./src/shaders/FragmentShader.frag";

    private int location_transformationMatrix;
    private int location_ProjectionMatrix;
    private int location_ViewMatrix;
    
    /**
     * creates the program from the vertex and fragment shader, and binds the attributes
     */
    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }
    
    @Override
    protected void getAllUniformLocations() {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
        location_ProjectionMatrix = super.getUniformLocation("projectionMatrix");
        location_ViewMatrix = super.getUniformLocation("viewMatrix");
    }

    /**
     * loads the transformation matrix to the vertex shader
     * @param matrix 4 Dimensional transformation matrix
     */
    public void loadTransformationMatrix(Matrix4f matrix){
        super.loadMatrix(location_transformationMatrix,matrix);
    }


    /**
     * Loads the projection Matrix. This projects the 3D object to the 2D plane. (scales it accordingly to 
     * it's Z coordinate)
     * @param projectionMatrix The 4 Dimensional projection matrix
     */
    public void loadProjectionMatrix(Matrix4f projectionMatrix){
        super.loadMatrix(location_ProjectionMatrix,projectionMatrix);
    }

    /**
     * Creates and loads the camera matrix
     * @param camera The camera object
     */
    public void loadViewMatrix(Camera camera){
        super.loadMatrix(location_ViewMatrix, Maths.createViewMatrix(camera));
    }
    
    
    
    
    @Override
    public void bindAttributes() {
        super.bindAttribute(0,"vertices");
        super.bindAttribute(1,"uvCoords");
        super.bindAttribute(2,"uvCoords");
    }

    
}
