package me.wand555.Challenge.Challenge;

import java.util.Random;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.WorldCreator;
import org.bukkit.World.Environment;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import ChallengeListeners.NoBlockBreakingListener;
import ChallengeListeners.NoBlockPlaceListener;
import ChallengeListeners.NoCraftListener;
import ChallengeListeners.NoDamageListener;
import ChallengeListeners.NoRegenListener;
import ChallengeListeners.NoSneakingListener;
import ChallengeListeners.PlayerDeathListener;
import ChallengeListeners.RandomizerListener;
import ChallengeListeners.SharedHPPlayerChangeLifeListener;
import ChallengeProfileListener.PlayerJoinListener;
import ChallengeProfileListener.PlayerQuitListener;
import EnderDragonListener.EnderDragonDeathListener;
import GUI.GUI;
import GUIClickListener.BackpackListener;
import GUIClickListener.ChallengeItemClickListener;
import StartListeners.PlayerTeleportWorldListener;
import me.wand555.Challenge.ChallengeData.ChallengeProfile;
import me.wand555.Challenge.ChallengeData.Settings;
import me.wand555.Challenge.Command.CE;
import me.wand555.Challenge.Config.UserConfig;
import me.wand555.Challenge.Config.Language.ConfigHandler;
import me.wand555.Challenge.Config.Language.Language;
import me.wand555.Challenge.Config.Language.LanguageMessages;
import me.wand555.Challenge.Util.SignMenuFactory;
import me.wand555.Challenge.WorldLinking.WorldLinkManager;
import me.wand555.Challenge.WorldLinking.NetherLinking.PortalListener;

public class Challenge extends JavaPlugin {

	public static final String PREFIX = ChatColor.GRAY + "[" + ChatColor.GREEN + "Challenge" + ChatColor.GRAY + "] ";
	
	private Challenge plugin;
	private CE myCE;
	private GUI gui;
	private SignMenuFactory signMenuFactory;
	public static Random random = new Random();
	
	public void onEnable() {
		plugin = this;
		UserConfig.placeLanguages();
		WorldLinkManager.worlds.add(Bukkit.createWorld(new WorldCreator("ChallengeOverworld").environment(Environment.NORMAL)));
		WorldLinkManager.worlds.add(Bukkit.createWorld(new WorldCreator("ChallengeNether").environment(Environment.NETHER)));
		WorldLinkManager.worlds.add(Bukkit.createWorld(new WorldCreator("ChallengeEnd").environment(Environment.THE_END)));
		
		this.gui = new GUI(this);
		this.signMenuFactory = new SignMenuFactory(this);
		
		ConfigHandler.loadFromConfig();
		
		UserConfig.setUpDefaultConfig();
		UserConfig.placeLanguages();
		String lang = this.getConfig().getString("Language");
		if(lang.equalsIgnoreCase("en")) LanguageMessages.loadLang(Language.ENGLISH);
		else if(lang.equalsIgnoreCase("de")) LanguageMessages.loadLang(Language.GERMAN);
		else LanguageMessages.loadLang(Language.ENGLISH);
		
		registerListener();
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				//Bukkit.getOnlinePlayers().forEach(p -> System.out.println(p.getWorld().getName()));
			}
		}.runTaskTimer(this, 20L, 40L);
		
		myCE = new CE(this, gui);
		this.getCommand("challenge").setExecutor(myCE);
		this.getCommand("timer").setExecutor(myCE);
		this.getCommand("hp").setExecutor(myCE);
		this.getCommand("settings").setExecutor(myCE);
		this.getCommand("pos").setExecutor(myCE);
		this.getCommand("bp").setExecutor(myCE);
		
		if(Settings.hasStarted) {
			//ChallengeProfile.resumeTimer();
		}
	}
	
	public void onDisable() {
		if(!Settings.isPaused && Settings.hasStarted) {
			ChallengeProfile.pauseTimerOnDisable();
		}
		
		Bukkit.getOnlinePlayers().stream()
			.filter(p -> WorldLinkManager.worlds.contains(p.getWorld()))
			.forEach(p -> p.closeInventory());
		
		ConfigHandler.storeToConfig();
	}
	
	private void registerListener() {
		new PlayerTeleportWorldListener(this);
		new EnderDragonDeathListener(this);
		
		new ChallengeItemClickListener(this, gui, signMenuFactory);
		new BackpackListener(this);
		new PlayerDeathListener(this);
		new NoDamageListener(this);
		new NoRegenListener(this);
		new SharedHPPlayerChangeLifeListener(this);
		new NoBlockPlaceListener(this);
		new NoBlockBreakingListener(this);
		new NoCraftListener(this);
		new NoSneakingListener(this);
		new RandomizerListener(this);
		
		new PortalListener(this);
		
		new PlayerJoinListener(this);
		new PlayerQuitListener(this);
	}
	
}
