package me.wand555.Challenge.WorldLinking;

import java.util.ArrayList;

import java.util.HashSet;

import org.bukkit.World;
import org.bukkit.entity.Player;

import me.wand555.Challenge.ChallengeData.WorldPlayerData;

public class WorldLinkManager {

	public static HashSet<World> worlds = new HashSet<World>();
	public static ArrayList<WorldPlayerData> pData = new ArrayList<WorldPlayerData>();
	
	
	
	public static void firstTimeJoining(Player p) {
		pData.add(new WorldPlayerData(p));
	}
}
