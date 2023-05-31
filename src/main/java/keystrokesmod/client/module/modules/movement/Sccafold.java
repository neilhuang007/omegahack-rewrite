package keystrokesmod.client.module.modules.movement;

import keystrokesmod.client.events.EventHandler;
import keystrokesmod.client.events.world.EventPreUpdate;
import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.DescriptionSetting;
import keystrokesmod.client.module.setting.impl.DoubleSliderSetting;
import keystrokesmod.client.module.setting.impl.SliderSetting;
import keystrokesmod.client.module.setting.impl.TickSetting;
import keystrokesmod.client.utils.CoolDown;
import keystrokesmod.client.utils.Rotation;
import keystrokesmod.client.utils.TimerUtil;
import keystrokesmod.client.utils.Utils;
import keystrokesmod.client.utils.client.PacketUtil;
import keystrokesmod.client.utils.player.EnumFacingOffset;
import keystrokesmod.client.utils.player.InventoryUtils;
import net.minecraft.block.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.*;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import javax.vecmath.Vector2f;

import static keystrokesmod.client.utils.Rotation.getRotations;
import static net.minecraft.util.EnumFacing.*;

public class Sccafold extends Module {

    private static final Rotation rotation = new Rotation(999.0f, 999.0f);

    public static TickSetting doShift;

    private CoolDown placedelay = new CoolDown(0);

    public static TickSetting blocksOnly;

    public static SliderSetting Yawoffset;
    public static TickSetting placeblocks, legit;

    public static TickSetting Aim, head;
    public static DoubleSliderSetting shiftTime, placeblockdelay, rotationspeed;

    public static boolean onHold = false;

    public static DoubleSliderSetting pitchRange;

    public Sccafold() {
        super("scaffold", ModuleCategory.movement);
        this.registerSetting(shiftTime = new DoubleSliderSetting("Shift time: (s)", 140, 200, 0, 280, 5));
        this.registerSetting(placeblockdelay = new DoubleSliderSetting("placement delay", 140, 200, 0, 280, 5));
        this.registerSetting(rotationspeed = new DoubleSliderSetting("rotation speed", 5, 10, 0, 10, 1));
        this.registerSetting(Yawoffset = new SliderSetting("Yaw Offset", 0, -45, 45, 45));
        this.registerSetting(legit = new TickSetting("legit", true));

    }


    // enable and disable
    public void onEnable() {
    }


    // the place function and its supporting functions

    //the variables

    private static boolean shouldBridge = false;


    private EnumFacingOffset enumFacing;

    private BlockPos blockFace;

    private BlockData data;

    //place func

