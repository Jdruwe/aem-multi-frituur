package be.jeroendruwe.aemmultifrituur.core.internal.services;

import be.jeroendruwe.aemmultifrituur.core.services.DevServerService;
import be.jeroendruwe.aemmultifrituur.core.services.ModuleBasedClientLibService;
import be.jeroendruwe.aemmultifrituur.core.services.TagGenerationService;
import be.jeroendruwe.aemmultifrituur.core.services.ViteDevServerConfig;
import com.adobe.granite.ui.clientlibs.ClientLibrary;
import org.apache.commons.lang3.StringUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

import java.util.*;
import java.util.stream.Collectors;

@Component(service = ModuleBasedClientLibService.class)
public class ViteClientLibServiceImpl implements ModuleBasedClientLibService {

    private static final String IDENTIFIER = "vite";
    private static final String DEV_CLIENT_SUFFIX = "@vite/client";
    private static final String DEV_SERVER_WARNING = "Vite dev server is not running.";

    private final List<ViteDevServerConfig> devServerConfigurations = new ArrayList<>();

    @Reference
    private TagGenerationService tagGenerationService;

    @Reference
    private DevServerService devServerService;

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
        if (devServerService.isDevServerEnabled()) {
            return getDevModules(library);
        } else {
            return tagGenerationService.generateTags(props);
        }
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
        Map<String, Object> map = new HashMap<>();
        map.put(TagGenerationService.PN_SCRIPTS, new String[]{
                getDevClientUrl(config),
                getDevEntryUrl(config)
        });
        map.put(TagGenerationService.PN_INLINE_SCRIPTS, new String[]{
                buildDevServerValidationScript(config)
        });
        return map;
    }

    private String buildDevServerValidationScript(ViteDevServerConfig config) {
        return String.format("fetch('%s').catch((error) => {alert('%s')})", getDevEntryUrl(config), DEV_SERVER_WARNING);
    }

    private String getDevClientUrl(ViteDevServerConfig config) {
        return String.format("%s/%s", getDevOrigin(config), DEV_CLIENT_SUFFIX);
    }

    private String getDevEntryUrl(ViteDevServerConfig config) {
        return String.format("%s/%s", getDevOrigin(config), config.getEntry());
    }

    private String getDevOrigin(ViteDevServerConfig config) {
        return String.format("%s://%s:%s", config.getProtocol(), config.getHostname(), config.getPort());
    }
}
