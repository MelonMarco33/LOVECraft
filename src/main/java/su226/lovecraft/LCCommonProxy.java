package su226.lovecraft;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

@EventBusSubscriber
public class LCCommonProxy {
  @SubscribeEvent
  public static void livingHurt(LivingHurtEvent event) {
    if (isVaildAttack(event)) {
      EntityPlayer player = (EntityPlayer)event.getSource().getTrueSource();
      LCLOVE love = player.getCapability(LCCapability.LOVE, null);
      int current = love.getLOVE();
      love.addEXP(calcExp(event));
      if (LCConfig.playSound) {
        if (love.getLOVE() > current) {
          player.getEntityWorld().playSound(null, player.getPosition(), LCSound.UPGRADE, SoundCategory.PLAYERS, 1F, 1F);
        } else {
          player.getEntityWorld().playSound(null, player.getPosition(), LCSound.KILL, SoundCategory.PLAYERS, 1F, 1F);
        }
      }
      if (LCConfig.showMessage) {
        player.sendMessage(new TextComponentTranslation("lovecraft.increased", love.getLOVE(), love.getTotalEXP(), love.getEXP()));
      }
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
    return (int)Math.ceil(eval.calc(LCConfig.EXPFormula));
  }

  private static boolean isVaildAttack(LivingHurtEvent event) {
    DamageSource source = event.getSource();
    if (source.damageType != "player") {
      return false;
    }
    EntityLivingBase living = event.getEntityLiving();
    if (event.getAmount() < living.getHealth()) {
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

  public void preInit(FMLPreInitializationEvent event) {
    LOVECraft.log = event.getModLog();
    LCNetwork.preInit();
    LCCapability.preInit();
  }
}