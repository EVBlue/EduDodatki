package com.evblue.edukacja.handlers.packets;

import com.evblue.edukacja.Main;
import com.evblue.edukacja.data.RequestEnum;
import com.evblue.edukacja.util.FileProcessor;
import com.evblue.edukacja.util.MenuParser;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class RequestPacket implements IMessage, IMessageHandler<RequestPacket, IMessage> {
	
	byte request;
	String customData;
	
	public RequestPacket() {
		
	}
	
	public RequestPacket(RequestEnum request, String customData) {
		this.request = (byte) request.ordinal();
		this.customData = customData;
	}
	
	public RequestPacket(RequestEnum request) {
		this(request, "");
	}
	
	@Override
	public void toBytes(ByteBuf buffer) {
		buffer.writeByte(request);
		ByteBufUtils.writeUTF8String(buffer,  customData);
	}
	
	@Override
	public void fromBytes(ByteBuf buffer) {
		request = buffer.readByte();
		customData = ByteBufUtils.readUTF8String(buffer);
	}
	
	@Override
	public IMessage onMessage(RequestPacket message, MessageContext ctx) {
		EntityPlayer player = Main.proxy.getMessagePlayer(ctx);
		RequestEnum req = RequestEnum.getValueById(message.request);
		Main.packets.sendTo(new ResponsePacket(req, "", (byte) 1), ((EntityPlayerMP) player));
		if (Main.proxy.canPlayerUsePanel(player)) {
			if (req == RequestEnum.ADMIN_LOGS) {
				if (Main.dynStorage.admin_logs.isEmpty()) {
					Main.packets.sendTo(new ResponsePacket(req, "Nothing here.", (byte) 2), ((EntityPlayerMP) player));
				}
				else {
					for (String s: Main.dynStorage.admin_logs) {
						Main.packets.sendTo(new ResponsePacket(req, s, (byte) 2), ((EntityPlayerMP) player));
					}
				}
			}
			else if (req == RequestEnum.DEATH_LOGS) {
                if (Main.dynStorage.last_deads.isEmpty()) {
                    Main.packets.sendTo(new ResponsePacket(req, "Nothing here.", (byte) 2), ((EntityPlayerMP) player));
                } 
                else {
                    for (String s : Main.dynStorage.last_deads) {
                        Main.packets.sendTo(new ResponsePacket(req, s, (byte) 2), ((EntityPlayerMP) player));
                    }
                }
            } 
			else if (req == RequestEnum.SEND_ACTION) {
                FileProcessor.appendToAdminLog(player.getCommandSenderEntity() + " " + message.customData);
            } 
			else if (req == RequestEnum.SEND_MENU_HASH) {
                String clientHash = message.customData;
                String localHash = MenuParser.instance.getMenuHash("custom.menu", true);
                if (localHash.equals("miss") && !localHash.equals(clientHash)) {
                    Main.packets.sendTo(new ResponsePacket(RequestEnum.REQUEST_MENU_HASH, "", (byte) 4), ((EntityPlayerMP) player));
                } 
                else if (!localHash.equals(clientHash)) {
                    Main.logger.warn("Sending menu to " + player.getCommandSenderEntity() + "...");
                    Main.packets.sendTo(new ResponsePacket(RequestEnum.SEND_MENU, "", (byte) 2, "custom.menu"), ((EntityPlayerMP) player));
                } 
                else {
                    Main.logger.warn("Everything is OK with " + player.getCommandSenderEntity());
                }
		}
	}
		else {
			Main.packets.sendTo(new ResponsePacket(req, "Testing message.", (byte) 2), ((EntityPlayerMP) player)); //todo
		}
		Main.packets.sendTo(new ResponsePacket(req, "", (byte) 10), ((EntityPlayerMP) player));
		return null;
	}
	
}
