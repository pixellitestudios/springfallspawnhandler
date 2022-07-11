package studio.pixellite.springfall.spawn.module;

import me.lucko.helper.Events;
import me.lucko.helper.terminable.TerminableConsumer;
import me.lucko.helper.terminable.module.TerminableModule;
import me.lucko.helper.utils.Players;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;
import studio.pixellite.springfall.spawn.SpawnPlugin;

public class FirstJoinModule implements TerminableModule {
  private final SpawnPlugin plugin;

  public FirstJoinModule(SpawnPlugin plugin) {
    this.plugin = plugin;
  }

  @Override
  public void setup(@NotNull TerminableConsumer consumer) {
    Events.subscribe(PlayerJoinEvent.class)
            .filter(e -> !e.getPlayer().hasPlayedBefore())
            .handler(e -> {
              Player player = e.getPlayer();

              // teleport player to the first join spawn location
              player.teleport(plugin.getLocationService()
                      .getFirstSpawnLocation()
                      .getBukkitLocation());

              // send the initial join message
              for(String string : plugin.getConfiguration().getFirstJoinMessage()) {
                Players.msg(player, string);
              }
            })
            .bindWith(consumer);
  }
}
