package eu.venthe.platform.projects.application;

import eu.venthe.platform.projects.domain.Project;
import eu.venthe.platform.projects.domain.ProjectCorrelationId;
import eu.venthe.platform.projects.domain.ProjectRepository;
import eu.venthe.platform.projects.domain.SourceConfigurationInternalIdentifier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProjectQueryService {
    private final ProjectRepository projectRepository;

    public Optional<ProjectDto> getProject(SourceConfigurationInternalIdentifier sourceConfigurationInternalIdentifier,
                                           ProjectCorrelationId projectCorrelationId) {
        return projectRepository.find(sourceConfigurationInternalIdentifier, projectCorrelationId).map(ProjectVisitor::toDto);
    }

    private static class ProjectVisitor implements eu.venthe.platform.projects.domain.ProjectVisitor {
        private final ProjectDto.ProjectDtoBuilder builder = ProjectDto.builder();

        @Override
        public void setCorrelationId(ProjectCorrelationId projectCorrelationId) {
            builder.projectCorrelationId(projectCorrelationId);
        }

        @Override
        public void setLastUpdated(ZonedDateTime lastUpdate) {
            builder.lastUpdated(lastUpdate);
        }

        @Override
        public void setSourceConfigurationIdentifier(SourceConfigurationInternalIdentifier sourceConfigurationInternalIdentifier) {
            builder.sourceInternalIdentifier(sourceConfigurationInternalIdentifier);
        }

        public ProjectDto build() {
            return builder.build();
        }

        private static ProjectDto toDto(Project project) {
            var visitor = new ProjectQueryService.ProjectVisitor();
            project.visit(visitor);
            return visitor.build();
        }
    }
}
