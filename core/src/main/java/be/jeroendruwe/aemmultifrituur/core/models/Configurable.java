package be.jeroendruwe.aemmultifrituur.core.models;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface Configurable {
    boolean isConfigured();

    String getConfig() throws JsonProcessingException;
}
