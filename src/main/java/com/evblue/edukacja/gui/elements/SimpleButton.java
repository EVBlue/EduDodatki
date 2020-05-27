package com.evblue.edukacja.gui.elements;

import com.evblue.edukacja.util.GLUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;


public class SimpleButton extends GuiButton {
	int bordercolor;
	int buttoncolor;
	int textcolor = 0xFFFFFFFF;
	private ResourceLocation sound = new ResourceLocation("gui.button.press");
	private boolean field_146123_n;
	
	public SimpleButton(int id, int x, int y, int w, int h, int bordercolor, int buttoncolor, String text) {
		super(id, x, y, w, h, text);
		this.bordercolor = bordercolor;
		this.buttoncolor = buttoncolor;
	}
	
	public SimpleButton(int id, int x, int y, int w, int h, int bordercolor, int buttoncolor, int textcolor, String text) {
		super(id, x, y, w, h, text);
		this.bordercolor = bordercolor;
		this.buttoncolor = buttoncolor;
		this.textcolor = textcolor;
	}
	
	public void drawButton(Minecraft mc, int x, int y ) {
		if (this.visible) {
			FontRenderer fontrenderer = mc.fontRenderer;
			
			this.field_146123_n = x >= this.x && y >= this.y && x < this.x + this.width && y < this.y + this.height;
			boolean isHovered = this.getHoverState(this.field_146123_n) != 2;
			
			GLUtils.drawBorderedRect(this.x, this.y, this.x + this.width, this.y + this.height, isHovered ? buttoncolor : buttoncolor + 0x00050505, 1, bordercolor);
			
			this.mouseDragged(mc, x, y);
			fontrenderer.drawString(this.displayString, this.x + this.width / 2 - fontrenderer.getStringWidth(this.displayString) / 2, this.y + this.height / 2 - 4, textcolor);
		}
	}
	
	public SimpleButton setSound(ResourceLocation sound) {
		this.sound = sound;
		return this;
	}
	
	/**public void func_146113_a(SoundHandler p_146113_1_) {
        p_146113_1_.playSound(PositionedSoundRecord.func_146113_a(sound, 1.0F));
    }*/


}
