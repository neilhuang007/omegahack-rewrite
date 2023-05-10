package keystrokesmod.client.utils.player;

import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class InventoryUtils {
    public static final Minecraft mc = Minecraft.getMinecraft();

    public static int getBlockSlot() {
        for (int i = 0; i < 9; ++i) {
            if (!mc.thePlayer.inventoryContainer.getSlot(i + 36).getHasStack()
                    || !(mc.thePlayer.inventoryContainer.getSlot(i + 36).getStack()
                    .getItem() instanceof ItemBlock))
                continue;
            return i;
        }
        return -1;
    }

    public static boolean isPlayerHoldingBlock() {
        if (mc.thePlayer.getCurrentEquippedItem() == null) {
            return false;
        } else {
            Item item = mc.thePlayer.getCurrentEquippedItem().getItem();
            return item instanceof ItemBlock;
        }
    }


}
