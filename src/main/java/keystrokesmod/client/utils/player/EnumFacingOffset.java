package keystrokesmod.client.utils.player;


import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;


public class EnumFacingOffset {
    public static EnumFacing enumFacing;
    private final Vec3 offset;

    public EnumFacingOffset(final EnumFacing enumFacing, final Vec3 offset) {
        this.enumFacing = enumFacing;
        this.offset = offset;
    }

    public EnumFacing getEnumFacing(){
        return this.enumFacing = enumFacing;
    }
}