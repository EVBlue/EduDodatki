package com.evblue.edukacja.blocks;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;

public class SymbolEqual extends BlockBase{
	
	public SymbolEqual(String name, Material material) {
		
		super(name, material);
		
		setSoundType(SoundType.METAL);
		setHardness(5.0F);
		setResistance(500.0F);
		setHarvestLevel("pickaxe", 2);
		setLightLevel(1.0F);
		setLightOpacity(0);
		setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	
	@Override
		protected BlockStateContainer createBlockState()
		{
			return new BlockStateContainer (this, new IProperty[] {FACING});
		}
	
	@Override
		public IBlockState getStateFromMeta(int meta)
		{
			EnumFacing enumfacing = EnumFacing.getFront(meta);
			if (enumfacing.getAxis() == EnumFacing.Axis.Y)
			{
				enumfacing = EnumFacing.NORTH;
			}
			
			return this.getDefaultState().withProperty(FACING, enumfacing);
		}
	
	@Override
		public int getMetaFromState(IBlockState state)
		{
			EnumFacing facing=state.getValue(FACING);
			int meta=((EnumFacing)state.getValue(FACING)).getIndex();
			return meta;
		}

}
