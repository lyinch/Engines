package utils;

import core.Camera;
import org.joml.Matrix4f;
import org.joml.Vector3f;

/**
 * Created by backes on 22/03/17.
 */
public class Math {
    
    /**
     * Creates the Camera Matrix. This matrix moves the world in the opposite direction, and thus creating the illusion
     * of a camera, therefore the negative position translation
     * @param camera The camera
     * @return The view Matrix
     */
    public static Matrix4f createViewMatrix(Camera camera){
        Matrix4f viewMatrix = new Matrix4f();
        Vector3f cameraPos = camera.getPosition();
        Vector3f negativeCameraPos = new Vector3f(-cameraPos.x,-cameraPos.y,-cameraPos.z);
        viewMatrix.translate(negativeCameraPos);
        return viewMatrix;
    }
    
    public static Matrix4f createProjectionMatrix(float scale){
        Matrix4f proj = new Matrix4f();
        proj.scale(scale);
        return proj;
    }
}
