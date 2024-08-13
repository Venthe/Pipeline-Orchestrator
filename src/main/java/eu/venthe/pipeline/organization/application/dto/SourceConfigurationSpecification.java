package eu.venthe.pipeline.organization.application.dto;

import eu.venthe.pipeline.organization.domain.OrganizationId;
import eu.venthe.pipeline.project.domain.source_configurations.SourceConfigurationId;
import eu.venthe.pipeline.project.domain.source_configurations.plugins.template.model.SourceType;
import eu.venthe.pipeline.shared_kernel.configuration_properties.SuppliedProperties;
import lombok.Builder;
import lombok.NonNull;

@Builder
public record SourceConfigurationSpecification(@NonNull OrganizationId organizationId,
                                               @NonNull SourceConfigurationId configurationId,
                                               @NonNull SourceType sourceType,
                                               @NonNull SuppliedProperties properties) {

    public static class SourceConfigurationSpecificationBuilder {
        private SuppliedProperties properties = SuppliedProperties.none();

        public SourceConfigurationSpecificationBuilder sourceType(String sourceType) {
            this.sourceType = new SourceType(sourceType);
            return this;
        }

        public SourceConfigurationSpecificationBuilder configurationId(String configurationId) {
            this.configurationId = new SourceConfigurationId(configurationId);
            return this;
        }

        public SourceConfigurationSpecificationBuilder organizationId(String organizationId) {
            this.organizationId = new OrganizationId(organizationId);
            return this;
        }

        public SourceConfigurationSpecificationBuilder organizationId(OrganizationId organizationId) {
            this.organizationId =organizationId;
            return this;
        }
    }
}
