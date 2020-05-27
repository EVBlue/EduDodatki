package com.evblue.edukacja.handlers;

import com.evblue.edukacja.Main;
import com.evblue.edukacja.data.CapabilitiesEnum;
import com.evblue.edukacja.data.RequestEnum;
import com.evblue.edukacja.handlers.packets.PlayerInfoPacket;
import com.evblue.edukacja.handlers.packets.ResponsePacket;
import com.evblue.edukacja.util.FileProcessor;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;

public class EventHandlers {

	@SubscribeEvent
	public void onDeath(LivingDeathEvent event) {
		try {
			if (event.getEntity() instanceof EntityPlayer) {
				FileProcessor.appendToDeathLog(((EntityPlayer) event.getEntity()).func_110142_aN().func_151521_b().getFormattedText().replaceAll("A§r", ""));
			}
		} catch (NoSuchMethodError ignored) {
		}
	}
	
	@SubscribeEvent
	public void onLogin(PlayerEvent.PlayerLoggedInEvent event) {
		Main.packets.sendTo(new PlayerInfoPacket(Main.proxy.canPlayerUsePanel(event.player)), (EntityPlayerMP) event.player);
		Main.packets.sendTo(new ResponsePacket(RequestEnum.REQUEST_MENU_HASH, "", (byte) 2), (EntityPlayerMP) event.player);
	}
	
	@SubscribeEvent
	public void onEntityConstructing(EntityEvent.EntityConstructing event) {
		if (event.getEntity() instanceof EntityPlayer) {
			if (PlayerProps.get((EntityPlayer) event.getEntity()) == null) {
				EntityPlayer player = (EntityPlayer) event.getEntity();
				PlayerProps.register(player);
			}
		}
	}
	
	@SubscribeEvent
	public void playerTick(TickEvent.PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.START) {
			PlayerProps.get(event.player).tick();
		}
	}
}
	


