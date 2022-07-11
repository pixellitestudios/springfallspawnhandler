package studio.pixellite.springfall.spawn.location;

import studio.pixellite.springfall.spawn.SpawnPlugin;

/**
 * A service for getting the various different spawn locations.
 */
public class SpawnLocationService {
  private final SpawnLocation firstSpawnLocation;
  private final SpawnLocation earthLocation;
  private final SpawnLocation fireLocation;
  private final SpawnLocation iceLocation;
  private final SpawnLocation highlandsLocation;

  public SpawnLocationService(SpawnPlugin plugin) {
    this.firstSpawnLocation = plugin.getConfiguration().getSpawnLocation("first-spawn-location");
    this.earthLocation = plugin.getConfiguration().getSpawnLocation("earth");
    this.fireLocation = plugin.getConfiguration().getSpawnLocation("fire");
    this.iceLocation = plugin.getConfiguration().getSpawnLocation("ice");
    this.highlandsLocation = plugin.getConfiguration().getSpawnLocation("highlands");
  }

  public SpawnLocation getFirstSpawnLocation() {
    return firstSpawnLocation;
  }

  public SpawnLocation getEarthLocation() {
    return earthLocation;
  }

  public SpawnLocation getFireLocation() {
    return fireLocation;
  }

  public SpawnLocation getIceLocation() {
    return iceLocation;
  }

  public SpawnLocation getHighlandsLocation() {
    return highlandsLocation;
  }

  /**
   * Gets the spawn location based off the given name.
   *
   * @param name the name of the spawn location
   * @return the spawn location
   */
  public SpawnLocation of(String name) {
    return switch (name.toLowerCase()) {
      case "earth" -> earthLocation;
      case "fire" -> fireLocation;
      case "ice" -> iceLocation;
      case "highlands" -> highlandsLocation;
      default -> throw new IllegalArgumentException("That is not an existing spawn location!");
    };
  }
}
