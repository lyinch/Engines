package utils;

import core.Camera;
import org.joml.Matrix4f;
import org.joml.Vector2f;
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
        Vector2f cameraPosOri = camera.getPosition();
        Vector3f cameraPos =  new Vector3f(cameraPosOri.x,cameraPosOri.y,0.0f);
        Vector3f negativeCameraPos = new Vector3f(-cameraPos.x,-cameraPos.y,-cameraPos.z);
        viewMatrix.translate(negativeCameraPos);
        return viewMatrix;
    }

    /**
     * Creates a projection matrix, which modifies the whole world (i.e. scaling, rotating)
     * @param scale The scale to modify the world
     * @return
     */
    public static Matrix4f createProjectionMatrix(float scale){
        Matrix4f proj = new Matrix4f();
        proj.scale(scale);
        return proj;
    }

    /**
     * Clamps the value between min and max
     * @param var
     * @param min
     * @param max
     * @param <T>
     * @return
     */
    public static <T extends Comparable<? super T>> T clamp(T var, T min, T max){
        if(var.compareTo(max) > 0)
            return max;
        else if (var.compareTo(min) < 0)
            return min;
        return var;
    }

    /**
     * Creates the transformation matrix of the position, the rotation and the scale
     * @param position The new position
     * @param rotation The rotation in degrees of the three axis
     * @param scale The scale
     * @return 4 Dimensional Transformation Matrix
     * https://github.com/JOML-CI/JOML/wiki/JOML-and-modern-OpenGL
     */
    public static Matrix4f createTransformationMatrix(Vector2f position, Vector2f rotation, float scale){
        return new Matrix4f().translate(new Vector3f(position.x,position.y,0))
                .rotate((float) org.joml.Math.toRadians(rotation.x), new Vector3f(1,0,0))
                .rotate((float) org.joml.Math.toRadians(rotation.y), new Vector3f(0,1,0))
                .scale(scale);
    }
    
}
