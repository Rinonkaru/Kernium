package me.rinonkaru.kernium.Commands;

import java.util.Map;
import java.util.List;
import java.util.Collection;

import me.rinonkaru.kernium.Kernium;
import me.rinonkaru.kernium.Types.KerniumLocation;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;


public class Pubwarp implements BasicCommand {
	
	private final Kernium instance;
	
	public Pubwarp(Kernium plugin) {
		this.instance = plugin;
	}
	
	@Override
	public Collection<String> suggest(CommandSourceStack stack, String[] arguments) {
		String[] suggestions;
		Map<String, KerniumLocation> public_warps = instance.warps.get("global");
		if (arguments.length == 0) {
			suggestions = new String[public_warps.size()];
			int i = 0;
			for (String key : public_warps.keySet()) {
				suggestions[i] = key;
				i++;
			}
			return List.of(suggestions);
		}
		if (arguments.length == 2) {
			if (arguments[0].equalsIgnoreCase("remove")) {
				if (public_warps == null) {
					return List.of();
				}
				suggestions = new String[public_warps.size()];
				int i = 0;
				for (String key : public_warps.keySet()) {
					suggestions[i] = key;
					i++;
				}
				return List.of(suggestions);
			}
			
		}
		return List.of();
	}
	
	@Override
	public void execute(CommandSourceStack stack, String[] arguments) {
		String executor = stack.getSender().getName().toLowerCase();
		Map<String, KerniumLocation> public_warps = instance.warps.get("global");
		if (public_warps == null) {
			instance.getLogger().info("No public warps found, creating entry..." );
			instance.warps.put("global", Map.of());
			stack.getSender().sendMessage("§cThere are no public warps set.");
			return;
		}
		if (arguments.length == 0) {
			stack.getSender().sendMessage("§cUsage: /pubwarp [Optional: add|remove] <name>");
			return;
		}
		if (arguments.length == 1) {
			String location_name = arguments[0].toLowerCase();
			if (!public_warps.containsKey(location_name)) {
				stack.getSender().sendMessage("§cPublic warp not found.");
			} else {
				KerniumLocation location = public_warps.get(location_name);
				if (location == null) {
					stack.getSender().sendMessage("§cError: Warp location is null.");
					return;
				}
				stack.getExecutor().teleportAsync(location.toBukkitLocation());
				stack.getSender().sendMessage("§aTeleported to public warp " + location_name + ".");
			}
			return;
		}
		if (arguments.length == 2) {
			if (arguments[0].equalsIgnoreCase("add")) {
				String location_name = arguments[1].toLowerCase();
				if (public_warps.containsKey(location_name)) {
					stack.getSender().sendMessage("§cA public warp with that name already exists.");
				} else {
					public_warps.put(location_name, new KerniumLocation(stack.getExecutor().getLocation()));
					instance.warps.put("global", public_warps);
					stack.getSender().sendMessage("§aPublic warp added.");
				}
			} else if (arguments[0].equalsIgnoreCase("remove")) {
				if (!executor.equalsIgnoreCase("rinonkaru")) {
					stack.getSender().sendMessage("§cYou do not have permission to remove public warps.");
					return;
				}
				String location_name = arguments[1].toLowerCase();
				if (!public_warps.containsKey(location_name)) {
					stack.getSender().sendMessage("§cPublic warp not found.");
				} else {
					public_warps.remove(location_name);
					instance.warps.put("global", public_warps);
					stack.getSender().sendMessage("§aPublic warp removed.");
				}
			} else {
				stack.getSender().sendMessage("§cUsage: /pubwarp [Optional: add|remove] <name>");
			}
		}
	}
}
