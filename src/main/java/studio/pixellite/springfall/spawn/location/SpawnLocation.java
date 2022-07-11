package studio.pixellite.springfall.spawn.location;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class SpawnLocation {
  private final Location bukkitLocation;
  private final String worldName;
  private final double x;
  private final double y;
  private final double z;
  private final float yaw;
  private final float pitch;

  public SpawnLocation(String worldName, double x, double y, double z, float yaw, float pitch) {
    this.bukkitLocation = new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch);
    this.worldName = worldName;
    this.x = x;
    this.y = y;
    this.z = z;
    this.yaw = yaw;
    this.pitch = pitch;
  }

  public Location getBukkitLocation() {
    return bukkitLocation;
  }

  public String getWorldName() {
    return worldName;
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  public double getZ() {
    return z;
  }

  public float getYaw() {
    return yaw;
  }

  public float getPitch() {
    return pitch;
  }
}
