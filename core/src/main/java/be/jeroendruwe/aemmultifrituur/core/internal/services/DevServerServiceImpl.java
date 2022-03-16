package be.jeroendruwe.aemmultifrituur.core.internal.services;

import be.jeroendruwe.aemmultifrituur.core.services.DevServerService;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Hashtable;

@Component(service = DevServerService.class)
@Designate(ocd = DevServerServiceImpl.Config.class)
public class DevServerServiceImpl implements DevServerService {

    private static final Logger LOG = LoggerFactory.getLogger(DevServerServiceImpl.class);

    //TODO: check if I can get this value from a Java method?
    private static final String PID = "be.jeroendruwe.aemmultifrituur.core.internal.services.DevServerServiceImpl";

    @Reference
    private ConfigurationAdmin configAdmin;

    private Config config;

    @ObjectClassDefinition(name = "Dev Server Configuration")
    @interface Config {
        @AttributeDefinition(name = "Enable Dev Server") boolean devServerEnabled() default false;
    }

    @Activate
    @Modified
    private void activate(Config config) {
        this.config = config;
    }

    @Override
    public boolean isDevServerEnabled() {
        return config.devServerEnabled();
    }

    @Override
    public void toggleDevServerStatus() {
        try {
            Configuration configuration = configAdmin.getConfiguration(PID);
            configuration.update(new Hashtable<String, Object>() {{
                put(PN_DEV_SERVER_ENABLED, !isDevServerEnabled());
            }});
        } catch (IOException e) {
            LOG.error("Couldn't update dev server status", e);
        }
    }
}
