package studio.pixellite.springfall.spawn.module;

import me.lucko.helper.Events;
import me.lucko.helper.terminable.TerminableConsumer;
import me.lucko.helper.terminable.module.TerminableModule;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.jetbrains.annotations.NotNull;
import studio.pixellite.springfall.spawn.SpawnPlugin;
import studio.pixellite.springfall.spawn.location.SpawnLocation;

public class RespawnModule implements TerminableModule {
  private final SpawnPlugin plugin;

  public RespawnModule(SpawnPlugin plugin) {
    this.plugin = plugin;
  }

  @Override
  public void setup(@NotNull TerminableConsumer consumer) {
    Events.subscribe(PlayerRespawnEvent.class)
            .filter(e -> !e.isBedSpawn())
            .handler(e -> {
              // get the player's spawn location
              SpawnLocation spawn = plugin.getPlayerDataHandler()
                      .getSpawnLocationFor(e.getPlayer().getUniqueId());

              // set the respawn location for this event
              e.setRespawnLocation(spawn.getBukkitLocation());
            })
            .bindWith(consumer);
  }
}
