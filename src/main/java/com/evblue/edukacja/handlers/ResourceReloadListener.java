package com.evblue.edukacja.handlers;

import java.util.function.Predicate;

import com.evblue.edukacja.util.MenuParser;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.IResourceManager;
import net.minecraftforge.client.resource.IResourceType;
import net.minecraftforge.client.resource.ISelectiveResourceReloadListener;

public class ResourceReloadListener  implements ISelectiveResourceReloadListener {
	private static boolean allowCustom = true;
	private static boolean blockReloading;
	
	public static void update() {
		if (blockReloading) return;
		
		if (!allowCustom || !MenuParser.instance.checkAndParseCustom()) {
			MenuParser.instance.parseMenu(I18n.format("edupanel.menufile"), false);
		}
	}
	
	public static void setAllowCustom(boolean allowCustom) {
		ResourceReloadListener.allowCustom = allowCustom;
	}
	
	public static void setBlockReloading(boolean blockReloading) {
		ResourceReloadListener.blockReloading = blockReloading;
	}
	
	@Override
	public void onResourceManagerReload(IResourceManager manager) {
		update();
	}

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager, Predicate<IResourceType> resourcePredicate) {
		// TODO Auto-generated method stub
		
	}
	

}
