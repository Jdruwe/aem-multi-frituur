package be.jeroendruwe.aemmultifrituur.core.services;

public interface ViteDevServerConfig {
    String getProtocol();

    String getHostname();

    String getPort();

    String getEntry();

    String getCategory();
}
