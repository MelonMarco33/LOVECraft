package su226.lovecraft;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@EventBusSubscriber(Side.CLIENT)
public class LCClientProxy extends LCCommonProxy {
  private static LCGUI gui = new LCGUI(Minecraft.getMinecraft());

  @SubscribeEvent
  public static void onDrawScreenPre(RenderGameOverlayEvent.Post event) {
    if (event.getType() == ElementType.EXPERIENCE) {
      gui.render();
    }
  }
}