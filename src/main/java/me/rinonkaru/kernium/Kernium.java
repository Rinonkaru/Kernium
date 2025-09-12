package me.rinonkaru.kernium;

import me.rinonkaru.kernium.Commands.Pubwarp;
import me.rinonkaru.kernium.Types.KerniumLocation;
import me.rinonkaru.kernium.Commands.Warp;
import me.rinonkaru.kernium.Commands.Setwarp;
import me.rinonkaru.kernium.Commands.Delwarp;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.papermc.paper.command.brigadier.BasicCommand;

import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Map;
import java.util.HashMap;


public class Kernium extends JavaPlugin {
	
	private final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
	public Map<String, Map<String, KerniumLocation>> warps;
	
	private void load() {
		boolean folder_exists = getDataFolder().exists();
		File storage = new File(getDataFolder(), "storage.json");
		if (!folder_exists) {
			getDataFolder().mkdirs();
		}
		try {
			if (!storage.exists()) {
				getLogger().info("No storage file found, creating one...");
				storage.createNewFile();
				Location world_spawn = getServer().getWorld("world").getSpawnLocation();
				KerniumLocation global_spawn = new KerniumLocation(world_spawn);
				Map<String, KerniumLocation> default_warps = new HashMap<>();
				default_warps.put("spawn", global_spawn);
				warps = new HashMap<>();
				warps.put("global", default_warps);
				mapper.writeValue(storage, warps);
			} else {
				getLogger().info("Loading storage file...");
				warps = mapper.readValue(storage, new TypeReference<Map<String, Map<String, KerniumLocation>>>(){});
			}
		} catch (Exception e) {
			getLogger().severe("Error loading storage file!");
			e.printStackTrace();
			warps = new HashMap<>();
		}
	}
	
	private void save() {
		try {
			File file = new File(getDataFolder(), "storage.json");
			getLogger().info("Saving storage file...");
			mapper.writeValue(file, warps);
		} catch (Exception e) {
			getLogger().severe("Error saving storage file!");
			e.printStackTrace();
		}
	}
	
	@Override
	public void onEnable() {
		BasicCommand warp = new Warp(this);
		BasicCommand setwarp = new Setwarp(this);
		BasicCommand delwarp = new Delwarp(this);
		BasicCommand pubwarp = new Pubwarp(this);
		registerCommand("warp", warp);
		registerCommand("setwarp", setwarp);
		registerCommand("delwarp", delwarp);
		registerCommand("pubwarp", pubwarp);
		load();
	}
	
	@Override
	public void onDisable() {
		save();
	}
}
