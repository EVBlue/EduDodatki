package com.evblue.edukacja.handlers;

import com.evblue.edukacja.Main;
import com.evblue.edukacja.util.Reference;
import com.evblue.edukacja.gui.PanelGui;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

public class KeyHandler {
	
	public static final KeyBinding show_panel = new KeyBinding("key.edupanel.open", Keyboard.KEY_M, "key.categories.multiplayer");
	
	public static void registerKeys() {
		ClientRegistry.registerKeyBinding(show_panel);
	}
	
	@SubscribeEvent
	public void keyEvent(InputEvent.KeyInputEvent event) {
		if (!Keyboard.getEventKeyState()) return;
		
		Minecraft mc = Minecraft.getMinecraft();
		
		if (mc.world == null) return;
		
		if (Keyboard.isKeyDown(show_panel.getKeyCode())) {
			if (Main.proxy.canClientUsePanel()) {
				mc.displayGuiScreen(new PanelGui());
			}
		}
	}

}
