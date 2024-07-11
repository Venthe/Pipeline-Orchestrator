package eu.venthe.pipeline.orchestrator.modules.workflow.application.dto;

import eu.venthe.pipeline.orchestrator.modules.workflow.domain.job_executions.adapters.template.model.AdapterId;
import eu.venthe.pipeline.orchestrator.modules.workflow.domain.job_executions.adapters.template.model.AdapterType;
import eu.venthe.pipeline.orchestrator.organizations.domain.OrganizationId;
import eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties.SuppliedProperties;
import lombok.Builder;
import lombok.NonNull;

@Builder
public record RegisterAdapterSpecification(@NonNull OrganizationId organizationId,
                                           @NonNull AdapterId adapterId,
                                           @NonNull AdapterType adapterType,
                                           @NonNull SuppliedProperties properties) {
    public static class RegisterAdapterSpecificationBuilder {
        private SuppliedProperties properties = SuppliedProperties.none();


        public RegisterAdapterSpecificationBuilder adapterId(String adapterId) {
            this.adapterId = new AdapterId(adapterId);
            return this;
        }

        public RegisterAdapterSpecificationBuilder adapterType(String adapterType) {
            this.adapterType = new AdapterType(adapterType);
            return this;
        }

        public RegisterAdapterSpecificationBuilder organizationId(String organizationId) {
            this.organizationId = new OrganizationId(organizationId);
            return this;
        }

        public RegisterAdapterSpecificationBuilder organizationId(OrganizationId organizationId) {
            this.organizationId = organizationId;
            return this;
        }
    }
}
