package io.github.jfischer00.tpsigns;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

public class TPSignsCommandExecutor implements CommandExecutor {
	public TPSignsCommandExecutor(TPSigns TestPlugin) {
		plugin = TestPlugin;
	}
	
	TPSigns plugin;
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("setsign")) {
			if (args.length == 5) {
				boolean argsChecked = true;
				
				for (int i = 2; i < args.length; i ++) {
					if (!isInt(args[i])) {
						argsChecked = false;
						
						sender.sendMessage(ChatColor.RED + "Coordinates must be integers!");
						return false;
					}
				}
				
				if (argsChecked) {
					int x = Integer.parseInt(args[2]);
					int y = Integer.parseInt(args[3]);
					int z = Integer.parseInt(args[4]);
					
					sender.sendMessage(plugin.addSign(args[0], args[1], x, y, z));
					
					Location loc = new Location(sender.getServer().getWorld("world"), x, y, z);
					
					Block block = loc.getBlock();
					
					block.setType(Material.SIGN);
					
					System.out.println(loc.getBlock().getType().toString());
					
					Sign sign = (Sign) block;
					
					sign.setLine(0, "[tp]");
					sign.setLine(1, args[1]);
					sign.setLine(3, args[0]);
				}
				
				return true;
			}
			else {
				sender.sendMessage(ChatColor.RED + "Incorrect Arguments!");
				return false;
			}
		}
		else if (cmd.getName().equalsIgnoreCase("isTPSign")) {
			if (args.length == 3) {
				Block block = sender.getServer().getWorld("world").getBlockAt(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
			
				if (block.getType() == Material.SIGN) {
					Sign sign = (Sign) block;

					//Grab sign file
					YamlConfiguration file = new YamlConfiguration();
					File signFile = new File(plugin.getDataFolder() + "\\signs.yml");
					
					//Check sign file existence
					if (!signFile.exists()) {
						return false;
					}
							
					//Load sign file 
					try {
						file.load(signFile);
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
						sender.sendMessage(ChatColor.RED + "An error occured. Please try again.");
						
						return false;
					} catch (IOException e1) {
						e1.printStackTrace();
						sender.sendMessage(ChatColor.RED + "An error occured. Please try again.");
						
						return false;
					} catch (InvalidConfigurationException e1) {
						e1.printStackTrace();
						sender.sendMessage(ChatColor.RED + "An error occured. Please try again.");
						
						return false;
					}
					
					if (sign.getLine(0).equalsIgnoreCase("[tp]") && file.contains("signs." + sign.getLine(3))) {
						sender.sendMessage(ChatColor.GREEN + "That sign is a TPSign!");
						
						return true;
					}
					else {
						sender.sendMessage(ChatColor.RED + "Sign is not a TPSign.");
						return false;
					}
				}
				else {
					sender.sendMessage(ChatColor.RED + "Block is not a sign!");
					return false;
				}
			}
			else {
				sender.sendMessage(ChatColor.RED + "Incorrect arguments!");
				return false;
			}
		}
		return false;
	}
	
	static private boolean isInt(String str){
        
        boolean result = false;
        try{
             @SuppressWarnings("unused")
			int num = Integer.parseInt(str);
             result = true;
        } catch (NumberFormatException e) {
        }
        return result;
    }
}
