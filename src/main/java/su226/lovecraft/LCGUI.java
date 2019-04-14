package su226.lovecraft;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LCGUI extends Gui {
  private static final ResourceLocation TEXTURE = new ResourceLocation(LOVECraft.MODID, "textures/gui/exp_bar.png");
  private final Minecraft mc;

  public LCGUI(Minecraft mc) {
    this.mc = mc;
  }

  public void render() {
    this.render(0);
  }

  private void render(int y) {
    LCLOVE love = mc.player.getCapability(LCCapability.LOVE, null);
    int width = (int)(182.0d * love.getEXP() / love.getMaxEXP());
    int screenWidth = new ScaledResolution(mc).getScaledWidth();
    int x = (screenWidth - 182) / 2;
    mc.getTextureManager().bindTexture(TEXTURE);
    this.drawTexturedModalRect(x, y, 0, 0, 182, 5);
    this.drawTexturedModalRect(x, y, 0, 5, width, 5);
    String str = String.valueOf(love.getLOVE());
    mc.fontRenderer.drawStringWithShadow(str, (screenWidth - mc.fontRenderer.getStringWidth(str)) / 2, y + 2, 0xff0000);
  }
}
