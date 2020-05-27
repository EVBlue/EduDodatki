package com.evblue.edukacja.proxy;

import com.evblue.edukacja.Main;
import com.evblue.edukacja.util.Reference;
import com.evblue.edukacja.handlers.EventHandlers;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.item.Item;

public class CommonProxy {

	public void registerItemRenderer(Item item, int meta, String id) {
		
	}
	
	public void clientInit() {
		
	}
	
	public EntityPlayer getMessagePlayer(MessageContext ctx) {
		if (ctx.side == Side.SERVER) {
			return ctx.getServerHandler().player;
		}
		return null;
	}
	
	public boolean isPlayerOp(EntityPlayer player) {
		return player.canUseCommand(2, "");
	}
	public boolean canPlayerUsePanel(EntityPlayer player ) {
		return isPlayerOp(player) || Main.dynStorage.permissed_users.contains(player.getUniqueID().toString()); // || BukkitP.isUserHasPermission(player.getUniqueID(), Reference.PANEL_PERMISSION)
	}
	
	public boolean canClientUsePanel() {
		return false;
	}
	
	public void registerHandlers() {
		EventHandlers handlers = new EventHandlers();
		MinecraftForge.EVENT_BUS.register(handlers);
		FMLCommonHandler.instance().bus().register(handlers);
	}
}
