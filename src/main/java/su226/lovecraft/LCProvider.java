package su226.lovecraft;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.items.CapabilityItemHandler;

@SuppressWarnings("rawtypes")
public class LCProvider implements ICapabilityProvider, LCLOVE, ICapabilitySerializable {
  private int exp;
  private int totalexp;
  private int love;

  @Override
  public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
    if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
      return true;
    }
    return false;
  }

  @Override
  @SuppressWarnings("all")
  public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
    if (capability == LCCapability.LOVE) {
      return (T)this;
    }
    return null;
  }

  @Override
  public int getEXP() {
    return exp;
  }

  @Override
  public int getTotalEXP() {
    return totalexp;
  }

  @Override
  public int getLOVE() {
    return love;
  }

  @Override
  public void setEXP(int value) {
    totalexp -= exp;
    exp = value;
    totalexp += exp;
    int maxexp = getMaxEXP(love);
    while (exp >= maxexp) {
      exp -= maxexp;
      love++;
      maxexp = getMaxEXP(love);
    }
  }

  private int getMaxEXP(int love2) {
    if (LCConfig.MaxEXPTable.length > love2 + 1) {
      return LCConfig.MaxEXPTable[love2];
    }
    LCEval eval = new LCEval();
    eval.var.put("x", (double)love2);
    return (int)Math.floor(eval.calc(LCConfig.MaxEXPFormula));
  }

  @Override
  public void setTotalEXP(int value) {
    love = 0;
    totalexp = value;
    exp = value;
    int maxexp = getMaxEXP(love);
    while (exp >= maxexp) {
      exp -= maxexp;
      love++;
      maxexp = getMaxEXP(love);
    }
  }

  @Override
  public void setLOVE(int value) {
    love = value;
    exp = 0;
    totalexp = getMaxEXP(love - 1) + 1;
  }

  @Override
  public void addEXP(int value) {
    exp += value;
    totalexp += value;
    int maxexp = getMaxEXP(love);
    while (exp >= maxexp) {
      exp -= maxexp;
      love++;
      maxexp = getMaxEXP(love);
    }
  }

  @Override
  public void deserializeNBT(NBTBase tag) {
    NBTTagCompound compound = (NBTTagCompound)tag;
    setTotalEXP(compound.getInteger("exp"));
  }

  @Override
  public NBTBase serializeNBT() {
    NBTTagCompound compound = new NBTTagCompound();
    compound.setInteger("exp", getTotalEXP());
    return compound;
  }
}