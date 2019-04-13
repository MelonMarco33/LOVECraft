package su226.lovecraft;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class LCCapability {
  public static <T> void register(Class<T> capabilityClass) {
    CapabilityManager.INSTANCE.register(capabilityClass, new Capability.IStorage<T>() {
        @Override
        public NBTBase writeNBT(Capability<T> capability, T instance, EnumFacing side) {
            throw new UnsupportedOperationException("Not supported");
        }
        @Override
        public void readNBT(Capability<T> capability, T instance, EnumFacing side, NBTBase nbt) {
            throw new UnsupportedOperationException("Not supported");
        } }, () -> { throw new UnsupportedOperationException("Not supported"); });
  }

  @CapabilityInject(LCLOVE.class)
  public static Capability<LCLOVE> LOVE;

  public static void preInit() {
    register(LCLOVE.class);
  }
}