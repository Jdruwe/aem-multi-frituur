package be.jeroendruwe.aemmultifrituur.core.internal.servlets;

import be.jeroendruwe.aemmultifrituur.core.services.DevServerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
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
                "sling.servlet.methods=get",
                "sling.servlet.paths=/bin/devServer/status"
        }
)
public class DevServerStatusServlet extends SlingAllMethodsServlet {

    @Reference
    private DevServerService devServerService;

    @Override
    protected void doGet(@NotNull SlingHttpServletRequest request, @NotNull SlingHttpServletResponse response) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        DevServerStatus status = new DevServerStatus(devServerService.isDevServerEnabled());
        response.getWriter().write(objectMapper.writeValueAsString(status));
    }

    @Getter
    @RequiredArgsConstructor
    private static class DevServerStatus {
        private final boolean enabled;
    }
}
