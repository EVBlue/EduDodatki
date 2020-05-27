package com.evblue.edukacja.handlers.packets;

import com.evblue.edukacja.Main;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;


public class PlayerInfoPacket implements IMessage, IMessageHandler<PlayerInfoPacket, IMessage> {
	
	boolean hasPerms;
	
	public PlayerInfoPacket() {
		
	}
	
	public PlayerInfoPacket(boolean hasPerms) {
		this.hasPerms = hasPerms;
	}
	
	@Override
	public void toBytes(ByteBuf buffer) {
		buffer.writeBoolean(hasPerms);
	}
	
	@Override
	public void fromBytes(ByteBuf buffer) {
		hasPerms = buffer.readBoolean();
	}
	
	@Override
	public IMessage onMessage(PlayerInfoPacket message, MessageContext ctx) {
		EntityPlayer player = Main.proxy.getMessagePlayer(ctx);
		Main.dynStorage.isPermissionsProvided = message.hasPerms;
		return null;
	}

}
