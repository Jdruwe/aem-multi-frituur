package be.jeroendruwe.aemmultifrituur.core.internal.models.openinghours;

import be.jeroendruwe.aemmultifrituur.core.models.OpeningHoursComponent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;

@Model(
        adaptables = SlingHttpServletRequest.class,
        adapters = OpeningHoursComponent.class,
        resourceType = "aem-multi-frituur/components/opening-hours"
)
public class OpeningHoursComponentImpl implements OpeningHoursComponent {

    @Override
    public String getConfig() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        OpeningHoursConfig config = new OpeningHoursConfig("This is an opening hours value!");
        return objectMapper.writeValueAsString(config);
    }
}
