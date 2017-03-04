package entities;

import models.Model;
import org.joml.Vector3f;
import renderer.DisplayManager;
import renderer.Loader;
import textures.Texture;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.glfwGetMouseButton;

/**
 * Created by backes on 26/02/17.
 */
public class TerrainEntity extends Entity {
    
    public TerrainEntity(Model model, Vector3f position, Vector3f rotation, float scale) {
        super(model, position, rotation, scale);
    }

    public TerrainEntity(Model model){
        super(model, new Vector3f(0,0,0), new Vector3f(0,0,0), 1f);
    }


    /**
     * Marks the visited quad with white vertices
     * @param point The point on the ground, coming from the raycast
     * @param length The dimension of a single terrain quad
     * @param width the total nbr. of the quads on the X direction
     * @param height the total nbr. of the quads on the Y direction
     * @param loader 
     * @param data A deep copy of the colour array, to prevent visited quads from staying marked
     */
    public void markQuad(Vector3f point, int length, int width, int height, Loader loader, float[] data){
        int gridX = (int)(point.x/length);
        int gridZ = (int)(point.z/length);
        
        //The positions are the array positions of the data (vertices & colour information, not indices)
        //position of the two lower vertices
        int pos = (gridX*width+gridZ+gridX)*3;
        //position of the two upper vertices
        int pos2 = ((gridX+1) * width + gridZ + (gridX+1)) * 3;
        
        Vector3f colour = new Vector3f(1,1,1);
        try {
            data[pos + 0] = colour.x;
            data[pos + 1] = colour.y;
            data[pos + 2] = colour.z;

            data[pos + 3] = colour.x;
            data[pos + 4] = colour.y;
            data[pos + 5] = colour.z;

            data[pos2 + 0] = colour.x;
            data[pos2 + 1] = colour.y;
            data[pos2 + 2] = colour.z;

            data[pos2 + 3] = colour.x;
            data[pos2 + 4] = colour.y;
            data[pos2 + 5] = colour.z;
        }catch (ArrayIndexOutOfBoundsException e){
            
        }
        loader.updateVBO(data,this.getModel().getColourVBO());

    }

    public void modifyQuad(Vector3f point, int length, int width, int height, Loader loader, float[] data){
        int gridX = (int)(point.x/length);
        int gridZ = (int)(point.z/length);

        //The positions are the array positions of the data (vertices & colour information, not indices)
        //position of the two lower vertices
        int pos = (gridX*width+gridZ+gridX)*3;
        //position of the two upper vertices
        int pos2 = ((gridX+1) * width + gridZ + (gridX+1)) * 3;

        float h = 0.8f;
        try {
            if (glfwGetMouseButton(DisplayManager.window,GLFW_MOUSE_BUTTON_RIGHT) == GLFW_TRUE) {    
                data[pos+1]+=h;
                data[pos+4]+=h;
        
                data[pos2+1]+=h;
                data[pos2+4]+=h;
                loader.updateVBO(data, this.getModel().getVerticesVBO());
        
            }
        }catch (ArrayIndexOutOfBoundsException e){

        }
    }
    
    private void stuff(){
        
    }
}
