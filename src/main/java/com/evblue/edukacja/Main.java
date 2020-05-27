package com.evblue.edukacja;

import com.evblue.edukacja.proxy.CommonProxy;
import com.evblue.edukacja.util.Reference;
import com.evblue.edukacja.creativetabs.EdukacjaTab;
import com.evblue.edukacja.data.DynamicStorage;
import com.evblue.edukacja.handlers.ActionHandler;
import com.evblue.edukacja.handlers.CommandHandler;
import com.evblue.edukacja.handlers.packets.PlayerInfoPacket;
import com.evblue.edukacja.handlers.packets.RequestPacket;
import com.evblue.edukacja.handlers.packets.ResponsePacket;
import com.evblue.edukacja.handlers.packets.SendCommandPacket;
import com.evblue.edukacja.util.FileProcessor;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION)
public class Main {
	
	public static final EdukacjaTab EdukacjaTab = new EdukacjaTab();


	@Instance
	public static Main instance;
	
	public static Logger logger = LogManager.getLogger(Reference.NAME);
	public static Logger logger_parser = LogManager.getLogger(Reference.PARSER_NAME);
	
	public static DynamicStorage dynStorage;
	public static ActionHandler actionHandler;
	
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;
	
	public static SimpleNetworkWrapper packets;
	
	@EventHandler
	public static void PreInit(FMLPreInitializationEvent event)
	{
		dynStorage = new DynamicStorage();
		actionHandler = new ActionHandler();
		FileProcessor.readVars();
		FileProcessor.readUsers();
		FileProcessor.readLastLogLines();
		proxy.registerHandlers();
		
	}
	
	@EventHandler
	public static void Init(FMLInitializationEvent event)
	{
		proxy.clientInit();
	}
	
	@EventHandler
	public static void PostInit(FMLPostInitializationEvent event)
	{
		packets = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);
		packets.registerMessage(SendCommandPacket.class, SendCommandPacket.class, 0, Side.SERVER);
		packets.registerMessage(PlayerInfoPacket.class, PlayerInfoPacket.class, 1, Side.CLIENT);
		packets.registerMessage(RequestPacket.class, RequestPacket.class, 2, Side.SERVER);
		packets.registerMessage(ResponsePacket.class, ResponsePacket.class, 3, Side.CLIENT);
		
	}
	
	@EventHandler
	public void startServer(FMLServerStartingEvent event) {
		event.registerServerCommand(new CommandHandler());
	}
}
