package com.github.marceloemanoel.vraptor.jscontroller.generators.velocity;

public enum DefaultParameters {

    LOGGER {
        public String getValue() {
            return "org.apache.velocity.runtime.log.NullLogSystem";
        }
    },
    RESOURCE_LOADER {
        @Override
        public String getValue() {
            return "classpath";
        }
    };

    public abstract String getValue();
}
