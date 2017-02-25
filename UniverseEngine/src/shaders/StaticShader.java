package shaders;


import org.joml.Matrix4f;

/**
 * Created by backes on 24/02/17.
 */
public class StaticShader extends Shader{
    private static final String VERTEX_FILE = "./src/shaders/VertexShader.vert";
    private static final String FRAGMENT_FILE = "./src/shaders/FragmentShader.frag";

    private int location_transformationMatrix;
    private int location_ProjectionMatrix;
    
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
    }

    /**
     * loads the transformation matrix to the vertex shader
     * @param matrix 4 Dimensional transformation matrix
     */
    public void loadTransformationMatrix(Matrix4f matrix){
        super.loadMatrix(location_transformationMatrix,matrix);
    }

    
    public void loadProjectionMatrix(Matrix4f projectionMatrix){
        super.loadMatrix(location_ProjectionMatrix,projectionMatrix);
    }
    
    @Override
    public void bindAttributes() {
        super.bindAttribute(0,"vertices");
    }

    
}
