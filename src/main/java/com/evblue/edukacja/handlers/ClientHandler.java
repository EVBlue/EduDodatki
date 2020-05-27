package com.evblue.edukacja.handlers;

import com.evblue.edukacja.data.CapabilitiesEnum;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;

public class ClientHandler {
	boolean invPrev;
	
	@SubscribeEvent
	public void renderNamePlate(RenderLivingEvent.Specials.Pre e) {
		if (e.getEntity() instanceof EntityPlayer && PlayerProps.get(((EntityPlayer) e.getEntity())).isPropEnabled(CapabilitiesEnum.HIDE_NAME)) {
			e.setCanceled(true);
		}
	}
	
	@SubscribeEvent
	public void renderPlayer(RenderPlayerEvent.Pre e) {
		boolean inv = PlayerProps.get(e.getEntityPlayer()).isPropEnabled(CapabilitiesEnum.INVISIBILITY);
		
		if (invPrev != inv) {
			try {
				ReflectionHelper.setPrivateValue(Render.class, e.getRenderer(), inv ? 0.0F : 1.0F, "shadowOpaque", "field_76987_f");
			} catch (ReflectionHelper.UnableToAccessFieldException ex) {
				ex.printStackTrace();
			}
			invPrev = inv;
		}
		
		if (inv) {
			e.setCanceled(true);
		}
	}

}
