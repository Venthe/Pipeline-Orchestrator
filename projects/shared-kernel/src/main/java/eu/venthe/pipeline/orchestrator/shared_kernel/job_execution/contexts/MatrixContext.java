package eu.venthe.pipeline.orchestrator.shared_kernel.job_execution.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * For workflows with a matrix, the matrix context contains the matrix properties defined in the workflow file that
 * apply to the current job. For example, if you configure a matrix with the os and node keys, the matrix context object
 * includes the os and node properties with the values that are being used for the current job.
 * <p>
 * There are no standard properties in the matrix context, only those which are defined in the workflow file.
 * <p>
 * This context is only available for jobs in a matrix, and changes for each job in a workflow run. You can access this
 * context from any job or step in a workflow. This object contains the properties listed below.
 */
public class MatrixContext {
    /**
     * { "os": "ubuntu-latest", "node": 16 }
     */
    ObjectNode values;

    public static MatrixContext ensure(JsonNode matrix) {
        throw new UnsupportedOperationException();
    }
}
