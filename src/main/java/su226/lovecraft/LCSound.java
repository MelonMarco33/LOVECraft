package su226.lovecraft;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class LCSound {
  public static SoundEvent UPGRADE = create("upgrade");
  public static SoundEvent KILL = create("kill");

  public static void init(IForgeRegistry<SoundEvent> registry) {
    registry.registerAll(UPGRADE, KILL);
  }

  private static SoundEvent create(String name) {
    ResourceLocation location = new ResourceLocation(LOVECraft.MODID, name);
    return new SoundEvent(location).setRegistryName(location);
  }
}