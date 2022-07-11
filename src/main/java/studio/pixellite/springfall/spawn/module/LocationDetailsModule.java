package studio.pixellite.springfall.spawn.module;

import me.lucko.helper.Commands;
import me.lucko.helper.terminable.TerminableConsumer;
import me.lucko.helper.terminable.module.TerminableModule;
import me.lucko.helper.utils.Players;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LocationDetailsModule implements TerminableModule {
  @Override
  public void setup(@NotNull TerminableConsumer consumer) {
    Commands.create()
            .assertPlayer()
            .assertPermission("pixellite.location")
            .handler(c -> {
              Player player = c.sender();
              Location location = c.sender().getLocation();

              Players.msg(player, "&aWorld: " + location.getWorld().getName());
              Players.msg(player, "&aX: " + location.getX());
              Players.msg(player, "&aY: " + location.getY());
              Players.msg(player, "&aZ: " + location.getZ());
              Players.msg(player, "&aYaw: " + location.getYaw());
              Players.msg(player, "&aPitch: " + location.getPitch());
            })
            .registerAndBind(consumer, "getlocation");
  }
}
