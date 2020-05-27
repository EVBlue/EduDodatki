package com.evblue.edukacja.proxy;

import com.evblue.edukacja.Main;
import com.evblue.edukacja.handlers.ClientHandler;
import com.evblue.edukacja.handlers.KeyHandler;
import com.evblue.edukacja.handlers.ResourceReloadListener;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class ClientProxy extends CommonProxy {

	public void registerItemRenderer(Item item, int meta, String id)
	{
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
	}
	
	@Override
	public void clientInit() {
		FMLCommonHandler.instance().bus().register(new KeyHandler());
		KeyHandler.registerKeys();
		Minecraft mc = Minecraft.getMinecraft();
		
		if (mc.getResourceManager() instanceof SimpleReloadableResourceManager) {
			((SimpleReloadableResourceManager) mc.getResourceManager()).registerReloadListener(new ResourceReloadListener());
		}
	}
	
	@Override
	public EntityPlayer getMessagePlayer(MessageContext ctx) {
		if (ctx.side == Side.CLIENT) {
			return Minecraft.getMinecraft().player;
		}
		else if (ctx.side == Side.SERVER) {
			return ctx.getServerHandler().player;
		}
		
		return null;
	}
	
	public boolean CanClientUsePanel() {
		return Main.dynStorage.isPermissionsProvided;
	}
	
	@Override
	public void registerHandlers() {
		super.registerHandlers();
		ClientHandler handler = new ClientHandler();
		MinecraftForge.EVENT_BUS.register(handler);
		FMLCommonHandler.instance().bus().register(handler);
	}
}
