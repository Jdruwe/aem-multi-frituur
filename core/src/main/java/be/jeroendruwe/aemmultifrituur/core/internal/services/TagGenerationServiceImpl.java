package be.jeroendruwe.aemmultifrituur.core.internal.services;

import be.jeroendruwe.aemmultifrituur.core.services.TagGenerationService;
import org.osgi.service.component.annotations.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component(service = TagGenerationService.class)
public class TagGenerationServiceImpl implements TagGenerationService {

    TagGenerationFunction generateInclineScripts = (content) -> "<script>" + content + "</script>";
    TagGenerationFunction generatePreloads = (file) -> "<link rel=\"modulepreload\" href=\"" + file + "\">";
    TagGenerationFunction generateScripts = (file) -> "<script type=\"module\" crossorigin src=\"" + file + "\"></script>";
    TagGenerationFunction generateStylesheets = (file) -> "<link rel=\"stylesheet\" href=\"" + file + "\">";

    @Override
    public Set<String> generateTags(Map<String, Object> props) {
        Set<String> tags = new LinkedHashSet<>();
        tags.addAll(getTags(props, PN_INLINE_SCRIPTS, generateInclineScripts));
        tags.addAll(getTags(props, PN_PRELOADS, generatePreloads));
        tags.addAll(getTags(props, PN_SCRIPTS, generateScripts));
        tags.addAll(getTags(props, PN_STYLESHEETS, generateStylesheets));
        return tags;
    }

    @FunctionalInterface
    public interface TagGenerationFunction {
        String apply(String file);
    }

    private Set<String> getTags(Map<String, Object> props, String type, TagGenerationFunction function) {
        String[] sources = (String[]) props.get(type);
        if (sources != null) {
            return Arrays.stream(sources)
                         .map(function::apply)
                         .collect(Collectors.toSet());
        } else {
            return Collections.emptySet();
        }
    }
}
