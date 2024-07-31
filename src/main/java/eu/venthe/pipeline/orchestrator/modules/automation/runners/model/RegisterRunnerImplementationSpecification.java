package eu.venthe.pipeline.orchestrator.modules.automation.runners.model;

import eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template.model.RunnerImplementationId;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template.model.RunnerEngineType;
import eu.venthe.pipeline.orchestrator.organizations.domain.OrganizationId;
import eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties.SuppliedProperties;
import lombok.Builder;
import lombok.NonNull;

@Builder
public record RegisterRunnerImplementationSpecification(@NonNull OrganizationId organizationId,
                                                        @NonNull RunnerImplementationId runnerImplementationId,
                                                        @NonNull RunnerEngineType runnerEngineType,
                                                        @NonNull SuppliedProperties properties) {
    public static class RegisterAdapterSpecificationBuilder {
        private SuppliedProperties properties = SuppliedProperties.none();


        public RegisterAdapterSpecificationBuilder adapterId(String adapterId) {
            this.adapterId = new RunnerImplementationId(adapterId);
            return this;
        }

        public RegisterAdapterSpecificationBuilder adapterType(String adapterType) {
            this.adapterType = new RunnerEngineType(adapterType);
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
