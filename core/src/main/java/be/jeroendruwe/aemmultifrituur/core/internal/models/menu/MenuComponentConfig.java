package be.jeroendruwe.aemmultifrituur.core.internal.models.menu;

import lombok.Getter;

import java.util.List;

@Getter
public class MenuComponentConfig {
    private final List<Group> groups;

    public MenuComponentConfig(List<Group> groups) {
        this.groups = groups;
    }
}
