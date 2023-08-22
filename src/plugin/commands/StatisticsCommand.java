package plugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import plugin.Statistics;

public class StatisticsCommand implements CommandExecutor {
	private Statistics main_plugin_;
	// configuration
	// constants
	
	public StatisticsCommand(Statistics main_plugin) {
		main_plugin_ = main_plugin;
	}
	
	private boolean reloadCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!sender.hasPermission("statistics.admin") && !(sender instanceof ConsoleCommandSender)) {
			sender.sendMessage("You don't have permission to do this!");
			return false;
		}
		main_plugin_.reloadPluginConfig();
		main_plugin_.checkEnableStatus();
		sender.sendMessage("The configuration was successfully reloaded!");
		return true;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 1) {
			if (args[0].equals("reload")) {
				return reloadCommand(sender, command, label, args);
			}
		}
		sender.sendMessage("Error in command syntax!");
		return false;
	}
	
}
