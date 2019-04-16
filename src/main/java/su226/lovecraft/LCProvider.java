package su226.lovecraft;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.items.CapabilityItemHandler;

public class LCProvider implements ICapabilityProvider, ICapabilitySerializable<NBTTagCompound>, LCLOVE {
  private int EXP;
  private int maxEXP;
  private int totalEXP;
  private int LOVE;
  private int prevLOVE;
  private EntityPlayer player;

  public LCProvider(EntityPlayer player) {
    this.player = player;
  }

  @Override
  public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
    if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
      return true;
    }
    return false;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
    if (capability == LCCapability.LOVE) {
      return (T)this;
    }
    return null;
  }

  @Override
  public int getEXP() {
    return EXP;
  }

  @Override
  public int getTotalEXP() {
    return totalEXP;
  }

  @Override
  public int getLOVE() {
    return LOVE;
  }

  @Override
  public int getMaxEXP() {
    return maxEXP;
  }

  private int getMinTotalEXP() {
    int ret = 0;
    for (int i = 0; i < LOVE; i++) {
      ret += getMaxEXP(i);
    }
    return ret;
  }

  public int getMaxEXP(int love2) {
    if (LCConfig.MaxEXPTable.length > love2 + 1) {
      return LCConfig.MaxEXPTable[love2];
    }
    LCEval eval = new LCEval();
    eval.var.put("x", (double)love2);
    return (int)Math.floor(eval.calc(LCConfig.MaxEXPFormula));
  }

  @Override
  public void setTotalEXP(int value, boolean silent) {
    LOVE = 0;
    totalEXP = value;
    EXP = value;
    update(silent);
  }

  @Override
  public void setEXP(int value, boolean silent) {
    totalEXP -= EXP;
    EXP = value;
    totalEXP += value;
    update(silent);
  }

  @Override
  public void setLOVE(int value, boolean silent) {
    LOVE = value;
    EXP = 0;
    totalEXP = getMinTotalEXP();
    update(silent);
  }

  @Override
  public void addEXP(int value, boolean silent) {
    EXP += value;
    totalEXP += value;
    update(silent);
  }

  @Override
  public void update(boolean silent) {
    maxEXP = getMaxEXP(LOVE);
    while (EXP >= maxEXP) {
      EXP -= maxEXP;
      LOVE++;
      maxEXP = getMaxEXP(LOVE);
    }
    boolean upgraded = prevLOVE < LOVE;
    prevLOVE = LOVE;
    if (!player.getEntityWorld().isRemote) {
      LCNetwork.INSTANCE.sendTo(new LCMessage(totalEXP, silent), (EntityPlayerMP)player);
    } else if (!silent) {
      if (LCConfig.playSound) {
        if (upgraded) {
          player.getEntityWorld().playSound(player, player.getPosition(), LCSound.UPGRADE, SoundCategory.PLAYERS, 1F, 1F);
        } else {
          player.getEntityWorld().playSound(player, player.getPosition(), LCSound.KILL, SoundCategory.PLAYERS, 1F, 1F);
        }
      }
      if (LCConfig.messageType == 1) {
        player.sendStatusMessage(new TextComponentTranslation("lovecraft.increased", LOVE, totalEXP, EXP), true);
      } else if (LCConfig.messageType == 2) {
        player.sendMessage(new TextComponentTranslation("lovecraft.increased", LOVE, totalEXP, EXP));
      }
    }
  }

  @Override
  public void deserializeNBT(NBTTagCompound tag) {
    setTotalEXP(tag.getInteger("exp"), true);
  }

  @Override
  public NBTTagCompound serializeNBT() {
    NBTTagCompound tag = new NBTTagCompound();
    tag.setInteger("exp", getTotalEXP());
    return tag;
  }
}