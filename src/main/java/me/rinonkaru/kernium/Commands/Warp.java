package me.rinonkaru.kernium.Commands;

import me.rinonkaru.kernium.Kernium;
import me.rinonkaru.kernium.Types.KerniumLocation;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Warp implements BasicCommand {
	
	private final Kernium instance;
	
	public Warp(Kernium plugin) { this.instance = plugin; }
	
	private void preload_warp(KerniumLocation location) {
		World world = instance.getServer().getWorld(location.getWorld());
		if (world == null) { return; }
		Chunk warp_chunk = world.getChunkAt(new Location(world, location.getX(), location.getY(), location.getZ()));
		for (int x = -12; x <= 12; x++) {
			for (int z = -12; z <= 12; z++) {
				Chunk chunk = world.getChunkAt(warp_chunk.getX() + x, warp_chunk.getZ() + z);
				if (!chunk.isLoaded()) {
					world.addPluginChunkTicket(chunk.getX(), chunk.getZ(), instance);
				}
			}
		}
	}
	
	@Override
	public @NotNull Collection<String> suggest(@NotNull CommandSourceStack stack, String @NotNull [] args) {
		String[] suggestions;
		String executor = stack.getSender().getName().toLowerCase();
		if (!instance.warps.containsKey(executor)) {
			instance.warps.put(executor, Map.of());
			return List.of();
		}
		Map<String, KerniumLocation> executor_warps = instance.warps.get(executor);
		if (executor_warps.isEmpty()) {
			return List.of();
		}
		suggestions = new String[executor_warps.size()];
		int i = 0;
		for (String key : executor_warps.keySet()) {
			suggestions[i] = key;
			i++;
		}
		return List.of(suggestions);
	}
	
	@Override
	public void execute(@NotNull CommandSourceStack stack, String @NotNull [] args) {
		String executor = stack.getSender().getName().toLowerCase();
		if (!instance.warps.containsKey(executor)) {
			instance.getLogger().info("No warps found for " + executor + ", creating entry..." );
			instance.warps.put(executor, Map.of());
			stack.getSender().sendMessage("§cYou have no warps set. Create one with /setwarp <name>.");
			return;
		}
		if (args.length == 0) {
			stack.getSender().sendMessage("§cUsage: /warp <name>");
			return;
		}
		String location_name = args[0].toLowerCase();
		Map<String, KerniumLocation> personal_warps = instance.warps.get(executor);
		if (!personal_warps.containsKey(location_name)) {
			stack.getSender().sendMessage("§cWarp not found.");
			return;
		}
		KerniumLocation warp_location = personal_warps.get(location_name);
		preload_warp(warp_location);
		stack.getExecutor().teleportAsync(warp_location.toBukkitLocation());
		stack.getSender().sendMessage("§aTeleported to warp §6" + location_name + "§a.");
	}
	
}
