package su226.lovecraft;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import su226.lovecraft.iwillbepunishedbyhplovecraft.Su226;

@EventBusSubscriber
public class LCCommonProxy {
  @SubscribeEvent
  public static void livingHurt(LivingHurtEvent event) {
    if (isVaildAttack(event)) {
      EntityPlayer player = (EntityPlayer)event.getSource().getTrueSource();
      LCLOVE love = player.getCapability(LCCapability.LOVE, null);
      love.addEXP(calcExp(event), false);
    }
  }

  private static int calcExp(LivingHurtEvent event) {
    EntityLivingBase living = event.getEntityLiving();
    ResourceLocation name = EntityRegistry.getEntry(living.getClass()).getRegistryName();
    for (String i : LCConfig.EntitiesEXP) {
      String[] j = i.split(":");
      if (j.length == 3 && j[0].equals(name.getResourceDomain()) && j[1].equals(name.getResourcePath())) {
        return Integer.parseInt(j[2]);
      }
    }
    LCEval eval = new LCEval();
    eval.var.put("x", (double)living.getMaxHealth());
    eval.var.put("y", (double)Math.min(event.getAmount(), living.getHealth()));
    return (int)Math.ceil(eval.calc(LCConfig.EXPFormula));
  }

  private static boolean isVaildAttack(LivingHurtEvent event) {
    DamageSource source = event.getSource();
    if (source.damageType != "player") {
      return false;
    }
    EntityLivingBase living = event.getEntityLiving();
    if (event.getAmount() < living.getHealth() || !LCConfig.onlyWhenDeath) {
      return false;
    }
    String id = EntityRegistry.getEntry(living.getClass()).getRegistryName().toString();
    if (LCConfig.EntitiesWhitelist.length != 0) {
      for (String i : LCConfig.EntitiesWhitelist) {
        if (i.equals(id)) {
          return true;
        }
      }
      return false;
    } else {
      for (String i : LCConfig.EntitiesBlacklist) {
        if (i.equals(id)) {
          return false;
        }
      }
    }
    return true;
  }

  @SubscribeEvent
  public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
    Entity entity = event.getObject();
    if (entity instanceof EntityPlayer) {
      event.addCapability(new ResourceLocation("lovecraft", "love"), new LCProvider((EntityPlayer)entity));
    }
  }

  @SubscribeEvent
  public static void registerSound(Register<SoundEvent> event) {
    LCSound.init(event.getRegistry());
  }

  @SubscribeEvent
  public static void playerJoin(EntityJoinWorldEvent event) {
    Entity entity = event.getEntity();
    World world = event.getWorld();
    if (entity instanceof EntityPlayer && !world.isRemote) {
      entity.getCapability(LCCapability.LOVE, null).sendToClient(true);
    }
  }

  public void preInit(FMLPreInitializationEvent event) {
    LOVECraft.log = event.getModLog();
    LCNetwork.preInit();
    LCCapability.preInit();
    Su226.INSTANCE.saySomething();
  }
}