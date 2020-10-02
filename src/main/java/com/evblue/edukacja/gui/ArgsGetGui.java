package com.evblue.edukacja.gui;

import com.evblue.edukacja.Main;
import com.evblue.edukacja.util.Reference;
import com.evblue.edukacja.gui.elements.SimpleButton;
import com.evblue.edukacja.util.FileProcessor;
import com.evblue.edukacja.util.GLUtils;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.List;

public class ArgsGetGui extends Gui{
	private static final ResourceLocation POP = new ResourceLocation(Reference.MOD_ID, "other.popup");

	private PanelGui parent;
	private HashMap<String, GuiTextField> textFields;
	private boolean visible;
	private boolean confirmed;

	private String command;
	private List<String> parsedVars;
	private SimpleButton close;

	private int xCenter;
	private int yCenter;

	private int frameWidth;
	private int frameHeight;
	private int maxStringWidth;
	private FontRenderer fontRenderer;

	public ArgsGetGui (PanelGui parent) {
		textFields = new HashMap<String, GuiTextField>();
		this.parent = parent;
	}

	private void prepareFields() {
		textFields.clear();
		if (parsedVars == null) return;
		maxStringWidth = 0;
		frameHeight = parsedVars.size() * 20 + 20;

		int num = 0;

		for (String s : parsedVars) {
			GuiTextField textField = new GuiTextField(num, fontRenderer, xCenter + frameWidth / 2 - 80 - 3, (yCenter - frameHeight / 2) + num * 20 + 4, 80, 14);
			if (Main.dynStorage.vars_cache.containsKey(s)) {
				textField.setText(Main.dynStorage.vars_cache.get(s));
			}
			textFields.put(s, textField);
			num++;
		}
	}

	public void initGui() {
		xCenter = parent.width / 2;
		yCenter = parent.height / 2;
		fontRenderer = parent.mc.fontRenderer;
		prepareFields();
		close = new SimpleButton(0, xCenter + frameWidth / 2 - 62, yCenter + frameHeight / 2 - 16, 60, 14, 0xFF747474, 0xFF3a3a3a, 0xFFa0a0a0, I18n.format("edudodatki.confirm"));
		close.setSound(new ResourceLocation(Reference.MOD_ID, "other.enterkey"));
	}

	public String makeCmd() {
		String out = command + " ";
		for(String s : textFields.keySet()) {
			String fieldText = textFields.get(s).getText();
			if (!fieldText.trim().equals("")) Main.dynStorage.vars_cache.put(s, fieldText);
			out = out.replaceAll("\\$" + s + "\\s", fieldText + " ");
			FileProcessor.writeVars();
		}
		return out.trim();
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		if (visible) {
			Main.logger.info("Args GUI is now visible.");
			initGui();
		}
		this.visible = visible;
	}

	public boolean isConfirmed() {
		return confirmed;
	}

	public void setParsedVars(List<String> parsedVars) {
		this.parsedVars = parsedVars;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public void draw(int mx, int my, float f) {
		if (!isVisible()) return;
		drawRect(0, 0, parent.width, parent.height, 0xBB000000);
		GLUtils.drawBorderedRect(xCenter - frameWidth / 2, yCenter - frameHeight / 2, xCenter + frameWidth / 2, yCenter + frameHeight / 2, 0xFF1C1C1C, 1, 0xFF747474);

		close.drawButton(parent.mc, mx, my);

		for(String s : textFields.keySet()) {
			GuiTextField field = textFields.get(s);
			fontRenderer.drawString(s.replaceAll("_", " ") + ":", xCenter - frameWidth / 2 + 4, field.y + 3, 0xFFFFFF);
			field.drawTextBox();
		}
	}

	protected void mouseClicked(int mx, int my, int mb) {
		if (close.mousePressed(parent.mc, mx, my)) {
			closeClicked();
		}
		else {
			for (GuiTextField f : textFields.values()) f.mouseClicked(mx, my, mb);
		}
	}

	private void closeClicked() {
		parent.argsConfirmed();
		setVisible(false);
		alert("clicked")
	}

	protected void keyTyped(char keyChar, int keyId) {
		if (keyId == 1) {
			setVisible(false);
		}
		else {
			for (GuiTextField f : textFields.values()) f.textboxKeyTyped(keyChar, keyId);
		}
	}

}
