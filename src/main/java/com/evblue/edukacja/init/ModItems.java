package com.evblue.edukacja.init;

import java.util.ArrayList;
import java.util.List;

import com.evblue.edukacja.Main;
import com.evblue.edukacja.items.ItemBase;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ModItems {

	public static final List<Item> ITEMS = new ArrayList<Item>();
	
	public static final Item ELOGO = new ItemBase("elogo").setCreativeTab(CreativeTabs.MATERIALS);
	public static final Item OBSIDIAN_INGOT = new ItemBase("obsidian_ingot");

			
}
