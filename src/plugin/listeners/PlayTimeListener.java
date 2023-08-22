package plugin.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import plugin.Statistics;

public class PlayTimeListener implements Listener {
	private Statistics mainPlugin_ = null;
	
	public PlayTimeListener(Statistics mainPlugin) {
		mainPlugin_ = mainPlugin;
	}
	
	public void register() {
		mainPlugin_.getServer().getPluginManager().registerEvents(this, mainPlugin_);
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		mainPlugin_.getPlayTimeManager().registerAddTask(event.getPlayer());
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		mainPlugin_.getPlayTimeManager().removeAddTask(event.getPlayer());
	}
	
}
