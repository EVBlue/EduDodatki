package com.evblue.edukacja.handlers;

import com.evblue.edukacja.util.Reference;
import com.evblue.edukacja.data.CapabilitiesEnum;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityEvent;

import java.util.List;

public class PlayerProps //implements IExtendedEntityProperties 
{
	
	public static final String ID = Reference.MOD_ID;
	private static final int PROPS_ID = 27;
	private final EntityPlayer player;
	
	private double freeze_x;
	private double freeze_y;
	private double freeze_z;
	
	private int entityCleanTimer;
	
	public PlayerProps(EntityPlayer player) {
		this.player = player;
	//	this.player.getDataWatcher().addObject(PROPS_ID, 0);
	}
	
	public static void register(EntityPlayer player) {
	//	player.registerExtendedProperties(ID, new PlayerProps(player));
	}
	
	public static PlayerProps get(Entity entity) {
		return null;
	//	return (PlayerProps) entity.registerExtendedProperties(ID);
	}
	
	public int getProps() {
	// return player.getDataWatcher().getWatchableObjectInt(PROPS_ID)
	}
	
	public void setProps(int bytes) {
	//	player.getDataWatcher().updateObject(PROPS_ID, bytes);
	}
	
	public void saveNBTData(NBTTagCompound compound) {
		
	}
	
	public void loadNBTData(NBTTagCompound compound) {
		
	}
	
	public void init(Entity entity, World world) {
		
	}
	
	public void flush() {
		setProps(0);
	}
	
	public void freeze() {
		freeze_x = player.posX;
		freeze_y = player.posY;
		freeze_z = player.posZ;
		enableProp(CapabilitiesEnum.FROZEN);
		player.setSneaking(false);
	}
	
	public void unFreeze() {
		disableProp(CapabilitiesEnum.FROZEN);
	}
	
	public void enableProp(CapabilitiesEnum prop) {
		setProps(getProps() | prop.getCode());
	}
	
	public void toggleProp(CapabilitiesEnum prop) {
		setProps(getProps() ^ prop.getCode());
		boolean whatnow = isPropEnabled(prop);
		if (whatnow) {
			player.sendMessage(new TextComponentString(TextFormatting.GREEN + "edupanel.turned.on"));
		}
		else {
			player.sendMessage(new TextComponentString(TextFormatting.BLUE + "edupanel.turned.off"));
		}
	}
	
	public void disableProp(CapabilitiesEnum prop) {
		setProps(getProps() & ~prop.getCode());
	}
	
	public boolean isPropEnabled(CapabilitiesEnum prop) {
		return (getProps() & prop.getCode()) > 0;
	}
	
	public void tick () {
		if (entityCleanTimer > 0) entityCleanTimer--;
		
		if (isPropEnabled(CapabilitiesEnum.REMOVE_HOSTILES) && entityCleanTimer <= 0) {
			entityCleanTimer = 40;
			List entities = player.world.getEntitiesWithinAABB(EntityMob.class, player.getEntityBoundingBox());
			for (Object obj : entities) {
				((EntityMob) obj).setDead();
			}
		}
		
		if (isPropEnabled(CapabilitiesEnum.FROZEN) &&(player.posX != freeze_x || player.posY != freeze_y || player.posZ != freeze_z)) {
			player.motionX = 0;
			player.motionY = 0;
			player.motionZ = 0;
			if (player.world.isRemote) {
				player.setPosition(player.prevPosX, player.prevPosY, player.prevPosZ);
			}
			else {
				player.setPositionAndUpdate(freeze_x, freeze_y, freeze_z);
				player.fallDistance = 0.0F;
			}
		}
	}

}
