/*
package eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.contexts.JobsContext;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.jobs.JobRuns;
import org.junit.jupiter.api.Test;

class JobsTestExecution {
    @Test
    void name() throws JsonProcessingException {
        String jobs_value = """
                job_a: {}
                job_b: {}
                job_c:
                  needs: job_a
                job_d:
                  needs:
                    - job_c
                    - job_a
                job_e:
                  needs: job_a
                """;

        var mapper = new YAMLMapper();

        var root = mapper.readTree(jobs_value);
        var jobs = JobsContext.ensure(root);

        var jobs1 = new JobRuns(jobs);
        jobs1.start(null, null);
    }
}
*/
