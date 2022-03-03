package be.jeroendruwe.aemmultifrituur.core.internal.models.openinghours;

public class OpeningHoursConfig {
    private final String message;

    public OpeningHoursConfig(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
