package com.evblue.edukacja.handlers;

import com.evblue.edukacja.Main;
import com.evblue.edukacja.data.ActionsEnum;
import com.evblue.edukacja.data.RequestEnum;
import com.evblue.edukacja.handlers.packets.ResponsePacket;
import com.evblue.edukacja.util.FileProcessor;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TextComponentString;


public class ActionHandler {
	
	public boolean clientCheck(String targetName, EntityPlayer player, EntityPlayer target, ActionsEnum action) {
		return !(action.isRequiresTarget() && checkTarget(targetName, target, player, false));
	}

	public void serverWork(String targetName, EntityPlayerMP player, EntityPlayerMP target, ActionsEnum action) {
		if (!Main.proxy.canPlayerUsePanel(player)) {
			printNoPerms(player);
			FileProcessor.appendToAdminLog(player.getCommandSenderEntity() + "tries to use command, but no perms.");
			return;
		}
		
		PlayerProps props = PlayerProps.get(player);
		
		if (action.getProp() != null) {
			props.toggleProp(action.getProp());
		}
		else if (action == ActionsEnum.FLY_TOGGLE) {
			player.capabilities.allowFlying = !player.capabilities.allowFlying;
			Main.packets.sendTo(new ResponsePacket(RequestEnum.FLY, "", player.capabilities.allowFlying ? (byte)3 : (byte)2), player);
		}
		
		if (action.isRequiresTarget() && checkTarget(targetName, target, player, true)) return;
		PlayerProps propsTarget;
		if (target == null) return;
		propsTarget = PlayerProps.get(target);
		
		if (action == ActionsEnum.HEAL) {
			target.setHealth(target.getMaxHealth());
		}
		else if (action == ActionsEnum.KILL) {
			target.attackEntityFrom(DamageSource.OUT_OF_WORLD, Float.MAX_VALUE);
		}
		else if (action == ActionsEnum.POISON) {
			target.addPotionEffect(new PotionEffect(Potion.getPotionById(19), 600, 1));
		}
		else if (action == ActionsEnum.FEED) {
			target.getFoodStats().addStats(20, 0.8F);
		}
		else if (action == ActionsEnum.STARVE) {
			target.getFoodStats().setFoodLevel(1);
			target.getFoodStats().setFoodSaturationLevel(0);
		}
		else if (action == ActionsEnum.DISMOUNT) {
			target.dismountEntity(null);
		}
		else if (action == ActionsEnum.IGNITE) {
			target.setFire(8);
		}
		else if (action == ActionsEnum.CLEAR_INVENTORY) {
			target.inventory.clear();
			target.inventoryContainer.detectAndSendChanges();
			if (!target.capabilities.isCreativeMode) {
				target.updateHeldItem();
			}
		}
		else if (action == ActionsEnum.UNPOTION) {
			target.clearActivePotions();
		}
		else if (action == ActionsEnum.FREEZE) {
			propsTarget.freeze();
		}
		else if (action == ActionsEnum.UNFREEZE) {
			propsTarget.unFreeze();
		}
	}
	
	public boolean checkTarget(String name, EntityPlayer checkfor, EntityPlayer sender, boolean server) {
		if (name == null) {
			printChatError(sender, I18n.format("edukacja.notarget"), server);
			return true;
		}
		if (name.trim().equals("")) {
			printChatError(sender, I18n.format("edukacja.notarget"), server);
		}
		if (checkfor == null && server) {
			printChatError(sender, "Player must be online!", true);
			return true;
		}
		return false;
	}
	
	public void printChatError(EntityPlayer player, String msg, boolean server) {
		player.sendMessage(new TextComponentString(TextFormatting.RED + "[EduErr" + (server ? "Server" : "Client") + "]" + msg));
	}
	
	public void printNoPerms(EntityPlayer player) {
		player.sendMessage(new TextComponentString(TextFormatting.DARK_RED + "edukacja.permissions"));
	}

}
