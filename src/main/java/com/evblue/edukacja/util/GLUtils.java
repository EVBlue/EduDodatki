package com.evblue.edukacja.util;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.OpenGlHelper;
import org.lwjgl.opengl.GL11;

public class GLUtils {
	
	public static void drawBorderedRect(int x1, int y1, int x2, int y2, int rectColor, int lineWidth, int borderColor) {
		Gui.drawRect(x1 + lineWidth, y1 + lineWidth, x2 - lineWidth, y2 - lineWidth, rectColor);
		Gui.drawRect(x1, y1, x1 + lineWidth, y2, borderColor);
		Gui.drawRect(x2 - lineWidth, y1, x2, y2, borderColor);
		Gui.drawRect(x1 + lineWidth, y1, x2 - lineWidth, y1 + lineWidth, borderColor);
		Gui.drawRect(x1 + lineWidth, y2 - lineWidth, x2 - lineWidth, y2, borderColor);
	}
	
	public static void setColor(int color) {
		float red = ((color >> 16) & 0xff) / 255F;
		float green = ((color >> 8) & 0xff) / 255F;
		float blue = (color & 0xff) / 255F;
		float alpha = ((color >> 24) & 0xff) / 255F;
		GL11.glColor4f(red, green, blue, alpha);
	}
	
	public static void blendUp() {
		GL11.glEnable(GL11.GL_BLEND);
		OpenGlHelper.glBlendFunc(770, 711, 1, 0);
	}
	
	public static void blednDown() {
		GL11.glDisable(GL11.GL_BLEND);
	}

}
