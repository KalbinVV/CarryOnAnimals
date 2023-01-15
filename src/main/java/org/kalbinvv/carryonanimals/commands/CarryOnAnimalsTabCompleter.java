package org.kalbinvv.carryonanimals.commands;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class CarryOnAnimalsTabCompleter implements TabCompleter{

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, 
			String alias, String[] args) {
		return Arrays.asList("help", "protections", "reload");
	}

}
