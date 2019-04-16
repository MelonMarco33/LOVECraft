package su226.lovecraft;

import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = LOVECraft.MODID, name = LOVECraft.NAME, version = LOVECraft.VERSION)
public class LOVECraft {
  public static final String MODID = "lovecraft";
  public static final String NAME = "LOVECraft";
  public static final String VERSION = "2.2";

  @SidedProxy(clientSide = "su226.lovecraft.LCClientProxy", serverSide = "su226.lovecraft.LCCommonProxy")
  public static LCCommonProxy proxy;
  @Instance(MODID)
  public static LOVECraft instance;
  public static Logger log;

  @EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    proxy.preInit(event);
  }

  @EventHandler
  public void init(FMLInitializationEvent event) {
  }

  @EventHandler
  public void postInit(FMLPostInitializationEvent event) {
  }
}
