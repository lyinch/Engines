package textures;


/**
 * Created by backes on 26/02/17.
 */
public class Texture {
    private int width, height;
    private int textureID;

    public Texture(int textureID, int width, int height) {
        this.width = width;
        this.height = height;
        this.textureID = textureID;
    }
    
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getTextureID() {
        return textureID;
    }
}
