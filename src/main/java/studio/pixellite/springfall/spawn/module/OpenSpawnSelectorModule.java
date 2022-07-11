package studio.pixellite.springfall.spawn.module;

import me.lucko.helper.Commands;
import me.lucko.helper.terminable.TerminableConsumer;
import me.lucko.helper.terminable.module.TerminableModule;
import me.lucko.helper.utils.Players;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import studio.pixellite.springfall.spawn.SpawnPlugin;
import studio.pixellite.springfall.spawn.gui.SpawnSelectorGui;

public class OpenSpawnSelectorModule implements TerminableModule {
  private final SpawnPlugin plugin;

  public OpenSpawnSelectorModule(SpawnPlugin plugin) {
    this.plugin = plugin;
  }

  @Override
  public void setup(@NotNull TerminableConsumer consumer) {
    Commands.create()
            .assertPermission("pixellite.spawn.gui")
            .assertConsole()
            .assertUsage("<player>")
            .handler(c -> {
              Player target = Bukkit.getPlayer(c.arg(0).parseOrFail(String.class));

              if(target == null) {
                Players.msg(c.sender(), "Target is not online.");
                return;
              }

              // open the gui for the target
              new SpawnSelectorGui(target, plugin);
            })
            .registerAndBind(consumer, "spawnselectorgui");
  }
}
