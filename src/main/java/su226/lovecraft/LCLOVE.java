package su226.lovecraft;

public interface LCLOVE {
  public int getEXP();
  public int getMaxEXP();
  public int getTotalEXP();
  public int getLOVE();
  public void setEXP(int value, boolean silent);
  public void addEXP(int value, boolean silent);
  public void setTotalEXP(int value, boolean silent);
  public void setLOVE(int value, boolean silent);
  public void updateAll(boolean silent);
  public LCLOVEInfo updateEXP();
  public void sendMessage(LCLOVEInfo info);
  public void sendToClient(boolean silent);
}