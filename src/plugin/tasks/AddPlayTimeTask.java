package plugin.tasks;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import plugin.Statistics;
import plugin.utilities.StConfigurator;

public class AddPlayTimeTask implements Runnable {
	private Statistics mainPlugin_ = null;
	private Player player_ = null;
	private int delay_ = -1;
	private int period_ = -1;
	private int task_id_ = -1;
	
	public AddPlayTimeTask(Statistics mainPlugin, Player player, int delay, int period) {
		mainPlugin_ = mainPlugin;
		player_= player;
		delay_ = delay;
		period_ = period;
	}

	@Override
	public void run() {
		if (!player_.isOnline()) {
			remove();
			return;
		}
		FileConfiguration buffer = mainPlugin_.getPlayTimeManager().getTmpBuffer();
		StConfigurator configurator = mainPlugin_.getStConfigurator();
		double value = configurator.getDouble(buffer, player_.getDisplayName(), 0);
		configurator.setDouble(buffer, player_.getDisplayName(), value + (period_ / 20));
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
