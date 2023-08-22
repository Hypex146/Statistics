package plugin.managers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import plugin.Statistics;
import plugin.listeners.PlayTimeListener;
import plugin.tasks.AddPlayTimeTask;
import plugin.tasks.SavePlayTimeTask;
import plugin.utilities.StConfigurator;

public class PlayTimeManager {
	private Statistics mainPlugin_ = null;
	private Map<UUID, AddPlayTimeTask> add_play_time_tasks_ = null;
	private SavePlayTimeTask save_play_time_task_ = null;
	private FileConfiguration tmp_buffer_ = null;
	private PlayTimeListener listener_ = null;
	// configuration
	private int add_delay_ = -1;
	private int add_period_ = -1;
	private int save_delay_ = -1;
	private int save_period_ = -1;
	// constants
	private final String tmp_buffer_path_ = "tmp-file/tmp-buffer.yml";
	private final String add_delay_field_ = "delay-to-add-time";
	private final int add_delay_default_ = 20;
	private final String add_period_field_ = "period-to-add-time";
	private final int add_period_default_ = 20;
	private final String save_delay_field_ = "delay-to-save-time";
	private final int save_delay_default_ = 1200;
	private final String save_period_field_ = "period-to-save-time";
	private final int save_period_default_ = 1200;
	
	
	public PlayTimeManager(Statistics mainPlugin) {
		mainPlugin_ = mainPlugin;
		tmp_buffer_ = mainPlugin_.getStConfigurator().getCustomConfig(tmp_buffer_path_);
		add_play_time_tasks_ = new HashMap<UUID, AddPlayTimeTask>();
		save_play_time_task_ = new SavePlayTimeTask(mainPlugin_, tmp_buffer_, tmp_buffer_path_, save_delay_, save_period_);
		listener_ = new PlayTimeListener(mainPlugin_);
		listener_.register();
	}
	
	public FileConfiguration getTmpBuffer() {
		return tmp_buffer_;
	}
	
	public void reloadParams() {
		FileConfiguration config = mainPlugin_.getConfig();
		StConfigurator configurator = mainPlugin_.getStConfigurator();
		ConfigurationSection play_time_section = configurator.getConfigurationSection(
				config, "PlayTime-settings");
		
		add_delay_ = configurator.getInt(play_time_section, add_delay_field_, add_delay_default_);
		
		add_period_ = configurator.getInt(play_time_section, add_period_field_, add_period_default_);
		
		save_delay_ = configurator.getInt(play_time_section, save_delay_field_, save_delay_default_);
		
		save_period_ = configurator.getInt(play_time_section, save_period_field_, save_period_default_);
		
		save_play_time_task_.reload(save_delay_, save_period_);
		
		for (AddPlayTimeTask task : add_play_time_tasks_.values()) {
			task.reload(add_delay_, add_period_);
		}
	}
	
	public AddPlayTimeTask getAddTask(Player player) {
		return add_play_time_tasks_.get(player.getUniqueId());
	}
	
	public boolean registerAddTask(Player player) {
		AddPlayTimeTask task = new AddPlayTimeTask(mainPlugin_, player, add_delay_, add_period_);
		add_play_time_tasks_.put(player.getUniqueId(), task);
		return task.register();
	}
	
	public void removeAddTask(Player player) {
		AddPlayTimeTask task = getAddTask(player);
		if (task == null) {
			return;
		}
		task.remove();
		add_play_time_tasks_.remove(player.getUniqueId());
	}
	
	public void onDisable() {
		save_play_time_task_.run();
	}
	
}
