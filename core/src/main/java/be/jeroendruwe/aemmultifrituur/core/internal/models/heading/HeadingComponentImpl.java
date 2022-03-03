package be.jeroendruwe.aemmultifrituur.core.internal.models.heading;

import be.jeroendruwe.aemmultifrituur.core.models.HeadingComponent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(
        adaptables = SlingHttpServletRequest.class,
        adapters = HeadingComponent.class,
        resourceType = "aem-multi-frituur/components/heading",
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class HeadingComponentImpl implements HeadingComponent {

    @ValueMapValue
    private String fileReference;

    @ValueMapValue
    private String alt;

    @Override
    public boolean isConfigured() {
        return StringUtils.isNoneBlank(fileReference, alt);
    }

    @Override
    public String getConfig() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(buildConfig());
    }

    private HeadingComponentConfig buildConfig() {
        return new HeadingComponentConfig.Builder()
                .withImage(fileReference)
                .withAlt(alt)
                .build();
    }
}
