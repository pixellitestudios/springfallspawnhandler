package studio.pixellite.springfall.spawn.player.database;

import me.lucko.helper.Schedulers;
import me.lucko.helper.Services;
import me.lucko.helper.sql.Sql;
import me.lucko.helper.sql.SqlProvider;
import me.lucko.helper.utils.UndashedUuids;
import studio.pixellite.springfall.spawn.SpawnPlugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * An implementation of {@link Database} using helper's sql utilities.
 */
public class SqlDatabase implements Database {
  // ignore column locations
  private static final String APPLY_PLAYER_DATA_SCHEMA = "CREATE TABLE IF NOT EXISTS springfall_spawns (" +
          "uuid VARCHAR(36) PRIMARY KEY," +
          "location VARCHAR(10) NOT NULL)";
  private static final String GET_PLAYER_DATA = "SELECT location FROM springfall_spawns WHERE uuid = ?";
  private static final String INSERT_PLAYER_DATA = "INSERT INTO springfall_spawns (uuid, location) VALUES(?, ?) ON DUPLICATE KEY UPDATE location = ?";

  private final Sql sql;
  private final SpawnPlugin plugin;

  public SqlDatabase(SpawnPlugin plugin) {
    this.sql = getSqlInstance();
    this.plugin = plugin;
  }

  /**
   * Gets the global {@link Sql} instance.
   *
   * @return the sql instance
   * @throws java.util.NoSuchElementException if a sql instance cannot be found
   */
  private Sql getSqlInstance() {
    SqlProvider provider = Services.get(SqlProvider.class).orElseThrow();
    return provider.getSql();
  }

  /**
   * Quietly prints to the console a SQL exception.
   *
   * @param exception the exception to print
   */
  private void handleErrorQuietly(SQLException exception) {
    plugin.getLogger().severe("SQL ERROR: " + exception.getMessage());
  }

  @Override
  public CompletableFuture<String> getLocationNameFor(UUID uniqueId) {
    return CompletableFuture.supplyAsync(() -> {
      try(Connection c = sql.getConnection()) {
        try (PreparedStatement ps = c.prepareStatement(GET_PLAYER_DATA)) {
          ps.setString(1, UndashedUuids.toString(uniqueId));

          try(ResultSet rs = ps.executeQuery()) {
            if(rs.next()) {
              return rs.getString("location");
            } else {
              return null;
            }
          }
        }
      } catch (SQLException e) {
        handleErrorQuietly(e);
        return null;
      }
    }, Schedulers.async());
  }

  @Override
  public CompletableFuture<Void> saveLocationName(UUID uniqueId, String locationName) {
    return CompletableFuture.runAsync(() -> {
      try(Connection c = sql.getConnection()) {
        try (PreparedStatement ps = c.prepareStatement(INSERT_PLAYER_DATA)) {
          ps.setString(1, UndashedUuids.toString(uniqueId));
          ps.setString(2, locationName);
          ps.setString(3, locationName);
          ps.execute();
        }
      } catch (SQLException e) {
        handleErrorQuietly(e);
      }
    }, Schedulers.async());
  }

  @Override
  public void init() {
    sql.executeAsync(APPLY_PLAYER_DATA_SCHEMA);
  }
}
