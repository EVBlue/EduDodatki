package com.evblue.edukacja.handlers.packets;

import com.evblue.edukacja.Main;
import com.evblue.edukacja.data.ActionsEnum;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class SendCommandPacket  implements IMessage, IMessageHandler<SendCommandPacket, IMessage> {
	
	int action;
	String target;
	
	public SendCommandPacket() {
		
	}
	
	public SendCommandPacket(String target, ActionsEnum action) {
		this.target = target == null ? "" : target;
		this.action = action.ordinal();
	}
	
	@Override
	public void toBytes(ByteBuf buffer) {
		buffer.writeInt(action);
		ByteBufUtils.writeUTF8String(buffer, target);
	}
	
	@Override
	public void fromBytes(ByteBuf buffer) {
		action = buffer.readInt();
		target = ByteBufUtils.readUTF8String(buffer);
	}
	
	@Override
	public IMessage onMessage(SendCommandPacket message, MessageContext ctx) {
		EntityPlayer player = Main.proxy.getMessagePlayer(ctx);
		EntityPlayer target = null;
		try {
			target = CommandBase.getPlayer(null, player, message.target);
		} catch (CommandException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ActionsEnum action = ActionsEnum.getValueById(message.action);
		Main.packets.sendTo(new PlayerInfoPacket(Main.proxy.canPlayerUsePanel(player)),(EntityPlayerMP) player);
		Main.actionHandler.serverWork(message.target, (EntityPlayerMP) player, (EntityPlayerMP) target, action);
		return null;
	}

}
