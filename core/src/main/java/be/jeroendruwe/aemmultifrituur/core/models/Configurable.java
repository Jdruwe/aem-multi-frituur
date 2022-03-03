package be.jeroendruwe.aemmultifrituur.core.models;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface Configurable {
    String getConfig() throws JsonProcessingException;
}
