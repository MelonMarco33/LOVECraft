package su226.lovecraft;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
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
  public void setEXP(int value) {
    totalEXP -= EXP;
    EXP = value;
    totalEXP += EXP;
    int maxexp = getMaxEXP(LOVE);
    while (EXP >= maxexp) {
      EXP -= maxexp;
      LOVE++;
      maxexp = getMaxEXP(LOVE);
    }
    sync();
  }

  @Override
  public int getMaxEXP() {
    if (prevLOVE != LOVE) {
      prevLOVE = LOVE;
      maxEXP = getMaxEXP(LOVE);
    }
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
  public void setTotalEXP(int value) {
    LOVE = 0;
    totalEXP = value;
    EXP = value;
    int maxexp = getMaxEXP();
    while (EXP >= maxexp) {
      EXP -= maxexp;
      LOVE++;
      maxexp = getMaxEXP();
    }
    sync();
  }

  @Override
  public void setLOVE(int value) {
    LOVE = value;
    EXP = 0;
    totalEXP = getMinTotalEXP();
    sync();
  }

  @Override
  public void addEXP(int value) {
    EXP += value;
    totalEXP += value;
    int maxexp = getMaxEXP();
    while (EXP >= maxexp) {
      EXP -= maxexp;
      LOVE++;
      maxexp = getMaxEXP();
    }
    sync();
  }

  @Override
  public void sync() {
    if (!player.getEntityWorld().isRemote) {
      LCNetwork.INSTANCE.sendTo(new LCMessage(totalEXP), (EntityPlayerMP)player);
    }
  }

  @Override
  public void deserializeNBT(NBTTagCompound tag) {
    setTotalEXP(tag.getInteger("exp"));
  }

  @Override
  public NBTTagCompound serializeNBT() {
    NBTTagCompound tag = new NBTTagCompound();
    tag.setInteger("exp", getTotalEXP());
    return tag;
  }
}