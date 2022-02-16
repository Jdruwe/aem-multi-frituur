package be.jeroendruwe.aemmultifrituur.core.internal.models;

import com.adobe.cq.wcm.core.components.models.Page;
import com.day.cq.wcm.api.designer.Style;
import lombok.experimental.Delegate;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.via.ResourceSuperType;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

@Model(
        adaptables = SlingHttpServletRequest.class,
        adapters = Page.class,
        resourceType = "aem-multi-frituur/components/page"
)
public class FrituurPage implements Page {

    /**
     * ES module client library style property name.
     */
    protected static final String PN_CLIENTLIBS_ES_MODULE = "esModuleClientlibs";
    
    @ScriptVariable(injectionStrategy = InjectionStrategy.OPTIONAL)
    @Nullable
    protected Style currentStyle;

    @Self
    @Via(type = ResourceSuperType.class)
    @Delegate
    private Page page;

    public String[] getESModuleClientlibCategories() {
        return Optional.ofNullable(currentStyle)
                       .map(style -> style.get(PN_CLIENTLIBS_ES_MODULE, String[].class))
                       .map(Arrays::stream)
                       .orElseGet(Stream::empty)
                       .distinct()
                       .toArray(String[]::new);
    }
}
