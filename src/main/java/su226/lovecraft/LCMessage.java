package su226.lovecraft;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class LCMessage implements IMessage {
  public int totalEXP;

  public LCMessage() {
  }

  public LCMessage(int totalEXP) {
    this.totalEXP = totalEXP;
  }

  @Override
  public void toBytes(ByteBuf buf) {
    buf.writeInt(totalEXP);
  }

  @Override
  public void fromBytes(ByteBuf buf) {
    totalEXP = buf.readInt();
  }

  public static class LCMessageHandler implements IMessageHandler<LCMessage, IMessage> {
    @Override
    public IMessage onMessage(LCMessage message, MessageContext ctx) {
      Minecraft mc = Minecraft.getMinecraft();
      EntityPlayerSP player = mc.player;
      int totalEXP = message.totalEXP;
      mc.addScheduledTask(() -> {
        LCLOVE love = player.getCapability(LCCapability.LOVE, null);
        love.setTotalEXP(totalEXP);
      });
      return null;
    }
  }
}