package su226.lovecraft;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@EventBusSubscriber(Side.CLIENT)
public class LCClientProxy extends LCCommonProxy {
  private static final Minecraft mc = Minecraft.getMinecraft();
  private static final LCGUI gui = new LCGUI();

  @SubscribeEvent
  public static void onDrawScreenPre(RenderGameOverlayEvent.Post event) {
    if (event.getType() == ElementType.EXPERIENCE) {
      NetworkPlayerInfo info = mc.getConnection().getPlayerInfo(mc.player.getGameProfile().getId());
      int gameMode = info == null ? -1 : info.getGameType().getID();
      for (int i : LCConfig.showBar) {
        if (i == gameMode) {
          gui.render();
          break;
        }
      }
    }
  }
}