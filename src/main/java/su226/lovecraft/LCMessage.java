package su226.lovecraft;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class LCMessage implements IMessage {
  public int totalEXP;
  public boolean silent;

  public LCMessage() {
  }

  public LCMessage(int totalEXP, boolean silent) {
    this.totalEXP = totalEXP;
    this.silent = silent;
  }

  @Override
  public void toBytes(ByteBuf buf) {
    buf.writeInt(totalEXP);
    buf.writeBoolean(silent);
  }

  @Override
  public void fromBytes(ByteBuf buf) {
    totalEXP = buf.readInt();
    silent = buf.readBoolean();
  }

  public static class LCMessageHandler implements IMessageHandler<LCMessage, IMessage> {
    @Override
    public IMessage onMessage(LCMessage message, MessageContext ctx) {
      Minecraft mc = Minecraft.getMinecraft();
      EntityPlayerSP player = mc.player;
      mc.addScheduledTask(() -> {
        LCLOVE love = player.getCapability(LCCapability.LOVE, null);
        love.setTotalEXP(message.totalEXP, message.silent);
      });
      return null;
    }
  }
}