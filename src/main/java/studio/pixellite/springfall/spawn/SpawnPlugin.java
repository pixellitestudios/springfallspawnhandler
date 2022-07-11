package studio.pixellite.springfall.spawn;

import me.lucko.helper.plugin.ExtendedJavaPlugin;
import studio.pixellite.springfall.spawn.config.Configuration;
import studio.pixellite.springfall.spawn.location.SpawnLocationService;
import studio.pixellite.springfall.spawn.module.*;
import studio.pixellite.springfall.spawn.player.PlayerDataHandler;

public class SpawnPlugin extends ExtendedJavaPlugin {
  private Configuration configuration;
  private SpawnLocationService locationService;
  private PlayerDataHandler playerDataHandler;

  @Override
  protected void enable() {
    // init configuration
    saveDefaultConfig();
    configuration = new Configuration(this);

    // init location service
    locationService = new SpawnLocationService(this);

    // init player data handler
    playerDataHandler = new PlayerDataHandler(this);
    playerDataHandler.init();

    // bind modules
    bindModule(new AdminSpawnTeleportModule(this));
    bindModule(new FirstJoinModule(this));
    bindModule(new LocationDetailsModule());
    bindModule(new RespawnModule(this));
    bindModule(new OpenSpawnSelectorModule(this));
  }

  public Configuration getConfiguration() {
    return configuration;
  }

  public PlayerDataHandler getPlayerDataHandler() {
    return playerDataHandler;
  }

  public SpawnLocationService getLocationService() {
    return locationService;
  }
}
