package io.github.jfischer00.tpsigns;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class TPSignsListener implements Listener {

	TPSigns plugin;
	
	public TPSignsListener(TPSigns plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onSignPlaced(SignChangeEvent e) {
		Player placer = e.getPlayer();
		
		if (e.getLine(0).equalsIgnoreCase("[tp]")) {
			if (!e.getLine(1).equalsIgnoreCase("")) {
				if (!e.getLine(3).equalsIgnoreCase("")) {
					String name = e.getLine(3);
					String target = e.getLine(1);
						
					int x = e.getBlock().getLocation().getBlockX();
					int y = e.getBlock().getLocation().getBlockY();
					int z = e.getBlock().getLocation().getBlockZ();
						
					placer.sendMessage(plugin.addSign(name, target, x, y, z));
				}
				else {
					placer.sendMessage(ChatColor.YELLOW + "Is this a TPSign? If so, it must have a sign name on the fourth line.");
				}
			}
			else {
				placer.sendMessage(ChatColor.YELLOW + "Is this a TPSign? If so, it must have a player name on the second line.");
			}
		}
	}
	
	@EventHandler
	public void onSignClicked(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		
		if (e.getClickedBlock().getType() == Material.SIGN || e.getClickedBlock().getType() == Material.SIGN_POST || e.getClickedBlock().getType() == Material.WALL_SIGN) {
			if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				Sign sign = (Sign) e.getClickedBlock().getState();
								
				if (plugin.isTPSign(sign, player)) {
					if (player.hasPermission("tpsigns.use")) {
						boolean playerFound = false;
						
						for (Player target : Bukkit.getOnlinePlayers()) {
							if (sign.getLine(1) == target.getName()) {
								Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tp " + player.getName() + " " + target.getName());
								player.sendMessage(ChatColor.GREEN + "You are now being teleported to " + target.getName());
								
								playerFound = true;
								return;
							}
						}
							
						if (!playerFound) {
							player.sendMessage(ChatColor.RED + sign.getLine(1) + " is not online!");
							return;
						}
					}
					else {
						player.sendMessage(ChatColor.RED + "You do not have permission to use tp signs.");
						return;
					}
				}
			}
		}
	}
}
