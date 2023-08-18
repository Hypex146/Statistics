package plugin.utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import plugin.Statistics;


public class StConfigurator {
	private Statistics main_plugin_;
	// configuration
	// constants
	
	public StConfigurator(Statistics main_plugin) {
		main_plugin_ = main_plugin;
	}
	
	public FileConfiguration getCustomConfig(String pathToFile) {
		File configFile = new File(main_plugin_.getDataFolder()+"/"+pathToFile);
		File folderFile = configFile.getParentFile();
		if (!folderFile.exists()) {
			folderFile.mkdirs();
			main_plugin_.getStLogger().log(LogLevel.DEBUG, Level.INFO, "Folder created: " + folderFile.getAbsolutePath());
		}
		if (!configFile.exists()) {
			try {
				configFile.createNewFile();
				main_plugin_.getStLogger().log(LogLevel.DEBUG, Level.INFO, "File created: " + configFile.getAbsolutePath());
			} catch (IOException e) {
				main_plugin_.getStLogger().log(LogLevel.MINIMAL, Level.SEVERE, "Error creating configuration file (IOException): "
						+configFile.getAbsolutePath());
				e.printStackTrace();
				return null;
			}
		}
		YamlConfiguration customConfig = new YamlConfiguration();
		try {
			customConfig.load(configFile);
			main_plugin_.getStLogger().log(LogLevel.DEBUG, Level.INFO, "The configuration file is loaded: " + configFile.getAbsolutePath());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			main_plugin_.getStLogger().log(LogLevel.MINIMAL, Level.SEVERE, "Error loading configuration file (FileNotFoundException): "
					+configFile.getAbsolutePath());
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			main_plugin_.getStLogger().log(LogLevel.MINIMAL, Level.SEVERE, "Error loading configuration file (IOException): "
					+configFile.getAbsolutePath());
			e.printStackTrace();
			return null;
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
			main_plugin_.getStLogger().log(LogLevel.MINIMAL, Level.SEVERE, "Error loading configuration file (InvalidConfigurationException): "
					+configFile.getAbsolutePath());
			e.printStackTrace();
			return null;
		}
		return customConfig;
	}
	
	public int saveCustomConfig(String pathToFile, FileConfiguration configToSave) {
		File configFile = new File(main_plugin_.getDataFolder()+"/"+pathToFile);
		try {
			configToSave.save(configFile);
			main_plugin_.getStLogger().log(LogLevel.DEBUG, Level.INFO, "The configuration file is saved: "+configFile.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
			main_plugin_.getStLogger().log(LogLevel.MINIMAL, Level.SEVERE,
					"Error saving the configuration file: (IOException) "+configFile.getAbsolutePath());
			e.printStackTrace();
			return -1;
		}
		return 0;
	}
	
	public boolean getBoolean(ConfigurationSection config, String field, boolean defaultValue) {
		if (!config.isBoolean(field)) {
			main_plugin_.getStLogger().log(LogLevel.DEBUG, Level.WARNING, 
					"In the field with the name \"" + field + "\" incorrect value is set (not boolean)");
			config.set(field, defaultValue);
			main_plugin_.getStLogger().log(LogLevel.DEBUG, Level.WARNING, 
					"In the field with the name \"" + field + "\" the default value is set");
		}
		return config.getBoolean(field);
	}
	
	public void setBoolean(ConfigurationSection config, String field, boolean value) {
		config.set(field, value);
	}
	
	public int getInt(ConfigurationSection config, String field, int defaultValue) {
		if (!config.isInt(field)) {
			main_plugin_.getStLogger().log(LogLevel.DEBUG, Level.WARNING, 
					"In the field with the name \"" + field + "\" incorrect value is set (not int)");
			config.set(field, defaultValue);
			main_plugin_.getStLogger().log(LogLevel.DEBUG, Level.WARNING, 
					"In the field with the name \"" + field + "\" the default value is set");
		}
		return config.getInt(field);
	}
	
	public void setInt(ConfigurationSection config, String field, int value) {
		config.set(field, value);
	}
	
	public String getString(ConfigurationSection config, String field, String defaultValue) {
		if (!config.isString(field)) {
			main_plugin_.getStLogger().log(LogLevel.DEBUG, Level.WARNING, 
					"In the field with the name \"" + field + "\" incorrect value is set (not string)");
			config.set(field, defaultValue);
			main_plugin_.getStLogger().log(LogLevel.DEBUG, Level.WARNING, 
					"In the field with the name \"" + field + "\" the default value is set");
		}
		return config.getString(field);
	}
	
	public void setString(ConfigurationSection config, String field, String value) {
		config.set(field, value);
	}
	
	public double getDouble(ConfigurationSection config, String field, double defaultValue) {
		if (!config.isDouble(field)) {
			main_plugin_.getStLogger().log(LogLevel.DEBUG, Level.WARNING, 
					"In the field with the name \"" + field + "\" incorrect value is set (not double)");
			config.set(field, defaultValue);
			main_plugin_.getStLogger().log(LogLevel.DEBUG, Level.WARNING, 
					"In the field with the name \"" + field + "\" the default value is set");
		}
		return config.getDouble(field);
	}
	
	public void setDouble(ConfigurationSection config, String field, double value) {
		config.set(field, value);
	}
	
	public List<String> getStringList(ConfigurationSection config, String field){
		List<String> stringList;
		stringList = config.getStringList(field);
		if (stringList.size()>0) {
			return stringList;
		}
		main_plugin_.getStLogger().log(LogLevel.DEBUG, Level.WARNING, 
				"In the field with the name \"" + field + "\" incorrect value is set (not string list)");
		config.set(field, stringList);
		main_plugin_.getStLogger().log(LogLevel.DEBUG, Level.WARNING, 
				"In the field with the name \"" + field + "\" the default value is set");
		return stringList;
	}
	
	public ConfigurationSection getConfigurationSection(ConfigurationSection config, String path) {
		if (!config.isConfigurationSection(path)) {
			return config.createSection(path);
		}
		return config.getConfigurationSection(path);
	}
	
	public boolean hasConfigurationSection(ConfigurationSection config, String path) {
		return config.isConfigurationSection(path);
	}
	
}