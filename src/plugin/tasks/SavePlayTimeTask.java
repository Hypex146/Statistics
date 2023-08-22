package plugin.tasks;

import org.bukkit.configuration.file.FileConfiguration;

import plugin.Statistics;

public class SavePlayTimeTask implements Runnable {
	private Statistics mainPlugin_;
	private FileConfiguration tmp_buffer_;
	private String tmp_buffer_path_;
	private int delay_;
	private int period_;
	private int task_id_;
	
	public SavePlayTimeTask(Statistics mainPlugin, FileConfiguration tmp_buffer, 
		String tmp_buffer_path, int delay, int period) {
		mainPlugin_ = mainPlugin;
		tmp_buffer_ = tmp_buffer;
		tmp_buffer_path_ = tmp_buffer_path;
		delay_ = delay;
		period_ = period;
	}

	@Override
	public void run() {
		mainPlugin_.getStConfigurator().saveCustomConfig(tmp_buffer_path_, tmp_buffer_);
		return;
	}
	
	public boolean isRunning() {
		if (task_id_ == -1) {
			return false;
		}
		return (mainPlugin_.getServer().getScheduler().isCurrentlyRunning(task_id_) 
				|| mainPlugin_.getServer().getScheduler().isQueued(task_id_));
	}
	
	public boolean register() {
		task_id_ = mainPlugin_.getServer().getScheduler().scheduleSyncRepeatingTask(
				mainPlugin_, this, delay_, period_);
		if (task_id_ == -1) {
			return false;
		} else {
			return true;
		}
	}
	
	public void remove() {
		mainPlugin_.getServer().getScheduler().cancelTask(task_id_);
	}
	
	public boolean reload(int new_delay, int new_period) {
		delay_ = new_delay;
		period_ = new_period;
		if (isRunning()) {
			remove();
		}
		return register();
	}
	
}
