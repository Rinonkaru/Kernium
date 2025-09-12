package me.rinonkaru.kernium.Commands;

import java.util.Map;
import java.util.List;
import java.util.Collection;

import me.rinonkaru.kernium.Kernium;
import me.rinonkaru.kernium.Types.KerniumLocation;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;


public class Delwarp implements BasicCommand {
	
	private final Kernium instance;
	
	public Delwarp(Kernium plugin) {
		this.instance = plugin;
	}
	
	@Override
	public Collection<String> suggest(CommandSourceStack stack, String[] arguments) {
		String executor = stack.getSender().getName().toLowerCase();
		if (!instance.warps.containsKey(executor)) {
			instance.warps.put(executor, Map.of());
			return List.of();
		}
		Map<String, KerniumLocation> executor_warps = instance.warps.get(executor);
		String[] suggestions = new String[executor_warps.size()];
		int i = 0;
		for (String key : executor_warps.keySet()) {
			suggestions[i] = key;
			i++;
		}
		return List.of(suggestions);
	}
	
	@Override
	public void execute(CommandSourceStack stack, String[] arguments) {
		String executor = stack.getSender().getName().toLowerCase();
		if (!instance.warps.containsKey(executor)) {
			instance.getLogger().info("No warps found for " + executor + ", creating entry..." );
			instance.warps.put(executor, Map.of());
			stack.getSender().sendMessage("§cYou have no warps set. Create one with /setwarp <name>.");
			return;
		}
		if (arguments.length == 0) {
			stack.getSender().sendMessage("§cUsage: /delwarp <name>");
			return;
		}
		String location_name = arguments[0].toLowerCase();
		Map<String, KerniumLocation> personal_warps = instance.warps.get(executor);
		if (!personal_warps.containsKey(location_name)) {
			stack.getSender().sendMessage("§cWarp not found.");
			return;
		}
		personal_warps.remove(location_name);
		instance.warps.put(executor, personal_warps);
		stack.getSender().sendMessage("§aWarp deleted.");
	}
	
}
