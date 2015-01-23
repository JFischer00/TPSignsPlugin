package io.github.jfischer00.tpsigns;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class TPSigns extends JavaPlugin {
	
	@Override
	public void onEnable() {
		this.getCommand("setsign").setExecutor(new TPSignsCommandExecutor(this));
		new TPSignsListener(this);
	}
	
	@Override
	public void onDisable() {
		
	}
	
	public String addSign(String name, int x, int y, int z) {
		//Grab sign file
		YamlConfiguration file = new YamlConfiguration();
		File signFile = new File(getDataFolder() + "\\signs.yml");
		
		//Check sign file existence
		if (!signFile.exists()) {
			try {
				signFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return (ChatColor.RED + "Couldn't save sign!");
			}
		}
		
		//Load sign file 
		try {
			file.load(signFile);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			return (ChatColor.RED + "Couldn't save sign!");
		} catch (IOException e1) {
			e1.printStackTrace();
			return (ChatColor.RED + "Couldn't save sign!");
		} catch (InvalidConfigurationException e1) {
			e1.printStackTrace();
			return (ChatColor.RED + "Couldn't save sign!");
		}
		
		if (!file.contains("signs")) {
			file.createSection("signs");
		}
		
		if (!file.contains("signs." + name)) {
			//Add sign
			file.createSection("signs." + name);
			
			//Add coords
			file.createSection("signs." + name + ".x");
			file.set("signs." + name + ".x", x);
			file.createSection("signs." + name + ".y");
			file.set("signs." + name + ".y", y);
			file.createSection("signs." + name + ".z");
			file.set("signs." + name + ".z", z);
			
			//Save signs file
			try {
				file.save(getDataFolder() + "\\signs.yml");
				return(ChatColor.GREEN + "Sign named " + name + " saved at coords: " + x + ", " + y + ", " + z + ".");
			} catch (IOException e) {
				e.printStackTrace();
				return(ChatColor.RED + "Couldn't save sign!");
			}
		}
		else {
			return(ChatColor.RED + "A sign with that name already exists!");
		}
	}
}
