package com.evblue.edukacja.init;

import java.util.ArrayList;
import java.util.List;

import com.evblue.edukacja.blocks.BlockBase;
import com.evblue.edukacja.blocks.NumberEight;
import com.evblue.edukacja.blocks.NumberEighteen;
import com.evblue.edukacja.blocks.NumberEleven;
import com.evblue.edukacja.blocks.NumberFive;
import com.evblue.edukacja.blocks.NumberFiveteen;
import com.evblue.edukacja.blocks.NumberFour;
import com.evblue.edukacja.blocks.NumberFourteen;
import com.evblue.edukacja.blocks.NumberNine;
import com.evblue.edukacja.blocks.NumberNineteen;
import com.evblue.edukacja.blocks.NumberOne;
import com.evblue.edukacja.blocks.NumberSeven;
import com.evblue.edukacja.blocks.NumberSeventeen;
import com.evblue.edukacja.blocks.NumberSix;
import com.evblue.edukacja.blocks.NumberSixteen;
import com.evblue.edukacja.blocks.NumberTen;
import com.evblue.edukacja.blocks.NumberThirteen;
import com.evblue.edukacja.blocks.NumberThree;
import com.evblue.edukacja.blocks.NumberTwelve;
import com.evblue.edukacja.blocks.NumberTwenty;
import com.evblue.edukacja.blocks.NumberTwo;
import com.evblue.edukacja.blocks.NumberZero;
import com.evblue.edukacja.blocks.SymbolArrowDown;
import com.evblue.edukacja.blocks.SymbolArrowLeft;
import com.evblue.edukacja.blocks.SymbolArrowRight;
import com.evblue.edukacja.blocks.SymbolArrowUp;
import com.evblue.edukacja.blocks.SymbolDivide;
import com.evblue.edukacja.blocks.SymbolEqual;
import com.evblue.edukacja.blocks.SymbolMinus;
import com.evblue.edukacja.blocks.SymbolMultiply;
import com.evblue.edukacja.blocks.SymbolPlus;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ModBlocks {
	
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	
	//BLOCKS
	public static final Block NUMBERZERO = new NumberZero("numberzero", Material.GLASS);
	public static final Block NUMBERONE = new NumberOne("numberone", Material.GLASS);
	public static final Block NUMBERTWO = new NumberTwo("numbertwo", Material.GLASS);
	public static final Block NUMBERTHREE = new NumberThree("numberthree", Material.GLASS);
	public static final Block NUMBERFOUR = new NumberFour("numberfour", Material.GLASS);
	public static final Block NUMBERFIVE = new NumberFive("numberfive", Material.GLASS);
	public static final Block NUMBERSIX = new NumberSix("numbersix", Material.GLASS);
	public static final Block NUMBERSEVEN = new NumberSeven("numberseven", Material.GLASS);
	public static final Block NUMBEREIGHT = new NumberEight("numbereight", Material.GLASS);
	public static final Block NUMBERNINE = new NumberNine("numbernine", Material.GLASS);
	public static final Block NUMBERTEN = new NumberTen("numberten", Material.GLASS);
	public static final Block NUMBERELEVEN = new NumberEleven("numbereleven", Material.GLASS);
	public static final Block NUMBERTWELVE = new NumberTwelve("numbertwelve", Material.GLASS);
	public static final Block NUMBERTHIRTEEN = new NumberThirteen("numberthirteen", Material.GLASS);
	public static final Block NUMBERFOURTEEN = new NumberFourteen("numberfourteen", Material.GLASS);
	public static final Block NUMBERFIVETEEN = new NumberFiveteen("numberfiveteen", Material.GLASS);
	public static final Block NUMBERSIXTEEN = new NumberSixteen("numbersixteen", Material.GLASS);
	public static final Block NUMBERSEVENTEEN = new NumberSeventeen("numberseventeen", Material.GLASS);
	public static final Block NUMBEREIGHTEEN = new NumberEighteen("numbereighteen", Material.GLASS);
	public static final Block NUMBERNINETEEN = new NumberNineteen("numbernineteen", Material.GLASS);
	public static final Block NUMBERTWENTY = new NumberTwenty("numbertwenty", Material.GLASS);
	public static final Block SYMBOLPLUS = new SymbolPlus("symbolplus", Material.GLASS);
	public static final Block SYMBOLMINUS = new SymbolMinus("symbolminus", Material.GLASS);
	public static final Block SYMBOLMULTIPLY = new SymbolMultiply("symbolmultiply", Material.GLASS);
	public static final Block SYMBOLDIVIDE = new SymbolDivide("symboldivide", Material.GLASS);
	public static final Block SYMBOLEQUAL = new SymbolEqual("symbolequal", Material.GLASS);
	public static final Block SYMBOLARROWUP = new SymbolArrowUp("symbolarrowup", Material.GLASS);
	public static final Block SYMBOLARROWDOWN = new SymbolArrowDown("symbolarrowdown", Material.GLASS);
	public static final Block SYMBOLARROWLEFT = new SymbolArrowLeft("symbolarrowleft", Material.GLASS);
	public static final Block SYMBOLARROWDOW = new SymbolArrowRight("symbolarrowright", Material.GLASS);
	

}
