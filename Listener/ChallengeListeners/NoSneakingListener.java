package ChallengeListeners;

import org.bukkit.event.EventHandler;

import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import me.wand555.Challenge.Challenge.Challenge;
import me.wand555.Challenge.Challenge.ChallengeEndReason;
import me.wand555.Challenge.Challenge.ChallengeProfile;
import me.wand555.Challenge.Challenge.Settings;

public class NoSneakingListener implements Listener {

	private Challenge plugin;
	
	public NoSneakingListener(Challenge plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onSneakingEvent(PlayerToggleSneakEvent event) {
		if(Settings.noSneaking) {
			if(Settings.canTakeEffect()) {
				ChallengeProfile.endChallenge(ChallengeEndReason.NO_SNEAKING);
			}
		}
	}
}
