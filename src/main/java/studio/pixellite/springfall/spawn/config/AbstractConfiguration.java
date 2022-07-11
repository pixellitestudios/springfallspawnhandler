package studio.pixellite.springfall.spawn.config;

import com.google.common.reflect.TypeToken;
import me.lucko.helper.config.ConfigurationNode;
import me.lucko.helper.config.objectmapping.ObjectMappingException;
import studio.pixellite.springfall.spawn.SpawnPlugin;
import studio.pixellite.springfall.spawn.config.resolver.ConfigurateResolver;

import java.util.List;

/**
 * An abstract implementation of a configuration file.
 */
public abstract class AbstractConfiguration {
  private final ConfigurationNode node;
  private final SpawnPlugin plugin;

  protected AbstractConfiguration(SpawnPlugin plugin, String path) {
    this.node = ConfigurateResolver.resolveNode(plugin, path);
    this.plugin = plugin;
  }

  protected SpawnPlugin getPlugin() {
    return plugin;
  }

  protected String getString(Object... path) {
    return node.getNode(path).getString("default");
  }

  protected int getInt(Object... path) {
    return node.getNode(path).getInt();
  }

  protected double getDouble(Object... path) {
    return node.getNode(path).getDouble();
  }

  protected float getFloat(Object... path) {
    return node.getNode(path).getFloat();
  }

  protected boolean getBoolean(Object... path) {
    return node.getNode(path).getBoolean();
  }

  @SuppressWarnings("UnstableApiUsage")
  protected <T> List<T> getList(Class<T> clazz, Object... path) {
    try {
      return node.getNode(path).getList(TypeToken.of(clazz));
    } catch (ObjectMappingException e) {
      throw new RuntimeException(e);
    }
  }
}
