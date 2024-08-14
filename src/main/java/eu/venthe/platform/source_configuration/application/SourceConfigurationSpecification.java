package eu.venthe.platform.source_configuration.application;

import eu.venthe.platform.shared_kernel.configuration_properties.SuppliedProperties;
import eu.venthe.platform.source_configuration.domain.model.SourceType;
import lombok.Builder;

@Builder
public record SourceConfigurationSpecification(SourceType sourceType, SuppliedProperties properties) {
    public static class SourceConfigurationSpecificationBuilder {
        private SuppliedProperties properties = SuppliedProperties.none();

        public SourceConfigurationSpecificationBuilder sourceType(String sourceType) {
            this.sourceType = new SourceType(sourceType);
            return this;
        }
    }
}
