package studio.pixellite.springfall.spawn.config.resolver;

import me.lucko.helper.config.ConfigurationNode;
import me.lucko.helper.config.yaml.YAMLConfigurationLoader;
import org.bukkit.Bukkit;
import studio.pixellite.springfall.spawn.SpawnPlugin;
import studio.pixellite.springfall.spawn.config.exception.ConfigurationLoadException;

import java.io.IOException;
import java.nio.file.Path;

public class ConfigurateResolver {
  /**
   * Generates a new configuration node using the given path.
   *
   * @param plugin the primary plugin instance
   * @param path the name of the configuration file
   * @return the new confiuration node
   * @throws ConfigurationLoadException if the node could not be loaded
   */
  public static ConfigurationNode resolveNode(SpawnPlugin plugin, String path) {
    YAMLConfigurationLoader loader = YAMLConfigurationLoader.builder()
            .setPath(Path.of(plugin.getDataFolder() + "/" + path))
            .build();

    ConfigurationNode root;

    try {
      root = loader.load();
    } catch (IOException e) {
      Bukkit.getPluginManager().disablePlugin(plugin);
      throw new ConfigurationLoadException("Unable to load node to file: " + path);
    }

    return root;
  }

  // ensure this class cannot be instantiated
  private ConfigurateResolver() {}
}
