package be.jeroendruwe.aemmultifrituur.core.internal.models.heading;

import be.jeroendruwe.aemmultifrituur.core.models.HeadingComponent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;

@Model(
        adaptables = SlingHttpServletRequest.class,
        adapters = HeadingComponent.class,
        resourceType = "aem-multi-frituur/components/heading"
)
public class HeadingComponentImpl implements HeadingComponent {

    @Override
    public String getConfig() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        HeadingComponentConfig config = new HeadingComponentConfig("This is a heading value!");
        return objectMapper.writeValueAsString(config);
    }
}
