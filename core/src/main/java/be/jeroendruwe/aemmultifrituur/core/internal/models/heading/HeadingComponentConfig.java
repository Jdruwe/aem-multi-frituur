package be.jeroendruwe.aemmultifrituur.core.internal.models.heading;

public class HeadingComponentConfig {
    private final String image;
    private final String alt;

    public String getImage() {
        return image;
    }

    public String getAlt() {
        return alt;
    }

    private HeadingComponentConfig(Builder builder) {
        image = builder.image;
        alt = builder.alt;
    }

    public static final class Builder {
        private String image;
        private String alt;

        public Builder() {
        }

        public Builder withImage(String val) {
            image = val;
            return this;
        }

        public Builder withAlt(String val) {
            alt = val;
            return this;
        }

        public HeadingComponentConfig build() {
            return new HeadingComponentConfig(this);
        }
    }
}
