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
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class LCEvent {
  @SubscribeEvent
  public static void livingHurt(LivingHurtEvent event) {
    DamageSource source = event.getSource();
    EntityLivingBase living = event.getEntityLiving();
    if (source.damageType == "player" && event.getAmount() >= living.getHealth()) {
      EntityPlayer player = (EntityPlayer)source.getTrueSource();
      LCLOVE love = player.getCapability(LCCapability.LOVE, null);
      int maxhealth = (int)Math.ceil(living.getMaxHealth());
      int current = love.getLOVE();
      love.addEXP(maxhealth);
      if (love.getLOVE() > current) {
        player.getEntityWorld().playSound(null, player.getPosition(), LCSound.UPGRADE, SoundCategory.PLAYERS, 1F, 1F);
      } else {
        player.getEntityWorld().playSound(null, player.getPosition(), LCSound.KILL, SoundCategory.PLAYERS, 1F, 1F);
      }
      player.sendMessage(new TextComponentTranslation("lovecraft.increased", love.getLOVE(), love.getTotalEXP(), love.getEXP()));
    }
  }

  @SubscribeEvent
  public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
    if (event.getObject() instanceof EntityPlayer) {
      event.addCapability(new ResourceLocation("lovecraft", "love"), new LCProvider());
    }
  }

  @SubscribeEvent
  public static void registerSound(Register<SoundEvent> event) {
    LCSound.init(event.getRegistry());
  }
}