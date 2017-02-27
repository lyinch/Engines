package utils;

import entities.Camera;
import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector3f;

/**
 * Created by backes on 25/02/17.
 */
public class Maths {

    /**
     * Creates the transformation matrix of the position, the rotation and the scale
     * @param position The new position
     * @param rotation The rotation in degrees of the three axis
     * @param scale The scale
     * @return 4 Dimensional Transformation Matrix
     * https://github.com/JOML-CI/JOML/wiki/JOML-and-modern-OpenGL
     */
    public static Matrix4f createTransformationMatrix(Vector3f position, Vector3f rotation, float scale){
        return new Matrix4f().translate(position)
                .rotate((float)Math.toRadians(rotation.x), new Vector3f(1,0,0))
                .rotate((float)Math.toRadians(rotation.y), new Vector3f(0,1,0))
                .rotate((float)Math.toRadians(rotation.z), new Vector3f(0,0,1))
                .scale(scale);
    }

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
        viewMatrix.rotate((float)Math.toRadians(camera.getPitch()), new Vector3f(1,0,0))
                .rotate((float)Math.toRadians(camera.getYaw()), new Vector3f(0,1,0))
                .rotate((float)Math.toRadians(camera.getRoll()), new Vector3f(0,0,1))
                .translate(negativeCameraPos);
        return viewMatrix;
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
}
