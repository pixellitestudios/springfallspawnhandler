package studio.pixellite.springfall.spawn.player.database;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * An abstraction for interacting with the plugin database.
 */
public interface Database {
  /**
   * Gets the spawn location name for the given unique id.
   *
   * @param uniqueId the unique id
   * @return the spawn location, null if nothing was found
   */
  CompletableFuture<String> getLocationNameFor(UUID uniqueId);

  /**
   * Saves the spawn location name for the given unique id.
   *
   * <p>This will overwrite any previous entries in the database.</p>
   *
   * @param uniqueId the unique id
   * @param locationName the location name
   * @return a future
   */
  CompletableFuture<Void> saveLocationName(UUID uniqueId, String locationName);

  /**
   * Initializes this database.
   */
  void init();
}
