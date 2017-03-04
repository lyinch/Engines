package utils;

import entities.Camera;
import org.joml.*;
import renderer.DisplayManager;
import renderer.MasterRenderer;

/**
 * Created by backes on 01/03/17.
 */
public class RayCasting {

    private Vector3f ray;

    private Matrix4f projectionMatrix;
    private Matrix4f viewMatrix;

    private Camera camera;

    /**
     * Casts a ray; gives us a normalized direction vector from the camera trough the mouse, thus the camera pos.
     * is a point lying on this line.
     * 
     * The Coordinate System in openGL: https://www.youtube.com/watch?v=pQcC2CqReSA
     * Details how the coordinates are reverted back from all the transformations: http://antongerdelan.net/opengl/raycasting.html
     * ThinMatrix Mouse Picker: https://www.youtube.com/watch?v=DLKN0jExRIM
     * 
     * @param camera
     * @param masterRenderer
     */
    public RayCasting(Camera camera, MasterRenderer masterRenderer) {
        this.camera = camera;
        this.projectionMatrix = masterRenderer.createProjectionMatrix();
        this.viewMatrix = Maths.createViewMatrix(camera);
    }

    public void update(){
        
        this.viewMatrix = Maths.createViewMatrix(camera);
        ray = mouseRay();
    }

    /**
     * Creates a with the help of a directional vector (the ray) and a starting point, and then calculating the intersection
     * coordinates x,z at the point y=0, i.e. the ground plane.
     * https://math.oregonstate.edu/home/programs/undergrad/CalculusQuestStudyGuides/vcalc/lineplane/lineplane.html
     * @return
     */
    public Vector3f getPointOnGround(){
        Vector3f p = camera.getPosition();
        double t = -p.y/ray.y;
        double x_c = p.x+ray.x*t;
        double z_c = p.z+ray.z*t;
        return new Vector3f((float) x_c,0,(float)z_c);
    }


    private Vector3f mouseRay(){
        //Normalized Device Coordinates
        float x = (2f*Input.mouseX)/ DisplayManager.getWIDTH()-1f;
        float y = 1-(2f*Input.mouseY)/DisplayManager.getHEIGHT();

        Vector2f normalizedCoords = new Vector2f(x,y);
        //The z direction should point forward -> -1 (LHS coordinate system)
        Vector4f clipCoords = new Vector4f(normalizedCoords.x,normalizedCoords.y,-1f,1);
        
        Vector4f eyeSpcae = toEyeSpace(clipCoords);
        
        return toWorldSpace(eyeSpcae);
    }

    /**
     * We invert the view Matrix, i.e. the Camera
     * @param eyeCoords
     * @return
     */
    private Vector3f toWorldSpace(Vector4f eyeCoords){
        Matrix4f invertedView = new Matrix4f();
        viewMatrix.invert(invertedView);
        Vector4f rayWorld = invertedView.transform(eyeCoords);
        return new Vector3f(rayWorld.x,rayWorld.y,rayWorld.z).normalize();
    }

    /**
     * We invert the projection
     * @param clipCoords
     * @return
     */
    private Vector4f toEyeSpace(Vector4f clipCoords){
        Matrix4f invertedProjection = new Matrix4f();
        projectionMatrix.invert(invertedProjection);
        Vector4f eyeCoords = new Vector4f();
        invertedProjection.transform(clipCoords,eyeCoords);
        return new Vector4f(eyeCoords.x,eyeCoords.y,-1f,0f);
    }

    public Vector3f getRay() {
        return ray;
    }
}
