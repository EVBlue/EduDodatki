package com.evblue.edukacja.handlers.packets;

import com.evblue.edukacja.Main;
import com.evblue.edukacja.data.ItemListNode;
import com.evblue.edukacja.data.MenuElement;
import com.evblue.edukacja.data.RequestEnum;
import com.evblue.edukacja.gui.PanelGui;
import com.evblue.edukacja.handlers.ResourceReloadListener;
import com.evblue.edukacja.util.MenuParser;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

public class ResponsePacket implements IMessage, IMessageHandler<ResponsePacket, IMessage> {
	
	byte response;
	byte phase;
	String value;
	boolean sendFile;
	byte[] fileBytes;
	
	public ResponsePacket() {
		
	}
	
	public ResponsePacket(RequestEnum response, String value, byte phase) {
		this.response = (byte) response.ordinal();
		this.value = value;
		this.phase = phase;
	}
	
	public ResponsePacket(RequestEnum response, String value, byte phase, String file) {
		this(response, value, phase);
		fileBytes = MenuParser.instance.serverMenuToBytes(file);
		if (fileBytes != null) sendFile = true;
	}
	
	@Override
	public void fromBytes(ByteBuf buffer) {
		response = buffer.readByte();
		phase = buffer.readByte();
		value = ByteBufUtils.readUTF8String(buffer);
		sendFile = buffer.readBoolean();
		if (sendFile) {
			int len = buffer.readInt();
			fileBytes = buffer.readBytes(len).array();
		}
	}
	
	@Override
	public IMessage onMessage(ResponsePacket message, MessageContext ctx) {
		EntityPlayer player = Main.proxy.getMessagePlayer(ctx);
		RequestEnum req = RequestEnum.getValueById(message.response);
		//System.out.printIn(req + " " + message.phase);
		if (req == RequestEnum.ADMIN_LOGS || req == RequestEnum.DEATH_LOGS) {
			GuiScreen guiScreen = Minecraft.getMinecraft().currentScreen;
			if (guiScreen instanceof PanelGui) {
				if (message.phase == 1) {
					((PanelGui) guiScreen).flushIncludes(req);
				}
				if (message.phase == 2) {
					Main.dynStorage.menus.add(MenuElement.create("$INC_" + req, ItemListNode.title(message.value)));
				}
				if (message.phase == 10) {
					((PanelGui) guiScreen).reloadMenu(false);
				}
			}
			
		}
		if (req == RequestEnum.REQUEST_MENU_HASH) {
			if (message.phase == 2) {
				ResourceReloadListener.setAllowCustom(true);
				ResourceReloadListener.setBlockReloading(false);
				String hash = MenuParser.instance.getMenuHash("custom.menu", false);
				Main.packets.sendToServer(new RequestPacket(RequestEnum.SEND_MENU_HASH));
			}
			if (message.phase == 4) {
				Main.logger.warn("Server doesn't accepts custom menus, reading default menu...");
				ResourceReloadListener.setAllowCustom(false);
				ResourceReloadListener.update();
			}
		}
		if (req == RequestEnum.SEND_MENU) {
			if (message.phase == 2) {
				Main.logger.info("Received custom menu, writing...");
				boolean b = MenuParser.instance.bytesToClientMenu(message.fileBytes, "custom.menu");
				if (b) {
					Main.logger.info("Successfully written received menu! Reloading...");
					ResourceReloadListener.setBlockReloading(false);
					ResourceReloadListener.setAllowCustom(true);
					ResourceReloadListener.update();
					ResourceReloadListener.setBlockReloading(true);
				}
				else {
					Main.logger.error("Can't write received menu!");
				}
			}
		}
		
		if (req == RequestEnum.FLY) {
			if (message.phase == 2) {
				player.capabilities.allowFlying = false;
			}
			else if (message.phase == 3) {
				player.capabilities.allowFlying = true;
			}
		}
		
		return null;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		// TODO Auto-generated method stub
		
	}

}
