package org.kalbinvv.carryon.commands;


import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.kalbinvv.carryon.CarryOn;
import org.kalbinvv.carryon.utils.ChatUtils;

public class CarryOnAnimalsCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, 
			String label, String[] args) {
		if(args.length < 1) {
			helpSubcommand(sender);
			return true;
		}

		if(args[0].equals("reload")) {
			reloadSubcommand(sender);
		}else if(args[0].equals("protections")) {
			protectionsSubcommand(sender);
		}else {
			helpSubcommand(sender);
		}

		return true;
	}

	private void helpSubcommand(CommandSender sender) {
		Configuration configuration = CarryOn.getConfiguration();

		String helpMessage = configuration.getString("helpMessage");

		if(sender instanceof Player) {
			Player player = (Player) sender;

			String prefix = configuration.getString("prefix");

			ChatUtils.sendMessage(
					prefix, 
					helpMessage, 
					player);
		}else {
			Logger logger = CarryOn.getPlugin().getLogger();
			logger.info(
					ChatUtils.removeAllColorsCodes(helpMessage));
		}

	}

	private void reloadSubcommand(CommandSender sender) {
		Configuration configuration = CarryOn.getConfiguration();

		boolean hasPermission = true;

		String reloadMessage = configuration.getString("reloadMessage");

		if(sender instanceof Player) {
			Player player = (Player) sender;

			hasPermission = player.hasPermission("carryonanimals.admin");

			String prefix = configuration.getString("prefix");

			if(hasPermission) {
				ChatUtils.sendMessage(
						prefix, 
						reloadMessage, 
						player);
			}else {
				ChatUtils.sendMessage(
						prefix, 
						configuration.getString("permissionMessage"), 
						player);
			}
		}else {
			CarryOn.getPlugin().getLogger().info(
					ChatUtils.removeAllColorsCodes(reloadMessage));
		}

		if(hasPermission) {
			CarryOn.loadConfiguration(true);
		}
	}

	private void protectionsSubcommand(CommandSender sender) {
		Configuration configuration = CarryOn.getConfiguration();

		boolean hasPermission = true;

		String protectionsMessage = String.format("%s%s", 
				configuration.getString("protectionsMessage"), CarryOn
				.getProtectionsList().getStringOfEnabledProtections());

		if(sender instanceof Player) {
			Player player = (Player) sender;

			hasPermission = player.hasPermission("carryonanimals.admin");

			String prefix = configuration.getString("prefix");

			if(hasPermission) {
				ChatUtils.sendMessage(
						prefix, 
						protectionsMessage, 
						player);
			}else {
				ChatUtils.sendMessage(
						prefix, 
						configuration.getString("permissionMessage"), 
						player);
			}
		}else {
			CarryOn.getPlugin().getLogger().info(
					ChatUtils.removeAllColorsCodes(protectionsMessage));
		}

	}

}
