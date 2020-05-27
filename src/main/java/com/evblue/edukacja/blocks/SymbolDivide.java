package com.evblue.edukacja.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class SymbolDivide extends BlockBase{
	
	public SymbolDivide(String name, Material material) {
		
		super(name, material);
		
		setSoundType(SoundType.METAL);
		setHardness(5.0F);
		setResistance(500.0F);
		setHarvestLevel("pickaxe", 2);
		setLightLevel(1.0F);
		setLightOpacity(0);
	}

}
