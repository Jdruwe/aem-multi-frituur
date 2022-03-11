package be.jeroendruwe.aemmultifrituur.core.internal.models.menu;

import be.jeroendruwe.aemmultifrituur.core.models.MenuComponent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;

import java.util.List;

@Model(
        adaptables = SlingHttpServletRequest.class,
        adapters = MenuComponent.class,
        resourceType = "aem-multi-frituur/components/menu"
)
public class MenuComponentImpl implements MenuComponent {

    @ChildResource
    private List<Group> groups;

    @Override
    public boolean isConfigured() {
        return groups != null && !groups.isEmpty();
    }

    @Override
    public String getConfig() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        MenuComponentConfig config = new MenuComponentConfig(groups);
        return objectMapper.writeValueAsString(config);
    }
}
