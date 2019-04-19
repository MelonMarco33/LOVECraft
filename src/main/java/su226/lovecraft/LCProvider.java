package su226.lovecraft;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.items.CapabilityItemHandler;

public class LCProvider implements ICapabilitySerializable<NBTTagCompound>, ITickable, LCLOVE {
  private int EXP;
  private int maxEXP;
  private int totalEXP;
  private int LOVE;
  private int prevLOVE;
  private int prevEXP;
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
    updateAll(silent);
  }

  @Override
  public void setEXP(int value, boolean silent) {
    totalEXP -= EXP;
    EXP = value;
    totalEXP += value;
    updateAll(silent);
  }

  @Override
  public void setLOVE(int value, boolean silent) {
    LOVE = value;
    EXP = 0;
    totalEXP = getMinTotalEXP();
    updateAll(silent);
  }

  @Override
  public void addEXP(int value, boolean silent) {
    EXP += value;
    totalEXP += value;
    updateAll(silent);
  }

  @Override
  public LCLOVEInfo updateEXP() {
    maxEXP = getMaxEXP(LOVE);
    while (EXP >= maxEXP) {
      EXP -= maxEXP;
      LOVE++;
      maxEXP = getMaxEXP(LOVE);
    }
    int increment = totalEXP - prevEXP;
    boolean upgraded = prevLOVE < LOVE;
    prevLOVE = LOVE;
    prevEXP = totalEXP;
    return new LCLOVEInfo(increment, upgraded);
  }

  @Override
  public void updateAll(boolean silent) {
    LCLOVEInfo info = updateEXP();
    if (!player.getEntityWorld().isRemote) {
      sendToClient(silent);
    } else if (!silent) {
      sendMessage(info);
    }
  }

  @Override
  public void sendMessage(LCLOVEInfo info) {
    TextComponentTranslation component = new TextComponentTranslation("lovecraft.exp_increased", info.increment, LOVE, totalEXP, EXP);
    boolean send = false;
    if (info.upgraded) {
      if (LCConfig.showMessage >= 1) {
        component.appendText(I18n.format("lovecraft.love_increased"));
        send = true;
      }
      if (LCConfig.playSound >= 1) {
        player.getEntityWorld().playSound(player, player.getPosition(), LCSound.UPGRADE, SoundCategory.PLAYERS, 1F, 1F);
      }
    } else {
      if (LCConfig.showMessage == 2) {
        send = true;
      }
      if (LCConfig.playSound == 2) {
        player.getEntityWorld().playSound(player, player.getPosition(), LCSound.KILL, SoundCategory.PLAYERS, 1F, 1F);
      }
    }
    if (send) {
      if (LCConfig.messageType == 1) {
        player.sendStatusMessage(component, true);
      } else if (LCConfig.messageType == 2) {
        player.sendMessage(component);
      }
    }
  }

  @Override
  public void sendToClient(boolean silent) {
    if (!player.getEntityWorld().isRemote) {
      EntityPlayerMP mp = (EntityPlayerMP)player;
      if (mp.getServer().getPlayerList().getPlayers().contains(mp)) {
        LCNetwork.INSTANCE.sendTo(new LCMessage(totalEXP, silent), mp);
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

  @Override
  public void update() {
    System.err.println(1);
  }
}