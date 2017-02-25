package generation;

/**
 * Created by backes on 24/02/17.
 */
public class CubeGenerator implements Generator {
    @Override
    public float[] generate() {
        return new float[] {
                -1.0f, -1.0f, 0.0f,
                1.0f, -1.0f, 0.0f,
                0.0f, 1.0f, 0.0f};
    }
}
