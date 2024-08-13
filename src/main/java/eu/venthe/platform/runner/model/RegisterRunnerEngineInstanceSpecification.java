package eu.venthe.platform.runner.model;

import eu.venthe.platform.runner.runner_engine.template.model.RunnerEngineType;
import eu.venthe.platform.organization.domain.OrganizationId;
import eu.venthe.platform.shared_kernel.configuration_properties.SuppliedProperties;
import lombok.Builder;
import lombok.NonNull;

@Builder
public record RegisterRunnerEngineInstanceSpecification(@NonNull OrganizationId organizationId,
                                                        @NonNull RunnerEngineInstanceId runnerEngineInstanceId,
                                                        @NonNull RunnerEngineType runnerEngineType,
                                                        @NonNull SuppliedProperties properties) {
    public static class RegisterRunnerEngineInstanceSpecificationBuilder {
        private SuppliedProperties properties = SuppliedProperties.none();


        public RegisterRunnerEngineInstanceSpecificationBuilder runnerEngineInstanceId(String runnerEngineInstanceId) {
            this.runnerEngineInstanceId = new RunnerEngineInstanceId(runnerEngineInstanceId);
            return this;
        }

        public RegisterRunnerEngineInstanceSpecificationBuilder runnerEngineType(String runnerEngineType) {
            this.runnerEngineType = new RunnerEngineType(runnerEngineType);
            return this;
        }

        public RegisterRunnerEngineInstanceSpecificationBuilder organizationId(String organizationId) {
            this.organizationId = new OrganizationId(organizationId);
            return this;
        }

        public RegisterRunnerEngineInstanceSpecificationBuilder organizationId(OrganizationId organizationId) {
            this.organizationId = organizationId;
            return this;
        }
    }
}