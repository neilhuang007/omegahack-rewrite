package keystrokesmod.client.utils.player;


import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;
import org.lwjgl.util.vector.Vector2f;

public class MovementUtils {
    static Minecraft mc = Minecraft.getMinecraft();

    public static final float PI2 = (float) (Math.PI * 2D);
    public static final float PId2 = (float) (Math.PI / 2D);

    public static float getMoveYaw(float yaw) {
        Vector2f from = new Vector2f((float) mc.thePlayer.lastTickPosX, (float) mc.thePlayer.lastTickPosZ),
                to = new Vector2f((float) mc.thePlayer.posX, (float) mc.thePlayer.posZ),
                diff = new Vector2f(to.x - from.x, to.y - from.y);

        double x = diff.x, z = diff.y;
        if (x != 0 && z != 0) {
            yaw = (float) Math.toDegrees((Math.atan2(-x, z) + PI2) % PI2);
        }
        return yaw;
    }


}
