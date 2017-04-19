package curiosity;

/**
 * Created by backes on 18/04/17.
 */
public class World {
    public final static int PIXELS = 128;
    public final static int WIDTH = 32;
    public final static int HEIGHT = 40;
    public static enum TILE_TYPES {NONE,DIRT,ORE_EASY,ORE_HEAVY};
    public final static int TILE_TYPES_NUM = TILE_TYPES.values().length;
}
