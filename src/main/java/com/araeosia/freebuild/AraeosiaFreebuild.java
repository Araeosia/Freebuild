package com.araeosia.freebuild;

import java.util.logging.Logger;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class AraeosiaFreebuild extends JavaPlugin implements Listener {

	public static Logger logger;

	@Override
	public void onEnable() {
		logger = this.getLogger();
		logger.info("Enabled plugin!");
		this.getServer().getPluginManager().registerEvents(this, this);
	}

	@Override
	public void onDisable() {
		logger.info("Disabled plugin!");
	}

	@EventHandler
	public void onWorldChange(final PlayerChangedWorldEvent event) {
		String worldName = event.getPlayer().getWorld().getName();
		if (getConfig().getString("AraeosiaFreebuild.strings." + worldName) != null) {
			if (!getConfig().getBoolean("AraeosiaFreebuild.players." + event.getPlayer().getName() + "." + worldName)) {
				event.getPlayer().sendMessage(getConfig().getString("AraeosiaFreebuild.strings." + worldName));
			}
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (cmd.getName().equalsIgnoreCase("worldinfo")) {
			World w = getServer().getWorld(args[0]);
			if (w != null) {
				if(getConfig().getBoolean("AraeosiaFreebuild.players."+sender.getName()+"."+w.getName())){
					getConfig().set("AraeosiaFreebuild.players."+sender.getName()+"."+w.getName(), true);
				}else{
					getConfig().set("AraeosiaFreebuild.players."+sender.getName()+"."+w.getName(), false);
				}
			} else {
				sender.sendMessage("Invalid world! Did you typo?");
			}
			return true;
		}else if(cmd.getName().equalsIgnoreCase("rules")){
			for(String s : getConfig().getStringList("AraeosiaFreebuild.rules")){
				sender.sendMessage(s);
			}
		}
		return false;
	}
}
