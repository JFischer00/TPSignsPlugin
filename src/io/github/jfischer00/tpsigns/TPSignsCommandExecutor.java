package io.github.jfischer00.tpsigns;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TPSignsCommandExecutor implements CommandExecutor {
	public TPSignsCommandExecutor(TPSigns TestPlugin) {
		plugin = TestPlugin;
	}
	
	TPSigns plugin;
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("setsign")) {
			if (args.length == 4) {
				boolean argsChecked = true;
				
				for (int i = 1; i < args.length; i ++) {
					if (!isInt(args[i])) {
						argsChecked = false;
						
						sender.sendMessage(ChatColor.RED + "Coordinates must be integers!");
						return false;
					}
				}
				
				if (argsChecked) {
					int x = Integer.parseInt(args[1]);
					int y = Integer.parseInt(args[2]);
					int z = Integer.parseInt(args[3]);
					
					sender.sendMessage(plugin.addSign(args[0], x, y, z));
				}
				
				return true;
			}
			else {
				sender.sendMessage(ChatColor.RED + "Incorrect Arguments!");
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
