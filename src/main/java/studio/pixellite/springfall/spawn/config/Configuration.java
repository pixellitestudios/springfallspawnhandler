package studio.pixellite.springfall.spawn.config;

import studio.pixellite.springfall.spawn.SpawnPlugin;
import studio.pixellite.springfall.spawn.location.SpawnLocation;

import java.util.List;

public class Configuration extends AbstractConfiguration {
  private static final String WORLD_SPAWN_PATH = "world-spawn-locations";

  private final List<String> firstJoinMessage;
  private final String defaultSpawnLocation;

  public Configuration(SpawnPlugin plugin) {
    super(plugin, "config.yml");
    this.firstJoinMessage = getList(String.class, "first-join-message");
    this.defaultSpawnLocation = getString("default-spawn-location");
  }

  public List<String> getFirstJoinMessage() {
    return firstJoinMessage;
  }

  public String getDefaultSpawnLocation() {
    return defaultSpawnLocation;
  }

  public SpawnLocation getSpawnLocation(String name) {
    String world = getString(WORLD_SPAWN_PATH, name, "world");
    double x = getDouble(WORLD_SPAWN_PATH, name, "x");
    double y = getDouble(WORLD_SPAWN_PATH, name, "y");
    double z = getDouble(WORLD_SPAWN_PATH, name, "z");
    float yaw = getFloat(WORLD_SPAWN_PATH, name, "yaw");
    float pitch = getFloat(WORLD_SPAWN_PATH, name, "pitch");

    return new SpawnLocation(world, x, y, z, yaw, pitch);
  }
}
