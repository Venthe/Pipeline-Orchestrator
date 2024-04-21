package eu.venthe.pipeline.orchestrator.shared_kernel.job_execution;

/**
 * Contexts are a way to access information about workflow runs, variables, runner environments, jobs, and steps. Each context is an object that contains properties, which can be strings or other objects.
 * <p>
 * Contexts, objects, and properties will vary significantly under different workflow run conditions. For example, the matrix context is only populated for jobs in a matrix.
 * <p>
 * You can access contexts using the expression syntax. For more information, see "Expressions."
 * <p>
 * ${{ <context> }}
 */
public interface JobContext_ {
}
