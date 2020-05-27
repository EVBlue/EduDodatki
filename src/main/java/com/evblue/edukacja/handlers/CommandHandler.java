package com.evblue.edukacja.handlers;

import com.mojang.authlib.GameProfile;
import com.evblue.edukacja.Main;
import com.evblue.edukacja.data.RequestEnum;
import com.evblue.edukacja.handlers.packets.PlayerInfoPacket;
import com.evblue.edukacja.handlers.packets.ResponsePacket;
import com.evblue.edukacja.util.FileProcessor;
import net.minecraft.command.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.ArrayList;
import java.util.List;


public class CommandHandler extends CommandBase {
	
	public String getCommandName() {
		return "edupanel";
	}
	
	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}
	
	public String getCommandUsage(ICommandSender sender) {
		return "edupanel add/remove <User>";
	}
	
	public void processCommand(ICommandSender sender, String[] args) {
		if (args.length > 1) {
            if (args[0].equals("add")) {
                MinecraftServer minecraftserver = FMLCommonHandler.instance().getMinecraftServerInstance().getServer();
                GameProfile gameprofile = minecraftserver.getServer().func_152358_ax().func_152655_a(args[1]);
                if (gameprofile == null) {
                    throw new CommandException("Can't add user, profile not exists.");
                } else {
                    if (Main.dynStorage.permissed_users.contains(gameprofile.getId().toString())) {
                        throw new CommandException("User already added.");
                    }
                    Main.dynStorage.permissed_users.add(gameprofile.getId().toString());

                    try {
                        EntityPlayer target = CommandBase.getPlayer(minecraftserver, sender, args[1]);
                        Main.packets.sendTo(new PlayerInfoPacket(Main.proxy.canPlayerUsePanel(target)), (EntityPlayerMP) target);
                    } catch (PlayerNotFoundException ignored) {
                    }
                    ITextComponent message = new TextComponentString("User added.");
                    message.getStyle().setColor(TextFormatting.GREEN);
                    sender.sendMessage(message);
                    FileProcessor.writeUsers();
                }
	}
            else if (args[0].equals("rem") || args[0].equals("remove")) {
                MinecraftServer minecraftserver = FMLCommonHandler.instance().getMinecraftServerInstance().getServer();
                GameProfile gameprofile = minecraftserver.func_152358_ax().func_152655_a(args[1]);
                if (gameprofile == null) {
                    throw new CommandException("Can't add user, profile not exists.");
                } else {
                    if (!Main.dynStorage.permissed_users.contains(gameprofile.getId().toString())) {
                        throw new CommandException("Nothing to remove.");
                    }
                    Main.dynStorage.permissed_users.remove(gameprofile.getId().toString());

                    try {
                        EntityPlayer target = CommandBase.getPlayer(minecraftserver, sender, args[1]);
                        PlayerProps.get(target).flush();
                        Main.packets.sendTo(new ResponsePacket(RequestEnum.FLY, "", (byte)2), (EntityPlayerMP) target);
                        Main.packets.sendTo(new PlayerInfoPacket(Main.proxy.canPlayerUsePanel(target)), (EntityPlayerMP) target);
                    } catch (PlayerNotFoundException ignored) {
                    }
                    ITextComponent message = new TextComponentString("User removed.");
                    message.getStyle().setColor(TextFormatting.GREEN);
					sender.sendMessage(message);
                    FileProcessor.writeUsers();
                }
            } else throw new WrongUsageException(getCommandUsage(sender));
        } else throw new WrongUsageException(getCommandUsage(sender));
    }

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUsage(ICommandSender sender) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		// TODO Auto-generated method stub
		
	}

}
