package utils;

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
    
    public static void createProjectionMatrix(){
        
    }
}
