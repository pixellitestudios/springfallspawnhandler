package studio.pixellite.springfall.spawn.player;

import me.lucko.helper.Events;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import studio.pixellite.springfall.spawn.SpawnPlugin;
import studio.pixellite.springfall.spawn.location.SpawnLocation;
import studio.pixellite.springfall.spawn.player.cache.PlayerDataCache;
import studio.pixellite.springfall.spawn.player.database.Database;
import studio.pixellite.springfall.spawn.player.database.SqlDatabase;

import java.util.UUID;

/**
 * A handler class for the caching and saving of player spawn data.
 */
public class PlayerDataHandler {
  /** The primary plugin instance. */
  private final SpawnPlugin plugin;

  /** The player data cache.  */
  private final PlayerDataCache cache;

  /** The backing database instance for storing player data. */
  private final Database database;

  public PlayerDataHandler(SpawnPlugin plugin) {
    this.plugin = plugin;
    this.cache = new PlayerDataCache();
    this.database = new SqlDatabase(plugin);
    new ConnectionListener().initListeners();
  }

  /**
   * Saves the spawn location to the database, and then to the cache.
   *
   * @param uniqueId the unique id to save
   * @param locationName the name of the location to save
   */
  public void saveSpawnLocation(UUID uniqueId, String locationName) {
    database.saveLocationName(uniqueId, locationName)
            .thenRunAsync(() -> cache.registerLocationOverwrite(uniqueId, locationName));
  }

  /**
   * Gets the {@link SpawnLocation} for the given unique id.
   *
   * @param uniqueId the unique id
   * @return the spawn location, resorting to the default location if nothing was found
   */
  public SpawnLocation getSpawnLocationFor(UUID uniqueId) {
    String locationName = cache.getLocationNameFor(uniqueId);

    if(locationName == null) {
      // return the default spawn location
      return plugin.getLocationService().of(plugin.getConfiguration().getDefaultSpawnLocation());
    }

    return plugin.getLocationService().of(locationName);
  }

  /**
   * Caches the spawn location for the given unique id.
   *
   * @param uniqueId the unique id to cache for
   */
  public void cacheSpawnLocation(UUID uniqueId) {
    database.getLocationNameFor(uniqueId).thenAcceptAsync(str -> {
      if(str == null) {
        return; // do nothing
      }

      // add to cache
      cache.registerLocation(uniqueId, str);
    });
  }

  /**
   * Initializes this handler.
   */
  public void init() {
    database.init();
  }

  /** A subclass for handling connections and disconnections. */
  private final class ConnectionListener {
    public void initListeners() {
      Events.subscribe(AsyncPlayerPreLoginEvent.class, EventPriority.MONITOR)
              .handler(this::handleAsyncLogin)
              .bindWith(plugin);

      Events.subscribe(PlayerLoginEvent.class, EventPriority.MONITOR)
              .handler(this::handlePlayerLogin)
              .bindWith(plugin);

      Events.subscribe(PlayerQuitEvent.class)
              .handler(this::handlePlayerQuit)
              .bindWith(plugin);
    }

    private void handleAsyncLogin(AsyncPlayerPreLoginEvent e) {
      // since we're at MONITOR, we just need to check if the result was allowed
      if(e.getLoginResult() != AsyncPlayerPreLoginEvent.Result.ALLOWED) {
        return;
      }

      // load player data
      cacheSpawnLocation(e.getUniqueId());
    }

    private void handlePlayerLogin(PlayerLoginEvent e) {
      // check to make sure a plugin didn't deny login between async login and sync login
      if(e.getResult() != PlayerLoginEvent.Result.ALLOWED) {
        // for some reason a plugin did cancel, unload any data for the player
        // and return
        cache.unregister(e.getPlayer().getUniqueId());
      }
    }

    private void handlePlayerQuit(PlayerQuitEvent e) {
      // remove player data & do a final save
      UUID playerId = e.getPlayer().getUniqueId();
      String location = cache.unregister(playerId);

      if(location == null) {
        return; // do nothing
      }

      saveSpawnLocation(playerId, location);
    }
  }
}
