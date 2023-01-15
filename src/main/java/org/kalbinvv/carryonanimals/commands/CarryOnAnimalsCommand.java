package org.kalbinvv.carryonanimals.commands;

import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.kalbinvv.carryonanimals.CarryOnAnimals;
import org.kalbinvv.carryonanimals.configuration.Reloadable;
import org.kalbinvv.carryonanimals.protections.ProtectionsList;
import org.kalbinvv.carryonanimals.utils.ChatUtils;
import org.kalbinvv.carryonanimals.utils.MessageType;

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
		Configuration configuration = CarryOnAnimals.getPlugin().getConfig();

		String helpMessage = configuration.getString("messages.help");

		if(sender instanceof Player) {
			Player player = (Player) sender;

			String prefix = configuration.getString("messages.prefix");

			ChatUtils.sendMessage(
					prefix, 
					helpMessage, 
					player,
					MessageType.CommandResponse);
		}else {
			Logger logger = CarryOnAnimals.getPlugin().getLogger();
			logger.info(
					ChatUtils.removeAllColorsCodes(helpMessage));
		}

	}

	private void reloadSubcommand(CommandSender sender) {
		Configuration configuration = CarryOnAnimals.getPlugin().getConfig();

		boolean hasPermission = true;

		String reloadMessage = configuration.getString("messages.reload");

		if(sender instanceof Player) {
			Player player = (Player) sender;

			hasPermission = player.hasPermission("carryonanimals.admin");

			String prefix = configuration.getString("messages.prefix");

			if(hasPermission) {
				ChatUtils.sendMessage(
						prefix, 
						reloadMessage, 
						player,
						MessageType.CommandResponse);
			}else {
				ChatUtils.sendMessage(
						prefix, 
						configuration.getString("messages.permission"), 
						player,
						MessageType.CommandResponse);
			}
		}else {
			CarryOnAnimals.getPlugin().getLogger().info(
					ChatUtils.removeAllColorsCodes(reloadMessage));
		}

		if(hasPermission) {
			((Reloadable) CarryOnAnimals.getPlugin().getConfig()).reload();
		}
	}

	private void protectionsSubcommand(CommandSender sender) {
		Configuration configuration = CarryOnAnimals.getPlugin().getConfig();

		boolean hasPermission = true;

		String protectionsMessage = String.format("%s%s", 
				configuration.getString("messages.protections"), 
				ProtectionsList.init().getStringOfEnabledProtections());

		if(sender instanceof Player) {
			Player player = (Player) sender;

			hasPermission = player.hasPermission("carryonanimals.admin");

			String prefix = configuration.getString("messages.prefix");

			if(hasPermission) {
				ChatUtils.sendMessage(
						prefix, 
						protectionsMessage, 
						player,
						MessageType.CommandResponse);
			}else {
				ChatUtils.sendMessage(
						prefix, 
						configuration.getString("messages.permission"), 
						player,
						MessageType.CommandResponse);
			}
		}else {
			CarryOnAnimals.getPlugin().getLogger().info(
					ChatUtils.removeAllColorsCodes(protectionsMessage));
		}

	}

}
