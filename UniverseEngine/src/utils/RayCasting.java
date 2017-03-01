package utils;

import entities.Camera;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.joml.Vector4fc;
import renderer.DisplayManager;
import renderer.MasterRenderer;

/**
 * Created by backes on 01/03/17.
 */
public class RayCasting {
    public static void ray(Camera camera, MasterRenderer renderer){
        //(0,0) is at the top left
        
        //System.out.println("[" + Input.mouseX + "," + Input.mouseY + "]");
        
        //convert mouse coords to 3D normalized device coordinates:
        float x = (2f*Input.mouseX)/ DisplayManager.getWIDTH()-1f;
        float y = 1f-(2*Input.mouseY)/DisplayManager.getHEIGHT();
        float z = 1f;
        Vector3f ray_nds = new Vector3f(x,y,z);
        //System.out.println("[" + x + "," + y + "," + z + "]");
        
        //4D Homogenous Clip Coordinates
        //The Z coordinate points forward (negative in openGL) 
        Vector4f ray_clip = new Vector4f(ray_nds.x,ray_nds.y,-1.0f,1.0f);
        
        //4D eye (what the camera sees Camera)we inverse the projection
        Matrix4f projectionMatrix = renderer.createProjectionMatrix();
        projectionMatrix.invert();
        Vector4f ray_eye = projectionMatrix.transform(ray_clip);
        ray_eye = new Vector4f(ray_eye.x,ray_eye.y,-1.0f,0.0f);
        
        //We reverse the view matrix (camera)
        Matrix4f view = Maths.createViewMatrix(camera);
        view.invert();
        Vector4f tmp = new Vector4f(view.transform(ray_eye));
        Vector3f ray_world = new Vector3f(tmp.x,tmp.y,tmp.z);
        ray_world.normalize();


        System.out.println(ray_world);
    }
}
