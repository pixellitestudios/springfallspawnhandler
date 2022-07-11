package studio.pixellite.springfall.spawn.config.exception;

/**
 * An exception for when a configuration file is unable to load.
 */
public class ConfigurationLoadException extends RuntimeException {
  public ConfigurationLoadException(String message) {
    super(message);
  }
}
