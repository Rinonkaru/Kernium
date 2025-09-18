package me.rinonkaru.kernium.Commands;

import java.util.Map;
import java.util.List;
import java.util.Collection;

import me.rinonkaru.kernium.Kernium;
import me.rinonkaru.kernium.Types.KerniumLocation;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;


public class Pubwarp implements BasicCommand {
	
	private final Kernium instance;
	
	public Pubwarp(Kernium plugin) {
		this.instance = plugin;
	}
	
	private void preload_warp(KerniumLocation location) {
		World world = instance.getServer().getWorld(location.getWorld());
		if (world == null) { return; }
		Chunk warp_chunk = world.getChunkAt(new Location(world, location.getX(), location.getY(), location.getZ()));
		for (int x = -12; x <= 12; x++) {
			for (int z = -12; z <= 12; z++) {
				Chunk chunk = world.getChunkAt(warp_chunk.getX() + x, warp_chunk.getZ() + z);
				if (!chunk.isLoaded()) {
					chunk.load();
				}
			}
		}
	}
	
	@Override
	public Collection<String> suggest(CommandSourceStack stack, String[] arguments) {
		Map<String, KerniumLocation> public_warps = instance.warps.get("global");
		if (arguments.length == 0) {
			String[] suggestions = new String[public_warps.size() + 2];
			suggestions[0] = "add";
			suggestions[1] = "remove";
			int i = 0;
			for (String key : public_warps.keySet()) {
				suggestions[i + 2] = key;
				i++;
			}
			return List.of(suggestions);
		}
		if (arguments.length == 2) {
			String[] suggestions;
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
		Map<String, KerniumLocation> pubwarps = instance.warps.get("global");
		if (pubwarps == null) {
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
			String name = arguments[0].toLowerCase();
			if (!pubwarps.containsKey(name)) {
				stack.getSender().sendMessage("§cPublic warp not found.");
				return;
			}
			KerniumLocation location = pubwarps.get(name);
			if (location == null) {
				stack.getSender().sendMessage("§cError: Warp location is null.");
				return;
			}
			preload_warp(location);
			stack.getExecutor().teleportAsync(location.toBukkitLocation());
			stack.getSender().sendMessage("§aTeleported to public warp " + name + ".");
		}
		if (arguments.length == 2) {
			String action = arguments[0].toLowerCase();
			String name = arguments[1].toLowerCase();
			if (action.equalsIgnoreCase("add")) {
				int users_warp_count = 0;
				for (KerniumLocation loc : pubwarps.values()) {
					if (loc == null) { continue; }
					if (loc.getCreator() == null) { continue; }
					if (loc.getCreator().equalsIgnoreCase(executor)) { users_warp_count++; }
				}
				if (users_warp_count >= 3) {
					stack.getSender().sendMessage("§cYou can only have 3 public warps.");
					return;
				}
				if (pubwarps.containsKey(name)) {
					stack.getSender().sendMessage("§cA public warp with that name already exists.");
					return;
				}
				KerniumLocation new_location = new KerniumLocation(stack.getExecutor().getLocation());
				new_location.setCreator(executor);
				pubwarps.put(name, new_location);
				instance.warps.put("global", pubwarps);
				stack.getSender().sendMessage("§aPublic warp " + name + " added.");
				return;
			}
			if (action.equalsIgnoreCase("remove")) {
				if (!executor.equalsIgnoreCase("rinonkaru")) {
					stack.getSender().sendMessage("§cYou do not have permission to remove public warps.");
					return;
				}
				if (!pubwarps.containsKey(name)) {
					stack.getSender().sendMessage("§cPublic warp not found.");
					return;
				}
				pubwarps.remove(name);
				instance.warps.put("global", pubwarps);
				stack.getSender().sendMessage("§aPublic warp removed.");
				return;
			}
		}
	}
}
