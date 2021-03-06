package ChallengeProfileListener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.wand555.Challenge.Challenge.Challenge;
import me.wand555.Challenge.ChallengeData.ChallengeProfile;
import me.wand555.Challenge.WorldLinking.WorldLinkManager;

public class PlayerJoinListener implements Listener {

	private Challenge plugin;
	
	public PlayerJoinListener(Challenge plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent event) {
		if(WorldLinkManager.worlds.contains(event.getPlayer().getWorld())) {
			ChallengeProfile.addToParticipants(event.getPlayer().getUniqueId());
		}
	}
}
