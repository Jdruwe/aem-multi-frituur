package be.jeroendruwe.aemmultifrituur.core.internal.models.menu;

import be.jeroendruwe.aemmultifrituur.core.models.MenuComponent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;

@Model(
        adaptables = SlingHttpServletRequest.class,
        adapters = MenuComponent.class,
        resourceType = "aem-multi-frituur/components/menu"
)
public class MenuComponentImpl implements MenuComponent {

    @Override
    public boolean isConfigured() {
        return true;
    }

    @Override
    public String getConfig() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        MenuComponentConfig config = new MenuComponentConfig("This is a menu value!");
        return objectMapper.writeValueAsString(config);
    }
}
