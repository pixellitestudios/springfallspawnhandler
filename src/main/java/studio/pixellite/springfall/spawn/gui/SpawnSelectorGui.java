package studio.pixellite.springfall.spawn.gui;

import me.lucko.helper.Schedulers;
import me.lucko.helper.item.ItemStackBuilder;
import me.lucko.helper.menu.Gui;
import me.lucko.helper.menu.scheme.MenuScheme;
import me.lucko.helper.menu.scheme.StandardSchemeMappings;
import me.lucko.helper.utils.Players;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import studio.pixellite.springfall.spawn.SpawnPlugin;
import studio.pixellite.springfall.spawn.location.SpawnLocation;

public class SpawnSelectorGui extends Gui {
  private final SpawnPlugin plugin;

  public SpawnSelectorGui(Player player, SpawnPlugin plugin) {
    super(player, 3, "Select Your Spawn");
    this.plugin = plugin;
  }

  private void setupSpawn(String spawnName) {
    // get the spawn location
    SpawnLocation spawn = plugin.getLocationService().of(spawnName);

    // save the spawn location
    plugin.getPlayerDataHandler().saveSpawnLocation(getPlayer().getUniqueId(), spawnName);

    // teleport the player
    Players.msg(getPlayer(), " ");
    Players.msg(getPlayer(), "&7&oYour new adventure awaits...");
    Players.msg(getPlayer(), " ");

    getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 40, 1));

    Schedulers.sync().runLater(() -> {
      getPlayer().teleportAsync(spawn.getBukkitLocation());
      getPlayer().playSound(getPlayer().getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
    }, 40);

    // close this gui
    close();
  }

  @Override
  public void redraw() {
    if(isFirstDraw()) {
      drawMenuScheme();
    }

    // add items
    setItem(10, ItemStackBuilder.of(Material.GRASS_BLOCK)
            .name("&aEarth Biome")
            .lore("&7Spawn in an earth-based biome!",
                    " ",
                    "&cWarning: This change is not reversible.",
                    "&eClick to spawn!")
            .build(() -> setupSpawn("earth")));

    setItem(12, ItemStackBuilder.of(Material.MAGMA_BLOCK)
            .name("&cFire Biome")
            .lore("&7Spawn in an fire-based biome!",
                    " ",
                    "&cWarning: This change is not reversible.",
                    "&eClick to spawn!")
            .build(() -> setupSpawn("fire")));

    setItem(14, ItemStackBuilder.of(Material.PACKED_ICE)
            .name("&bIce Biome")
            .lore("&7Spawn in an ice-based biome!",
                    " ",
                    "&cWarning: This change is not reversible.",
                    "&eClick to spawn!")
            .build(() -> setupSpawn("ice")));

    setItem(16, ItemStackBuilder.of(Material.COBWEB)
            .name("&fHighlands Biome")
            .lore("&7Spawn in a highlands-based biome!",
                    " ",
                    "&cWarning: This change is not reversible.",
                    "&eClick to spawn!")
            .build(() -> setupSpawn("highlands")));
  }

  public void drawMenuScheme() {
    new MenuScheme(StandardSchemeMappings.STAINED_GLASS)
            .mask("111111111")
            .mask("101010101")
            .mask("111111111")
            .scheme(15, 15, 15, 15, 15, 15, 15, 15, 15)
            .scheme(15, 15, 15, 15, 15, 15, 15, 15, 15)
            .scheme(15, 15, 15, 15, 15, 15, 15, 15, 15)
            .apply(this);
  }
}
