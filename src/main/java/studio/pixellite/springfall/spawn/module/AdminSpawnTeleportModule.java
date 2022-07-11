package studio.pixellite.springfall.spawn.module;

import me.lucko.helper.Commands;
import me.lucko.helper.terminable.TerminableConsumer;
import me.lucko.helper.terminable.module.TerminableModule;
import me.lucko.helper.utils.Players;
import org.jetbrains.annotations.NotNull;
import studio.pixellite.springfall.spawn.SpawnPlugin;
import studio.pixellite.springfall.spawn.location.SpawnLocation;

public class AdminSpawnTeleportModule implements TerminableModule {
  private final SpawnPlugin plugin;

  public AdminSpawnTeleportModule(SpawnPlugin plugin) {
    this.plugin = plugin;
  }

  @Override
  public void setup(@NotNull TerminableConsumer consumer) {
    Commands.create()
            .assertPermission("pixellite.spawn.teleport")
            .assertPlayer()
            .assertUsage("<locationName>")
            .handler(c -> {
              SpawnLocation location;

              try {
                location = plugin.getLocationService().of(c.arg(0).parseOrFail(String.class));
              } catch (IllegalArgumentException e) {
                Players.msg(c.sender(), "&cThat is an invalid spawn location!");
                return;
              }

              c.sender().teleport(location.getBukkitLocation());
            })
            .registerAndBind(consumer, "spawntp");
  }
}
