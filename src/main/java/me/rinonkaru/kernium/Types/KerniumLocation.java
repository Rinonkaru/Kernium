package me.rinonkaru.kernium.Types;

import me.rinonkaru.kernium.Kernium;

import org.bukkit.Location;
import org.bukkit.World;

public class KerniumLocation {
	
	private String world;
	private double x;
	private double y;
	private double z;
	private float yaw;
	private float pitch;
	
	public KerniumLocation() {}
	
	public KerniumLocation(Location location) {
		this.world = location.getWorld().getName();
		this.x = location.getX();
		this.y = location.getY();
		this.z = location.getZ();
		this.yaw = location.getYaw();
		this.pitch = location.getPitch();
	}
	
	public Location toBukkitLocation() {
		World world = org.bukkit.Bukkit.getWorld(this.world);
		if (world == null) {
			Kernium.getPlugin(Kernium.class).getLogger().severe("World: " + this.world + " not found!");
			return null;
		}
		return new Location(world, this.x, this.y, this.z, this.yaw, this.pitch);
	}
	
	public String getWorld() {
		return world;
	}
	
	public void setWorld(String world) {
		this.world = world;
	}
	
	public double getX() {
		return x;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public double getY() {
		return y;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public double getZ() {
		return z;
	}
	
	public void setZ(double z) {
		this.z = z;
	}
	
	public float getYaw() {
		return yaw;
	}
	
	public void setYaw(float yaw) {
		this.yaw = yaw;
	}
	
	public float getPitch() {
		return pitch;
	}
	
	public void setPitch(float pitch) {
		this.pitch = pitch;
	}
}
