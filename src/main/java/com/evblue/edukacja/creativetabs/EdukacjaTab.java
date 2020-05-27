package com.evblue.edukacja.creativetabs;

import com.evblue.edukacja.Main;
import com.evblue.edukacja.init.ModItems;
import com.evblue.edukacja.util.Reference;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class EdukacjaTab extends CreativeTabs{
	
	public EdukacjaTab()
	{
		super(Reference.MOD_ID);
		
	}
	
	@Override
	public ItemStack getTabIconItem()
	{
		return new ItemStack(ModItems.ELOGO);
	}
}
