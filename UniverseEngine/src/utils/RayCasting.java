package utils;

import entities.Camera;
import org.joml.*;
import renderer.DisplayManager;
import renderer.MasterRenderer;

/**
 * Created by backes on 01/03/17.
 */
public class RayCasting {

    /**
     * Casts a ray.
     * The Coordinate System in openGL: https://www.youtube.com/watch?v=pQcC2CqReSA
     * Details how the coordinates are reverted back from all the transformations: http://antongerdelan.net/opengl/raycasting.html
     * ThinMatrix Mouse Picker: https://www.youtube.com/watch?v=DLKN0jExRIM
     * @param camera
     * @param renderer
     */
//    public static void ray(Camera camera, MasterRenderer renderer){
//
//
//
//        //(0,0) is at the top left
//
//        //System.out.println("[" + Input.mouseX + "," + Input.mouseY + "]");
//
//        //convert mouse coords to 3D normalized device coordinates:
//        float x = (2f*Input.mouseX)/ DisplayManager.getWIDTH()-1f;
//        float y = (2*Input.mouseY)/DisplayManager.getHEIGHT()-1f;
//        float z = 1f;
//        Vector3f ray_nds = new Vector3f(x,y,z);
//        //System.out.println("[" + x + "," + y + "," + z + "]");
//
//        //4D Homogenous Clip Coordinates
//        //The Z coordinate points forward (negative in openGL) 
//        Vector4f ray_clip = new Vector4f(ray_nds.x,ray_nds.y,-1.0f,1.0f);
//
//        //4D eye (what the camera sees Camera)we inverse the projection
//        Matrix4f projectionMatrix = renderer.createProjectionMatrix();
//        projectionMatrix.invert();
//        Vector4f ray_eye = projectionMatrix.transform(ray_clip);
//        ray_eye = new Vector4f(ray_eye.x,ray_eye.y,-1.0f,0.0f);
//
//        //We reverse the view matrix (camera)
//        Matrix4f view = Maths.createViewMatrix(camera);
//        view.invert();
//        Vector4f tmp = new Vector4f(view.transform(ray_eye));
//        Vector3f ray_world = new Vector3f(tmp.x,tmp.y,tmp.z);
//        ray_world.normalize();
//
//
//        Vector3f camPos = camera.getPosition();
//        Vector3f start = new Vector3f(camPos.x, camPos.y, camPos.z);
//        Vector3f scaledRay = new Vector3f(ray_world.x * 0.4f, ray_world.y * 0.4f, ray_world.z * 0.4f);
//        Vector3f p = start.add(scaledRay);
//
//
//        //flat plane normal: (1,1,0);
//        double t = -p.y/ray_world.y;
//        double x_c = ray_world.x+p.x*t;
//        double y_c = ray_world.z+p.z*t;
//
//        System.out.println(x_c + " : " + y_c);
//    }
//    
    private Vector3f currentRay;

    private Matrix4f projectionMatrix;
    private Matrix4f viewMatrix;

    private Camera camera;
    private MasterRenderer masterRenderer;

    public RayCasting(Camera camera, MasterRenderer masterRenderer) {
        this.camera = camera;
        this.masterRenderer = masterRenderer;
        this.projectionMatrix = masterRenderer.createProjectionMatrix();
        this.viewMatrix = Maths.createViewMatrix(camera);
    }

    public void update(){
        this.viewMatrix = Maths.createViewMatrix(camera);
        currentRay = calculateMouseRay();
        
        Vector3f p = getPointOnRay(currentRay,10);
        p = camera.getPosition();
        double t = -p.y/currentRay.y;
        double x_c = p.x+currentRay.x*t;
        double z_c = p.z+currentRay.z*t;
        System.out.println(currentRay);
        System.out.println(x_c + " : " + z_c);
        //System.out.println(calculateMouseRay());
    }

    private Vector3f calculateMouseRay(){
        //Normalized Device Coordinates
        float x = (2f*Input.mouseX)/ DisplayManager.getWIDTH()-1f;
        float y = 1-(2f*Input.mouseY)/DisplayManager.getHEIGHT();

        Vector2f normalizedCoords = new Vector2f(x,y);
        Vector4f clipCoords = new Vector4f(normalizedCoords.x,normalizedCoords.y,-1f,1);
        Vector4f eyeCoords = toEyeCoords(clipCoords);
        return toWorldCoords(eyeCoords);
    }
    
    private Vector3f toWorldCoords(Vector4f eyeCoords){
        Matrix4f invertedView = new Matrix4f();
        viewMatrix.invert(invertedView);
        Vector4f rayWorld = invertedView.transform(eyeCoords);
        return new Vector3f(rayWorld.x,rayWorld.y,rayWorld.z).normalize();
    }
    
    private Vector4f toEyeCoords(Vector4f clipCoords){
        Matrix4f invertedProjection = new Matrix4f();
        projectionMatrix.invert(invertedProjection);
//        System.out.println(projectionMatrix);
//        System.out.println(invertedProjection);
        
        Vector4f eyeCoords = new Vector4f();
        invertedProjection.transform(clipCoords,eyeCoords);
        return new Vector4f(eyeCoords.x,eyeCoords.y,-1f,0f);
    }

    public Vector3f getCurrentRay() {
        return currentRay;
    }
    
    private Vector3f getPointOnRay(Vector3f ray, float distance){
        Vector3f camPos = camera.getPosition();
        Vector3f start = new Vector3f(camPos.x, camPos.y, camPos.z);
        Vector3f scaledRay = new Vector3f(ray.x * distance, ray.y * distance, ray.z * distance);
        return start.add(scaledRay);
    }
}
