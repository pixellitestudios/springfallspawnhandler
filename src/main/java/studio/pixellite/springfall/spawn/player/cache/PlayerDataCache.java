package studio.pixellite.springfall.spawn.player.cache;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A cache for storing player spawn data. Backed by a {@link ConcurrentHashMap}. Thus, this
 * object's thread-safety is that of the map implementation.
 */
public class PlayerDataCache {
  /** The backing concurrent map instance. */
  private final Map<UUID, String> map = new ConcurrentHashMap<>();

  /**
   * Registers the location name with the unique id.
   *
   * @param uniqueId the unique id
   * @param locationName the location name
   */
  public void registerLocation(UUID uniqueId, String locationName) {
    map.putIfAbsent(uniqueId, locationName);
  }

  /**
   * Registers a location name with the unique id, overwriting any previous entries for that id.
   *
   * @param uniqueId the unique id
   * @param locationName the location name
   */
  public void registerLocationOverwrite(UUID uniqueId, String locationName) {
    map.put(uniqueId, locationName);
  }

  /**
   * Gets the location name for the given unique id.
   *
   * @param uniqueId the unique id
   * @return the location name, null of nothing was found
   */
  public String getLocationNameFor(UUID uniqueId) {
    return map.get(uniqueId);
  }

  /**
   * Unregisters an entry from this cache. If no entry exists, then the effective behavior of this
   * method is that of undefined.
   *
   * @param uniqueId the unique id
   * @return the value that was stored, null of there was none
   */
  public String unregister(UUID uniqueId) {
    return map.remove(uniqueId);
  }
}
