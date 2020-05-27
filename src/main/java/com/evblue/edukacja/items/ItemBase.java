package com.evblue.edukacja.items;

import com.evblue.edukacja.Main;
import com.evblue.edukacja.init.ModItems;
import com.evblue.edukacja.util.IHasModel;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemBase extends Item implements IHasModel{
	
	public ItemBase(String name)
	{
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(Main.EdukacjaTab);
		
		ModItems.ITEMS.add(this);
	}

	@Override
	public void registerModels() 
	{
	
		Main.proxy.registerItemRenderer(this, 0, "inventory");
		
	}

	
}
