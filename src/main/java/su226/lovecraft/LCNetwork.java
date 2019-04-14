package su226.lovecraft;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import su226.lovecraft.LCMessage.LCMessageHandler;

public class LCNetwork {
  public static SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(LOVECraft.MODID);

  public static void preInit() {
    INSTANCE.registerMessage(LCMessageHandler.class, LCMessage.class, 0, Side.CLIENT);
  }
}