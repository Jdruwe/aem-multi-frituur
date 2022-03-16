package be.jeroendruwe.aemmultifrituur.core.internal.servlets;

import be.jeroendruwe.aemmultifrituur.core.services.DevServerService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.jetbrains.annotations.NotNull;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(service = {Servlet.class},
        property = {
                "sling.servlet.methods=post",
                "sling.servlet.paths=/bin/devServer/toggle",
        }
)
public class DevServerToggleServlet extends SlingAllMethodsServlet {

    @Reference
    private DevServerService devServerService;

    @Override
    protected void doPost(@NotNull SlingHttpServletRequest request, @NotNull SlingHttpServletResponse response) throws ServletException, IOException {
        devServerService.toggleDevServerStatus();
    }
}
