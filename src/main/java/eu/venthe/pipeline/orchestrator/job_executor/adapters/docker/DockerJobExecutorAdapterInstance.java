package eu.venthe.pipeline.orchestrator.job_executor.adapters.docker;

import com.github.dockerjava.api.DockerClient;
import eu.venthe.pipeline.orchestrator.job_executor.adapters.template.JobExecutorAdapter;
import eu.venthe.pipeline.orchestrator.job_executor.application.runner.RunnerDimensions;
import eu.venthe.pipeline.orchestrator.job_executor.application.runner.RunnerId;
import eu.venthe.pipeline.orchestrator.job_executor.domain.model.ExecutionId;
import eu.venthe.pipeline.orchestrator.projects.projects.domain.model.ProjectId;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.net.URL;

@Value
@RequiredArgsConstructor
public class DockerJobExecutorAdapterInstance implements JobExecutorAdapter.AdapterInstance {
    DockerClient dockerClient;

    @Override
    public void queueJobExecution(ProjectId projectId, ExecutionId executionId, RunnerDimensions dimensions, URL systemApiUrl, JobExecutorAdapter.CallbackToken callbackToken) {
        // try (CreateContainerCmd containerCmd = dockerClient.createContainerCmd("docker.home.arpa/venthe/ubuntu-runner:23.10")) {
        //     CreateContainerResponse exec = containerCmd
        //             .withEnv(
        //                     "PIPELINE_FILE_STORAGE_TYPE=nexus",
        //                     "PIPELINE_FILE_STORAGE_URL=",
        //                     "PIPELINE_VERSION_CONTROL_TYPE=ssh",
        //                     "PIPELINE_VERSION_CONTROL_SSH_PORT=1556",
        //                     "PIPELINE_VERSION_CONTROL_SSH_HOST=host.docker.internal",
        //                     "PIPELINE_VERSION_CONTROL_SSH_USERNAME=admin",
        //                     "PIPELINE_ORCHESTRATOR_URL=http://host.docker.internal:80",
        //                     "__DEBUG_SSH_PRIVATE_KEY=LS0tLS1CRUdJTiBPUEVOU1NIIFBSSVZBVEUgS0VZLS0tLS0KYjNCbGJuTnphQzFyWlhrdGRqRUFBQUFBQkc1dmJtVUFBQUFFYm05dVpRQUFBQUFBQUFBQkFBQUNGd0FBQUFkemMyZ3RjbgpOaEFBQUFBd0VBQVFBQUFnRUEyWTZHcjRmQTNueUNYdGgwMW9hQko4RmtmSm5zb2xXNjlYYzRZV2VHVUJoOE5XS3ZlbzB3CmZxV3QxUlFkZzBHc0M0eU1nY0JZaXc3TEdRK1VRYXg1RkRjKy96SzJvZ0FDd1VPS0ZvSWd3bFRTZHpwMytTK0MwTCtyUE8KRlIzcjRLMDQwY09kdzdaZ0d1aFdmTmtUYTlrM1BQK1dMWFJxaU5uK3BZeDZzeWtnS0Y1djFXRlJPTWxnUzRkYmhjMnFKUQp4RC9aZy85KzFOWjRDRDhzcXk2MTZxcERjL1Fmc0JWVVpIUThMOUk3V0VsTmRFb21TT01ldTEwQ2oweXF2NkhWdklneWhhCjBRdExGRHVubGJrNTVzL2VtbDhEUVBpRVYwSWlETGcza2UyNzV0eHdqa29UYUIwbDZFRi9TUlRLbmJHMzJBRGdRT0huMDcKS2MvSGNhbXRNeWFUMDJYbXQ5VkRlbWJoY0xTSDIxNzIvcCtwTndmSTR5SjF2OVBlUzZwL1NzVVFUUkVBNUpHQXB4ZXVPWgpaQkFWaTFmTjBTeC8zeHVUNVFZa1dOVU1EMENxREdwazBpR2dpaXVrVHZhYXRMNXRoTTNOQ213dEw1RW94OHRsR2w2eElpClIvK2kwZVJaQXN1TWJ5N1V4dGZXbi84YUZBNmdvREdXbnNqd2pyOFN3d2k2Sk9URnRWRm92bWsyRWhhRkRsQ1pBV1d0cTgKTjkxS2ZHaW1XNk5JR1lmYU42VWxCSE80K3htVmh1eHFZaGFLRXo5bXYrVEhLd2R1cE00Sy9JSkkvd1lCNVYvQ2NkNHk5aQpzYU9xcnVlNWZ2NC9jY1RlUGxFUGpXNWVHVUJkSGJVNlNOVHBXRmNCNWczTGdCa1VKRFpMS2hjNjlPelJ6Rm1Vb3VkSXRJCmtBQUFjNDJuYWpuOXAybzU4QUFBQUhjM05vTFhKellRQUFBZ0VBMlk2R3I0ZkEzbnlDWHRoMDFvYUJKOEZrZkpuc29sVzYKOVhjNFlXZUdVQmg4TldLdmVvMHdmcVd0MVJRZGcwR3NDNHlNZ2NCWWl3N0xHUStVUWF4NUZEYysveksyb2dBQ3dVT0tGbwpJZ3dsVFNkenAzK1MrQzBMK3JQT0ZSM3I0SzA0MGNPZHc3WmdHdWhXZk5rVGE5azNQUCtXTFhScWlObitwWXg2c3lrZ0tGCjV2MVdGUk9NbGdTNGRiaGMycUpReEQvWmcvOSsxTlo0Q0Q4c3F5NjE2cXBEYy9RZnNCVlVaSFE4TDlJN1dFbE5kRW9tU08KTWV1MTBDajB5cXY2SFZ2SWd5aGEwUXRMRkR1bmxiazU1cy9lbWw4RFFQaUVWMElpRExnM2tlMjc1dHh3amtvVGFCMGw2RQpGL1NSVEtuYkczMkFEZ1FPSG4wN0tjL0hjYW10TXlhVDAyWG10OVZEZW1iaGNMU0gyMTcyL3ArcE53Zkk0eUoxdjlQZVM2CnAvU3NVUVRSRUE1SkdBcHhldU9aWkJBVmkxZk4wU3gvM3h1VDVRWWtXTlVNRDBDcURHcGswaUdnaWl1a1R2YWF0TDV0aE0KM05DbXd0TDVFb3g4dGxHbDZ4SWlSLytpMGVSWkFzdU1ieTdVeHRmV24vOGFGQTZnb0RHV25zandqcjhTd3dpNkpPVEZ0VgpGb3ZtazJFaGFGRGxDWkFXV3RxOE45MUtmR2ltVzZOSUdZZmFONlVsQkhPNCt4bVZodXhxWWhhS0V6OW12K1RIS3dkdXBNCjRLL0lKSS93WUI1Vi9DY2Q0eTlpc2FPcXJ1ZTVmdjQvY2NUZVBsRVBqVzVlR1VCZEhiVTZTTlRwV0ZjQjVnM0xnQmtVSkQKWkxLaGM2OU96UnpGbVVvdWRJdElrQUFBQURBUUFCQUFBQ0FRQ2tnTHZnOEVKck9UU1BLUmMzdTE3Znc4RUs0b3ptdHZKMgpkMTUvY0h0RUJ5K3NZbUdUeGp6Y0ZzVVpzR3NjZThUdXVxam4xNXhjRWFTSzM1ZllET1ZFM3YxWWxHMDZrODJCZTJObjM1CitMc2YydXY5eEY1S0lDM0Z0TDNvZzhaL0Rody9xSzJiZGtTM00rVnA0KzVtaDBuWWF0NlFvazJJQzJZY29kdUpxMWd3amkKLzc1ZWszaWpXeEJybXl0QytxY2RyYlg0dEhHZjJFZk0zSkFYSkd2VDlFaXEzZll6Ky96ak5HenFNQXBGbUsvamRjbjdKUgpsd3pVU0pQSXlEK0lnWVZkZjE4a1lHaTE5T0drT2ZuSlNIYm12VjlGZW1UL1lmZERMT3lBQUlZcmE1WUVVWWpaR2VrMjlJCmVkcUFZSk5YOXc2a0NuYnVvUHppZ2NVcWNnV09tY2pXL0NOZmhiMlRCRndWT0hMcFc5bEo3cGE4Si94SFFxOGw3S0hTZkcKMHNyWHJLbUdoZzZyWkRLYk1JcEVTMHVMcTlrWEYxcUhkZGZESzJSeGVRbitZWW5xSG1QTis4NWRhcGdxSmVUejMxZTRZVQo5aE53bGlUMFZJNG9XOGU0MHM1TlBpQXArWVpMaURtY1RIbVZaWHZ5SXVtMFlPbk8rdEZUK3FSQ1psUUo1RHk5TnRnZGJGCktPRmVTQkl3T2pjanBHTDgxampQRVpMVTM4UEszbUp1UlRhRUdaL1FDZGQyUHRnSnU1eVJLTUpPUEFzb2dCbnlYdi85K00KdEdTdGYvblFqWkJNdkVwcFpMZkxkSjMraW1wSjhUelIxWGFEMkVuOUtGYmxZczk4MjZTb0hsOTZHZnVTQ2FiaVNFTnY0Vgp3VndUSTU3cVU0T2JZOVRaaDlEUUFBQVFBSDlXc2VrdlZNeEROTkpRNTFGaDhIWHE4ZVJyQkNuMTNydGlmYnlSbVNLL0FQCmFVN2VOeU5zYWdWeU5KRkxzUm5LZnQrbWFsRUVvVklBK1F1Z1Jmb3U2VXJGTnQzY3dpUnNYZFJER3I2RFlGbHNPaHNWV2MKTDhGMmdMZXNETklFZVc1azFCc2w5TitMendPR083WklUcENTWXdUdUdnTm1NdDYwSGdrSk5rKy83TzQzZXBGVXZBTk5RcwoxYTFZcXhkbFQwMC9MT0xHbC9HTGM5YlJqNTVRajBzOG1neFpsYndtMXQ5TTVoMTNQL2sxZGhPay92Yld6emZhWXhCZVIxCmsxbEVUaTBSN2pQTTJxdGlaYktpRU40VXl5TUxPVlVlejc1SzhjM0VRRkZtb2M2SVNOZldjMHFCRVBXWmhrMlROeU5WMEwKd1BEVy84RSszSUl6ZDJ1OEFBQUJBUUQwV3NsalZINjJvanBCYmNuQTQ3WEdVVXF0NjBIWFl6blJXbC9jcG0rZEVTSGN2SgoxUFJ4dkJTc2ZUdHpJQm1LT3JqU0kxb0xCNUhrbzBTdU11bGRjbXdTYXdvN1AzWEVKZ242d2VyS3ZqbWpPMTRtNjJ5NUFrCllhNU0rWTVYUnA5cmtNVHBoVSsxUFZpMVJ2aGdTVzMycWx1dmVZd1l0UCtWVmN2QkZvMWpZRDVJcE53d2REa2VMUGV0UU8KOWpPV0lnMXk5dmZ5ZmtDUEM0OE12NmhHWlc0ZmZ2ZUZGQWxIRUNZMm9DOVBweWFNQnU1WUJ3TmxkcGdCK0hGSTlYTHJqegpOem5mQlVvOFBrM1l6Q3hsNnhwYUJabkFJUThuazM2ZEJaTlZtcGFybjQxVW42cDBzTHlNMmgzbVBxb3U1RTU3eENHQzhTCnFWMUtsMnRIZmJvY0NMQUFBQkFRRGo3TXV1d2F0UmNuVlozWUZVa0loYkFqZGpWL1QxUVRnbFhVOXM1VEs3S2ZQdXBjdFEKOG9sQzBSUlRUK05JY1RWMlVZS0tKamFtUCtvVEJsWUd3R0x4Sk1UakFkQU9CeUdXdXBYTDBMNTJ4eTVwcnNJSmQ3cVVrVQpzVS9SZ21XRG12a25Ka2xaaW9XaWJhWGEzeXNQZHNUbkVWMWd0QU1OSXZYUGc1MGlNbXBwd2VxZXdkNE5CV0RXT09NdzlaCjRrOWtDeHpuY2dNUURMSjR4L3ZwUVJBWW0rS01XbzdkUVRNRXNidmNkM3lIT1BLaGZUc0llR0tFaEx0a1BCOUJ6MmtaSVAKRUZNdUFiekxGbzAvdi83aEZ2Q2RHL3Fza29XUlBpUm52VldQRkxhazQ5NldxaUh0UHZzVzNndlpFTk1yalBmeEVsN1hWTgpwZCtOb1NTbFRnMjdBQUFBQUFFQwotLS0tLUVORCBPUEVOU1NIIFBSSVZBVEUgS0VZLS0tLS0K",
        //                     "PIPELINE_WORKFLOW_EXECUTION_ID=" + workflowExecutionId,
        //                     "PIPELINE_JOB_NAME=" + jobId
        //             )
        //             .withTty(true)
        //             .withName("action_runner-" + UUID.randomUUID())
        //             .exec();
        //     dockerClient.startContainerCmd(exec.getId()).exec();
        // }
        throw new UnsupportedOperationException();
    }

    @Override
    public RunnerId registerRunner(RunnerDimensions.Dimension... dimensions) {
        throw new UnsupportedOperationException();
    }
}
