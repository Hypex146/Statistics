package plugin;

import java.util.logging.Level;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import plugin.utilities.LogLevel;
import plugin.utilities.StConfigurator;
import plugin.utilities.StLogger;

public class Statistics extends JavaPlugin {
	private FileConfiguration config_;
	private StConfigurator configurator_;
	private StLogger logger_;
	// configuration
	private boolean enable_;
	private boolean enable_greeting_;
	// constants
	private final String plugin_name_ = "Command_control";
	
	public Statistics() {
		configurator_=  new StConfigurator(this);
		logger_ = new StLogger(this, LogLevel.STANDART);
	}
	
	public String getPluginName() {
		return plugin_name_;
	}
	
	public boolean isEnable() {
		return enable_;
	}
	
	public StConfigurator getStConfigurator() {
		return configurator_;
	}
	
	public StLogger getStLogger() {
		return logger_;
	}
	
	public void reloadPluginConfig() {
		saveDefaultConfig();
		reloadConfig();
		reloadParams();
		saveConfig();
	}
	
	private void reloadParams() {
		config_ = getConfig();
		ConfigurationSection main_section = configurator_.getConfigurationSection(config_, "Main-settings");
		
		// Configure log level settings
		String log_level_section_name = "log-level";
		LogLevel log_level = LogLevel.toEnum(configurator_.getString(main_section, 
				log_level_section_name, LogLevel.STANDART.toString()));
		if (log_level == null) {
			configurator_.setString(main_section, log_level_section_name, LogLevel.STANDART.toString());
			log_level = LogLevel.STANDART;
		}
		logger_.setLogLevel(log_level);
		
		enable_ = configurator_.getBoolean(main_section, "enable", true);
		
		enable_greeting_ = configurator_.getBoolean(main_section, "enable_greeting", true);
	}
	
	public boolean checkEnableStatus() {
		logger_.log(LogLevel.DEBUG, Level.INFO, "Checking whether the plugin should work.");
		if (enable_ == true) { 
			logger_.log(LogLevel.DEBUG, Level.INFO, "The plugin should work");
			return true; 
			}
		logger_.log(LogLevel.DEBUG, Level.INFO, "The plugin should not work. Disabling the plugin.");
		getServer().getPluginManager().disablePlugin(this);
		logger_.log(LogLevel.STANDART, Level.INFO, "The plugin is disabled.");
		return false;
	}
	
	private void printGreetingInConsole() {
		String greeting = "\n===========================\n"
				+ "|   |     ___   ___        \n"
				+ "|   |\\   /|  |  |     \\  / \n"
				+ "|===| \\ / |__|  |__    \\/  \n"
				+ "|   |  |  |     |      /\\  \n"
				+ "|   |  |  |     |__   /  \\ \n"
				+ "===========================";
		logger_.log(LogLevel.STANDART, Level.INFO, greeting);
	}
	
	@Override
	public void onEnable() {
		reloadPluginConfig();
		if (!checkEnableStatus()) { return; }
		if (enable_greeting_) { printGreetingInConsole(); }
	}
	
}