    public void place() {
        if (data != null) {
            if (placedelay.hasFinished()) {
                mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getCurrentEquippedItem(), this.data.getBlockPos(), this.data.getEnumFacing(), getVec3(this.data.getBlockPos(), this.data.getEnumFacing()));
                mc.thePlayer.swingItem();
            }
        }
    }

    //supporting func

    private BlockData getBlockData(BlockPos pos) {
        if (this.isPosSolid(pos.add(0, -1, 0))) {
            return new BlockData(pos.add(0, -1, 0), EnumFacing.UP);
        }
        if (this.isPosSolid(pos.add(-1, 0, 0))) {
            return new BlockData(pos.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (this.isPosSolid(pos.add(1, 0, 0))) {
            return new BlockData(pos.add(1, 0, 0), EnumFacing.WEST);
        }
        if (this.isPosSolid(pos.add(0, 0, 1))) {
            return new BlockData(pos.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (this.isPosSolid(pos.add(0, 0, -1))) {
            return new BlockData(pos.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos1 = pos.add(-1, 0, 0);
        if (this.isPosSolid(pos1.add(0, -1, 0))) {
            return new BlockData(pos1.add(0, -1, 0), EnumFacing.UP);
        }
        if (this.isPosSolid(pos1.add(-1, 0, 0))) {
            return new BlockData(pos1.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (this.isPosSolid(pos1.add(1, 0, 0))) {
            return new BlockData(pos1.add(1, 0, 0), EnumFacing.WEST);
        }
        if (this.isPosSolid(pos1.add(0, 0, 1))) {
            return new BlockData(pos1.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (this.isPosSolid(pos1.add(0, 0, -1))) {
            return new BlockData(pos1.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos2 = pos.add(1, 0, 0);
        if (this.isPosSolid(pos2.add(0, -1, 0))) {
            return new BlockData(pos2.add(0, -1, 0), EnumFacing.UP);
        }
        if (this.isPosSolid(pos2.add(-1, 0, 0))) {
            return new BlockData(pos2.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (this.isPosSolid(pos2.add(1, 0, 0))) {
            return new BlockData(pos2.add(1, 0, 0), EnumFacing.WEST);
        }
        if (this.isPosSolid(pos2.add(0, 0, 1))) {
            return new BlockData(pos2.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (this.isPosSolid(pos2.add(0, 0, -1))) {
            return new BlockData(pos2.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos3 = pos.add(0, 0, 1);
        if (this.isPosSolid(pos3.add(0, -1, 0))) {
            return new BlockData(pos3.add(0, -1, 0), EnumFacing.UP);
        }
        if (this.isPosSolid(pos3.add(-1, 0, 0))) {
            return new BlockData(pos3.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (this.isPosSolid(pos3.add(1, 0, 0))) {
            return new BlockData(pos3.add(1, 0, 0), EnumFacing.WEST);
        }
        if (this.isPosSolid(pos3.add(0, 0, 1))) {
            return new BlockData(pos3.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (this.isPosSolid(pos3.add(0, 0, -1))) {
            return new BlockData(pos3.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos4 = pos.add(0, 0, -1);
        if (this.isPosSolid(pos4.add(0, -1, 0))) {
            return new BlockData(pos4.add(0, -1, 0), EnumFacing.UP);
        }
        if (this.isPosSolid(pos4.add(-1, 0, 0))) {
            return new BlockData(pos4.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (this.isPosSolid(pos4.add(1, 0, 0))) {
            return new BlockData(pos4.add(1, 0, 0), EnumFacing.WEST);
        }
        if (this.isPosSolid(pos4.add(0, 0, 1))) {
            return new BlockData(pos4.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (this.isPosSolid(pos4.add(0, 0, -1))) {
            return new BlockData(pos4.add(0, 0, -1), EnumFacing.SOUTH);
        }
        if (this.isPosSolid(pos1.add(0, -1, 0))) {
            return new BlockData(pos1.add(0, -1, 0), EnumFacing.UP);
        }
        if (this.isPosSolid(pos1.add(-1, 0, 0))) {
            return new BlockData(pos1.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (this.isPosSolid(pos1.add(1, 0, 0))) {
            return new BlockData(pos1.add(1, 0, 0), EnumFacing.WEST);
        }
        if (this.isPosSolid(pos1.add(0, 0, 1))) {
            return new BlockData(pos1.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (this.isPosSolid(pos1.add(0, 0, -1))) {
            return new BlockData(pos1.add(0, 0, -1), EnumFacing.SOUTH);
        }
        if (this.isPosSolid(pos2.add(0, -1, 0))) {
            return new BlockData(pos2.add(0, -1, 0), EnumFacing.UP);
        }
        if (this.isPosSolid(pos2.add(-1, 0, 0))) {
            return new BlockData(pos2.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (this.isPosSolid(pos2.add(1, 0, 0))) {
            return new BlockData(pos2.add(1, 0, 0), EnumFacing.WEST);
        }
        if (this.isPosSolid(pos2.add(0, 0, 1))) {
            return new BlockData(pos2.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (this.isPosSolid(pos2.add(0, 0, -1))) {
            return new BlockData(pos2.add(0, 0, -1), EnumFacing.SOUTH);
        }
        if (this.isPosSolid(pos3.add(0, -1, 0))) {
            return new BlockData(pos3.add(0, -1, 0), EnumFacing.UP);
        }
        if (this.isPosSolid(pos3.add(-1, 0, 0))) {
            return new BlockData(pos3.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (this.isPosSolid(pos3.add(1, 0, 0))) {
            return new BlockData(pos3.add(1, 0, 0), EnumFacing.WEST);
        }
        if (this.isPosSolid(pos3.add(0, 0, 1))) {
            return new BlockData(pos3.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (this.isPosSolid(pos3.add(0, 0, -1))) {
            return new BlockData(pos3.add(0, 0, -1), EnumFacing.SOUTH);
        }
        if (this.isPosSolid(pos4.add(0, -1, 0))) {
            return new BlockData(pos4.add(0, -1, 0), EnumFacing.UP);
        }
        if (this.isPosSolid(pos4.add(-1, 0, 0))) {
            return new BlockData(pos4.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (this.isPosSolid(pos4.add(1, 0, 0))) {
            return new BlockData(pos4.add(1, 0, 0), EnumFacing.WEST);
        }
        if (this.isPosSolid(pos4.add(0, 0, 1))) {
            return new BlockData(pos4.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (this.isPosSolid(pos4.add(0, 0, -1))) {
            return new BlockData(pos4.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos5 = pos.add(0, -1, 0);
        if (this.isPosSolid(pos5.add(0, -1, 0))) {
            return new BlockData(pos5.add(0, -1, 0), EnumFacing.UP);
        }
        if (this.isPosSolid(pos5.add(-1, 0, 0))) {
            return new BlockData(pos5.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (this.isPosSolid(pos5.add(1, 0, 0))) {
            return new BlockData(pos5.add(1, 0, 0), EnumFacing.WEST);
        }
        if (this.isPosSolid(pos5.add(0, 0, 1))) {
            return new BlockData(pos5.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (this.isPosSolid(pos5.add(0, 0, -1))) {
            return new BlockData(pos5.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos6 = pos5.add(1, 0, 0);
        if (this.isPosSolid(pos6.add(0, -1, 0))) {
            return new BlockData(pos6.add(0, -1, 0), EnumFacing.UP);
        }
        if (this.isPosSolid(pos6.add(-1, 0, 0))) {
            return new BlockData(pos6.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (this.isPosSolid(pos6.add(1, 0, 0))) {
            return new BlockData(pos6.add(1, 0, 0), EnumFacing.WEST);
        }
        if (this.isPosSolid(pos6.add(0, 0, 1))) {
            return new BlockData(pos6.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (this.isPosSolid(pos6.add(0, 0, -1))) {
            return new BlockData(pos6.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos7 = pos5.add(-1, 0, 0);
        if (this.isPosSolid(pos7.add(0, -1, 0))) {
            return new BlockData(pos7.add(0, -1, 0), EnumFacing.UP);
        }
        if (this.isPosSolid(pos7.add(-1, 0, 0))) {
            return new BlockData(pos7.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (this.isPosSolid(pos7.add(1, 0, 0))) {
            return new BlockData(pos7.add(1, 0, 0), EnumFacing.WEST);
        }
        if (this.isPosSolid(pos7.add(0, 0, 1))) {
            return new BlockData(pos7.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (this.isPosSolid(pos7.add(0, 0, -1))) {
            return new BlockData(pos7.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos8 = pos5.add(0, 0, 1);
        if (this.isPosSolid(pos8.add(0, -1, 0))) {
            return new BlockData(pos8.add(0, -1, 0), EnumFacing.UP);
        }
        if (this.isPosSolid(pos8.add(-1, 0, 0))) {
            return new BlockData(pos8.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (this.isPosSolid(pos8.add(1, 0, 0))) {
            return new BlockData(pos8.add(1, 0, 0), EnumFacing.WEST);
        }
        if (this.isPosSolid(pos8.add(0, 0, 1))) {
            return new BlockData(pos8.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (this.isPosSolid(pos8.add(0, 0, -1))) {
            return new BlockData(pos8.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos9 = pos5.add(0, 0, -1);
        if (this.isPosSolid(pos9.add(0, -1, 0))) {
            return new BlockData(pos9.add(0, -1, 0), EnumFacing.UP);
        }
        if (this.isPosSolid(pos9.add(-1, 0, 0))) {
            return new BlockData(pos9.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (this.isPosSolid(pos9.add(1, 0, 0))) {
            return new BlockData(pos9.add(1, 0, 0), EnumFacing.WEST);
        }
        if (this.isPosSolid(pos9.add(0, 0, 1))) {
            return new BlockData(pos9.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (this.isPosSolid(pos9.add(0, 0, -1))) {
            return new BlockData(pos9.add(0, 0, -1), EnumFacing.SOUTH);
        }
        return null;
    }

    private boolean isPosSolid(BlockPos pos) {
        Block block = mc.theWorld.getBlockState(pos).getBlock();
        return (block.getMaterial().isSolid() || !block.isTranslucent() || block.isVisuallyOpaque()
                || block instanceof BlockLadder || block instanceof BlockCarpet || block instanceof BlockSnow
                || block instanceof BlockSkull) && !block.getMaterial().isLiquid()
                && !(block instanceof BlockContainer);
    }

    public static Vec3 getVec3(BlockPos pos, EnumFacing face) {
        double x = (double) pos.getX() + 0.5;
        double y = (double) pos.getY() + 0.5;
        double z = (double) pos.getZ() + 0.5;
        if (face == EnumFacing.UP || face == EnumFacing.DOWN) {
            x += randomNumber((double) 0.3, (double) -0.3);
            z += randomNumber((double) 0.3, (double) -0.3);
        } else {
            y += randomNumber((double) 0.3, (double) -0.3);
        }
        if (face == EnumFacing.WEST || face == EnumFacing.EAST) {
            z += randomNumber(0.3, -0.3);
        }
        if (face == EnumFacing.SOUTH || face == EnumFacing.NORTH) {
            x += randomNumber(0.3, -0.3);
        }
        return new Vec3(x, y, z);
    }

    public static double randomNumber(double max, double min) {
        return Math.random() * (max - min) + min;
    }

    private static class BlockData {
        private final BlockPos pos;
        private final EnumFacing facing;

        public BlockData(final BlockPos pos, final EnumFacing facing) {
            this.pos = pos;
            this.facing = facing;
        }

        public BlockPos getBlockPos() {
            return this.pos;
        }

        public EnumFacing getEnumFacing() {
            return this.facing;
        }
    }

    // shifting

    // variables

    private CoolDown shiftTimer = new CoolDown(0);

    private static boolean isShifting = false;

    private void setShift(boolean sh) {

        KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), sh);
        if (!legit.isToggled()){
            place();
        }

    }

        // main func

    @SubscribeEvent
    public void p(TickEvent.PlayerTickEvent e) {
        boolean placeTimeSettingActive = placeblockdelay.getInputMax() > 0;
        this.data = this.getBlockData(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0, mc.thePlayer.posZ)) == null ? this.getBlockData(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0, mc.thePlayer.posZ).down(1)) : this.getBlockData(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0, mc.thePlayer.posZ));

        boolean shiftTimeSettingActive = shiftTime.getInputMax() > 0;



        if(legit.isToggled()) {
//            if(legit.isToggled()) {
//                if(mc.thePlayer.rotationPitch < pitchRange.getInputMin() || mc.thePlayer.rotationPitch > pitchRange.getInputMax()) {
//                    shouldBridge = false;
//                    if(Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode())) {
//                        setShift(true);
//                    }
//                    return;
//                }
//            }
//            if (onHold) {
//                if  (!Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode())) {
//                    shouldBridge = false;
//                    return;
//                }
//            }
//
//            if (blocksOnly.isToggled()) {
//                ItemStack i = mc.thePlayer.getHeldItem();
//                if (i == null || !(i.getItem() instanceof ItemBlock)) {
//                    if (isShifting) {
//                        isShifting = false;
//                        this.setShift(false);
//                    }
//
//                    return;
//                }
            }

            if (mc.thePlayer.onGround) {
                if (Utils.Player.playerOverAir()) {
                    // code fo the timer
                    if(shiftTimeSettingActive){ // making sure that the player has set the value so some number
                        shiftTimer.setCooldown(Utils.Java.randomInt(shiftTime.getInputMin(), shiftTime.getInputMax() + 0.1));
                        shiftTimer.start();
                    }

                    isShifting = true;
                    this.setShift(true);
                    shouldBridge = true;
                }
                else if (mc.thePlayer.isSneaking() && !Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode()) && onHold) { // if player is smeaking and shiftDown and holdSetting turned on
                    isShifting = false;
                    shouldBridge = false;
                    this.setShift(false);
                }
                else if(onHold && !Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode())) { // if shiftDown and holdSetting turned on
                    isShifting = false;
                    shouldBridge = false;
                    this.setShift(false);
                }
                else if(mc.thePlayer.isSneaking() && (Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode()) && onHold) && (!shiftTimeSettingActive|| shiftTimer.hasFinished())) {
                    isShifting = false;
                    this.setShift(false);
                    shouldBridge = true;
                }
                else if(mc.thePlayer.isSneaking() && !onHold  && (!shiftTimeSettingActive|| shiftTimer.hasFinished())) {
                    isShifting = false;
                    this.setShift(false);
                    shouldBridge = true;
                }
            }

            else if (shouldBridge && mc.thePlayer.capabilities.isFlying) {
                this.setShift(false);
                shouldBridge = false;
            }

            else if (shouldBridge && Utils.Player.playerOverAir() && legit.isToggled()) {
                isShifting = true;
                this.setShift(true);
            } else {
                // rn we are in the air and we are not flying, meaning that we are in a jump. and since shiftOnJump is turned off, we just unshift and uhh... nyoooom
                isShifting = false;
                this.setShift(false);
            }
        }


}

