package su226.lovecraft;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = LOVECraft.MODID, name = LOVECraft.NAME, version = LOVECraft.VERSION)
public class LOVECraft {
  public static final String MODID = "lovecraft";
  public static final String NAME = "LOVECraft";
  public static final String VERSION = "2.0";

  @EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    LCCapability.preInit();
  }

  @EventHandler
  public void init(FMLInitializationEvent event) {
  }

  @EventHandler
  public void postInit(FMLPostInitializationEvent event) {
  }
}
