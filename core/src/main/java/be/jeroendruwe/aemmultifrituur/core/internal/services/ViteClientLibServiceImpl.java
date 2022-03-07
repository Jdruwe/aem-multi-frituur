package be.jeroendruwe.aemmultifrituur.core.internal.services;

import be.jeroendruwe.aemmultifrituur.core.services.ModuleBasedClientLibService;
import be.jeroendruwe.aemmultifrituur.core.services.TagGenerationService;
import be.jeroendruwe.aemmultifrituur.core.services.ViteDevServerConfig;
import com.adobe.granite.ui.clientlibs.ClientLibrary;
import org.apache.commons.lang3.StringUtils;
import org.osgi.service.component.annotations.*;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

import java.util.*;
import java.util.stream.Collectors;

@Component(service = ModuleBasedClientLibService.class)
@Designate(ocd = ViteClientLibServiceImpl.Config.class)
public class ViteClientLibServiceImpl implements ModuleBasedClientLibService {

    private static final String IDENTIFIER = "vite";

    private final List<ViteDevServerConfig> devServerConfigurations = new ArrayList<>();
    private Config config;

    @Reference
    private TagGenerationService tagGenerationService;

    @Reference(
            service = ViteDevServerConfig.class,
            cardinality = ReferenceCardinality.MULTIPLE,
            policy = ReferencePolicy.DYNAMIC
    )
    protected synchronized void bindConfigurationFactory(final ViteDevServerConfig config) {
        devServerConfigurations.add(config);
    }

    protected synchronized void unbindConfigurationFactory(final ViteDevServerConfig config) {
        devServerConfigurations.remove(config);
    }

    @Override
    public boolean isApplicable(Map<String, Object> props) {
        String moduleIdentifier = (String) props.get(PN_MODULE_IDENTIFIER);
        return moduleIdentifier != null && moduleIdentifier.equals(IDENTIFIER);
    }

    @Override
    public Set<String> getIncludes(ClientLibrary library, Map<String, Object> props) {
        if (isDevServerEnabled()) {
            return getDevModules(library);
        } else {
            return tagGenerationService.generateTags(props);
        }
    }

    @ObjectClassDefinition(name = "Vite Dev Server Integration Configuration")
    @interface Config {
        @AttributeDefinition(name = "Enable Vite Integration") boolean viteDevServerEnabled() default false;
    }

    @Activate
    @Modified
    protected void activate(Config config) {
        this.config = config;
    }

    private boolean isDevServerEnabled() {
        return config.viteDevServerEnabled();
    }

    private Set<String> getDevModules(ClientLibrary library) {
        return Arrays.stream(library.getCategories())
                     .map(this::getDevServerConfig)
                     .filter(Optional::isPresent)
                     .map(Optional::get)
                     .map(this::buildDevTagMap)
                     .flatMap(map -> tagGenerationService.generateTags(map).stream())
                     .collect(Collectors.toSet());
    }

    private Optional<ViteDevServerConfig> getDevServerConfig(String category) {
        return devServerConfigurations.stream()
                                      .filter(c -> StringUtils.equals(c.getCategory(), category))
                                      .filter(this::isValidDevServerConfig)
                                      .findFirst();
    }

    private boolean isValidDevServerConfig(ViteDevServerConfig c) {
        return StringUtils.isNoneBlank(c.getEntry(), c.getProtocol(), c.getPort(), c.getHostname());
    }

    private Map<String, Object> buildDevTagMap(ViteDevServerConfig config) {
        String viteDevServerUrl = getDevServerUrl(config);
        return Collections.singletonMap(TagGenerationService.PN_SCRIPTS, new String[]{
                viteDevServerUrl + "@vite/client",
                viteDevServerUrl + config.getEntry()
        });
    }

    private String getDevServerUrl(ViteDevServerConfig config) {
        return String.format("%s://%s:%s/", config.getProtocol(), config.getHostname(), config.getPort());
    }
}
