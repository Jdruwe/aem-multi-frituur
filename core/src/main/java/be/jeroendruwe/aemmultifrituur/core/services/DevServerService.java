package be.jeroendruwe.aemmultifrituur.core.services;

public interface DevServerService {
    String PN_DEV_SERVER_ENABLED = "devServerEnabled";

    boolean isDevServerEnabled();

    void toggleDevServerStatus();
}
